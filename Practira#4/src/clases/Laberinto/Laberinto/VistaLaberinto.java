package Laberinto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import datastruct.list.LinkedList;

public class VistaLaberinto extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panelControl;
    private JPanel panelMaze;
    private JSpinner spinnerDimension;
    private JButton btnGenerar;
    private JButton btnResolver;
    private JTextArea textAreaInfo;
    private JScrollPane scrollPane;
    
    private char[][] laberinto;
    private MazeGenerator generator;
    private MazeSolver solver;
    private LinkedList<Point> camino;

    public VistaLaberinto() {
        initComponents();
        setupLayout();
        setupListeners();
        generator = new MazeGenerator();
    }

    private void initComponents() {
        setTitle("Solucionador de Laberintos - Algoritmo Dijkstra");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        panelPrincipal = new JPanel(new BorderLayout());
        panelControl = new JPanel(new FlowLayout());
        panelMaze = new JPanel();
        
        spinnerDimension = new JSpinner(new SpinnerNumberModel(30, 30, 100, 1));
        spinnerDimension.setPreferredSize(new Dimension(80, 30));
        
        btnGenerar = new JButton("Generar Laberinto");
        btnResolver = new JButton("Resolver con Dijkstra");
        btnResolver.setEnabled(false);
        
        textAreaInfo = new JTextArea(8, 40);
        textAreaInfo.setEditable(false);
        textAreaInfo.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        scrollPane = new JScrollPane(textAreaInfo);
    }

    private void setupLayout() {
        // Panel de control
        panelControl.add(new JLabel("Dimensión:"));
        panelControl.add(spinnerDimension);
        panelControl.add(btnGenerar);
        panelControl.add(btnResolver);
        
        // Panel principal
        panelPrincipal.add(panelControl, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(panelMaze), BorderLayout.CENTER);
        panelPrincipal.add(scrollPane, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }

    private void setupListeners() {
        btnGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarLaberinto();
            }
        });

        btnResolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resolverLaberinto();
            }
        });
    }

    private void generarLaberinto() {
        try {
            btnGenerar.setEnabled(false);
            btnResolver.setEnabled(false);
            
            int dimension = (Integer) spinnerDimension.getValue();
            textAreaInfo.setText("Generando laberinto de " + dimension + "x" + dimension + "...\n");
            
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    laberinto = generator.generarMatriz(dimension, dimension);
                    return null;
                }
                
                @Override
                protected void done() {
                    dibujarLaberinto();
                    textAreaInfo.append("Laberinto generado exitosamente.\n");
                    textAreaInfo.append("S = Inicio, E = Final, 0 = Pared, 1 = Camino\n");
                    btnGenerar.setEnabled(true);
                    btnResolver.setEnabled(true);
                }
            };
            worker.execute();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al generar laberinto: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            btnGenerar.setEnabled(true);
        }
    }

    private void resolverLaberinto() {
        if (laberinto == null) {
            JOptionPane.showMessageDialog(this, "Primero debe generar un laberinto", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            btnResolver.setEnabled(false);
            textAreaInfo.append("\nResolviendo laberinto con algoritmo de Dijkstra...\n");
            
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    solver = new MazeSolver(laberinto);
                    camino = solver.solveDijkstra();
                    return null;
                }
                
                @Override
                protected void done() {
                    if (camino.isEmpty()) {
                        textAreaInfo.append("No se encontró solución al laberinto.\n");
                    } else {
                        textAreaInfo.append("¡Laberinto resuelto!\n");
                        textAreaInfo.append("Inicio: " + solver.getStart() + "\n");
                        textAreaInfo.append("Final: " + solver.getEnd() + "\n");
                        textAreaInfo.append("Longitud del camino: " + camino.getLength() + " pasos\n");
                        
                        // Actualizar visualización con el camino
                        char[][] laberintoConCamino = solver.getMazeWithPath(camino);
                        dibujarLaberintoConCamino(laberintoConCamino);
                        
                        textAreaInfo.append("Camino marcado con '*'\n");
                    }
                    btnResolver.setEnabled(true);
                }
            };
            worker.execute();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al resolver laberinto: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            btnResolver.setEnabled(true);
        }
    }

    private void dibujarLaberinto() {
        if (laberinto == null) return;
        
        panelMaze.removeAll();
        int rows = laberinto.length;
        int cols = laberinto[0].length;
        
        panelMaze.setLayout(new GridLayout(rows, cols, 1, 1));
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JPanel celda = new JPanel();
                celda.setPreferredSize(new Dimension(8, 8));
                celda.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                
                switch (laberinto[i][j]) {
                    case '0': // Pared
                        celda.setBackground(Color.BLACK);
                        break;
                    case '1': // Camino
                        celda.setBackground(Color.WHITE);
                        break;
                    case 'S': // Inicio
                        celda.setBackground(Color.GREEN);
                        break;
                    case 'E': // Final
                        celda.setBackground(Color.RED);
                        break;
                    default:
                        celda.setBackground(Color.GRAY);
                }
                
                panelMaze.add(celda);
            }
        }
        
        panelMaze.revalidate();
        panelMaze.repaint();
    }

    private void dibujarLaberintoConCamino(char[][] laberintoConCamino) {
        panelMaze.removeAll();
        int rows = laberintoConCamino.length;
        int cols = laberintoConCamino[0].length;
        
        panelMaze.setLayout(new GridLayout(rows, cols, 1, 1));
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JPanel celda = new JPanel();
                celda.setPreferredSize(new Dimension(8, 8));
                celda.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                
                switch (laberintoConCamino[i][j]) {
                    case '0': // Pared
                        celda.setBackground(Color.BLACK);
                        break;
                    case '1': // Camino
                        celda.setBackground(Color.WHITE);
                        break;
                    case 'S': // Inicio
                        celda.setBackground(Color.GREEN);
                        break;
                    case 'E': // Final
                        celda.setBackground(Color.RED);
                        break;
                    case '*': // Camino solución
                        celda.setBackground(Color.YELLOW);
                        break;
                    default:
                        celda.setBackground(Color.GRAY);
                }
                
                panelMaze.add(celda);
            }
        }
        
        panelMaze.revalidate();
        panelMaze.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getLookAndFeel());
                } catch (Exception e) {
                    // Usar look and feel por defecto
                    System.out.println("No se pudo establecer el look and feel del sistema");
                }
                
                new VistaLaberinto().setVisible(true);
            }
        });
    }
}
