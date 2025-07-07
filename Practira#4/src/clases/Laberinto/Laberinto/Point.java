package Laberinto;

public class Point {
    private Integer r;
    private Integer c;
    private Point parent;

    public Point(int x, int y, Point p) {
        r = x;
        c = y;
        parent = p;
    }

    public Integer getR() {
        return r;
    }

    public Integer getC() {
        return c;
    }

    public Point getParent() {
        return parent;
    }

    // compute opposite node given that it is in the other direction from the parent
    public Point opposite() {
        if (parent == null) return null;
        
        if (this.r.compareTo(parent.r) != 0) {
            return new Point(this.r + this.r.compareTo(parent.r), this.c, this);
        }
        if (this.c.compareTo(parent.c) != 0) {
            return new Point(this.r, this.c + this.c.compareTo(parent.c), this);
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return r.equals(point.r) && c.equals(point.c);
    }

    @Override
    public String toString() {
        return "(" + r + "," + c + ")";
    }
}
