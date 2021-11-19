package br.senai.sp.agenda.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

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
	
	public static List<Integer> readTarefas() throws FileNotFoundException{
		File arqTarefas = new File(FILE_TAREFA);
		List<Tarefa> tarefas = new ArrayList<>();
		FileReader reader = new FileReader(arqTarefas);
		BufferedReader buff = new  BufferedReader(reader);
		return null;
		
	}
}
