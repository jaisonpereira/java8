package br.com.jaison.java8.chapter7;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import br.com.jaison.java8.chapter2.Usuario;
import br.com.jaison.util.UtilDados;

/**
 * @author Jpereira Stream -> Imutaveis realizam filters em uma collection e
 *         esta presente em uma collection como default methods
 *
 *         Collector - nada mais é que alguém que tem um supplier, um ac-
 *         cumulator e um combiner. A vantagem é que existem vários Collectors
 *         prontos. Podemos simplificar bastante nosso código
 *
 *         map -
 */
public class Chapter7Stream {

	private static void exibirUsuariosComMaisDe30Pontos() {
		System.out.println("Exibindo os usuarios com mais de 30 pontos");
		List<Usuario> usuarios = UtilDados.getUsuarios();
		Stream<Usuario> stream = usuarios.stream().filter(u -> u.getPontos() > 30);
		stream.forEach(System.out::println);

		/**
		 * Aplicando o uso de collectors colletando para uma list
		 *
		 * podemos coletar em um set usando Collectors.toSet()
		 */
		List<Usuario> maisQue100 = usuarios.stream().filter(u -> u.getPontos() > 100).collect(Collectors.toList());
		maisQue100.forEach(System.out::println);

	}

	private static void exibirUsuariosModeradores() {
		System.out.println("Exibindo os usuarios moderadores");
		// apenas usuarios moderadores
		UtilDados.getUsuarios().stream().filter(Usuario::isModerador).forEach(System.out::println);

	}

	/**
	 * Extraindo pontos dos usuarios
	 */
	private static void extraindoPontosDosUsuarios() {
		/**
		 * Usando variavel intermediaria e podemos alterar e criar acoplamendo
		 * de referencia dessa forma a variavel precisa ser final por ser um
		 * lambda
		 */
		// final List<Integer> pontos = new ArrayList<>();
		// UtilDados.getUsuarios().forEach(u -> pontos.add(u.getPontos()));
		/**
		 * Utilizando map
		 *
		 */
		List<Integer> pontos = UtilDados.getUsuarios().stream().map(Usuario::getPontos).collect(Collectors.toList());
		pontos.forEach(p -> System.out.println("Point: " + p));
		/**
		 * para evitarmos o autoboxing e um overhead iremos trabalhar com tipos
		 * inteiros
		 *
		 * podemos retornar um array de tipos primitivos chamado toArray;
		 */
		IntStream stream = UtilDados.getUsuarios().stream().mapToInt(Usuario::getPontos);

		/**
		 * usando metodos max, sorted e average
		 *
		 * usamos o orElse para definir um resultado que evita um infinito
		 * positivo Lancando uma exception caso nao exista valor
		 */
		double pontuacaoMedia = UtilDados.getUsuarios().stream().mapToInt(Usuario::getPontos).average().orElseThrow(IllegalStateException::new);

		pontuacaoMedia = UtilDados.getUsuarios().stream().mapToInt(Usuario::getPontos).average().orElse(0.0);

		System.out.println("Pontuacao media " + pontuacaoMedia);

	}

	private static void testeExibindoOsUsuariosTop10() {
		List<Usuario> usuarios = UtilDados.getUsuarios();
		// revertendo a lista para exibir a pontuacao em ordem decrescente
		usuarios.sort(Comparator.nullsLast(Comparator.comparing(Usuario::getPontos).reversed()));
		System.out.println("Exibindo top 10 pontuacao usuarios");
		usuarios.subList(0, 10).forEach(Usuario::showNome);
	}

	public static void main(String[] args) {
		testeExibindoOsUsuariosTop10();
		exibirUsuariosComMaisDe30Pontos();
		exibirUsuariosModeradores();
		extraindoPontosDosUsuarios();

	}

}
