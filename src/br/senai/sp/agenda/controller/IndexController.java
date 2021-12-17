package br.senai.sp.agenda.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import br.senai.sp.agenda.io.TarefaIO;
import br.senai.sp.agenda.model.StatusTarefa;
import br.senai.sp.agenda.model.Tarefa;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import jdk.nashorn.internal.ir.CallNode.EvalArgs;
import jdk.nashorn.internal.scripts.JO;

public class IndexController implements Initializable, ChangeListener<Tarefa> {

	private Button btnLimpar;

	@FXML
	private TextField dataFinalizada;
	@FXML
	private TextField codigo;

	@FXML
	private DatePicker datatf;

	@FXML
	private TableView<Tarefa> tvTarefa;

	@FXML
	private TableColumn<Tarefa, LocalDate> tcData;

	@FXML
	private TableColumn<Tarefa, String> tcTarefa;

	private List<Tarefa> tarefas;

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
	private Button btnconcluirclick;

	@FXML
	private Button clickadiar;

	@FXML
	private Button concluirclick;

	@FXML
	private Label TITULOTF;

	@FXML
	private Hyperlink linktf;

	private Tarefa tarefa;

	@FXML
	void btnadiar() {

		if (tarefa != null) {
			int dias = Integer.parseInt(JOptionPane.showInputDialog(null, "Quantos dias Voce Deseja Adiar",
					"Informe Quantos Dias", JOptionPane.QUESTION_MESSAGE));

			// Tera uma nova data a partir de hj metodo .PLUS
			LocalDate novaData = tarefa.getDataLimite().plusDays(dias);

			// nova data apos o adiamento
			tarefa.setDataLimite(novaData);

			tarefa.setStatus(StatusTarefa.ADIADA);

			try {
				TarefaIO.saveTarefas(tarefas);

				carregarTarefas();
				limparCampos();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao atualizar as tarefas", "Erro",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

			DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			JOptionPane.showMessageDialog(null, "Data Alterada com Sucesso ",
					"Data Atualizada " + novaData.format(formatador), JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@FXML

	void btnLimpar() {
		limparCampos();

	}

	@FXML
	void btnconcluir() {

		if (tarefa != null) {

			tarefa.setDataFinalizada(LocalDate.now());

			tarefa.setStatus(StatusTarefa.CONCLUIDA);

			try {
				TarefaIO.saveTarefas(tarefas);

				carregarTarefas();
				limparCampos();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao atualizar as tarefas", "Erro",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

		}

	}

	@FXML
	public void btnexcluir() {

		if (tarefa != null) {

			int resposta = JOptionPane.showConfirmDialog(null,
					"Deseja realmente excluir a tarefa " + tarefa.getId() + "?", "Confirmar Exclusão",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (resposta == 0) {

				tarefas.remove(tarefa);

				try {
					TarefaIO.saveTarefas(tarefas);
					limparCampos();
					carregarTarefas();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Ocorreu Um Erro ao Atualizar A tarefa", "Erro",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}

			}
		}
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
			// verifica se a data informada nao é anterior a data atual

			if (datatf.getValue().isBefore(LocalDate.now())) {

				JOptionPane.showConfirmDialog(null, "Informe uma Data Válida", "Informe", JOptionPane.ERROR_MESSAGE);
				datatf.requestFocus();

			} else {

				// instanciando a tarefa

				// verifica se é uma tarefa nova

				if (tarefa == null) {
					tarefa = new Tarefa();
					tarefa.setDataCriacao(LocalDate.now());
					tarefa.setStatus(StatusTarefa.ABERTA);
				}

				// populando as tarefas
				tarefa.setDataLimite(datatf.getValue());
				tarefa.setComentario(comentariostf.getText());
				tarefa.setDescricao(tarefatf.getText());

				// salvar no banco de dados

				System.out.println(tarefa.formatToSave());

				try {

					if (tarefa.getId() == 0) {
						TarefaIO.insert(tarefa);
					} else {

						TarefaIO.saveTarefas(tarefas);
					}

					// chamando o metodo limpar campos
					limparCampos();
					carregarTarefas();
				} catch (FileNotFoundException e) {
					JOptionPane.showConfirmDialog(null, "Arquivo Não Encontrado" + e.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showConfirmDialog(null, "Erro De I/O: " + e.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}

	}

// fechando btn salvar

	// criando um metodo de limpar os campos
	private void limparCampos() {
		tarefa = null;
		comentariostf.setText("");
		statustf.setText("");
		tarefatf.setText("");
		datatf.setValue(null);
		datatf.requestFocus();
		datatf.setDisable(false);
		tvTarefa.getSelectionModel().clearSelection();
		codigo.setText("");
		btnconcluirclick.setDisable(false);
		comentariostf.setEditable(true);
		tarefatf.setEditable(true);
		comentariostf.setEditable(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// define os parametros para as colunas do TableView
		tcData.setCellValueFactory(new PropertyValueFactory<>("dataLimite"));

		tcTarefa.setCellValueFactory(new PropertyValueFactory<>("descricao"));

		// "classe anonima"
		tcData.setCellFactory(call -> {

			return new TableCell<Tarefa, LocalDate>() {

				// Metodo que sobre escreve ("Os Metodos da Table View")
				@Override
				protected void updateItem(LocalDate item, boolean empty) {

					super.updateItem(item, empty);
					DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MMM/yyyy");

					if (!empty) {

						setText(item.format(fmt));

					} else {

						setText("");
					}

				}

			};
		});
		tvTarefa.setRowFactory(call -> new TableRow<Tarefa>() {

			protected void updateItem(Tarefa item, boolean empty) {

				super.updateItem(item, empty);

				if (item == null) {

					setStyle("");
				} else if (item.getStatus() == StatusTarefa.CONCLUIDA) {

					setStyle("-fx-background-color: #80ed99");

				} else if (item.getDataLimite().isBefore(LocalDate.now())) {

					setStyle("-fx-background-color: red");

				} else {

					setStyle("-fx-background-color: #ccc");
				}

			}

		});

		// Evento de Seleção de Um Item na Table View

		tvTarefa.getSelectionModel().selectedItemProperty().addListener(this);
		carregarTarefas();
	}

	public IndexController() {

	}

	private void carregarTarefas() {

		try {
			// buscando a tarefa no bd
			tarefas = TarefaIO.readTarefas();
			tvTarefa.setItems(FXCollections.observableArrayList(tarefas));

			// atualizar tabela
			tvTarefa.refresh();

		} catch (IOException e) {
			JOptionPane.showInternalMessageDialog(null, "Erro ao Buscar a tarefas", "Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();

		}

	}

	@Override
	public void changed(ObservableValue<? extends Tarefa> observable, Tarefa oldValue, Tarefa newValue) {

		// passar a referencia da variavel local
		// para a tarefa local

		tarefa = newValue;

		// se houver uma tarefa selecionada
		if (tarefa != null) {

			dataFinalizada.setText(tarefa.getDataFinalizada() + "");
			comentariostf.setText(tarefa.getComentario());
			tarefatf.setText(tarefa.getDescricao());
			statustf.setText(tarefa.getStatus().toString());
			datatf.setValue(tarefa.getDataLimite());
			datatf.setDisable(true);
			datatf.setOpacity(1);
			;
			clickadiar.setDisable(false);
			btnexcluirclick.setDisable(false);
			codigo.setText(tarefa.getId() + "");

			if (tarefa.getStatus() == StatusTarefa.CONCLUIDA) {
				btnconcluirclick.setDisable(true);
				btnsalvarclick.setDisable(true);
				comentariostf.setEditable(false);
				tarefatf.setEditable(false);
				btnexcluirclick.setDisable(false);

			} else {

				btnconcluirclick.setDisable(false);
				comentariostf.setEditable(true);
				tarefatf.setEditable(true);
				comentariostf.setEditable(true);
				dataFinalizada.setText("");
			}

			btnexcluirclick.setDisable(false);

		}
	}

	@FXML
	public void menuExportar() {

		// metodo que faz com que abra o menu para salvar
		JFileChooser choser = new JFileChooser();

		// metodo que faz so filtrar arquivos Html
		FileFilter filter = new FileNameExtensionFilter("Arquivos Html", "html");

		choser.setFileFilter(filter);

		if (choser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

			File arqSelecionado = choser.getSelectedFile();
			arqSelecionado = new File(arqSelecionado + ".html");
			try {
				TarefaIO.exportHtml(tarefas, arqSelecionado);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	@FXML
	public void menuAjuda() {

	}

	@FXML
	public void menuPrincipal() {

	}

	@FXML
	public void menuAbout() {
		try {

			AnchorPane root = (AnchorPane) FXMLLoader
					.load(getClass().getResource("/br/senai/sp/agenda/view/Sobre.fxml"));
			Scene scene = new Scene(root, 846, 590);
			Stage stage = new Stage();
			stage.setTitle("Sobre My Schedule");
			// TIRANDO A BORDA
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);

			// DANDO FOCO TOTAL A JANELA
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void menuItem() {

	}
}
