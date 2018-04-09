package br.com.jaison.java8.chapter6;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Supplier;
import java.util.function.ToIntBiFunction;

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
		 * utilizamos method reference 1 - class 2 - method name perceba que estamos
		 * usando referencia de classe no primeiro argumento
		 */
		usuarios.forEach(Usuario::showNome);
		/**
		 * Method reference em comparator
		 */
		usuarios.sort(Comparator.comparing(Usuario::getNome));

		/**
		 * Utilizando thenComparing com evite boxing primitive aqui estamos usando
		 * composição usando nullsLast conseguimos colocar os valores nulos por ultimo
		 *
		 * - sonar orienta a seguinte regra para o ultimo lambda
		 *
		 * Method/constructor references are more compact and readable than using
		 * lambdas, and are therefore preferred. Similarly, null checks can be replaced
		 * with references to the Objects::isNull and Objects::nonNull methods.
		 *
		 *
		 */
		usuarios.sort(
				Comparator.nullsLast(Comparator.comparingInt(Usuario::getPontos).thenComparing(u -> u.getNome())));

		/**
		 * E se desejar ordenar por pontos, porém na ordem decrescente? Utilizamos o
		 * método default reversed() no Comparator :
		 */
		usuarios.sort(Comparator.nullsLast(Comparator.comparing(Usuario::getPontos).reversed()));

		/**
		 * atribuindo method reference
		 */
		Consumer<Usuario> exibe = Usuario::showNome;
		usuarios.forEach(exibe);
		/**
		 * Printa toString do objeto
		 */
		usuarios.forEach(System.out::println);

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

	private static void testeConstructorReference() {
		/*
		 * Construindo usuarios com supplier construtor sem argumentos
		 */
		Supplier<Usuario> criadorDeUsuarios = Usuario::new;
		Usuario novo = criadorDeUsuarios.get();

		/*
		 * Caso seja necessario argumentos no contrutor poderemos utilizar Function
		 */

		Function<String, Usuario> criaUsuarioComParametro = Usuario::new;
		Usuario jaison = criaUsuarioComParametro.apply("Jaison Pereira");
		/**
		 * Criando usuarios com dois parametros utilizando BiFunction
		 */
		BiFunction<String, Integer, Usuario> criadorDeUsuarioComDoisParametros = Usuario::new;
		Usuario jaisonComPonto = criadorDeUsuarioComDoisParametros.apply("Jaison Pereira with points", 50);

	}

	private static void testeBinaryOperation() {
		/**
		 * utilizando auto boxing de int para Integer alocação desnecessaria de memoria
		 */
		BiFunction<Integer, Integer, Integer> max = Math::max;
		/**
		 * Evita auto boxing
		 */
		ToIntBiFunction<Integer, Integer> max2 = Math::max;
		/**
		 * Evitamos todos os auto boxings recebendo ate o argumento primitivo
		 */
		IntBinaryOperator max3 = Math::max;

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
		testeConstructorReference();
	}

}
