package br.com.jaison.java8.chapter7;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import br.com.jaison.java8.chapter2.Usuario;
import br.com.jaison.util.UtilDados;

/**
 * @author Jpereira Stream -> Imutaveis realizam filters em uma collection e
 *         esta presente em uma collection como default methods
 */
public class Chapter7Stream {

	private static void testeExibindoOsUsuariosTop10() {
		List<Usuario> usuarios = UtilDados.getUsuarios();
		// revertendo a lista para exibir a pontuacao em ordem decrescente
		usuarios.sort(Comparator.nullsLast(Comparator.comparing(Usuario::getPontos).reversed()));
		System.out.println("Exibindo top 10 pontuacao usuarios");
		// exibindo os 10 primeiros resultados
		usuarios.subList(0, 10).forEach(Usuario::showNome);
	}

	private static void exibirUsuariosComMaisDe30Pontos() {
		System.out.println("Exibindo os usuarios com mais de 30 pontos");
		List<Usuario> usuarios = UtilDados.getUsuarios();
		Stream<Usuario> stream = usuarios.stream().filter(u -> u.getPontos() > 30);
		stream.forEach(System.out::println);
		// unica linha
		usuarios.stream().filter(u -> u.getPontos() > 100).forEach(System.out::println);

	}

	public static void main(String[] args) {

		testeExibindoOsUsuariosTop10();
		exibirUsuariosComMaisDe30Pontos();

	}

}
