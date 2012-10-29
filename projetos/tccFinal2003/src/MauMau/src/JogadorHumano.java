/*
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 01/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Cria��o da classe
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 14/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando comando para comprar carta do baralho
	   Adicionando comando para dizer mau mau	
	   Modificando rotinas de debug para utilizarem o m�todo exibirDebugMsg da
		classe ControleJogo
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 19/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando rotina de tratamento de commandos

Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 09/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando rotina para verificar se o usu�rio falou "mau-mau"
	   Adicionando rotinas de simula��o do fim do jogo.
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 20/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando rotina de jogo on-line
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 26/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Arrumando mensagens
	   Criando l�gica para penalidades

Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


*/

package jogo;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.util.*;
import jogo.apibasica.*;



/**
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
<p>Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
<p>Classe: jogo.JogadorHumano
<p>Responsabilidades: Guarda as informa��es sobre o jogador que est� utilizando o dispositivo m�vel

@author Rog�rio de Paula Aguilar
@version  1.0

*/
public class JogadorHumano extends Jogador implements CommandListener{
	
	/**
		Classe que guarda informa��es sobre o dispositivo
	*/
	private Dispositivo dispositivo;
	
	/**
		Comando utilizado para comprar uma carta
	*/
	private Command cmdComprarCarta = new Command("Comprar Carta", Command.SCREEN, 0);
	
	/**
		Comando que o usu�rio seleciona quando quer dizer mau-mau
	*/
	private Command cmdDizerMauMau = new Command("Dizer \"Mau Mau \"", Command.SCREEN, 0);
	
	/**
		Comando para compra de tr�s ou mais cartas
	*/
	private Command cmdComprarTresCartas;
		

	/**
		Simula o fim de jogo
	*/
	public void simularFimDeJogo(){
		cartasAtuais = new Stack();
		cartasAtuais.push(baralho.cartasBaralho[Carta.COPAS][Carta.CORINGA]);
		cartasAtuais.push(baralho.cartasBaralho[Carta.PAUS][Carta.DOIS]);

	}

	/**
		Construtor
	*/
	public JogadorHumano(String nome, Dispositivo dispositivo, Baralho baralho, ControleJogo controleJogo){
		super(nome, baralho, controleJogo);
		this.dispositivo = dispositivo;
		addCommand(cmdComprarCarta);
		addCommand(cmdDizerMauMau);
		setCommandListener(this);

	}	

	/**
		Adiciona o comando de compra de cartas na tela do usu�rio
	*/
	public void setComandoCompra(byte tipo){
		//0 --> Adiciona commando para comprar n cartas
		//1 --> Adiciona commando para comprar 1 carta
		removeCommand(cmdComprarTresCartas);
		removeCommand(cmdComprarCarta);
		if(tipo == 0){
			cmdComprarTresCartas = new Command("Comprar " + ControleJogo.getQuantidadeCompra() + " Cartas.", Command.SCREEN, 0);
			addCommand(cmdComprarTresCartas);
		}else{
			addCommand(cmdComprarCarta);

		}
		if(numeroCartas() > 1) addCommand(cmdDizerMauMau);
		cartaSelecionada = 0;
		
	}

	/**
		Chama o m�todo verificarJogada de ControleJogo para verificar a jogada, exibe 
		uma mensagem para o usu�rio<br> e envia a atualiza��o para o servidor, caso o jogo seja on-line
	*/
	public void jogar(Object args[]){
		ControleJogo.exibirDebugMsg("JogadorHumano>>jogar");	
		ControleJogo.Mensagem m = null;
		
		if(args == null){
			 m = controleJogo.verificarJogada(( (Carta)cartasAtuais.elementAt(cartaSelecionada) ), this);
		}else{
			if(args[0] instanceof ControleJogo.Mensagem){
				m = (ControleJogo.Mensagem)args[0];
			}
		}
		int strValete = m.getString().indexOf("jogou um valete");
		System.out.println("STRVALETE: " + strValete);
		if(strValete == -1 || args != null && args[0] instanceof ControleJogo.Mensagem){
			if(m.getType() != AlertType.ERROR)
				removerCarta(cartaSelecionada);
			m.setTimeout(ESPERA);


			//Verificando vencedor
			if(m.getType() != AlertType.ERROR && numeroCartas() == 0){
				controleJogo.exibirDebugMsg("JogadorHumano>>jogar>>JOGADOR HUMANO VENCEU!!!!!");
				m = controleJogo.new Mensagem("Fim de jogo. O vencedor foi: " + getNome()
				, AlertType.INFO, controleJogo);
				m.setTimeout(10000);
				controleJogo.setApresentacao(true);
				controleJogo.setTelaSelecao(true);
				controleJogo.enviarMsgServidor("VENCEDOR_JOGO:" + getNome());
				controleJogo.fecharConexaoSemConfirmacao();
			}
		
			if(controleJogo.ONLINE && m.getType() != AlertType.ERROR)
				m.setTimeout(300000);

			CartasMidlet.getDisplay().setCurrent(m);

			if(controleJogo.ONLINE && m.getType() != AlertType.ERROR && numeroCartas() != 0){
				controleJogo.enviarMsgServidor(controleJogo.montarStatusJogo(m, false));
				System.gc();
			}
		}		
		

	
	}

	/**
		M�todo invocado quando o usu�rio pressiona alguma tecla
	*/
	public void keyPressed(int keyCode){
	       synchronized(controleJogo){		
		super.keyPressed(keyCode);

		
		if(getKeyCode(FIRE) == keyCode){
			if(controleJogo.getJogadorAtual() != controleJogo.getJogadorExibido()){
				ControleJogo.exibirDebugMsg("JogadorHumano>>keyPressed>>Jogador est� exibindo cartas de outro jogador");
			}else{
				jogar(null);
			}
		}



		repaint();
	       }

	}

	/**
		Desenha a interface gr�fica do jogador
	*/
	public  void paint(Graphics g){
		super.paint(g);		
		posicao = 12;

		if( ( (cartasAtuais.size() + 2) * 12) < dispositivo.getLarguraTela()){
			ControleJogo.exibirDebugMsg("Jogador Humano>>paint>>Ajustando posi��o: n� de cartas < tamTela");
			posicao = (dispositivo.getLarguraTela() - ( (cartasAtuais.size() + 2) * 12)) / 2;
		}else{
			if( ((cartaSelecionada + 2) * 12) + posicao > dispositivo.getLarguraTela()){
				ControleJogo.exibirDebugMsg("Jogador Humano>>paint>>Ajustando posi��o: n� de cartas > tamTela");
				
				posicao = - ( ((cartaSelecionada + 2) * 12) - Carta.LARGURA_CARTA );
			}
		
		}

		Carta objcartaSelecionada = null;		
		for(int i = 0 ; i < cartasAtuais.size(); i++, posicao += 12){
			Carta carta = (Carta)cartasAtuais.elementAt(i);
			if(i != cartaSelecionada){
				carta.setPosition(posicao, (dispositivo.getAlturaTela() - carta.getHeight()) / 2);
		
			}else{
				carta.setPosition(posicao, ((dispositivo.getAlturaTela() - carta.getHeight()) / 2) - 15);
				objcartaSelecionada = carta;	
			}
				carta.paint(g);
		
		}
		//objcartaSelecionada.paint(g);

		g.setColor(0,0,255);
				g.drawString("N� de Cartas: " + cartasAtuais.size(), (Dispositivo.getLarguraTela() - Font.getDefaultFont().stringWidth("N� de Cartas: " + cartasAtuais.size())) / 2, 
							(Dispositivo.getCentroY() + Baralho.cartaVerso.getHeight()/2) + 1, 0);	
		
			g.drawString(getNome(), 
				     (Dispositivo.getLarguraTela() - Font.getDefaultFont().stringWidth(getNome()) ) / 2,
					(Dispositivo.getCentroY() + Baralho.cartaVerso.getHeight()/2) + 1 + Font.getDefaultFont().getHeight() + 1,0);

	}

	/**
		Procedimento chamado quando o jogador aciona algum comando
	*/
	public void commandAction(Command c, Displayable d){
		synchronized(controleJogo){
		ControleJogo.Mensagem mensagem = null;
		super.commandAction(c, d);
		if(controleJogo.getJogadorAtual() == controleJogo.getJogadorExibido()){
					

		   try{
			if(c == cmdComprarCarta){
				controleJogo.setPenalidadeAplicada(true);
			 	ControleJogo.exibirDebugMsg("JogadorHumano>>commandAction()>>Comprando uma carta");
				if(getDisseMauMau()){
					mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " comprou uma carta e receber� mais tr�s por ter dito mau-mau na hora errada. O pr�ximo jogador ser�: " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o pr�ximo jogador.", 
					   AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()) );
					for(byte i = 0; i < 4; i++)
						adicionarCarta(baralho.comprarCarta());
			
				}else{
					mensagem = controleJogo.new Mensagem("O jogador  " + getNome() + " comprou uma carta. O pr�ximo jogador ser�: " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o pr�ximo jogador.", 
					   AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()) );
					adicionarCarta(baralho.comprarCarta());
				}											
			}else if(c == cmdComprarTresCartas){
				ControleJogo.exibirDebugMsg("JogadorHumano>>commandAction()>>Comprando " + controleJogo.getQuantidadeCompra() + " cartas");
				for(int i = 0; i < controleJogo.getQuantidadeCompra(); i++){
					adicionarCarta(baralho.comprarCarta());
				}
				
				if(getDisseMauMau()){
					for(int i = 0; i < 3; i++){
						adicionarCarta(baralho.comprarCarta());
					}
				

					mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " comprou " + controleJogo.getQuantidadeCompra() + " cartas e receber� mais tr�s por ter dito mau-mau na hora errada. O pr�ximo jogador ser�: " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o pr�ximo jogador.", 
					   AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()) );
									

				}else{
					mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " comprou " + controleJogo.getQuantidadeCompra() + " cartas. O pr�ximo jogador ser�: " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o pr�ximo jogador.", 
					   AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()) );

				}
				controleJogo.setPenalidadeAplicada(true);
			 	
				/*if(controleJogo.getQuantidadeCompra() % 3 == 0)
					controleJogo.setQuantidadeCompra((byte)3);*/

			
			}else if(c == cmdDizerMauMau){
				setDisseMauMau(true);
				removeCommand(cmdDizerMauMau);

			}
		}catch(EmptyStackException e){
			mensagem = controleJogo.elegerVencedor();

		}
		//if(!ControleJogo.ONLINE)
			CartasMidlet.getDisplay().setCurrent(mensagem);
		if(ControleJogo.ONLINE && mensagem != null)
			controleJogo.enviarMsgServidor(controleJogo.montarStatusJogo(mensagem, false));

	  }
	 }
	}
}