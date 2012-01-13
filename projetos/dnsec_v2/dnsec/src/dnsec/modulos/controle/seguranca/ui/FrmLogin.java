package dnsec.modulos.controle.seguranca.ui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dnsec.modulos.controle.seguranca.business.CommandLogin;
import dnsec.modulos.controle.seguranca.vo.ControleSegurancaVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.factory.CommandFactory;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.swing.base.BaseJButton;
import dnsec.shared.swing.base.BaseJDialog;
import dnsec.shared.swing.base.BaseJLabel;
import dnsec.shared.swing.base.BaseJPasswordField;
import dnsec.shared.swing.base.BaseJTextBox;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.RecursosUtil;

public class FrmLogin extends BaseJDialog
{

	
	
	private BaseJLabel lblUsuario;
	private BaseJLabel lblSenha;
	private BaseJTextBox txtUsuario;
	private BaseJPasswordField txtSenha;
	private BaseJButton cmdLogin;
	private BaseJButton cmdCancelar;
	
	
	private void inicializarComponentes()
	{
		JPanel painelControlesUsrSenha = new JPanel();
		painelControlesUsrSenha.setLayout(new GridLayout(2,2));
		
		lblUsuario = new BaseJLabel("Usuário");
		txtUsuario = new BaseJTextBox();
		lblSenha = new BaseJLabel("Senha");
		txtSenha = new BaseJPasswordField();
		
		painelControlesUsrSenha.add(lblUsuario);
		painelControlesUsrSenha.add(txtUsuario);
		painelControlesUsrSenha.add(lblSenha);
		painelControlesUsrSenha.add(txtSenha);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(painelControlesUsrSenha, BorderLayout.CENTER);
		JPanel painelBotoes = new JPanel();
		cmdLogin = new BaseJButton("Login", GerenciadorJanelas.ICONE_BOTAO_CONFIRMAR);
		cmdCancelar = new BaseJButton("Cancelar", GerenciadorJanelas.ICONE_BOTAO_CANCELAR);
		painelBotoes.add(cmdLogin);
		painelBotoes.add(cmdCancelar);
		getContentPane().add(painelBotoes, BorderLayout.SOUTH);
		pack();
		
		cmdLogin.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_LOGIN);
					baseDispatchCRUDCommand.setStrMetodo(CommandLogin.METODO_LOGIN);
					Usuario usuario = new Usuario();
					try{
						if("".equals(txtUsuario.getText().trim())){
							CommandException exception = new CommandException();
							exception.setStrKeyConfigFile("key.erro.login.informe.usuario");
							//exception.setObjArgs(new Object[] { grupo } );
							exception.setObjArgs(new Object[0]);
							throw exception;
						}
						usuario.setCodUsuarioUsua(txtUsuario.getText());
						usuario.setCodSenhaUsua(new String(txtSenha.getPassword()));
						Object args[] = new Object[]{usuario, Constantes.COD_SIST_ADM};
						desabilitarBotoes();
						Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
						ControleSegurancaVo controleSegurancaVo = new ControleSegurancaVo(
							(Usuario)objRetorno[0],
							(Map)objRetorno[1],
							(Map)objRetorno[2],
							(Map)objRetorno[3]
						);
						GerenciadorJanelas.getInstance().setControleSegurancaVo(controleSegurancaVo);
						setVisible(false);
						dispose();
						GerenciadorJanelas.getInstance().abrirJanelaPrincipal(true);						
					}catch(CommandException commandException){
						FrmLogin.super.tratarMensagemErro(commandException); 
						requestFocus();
					}
					habilitarBotoes();
				}
			}	
		);
		
		
		cmdCancelar.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if(
							    JOptionPane.showConfirmDialog(FrmLogin.this,RecursosUtil.getInstance().getResource("key.janelaprincipal.confirmacao.saida"),RecursosUtil.getInstance().getResource("key.janelaprincipal.confirmacao.saida.titulo.janela"), JOptionPane.OK_CANCEL_OPTION)
							    == JOptionPane.OK_OPTION		
						   )
						{ 
							dispose();
							System.exit(0);
						}
					}
				}
		);
		
	}
	
	public FrmLogin(){
		setTitle("DNSEC - Login");
		this.inicializarComponentes();
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		centralizarJanelaNaTela();		
	}
	
	public void desabilitarBotoes(){
		cmdLogin.setEnabled(false);
		cmdCancelar.setEnabled(false);
	}

	public void habilitarBotoes(){
		cmdLogin.setEnabled(true);
		cmdCancelar.setEnabled(true);
	}

	public FrmLogin(Dialog owner, boolean modal) throws HeadlessException {
		super(owner, modal);
		// TODO Auto-generated constructor stub
	}

	public FrmLogin(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) throws HeadlessException {
		super(owner, title, modal, gc);
		// TODO Auto-generated constructor stub
	}

	public FrmLogin(Dialog owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
		// TODO Auto-generated constructor stub
	}

	public FrmLogin(Dialog owner, String title) throws HeadlessException {
		super(owner, title);
		// TODO Auto-generated constructor stub
	}

	public FrmLogin(Dialog owner) throws HeadlessException {
		super(owner);
	}

	public FrmLogin(Frame owner, boolean modal) throws HeadlessException {
		super(owner, modal);
	}

	public FrmLogin(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
	}

	public FrmLogin(Frame owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
	}

	public FrmLogin(Frame owner, String title) throws HeadlessException {
		super(owner, title);
	}

	public FrmLogin(Frame owner) throws HeadlessException {
		super(owner);
	}
	
	
}
