/*
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
Classe: jogo.apibasica.ControleJogo
Responsabilidades: Controlar a execu��o do jogo e a atualiza��o das telas de apresenta��o
		   e principal do mesmo. Controla o loop principal do jogo e a navegabilidade
		   da aplica��o.


****************************************Altera��es****************************************


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 09/08/2003
Respons�vel: Rog�rrio de Paula Aguilar
Descri��o: t�rmino da primeira parte da apresenta��o do jogo, adi��o de comandos
	   principais do jogo
Status: altera��es OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 22/08/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Implementa��o do formul�rio de ajuda e t�rmino da apresenta��o
Status: pendente (falta terminar o formul�rio de ajuda)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 30/08/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Associando imagens �s cartas
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 05/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando m�todos para cria��o do modo OpenMauMau
	   , atualizando tipos de jogador, adicionando formul�rio de op��es
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 11/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando rotina de verifica��o de jogada e classe Mensagem
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 14/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Continuando rotina de verifica��o de jogada e classe Mensagem
	   Adicionando rotina que verifica o vencedor do jogo	
	   Modificando rotinas de debug para utilizarem o m�todo exibirDebugMsg da
		classe ControleJogo
	   Adicionando vari�vel que controla o n�mero de cartas que o jogador
		deve comprar

Status:OK 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 15/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando rotina que retorna o �ndice do pr�ximo jogador
	   Adicionando rotina que retorna o �ndice do jogador anterior
Status:OK 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 19/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Implementando rotina que elege o vencedor do jogo
	   Testendo e corrigindo rotina verificarJogada
Status:OK 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 09/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Testando rotina de verifica��o de jogadas
	   Alterando mensagens para se tornarem mais claras para o usu�rio
Status:OK 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 10/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Arrumando interface gr�fica
	   Criando vari�vel JOGO_EM_ANDAMENTO para controlar
		erros de sincronismo entre as threads de mensagens
	   IMPORTANTE: TODOS OS FORMUL�RIOS QUE VOLTAREM PARA ESA TELA
			DEVEM SETAR A VARI�VEL JOGO_EM_ANDAMENTO PARA FALSE,
			PARA QUE A THREAD DE IN�CIO SEJA EXIBIDA CORRETAMENTOE


Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 13/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Criando op��o de jogo on-line
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 15/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Modificando protocolo (http para socket)
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 20/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando rotina de jogo on-line
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 21/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando rotina de jogo on-line
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 22/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando rotina de jogo on-line
	   Adicionando rotina que trata da atualiza��o do jogo, quando
	     o quadro com o status do jogo chega do servidor	
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 26/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Terminando rotina de tratamento de quadros
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 26/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Refazendo rotina de jogada do usu�rio, para trocar mensagens e
	   corrigir problemas
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 05/11/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Arrumando tela de apresenta��o
	   Criando formul�rio de ajuda
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 12/11/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Arrumando problema da thread de jogada do computador
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 28/11/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando thread que fica enviando Acks para o servidor
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


*/
package jogo.apibasica;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.game.*;
import java.util.*;
import java.io.*;
import javax.microedition.io.*;
import jogo.*;


/**
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
<p>Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
<p>Classe: jogo.apibasica.ControleJogo
<p>Responsabilidades: Controlar a execu��o do jogo e a atualiza��o das telas de apresenta��o
		   e principal do mesmo. Controla o loop principal do jogo e a navegabilidade
		   da aplica��o.

@author Rog�rio de Paula Aguilar
@version 1.0

*/

public class ControleJogo extends GameCanvas implements Runnable, CommandListener{
	/**
		Indica se o modo debug est� ativo ou n�o
	*/
	public final static boolean DEBUG_MODE = true;


	/**
		Indica se o jogador est� no meio de uma partida
	*/
	
	public static boolean JOGO_EM_ANDAMENTO = false;


	/**
		Indica o �ndice do jogo atual contra o computador
	*/

	private int jogoAtual = 0;

	/**
		Indica se o jogador est� conectado ao servidor
	*/
	
	public static boolean CONECTADO;
	
	
	/**
		Estado do jogo
	*/
	
	public static boolean EXIBIR_INFORMACAO_CARTA = false;
	public static boolean APRESENTACAO, ONLINE, TELASELECAO; //Estados do jogo
	
	/**
		Tempo de espera para atualiza��o da tela de jogo
	*/
	
	private byte DELAY = 100; //Tempo de espera para atualiza��o da tela
	
	/**
		Objeto que cont�m as caracter�sticas do dispositivo onde o jogo est�
		sendo executado
	*/
	
	private Dispositivo dispositivo;

	/**
		Disply do jogo
	*/
	
	private Display display;
	private boolean JOGANDO;
	
	/**
		Vari�veis que controlam o sentido do jogo
	*/
	public final static byte SENTIDO_NORMAL = 1, SENTIDO_INVERSO = -1;
	public static byte SENTIDO_JOGO = SENTIDO_NORMAL;

	/**
		Indica se a penalidade de uma carta hostil j� foi aplicada ou n�o
	*/
	public static boolean PENALIDADE_APLICADA = false; 
	
	/**
		Quantidade de cartas para compra	
	*/
	private static byte QUANT_COMPRA = 0;	
	
	/**
		Vari�vel auxiliar para o desenho da tela de apresenta��o
	*/
	private int x_src = 5;

	/**
		Imagem contando o logo do jogo
	*/
	private Sprite imgLogo; //Carta

	/**
		Imagem contando o logo do jogo de forma escritaa
	*/
	private Image imgLogoEscrita;//Logo escrito
	
	/**
		Formul�rio de ajuda
	*/

	private frmAjuda formularioAjuda; //Formul�rio de ajuda

	/**
		Formul�rio de visualiza��o de cartas
	*/
	private frmVisualizadorCartas visualizadorCartas; //Visualiza��o das cartas

	/**
		Formul�rio de Op��es
	*/
	private frmOpcoes frmopcoes; //Op��es
	
	/**
		Formul�rio contendo a lista de salas (utilizado no jogo on-line)
	*/
	private frmListaSalas frmlistasalas; //Lista de salas
	
	/**	Formul�rio de espera
	*/
	public static frmEspera frmespera = new frmEspera("Aguarde comunica��o com o servidor...");
	
	/**
		Formul�rio de escolha de naipe (utilizado na jogada da valete)
	*/
	private frmEscolhaNaipe frmescolhanaipe;

	/**
		Comando do menu principal
	*/
	private Command cmdSair = new Command("Sair", Command.EXIT, 2);
	
	/**
		Comando do menu principal
	*/
	private Command cmdIniciarJogo = new Command("Jogar", Command.SCREEN, 0);
	
	/**
		Comando do menu principal
	*/
	private Command cmdIniciarJogoOnLine = new Command("Jogo on-line", Command.SCREEN, 0);
	
	/**
		Comando do menu principal
	*/
	private Command cmdOpcoes = new Command("Op��es", Command.SCREEN,0);
	
	/**
		Comando do menu principal
	*/
	private Command cmdAjuda = new Command("Ajuda", Command.SCREEN,0);
	
	/**
		Comando do menu principal
	*/
	private Command cmdVisualizadorCartas = new Command("Visualizador de Cartas", Command.SCREEN,0);
	

	/**
		Midlet principal do jogo
	*/
	private CartasMidlet cartasMidlet;


	
	/**
		Vari�vel auxiliar para desenho de cartas
	*/
	private int posicaoCartaX, posicaoCartaY;
	
	/**
		Vari�vel auxiliar para desenho de cartas
	*/
	private byte direcao = 1;


	/**
		Baralho do jogo
	*/
	private Baralho baralhoAtual = new Baralho();
	private byte naipeAtual = 0, cartaAtual = 0;
	
	/**
		N�mero de jogadores
	*/
	public static int NUMERO_JOGADORES = 4;
	
	/**
		Jogadores
	*/
	Jogador jogadores[] = new Jogador[NUMERO_JOGADORES];
	
	/**
		�ndice do jogador atual
	*/
	private byte jogadorAtual;
	
	/**
		�ndice do jogador que est� sendo exibido
	*/
	private byte jogadorExibido;


	/**
		Indica se um jogador pode ver as cartas do outro
	*/
	private static boolean OPEN_MAU_MAU = false;	

	/**
		Indica se o jogador anterior foi pulado
	*/
	public boolean ANTERIOR_PULADO = false; 

	/**
		Gerador de n�meros aleat�rios
	*/
	
	private static Random random = new Random();


	/**
		Objeto utilizado para exibir mensagens de erro
	
	*/
	private Mensagem msgErro;


	/**
		Objeto que guarda a conex�o com o servidor
	*/
	public static SocketConnection conexao;
	
	/**
		Objeto utilizado para ler comandos que vem do servidor
	*/
	public static InputStream is;
	
	/**
		Objeto utilizado para enviar mensagens para o servidor
	*/
	public static OutputStream os;
	
	/**
		Objeto utilizado para enviar mensagens para o servidor
	*/
	public static PrintStream socketWriter;
	
	/**
		Objeto utilizado para guardar os nomes dos jogadores
	*/
	public static String[] nomeJogadores = new String[NUMERO_JOGADORES];
	
	/**
		Indica se o jogador � o primeiro
	*/
	public static boolean primeiroJogador;

	/**
		Objeto que guarda o id da sala que o usu�rio est� conectado
	*/
	public static String idSala = "";
	
	/**
		Objeto  que guarda o nome do jogador atual
	*/
	public static String nomeJogador = "";	
	
	/**
		Guarda o �ndice do jogador na rede
	*/
	public static int indiceJogadorRede;
	
	/**
		Indica se o usu�rio j� recebeu o primeiro status do servidor
	*/
	private boolean primeiroStatusRecebido = false;
	
	/**
		Indica se o jogador recebeu permiss�o para jogar
	*/
	private boolean RECEBEU_PERMISSAO_JOGADA;
	
	/**
		Endere�o do servidor
	*/
	private static String IP_SERVIDOR, PORTA_SERVIDOR;
	

	private static String comando = "";


	/**
		Guarda o naipe escolhido para a pr�xima jogada (Jogada: Valete(*))
	*/
	public static byte NAIPE_ESCOLHIDO = 0;

	/**
		Listeners para formul�rios de espera
	*/
	private static CommandListener listenerOK, listenerEntradaJogador;

	/**
		Formul�rio de espera
	*/
	private static frmEspera frm;
	
	/**
		Comando cancelar do formul�rio de espera
	*/
	private static Command cmdCancelar = new Command("Cancelar", Command.SCREEN, 0);
	
	/**
		Comando de come�o de jogo do formul�rio de espera
	*/
	private static Command cmdComecarJogo = new Command("Come�ar jogo", Command.SCREEN, 0);
															

	/**
		Thread que fica mandando acks para o servidor
	*/

	private static class threadAck implements Runnable{

		private ControleJogo controleJogo;

		public threadAck(ControleJogo controleJogo){
			this.controleJogo = controleJogo;
		}
		
		public void run(){
			while(CONECTADO){
				try{
					synchronized(controleJogo){
						exibirDebugMsg("Enviando ack");
						socketWriter.println();
						socketWriter.flush();					
					}
					Thread.sleep(25000); //25 segundos
				}catch(Exception e){
					exibirDebugMsg("Erro ao enviar ack: " + e);
				}
			}

			exibirDebugMsg("Encerrando thread de envios de acks");
		}	

	}

	private static Thread tAck;

	/**
		Retorna o gerador de n�meros aleat�rios
	*/
	public static int getNumeroAleatorio(){
		return random.nextInt();

	}

	public synchronized void setRecebeuPermissaoJogada(boolean b){
		RECEBEU_PERMISSAO_JOGADA = b;
	}

	public synchronized boolean getRecebeuPermissaoJogada(){
		return RECEBEU_PERMISSAO_JOGADA ;
	}

	/**
		Construtor
	*/

	public ControleJogo(boolean s, Dispositivo d, Display display, CartasMidlet cartasMidlet, String ip, int porta){

		
		super(s);
		
		dispositivo = d;
		this.display = display;
		JOGANDO = true;
		APRESENTACAO = true;
		TELASELECAO = false;
		try{
		
		         imgLogo = new Sprite(Image.createImage("/logo.png"));
			 imgLogoEscrita = Image.createImage("/logoEscr.png");
			 imgLogo = baralhoAtual.cartasBaralho[0][0];
		}catch(Exception e){
			System.out.println("ERRO ao carregar imagem (ControleJogo): " + e);
			
		}


		posicaoCartaX = -imgLogo.getWidth();

		formularioAjuda = new frmAjuda("Ajuda", this, display);
		visualizadorCartas = new frmVisualizadorCartas("Visualizador de Cartas",this, display, baralhoAtual, d);
		frmopcoes = new frmOpcoes("Op��es", this);		
		
		display.setCurrent(this);
		new Thread(this).start();
		this.IP_SERVIDOR = ip;
		this.PORTA_SERVIDOR = "" + porta;
		//atualizarTela();
	
		this.cartasMidlet = cartasMidlet;

		//Adicionando comandos
		addCommand(cmdIniciarJogo);
		addCommand(cmdIniciarJogoOnLine);
		addCommand(cmdVisualizadorCartas);
		addCommand(cmdOpcoes);
		addCommand(cmdAjuda);
		addCommand(cmdSair);
		setCommandListener(this);


		
		
	}

	/**
		Loop do jogo (descontinuado)
	*/

	public void mainLoop(){
	
	}

	/**
		Desenha a tela de apresenta��o
	*/

	public void atualizarTela(){
		Graphics g = getGraphics();
		g.setColor(0, 190, 0);
		g.fillRect(0,0,dispositivo.getLarguraTela(), dispositivo.getAlturaTela());
		int j = 220;
		x_src = 5;		
		if(APRESENTACAO){//Exibindo apresenta��o do jogo
			//Exibindo logo
			for(int i = -imgLogo.getWidth(), i2 = dispositivo.getLarguraTela(); i < dispositivo.getLarguraTela() + 10; i+=5, i2-=5){
				imgLogo.setPosition(i, (dispositivo.getAlturaTela()/2) - imgLogo.getHeight());
				g.fillRect(0 ,0 ,dispositivo.getLarguraTela(), dispositivo.getAlturaTela());
				if(j - 2 >= 190) j-=2;
				g.setColor(0, j, 0);
				imgLogo.paint(g);
				
				if(imgLogo.getX() > dispositivo.getCentroX() - imgLogoEscrita.getWidth() && imgLogo.getX() <= dispositivo.getCentroX()){
				   //g.drawImage(imgLogoEscrita, dispositivo.getCentroX() - imgLogoEscrita.getWidth(), (( imgLogo.getHeight() + imgLogo.getY() - imgLogoEscrita.getHeight() - 12)),0);

				   //System.out.println("" + (imgLogo.getX() - (dispositivo.getCentroX() - imgLogoEscrita.getWidth())));
				  
				   g.drawRegion(imgLogoEscrita, 0, 0, 
						imgLogo.getX() - (dispositivo.getCentroX() - imgLogoEscrita.getWidth()), 
						imgLogoEscrita.getHeight(), 0, 
						dispositivo.getCentroX() - imgLogoEscrita.getWidth(), 
						(( imgLogo.getHeight() + imgLogo.getY() - imgLogoEscrita.getHeight() - 12)), 0); 
 				

				}else{
					       
					if(imgLogo.getX() > dispositivo.getCentroX()){
					       imgLogo.setPosition(i, (dispositivo.getAlturaTela()/2) - imgLogo.getHeight());
					       g.drawImage(imgLogoEscrita, dispositivo.getCentroX() - imgLogoEscrita.getWidth(), (( imgLogo.getHeight() + imgLogo.getY() - imgLogoEscrita.getHeight() - 12)),0);
					}
				}
				
				imgLogo.setPosition(i2 , (dispositivo.getAlturaTela()/2));
			
				
				if(imgLogo.getX() + imgLogo.getWidth() < dispositivo.getCentroX() -10 + imgLogoEscrita.getWidth() && imgLogo.getX() + imgLogo.getWidth() >= dispositivo.getCentroX() -10){
					g.drawRegion(imgLogoEscrita, //Image
						     imgLogoEscrita.getWidth() - x_src, //x_src
						     0, //y_src 
						     x_src, //width
						     imgLogoEscrita.getHeight(), //height
						     0, //transform
						     dispositivo.getCentroX() - 10 + imgLogoEscrita.getWidth() - x_src, //x_dest							 
						     (( imgLogo.getHeight() + imgLogo.getY() - imgLogoEscrita.getHeight() - 12)), //y_dest 
						     0); //anchor
					x_src+=5;

				
				}else{
					if(imgLogo.getX() < dispositivo.getCentroX() - 10){
					       g.drawImage(imgLogoEscrita, dispositivo.getCentroX() - 10, (( imgLogo.getHeight() + imgLogo.getY() - imgLogoEscrita.getHeight() - 12)),0);
					}

				}

				imgLogo.paint(g);

				flushGraphics();
				try{
					Thread.sleep(DELAY - 39);
				}catch(Exception e){}
			}
			APRESENTACAO = false;
			TELASELECAO = true;
			
			
		}else{
			if(TELASELECAO){
				g.fillRect(0 ,0 ,dispositivo.getLarguraTela(), dispositivo.getAlturaTela());
				//atualizando posi��o da carta que fica passando
				imgLogo.setPosition(posicaoCartaX, posicaoCartaY);
				posicaoCartaX += 10 * direcao;
				imgLogo.paint(g);
				if( (posicaoCartaX >= dispositivo.getLarguraTela()) || (posicaoCartaX <= -imgLogo.getWidth())){
					posicaoCartaY += imgLogo.getHeight();
					//posicaoCartaX = -imgLogo.getWidth();
					direcao = (byte)-direcao;
					naipeAtual++;
					if(naipeAtual == 4) naipeAtual = 0;
					cartaAtual++;
					if(cartaAtual == 14) cartaAtual = 0;	
					try{
						imgLogo = baralhoAtual.cartasBaralho[naipeAtual][cartaAtual];
					}catch(Exception e){

						System.out.println("ControleJogo>>TELASELECAO>>ERRO>>Imagem n�o pode ser nula");
					}
				}
			
				if(posicaoCartaY >= dispositivo.getAlturaTela()){
					posicaoCartaY = 0;

				}
				imgLogo.setPosition(0, (dispositivo.getAlturaTela()/2) - imgLogo.getHeight());
				g.drawImage(imgLogoEscrita, dispositivo.getCentroX() - imgLogoEscrita.getWidth(), (( imgLogo.getHeight() + imgLogo.getY() - imgLogoEscrita.getHeight() - 12)),0);
        			imgLogo.setPosition(0 , (dispositivo.getAlturaTela()/2) );
				g.drawImage(imgLogoEscrita, dispositivo.getCentroX() -10, (( imgLogo.getHeight() + imgLogo.getY() - imgLogoEscrita.getHeight() - 12)),0);
			        
			}		

		}

		System.gc();
		flushGraphics();
		
	}

	/**
		Loop de atualiza��o da tela de apresenta��o do jogo
	*/
	public void run(){
		while(JOGANDO){
			
			atualizarTela();
	
			try{
				Thread.sleep(DELAY);
			}catch(Exception e){
				System.out.println("ERRO: " + e);
			}

			
		}

	}

	/**
		Para a execu��o do jogo
	
	*/
	public void parar(){
		exibirDebugMsg("ControleJogo>>parar>>Parando a execu��o do jogo");
		JOGANDO = false;
		JOGO_EM_ANDAMENTO = false;
		fecharConexao();	 
		System.gc();
	}


	/**
		Listener de eventos
	*/
	public void commandAction(Command command, Displayable displayable){
		exibirDebugMsg("ControleJogo>>commandAction");
		//Saindo da aplica��o
		if(command == cmdSair){
			fecharConexao();	
			cartasMidlet.destroyApp(true);
		}else if(command == cmdAjuda){
			//Exibindo formul�rio de ajuda
			exibirDebugMsg("ControleJogo>>CommandAction>>Exibindo ajuda");
			APRESENTACAO = false;
			TELASELECAO = false;
			display.setCurrent(formularioAjuda);
		}else if(command == cmdOpcoes){
			//Exibindo formul�rio de op��es
			exibirDebugMsg("ControleJogo>>CommandAction>>Exibindo op��es");
			APRESENTACAO = false;
			TELASELECAO = false;
			frmopcoes.setOpenMauMau(OPEN_MAU_MAU);
			frmopcoes.setExibirInformacoesCarta(EXIBIR_INFORMACAO_CARTA);
			frmopcoes.setIPServidor(IP_SERVIDOR);
			frmopcoes.setPortaServidor(PORTA_SERVIDOR);

			display.setCurrent(frmopcoes);
		}else if(command == cmdVisualizadorCartas){
			//Exibindo formul�rio de visualiza��o de cartas
			exibirDebugMsg("ControleJogo>>CommandAction>>Visualizador de Cartas");
			APRESENTACAO = false;
			TELASELECAO = false;
			display.setCurrent(visualizadorCartas);
		}else if(command == cmdIniciarJogo){
			exibirDebugMsg("ControleJogo>>Inicializando novo jogo");
			incrementaJogoAtual();
			NUMERO_JOGADORES = 4;
			ONLINE = false;
			PENALIDADE_APLICADA = false;
			APRESENTACAO = false;
			TELASELECAO = false;
			SENTIDO_JOGO = SENTIDO_NORMAL;
			jogadorAtual = jogadorExibido = 0;
			ANTERIOR_PULADO = false;
			QUANT_COMPRA = 0;
			JOGO_EM_ANDAMENTO = true;
			resetarJogadores();
			Graphics g = getGraphics();
			g.setColor(0, 190, 0);
			g.fillRect(0,0,dispositivo.getLarguraTela(), dispositivo.getAlturaTela());resetarJogadores();
			flushGraphics();
			baralhoAtual.resetar();
			baralhoAtual.embaralhar();
			baralhoAtual.distribuirCartas(jogadores);
			
			/*
			//Teste da jogada Coringa
			jogadores[0].adicionarCarta(Baralho.cartasBaralho[Carta.OUROS][Carta.CORINGA]);

			//Teste da jogada Valete(*)	
			baralhoAtual.adicionarCarta(Baralho.cartasBaralho[Carta.OUROS][Carta.DOIS]);
			jogadores[0].adicionarCarta(Baralho.cartasBaralho[Carta.OUROS][Carta.VALETE]);
			*/
			//jogadores[0].adicionarCarta(Baralho.cartasBaralho[Carta.OUROS][Carta.VALETE]);

			if(baralhoAtual.cartaAtual().getValor() == Carta.SETE){
				setQuantidadeCompra((byte)3);
				((JogadorHumano)jogadores[0]).setComandoCompra((byte)0);
			}else{
				if(baralhoAtual.cartaAtual().getValor() == Carta.DEZ &&
				   baralhoAtual.cartaAtual().getNaipe() == Carta.PAUS){
					setQuantidadeCompra((byte)5);
					((JogadorHumano)jogadores[0]).setComandoCompra((byte)0);
				}
			}
			//((JogadorHumano)jogadores[0]).simularFimDeJogo(); //--> Apenas para testar fim de jogo
			display.setCurrent(jogadores[0]);

			jogadorAtual = 0;
			//loopback();
			
			
		}else if(command == cmdIniciarJogoOnLine){
			exibirDebugMsg("ControleJogo>>Inicializando jogo on-line");
			synchronized(this){
				ONLINE = true;
				primeiroStatusRecebido = false;
				NUMERO_JOGADORES = 4;
			}
			//exibirDebugMsg("commandAction>>Jogo on-line>>" + retornarMsgServidor(getHostServidor() + "listarSalasDisponiveis.jsp", "p=1", true));
			CartasMidlet.getDisplay().setCurrent(frmespera);
			Thread t = new Thread(){
					public void run(){			
						String strResultado = retornarMsgServidor(getHostServidor(), "acao=LISTA_SALAS",false, ControleJogo.this);			
						if(strResultado.startsWith("ERRO")){
							ONLINE=false;
							exibirMsg("N�o foi poss�vel estabelecer a conex�o com o servidor. Verifique o endere�o do servidor e a porta (atrav�s do menu op��es). Se estiverem corretos, tente novamente mais tarde.");
							exibirDebugMsg("ControleJogo>>commandAction>>ERRO: " + strResultado);
						}else{
							if(frmlistasalas == null)
								frmlistasalas = new frmListaSalas("Lista de salas", ControleJogo.this, strResultado, ControleJogo.this);
							else							
								frmlistasalas.reset(strResultado);
					
							CartasMidlet.getDisplay().setCurrent(frmlistasalas);
						}
					}
			};
			
			t.start();
			
		}

	}

	/**
		Inicializa o estado do jogo para come�ar um novo jogo on-line
	*/
	public synchronized void comecarJogoOnLine(boolean enviar){
		exibirDebugMsg("########################################################ControleJogo>>Inicializando novo jogo on-line>>JOGADOR:" + nomeJogador);
		primeiroStatusRecebido = true;
		ONLINE = true;
		APRESENTACAO = false;
		TELASELECAO = false;
		SENTIDO_JOGO = SENTIDO_NORMAL;
		jogadorAtual = jogadorExibido = 0;
		ANTERIOR_PULADO = false;
		QUANT_COMPRA = 0;
		JOGO_EM_ANDAMENTO = true;
		PENALIDADE_APLICADA = false;
		Graphics g = getGraphics();
		g.setColor(0, 190, 0);
		g.fillRect(0,0,dispositivo.getLarguraTela(), dispositivo.getAlturaTela());
		flushGraphics();
		int i = 0;
		exibirDebugMsg("ControleJogo>>Inicializando novo jogo on-line>>Contando jogadores");
		
		for(i = 0; i < NUMERO_JOGADORES; i++){
			if(nomeJogadores[i] == null){
				break;
			}else{
				exibirDebugMsg("ControleJogo>>comecarJogoOnLine>>NOMEJOGADORES[" + i + "] --> " + nomeJogadores[i]);
			}
		}	
		NUMERO_JOGADORES = i;	
		exibirDebugMsg("ControleJogo>>comecarJogoOnLine>>N� de Jogadores: " + NUMERO_JOGADORES);
		resetarJogadores();
		
		synchronized(baralhoAtual){
			baralhoAtual.resetar();
			baralhoAtual.embaralhar();
			baralhoAtual.distribuirCartas(jogadores);
		}
		

		if(baralhoAtual.cartaAtual().getValor() == Carta.SETE){
			setQuantidadeCompra((byte)3);
			if(jogadores[0] instanceof JogadorHumano)
				((JogadorHumano)jogadores[0]).setComandoCompra((byte)0);
		}else{
			if(baralhoAtual.cartaAtual().getValor() == Carta.DEZ &&
			   baralhoAtual.cartaAtual().getNaipe() == Carta.PAUS){
				setQuantidadeCompra((byte)5);
				if(jogadores[0] instanceof JogadorHumano)
					((JogadorHumano)jogadores[0]).setComandoCompra((byte)0);
			}
		}
		
		
		/*jogadores[1].zerar(); //-->Para testar o fim do jogo
		jogadores[1].adicionarCarta(Baralho.cartasBaralho[Carta.PAUS][Carta.CORINGA]);
		
		baralhoAtual.zerar(); //-->Para testar o fim do jogo
		baralhoAtual.adicionarCarta(Baralho.cartasBaralho[Carta.PAUS][Carta.CORINGA]);
		


		
		//Teste de jogada com valetes		
		jogadores[0].adicionarCarta(Baralho.cartasBaralho[Carta.OUROS][Carta.VALETE]);
		jogadores[0].adicionarCarta(Baralho.cartasBaralho[Carta.PAUS][Carta.VALETE]);
		jogadores[0].adicionarCarta(Baralho.cartasBaralho[Carta.COPAS][Carta.VALETE]);
		jogadores[0].adicionarCarta(Baralho.cartasBaralho[Carta.ESPADAS][Carta.VALETE]);
		*/

		if(enviar){
			
			if(frm == null){
				frm = new frmEspera("Aguarde, inicializando jogo on-line");
			}else{			
				frm.reset("Aguarde, inicializando jogo on-line");
				frm.removeCommand(cmdCancelar);
				frm.removeCommand(cmdComecarJogo);
			}
			frm.append(new StringItem("Aguarde: ", "Aguardando resposta do servidor"));
				
			display.setCurrent(frm);
		
			enviarMsgServidor(
				montarStatusJogo(
					new Mensagem("Iniciando jogo. O jogador " + jogadores[0].getNome() + " far� a primeira jogada.", AlertType.INFO, jogadores[jogadorAtual])
					, false));
		
		//}else{

		}
		System.gc();
	}

	/**
		Reseta o estado dos jogadores
	*/
	public void resetarJogadores(){
		exibirDebugMsg("ControleJogo>>resetarJogadores()");
		jogadores = new Jogador[NUMERO_JOGADORES];
		
		if(!ONLINE){
			jogadores[0] = new JogadorHumano("Player 1", dispositivo, baralhoAtual, this);
			jogadores[1] = new JogadorComputador("Player 2",  baralhoAtual, this);
			jogadores[2] = new JogadorComputador("Player 3",  baralhoAtual, this);
			jogadores[3] = new JogadorComputador("Player 4",  baralhoAtual, this);
		}else{
			for(int i = 0; i < NUMERO_JOGADORES; i++){
			    if(i == indiceJogadorRede){	
				jogadores[i] = new JogadorHumano(nomeJogador.trim().toUpperCase(), dispositivo, baralhoAtual, this);
			    }else{
				jogadores[i] = new JogadorComputador(
					nomeJogadores[i].trim().toUpperCase().substring(8, nomeJogadores[i].length()).trim()
					, baralhoAtual, this);

			    }
			}	

		}
		System.gc();  
	}

	public void setApresentacao(boolean b){
			APRESENTACAO = b;
	}

	public void setTelaSelecao(boolean b){
			TELASELECAO = b;
			posicaoCartaX = -imgLogo.getWidth();
			posicaoCartaY = 0;
	}


	

	/**
	  Rotina que verifica qual � o pr�ximo jogador e modfica o �ndice
	  do jogador atual para o pr�ximo
	*/
	public int proximoJogador(){
			exibirDebugMsg("ControleJogo>>Exibindo pr�ximo jogador");
			jogadorAtual += SENTIDO_JOGO;
			if(jogadorAtual < 0) jogadorAtual = (byte)(NUMERO_JOGADORES - 1);
			else if(jogadorAtual > (NUMERO_JOGADORES - 1)) jogadorAtual = 0;
			jogadorExibido = jogadorAtual;
			return jogadorAtual;
	}

	/**
		Rotina que retorna o �ndice do pr�ximo jogador, mas n�o modifica o �ndice do jogador atual
	*/
	public int getIndiceProximoJogador(){
			exibirDebugMsg("ControleJogo>>getIndiceProximoJogador()>>Retornando o �ndice do pr�ximo jogador");
			int proximoJogador = jogadorAtual + SENTIDO_JOGO;
			if(proximoJogador < 0) proximoJogador = (byte)(NUMERO_JOGADORES - 1);
			else if(proximoJogador > (NUMERO_JOGADORES - 1)) proximoJogador = 0;
			return proximoJogador;
	}


	/**
		Rotina que retorna o �ndice do jogador anterior
	*/
	public int getIndiceJogadorAnterior(){
			exibirDebugMsg("ControleJogo>>getIndiceJogadorAnterior()>>Retornando o �ndice do jogador anterior");
			int jogadorAnterior = jogadorAtual - SENTIDO_JOGO;
			if(jogadorAnterior < 0) jogadorAnterior = (byte)(NUMERO_JOGADORES - 1);
			else if(jogadorAnterior > (NUMERO_JOGADORES - 1)) jogadorAnterior = 0;
			return jogadorAnterior;
	}


	/**
		Muda o jogador que est� sendo exibido
	*/
	public void exibirProximoJogador(){
			jogadorExibido++;
			if(jogadorExibido == NUMERO_JOGADORES) jogadorExibido = 0;
			exibirDebugMsg("ContoleJogo>>exibirProximoJogador>>Exibindo cartas dojogador:" + jogadores[jogadorExibido].getNome());
			CartasMidlet.getDisplay().setCurrent(jogadores[jogadorExibido]);
	}

	/**
		Retorna o �ndice do jogador atual
	*/
	public byte getJogadorAtual(){
		return jogadorAtual;
	}

	/**
		Retorna o jogador que est� sendo exibido
	*/
	public byte getJogadorExibido(){
		return jogadorExibido;
	}

	/**
		Seta Open MauMau
	*/	
	public static void setOpenMauMau(boolean b){
		OPEN_MAU_MAU = b;
	}

	/**
		Retorna o valor de OpenMauMau
	*/
	public static boolean getOpenMauMau(){
		return OPEN_MAU_MAU;
	}
	
	/**
		Exibe informa��o de debug (apenas se o modo debug estiver acionado)
	*/
	public static void exibirDebugMsg(String msg){
		if(DEBUG_MODE)
			System.out.println(msg);
	}






	/**
		Verifica se a jogada que est� sendo feita � v�lida ou n�o
	*/

	public Mensagem verificarJogada(Carta carta, JogadorHumano jogador){
		Carta cartaBaralho = null, cartaJogador = carta;
		exibirDebugMsg("ControleJogo>>verificarJogada(carta,jogador)>>INICIANDO VERIFICA��O DE JOGADA");
		
		Mensagem mensagem = null;
		try{		
			cartaBaralho = baralhoAtual.cartaAtual();

			exibirDebugMsg("ControleJogo>>verificarJogada(carta,jogador)>>CARTA DO BARALHO: " + cartaBaralho.toString());
			exibirDebugMsg("ControleJogo>>verificarJogada(carta,jogador)>>CARTA JOGADA: " + cartaJogador.toString());
				
			/*******************************************************CARTAS QUE N�O DEPENDEM DA CARTA ATUAL DO BARALHO*************************************************************************************/
			/*Jogada: Coringa(*) --> Pode ser colocado sobre qualquer carta e anula o efeito de qualquer carta especial*/
			exibirDebugMsg("ControleJogo>>verificarJogada(C*)>>Verificando se carta jogada � um coringa");
			
			if(cartaJogador.getValor() == Carta.CORINGA){
				exibirDebugMsg("ControleJogo>>verificarJogada(C*)>>Carta jogada � um coringa");
			
				setPenalidadeAplicada(false);
				setQuantidadeCompra((byte)0);
				String msgComplemento = "";
				if(cartaBaralho.getValor() == Carta.SETE)
					msgComplemento = "O coringa anulou o efeito do sete anterior.";
				else if(cartaBaralho.getValor() == Carta.NOVE)
					msgComplemento = "O coringa anulou o efeito do nove anterior.";
				else if(cartaBaralho.getValor() == Carta.DEZ && cartaBaralho.getNaipe() == Carta.PAUS)
					msgComplemento = "O coringa anulou o efeito do 10 de paus anterior.";
				baralhoAtual.adicionarCarta(cartaJogador);
				mensagem = new Mensagem("O jogador " + jogador.getNome() + " jogou um coringa, que pode ser jogado sobre qualquer carta e anula o efeito de qualquer carta especial. " + msgComplemento + " O pr�ximo jogador ser�: " + jogadores[getIndiceProximoJogador()].getNome() + ". Movendo para o pr�ximo jogador.", 
					AlertType.INFO, jogadores[proximoJogador()]);
				
			
			}else{
				/*Jogada: Valete(*): Pode ser colocada sobre qualquer carta e anula o efeito de cartas especiais. Ao jogar um valete,
			  	o jogador escolhe qual ser� o naipe da pr�xima carta que dever� ser jogada*/
			  	exibirDebugMsg("ControleJogo>>verificarJogada(Valete*)>>VERIFICANDO SE CARTA JOGADA � UM VALETE");
				if(cartaJogador.getValor() == Carta.VALETE){
					exibirDebugMsg("ControleJogo>>verificarJogada(Valete*)>>Carta jogada � um valete");
					setPenalidadeAplicada(false);
					setQuantidadeCompra((byte)0);
					String msgComplemento = "";
					if(cartaBaralho.getValor() == Carta.SETE)
						msgComplemento = "O valete anulou o efeito do sete anterior.";
					else if(cartaBaralho.getValor() == Carta.NOVE)
						msgComplemento = "O valete anulou o efeito do nove anterior.";
					else if(cartaBaralho.getValor() == Carta.DEZ && cartaBaralho.getNaipe() == Carta.PAUS)
						msgComplemento = "O valete anulou o efeito do 10 de paus anterior.";
					baralhoAtual.adicionarCarta(cartaJogador);
					
					mensagem = new Mensagem("O jogador " + jogador.getNome() + " jogou um valete, que pode ser jogada sobre qualquer carta e anula o efeito de qualquer carta especial. " + msgComplemento, 
					AlertType.INFO, jogadores[proximoJogador()]);
				
					frmescolhanaipe = new frmEscolhaNaipe(jogador, this, mensagem);
					CartasMidlet.getDisplay().setCurrent(frmescolhanaipe);

				}else{
			/*******************************************************FIM DE CARTAS QUE N�O DEPENDEM DA CARTA ATUAL DO BARALHO*************************************************************************************/
			/*******************************************************DEFESA DE CARTAS*************************************************************************************/
					exibirDebugMsg("ControleJogo>>verificarJogada(7*)>>VERIFICANDO SE CARTA DO BARALHO � UM SETE");
					if(cartaBaralho.getValor() == Carta.SETE && !getPenalidadeAplicada()){
						exibirDebugMsg("ControleJogo>>verificarJogada(7*)>>CARTA DO BARALHO � UM SETE");
						if(cartaJogador.getValor() == Carta.SETE){
							setPenalidadeAplicada(false);
					   		exibirDebugMsg("ControleJogo>>VerificarJogada(7)>>DOBRANDO A QUANTIDADE DE COMPRA");
							baralhoAtual.adicionarCarta(carta);
							setQuantidadeCompra((byte)(getQuantidadeCompra() + 3));
							mensagem = new Mensagem(
							"O jogador " + jogador.getNome() + " jogou um sete. O pr�ximo jogador dever� comprar " + getQuantidadeCompra() + " cartas, jogar outro 7, jogar um 2 do mesmo naipe do 7 atual, um coringa ou um valete. O pr�ximo jogador ser�: " + jogadores[getIndiceProximoJogador()].getNome() + ". Movendo para o pr�ximo jogador.", 
								AlertType.INFO, jogadores[proximoJogador()]);
						}else{
							if(cartaJogador.getNaipe() == cartaBaralho.getNaipe() && cartaJogador.getValor() == Carta.DOIS){
								baralhoAtual.adicionarCarta(carta);
								setQuantidadeCompra((byte)0);
								mensagem = new Mensagem(
								"O jogador " + jogador.getNome() + " jogou um 2, que anulou o efeito do 7 anterior. O pr�ximo jogador ser�: " + jogadores[getIndiceProximoJogador()].getNome() + ". Movendo para o pr�ximo jogador.", 
						        		AlertType.INFO, jogadores[proximoJogador()]);
							}else{
								exibirDebugMsg("ControleJogo>>verificarJogada>>Jogada inv�lida detectada[7]");
								mensagem = new Mensagem("Jogada inv�lida!", 
									AlertType.ERROR, jogadores[jogadorAtual]);
							}
						}
					

					}else{
						exibirDebugMsg("ControleJogo>>verificarJogada(9*)>>VERIFICANDO SE CARTA DO BARALHO � UM NOVE");
						if(cartaBaralho.getValor() == Carta.NOVE && !getPenalidadeAplicada()){
							exibirDebugMsg("ControleJogo>>verificarJogada(9*)>>CARTA DO BARALHO � UM NOVE");
						
							if(cartaJogador.getValor() == Carta.DOIS && cartaJogador.getNaipe() == cartaBaralho.getNaipe()){
								exibirDebugMsg("ControleJogo>>verificarJogada(9*)>>DOIS. ANULADO O EFEITO DO NOVE ANTERIOR...");						
								baralhoAtual.adicionarCarta(carta);    
								mensagem = new Mensagem("O jogador " + jogador.getNome() + " jogou um dois do mesmo naipe do nove que estava no baralho. Com isto, o efeito do nove foi cancelado. O pr�ximo jogador ser�: " + jogadores[getIndiceProximoJogador()].getNome() + ". Movendo para o pr�ximo jogador.", 
								AlertType.INFO, jogadores[proximoJogador()]);
					        		setQuantidadeCompra((byte)0);
							}else{
								exibirDebugMsg("ControleJogo>>verificarJogada(9*)>>CARTA INV�LIDA");
								mensagem = new Mensagem("Jogada inv�lida! Voc� deve jogar um dois do mesmo naipe que o nove que est� no baralho, um coringa, um valete ou comprar uma carta.", 
									AlertType.ERROR, jogadores[jogadorAtual]);
							

							}
						 }else{
							exibirDebugMsg("ControleJogo>>VerificarJogada(10p)>>Verificando se a carta do baralho � um 10 de paus");
								
							if(cartaBaralho.getNaipe() == Carta.PAUS && cartaBaralho.getValor() == Carta.DEZ && !getPenalidadeAplicada()){
								exibirDebugMsg("ControleJogo>>VerificarJogada(10p)>>Carta do baralho � um 10 de paus>>Verificando se a carta jogada � um 2 de paus");
								setQuantidadeCompra((byte)5);
								if(cartaJogador.getValor() == Carta.DOIS && cartaJogador.getNaipe() == Carta.PAUS){
									exibirDebugMsg("ControleJogo>>VerificarJogada(10p)>>Carta do baralho � um 10 de paus>>2 DE PAUS JOGADO");
									baralhoAtual.adicionarCarta(carta);
									mensagem = new Mensagem("O jogador " + jogador.getNome() + " jogou um dois de paus. Com isto, o efeito do 10 de paus foi cancelado. O pr�ximo jogador ser�: " + jogadores[getIndiceProximoJogador()].getNome() + ". Movendo para o pr�ximo jogador.", 
										AlertType.INFO, jogadores[proximoJogador()]);
									setQuantidadeCompra((byte)0);
								}else{
									exibirDebugMsg("ControleJogo>>VerificarJogada(10p)>>Carta do baralho � um 10 de paus>>Carta jogada � inv�lida!");
									mensagem = new Mensagem("Jogada inv�lida. Voc� deve jogar um dois de paus, um coringa, um valete ou comprar 5 cartas.", 
										AlertType.ERROR, jogadores[jogadorAtual]);
							

								}

							}else{
						/*******************************************************FIM DA DEFESA DE CARTAS*************************************************************************************/
						/*******************************************************CARTAS QUE SATISFAZEM A CONDI��O B�SICA*************************************************************************************/
								exibirDebugMsg("ControleJogo>>verificarJogada>>VERIFICANDO CONDI��O B�SICA");
								
								if( ( (cartaBaralho.getValor() != Carta.VALETE || (cartaBaralho.getValor() == Carta.VALETE && getPenalidadeAplicada()) ) && (cartaBaralho.getNaipe() == cartaJogador.getNaipe() || cartaBaralho.getValor() == cartaJogador.getValor()))
								    || (cartaBaralho.getValor() == Carta.VALETE && cartaJogador.getNaipe() == NAIPE_ESCOLHIDO && !getPenalidadeAplicada()) ){
									
									/*Regra: A* --> Indica que o jogador deve jogar novamente*/
									exibirDebugMsg("ControleJogo>>verificarJogada>>VERIFICANDO SE A CARTA DO JOGADOR � UM AS");
			
									if(cartaJogador.getValor() == Carta.AS){
										exibirDebugMsg("ControleJogo>>verificarJogada>>CARTA DO JOGADOR � UM AS");
										baralhoAtual.adicionarCarta(carta);    
										mensagem = new Mensagem("O jogador " + jogador.getNome() + " jogou um AS e tem direito a mais uma jogada!", 
										AlertType.INFO, jogadores[jogadorAtual]);
									}else{


										/*VerificandoRegra: 4 de espadas--> Todo mundo na mesa (menos quem jogou) compra uma carta>>Indefens�vel*/
										exibirDebugMsg("Baralho>>VerificarJogada>>Verificando Regra: 4 de espadas--> Todo mundo na mesa compra uma carta>>Indefens�vel");
										if(cartaJogador.getNaipe() == Carta.ESPADAS && cartaJogador.getValor() == Carta.QUATRO){
											exibirDebugMsg("ControleJogo>>verificarJogada(Carta)>>Jogador jogou um 4 de Espadas>>Todos os outros \ndevem comprar uma carta");
											baralhoAtual.adicionarCarta(carta);    
											for(int i = 0 ; i < NUMERO_JOGADORES; i++){
												if(!(jogadores[i].equals(jogador))){
													try{
														jogadores[i].adicionarCarta(baralhoAtual.comprarCarta());
													}catch(EmptyStackException e){
														exibirDebugMsg("Baralho>>verificarJogada(4 espadas)>>Baralho Vazio!");
														mensagem = elegerVencedor();
														//return mensagem;
													}
												}	
											}	
											mensagem = new Mensagem("O jogador " + jogador.getNome() + " jogou um 4 de espadas e todos ou outros jogadores compraram uma carta. O pr�ximo jogador ser�: " + jogadores[getIndiceProximoJogador()].getNome() + ". Movendo para o pr�ximo jogador.", 
												AlertType.INFO, jogadores[proximoJogador()]);
										}else{
											if(cartaJogador.getValor() == Carta.CINCO){
												exibirDebugMsg("Baralho>>verificarJogada(5*)>>INVERTENDO O SENTIDO DO JOGO");
												baralhoAtual.adicionarCarta(carta);
												inverterSentidoJogo();
												mensagem = new Mensagem("O jogador " + jogador.getNome() + " jogou um 5 e inverteu o sentido do jogo. O pr�ximo jogador ser�: " + getJogador(getIndiceProximoJogador()).getNome() + ". Movendo para o pr�ximo jogador.", 
													AlertType.INFO, jogadores[proximoJogador()]);
			    								}else{
												/*Regra (Q(DAMA)*): pula o pr�ximo jogador*/
												exibirDebugMsg("Baralho>>verificarJogada(Q*(DAMA))>>VERIFICANDO SE A CARTA JOGADA � UMA DAMA");
												
												if(cartaJogador.getValor() == Carta.DAMA){
													exibirDebugMsg("Baralho>>verificarJogada(Q*(DAMA))>>CARTA JOGADA � UMA DAMA>>PULANDO PR�XIMO JOGADOR.");
													String nomeJogadorPulado = getJogador(getIndiceProximoJogador()).getNome();
													proximoJogador();
													mensagem = new Mensagem("O jogador " + jogador.getNome() + " jogou uma dama e, como consequ�ncia, o jogador " + nomeJogadorPulado + " foi pulado. O pr�ximo jogador ser�: " + getJogador(getIndiceProximoJogador()).getNome() + ". Movendo para o pr�ximo jogador.", 
														AlertType.INFO, jogadores[proximoJogador()]);
			    										baralhoAtual.adicionarCarta(carta);
														

												}else{

													/*Verificando regra 8*: Se a que est� sendo jogada for um 8, o jogador receber� uma carta do pr�ximo
														jogador e poder� jogar novamente, a n�o ser que o pr�ximo jogador tenha apenas uma carta.
								  						Neste caso, o 8 tem o efeito de uma carta normal.
													*/
													exibirDebugMsg("ControleJogo>>VerificarJogada(8*)>>Verificando se carta jogada � um 8");
													if(cartaJogador.getValor() == Carta.OITO){
														baralhoAtual.adicionarCarta(carta);
														exibirDebugMsg("ControleJogo>>VerificarJogada(8*)>>Carta jogada � um 8");
														if(jogadores[getIndiceProximoJogador()].numeroCartas() > 1){
															exibirDebugMsg("ControleJogo>>VerificarJogada(8*)>>Comprando uma carta do pr�ximo jogador");
															jogador.adicionarCarta(jogadores[getIndiceProximoJogador()].removerCarta());
															mensagem = new Mensagem("O jogador " + jogador.getNome() + " jogou um 8. Com esta jogada, ele est� recebendo uma carta do pr�ximo jogador e pode jogar novamente! Realizando nova jogada.", 
																AlertType.INFO, jogadores[jogadorAtual]);
						
														}else{
															exibirDebugMsg("ControleJogo>>VerificarJogada(8*)>>N�o � poss�vel comprar uma carta do pr�ximo jogador, pois o mesmo possui apenas uma carta! Movendo para o pr�ximo jogador.");
						
															mensagem = new Mensagem("O jogador " + jogador.getNome() + " jogou um 8, por�m n�o poder� receber uma carta do pr�ximo jogador, pois o mesmo possui apenas uma carta. O pr�ximo jogador ser�: " + getJogador(getIndiceProximoJogador()).getNome() + ". Movendo para o pr�ximo jogador.", 
															AlertType.INFO, jogadores[proximoJogador()]);
						
														}
													}else{

															

														exibirDebugMsg("ControleJogo>>VerificarJogada(R*)>>VERIFICANDO SE CARTA JOGADA � UM REI");
														
														/*Verificando jogada: R(*) --> Faz o jogador anterior comprar uma carta*/
														if(cartaJogador.getValor() == Carta.REI){
															exibirDebugMsg("ControleJogo>>VerificarJogada(R*)>>VERIFICANDO SE CARTA JOGADA � UM REI>>CARTA JOGADA � UM REI");
															baralhoAtual.adicionarCarta(carta);
															jogadores[getIndiceJogadorAnterior()].adicionarCarta(baralhoAtual.comprarCarta());
															mensagem = new Mensagem(
																"O jogador " + jogador.getNome() + " jogou um rei e fez o jogador anterior comprar uma carta. O pr�ximo jogador ser�: " + getJogador(getIndiceProximoJogador()).getNome() + ". Movendo para o pr�ximo jogador.", 
						        									AlertType.INFO, jogadores[proximoJogador()]);
														}else{					

															if(cartaJogador.getValor() == Carta.SETE){
																setPenalidadeAplicada(false);
																baralhoAtual.adicionarCarta(carta);
																setQuantidadeCompra((byte)(getQuantidadeCompra() + 3));
																mensagem = new Mensagem("O jogador " + jogador.getNome() + " jogou um sete. O pr�ximo jogador deve comprar " + getQuantidadeCompra() + " cartas, jogar outro 7, jogar um 2 do naipe atual, um coringa ou um valete. O pr�ximo jogador ser�: " + getJogador(getIndiceProximoJogador()).getNome() +". Movendo para o pr�ximo jogador.", 
																AlertType.INFO, jogadores[proximoJogador()]);
												
															}else{
																if(cartaJogador.getValor() == Carta.DEZ && cartaJogador.getNaipe() == Carta.PAUS){
																	setPenalidadeAplicada(false);
																	setQuantidadeCompra((byte)5);
																	baralhoAtual.adicionarCarta(carta);
																	mensagem = new Mensagem("O jogador " + jogador.getNome() + " jogou um 10 de paus. O pr�ximo jogador deve comprar " + getQuantidadeCompra() + " cartas, jogar um 2 de paus, um coringa ou um valete. O pr�ximo jogador ser�: " + getJogador(getIndiceProximoJogador()).getNome() + ". Movendo para o pr�ximo jogador.", 
																	AlertType.INFO, jogadores[proximoJogador()]);

																}else{

																	if(carta.getValor() == Carta.NOVE){
																		        setPenalidadeAplicada(false);
																			setQuantidadeCompra((byte)1);
																			baralhoAtual.adicionarCarta(carta);
																			mensagem = new Mensagem("O jogador " + jogador.getNome() + " jogou um 9. O pr�ximo jogador deve comprar " + getQuantidadeCompra() + " cartas, jogar um 2 do mesmo naipe do nove, um coringa ou um valete. O pr�ximo jogador ser�: " + getJogador(getIndiceProximoJogador()).getNome() + ". Movendo para o pr�ximo jogador.", 
																				AlertType.INFO, jogadores[proximoJogador()]);
																	}else{
																		baralhoAtual.adicionarCarta(carta);
																				
																		mensagem = new Mensagem("O jogador " + jogador.getNome() + " fez uma jogada legal, por�m n�o jogou nenhuma carta especial. O pr�ximo jogador ser�: " + getJogador(getIndiceProximoJogador()).getNome() + ". Movendo para o pr�ximo jogador.", 
																		AlertType.INFO, jogadores[proximoJogador()]);
																	}
																}
															}

																	



														}
														



													}						


												}				

											}
						

										}
								



									}//Fim do if AS

								/*******************************************************FIM DAS CARTAS QUE SATISFAZEM A CONDI��O B�SICA*************************************************************************************/

									
								}else{	
									exibirDebugMsg("ControleJogo>>verificarJogada>>Jogada inv�lida detectada");
									mensagem = new Mensagem("Jogada inv�lida! Voc� deve jogar uma carta do mesmo naipe ou do mesmo valor da carta que est� no baralho ou jogar uma das cartas especiais. Jogue novamente.", 
										AlertType.ERROR, jogadores[jogadorAtual]);
								}
						

							
							}
						 }


					}

				}
			} 





			if(mensagem.getType() != AlertType.ERROR && jogador.numeroCartas() == 0){
				mensagem = new Mensagem("Fim de jogo. O vencedor foi: " + jogador.getNome()
					, AlertType.INFO, this);
				mensagem.setTimeout(Jogador.ESPERA);
			
			}else{
		

				mensagem.setTimeout(Jogador.ESPERA);
		
				if(mensagem.getType() != AlertType.ERROR){

					if( jogador.getDisseMauMau() && jogador.numeroCartas() > 2){
						mensagem.setString(mensagem.getString() + " Voc� s� dever� dizer mau-mau quando possuir apenas uma carta. Voc� receber� tr�s cartas como penalidade por dizer mau-mau na hora errada."); 
						for(byte i = 0; i < 3; i++)
							jogador.adicionarCarta(baralhoAtual.comprarCarta());
					}else{
						if(jogador.numeroCartas() == 2 && !(jogador.getDisseMauMau())){

							mensagem.setString(mensagem.getString() + " Voc� possui uma carta e n�o disse mau-mau, portanto, como penalidade, voc� receber� tr�s cartas."); 
							for(byte i = 0; i < 3; i++)
								jogador.adicionarCarta(baralhoAtual.comprarCarta());

						} 

					}


				}
			}
		}catch(EmptyStackException e){
			//Se o baralho estiver vazio, elege o vencedor do jogo
			exibirDebugMsg("Baralho>>verificarJogada>>Baralho Vazio!");
			mensagem = elegerVencedor();
			
		}
		System.gc();
	
		return mensagem;
	}
	
	/**
		Elege o vencedor do jogo
	*/
	public Mensagem elegerVencedor(){
		exibirDebugMsg("Baralho>>elegerVencedor()>>elegendo vencedor do jogo");
		Mensagem mensagem = null;
		Jogador vencedor = jogadores[0];
		for(int i = 0; i < NUMERO_JOGADORES; i++){
			if(jogadores[i].numeroCartas() < vencedor.numeroCartas()){
					vencedor = jogadores[i];
			}
		}
		if(baralhoAtual.numeroCartas() == 0){
			mensagem = new Mensagem("O baralho ficou vazio! Neste caso, o vencedor � o jogador com menos cartas. O vencedor foi: " + vencedor.getNome()
						, AlertType.INFO, this);
		}else{
			mensagem = new Mensagem("Fim de jogo. O vencedor foi: " + vencedor.getNome()
					, AlertType.INFO, this);
		}
		return mensagem;
	}


	/**
		Seta a quantidade de cartas que devem ser compradas
	*/
	public static void setQuantidadeCompra(byte quantidade){
		if(quantidade >= 0)
			QUANT_COMPRA = quantidade;
	}

	/**
		Retorna a quantidade de cartas que devem ser compradas
	*/
	public static byte getQuantidadeCompra(){
			return QUANT_COMPRA;
	}
	
	/**
		Classe que exibe mensagens na tela
		
		@version 1.0
		@author Rog�rio de Paula Aguilar
	*/
	public class Mensagem extends Alert{
		/**
			Tela de volta
		*/
		protected Displayable proximaTela;

		/**
			Construtor
		*/
		public Mensagem(String titulo, AlertType tipo, Displayable proximaTela){
			super("", titulo, null, tipo);
			this.proximaTela = proximaTela;	
			setTimeout(6000);
			setCommandListener(
				new CommandListener(){
					public void commandAction(Command c, Displayable d){
						exibirDebugMsg("Mensagem>>commandAction");
						if(indiceJogadorRede == jogadorAtual && ONLINE && !getRecebeuPermissaoJogada()){
							MensagemAtualizacao msg = new MensagemAtualizacao(getString(), getType(), Mensagem.this.proximaTela);
							msg.setTimeout(2000);
							CartasMidlet.getDisplay().setCurrent(msg);
							System.gc();
							return;
						}
						
						if(Mensagem.this.proximaTela instanceof Jogador){
							((Jogador)Mensagem.this.proximaTela).setDisseMauMau(false);
						
							if(Mensagem.this.proximaTela instanceof JogadorHumano){
								try{
									if(getQuantidadeCompra() > 1){
										if(!ONLINE)
											((JogadorHumano)jogadores[0]).setComandoCompra((byte)0);
										else
											((JogadorHumano)jogadores[indiceJogadorRede]).setComandoCompra((byte)0);
										
									}else{
										if(!ONLINE)
											((JogadorHumano)jogadores[0]).setComandoCompra((byte)1);
										else
											((JogadorHumano)jogadores[indiceJogadorRede]).setComandoCompra((byte)1);

									}

								}catch(EmptyStackException e){
									CartasMidlet.getDisplay().setCurrent(elegerVencedor());
								}
							}
						}
						CartasMidlet.getDisplay().setCurrent(Mensagem.this.proximaTela);
						if(Mensagem.this.proximaTela instanceof JogadorComputador){
							((JogadorComputador)Mensagem.this.proximaTela).jogar(null);
						}


						if(Mensagem.this instanceof MensagemAtualizacao && ONLINE && indiceJogadorRede != getIndiceJogadorAtual()){
							enviarMsgServidor("ACKATUALIZACAO");
						}
					}

				}
			);

				

			/*if(ControleJogo.this.JOGO_EM_ANDAMENTO) 
				CartasMidlet.getDisplay().setCurrent(this);*/
						
		}

		/**
			Modifica a tela de volta
		*/
		public void setProximaTela(Displayable displayable){
			proximaTela = displayable;
		}
	}

	/**
		Mensagem especial, utilizada para indicar que o jogador atualizou 
		o status do jogo em rede
		
		@author Rog�rio de Paula Aguilar
		@version 1.0
	*/	
	class MensagemAtualizacao extends Mensagem{

		public MensagemAtualizacao(String titulo, AlertType tipo, Displayable proximaTela){
			super(titulo, tipo, proximaTela);
		}

	}

	/**
		Retorna um jogador
	*/
	public Jogador getJogador(int indiceJogador){
		if(indiceJogador < 0 || indiceJogador > NUMERO_JOGADORES)
			throw new IllegalArgumentException("Jogador inv�lido!");
		else
			return jogadores[indiceJogador];
	}


	/**
		Inverte o sentido do jogo
	*/	

	public void inverterSentidoJogo(){
			if(SENTIDO_JOGO == SENTIDO_NORMAL) SENTIDO_JOGO = SENTIDO_INVERSO;
			else SENTIDO_JOGO = SENTIDO_NORMAL;
									
	}

	/**
		Mostra a tela informando o vencedor do jogo
	*/

	public void setVencedor(Jogador jogador){
			Mensagem mensagem = new Mensagem("Fim de jogo. O vencedor foi: " + jogador.getNome()
					, AlertType.INFO, this);
			mensagem.setTimeout(Alert.FOREVER);
			if(!ONLINE)
				CartasMidlet.getDisplay().setCurrent(mensagem);
			else
				enviarMsgServidor(montarStatusJogo(mensagem, true));
	}



	/**
		Classe que fica esperando um comando do servidor. <p>Esta classe sincroniza os comandos que vem do servidor
		e executa os mesmos
	*/
	private static class threadLeitura implements Runnable{
		/**
			Objeto utilizado para sincroniza��o de comandos
		*/
		private static Object objSync = new Object(); //Objeto utilizado para sincroniza��o
		
		private ControleJogo controleJogo;	

		/**
			Construtor
		*/
		public threadLeitura(ControleJogo controleJogo){
			new Thread(this).start();
			this.controleJogo = controleJogo;
		}

		/**
			Fica esperando um comando do servidor, sincroniza comandos e executa os mesmos
		*/
		public void run(){
			exibirDebugMsg("ControleJogo>>threadLeitura>>Iniciando thread de leitura");
			StringBuffer resultado = new StringBuffer();	
			while(CONECTADO){
				resultado.delete(0, resultado.length());
				int ch = 0;
   				
				try{
					while(ch != -1 && ((char)ch) != '\n') {
       						ch = is.read();
						resultado.append((char)ch);
   					}
				
					comando = resultado.toString().trim();
					exibirDebugMsg("threadLeitura(" + (nomeJogadores[0] == null ? "null" : nomeJogadores[0]) + ")>>Comando>>" + comando);
					synchronized(controleJogo){
						if(comando.equals("SAIR_SERVIDOR")){
							synchronized(controleJogo){
								exibirDebugMsg("ControleJogo>>threadLeitura>>Jogador foi desconectado pelo servidor");
								controleJogo.setApresentacao(true);
			   					controleJogo.setTelaSelecao(true);
								controleJogo.JOGO_EM_ANDAMENTO = false;
			   					controleJogo.ONLINE=false;
								controleJogo.exibirMsg("Voc� foi desconectado pelo servidor. Reinicializando jogo.");
								fecharConexaoSemConfirmacao();
							}
						}else if(comando.startsWith("ERRO")){
							synchronized(controleJogo){
								exibirDebugMsg("ControleJogo>>threadLeitura>>N�o foi poss�vel entrar na sala. " + comando);
								controleJogo.setApresentacao(true);
			   					controleJogo.setTelaSelecao(true);
								controleJogo.JOGO_EM_ANDAMENTO = false;
			   					controleJogo.ONLINE=false;
								controleJogo.exibirMsg("Ocorreu um erro. Mensagem do servidor: " + comando);
				
								//fecharConexao();
								fecharConexaoSemConfirmacao();
							}
						}else if(comando.equals("OKPRIM") || comando.equals("OK")){
							frmEspera frm = null;
							primeiroJogador = false;
							if(comando.equals("OKPRIM")){
								primeiroJogador = true; 
								if(frm == null){
									frm = new frmEspera("Voc� � o primeiro jogador a entrar nesta sala. Aguardando a entrada de novos jogadores.");
								}else{
									frm.reset("Voc� � o primeiro jogador a entrar nesta sala. Aguardando a entrada de novos jogadores.");
								}
									
							}else{
								if(frm == null){
									frm = new frmEspera("Voc� entrou na sala com sucesso. O respons�vel pelo in�cio do jogo � o primeiro jogador da sala. Aguardando o in�cio do jogo.");
								}else{
									frm.reset("Voc� entrou na sala com sucesso. O respons�vel pelo in�cio do jogo � o primeiro jogador da sala. Aguardando o in�cio do jogo.");

								}
							}
							frm.removeCommand(cmdCancelar);
							frm.removeCommand(cmdComecarJogo);
							frm.addCommand(cmdCancelar);

							if(comando.equals("OKPRIM")){
								frm.append(new StringItem("Jogadores na sala " + idSala + ":", ""));
								frm.append(new StringItem("", "\nJogador: " + nomeJogadores[0].toUpperCase()));							

							}
							//frm.append("\nJogador: " + nomeJogadores[0]);

							if(listenerOK == null){
								     listenerOK = new CommandListener(){

										public void commandAction(Command c, Displayable d){
											if(c.getLabel().equals("Cancelar")){ 
												synchronized(controleJogo){
												//if(CartasMidlet.getDisplay() != null){
														controleJogo.setApresentacao(true);
			   											controleJogo.setTelaSelecao(true);
														controleJogo.JOGO_EM_ANDAMENTO = false;
			   											if(CartasMidlet.getDisplay() != null)
															CartasMidlet.getDisplay().setCurrent(controleJogo);
														enviarMsgServidor("SAIR_CLIENTE");
														controleJogo.fecharConexao();	
												//	}
												}
											}
										}
									};


							}


							frm.setCommandListener(
									listenerOK
							);
							CartasMidlet.getDisplay().setCurrent(frm);
						}else if(comando.startsWith("ENTRADA_JOGADOR")){
						     //if(!JOGO_EM_ANDAMENTO){
							System.out.println("CRIANDO FORMUL�RIO DE LISTA DE USU�RIOS");
							frmEspera frmEsperaTMP = frm;
							if(primeiroJogador){
								if(frmEsperaTMP == null){
									frmEsperaTMP = new frmEspera("Voc� � o primeiro jogador a entrar nesta sala. Aguardando a entrada de novos jogadores.");
								}else{
									frmEsperaTMP.reset("Voc� � o primeiro jogador a entrar nesta sala. Aguardando a entrada de novos jogadores.");
								}
							}else{
								if(frmEsperaTMP == null){
									frmEsperaTMP = new frmEspera("Voc� entrou na sala com sucesso. O respons�vel pelo in�cio do jogo � o primeiro jogador da sala. Aguardando o in�cio do jogo.");
								}else{
									frmEsperaTMP.reset("Voc� entrou na sala com sucesso. O respons�vel pelo in�cio do jogo � o primeiro jogador da sala. Aguardando o in�cio do jogo.");


								}
							}

							frmEsperaTMP.removeCommand(cmdCancelar);
							frmEsperaTMP.removeCommand(cmdComecarJogo);
							
			
							int indice = comando.indexOf("]");
							int indiceInicial = 16;
							int i = 0;
							for(i = 0; i < NUMERO_JOGADORES; i++)
								nomeJogadores[i] = null;
							int j = 0;
							System.out.println("FORMUL�RIO DE LISTA DE USU�RIOS CRIADO");
							
							comando = comando.substring(indiceInicial, comando.length() -1 );
							exibirDebugMsg("COMANDO_PARCIAL: " + comando);
							
							indice = comando.indexOf("][");
							
							while(indice != -1){
							    nomeJogadores[j++] = comando.substring(0, indice);
							    comando = comando.substring(indice + 2, comando.length());
							    exibirDebugMsg("COMANDO_PARCIAL_ENTRADA: " + comando);
							    indice = comando.indexOf("][");
							    if( nomeJogadores[j-1].trim().toUpperCase().substring(8, nomeJogadores[j-1].length()).trim().equals(nomeJogador.trim().toUpperCase())){
								exibirDebugMsg("JOGADORREDE>>INDICEJOGADORREDE>>" + (j-1));
								indiceJogadorRede = j - 1;
							    
							    }		
								
							}
							exibirDebugMsg("COMANDO_TRIM: " + comando.trim());
							nomeJogadores[j++] = comando.trim();
    							if( nomeJogadores[j-1].trim().toUpperCase().substring(8, nomeJogadores[j-1].length()).trim().equals(nomeJogador.trim().toUpperCase())){
								exibirDebugMsg("JOGADORREDE>>INDICEJOGADORREDE>>" + (j-1));
								indiceJogadorRede = j - 1;
							    
						    	}		
							


							frmEsperaTMP.append(new StringItem("Jogadores na sala " + idSala + ":", ""));
							
							exibirDebugMsg("##################LISTA DE USU�RIOS ON-LINE###############");
							for(i = 0; i < NUMERO_JOGADORES; i++){
								exibirDebugMsg("JOGADOR: " + nomeJogadores[i]);
								if(nomeJogadores[i] != null)
									frmEsperaTMP.append("\n" + nomeJogadores[i]);
							}
							exibirDebugMsg("##########################################################");
							
							//frm.append(new StringItem("Jogadores na sala:", comando.substring(15, comando.length())));
								
							if(primeiroJogador){
								frmEsperaTMP.addCommand(cmdComecarJogo);
							}


							//Command cmdCancelar = new Command("Cancelar", Command.SCREEN, 0);
							frmEsperaTMP.addCommand(cmdCancelar);


							if(listenerEntradaJogador == null){
								listenerEntradaJogador = new CommandListener(){

										public void commandAction(Command c, Displayable d){
											if(c.getLabel().equals("Cancelar")){ 
												synchronized(controleJogo){
													controleJogo.setApresentacao(true);
			   										controleJogo.setTelaSelecao(true);
													controleJogo.JOGO_EM_ANDAMENTO = false;
			   										controleJogo.ONLINE = false;
													if(CartasMidlet.getDisplay() != null)
														CartasMidlet.getDisplay().setCurrent(controleJogo);
													enviarMsgServidor("SAIR_CLIENTE");
													controleJogo.fecharConexao();				
												}
											}else if(c.getLabel().equals("Come�ar jogo")){
												controleJogo.comecarJogoOnLine(true);	

											}
										}
									};


							}

							frmEsperaTMP.setCommandListener(
									listenerEntradaJogador

							);

							System.gc();
							CartasMidlet.getDisplay().setCurrent(frmEsperaTMP);						
						     //}								
						}else if(comando.startsWith("ST")){
							synchronized(controleJogo){
								if(!controleJogo.primeiroStatusRecebido){
									controleJogo.comecarJogoOnLine(false);
								}
								controleJogo.atualizarStatusJogo(comando);
						
							}
						}else if(comando.startsWith("VENCEDOR_JOGO")){
							synchronized(controleJogo){
								Mensagem m = controleJogo.new Mensagem("Fim de jogo. O vencedor foi: " + comando.substring(14, comando.length()) , AlertType.INFO, controleJogo);
								m.setTimeout(10000);
								controleJogo.setApresentacao(true);
								controleJogo.setTelaSelecao(true);
								controleJogo.JOGO_EM_ANDAMENTO = false;
			   					controleJogo.ONLINE = false;
								//fecharConexao();
								fecharConexaoSemConfirmacao();
								System.gc();
								CartasMidlet.getDisplay().setCurrent(m);
							}
						}else if(comando.equals("PODE_JOGAR")){
							if(controleJogo.indiceJogadorRede == controleJogo.getIndiceJogadorAtual()){
								controleJogo.exibirDebugMsg("Jogador>>" + controleJogo.getJogador(controleJogo.getIndiceJogadorAtual()).getNome() + ">>CONFIRMA��O DE JOGADA RECEBIDA");
								controleJogo.setRecebeuPermissaoJogada(true);
							}
						}else if(comando.startsWith("SAIR_SALA")){
							synchronized(controleJogo){
								controleJogo.setApresentacao(true);
			   					controleJogo.setTelaSelecao(true);
								controleJogo.JOGO_EM_ANDAMENTO = false;
								controleJogo.ONLINE = false;
								controleJogo.exibirMsg("Voc� foi desconectado pelo servidor. Mensagem do servidor: " + comando + " Reinicializando jogo.");
								//fecharConexao();
								fecharConexaoSemConfirmacao();
							}
						}else if(comando.startsWith("RECOMECO_ENTRADA_JOGADOR")){
							synchronized(controleJogo){
								int indice = comando.indexOf("]");
								int indiceInicial = "RECOMECO_ENTRADA_JOGADOR".length() + 1;
								int i = 0;
								NUMERO_JOGADORES = 4;
								for(i = 0; i < NUMERO_JOGADORES; i++)
									nomeJogadores[i] = null;
								int j = 0;
								comando = comando.substring(indiceInicial, comando.length() -1 );
								indice = comando.indexOf("][");
								while(indice != -1){
							    		nomeJogadores[j++] = comando.substring(0, indice);
							    		comando = comando.substring(indice + 2, comando.length());
							    		exibirDebugMsg("COMANDO_PARCIAL_ENTRADA: " + comando);
							    		indice = comando.indexOf("][");
							    		if( nomeJogadores[j-1].trim().toUpperCase().substring(8, nomeJogadores[j-1].length()).trim().equals(nomeJogador.trim().toUpperCase())){
										exibirDebugMsg("JOGADORREDE>>INDICEJOGADORREDE>>" + (j-1));
										indiceJogadorRede = j - 1;
							    		}		
								
								}
								nomeJogadores[j++] = comando.trim();
    								if( nomeJogadores[j-1].trim().toUpperCase().substring(8, nomeJogadores[j-1].length()).trim().equals(nomeJogador.trim().toUpperCase())){
									exibirDebugMsg("JOGADORREDE>>INDICEJOGADORREDE>>" + (j-1));
									indiceJogadorRede = j - 1;
							    
						    		}
								exibirDebugMsg("@@@@@@@@@@@@@@@@@LISTA DE JOGADORES(RECOME�O)@@@@@@@@@@@@@@");
								for(i = 0; i < NUMERO_JOGADORES; i++){
									if(nomeJogadores[i] != null)
										exibirDebugMsg(nomeJogadores[i]);

								}
								exibirDebugMsg("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
																		
								controleJogo.comecarJogoOnLine(true);
							}

						}
					}
					
				}catch(Exception e){
					synchronized(controleJogo){
						exibirDebugMsg("ControleJogo>>threadLeitura>>Erro ao ler>>Fechando conex�o>>" + e);
						controleJogo.setApresentacao(true);
						controleJogo.setTelaSelecao(true); 
						controleJogo.JOGO_EM_ANDAMENTO = false;
						controleJogo.ONLINE = false;
						controleJogo.exibirMsg("Ocorreu um problema de comunica��o com o servidor. Isto ocorre quando h� falhas no meio de comunica��o ou quando os outros jogadores da sala j� sa�ram antes do in�cio do jogo.");
						fecharConexaoSemConfirmacao();
						
					}
				}
		
				System.gc();
			}

			exibirDebugMsg("ControleJogo>>threadLeitura>>Finalizando thread de leitura");
			
			
		}
	}

	/**
		Conecta com o servidor e envia uma requisi��o. Se manterConexao for igual a true, <p> mant�m a conex�o
		com o servidor aberta e inicializa a thread de leitura de comandos, <p> sen�o fecha a conex�o ap�s receber
		a resposta ao comando enviado.
	*/

	public static String retornarMsgServidor(String URL, String str, boolean manterConexao, ControleJogo controleJogo){
		exibirDebugMsg("ControleJogo>>retornarMsgServidor>>" + URL + " " + str);
		StringBuffer resultado = new StringBuffer("");	
		
		fecharConexao();
		
		try{

			conexao = (SocketConnection)Connector.open(URL);
   			conexao.setSocketOption(SocketConnection.KEEPALIVE, 1);
			is  = conexao.openInputStream();
   			os = conexao.openOutputStream();
   			socketWriter = new PrintStream(os);
			socketWriter.println(str);
			socketWriter.flush();
			int ch = 0;
			if(!manterConexao){
   				while(ch != -1 && ((char)ch) != '\n') {
       					ch = is.read();
					resultado.append((char)ch);
   				}
				conexao.close();
			}else{
				setConectado(true);
				new threadLeitura(controleJogo);
				tAck = new Thread(new threadAck(controleJogo));
				tAck.start();
					
			}
		

		}catch(Exception e){
			exibirDebugMsg("ControleJogo>>retornarMsgServidor>>ERRO: " + e);
			fecharConexao();
			return "ERRO";
		}
		return resultado.toString().trim();		
	}


	

	/**
		Retorna a string de conex�o com o servidor
	*/	
	public static String getHostServidor(){
		return "socket://" + IP_SERVIDOR + ":" + PORTA_SERVIDOR;
	}

	/**
		Mostra uma mensagem na tela do usu�rio
	*/
	public void exibirMsg(String msg){
		msgErro = new Mensagem(msg, AlertType.INFO, this);
		setApresentacao(true);
		setTelaSelecao(true);
		JOGO_EM_ANDAMENTO = false;
		CartasMidlet.getDisplay().setCurrent(msgErro);
		
	}
		


	/**
		Fecha a conex�o com o servidor
	*/

	public synchronized static void fecharConexao(){

				try{
							 		
					if (socketWriter != null){
						socketWriter.println("SAIR_CLIENTE");
					}
                    				
				
				}catch(Exception e4){
					exibirDebugMsg("ControleJogo>>retornarMsgServidor>>ERRO AO FECHAR CONEX�O (socketWriter): " + e4);
		    		}
		     	

				
				try{
		 		
					if (conexao != null)
                    				conexao.close();
				
				}catch(Exception e3){
					exibirDebugMsg("ControleJogo>>retornarMsgServidor>>ERRO AO FECHAR CONEX�O (C): " + e3);
		    		}

				setConectado(false);	


	}



	/**
		Fecha a conex�o com o servidor, mas n�o envia
		mensagem informando a sa�da do cliente (Utilizar somente
		quando processar msgs do servidor)
	*/

	public synchronized static void fecharConexaoSemConfirmacao(){

				/*try{
		 		
					if (socketWriter != null){
						socketWriter.println("SAIR_CLIENTE");
					}
                    				
				
				}catch(Exception e4){
					exibirDebugMsg("ControleJogo>>retornarMsgServidor>>ERRO AO FECHAR CONEX�O (socketWriter): " + e4);
		    		}*/
		     	

				
				try{
		 		
					if (conexao != null)
                    				conexao.close();
				
				}catch(Exception e3){
					exibirDebugMsg("ControleJogo>>retornarMsgServidor>>ERRO AO FECHAR CONEX�O (C): " + e3);
		    		}

				setConectado(false);	


	}


	public static synchronized void setConectado(boolean b){
		CONECTADO = b;
		ONLINE=false;	
	}

	public static synchronized boolean getConectado(){
		return CONECTADO;
	}

	/**
		Envia uma mensagem de texto para o servidor
	*/
	public static synchronized void enviarMsgServidor(String msg){
		try{
			socketWriter.println(msg);
			socketWriter.flush();
		}catch(Exception e){
			exibirDebugMsg("ControleJogo>>enviarMsgServidor>>Erro ao enviarMsg>>" + e);
			fecharConexao();
		}		
	}

	/**
		Monta o quadro contendo o status do jogo
	*/
	public synchronized String montarStatusJogo(Mensagem mensagem, boolean fecharJogo){
		/*
			Formato do quadro:
				ST B NV NV ... NV J NV NV ... NV J NV NV ... NV J NV NV ... NV J NV NV ... NV MM
				SJ MSG JA PA NE FJ FST
			ST --> Indica o tipo do quadro ("Status")
			NV --> Naipe e valor de cada carta do baralho
			B --> In�cio das cartas do baralho
			J -->In�cio da carta de um jogador
			OMM --> Indica se o jogo � OpenMauMau ou n�o
			SJ --> Sentido do jogo
			MSG --> Mensagem
			QC --> Quantidade de cartas para compra
			JA --> Jogador atual
			PA --> Penalidade aplicada
			NE --> Naipe Escolhido
			FJ --> Fechar Jogo
			FST --> Fim do quadro de status
			
		*/		
		StringBuffer quadro = new StringBuffer("STB");
		int numeroCartas = baralhoAtual.numeroCartas();
		//Adiconando cartas do baralho
		if(numeroCartas > 0){
			for(int i = 0; i < numeroCartas; i++){
				Carta carta = baralhoAtual.carta(i);
				quadro.append("N" + carta.getNaipe());
				quadro.append("V" + carta.getValor());
	
			}
		}

		//Adicionando cartas dos jogadores
		for(int j = 0; j < NUMERO_JOGADORES; j++){
			if(jogadores[j] != null){
				Jogador jogadorAtual = jogadores[j];
				quadro.append("J");
				numeroCartas = jogadorAtual.numeroCartas();
				for(int i = 0; i < numeroCartas; i++){
					Carta carta = jogadorAtual.carta(i);
					quadro.append("N" + carta.getNaipe());
					quadro.append("V" + carta.getValor());
				}
			}
		}

		quadro.append("OMM" + getOpenMauMau());
		quadro.append("SJ" + SENTIDO_JOGO);
		quadro.append("MSG" + mensagem.getString());
		quadro.append("QC" + QUANT_COMPRA);
		quadro.append("JA" + jogadorAtual);
		quadro.append("PA" + getPenalidadeAplicada());
		quadro.append("NE" + NAIPE_ESCOLHIDO);

		quadro.append("FJ" + fecharJogo);
		
		quadro.append("FST");
		System.gc();	
		exibirDebugMsg("ControleJogo>>montarStatusJogo(Mensagem)>>\n" + quadro.toString().trim());
		return quadro.toString().trim();
	}

	/**
		Atualiza o status do jogo de acordo com o quadro recebido do servidor

	*/
	public synchronized void atualizarStatusJogo(String msg){
		/*
			Formato do quadro:
				ST B NV NV ... NV J NV NV ... NV J NV NV ... NV J NV NV ... NV J NV NV ... NV OMM
				SJ MSG JA FJ FST
			ST --> Indica o tipo do quadro ("Status") 
			NV --> Naipe e valor de cada carta do baralho
			B --> In�cio das cartas do baralho
			J -->In�cio da carta de um jogador
			OMM --> Indica se o jogo � OpenMauMau ou n�o
			SJ --> Sentido do jogo
			MSG --> Mensagem
			QC --> Quantidade de cartas para compra
			JA --> Jogador atual
			PA --> Penalidade Aplicada
			NE --> Naipe Escolhido
			FJ --> Fechar Jogo
			FST --> Fim do quadro de status
			
		*/
		RECEBEU_PERMISSAO_JOGADA = false;

		exibirDebugMsg("JOGADOR_REDE>>" + jogadores[indiceJogadorRede].getNome() + ">>ControleJogo>>atualizarStatusJogo()>>Atualizando status do jogo");
		exibirDebugMsg("ControleJogo>>atualizarStatusJogo()>>Atualizando status do jogo>>Atualizando baralho");
		String strCartasBaralho = msg.substring(msg.indexOf("B") + 1, msg.indexOf("J"));
		exibirDebugMsg("ControleJogo>>atualizarStatusJogo()>>Atualizando status do jogo>>Atualizando baralho>>" + strCartasBaralho);

		/*******************************************************Atualiza��o do baralho********************************************************************/
		baralhoAtual.zerar();
		
		int naipe, valor;
		StringBuffer strNaipe = new StringBuffer();
		StringBuffer strValor = new StringBuffer();
		
		while(strCartasBaralho.length() > 0){
			int i = 1;	
			strNaipe.setLength(0);
			strValor.setLength(0);
			
			while(strCartasBaralho.charAt(i) != 'V')
				strNaipe.append(strCartasBaralho.charAt(i++));	
		
			i++;
			while(i < strCartasBaralho.length() && strCartasBaralho.charAt(i) != 'N' )
				strValor.append(strCartasBaralho.charAt(i++));	
			
			naipe = Integer.parseInt(strNaipe.toString().trim());
			valor = Integer.parseInt(strValor.toString().trim());
			exibirDebugMsg("ControleJogo>>atualizarStatusJogo()>>Atualizando status do jogo>>Atualizando baralho");
			exibirDebugMsg("ADICIONANDO CARTA AO BARALHO " +  Baralho.cartasBaralho[naipe][valor].toString());

			baralhoAtual.adicionarCarta(Baralho.cartasBaralho[naipe][valor]);
			if( i < strCartasBaralho.length()){
				strCartasBaralho = strCartasBaralho.substring(i, strCartasBaralho.length());
			}else{
				strCartasBaralho = "";		
			}
		}
		exibirDebugMsg("ControleJogo>>atualizarStatusJogo()>>Atualizando status do jogo>>Atualizando baralho>>BARALHO ATUALIZADO");
		//CartasMidlet.getDisplay().setCurrent(baralhoAtual);

		/*************************************************************************************************************************************************/


		/*******************************************************Atualiza��o dos jogadores********************************************************************/
		msg = msg.substring(msg.indexOf("J"), msg.length());

		exibirDebugMsg("ControleJogo>>comecarJogoOnLine>>Atualizando jogadores>>"+ msg);
		for(int j = 0; j < NUMERO_JOGADORES; j++){
			exibirDebugMsg("ControleJogo>>comecarJogoOnLine>>Atualizando jogadores>>JOGADOR:" + jogadores[j].getNome());
			jogadores[j].zerar();

			int indiceProximoJogador = msg.indexOf("J", 2);

			if(indiceProximoJogador == -1 || msg.charAt(indiceProximoJogador - 1) == 'S'){
				indiceProximoJogador = msg.indexOf("O");
				System.out.println("INDICEPROXIMOJOGADOR: " + indiceProximoJogador);	
			}
			String cartasJogadorAtual = msg.substring(0, indiceProximoJogador);
			
			exibirDebugMsg("ControleJogo>>comecarJogoOnLine>>Atualizando jogadores>>CARTASJOGADORATUAL>>" + cartasJogadorAtual);
			
			int i = 1;	
			while(i < cartasJogadorAtual.length()){
				i++;
				strNaipe.setLength(0);
				strValor.setLength(0);
				while(cartasJogadorAtual.charAt(i) != 'V'){
					strNaipe.append(cartasJogadorAtual.charAt(i++));	
				}
				i++;
				while(i < cartasJogadorAtual.length() && cartasJogadorAtual.charAt(i) != 'N' )
					strValor.append(cartasJogadorAtual.charAt(i++));	
			
				naipe = Integer.parseInt(strNaipe.toString().trim());
				valor = Integer.parseInt(strValor.toString().trim());

				exibirDebugMsg("ADICIONANDO CARTA AO JOGADOR " + jogadores[j].getNome() + ": " + Baralho.cartasBaralho[naipe][valor].toString());
				jogadores[j].adicionarCarta(Baralho.cartasBaralho[naipe][valor]);
			}

			msg = msg.substring(indiceProximoJogador, msg.length());
			exibirDebugMsg("ControleJogo>>comecarJogoOnLine>>Atualizando jogadores>>CARTASJOGADORATUAL>>MSG>>" + msg);
			
			exibirDebugMsg("ControleJogo>>atualizarStatusJogo()>>Atualizando status do jogo>>Atualizando JOGADORES>>JOGADORES ATUALIZADOS");
	
								
								
		
		}	
		

		/*************************************************************************************************************************************************/

		
		/***********************************************************Status do jogo**************************************


			OMM --> Indica se o jogo � OpenMauMau ou n�o
			SJ --> Sentido do jogo
			MSG --> Mensagem
			QC --> Quantidade de cartas para compra
			JA --> Jogador atual
			PA --> Penalidade Aplicada
			NE --> Naipe Escolhido
			FJ --> Fechar Jogo
			FST --> Fim do quadro de status
		*/


		String OMM = msg.substring(3, msg.indexOf("SJ"));
		exibirDebugMsg("ControleJogo>>atualizarStatusJogo>>OMM: " + OMM);
		if(OMM.equals("true"))
			OPEN_MAU_MAU = true;
		else
			OPEN_MAU_MAU = false;
		
		
		msg = msg.substring(msg.indexOf("SJ"), msg.length()); 
		exibirDebugMsg("ControleJogo>>atualizarStatusJogo>>SJ: " + msg.substring(2, msg.indexOf("MSG")));

		SENTIDO_JOGO = (byte)Integer.parseInt(msg.substring(2, msg.indexOf("MSG")));

		msg = msg.substring(msg.indexOf("MSG"), msg.length());
		exibirDebugMsg("ControleJogo>>atualizarStatusJogo>>MSG: " + msg.substring(3, msg.indexOf("QC"))); //MSG
		String mensagem = msg.substring(3, msg.indexOf("QC"));
		
		msg = msg.substring(msg.indexOf("QC"), msg.length());
		exibirDebugMsg("ControleJogo>>atualizarStatusJogo>>QC: " + msg.substring(2, msg.indexOf("JA"))); //QC
		QUANT_COMPRA = (byte)Integer.parseInt(msg.substring(2, msg.indexOf("JA")));
		
		msg = msg.substring(msg.indexOf("JA"), msg.length());
		exibirDebugMsg("ControleJogo>>atualizarStatusJogo>>JA: " + msg.substring(2, msg.indexOf("PA"))); //JA
		jogadorExibido = jogadorAtual = (byte)Integer.parseInt(msg.substring(2, msg.indexOf("PA")));
		
		msg = msg.substring(msg.indexOf("PA"), msg.length());
		exibirDebugMsg("ControleJogo>>atualizarStatusJogo>>PA: " + msg.substring(2, msg.indexOf("NE"))); //PA
		setPenalidadeAplicada( msg.substring(2, msg.indexOf("NE")).equals("true") ? true: false );

		msg = msg.substring(msg.indexOf("NE"), msg.length());
		exibirDebugMsg("ControleJogo>>atualizarStatusJogo>>NE: " + msg.substring(2, msg.indexOf("FJ"))); //NE
		NAIPE_ESCOLHIDO = (byte)Integer.parseInt(msg.substring(2, msg.indexOf("FJ")));
		

		msg = msg.substring(msg.indexOf("FJ"), msg.length());
		exibirDebugMsg("ControleJogo>>atualizarStatusJogo>>FJ: " + msg.substring(2, msg.indexOf("FST"))); //FJ
		

		msg = msg.substring(msg.indexOf("FST"), msg.length());
		exibirDebugMsg("ControleJogo>>atualizarStatusJogo>>FST: " + msg); //FST
		
		int vencedor = mensagem.indexOf("vencedor");
		Mensagem m = null;
		if(vencedor != -1){ //Vencedor do jogo detectado
			fecharConexao();
			setTelaSelecao(true);
			setApresentacao(true);
			JOGO_EM_ANDAMENTO = false;
			m = new MensagemAtualizacao(mensagem, AlertType.INFO, this);
		}else{
			m = new MensagemAtualizacao(mensagem, AlertType.INFO, jogadores[jogadorAtual]);

		}
		
		System.gc();
		

		//if(indiceJogadorRede != getIndiceJogadorAnterior())
		CartasMidlet.getDisplay().setCurrent(m);

		if(indiceJogadorRede == getIndiceJogadorAtual()){
			enviarMsgServidor("ACKATUALIZACAO");
		}
		//CartasMidlet.getDisplay().setCurrent(m);
		exibirDebugMsg("ControleJogo>>atualizarStatusJogo>>Status atualizado com sucesso");
		
		
	}

	/**
		Verifica a integridade entre os procedimentos atualizarStatusJogo e montarStatusJogo
	*/
	public void loopback(){
		atualizarStatusJogo(montarStatusJogo(new Mensagem("teste", AlertType.INFO, jogadores[0]), false));

	}

	/**
		Modifica o valor da vari�vel PenalidadeAplicada
	*/
	public synchronized void setPenalidadeAplicada(boolean b){
		if(b)
			setQuantidadeCompra((byte)0);
		PENALIDADE_APLICADA = b;
	}

	/**
		Retorna o valor da vari�vel penalidadeAplicada 
	*/	
	public synchronized boolean getPenalidadeAplicada(){
		return PENALIDADE_APLICADA;
	}

	/**
		Retorna o �ndice do jogador atual
	*/
	public int getIndiceJogadorAtual(){
		return jogadorAtual;
	}

	/**
		Modifica o endere�o ip do servidor
	*/
	public static void setIPServidor(String IP){
		IP_SERVIDOR = IP;
	}

	/**
		Modifica a porta do servidor
	*/
	public static void setPortaServidor(String porta){
		PORTA_SERVIDOR = porta;
	}

	/**
		Retorna o endere�o ip do servidor
	*/
	public static String getIPServidor(){
		return IP_SERVIDOR;
	}

	/**
		Retorna a porta do servidor
	*/
	public static String getPortaServidor(){
		return PORTA_SERVIDOR;
	}

	/**
		Incrementa o �ndice do jogo atual 	
	*/
	public synchronized void incrementaJogoAtual(){
		jogoAtual++;
		exibirDebugMsg("ControleJogo>>INCREMENTANDO �NDICE DO JOGO ATUAL: " + jogoAtual);
	}

	/**
		Retorna o �ndice do jogo atual
	*/
	public synchronized int getJogoAtual(){
		return jogoAtual;
	}
	
}

