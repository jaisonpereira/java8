package br.com.jaison.java8.chapter5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.jaison.java8.chapter2.Usuario;

public class Chapter5 {

	private static void comparingUsingInt() {

		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(new Usuario("jaison", 100));
		usuarios.add(new Usuario("Carlos", 45));
		usuarios.add(new Usuario("Roberto", 30));
		/**
		 * evitando autoBoxing e unboxing de int para Integer usamos metodos
		 * especificos que tem relacao com as variaveis primitivas
		 */
		usuarios.sort(Comparator.comparingInt(u -> u.getPontos()));

		// apenas o usuario jaison sera removido
		usuarios.forEach(u -> System.out.println(u.getNome() + " point " + u.getPontos()));

	}

	/**
	 * Criando comparator personalizado
	 */
	private static void testeComparator() {
		/**
		 * Criamos um comparator usando o comparator da classe string porem nao
		 * estamos definindo os casos dos nomes que vierem nulos se iriam para o
		 * final ou para o começo aqui nao estamos usando os case sensitive
		 */
		Comparator<Usuario> comparator = (u1, u2) -> String.CASE_INSENSITIVE_ORDER.compare(u1.getNome(), u2.getNome());

		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(new Usuario("Jaison", 100));
		usuarios.add(new Usuario("Carlos", 45));
		usuarios.add(new Usuario("Roberto", 30));
		// passando como argumento comprator e criterio de comparao
		Collections.sort(usuarios, comparator);
		// inclusao em uma unica instrução
		Collections.sort(usuarios, (u1, u2) -> u1.getNome().compareTo(u2.getNome()));
		/**
		 * Esse é um excelente exemplo de uso de métodos default, pois permitiu
		 * a evolução da interface List sem quebrar o código existente
		 */
		usuarios.sort((u1, u2) -> u1.getNome().compareTo(u2.getNome()));
		/**
		 * O uso do comparing só funciona passando um lambda que, dado um tipo T
		 * (no nosso caso um usuário), devolve algo que seja Comparable<U> . No
		 * nosso exemplo, devolvemos uma String , o nome do usuário, que é
		 * Comparable<String>
		 */
		usuarios.sort(Comparator.comparing(u -> u.getNome()));

		// exibindo
		usuarios.forEach(u -> System.out.println(u.getNome()));

	}

	private static void testeOrdemNatural() {
		System.out.println("\n");
		System.out.println("Natural order");
		List<String> palavras = Arrays.asList("Zebra", "Código", "Jaison", "Regina");
		palavras.sort(Comparator.naturalOrder());
		palavras.forEach(p -> System.out.println(p));
	}

	public static void main(String[] args) {
		testeComparator();
		testeOrdemNatural();
		comparingUsingInt();
	}

}
