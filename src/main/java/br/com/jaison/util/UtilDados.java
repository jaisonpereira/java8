package br.com.jaison.util;

import java.util.ArrayList;
import java.util.List;

import br.com.jaison.java8.chapter2.Usuario;

/**
 * @author jpereira Classe utilizada para gerar dados aleatorios
 */
public class UtilDados {

	public static List<Usuario> getUsuarios() {
		List<Usuario> usuarios = new ArrayList<>();
		for (int i = 0; i < 70; i++) {
			usuarios.add(new Usuario("Jaison " + 1, i));
		}
		return usuarios;
	}

	
	
	
}
