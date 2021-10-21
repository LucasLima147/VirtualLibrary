package br.fai.vl.model;

public class Autor {
	private int id;
	private String nome;
	private String obra;
	private String frase;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public String getObra() {
		return obra;
	}

	public void setObra(final String obra) {
		this.obra = obra;
	}

	public String getFrase() {
		return frase;
	}

	public void setFrase(final String frase) {
		this.frase = frase;
	}
}
