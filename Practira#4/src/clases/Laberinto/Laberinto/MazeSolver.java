package Laberinto;

import datastruct.list.LinkedList;

public class MazeSolver {
    private char[][] maze;
    private int rows;
    private int cols;
    private Point start;
    private Point end;

    public MazeSolver(char[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
        findStartAndEnd();
    }

    private void findStartAndEnd() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 'S') {
                    start = new Point(i, j, null);
                } else if (maze[i][j] == 'E') {
                    end = new Point(i, j, null);
                }
            }
        }
    }

    public LinkedList<Point> solveDijkstra() {
        if (start == null || end == null) {
            return new LinkedList<>();
        }

        // Matrices para el algoritmo de Dijkstra
        float[][] distances = new float[rows][cols];
        boolean[][] visited = new boolean[rows][cols];
        Point[][] previous = new Point[rows][cols];

        // Inicializar distancias
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                distances[i][j] = Float.POSITIVE_INFINITY;
                visited[i][j] = false;
                previous[i][j] = null;
            }
        }

        distances[start.getR()][start.getC()] = 0.0f;

        // Cola de prioridad simple usando LinkedList
        LinkedList<Point> queue = new LinkedList<>();
        queue.add(start);

        // Direcciones: arriba, abajo, izquierda, derecha
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            // Encontrar el nodo con menor distancia en la cola
            Point current = findMinDistanceNode(queue, distances);
            if (current == null) break;

            // Remover de la cola
            removeFromQueue(queue, current);

            // Marcar como visitado
            visited[current.getR()][current.getC()] = true;

            // Si llegamos al destino, podemos terminar
            if (current.getR().equals(end.getR()) && current.getC().equals(end.getC())) {
                break;
            }

            // Explorar vecinos
            for (int i = 0; i < 4; i++) {
                int newRow = current.getR() + dx[i];
                int newCol = current.getC() + dy[i];

                // Verificar límites y que no sea una pared
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    if (maze[newRow][newCol] != '0' && !visited[newRow][newCol]) {
                        
                        float newDistance = distances[current.getR()][current.getC()] + 1.0f;
                        
                        if (newDistance < distances[newRow][newCol]) {
                            distances[newRow][newCol] = newDistance;
                            previous[newRow][newCol] = current;
                            
                            // Agregar a la cola si no está ya
                            Point neighbor = new Point(newRow, newCol, null);
                            if (!containsPoint(queue, neighbor)) {
                                queue.add(neighbor);
                            }
                        }
                    }
                }
            }
        }

        // Reconstruir el camino
        return reconstructPath(previous);
    }

    /**
     * Encuentra el nodo con menor distancia en la cola
     */
    private Point findMinDistanceNode(LinkedList<Point> queue, float[][] distances) {
        if (queue.isEmpty()) return null;

        Point minNode = null;
        float minDistance = Float.POSITIVE_INFINITY;

        try {
            for (int i = 0; i < queue.getLength(); i++) {
                Point p = queue.get(i);
                float dist = distances[p.getR()][p.getC()];
                if (dist < minDistance) {
                    minDistance = dist;
                    minNode = p;
                }
            }
        } catch (Exception e) {
            // Error al acceder a la cola
        }

        return minNode;
    }

    /**
     * Remueve un punto específico de la cola
     */
    private void removeFromQueue(LinkedList<Point> queue, Point target) {
        try {
            for (int i = 0; i < queue.getLength(); i++) {
                Point p = queue.get(i);
                if (p.getR().equals(target.getR()) && p.getC().equals(target.getC())) {
                    queue.delete(i);
                    break;
                }
            }
        } catch (Exception e) {
            // Error al remover de la cola
        }
    }

    /**
     * Verifica si la cola contiene un punto específico
     */
    private boolean containsPoint(LinkedList<Point> queue, Point target) {
        try {
            for (int i = 0; i < queue.getLength(); i++) {
                Point p = queue.get(i);
                if (p.getR().equals(target.getR()) && p.getC().equals(target.getC())) {
                    return true;
                }
            }
        } catch (Exception e) {
            // Error al buscar en la cola
        }
        return false;
    }

    /**
     * Reconstruye el camino desde el final hasta el inicio
     */
    private LinkedList<Point> reconstructPath(Point[][] previous) {
        LinkedList<Point> path = new LinkedList<>();
        
        if (previous[end.getR()][end.getC()] == null) {
            // No hay camino
            return path;
        }

        Point current = end;
        while (current != null) {
            path.add(current, 0); // Insertar al inicio para obtener el orden correcto
            current = previous[current.getR()][current.getC()];
        }

        return path;
    }

    /**
     * Genera una representación en texto del camino encontrado
     */
    public String getPathAsString(LinkedList<Point> path) {
        if (path.isEmpty()) {
            return "No se encontró camino";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Camino encontrado (").append(path.getLength()).append(" pasos):\n");
        
        try {
            for (int i = 0; i < path.getLength(); i++) {
                Point p = path.get(i);
                sb.append("(").append(p.getR()).append(",").append(p.getC()).append(")");
                if (i < path.getLength() - 1) {
                    sb.append(" -> ");
                }
            }
        } catch (Exception e) {
            sb.append("Error al generar el camino");
        }

        return sb.toString();
    }

    /**
     * Crea una copia del laberinto con el camino marcado
     */
    public char[][] getMazeWithPath(LinkedList<Point> path) {
        char[][] result = new char[rows][cols];
        
        // Copiar laberinto original
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = maze[i][j];
            }
        }

        // Marcar el camino (excepto inicio y fin)
        try {
            for (int i = 1; i < path.getLength() - 1; i++) { // Saltar inicio (0) y fin (length-1)
                Point p = path.get(i);
                if (result[p.getR()][p.getC()] == '1') {
                    result[p.getR()][p.getC()] = '*';
                }
            }
        } catch (Exception e) {
            // Error al marcar el camino
        }

        return result;
    }

    /**
     * Obtiene el punto de inicio del laberinto
     */
    public Point getStart() {
        return start;
    }


    //Obtiene el punto final del laberinto
    public Point getEnd() {
        return end;
    }

    //Obtiene las dimensiones del laberinto

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /**
     * Verifica si existe un camino entre dos puntos específicos
     */
    public boolean hasPath() {
        LinkedList<Point> path = solveDijkstra();
        return !path.isEmpty();
    }
}
