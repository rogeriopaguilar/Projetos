/**
 * 20/12/2005 - Rog�rio de Paula Aguilar
 * http://www.rogeriaoguilar.eti.br
 * 
 * Exce��o lan�ada pelos m�todos das classes command
 * */

package dnsec.shared.icommand.exception;

import dnsec.shared.exception.BaseException;

public class CommandException extends BaseException{

	public CommandException(){
		super();
		
	}

	public CommandException(String str){
		super(str);
	}

	public CommandException(Throwable throwable){
		super(throwable);
	}
	
	public CommandException(String str, Throwable throwable){
		super(str, throwable);
	}

}
