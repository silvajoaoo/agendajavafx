package br.senai.sp.agenda.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Tarefa {

	private long id;
	private LocalDate dataCriacao;
	private LocalDate dataLimite;
	private LocalDate dataFinalizada;
	private String descricao;
	private String comentario;
	private StatusTarefa status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDate getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(LocalDate dataLimite) {
		this.dataLimite = dataLimite;
	}

	public LocalDate getDataFinalizada() {
		return dataFinalizada;
	}

	public void setDataFinalizada(LocalDate dataFinalizada) {
		this.dataFinalizada = dataFinalizada;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public StatusTarefa getStatus() {
		return status;
	}

	public void setStatus(StatusTarefa status) {
		this.status = status;
	}

	public String formatToSave() {
		// contruindo uma string
		StringBuilder builder = new StringBuilder();

		// formatando a data no padrao normal dia/mes/ano
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		builder.append(this.getId() + ";");
		// formatando a data naquele padrao
		builder.append(this.getDataCriacao().format(fmt) + ";");
		builder.append(this.getDataLimite().format(fmt) + ";");

		if (this.getDataFinalizada() != null) {

			builder.append(this.getDataFinalizada().format(fmt));

		}
		builder.append(";");
		builder.append(this.getComentario() + ";");
		builder.append(this.getDescricao() + ";");
		builder.append(this.getStatus().ordinal() + "\n");
		// devolve Uma String
		return builder.toString();
	}
}
