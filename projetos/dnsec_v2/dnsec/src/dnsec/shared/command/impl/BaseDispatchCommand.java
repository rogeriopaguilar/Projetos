/**
 * Implementa a interface ICommand e em execute chama
 * o método configurado no campo strMetodo, caso este método exista.
 * @author raguilar
 * */

package dnsec.shared.command.impl;

import java.lang.reflect.InvocationTargetException;

import dnsec.shared.icommand.ICommand;
import dnsec.shared.icommand.exception.CommandException;

public class BaseDispatchCommand implements ICommand{

	private String strMetodo = "";
	private String metodoAnterior = "";
	
	
	public String getMetodoAnterior() {
		return metodoAnterior;
	}

	public void setMetodoAnterior(String metodoAnterior) {
		this.metodoAnterior = metodoAnterior;
	}

	public String getStrMetodo() {
		return strMetodo;
	}

	public void setStrMetodo(String strMetodo) {
		this.strMetodo = strMetodo;
	}

	/**
	 * Sobrescreve o método definido em ICommand para executar o método configurado 
	 * no atributo strMetodo
	 * */
	public Object[] executar(Object[] args) throws CommandException {
		Object objReturn = null;
		try{
			objReturn = this.getClass().getDeclaredMethod(this.getStrMetodo(), new Class[]{ Object[].class} ).invoke(this, new Object[] { args });
		}catch(NoSuchMethodException e){
			//TODO --> Colocar log para indicar que o método não foi encontrado
			e.printStackTrace();
			throw new CommandException("Método configurado em strMetodo não encontrado na classe!", e);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CommandException("Erro ao executar método configurado em strMetodo!", e);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CommandException("Erro ao executar método configurado em strMetodo!", e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CommandException("Erro ao executar método configurado em strMetodo!", e);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e.getTargetException() != null && (e.getTargetException() instanceof CommandException)){
				throw (CommandException )e.getTargetException();
			}else{
				throw new CommandException("Erro ao executar método configurado em strMetodo!", e);
			}
		}
		return (Object[])objReturn;
	}

	/**
	 * Tenta encontrar um método que deve ter a assinatura
	 * desfazer + strMetodo e chama este método na classe.
	 * */
	public Object[] desfazer(Object[] args) throws CommandException {
		Object objReturn = null;
		try{
			objReturn = this.getClass().getDeclaredMethod("desfazer" + this.getStrMetodo(), new Class[]{ Object[].class} ).invoke(this, args);
		}catch(NoSuchMethodException e){
			//TODO --> Colocar log para indicar que o método não foi encontrado
			e.printStackTrace();
			throw new CommandException("Método configurado em strMetodo não encontrado na classe!", e);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CommandException("Erro ao executar método configurado em strMetodo!", e);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CommandException("Erro ao executar método configurado em strMetodo!", e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CommandException("Erro ao executar método configurado em strMetodo!", e);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e.getTargetException() != null && (e.getTargetException() instanceof CommandException)){
				throw (CommandException )e.getTargetException();
			}else{
				throw new CommandException("Erro ao executar método configurado em strMetodo!", e);
			}
		}
		return (Object[])objReturn;
	}

	

}
