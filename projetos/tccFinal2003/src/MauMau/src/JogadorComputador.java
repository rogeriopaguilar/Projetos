/*
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 05/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Criação da classe
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 08/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Arrumando rotina de exibição
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 14/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Começando rotina jogar()
	   Criando thread que fará a jogada pelo computador
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 18/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Implementando rotina de jogo
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 19/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Implementando rotina de jogo (jogar)
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 09/10/2003
Responsável: Rogério de Paula Aguilar
Descrição: Testando rotina de jogo
	   Alterando mensagens para se tornarem mais claras para o jogador
	   Inserindo jogada 8* que estava faltando
	   Adicionando rotina para verificar se o jogador disse mau-mau ou não	
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 10/10/2003
Responsável: Rogério de Paula Aguilar
Descrição: Arrumando interface gráfica
	   Resolvendo problemas de sincronismo da thread que joga pelo computador

Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 12/11/2003
Responsável: Rogério de Paula Aguilar
Descrição: Corrigindo problema com a thread de jogada

Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 03/12/2003
Responsável: Rogério de Paula Aguilar
Descrição: Arrumando mensagens e adicionando jogada Valete

Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


*/


package jogo;
import jogo.apibasica.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.util.*;



/**
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
<p>Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
<p>Classe: jogo.JogadorComputador
<p>Responsabilidades: Guarda as informações sobre um jogador controlado pelo computador e realiza a
			a jogada do computador
@author Rogério de Paula Aguilar
@version  1.0

*/


public class JogadorComputador extends Jogador{


	/**
		Classe que faz a jogada pelo computador
	*/
	class threadJogada implements Runnable{

			/**
				Índice do jogo atual
			*/			

			private int indiceJogoAtual;

			/**
				Modifica o índice do jogo atual
			*/
			public void setJogoAtual(int jogoAtual){
				indiceJogoAtual = jogoAtual;
			}

			/**
				Método que faz a jogada pelo computador
			*/   	
			public void run(){
						try{
							
						Thread.sleep(JogadorComputador.this.ESPERA);
							ControleJogo.exibirDebugMsg("**********JogadorCOmputador>>jogar()>>threadJogada>>Continuando jogada**********");
							
							ControleJogo.Mensagem mensagem = null;

							int indiceCarta = -1;
							try{
								/*Verificando jogada: 7* */
								ControleJogo.exibirDebugMsg("JogadorComputador>>Testando se carta do baralho é um sete...");
								if(baralho.cartaAtual().getValor() == Carta.SETE && !controleJogo.getPenalidadeAplicada()){
								   ControleJogo.exibirDebugMsg("JogadorComputador>>A carta atual  é um sete>>Procurando sete");
								   indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.PAUS][Carta.SETE]);
								   if(indiceCarta == -1) indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.OUROS][Carta.SETE]);
								   if(indiceCarta == -1) indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.COPAS][Carta.SETE]);
								   if(indiceCarta == -1) indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.ESPADAS][Carta.SETE]);
								   if(indiceCarta == -1){
									ControleJogo.exibirDebugMsg("JogadorComputador>>A carta atual  é um sete>>Procurando sete>>sete não encontrado>>procurando 2 do mesmo naipe");
									indiceCarta = getIndiceCarta(Baralho.cartasBaralho[baralho.cartaAtual().getNaipe()][Carta.DOIS]);
									if(indiceCarta == -1){
										ControleJogo.exibirDebugMsg("JogadorComputador>>A carta atual  é um sete>>Procurando sete>>sete não encontrado>>2 do mesmo naipe não encontrado");
										ControleJogo.exibirDebugMsg("JogadorComputador>>A carta atual  é um sete>>Procurando sete>>sete não encontrado>>2 do mesmo naipe não encontrado>>Procurando um coringa");
										indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.OUROS][Carta.CORINGA]);
										if(indiceCarta == -1) 	indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.PAUS][Carta.CORINGA]);
										if(indiceCarta == -1) 	indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.COPAS][Carta.CORINGA]);
										if(indiceCarta == -1) 	indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.ESPADAS][Carta.CORINGA]);
										if(indiceCarta == -1){
											ControleJogo.exibirDebugMsg("JogadorComputador>>A carta atual  é um sete>>Procurando sete>>sete não encontrado>>Coringa não encontrado>>Comprando cartas");
								        		for(int i = 0; i < controleJogo.getQuantidadeCompra(); i++){
												adicionarCarta(baralho.comprarCarta());
											}
											mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " comprou " + controleJogo.getQuantidadeCompra() + " cartas. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
												AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
											
											controleJogo.setPenalidadeAplicada(true);
											controleJogo.setQuantidadeCompra((byte)0);
								    		}else{
											ControleJogo.exibirDebugMsg("JogadorComputador>>A carta atual  é um sete>>Procurando sete>>sete não encontrado>>Coringa encontrado");
								        		mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um coringa, que anula o efeito de qualquer carta especial e pode ser jogado sobre qualquer carta. O coringa anulou o efeito do sete anterior. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
												AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
											baralho.adicionarCarta((Carta)cartasAtuais.elementAt(indiceCarta));
											cartasAtuais.removeElementAt(indiceCarta);
											controleJogo.setQuantidadeCompra((byte)0);
								    		

										}
									}else{
										ControleJogo.exibirDebugMsg("JogadorComputador>>A carta atual  é um sete>>Procurando dois do mesmo naipe>>dois encontrado");
										baralho.adicionarCarta((Carta)cartasAtuais.elementAt(indiceCarta));
										cartasAtuais.removeElementAt(indiceCarta);
										controleJogo.setQuantidadeCompra((byte)0);
								        	mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um dois que anulou o efeito do sete anterior. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
											AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
								    	
									}
								}else{
									ControleJogo.exibirDebugMsg("JogadorComputador>>A carta atual  é um sete>>Sete encontrado");
								   	baralho.adicionarCarta((Carta)cartasAtuais.elementAt(indiceCarta));
									cartasAtuais.removeElementAt(indiceCarta);
									controleJogo.setPenalidadeAplicada(false);
									controleJogo.setQuantidadeCompra((byte)(controleJogo.getQuantidadeCompra() + 3));
								        mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um sete. O próximo jogador deve comprar" + controleJogo.getQuantidadeCompra() + " cartas ou jogar outro 7, um valete, um dois do mesmo naipe ou um coringa. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
										AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
								    }


								}else{
								   /*Verificando jogada: 9* */	
								   ControleJogo.exibirDebugMsg("JogadorComputador>>Verificando se a carta atual é um nove");
								   if(baralho.cartaAtual().getValor() == Carta.NOVE && !controleJogo.getPenalidadeAplicada()){
									ControleJogo.exibirDebugMsg("JogadorComputador>>Verificando se a carta atual é um nove>>Carta atual é um nove>>Procurando por um dois do naipe " + baralho.cartaAtual().toString());
									indiceCarta = getIndiceCarta(baralho.cartasBaralho[baralho.cartaAtual().getNaipe()][Carta.DOIS]);
									if(indiceCarta == -1){
									   ControleJogo.exibirDebugMsg("JogadorComputador>>Verificando se a carta atual é um nove>>Carta atual é um nove>>Procurando por um dois do naipe>>DOIS NÃO ENCONTRADO>>Procurando um coringa");
									   indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.OUROS][Carta.CORINGA]);
									   if(indiceCarta == -1) indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.PAUS][Carta.CORINGA]);
									   if(indiceCarta == -1) indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.COPAS][Carta.CORINGA]);
									   if(indiceCarta == -1) indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.ESPADAS][Carta.CORINGA]);
									   if(indiceCarta == -1){
										ControleJogo.exibirDebugMsg("JogadorComputador>>Verificando se a carta atual é um nove>>Carta atual é um nove>>Procurando por um dois do naipe>>DOIS NÃO ENCONTRADO>>Procurando um coringa>>Coringa não encontrado");
									      	adicionarCarta(baralho.comprarCarta());
									   	controleJogo.setQuantidadeCompra((byte)0);
										controleJogo.setPenalidadeAplicada(true);	
									   	mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " comprou uma carta, pois não tinha um 2 do mesmo do 9 que estava no baralho para se defender e não possuia um coringa. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
											AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
									   }else{
										ControleJogo.exibirDebugMsg("JogadorComputador>>Verificando se a carta atual é um nove>>Carta atual é um nove>>Procurando por um dois do naipe>>DOIS NÃO ENCONTRADO>>Procurando um coringa>>CORINGA ENCONTRADO");
									   	mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um coringa, que anula o efeito de qualquer carta especial e pode ser jogado sobre qualquer carta. O coringa anulou o efeito do 9 anterior. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
											AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
										baralho.adicionarCarta((Carta)cartasAtuais.elementAt(indiceCarta));
										cartasAtuais.removeElementAt(indiceCarta);
										controleJogo.setQuantidadeCompra((byte)0);
								    		

									   }
								   		
									}else{
									   ControleJogo.exibirDebugMsg("JogadorComputador>>Verificando se a carta atual é um nove>>Carta atual é um nove>>Procurando por um dois do naipe>>DOIS ENCONTRADO");
									   baralho.adicionarCarta((Carta)cartasAtuais.elementAt(indiceCarta));
									   cartasAtuais.removeElementAt(indiceCarta);	
									   controleJogo.setQuantidadeCompra((byte)0);	
									   mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um 2 do mesmo naipe do nove anterior, anulando o efeito deste. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
										AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));


									}
								   }else{

									/*Verificando jogada: 10 de PAUS*/
									if(baralho.cartaAtual().getNaipe() == Carta.PAUS && baralho.cartaAtual().getValor() == Carta.DEZ && !controleJogo.getPenalidadeAplicada()){
										ControleJogo.exibirDebugMsg("JogadorComputador>>A carta atual  é 10p >>Procurando um 2 de Paus");
								   		indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.PAUS][Carta.DOIS]);
								   		if(indiceCarta == -1){
									        	indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.OUROS][Carta.CORINGA]);
									   		if(indiceCarta == -1) indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.PAUS][Carta.CORINGA]);
									   		if(indiceCarta == -1) indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.COPAS][Carta.CORINGA]);
									   		if(indiceCarta == -1) indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.ESPADAS][Carta.CORINGA]);
									   		if(indiceCarta == -1){
												ControleJogo.exibirDebugMsg("JogadorComputador>>A carta atual  é 10p>>Procurando um 2 de paus>>2 de paus não encontrado>>Comprando 5 cartas");
												for(int i = 0; i < controleJogo.getQuantidadeCompra(); i++){
													adicionarCarta(baralho.comprarCarta());
										
												}
												mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " comprou " + controleJogo.getQuantidadeCompra() + " cartas. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
													AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
												controleJogo.setPenalidadeAplicada(true);
												//controleJogo.setQuantidadeCompra((byte)0);
											

											}else{
												ControleJogo.exibirDebugMsg("JogadorComputador>>Verificando se a carta atual é um 10p>>Carta atual é um 10p>>Procurando por um dois do naipe>>DOIS NÃO ENCONTRADO>>Procurando um coringa>>CORINGA ENCONTRADO");
									   			mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um coringa, que anula o efeito de qualquer carta especial e pode ser jogado sobre qualquer carta. O coringa anulou o efeito do 10 de paus anterior. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
													AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
												baralho.adicionarCarta((Carta)cartasAtuais.elementAt(indiceCarta));
												cartasAtuais.removeElementAt(indiceCarta);
												controleJogo.setQuantidadeCompra((byte)0);
								    		

								   			}


								    		}else{
											ControleJogo.exibirDebugMsg("JogadorComputador>>A carta atual  é um 10p>>Dois de paus encontrado>>Efeito do 10 anulado");
								   			baralho.adicionarCarta((Carta)cartasAtuais.elementAt(indiceCarta));
											cartasAtuais.removeElementAt(indiceCarta);
											controleJogo.setQuantidadeCompra((byte)0);
								        		mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um 2 de paus, o que anulou o efeito do 10 de paus. A quatidade de cartas para compra agora é: " + controleJogo.getQuantidadeCompra() + " cartas. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
												AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
								    		}

									}else{	
										ControleJogo.exibirDebugMsg("JogadorComputador>>Carta do baralho não é especial");
								   		ControleJogo.exibirDebugMsg("JogadorComputador>>ProcurandoCarta>>Carta atual do baralho: " + baralho.cartaAtual().toString());
										boolean encontrou = false;
										indiceCarta = -1;
										
										ControleJogo.exibirDebugMsg("JogadorComputador>>Procurando carta>>Procurando um AS");
										//Procurando um AS
										
										if(indiceCarta == -1) 
											if(baralho.cartaAtual().getNaipe() == Carta.OUROS)
													indiceCarta = getIndiceCarta(baralho.cartasBaralho[Carta.OUROS][Carta.AS]);
										
										if(indiceCarta == -1) 
											if(baralho.cartaAtual().getNaipe() == Carta.PAUS)
													indiceCarta = getIndiceCarta(baralho.cartasBaralho[Carta.PAUS][Carta.AS]);
										if(indiceCarta == -1) 
											if(baralho.cartaAtual().getNaipe() == Carta.COPAS)
													indiceCarta = getIndiceCarta(baralho.cartasBaralho[Carta.COPAS][Carta.AS]);
										if(indiceCarta == -1) 
											if(baralho.cartaAtual().getNaipe() == Carta.ESPADAS)
													indiceCarta = getIndiceCarta(baralho.cartasBaralho[Carta.ESPADAS][Carta.AS]);

										if(indiceCarta != -1){
											ControleJogo.exibirDebugMsg("JogadorComputador>>Procurando carta>>Procurando um AS>>As encontrado");
											mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um AS e poderá jogar novamente. Realizando nova jogada.", 
													AlertType.INFO, controleJogo.getJogador(controleJogo.getJogadorAtual()));
												
										        baralho.adicionarCarta((Carta)cartasAtuais.elementAt(indiceCarta));
											cartasAtuais.removeElementAt(indiceCarta);
												
										}else{
											indiceCarta = -1;
											if(baralho.cartaAtual().getNaipe() == Carta.PAUS){
												ControleJogo.exibirDebugMsg("JogadorComputador>>Naipe atual é PAUS>>Procurando um 10 de paus");
												indiceCarta = getIndiceCarta(baralho.cartasBaralho[Carta.PAUS][Carta.DEZ]);
												if(indiceCarta != -1){
													ControleJogo.exibirDebugMsg("JogadorComputador>>Naipe atual é PAUS>>Procurando um 10 de paus>>Dez de paus encontrado");
													baralho.adicionarCarta((Carta)cartasAtuais.elementAt(indiceCarta));
													cartasAtuais.removeElementAt(indiceCarta);
													controleJogo.setPenalidadeAplicada(false);
													controleJogo.setQuantidadeCompra((byte)5);
													mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um 10 de PAUS. O próximo jogador deverá comprar " + controleJogo.getQuantidadeCompra() + " cartas, jogar um 2 de PAUS, um coringa ou um valete. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
														AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
										
										
											
												}
											}
											if(indiceCarta == -1){
												//Procurando um sete do naipe atual
												indiceCarta = -1;
												ControleJogo.exibirDebugMsg("JogadorComputador>>Procurando um 7 do naipe atual");
												indiceCarta = getIndiceCarta(baralho.cartasBaralho[baralho.cartaAtual().getNaipe()][Carta.SETE]);
												if(indiceCarta != -1){
													ControleJogo.exibirDebugMsg("JogadorComputador>>Procurando um 7 do naipe atual>>7 encontrado");
													baralho.adicionarCarta((Carta)cartasAtuais.elementAt(indiceCarta));
													cartasAtuais.removeElementAt(indiceCarta);
													controleJogo.setPenalidadeAplicada(false);
													controleJogo.setQuantidadeCompra((byte)(controleJogo.getQuantidadeCompra() + 3));
													mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um 7. O próximo jogador deverá comprar " + controleJogo.getQuantidadeCompra() + " cartas, jogar um outro 7, um dois do mesmo naipe do sete, um coringa ou um valete. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
														AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()) );
										
												}else{
													//Procurando um quatro de espadas, caso o naipe atual seja espadas		
													indiceCarta = -1;
													if(baralho.cartaAtual().getNaipe() == Carta.ESPADAS){
														indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.ESPADAS][Carta.QUATRO]);
														if(indiceCarta != -1){
															//4 de espadas encontrado
															baralho.adicionarCarta( (Carta)cartasAtuais.elementAt(indiceCarta) );
															cartasAtuais.removeElementAt(indiceCarta);
																
															for(int i = 0 ; i < ControleJogo.NUMERO_JOGADORES; i++){
																if( ! ( controleJogo.getJogador(i).equals(this)) ){
																	controleJogo.getJogador(i).adicionarCarta(baralho.comprarCarta());
																}	
															}	
															mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um 4 de espadas e, como consequência, todos ou outros jogadores compraram uma carta. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
																AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
			    								
														}		
													}
													
													if(indiceCarta == -1){	
														
														//Se o próximo jogador tem um número menor de cartas que o jogador anterior, tentar inverter o sentido do jogo
														int proximoJogador = controleJogo.getIndiceProximoJogador();
														int jogadorAnterior = controleJogo.getIndiceJogadorAnterior();
														if(controleJogo.getJogador(proximoJogador).numeroCartas() < controleJogo.getJogador(jogadorAnterior).numeroCartas()){
															indiceCarta = getIndiceCarta(Baralho.cartasBaralho[baralho.cartaAtual().getNaipe()][Carta.CINCO]);
															if(indiceCarta != -1){
																//Cinco encontrado
																baralho.adicionarCarta( (Carta)cartasAtuais.elementAt(indiceCarta) );
																cartasAtuais.removeElementAt(indiceCarta);
																controleJogo.inverterSentidoJogo();
																mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um 5 e inverteu o sentido do jogo. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
																AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
			    								
															}	
														}	

														if(indiceCarta == -1){
	
															
															//Procurando um  9, que faz o próximo jogador comprar uma carta

															
															indiceCarta = getIndiceCarta(Baralho.cartasBaralho[baralho.cartaAtual().getNaipe()][Carta.NOVE]);
															if(indiceCarta != -1){
																//9 encontrado
																baralho.adicionarCarta( (Carta)cartasAtuais.elementAt(indiceCarta) );
																cartasAtuais.removeElementAt(indiceCarta);
																controleJogo.setPenalidadeAplicada(false);
																mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um 9. O próximo jogador deve comprar uma carta, jogar um 2 do mesmo naipe do nove, um coringa ou um valete. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
																	AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
			    								
															
															}
															
															if(indiceCarta == -1){
																//Tentando encontrar um Rei, que faz o jogador anterior comprar uma carta
																indiceCarta = getIndiceCarta(Baralho.cartasBaralho[baralho.cartaAtual().getNaipe()][Carta.REI]);
																if(indiceCarta != -1){
																	controleJogo.getJogador(controleJogo.getIndiceJogadorAnterior()).adicionarCarta(baralho.comprarCarta());
			    														baralho.adicionarCarta( (Carta)cartasAtuais.elementAt(indiceCarta) );
																	cartasAtuais.removeElementAt(indiceCarta);
																	mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um Rei e, como consequência, o jogador anterior comprou uma carta. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
																		AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
											


																}										
															}			
	
															if(indiceCarta == -1){
																//Se o próximo jogador tem menos que 5 cartas, tenta encontrar uma dama para pular o jogador			
																if(controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).numeroCartas() < 5 && baralho.cartaAtual().getValor() != Carta.DAMA){
																	indiceCarta = getIndiceCarta(Baralho.cartasBaralho[baralho.cartaAtual().getNaipe()][Carta.DAMA]);
																	if(indiceCarta != -1){
																		baralho.adicionarCarta( (Carta)cartasAtuais.elementAt(indiceCarta) );
																		cartasAtuais.removeElementAt(indiceCarta);
																		controleJogo.proximoJogador();
																		mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou uma Dama e, como consequência, o próximo jogador foi pulado. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
																			AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
											

																	}										
																							
																}				
															}	
															
															if(indiceCarta == -1){			
																//Procurando carta do mesmo naipe
										
																for(int i = Carta.PRIMEIRO_VALOR ; (i <= Carta.ULTIMO_VALOR) && !encontrou; i++){
																	indiceCarta = getIndiceCarta(baralho.cartasBaralho[baralho.cartaAtual().getNaipe()][i]);
																	if(indiceCarta != -1){
																		encontrou = true;
												
																	}
																}	
																if(!encontrou){
																	//Procurando carta do mesmo valor
																	for(int i = Carta.PRIMEIRO_NAIPE; (i <= Carta.ULTIMO_NAIPE) && (!encontrou); i++){
																		indiceCarta = getIndiceCarta(baralho.cartasBaralho[i][baralho.cartaAtual().getValor()]);
																		if(indiceCarta != -1){
																			encontrou = true;
												
																		}
											
																	}		
																}
																if(!encontrou){
																	//Se não encontrou carta de mesmo valor ou naipe, procura um coringa
																	indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.OUROS][Carta.CORINGA]);
									   								if(indiceCarta == -1) indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.PAUS][Carta.CORINGA]);
									   								if(indiceCarta == -1) indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.COPAS][Carta.CORINGA]);
									   								if(indiceCarta == -1) indiceCarta = getIndiceCarta(Baralho.cartasBaralho[Carta.ESPADAS][Carta.CORINGA]);
									   		
																	if(indiceCarta == -1){					
								   										mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " comprou 1 carta. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
																			AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
																		adicionarCarta(baralho.comprarCarta());
																	}else{
																		//Coringa encontrado
																		mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um coringa, que anula o efeito de qualquer carta especial e pode ser jogado sobre qualquer carta. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
																			AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
																		baralho.adicionarCarta((Carta)cartasAtuais.elementAt(indiceCarta));
																		cartasAtuais.removeElementAt(indiceCarta);
																		controleJogo.setQuantidadeCompra((byte)0);
								    		
																	
																	}
												   			
																}else{
																	if( ((Carta)cartasAtuais.elementAt(indiceCarta)).getValor() == Carta.DAMA){
																		String nomeJogadorPulado = controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome();
																		controleJogo.proximoJogador();
																		mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou uma Dama e, como consequência, o próximo jogador foi pulado. O próximo jogador será: " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
																		AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
																	}else
																	if( ((Carta)cartasAtuais.elementAt(indiceCarta)).getValor() == Carta.CINCO){
																		controleJogo.inverterSentidoJogo();
																		mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um 5 e alterou o sentido do jogo. O próximo jogador será: " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
																		AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
																	
																	}else{
																		if( ((Carta)cartasAtuais.elementAt(indiceCarta)).getValor() == Carta.CORINGA ){
																			mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um coringa, que pode ser jogado sobre qualquer carta e anula o efeito de qualquer carta especial. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
																		 			AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
																	
																		}else{
																			if( ((Carta)cartasAtuais.elementAt(indiceCarta)).getValor() == Carta.OITO){
																				//Verifica se a carta escolhida foi um oito
																				if(controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).numeroCartas() > 1){
																					ControleJogo.exibirDebugMsg("JogadorComputador>>VerificarJogada(8*)>>Comprando uma carta do próximo jogador");
																					adicionarCarta(controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).removerCarta());
																					mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um 8. Com esta jogada, ele está recebendo uma carta do próximo jogador e pode jogar novamente!", 
																					AlertType.INFO, controleJogo.getJogador(controleJogo.getJogadorAtual()));
						

																				}else{
																					ControleJogo.exibirDebugMsg("JogadorComputador>>VerificarJogada(8(*))>>Próximo jogador possui apenas uma carta...");
																					mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " jogou um 8. Com esta jogada, ele estaria recebendo uma carta do próximo jogador e poderia jogar novamente, porém o próximo jogador possui apenas uma carta. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
																					AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
						
																						

																				}	

																			}else{	
																				mensagem = controleJogo.new Mensagem("O jogador " + getNome() + " fez uma jogada legal e não jogou nenhuma carta especial. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ". Movendo para o próximo jogador.", 
																					AlertType.INFO, controleJogo.getJogador(controleJogo.proximoJogador()));
																			}		
																		}
																	}
																	baralho.adicionarCarta((Carta)cartasAtuais.elementAt(indiceCarta));
																	cartasAtuais.removeElementAt(indiceCarta);
																		   		

																}
															}	
														}
													}
												}
											}
										}
									  }
								    }			

								}

										
									
							}catch(EmptyStackException e){
								ControleJogo.exibirDebugMsg("JogadorComputador>>jogar()>>Verificando jogada>>Baralho está vazio!");
								mensagem = JogadorComputador.this.controleJogo.elegerVencedor();
							}
							JogadorComputador.this.setDisseMauMau(false);
							if(ControleJogo.JOGO_EM_ANDAMENTO){ //Verifica se o jogador ainda está jogando	
								ControleJogo.exibirDebugMsg("JogadorComputador>>Verificando andamento>>JOGO_EM_ANDAMENTO>>" + ControleJogo.JOGO_EM_ANDAMENTO);
								if(numeroCartas() > 0){
									 //Fazendo o computador dizer mau-mau ou não de acordo com o número de cartas
								 	if(numeroCartas() == 1) setDisseMauMau( ControleJogo.getNumeroAleatorio() > 5);
								 	if(numeroCartas() == 1 && getDisseMauMau() == false){
										mensagem.setString(mensagem.getString() + " O jogador possui apenas uma carta e não disse mau-mau, portanto ele comprou três cartas. O próximo jogador será " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + ".");
								 		try{
											for(byte i = 0; i < 3; i++)
												adicionarCarta(baralho.comprarCarta());
										}catch(EmptyStackException e){

											ControleJogo.exibirDebugMsg("JogadorComputador>>jogar()>>Verificando jogada (disse mau-mau)>>Baralho está vazio!");
											mensagem = JogadorComputador.this.controleJogo.elegerVencedor();

										}catch(Exception e){
											ControleJogo.exibirDebugMsg("JogadorComputador>>Verificando Disse Mau-Mau>>Erro>> " + e);
										}
								 	}

								 	if(indiceJogoAtual == controleJogo.getJogoAtual())
										CartasMidlet.getDisplay().setCurrent(mensagem);
									else
										controleJogo.exibirDebugMsg("JogadorComputador>>threadJogada>>THREAD É DO JOGO ANTERIOR");
								}else{
								 	if(indiceJogoAtual == controleJogo.getJogoAtual())
										controleJogo.setVencedor(JogadorComputador.this);
									else
										controleJogo.exibirDebugMsg("JogadorComputador>>threadJogada>>THREAD É DO JOGO ANTERIOR");
								
								}
									
							}
							ControleJogo.exibirDebugMsg("*******************************************************");
							
						}catch(Exception e){
							ControleJogo.exibirDebugMsg("JogadorComputador>>jogar()>>ERRO: " + e);

						}
			

					}
			







	}

	public threadJogada jogada = new threadJogada();
	

	/**
		Construtor
	*/
	public JogadorComputador(String nome, Baralho baralho, ControleJogo controleJogo){
		super(nome, baralho, controleJogo);
	}	


	/**
		Método invocado para realizar a jogada do computador
	*/
	public void jogar(Object arg[]){
		if(ControleJogo.ONLINE){
			/*
				Rotina de jogo para jogo ONLINE -->Nesta versão o jogador computador
				não participa do jogo on-line
			*/
			ControleJogo.exibirDebugMsg("JogadorComputador>>AGUARDANDO JOGADA DO PRóXIMO JOGADOR");
		}else{
			//Rotina de jogo para jogo OFFLINE
			ControleJogo.exibirDebugMsg("JogadorComputador>>jogar()");
			jogada.setJogoAtual(controleJogo.getJogoAtual());
			Thread thread = new Thread(jogada);
			thread.start();
			
			System.gc();
		}
	}

	/**
		Desenha a interface gráfica do jogador
	*/
	public void paint(Graphics g){
		super.paint(g);		

		if(ControleJogo.getOpenMauMau()){
			//Desenha as cartas
			posicao = 12;

			if( ( (cartasAtuais.size() + 2) * 12) < Dispositivo.getLarguraTela()){
				if(ControleJogo.DEBUG_MODE)
			   		System.out.println("Jogador Humano>>paint>>Ajustando posição: nº de cartas < tamTela");
			
				posicao = (Dispositivo.getLarguraTela() - ( (cartasAtuais.size() + 2) * 12)) / 2;
			}else{
				if( ((cartaSelecionada + 2) * 12) + posicao > Dispositivo.getLarguraTela()){
					if(ControleJogo.DEBUG_MODE)
					System.out.println("Jogador Humano>>paint>>Ajustando posição: nº de cartas > tamTela");
					posicao = - ( ((cartaSelecionada + 2) * 12) - Carta.LARGURA_CARTA );
				}
		
			}
		
			for(int i = 0 ; i < cartasAtuais.size(); i++, posicao += 12){
				Carta carta = (Carta)cartasAtuais.elementAt(i);
				if(i != cartaSelecionada){
					carta.setPosition(posicao, (Dispositivo.getAlturaTela() - carta.getHeight()) / 2);
				}else{
					carta.setPosition(posicao, ((Dispositivo.getAlturaTela() - carta.getHeight()) / 2) - 15);
			
				}
				carta.paint(g);
			}


				
		}else{
			//Exibir apenas o fundo da carta e o número decartas
			Baralho.cartaVerso.setPosition(((Dispositivo.getLarguraTela() - Baralho.cartaVerso.getWidth()) / 2) ,
							(Dispositivo.getAlturaTela() - Baralho.cartaVerso.getHeight()) / 2);
			Baralho.cartaVerso.paint(g);
		}
		
		g.setColor(255,255,255);
		g.drawString("Nº de Cartas: " + cartasAtuais.size(), (Dispositivo.getLarguraTela() - Font.getDefaultFont().stringWidth("Nº de Cartas: " + cartasAtuais.size())) / 2, 
				(Dispositivo.getCentroY() + Baralho.cartaVerso.getHeight()/2) + 1, 0);	
		g.drawString(getNome(), 
			     (Dispositivo.getLarguraTela() - Font.getDefaultFont().stringWidth(getNome()) ) / 2,
				(Dispositivo.getCentroY() + Baralho.cartaVerso.getHeight()/2) + 1 + Font.getDefaultFont().getHeight() + 1,0);

				

	}

	/**
		Método invocado quando o jogador pressiona alguma tecla
	*/
	public void keyPressed(int keyCode){
		synchronized(controleJogo){
		super.keyPressed(keyCode);
		repaint();
		}
	}

}