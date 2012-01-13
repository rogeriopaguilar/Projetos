package dnsec.shared.swing.base.janelaeerro;

import javax.swing.JPanel;

import dnsec.shared.swing.base.BaseJInternalFrame;
import dnsec.shared.swing.base.BaseJTextArea;

public class JanelaErro extends BaseJInternalFrame{

	private BaseJTextArea txtErro = new BaseJTextArea();
	
	public JanelaErro(String erro)
	{
		this.inicializarComponentes();
		txtErro.setText(erro);
	}

	protected void inicializarComponentes()
	{
		JPanel painelErro = new JPanel();
		painelErro.add(txtErro);
		getContentPane().add(painelErro);
	}
	
}
