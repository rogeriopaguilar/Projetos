package dnsec.modulos.principal.ui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import dnsec.modulos.controle.seguranca.vo.ControleSegurancaVo;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.listener.teste.PesquisaFuncaoListenerTeste;
import dnsec.shared.listener.teste.PesquisaSistemaListenerTeste;
import dnsec.shared.swing.base.BaseJFrame;
import dnsec.shared.swing.base.BaseJMenu;
import dnsec.shared.swing.base.BaseJMenuBar;
import dnsec.shared.swing.base.BaseJMenuItem;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.RecursosUtil;

public class JanelaPrincipal extends BaseJFrame{
		
		private static  ControleSegurancaVo controleSegurancaVo;
		
	
		private Container containerPrincipal;
		private BaseJMenuBar menuBar = new BaseJMenuBar(); 

		private JToolBar toolbar = new JToolBar();

		private JPanel jpStatus = new JPanel();
		private JLabel lblStatus = new JLabel();		
		
		
		
		
		/**
		 *Ações para a janela principal
		 * */
		Action acaoSair;

		/**
		 * Ações relacionadas aos menus da aplicação
		 * */
		
	
		/**
		 * Menus da aplicação
		 * */

		/**Menus de cadastro*/
		private BaseJMenu mnuCadastro;
		private BaseJMenuItem mnuCadastroGrupos;
		private BaseJMenuItem mnuCadastroFuncoes;
		private BaseJMenuItem mnuCadastroSistemas;
		private BaseJMenuItem mnuAssociacoes;
		private BaseJMenuItem mnuCadastroUsuarios;
		private BaseJMenu mnuAjuda;
		private BaseJMenuItem mnuSobre;
		private BaseJMenu mnuLookAndFeel;
		private JRadioButtonMenuItem mnuLookAndFeelDefault;
		private JRadioButtonMenuItem mnuLookAndFeelWindows;
		private JRadioButtonMenuItem mnuLookAndFeelMotif;
		private JRadioButtonMenuItem mnuLookAndFeelQuaqua;
		private JRadioButtonMenuItem mnuLookAndFeelSubstance;
		
		private BaseJMenuItem mnuSair;

		/**Menus para os formulários de Pesquisa*/
		private BaseJMenu mnuPesquisa;
		private BaseJMenuItem mnuPesquisaSistemas;
		private BaseJMenuItem mnuPesquisaFuncoes;

		
		/**
		 Ação que implementa o mecanismo de mudança de aparência da interface gráfica
		*/
	private class MudarLookAndFeel implements ActionListener{
		public void actionPerformed(ActionEvent evt){
			try{
				if(evt.getSource() == mnuLookAndFeelMotif)
					UIManager.setLookAndFeel(GerenciadorJanelas.lookAndFeel[0]);

				else if(evt.getSource() == mnuLookAndFeelDefault)
					UIManager.setLookAndFeel(GerenciadorJanelas.lookAndFeel[1]);
				else if(evt.getSource() == mnuLookAndFeelWindows)
					UIManager.setLookAndFeel(GerenciadorJanelas.lookAndFeel[2]);
				else if(evt.getSource() == mnuLookAndFeelQuaqua)
					UIManager.setLookAndFeel(GerenciadorJanelas.lookAndFeel[3]);
				else
					UIManager.setLookAndFeel(GerenciadorJanelas.lookAndFeel[4]);
				
				GerenciadorJanelas.getInstance().atualizarLookAndFeel();
				
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Erro ao mudar a aparência! Erro:" + e);
			}
		}

	}

		
		/**
		 * Inicializa a janela principal do programa, colocando
		 * a mesma no centro da tela e com o tamanho igual à metade
		 * da largura e altura da janela
		 * */
		protected void inicializarComponentes(){
			int larguraTela = tk.getScreenSize().width - 200;
			int alturaTela = tk.getScreenSize().height - 200;
			this.setSize(larguraTela, alturaTela);
			//this.setLocation( (larguraTela + 200) / 2, (alturaTela + 200) / 2);
			this.setLocation((tk.getScreenSize().width - larguraTela) / 2, (tk.getScreenSize().height - alturaTela) / 2);
			this.setTitle(RecursosUtil.getInstance().getResource(Constantes.KEY_TITULO_JANELA_PRINCIPAL));
			containerPrincipal = new JDesktopPane();
			//containerPrincipal = getContentPane();
			getContentPane().setLayout(new BorderLayout());
			
			//Associa o container principal com o gerenciador de janelas
			GerenciadorJanelas.getInstance().setContainerPrincipal(containerPrincipal);


			
			/**Ação para sair do sistema*/
			acaoSair = new AbstractAction(RecursosUtil.getInstance().getResource("key.menu.sair"), GerenciadorJanelas.ICONE_BOTAO_SAIR){
				public void actionPerformed(ActionEvent e) {
					if(
						    JOptionPane.showConfirmDialog(JanelaPrincipal.this,RecursosUtil.getInstance().getResource("key.janelaprincipal.confirmacao.saida"),RecursosUtil.getInstance().getResource("key.janelaprincipal.confirmacao.saida.titulo.janela"), JOptionPane.OK_CANCEL_OPTION)
						    == JOptionPane.OK_OPTION		
					   )
					{ 
						System.exit(0);
					}
				}
			};
			acaoSair.putValue(Action.SHORT_DESCRIPTION, "Sair");
			
			//Inicializando menus de pesquisa

			mnuPesquisaSistemas = new BaseJMenuItem(RecursosUtil.getInstance().getResource("key.menu.cadastro.sistemas"));
			mnuPesquisaSistemas.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						List listenersSistemas = new LinkedList();
						listenersSistemas.add(new PesquisaSistemaListenerTeste());
						GerenciadorJanelas.getInstance().getAcaoPesquisaSistemas(JanelaPrincipal.this.containerPrincipal, listenersSistemas, true, false).actionPerformed(e);
					}
				}
			);
			
			mnuPesquisaFuncoes = new BaseJMenuItem(RecursosUtil.getInstance().getResource("key.menu.pesquisa.funcoes"));
			mnuPesquisaFuncoes.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						List listenersFuncoes = new LinkedList();
						listenersFuncoes.add(new PesquisaFuncaoListenerTeste());
						GerenciadorJanelas.getInstance().getAcaoPesquisaFuncoes(JanelaPrincipal.this.containerPrincipal, listenersFuncoes, Collections.EMPTY_LIST, true).actionPerformed(e);
					}
				}
			);
			
			//Inicializando menus
			mnuCadastro = new BaseJMenu(RecursosUtil.getInstance().getResource(Constantes.KEY_MENU_CADASTROS));

			Action acaoCadastroGrupos = GerenciadorJanelas.getInstance().getAcaoCadastroGrupos(JanelaPrincipal.this.containerPrincipal);
			mnuCadastroGrupos = new BaseJMenuItem(acaoCadastroGrupos);
			
			/**Referência para a ação que iniciliza a janela do cadastro de funções*/
			Action acaoCadastroFuncoes = GerenciadorJanelas.getInstance().getAcaoCadastroFuncoes(JanelaPrincipal.this.containerPrincipal);
			mnuCadastroFuncoes = new BaseJMenuItem(acaoCadastroFuncoes);

			/**Referência para a ação que inicializa a janela do cadastro de sistemas*/
			Action acaoCadastroSistemas = GerenciadorJanelas.getInstance().getAcaoCadastroSistemas(JanelaPrincipal.this.containerPrincipal); //, new LinkedList(), false, true);
			mnuCadastroSistemas = new BaseJMenuItem(acaoCadastroSistemas);
			mnuCadastroSistemas.setText(RecursosUtil.getInstance().getResource("key.menu.cadastro.sistemas"));

			//TODO
			Action acaoCadastroUsuarios = GerenciadorJanelas.getInstance().getAcaoCadastroUsuarios(JanelaPrincipal.this.containerPrincipal);
			mnuCadastroUsuarios = new BaseJMenuItem(acaoCadastroUsuarios);
			
			
			/**Referência para a ação que inicializa a janela de associações*/
			Action acaoCadastroAssociacoes = GerenciadorJanelas.getInstance().getAcaoAssociacoes(JanelaPrincipal.this.containerPrincipal);
			mnuAssociacoes = new BaseJMenuItem(acaoCadastroAssociacoes);
			
			
			
			mnuSair = new BaseJMenuItem(acaoSair);
			mnuPesquisa = new BaseJMenu(RecursosUtil.getInstance().getResource("key.menu.pesquisas"));

			mnuAjuda = new BaseJMenu("?");
			mnuSobre = new BaseJMenuItem("Sobre");
			
			mnuSobre.addActionListener(
					new ActionListener(){

						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog(JanelaPrincipal.this, "DNSEC - Controle de Segurança\nVersão 0.2 - beta\n2006-2007 - Amare Sistemas\nContato: rogeriopaguilar@terra.com.br","Sobre", JOptionPane.INFORMATION_MESSAGE);
						}
					}
			);
			
			MudarLookAndFeel mudarLookAndFeel = new MudarLookAndFeel();
			mnuLookAndFeel = new BaseJMenu("Aparência");
			mnuLookAndFeelDefault = new  JRadioButtonMenuItem("Default");
			mnuLookAndFeelDefault.addActionListener(mudarLookAndFeel);
			mnuLookAndFeelWindows =  new JRadioButtonMenuItem("Windows");
			mnuLookAndFeelWindows.addActionListener(mudarLookAndFeel);
			mnuLookAndFeelMotif = new JRadioButtonMenuItem("Motif");
			mnuLookAndFeelMotif.addActionListener(mudarLookAndFeel);
			mnuLookAndFeelQuaqua =  new JRadioButtonMenuItem("Quaqua (Mac)");
			mnuLookAndFeelQuaqua.addActionListener(mudarLookAndFeel);
			mnuLookAndFeelSubstance =  new JRadioButtonMenuItem("Substance");
			mnuLookAndFeelSubstance.addActionListener(mudarLookAndFeel);
			ButtonGroup grupoBotoesLookAndFeel = new ButtonGroup();
			grupoBotoesLookAndFeel.add(mnuLookAndFeelDefault);
			grupoBotoesLookAndFeel.add(mnuLookAndFeelWindows);
			grupoBotoesLookAndFeel.add(mnuLookAndFeelMotif);
			grupoBotoesLookAndFeel.add(mnuLookAndFeelQuaqua);
			grupoBotoesLookAndFeel.add(mnuLookAndFeelSubstance);
			mnuLookAndFeelDefault.setSelected(true);
			
			this.montarMenus();

			toolbar.add(acaoCadastroSistemas);
			toolbar.add(acaoCadastroFuncoes);
			toolbar.add(acaoCadastroUsuarios);
			toolbar.add(acaoCadastroGrupos);
			toolbar.add(acaoCadastroAssociacoes);
			toolbar.add(acaoSair);
			getContentPane().add(toolbar, BorderLayout.NORTH);
			getContentPane().add(containerPrincipal, BorderLayout.CENTER);

			jpStatus.setLayout(new BorderLayout());
			jpStatus.add(lblStatus, BorderLayout.CENTER);
			jpStatus.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			getContentPane().add(jpStatus,BorderLayout.SOUTH);		
		}

		/**
		 * Configura os menus da aplicação
		 * */
		protected void montarMenus(){
			//Menu de cadastro
			menuBar.add(mnuCadastro);
			mnuCadastro.add(mnuCadastroSistemas);
			mnuCadastro.add(mnuCadastroFuncoes);
			mnuCadastro.add(mnuCadastroUsuarios);
			mnuCadastro.add(mnuCadastroGrupos);
			mnuCadastro.add(mnuAssociacoes);
			mnuLookAndFeel.add(mnuLookAndFeelDefault); 
			mnuLookAndFeel.add(mnuLookAndFeelMotif); 
			mnuLookAndFeel.add(mnuLookAndFeelQuaqua); 
			//mnuLookAndFeel.add(mnuLookAndFeelWindows); 
			//mnuLookAndFeel.add(mnuLookAndFeelSubstance); 
			//menuBar.add(mnuLookAndFeel);
			mnuAjuda.add(mnuSobre);
			menuBar.add(mnuAjuda);
			mnuCadastro.addSeparator();
			mnuCadastro.add(mnuSair);
			//Menu de pesquisa (adicionado apenas para debug das telas de pesquisa)
			/*menuBar.add(mnuPesquisa);
			mnuPesquisa.add(mnuPesquisaSistemas);
			mnuPesquisa.add(mnuPesquisaFuncoes);*/
			
			this.setJMenuBar(menuBar);
		}
		
		public JanelaPrincipal(){
			setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("config/resource/dnsec.gif")));
			this.inicializarComponentes();
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
		}

		/**
		 * Método chamado antes de fechar a janela. 
		 * Deve ser sobrescrito para realizar uma ação
		 * específica.
		 * */
		protected void fecharJanela(){
			acaoSair.actionPerformed(null);
		}

		public static ControleSegurancaVo getControleSegurancaVo() {
			return controleSegurancaVo;
		}

		public static void setControleSegurancaVo(
				ControleSegurancaVo controleSegurancaVo) {
			JanelaPrincipal.controleSegurancaVo = controleSegurancaVo;
		}

		public void setStatusMsg(String msg){
			lblStatus.setText(msg);
		}
}
