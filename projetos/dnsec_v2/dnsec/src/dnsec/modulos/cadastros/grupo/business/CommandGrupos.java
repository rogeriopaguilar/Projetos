
package dnsec.modulos.cadastros.grupo.business;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import dnsec.modulos.cadastros.grupo.vo.GrupoSearchVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.database.hibernate.Grupo;
import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.StringUtil;

public class CommandGrupos extends BaseDispatchCRUDCommand{
    static Logger logger = Logger.getLogger(CommandGrupos.class.getName());

	/**
	 * M�todos espec�ficos do Command para os grupos
	 * */
	public static final String METODO_ADICIONAR_FUNCAO = "adicionarFuncao";
	public static final String METODO_REMOVER_FUNCAO = "removerFuncao";
	//public static final String METODO_CARREGAR_FUNCOES_GRUPO = "carregarFuncoesGrupo";
	public static String METODO_SALVAR_FUNCOES_GRUPO = "confirmarEdicaoFuncoesGrupo";
	public static String METODO_SALVAR_SISTEMAS_GRUPO = "confirmarEdicaoSistemasGrupo";
	public static String METODO_SALVAR_USUARIOS_GRUPO = "confirmarEdicaoUsuariosGrupo";

	public CommandGrupos() {
		super();
	}

	public CommandGrupos(Session session) {
		super(session);
	}

	
	/**
	 * Remove uma fun��o associada com o grupo
	 * @param args 
	 * @param arg[0] - objeto do tipo Grupo
	 * @param arg[1] - objeto do tipo Funcao
	 * @TODO param arg[2] - objeto de pagina��o. Se for null a pagina��o n�o ser� feita.
	 * @throws CommandException - caso algum erro ocorra
	 * @return object[] - object[0] contendo o grupo com os dados do banco - object[1] - contendo o objeto funcao passado (n�o � carregado com os dados do banco)
	 */
	public Object[] removerFuncao(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];	
		Grupo grupo = (Grupo) args[0];
		Funcao funcao = (Funcao) args[1];
		CommandException exception = null;
		logger.debug("Inicializando remo��o de fun��o relacionada ao grupo... Grupo --> " + grupo + " Fun��o --> " + funcao);
		//Pesquisa para saber se o grupo ainda existe na base de dados
		grupo = (Grupo) getSession().get(Grupo.class, grupo.getCodGrupoGrup());
		if(grupo != null){
				if(grupo.getFuncoes().contains(funcao)){
					grupo.getFuncoes().remove(funcao);
				}
		}else{
			//Grupo n�o existe
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			exception.setObjArgs(new Object[] { grupo } );
			throw exception;
		}
		getSession().update(grupo);
		objRetorno = new Object[]{grupo, funcao};
		logger.debug("Remo��o de fun��o relacionada ao grupo realizada com sucesso...");
		return objRetorno;
	}	


	
	/**
	 * Relaciona uma fun��o com o grupo
	 * @param args 
	 * @param arg[0] - objeto do tipo Grupo
	 * @param arg[1] - objeto do tipo Funcao
	 * @throws CommandException - caso algum erro ocorra
	 * @return object[] - object[0] contendo o grupo com os dados do banco - object[1] - contendo o objeto funcao com os dados do banco
	 */
	public Object[] adicionarFuncao(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];	
		Grupo grupo = (Grupo) args[0];
		Funcao funcao = (Funcao) args[1];
		CommandException exception = null;
		logger.debug("Inicializando adi��o de fun��o relacionada ao grupo... Grupo --> " + grupo + " Fun��o --> " + funcao);
		//Pesquisa para saber se o grupo ainda existe na base de dados
		grupo = (Grupo) getSession().get(Grupo.class, grupo.getCodGrupoGrup());
		funcao = (Funcao) getSession().get(Funcao.class, funcao.getCompId());
		if(grupo != null){
			if(funcao != null){
				if(!grupo.getFuncoes().contains(funcao)){
					grupo.getFuncoes().add(funcao);
				}
			}else{
				//Fun��o n�o existe - atualmente n�o faz nada
			}
		}else{
			//Grupo n�o existe - atualmente n�o faz nada
		}
		getSession().update(grupo);
		objRetorno = new Object[]{grupo, funcao};
		logger.debug("Adi��o de fun��o relacionada ao grupo realziada com sucesso... ");
		return objRetorno;
	}	
	
	/**
	 * Lista os grupos cadastrados no sistema
	 * @param args 
	 * @param arg[0] - objeto do tipo GrupoSearchVo para o filtro da pesquisa
	 * @throws CommandException - caso algum erro ocorra
	 * */
	public Object[] listar(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[]{Collections.EMPTY_LIST};	
		GrupoSearchVo grupoSearchVo = (GrupoSearchVo) args[0]; 
		logger.debug("Inicializando listagem de grupos... Filtro --> " + grupoSearchVo);
		Criteria critGrupos = getSession().createCriteria(Grupo.class);
		Criteria critGruposContador = getSession().createCriteria(Grupo.class);
		if(grupoSearchVo != null && grupoSearchVo.isRealizarPesquisa()){
			//Filtro para a pesquisa
			if(grupoSearchVo.getGrupo() != null){
				if(!"".equals(StringUtil.NullToStr(grupoSearchVo.getGrupo().getDescrGrupoGrup()))){
					//Filtra fazendo um like pelo nome do grupo
					critGrupos.add(Expression.ilike("descrGrupoGrup", "%" + grupoSearchVo.getGrupo().getDescrGrupoGrup() + "%"));	
					critGruposContador.add(Expression.ilike("descrGrupoGrup", "%" + grupoSearchVo.getGrupo().getDescrGrupoGrup() + "%"));	
				}
				
				if(!"".equals(StringUtil.NullToStr(grupoSearchVo.getGrupo().getCodGrupoGrup()))){
					//Filtra fazendo um like pelo c�digo do grupo
					critGrupos.add(Expression.ilike("codGrupoGrup", "%" + grupoSearchVo.getGrupo().getCodGrupoGrup() + "%"));	
					critGruposContador.add(Expression.ilike("codGrupoGrup", "%" + grupoSearchVo.getGrupo().getCodGrupoGrup() + "%"));	
				}
			}
			
			/*
			 * Por default o usu�rio pode visualizar apenas os grupos aos quais ele est� relacionado
			 * a n�o ser que ele seja o administrador ou esteja relacionado
			 * com a fun��o que d� acesso a todos os grupos
			 */
			if(!Constantes.COD_USR_ADM.equals(GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua()))
			{
				if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_ACESSO_TODOS_GRUPOS) == null){
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
						codigosGrupos.add(grupoAtual.getCodGrupoGrup());
					}
					critGrupos.add(Expression.in("codGrupoGrup", codigosGrupos));	
					critGruposContador.add(Expression.in("codGrupoGrup", codigosGrupos));	
				}
				//Exclui o grupo admin
				critGrupos.add(Expression.ne("codGrupoGrup", Constantes.COD_GRUPO_SIST));	
				critGruposContador.add(Expression.ne("codGrupoGrup", Constantes.COD_GRUPO_SIST));	
			 }
			
			
			critGrupos.addOrder(Order.asc("codGrupoGrup"));
			critGruposContador.addOrder(Order.asc("codGrupoGrup"));
			super.paginarDados(critGrupos, critGruposContador);
		}
		objRetorno = new Object[]{	pagina };	
		logger.debug("Listagem de grupos realizada com sucesso");
		return objRetorno;
	}

	/**
	 * Retorna os dados necess�rios para preparar a tela para inclus�o de dados
	 * */
	public Object[] prepararInclusao(Object[] args) throws CommandException {
		logger.debug("Inicializando procedimento para prepara��o de inclus�o de grupos");
		logger.debug("procedimento para prepara��o de inclus�o de grupos realziado com suceso!");
		return new Object[] { new Grupo() };
	}

	/**
	 * Retorna os dados necess�rios para preparar a tela para edi��o de dados
	 * */
	public Object[] prepararEdicao(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];	
		Grupo grupo = (Grupo) args[0];
		Boolean carregarFuncoesGrupo = args.length >= 2 ? (Boolean)args[1] : false;
		Boolean carregarFuncoesDisponiveis = args.length >= 3 ? (Boolean)args[2] : false;
		Boolean carregarSistemasGrupo = args.length >= 4 ? (Boolean)args[3] : false;
		Boolean carregarSistemasDisponiveis = args.length >= 5 ? (Boolean)args[4] : false;
		Boolean carregarUsuariosGrupo = args.length >= 6 ? (Boolean)args[5] : false;
		Boolean carregarUsuariosDisponiveis = args.length >= 7 ? (Boolean)args[6] : false;
		Sistema sistemaFiltroCarregarFuncoesDisponiveis = args.length >= 8 ? (Sistema)args[7] : null;

		CommandException exception = null;
		logger.debug("Inicializando procedimento para edi��o de grupos... Grupo --> " + grupo);
		//Pesquisa para saber se o grupo ainda existe na base de dados
		grupo = (Grupo) getSession().get(Grupo.class, grupo.getCodGrupoGrup());
		objRetorno = new Object[]{grupo};
		List<Funcao> listaFuncoesDisponiveis = null;
		List<Sistema> listaSistemasDisponiveis = null;
		List<Usuario> listaUsuariosDisponiveis = null;
		if(grupo == null){
			//Grupo n�o existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			//exception.setObjArgs(new Object[] { grupo } );
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		if(carregarFuncoesGrupo){
			if(grupo.getFuncoes() == null || grupo.getFuncoes().size() ==0) grupo.setFuncoes(Collections.EMPTY_SET);
			if(carregarFuncoesDisponiveis)
			{
				listaFuncoesDisponiveis = new LinkedList<Funcao>();
				Criteria crit = getSession().createCriteria(Funcao.class);
				crit.addOrder(Order.asc("nomeFunc"));
				if(sistemaFiltroCarregarFuncoesDisponiveis != null)
				{
					//Filtra pelo sistema
					crit.createCriteria("sistema").add(Expression.eq("codSistemaSist", sistemaFiltroCarregarFuncoesDisponiveis.getCodSistemaSist()));
				}
				listaFuncoesDisponiveis = new LinkedList(crit.list());
				listaFuncoesDisponiveis.removeAll(grupo.getFuncoes());
				//objRetorno = new Object[]{grupo, listaFuncoesDisponiveis};
			}
		}
		if(carregarSistemasGrupo){
			if(grupo.getSistemas() == null || grupo.getSistemas().size() ==0) grupo.setSistemas(Collections.EMPTY_SET);
			if(carregarSistemasDisponiveis)
			{
				Criteria crit = getSession().createCriteria(Sistema.class);
				crit.addOrder(Order.asc("codSistemaSist"));
				listaSistemasDisponiveis = new LinkedList(crit.list());
				listaSistemasDisponiveis.removeAll(grupo.getSistemas());
				//objRetorno = new Object[]{grupo, listaFuncoesDisponiveis};
			}
		}
		if(carregarUsuariosGrupo){
			if(grupo.getUsuarios() == null || grupo.getUsuarios().size() ==0) grupo.setUsuarios(Collections.EMPTY_SET);
			if(carregarUsuariosDisponiveis)
			{
				Criteria crit = getSession().createCriteria(Usuario.class);
				crit.addOrder(Order.asc("codUsuarioUsua"));
				listaUsuariosDisponiveis = new LinkedList(crit.list());
				listaUsuariosDisponiveis.removeAll(grupo.getUsuarios());
				//objRetorno = new Object[]{grupo, listaFuncoesDisponiveis};
			}
		}
		objRetorno = new Object[]{grupo, listaFuncoesDisponiveis, listaSistemasDisponiveis, listaUsuariosDisponiveis};
		logger.debug("Procedimento para edi��o de grupos realizado com sucesso");
		return objRetorno;
	}

	/**
	 * Confirma a inclus�o dos dados do grupo.
	 * @param args - args[0] objeto do tipo Grupo que ser� inclu�do na base
	 * @throws CommandException - caso ocorra algum erro
	 * @return Object - Object[0] - grupo passado como par�metro
	 * */
	public Object[] confirmarInclusao(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];	
		Grupo grupoIncluir = (Grupo) args[0];
		CommandException exception = null;
		logger.debug("Inicializando procedimento para confirma��o dos dados de inclus�o de grupos.. Grupo --> " + grupoIncluir);
		//valida os dados para inclus�o 
		this.validarDados(new Object[]{ grupoIncluir });
		//Pesquisa para saber se j� existe um grupo com o c�digo requerido
		Grupo grupo = (Grupo) getSession().get(Grupo.class, grupoIncluir.getCodGrupoGrup());
		if(grupo != null){
			//Chave duplicada
			exception = new CommandException();
			exception.setStrKeyConfigFile(Constantes.KEY_ERRO_GRUPOS_CHAVE_PRIMARIA_DUPLICADA);
			exception.setObjArgs(new Object[] { grupoIncluir.getCodGrupoGrup() } );
			throw exception;
		}else{
			//OK - salva o grupo na base de dados	
			String chavePrimaria = (String) getSession().save(grupoIncluir);
			grupoIncluir.setCodGrupoGrup(chavePrimaria);
			objRetorno = new Object[] { grupoIncluir }; 
		}
		logger.debug("Procedimento para confirma��o dos dados de inclus�o de grupos realizado com sucesso");
		return objRetorno;
	}

	/**
	 * Confirma a edi��o dos dados do grupo.
	 * OBS: 
	 * 1 - N�o testa o c�digo para saber se j� existe outro grupo com o mesmo
	 * c�digo, pois na tela o campo de chavae prim�ria deve ficar desabilitado
	 * durante a edi��o.
	 * @param args - args[0] objeto do tipo Grupo que ser� editado na base
	 * @throws CommandException - caso ocorra algum erro
	 * @return Object - Object[0] - grupo passado como par�metro
	 * */
	public Object[] confirmarEdicao(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];	
		Grupo grupoEditar= (Grupo) args[0];
		List listaFuncoesGrupoEditadas = args.length > 1 ? (List)args[1] : Collections.EMPTY_LIST;
		Boolean editarFuncoes = args.length > 2 ? (Boolean)args[2] : false;
		CommandException exception = null;
		logger.debug("Inicializando procedimento para confirma��o dos dados para edi��o do grupo... Grupo --> " + grupoEditar);
		//valida os dados para inclus�o 
		this.validarDados(new Object[]{ grupoEditar });
		String codigoGrupo = grupoEditar.getCodGrupoGrup();
		grupoEditar.setCodGrupoGrup("");
		//Pesquisa para saber se o grupo ainda existe na base de dados
		Grupo grupo = (Grupo) getSession().get(Grupo.class, codigoGrupo);
		if(grupo != null){
			//OK - salva o grupo na base de dados	
			grupo.setDescrGrupoGrup(StringUtil.NullToStrUpperTrim(grupoEditar.getDescrGrupoGrup()));
			if(editarFuncoes){
				grupo.getFuncoes().clear();
				grupo.getFuncoes().addAll(listaFuncoesGrupoEditadas);
			}
			getSession().update(grupo);
			objRetorno = new Object[] { grupoEditar}; 
		}else{
			//Grupo n�o existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			//exception.setObjArgs(new Object[] { grupo } );
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		logger.debug("Procedimento para confirma��o dos dados para edi��o do grupo realizado com sucesso");
		return objRetorno;
	}

	/**
	 * Exclui os dados do grupo.
	 * @param args[0] - Objeto Grupo a ser exclu�do
	 * @throws CommandException, caso algum erro ocorra durante a exclus�o dos dados
	 * */
	public Object[] excluir(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];	
		Grupo grupoExcluir = (Grupo) args[0];
		CommandException exception = null;
		logger.debug("Inizializando procedimento para excluir um grupo... Grupo --> " + grupoExcluir);
		//Pesquisa para saber se o grupo ainda existe na base de dados
		grupoExcluir = (Grupo) getSession().get(Grupo.class, grupoExcluir.getCodGrupoGrup());
		if(grupoExcluir != null){
			//Grupo existe.
			Integer qtdeFuncoes = (Integer) getSession().createFilter(grupoExcluir.getFuncoes(), "select count(*)").list().iterator().next();
			if(qtdeFuncoes.intValue() > 0){
				//Grupo possui fun��es associadas
				if(exception == null) {
					exception = new CommandException();
				}
				exception.setStrKeyConfigFile(Constantes.KEY_ERRO_GRUPOS_POSSUI_FUNCOES_ASSOCIADAS);
				exception.setObjArgs(new Object[] { grupoExcluir, qtdeFuncoes } );
				throw exception;
			}else{
				Integer qtdeSistemas= (Integer) getSession().createFilter(grupoExcluir.getSistemas(), "select count(*)").list().iterator().next();
				if(qtdeSistemas.intValue() > 0){
					//Grupo possui sistemas associados
					if(exception == null) {
						exception = new CommandException();
					}
					exception.setStrKeyConfigFile(Constantes.KEY_ERRO_GRUPOS_POSSUI_SISTEMAS_ASSOCIADOS);
					exception.setObjArgs(new Object[] { grupoExcluir, qtdeSistemas } );
					throw exception;
				}else{	
					Integer qtdeUsuarios = (Integer) getSession().createFilter(grupoExcluir.getUsuarios(), "select count(*)").list().iterator().next();
					if(qtdeUsuarios.intValue() > 0){
						//Grupo possui usu�rios associados
						if(exception == null) {
							exception = new CommandException();
						}
						exception.setStrKeyConfigFile(Constantes.KEY_ERRO_GRUPOS_POSSUI_USUARIO_ASSOCIADOS);
						exception.setObjArgs(new Object[] { grupoExcluir, qtdeUsuarios } );
						throw exception;
					}
				}
			}	
			//Se for o grupo de acesso ao sistema n�o permite a exclus�o
			if(grupoExcluir.getCodGrupoGrup().equalsIgnoreCase(Constantes.COD_GRUPO_SIST)){
				if(exception == null) {
					exception = new CommandException();
				}
				exception.setStrKeyConfigFile("key.erro.tentanto.exluir.grupo.principal");
				exception.setObjArgs(new Object[] { grupoExcluir } );
				throw exception;
			}
			getSession().delete(grupoExcluir);	
		}else{
			//Grupo n�o existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			//exception.setObjArgs(new Object[] { grupo } );
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		logger.debug("Grupo exclu�do com sucesso");
		return objRetorno;
	}

	/**
	 * Valida os dados do grupo para inclus�o/edi��o e lan�a exce��es caso ocorram
	 * erros de valida��o
	 * @param obj[] - obj[0] Objeto grupo que deve ser validado
	 * @throws CommandException 
	 * @throws CommandException - exception contendo o erro de valida��o
	 * */
	public Object[] validarDados(Object[] obj) throws CommandException {
		   Grupo grupo = (Grupo) obj[0];
		   Object[] objRetorno = new Object[0];
		   CommandException exception = null;
		   //Valida os dados para inclus�o/altera��o de dados
		   if(
			   BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO.equals(this.getStrMetodo())
			   									||
			   BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO.equals(this.getStrMetodo())

		   )
		   {
			   grupo.setCodGrupoGrup(StringUtil.NullToStrUpperTrim(grupo.getCodGrupoGrup()));
			   grupo.setDescrGrupoGrup(StringUtil.NullToStrUpperTrim(grupo.getDescrGrupoGrup()));
			   List listaErros = new LinkedList();
			   if("".equals(grupo.getCodGrupoGrup())){
				   //listaErros.add(InternacUtil.getInstance().getResource("key.erro.grupos.codigo.faltando"));	
					if(exception == null) {
						exception = new CommandException();
					}
					exception.setStrKeyConfigFile("key.erro.grupos.codigo.faltando");
					exception.setObjArgs(new Object[] { grupo } );
					throw exception;
			   }else{
				   if("".equals(grupo.getDescrGrupoGrup())){
					   //listaErros.add(InternacUtil.getInstance().getResource("key.erro.grupos.descricao.faltando"));	
						if(exception == null) {
							exception = new CommandException();
						}
						exception.setStrKeyConfigFile("key.erro.grupos.descricao.faltando");
						exception.setObjArgs(new Object[] { grupo } );
						throw exception;
				   }
			   }
			   objRetorno = new Object[listaErros.size()];
			   listaErros.toArray(objRetorno);
		   }
		   return objRetorno;
	}

	public Object[] confirmarEdicaoFuncoesGrupo(Object[] args) throws CommandException {
		logger.debug("Editando as fun��es do grupo!");
		Object[] objRetorno = new Object[0];
		Grupo grupoAlterar = (Grupo) args[0];
		Grupo grupoTMP = null;
		List listaFuncoesAlterar = (List)args[1];
		String codGrupoGrup = grupoAlterar.getCodGrupoGrup();
		grupoAlterar.setCodGrupoGrup(null);
		grupoTMP = (Grupo) getSession().get(Grupo.class, codGrupoGrup);
		CommandException exception = null;
		if( grupoTMP != null)
		{
			grupoTMP.getFuncoes().clear();
			grupoTMP.getFuncoes().addAll(listaFuncoesAlterar);
			getSession().update(grupoTMP);
		}else
		{
			//Sistema n�o existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		logger.debug("Edi��o das fun��es do grupo realizada com sucesso!");
		return objRetorno;
	}

	public Object[] confirmarEdicaoSistemasGrupo(Object[] args) throws CommandException {
		logger.debug("Editando os sistemas do grupo!");
		Object[] objRetorno = new Object[0];
		Grupo grupoAlterar = (Grupo) args[0];
		Grupo grupoTMP = null;
		List listaFuncoesAlterar = (List)args[1];
		String codGrupoGrup = grupoAlterar.getCodGrupoGrup();
		grupoAlterar.setCodGrupoGrup(null);
		grupoTMP = (Grupo) getSession().get(Grupo.class, codGrupoGrup);
		CommandException exception = null;
		if( grupoTMP != null)
		{
			grupoTMP.getSistemas().clear();
			grupoTMP.getSistemas().addAll(listaFuncoesAlterar);
			getSession().update(grupoTMP);
		}else
		{
			//Sistema n�o existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		logger.debug("Edi��o dos sistemas do grupo realizada com sucesso!");
		return objRetorno;
	}

	public Object[] confirmarEdicaoUsuariosGrupo(Object[] args) throws CommandException {
		logger.debug("Editando os usu�rios do grupo!");
		Object[] objRetorno = new Object[0];
		Grupo grupoAlterar = (Grupo) args[0];
		Grupo grupoTMP = null;
		List listaFuncoesAlterar = (List)args[1];
		String codGrupoGrup = grupoAlterar.getCodGrupoGrup();
		grupoAlterar.setCodGrupoGrup(null);
		grupoTMP = (Grupo) getSession().get(Grupo.class, codGrupoGrup);
		CommandException exception = null;
		if( grupoTMP != null)
		{
			grupoTMP.getUsuarios().clear();
			grupoTMP.getUsuarios().addAll(listaFuncoesAlterar);
			getSession().update(grupoTMP);
		}else
		{
			//Sistema n�o existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		logger.debug("Edi��o dos usu�rios do grupo realizada com sucesso!");
		return objRetorno;
	}
	
}


