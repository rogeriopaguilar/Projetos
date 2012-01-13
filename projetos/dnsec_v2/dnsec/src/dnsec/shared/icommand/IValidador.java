package dnsec.shared.icommand;
import dnsec.shared.icommand.exception.CommandException;

/**
 * Interface para fornecer um mecanismo padrão de validação  <br>
 * de dados.												 <br>
 * 															 <br>
 * @author Rogério de Paula Aguilar							
 * @version 0.1
 * */
public interface IValidador {

	/**
	 * Método que deve validar os dados passados como parâmetro
	 * 
	 * @param obj[] objeto contendo os parâmetros necessários para a validação
	 * @return Object[] objeto contendo os dados de retorno do método
	 * @throws CommandException caso ocorra algum erro de validação
	 * */
	public Object[] validarDados(Object[] obj) throws CommandException;

}
