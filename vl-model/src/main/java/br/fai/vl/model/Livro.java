package br.fai.vl.model;

public class Livro {

	private int id;
	private Long isbn;
	private String titulo;
	private String sinopse;
	private int numPaginas;
	private boolean ativo;
	private int editoraId;
	private int generoId;
	private int autorId;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public Long getIsbn() {
		return isbn;
	}

	public void setIsbn(final Long isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(final String sinopse) {
		this.sinopse = sinopse;
	}

	public int getNumPaginas() {
		return numPaginas;
	}

	public void setNumPaginas(final int numPaginas) {
		this.numPaginas = numPaginas;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(final boolean ativo) {
		this.ativo = ativo;
	}

	public int getEditoraId() {
		return editoraId;
	}

	public void setEditoraId(final int editoraId) {
		this.editoraId = editoraId;
	}

	public int getGeneroId() {
		return generoId;
	}

	public void setGeneroId(final int generoId) {
		this.generoId = generoId;
	}

	public int getAutorId() {
		return autorId;
	}

	public void setAutorId(final int autorId) {
		this.autorId = autorId;
	}

}
