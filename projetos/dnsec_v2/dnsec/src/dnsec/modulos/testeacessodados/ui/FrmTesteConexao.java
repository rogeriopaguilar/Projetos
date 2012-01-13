package dnsec.modulos.testeacessodados.ui;

import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.PrintWriter;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import dnsec.modulos.principal.business.AppStart;
import dnsec.modulos.testeacessodados.business.CommandTestarConexao;
import dnsec.shared.command.impl.BaseDispatchCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.factory.CommandFactory;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.swing.base.BaseJFrame;
import dnsec.shared.swing.base.BaseJInternalFrame;
import dnsec.shared.swing.base.BaseJLabel;
import dnsec.shared.util.RecursosUtil;


public class FrmTesteConexao extends BaseJFrame{
    static Logger logger = Logger.getLogger(FrmTesteConexao.class.getName());
	boolean conexaoOK = false;

	
	protected BaseJLabel lblTitulo = new BaseJLabel(RecursosUtil.getInstance().getResource("key.acessodados.msg.aguarde") + "...");

	private boolean continuarTestandoConexao  = true;
	
	
	
	public boolean isContinuarTestandoConexao() {
		return continuarTestandoConexao;
	}



	public void setContinuarTestandoConexao(boolean continuarTestandoConexao) {
		this.continuarTestandoConexao = continuarTestandoConexao;
	}



	public BaseJLabel getLblTitulo() {
		return lblTitulo;
	}



	public void setLblTitulo(BaseJLabel lblTitulo) {
		this.lblTitulo = lblTitulo;
	}


	class ThreadTesteConexao implements Runnable{
		public void run() {
				try{
					testarConexao();
				}finally{
					FrmTesteConexao.this.setContinuarTestandoConexao(false);
				}	
		}
	};

	
	
	ThreadTesteConexao threadTesteConexao = null;
	
	/**
	 *Faz um teste com a conexão de dados do hibernate.
	 *Caso ocorra algum problema, exibe uma tela relatando o mesmo
	 *e sai da aplicação.
	 * */
	public void testarConexao(){
				logger.debug("Inicializando teste de conexão com a base de dados...");
				try{
					BaseDispatchCommand comandoTestarConexao = new CommandTestarConexao();
					comandoTestarConexao.setStrMetodo(CommandTestarConexao.METODO_TESTAR_CONEXAO);
					(new Thread(){
						public void run(){
							try{
								sleep(120000);
								if(!conexaoOK){
									JOptionPane.showMessageDialog(FrmTesteConexao.this,"Não foi possível estabelecer a conexão com a base de dados!" + System.getProperty("line.separator") + "Verifique o arquivo de configuração e tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
									FrmTesteConexao.this.dispose();
									AppStart.main(new String[]{});
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}).start();
					comandoTestarConexao.executar(new Object[0]);
					conexaoOK = true;
					logger.debug("Teste de conexão com a base de dados realizado com sucesso...");
					File arquivoSetupRealizado = new File(System.getProperty("user.home"), "dnsec-check-bd.tmp");
					if(!arquivoSetupRealizado.exists()){
						logger.debug("Verificando existência de dados na base de dados...");
						BaseDispatchCommand commandLogin = CommandFactory.getCommand(CommandFactory.COMMAND_LOGIN);
						commandLogin.setStrMetodo("setupBancoDados");
						commandLogin.executar(null);
						arquivoSetupRealizado.createNewFile();
						logger.debug("verificação ok...");
					}else{
						logger.debug("Verificação de dados na base não necessária...");
					}
				}catch(CommandException commandException){
					new BaseJInternalFrame().tratarMensagemErro(commandException);
					logger.fatal("Não é possível estabelecer aconexão com a base de dados " + commandException);
					System.exit(1);
				}catch(Throwable exception){
					//Erro fatal durante a inicialização
					String msgErroPadrao = RecursosUtil.getInstance().getResource("key.erro.inicializacao.app");
					exception.printStackTrace();
					CharArrayWriter arrayMsg = new CharArrayWriter();
					PrintWriter printWriter = new PrintWriter(arrayMsg);
					exception.printStackTrace(printWriter);
					String msgErroEspecifico = arrayMsg.toString();
					JOptionPane.showMessageDialog(this, msgErroPadrao + System.getProperty("line.separator") + msgErroEspecifico, RecursosUtil.getInstance().getResource("key.jpanelmanutencao.erro.titulo.janela"), JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
		
	}

	/**
	 * Inicializa os componentes da tela e inicializa uma thread 
	 * que faz o teste de conexão com a base de dados. 
	 * Caso o teste falhe, sai do aplicativo.
	 * Se o teste ocorrer com sucesso, inicializa a tela principal
	 * da aplicação.
	 * */
	protected void inicializarComponentes(){

		int larguraTela = tk.getScreenSize().width ;
		int alturaTela = tk.getScreenSize().height ;
		this.setSize(larguraTela / 4, alturaTela / 4);
		this.setLocation( (larguraTela - (larguraTela / 4)) / 2, ( alturaTela - (alturaTela / 4))/2);
		this.setTitle(RecursosUtil.getInstance().getResource("key.acessodados.msg.titulo.janela"));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(lblTitulo);
		pack();

		new Thread(){
			public void run(){
				int contador = 0;
				StringBuffer bufferMsg = new StringBuffer(RecursosUtil.getInstance().getResource("key.acessodados.msg.aguarde"));
				while(FrmTesteConexao.this.isContinuarTestandoConexao()){
					if(	threadTesteConexao == null){
						threadTesteConexao = new ThreadTesteConexao();
						new Thread(threadTesteConexao).start();
					}
					bufferMsg.append(".");
					FrmTesteConexao.this.lblTitulo.setText(bufferMsg.toString());
					contador++;
					if(contador == 3){
						contador = 0;
						bufferMsg.delete(
									RecursosUtil.getInstance().getResource("key.acessodados.msg.aguarde").length(),
									RecursosUtil.getInstance().getResource("key.acessodados.msg.aguarde").length()	+ 3		
						);
					}
					try{
						Thread.sleep(200);
					}catch(InterruptedException e){}
					
				}
				
				FrmTesteConexao.this.lblTitulo.setText(
						RecursosUtil.getInstance().getResource("key.acessodados.conexao.ok")
				);

				try{
					Thread.sleep(500);
				}catch(InterruptedException e){}


				//Se nenhum problema ocorreu, inicializa a aplciação.
				logger.debug("Inicializando janela principal do aplicativo...");
				javax.swing.SwingUtilities.invokeLater(
						new Runnable(){
							public void run(){	
								/*JanelaPrincipal janelaPrincipal = new JanelaPrincipal();
								janelaPrincipal.setVisible(true);*/
								JDialog.setDefaultLookAndFeelDecorated(true);
								GerenciadorJanelas.getInstance().getAcaoLogin().actionPerformed(null);
							}
						}
			    );
				
				FrmTesteConexao.this.dispose();
			}
			
		}.start();
	}



	public FrmTesteConexao() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("config/resource/dnsec.gif")));
		this.inicializarComponentes();
	}



	public FrmTesteConexao(GraphicsConfiguration graphicsConfiguration) {
		super(graphicsConfiguration);
		this.inicializarComponentes();
	}



	public FrmTesteConexao(String title, GraphicsConfiguration graphicsConfiguration) {
		super(title, graphicsConfiguration);
		this.inicializarComponentes();
	}



	public FrmTesteConexao(String title) {
		super(title);
		this.inicializarComponentes();
	}
	
	
	
}
