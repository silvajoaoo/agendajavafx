package br.senai.sp.agenda.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import br.senai.sp.agenda.model.StatusTarefa;
import br.senai.sp.agenda.model.Tarefa;

public class TarefaIO {

	private static final String FOLDER =

			System.getProperty("user.home") + "/Agenda-fx";

	private static final String FILE_IDS = FOLDER + "/id.csv";

	private static final String FILE_TAREFA = FOLDER + "/tarefas.csv";

	// Metodo que cria a pasta
	public static void createFiles() {

		try {
			File pasta = new File(FOLDER);
			File arqIds = new File(FILE_IDS);
			File arqTarefas = new File(FILE_TAREFA);

			if (!pasta.exists()) {

				pasta.mkdir();

				arqIds.createNewFile();

				arqTarefas.createNewFile();

				FileWriter writer = new FileWriter(arqIds);

				// Escrevendo o primeiro id
				writer.write("1");

				// Fechando o arquivo
				writer.close();
			}
		} catch (Exception e) {

			// Imprime o erro no console
			e.printStackTrace();
		}
	}

	public static void insert(Tarefa tarefa) throws FileNotFoundException, IOException {

		File arqTarefas = new File(FILE_TAREFA);
		File arqIds = new File(FILE_IDS);

		// LER O ULTIMO ID NO FILE_IDS

		Scanner leitor = new Scanner(arqIds);
		// lendo o id
		tarefa.setId(leitor.nextLong());

		// fechando o scanner
		leitor.close();

		// GRAVA A TAREFA NO ARQUIVO

		FileWriter writer = new FileWriter(arqTarefas, true);

		writer.append(tarefa.formatToSave());
		writer.close();

		// GRAVA O NOVO "PROXIMO ID" NO ARQUIVO IDS

		long proxId = tarefa.getId() + 1;
		FileWriter writerId = new FileWriter(arqIds);
		writerId.write(proxId + "");
		writerId.close();
	}

	// lista nao fixa, possivel colocar e tirar de qualquer posicao//
	public static List<Tarefa> readTarefas() throws IOException {
		File arqTarefas = new File(FILE_TAREFA);
		List<Tarefa> tarefas = new ArrayList<>();
		FileReader reader = new FileReader(arqTarefas);
		BufferedReader buff = new BufferedReader(reader);
		String linha;

		while ((linha = buff.readLine()) != null) {

			// tranformar a linha num vetor(Método Split)

			String[] vetor = linha.split(";");

			// cria uma Tarefa
			Tarefa t = new Tarefa();

			// tranforma String em Long
			t.setId(Long.parseLong(vetor[0]));

			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			t.setDataCriacao(LocalDate.parse(vetor[1], fmt));

			t.setDataLimite(LocalDate.parse(vetor[2], fmt));

			if (!vetor[3].isEmpty()) {

				t.setDataFinalizada(LocalDate.parse(vetor[3], fmt));
			}

			t.setDescricao(vetor[4]);

			// Integer.parseInt(Coverte de String Para Inteiro)

			int indStatus = Integer.parseInt(vetor[6]);

			t.setStatus(StatusTarefa.values()[indStatus]);

			t.setComentario(vetor[5]);

			// adicionando a tarefa No Arquivo txt(Banco de dados)

			tarefas.add(t);
		}

		buff.close();
		reader.close();

		// METODO QUE ORDENA AS TAREFAS
		Collections.sort(tarefas);

		return tarefas;

	}

	public static void saveTarefas(List<Tarefa> tarefas) throws IOException {

		File arqTarefas = new File(FILE_TAREFA);
		FileWriter writer = new FileWriter(arqTarefas);
		for (Tarefa t : tarefas) {

			writer.append(t.formatToSave());
		}
		writer.close();

	}

	public static void exportHtml(List<Tarefa> lista, File Arquivo) throws IOException {

		// metodo que usamos para para escrever
		FileWriter writer = new FileWriter(Arquivo);
		writer.append("<!DOCTYPE html>\n");
		writer.append("<html>\n");
		writer.append("<body>\n");
		writer.append("<h1> Lista de Tarefas </h1>\n");
		writer.append("<ul>\n");

		for (Tarefa tarefa : lista) {
			writer.append("<li>\n");
			writer.append(tarefa.getDescricao() + "-" + tarefa.getDataLimite() + "-" + tarefa.getStatus());
			writer.append("</list>\n");
		}
		writer.append("</ul>\n");
		writer.append("</body>\n");
		writer.append("</html>\n");
		writer.close();

	}
}
