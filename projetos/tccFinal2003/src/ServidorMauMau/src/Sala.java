/*
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia

Rog�rio de Paula Aguilar 8NA
Luiz Fernando P.S. Forgas 8NA

Trabalho de Conclus�o de Curso:
T�cnicas de desenvolvimento de Jogos para dispositivos m�veis

Pacote: jogo.rede
Classe: Sala

Descri��o: Representa uma sala no servidor, onde
os jogadores se encontram para jogar


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 21/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando thread que controla o timeout de in�cio de jogo
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 26/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando mensagem que detecta o fim do jogo
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 04/11/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Arrumando thread de timeout
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


*/

package jogo.rede;

import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;


/**
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia

<p>Rog�rio de Paula Aguilar 8NA
<p>Luiz Fernando P.S. Forgas 8NA

<p>Trabalho de Conclus�o de Curso:
T�cnicas de desenvolvimento de Jogos para dispositivos m�veis

<p>Pacote: jogo.rede
<p>Classe: Sala

<p>Descri��o: Representa uma sala no servidor, onde
os jogadores se encontram para jogar

*/

public class Sala implements MontarArvore{

	/**
		N�mero de jogadores na sala
	*/
	public static final int NUMERO_JOGADORES = 4;
	
	/**
		Jogadores da sala
	*/
	private JogadorRede jogadores[] = new JogadorRede[NUMERO_JOGADORES];
	
	/**
		Primeiro jogador que entrou na sala
	*/
	private JogadorRede primeiroJogador;
	
	/**
		Id da sala
	*/
	private String id;	
	
	/**
		Jogadores ativos
	*/
	private int jogadoresAtivos = 0;
		
	/**
		Indica se existe algum jogo em andamento nesta sala
	*/
	private boolean JOGANDO = false;
	
	/**
		Objeto servidor
	*/
	private Servidor servidor;	
	
	/**
		Controla o tempo m�ximo de espera pelo in�cio de um novo jogo
	*/
	private Thread timeout;
	
	/**
		Controla o tempo m�ximo de espera pelo final de uma partida	
	*/

	private Thread timeoutJogo;
	
	/**
		Vari�vel que controla a sincroniza��o do jogo nos clientes
	*/
	private int ackAtualizacoes = 0; //Para sincronizar as atualiza��es nos clientes
	
	
	/**
		Incrementa a vari�vel ackAtualizacoes
	*/
	public synchronized void addAck(){
		ackAtualizacoes++;
		
	}

	/**
		Faz ackAtualizacoes = 0
	*/
	public synchronized void zerarAck(){
		ackAtualizacoes = 0;
		
	}

	/**
		Retorna o valor de ackAtualizacoes
	*/
	public synchronized int getAck(){
		return ackAtualizacoes;
	}


	/**
		Classe que controla o timeout para o in�cio do jogo

	*/
	private class threadTimeout implements Runnable{
		private int TIMEOUT = 60000 * 15;//15 minutos		
	
	
		public void run(){
			TIMEOUT = (int)(60000 * servidor.getTimeout());
			if(TIMEOUT <= 0) TIMEOUT = 60000 * 15;
			try{
				servidor.log("Sala>>" + id + ">>Inicializando thread timeout>>TIMEOUT: " + (TIMEOUT/1000) + " segundos.");
				Thread.sleep(TIMEOUT);
				synchronized(Sala.this){
					if(!Sala.this.getJogando()){
						servidor.log("Sala>>" + id + ">>TIMEOUT: Tempo para o in�cio do jogo expirado>>Retirando jogadores e reinicializando sala");
						Sala.this.enviarMsgBroadcast("ERRO: O tempo para o in�cio de jogo expirou. Voc� foi desconectado pelo servidor. Reinicializando jogo.");
						Sala.this.resetar();
						servidor.atualizarArvore();  
					}

				}
			}catch(Exception e){
				servidor.log("Sala>>" + id + ">>Erro em threadTimeout>>" + e);
				servidor.atualizarArvore();
			}
		}

	}


	/**
		Classe que controla o tempo limite de dura��o de um jogo
	*/
	private class threadTimeoutJogo implements Runnable{
		private int TIMEOUT = 60000 * 60;//1 hora
	
		public void run(){
			TIMEOUT = (int)(60000 * servidor.getTimeoutJogo());
			if(TIMEOUT <= 0) TIMEOUT = (int)60000 * 60;			
			try{
				servidor.log("Sala>>" + id + ">>Inicializando thread de timeout do JOGO>>TIMEOUT: " + (TIMEOUT/1000) + " segundos.");
				Thread.sleep(TIMEOUT);
				synchronized(Sala.this){
					if(Sala.this.getJogando()){
						servidor.log("Sala>>" + id + ">>TIMEOUT: Tempo para o t�rmino do jogo expirado>>Retirando jogadores e reinicializando sala");
						Sala.this.enviarMsgBroadcast("ERRO: O tempo para o t�rmino de jogo expirou. Voc� foi desconectado pelo servidor. Reinicializando jogo.");
						Sala.this.resetar();
						servidor.atualizarArvore();  
					}

				}
			}catch(Exception e){
				servidor.log("Sala>>" + id + ">>Exception em threadTimeoutJogo>>" + e);
				servidor.atualizarArvore();
			}
		}

	}

	
	
	/**
	*/
	void notifyObservers(String str){
		System.out.println(str);
	}

	/**
		Construtor
	*/
	public Sala(String id, Servidor servidor){
		//notifyObservers("Criando sala. ID = " + id);
		this.id = id.trim();
		this.servidor = servidor;
	}

	/**
		Modifica o valor da vari�vel JOGANDO
	*/	
	public synchronized void setJogando(boolean jogando){
		JOGANDO = jogando;
		if(JOGANDO){
			if(timeout != null) timeout.interrupt();
			if(timeoutJogo != null) timeoutJogo.interrupt();
			timeoutJogo = new Thread(new threadTimeoutJogo());
			timeoutJogo.start();
				
		}else{
			if(timeoutJogo != null) timeoutJogo.interrupt();
		}
	}

	/**
		Retorna o valor da vari�vel JOGANDO
	*/
	public synchronized boolean getJogando(){
		return JOGANDO;
	}
	
	/**
		Reseta a sala, desconectando todos que estiverem nela
	*/
	public synchronized void resetar(){
		servidor.log("Sala>>" + id + ">>Resetando sala");
		for(byte i = 0; i < NUMERO_JOGADORES; i++){
			if(jogadores[i] != null){			
				try{
					notifyObservers("Sala>>ID = " + id + ">>Fechando jogador " + i);
					jogadores[i].desconectarUsuario();
					jogadores[i].fechar();

				}catch(Exception e){
					notifyObservers("\nSala>>ID = " + id + ">>ERRO AO FECHAR JOGADOR " + i + ": " + e);	
				}
				jogadores[i] = null;			

			}

		}

		jogadoresAtivos = 0;
		JOGANDO = false;
		if(timeout != null) timeout.interrupt();
		if(timeoutJogo != null) timeoutJogo.interrupt();
		ackAtualizacoes = 0;
		servidor.atualizarArvore();
		notifyObservers("Sala>>ID = " + id + ">>Sala resetada");
		
	}

	/**
		Retorna o id da sala
	*/
	public String getId(){
		return id;
	}	
	

	/**
		Adiciona um jogador na sala

	*/
	public synchronized boolean adicionarJogador(JogadorRede jogador) throws JogadorDuplicadoException, NumeroJogadoresExcedidoException, JogandoException{
		
		if(jogador == null){
			throw new IllegalArgumentException("Jogador deve ser diferente de null");
		}
		if(JOGANDO){
			jogador.setManterConexao(false);
			throw new JogandoException("Sala>>ID = " + id + ">>N�o � poss�vel adicionar jogador. Jogo em andamento");
		}
		for(byte i = 0; i < NUMERO_JOGADORES; i++){
			
			if(jogadores[i] != null && jogadores[i].equals(jogador)){
				notifyObservers("Sala>>ID = " + id + ">>N�o � poss�vel adicionar jogador " + jogador + ". J� existe um jogador com este nome");				
				jogador.setManterConexao(false);
				throw new JogadorDuplicadoException("Sala>>ID = " + id + ">>N�o � poss�vel adicionar jogador " + jogador + ". J� existe um jogador com este nome");


			}

		}

		if(jogadoresAtivos == NUMERO_JOGADORES){
			notifyObservers("Sala>>ID = " + id + ">>N�o � poss�vel adicionar jogador " + jogador + ". N�mero de jogadores excedido.");
			jogador.setManterConexao(false);
			throw new NumeroJogadoresExcedidoException("Sala>>ID = " + id + ">>N�o � poss�vel adicionar jogador " + jogador + ". N�mero de jogadores excedido.");

		}else{
			if(jogadoresAtivos == 0){
				primeiroJogador = jogador;
				//servidor.log("INICIALIZANDO THREAD TIMEOUT");
				timeout = new Thread(new threadTimeout());
				timeout.start();
			} 
			//primeiroJogador.enviarMensagem("ENTRADA_JOGADOR" + jogador.getNome()); 
			
			
			jogadores[jogadoresAtivos++] = jogador;	
			jogador.iniciarThreadLeitura();		
			
		}
		if(jogadoresAtivos == 1) 
			return true;
		return false;
	}


	/**
		Remove os jogadores que n�o est�o ativos
	*/
	private void removerNulls(){
		for(byte i = 0; i < NUMERO_JOGADORES; i++){
			if(jogadores[i] == null && i < NUMERO_JOGADORES - 1){
				jogadores[i] = jogadores[i+1];
				jogadores[i+1] = null;
			}
		}
		
	}

	/**
		Remove um jogador da sala
	*/

	public synchronized void removerJogador(JogadorRede jogador) throws JogadorNaoEncontradoException{
		if(jogador == null)
			throw new IllegalArgumentException("Sala>> ID = " + id + ">>removerJogador>>Jogador deve ser diferente de null");
		for(byte i = 0; i < NUMERO_JOGADORES; i++){
			
			if(jogadores[i] != null && jogadores[i].equals(jogador)){
			   notifyObservers("Sala>>ID = " + id + ">>Removendo jogador:" + jogador);
			   /*try{
				jogadores[i].fechar();
			   }catch(Exception e){

			   }*/
			   jogadores[i] = null;
			   jogadoresAtivos--;
			   if(jogadoresAtivos == 0){ 
				setJogando(false);
				timeout.interrupt();
			   }
			   removerNulls();
			   servidor.atualizarArvore();
			   return;					
			}

		}


		throw new JogadorNaoEncontradoException("Sala>> ID = " + id + ">>removerJogador: " + jogador + ". Jogador n�o encontrado.");
		
	}	

	/**
		Retorna o n�mero de jogadores ativos
	*/
	public synchronized int getJogadoresAtivos(){
		return jogadoresAtivos;
	}
	

	/**
		Retorna um jogador pelo seu �ndice
	*/
	public JogadorRede getJogador(int id){
		if(id >= NUMERO_JOGADORES)
			throw new IllegalArgumentException("getJogador>>�ndice inv�lido");
		else
			return jogadores[id];		
		
	}

	/**
		Retorna uma representa��o da sala num objeto String
	*/	
	public String toString(){
		StringBuffer buffer = new StringBuffer("Sala>>ID = " + id + "\n");
		for(int i = 0; i < NUMERO_JOGADORES; i++){
			if(jogadores[i] != null)
				buffer.append(">>>>" + jogadores[i] + "\n");
			else
				buffer.append(">>>>Jogador " + i  + " = vazio\n");
				
		}
		buffer.append("Jogadores ativos: " + jogadoresAtivos);

		return buffer.toString().trim();
	}

	/**
		Retorna o n� da �rvore de salas correspondente a esta sala
	*/	
	public DefaultMutableTreeNode retornarNo(JTree arvore){
		DefaultMutableTreeNode sala = new DefaultMutableTreeNode(getId());
		DefaultMutableTreeNode jogadoresAtivos = new DefaultMutableTreeNode("Jogadores Ativos: " + getJogadoresAtivos());
		servidor.addUltimoNoSala(jogadoresAtivos);
		
		sala.add(jogadoresAtivos);
		for(int i = 0; i < NUMERO_JOGADORES; i++){
			if(jogadores[i] != null){
				DefaultMutableTreeNode no = new DefaultMutableTreeNode(jogadores[i].toString());
				no.add(new DefaultMutableTreeNode("IP: " + jogadores[i].getIp()));
				no.add(new DefaultMutableTreeNode("PORTA: " + jogadores[i].getPorta()));
				sala.add(no);
			

			}
			else
				sala.add(new DefaultMutableTreeNode("Jogador " + i + ": vazio"));

		}
		
		
		
		
		return sala;
	}


	/**
		Modifica o primeiro jogador
	*/
	public void setPrimeiroJogador(JogadorRede primeiroJogador){
		this.primeiroJogador = primeiroJogador;
	}
	
	/**
		Retorna o primeiro jogador
	*/
	public JogadorRede getPrimeiroJogador(){
		return primeiroJogador;
	}

	/**
		Envia uma mensagem a todos os jogadores da sala
	*/
	public synchronized void enviarMsgBroadcast(String msg){
		for(int i = 0; i < NUMERO_JOGADORES; i++){
			if(jogadores[i] != null)
				jogadores[i].enviarMensagem(msg);
				
		}
		
	}
	

	



	/**
		Adiciona uma entrada ao log do servidor
	*/
	public void log(String str){
		servidor.log(str);
	}

	/**
		Retorna o servidor associado com esta sala
	*/
	public Servidor getServidor(){
		return servidor;
	}

	/**	
		Retorna a thread que controla o tempo limite de fim de jogo
	*/	

	public synchronized Thread getThreadTimeoutJogo(){
		return timeoutJogo;
	
	}
	

}
