/**
 * 05/01/2006
 * Rogério de Paula Aguilar
 * 
 * Gerencia as janelas da aplicação. Para cada janela de cria uma instância			<br>
 * e uma lista de listeners para as janelas de pesquisa, que serão notificados 		<br>
 * quando a seleção for feita na janela.											<br>
 * 			
 * @author Rogério de Paula Aguilar																		<br>
 * @version 0.1
 *TODO --> Adicionar opção para configurar a janela como modal ou não
 * */

package dnsec.shared.controller;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import dnsec.modulos.cadastros.associacoes.ui.FrmAssociacoes;
import dnsec.modulos.cadastros.funcao.ui.FrmFuncoes;
import dnsec.modulos.cadastros.funcao.ui.FrmPesquisaFuncoes;
import dnsec.modulos.cadastros.funcao.vo.FuncaoTelaVo;
import dnsec.modulos.cadastros.grupo.business.CommandGrupos;
import dnsec.modulos.cadastros.grupo.ui.FrmGrupos;
import dnsec.modulos.cadastros.sistema.ui.FrmPesquisaSistemas;
import dnsec.modulos.cadastros.sistema.ui.FrmSistemas;
import dnsec.modulos.cadastros.sistema.vo.SistemaTelaVo;
import dnsec.modulos.cadastros.usuario.ui.FrmPesquisaUsuarios;
import dnsec.modulos.cadastros.usuario.ui.FrmUsuario;
import dnsec.modulos.cadastros.usuario.vo.UsuarioTelaVo;
import dnsec.modulos.controle.seguranca.ui.FrmLogin;
import dnsec.modulos.controle.seguranca.vo.ControleSegurancaVo;
import dnsec.modulos.principal.ui.JanelaPrincipal;
import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.listener.PesquisaFuncaoListener;
import dnsec.shared.listener.PesquisaSistemaListener;
import dnsec.shared.listener.PesquisaUsuarioListener;
import dnsec.shared.swing.base.BaseJDialog;
import dnsec.shared.swing.base.BaseJInternalFrame;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.RecursosUtil;

public class GerenciadorJanelas {
    	static Logger logger = Logger.getLogger(GerenciadorJanelas.class.getName());
		private static GerenciadorJanelas gerenciadorJanelasPesquisa = new GerenciadorJanelas();
		private static ControleSegurancaVo controleSegurancaVo;
		
		//Ícones
		public static Icon ICONE_PRINCIPAL = null; //new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/dnsec.gif"));
		public static Icon ICONE_BOTAO_PESQUISA = null; //new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/pesquisar.gif"));
		public static Icon ICONE_BOTAO_INCLUIR = null; // new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/incluir.gif"));
		public static Icon ICONE_BOTAO_ALTERAR = null; // new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/alterar.gif"));
		public static Icon ICONE_BOTAO_EXCLUIR = null; // new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/excluir.gif"));
		public static Icon ICONE_BOTAO_CONFIRMAR = null; // new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/confirmar.gif"));
		public static Icon ICONE_BOTAO_CANCELAR =  null; //new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/cancelar.gif"));
		public static Icon ICONE_ERRO = null; // new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/cancelar.gif"));
		public static Icon ICONE_BOTAO_PRIMEIRO = null; // new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/primeiro.gif"));
		public static Icon ICONE_BOTAO_ANTERIOR = null; // new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/anterior.gif"));
		public static Icon ICONE_BOTAO_PROXIMO =  null; //new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/proximo.gif"));
		public static Icon ICONE_BOTAO_ULTIMO = null; // new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/ultimo.gif"));
		public static Icon ICONE_SISTEMA       =  null; //new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/sistema.gif"));
		public static Icon ICONE_SISTEMA_PEQUENO=  null; //new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/sistema_pequeno.gif"));
		public static Icon ICONE_FUNCAO =  null; //new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/funcao.gif"));
		public static Icon ICONE_FUNCAO_PEQUENO = null; // new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/funcao_pequeno.gif"));
		public static Icon ICONE_BOTAO_SAIR =  null; //new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/sair.gif"));
		public static Icon ICONE_BOTAO_SAIR_PEQUENO = null; // new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/sair_pequeno.gif"));
		public static Icon ICONE_USUARIO = null; // new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/usuario.gif"));
		public static Icon ICONE_USUARIO_PEQUENO= null; // new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/usuario_pequeno.jpg"));
		public static Icon ICONE_GRUPO = null; // new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/grupo.gif"));
		public static Icon ICONE_GRUPO_PEQUENO = null; // new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/grupo_pequeno.jpg"));
		public static Icon ICONE_ASSOCIACOES =  null; //new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/associacoes.gif"));		
		public static Icon ICONE_ASSOCIACOES_PEQUENO = null; // new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/associacoes_pequeno.gif"));
		public static Icon ICONE_RECARREGAR =  null; //new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/refresh.gif"));
		public static JLabel LABEL_RECARREGAR = new JLabel(ICONE_RECARREGAR);

		static{
			try
			{
				LABEL_RECARREGAR.setToolTipText("Recarregar dados da árvore");
				ICONE_PRINCIPAL = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/dnsec.gif"));
				ICONE_BOTAO_PESQUISA = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/pesquisar.gif"));
				ICONE_BOTAO_INCLUIR = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/incluir.gif"));
				ICONE_BOTAO_ALTERAR = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/alterar.gif"));
				ICONE_BOTAO_EXCLUIR = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/excluir.gif"));
				ICONE_BOTAO_CONFIRMAR = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/confirmar.gif"));
				ICONE_BOTAO_CANCELAR = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/cancelar.gif"));
				ICONE_ERRO = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/cancelar.gif"));
				ICONE_BOTAO_PRIMEIRO = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/primeiro.gif"));
				ICONE_BOTAO_ANTERIOR = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/anterior.gif"));
				ICONE_BOTAO_PROXIMO = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/proximo.gif"));
				ICONE_BOTAO_ULTIMO = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/ultimo.gif"));
				ICONE_SISTEMA       = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/sistema.gif"));
				ICONE_SISTEMA_PEQUENO= new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/sistema_pequeno.gif"));
				ICONE_FUNCAO = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/funcao.gif"));
				ICONE_FUNCAO_PEQUENO = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/funcao_pequeno.gif"));
				ICONE_BOTAO_SAIR = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/sair.gif"));
				ICONE_BOTAO_SAIR_PEQUENO = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/sair_pequeno.gif"));
				ICONE_USUARIO = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/usuario.gif"));
				ICONE_USUARIO_PEQUENO= new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/usuario_pequeno.jpg"));
				ICONE_GRUPO = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/grupo.gif"));
				ICONE_GRUPO_PEQUENO = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/grupo_pequeno.jpg"));
				ICONE_ASSOCIACOES = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/associacoes.gif"));		
				ICONE_ASSOCIACOES_PEQUENO = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/associacoes_pequeno.gif"));
				ICONE_RECARREGAR = new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/refresh.gif"));
				LABEL_RECARREGAR = new JLabel(ICONE_RECARREGAR);
			}catch(Exception e)
			{
				logger.warn("Problema ao carregar recursos para as janelas!", e);
			}
			
		}
		
		public static final String lookAndFeel[] = new String[]{"com.sun.java.swing.plaf.motif.MotifLookAndFeel",
		    	  "javax.swing.plaf.metal.MetalLookAndFeel",
		    	  "com.sun.java.swing.plaf.windows.WindowsLookAndFeel",
		    	  "ch.randelshofer.quaqua.QuaquaLookAndFeel",
	  			  "org.jvnet.substance.SubstanceLookAndFeel"
		};

		

		
		//Janela principal do sistema
		private JanelaPrincipal janelaPrincipal = null;
		
		//Formulários
		private BaseJDialog frmLogin;
		private BaseJInternalFrame frmPesquisaSistemas;
		private BaseJInternalFrame frmPesquisaFuncoes;
		private BaseJInternalFrame frmGrupos;
		private BaseJInternalFrame frmFuncoes;
		private BaseJInternalFrame frmSistemas;
		private BaseJInternalFrame frmAssociacoes;
		private BaseJInternalFrame frmPesquisaUsuarios;
		private BaseJInternalFrame frmUsuario;

		
		
		class AcaoLogin extends AbstractAction{
			public void actionPerformed(ActionEvent e) {
					synchronized(GerenciadorJanelas.this){
						if(frmLogin == null){
							frmLogin = new FrmLogin();
							SwingUtilities.invokeLater(new Runnable(){
								public void run(){
									frmLogin.setVisible(true);
									//janelaPrincipal.setVisible(true);
								}
							   }
						 );
						}
					}
					
			}
		};
		
		
		private AcaoLogin acaoLogin = new AcaoLogin();

		public Action getAcaoLogin(){
			return acaoLogin;
		}	
		
		//Container para exibir o formulário
		private Container containerPrincipal;
		
		/**
		 *Lista de listeners que serão notificados quando um sistema for selecionado na tela
		 *de pesquisa de sistemas
		 */
		//private static List pesquisaSistemaListenersList = new LinkedList();

		
		/**
		 *Lista de listeners que serão notificados quando uma função for selecionada na tela
		 *de pesquisa de funções
		 */
		//private static List pesquisaFuncoesListenersList = new LinkedList();

		
		//private static List pesquisaUsuariosListenersList = new LinkedList();
		
		private GerenciadorJanelas(){
			
		}

		/**
		 * Retorna a instância do gerenciados de janelas
		 * */
		public static GerenciadorJanelas getInstance(){
				return gerenciadorJanelasPesquisa;
		}

		/**
		 * Exibe o formulário de pesquisa de sistemas
		 * */
		class AcaoPesquisaSistemas extends AbstractAction{
			private boolean exibirBotoesManutencao;
			private boolean modal = false;
			private List pesquisaSistemaListenersList;
			
			public void setModal(boolean modal){
				this.modal = modal;
			}

			public boolean getModal(){
				return this.modal;
				
			}

			public void setExibirBotoesManutencao(boolean exibirBotoesManutencao){
				this.exibirBotoesManutencao = exibirBotoesManutencao;
			}

			public boolean getExibirBotoesManutencao(){
				return this.exibirBotoesManutencao;
			}
			
			public AcaoPesquisaSistemas(List listeners){
				super(RecursosUtil.getInstance().getResource("key.menu.pesquisa.sistemas"), ICONE_SISTEMA);
				this.pesquisaSistemaListenersList = listeners;
				this.putValue(Action.SHORT_DESCRIPTION, "Cadastro de Sistemas");
			}	
			
			public void actionPerformed(ActionEvent e) {
					synchronized(GerenciadorJanelas.this){
						if(frmPesquisaSistemas == null){
							//frmPesquisaSistemas = new FrmPesquisaSistemas(JanelaPrincipal.this);
							//frmPesquisaSistemas.dispose();
							frmPesquisaSistemas = new FrmPesquisaSistemas();
						}
						//Adiciona a janela ao container
						if(containerPrincipal != null){
							containerPrincipal.remove(frmPesquisaSistemas);
							containerPrincipal.add(frmPesquisaSistemas);
						}
						((FrmPesquisaSistemas)frmPesquisaSistemas).clearListeners();
						if(pesquisaSistemaListenersList != null){
							Iterator it = pesquisaSistemaListenersList.iterator();
							while(it.hasNext()){
								((FrmPesquisaSistemas)frmPesquisaSistemas).addPesquisaSistemaListener((PesquisaSistemaListener)it.next());
							}	
						}
						//Exibe a janela e move para o primeiro plano
						((FrmPesquisaSistemas)frmPesquisaSistemas).atualizarEstado();
						frmPesquisaSistemas.setVisible(true);
						frmPesquisaSistemas.moveToFront();
					}
			}
		};
		
		//AcaoPesquisaSistemas acaoPesquisaSistemas = new AcaoPesquisaSistemas();

		/**
		 * Exibe o formulário de pesquisa de funções
		 * */
		class AcaoPesquisaFuncoes extends AbstractAction{
			
			private boolean exibirBotoesManutencao;
			private List pesquisaFuncoesListenersList;
			
			public void setExibirBotoesManutencao(boolean exibirBotoesManutencao){
				this.exibirBotoesManutencao = exibirBotoesManutencao;
			}

			public boolean getExibirBotoesManutencao(){
				return this.exibirBotoesManutencao;
			}
			
			public AcaoPesquisaFuncoes(List listeners){
				super(RecursosUtil.getInstance().getResource("key.menu.pesquisa.funcoes"));
				pesquisaFuncoesListenersList = listeners;
			}
			
			public void actionPerformed(ActionEvent e) {
					synchronized(GerenciadorJanelas.this){
						if(frmPesquisaFuncoes == null){
							//frmPesquisaFuncoes.dispose();
							frmPesquisaFuncoes = new FrmPesquisaFuncoes();
						}
						((FrmPesquisaFuncoes)frmPesquisaFuncoes).setExibirBotoesManutencao(this.exibirBotoesManutencao);
						((FrmPesquisaFuncoes)frmPesquisaFuncoes).clearListeners();
						if(containerPrincipal != null){
							containerPrincipal.remove(frmPesquisaFuncoes);
							containerPrincipal.add(frmPesquisaFuncoes);
						}
						if(pesquisaFuncoesListenersList != null){
							Iterator it = pesquisaFuncoesListenersList.iterator();
							while(it.hasNext()){
								((FrmPesquisaFuncoes)frmPesquisaFuncoes).addPesquisaFuncaoListener((PesquisaFuncaoListener)it.next());
							}	
						}
						//Exibe a janela e move para o primeiro plano
						((FrmPesquisaFuncoes)frmPesquisaFuncoes).atualizarEstado();
						frmPesquisaFuncoes.setVisible(true);
						frmPesquisaFuncoes.moveToFront();
					}
			}
		};

		//AcaoPesquisaFuncoes acaoPesquisaFuncoes = new AcaoPesquisaFuncoes();
		
		/**
		 * Exibe o formulário de cadastro de grupos
		 * */
		
		/*Ação para inicializar o cadastro de grupos*/
		class AcaoCadastroGrupos extends AbstractAction { 
			
			public AcaoCadastroGrupos(){
				super(RecursosUtil.getInstance().getResource(Constantes.KEY_MENU_CADASTRO_GRUPOS) , new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/grupo.gif")));
				putValue(Action.SHORT_DESCRIPTION, "Cadastro de Grupos");
			}
			
			public void actionPerformed(ActionEvent e) {
					synchronized(GerenciadorJanelas.this){
						if(frmGrupos == null){
							//frmGrupos.dispose();
							frmGrupos = new FrmGrupos();
						}
						if(containerPrincipal != null){
							containerPrincipal.remove(frmGrupos);
							containerPrincipal.add(frmGrupos);
						}
						((FrmGrupos)frmGrupos).atualizarEstado();					
						this.putValue(Action.SHORT_DESCRIPTION, "Cadastro de Grupos");
						frmGrupos.setVisible(true);
						frmGrupos.moveToFront();
					}
			}
		};

		AcaoCadastroGrupos acaoCadastroGrupos = new AcaoCadastroGrupos();
		
		
		/**
		 *Ação para o cadastro de funções.
		 *Inicializa a tela de pesquisa de funções
		 **/
		class AcaoCadastroFuncoes extends AbstractAction{
			
			public AcaoCadastroFuncoes(){ 
				super(RecursosUtil.getInstance().getResource("key.menu.cadastro.funcoes"), new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/funcao.gif")));
				this.putValue(Action.SHORT_DESCRIPTION, "Cadastro de Funções");
			}
			
			public void actionPerformed(ActionEvent e) {
					List listenersSistema = new LinkedList();
					listenersSistema.add(getFrmPesquisaFuncoes());
					getAcaoPesquisaFuncoes(containerPrincipal, Collections.EMPTY_LIST,listenersSistema, true).actionPerformed(e);
			}
		};

		
		AcaoCadastroFuncoes acaoCadastroFuncoes = new AcaoCadastroFuncoes();
		
		/*Ação para inicializar o cadastro de funções*/
		class AcaoJanelaCadastroFuncoes extends AbstractAction{
		
			 private String strMetodo = "";
			 private Funcao funcao = null;
			 
			 public String getStrMetodo(){
				 return strMetodo;
			 }
			 
			 public void setStrMetodo(String strMetodo){
				 this.strMetodo = strMetodo;
			 }
			 
			 public void setFuncao(Funcao funcao){
				 this.funcao = funcao;
			 }
			 
			 public Funcao getFuncao(){
				 return this.funcao;
			 }
			 
			 public AcaoJanelaCadastroFuncoes(){ 	
				 	super(RecursosUtil.getInstance().getResource("key.menu.cadastro.funcoes"));
			 }
			 
			public void actionPerformed(ActionEvent e) {
					synchronized(GerenciadorJanelas.this){
						if(frmFuncoes == null){
							frmFuncoes = new FrmFuncoes();
						}
						if(containerPrincipal != null){
							containerPrincipal.remove(frmFuncoes);
							containerPrincipal.add(frmFuncoes);
						}
						((FrmFuncoes)frmFuncoes).getBaseDispatchCRUDCommand().setStrMetodo(strMetodo);
						if(getFuncao() != null){
							FuncaoTelaVo funcaoTelaVo = new FuncaoTelaVo();
							funcaoTelaVo.setFuncao(getFuncao());
							((FrmFuncoes)frmFuncoes).setTelaVo(funcaoTelaVo);
						} 
						((FrmFuncoes)frmFuncoes).atualizarEstado();
						frmFuncoes.setVisible(true);
						frmFuncoes.moveToFront();
					}
			}
		};

		private AcaoJanelaCadastroFuncoes acaoJanelaCadastroFuncoes = new AcaoJanelaCadastroFuncoes();

		

		
		/*Ação para inicializar o cadastro de funções*/
		class AcaoJanelaCadastroSistemas extends AbstractAction{
		
			 private String strMetodo = "";
			 private Sistema sistema = null;
			 
			 public String getStrMetodo(){
				 return strMetodo;
			 }
			 
			 public void setStrMetodo(String strMetodo){
				 this.strMetodo = strMetodo;
			 }
			 
			 public void setSistema(Sistema sistema){
				 this.sistema = sistema;
			 }
			 
			 public Sistema getSistema(){
				 return this.sistema;
			 }
			 
			 public AcaoJanelaCadastroSistemas(){ 	
				 	super(RecursosUtil.getInstance().getResource("key.menu.cadastro.sistemas"));
			 }
			 
			public void actionPerformed(ActionEvent e) {
					synchronized(GerenciadorJanelas.this){
						/*if(frmSistemas != null){
							frmSistemas.dispose();
							if(containerPrincipal != null){
								containerPrincipal.remove(frmSistemas);
							}
							frmSistemas = null;
						}*/
						if(frmSistemas == null){
							frmSistemas = new FrmSistemas();
						}
						if(containerPrincipal != null){
							containerPrincipal.remove(frmSistemas);
							containerPrincipal.add(frmSistemas);
						}
						((FrmSistemas)frmSistemas).getBaseDispatchCRUDCommand().setStrMetodo(strMetodo);
						if(getSistema() != null){
							SistemaTelaVo sistemaTelaVo = new SistemaTelaVo();
							sistemaTelaVo.setSistema(getSistema());
							((FrmSistemas)frmSistemas).setTelaVo(sistemaTelaVo);
						} 
						((FrmSistemas)frmSistemas).atualizarEstado();
						frmSistemas.setVisible(true);
					frmSistemas.moveToFront();
					}
			}
		};

		private AcaoJanelaCadastroSistemas acaoJanelaCadastroSistemas = new AcaoJanelaCadastroSistemas();
		

		
		/*Ação para inicializar o formulário de associacoes*/
		class AcaoAssociacoes extends AbstractAction 
		{
			private String strMetodo = "";
		
			public AcaoAssociacoes()
			{
				super(RecursosUtil.getInstance().getResource("key.menu.cadastro.associacoes"), 	new ImageIcon(GerenciadorJanelas.class.getClassLoader().getResource("config/resource/associacoes.gif")));				
				putValue(Action.SHORT_DESCRIPTION, "Associações");
			}
			
			public void actionPerformed(ActionEvent e) {
					synchronized(GerenciadorJanelas.this){
						if(frmAssociacoes == null){
							frmAssociacoes= new FrmAssociacoes();
						}
						if(containerPrincipal != null){
							containerPrincipal.remove(frmAssociacoes);
							containerPrincipal.add(frmAssociacoes);
						}
						((FrmAssociacoes)frmAssociacoes).atualizarEstado();
						frmAssociacoes.setVisible(true);
						frmAssociacoes.moveToFront();
					}
			}
			
		};
		
		private AcaoAssociacoes acaoAssociacoes = new AcaoAssociacoes();

		
		/**
		 * Exibe o formulário de pesquisa de funções
		 * */
		class AcaoPesquisaUsuarios extends AbstractAction{
			
			private boolean exibirBotoesManutencao = true;
			private boolean modal;
			private List pesquisaUsuariosListenersList ;
			
			public AcaoPesquisaUsuarios(List listeners){
				super("Usuários", ICONE_USUARIO);
				putValue(Action.SHORT_DESCRIPTION, "Cadastro de Usuários");
				pesquisaUsuariosListenersList = listeners;				
			}
			
			public boolean isModal() {
				return modal;
			}

			public void setModal(boolean modal) {
				this.modal = modal;
			}

			public void setExibirBotoesManutencao(boolean exibirBotoesManutencao){
				this.exibirBotoesManutencao = exibirBotoesManutencao;
			}

			public boolean getExibirBotoesManutencao(){
				return this.exibirBotoesManutencao;
			}
			
			
			public void actionPerformed(ActionEvent e) {
					synchronized(GerenciadorJanelas.this){
						if(frmPesquisaUsuarios == null){
							//frmPesquisaFuncoes.dispose();
							frmPesquisaUsuarios = new FrmPesquisaUsuarios();
						}
						((FrmPesquisaUsuarios)frmPesquisaUsuarios).setExibirBotoesManutencao(this.exibirBotoesManutencao);
						((FrmPesquisaUsuarios)frmPesquisaUsuarios).clearListeners();
						if(containerPrincipal != null){
							containerPrincipal.remove(frmPesquisaUsuarios);
							containerPrincipal.add(frmPesquisaUsuarios);
						}
						if(pesquisaUsuariosListenersList != null){
							Iterator it = pesquisaUsuariosListenersList.iterator();
							while(it.hasNext()){
								((FrmPesquisaUsuarios)frmPesquisaUsuarios).addPesquisaUsuarioListener((PesquisaUsuarioListener)it.next());
							}	
						}
						//Exibe a janela e move para o primeiro plano
						((FrmPesquisaUsuarios)frmPesquisaUsuarios).atualizarEstado();
						frmPesquisaUsuarios.setVisible(true);
						frmPesquisaUsuarios.moveToFront();
					}
			}
		};

		//private AcaoPesquisaUsuarios acaoPesquisaUsuarios = new AcaoPesquisaUsuarios();
		

		
		
		public AcaoAssociacoes getAcaoAssociacoes() {
			return acaoAssociacoes;
		}

		public void setAcaoAssociacoes(AcaoAssociacoes acaoAssociacoes) {
			this.acaoAssociacoes = acaoAssociacoes;
		}

		/**
		 * Abre a janela principal do sistema
		 * */
		public void abrirJanelaPrincipal(boolean exibir){
			synchronized(GerenciadorJanelas.this){
				if(this.janelaPrincipal == null){
					this.janelaPrincipal = new JanelaPrincipal();
				}
				this.janelaPrincipal.setVisible(true);
				if(getControleSegurancaVo() != null && getControleSegurancaVo().getUsuarioConectado() != null){
					String codUsuarioConectado = getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua();
					if(StringUtils.isEmpty(codUsuarioConectado))
					{
						codUsuarioConectado = "";
					}
					this.janelaPrincipal.setStatusMsg("Usuário: " + codUsuarioConectado);
				}
			}
		}

		/**
		 * Seta o vo contendo as informações de segurança para o usuáio conectado
		 * ao sistema
		 * */
		public void setControleSegurancaVo(ControleSegurancaVo vo)
		{
				controleSegurancaVo = vo;
		}

		public static ControleSegurancaVo getControleSegurancaVo()
		{
				return controleSegurancaVo;
		}

		
		/**
		 * Abre a janela de cadastro de funções e
		 * seta o método que será executado quando
		 * a janela abrir.
		 * */
		public Action getAcaoJanelaCadastroFuncoes(Container containerPrincipal, String strMetodo, Funcao funcaoEditar) {
			if(containerPrincipal != null){
				this.containerPrincipal = containerPrincipal;
			}
			acaoJanelaCadastroFuncoes.setStrMetodo(strMetodo);
			acaoJanelaCadastroFuncoes.setFuncao(funcaoEditar);
			return acaoJanelaCadastroFuncoes;
		}

		/**
		 * Abre a janela de cadastro de usuários e
		 * seta o método que será executado quando
		 * a janela abrir.
		 * */
		public Action getAcaoJanelaCadastroUsuarios(Container containerPrincipal, String strMetodo, Usuario usuarioEditar) {
			if(containerPrincipal != null){
				this.containerPrincipal = containerPrincipal;
			}
			acaoJanelaCadastroUsuarios.setStrMetodo(strMetodo);
			acaoJanelaCadastroUsuarios.setUsuario(usuarioEditar);
			return acaoJanelaCadastroUsuarios;
		}
		
		
		public Action getAcaoAssociacoes(Container containerPrincipal) {
			if(containerPrincipal != null){
				this.containerPrincipal = containerPrincipal;
			}
			return acaoAssociacoes;
		}

		
		public Action getAcaoCadastroFuncoes(Container containerPrincipal) {
			if(containerPrincipal != null){
				this.containerPrincipal = containerPrincipal;
			}
			return acaoCadastroFuncoes;
		}

		public Action getAcaoCadastroSistemas(Container containerPrincipal, String strMetodo, Sistema sistemaEditar) {
			if(containerPrincipal != null){
				this.containerPrincipal = containerPrincipal;
			}
			acaoJanelaCadastroSistemas.setStrMetodo(strMetodo);
			acaoJanelaCadastroSistemas.setSistema(sistemaEditar);
			return acaoJanelaCadastroSistemas;
		}

		public Action getAcaoCadastroSistemas(Container containerPrincipal) {
			if(containerPrincipal != null){
				this.containerPrincipal = containerPrincipal;
			}
			return new AcaoPesquisaSistemas(Collections.EMPTY_LIST);
			
		}
		
		
		/**
		 * Exibe a ação do cadastro de grupos do sistema 
		 * */
		public Action getAcaoCadastroGrupos(Container containerPrincipal) {
			if(containerPrincipal != null){
				this.containerPrincipal = containerPrincipal;
			}
			return acaoCadastroGrupos;
		}

		public Action getAcaoCadastroUsuarios(Container containerPrincipal) {
			if(containerPrincipal != null){
				this.containerPrincipal = containerPrincipal;
			}
			return new AcaoPesquisaUsuarios(Collections.EMPTY_LIST);
		}

		
		/**
		 * Retorna a ação relacionada com a pesquisa de funções
		 * */
		public Action getAcaoPesquisaFuncoes(Container containerPrincipal, List listenersFuncao, List listenersSistemas, boolean exibirBotoesManutencao) {
			//this.clearListenersFuncoes();
			//this.clearListenersSistemas();
			AcaoPesquisaFuncoes acaoPesquisaFuncoes = new AcaoPesquisaFuncoes(listenersFuncao == null ? Collections.EMPTY_LIST : listenersFuncao);
			acaoPesquisaFuncoes.setExibirBotoesManutencao(exibirBotoesManutencao);
			if(containerPrincipal != null){
				this.containerPrincipal = containerPrincipal;
			}
			/*if(listenersFuncao != null){
				this.pesquisaFuncoesListenersList = listenersFuncao;
			}else{
				this.pesquisaFuncoesListenersList = Collections.EMPTY_LIST;
			}*/
			/*if(listenersSistemas != null){
				this.pesquisaSistemaListenersList = listenersSistemas;
			}else{
				this.pesquisaSistemaListenersList = Collections.EMPTY_LIST;
			}*/
			
			return acaoPesquisaFuncoes;
		} 


		public Action getAcaoPesquisaSistemas(Container containerPrincipal, List listeners, boolean modal, boolean exibirBotoesManutencao) {
			//this.clearListenersSistemas();
			if(containerPrincipal != null){
				this.containerPrincipal = containerPrincipal;
			}
			AcaoPesquisaSistemas acaoPesquisaSistemas = new AcaoPesquisaSistemas(listeners);
			/*if(listeners != null){
				this.pesquisaSistemaListenersList = listeners;
			}else{
				this.pesquisaSistemaListenersList = Collections.EMPTY_LIST;
			}*/
			acaoPesquisaSistemas.setModal(modal);
			return acaoPesquisaSistemas;
		}
		
		public Action getAcaoPesquisaUsuarios(Container containerPrincipal, List listeners, boolean modal, boolean exibirBotoesManutencao) {
			//this.clearListenersUsuarios();
			if(containerPrincipal != null){
				this.containerPrincipal = containerPrincipal;
			}
			AcaoPesquisaUsuarios acaoPesquisaUsuarios = new AcaoPesquisaUsuarios(listeners);
			/*if(listeners != null){
				this.pesquisaUsuariosListenersList = listeners;
			}else{
				this.pesquisaUsuariosListenersList = Collections.EMPTY_LIST;
			}*/
			acaoPesquisaUsuarios.setModal(modal);
			return acaoPesquisaUsuarios;
		}
		
		class AcaoJanelaCadastroUsuarios extends AbstractAction{
			
			 private String strMetodo = "";
			 private Usuario usuario = null;
			 
			 public String getStrMetodo(){
				 return strMetodo;
			 }
			 
			 public void setStrMetodo(String strMetodo){
				 this.strMetodo = strMetodo;
			 }
			 
			 
			 public AcaoJanelaCadastroUsuarios(){ 	
				 	super(RecursosUtil.getInstance().getResource("key.menu.cadastro.usuarios"));
			 }
			 
			 
			public Usuario getUsuario() {
				return usuario;
			}

			public void setUsuario(Usuario usuario) {
				this.usuario = usuario;
			}

			public void actionPerformed(ActionEvent e) {
					synchronized(GerenciadorJanelas.this){
						/*if(frmUsuario != null){
							if(containerPrincipal != null){
								containerPrincipal.remove(frmUsuario);
							}
							frmUsuario.dispose();
							frmUsuario= null;
						}*/
						if(frmUsuario == null){
							frmUsuario = new FrmUsuario();
						}
						if(containerPrincipal != null){
							containerPrincipal.remove(frmUsuario);
							containerPrincipal.add(frmUsuario);
						}
						((FrmUsuario)frmUsuario).getBaseDispatchCRUDCommand().setStrMetodo(strMetodo);
						if(getUsuario() != null){
							UsuarioTelaVo usuarioTelaVo = new UsuarioTelaVo();
							usuarioTelaVo.setUsuario(getUsuario());
							((FrmUsuario)frmUsuario).setUsuarioTelaVo(usuarioTelaVo);
						} 
						((FrmUsuario)frmUsuario).atualizarEstado();
						frmUsuario.setVisible(true);
						frmUsuario.moveToFront();
					}
			}
		};

		private AcaoJanelaCadastroUsuarios acaoJanelaCadastroUsuarios = new AcaoJanelaCadastroUsuarios();
		
		/**Adiciona um ouvinte para o formulário de pesquisa de sistemas*/
		/*public void addPesquisaSistemaListener(PesquisaSistemaListener pesquisaSistemaListener){
			if(pesquisaSistemaListener != null){
				if(!pesquisaSistemaListenersList.contains(pesquisaSistemaListener)){
					pesquisaSistemaListenersList.add(pesquisaSistemaListener);
				}
			}
		}*/


		/**Controle dos ouvintes para o formulário de pesquisa de sistemas*/
		/*public void clearListenersSistemas(){
			try{
				this.pesquisaSistemaListenersList.clear();
			}catch(UnsupportedOperationException e){
				this.pesquisaSistemaListenersList = new LinkedList();
			}
		}*/		
		

		/**Limpas os ouvintes do formulário de pesquisa de funções*/
		/*public void clearListenersFuncoes(){
			try{
				this.pesquisaFuncoesListenersList.clear();
			}catch(UnsupportedOperationException e){
				this.pesquisaFuncoesListenersList = new LinkedList();
			}
		}*/		

		/*public void clearListenersUsuarios(){
			try{
				this.pesquisaUsuariosListenersList.clear();
			}catch(UnsupportedOperationException e){
				this.pesquisaUsuariosListenersList = new LinkedList();
			}
		}*/		
		
		
		/**Adiciona um ouvinte para a janela de pesquisa de funções*/
		/*public void addPesquisaFuncaoListener(PesquisaFuncaoListener pesquisaFuncaoListener){
			if(pesquisaFuncaoListener != null){
				if(!pesquisaFuncoesListenersList.contains(pesquisaFuncaoListener)){
					pesquisaFuncoesListenersList.add(pesquisaFuncaoListener);
				}
			}
		}*/

		/*public static void removePesquisaSistemaListener(PesquisaSistemaListener pesquisaSistemaListener){
			if(pesquisaSistemaListenersList.contains(pesquisaSistemaListener)){
				pesquisaSistemaListenersList.remove(pesquisaSistemaListener);
			}
		}*/

		/**Adiciona um ouvinte para a janela de pesquisa de funções*/
		/*public void addPesquisaUsuarioListener(PesquisaUsuarioListener pesquisaUsuarioListener){
			if(pesquisaUsuarioListener != null){
				if(!pesquisaUsuariosListenersList.contains(pesquisaUsuarioListener)){
					pesquisaUsuariosListenersList.add(pesquisaUsuarioListener);
				}
			}
		}*/

		/**Remove um ouvinte para a janela de pesquisa de funções*/
		/*public static void removePesquisaUsuarioListener(PesquisaUsuarioListener pesquisaUsuarioListener){
			if(pesquisaUsuariosListenersList.contains(pesquisaUsuarioListener)){
				pesquisaUsuariosListenersList.remove(pesquisaUsuarioListener);
			}
		}*/
		
		/*public static void removePesquisaFuncaoListener(PesquisaFuncaoListener pesquisaFuncaoListener){
			if(pesquisaFuncoesListenersList.contains(pesquisaFuncaoListener)){
				pesquisaFuncoesListenersList.remove(pesquisaFuncaoListener);
			}
		}*/
		
		/**Limpa todos os listeners configurados no gerenciador*/
		/*public void clearTodosListeners(){
			this.clearListenersFuncoes();
			this.clearListenersSistemas();
			this.clearListenersUsuarios();
		}*/

		
		
		public Container getContainerPrincipal() {
			return containerPrincipal;
		}

		public void setContainerPrincipal(Container containerPrincipal) {
			this.containerPrincipal = containerPrincipal;
		}

		public BaseJInternalFrame getFrmPesquisaFuncoes() {
			return frmPesquisaFuncoes;
		}

		public BaseJInternalFrame getFrmPesquisaSistemas() {
			return frmPesquisaSistemas;
		}

		public BaseJInternalFrame getFrmFuncoes() {
			return frmFuncoes;
		}

		public void setFrmFuncoes(BaseJInternalFrame frmFuncoes) {
			this.frmFuncoes = frmFuncoes;
		}

		public BaseJInternalFrame getFrmGrupos() {
			return frmGrupos;
		}

		public void setFrmGrupos(BaseJInternalFrame frmGrupos) {
			this.frmGrupos = frmGrupos;
		}


		public void atualizarLookAndFeel(){
			if(janelaPrincipal != null){
				SwingUtilities.updateComponentTreeUI(janelaPrincipal);
			}
			if(frmAssociacoes != null){
				SwingUtilities.updateComponentTreeUI(frmAssociacoes);
			}
			if(frmFuncoes != null){
				SwingUtilities.updateComponentTreeUI(frmFuncoes);
			}
			if(frmGrupos != null){
				SwingUtilities.updateComponentTreeUI(frmGrupos);
			}
			if(frmPesquisaFuncoes != null){
				SwingUtilities.updateComponentTreeUI(frmPesquisaFuncoes);
			}
			if(frmPesquisaSistemas != null){
				SwingUtilities.updateComponentTreeUI(frmPesquisaSistemas);
			}
			if(frmPesquisaUsuarios != null){
				SwingUtilities.updateComponentTreeUI(frmPesquisaUsuarios);
			}
			if(frmSistemas != null){
				SwingUtilities.updateComponentTreeUI(frmSistemas);
			}
			if(frmUsuario != null){
				SwingUtilities.updateComponentTreeUI(frmUsuario);
			}

			
		}
}
