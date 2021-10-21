package br.fai.vl.model;

public class Bibliotecario extends Pessoa {
	private int registro;
	private String email;
	private String senha;
	private int pessoaId;

	public int getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(final int pessoaId) {
		this.pessoaId = pessoaId;
	}

	public int getRegistro() {
		return registro;
	}

	public void setRegistro(final int registro) {
		this.registro = registro;
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
