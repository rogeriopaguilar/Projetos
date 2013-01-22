package simuladorsemaforos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
*Programa que simula o tráfego de veículos por um semáforo entre duas ruas que se cruzam e tem direção única
*@author Rogério de Paula Aguilar - rogeriodpaguilarbr@gmail.com
 *
 * os arquivos fontes estão com a codificação utf-8
 * mais informações no arquivo leiame.txt
*/


public class Semaforo extends ElementoSimulacao{
	public static final int TEMPO_SEMAFORO = 15; //segundos
	
	private static final String CHAVE_IMG_SEMAFORO_VERMELHO = "SEMAFORO_VERMELHO";
	private static final String CHAVE_IMG_SEMAFORO_VERDE = "SEMAFORO_VERDE";
	private Color luzAtual;
	private long tempoParaMudarOSinal;
	
	public Semaforo() {
		luzAtual = Color.RED;
		this.posicao = new Point2D.Double(0, 0);
		this.tempoParaMudarOSinal = TEMPO_SEMAFORO;
	}

	public Semaforo(Color luzAtual) {
		this();
		this.luzAtual = luzAtual;
	}
	
	
	public Color getLuzAtual() {
		return this.luzAtual;
	}
	
	
	
	public void atualizarEstado(long tempoAtual) {
		long tempoCorrido = (tempoAtual / 1000 - ultimaAtualizacao / 1000);
		if( tempoCorrido > TEMPO_SEMAFORO) { 
			mudarCores();
		} else {
			tempoParaMudarOSinal = TEMPO_SEMAFORO - tempoCorrido;
		}
	}

	private void mudarCores() {
		if( Color.RED.equals(luzAtual) ) {
			luzAtual = Color.GREEN;
		} else {
			luzAtual = Color.RED;
		} 
		ultimaAtualizacao = System.currentTimeMillis();
		tempoParaMudarOSinal = TEMPO_SEMAFORO;
	}
	
	public void pintar(Graphics2D g, boolean exibirImagens, boolean exibirInfo) {

		final Map<String, Image> mapaSemaforo = mapaImagens.get(Semaforo.class);
		final Image imgVermelha = mapaSemaforo.get(CHAVE_IMG_SEMAFORO_VERMELHO);
		final Image imgVerde = mapaSemaforo.get(CHAVE_IMG_SEMAFORO_VERDE);

                //System.out.println("" + exibirInfo);

		if( Color.RED.equals(luzAtual) ) {
                        if(exibirImagens) {
                            g.drawImage(imgVermelha, (int) posicao.getX(), (int)posicao.getY(), null );
                        } else {
                            Color c = g.getColor();
                            g.setColor(luzAtual);
                            g.fillRect((int)posicao.getX(), (int)posicao.getY(), (int)tamanho.width, (int)tamanho.height);
                            g.setColor(c);
                        }
                        if(exibirInfo) {
                            desenharTempoRestante(g);
                        }
		} else {
                        if(exibirImagens) {
                            g.drawImage(imgVerde, (int) posicao.getX(), (int)posicao.getY(), null );
                        } else {
                            Color c = g.getColor();
                            g.setColor(luzAtual);
                            g.fillRect((int)posicao.getX(), (int)posicao.getY(), (int)tamanho.width, (int)tamanho.height);
                            g.setColor(c);

                        }
                        if(exibirInfo) {
                            desenharTempoRestante(g);
                        }
		}
	}

	private void desenharTempoRestante(Graphics2D g) {
		String str = String.format("%2d", tempoParaMudarOSinal);
		g.drawString(str, 
				(int)(posicao.getX() + tamanho.getWidth()) + 2, 
				(int)(posicao.getY()) + 21);
	}

	@Override
	public void inicializar() {
		try {
			synchronized(mapaImagens){
				Map<String, Image> mapaSemaforo = mapaImagens.get(Semaforo.class);
				if(mapaSemaforo == null) { 
					mapaSemaforo = new HashMap<String, Image>();
					BufferedImage imgOriginal = ImageIO.read(getClass().getResource("res/semaforo.gif"));
					BufferedImage img = new BufferedImage(imgOriginal.getWidth() / 2, imgOriginal.getHeight() / 2, BufferedImage.TYPE_INT_ARGB);
					AffineTransform at = new AffineTransform();
					at.scale(0.5, 0.5);
					AffineTransformOp scaleOp =  new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
					img = scaleOp.filter(imgOriginal, img);			
					tamanho = new Dimension(img.getWidth() / 2, img.getHeight());
					Image imgTrVermelho = img.getSubimage(0, 0, tamanho.width, tamanho.height);
					Image imgTrVerde = img.getSubimage(tamanho.width, 0, tamanho.width , tamanho.height);
					mapaSemaforo.put(CHAVE_IMG_SEMAFORO_VERMELHO, imgTrVermelho);
					mapaSemaforo.put(CHAVE_IMG_SEMAFORO_VERDE, imgTrVerde);
					mapaImagens.put(Semaforo.class, mapaSemaforo);
				} else if(tamanho == null) {				
					Image img = mapaSemaforo.get(CHAVE_IMG_SEMAFORO_VERDE);
					tamanho = new Dimension(img.getWidth(null), img.getHeight(null));
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}


}
