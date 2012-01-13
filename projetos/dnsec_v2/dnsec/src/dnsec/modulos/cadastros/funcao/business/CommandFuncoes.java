/**
 * Command para o cadastro de funções
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
	 * Lista as funções cadastradas no sistema
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
		logger.debug("Inicializando listagem de funções --> Filtro "
				+ funcaoSearchVo);
		Criteria critFuncoes = getSession().createCriteria(Funcao.class);
		Criteria critFuncoesContador = getSession().createCriteria(Funcao.class);
		if (funcaoSearchVo != null && funcaoSearchVo.isRealizarPesquisa()) {
			// Filtro para a pesquisa
			if (funcaoSearchVo.getFuncao() != null) {
				// Filtra pelo código da função
				if (funcaoSearchVo.getFuncao().getCompId().getCodFuncaoFunc() != null) {
					// Filtra fazendo um like pelo código do sistema
					critFuncoes.add(Expression.ilike("compId.codFuncaoFunc", funcaoSearchVo.getFuncao().getCompId().getCodFuncaoFunc()));
					critFuncoesContador.add(Expression.ilike("compId.codFuncaoFunc", funcaoSearchVo.getFuncao().getCompId().getCodFuncaoFunc()));
				}
				// Filtra pelo nome da função
				if (!"".equals(StringUtil.NullToStr(funcaoSearchVo.getFuncao()
						.getNomeFunc()))) {
					// Filtra fazendo um like pela descrição da função
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
		 * Por default o usuário pode visualizar apenas as funções relacionadas
		 * com os grupos aos quais o usuário possui acesso,
		 * a não ser que ele seja o administrador ou esteja relacionado
		 * com a função que dá acesso a todas as funções
		 * */
		if(!Constantes.COD_USR_ADM.equals(GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua())){
			if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_ACESSO_TODAS_FUNCOES) == null){
				//Mostra apenas as funções associadas com os grupos aos quais o usuário possui acesso
				Usuario usuario = (Usuario) getSession().get(Usuario.class, GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua());
				if(usuario == null){
					//Usuário não existe mais.
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
			//Usuário ve todas as funções menos as relacionadas com o controle de segurança
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
		logger.debug("Listagem de funções realizada com sucesso");
		return objRetorno;
	}

	/**
	 * Retorna um vo contendo os dados para inclusão
	 * 
	 * @return Object[] Object[0] -> objeto funcao para a tela de cadastro
	 */
	public Object[] prepararInclusao(Object[] args) throws CommandException {
		logger
				.info("Inicializando procedimento para preparação de inclusão de funções");
		Object[] objRetorno = new Object[] { new Funcao() };
		logger
				.info("procedimento para preparação de inclusão de funções realziado com suceso!");
		return objRetorno;
	}

	public Object[] prepararEdicao(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];
		Funcao funcao = (Funcao) args[0];
		boolean retornarGrupos = args.length > 1 && ((Boolean)args[1]).equals(true);
		boolean retornarGruposNaoRelacionados = args.length > 2 && ((Boolean)args[2]).equals(true);
		CommandException exception = null;
		logger
				.info("Inicializando procedimento para edição de uma função... Função --> "
						+ funcao);
		// Pesquisa para saber se a função ainda existe na base de dados
		funcao = (Funcao) getSession().get(Funcao.class, funcao.getCompId());
		if (funcao == null) {
			// Função não existe mais.
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
				.info("Procedimento para edição de uma funcao realizado com sucesso");
		return objRetorno;
	}

	public Object[] confirmarInclusao(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];
		Funcao funcaoIncluir = (Funcao) args[0];
		CommandException exception = null;
		logger
				.info("Inicializando procedimento para confirmação dos dados de inclusão de uma função... Função --> "
						+ funcaoIncluir);
		// valida os dados para inclusão
		this.validarDados(new Object[] { funcaoIncluir });
		// Pesquisa para saber se já existe uma funcao com o código requerido
		Funcao funcao = (Funcao) getSession().get(Funcao.class,
				funcaoIncluir.getCompId());
		if (funcao != null) {
			// Chave duplicada
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.funcoes.chave.duplicada");
			exception.setObjArgs(new Object[] { funcaoIncluir.getCompId() });
			throw exception;
		} else {
			//Função com o mesmo nome
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
				.info("Procedimento para confirmação dos dados de inclusão de uma função realizado com sucesso");
		return objRetorno;
	}

	public Object[] confirmarEdicao(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];
		Funcao funcaoEditar = (Funcao) args[0];
		CommandException exception = null;
		logger
				.info("Inicializando procedimento para confirmação dos dados para edição de uma função. Função --> "
						+ funcaoEditar);
		// valida os dados para inclusão
		this.validarDados(new Object[] { funcaoEditar });
		FuncaoPk pk = funcaoEditar.getCompId();
		funcaoEditar.setCompId(null);
		// Pesquisa para saber se a função ainda existe na base de dados
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
				// Função pré-definida pelo sistema de segurança, não é possível alterar o nome.
				exception = new CommandException();
				exception.setStrKeyConfigFile("key.erro.tentanto.alterar.nome.funcao.principal");
				exception.setObjArgs(new Object[0]);
				throw exception;
			}	
			
			
			// OK - salva a função na base de dados
			funcao.setNomeFunc(funcaoEditar.getNomeFunc());
			funcao.setDescrFuncaoFunc(funcaoEditar.getDescrFuncaoFunc());
			funcao.setCodFuncaoPaiFunc(funcaoEditar.getCodFuncaoPaiFunc());
			funcao.setCodSistemaPaiFunc(funcaoEditar.getCodSistemaPaiFunc());
			getSession().update(funcao);
			objRetorno = new Object[] { funcao };
		} else {
			// Função não existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			// exception.setObjArgs(new Object[] { grupo } );
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		logger
				.info("Procedimento para confirmação dos dados para edição do grupo realizado com sucesso");
		return objRetorno;
	}

	/**
	 * Exclui os dados da função.
	 * 
	 * @param args[0] -
	 *            Objeto Função a ser excluído
	 * @throws CommandException,
	 *             caso algum erro ocorra durante a exclusão dos dados
	 */
	public Object[] excluir(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];
		Funcao funcaoExcluir = (Funcao) args[0];
		CommandException exception = null;
		logger
				.info("Inizializando procedimento para excluir uma função... Função --> "
						+ funcaoExcluir);
		// Pesquisa para saber se a função ainda existe na base de dados
		funcaoExcluir = (Funcao) getSession().get(Funcao.class,
				funcaoExcluir.getCompId());
		if (funcaoExcluir != null) {
			// Função ainda existe na base de dados.
			Integer qtdeGrupos = (Integer) getSession().createFilter(
					funcaoExcluir.getGrupos(), "select count(*)").list()
					.iterator().next();
			if (qtdeGrupos.intValue() > 0) {
				// Função possui grupos associados
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
				// Função possui funções filhas
				if (exception == null) {
					exception = new CommandException();
				}
				exception
						.setStrKeyConfigFile("key.erro.funcoes.possui.funcoes.filhas.associadas");
				exception.setObjArgs(new Object[] { funcaoExcluir,
						qtdeFuncoesFilhas });
				throw exception;
			}

			
			//Se for o usuário admin não permite a exclusão
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
			
			
			/*Pesquisa por registros de histórico de acesso (OBS: Esta consulta é 
			 *realizada diretamente pois atualmente o sistema não mapeia a tabela historico_acesso)
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
				logger.error("Erro ao pesquisar quantidade de parâmtros para o sistema!",e);
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
			//TODO --> Mapear a tabela historico_acesso para não realizar mais a verificação acima "na mão"
			
			getSession().delete(funcaoExcluir);
		} else {
			// Função não existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			// exception.setObjArgs(new Object[] { grupo } );
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		logger.debug("Função excluída com sucesso");
		return objRetorno;
	}

	public Object[] validarDados(Object[] obj) throws CommandException {
		Funcao funcao = (Funcao) obj[0];
		Object[] objRetorno = new Object[0];
		CommandException exception = null;
		// Valida os dados para inclusão/alteração de dados
		if (BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO.equals(this
				.getStrMetodo())
				|| BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO.equals(this
						.getStrMetodo())

		) {
			if (funcao.getCompId().getCodFuncaoFunc() == null) {
				// Função não foi selecionada
				if (exception == null) {
					exception = new CommandException();
				}
				exception
						.setStrKeyConfigFile("key.erro.funcoes.codigo.faltando");
				exception.setObjArgs(new Object[] { funcao });
				throw exception;
			} else {
				// Sistema não selecionado para a função
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
		logger.debug("Editando os grupos da função!");
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
			//Usuário não existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		logger.debug("Edição de Grupos Realizada com sucesso!");
		return objRetorno;
	}

	
}
