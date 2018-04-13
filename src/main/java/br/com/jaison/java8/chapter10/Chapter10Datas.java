package br.com.jaison.java8.chapter10;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Jaison Pereira 11 de abr de 2018
 *
 *         Chega de Calendar! Nova API de datas
 */

public class Chapter10Datas {

	private static void testeWithData() {

		/**
		 * Aplicar uma transformação em um Calendar é um processo muito verboso, como
		 * por exemplo para criar uma data com um mês a partir da data atual. Repare:
		 */

		Calendar mesQueVem = Calendar.getInstance();
		mesQueVem.add(Calendar.MONTH, 1);
		/**
		 * Com a nova API de datas podemos fazer essa mesma operação de uma forma mais
		 * moderna, utilizando sua interface fluente:
		 */
		LocalDate mesQueVem2 = LocalDate.now().plusMonths(1);

		/**
		 * Para converter esses objetos para outras medidas de tempo podemos utili- zar
		 * os métodos to, como é o caso do toLocalDateTime presente na classe
		 * ZonedDateTime:
		 * 
		 */
		LocalTime agora = LocalTime.now();
		LocalDate hoje = LocalDate.now();
		LocalDateTime dataEhora = hoje.atTime(agora);

		ZonedDateTime dataComHoraETimezone = dataEhora.atZone(ZoneId.of("America/Sao_Paulo"));

		LocalDateTime semTimeZone = dataComHoraETimezone.toLocalDateTime();

		/**
		 * Construindo
		 */

		LocalDate date = LocalDate.of(2014, 12, 25);
		LocalDateTime dateTime = LocalDateTime.of(2014, 12, 25, 10, 30);

		/**
		 * Existem também outros comportamentos essencias, como saber se alguma me- dida
		 * de tempo acontece antes, depois ou ao mesmo tempo que outra. Para esses
		 * casos, utilizamos os métodos is:
		 * 
		 */

		LocalDate hoje2 = LocalDate.now();
		LocalDate amanha = LocalDate.now().plusDays(1);
		System.out.println(hoje.isBefore(amanha));
		System.out.println(hoje.isAfter(amanha));
		System.out.println(hoje.isEqual(amanha));

		/**
		 * Para obter o dia do mês atual, por exemplo, poderíamos utilizar o método
		 * getDayOfMonth de uma instância da classe MonthDay. Repare:
		 * 
		 */
		System.out.println("hoje é dia: " + MonthDay.now().getDayOfMonth());

		/**
		 * Para imprimir o nome de um mês formatado, podemos utilizar o método
		 * getDisplayName fornecendo o estilo de formatação (completo, resumido, entre
		 * outros) e também o Locale:
		 * 
		 * Outro enum introduzido no java.time foi o DayOfWeek. Com ele, pode- mos
		 * representar facilmente um dia da semana, sem utilizar constantes ou números
		 * mágicos!
		 * 
		 * 
		 */
		Locale pt = new Locale("pt");
		System.out.println(Month.DECEMBER.getDisplayName(TextStyle.FULL, pt));
		System.out.println(Month.DECEMBER.getDisplayName(TextStyle.SHORT, pt));

		/**
		 * criando atraves de um pattern
		 */
		LocalDateTime agora3 = LocalDateTime.now();
		agora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		/**
		 * Um exemplo de resultado seria 01:15:45, ou seja, o pattern é hh:mm:ss. Note
		 * que usamos um DateTimeFormatter predefinido, o ISO_LOCAL_TIME. Assim como ele
		 * existem diversos outros que você pode ver no javadoc do DateTimeFormatter.
		 * 
		 */
		LocalDateTime agora22 = LocalDateTime.now();
		String resultado = agora.format(DateTimeFormatter.ISO_LOCAL_TIME);

	}

	private static void diferencaEntreDatas() {
		/**
		 * 
		 * A classe Period tem uma série de métodos que auxiliam nas diversas situações
		 * que enfrentamos ao trabalhar com datas. Por exemplo, ao calcular uma
		 * diferença entre datas, é comum a necessidade de lidarmos com valores
		 * negativos. Observe o que acontece se alterarmos o ano da outraData para 2015:
		 */

		LocalDate agora = LocalDate.now();
		LocalDate outraData = LocalDate.of(2015, Month.JANUARY, 25);
		Period periodo = Period.between(outraData, agora);
		System.out.printf("%s dias, %s meses e %s anos", periodo.getDays(), periodo.getMonths(), periodo.getYears());
		/**
		 * 
		 * A saída será algo como: -15 dias, -11 meses e 0 anos. Essa pode ser a saída
		 * esperada, mas caso não seja, podemos facilmente per- guntar ao Period se ele
		 * é um período de valores negativos invocando o método isNegative. Caso seja,
		 * poderíamos negar seus valores com o método negated, repare:
		 * 
		 * Também podemos criar um período com apenas dias, meses ou anos utilizando os
		 * métodos: ofDays, ofMonths ou ofYears.
		 * 
		 */
		Period periodo2 = Period.between(outraData, agora);
		if (periodo2.isNegative()) {
			periodo2 = periodo2.negated();
		}
		System.out.printf("%s dias, %s meses e %s anos", periodo2.getDays(), periodo2.getMonths(), periodo2.getYears());
		// Agora a saída terá seus valores invertidos: 15 dias, 11 meses e 0 anos

		/**
		 * 
		 * Enquanto um Period considera as medidas de data (dias, meses e anos), a
		 * Duration considera as medidas de tempo (horas, minutos, segundos etc.). Sua
		 * API é muito parecida, observe:
		 */

		LocalDateTime nowJ = LocalDateTime.now();
		LocalDateTime daquiAUmaHora = LocalDateTime.now().plusHours(1);
		Duration duration = Duration.between(nowJ, daquiAUmaHora);
		if (duration.isNegative()) {
			duration = duration.negated();
		}
		System.out.printf("%s horas, %s minutos e %s segundos", duration.toHours(), duration.toMinutes(),
				duration.getSeconds());
		/**
		 * Repare que, como agora estamos trabalhando com tempo, utilizamos a classe
		 * LocalDateTime.
		 * 
		 */

	}

	public static void main(String[] args) {
		testeWithData();
		diferencaEntreDatas();
	}

}
