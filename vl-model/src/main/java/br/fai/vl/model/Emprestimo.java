package br.fai.vl.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Emprestimo {
	private int id;
	private int codigo;
	private Timestamp dataRealizacao;
	private int leitorId;
	private Timestamp dataEfetivDevolucao;
	private boolean devolvido;
	private Timestamp dataEfetivaDevolucao;
	private BigDecimal multa;
	private int exemplar_id;

	public Timestamp getDataEfetivDevolucao() {
		return dataEfetivDevolucao;
	}

	public void setDataEfetivDevolucao(final Timestamp dataEfetivDevolucao) {
		this.dataEfetivDevolucao = dataEfetivDevolucao;
	}

	public boolean isDevolvido() {
		return devolvido;
	}

	public void setDevolvido(final boolean devolvido) {
		this.devolvido = devolvido;
	}

	public Timestamp getDataEfetivaDevolucao() {
		return dataEfetivaDevolucao;
	}

	public void setDataEfetivaDevolucao(final Timestamp dataEfetivaDevolucao) {
		this.dataEfetivaDevolucao = dataEfetivaDevolucao;
	}

	public BigDecimal getMulta() {
		return multa;
	}

	public void setMulta(final BigDecimal multa) {
		this.multa = multa;
	}

	public int getExemplar_id() {
		return exemplar_id;
	}

	public void setExemplar_id(final int exemplar_id) {
		this.exemplar_id = exemplar_id;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(final int codigo) {
		this.codigo = codigo;
	}

	public Timestamp getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(final Timestamp dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public int getLeitorId() {
		return leitorId;
	}

	public void setLeitorId(final int leitor_id) {
		this.leitorId = leitor_id;
	}

}
