

/*
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
Classe: jogo.Baralho
Responsabilidades: Representa o baralho

****************************************Alterações****************************************


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 28/08/2003
Responsável: Rogério de Paula Aguilar
Descrição: Criação da Classe
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 30/08/2003
Responsável: Rogério de Paula Aguilar
Descrição: Colocando imagens finais nas cartas
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 04/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Atualizando classe para exibir a carta atual
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 08/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Adicionando imagem com o verso da carta
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 11/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Adicionando rotinas para adicionar cartas ao baralho
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 14/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Adicionando rotinas para compra de cartas
	   Modificando rotinas de debug para utilizarem o método exibirDebugMsg da
		classe ControleJogo
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 19/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Adicionando rotina que retorna o número de cartas do baralho
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 10/10/2003
Responsável: Rogério de Paula Aguilar
Descrição: Arrumando interface gráfica

Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 22/10/2003
Responsável: Rogério de Paula Aguilar
Descrição: Adicionando rotina para zerar o baralho

Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 12/11/2003
Responsável: Rogério de Paula Aguilar
Descrição: Adicionando informação sobre o próximo naipe que deve ser jogado
		quando a carta atual é uma valete

Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%



*/


/*
@author  1.0 
@param  1.0 
@return  1.0 
@throws  1.2 
@version  



*/


package jogo;

import java.util.*;
import jogo.apibasica.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

/** 
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
<p>Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
<p>Classe: jogo.Baralho
<p>Responsabilidades: Representa o baralho do jogo

@author Rogério de Paula Aguilar
@version  1.0

*/


public class Baralho extends GameCanvas {
		
	/**
		Cartas do baralho
	*/
	public final static Carta[][] cartasBaralho = new Carta[4][14];
	
	/**
		Imagem do verso de uma carta
	*/

	private static Image imgVerso;

	/**
		Carta com o verso de uma carta
	*/
	public static Carta cartaVerso;

	/**
		Carta contendo o verso em miniatura
	*/
	public static Carta cartaVersoMiniatura;
	
	/**
		Número de cartas do baralho
	*/
	public static final byte NUMERO_CARTAS = 56;

	/**
		Número de cartas que é distribuído para cada jogador
	*/
	public static final byte CARTAS_PARA_CADA_JOGADOR = 7;

	/**
		Variável auxiliar para o desenho das setas de navegação
	*/
	protected byte deslocamentoSetaBaixo = 0;


	/**
		Cartas atuais do baralho
	*/

	private Stack cartasAtuais = new Stack();

	
	/**
		Tela de volta, utilizada quando o jogador seleciona a seta
		de navegação para baixo
	*/

	private Displayable telaVolta; //Tela para voltar

	static{
		try{
			Baralho.imgVerso = Image.createImage("/fundo.png");
			Baralho.cartaVerso = new Carta(imgVerso, Carta.VERSO, Carta.VERSO);
			Baralho.cartaVersoMiniatura = new Carta(Image.createImage("/fundomini.png"), Carta.VERSO, Carta.VERSO);
		}catch(Exception e){
			System.out.println("Baralho>>static>>Erro ao carregar imagem>>" + e);
		}

	}

	/**
		Método invocado quando o jogador pressiona uma tecla na tela do baralho 
	*/

	public void keyPressed(int keyCode){
		
		if(getKeyCode(DOWN) == keyCode){
			//Exibindo tela anterior
			ControleJogo.exibirDebugMsg("Baralho>>keyPressed>>Voltando à tela anterior");
			deslocamentoSetaBaixo = 2;
		}
		repaint();

	}

	/**
		Método invocado quando o jogador solta uma tecla na tela do baralho 
	*/

	public void keyReleased(int keyCode){
		if(getKeyCode(DOWN) == keyCode){
			//Exibindo tela anterior
			deslocamentoSetaBaixo = 0;
			repaint();
			CartasMidlet.getDisplay().setCurrent(telaVolta);
		}
		repaint();

	}

	/**
		Construtor padrão
	*/

	public Baralho(){
		super(false);


		Image img = null;
			
		try{
		   img = Image.createImage("/cartas.png");

		}catch(Exception e){
		   System.out.println("BARALHO>>Baralho()>>ERRO AO CARREGAR IMAGEM PRINCIPAL DAS CARTAS");

		}		
	
				

		//Inicializando cartas
		for(byte i = Carta.PAUS; i <= Carta.OUROS; i++){
			for(byte j = Carta.CORINGA; j <= Carta.AS; j++){

				try{
					
			
					cartasBaralho[i][j] = new Carta(
						Image.createImage(img, j * Carta.LARGURA_CARTA, i * Carta.ALTURA_CARTA, Carta.LARGURA_CARTA, Carta.ALTURA_CARTA, 0),
						 i, j
					);
					ControleJogo.exibirDebugMsg(cartasBaralho[i][j].toString());	
			
				}catch(Exception e){
					System.out.println("BARALHO>>Baralho()>>ERRO AO CARREGAR IMAGEM");
					
				}

				cartasAtuais.push(cartasBaralho[i][j]);
			}
		}

		if(ControleJogo.DEBUG_MODE){
			ControleJogo.exibirDebugMsg("BARALHO>>Baralho()>>Cartas Atuais: " + cartasAtuais.size());
			imprimirCartasAtuais();
		}
		//embaralhar();
		System.gc();
				
	}		

	/**
		Imprime as cartas atuais
	*/
	public void imprimirCartasAtuais(){
		System.out.println("*****Baralho>>imprimirCartasAtuais*****");
		for(int i =0; i < cartasAtuais.size(); i++){
			Carta carta = (Carta)cartasAtuais.elementAt(i);
			System.out.println(carta);
		}
		System.out.println("***************************************");
		System.gc();
	}

	/**
		Embaralha as cartas atuais 
	*/

	public  void embaralhar(){
		
		cartasAtuais = new Stack();
		Random r = new Random();
		
		while(cartasAtuais.size() != NUMERO_CARTAS){
			int proximoItemX = Math.abs(r.nextInt() % 4);
			int proximoItemY = Math.abs(r.nextInt() % 14);
			Carta carta = cartasBaralho[proximoItemX][proximoItemY];
			if(!(cartasAtuais.contains(carta))){
				cartasAtuais.push(carta);
			}
			
		}
		
		if(ControleJogo.DEBUG_MODE){
			ControleJogo.exibirDebugMsg("*****Baralho>>Embaralhar*****");
			imprimirCartasAtuais();
			ControleJogo.exibirDebugMsg("*****************************");
		}

		System.gc();				
	}

	/** 
		Reseta o baralho
	*/
	public void resetar(){
		ControleJogo.exibirDebugMsg("Baralho>>resetar()");

		cartasAtuais = new Stack();

		for(byte i = Carta.PAUS; i <= Carta.OUROS; i++){
			for(byte j = Carta.CORINGA; j <= Carta.AS; j++){

				cartasAtuais.push(cartasBaralho[i][j]);
			}
		}

		
		System.gc();		
	}


	/**
		Distribui as cartas para os jogadores
	*/

	public void distribuirCartas(Jogador[] jogadores){
		ControleJogo.exibirDebugMsg("Baralho>>distribuirCartas()");
		if(jogadores.length != ControleJogo.NUMERO_JOGADORES){
			ControleJogo.exibirDebugMsg("Baralho>>distribuirCartas>>Nº de jogadores inválido>>" + ControleJogo.NUMERO_JOGADORES);
			throw new IllegalArgumentException("Número de joadores inválido!");
		}		

		ControleJogo.exibirDebugMsg("Baralho>>distribuirCartas()>>Começando a distribuir cartas");

		//Distribuindo cartas aos jogadores
		for(byte i = 0; i < ControleJogo.NUMERO_JOGADORES; i++){
			for(byte j = 0; j < CARTAS_PARA_CADA_JOGADOR; j++){
				Carta carta = null;
				try{
					carta = (Carta) cartasAtuais.pop();
				}catch(EmptyStackException e){
					ControleJogo.exibirDebugMsg("Baralho>>distribuirCartas>>Impossível distribuir cartas, baralho está vazio");
				}
				if(carta != null){
					jogadores[i].adicionarCarta(carta);
				}else{
					ControleJogo.exibirDebugMsg("Baralho>>distribuirCartas>>carta = null");
				}
			}
		}
		ControleJogo.exibirDebugMsg("Baralho>>distribuirCartas()>>Cartas distribuídas com sucesso");

	}

	/**
		Retorna a carta no topo do baralho

	*/

	public Carta cartaAtual(){
		Carta carta = null;
		try{
			carta = (Carta) cartasAtuais.peek();
		}catch(EmptyStackException e){
			ControleJogo.exibirDebugMsg("Baralho>>cartaAtual>>baraalho está vazio");
			throw e;
		}
		
		return carta;	

	}

	/**
		Desenha a interface do baralho
	*/

	public void  paint(Graphics g){
		g.setColor(0, 190, 0);
		g.fillRect(0, 0, Dispositivo.getLarguraTela(), Dispositivo.getAlturaTela());
		
		try{
			Carta carta = cartaVerso;
			
			for(byte n = 0; n < 5; n++){
				carta.setPosition(((Dispositivo.getLarguraTela() - ( (carta.getWidth() * 2) + 10) ) / 2) + n,
					  (Dispositivo.getAlturaTela() - carta.getHeight()) / 2 + n);
			
				carta.paint(g);
			}
		
			if(!(cartasAtuais.isEmpty())){
				 g.setColor(0, 0, 255);
				
				 g.drawString("Carta Atual" , (Dispositivo.getLarguraTela() - Font.getDefaultFont().stringWidth("Carta Atual")) / 2, 0, 0);
					
				 g.setColor(255,255,255);	
				 g.drawString(cartaAtual().getNaipeStr(), 
					     (Dispositivo.getLarguraTela() - Font.getDefaultFont().stringWidth(cartaAtual().getNaipeStr())) / 2
						, Font.getDefaultFont().getHeight(), 0);
				 g.drawString(cartaAtual().getValorStr(), 
					     (Dispositivo.getLarguraTela() - Font.getDefaultFont().stringWidth(cartaAtual().getValorStr())) / 2

						, Font.getDefaultFont().getHeight() * 2, 0);
				
				if(cartaAtual().getValor() == Carta.VALETE){
					Ticker tk = getTicker();
					String naipeProximaJogada = "PRÓXIMO NAIPE:";
					
					switch(ControleJogo.NAIPE_ESCOLHIDO){
						case Carta.COPAS:
							naipeProximaJogada += "COPAS";
							break;
						case Carta.ESPADAS:
							naipeProximaJogada += "ESPADAS";
							break;
 						case Carta.PAUS:
							naipeProximaJogada += "PAUS";
							break;
						case Carta.OUROS:
							naipeProximaJogada += "OUROS";
							break;
					}
			
				 	g.drawString(naipeProximaJogada, 
					     (Dispositivo.getLarguraTela() - Font.getDefaultFont().stringWidth(naipeProximaJogada)) / 2

						, Font.getDefaultFont().getHeight() * 3, 0);

					
				}
			}
			

			carta = cartaAtual();
			carta.setPosition( ((Dispositivo.getLarguraTela() - ( (carta.getWidth() * 2) + 10) ) / 2) + carta.getWidth() + 10, 
					   (Dispositivo.getAlturaTela() - carta.getHeight()) / 2);
			carta.paint(g);

			//Seta de cima apontando para baixo (próximo jogador)
			Jogador.imgSeta.setTransform(Sprite.TRANS_ROT270);
			Jogador.imgSeta.setPosition( Dispositivo.getCentroX() , Dispositivo.getAlturaTela() - Jogador.imgSeta.getHeight() - 2 + deslocamentoSetaBaixo);
			Jogador.imgSeta.paint(g);
			//Boneco
			Jogador.imgJogador.setPosition( Dispositivo.getCentroX() - Jogador.imgJogador.getWidth() , Dispositivo.getAlturaTela() - Jogador.imgJogador.getHeight() - 12);
			Jogador.imgJogador.paint(g);
		

		}catch(Exception e){
			System.out.println("ERRO: " + e);
		}
	}

	/**
		Muda a tela de volta
	*/
	public void setTelaVolta(Displayable d){
		telaVolta = d;
	}


	/**
		Adiciona uma carta ao baralho
	*/
	public void adicionarCarta(Carta carta){
		cartasAtuais.push(carta);
		repaint();
	}
	
	/**
		Retorna uma carta para compra
	*/
	public Carta comprarCarta() throws EmptyStackException{
		ControleJogo.exibirDebugMsg("Baralho>>comprarCarta()>>retornando carta");
		Carta carta = null;
		if(cartasAtuais.size() >= 3){
			int indice = Math.abs((new Random().nextInt() % (cartasAtuais.size() - 1)));
			ControleJogo.exibirDebugMsg("Baralho>>comprarCarta()>>ìndice: " + indice);
			carta = (Carta)cartasAtuais.elementAt(indice);
			cartasAtuais.removeElementAt(indice);
		}else if(cartasAtuais.size() == 2){
			carta = (Carta)cartasAtuais.elementAt(cartasAtuais.size()-2);	
			cartasAtuais.removeElementAt(cartasAtuais.size()-2);
		}else{
			carta = ((Carta)cartasAtuais.pop());
		}
		return carta;	

	}

	/** 
		Retorna a quantidade de cartas que o baralho possui atualmente
	*/

	public int numeroCartas(){
		return cartasAtuais.size();
	}

	/**
		Retorna uma carta do baralho pelo seu índice
		
	*/
	
	public Carta carta(int indice){
		if(indice < 0 || indice > cartasAtuais.size())
			throw new IllegalArgumentException("Baralho>>carta>>Índice inválido!");
		return (Carta)cartasAtuais.elementAt(indice);
	}


	/**
		Deixa o baralho sem nenhuma carta
	*/
	
	public void zerar(){
		ControleJogo.exibirDebugMsg("Baralho>>zerar()");

		cartasAtuais = new Stack();
		
		System.gc();		
	}


}
