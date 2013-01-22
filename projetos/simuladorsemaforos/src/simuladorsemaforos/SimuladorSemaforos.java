package simuladorsemaforos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


/**
*Programa que simula o tráfego de veículos por um semáforo entre duas ruas que se cruzam e tem direção única
*@author Rogério de Paula Aguilar - rogeriodpaguilarbr@gmail.com
 *
 * os arquivos fontes estão com a codificação utf-8
 * mais informações no arquivo leiame.txt
*/

public class SimuladorSemaforos extends JFrame {

    	private JMenuBar barraMenus;
	private JMenu menuArquivo;
	private JMenuItem menuSair;
	private JMenuItem menuSobre;

	private PainelPrincipal painelPrincipal = new PainelPrincipal();
	private Graphics2D bufferGraphics;
	private BufferedImage bufferImagem;
	private int periodoPorFrame = 10; //1000 ms / 100 f = 10ms por frame
	
	private Semaforo semaforoUm, semaforoDois;
	private Rua ruaEsquerdaDireita, ruaCimaBaixo;
	private Font fonte = new Font("Serif", Font.BOLD, 14);
	
	private Deque<ElementoSimulacao> listaElementos = new ConcurrentLinkedDeque<ElementoSimulacao>();
	private ConfiguracaoSimulacao configuracao = new ConfiguracaoSimulacao();
	private PainelConfiguracaoSub painelConfiguracao;
	private volatile boolean executandoSimulacao = false;
        private volatile boolean exibirImagens = true;
        private volatile boolean exibirInformacoes = true;
	private volatile boolean pausaNaSimulacao = false;
	private ExecutorService exSimulacao = Executors.newSingleThreadExecutor();
	private Future futSimulacao = null;
	private Runnable runnableSimulacao = new Runnable(){
		public void run() {
			SimuladorSemaforos.this.loop();
		}
	};

	private ListenerRemocaoCarro listenerCarrosRemovidos = new ListenerRemocaoCarro() {
		
		@Override
		public void carroRemovido(Rua rua, Carro carro) {
			if(Rua.Direcao.ESQUERDA_PARA_DIREITA.equals(rua.getDirecao())) {
				simularProximoCarroRuaEsquerdaDireita();
			} else {
				simularProximoCarroRuaCimaBaixo();
			}
			
		}
	};
	
	
	
	class PainelPrincipal extends JPanel{
		
		private BufferedImage imgFundo;

		public PainelPrincipal(){
			try {
				imgFundo = ImageIO.read(getClass().getResource("res/fundo.png"));
			}catch(IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
                        setBackground(Color.WHITE);
		}
		
		public void pintar() {
                        Graphics2D g = (Graphics2D)getGraphics();
                        if(executandoSimulacao ) {
                            if(bufferGraphics.getFont() != fonte) {
                                    bufferGraphics.setFont(fonte);
                            }

                            if(exibirImagens) {
                                for(int i = 0; i < (getWidth() / 237) + 1; i++) {
                                        for(int j = 0; j < (getHeight() / 180) + 1; j++) {
                                                bufferGraphics.drawImage(imgFundo, i * imgFundo.getWidth(null), j * 180, null );
                                        }
                                }
                            } else {

                                Color c = bufferGraphics.getColor();
                                bufferGraphics.setColor(Color.WHITE);
                                bufferGraphics.fillRect(0, 0, getWidth(), getHeight());
                                bufferGraphics.setColor(c);
                            }



                            for(ElementoSimulacao elemento : listaElementos) {
                                    elemento.pintar(bufferGraphics, exibirImagens, exibirInformacoes);
                            }

                            Toolkit.getDefaultToolkit().sync();
                            g.drawImage(bufferImagem, 0, 0, null);
                            g.dispose();
                    } else {
                        Color c = g.getColor();
                        g.setColor(Color.WHITE);
                        g.fillRect(0, 0, getWidth(), getHeight());
                        g.setColor(c);
                    }
		}
		
	};

	
	public void iniciarOuReiniciarSimulacao(ConfiguracaoSimulacao configuracaoSimulacao) {
		pararSimulacao();			
		this.configuracao = configuracaoSimulacao;
                setExibirImagens(configuracao.isExibirImagens());
                setExibirInformacoes(configuracao.isExibirInformacoes());
		simularProximoCarroRuaEsquerdaDireita();
		simularProximoCarroRuaCimaBaixo();
		executandoSimulacao = true;
		futSimulacao = exSimulacao.submit(runnableSimulacao);
	}
	
	public void pararSimulacao() {
		executandoSimulacao = false;
		if(futSimulacao != null) {
 			try {
                            futSimulacao.cancel(true);
			    futSimulacao = null;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

                Iterator<ElementoSimulacao> it = listaElementos.iterator();
                for(;it.hasNext();) {
                    ElementoSimulacao elem = it.next();
                    if(elem instanceof Carro) {
                        it.remove();
                    } 
                }

		
		if(configuracao != null) {
			configuracao.limpar();
			configuracao = null;
		}
		painelPrincipal.repaint();
	}
	
	

	private boolean simularProximoCarroRuaEsquerdaDireita() {
		Carro carro = configuracao.getProximoCarroSimulacaoPistaEsquerdaDireita();
		if(carro != null) {
			carro.inicializar();
			carro.resetUltimaAtualizacao();
			ruaEsquerdaDireita.adicionarCarro(carro);
			listaElementos.addLast(carro);
		}
		return carro != null;
	}
	
	private boolean simularProximoCarroRuaCimaBaixo() {
		Carro carro = configuracao.getProximoCarroSimulacaoPistaCimaBaixo();
		if(carro != null) {
			carro.inicializar();
			carro.resetUltimaAtualizacao();
			ruaCimaBaixo.adicionarCarro(carro);
			listaElementos.addLast(carro);
		}
		return carro != null;
	}

	
	public SimuladorSemaforos() throws InvocationTargetException, InterruptedException {
		super("Simulador semáforo");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Constantes.LARGURA_JANELA + 380, Constantes.ALTURA_JANELA);
		setLocationRelativeTo(null);
		inicializarSemaforos();
		inicializarRuas();
                inicializarMenus();
                inicializarPainelConfiguracao();
                inicializarPainelPrincipal();
		setResizable(false);

		setVisible(true);
	}

        private void inicializarPainelConfiguracao() {
                this.painelConfiguracao = new PainelConfiguracaoSub(this.ruaEsquerdaDireita, this.ruaCimaBaixo, this);
                this.ruaCimaBaixo.adicionarListenerRemocaoCarro(painelConfiguracao);
                this.ruaEsquerdaDireita.adicionarListenerRemocaoCarro(painelConfiguracao);
        }

        private void inicializarMenus() {
		this.barraMenus = new JMenuBar();
		this.menuSobre = new JMenuItem();
		this.menuArquivo = new JMenu();

		this.menuSair = new JMenuItem();
		this.menuArquivo.setText("Arquivo");
		this.menuSobre.setText("Sobre");
		this.menuSair.setText("Sair");
		this.menuSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
                            System.exit(0);
			}
		});

                this.menuSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
                            JOptionPane.showMessageDialog(SimuladorSemaforos.this, new JanelaSobre(), "Sobre", -1);
			}
		});

                this.menuArquivo.add(this.menuSobre);
                this.menuArquivo.addSeparator();
                this.menuArquivo.add(this.menuSair);
                this.barraMenus.add(menuArquivo);
                setJMenuBar(this.barraMenus);
        }

	private void inicializarPainelPrincipal() {
		JPanel painelConteudo = new JPanel();
		painelConteudo.setLayout(new BorderLayout());
		painelPrincipal.setPreferredSize(new Dimension(Constantes.LARGURA_JANELA, Constantes.ALTURA_JANELA));
		painelConfiguracao.setPreferredSize(new Dimension(380, Constantes.ALTURA_JANELA));
		painelConteudo.add(painelConfiguracao, BorderLayout.WEST);
                painelConteudo.add(painelPrincipal, BorderLayout.CENTER);
		bufferImagem = new BufferedImage(Constantes.LARGURA_JANELA, Constantes.ALTURA_JANELA, BufferedImage.TYPE_INT_ARGB);		
		bufferGraphics = bufferImagem.createGraphics();
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		bufferGraphics.addRenderingHints(hints);
		setContentPane(painelConteudo);

	}
	

	public void loop() {
		List<ElementoSimulacao> elementosARemover = new LinkedList<ElementoSimulacao>();
		                Iterator<ElementoSimulacao> it = listaElementos.iterator();
                                
                for(;it.hasNext();) {
                    ElementoSimulacao elem = it.next();
                    elem.resetUltimaAtualizacao();
                }
		long tempoInicial = System.currentTimeMillis();

		while(executandoSimulacao) {
			if(!pausaNaSimulacao) {

				painelPrincipal.pintar();
				for(ElementoSimulacao elemento : listaElementos) {
					elemento.atualizarEstado(tempoInicial);
					if(elemento.elementoDeveSerRemovido) {
						elementosARemover.add(elemento);
					}
				}
				
				if(elementosARemover.size() > 0) {
					listaElementos.removeAll(elementosARemover);
					elementosARemover.clear();
				}

				
				
				
				long tempoCorrido = System.currentTimeMillis() - tempoInicial;
				long tempoSleep = periodoPorFrame - tempoCorrido;
				if(tempoSleep < 0) {
					tempoSleep = 5;
				}
				sleep(tempoSleep);
	
			}
			tempoInicial = System.currentTimeMillis();			
			
		}

	}


	private void sleep(long tempoSleep) {
		try {
			Thread.sleep(tempoSleep);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
	
	private void inicializarRuas() {
		ruaEsquerdaDireita = new Rua(semaforoUm, Rua.Direcao.ESQUERDA_PARA_DIREITA, listenerCarrosRemovidos);
		ruaCimaBaixo = new Rua(semaforoDois, Rua.Direcao.CIMA_PARA_BAIXO, listenerCarrosRemovidos);
		ruaEsquerdaDireita.inicializar();
		ruaCimaBaixo.inicializar();
                ruaEsquerdaDireita.setCruzamento(ruaCimaBaixo);
                ruaCimaBaixo.setCruzamento(ruaEsquerdaDireita);
		listaElementos.addFirst(ruaEsquerdaDireita);
		listaElementos.addFirst(ruaCimaBaixo);
		
	}

	private void inicializarSemaforos(){
		semaforoUm = new Semaforo(Color.GREEN);
		semaforoDois = new Semaforo(Color.RED);

		semaforoUm.inicializar();
		semaforoDois.inicializar();
		
		listaElementos.addLast(semaforoUm);
		listaElementos.addLast(semaforoDois);
		
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					JFrame.setDefaultLookAndFeelDecorated(true);
					new SimuladorSemaforos();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

    public boolean isExecutandoSimulacao() {
        return executandoSimulacao;
    }

    public boolean isExibirImagens() {
        return exibirImagens;
    }

    public void setExibirImagens(boolean exibirImagens) {
        this.exibirImagens = exibirImagens;
    }

    public boolean isExibirInformacoes() {
        return exibirInformacoes;
    }

    public void setExibirInformacoes(boolean exibirInformacoes) {
        this.exibirInformacoes = exibirInformacoes;
    }



}

