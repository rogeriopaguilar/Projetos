package dnsec.shared.swing.base;

import javax.swing.Action;
import javax.swing.JMenu;

public class BaseJMenu extends JMenu{
	
	public BaseJMenu(){
		super();
	}

	public BaseJMenu(Action action){
		super(action);
	}

	public BaseJMenu(String str){
		super(str);
	}

	public BaseJMenu(String str, boolean b){
		super(str, b);
	}

}
