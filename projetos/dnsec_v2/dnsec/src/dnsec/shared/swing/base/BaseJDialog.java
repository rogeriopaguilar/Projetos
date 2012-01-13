package dnsec.shared.swing.base;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.util.RecursosUtil;
import dnsec.shared.util.StringUtil;

public class BaseJDialog extends JDialog{

	protected Toolkit tk = Toolkit.getDefaultToolkit();

	
	public BaseJDialog() throws HeadlessException {
		super();
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public BaseJDialog(Dialog owner, boolean modal) throws HeadlessException {
		super(owner, modal);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public BaseJDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) throws HeadlessException {
		super(owner, title, modal, gc);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public BaseJDialog(Dialog owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public BaseJDialog(Dialog owner, String title) throws HeadlessException {
		super(owner, title);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public BaseJDialog(Dialog owner) throws HeadlessException {
		super(owner);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public BaseJDialog(Frame owner, boolean modal) throws HeadlessException {
		super(owner, modal);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public BaseJDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public BaseJDialog(Frame owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public BaseJDialog(Frame owner, String title) throws HeadlessException {
		super(owner, title);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public BaseJDialog(Frame owner) throws HeadlessException {
		super(owner);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	public void centralizarJanelaNaTela() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		setLocation( (tk.getScreenSize().width - getWidth()) / 2,
				(tk.getScreenSize().height - getHeight()) / 2);
	}

	public void tratarMensagemErro(CommandException commandException) {
		DialogoErro dialogoErro = new DialogoErro();

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
			dialogoErro.setSize(
					Toolkit.getDefaultToolkit().getScreenSize().width / 2,
					Toolkit.getDefaultToolkit().getScreenSize().height / 2);
			dialogoErro
					.setLocation(
							(Toolkit.getDefaultToolkit().getScreenSize().width - dialogoErro
									.getWidth()) / 2,
							(Toolkit.getDefaultToolkit().getScreenSize().height - dialogoErro
									.getHeight()) / 2);
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
