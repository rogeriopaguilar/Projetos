package dnsec.shared.icommand;
import dnsec.shared.icommand.exception.CommandException;

/**
 * Interface para fornecer um mecanismo padr�o de valida��o  <br>
 * de dados.												 <br>
 * 															 <br>
 * @author Rog�rio de Paula Aguilar							
 * @version 0.1
 * */
public interface IValidador {

	/**
	 * M�todo que deve validar os dados passados como par�metro
	 * 
	 * @param obj[] objeto contendo os par�metros necess�rios para a valida��o
	 * @return Object[] objeto contendo os dados de retorno do m�todo
	 * @throws CommandException caso ocorra algum erro de valida��o
	 * */
	public Object[] validarDados(Object[] obj) throws CommandException;

}
