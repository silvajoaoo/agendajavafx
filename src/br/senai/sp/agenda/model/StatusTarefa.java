package br.senai.sp.agenda.model;

public enum StatusTarefa {

	ABERTA("Aberta"), CONCLUIDA("Conckuida"), ADIADA("Adiada");

	String descricao;

	private StatusTarefa(String desc) {
		this.descricao = desc;
		
	}

	@Override
	public String toString() {
		return descricao;
	}
}