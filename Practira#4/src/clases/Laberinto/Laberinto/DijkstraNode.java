package Laberinto;

public class DijkstraNode implements Comparable<DijkstraNode> {
    private Point position;
    private Float distance;
    private DijkstraNode parent;
    private boolean visited;

    public DijkstraNode(Point position) {
        this.position = position;
        this.distance = Float.POSITIVE_INFINITY;
        this.parent = null;
        this.visited = false;
    }

    public Point getPosition() {
        return position;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public DijkstraNode getParent() {
        return parent;
    }

    public void setParent(DijkstraNode parent) {
        this.parent = parent;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public int compareTo(DijkstraNode other) {
        return Float.compare(this.distance, other.distance);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DijkstraNode that = (DijkstraNode) obj;
        return position.equals(that.position);
    }

    @Override
    public String toString() {
        return "DijkstraNode{" +
                "position=" + position +
                ", distance=" + distance +
                ", visited=" + visited +
                '}';
    }
}
