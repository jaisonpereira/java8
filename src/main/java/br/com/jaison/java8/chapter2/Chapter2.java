package br.com.jaison.java8.chapter2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Chapter2 {

	private static void newFormIterateObject(List<Usuario> usuarios) {
		ShowableUser show = new ShowableUser();
		usuarios.forEach(show);
		/**
		 * Aqui conseguimos implementar o acept no momento da chamada da função
		 */
		usuarios.forEach(new Consumer<Usuario>() {
			@Override
			public void accept(Usuario u) {
				System.out.println("implements function locale: " + u.getNome());
			}
		});

		/**
		 * -- LAMBDA -- Instanciando usando Lambda o unico metodo obrigatorio
		 * 'Accept' é inferido durante o processo de implementacao essa
		 * implementação e utilizada para interfaces de um unico metodo sem uso
		 * de cast explicito, e como é uma unica instrução omitimos {} as chaves
		 */
		Consumer<Usuario> showAble = u -> System.out.println("Lambda like this: " + u.getNome());

	}

	private static void oldFormIterateObject(List<Usuario> usuarios) {
		// for each em qualquer tipo de objeto que implemente a interface
		// java.lang.Iterable
		// Java 5 Since
		for (Usuario u : usuarios) {
			System.out.println("Old form :" + u.getNome());
		}
	}

	public static void main(String... args) {
		Usuario user1 = new Usuario("Jaison Pereira", 150);
		Usuario user2 = new Usuario("James Gosling", 120);
		Usuario user3 = new Usuario("Roberto Oliveira", 190);
		List<Usuario> usuarios = Arrays.asList(user1, user2, user3);
		oldFormIterateObject(usuarios);
		newFormIterateObject(usuarios);

	}

}
