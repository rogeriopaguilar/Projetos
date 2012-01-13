package dnsec.shared.swing.base;

import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class BaseJFrame extends JFrame{

	protected Toolkit tk = Toolkit.getDefaultToolkit();

	public BaseJFrame(){
		this.addWindowListener(
				new WindowAdapter(){
					public void windowClosing(WindowEvent evt){
						/*Por padrão chama o método fecharJanela, que deve ser sobrescrito
						  para liberar recursos, caso seja necessário
						*/
						BaseJFrame.this.fecharJanela();
					}
				}
		);
	}

	public BaseJFrame(GraphicsConfiguration graphicsConfiguration){
			super(graphicsConfiguration);
	}

	public BaseJFrame(String title){
		super(title);
	}

	public BaseJFrame(String title, GraphicsConfiguration graphicsConfiguration){
		super(title, graphicsConfiguration);
	}

	/**
	 * Método chamado antes de fechar a janela. 
	 * Deve ser sobrescrito para realizar uma ação
	 * específica.
	 * */
	protected void fecharJanela(){
		//System.out.println("Fechando a janela...");
		dispose();
	}
	
	public void centralizarJanelaNaTela() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		setLocation( (tk.getScreenSize().width - getWidth()) / 2,
				(tk.getScreenSize().height - getHeight()) / 2);
	}

}
