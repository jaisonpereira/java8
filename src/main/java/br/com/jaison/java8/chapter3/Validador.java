package br.com.jaison.java8.chapter3;

/**
 * @author Jaison Pereira 2 de abr de 2018 Interface funcional a ser utilizada
 *         em uma expressao lambda , possue apenas um metodo
 *
 *         Colocamos a notation funcional interface explicitamente , para que o
 *         fato dela ser uma interface funcional nao seja uma coincidencia,
 *         impedindo criacoes de outros metodos
 */
@FunctionalInterface
public interface Validador<T> {

	boolean valida(T value);
}
