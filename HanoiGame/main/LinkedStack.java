package types;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Uma implementação da pilha utilizando uma lista simplemente ligada.
 * 
 * @author Algoritmos e Estruturas de Dados 2023-24, Faculdade de Ciências,
 *         Universidade de Lisboa
 *
 * @param <E> O tipo dos elementos da pilha
 */
public class LinkedStack<E> implements Stack<E> {

    /**
     * Os nós desta pilha
     */
    private class Node {
        E item;
        Node next;
    }

    /**
     * Topo da pilha O nó que foi mais recentemente adicionado
     */
    private Node first = null;

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public void push(E item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    @Override
    public E peek() {
        return first.item;
    }

    @Override
    public void pop() {
        first = first.next;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<E> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E result = current.item;
            current = current.next;
            return result;
        }
    }
}
