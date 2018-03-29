package br.com.jaison.java8.chapter2;

public class Usuario {

	private String nome;

	private int pontos;

	private boolean moderador;

	public Usuario(String nome, int pontos) {
		super();
		this.nome = nome;
		this.pontos = pontos;
	}

	public String getNome() {
		return this.nome;
	}

	public int getPontos() {
		return this.pontos;
	}

	public boolean isModerador() {
		return this.moderador;
	}

	public void setModerador(boolean moderador) {
		this.moderador = moderador;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

}
