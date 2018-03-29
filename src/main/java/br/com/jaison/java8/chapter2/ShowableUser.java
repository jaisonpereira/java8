package br.com.jaison.java8.chapter2;

import java.util.function.Consumer;

/**
 * @author Jaison Pereira 29 de mar de 2018 Classe implementa java.util.function
 *         da nova api Java 8
 */
public class ShowableUser implements Consumer<Usuario> {

	@Override
	public void accept(Usuario t) {
		System.out.println("Show by Consumer Jdk8 :" + t.getNome());
	}

}
