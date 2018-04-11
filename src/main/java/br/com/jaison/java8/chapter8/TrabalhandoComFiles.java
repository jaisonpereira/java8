package br.com.jaison.java8.chapter8;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 
 * @author jpereira
 *
 *         trabalhando com arquivos usando stream java.nio.file
 */
public class TrabalhandoComFiles {

	public static final String CURRENT_PATH = "./src/main/java/br/com/jaison/java8/chapter8/";

	/**
	 * Listando arquivos do diretorio atual
	 */
	private static void listandoArquivos() {
		try {
			/**
			 * Listando arquivos do diretorio atual
			 */
			Files.list(Paths.get("./")).forEach(System.out::println);
			/**
			 * listando apenas com uma extensao especifica
			 */
			System.out.println("#####  listando apenas arquivos com extensao .java ####");
			/**
			 * necessario chamar toString
			 */
			Files.list(Paths.get(CURRENT_PATH)).filter(p -> p.toString().endsWith(".java"))
					.forEach(System.out::println);

			/**
			 * Aqui ele devolveria um stream dentro de outro stream Stream<Stream<String>>
			 * 
			 * Podemos achatar um Stream de Streams com o flatMap. Basta trocar a invo-
			 * cação, que teremos no final um Stream<String>:
			 * 
			 * Para cada String podemos invo- car String.chars() e obter um IntStream
			 * (definiram assim para evitar o bo- xing para Stream<Character>). Se fizermos
			 * map(s -> s.chars()), obtere- mos um indesejado Stream<IntStream>. Precisamos
			 * passar esse lambda para o flatMaptoInt
			 * 
			 * O IntStream resultante possui todos os caracteres de todos os arquivos java
			 * do nosso diretório.
			 * 
			 * 
			 */
			IntStream chars = Files.list(Paths.get(CURRENT_PATH)).filter(p -> p.toString().endsWith(".java"))
					.flatMap(p -> lines(p)).flatMapToInt((String s) -> s.chars());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * problema é que Files.lines lança IOException. Mesmo que o método que invoca o
	 * map lance essa exception, não compilará, pois nesse caso é a implementação do
	 * lambda que estará lançando IOException. O map recebe uma Function, que tem o
	 * método apply e que não lança exception alguma na assinatura.
	 * 
	 * @param p
	 * @return
	 */
	public static Stream<String> lines(Path p) {
		try {
			return Files.lines(p);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Podemos fazer um forEach e popular um Map<Path, Long>, no qual a chave é o
	 * arquivo e o valor é a quantidade de linhas daquele arquivo:
	 * 
	 */
	private static void descrobrindoQuantidadeLinhasPorArquivo() {
		try {
			Map<Path, Long> linesPerFile = new HashMap<>();
			Files.list(Paths.get(CURRENT_PATH)).filter(p -> p.toString().endsWith(".java"))
					.forEach(p -> linesPerFile.put(p, lines(p).count()));
			System.out.println(linesPerFile);

			Map<Path, Long> lines = Files.list(Paths.get("./br/com/casadocodigo/java8"))
					.filter(p -> p.toString().endsWith(".java"))
					.collect(Collectors.toMap(p -> p, p -> lines(p).count()));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		listandoArquivos();
	}

}
