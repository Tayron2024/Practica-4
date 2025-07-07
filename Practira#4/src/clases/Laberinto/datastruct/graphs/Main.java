package datastruct.graphs;

public class Main {
    public static void main(String[] args) {
        //Grafo dirigido
        System.out.println("---GRAFO DIRIGIDO---");
        Graph grafo = new DirectedGraph(5);
        grafo.insert(1, 5, 0.65f);
        grafo.insert(2, 4, 0.85f);
        System.out.println(grafo.toString());
        //Grafo no dirigido
        System.out.println("---GRAFO NO DIRIGIDO---");
        Graph grafoU = new UndirectedGraph(5);
        grafoU.insert(1, 5, 0.65f);
        grafoU.insert(2, 4, 0.85f);
        System.out.println(grafoU.toString());
        //Grafo dirigido con etiquetas
        System.out.println("---GRAFO DIRIGIDO CON ETIQUETAS---");
        DirectLabelGraph gd = new DirectLabelGraph<>(5, String.class);
        gd.label_vertex(1, "Nole");
        gd.label_vertex(2, "Anahi");
        gd.label_vertex(3, "Douglas");
        gd.label_vertex(4, "Ariana");
        gd.label_vertex(5, "Maria");
        gd.insert_label("Maria", "Douglas", 10.56f);
        gd.insert_label("Maria", "Ariana", 1.67f);
        gd.insert_label("Nole", "Anahi", 5.67f);
        System.out.println(gd.toString());
        //Grafo no dirigido con etiquetas
        System.out.println("---GRAFO NO DIRIGIDO CON ETIQUETAS---");
        UndirectLabelGraph gu = new UndirectLabelGraph<>(5, String.class);
        gu.label_vertex(1, "Nole");
        gu.label_vertex(2, "Anahi");
        gu.label_vertex(3, "Douglas");
        gu.label_vertex(4, "Ariana");
        gu.label_vertex(5, "Maria");
        gu.insert_label("Maria", "Douglas", 10.56f);
        gu.insert_label("Maria", "Ariana", 1.67f);
        gu.insert_label("Nole", "Anahi", 5.67f);
        System.out.println(gu.toString());
    
    }
    
}
