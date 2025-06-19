package types;

import java.util.Iterator;

/**
 * Interface para uma pilha mutável: uma colecção de objectos que são inseridos
 * e removidos de acordo com o princípio de que o último a entrar é o primeiro a
 * sair.
 * 
 * @author Algoritmos e Estruturas de Dados 2024-25, Faculdade de Ciências,
 *         Universidade de Lisboa
 *
 * @param <E> O tipo dos elementos da pilha
 */
public interface Stack<E> {

    /**
     * Inserir um elemento no topo da pilha
     * 
     * @param item O elemento a inserir
     */
    void push(E e);

    /**
     * O elemento no topo da pilha
     * 
     * @return O elemento no topo da pilha
     * @requires A pilha não está vazia
     */
    E peek();

    /**
     * Retirar o elemento no topo da pilha
     * 
     * @requires A pilha não está vazia
     */
    void pop();

    /**
     * Esta pilha está vazia?
     * 
     * @return true se a pilha está vazia, false caso contrário
     */
    boolean isEmpty();

    /**
     * Retirar todos os elementos de uma pilha
     */
    default void popAll() {
        while (!isEmpty()) {
            pop();
        }
    }

    Iterator<E> iterator();

}