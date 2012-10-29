
/*
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia

Rog�rio de Paula Aguilar 8NA
Luiz Fernando P.S. Forgas 8NA

Trabalho de Conclus�o de Curso:
T�cnicas de desenvolvimento de Jogos para dispositivos m�veis

Pacote: jogo.rede
Classe: JogadorRede

Descri��o: Guarda as informa��es sobre os jogadores
da rede.
*/

package jogo.rede;

import java.io.*;
import java.net.*;

/**
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia

<p>Rog�rio de Paula Aguilar 8NA
<p>Luiz Fernando P.S. Forgas 8NA

<p>Trabalho de Conclus�o de Curso:
T�cnicas de desenvolvimento de Jogos para dispositivos m�veis

<p>Pacote: jogo.rede
<p>Classe: JogadorRede

<p>Descri��o: Guarda as informa��es sobre os jogadores
da rede.

@author Rog�rio de Paula Aguilar
@version 1.0
*/

public class JogadorRede implements java.io.Serializable{

	/**
		Nome do jogador
	*/	
	private String nome;
	
	/**
		Objeto utilizado para enviar mensagens para o jogador
	*/
	private PrintWriter writer;
	
	/**
		Objeto utilizado para ler mensagens que vem do cliente
	*/
	private BufferedReader reader;
	
	/**
		Indica se a conex�o deve ser mantida
	*/
	private boolean MANTER_CONEXAO;
	
	/**
		IP da aplica��o cliente
	*/
	private String IP;
	
	/**
		Porta da aplica��o cliente
	*/
	private String PORTA;
	
	/**
		Objeto utilizado para estabelecer a comunica��o
	*/
	private Socket socket;
	
	/**
		Sala associada com este jogador
	*/
	private Sala sala;

	/**
		Classe que fica esperando por comandos da aplica��o cliente e processa estes comandos
	*/
	private Thread threadLeitura = new Thread(){
		public void run(){
			System.out.println("Jogador:" + getNome() + ">>Inicializando thread de leitura.");
				
			while(JogadorRede.this.MANTER_CONEXAO){
				try{
					String str = reader.readLine();
					synchronized(JogadorRede.this){
					if(str != null){
						sala.log("Jogador:" + getNome() + ">>MENSAGEM RECEBIDA: " + str);
					
						if(str.startsWith("SAIR_CLIENTE")){
							Thread threadEnviarSaida = new Thread(){
								public void run(){
									synchronized(sala){
										retirarJogadorSala();
										sala.enviarMsgBroadcast("SAIR_SALA: Jogador " + getNome() + " saiu da sala. O jogo foi cancelado.");	
										fechar();
										sala.resetar();
									}
								
								}
							};
							try{
								threadEnviarSaida.start();
								threadEnviarSaida.join(300000);
							}catch(Exception e){
							}
								
						}else if(str.startsWith("ST")){
							synchronized(sala){
								sala.setJogando(true);

								int indiceJN = str.indexOf("JN");
								int quantJogadoresStatus = 0;
								while(indiceJN != -1){
									quantJogadoresStatus++;
									if(indiceJN + 1 < str.length())
										indiceJN = str.indexOf("JN", indiceJN + 1 );
									else
										indiceJN = -1;
								}
								if(quantJogadoresStatus < sala.getJogadoresAtivos()){
									sala.log("Sala>>" + sala.getId() + ">>Jogador>>" + getNome() + ">>Quantidade de jogadores na mensagem de status != JogadoresAtivos>>Recome�ando jogo");
									StringBuffer buffer = new StringBuffer();
									for(int i = 0; i < sala.NUMERO_JOGADORES; i++){
											if(sala.getJogador(i) != null){
												buffer.append("[Jogador: " + sala.getJogador(i).getNome() + "]");
											}
									}
									sala.log("Enviando lista de jogadores para o cliente para recome�o de jogo: " + buffer.toString().trim());
									sala.getPrimeiroJogador().enviarMensagem("RECOMECO_ENTRADA_JOGADOR" + buffer.toString().trim());	

								}else{ 
									sala.log("Sala>>" + sala.getId() + ">>Jogador>>" + getNome() + ">>Quantidade de jogadores na mensagem de status: " + quantJogadoresStatus);
									sala.log("Sala>>" + sala.getId() + ">>Jogador>>" + getNome() + ">>Quantidade de jogadores na sala:" + sala.getJogadoresAtivos());

									//if(!sala.getJogando()){

									//}
									sala.log("Sala>>" + sala.getId() + ">>Jogador:" + getNome() + ">>MENSAGEM DE STATUS RECEBIDA: " + str);
									sala.enviarMsgBroadcast(str);
								}								
							}
						}else if(str.startsWith("VENCEDOR_JOGO:")){
							class threadVencedor implements Runnable{
								private String str;
								private Sala sala;			
						
								public threadVencedor(String str, Sala sala){
									this.str = str;
									this.sala = sala;
								}	
								
								public void run(){								

									sala.log("Sala>>" + sala.getId() + ">>Jogador:" + getNome() + ">>VENCEDOR DETECTADO:" + str);
									retirarJogadorSala();
									sala.enviarMsgBroadcast(str);
									fechar();								
									sala.resetar();
								}
							}
							try{
								Thread tV = new Thread(new threadVencedor(str, sala));
								tV.start();
								tV.join(300000);
							}catch(Exception e){
							}
								


						}else if(str.startsWith("ACKATUALIZACAO")){
							synchronized(sala){
								sala.addAck();
								if(sala.getAck() == sala.getJogadoresAtivos()){
									sala.zerarAck();
									sala.log("Sala>>" + sala.getId() + ">>Jogador:" + getNome() + ">>Confirma��o de atualiza��es nos clientes");
									sala.enviarMsgBroadcast("PODE_JOGAR");
								}
							
							}
						}					

					}
					else if(socket != null && !(socket.isConnected())){

						System.out.println("Jogador>>" + getNome() + ">>Fechando conex�o");
						synchronized(sala){
							retirarJogadorSala();
							sala.enviarMsgBroadcast("SAIR_SALA: Jogador " + getNome() + " saiu da sala. O jogo foi cancelado.");	
							fechar();
							sala.resetar();
						}				

					}
					Thread.sleep(100);
				  }
				}catch(Exception e){
					sala.log("Jogador:" + getNome() + ">>ERRO NA THREAD DE LEITURA: " + e);
					synchronized(sala){
						retirarJogadorSala();
						sala.enviarMsgBroadcast("SAIR_SALA: Jogador " + getNome() + " saiu da sala. O jogo foi cancelado.");	
						fechar();
						sala.resetar();
					}
						
				}
				
			     	
			}
		
			System.out.println("Jogador:" + getNome() + ">>Encerrando thread de leitura.");
			
		}
	};


	/**
		Construtor. N�o inicializa a thread de leitura. Esta deve ser inicializada manualmente
		atrav�s do m�todo iniciarThreadLeitura
	*/	
	public JogadorRede(String nome, BufferedReader reader, PrintWriter writer, Sala sala, Socket socket) throws IOException{
		this.nome = nome.toUpperCase().trim();
		MANTER_CONEXAO = true;
		if(reader == null || writer == null)
			throw new IllegalArgumentException("JogadorRede>>Socket deve ser diferente de null");
		
		//Thread threadInicializar = new Thread(){
			this.socket = socket;
			this.reader = reader;
			this.writer = writer;

			this.PORTA = new Integer(socket.getPort()).toString();
			this.IP = socket.getInetAddress().getHostAddress();
		//}
		//threadLeitura.start();
		this.sala = sala;
					
	}

	
	/**
		Inicializa a thread de leitura de comandos.
	*/
	public void iniciarThreadLeitura(){
		threadLeitura.start();
		try{
			socket.setSoTimeout(180000); //Servidor pode ficar no m�ximo 3 minutos sem receber mensagens do cliente
		}catch(Exception e){
			sala.log("Sala>>" + sala.getId() + ">>Jogador>>" + getNome() + ">>Erro ao setar timeout para o socket>>" + e);
		}
	}


	/**
		Retorna o nome do jogador
	*/
	public String getNome(){
		return nome;
	}
	
	/**
		Retorna o ip da aplica��o cliente
	*/
	public String getIp(){
		return IP;
	}

	/**
		Retorna a porta da aplica��o cliente
	*/
	public String getPorta(){
		return PORTA;
	}

	/**
		Retorna se duas inst�ncias desta classe s�o iguais
	*/
	public boolean equals(Object obj){
		return (obj instanceof JogadorRede) && ((JogadorRede)obj).nome.equals(this.nome);
	}

	/**
		Retorna um inteiro para ser utilizado com �ndice de uma tabela hash
	*/
	public int hashCode(){
		return nome.hashCode();
	}

	/**
		Fecha a conex�o com o jogador
	*/
	public void fechar() {
		MANTER_CONEXAO = false;
		
		
		
		try{
			if(socket != null) socket.close();
		}catch(Exception e2){}

		
	}

	/**
		Envia uma mensagem para a aplica��o cliente indicando que o servidor ir� desconectar o usu�rio
	*/	
	public void desconectarUsuario(){
		try{
			if(writer != null){
				 writer.println("SAIR_SERVIDOR");
		}
		}catch(Exception e2){}
		
	}

	/**
		Remove o jogador da sala. OBS: N�o envia mensagem para o jogador. Sempre utilizar o m�todo<br>
		enviarMensagem para informar a aplica��o cliente que o jogador est� sendo retirado da sala<br>
		antes de chamar este m�todo. 
	*/
	public void retirarJogadorSala(){
		try{
			sala.removerJogador(this);
		}catch(Exception e){
			System.out.println("Erro ao remover jogador: " + e);	
		}
		
	}
	
	/**
		Retorna uma representa��o deste jogador num objeto String
	*/
	public String toString(){
		return "JogadorRede: " + getNome();
	}


	/**
		Modifica o valor de Manter conex�o
	*/
	public synchronized void setManterConexao(boolean b){
		MANTER_CONEXAO = b;
	}
	
	/**
		Retorna o valor de Manter conex�o
	*/
	public synchronized boolean getManterConexao(){
		return MANTER_CONEXAO;
	}

	/**
		Envia uma mensagem para o cliente
	*/
	public synchronized void enviarMensagem(String str){
		try{
			writer.println(str);
			writer.flush();
		}catch(Exception erro){
			/*retirarJogadorSala();
			sala.enviarMsgBroadcast("SAIR_SALA: Jogador " + getNome() + " saiu da sala. O jogo foi cancelado.");	
			fechar();
			sala.resetar();*/
			sala.log("Sala>>" + sala.getId() + ">>Jogador>>" + getNome() + ">>Erro ao enviar mensagem para o jogador>>" + erro);
		}
	}

	

	

}