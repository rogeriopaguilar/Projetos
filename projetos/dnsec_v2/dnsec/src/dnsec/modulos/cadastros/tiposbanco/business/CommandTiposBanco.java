/**
 * Command para o cadastro de funções
 * @author raguilar
 * */
package dnsec.modulos.cadastros.tiposbanco.business;

import java.util.Collections;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.database.hibernate.TiposBanco;
import dnsec.shared.icommand.exception.CommandException;

public class CommandTiposBanco extends BaseDispatchCRUDCommand {
	static Logger logger = Logger.getLogger(CommandTiposBanco.class.getName());

	
	
	public CommandTiposBanco() {
		super();
	}

	public CommandTiposBanco(Session session) {
		super(session);
	}

	/**
	 * Lista os tipos de banco de dados cadastrados no sistema
	 * @param args
	 * @param arg[0] -
	 * @throws CommandException -
	 *             caso algum erro ocorra
	 */
	public Object[] listar(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[] { Collections.EMPTY_LIST };
		logger.debug("Inicializando listagem de tipos de banco");
		Criteria critTiposBanco = getSession().createCriteria(TiposBanco.class);
		critTiposBanco.addOrder(Order.asc("codTipoBanco"));
		objRetorno = new Object[]{critTiposBanco.list()};
		return objRetorno;
	}

	@Override
	public Object[] confirmarEdicao(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] confirmarInclusao(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] excluir(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] prepararEdicao(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] prepararInclusao(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] validarDados(Object[] obj) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}	

	
}
