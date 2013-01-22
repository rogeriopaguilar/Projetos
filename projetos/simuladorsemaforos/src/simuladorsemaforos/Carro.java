package simuladorsemaforos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
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

public class Carro extends ElementoSimulacao{
	private Rua ruaAtual;
	private double velocidade;
	public static final int LARGURA_CARRO = 75; //largura do carro
	public static final int ALTURA_CARRO = 45; //altura do carro
	
	public static final int LARGURA_CARRO_CIMA_BAIXO = 45; //largura do carro
	public static final int ALTURA_CARRO_CIMA_BAIXO = 45; //altura do carro
	
	private Cor cor;
	private double tempoParaChegarAoSemaforo;
        private int tempoOriginal;

	public enum Cor{CINZA_ESCURO, VERMELHO, CINZA_CLARO, VERDE,  BRANCO}
	//Guardarei a imagem em um array para que o acesso seja mais rápido
	private static Image[] imgCarrosEsqerdaDireita = new Image[Cor.values().length];
	private static Image[] imgCarrosCimaBaixo = new Image[Cor.values().length];
	private static Color[] cores = new Color[]{new Color(105, 105, 105), Color.RED, Color.GRAY, Color.GREEN, Color.WHITE};
	
	
	public Carro(Rua ruaAtual, int tempo, Cor cor) {
		this.ruaAtual = ruaAtual;
		this.cor = cor; //new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
                this.tempoOriginal = tempo;
                reset();
	}

        public void reset() {

		this.tempoParaChegarAoSemaforo = tempoOriginal * 1000; //mudei para o tempo no construtor ser passado em segundos e não em milesegundos

      		posicao = new Point2D.Double(0,0);


		if(Rua.Direcao.ESQUERDA_PARA_DIREITA.equals(ruaAtual.getDirecao())) {
			tamanho = new Dimension(LARGURA_CARRO, ALTURA_CARRO);
		} else {
			tamanho = new Dimension(LARGURA_CARRO_CIMA_BAIXO, ALTURA_CARRO_CIMA_BAIXO);
		}

		velocidade = 0.1;
		ruaAtual.adicionarCarro(this);
		this.ultimaAtualizacao = System.currentTimeMillis();

		if(Rua.Direcao.ESQUERDA_PARA_DIREITA.equals(ruaAtual.getDirecao())) {
			velocidade = ((ruaAtual.getCentroX() - posicao.getX() - tamanho.getWidth() ) / tempoParaChegarAoSemaforo); /// 1000; //s para ms
		} else {
			velocidade = ((ruaAtual.getCentroY() - posicao.getY() - tamanho.getHeight() ) / tempoParaChegarAoSemaforo); /// 1000; //s para ms
		}

                resetUltimaAtualizacao();
        }


	
	public void atualizarEstado(long tempoAtual) {
		
			if(Rua.Direcao.ESQUERDA_PARA_DIREITA.equals(ruaAtual.getDirecao())) {
				
				double posX = posicao.getX();
				
				if( Color.GREEN.equals(ruaAtual.getSemaforo().getLuzAtual())
                                    
					
						||
						
						(
                                                    Color.RED.equals(ruaAtual.getSemaforo().getLuzAtual())
									&&
						    ((posX  + tamanho.getWidth()) <= ruaAtual.getCentroX()  )
						)

						|| 
						
						(posX) > (ruaAtual.getCentroX() )

					){
					
                                        posX += (tempoAtual - ultimaAtualizacao) * velocidade;

                                        if(Color.GREEN.equals(ruaAtual.getSemaforo().getLuzAtual())
                                           && ruaAtual.getCruzamento().carroAtravessando()
                                           && (posX  + tamanho.getWidth()) > ruaAtual.getCentroX()
                                           && (posX  + tamanho.getWidth()) < ruaAtual.getCentroX() + ruaAtual.getTamanho().getHeight()
                                        ) {
                                            posX = posicao.getX();
                                        }


					posicao.setLocation(posX, posicao.getY());
					calcularTempoRestante(tempoAtual);
					
					
				}	
				
				if(posX > ruaAtual.getTamanho().getWidth()) {
					elementoDeveSerRemovido = true;
					ruaAtual.removerCarro(this);
				}
			
			} else {

				double posY = posicao.getY();
				if(
                                        Color.GREEN.equals(ruaAtual.getSemaforo().getLuzAtual())

						||
						
						(
                                                    Color.RED.equals(ruaAtual.getSemaforo().getLuzAtual())
									&&
						    (posY + tamanho.getHeight()) < (ruaAtual.getCentroY() )
						)

						|| 
						
						posY > (ruaAtual.getCentroY() )


					){

					
					
					posY += (tempoAtual - ultimaAtualizacao) * velocidade;

                                        if(Color.GREEN.equals(ruaAtual.getSemaforo().getLuzAtual())
                                           && ruaAtual.getCruzamento().carroAtravessando()
                                           && (posY  + tamanho.getHeight()) > ruaAtual.getCentroY()
                                           && (posY  + tamanho.getHeight()) < ruaAtual.getCentroY() + ruaAtual.getTamanho().getWidth()
                                        ) {
                                            posY = posicao.getY();
                                        }


					posicao.setLocation(posicao.getX(), posY);
					
					calcularTempoRestante(tempoAtual);
					
					
				}	
				
				if(posY > ruaAtual.getTamanho().getHeight()) {
					elementoDeveSerRemovido = true;
					ruaAtual.removerCarro(this);
				}
				
			}
			
			ultimaAtualizacao = tempoAtual;
	}


	private void calcularTempoRestante(long tempoAtual) {
		tempoParaChegarAoSemaforo -= (tempoAtual - ultimaAtualizacao);
		if(tempoParaChegarAoSemaforo < 0) { //Já chegou e começou a passar
			tempoParaChegarAoSemaforo = 0;
		}
	}
	
	public void pintar(Graphics2D g, boolean exibirImagens, boolean exibirInformacoes) {
		
		g.setColor(cores[cor.ordinal()]);
		if(Rua.Direcao.ESQUERDA_PARA_DIREITA.equals(ruaAtual.getDirecao())) {
                    if(exibirImagens) {
                        g.drawImage(imgCarrosEsqerdaDireita[cor.ordinal()], (int)posicao.getX(), (int)posicao.getY(), null);
                    } else {
                        g.fill(new Rectangle2D.Double(posicao.getX(), posicao.getY(), tamanho.getWidth(), tamanho.getHeight()));
                    }
                    if(exibirInformacoes) {    
                            desenharTempoRestante(g);
                    }
		} else {
                    if(exibirImagens) {
			g.drawImage(imgCarrosCimaBaixo[cor.ordinal()], (int)posicao.getX(), (int)posicao.getY(), null);
                    } else {
			g.fill(new Rectangle2D.Double(posicao.getX(), posicao.getY(), tamanho.getWidth(), tamanho.getHeight()));
                    }
                    if(exibirInformacoes) {
			desenharTempoRestante(g);
                    }
		}
	}


	private void desenharTempoRestante(Graphics2D g) {
            String str = String.format("%.2f", tempoParaChegarAoSemaforo / 1000);

            int larguraString = g.getFontMetrics().stringWidth(str);

            int posX = (int) (posicao.getX() + tamanho.getWidth() - larguraString);

            g.drawString(str, posX, (int)posicao.getY() - g.getFontMetrics().getHeight() / 2);
	}

        public static void main(String[] args) {

        }


	@Override
	public void inicializar() {
            inicializarImpl();
	}
	
        private static void inicializarImpl() {
		try {
			synchronized(mapaImagens){
				Map<String, Image> mapaCarros = mapaImagens.get(Carro.class);
				if(mapaCarros == null) {
					mapaCarros = new HashMap<String, Image>();
					mapaImagens.put(Carro.class, mapaCarros);


					BufferedImage imgOriginal = ImageIO.read(Carro.class.getResource("res/carros_pequenos_75.gif"));


					imgCarrosEsqerdaDireita[Cor.CINZA_ESCURO.ordinal()] = imgOriginal.getSubimage(0, 0, LARGURA_CARRO, ALTURA_CARRO);
					imgCarrosCimaBaixo[Cor.CINZA_ESCURO.ordinal()] = imgOriginal.getSubimage(LARGURA_CARRO, 0, LARGURA_CARRO_CIMA_BAIXO, ALTURA_CARRO_CIMA_BAIXO);

					imgCarrosEsqerdaDireita[Cor.VERMELHO.ordinal()] = imgOriginal.getSubimage(195, 0, LARGURA_CARRO-3, ALTURA_CARRO);
					imgCarrosCimaBaixo[Cor.VERMELHO.ordinal()] = imgOriginal.getSubimage(267, 0, LARGURA_CARRO_CIMA_BAIXO, ALTURA_CARRO_CIMA_BAIXO);


					imgCarrosEsqerdaDireita[Cor.CINZA_CLARO.ordinal()] = imgOriginal.getSubimage(0, 95 , LARGURA_CARRO, ALTURA_CARRO);
					imgCarrosCimaBaixo[Cor.CINZA_CLARO.ordinal()] = imgOriginal.getSubimage(LARGURA_CARRO, 95, LARGURA_CARRO_CIMA_BAIXO, ALTURA_CARRO_CIMA_BAIXO);


					imgCarrosEsqerdaDireita[Cor.VERDE.ordinal()] = imgOriginal.getSubimage(192, 95, LARGURA_CARRO, ALTURA_CARRO);
					imgCarrosCimaBaixo[Cor.VERDE.ordinal()] = imgOriginal.getSubimage(267, 95, LARGURA_CARRO_CIMA_BAIXO, ALTURA_CARRO_CIMA_BAIXO);


					imgCarrosEsqerdaDireita[Cor.BRANCO.ordinal()] = imgOriginal.getSubimage(190, 190, LARGURA_CARRO, ALTURA_CARRO);
					imgCarrosCimaBaixo[Cor.BRANCO.ordinal()] = imgOriginal.getSubimage(267, 192, LARGURA_CARRO_CIMA_BAIXO, ALTURA_CARRO_CIMA_BAIXO);

                            }
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

        }

        public static Image getImagemParaACor(Carro.Cor cor, Rua.Direcao direcao) {
            inicializarImpl();
            Image img = null;
            if(Rua.Direcao.ESQUERDA_PARA_DIREITA.equals(direcao)) {
                img = imgCarrosEsqerdaDireita[cor.ordinal()];
            } else {
                img = imgCarrosCimaBaixo[cor.ordinal()];
            }
            return img;
        }

        public static Color getColorParaACor(Cor cor) {
            return cores[cor.ordinal()];
        }

        public Image getImagemParaCorAtualEsquerdaDireita() {
            return getImagemParaACor(cor, Rua.Direcao.ESQUERDA_PARA_DIREITA);
        }

        public Image getImagemParaCorAtualCimaBaixo() {
            return getImagemParaACor(cor, Rua.Direcao.CIMA_PARA_BAIXO);
        }

        public Color getColorParaCorAtual() {
            return getColorParaACor(cor);
        }

        public long getTempoParaChegarAoSemaforo() {
            return (long)this.tempoParaChegarAoSemaforo / 1000;
        }
}
