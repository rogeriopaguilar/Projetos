package br.eti.rogerioaguilar.minhasclasses.util.tail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.JOptionPane;

import br.eti.rogerioaguilar.minhasclasses.util.tail.listener.LogListener;
import br.eti.rogerioaguilar.minhasclasses.util.tail.ui.ErroPainel;

/**
 * Classe que lê o arquivo, aguarda alterações no mesmo e notifica os listeners sobre as alterações
 * ocorridas no arquivo.
 * @author Rogério de Paula Aguilar - 2008
 * */
public class ThreadTail implements Runnable {
	private File arquivo;
	private volatile boolean continuar;
	private long bytesLidos;
	private LogListener logListener;

	public ThreadTail(File arquivo, LogListener logListener) {
		this.arquivo = arquivo;
		this.bytesLidos = (arquivo.length() - 16384L);
		if (this.bytesLidos < 0L)
			this.bytesLidos = 0L;
		this.logListener = logListener;
		setContinuar(true);
	}

	public void run() {
		FileChannel channel = null;
		ByteBuffer buffer = null;
		buffer = ByteBuffer.allocate(262144);
		if (this.logListener != null)
			this.logListener.inicioLog(null);
		try {
			while (isContinuar()) {
				channel = new FileInputStream(this.arquivo).getChannel();
				if (channel.size() > this.bytesLidos) {
					int lidos = 0;
					while ((lidos = channel.read(buffer, this.bytesLidos)) != -1) {
						buffer.flip();
						byte[] b = new byte[lidos];
						buffer.get(b);
						if (this.logListener != null)
							this.logListener.log(new String(b));
						buffer.clear();
						this.bytesLidos += lidos;
					}
				} else if (channel.size() < this.bytesLidos) {
					this.bytesLidos = (this.arquivo.length() - 16384L);
					if (this.bytesLidos < 0L)
						this.bytesLidos = 0L;
					if (this.logListener != null)
						this.logListener
								.log(System.getProperty("line.separator")
										+ "********Parte do arquivo foi apagada. Reinicializando leitura********"
										+ System.getProperty("line.separator"));

				}

				try {
					Thread.sleep(500L);
				} catch (InterruptedException e) {
					e.printStackTrace();
					setContinuar(false);
				}
				channel.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			PrintStream s = new PrintStream(bos);
			e.printStackTrace(s);
			s.flush();
			JOptionPane.showMessageDialog(null,
					new ErroPainel(new String(bos.toByteArray())), "Erro", 0);
		} finally {
			if ((channel != null) && (!channel.isOpen()))
				try {
					channel.close();
				} catch (IOException localIOException1) {
				}
		}
	}

	public boolean isContinuar() {
		return this.continuar;
	}

	public void setContinuar(boolean continuar) {
		this.continuar = continuar;
	}
}
