package br.com.jaison.java8.chapter8;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import br.com.jaison.java8.chapter2.Usuario;
import br.com.jaison.util.UtilDados;

/**
 * @author jpereira Stream
 * 
 *         ordenando com stream
 * 
 * 
 *         Pipeline - lazy * Stream - findAny,findFirst ,forEach sao operacoes
 *         terminais que executam o pipeline
 * 
 *         sorted é um método intermediário stateful. Operações stateful podem
 *         precisar processar todo o stream mesmo que sua operação terminal não
 *         de- mande isso.
 * 
 * 
 * 
 */
public class Chapter8Stream {

	private static void getfirstElement() {
		/**
		 * Má pratica estoura uma exeception pois nao existe usuario com mais de 100
		 * pontos
		 */
		System.out.println(UtilDados.getUsuarios().stream().filter(u -> u.getPontos() > 100)
				.sorted(Comparator.comparing(Usuario::getNome)).collect(Collectors.toList()).get(0));

	}

	private static void ordenandoColetandoUsandoStream() {
		List<Usuario> filtradosOrdenados = UtilDados.getUsuarios().stream().filter(u -> u.getPontos() > 100)
				.sorted(Comparator.comparing(Usuario::getNome)).collect(Collectors.toList());

		/**
		 * Pipeline -Lazy
		 * 
		 * as operacoes sao intermediarias e sao passadas para as operacoes terminais
		 * 
		 * imagine filtrarmos os usuarios com mais de 100 pontos para so depois pegarmos
		 * um user com mais de 100 pontos para evitar esse tipo de processamento
		 * desnecessario usamos findAny ou findFirst para retornar o primeiro encontrado
		 * 
		 * 
		 * como todo o trabalho foi lazy, o stream não foi inteiramente filtrado
		 */
		Optional<Usuario> usuarioOptional = UtilDados.getUsuarios().stream().filter(u -> u.getPontos() > 100).findAny();

	}

	private static void testePeek() {
		// UtilDados.getUsuarios().forEach(System.out::println);

		/**
		 * Aqui ele imprimi todos os usuarios mesmo que chamando uma operacao terminal
		 * pois sorted é stateful e precisa processar o stream para ordenar
		 */

		UtilDados.getUsuarios().stream().sorted(Comparator.comparingInt(Usuario::getPontos)).peek(System.out::println)
				.findAny();

		// Podemos pedir para que o stream execute um tarefa toda vez que processar um
		// ele-
		// mento. Fazemos isso através do peek:
		UtilDados.getUsuarios().stream().filter(u -> u.getPontos() > 25).peek(System.out::println).findFirst();

	}

	/**
	 * Com reduce é possivel reduzir operacoes
	 * 
	 * podemos passar uma operacao e o valor inicial dela
	 * 
	 * no caso abaixo estamos criando um metodo equivalente a um 'sum'
	 * 
	 * 
	 * 
	 * 
	 * Qual é a vantagem de usarmos a redução em vez do sum? Nenhuma. O impor- tante
	 * é conhecê-lo para poder realizar operações que não se encontram no Stream.
	 * Por exemplo? Multiplicar todos os pontos:
	 * 
	 * 
	 * 
	 */
	private static void testeMapReduce() {
		int valorInicial = 0;
		IntBinaryOperator operacao = (a, b) -> a + b;
		int total = UtilDados.getUsuarios().stream().mapToInt(Usuario::getPontos).reduce(valorInicial, operacao);

		/***
		 * ou de uma maneira mais sucinta
		 */
		int total2 = UtilDados.getUsuarios().stream().mapToInt(Usuario::getPontos).reduce(0, (a, b) -> a + b);

		/**
		 * Podemos ir além. Na classe Integer, há agora o método estático Integer.sum,
		 * que soma dois inteiros. Em vez do lambda, podemos usar um method reference:
		 */
		int total3 = UtilDados.getUsuarios().stream().mapToInt(Usuario::getPontos).reduce(0, Integer::sum);

		/**
		 * Multiplicando todos os pontos
		 */
		int multiplicacao = UtilDados.getUsuarios().stream().mapToInt(Usuario::getPontos).reduce(1, (a, b) -> a * b);
		/**
		 * Há também alguns casos especiais em que invocar o map pode ser custoso, e o
		 * melhor seria fazer a operação de soma diretamente. Esse não é o nosso caso,
		 * mas só para enxergarmos o exemplo, a soma sem o map ficaria assim:
		 * 
		 * 
		 * Esse overload do reduce recebe mais um lambda que, no nosso caso, é o
		 * Integer::sum. Esse lambda a mais serve para combinar os valores de reduções
		 * parciais, no caso de streams paralelos.
		 * 
		 * Não há necessidade de querer sempre evitar o número de operações em um
		 * stream. São casos isolados.
		 * 
		 */
		int total4 = UtilDados.getUsuarios().stream().reduce(0, (atual, u) -> atual + u.getPontos(), Integer::sum);

	}

	private static void testandoPredicated() {
		/**
		 * Há outras situações em que queremos testar predicados mas não precisamos da
		 * lista filtrada.
		 * 
		 * Assim como o anyMatch, podemos descobrir se todos os usuários são mode-
		 * radores com allMatch ou se nenhum deles é, com o noneMatch.
		 * 
		 * Você pode utilizar o count para saber quantos elementos há no Stream, skip
		 * para pular os n próximos elementos e limit para cortar o número de elementos.
		 * 
		 * 
		 */
		boolean hasModerator = UtilDados.getUsuarios().stream().anyMatch(Usuario::isModerador);

	}

	private static void testeCurtoCircuito() {
		/**
		 * Gerando numeros infinitos Agora precisamos de cuidado. Qualquer operação que
		 * necessite passar por todos os elementos do Stream nunca terminará de
		 * executar. Por exemplo:
		 */
		Random random = new Random(0);
		IntStream stream = IntStream.generate(() -> random.nextInt());
		int valor = stream.sum();

		/**
		 * Você pode apenas utilizar operações de curto-circuito em Streams infinitos.
		 * Operações de curto circuito São operações que não precisam processar todos os
		 * elementos. Um exemplo seria pegar apenas os 100 primeiros elementos com
		 * limit:
		 */
		Random random2 = new Random(0);
		IntStream stream2 = IntStream.generate(() -> random.nextInt());
		/**
		 * Operacao de curto circuito Repare a invocação de boxed. Ele retorna um
		 * Stream<Integer> em vez do IntStream, possibilitando a invocação a collect da
		 * forma que já vimos. Sem isso, teríamos apenas a opção de fazer
		 * IntStream.toArray, ou então de chamar o collect que recebe três argumentos,
		 * mas não teríamos onde guardar os números.
		 */
		List<Integer> list = stream.limit(100).boxed().collect(Collectors.toList());

	}

	public static void main(String[] args) {
		testePeek();
		// ordenandoColetandoUsandoStream();
	}

}
