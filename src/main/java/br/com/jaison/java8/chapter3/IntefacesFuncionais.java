package br.com.jaison.java8.chapter3;

/**
 * Interfaces Funcionais
 *
 * @author Jaison Pereira 2 de abr de 2018
 *
 *         Interfaces Funcionais no novo Java8
 *
 *         uma interface funcional , é uma interface com apenas um unico metodo
 *         que podemos utilizar em nossas expressões lambdas
 *
 */
public class IntefacesFuncionais {

	private static void oldForm() {
		Validador<String> validaCEP = new Validador<String>() {
			@Override
			public boolean valida(String value) {
				return value.matches("[0-9]{5}-[0-9]{3}");
			}
		};
		System.out.println("Old Form retornara false pois nao contempla : " + validaCEP.valida("teste"));
		System.out.println("Old Form retornara true pois  contempla 04101-300 : " + validaCEP.valida("04101-300"));
	}

	/**
	 * Podemos acessar as variaveis finais de um metodo local
	 */
	public static void accessFinalVariable() {
		final Integer value = 5;
		new Thread(() -> System.out.println("final value " + value)).start();

		new Thread(() -> {
			for (int i = 0; i < value; i++) {
				System.out.println("final count  value " + value);
			}
		}).start();

	}

	public static void main(String[] args) {
		oldForm();
		newForm();
		accessFinalVariable();
	}

	public static void newForm() {
		/**
		 * Utilizando Lambda Java 8
		 */

		Validador<String> validaCEP = value -> value.matches("[0-9]{5}-[0-9]{3}");

		System.out.println("new Form retornara false pois nao contempla : " + validaCEP.valida("teste"));
		System.out.println("new Form retornara true pois  contempla 04101-300 : " + validaCEP.valida("04101-300"));
	}

}
