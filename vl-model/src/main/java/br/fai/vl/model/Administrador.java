package br.fai.vl.model;

public class Administrador extends Pessoa {
	private String email;
	private String senha;
	private int pessoaId;

	public int getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(final int pessoaId) {
		this.pessoaId = pessoaId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(final String senha) {
		this.senha = senha;
	}
}
