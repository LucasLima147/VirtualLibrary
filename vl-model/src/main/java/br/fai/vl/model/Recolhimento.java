package br.fai.vl.model;

import java.sql.Timestamp;

public class Recolhimento {
	private int id;
	private Timestamp dataSolicitacao;
	private Timestamp dataRecolhimento;
	private boolean recolhido;
	private int leitorId;
	private int emprestimoId;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public Timestamp getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(final Timestamp dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public Timestamp getDataRecolhimento() {
		return dataRecolhimento;
	}

	public void setDataRecolhimento(final Timestamp dataRecolhimento) {
		this.dataRecolhimento = dataRecolhimento;
	}

	public boolean isRecolhido() {
		return recolhido;
	}

	public void setRecolhido(final boolean recolhido) {
		this.recolhido = recolhido;
	}

	public int getLeitorId() {
		return leitorId;
	}

	public void setLeitorId(final int leitorId) {
		this.leitorId = leitorId;
	}

	public int getEmprestimoId() {
		return emprestimoId;
	}

	public void setEmprestimoId(final int emprestimoId) {
		this.emprestimoId = emprestimoId;
	}

}
