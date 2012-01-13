package dnsec.shared.swing.base;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BaseJModalInternalFrame extends BaseJInternalFrame{

	private JPanel glass = new JPanel();
	private JFrame janelaABloquear;
	

	
	
	public void setVisible(boolean flag){
		super.setVisible(true);
		if(janelaABloquear != null){
			janelaABloquear.setGlassPane(glass);
		}
		if(glass != null){
			glass.setVisible(true);
		}
	}
	
	public BaseJModalInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable, JFrame janelaABloquear) {
		super(title, resizable, closable, maximizable, iconifiable);
		this.janelaABloquear = janelaABloquear;
	}

	public BaseJModalInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, JFrame janelaABloquear) {
		super(title, resizable, closable, maximizable);
		this.janelaABloquear = janelaABloquear;
	}

	public BaseJModalInternalFrame(String title, boolean resizable, boolean closable, JFrame janelaABloquear) {
		super(title, resizable, closable);
		this.janelaABloquear = janelaABloquear;
	}

	public BaseJModalInternalFrame(String title, boolean resizable, JFrame janelaABloquear) {
		super(title, resizable);
		this.janelaABloquear = janelaABloquear;
	}

	public BaseJModalInternalFrame(String title, JFrame janelaABloquear) {
		super(title);
		this.janelaABloquear = janelaABloquear;
	}	

	public BaseJModalInternalFrame(JFrame janelaABloquear){
		super();
		this.janelaABloquear = janelaABloquear;
	}

}
