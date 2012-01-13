package dnsec.shared.swing.base;

import java.awt.LayoutManager;

import javax.swing.JPanel;

public class BaseJPanel extends JPanel{

	public BaseJPanel(){
			super();
	}

	public BaseJPanel(boolean isDoubleBuffered){
		super(isDoubleBuffered);
	}

	public BaseJPanel(LayoutManager layoutManager){
		super(layoutManager);
	}

	public BaseJPanel(LayoutManager layoutManager, boolean isDoubleBuffered){
		super(layoutManager, isDoubleBuffered);
	}

}
