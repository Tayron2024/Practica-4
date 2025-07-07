package datastruct.graphs;

import java.lang.reflect.Array;
import java.util.HashMap;

import datastruct.list.LinkedList;

public class DirectLabelGraph <E> extends DirectedGraph {
    protected E labels[];
    protected HashMap<E, Integer> dicVertex;
    private Class clazz;
    
    public DirectLabelGraph(Integer nro_vertex, Class clazz) {
        super(nro_vertex);
        this.clazz = clazz;
        this.labels = (E[]) Array.newInstance(this.clazz, nro_vertex + 1);
        this.dicVertex = new HashMap<>(nro_vertex);
    }

    public Adjacency exists_edge_label(E o, E d) {
        if(isLabelsGraph()){
            return exists_edge(getVertex(o), getVertex(d));
        }
        return null;
    }

    public void insert_label (E o, E d, Float weight) {
        if(isLabelsGraph()){
            insert(getVertex(o), getVertex(d), weight);
        }
    }

    public void insert_label (E o, E d) {
        if(isLabelsGraph()){
            insert(getVertex(o), getVertex(d), Float.NaN);
        }
    }

    public LinkedList<Adjacency> adjacencies_label(E o) {
        if(isLabelsGraph()){
            return adjacencies(getVertex(o));
        }
        return new LinkedList<>();
    }

    public void label_vertex(Integer vertex, E data){
        labels[vertex] = data;
        dicVertex.put(data, vertex);
    }

    public Boolean isLabelsGraph(){
        Boolean band = false;
        for(int i = 1 ; i <= nro_vertex(); i++){
            if(labels[i] != null){
                band = true;
                break;
            }
        }
        return band;
    }

    public Integer getVertex(E label) {
        return dicVertex.get(label);
    }

    public E getLabel(Integer i) {
        return labels[i];
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 1 ; i <= nro_vertex(); i++){
            sb.append("Vertex = ").append(getLabel(i)).append("\n");
            LinkedList<Adjacency> list = adjacencies(i);
            if(!list.isEmpty()) {
                Adjacency[] matrix = list.toArray();
                for(Adjacency ad : matrix){
                    sb.append("\tAdjacency").append("\n").append("\tVertex = ").append(String.valueOf(getLabel(ad.getDestiny())));
                    if(!ad.getWeight().isNaN()) {
                        sb.append(" Weight = " + ad.getWeight().toString()).append("\n");
                    }
                }
            }
        }
        return sb.toString();
    } 
}
