package br.fai.vl.dto;

public class EntregaDTO {

	private int idEntrega;
	private int idEmprestimo;
	private int codEmprestimo;
	private String userName;
	private String userEmail;
	private int userID;

	public int getIdEmprestimo() {
		return idEmprestimo;
	}

	public void setIdEmprestimo(final int idEmprestimo) {
		this.idEmprestimo = idEmprestimo;
	}

	public int getCodEmprestimo() {
		return codEmprestimo;
	}

	public void setCodEmprestimo(final int codEmprestimo) {
		this.codEmprestimo = codEmprestimo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(final String userEmail) {
		this.userEmail = userEmail;
	}

	public int getIdEntrega() {
		return idEntrega;
	}

	public void setIdEntrega(final int idEntrega) {
		this.idEntrega = idEntrega;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(final int userID) {
		this.userID = userID;
	}

}
