package br.senai.sp.agenda.application;

import br.senai.sp.agenda.io.TarefaIO;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			TarefaIO.createFiles();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/br/senai/sp/agenda/view/Login.fxml"));
			Scene scene = new Scene(root, 500, 500);
			scene.getStylesheets().add(getClass().getResource("/br/senai/sp/agenda/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("My Schedule");
			primaryStage.setResizable(false);
			primaryStage.show();
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/br/senai/sp/agenda/imagens/agenda.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
