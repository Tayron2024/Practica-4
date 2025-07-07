package Laberinto;

import datastruct.list.LinkedList;
import datastruct.list.LinkedList;

public class MazeGenerator {
    
    public String generar(int r, int c) {
        // build maze and initialize with only walls
        StringBuilder s = new StringBuilder(c);
        for (int x = 0; x < c; x++) {
            s.append('0');
        }
        char[][] maz = new char[r][c];
        for (int x = 0; x < r; x++) {
            maz[x] = s.toString().toCharArray();
        }

        // select random point and open as start node
        Point st = new Point((int) (Math.random() * r), (int) (Math.random() * c), null);
        maz[st.getR()][st.getC()] = 'S';

        // iterate through direct neighbors of node
        LinkedList<Point> frontier = new LinkedList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0) {
                    continue;
                }
                try {
                    if (maz[st.getR() + x][st.getC() + y] == '1') {
                        continue;
                    }
                } catch (Exception e) { // ignore ArrayIndexOutOfBounds
                    continue;
                }
                // add eligible points to frontier
                frontier.add(new Point(st.getR() + x, st.getC() + y, st));
            }
        }

        Point last = null;
        while (!frontier.isEmpty()) {
            // pick current node at random
            int randomIndex = (int) (Math.random() * frontier.getLength());
            Point cu = null;
            try {
                cu = frontier.delete(randomIndex);
            } catch (Exception e) {
                continue;
            }
            
            Point op = cu.opposite();
            try {
                // if both node and its opposite are walls
                if (maz[cu.getR()][cu.getC()] == '0') {
                    if (maz[op.getR()][op.getC()] == '0') {

                        // open path between the nodes
                        maz[cu.getR()][cu.getC()] = '1';
                        maz[op.getR()][op.getC()] = '1';

                        // store last node in order to mark it later
                        last = op;

                        // iterate through direct neighbors of node, same as earlier
                        for (int x = -1; x <= 1; x++) {
                            for (int y = -1; y <= 1; y++) {
                                if (x == 0 && y == 0 || x != 0 && y != 0) {
                                    continue;
                                }
                                try {
                                    if (maz[op.getR() + x][op.getC() + y] == '1') {
                                        continue;
                                    }
                                } catch (Exception e) {
                                    continue;
                                }
                                frontier.add(new Point(op.getR() + x, op.getC() + y, op));
                            }
                        }
                    }
                }
            } catch (Exception e) { // ignore NullPointer and ArrayIndexOutOfBounds
            }

            // if algorithm has resolved, mark end node
            if (frontier.isEmpty() && last != null) {
                maz[last.getR()][last.getC()] = 'E';
            }
        }

        s = new StringBuilder();
        for (int i = 0; i < r; i++) {
            String aux = "";
            for (int j = 0; j < c; j++) {
                aux += maz[i][j] + ",";
            }
            aux = aux.substring(0, aux.length() - 1);
            s.append(aux).append("\n");
        }
        
        return s.toString();
    }

    public char[][] generarMatriz(int r, int c) {
        // build maze and initialize with only walls
        StringBuilder s = new StringBuilder(c);
        for (int x = 0; x < c; x++) {
            s.append('0');
        }
        char[][] maz = new char[r][c];
        for (int x = 0; x < r; x++) {
            maz[x] = s.toString().toCharArray();
        }

        // select random point and open as start node
        Point st = new Point((int) (Math.random() * r), (int) (Math.random() * c), null);
        maz[st.getR()][st.getC()] = 'S';

        // iterate through direct neighbors of node
        LinkedList<Point> frontier = new LinkedList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0) {
                    continue;
                }
                try {
                    if (maz[st.getR() + x][st.getC() + y] == '1') {
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }
                frontier.add(new Point(st.getR() + x, st.getC() + y, st));
            }
        }

        Point last = null;
        while (!frontier.isEmpty()) {
            int randomIndex = (int) (Math.random() * frontier.getLength());
            Point cu = null;
            try {
                cu = frontier.delete(randomIndex);
            } catch (Exception e) {
                continue;
            }
            
            Point op = cu.opposite();
            try {
                if (maz[cu.getR()][cu.getC()] == '0') {
                    if (maz[op.getR()][op.getC()] == '0') {
                        maz[cu.getR()][cu.getC()] = '1';
                        maz[op.getR()][op.getC()] = '1';
                        last = op;

                        for (int x = -1; x <= 1; x++) {
                            for (int y = -1; y <= 1; y++) {
                                if (x == 0 && y == 0 || x != 0 && y != 0) {
                                    continue;
                                }
                                try {
                                    if (maz[op.getR() + x][op.getC() + y] == '1') {
                                        continue;
                                    }
                                } catch (Exception e) {
                                    continue;
                                }
                                frontier.add(new Point(op.getR() + x, op.getC() + y, op));
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }

            if (frontier.isEmpty() && last != null) {
                maz[last.getR()][last.getC()] = 'E';
            }
        }

        return maz;
    }
}
