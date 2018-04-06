package br.com.jaison.java8.chapter4;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import br.com.jaison.java8.chapter2.Usuario;

/**
 *
 * @author Jaison Pereira 5 de abr de 2018 Default methods
 */
public class Chapter4 {

	public static void main(String[] args) {
		testeRemoveIf();
	}

	public static void testeRemoveIf() {

		/**
		 * Uso de nao lambda foi puramente academico para demonstrar a classe
		 * anonima Predicate utilizando generics
		 */
		Predicate<Usuario> predicadoCondicaoRemove = new Predicate<Usuario>() {
			@Override
			public boolean test(Usuario u) {
				return u.getPontos() > 50;
			}
		};

		Consumer<Usuario> mostraMensagem = u -> System.out.println("antes de imprimir os nomes");

		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(new Usuario("jaison", 100));
		usuarios.add(new Usuario("Carlos", 45));
		usuarios.add(new Usuario("Roberto", 30));
		// apenas o usuario jaison sera removido
		usuarios.removeIf(predicadoCondicaoRemove);
		usuarios.forEach(mostraMensagem.andThen(u -> System.out.println(u.getNome())));

	}

}
