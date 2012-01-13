/**
 * Realiza um teste para saber se a conexão com a base de dados
 * está ok.
 * */

package dnsec.modulos.testeacessodados.business;

import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.icommand.exception.CommandException;

public class CommandTestarConexao extends BaseDispatchCRUDCommand{

	public static final String METODO_TESTAR_CONEXAO = "testarConexao";
	
	/**
	 * Testa a conexão com a base de dados
	 * OBS: A implementação é feita na superclasse.
	 * O método precisa apenas certificar que a conexão e mapeamento
	 * do o hibernate estejam corretos 
	 * */
	public Object[] testarConexao(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];	
		return objRetorno;
	}

	public Object[] prepararInclusao(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] prepararEdicao(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] confirmarInclusao(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] confirmarEdicao(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] excluir(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] validarDados(Object[] obj) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] listar(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

}


