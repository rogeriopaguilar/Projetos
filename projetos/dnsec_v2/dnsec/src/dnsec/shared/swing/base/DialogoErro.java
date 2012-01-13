package dnsec.shared.swing.base;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;

import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.util.RecursosUtil;

public class DialogoErro extends BaseJDialog{

		private BaseJTextArea txtErro = new BaseJTextArea();
		private BaseJPanel painelBotoes = new BaseJPanel();
		private BaseJButton cmdOk = new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.erro.titulo.botao"), GerenciadorJanelas.ICONE_BOTAO_CANCELAR);

		protected void inicializar(){
			setTitle(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.erro.titulo.janela"));
			setModal(true);
			setLayout(new BorderLayout());
			txtErro.setEditable(false);
			txtErro.setForeground(Color.RED);
			add(new JScrollPane(txtErro), BorderLayout.CENTER);
			painelBotoes.add(cmdOk);
			add(painelBotoes, BorderLayout.SOUTH);
			cmdOk.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			pack();
		}

		public void setMsgErro(String msgErro){
			txtErro.setText(msgErro);
		}
		
		public DialogoErro(){
			inicializar();
		}

		public DialogoErro(Dialog owner, boolean modal) throws HeadlessException {
			super(owner, modal);
			inicializar();
		}

		public DialogoErro(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) throws HeadlessException {
			super(owner, title, modal, gc);
			inicializar();
		}

		public DialogoErro(Dialog owner, String title, boolean modal) throws HeadlessException {
			super(owner, title, modal);
			inicializar();
		}

		public DialogoErro(Dialog owner, String title) throws HeadlessException {
			super(owner, title);
			inicializar();
		}

		public DialogoErro(Dialog owner) throws HeadlessException {
			super(owner);
			inicializar();
		}

		public DialogoErro(Frame owner, boolean modal) throws HeadlessException {
			super(owner, modal);
			inicializar();
		}

		public DialogoErro(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
			super(owner, title, modal, gc);
			inicializar();
		}

		public DialogoErro(Frame owner, String title, boolean modal) throws HeadlessException {
			super(owner, title, modal);
			inicializar();
		}

		public DialogoErro(Frame owner, String title) throws HeadlessException {
			super(owner, title);
			inicializar();
		}

		public DialogoErro(Frame owner) throws HeadlessException {
			super(owner);
			inicializar();
		}
		
}
