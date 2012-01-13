
package dnsec.modulos.cadastros.sistema.business;

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
import org.hibernate.criterion.Restrictions;

import dnsec.modulos.cadastros.sistema.vo.SistemaSearchVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Grupo;
import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.database.hibernate.TiposBanco;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.StringUtil;

public class CommandSistemas extends BaseDispatchCRUDCommand{
    static Logger logger = Logger.getLogger(CommandSistemas.class.getName());

    public static String METODO_SALVAR_GRUPOS_SISTEMA = "confirmarEdicaoGruposSistema";
    
	public CommandSistemas() {
		super();
	}

	public CommandSistemas(Session session) {
		super(session);
	}

	
	/**
	 * Lista os sistemas cadastrados 
	 * @param args 
	 * @param arg[0] - objeto do tipo SistemaSearchVo para o filtro da pesquisa
	 * @throws CommandException - caso algum erro ocorra
	 * */
	public Object[] listar(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[]{Collections.EMPTY_LIST};	
		SistemaSearchVo sistemaSearchVo = (SistemaSearchVo) args[0]; 
		logger.debug("Inicializando listagem de sistemas - Filtro --> " + sistemaSearchVo);
		List codigosGruposRetornar = new LinkedList();
		Criteria critSistemas = getSession().createCriteria(Sistema.class);
		Criteria critSistemasContador = getSession().createCriteria(Sistema.class);
		if(sistemaSearchVo != null && sistemaSearchVo.isRealizarPesquisa()){
			//Filtro para a pesquisa
			if(sistemaSearchVo.getSistema() != null){
				if(!"".equals(StringUtil.NullToStr(sistemaSearchVo.getSistema().getCodSistemaSist()))){
					//Filtra fazendo um like pelo código do sistema
					critSistemas.add(Expression.ilike("codSistemaSist", "%" + sistemaSearchVo.getSistema().getCodSistemaSist() + "%"));	
					critSistemasContador.add(Expression.ilike("codSistemaSist", "%" + sistemaSearchVo.getSistema().getCodSistemaSist() + "%"));	
				}
				
				if(!"".equals(StringUtil.NullToStr(sistemaSearchVo.getSistema().getDescrSistemaSist()))){
					//Filtra fazendo um like pela descrição do sistema
					critSistemas.add(Expression.ilike("descrSistemaSist", "%" + sistemaSearchVo.getSistema().getDescrSistemaSist() + "%"));	
					critSistemasContador.add(Expression.ilike("descrSistemaSist", "%" + sistemaSearchVo.getSistema().getDescrSistemaSist() + "%"));	
				}
			}
			
			/*
			 * Por default o usuário pode visualizar apenas os sistemas relacionados
			 * com os grupos que ele possui acesso,
			 * a não ser que ele seja o administrador ou esteja relacionado
			 * com a função que dá acesso a todos os sistemas
			 * */
			if(!Constantes.COD_USR_ADM.equals(GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua())){
				if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_ACESSO_TODOS_SISTEMAS) == null){
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
					while(itGruposUsuario.hasNext()){
						Grupo grupoAtual = (Grupo) itGruposUsuario.next();
						codigosGruposRetornar.add(grupoAtual.getCodGrupoGrup());
					}
					
					critSistemas.createCriteria("grupos").add(Restrictions.in("codGrupoGrup", codigosGruposRetornar));
					critSistemasContador.createCriteria("grupos").add(Restrictions.in("codGrupoGrup", codigosGruposRetornar));
				}
				//Usuário ve todos os sistemas menos o adm
				critSistemas.add(Expression.ne("codSistemaSist", Constantes.COD_SIST_ADM));
				critSistemasContador.add(Expression.ne("codSistemaSist", Constantes.COD_SIST_ADM));
			}

			critSistemas.addOrder(Order.asc("codSistemaSist"));			
			critSistemasContador.addOrder(Order.asc("codSistemaSist"));			
			super.paginarDados(critSistemas, critSistemasContador);
		}
		
		
		
		objRetorno = new Object[]{	pagina };	
		logger.debug("Listagem de sistemas realizada com sucesso");
		return objRetorno;
	}

	public Object[] prepararInclusao(Object[] args) throws CommandException {
		logger.debug("Preparando a tela para inclusão de dados...");
		Object objRetorno[] = new Object[]{
				new Sistema()
		};
		logger.debug("Operação realizada com sucesso!");
		return objRetorno;
	}

	public Object[] prepararEdicao(Object[] args) throws CommandException {
		logger.debug("Preparando a tela para edição de dados...");
		Object[] objRetorno = new Object[0];
		Sistema sistemaAlterar = (Sistema) args[0];
		boolean retornarGrupos = args.length > 1 && ((Boolean)args[1]).equals(true);
		boolean retornarGruposNaoRelacionados = args.length > 2 && ((Boolean)args[2]).equals(true);
		sistemaAlterar = (Sistema) getSession().get(Sistema.class, sistemaAlterar.getCodSistemaSist());
		objRetorno = new Object[]{sistemaAlterar};
		CommandException exception = null;
		if(sistemaAlterar != null)
		{
			if(retornarGrupos)
			{
				Iterator itSistemaAlterar = sistemaAlterar.getGrupos().iterator();
				if(itSistemaAlterar.hasNext()) itSistemaAlterar.next();
				if(retornarGruposNaoRelacionados)
				{
					Criteria crit = getSession().createCriteria(Grupo.class);
					crit.addOrder(Order.asc("codGrupoGrup"));
					List listaGruposNaoRelacionados = crit.list();
					listaGruposNaoRelacionados.removeAll(sistemaAlterar.getGrupos());
					objRetorno = new Object[]{sistemaAlterar, listaGruposNaoRelacionados};
				}
				
			}
			
		}else
		{
			//Sistema não existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		logger.debug("Operação realizada com sucesso!");
		return objRetorno;
	}

	
	
	public Object[] confirmarInclusao(Object[] args) throws CommandException {
		logger.debug("Confirmando a inclusão de dados!");
		Sistema sistema = (Sistema) args[0];
		this.validarDados(new Object[]{sistema});
		sistema.setCodSistemaSist(sistema.getCodSistemaSist().toUpperCase());
		sistema.setDescrSistemaSist(sistema.getDescrSistemaSist().toUpperCase());
		sistema.setTipoBancoSist(sistema.getTipoBancoSist().toUpperCase());
		Criteria crit = getSession().createCriteria(Sistema.class);
		crit.add(Restrictions.eq("codSistemaSist", sistema.getCodSistemaSist()));
		if(crit.list().size() > 0)
		{
			CommandException exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.sistemas.chave.duplicada");
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		getSession().save(sistema);
		logger.debug("Operação realizada com sucesso!");
		return null;
	}

	public Object[] confirmarEdicao(Object[] args) throws CommandException {
		logger.debug("Confirmando a edição de dados!");
		Object[] objRetorno = new Object[0];
		Sistema sistemaAlterar = (Sistema) args[0];
		Sistema sistemaTMP = null;
		String codSistemaSist = sistemaAlterar.getCodSistemaSist();
		sistemaAlterar.setCodSistemaSist(null);
		sistemaTMP = (Sistema) getSession().get(Sistema.class, codSistemaSist);
		CommandException exception = null;
		if( sistemaTMP != null)
		{
			sistemaTMP.setCondCadastrarSist(sistemaAlterar.getCondCadastrarSist());
			sistemaTMP.setDescrSistemaSist(sistemaAlterar.getDescrSistemaSist().toUpperCase());
			sistemaTMP.setNomeAnalistaRespSist(sistemaAlterar.getNomeAnalistaRespSist());
			sistemaTMP.setTipoBancoSist(sistemaAlterar.getTipoBancoSist().toUpperCase());
			sistemaTMP.setNomeBancoSist(sistemaAlterar.getNomeBancoSist());
			sistemaTMP.setNomeProprietarioSist(sistemaAlterar.getNomeProprietarioSist());
			sistemaTMP.setNomeServidorSist(sistemaAlterar.getNomeServidorSist());
			objRetorno = new Object[]{sistemaTMP};
			this.validarDados(new Object[]{sistemaTMP});
			getSession().update(sistemaTMP);
		}else
		{
			//Sistema não existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		logger.debug("Operação realizada com sucesso!");
		return objRetorno;
	}

	public Object[] excluir(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];
		Sistema sistemaExcluir = (Sistema) args[0];
		logger.debug("Alterando dados do sistema --> " + sistemaExcluir);
		sistemaExcluir = (Sistema) getSession().get(Sistema.class, sistemaExcluir.getCodSistemaSist());
		CommandException exception = null;
		if(sistemaExcluir != null)
		{
			Integer qtdeFuncoes = (Integer) getSession().createFilter(sistemaExcluir.getFuncoes(), "select count(*)").list().iterator().next();
			if(qtdeFuncoes.intValue() > 0){
				logger.debug("Não é possível excluir o sistema pois o mesmo possui funções associadas!");
				//Sistema possui funções associadas
				if(exception == null) {
					exception = new CommandException();
				}
				exception.setStrKeyConfigFile("key.erro.sistema.possui.funcoes.associadas");
				exception.setObjArgs(new Object[] { sistemaExcluir, qtdeFuncoes } );
				throw exception;
			}
			Integer qtdeGrupos = (Integer) getSession().createFilter(sistemaExcluir.getGrupos(), "select count(*)").list().iterator().next();
			if(qtdeGrupos.intValue() > 0){
				logger.debug("Não é possível excluir o sistema pois o mesmo possui grupos associados!");
				//Sistema possui grupos associados
				if(exception == null) {
					exception = new CommandException();
				}
				exception.setStrKeyConfigFile("key.erro.sistema.possui.grupos.associados");
				exception.setObjArgs(new Object[] { sistemaExcluir, qtdeGrupos } );
				throw exception;
			}

			//Se for o usuário admin não permite a exclusão
			if(sistemaExcluir.getCodSistemaSist().equalsIgnoreCase(Constantes.COD_SIST_ADM)){
				if(exception == null) {
					exception = new CommandException();
				}
				exception.setStrKeyConfigFile("key.erro.tentanto.exluir.sistema.principal");
				exception.setObjArgs(new Object[] { sistemaExcluir } );
				throw exception;
			}

			
			/*Pesquisa os parâmetros do sistema (OBS: Esta consulta é 
			 *realizada diretamente pois atualmente o sistema não mapeia a tabela parâmetro-sistema)
			PreparedStatement st = null;
			ResultSet rs = null;
			try{
				st = getSession().connection().prepareStatement(
						"select count(*) from PARAMETRO_SISTEMA where cod_sistema_sist = ?"
				);
				st.setString(1, sistemaExcluir.getCodSistemaSist());
				rs = st.executeQuery();
				rs.next();
				Integer qtdeParametros = new Integer(rs.getInt(1));
				if(exception == null) {
					exception = new CommandException();
				}
				if(qtdeParametros.intValue() > 0)
				{
					exception.setStrKeyConfigFile("key.erro.sistema.possui.parametros.associados");
					exception.setObjArgs(new Object[] { sistemaExcluir, qtdeParametros } );
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
			}	
			
			/*Pesquisa os as tabelas do sistema (OBS: Esta consulta é 
			 *realizada diretamente pois atualmente o sistema não mapeia a tabela sistema_tabela)
			st = null;
			rs = null;
			try{
				st = getSession().connection().prepareStatement(
						"select count(*) from SISTEMA_TABELA where cod_sistema_sist = ?"
				);
				st.setString(1, sistemaExcluir.getCodSistemaSist());
				rs = st.executeQuery();
				rs.next();
				Integer qtdeParametros = new Integer(rs.getInt(1));
				if(exception == null) {
					exception = new CommandException();
				}
				if(qtdeParametros.intValue() > 0)
				{
					exception.setStrKeyConfigFile("key.erro.sistema.possui.tabelas.associadas");
					exception.setObjArgs(new Object[] { sistemaExcluir, qtdeParametros } );
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
			//TODO --> Mapear a tabela sistema_tabela para não realizar mais a verificação acima "na mão"
			getSession().delete(sistemaExcluir);
		}else
		{
			//Sistema não existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			//exception.setObjArgs(new Object[] { grupo } );
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		return objRetorno;
	}

	public Object[] validarDados(Object[] obj) throws CommandException {
		   Sistema sistema = (Sistema) obj[0];
		   Object[] objRetorno = new Object[0];
		   CommandException exception = new CommandException();
		   //Valida os dados para inclusão/alteração de dados
		   if(
			   BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO.equals(this.getStrMetodo())
			   									||
			   BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO.equals(this.getStrMetodo())

		   )
		   {
			   if(StringUtils.isEmpty(sistema.getCodSistemaSist())){
				   //Código do sistema não foi informado
					exception.setStrKeyConfigFile("key.erro.sistemas.codigo.faltando");
					exception.setObjArgs(new Object[] { sistema } );
					throw exception;
			   }else if(StringUtils.isEmpty(sistema.getDescrSistemaSist())){
					//Descrição não informada
					exception.setStrKeyConfigFile("key.erro.sistemas.descricao.faltando");
					exception.setObjArgs(new Object[] { sistema } );
					throw exception;
			   }else if(StringUtils.isEmpty(sistema.getTipoBancoSist()))
				   //Tipo de banco não informado
			   {
					exception.setStrKeyConfigFile("key.erro.sistemas.tipo.banco.faltando");
					exception.setObjArgs(new Object[] { sistema } );
					throw exception;
			   }else if(
					   getSession().createCriteria(TiposBanco.class).add(Expression.eq("codTipoBanco", sistema.getTipoBancoSist())).list().size() == 0
			   ){
					//exception.setStrKeyConfigFile("key.erro.sistemas.tipo.banco.invalido");
					//exception.setObjArgs(new Object[] { sistema } );
					//throw exception;
				   TiposBanco tipoBanco = new TiposBanco();
				   tipoBanco.setCodTipoBanco(sistema.getTipoBancoSist());
				   getSession().save(tipoBanco);
			   }
		   }
		   return objRetorno;
		   
	}


	public Object[] confirmarEdicaoGruposSistema(Object[] args) throws CommandException {
		logger.debug("Editando os grupos do sistema!");
		Object[] objRetorno = new Object[0];
		Sistema sistemaAlterar = (Sistema) args[0];
		Sistema sistemaTMP = null;
		List listaGruposAlterar = (List)args[1];
		String codSistemaSist = sistemaAlterar.getCodSistemaSist();
		sistemaAlterar.setCodSistemaSist(null);
		sistemaTMP = (Sistema) getSession().get(Sistema.class, codSistemaSist);
		CommandException exception = null;
		if( sistemaTMP != null)
		{
			sistemaTMP.getGrupos().clear();
			sistemaTMP.getGrupos().addAll(listaGruposAlterar);
			getSession().update(sistemaTMP);
		}else
		{
			//Sistema não existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		logger.debug("Edição de Grupos Realizada com sucesso!");
		return objRetorno;
	}

	
	
}


