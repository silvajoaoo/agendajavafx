package br.senai.sp.agenda.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController {

	@FXML
	private Button botao;
	
	@FXML
	public void btnok() {

		// usado para fechar uma janela
		Stage stage = (Stage) botao.getScene().getWindow();
		stage.close();
	}

}
