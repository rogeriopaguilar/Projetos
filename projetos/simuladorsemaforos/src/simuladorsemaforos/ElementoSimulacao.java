package simuladorsemaforos;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

/**
*Programa que simula o tráfego de veículos por um semáforo entre duas ruas que se cruzam e tem direção única
*@author Rogério de Paula Aguilar - rogeriodpaguilarbr@gmail.com
 *
 * os arquivos fontes estão com a codificação utf-8
 * mais informações no arquivo leiame.txt
*/

public abstract class ElementoSimulacao {
	
	protected static Map<Class, Map<String, Image>> mapaImagens = new HashMap<Class, Map<String, Image>>();

	protected Dimension tamanho;
	protected Point2D posicao;
	protected boolean elementoDeveSerRemovido = false;
	protected long ultimaAtualizacao = -1;

	public void setPosicao(double x, double y)
	{
		posicao.setLocation(x, y);
	}
	
	public Point2D getPosicao() {
		return new Point2D.Double(posicao.getX(), posicao.getY());
	}

	public Dimension getTamanho(){
		return new Dimension(tamanho);
	}
	
	public void setTamanho(int largura, int altura) {
		tamanho.setSize(largura, altura);
	}
	
	public void resetUltimaAtualizacao() {
		this.ultimaAtualizacao = System.currentTimeMillis();
	}
	
	public abstract void inicializar();
	public abstract void atualizarEstado(long tempoAtual);
	public abstract void pintar(Graphics2D g, boolean exibirImagens, boolean exibirInfo);
	
}
