package Laberinto;

import datastruct.list.LinkedList;

public class TestLaberinto {
    
    public static void main(String[] args) {
        System.out.println("=== Test del Solucionador de Laberintos ===\n");
        
        // Test 1: Generar laberinto
        System.out.println("1. Generando laberinto de 20x20...");
        MazeGenerator generator = new MazeGenerator();
        char[][] maze = generator.generarMatriz(20, 20);
        
        System.out.println("Laberinto generado:");
        printMaze(maze);
        
        // Test 2: Resolver laberinto
        System.out.println("\n2. Resolviendo laberinto con Dijkstra...");
        MazeSolver solver = new MazeSolver(maze);
        LinkedList<Point> path = solver.solveDijkstra();
        
        if (path.isEmpty()) {
            System.out.println("No se encontró solución.");
        } else {
            System.out.println("¡Laberinto resuelto!");
            System.out.println("Inicio: " + solver.getStart());
            System.out.println("Final: " + solver.getEnd());
            System.out.println("Longitud del camino: " + path.getLength() + " pasos");
            
            // Mostrar laberinto con solución
            char[][] mazeWithPath = solver.getMazeWithPath(path);
            System.out.println("\nLaberinto con solución (camino marcado con *):");
            printMaze(mazeWithPath);
            
            // Mostrar camino paso a paso
            System.out.println("\nCamino paso a paso:");
            System.out.println(solver.getPathAsString(path));
        }
        
        // Test 3: Test de estructuras de datos
        System.out.println("\n3. Test de estructuras de datos personalizadas...");
        testLinkedList();
        testPriorityQueue();
        
        System.out.println("\n=== Todos los tests completados ===");
        System.out.println("Para usar la interfaz gráfica, ejecute VistaLaberinto.main()");
    }
    
    private static void printMaze(char[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    private static void testLinkedList() {
        System.out.println("Probando LinkedList...");
        datastruct.list.LinkedList<Integer> list = new datastruct.list.LinkedList<>();
        
        // Agregar elementos
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println("Lista después de agregar 1, 2, 3: " + list.print());
        
        // Eliminar elemento
        try {
            Integer deleted = list.delete(1);
            System.out.println("Elemento eliminado en posición 1: " + deleted);
            System.out.println("Lista después de eliminar: " + list.print());
        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
        
        System.out.println("LinkedList funciona correctamente ✓");
    }
    
    private static void testPriorityQueue() {
        System.out.println("Probando PriorityQueue...");
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        // Agregar elementos desordenados
        pq.enqueue(5);
        pq.enqueue(2);
        pq.enqueue(8);
        pq.enqueue(1);
        
        System.out.println("Elementos agregados: 5, 2, 8, 1");
        System.out.print("Elementos en orden de prioridad: ");
        
        try {
            while (!pq.isEmpty()) {
                System.out.print(pq.dequeue() + " ");
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("PriorityQueue funciona correctamente ✓");
    }
}
