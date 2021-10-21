package br.fai.vl.model;

public class Exemplar {
	private int id;
	private int edicao;
	private String estadoConservacao;
	private int livroId;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getEdicao() {
		return edicao;
	}

	public void setEdicao(final int edicao) {
		this.edicao = edicao;
	}

	public String getEstadoConservacao() {
		return estadoConservacao;
	}

	public void setEstadoConservacao(final String estadoConservacao) {
		this.estadoConservacao = estadoConservacao;
	}

	public int getLivroId() {
		return livroId;
	}

	public void setLivroId(final int livroId) {
		this.livroId = livroId;
	}

}
