package Laberinto;

import datastruct.list.LinkedList;

public class PriorityQueue<E extends Comparable<E>> {
    private LinkedList<E> list;

    public PriorityQueue() {
        this.list = new LinkedList<>();
    }

    public void enqueue(E element) {
        if (list.isEmpty()) {
            list.add(element);
            return;
        }

        // Insertar en orden (cola de prioridad ascendente)
        boolean inserted = false;
        for (int i = 0; i < list.getLength(); i++) {
            try {
                E current = list.get(i);
                if (element.compareTo(current) <= 0) {
                    list.add(element, i);
                    inserted = true;
                    break;
                }
            } catch (Exception e) {
                // Si hay error, insertar al final
                break;
            }
        }
        
        if (!inserted) {
            list.add(element);
        }
    }

    public E dequeue() throws Exception {
        if (isEmpty()) {
            throw new Exception("Priority queue is empty");
        }
        return list.deleteFirst();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public Integer size() {
        return list.getLength();
    }

    public boolean contains(E element) {
        return list.contains(element);
    }

    public void updatePriority(E oldElement, E newElement) {
        try {
            // Buscar y eliminar el elemento viejo
            for (int i = 0; i < list.getLength(); i++) {
                if (list.get(i).equals(oldElement)) {
                    list.delete(i);
                    break;
                }
            }
            // Insertar el nuevo elemento en su posición correcta
            enqueue(newElement);
        } catch (Exception e) {
            // Si no se encuentra, simplemente agregar el nuevo
            enqueue(newElement);
        }
    }
}
