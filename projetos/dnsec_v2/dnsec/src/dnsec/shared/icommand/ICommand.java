package dnsec.shared.icommand;
import dnsec.shared.icommand.exception.CommandException;


/**
 * Classe b�sica para a implementa��o do design pattern Command. 		<br>	
 *@author Rog�rio de Paula Aguilar										<br>
 *@version 0.1
 * */

public interface ICommand extends java.io.Serializable{
	/**
	 * M�todo que deve ser implementado para executar o comando
	 * @param args --> argumentos para a execu��o
	 * @return Object[] --> array contendo os dados da execu��o (caso existam)
	 * */
	public Object[] executar(Object[] args) throws CommandException;
	/**
	 * M�todo que deve ser implementado desfazer a execu��o realizada pelo m�todo
	 * executar
	 * @param args --> argumentos para a execu��o
	 * @return Object[] --> array contendo os dados da (caso existam)
	 * */
	public Object[] desfazer(Object[] args) throws CommandException;
}
