package br.senai.sp.agenda.io;

import java.io.File;
import java.io.FileWriter;

import jdk.jfr.events.FileWriteEvent;

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
}
