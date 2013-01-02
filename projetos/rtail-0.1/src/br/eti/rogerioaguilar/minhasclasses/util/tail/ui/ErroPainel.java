package br.eti.rogerioaguilar.minhasclasses.util.tail.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Painel para mensagens de erro
 * @author Rogério de Paula Aguilar - 2008
 * */
public class ErroPainel extends JPanel {
	private JLabel jLabel1;
	private BorderLayout borderLayout1;
	private JTextArea jTextArea1;
	private String msg;

	public ErroPainel(String msg) {
		try {
			this.msg = msg;
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() throws Exception {
		setLayout(this.borderLayout1);
		this.jLabel1.setText("Ocorreu um problema ao abrir o arquivo!");
		this.jLabel1.setForeground(new Color(0, 82, 255));
		this.jLabel1.setFont(new Font("Dialog", 0, 12));
		add(this.jLabel1, "North");
		add(new JScrollPane(this.jTextArea1), "Center");
		this.jTextArea1.setText(this.msg);
		this.jTextArea1.setEditable(false);
		this.jTextArea1.setSize(100, 100);
	}
}
