/**
 * Command para o cadastro de fun��es
 * @author raguilar
 * */
package dnsec.modulos.cadastros.funcao.business;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import dnsec.modulos.cadastros.funcao.vo.FuncaoSearchVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.database.hibernate.Grupo;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.database.hibernate.pk.FuncaoPk;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.StringUtil;

public class CommandFuncoes extends BaseDispatchCRUDCommand {
	static Logger logger = Logger.getLogger(CommandFuncoes.class.getName());

	
    public static String METODO_SALVAR_GRUPOS_FUNCAO = "confirmarEdicaoGruposFuncao";
	
	public CommandFuncoes() {
		super();
	}

	public CommandFuncoes(Session session) {
		super(session);
	}

	/**
	 * Lista as fun��es cadastradas no sistema
	 * 
	 * @param args
	 * @param arg[0] -
	 *            objeto do tipo FuncaoSearchVo para o filtro da pesquisa
	 * @throws CommandException -
	 *             caso algum erro ocorra
	 */
	public Object[] listar(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[] { Collections.EMPTY_LIST };
		FuncaoSearchVo funcaoSearchVo = (FuncaoSearchVo) args[0];
		logger.debug("Inicializando listagem de fun��es --> Filtro "
				+ funcaoSearchVo);
		Criteria critFuncoes = getSession().createCriteria(Funcao.class);
		Criteria critFuncoesContador = getSession().createCriteria(Funcao.class);
		if (funcaoSearchVo != null && funcaoSearchVo.isRealizarPesquisa()) {
			// Filtro para a pesquisa
			if (funcaoSearchVo.getFuncao() != null) {
				// Filtra pelo c�digo da fun��o
				if (funcaoSearchVo.getFuncao().getCompId().getCodFuncaoFunc() != null) {
					// Filtra fazendo um like pelo c�digo do sistema
					critFuncoes.add(Expression.ilike("compId.codFuncaoFunc", funcaoSearchVo.getFuncao().getCompId().getCodFuncaoFunc()));
					critFuncoesContador.add(Expression.ilike("compId.codFuncaoFunc", funcaoSearchVo.getFuncao().getCompId().getCodFuncaoFunc()));
				}
				// Filtra pelo nome da fun��o
				if (!"".equals(StringUtil.NullToStr(funcaoSearchVo.getFuncao()
						.getNomeFunc()))) {
					// Filtra fazendo um like pela descri��o da fun��o
					critFuncoes.add(Expression.ilike("nomeFunc", "%"
							+ funcaoSearchVo.getFuncao().getNomeFunc() + "%"));
					critFuncoesContador.add(Expression.ilike("nomeFunc", "%"
							+ funcaoSearchVo.getFuncao().getNomeFunc() + "%"));
				}
				// Filtra pelo sistema
				if (funcaoSearchVo.getFuncao().getSistema() != null && !StringUtils.isEmpty(funcaoSearchVo.getFuncao().getSistema().getCodSistemaSist())) {
					critFuncoes.createCriteria("sistema").add(
							Expression.ilike("codSistemaSist", funcaoSearchVo
									.getFuncao().getSistema()
									.getCodSistemaSist()));
					critFuncoesContador.createCriteria("sistema").add(
							Expression.ilike("codSistemaSist", funcaoSearchVo
									.getFuncao().getSistema()
									.getCodSistemaSist()));
				}
			}
		}

		/*
		 * Por default o usu�rio pode visualizar apenas as fun��es relacionadas
		 * com os grupos aos quais o usu�rio possui acesso,
		 * a n�o ser que ele seja o administrador ou esteja relacionado
		 * com a fun��o que d� acesso a todas as fun��es
		 * */
		if(!Constantes.COD_USR_ADM.equals(GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua())){
			if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_ACESSO_TODAS_FUNCOES) == null){
				//Mostra apenas as fun��es associadas com os grupos aos quais o usu�rio possui acesso
				Usuario usuario = (Usuario) getSession().get(Usuario.class, GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua());
				if(usuario == null){
					//Usu�rio n�o existe mais.
					CommandException exception = new CommandException();
					exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
					//exception.setObjArgs(new Object[] { grupo } );
					exception.setObjArgs(new Object[0]);
					throw exception;
				}
				Iterator itGruposUsuario = usuario.getGrupos().iterator();
				List codigosGrupos = new LinkedList();
				while(itGruposUsuario.hasNext()){
					Grupo grupoAtual = (Grupo) itGruposUsuario.next();
					if(!Constantes.COD_GRUPO_SIST.equals(grupoAtual.getCodGrupoGrup())){ //Exclui o grupo principal do sistema
						codigosGrupos.add(grupoAtual.getCodGrupoGrup());
					}
				}
				critFuncoes.createCriteria("grupos").add(Expression.in("codGrupoGrup", codigosGrupos));	
				critFuncoesContador.createCriteria("grupos").add(Expression.in("codGrupoGrup", codigosGrupos));	
			}
			//Usu�rio ve todas as fun��es menos as relacionadas com o controle de seguran�a
			Iterator itFuncoes = getSession().createCriteria(Funcao.class).add(Expression.eq("compId.codSistemaSist", Constantes.COD_SIST_ADM) ).list().iterator();
			
			while(itFuncoes.hasNext()){
				Funcao funcao = (Funcao)itFuncoes.next();
				critFuncoes.add(Expression.ne("compId", funcao.getCompId()));	
				critFuncoesContador.add(Expression.ne("compId", funcao.getCompId()));	
			}
		}
		critFuncoes.addOrder(Order.asc("compId.codSistemaSist"));
		critFuncoes.addOrder(Order.asc("compId.codFuncaoFunc"));
		critFuncoesContador.addOrder(Order.asc("compId.codSistemaSist"));
		critFuncoesContador.addOrder(Order.asc("compId.codFuncaoFunc"));
		
		super.paginarDados(critFuncoes, critFuncoesContador);
		
		objRetorno = new Object[] { pagina };
		logger.debug("Listagem de fun��es realizada com sucesso");
		return objRetorno;
	}

	/**
	 * Retorna um vo contendo os dados para inclus�o
	 * 
	 * @return Object[] Object[0] -> objeto funcao para a tela de cadastro
	 */
	public Object[] prepararInclusao(Object[] args) throws CommandException {
		logger
				.info("Inicializando procedimento para prepara��o de inclus�o de fun��es");
		Object[] objRetorno = new Object[] { new Funcao() };
		logger
				.info("procedimento para prepara��o de inclus�o de fun��es realziado com suceso!");
		return objRetorno;
	}

	public Object[] prepararEdicao(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];
		Funcao funcao = (Funcao) args[0];
		boolean retornarGrupos = args.length > 1 && ((Boolean)args[1]).equals(true);
		boolean retornarGruposNaoRelacionados = args.length > 2 && ((Boolean)args[2]).equals(true);
		CommandException exception = null;
		logger
				.info("Inicializando procedimento para edi��o de uma fun��o... Fun��o --> "
						+ funcao);
		// Pesquisa para saber se a fun��o ainda existe na base de dados
		funcao = (Funcao) getSession().get(Funcao.class, funcao.getCompId());
		if (funcao == null) {
			// Fun��o n�o existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			// exception.setObjArgs(new Object[] { grupo } );
			exception.setObjArgs(new Object[0]);
			throw exception;
		}else{
			objRetorno = new Object[] { funcao };
			if(retornarGrupos)
			{
				Iterator itFuncoesAlterar = funcao.getGrupos().iterator();
				if(retornarGruposNaoRelacionados)
				{
					Criteria crit = getSession().createCriteria(Grupo.class);
					crit.addOrder(Order.asc("codGrupoGrup"));
					List listaGruposNaoRelacionados = crit.list();
					listaGruposNaoRelacionados.removeAll(funcao.getGrupos());
					objRetorno = new Object[]{funcao, listaGruposNaoRelacionados};
				}
			}
			
			
		}
		logger
				.info("Procedimento para edi��o de uma funcao realizado com sucesso");
		return objRetorno;
	}

	public Object[] confirmarInclusao(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];
		Funcao funcaoIncluir = (Funcao) args[0];
		CommandException exception = null;
		logger
				.info("Inicializando procedimento para confirma��o dos dados de inclus�o de uma fun��o... Fun��o --> "
						+ funcaoIncluir);
		// valida os dados para inclus�o
		this.validarDados(new Object[] { funcaoIncluir });
		// Pesquisa para saber se j� existe uma funcao com o c�digo requerido
		Funcao funcao = (Funcao) getSession().get(Funcao.class,
				funcaoIncluir.getCompId());
		if (funcao != null) {
			// Chave duplicada
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.funcoes.chave.duplicada");
			exception.setObjArgs(new Object[] { funcaoIncluir.getCompId() });
			throw exception;
		} else {
			//Fun��o com o mesmo nome
			Criteria crit = getSession().createCriteria(Funcao.class);
			crit.add(Expression.ilike("nomeFunc", "%" + funcaoIncluir.getNomeFunc().trim() + "%"));
			if(crit.list().size() > 0)
			{
				// nome duplicado
				exception = new CommandException();
				exception.setStrKeyConfigFile("key.erro.funcoes.nome.duplicado");
				exception.setObjArgs(new Object[] { funcaoIncluir.getCompId() });
				throw exception;
			}
			
			// OK - salva o grupo na base de dados
			FuncaoPk chavePrimaria = (FuncaoPk) getSession()
					.save(funcaoIncluir);
			// grupoIncluir.setCodGrupoGrup(chavePrimaria);
			objRetorno = new Object[] { funcaoIncluir };
		}
		logger
				.info("Procedimento para confirma��o dos dados de inclus�o de uma fun��o realizado com sucesso");
		return objRetorno;
	}

	public Object[] confirmarEdicao(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];
		Funcao funcaoEditar = (Funcao) args[0];
		CommandException exception = null;
		logger
				.info("Inicializando procedimento para confirma��o dos dados para edi��o de uma fun��o. Fun��o --> "
						+ funcaoEditar);
		// valida os dados para inclus�o
		this.validarDados(new Object[] { funcaoEditar });
		FuncaoPk pk = funcaoEditar.getCompId();
		funcaoEditar.setCompId(null);
		// Pesquisa para saber se a fun��o ainda existe na base de dados
		Funcao funcao = (Funcao) getSession().get(Funcao.class, pk);
		if (funcao != null) {
			if(
					(
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_INC_SISTEMA) ||      
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_SISTEMA) ||      
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_EXC_SISTEMA) ||     
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_INC_USUARIO) ||      
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_USUARIO) ||      
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_EXC_USUARIO) ||      
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_INC_GRUPO) ||       
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_GRUPO) ||        
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_EXC_GRUPO) ||        
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_INC_FUNCAO) ||      
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_FUNCAO) ||       
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_EXC_FUNCAO) ||       
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_REL_SISTEMA) ||  
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_REL_USUARIO) ||  
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_REL_GRUPO) ||    
						funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_REL_FUNCAO) ||   
						funcao.getNomeFunc().equals(Constantes.FUNC_ACESSO_TODOS_GRUPOS) || 
						funcao.getNomeFunc().equals(Constantes.FUNC_ACESSO_TODOS_USUARIOS) || 
						funcao.getNomeFunc().equals(Constantes.FUNC_ACESSO_TODAS_FUNCOES) || 
						funcao.getNomeFunc().equals(Constantes.FUNC_ACESSO_TODOS_SISTEMAS)
					) && Constantes.COD_SIST_ADM.equals(funcao.getCompId().getCodSistemaSist())
					  && !funcao.getNomeFunc().equals(funcaoEditar.getNomeFunc())
				)
			{
				// Fun��o pr�-definida pelo sistema de seguran�a, n�o � poss�vel alterar o nome.
				exception = new CommandException();
				exception.setStrKeyConfigFile("key.erro.tentanto.alterar.nome.funcao.principal");
				exception.setObjArgs(new Object[0]);
				throw exception;
			}	
			
			
			// OK - salva a fun��o na base de dados
			funcao.setNomeFunc(funcaoEditar.getNomeFunc());
			funcao.setDescrFuncaoFunc(funcaoEditar.getDescrFuncaoFunc());
			funcao.setCodFuncaoPaiFunc(funcaoEditar.getCodFuncaoPaiFunc());
			funcao.setCodSistemaPaiFunc(funcaoEditar.getCodSistemaPaiFunc());
			getSession().update(funcao);
			objRetorno = new Object[] { funcao };
		} else {
			// Fun��o n�o existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			// exception.setObjArgs(new Object[] { grupo } );
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		logger
				.info("Procedimento para confirma��o dos dados para edi��o do grupo realizado com sucesso");
		return objRetorno;
	}

	/**
	 * Exclui os dados da fun��o.
	 * 
	 * @param args[0] -
	 *            Objeto Fun��o a ser exclu�do
	 * @throws CommandException,
	 *             caso algum erro ocorra durante a exclus�o dos dados
	 */
	public Object[] excluir(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];
		Funcao funcaoExcluir = (Funcao) args[0];
		CommandException exception = null;
		logger
				.info("Inizializando procedimento para excluir uma fun��o... Fun��o --> "
						+ funcaoExcluir);
		// Pesquisa para saber se a fun��o ainda existe na base de dados
		funcaoExcluir = (Funcao) getSession().get(Funcao.class,
				funcaoExcluir.getCompId());
		if (funcaoExcluir != null) {
			// Fun��o ainda existe na base de dados.
			Integer qtdeGrupos = (Integer) getSession().createFilter(
					funcaoExcluir.getGrupos(), "select count(*)").list()
					.iterator().next();
			if (qtdeGrupos.intValue() > 0) {
				// Fun��o possui grupos associados
				if (exception == null) {
					exception = new CommandException();
				}
				exception
						.setStrKeyConfigFile("key.erro.funcoes.possui.grupos.associados");
				exception
						.setObjArgs(new Object[] { funcaoExcluir, qtdeGrupos });
				throw exception;
			}
			Integer qtdeFuncoesFilhas = (Integer) getSession().createFilter(
					funcaoExcluir.getFuncoesFilhas(), "select count(*)").list()
					.iterator().next();
			if (qtdeFuncoesFilhas.intValue() > 0) {
				// Fun��o possui fun��es filhas
				if (exception == null) {
					exception = new CommandException();
				}
				exception
						.setStrKeyConfigFile("key.erro.funcoes.possui.funcoes.filhas.associadas");
				exception.setObjArgs(new Object[] { funcaoExcluir,
						qtdeFuncoesFilhas });
				throw exception;
			}

			
			//Se for o usu�rio admin n�o permite a exclus�o
			if(funcaoExcluir.getCompId().getCodSistemaSist().equals(Constantes.COD_SIST_ADM)
				&& 
				(
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_INC_SISTEMA) ||      
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_SISTEMA) ||      
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_EXC_SISTEMA) ||     
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_INC_USUARIO) ||      
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_USUARIO) ||      
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_EXC_USUARIO) ||      
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_INC_GRUPO) ||       
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_GRUPO) ||        
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_EXC_GRUPO) ||        
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_INC_FUNCAO) ||      
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_FUNCAO) ||       
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_EXC_FUNCAO) ||       
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_REL_SISTEMA) ||  
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_REL_USUARIO) ||  
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_REL_GRUPO) ||    
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_REL_FUNCAO) ||   
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_ACESSO_TODOS_GRUPOS) || 
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_ACESSO_TODOS_USUARIOS) || 
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_ACESSO_TODAS_FUNCOES) || 
					funcaoExcluir.getNomeFunc().equals(Constantes.FUNC_ACESSO_TODOS_SISTEMAS)
				)
			){
				if(exception == null) {
					exception = new CommandException();
				}
				exception.setStrKeyConfigFile("key.erro.tentanto.exluir.funcao.principal");
				exception.setObjArgs(new Object[] { funcaoExcluir } );
				throw exception;
			}
			
			
			/*Pesquisa por registros de hist�rico de acesso (OBS: Esta consulta � 
			 *realizada diretamente pois atualmente o sistema n�o mapeia a tabela historico_acesso)
			PreparedStatement st = null;
			ResultSet rs = null;
			try{
				st = getSession().connection().prepareStatement(
						"select count(*) from HISTORICO_ACESSO where cod_sistema_sist = ?" +
						"and cod_funcao_func = ? "
				);
				st.setString(1, funcaoExcluir.getCompId().getCodSistemaSist());
				st.setLong(2, funcaoExcluir.getCompId().getCodFuncaoFunc());
				rs = st.executeQuery();
				rs.next();
				Integer qtdeHistoricoAcesso = new Integer(rs.getInt(1));
				if(exception == null) {
					exception = new CommandException();
				}
				if(qtdeHistoricoAcesso.intValue() > 0)
				{
					exception.setStrKeyConfigFile("key.erro.funcoes.possui.historico.acesso");
					exception.setObjArgs(new Object[] { funcaoExcluir, qtdeHistoricoAcesso } );
					throw exception;
				}
			}catch(SQLException e)
			{
				logger.error("Erro ao pesquisar quantidade de par�mtros para o sistema!",e);
				e.printStackTrace();
				throw new CommandException(e);
			}finally
			{
				if(rs != null)
				{
					try{rs.close();}catch(Exception e){logger.error(e);}
				}
				if(st != null)
				{ 
					try{st.close();}catch(Exception e){logger.error(e);}
				}
			}	*/
			//TODO --> Mapear a tabela historico_acesso para n�o realizar mais a verifica��o acima "na m�o"
			
			getSession().delete(funcaoExcluir);
		} else {
			// Fun��o n�o existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			// exception.setObjArgs(new Object[] { grupo } );
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		logger.debug("Fun��o exclu�da com sucesso");
		return objRetorno;
	}

	public Object[] validarDados(Object[] obj) throws CommandException {
		Funcao funcao = (Funcao) obj[0];
		Object[] objRetorno = new Object[0];
		CommandException exception = null;
		// Valida os dados para inclus�o/altera��o de dados
		if (BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO.equals(this
				.getStrMetodo())
				|| BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO.equals(this
						.getStrMetodo())

		) {
			if (funcao.getCompId().getCodFuncaoFunc() == null) {
				// Fun��o n�o foi selecionada
				if (exception == null) {
					exception = new CommandException();
				}
				exception
						.setStrKeyConfigFile("key.erro.funcoes.codigo.faltando");
				exception.setObjArgs(new Object[] { funcao });
				throw exception;
			} else {
				// Sistema n�o selecionado para a fun��o
				if (funcao.getSistema() == null || StringUtils.isEmpty(funcao.getSistema().getCodSistemaSist() ) ) {
					if (exception == null) {
						exception = new CommandException();
					}
					exception
							.setStrKeyConfigFile("key.erro.funcoes.sistema.faltando");
					exception.setObjArgs(new Object[] { funcao });
					throw exception;
				}
			}
		}
		return objRetorno;
	}

	public Object[] confirmarEdicaoGruposFuncao(Object[] args) throws CommandException {
		logger.debug("Editando os grupos da fun��o!");
		Object[] objRetorno = new Object[0];
		Funcao funcaoAlterar = (Funcao) args[0];
		Funcao funcaoTMP = null;
		List listaGruposAlterar = (List)args[1];
		FuncaoPk pk = funcaoAlterar.getCompId();
		funcaoAlterar.setCompId(null);
		funcaoTMP = (Funcao) getSession().get(Funcao.class, pk);
		CommandException exception = null;
		if( funcaoTMP != null)
		{
			funcaoTMP.getGrupos().clear();
			funcaoTMP.getGrupos().addAll(listaGruposAlterar);
			getSession().update(funcaoTMP);
		}else
		{
			//Usu�rio n�o existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		logger.debug("Edi��o de Grupos Realizada com sucesso!");
		return objRetorno;
	}

	
}
