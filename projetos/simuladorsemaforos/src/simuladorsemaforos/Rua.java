package simuladorsemaforos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;

/**
*Programa que simula o tráfego de veículos por um semáforo entre duas ruas que se cruzam e tem direção única
*@author Rogério de Paula Aguilar - rogeriodpaguilarbr@gmail.com
 *
 * os arquivos fontes estão com a codificação utf-8
 * mais informações no arquivo leiame.txt
*/

public class Rua extends ElementoSimulacao {
	public static enum Direcao { ESQUERDA_PARA_DIREITA, CIMA_PARA_BAIXO }
	private static final String CHAVE_IMG_RUA_CIMA_BAIXO = "CHAVE_IMG_RUA_CIMA_BAIXO"; 
	private static final String CHAVE_IMG_RUA_ESQUERDA_DIREITA = "CHAVE_IMG_RUA_ESQUERDA_DIREITA"; 
	private Direcao direcao;
	private Rua cruzamento;
	private Queue<Carro> listaCarros = new ConcurrentLinkedQueue<Carro>();
	private Semaforo semaforo;
	private double centroX, centroY;
	private List<ListenerRemocaoCarro> listeners = new CopyOnWriteArrayList<ListenerRemocaoCarro>();
	public static final int BASE_ALTURA_RUA = 25; 


	public Rua(Semaforo semaforo, Direcao direcao, ListenerRemocaoCarro listener) {
		this.semaforo = semaforo;
		this.direcao = direcao;
                this.listeners.add(listener);
	}

        public Rua getCruzamento() {
            return cruzamento;
        }

        public void setCruzamento(Rua cruzamento) {
            this.cruzamento = cruzamento;
        }



	public Semaforo getSemaforo() {
		return this.semaforo;
	}
	
	public Direcao getDirecao(){
		return direcao;
	}
	
	public void atualizarEstado(long tempoAtual){}
	
	public void pintar(Graphics2D g, boolean exibirImagens, boolean exibirInformacoes) {
		
		
		g.setColor(Color.BLACK);
		if(Direcao.ESQUERDA_PARA_DIREITA.equals(direcao)) {

			final Image img = mapaImagens.get(Rua.class).get(CHAVE_IMG_RUA_ESQUERDA_DIREITA);
			final Line2D linhaEsquerdaCima = new Line2D.Double(posicao.getX(), posicao.getY(), tamanho.getWidth() / 2 - tamanho.getHeight() / 2, posicao.getY());
			final Line2D linhaDireitaCima = new Line2D.Double(tamanho.getWidth() / 2 + tamanho.getHeight() / 2 , posicao.getY(), tamanho.getWidth()  , posicao.getY());
			final Line2D linhaEsquerdaBaixo = new Line2D.Double(posicao.getX(), posicao.getY() + tamanho.getHeight(), tamanho.getWidth() / 2 - tamanho.getHeight() / 2, posicao.getY() + tamanho.getHeight());
			final Line2D linhaDireitaBaixo = new Line2D.Double(tamanho.getWidth() / 2 + tamanho.getHeight() / 2 , posicao.getY() + tamanho.getHeight(), tamanho.getWidth()  , posicao.getY() + tamanho.getHeight());
			final Line2D linhaCruzamento = new Line2D.Double(centroX, posicao.getY(), centroX, posicao.getY() + tamanho.getHeight());

                        if(exibirImagens) {
                            for(int i = 0; i < (tamanho.getWidth() / img.getWidth(null)); i++) {
                                    g.drawImage(img, i * img.getWidth(null), (int) posicao.getY(), null );
                                    //g.setColor(Color.BLUE);
                                    //g.draw(new Rectangle2D.Double(i*img.getWidth(null), posicao.getY(), i*img.getWidth(null) + tamanho.getWidth(), img.getHeight(null)));
                            }
                        } else {

                            g.setColor(Color.BLACK);
                            g.draw(linhaEsquerdaCima);
                            g.draw(linhaDireitaCima);
                            g.draw(linhaEsquerdaBaixo);
                            g.draw(linhaDireitaBaixo);
                        }

                         if(exibirInformacoes) {
                             pintarLinhaCruzamento(g, linhaCruzamento);
                         }
		} else {
			final Image img = mapaImagens.get(Rua.class).get(CHAVE_IMG_RUA_CIMA_BAIXO);
			final Line2D linhaEsquerdaCima = new Line2D.Double(posicao.getX(), posicao.getY(), posicao.getX(), tamanho.getHeight() / 2 - tamanho.getWidth() / 2);
			final Line2D linhaEsquerdaBaixo = new Line2D.Double(posicao.getX(), tamanho.getHeight() / 2 + tamanho.getWidth() / 2, posicao.getX(), tamanho.getHeight());
			final Line2D linhaDireitaCima = new Line2D.Double(posicao.getX() + tamanho.width, posicao.getY(), posicao.getX() + tamanho.width, tamanho.getHeight() / 2 - tamanho.getWidth() / 2);
			final Line2D linhaDireitaBaixo = new Line2D.Double(posicao.getX() + tamanho.width, tamanho.height / 2 + tamanho.getWidth() / 2, posicao.getX() + tamanho.width, tamanho.getHeight() );
                        final Line2D linhaCruzamento = new Line2D.Double(posicao.getX(), centroY, posicao.getX() + tamanho.getWidth(), centroY );
			
                        if(exibirImagens) {
                            for(int i = 0; i < (tamanho.getHeight() / img.getHeight(null)); i++) {
                                    g.drawImage(img, (int) posicao.getX(), i * img.getHeight(null), null );
                            }
                        } else {
			
                            g.draw(linhaEsquerdaCima);
                            g.draw(linhaEsquerdaBaixo);
                            g.draw(linhaDireitaCima);
                            g.draw(linhaDireitaBaixo);
                        }

                        if(exibirInformacoes) {
                            pintarLinhaCruzamento(g, linhaCruzamento);
                        }
			
		}
		
	}

        private void pintarLinhaCruzamento(Graphics2D g, Line2D linhaCruzamento) {
		final Stroke s = new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,   10.0f, new float[] {16.0f,20.0f}, 0.0f);
                Stroke strokeAnterior = g.getStroke();
                Color corAnterior = g.getColor();
                g.setStroke(s);
                g.setColor(Color.CYAN);
                g.draw(linhaCruzamento);
                g.setColor(corAnterior);
                g.setStroke(strokeAnterior);
        }

	
	public void adicionarCarro(Carro carro) {
		if(Direcao.ESQUERDA_PARA_DIREITA.equals(direcao)) {
			double posY = posicao.getY() + ((tamanho.height - BASE_ALTURA_RUA) / 2);
			carro.setPosicao(carro.getPosicao().getX(), posY);
			
		} else {
			double posX = posicao.getX() + ((tamanho.width - BASE_ALTURA_RUA) / 2);
			carro.setPosicao(posX, carro.getPosicao().getY());
		}
		listaCarros.add(carro);
	}

	public void removerCarro(Carro carro) {
		listaCarros.remove(carro);
		if(listeners != null) {
                    for(ListenerRemocaoCarro listener : listeners) {
                        listener.carroRemovido(this, carro);
                    }
		}
	}
	
	@Override
	public void inicializar() {

		if(Direcao.ESQUERDA_PARA_DIREITA.equals(direcao)) {
			this.tamanho = new Dimension(Constantes.LARGURA_JANELA, (int)(BASE_ALTURA_RUA * 5));
			posicao = new Point2D.Double(0, (Constantes.ALTURA_JANELA - tamanho.height) / 2);

			semaforo.setPosicao((Constantes.LARGURA_JANELA / 2) + (tamanho.height / 2), 
									 (Constantes.ALTURA_JANELA / 2) - tamanho.height / 2				
					);
			
			this.centroX = (tamanho.getWidth() / 2) - (tamanho.getHeight() / 2);
			
		} else {
			this.tamanho = new Dimension((int)(BASE_ALTURA_RUA * 5), Constantes.ALTURA_JANELA);
			posicao = new Point2D.Double((Constantes.LARGURA_JANELA - tamanho.width) / 2, 0);
			
			semaforo.setPosicao((Constantes.LARGURA_JANELA / 2) - tamanho.width / 2, 
					(Constantes.ALTURA_JANELA / 2) + tamanho.width / 2	);

			this.centroY = (tamanho.getHeight() / 2) - (tamanho.getWidth() / 2);
			
		}
		
		try {
			synchronized(mapaImagens) {
				Map<String, Image> mapaRua = mapaImagens.get(Rua.class);
				if(mapaRua == null) { 
					mapaRua = new HashMap<String, Image>();
					mapaImagens.put(Rua.class, mapaRua);
				}
					
					
					
					if(Direcao.ESQUERDA_PARA_DIREITA.equals(direcao)) {
						if(!mapaRua.containsKey(CHAVE_IMG_RUA_ESQUERDA_DIREITA)){
							
							BufferedImage imgOriginal = ImageIO.read(getClass().getResource("res/rua.jpg"));
							
							
							BufferedImage img = ImagemUtil.rotacionarImagem(imgOriginal, 1);
							double novaAltura= (img.getHeight() - tamanho.getHeight()) / img.getHeight();
							AffineTransform at = new AffineTransform();
							at.scale(0.5, novaAltura + .02);
							AffineTransformOp scaleOp =  new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
							img = scaleOp.filter(img, null);			
							

							mapaRua.put(CHAVE_IMG_RUA_ESQUERDA_DIREITA, img);
						}
						
					} else {

						if(!mapaRua.containsKey(CHAVE_IMG_RUA_CIMA_BAIXO)){
							
							BufferedImage imgOriginal = ImageIO.read(getClass().getResource("res/rua.jpg"));
							double novaLargura = (imgOriginal.getWidth() - tamanho.getWidth()) / imgOriginal.getWidth();
							
							BufferedImage img = new BufferedImage((int)tamanho.getWidth(), (int)imgOriginal.getHeight()/2, BufferedImage.TYPE_INT_ARGB);
							AffineTransform at = new AffineTransform();
							at.scale(novaLargura + .02, 0.5);
							AffineTransformOp scaleOp =  new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
							img = scaleOp.filter(imgOriginal, img);			
							mapaRua.put(CHAVE_IMG_RUA_CIMA_BAIXO, img);
						}
					}
					
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		

		
	}

	public boolean carroAtravessando() {
            for(Carro carro : listaCarros) {
                if(Direcao.ESQUERDA_PARA_DIREITA.equals(direcao)) {
                    if(carro.getPosicao().getX() > centroX && carro.getPosicao().getX() < centroX + this.getTamanho().getHeight()) {
                        return true;
                    }
                } else {
                    if(carro.getPosicao().getY() > centroY && carro.getPosicao().getY() < centroY + this.getTamanho().getWidth()) {
                        return true;
                    }
                }
            }
            return false;
        }

	public double getCentroX() {
		return centroX;
	}


	public double getCentroY() {
		return centroY;
	}

	
	public int getQtdeCarros() {
		return listaCarros.size();
	}
	
        public void adicionarListenerRemocaoCarro(ListenerRemocaoCarro listener) {
            listeners.add(listener);
        }

        public void removerListenerRemocaoCarro(ListenerRemocaoCarro listener) {
            listeners.remove(listener);
        }

	
}
