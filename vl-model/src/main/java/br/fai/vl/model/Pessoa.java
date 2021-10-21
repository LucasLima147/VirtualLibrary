package br.fai.vl.model;

public abstract class Pessoa{
	private int id;
	private String cpf;
	private String nome;
	private String rua;
	private int numero;
	private String bairro;
	private String cidade;
	private String estado;
	private String telefone;

	public String getCpf() {
		return cpf;
	}
	public void setCpf(final String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(final String nome) {
		this.nome = nome;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(final String rua) {
		this.rua = rua;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(final int numero) {
		this.numero = numero;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(final String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(final String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(final String estado) {
		this.estado = estado;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(final String telefone) {
		this.telefone = telefone;
	}
	public int getId() {
		return id;
	}
	public void setId(final int id) {
		this.id = id;
	}

}
