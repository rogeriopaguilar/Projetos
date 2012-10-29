/*
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia

Rog�rio de Paula Aguilar 8NA
Luiz Fernando P.S. Forgas 8NA

Trabalho de Conclus�o de Curso:
T�cnicas de desenvolvimento de Jogos para dispositivos m�veis

Pacote: jogo.rede
Classe: Servidor

Descri��o: Servidor que gerencia as salas do jogo on-line


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 13/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Cria��o da classe
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 15/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Modifica��o do protocolo (http para socket)
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 24/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Arrumando interface gr�fica
	   Implementando a��es relacionadas aos bot�es
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 04/11/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Arrumando interface gr�fica
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


*/


package jogo.rede;

import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.text.*;
import java.io.*;
import java.net.*;
import javax.net.*;
import java.util.prefs.*;

/**
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia

<p>Rog�rio de Paula Aguilar 8NA
<p>Luiz Fernando P.S. Forgas 8NA

<p>Trabalho de Conclus�o de Curso:
<p>T�cnicas de desenvolvimento de Jogos para dispositivos m�veis

<p>Pacote: jogo.rede
<p>Classe: Servidor

Descri��o: Servidor que gerencia as salas do jogo on-line

@author Rog�rio de Paula Aguilar
@version 1.0

*/
public class Servidor implements MontarArvore{



	/**
		Classe que implementa a interface gr�fica com o usu�rio
	*/
	private class ServidorGUI extends JFrame implements Observer{

		/**
			Objeto servidor
		*/	
		private Servidor servidor;
		
		/**
			Objeto de interface gr�fica
		*/
		private JPanel painelStatus = new JPanel();
		
		/**
			Objeto de interface gr�fica
		*/
		private JLabel lblStatus = new JLabel("Pronto", SwingConstants.LEFT);
		
		/**
			Objeto de interface gr�fica que exibe o log da aplica��o
		*/
		private JTextArea txtLog = new JTextArea();
		
		/**
			Objeto de interface gr�fica que mostra uma �rvore contendo as salas e os usu�rio que et�o conectados nestas salas
		*/
		private JTree arvore;

		/**
			A��o que implementa o mecanismo de sair do servidor
		*/
		private class acaoSair extends AbstractAction{

			public acaoSair(String acao, Icon icon){
				super(acao, icon);
			}
			public void actionPerformed(ActionEvent evt){
					parar();
					System.exit(0);
			}
		}		


		/**
			A��o que implementa o mecanismo de atualiza��o da �rvore de salas
		*/
		
		private class acaoAtualizarArvore extends AbstractAction{

			public acaoAtualizarArvore(String acao, Icon icon){
				super(acao, icon);
			}
			public void actionPerformed(ActionEvent evt){
				servidorGUI.atualizarArvore();	
			}
		}
		

		/**
			A��o que implementa o mecanismo de limpar o log da aplica��o
		*/
		
		private class acaoLimparLog extends AbstractAction{

			public acaoLimparLog(String acao, Icon icon){
				super(acao, icon);
			}
			public void actionPerformed(ActionEvent evt){
				txtLog.setText("");	
			}
		}

		/**
			A��o que implementa o mecanismo de mudan�a de apar�ncia da interface gr�fica
		*/
		private class mudarLookAndFeel implements ActionListener{
			public void actionPerformed(ActionEvent evt){
				try{
					if(evt.getSource() == menuAparenciaGTK)
						UIManager.setLookAndFeel(lookAndFeel[0]);

					else if(evt.getSource() == menuAparenciaWINDOWS)
						UIManager.setLookAndFeel(lookAndFeel[2]);
					else if(evt.getSource() == menuAparenciaMetal)
						UIManager.setLookAndFeel(lookAndFeel[1]);

					SwingUtilities.updateComponentTreeUI(ServidorGUI.this);
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Erro ao mudar a apar�ncia! Erro:" + e);
				}
			}

		}

		/**
			Objeto a��o	
		*/
		private AbstractAction aSair = new acaoSair("Sair", new ImageIcon("imagens/sair.gif"));		
		
		/**
			Objeto a��o	
		*/
		private AbstractAction aAtualizarArvore = new acaoAtualizarArvore("Atualizar �rvore", new ImageIcon("imagens/arvore.gif"));		
		
		/**
			Objeto a��o	
		*/
		private AbstractAction aLimparLog = new acaoLimparLog("Limpar Log", new ImageIcon("imagens/limpar.gif"));		

		private mudarLookAndFeel mudar = new mudarLookAndFeel();		

		/**
			Objeto da interface gr�fica
		*/
		private JButton cmdSair = new JButton(aSair);
		
		/**
			Objeto da interface gr�fica
		*/
		private JMenuBar menus = new JMenuBar();
		
		/**
			Objeto da interface gr�fica
		*/
		private JMenu menuOpcoes = new JMenu("Op��es");
		
		/**
			Objeto da interface gr�fica
		*/
		private JMenuItem menuSobre = new JMenuItem("Sobre");
		
		/**
			Objeto da interface gr�fica
		*/
		private JMenuItem menuAtualizarArvore = new JMenuItem(aAtualizarArvore);
		
		/**
			Objeto da interface gr�fica
		*/
		private JMenuItem menuSair = new JMenuItem(aSair);
		
		/**
			Objeto da interface gr�fica
		*/
		private JToolBar toolbar = new JToolBar(SwingConstants.HORIZONTAL);
		
		/**
			Objeto da interface gr�fica
		*/
		private JMenuItem menuLimparLog = new JMenuItem(aLimparLog);
		
		/**
			Objeto da interface gr�fica
		*/
		private JMenu menuAparencia = new JMenu("Apar�ncia");
		
		/**
			Objeto da interface gr�fica
		*/
		private JRadioButtonMenuItem menuAparenciaGTK = new JRadioButtonMenuItem("Motif");
		
		/**
			Objeto da interface gr�fica
		*/
		private JRadioButtonMenuItem menuAparenciaWINDOWS = new JRadioButtonMenuItem("Windows");
		
		/**
			Objeto da interface gr�fica
		*/
		private JRadioButtonMenuItem menuAparenciaMetal = new JRadioButtonMenuItem("Metal", true);
		
		/**
			Objeto da interface gr�fica
		*/
		private ButtonGroup grupoBotoes = new ButtonGroup();
		
		/**
			Objeto da interface gr�fica
		*/
		private JScrollPane scrollLog;
		
		/**
			Apar�ncias
		*/
		
		private String lookAndFeel[] = new String[]{"com.sun.java.swing.plaf.motif.MotifLookAndFeel",
						    	  "javax.swing.plaf.metal.MetalLookAndFeel",
						    	  "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"};

	

		/**
			Construtor
		*/
		public ServidorGUI(Servidor servidor){

			//Configurando descri��o das a��es
			aSair.putValue(Action.SHORT_DESCRIPTION, "Sai da aplica��o");
			aAtualizarArvore.putValue(Action.SHORT_DESCRIPTION, "Atualiza a �rvore");
			aLimparLog.putValue(Action.SHORT_DESCRIPTION, "Limpa o log da aplica��o");
	

			//Configurando disposi��o da janela
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension d = tk.getScreenSize();
			setIconImage(tk.getImage("imagens/MauMau.png"));
			setSize(d.width, d.height);
			setTitle("Mau-Mau Server - 1.0");
			
			this.servidor = servidor;
			txtLog.setEditable(false);			

			painelStatus.setLayout(new FlowLayout());
			painelStatus.add(lblStatus, FlowLayout.LEFT);
			

			Container contentPane = getContentPane();
			//contentPane.setLayout(new GridBagLayout());
			contentPane.setLayout(new BorderLayout());
			
			//GridBagConstraints con = new GridBagConstraints();
			
			//Configurando e adicionando a toolbar
			toolbar.add(aAtualizarArvore);
			toolbar.add(aLimparLog);
			toolbar.add(aSair);
			
			/*con.weightx = 1.0;
			con.weighty = 1.0;

			con.gridx = 0;
			con.gridy = 0;
			con.gridwidth = 1;
			con.gridheight = 1;
			con.fill = GridBagConstraints.BOTH;
			contentPane.add(toolbar, con);*/
			contentPane.add(toolbar, BorderLayout.NORTH);

			

			//Configurando e adicionando a �rvore
			TreeNode raiz = servidor.retornarNo(arvore);
			arvore = new JTree(raiz);
			DefaultTreeSelectionModel modeloSelecao = new DefaultTreeSelectionModel();
			modeloSelecao.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			arvore.setSelectionModel(modeloSelecao); 
			JScrollPane scrollArvore = new JScrollPane(arvore);
				
			scrollArvore.setPreferredSize(new Dimension(getWidth() / 2 , getHeight()));
			//contentPane.add(scrollArvore, BorderLayout.WEST);
			

			JPanel painelLog = new JPanel();
			painelLog.setLayout(new BorderLayout());
			painelLog.add(new JLabel("Log:"), BorderLayout.NORTH);
			scrollLog = new JScrollPane(txtLog);
			//s.setAutoscrolls(true);  
			painelLog.setPreferredSize(new Dimension(getWidth(), getHeight()));
			
			painelLog.add(scrollLog, BorderLayout.CENTER);
			/*con.gridx = 5;
			con.gridy = 1;
			con.gridwidth = 4;
			con.gridheight = 9;
			con.fill = GridBagConstraints.BOTH;
			
			contentPane.add(painelLog, con);*/

			//contentPane.add(painelLog, BorderLayout.CENTER);

			 JSplitPane splitPane =
    				new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
          			scrollArvore, scrollLog);
			//splitPane.setContinuousLayout( true );
  			splitPane.setDividerLocation( getWidth()/2 - 200);
  			splitPane.setPreferredSize( 
    				new Dimension(10 , 10 ));

			contentPane.add(splitPane, BorderLayout.CENTER);
			

			JPanel painelBaixo = new JPanel();
			painelBaixo.setLayout(new FlowLayout(FlowLayout.RIGHT));
			painelBaixo.add(cmdSair);
			contentPane.add(painelBaixo, BorderLayout.SOUTH);
			


			grupoBotoes.add(menuAparenciaGTK);
			grupoBotoes.add(menuAparenciaWINDOWS);
			grupoBotoes.add(menuAparenciaMetal);

			//Menu

			menuOpcoes.add(menuAtualizarArvore);
			menuOpcoes.add(menuLimparLog);
			menuOpcoes.addSeparator();
			menuOpcoes.add(menuSair);
			menuAparencia.add(menuAparenciaGTK);
			menuAparencia.add(menuAparenciaWINDOWS);
			menuAparencia.add(menuAparenciaMetal);
			menuAparenciaGTK.addActionListener(mudar);
			menuAparenciaWINDOWS.addActionListener(mudar);
			menuAparenciaMetal.addActionListener(mudar);
			menus.add(menuOpcoes);
			menus.add(menuAparencia);
			
			
			menuSobre.addActionListener(

				new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						JOptionPane.showMessageDialog(null , "2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia\n\n >>Trabalho de Conclus�o de Curso:\nT�cnicas de Desenvolvimento de Jogos para Dispositivos M�veis\nServidor do jogo Mau-Mau - vers�o 1.0\n\n>>Alunos:\nRog�rio de Paula Aguilar - rogeriopaguilar@terra.com.br \nhttp:\\\\www.rogerioaguilar.cjb.net\n\nLuiz Fernando P.S. Forgas - luizforgas@terra.com.br\n\n\"O racioc�nio l�gico leva voc� de A a B. A imagina��o leva voc� a qualquer lugar\"\n\n","Sobre", JOptionPane.INFORMATION_MESSAGE, null);
					}

				}

			);
			JMenu mnuInt = new JMenu("?");
			menus.add(mnuInt);
			mnuInt.add(menuSobre);	
			setJMenuBar(menus);

			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent evt){
					parar();
					System.exit(0);
				}
	
			});
			
			//pack();
			setLocation( 0, 0);


			
		}
		
		/**
			Modifica o status da aplica��o
		*/
		public void setStatus(String texto){
			lblStatus.setText(texto);
		}

		public void update(Observable obs, Object args){
			if(args instanceof String){
				setStatus((String)args);
			}
			log("OBSERVADOR: " + args.toString());
			JOptionPane.showMessageDialog(null, "teste");
		
		}

		/**
			Adiciona uma entrada ao log da aplica��o
		*/
		public void log(String str){
			System.out.println("\n" + str);
			txtLog.append("\n" + str + "\n");
			JScrollBar barraHorizontal = scrollLog.getVerticalScrollBar();
			barraHorizontal.setValue(barraHorizontal.getMaximum()); 
		}

		/**
			Atualiza a �rvore da aplica��o
		*/

		public void atualizarArvore(){
			//synchronized(this){
				try{
					noAtual = 0;
					arvore.setModel(new DefaultTreeModel(servidor.retornarNo(arvore)));
					arvore.putClientProperty("JTree.lineStyle", "Angled");
					DefaultTreeCellRenderer renderSala = new DefaultTreeCellRenderer();
					renderSala.setLeafIcon(new ImageIcon("imagens/jogador.png"));			
					renderSala.setClosedIcon(new ImageIcon("imagens/maumau.png"));			
					renderSala.setOpenIcon(new ImageIcon("imagens/maumau.png"));			
			
					arvore.setCellRenderer(renderSala);

					for(int i = 0; i < ultimoNo.length; i++){
						if(ultimoNo[i] != null){
							TreeNode nos[] = ((DefaultTreeModel)arvore.getModel()).getPathToRoot(ultimoNo[i]);
							TreePath path = new TreePath(nos);
							arvore.makeVisible(path);
						}
					}			
				}catch(Exception e){
					log("Exception ao atualizar �rvore>>" + e);
				
				}
			//}		

		}


		

	}

	/**
		Thread que fica esperando por conex�es
	*/
	private class threadConexao implements Runnable{
		
		/**
			Socket que fica aguardando novas conex�es
		*/
		private ServerSocket server;

		/**
			Construtor
		*/
		public threadConexao(ServerSocket server){
			this.server = server;
			new Thread(this).start();
		}

		
		/**
			M�todo que inicializa as vari�veis e fica aguardando por conex�es
		*/
		public void run(){
			servidorGUI.log("threadConexao>>Inicializando threadConexao.Aguardando requisi��es...");
			while(SERVIDOR_RODANDO){
				try{
					Socket s = server.accept();
					s.setKeepAlive(true); 
					servidorGUI.log("threadConexao>>Requisi��o recebida...");
					
					new threadRequisicao(s);
				}catch(Exception e){
					servidorGUI.log("threadConexao>>ERRO: " + e);
				}
				try{
					Thread.sleep(200);
				}catch(Exception e){}

			}
			servidorGUI.log("threadConexao>>Encerrando threadConexao");
			System.out.println("threadConexao>>Encerrando threadConexao");

			
		}

			
	}

	/**
		Thread que atende uma requisi��o de um cliente
	*/
	private class threadRequisicao implements Runnable{

		private String requisicao = "";
		private Socket socket;
		private PrintWriter writer;
		private BufferedReader reader;
		
		/**
			COnstrutor
		*/
		public threadRequisicao(Socket socket) throws IOException{
			this.socket = socket;
			if(socket == null)
				throw new IllegalArgumentException("threadRequisicao>>ERRO: socket = null");
			Thread threadAbrir = new Thread(){
				public void run(){
					try{
					    writer = new PrintWriter(new OutputStreamWriter(threadRequisicao.this.socket.getOutputStream()), true);
					    reader = new BufferedReader(new InputStreamReader(threadRequisicao.this.socket.getInputStream()));
					}catch(Exception e){
						//throw e;
					}
				}
			};
			threadAbrir.start();
			try{
				threadAbrir.join();
				new Thread(this).start();

			}catch(Exception e){

			}

		}

		/**
			M�todo que processa a requisi��o do usu�rio
		*/
		public void run(){
			servidorGUI.log("threadRequisi��o>>Atendendo requisi��o: IP --> " + socket.getRemoteSocketAddress()  + " PORTA --> " + socket.getPort());
			String tipoRequisicao = "";
		
			try{
				requisicao = reader.readLine();
				servidorGUI.log("threadRequisi��o>>Atendendo requisi��o>>Tipo:" + requisicao);
			
			}catch(IOException e){
				servidorGUI.log("threadRequisicao>>Imposs�vel ler requisi��o");
			}
			if(requisicao != null){
				int indice = requisicao.indexOf("acao=");
				if(indice != -1){
					tipoRequisicao = requisicao.substring( (requisicao.indexOf("=") + 1), (requisicao.indexOf("&") == -1 ? requisicao.length() : requisicao.indexOf("&")) );
					System.out.println("TIPO DE REQUISI��O: " + tipoRequisicao);
					tipoRequisicao = tipoRequisicao.trim().toUpperCase();
					if(tipoRequisicao.equals("LISTA_SALAS")){
						//Retornando lista de salas
						servidorGUI.log("threadRequisicao>>Retornando lista de salas");
						try{
							writer.println(retornarSalasDisponiveis());
							writer.flush();
							servidorGUI.log("threadRequisicao>>Lista de salas enviada com sucesso");
					
						}catch(Exception e){
							servidorGUI.log("threadRequisicao>>N�o � poss�vel enviar lista de salas>>ERRO: " + e);
							
						}finally{
							try{
								socket.close();
							}catch(Exception e){
								servidorGUI.log("threadRequisicao>>ERRO ao fechar socket:" + e);
			
							}
			
						
						}
					}else if(tipoRequisicao.equals("ENTRAR_SALA")){
						//Jogador est� tentando entrar numa sala
						servidorGUI.log("threadRequisicao>>Tentando entrar na sala");
						StringTokenizer tokens = new StringTokenizer(requisicao, "&");
						if(tokens.countTokens() != 3){
							servidorGUI.log("threadRequisicao>>ENTRAR_SALA>>PAr�metros faltando");
							try{
								socket.close();
							}catch(Exception e){
								servidorGUI.log("threadRequisicao>>ERRO ao fechar socket:" + e);
			
							}
						

						}else{
							servidorGUI.log("threadRequisicao>>ENTRAR_SALA>>Numero de tokens ok");
						
							tokens.nextToken();
							String nomeJogador = (String)tokens.nextToken();
							String sala = (String)tokens.nextToken();
							indice = nomeJogador.indexOf("=");
							if(indice == -1){
								servidorGUI.log("threadRequisicao>>ENTRAR_SALA>>PAr�metros faltando");
								try{
									socket.close();
								}catch(Exception e){
									servidorGUI.log("threadRequisicao>>ERRO ao fechar socket:" + e);
			
								}
							}else{

								nomeJogador = nomeJogador.substring( indice + 1, nomeJogador.length() );
								servidorGUI.log("threadRequisicao>>Token nomeJogador encontrado");
						
								indice = sala.indexOf("=");
								if(indice == -1){
									servidorGUI.log("threadRequisicao>>ENTRAR_SALA>>PAr�metros faltando");
									try{
										socket.close();
									}catch(Exception e){
										servidorGUI.log("threadRequisicao>>ERRO ao fechar socket:" + e);
			
									}
								}else{

									sala = sala.substring( indice + 1, sala.length() );
								}
								servidorGUI.log("threadRequisicao>>Token SALA encontrado");
						
								servidorGUI.log("threadRequisicao>>Chamando rotina de entrada");
								synchronized(sala){
									String resultado = entrarSala(sala, nomeJogador, reader, writer, socket);
									resultado = resultado.trim();
									System.out.println(resultado);
									writer.println(resultado);
									if(resultado.equals("OK")){
										Sala s = procurarSala(sala);
										StringBuffer buffer = new StringBuffer("ENTRADA_JOGADOR");
										for(int i = 0; i < s.NUMERO_JOGADORES; i++)
											if(s.getJogador(i) != null)
												buffer.append("[Jogador: " + s.getJogador(i).getNome() + "]");
											log("Enviando lista de jogadores para o cliente: " + buffer.toString().trim());
											s.enviarMsgBroadcast(buffer.toString().trim());

									}

								
									if(resultado.startsWith("ERRO")){
										servidorGUI.log("threadRequisicao>>Fechando socket: " + resultado);
			
										try{
											socket.close();
										}catch(Exception e){
											servidorGUI.log("threadRequisicao>>ERRO ao fechar socket:" + e);
			
										}

									}
								}

							}
							
						}

					}
				}else{
					servidorGUI.log("threadRequisicao>>Formato de requisi��o inv�lido. Requisi��o:" + requisicao);

				}
			}else{
				try{
					socket.close();
				}catch(Exception e){
					servidorGUI.log("threadRequisicao>>ERRO ao fechar socket:" + e);
			
				}
			}
			servidorGUI.log("threadRequisi��o>>Atendendo requisi��o>>Requisi��o atendida");
			

		}
	}

	/**
		Quantidade de salas do servidor
	*/
	public final static int NUMERO_SALAS = 10; //Quantidade de salas do servidor
	
	/**
		Salas
	*/
	private Sala sala[] = new Sala[NUMERO_SALAS];
	
	/**
		Objeto utilizado para sincroniza��o de mensagens
	*/
	private Object syncMsg = new Object(); //Para sincronizar as mensagens
	
	/**
		IP do servidor
	*/
	private String ip;
	
	/**
		Porta do servidor
	*/
	private int porta;	
	
	
	/**
		Timeout para in�cio de jogo
	*/

	private double timeout;


	/**
		Timeout para fim de jogo
	*/

	private double timeoutjogo;


	/**
		Objeto de interface gr�fica
	*/
	private ServidorGUI servidorGUI;
	
	/**
		Objeto para conex�o em rede
	*/
	private ServerSocketFactory factory = ServerSocketFactory.getDefault();
	/**
		Objeto para conex�o em rede
	*/
	private ServerSocket serverSocket;
	
	/**
		Porta default do servidor
	*/
	
	private int PORTA_SERVIDOR = 5656;
	
	/**
		Indica se o servidor est� funcionando ou n�o
	*/
	private boolean SERVIDOR_RODANDO = true;
	
	/**
		Objeto para atualiza��o da �rvore de salas
	*/
	private DefaultMutableTreeNode ultimoNo[] = new DefaultMutableTreeNode[NUMERO_SALAS];		
	
	/**
		Vari�vel utilizada na atualiza��o da �rvore de salas
	*/

	private int noAtual = 0;

	/**
		Construtor
	*/
	public Servidor(String ip, int porta, double timeout, double timeoutjogo){
		servidorGUI = new ServidorGUI(this);
		//addObserver(servidorGUI);
		
		this.ip = ip;
		this.porta = porta;
		this.timeout = timeout;
		this.timeoutjogo = timeoutjogo;	

		try{
			servidorGUI.log("Criando ServerSocket...");
			serverSocket = factory.createServerSocket(porta);
			servidorGUI.log("ServerSocket criado: " + ip + ":" + porta);
			servidorGUI.log("Timeout para o in�cio de um jogo: " + getTimeout() + " minutos.");
			servidorGUI.log("Timeout para o final de um jogo: " + getTimeoutJogo() + " minutos.");

			new threadConexao(serverSocket);
			System.out.println(serverSocket);
			
		}catch(IOException e){

			JOptionPane.showMessageDialog(null, "J� existe algum processo utilizando a porta "+ PORTA_SERVIDOR + ". Feche este processo e tente novamente!");
			System.exit(1);
		}	


		for(int i = 0; i < NUMERO_SALAS; i++){
			sala[i] = new Sala("Sala " + i, this);
			//sala[i].addObserver(servidorGUI);
			//notifyObservers(sala[i].getId() + " criada");
			servidorGUI.log(sala[i].toString());
			servidorGUI.log("\n" + sala[i].getId() + " criada\n");
		}

		servidorGUI.atualizarArvore();		
		servidorGUI.show();		
	
	}

	/**
		Atualiza a �rvore de salas
	*/
	public void atualizarArvore(){
		servidorGUI.atualizarArvore();		

	}

	/**
		Fornece uma representa��o do servidor num objeto string
	*/
	public String toString(){
		StringBuffer buffer = new StringBuffer("Servidor: IP --> " + ip + " Porta: " + porta + "\n");
		for(int i = 0; i < NUMERO_SALAS; i++){
			buffer.append(">>" + sala[i] + "\n");
			
		}
		return buffer.toString().trim();
	
	}

	/**
		Interrompe o servidor
	*/
	public void parar(){
		SERVIDOR_RODANDO = false;
		for(int i = 0; i < NUMERO_SALAS; i++){
			if(sala[i] != null) sala[i].resetar();
			sala[i] = null;
		}
		servidorGUI.dispose();

	}

	/**
		Retorna uma sala pelo seu �ndice
	*/
	public Sala getSala(int id){
		if(id >= NUMERO_SALAS)
			throw new IllegalArgumentException("Servidor>>getSala>>�ndice inv�lido");
		else
			return sala[id];	

	}

	
	/**
		Retorna a lista de salas dispon�veis
	*/
	public String retornarSalasDisponiveis(){
		StringBuffer buffer = new StringBuffer("");
		for(int i = 0; i < NUMERO_SALAS; i++){
			if(sala[i] != null && (sala[i].getJogadoresAtivos() < Sala.NUMERO_JOGADORES) && !(sala[i].getJogando()))
				buffer.append(sala[i].getId() + "[" + sala[i].getJogadoresAtivos() + "]" + ( i < (NUMERO_SALAS - 1) ? "-" : "") );
			
		}
		return buffer.toString().trim();

		
	}
			
	/**
		Procura uma sala
	*/
	private Sala procurarSala(String id){
		servidorGUI.log("Procurando sala: " + id);
		for(int i = 0; i < NUMERO_SALAS; i++){
			if(sala[i] != null && sala[i].getId().trim().toUpperCase().equals(id.trim().toUpperCase())){
				servidorGUI.log("Procurando sala: " + id + ". Sala encontrada.");
		
				return sala[i];
				
			}
		}
		servidorGUI.log("Procurando sala: " + id + ". Sala n�o encontrada.");
		return null;
	}

	/**
		Tenta adicionar um jogador em uma sala
	*/
	public String entrarSala(String id, String jogador, BufferedReader reader, PrintWriter writer, Socket socket){
		servidorGUI.log("Jogador " + jogador + " quer entrar na sala " + id);
		String resultado = "";
		Sala s = procurarSala(id);
		if(s == null){ //Sala n�o encontrada
			resultado = "ERRO: Id de sala inv�lido! Obtenha novamente a lista de salas, provavelmente o nome de alguma sala do servidor foi modificado.";
			servidorGUI.log("Jogador>>" + jogador + ">>ERRO: Id de sala(" + id + ") inv�lido! Obtenha novamente a lista de salas, provavelmente o nome de alguma sala do servidor foi modificado.");
		}else{
			try{
				boolean primeiro = s.adicionarJogador(new JogadorRede(jogador, reader, writer, s, socket));
				if(primeiro){
				   resultado = "OKPRIM"; //Jogador foi o primeiro a entrar nesta sala
				   servidorGUI.log("Jogador>>" + jogador + " entrou na sala " + id + " e � o primeiro usu�rio desta sala.");
				   
				}else{
				   resultado = "OK"; //J� exitiam jogadores nesta sala
		   		   servidorGUI.log("Jogador>>" + jogador + " entrou na sala " + id + ".");
				   /*synchronized(s){
				   	if(s.getJogadoresAtivos() == Sala.NUMERO_JOGADORES)
						s.setJogando(true);
				   }*/	
				}
				servidorGUI.atualizarArvore();
			}catch(NumeroJogadoresExcedidoException e2){
				resultado = "ERRORET: A sala escolhida est� cheia. Selecione outra sala e tente novamente.";
				servidorGUI.log("Jogador>>" + jogador + " n�o conseguiu entrar na sala " + id + ", pois a mesma est� cheia");
		
			}catch(JogandoException e3){
				resultado = "ERRORET: O jogo j� foi iniciado na sala escolhida. Selecione outra sala e tente novamente.";
				servidorGUI.log("Jogador>>" + jogador + " n�o conseguiu entrar na sala " + id + ", pois o jogo j� est� em andamento nesta sala.");
		
			}catch(JogadorDuplicadoException e1){
				resultado = "ERRO: J� existe um jogador na sala com o apelido que voc� escolheu. Troque o apelido e tente novamente.";
				servidorGUI.log("Jogador>>" + jogador + " n�o conseguiu entrar na sala " + id + ", pois j� existe um jogador com este apelido na sala.");
		
			}catch(IOException e4){
				resultado = "ERRO: N�o � poss�vel criar a conex�o.";
				servidorGUI.log("Jogador>>" + jogador + " n�o conseguiu entrar na sala " + id + ", pois n�o foi poss�vel criar uma conex�o.\n" + e4);
		

			}

		}
		
		return resultado;		

	}

	/**
		Retorna o n� principal da �rvore de salas
	*/
	public DefaultMutableTreeNode retornarNo(JTree arvore){
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Servidor Mau-Mau");
		DefaultMutableTreeNode salas = new DefaultMutableTreeNode("Salas");
		/*raiz.add(new DefaultMutableTreeNode("Log"));
		raiz.add(new DefaultMutableTreeNode("Configura��es"));*/


		for(int i = 0; i < NUMERO_SALAS; i++){
			if(sala[i] != null){
				synchronized(sala[i]){
					DefaultMutableTreeNode noSala = sala[i].retornarNo(arvore); 
					salas.add(noSala);
				}
			}
		}	
		
		raiz.add(salas);
		
		

		return raiz;
	}

	/**
		Adiciona uma entrada ao log
	*/	
	public void log(String msg){
		servidorGUI.log(msg);
	}

	/**
		Inicializa a execu��o do servidor
	*/
	
	public static void main(String[] args){
		int PORTA_SERVIDOR = 5656;
		double TIMEOUT = 15, TIMEOUTJOGO = 60;
		
		try{
			Preferences.userRoot().node("servidormaumau").removeNode();			
		}catch(Exception e1){}

		try{
			Preferences.importPreferences(new FileInputStream("config.xml"));
			Preferences p = Preferences.userRoot();
			System.out.println("**********Arquivo de configura��o encontrado**********");
			System.out.println("COnfigura��es:");
			PORTA_SERVIDOR = p.node("servidormaumau").getInt("PORTA", 5656);
			TIMEOUT = p.node("servidormaumau").getDouble("TIMEOUT", 15.0);
			TIMEOUTJOGO = p.node("servidormaumau").getDouble("TIMEOUTJOGO", 60.0);
			System.out.println("PORTA_SERVIDOR: " + PORTA_SERVIDOR);
			System.out.println("TIMEOUT: " + TIMEOUT);
			System.out.println("TIMEOUT_JOGO: " + TIMEOUTJOGO);
			System.out.println("******************************************************");
			if(PORTA_SERVIDOR <= 0) PORTA_SERVIDOR = 5656;
			if(TIMEOUT <= 0) TIMEOUT = 15;
			if(TIMEOUTJOGO <= 0) TIMEOUTJOGO = 60;


		}catch(Exception e){
			System.out.println("Erro ao carregar arquivo de configura��o(config.xml)>> " + e + ">>Utilizando op��es default.");


		}
		Servidor s = new Servidor("socket://localhost", PORTA_SERVIDOR, TIMEOUT, TIMEOUTJOGO);

	}

	/**
		M�todo utilizado para atualiza��o da �rvore
	*/
	public synchronized void addUltimoNoSala(DefaultMutableTreeNode no){
		if(noAtual < NUMERO_SALAS)
			ultimoNo[noAtual++] = no;

	}

	/**
		Retorna o timeout para o in�cio do jogo
	*/
	public double getTimeout(){
		return timeout;
	}

	/**
		Retorna o timeout para o t�rmino do jogo
	*/
	public double getTimeoutJogo(){
		return timeoutjogo;
	}




}
