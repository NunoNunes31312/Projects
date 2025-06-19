package types;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação de uma pilha utilizando um array circular.
 * 
 * @author Algoritmos e Estruturas de Dados 2023-24, Faculdade de Ciências,
 *         Universidade de Lisboa
 *
 * @param <E> O tipo dos elementos da pilha
 */
public class ArrayStack<E> implements Stack<E> {

    /**
     * Array utilizado para guardar os elementos na pilha
     */
    private E[] data;

    /**
     * Número de elementos na pilha
     */
    private int size;

    /**
     * A capacidade da pilha por omissão
     */
    private static final int DEFAULT_CAPACITY = 8;

    /**
     * Criar uma pilha vazia
     */
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        data = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E peek() {
        return data[size - 1];
    }

    @Override
    public void push(E e) {
        if (size == data.length) {
            resize(size * 2);
        }
        data[size++] = e;
    }

    @Override
    public void pop() {
        data[--size] = null;
        if (size > 0 && size == data.length / 4) {
            resize(data.length / 2);
        }
    }

    /**
     * Redimensionar o array
     * 
     * @param capacity A nova capacidade do array
     */
    private void resize(int capacity) {
        data = Arrays.copyOf(data, capacity);
    }

    @Override
    public Iterator<E> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<E> {
        private int i = size - 1;

        public boolean hasNext() {
            return i >= 0;
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return data[i--];
        }
    }
}
