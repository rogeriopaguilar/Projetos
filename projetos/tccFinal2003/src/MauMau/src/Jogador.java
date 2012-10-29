/*
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 01/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Criação da classe
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 05/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Movendo rotinas de teclado que são comuns a todos os tipos de jogadores
	   (visualizar o baralo e exibir próximo jogador)
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 11/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Adicionando novas rotinas de exclusão de cartas

Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 14/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Adicionando rotina que retorna o número de cartas do jogador
           Adicionando método equals
	   Adicionando variável que verifica se o jogador disse mau mau
	   Adicionando método dizer Mau Mau
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 18/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Adicionando rotina procurar carta
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 19/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: movendo rotina de movimentação de carta de jogadorhumano para jogador
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 09/10/2003
Responsável: Rogério de Paula Aguilar
Descrição: movendo rotina de movimentação de carta de jogadorhumano para jogador
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 20/10/2003
Responsável: Rogério de Paula Aguilar
Descrição: adicionando rotina que retorna uma carta pelo índice
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 22/10/2003
Responsável: Rogério de Paula Aguilar
Descrição: adicionando rotina que zera o baralho do jogador
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%



*/
package jogo;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.util.*;
import jogo.*;
import jogo.apibasica.*;

/**
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
<p>Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
<p>Classe: jogo.Jogador
<p>Responsabilidades: Guarda as informações e ações que são comuns aos jogadores, independente se estes <br>
			são jogadores humanos ou o computador

@author Rogério de Paula Aguilar
@version  1.0

*/


public abstract class Jogador extends GameCanvas implements CommandListener{
	
	/**
		Nome do jogador
	*/
	private String nome;
	
	/**
		Cartas do jogador
	*/
	protected Stack cartasAtuais;
	
	/**
		Baralho
	*/
	protected Baralho baralho;
	
	/**
		Indica que tela está sendo exibida atualmente
	*/
	private byte telaAtual;
	
	/**
		Tempo de espera
	*/
	public final static int ESPERA = 10000; //tempo de espera
	

	protected ControleJogo controleJogo;
	
	/**
		Índice da carta selecionada
	*/
	protected int cartaSelecionada = 0;
	
	/**
		Posição inicial para o desenho das cartas
	*/
	protected int posicao = 12;//posição inicial de desenho das cartas
	
	/**
		Indica se o jogador disse mau-mau ou não
	*/
	protected boolean DISSE_MAU_MAU = false;
	
	/**
		Comando utilizado para sair
	*/
	protected Command cmdSair = new Command("Sair", Command.SCREEN, 0);
	
	/**
		Imagem que aparece na tela do jogador que está jogando
	*/
	public static Sprite imgPensar;	
	
	/**
		Imagem do jogador
	*/
	public static Sprite imgJogador;	
	
	/**
		Transformação atual (utilizada para o desenho das setas de navegação)
	*/
	protected int transformacaoAtual = Sprite.TRANS_NONE;
	
	/**
		Imagem da seta de navegação
	*/
	public static Sprite imgSeta;
	
	protected byte deslocamentoSetaEsquerda = 0;
	protected byte deslocamentoSetaDireita = 0;
	protected byte deslocamentoSetaCima = 0;
	protected byte deslocamentoSetaCimaBaixo = 0;


	
	/**
		Construtor
	*/
	public Jogador(String nome, Baralho baralho, ControleJogo controleJogo){
		super(false);
		this.nome = nome;
		this.baralho = baralho;
		cartasAtuais = new Stack();
		this.controleJogo = controleJogo;
		//setTicker(new Ticker("Mesa: " + baralho.cartaAtual().toString()) );
		addCommand(cmdSair);
		setCommandListener(this);
		try{
			imgPensar = new Sprite(Image.createImage("/pensar.png"));
			imgSeta = new Sprite(Image.createImage("/seta.png"));
			imgJogador = new Sprite(Image.createImage("/jogador.png"));

		}catch(Exception e){
			ControleJogo.exibirDebugMsg("Jogador>>Jogador(...)>>Erro ao carregar .png>>" + e);
		}
		
	}

	/**
		Método invocado quando o jogador seleciona algum comando do jogo
	*/
	public void commandAction(Command c, Displayable d){
	       synchronized(controleJogo){	
		if(c == cmdSair){
			controleJogo.setApresentacao(true);
			controleJogo.incrementaJogoAtual();
			controleJogo.setTelaSelecao(true);
			ControleJogo.JOGO_EM_ANDAMENTO = false;

			ControleJogo.exibirDebugMsg("JOGO_EM_ANDAMENTO: " + ControleJogo.JOGO_EM_ANDAMENTO);
			CartasMidlet.getDisplay().setCurrent(controleJogo);
			if(controleJogo.ONLINE){
				controleJogo.enviarMsgServidor("SAIR_CLIENTE");
				
				controleJogo.fecharConexao();
				ControleJogo.ONLINE = false;

			}
			System.gc();
		}
		}
	}
	

	/**
		Adiciona uma carta para este jogador
	*/
	public void adicionarCarta(Carta carta){
		cartasAtuais.push(carta);
	}

	/**
		Remove uma carta do jogador
	*/
	public Carta removerCarta(){
		return (Carta)(cartasAtuais.pop());
	}

	/**
		Remove uma carta do jogador pelo índice da carta
	*/
	public Carta removerCarta(int i){
		Carta carta = (Carta)cartasAtuais.elementAt(i);
		cartasAtuais.removeElementAt(i);
		return carta;
	}
	
	

	/**
		Método de jogo
	*/
	public abstract void jogar(Object args[]);

	/**
		Retorna o nome do jogador	
	*/
	public String getNome(){
		return nome;
	}

	/**
		Retorna uma representação do jogador no formato de um objeto String
	*/
	public String toString(){
		StringBuffer str = new StringBuffer("*****Jogador>>Nome: " + nome + "*****");
		for(int i = 0; i < cartasAtuais.size(); i++){
			str.append("\n" + (Carta)cartasAtuais.elementAt(i));
		}
		return str.toString().trim();

	}
	
	/**
		Modifica o nome do jogador
	*/
	public void setNome(String nome){
		this.nome = nome;
	}


	/**
		Método invocado quando o ujogador pressiona alguma tecla
	*/
	public void keyPressed(int keyCode){
		synchronized(controleJogo){		

		if(getKeyCode(UP) == keyCode){
			deslocamentoSetaCima = -2;
		}else if(getKeyCode(DOWN) == keyCode){
			//Exibindo cartas do próximo jogador
			deslocamentoSetaCimaBaixo = 2;
		}else if(getKeyCode(LEFT) == keyCode){
			//Move a carta para a esquerda
			cartaSelecionada -= 1;
			if(cartaSelecionada < 0)
				cartaSelecionada = cartasAtuais.size() - 1;
			deslocamentoSetaEsquerda = -2; 

		}else if(getKeyCode(RIGHT) == keyCode){
			//Move a carta para a direita
			cartaSelecionada += 1;
			if(cartaSelecionada > (cartasAtuais.size() - 1))
				cartaSelecionada = 0;
			deslocamentoSetaDireita = 2;	
			
		}
		repaint();
		}
	}


	/**
		Método invocado quando o usuário solta uma tecla que estava pressionada
	*/
	public void keyReleased(int keyCode){
 	       synchronized(controleJogo){
		if(getKeyCode(LEFT) == keyCode)
			deslocamentoSetaEsquerda = 0;
	       else if(getKeyCode(RIGHT) == keyCode)
			deslocamentoSetaDireita = 0;
	       else if(getKeyCode(UP) == keyCode){
			//Exibindo Baralho
			deslocamentoSetaCima = 0;	 
			repaint();
			ControleJogo.exibirDebugMsg("JogadorHumano>>keyPressed>>Exibindo Baralho");
			baralho.setTelaVolta(this);
			CartasMidlet.getDisplay().setCurrent(baralho);
			
	        }else if(getKeyCode(DOWN) == keyCode){
			ControleJogo.exibirDebugMsg("JogadorHumano>>keyPressed>>Exibindo cartas do próximo jogador");
			deslocamentoSetaCimaBaixo = 0;
			repaint();
			controleJogo.exibirProximoJogador();	


		}
		repaint();
		}
	}

	/**
		Retorna o número de cartas do jogador
	*/
	public int numeroCartas(){
		return cartasAtuais.size();
	}

	/**
		Indica se duas instâncias de um jogador são iguais
	*/
	public boolean equals(Object obj){
		boolean b = false;
		if(obj instanceof Jogador){
			Jogador jogador = (Jogador)obj;
			b = nome.equals(jogador.nome);
			if(b){
				if(cartasAtuais.size() == jogador.cartasAtuais.size()){
					for(int i = 0; i < cartasAtuais.size(); i++){
						Carta carta1 = (Carta)cartasAtuais.elementAt(i);
						Carta carta2 = (Carta)jogador.cartasAtuais.elementAt(i);
						b = b && carta1.equals(carta2);
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
		return b; 
	}

	/**	
		Seta a opção disse mau mau
	*/
	public void setDisseMauMau(boolean d){
		DISSE_MAU_MAU = d;
	}

	/**
		Retorna o valor de DISSE_MAU_MAU
	*/
	public boolean getDisseMauMau(){
		return DISSE_MAU_MAU;
	}

	/**
		Retorna o índice de determinada carta
	*/

	public int getIndiceCarta(Carta carta){
		return cartasAtuais.indexOf(carta);
	}

	
	/**
		Desenha os elementos da interface gráfica que são comuns a todos os tipos de 
		jogadores
	*/
	public void paint(Graphics g){
		


		g.setColor(0, 190, 0);
		g.fillRect(0, 0, Dispositivo.getLarguraTela(), Dispositivo.getAlturaTela());
	

		if(controleJogo.getJogadorAtual() == controleJogo.getJogadorExibido()){
			imgPensar.setPosition(Dispositivo.getLarguraTela() - 70, 25);
			imgPensar.paint(g);
		}
		
		
		//Seta da esquerda
		imgSeta.setTransform(Sprite.TRANS_NONE);
		imgSeta.setPosition( 5 + deslocamentoSetaEsquerda, Dispositivo.getCentroY() + (Carta.ALTURA_CARTA / 2) + 10);
		imgSeta.paint(g);
		//Seta da direita
		imgSeta.setTransform(Sprite.TRANS_MIRROR);
		imgSeta.setPosition( Dispositivo.getLarguraTela() - 5 - imgSeta.getWidth() + deslocamentoSetaDireita, Dispositivo.getCentroY() + (Carta.ALTURA_CARTA / 2) + 10);
		imgSeta.paint(g);
		//Seta de cima (baralho)
		imgSeta.setTransform(Sprite.TRANS_ROT90);
		imgSeta.setPosition( 2, 5 + deslocamentoSetaCima);
		imgSeta.paint(g);
		//Seta de cima apontando para baixo (próximo jogador)
		imgSeta.setTransform(Sprite.TRANS_ROT270);
		imgSeta.setPosition( Dispositivo.getLarguraTela() - imgSeta.getWidth() - 2 , 5 + deslocamentoSetaCimaBaixo);
		imgSeta.paint(g);
		//Boneco
		imgJogador.setPosition( Dispositivo.getLarguraTela() - imgSeta.getWidth() - 12, 5);
		imgJogador.paint(g);
		//Baralho em miniatura
		for(byte n = 0; n < 3; n++){
			Baralho.cartaVersoMiniatura.setPosition(8 + imgSeta.getWidth() + n, 4 + n);
			Baralho.cartaVersoMiniatura.paint(g);

		}

		//Informações sobre a carta
		if(ControleJogo.EXIBIR_INFORMACAO_CARTA && (this instanceof JogadorHumano || (this instanceof JogadorComputador && controleJogo.getOpenMauMau()))){
			
			if(cartaSelecionada >= 0 && cartaSelecionada < cartasAtuais.size()){
				Ticker tk = getTicker();
				if(tk != null){
					tk.setString(((Carta)cartasAtuais.elementAt(cartaSelecionada)).toString());
				}else{
					tk = new Ticker(((Carta)cartasAtuais.elementAt(cartaSelecionada)).toString());
					setTicker(tk);
				}

				/*g.setColor(0, 0, 255);
				g.drawString( ((Carta)cartasAtuais.elementAt(cartaSelecionada)).toString(), 0, 0, 0);
				*/
				
			}
		}
				
		
	}

	/**
		Retorna uma carta do baralho
	*/
	
	public Carta carta(int indice){
		if(indice < 0 || indice > cartasAtuais.size())
			throw new IllegalArgumentException("Baralho>>carta>>Índice inválido!");
		return (Carta)cartasAtuais.elementAt(indice);
	}

	/**
		Deixa o jogador sem nenhuma carta
	*/
	public void zerar(){
		cartasAtuais = new Stack();
		System.gc();
	}
	
}