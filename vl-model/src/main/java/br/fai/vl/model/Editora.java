package br.fai.vl.model;

public class Editora {
	private int id;
	private String email;
	private String razaoSocial;
	private String nomeFastasia;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(final String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFastasia() {
		return nomeFastasia;
	}

	public void setNomeFastasia(final String nomeFastasia) {
		this.nomeFastasia = nomeFastasia;
	}

}
