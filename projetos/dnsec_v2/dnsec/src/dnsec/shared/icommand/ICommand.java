package dnsec.shared.icommand;
import dnsec.shared.icommand.exception.CommandException;


/**
 * Classe básica para a implementação do design pattern Command. 		<br>	
 *@author Rogério de Paula Aguilar										<br>
 *@version 0.1
 * */

public interface ICommand extends java.io.Serializable{
	/**
	 * Método que deve ser implementado para executar o comando
	 * @param args --> argumentos para a execução
	 * @return Object[] --> array contendo os dados da execução (caso existam)
	 * */
	public Object[] executar(Object[] args) throws CommandException;
	/**
	 * Método que deve ser implementado desfazer a execução realizada pelo método
	 * executar
	 * @param args --> argumentos para a execução
	 * @return Object[] --> array contendo os dados da (caso existam)
	 * */
	public Object[] desfazer(Object[] args) throws CommandException;
}
