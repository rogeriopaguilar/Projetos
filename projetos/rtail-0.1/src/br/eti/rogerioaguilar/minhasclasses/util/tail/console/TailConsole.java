package br.eti.rogerioaguilar.minhasclasses.util.tail.console;

import java.io.File;

import br.eti.rogerioaguilar.minhasclasses.util.tail.ThreadTail;
import br.eti.rogerioaguilar.minhasclasses.util.tail.listener.LogListener;

/**
 * Classe que utiliza a thread que faz a leitura do arquivo no console.
 * @author Rogério de Paula Aguilar - 2008
 * */
public class TailConsole {
	public static void main(String[] args) {
		if ((args.length != 2) || (!"-f".equals(args[0]))) {
			System.out.println("use TailConsole -f caminho_arquivo");
			System.exit(1);
		}

		File arquivo = new File(args[1]);

		ThreadTail tail = new ThreadTail(arquivo, new LogListener() {
			public void log(String msg) {
				System.out.print(msg);
			}

			public void inicioLog(Object obj) {
			}
		});
		new Thread(tail).start();
	}
}
