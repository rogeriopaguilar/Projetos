/*
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 01/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Criação da classe
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 14/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Adicionando comando para comprar carta do baralho
	   Adicionando comando para dizer mau mau	
	   Modificando rotinas de debug para utilizarem o método exibirDebugMsg da
		classe ControleJogo
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 19/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Adicionando rotina de tratamento de commandos

Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 09/10/2003
Responsável: Rogério de Paula Aguilar
Descrição: Adicionando rotina para verificar se o usuário falou "mau-mau"
	   Adicionando rotinas de simulação do fim do jogo.
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 20/10/2003
Responsável: Rogério de Paula Aguilar
Descrição: Adicionando rotina de jogo on-line
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 26/10/2003
Responsável: Rogério de Paula Aguilar
Descrição: Arrumando mensagens
	   Criando lógica para penalidades

Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


*/

package jogo;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.util.*;
import jogo.apibasica.*;



/**
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
<p>Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
<p>Classe: jogo.JogadorHumano
<p>Responsabilidades: Guarda as informações sobre o jogador que está utilizando o dispositivo móvel

@author Rogério de Paula Aguilar
@version  1.0

*/
public class JogadorHumano extends Jogador implements CommandListener{
	
	/**
		Classe que guarda informações sobre o dispositivo
	*/
	private Dispositivo dispositivo;
	
	/**
		Comando utilizado para comprar uma carta
	*/
	private Command cmdComprarCarta = new Command("Comprar Carta", Command.SCREEN, 0);
	
	/**
		Comando que o usuário seleciona quando quer dizer mau-mau
	*/
	private Command cmdDizerMauMau = new Command("Dizer \"Mau Mau \"", Command.SCREEN, 0);
	
	/**
		Comando para compra de três ou mais cartas
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
		Adiciona o comando de compra de cartas na tela do usuário
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
		Chama o método verificarJogada de ControleJogo para verificar a jogada, exibe 
		uma mensagem para o usuário<br> e envia a atualização para o servidor, caso o jogo seja on-line
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
		Método invocado quando o usuário pressiona alguma tecla
	*/
	public void keyPressed(int keyCode){
	       synchronized(controleJogo){		
		super.keyPressed(keyCode);

		
		if(getKeyCode(FIRE) == keyCode){
			if(controleJogo.getJogadorAtual() != controleJogo.getJogadorExibido()){
				ControleJogo.exibirDebugMsg("JogadorHumano>>keyPressed>>Jogador está exibindo cartas de outro jogador");
			}else{
				jogar(null);
			}
		}



		repaint();
	       }

	}

	/**
		Desenha a interface gráfica do jogador
	*/
	public  void paint(Graphics g){
		super.paint(g);		
		posicao = 12;

		if( ( (cartasAtuais.size() + 2) * 12) < dispositivo.getLarguraTela()){
			ControleJogo.exibirDebugMsg("Jogador Humano>>paint>>Ajustando posição: nº de cartas < tamTela");
			posicao = (dispositivo.getLarguraTela() - ( (cartasAtuais.size() + 2) * 12)) / 2;
		}else{
			if( ((cartaSelecionada + 2) * 12) + posicao > dispositivo.getLarguraTela()){
				ControleJogo.exibirDebugMsg("Jogador Humano>>paint>>Ajustando posição: nº de cartas > tamTela");
				
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
				g.drawString("Nº de Cartas: " + cartasAtuais.size(), (Dispositivo.getLarguraTela() - Font.getDefaultFont().stringWidth("Nº de Cartas: " + cartasAtuais.size())) / 2, 
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
					mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " comprou uma carta e receberá mais três por ter dito mau-mau na hora errada. O próximo jogador será: " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
					   AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()) );
					for(byte i = 0; i < 4; i++)
						adicionarCarta(baralho.comprarCarta());
			
				}else{
					mensagem = controleJogo.new Mensagem("O jogador  " + getNome() + " comprou uma carta. O próximo jogador será: " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
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
				

					mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " comprou " + controleJogo.getQuantidadeCompra() + " cartas e receberá mais três por ter dito mau-mau na hora errada. O próximo jogador será: " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
					   AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()) );
									

				}else{
					mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " comprou " + controleJogo.getQuantidadeCompra() + " cartas. O próximo jogador será: " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
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