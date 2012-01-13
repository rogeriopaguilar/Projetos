/**
 * 20/12/2005 - Rogério de Paula Aguilar
 * http://www.rogeriaoguilar.eti.br
 * 
 * Exceção lançada pelos métodos das classes command
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
