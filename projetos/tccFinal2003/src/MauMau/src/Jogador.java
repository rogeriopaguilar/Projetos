/*
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 01/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Cria��o da classe
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 05/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Movendo rotinas de teclado que s�o comuns a todos os tipos de jogadores
	   (visualizar o baralo e exibir pr�ximo jogador)
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 11/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando novas rotinas de exclus�o de cartas

Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 14/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando rotina que retorna o n�mero de cartas do jogador
           Adicionando m�todo equals
	   Adicionando vari�vel que verifica se o jogador disse mau mau
	   Adicionando m�todo dizer Mau Mau
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 18/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando rotina procurar carta
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 19/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: movendo rotina de movimenta��o de carta de jogadorhumano para jogador
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 09/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: movendo rotina de movimenta��o de carta de jogadorhumano para jogador
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 20/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: adicionando rotina que retorna uma carta pelo �ndice
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 22/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: adicionando rotina que zera o baralho do jogador
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
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
<p>Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
<p>Classe: jogo.Jogador
<p>Responsabilidades: Guarda as informa��es e a��es que s�o comuns aos jogadores, independente se estes <br>
			s�o jogadores humanos ou o computador

@author Rog�rio de Paula Aguilar
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
		Indica que tela est� sendo exibida atualmente
	*/
	private byte telaAtual;
	
	/**
		Tempo de espera
	*/
	public final static int ESPERA = 10000; //tempo de espera
	

	protected ControleJogo controleJogo;
	
	/**
		�ndice da carta selecionada
	*/
	protected int cartaSelecionada = 0;
	
	/**
		Posi��o inicial para o desenho das cartas
	*/
	protected int posicao = 12;//posi��o inicial de desenho das cartas
	
	/**
		Indica se o jogador disse mau-mau ou n�o
	*/
	protected boolean DISSE_MAU_MAU = false;
	
	/**
		Comando utilizado para sair
	*/
	protected Command cmdSair = new Command("Sair", Command.SCREEN, 0);
	
	/**
		Imagem que aparece na tela do jogador que est� jogando
	*/
	public static Sprite imgPensar;	
	
	/**
		Imagem do jogador
	*/
	public static Sprite imgJogador;	
	
	/**
		Transforma��o atual (utilizada para o desenho das setas de navega��o)
	*/
	protected int transformacaoAtual = Sprite.TRANS_NONE;
	
	/**
		Imagem da seta de navega��o
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
		M�todo invocado quando o jogador seleciona algum comando do jogo
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
		Remove uma carta do jogador pelo �ndice da carta
	*/
	public Carta removerCarta(int i){
		Carta carta = (Carta)cartasAtuais.elementAt(i);
		cartasAtuais.removeElementAt(i);
		return carta;
	}
	
	

	/**
		M�todo de jogo
	*/
	public abstract void jogar(Object args[]);

	/**
		Retorna o nome do jogador	
	*/
	public String getNome(){
		return nome;
	}

	/**
		Retorna uma representa��o do jogador no formato de um objeto String
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
		M�todo invocado quando o ujogador pressiona alguma tecla
	*/
	public void keyPressed(int keyCode){
		synchronized(controleJogo){		

		if(getKeyCode(UP) == keyCode){
			deslocamentoSetaCima = -2;
		}else if(getKeyCode(DOWN) == keyCode){
			//Exibindo cartas do pr�ximo jogador
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
		M�todo invocado quando o usu�rio solta uma tecla que estava pressionada
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
			ControleJogo.exibirDebugMsg("JogadorHumano>>keyPressed>>Exibindo cartas do pr�ximo jogador");
			deslocamentoSetaCimaBaixo = 0;
			repaint();
			controleJogo.exibirProximoJogador();	


		}
		repaint();
		}
	}

	/**
		Retorna o n�mero de cartas do jogador
	*/
	public int numeroCartas(){
		return cartasAtuais.size();
	}

	/**
		Indica se duas inst�ncias de um jogador s�o iguais
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
		Seta a op��o disse mau mau
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
		Retorna o �ndice de determinada carta
	*/

	public int getIndiceCarta(Carta carta){
		return cartasAtuais.indexOf(carta);
	}

	
	/**
		Desenha os elementos da interface gr�fica que s�o comuns a todos os tipos de 
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
		//Seta de cima apontando para baixo (pr�ximo jogador)
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

		//Informa��es sobre a carta
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
			throw new IllegalArgumentException("Baralho>>carta>>�ndice inv�lido!");
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