package br.com.jaison.java8.chapter6;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import br.com.jaison.java8.chapter2.Usuario;

/**
 * @author Jaison Pereira 6 de abr de 2018 Method References
 */
public class Chapter6 {

	private static void testeMethodReference() {
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(new Usuario("jaison", 100));
		usuarios.add(new Usuario("Carlos", 45));
		usuarios.add(new Usuario("Roberto", 30));
		/**
		 * utilizamos method reference 1 - class 2 - method name perceba que
		 * estamos usando referencia de classe no primeiro argumento
		 */
		usuarios.forEach(Usuario::showNome);
		/**
		 * Method reference em comparator
		 */
		usuarios.sort(Comparator.comparing(Usuario::getNome));

		/**
		 * Utilizando thenComparing com evite boxing primitive aqui estamos
		 * usando composição usando nullsLast conseguimos colocar os valores
		 * nulos por ultimo
		 *
		 * - sonar orienta a seguinte regra para o ultimo lambda
		 *
		 * Method/constructor references are more compact and readable than
		 * using lambdas, and are therefore preferred. Similarly, null checks
		 * can be replaced with references to the Objects::isNull and
		 * Objects::nonNull methods.
		 *
		 *
		 */
		usuarios.sort(Comparator.nullsLast(Comparator.comparingInt(Usuario::getPontos).thenComparing(u -> u.getNome())));

		/**
		 * E se desejar ordenar por pontos, porém na ordem decrescente?
		 * Utilizamos o método default reversed() no Comparator :
		 */
		usuarios.sort(Comparator.nullsLast(Comparator.comparing(Usuario::getPontos).reversed()));

		/**
		 * atribuindo method reference
		 */
		Consumer<Usuario> tornaModerador = Usuario::showNome;
		usuarios.forEach(tornaModerador);

	}

	private static void testeReferenciaMetodosInstancia() {
		Usuario method = new Usuario("METHOD de instancia ", 50);
		Usuario lambdaMethod = new Usuario("METHOD de instancia lambda ", 100);
		// atribuimos o metodo para uma variavel
		Runnable bloco = method::showNome;
		Runnable bloco2 = () -> lambdaMethod.showNome();
		executaMetodoRecebido(bloco);
		executaMetodoRecebido(bloco2);

	}

	/**
	 * Recebe metodo por parametro e o executa
	 *
	 * @param metodo
	 */
	public static void executaMetodoRecebido(Runnable metodo) {
		metodo.run();
	}

	public static void main(String[] args) {
		testeMethodReference();
		testeReferenciaMetodosInstancia();
	}

}
