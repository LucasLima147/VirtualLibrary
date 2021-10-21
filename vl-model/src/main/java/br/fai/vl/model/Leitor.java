package br.fai.vl.model;

public class Leitor extends Pessoa {
	private int matricula;
	private String email;
	private String senha;
	private int pessoaId;

	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(final int matricula) {
		this.matricula = matricula;
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

	public int getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(final int pessoa_id) {
		this.pessoaId = pessoa_id;
	}

}
