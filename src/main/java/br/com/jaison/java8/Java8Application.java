package br.com.jaison.java8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jaison Pereira 29 de mar de 2018 Classe starta aplicação
 * 
 * 
 *         Stream - findAny,findFirst ,forEach sao operacoes terminais que
 *         executam o pipeline
 * 
 *         sorted é um método intermediário stateful. Operações stateful podem
 *         precisar processar todo o stream mesmo que sua operação terminal não
 *         de- mande isso.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
@SpringBootApplication
public class Java8Application {

	public static void main(String[] args) {
		SpringApplication.run(Java8Application.class, args);
	}
}
