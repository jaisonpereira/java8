package br.com.jaison.java8.chapter9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import br.com.jaison.java8.chapter2.Usuario;

/**
 * @author Jaison Pereira 11 de abr de 2018 Mapeando, particionando, agrupando e
 *         paralelizando
 *
 */

public class Chapter9Paralelizando {

	private static List<Usuario> getUsers() {
		Usuario user1 = new Usuario("Jaison Pereira", 150, true);
		Usuario user2 = new Usuario("Ronaldo Jogador", 120, true);
		Usuario user3 = new Usuario("Bianca Santos", 90);
		Usuario user4 = new Usuario("Stephany Drissineti", 120);
		Usuario user5 = new Usuario("Roberto Carlos", 100);
		return Arrays.asList(user1, user2, user3, user4, user5);
	}

	/**
	 * Queremos um mapa em que a chave seja a pontuação do usuário e o valor
	 * seja uma lista de usuários que possuem aquela pontuação. Isto é, um
	 * Map<Integer, List<Usuario>>.
	 */
	private static void groupByPartitioningBy() {

		/**
		 * Old form
		 */
		Map<Integer, List<Usuario>> pontuacao = new HashMap<>();
		for (Usuario u : getUsers()) {
			if (!pontuacao.containsKey(u.getPontos())) {
				pontuacao.put(u.getPontos(), new ArrayList<>());
			}
			pontuacao.get(u.getPontos()).add(u);
		}

		System.out.println(pontuacao);

		/**
		 * O método computeIfAbsent vai chamar a Function do lambda no caso de
		 * não encontrar um valor para a chave u.getPontos() e associar o
		 * resultado (a nova ArrayList) a essa mesma chave. Isto é, essa
		 * invocação do computeIfAbsent faz o papel do if que fizemos no código
		 * anterior.
		 *
		 */
		Map<Integer, List<Usuario>> pontuacao2 = new HashMap<>();
		for (Usuario u : getUsers()) {
			pontuacao2.computeIfAbsent(u.getPontos(), user -> new ArrayList<>()).add(u);
		}
		System.out.println(pontuacao2);

		/**
		 * trabalhando com stream
		 *
		 * A saída é a mesma! O segredo é o Collectors.groupingBy, que é uma
		 * factory de Collectors que fazem agrupamentos.
		 *
		 */
		Map<Integer, List<Usuario>> pontuacao3 = getUsers().stream().collect(Collectors.groupingBy(Usuario::getPontos));

		/**
		 * Podemos fazer mais. Podemos particionar todos os usuários entre
		 * moderadores e não moderadores, usando o partitionBy:
		 *
		 */
		Map<Boolean, List<Usuario>> moderadores = getUsers().stream().collect(Collectors.partitioningBy(Usuario::isModerador));
		System.out.println(moderadores);

		/**
		 *
		 * Em vez de guardar os objetos dos usuários, poderíamos guardar uma
		 * lista com apenas o nome de cada usuário, usando o mapping para
		 * coletar esses nomes em uma lista:
		 */
		Map<Boolean, List<String>> nomesPorTipo = getUsers().stream()
				.collect(Collectors.partitioningBy(Usuario::isModerador, Collectors.mapping(Usuario::getNome, Collectors.toList())));

		/**
		 *
		 * Vamos a mais um desafio. Queremos particionar por moderação, mas ter
		 * como valor não os usuários, mas sim a soma de seus pontos. Também
		 * existe um coletor para realizar essas somatórias, que pode ser usado
		 * em conjunto com o partitioningBy e groupingBy:
		 */
		Map<Boolean, Integer> pontuacaoPorTipo = getUsers().stream()
				.collect(Collectors.partitioningBy(Usuario::isModerador, Collectors.summingInt(Usuario::getPontos)));
		System.out.println(pontuacaoPorTipo);

		/**
		 * Conhecer bem toda a factory Collectors certamente vai ajudar suas
		 * manipu- lações de coleções. Perceba que não usamos mais loops para
		 * processar os elementos. Até mesmo para concatenar todos os nomes dos
		 * usuários há um coletor:
		 *
		 */
		String nomes = getUsers().stream().map(Usuario::getNome).collect(Collectors.joining(", "));

	}

	/**
	 * As collections oferecem uma implementação de Stream diferente, o stream
	 * pa- ralelo. Ao usar um stream paralelo, ele vai decidir quantas threads
	 * deve utilizar, como deve quebrar o processamento dos dados e qual será a
	 * forma de unir o resultado fi- nal em um só. Tudo isso sem você ter de
	 * configurar nada. Basta apenas invocar parallelStream em vez de Stream:
	 *
	 */
	private static void testePipelineEmParalelo() {

		List<Usuario> filtradosOrdenados = getUsers().parallelStream().filter(u -> u.getPontos() > 25).sorted(Comparator.comparing(Usuario::getNome))
				.collect(Collectors.toList());

		/**
		 * com uma coleção pequena, não podemos enxergar as perdas e ganhos com
		 * fa- cilidade. Vamos gerar uma quantidade grande de números,
		 * filtrá-los e ordená-los, para poder ter uma base de comparação.
		 *
		 * Para gerar os números de 1 a um bilhão, utilizaremos o
		 * LongStream.range. Usaremos o parallel e o filter para filtrar:
		 *
		 *
		 * Em um computador com 2 cores, executamos o código em 1.276s de tempo
		 * realmente gasto. As configurações da máquina não importam muito, o
		 * que queremos é fazer a comparação.
		 *
		 *
		 * Removendo a invocação do parallel(), o tempo é significativamente
		 * maior: 1.842s. Não é o dobro, pois paralelizar a execução de um
		 * pipeline sempre tem seu preço. Essa diferença favorece o parallel
		 * pois temos um input grande de dados. Se diminuirmos a sequência
		 * gerada de 1 bilhão para 1 milhão, fica claro o problema. O paralelo
		 * roda em 0.239s e o sequencial em 0.201. Isso mesmo: a versão paralela
		 * é mais lenta por causa do overhead: quebrar o problema em várias
		 * partes, juntar os dados resultantes etc.
		 *
		 *
		 *
		 */
		Long sum = LongStream.range(0, 1_000_000_000).parallel().filter(x -> x % 2 == 0).sum();
		System.out.println(sum);

	}

}
