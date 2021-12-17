package br.senai.sp.agenda.controller;

import javax.swing.JOptionPane;

import br.senai.sp.agenda.io.TarefaIO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.awt.RequestFocusController;

public class LoginController {

	@FXML
	private TextField senha;

	@FXML
	private TextField login;

	public void btnEnviar() {

		if (senha.getText().isEmpty() && login.getText().isEmpty()) {

			JOptionPane.showMessageDialog(null, "Campos vazios", "Vazios", JOptionPane.INFORMATION_MESSAGE);
			login.requestFocus();

		} else if (senha.getText().equals("Admin") && login.getText().equals("Admin")) {

			try {
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/br/senai/sp/agenda/view/Index.fxml"));
				Scene scene = new Scene(root, 1100, 680);
				scene.getStylesheets()
						.add(getClass().getResource("/br/senai/sp/agenda/view/application.css").toExternalForm());
				Stage primaryStage = new Stage();
				primaryStage.setScene(scene);
				primaryStage.setTitle("My Schedule");
				primaryStage.setResizable(false);
				primaryStage.show();
				primaryStage.getIcons()
						.add(new Image(getClass().getResourceAsStream("/br/senai/sp/agenda/imagens/agenda.png")));
				// usado para fechar uma janela
				Stage stage = (Stage) login.getScene().getWindow();
				stage.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		} else {

			JOptionPane.showMessageDialog(null, "Senha E login Incorretos", "Errada", JOptionPane.ERROR_MESSAGE);
			login.requestFocus();
		}
	}

}
