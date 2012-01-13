package dnsec.shared.swing.base;

import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.util.RecursosUtil;
import dnsec.shared.util.StringUtil;

public class BaseJInternalFrame extends JInternalFrame {

	private DialogoErro dialogoErro = new DialogoErro();

	protected boolean inicializarMaximizado = false;

	public BaseJInternalFrame(boolean inicializarMaximizado) {
		super();
		this.inicializarMaximizado = inicializarMaximizado;
		this.inicializar();
	}

	/**
	 * Método sobrescrito para fazer funcionar a chamada ao listener caso a
	 * janela seja fechada
	 */
	/*
	 * public void dispose(){ try{ setClosed(true); }catch(Exception e){
	 * e.printStackTrace(); } super.dispose(); }
	 */

	protected void inicializar() {

		
		this.addInternalFrameListener(new InternalFrameListener() {
			public void internalFrameActivated(InternalFrameEvent e) {
				/* Por padrão deixa a tela maximizada */
				if (inicializarMaximizado) {
					try {
						BaseJInternalFrame.this.setMaximum(true);
					} catch (PropertyVetoException ex) {
						ex.printStackTrace();
					}
				}

			}

			public void internalFrameClosed(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}

			public void internalFrameClosing(InternalFrameEvent e) {
				/*
				 * Por padrão chama o método fecharJanela, que deve ser
				 * sobrescrito para liberar recursos, caso seja necessário
				 */
				BaseJInternalFrame.this.fecharJanela();
			}

			public void internalFrameDeactivated(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}

			public void internalFrameDeiconified(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}

			public void internalFrameIconified(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}

			public void internalFrameOpened(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}
		});

		this.setClosable(true);
		this.setFocusable(true);
		this.setIconifiable(false); //BUG
		this.setResizable(true);
		this.setMaximizable(true);
		this.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);

	}

	public BaseJInternalFrame() {
		super();
		this.inicializar();
	}

	public BaseJInternalFrame(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	public BaseJInternalFrame(String title, boolean resizable,
			boolean closable, boolean maximizable) {
		super(title, resizable, closable, maximizable);
		// TODO Auto-generated constructor stub
	}

	public BaseJInternalFrame(String title, boolean resizable, boolean closable) {
		super(title, resizable, closable);
		// TODO Auto-generated constructor stub
	}

	public BaseJInternalFrame(String title, boolean resizable) {
		super(title, resizable);
		// TODO Auto-generated constructor stub
	}

	public BaseJInternalFrame(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public void fecharJanela() {
		this.setVisible(false);
		dispose();
	}

	public void centralizarJanela() {
		setLocation((getParent().getSize().width - getWidth()) / 2,
				(getParent().getSize().height - getHeight()) / 2);
	}

	public void tratarMensagemErro(CommandException commandException) {

		if (commandException.getCause() != null
				&& !(commandException.getCause() instanceof CommandException)) {
			// Erro não esperado na aplicação
			Throwable exception = commandException.getCause();
			String msgErroPadrao = RecursosUtil.getInstance().getResource(
					"key.jpanelmanutencao.erro.nao.esperado.operação");
			exception.printStackTrace();
			CharArrayWriter arrayMsg = new CharArrayWriter();
			PrintWriter printWriter = new PrintWriter(arrayMsg);
			exception.printStackTrace(printWriter);
			String msgErroEspecifico = arrayMsg.toString();
			dialogoErro.setMsgErro(msgErroPadrao
					+ System.getProperty("line.separator") + msgErroEspecifico);
			if (getParent() != null) {
				dialogoErro.setSize(getParent().getSize().width / 2,
						getParent().getSize().height / 2);
				dialogoErro
						.setLocation((getParent().getSize().width - dialogoErro
								.getWidth()) / 2,
								(getParent().getSize().height - dialogoErro
										.getHeight()) / 2);
			} else {
				dialogoErro.setSize(
						Toolkit.getDefaultToolkit().getScreenSize().width / 2,
						Toolkit.getDefaultToolkit().getScreenSize().height / 2);
				dialogoErro
						.setLocation(
								(Toolkit.getDefaultToolkit().getScreenSize().width - dialogoErro
										.getWidth()) / 2,
								(Toolkit.getDefaultToolkit().getScreenSize().height - dialogoErro
										.getHeight()) / 2);
			}
			dialogoErro.setVisible(true);
		} else {
			// Erro esperado na aplicação
			String strKeyMsg = StringUtil.NullToStr(commandException
					.getStrKeyConfigFile());
			Object[] argsMsg = commandException.getObjArgs();
			if (argsMsg == null) {
				argsMsg = new Object[0];
			}
			String msgErroPadrao = RecursosUtil.getInstance().getResource(
					"key.jpanelmanutencao.erro.operação");
			String msgErroEspecifico = "";
			if (!"".equals(strKeyMsg)) {
				msgErroEspecifico = RecursosUtil.getInstance().getResource(
						strKeyMsg, argsMsg);
			}
			JOptionPane.showMessageDialog(this, msgErroPadrao
					+ System.getProperty("line.separator") + msgErroEspecifico,
					RecursosUtil.getInstance().getResource(
							"key.jpanelmanutencao.erro.titulo.janela"),
					JOptionPane.ERROR_MESSAGE, GerenciadorJanelas.ICONE_ERRO);

		}

	}

}
