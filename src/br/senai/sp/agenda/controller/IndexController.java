package br.senai.sp.agenda.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.senai.sp.agenda.io.TarefaIO;
import br.senai.sp.agenda.model.StatusTarefa;
import br.senai.sp.agenda.model.Tarefa;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class IndexController implements Initializable {

	@FXML
	private DatePicker datatf;

	@FXML
	private TextField tarefatf;

	@FXML
	private TextField statustf;

	@FXML
	private TextArea comentariostf;

	@FXML
	private Button btnsalvarclick;

	@FXML
	private Button btnexcluirclick;

	@FXML
	private Button btnclearclick;

	@FXML
	private Button clickadiar;

	@FXML
	private Label TITULOTF;

	@FXML
	private Hyperlink linktf;

	private Tarefa tarefa;

	@FXML
	void btnadiar() {

	}

	@FXML
	void btnclear() {

		// chamando o metodo limpar Campos
		limparCampos();
		comentariostf.setStyle("-fx-background-color:red");

	}

	@FXML
	void btnexcluir() {
		// chamando o metodo limpar Campos
		limparCampos();

	}

	@FXML
	void btnsalvar() {

		if (datatf.getValue() == null) {
			JOptionPane.showConfirmDialog(null, "Informe a Data Limite", "Informe", JOptionPane.ERROR_MESSAGE);
			btnsalvarclick.requestFocus();

		} else if (comentariostf.getText().isEmpty()) {

			JOptionPane.showMessageDialog(null, "Informe Os Comentarios da Tarefa", "Informe",
					JOptionPane.ERROR_MESSAGE);
			comentariostf.requestFocus();

		} else {
			// verifica se a data informada nao � anterior a data atual

			if (datatf.getValue().isBefore(LocalDate.now())) {

				JOptionPane.showConfirmDialog(null, "Informe uma Data V�lida", "Informe", JOptionPane.ERROR_MESSAGE);
				datatf.requestFocus();

			} else {

				// instanciando a tarefa
				tarefa = new Tarefa();

				// populando as tarefas
				tarefa.setDataCriacao(LocalDate.now());
				tarefa.setDataLimite(datatf.getValue());
				tarefa.setStatus(StatusTarefa.ABERTA);
				tarefa.setComentario(comentariostf.getText());
				tarefa.setDescricao(tarefatf.getText());

				// salvar no banco de dados

				System.out.println(tarefa.formatToSave());

				// Limpar os campos do formulario

				try {
					TarefaIO.insert(tarefa);
					// chamando o metodo limpar campos
					limparCampos();
				} catch (FileNotFoundException e) {
					JOptionPane.showConfirmDialog(null, "Arquivo N�o Encontrado" + e.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showConfirmDialog(null, "Erro De I/O: " + e.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}

			}

		}

	}// fechando btn salvar

	// criando um metodo de limpar os campos
	private void limparCampos() {
		tarefa = null;
		comentariostf.setText("");
		statustf.setText("");
		tarefatf.setText("");
		datatf.setValue(null);
		datatf.requestFocus();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
