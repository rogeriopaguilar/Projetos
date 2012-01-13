package dnsec.shared.swing.base;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

public class BaseJMenuItem extends JMenuItem{

	public BaseJMenuItem(){
		super();
	}
	
	public BaseJMenuItem(Action action){
		super(action);
	}

	public BaseJMenuItem(Icon icon){
		super(icon);
	}

	public BaseJMenuItem(String text){
		super(text);
	}

	public BaseJMenuItem(String text, Icon icon){
		super(text, icon);
	}

	public BaseJMenuItem(String text, int mnemonic){
		super(text, mnemonic);
	}
	
	
}
