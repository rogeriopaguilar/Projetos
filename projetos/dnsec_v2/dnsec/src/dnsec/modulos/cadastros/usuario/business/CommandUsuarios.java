/**
 * Command para o cadastro de usuários
 * @author raguilar
 * */
package dnsec.modulos.cadastros.usuario.business;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import dnsec.modulos.cadastros.usuario.vo.UsuarioSearchVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Grupo;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.StringUtil;

public class CommandUsuarios extends BaseDispatchCRUDCommand{
    static Logger logger = Logger.getLogger(CommandUsuarios.class.getName());

    private CriptografiaSenha criptografiaSenha;
    public static String METODO_SALVAR_GRUPOS_USUARIO = "confirmarEdicaoGruposUsuario";

	public CommandUsuarios(CriptografiaSenha criptografiaSenha) {
		super();
		this.criptografiaSenha = criptografiaSenha;
	}

	public CommandUsuarios(Session session, CriptografiaSenha criptografiaSenha) {
		super(session);
		this.criptografiaSenha = criptografiaSenha;
	}

	public CommandUsuarios(Session session) {
		super(session);
	}
	
	/**
	 * Lista as funções cadastradas no sistema 
	 * @param args 
	 * @param arg[0] - objeto do tipo UsuarioSearchVo para o filtro da pesquisa
	 * @throws CommandException - caso algum erro ocorra
	 * */
	public Object[] listar(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[]{Collections.EMPTY_LIST};	
		UsuarioSearchVo usuarioSearchVo = (UsuarioSearchVo) args[0]; 
		logger.debug("Inicializando listagem de usuários --> Filtro " + usuarioSearchVo);
		Criteria critUsuarios = getSession().createCriteria(Usuario.class);
		Criteria critUsuariosContador = getSession().createCriteria(Usuario.class);
		if(usuarioSearchVo != null && usuarioSearchVo.isRealizarPesquisa()){
			//Filtro para a pesquisa
			if(usuarioSearchVo.getUsuario() != null){
				//Filtra pelo código do usuário
				if(usuarioSearchVo.getUsuario().getCodUsuarioUsua() != null && !"".equals(usuarioSearchVo.getUsuario().getCodUsuarioUsua())){
					critUsuarios.add(Expression.ilike("codUsuarioUsua", "%" + usuarioSearchVo.getUsuario().getCodUsuarioUsua() + "%"));	
					critUsuariosContador.add(Expression.ilike("codUsuarioUsua", "%" + usuarioSearchVo.getUsuario().getCodUsuarioUsua() + "%"));	
				}
				//Filtra pelo nome da do usuário
				if(usuarioSearchVo.getUsuario().getNomeUsuarioUsua() != null && !"".equals(StringUtil.NullToStr(usuarioSearchVo.getUsuario().getNomeUsuarioUsua()))){
					critUsuarios.add(Expression.ilike("nomeUsuarioUsua", "%" + usuarioSearchVo.getUsuario().getNomeUsuarioUsua() + "%"));	
					critUsuariosContador.add(Expression.ilike("nomeUsuarioUsua", "%" + usuarioSearchVo.getUsuario().getNomeUsuarioUsua() + "%"));	
				}
			}
		}	

		/*
		 * Por default o usuário pode visualizar apenas os seus dados,
		 * a não ser que ele seja o administrador ou esteja relacionado
		 * com a função que dá acesso a todos os usuários
		 * */
		if(!Constantes.COD_USR_ADM.equals(GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua())){
			if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_ACESSO_TODOS_USUARIOS) == null){
				critUsuarios.add(Expression.eq("codUsuarioUsua", GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua() ));	
				critUsuariosContador.add(Expression.eq("codUsuarioUsua", GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua() ));	
			}else{
				//Usuário comum não ve o usuário admin
				critUsuarios.add(Expression.ne("codUsuarioUsua", Constantes.COD_USR_ADM));	
				critUsuariosContador.add(Expression.ne("codUsuarioUsua", Constantes.COD_USR_ADM));	
			}
		}

		
		
		critUsuarios.addOrder(Order.asc("codUsuarioUsua"));
		critUsuariosContador.addOrder(Order.asc("codUsuarioUsua"));
		super.paginarDados(critUsuarios, critUsuariosContador);
		objRetorno = new Object[]{	pagina };	
		logger.debug("Listagem de usuários realizada com sucesso");
		return objRetorno;
	}

	/**
	 * Retorna um vo contendo os dados para inclusão
	 * @return Object[] Object[0] -> objeto funcao para a tela de cadastro
	 * */
	public Object[] prepararInclusao(Object[] args) throws CommandException {
		logger.debug("Inicializando procedimento para preparação de inclusão de usuário");
		Usuario usuario = new Usuario();
		long tempo = System.currentTimeMillis();
		usuario.setDataInclusaoUsua(new Timestamp(tempo));
		usuario.setDataSolicNovaSenha(new Timestamp(tempo));
		usuario.setQtdeErrosLoginUsua(5L);
		usuario.setNumDiasValidadesenhaUsua(50L);
		Object[] objRetorno = new Object[]{ usuario };
		logger.debug("procedimento para preparação de inclusão de usuario realizado com suceso!");
		return objRetorno;
	}

	public Object[] prepararEdicao(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];	
		Usuario usuario = (Usuario) args[0];
		boolean retornarGrupos = args.length > 1 && ((Boolean)args[1]).equals(true);
		boolean retornarGruposNaoRelacionados = args.length > 2 && ((Boolean)args[2]).equals(true);
		boolean retornarFuncoes = args.length > 2 && ((Boolean)args[2]).equals(true);

		CommandException exception = null;
		logger.debug("Inicializando procedimento para edição de um usuário... Usuário --> " + usuario);
		//Pesquisa para saber se o usuário ainda existe na base de dados
		usuario = (Usuario) getSession().get(Usuario.class, usuario.getCodUsuarioUsua());
		if(usuario == null){
			//Usuário não existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			//exception.setObjArgs(new Object[] { grupo } );
			exception.setObjArgs(new Object[0]);
			throw exception;
		}else{
			objRetorno = new Object[]{ usuario };
			if(retornarGrupos)
			{
				Iterator itUsuariosAlterar = usuario.getGrupos().iterator();
				if(retornarGruposNaoRelacionados)
				{
					Criteria crit = getSession().createCriteria(Grupo.class);
					crit.addOrder(Order.asc("codGrupoGrup"));
					List listaGruposNaoRelacionados = crit.list();
					listaGruposNaoRelacionados.removeAll(usuario.getGrupos());
					objRetorno = new Object[]{usuario, listaGruposNaoRelacionados};
				}
			}
		}
		if(criptografiaSenha != null && criptografiaSenha.suportaDescriptografia()){
			usuario.setCodSenhaUsua(criptografiaSenha.descriptografar(usuario.getCodUsuarioAdabasUsua(), usuario.getCodSenhaUsua()));
		}
		logger.debug("Procedimento para edição de um usuário realizado com sucesso");
		return objRetorno;
	}

	
	public Object[] confirmarInclusao(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];	
		Usuario usuarioIncluir = (Usuario) args[0];
		CommandException exception = null;
		logger.debug("Inicializando procedimento para confirmação dos dados de inclusão de um usuário... Usuário --> " + usuarioIncluir);
		//valida os dados para inclusão 
		this.validarDados(new Object[]{ usuarioIncluir });
		//Pesquisa para saber se já existe um usuário com o código requerido
		Usuario usuario = (Usuario) getSession().get(Usuario.class, usuarioIncluir.getCodUsuarioUsua());
		if(usuario != null){
			//Chave duplicada
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.usuarios.chave.duplicada");
			exception.setObjArgs(new Object[] { usuarioIncluir.getCodUsuarioUsua() } );
			throw exception;
		}else{
			//OK - salva o usuário na base de dados	
			if(criptografiaSenha != null 
					&& 
				!"".equals(usuarioIncluir.getCodSenhaUsua()))
			{
				usuarioIncluir.setCodSenhaUsua(criptografiaSenha.criptografar(usuarioIncluir.getCodUsuarioUsua(), usuarioIncluir.getCodSenhaUsua()));
			}
			usuarioIncluir.setDataInclusaoUsua(new Timestamp(new Date().getTime()));
			String chavePrimaria = (String) getSession().save(usuarioIncluir);
			//grupoIncluir.setCodGrupoGrup(chavePrimaria);
			objRetorno = new Object[] { usuarioIncluir }; 
		}
		logger.debug("Procedimento para confirmação dos dados de inclusão de um usuário realizado com sucesso");
		return objRetorno;
	}

	public Object[] confirmarEdicao(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];	
		Usuario usuarioEditar= (Usuario) args[0];
		CommandException exception = null;
		logger.debug("Inicializando procedimento para confirmação dos dados para edição de um usuário. Usuário --> " + usuarioEditar);
		//valida os dados para inclusão 
		this.validarDados(new Object[]{ usuarioEditar });
		String pk = usuarioEditar.getCodUsuarioUsua();
		//usuarioEditar.setCodUsuarioUsua(null);
		//Pesquisa para saber se o usuário ainda existe na base de dados
		Usuario usuario = (Usuario) getSession().get(Usuario.class, pk);
		if(usuario != null){
			//OK - salva o usuário na base de dados	
			String senhaBanco = usuario.getCodSenhaUsua();
			//getBeanUtils().copyProperties(usuario, usuarioEditar);
			/*usuario.setCodUsuarioUsua(
					usuarioEditar.getCodEditarUsua()
			);*/
			usuario.setCodSenhaUsua(usuarioEditar.getCodSenhaUsua());
			usuario.setNumChapaFunc(usuarioEditar.getNumChapaFunc());
			usuario.setNomeUsuarioUsua(usuarioEditar.getNomeUsuarioUsua());
			usuario.setDataInclusaoUsua(usuarioEditar.getDataInclusaoUsua());
			usuario.setDataUltAcessoUsua(usuarioEditar.getDataUltAcessoUsua());
			usuario.setDataUltAltSenhaUsua(usuarioEditar.getDataUltAltSenhaUsua());
			usuario.setDataSolicNovaSenha(usuarioEditar.getDataSolicNovaSenha());
			usuario.setNumDiasValidadesenhaUsua(usuarioEditar.getNumDiasValidadesenhaUsua());
			usuario.setQtdeErrosLoginUsua(usuarioEditar.getQtdeErrosLoginUsua());
			usuario.setCondAltSenhaUsua(usuarioEditar.getCondAltSenhaUsua());
			usuario.setNumTelefoneUsua(usuarioEditar.getNumTelefoneUsua());
			usuario.setCodEmailUsua(usuarioEditar.getCodEmailUsua());		
			usuario.setCondBloqueadoUsua(usuarioEditar.getCondBloqueadoUsua());
			usuario.setCodUsuarioAdabasUsua(usuarioEditar.getCodUsuarioAdabasUsua());
			usuario.setCodUsuarioUsua(pk);
			if(criptografiaSenha != null){
				if(criptografiaSenha instanceof CriptografiaSenhaDinapImpl || criptografiaSenha instanceof CriptografiaSenhaMySQLImpl){
					if(!"".equals(usuarioEditar.getCodSenhaUsua())){
						//Neste caso não é possível descriptografar, então eu criptografo novamente
						//somente se o usuário mudou a senha
						usuario.setCodSenhaUsua(criptografiaSenha.criptografar(pk, usuario.getCodSenhaUsua()));
						usuario.setDataSolicNovaSenha(new Timestamp((new Date()).getTime()));
					}else{
						usuario.setCodSenhaUsua(senhaBanco);
					}
				}else{
					usuario.setCodSenhaUsua(criptografiaSenha.criptografar(pk, usuario.getCodSenhaUsua()));
				}
			}
			getSession().update(usuario);
			objRetorno = new Object[] { usuario}; 
		}else{
			//Usuário não existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		logger.debug("Procedimento para confirmação dos dados para edição do usuário realizado com sucesso");
		return objRetorno;
	}

	/**
	 * Exclui os dados do usuário.
	 * @param args[0] - Objeto Usuário a ser excluído
	 * @throws CommandException, caso algum erro ocorra durante a exclusão dos dados
	 * */
	public Object[] excluir(Object[] args) throws CommandException {
		Object[] objRetorno = new Object[0];	
		Usuario usuarioExcluir = (Usuario) args[0];
		CommandException exception = null;
		logger.debug("Inizializando procedimento para excluir um usuário... Usuário --> " + usuarioExcluir);
		//Pesquisa para saber se o usuário ainda existe na base de dados
		usuarioExcluir = (Usuario) getSession().get(Usuario.class, usuarioExcluir.getCodUsuarioUsua());
		if(usuarioExcluir != null){
			//Usuário ainda existe na base de dados.
			Integer qtdeGrupos = (Integer) getSession().createFilter(usuarioExcluir.getGrupos(), "select count(*)").list().iterator().next();
			if(qtdeGrupos.intValue() > 0){
				//Usuário possui grupos associados
				if(exception == null) {
					exception = new CommandException();
				}
				exception.setStrKeyConfigFile("key.erro.usuarios.possui.grupos.associados");
				exception.setObjArgs(new Object[] { usuarioExcluir, qtdeGrupos } );
				throw exception;
			}

			//Se for o usuário admin não permite a exclusão
			if(usuarioExcluir.getCodUsuarioUsua().equalsIgnoreCase(Constantes.COD_USR_ADM)){
				if(exception == null) {
					exception = new CommandException();
				}
				exception.setStrKeyConfigFile("key.erro.tentanto.exluir.usuario.principal");
				exception.setObjArgs(new Object[] { usuarioExcluir } );
				throw exception;
			}

			
			/*Pesquisa por registros de histórico de acesso (OBS: Esta consulta é 
			 *realizada diretamente pois atualmente o sistema não mapeia a tabela historico_acesso)
			 *
			PreparedStatement st = null;
			ResultSet rs = null;
			try{
				st = getSession().connection().prepareStatement(
						"select count(*) from HISTORICO_ACESSO where cod_usuario_usua = ?" 
				);
				st.setString(1, usuarioExcluir.getCodUsuarioUsua());
				rs = st.executeQuery();
				rs.next();
				Integer qtdeHistoricoAcesso = new Integer(rs.getInt(1));
				if(exception == null) {
					exception = new CommandException();
				}
				if(qtdeHistoricoAcesso.intValue() > 0)
				{
					exception.setStrKeyConfigFile("key.erro.funcoes.possui.historico.acesso");
					exception.setObjArgs(new Object[] { usuarioExcluir, qtdeHistoricoAcesso } );
					throw exception;
				}
			}catch(SQLException e)
			{
				logger.error("Erro ao pesquisar quantidade de registros de histórico de acesso para o usuário!",e);
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

			//Pesquisando na tabela USUARIO_PESQUISA_EIS para verificar se o usuário possui relacionamentos
			try{
				st = getSession().connection().prepareStatement(
						"select count(*) from USUARIO_PESQUISA_EIS where cod_usuario_usua = ?" 
				);
				st.setString(1, usuarioExcluir.getCodUsuarioUsua());
				rs = st.executeQuery();
				rs.next();
				Integer qtdePesquisaEis = new Integer(rs.getInt(1));
				if(exception == null) {
					exception = new CommandException();
				}
				if(qtdePesquisaEis.intValue() > 0)
				{
					exception.setStrKeyConfigFile("key.erro.usuarios.possui.pesquisa.eis");
					exception.setObjArgs(new Object[] { usuarioExcluir, qtdePesquisaEis } );
					throw exception;
				}
			}catch(SQLException e)
			{
				logger.error("Erro ao pesquisar quantidade de registros de USUARIO_PESQUISA_EIS para o usuário!",e);
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

			getSession().delete(usuarioExcluir);	
		}else{
			//Usuário não existe mais.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.registro.nao.existe.mais");
			//exception.setObjArgs(new Object[] { grupo } );
			exception.setObjArgs(new Object[0]);
			throw exception;
		}
		
		logger.debug("Usuário excluído com sucesso");
		return objRetorno;
	}

	public Object[] validarDados(Object[] obj) throws CommandException {
		   Usuario usuario = (Usuario) obj[0];
		   Object[] objRetorno = new Object[0];
		   CommandException exception = null;
		   //Valida os dados para inclusão/alteração de dados
		   if(
			   BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO.equals(this.getStrMetodo())
			   									||
			   BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO.equals(this.getStrMetodo())

		   )
		   {
			   if(usuario.getCodUsuarioUsua() == null || "".equals(usuario.getCodUsuarioUsua().trim())){
					if(exception == null) {
						exception = new CommandException();
					}
					exception.setStrKeyConfigFile("key.erro.usuarios.codigo.faltando");
					exception.setObjArgs(new Object[] { usuario } );
					throw exception;
			   }else if(usuario.getNomeUsuarioUsua() == null || "".equals(usuario.getNomeUsuarioUsua().trim())){
						if(exception == null) {
							exception = new CommandException();
						}
						exception.setStrKeyConfigFile("key.erro.usuarios.nome.faltando");
						exception.setObjArgs(new Object[] { usuario } );
						throw exception;
			   }else{
				   usuario.setCodUsuarioUsua(usuario.getCodUsuarioUsua().toUpperCase());
				   usuario.setNomeUsuarioUsua(usuario.getNomeUsuarioUsua().toUpperCase());
				   if(!StringUtils.isEmpty(usuario.getCodEmailUsua()))
				   {
					   usuario.setCodEmailUsua(usuario.getCodEmailUsua().toUpperCase());
				   }
				   
			   }
		   }
		   return objRetorno;
	}


	public Object[] confirmarEdicaoGruposUsuario(Object[] args) throws CommandException {
		logger.debug("Editando os grupos do usuário!");
		Object[] objRetorno = new Object[0];
		Usuario usuarioAlterar = (Usuario) args[0];
		Usuario usuarioTMP = null;
		List listaGruposAlterar = (List)args[1];
		String codUsuarioUsua = usuarioAlterar.getCodUsuarioUsua();
		usuarioAlterar.setCodUsuarioUsua(null);
		usuarioTMP = (Usuario) getSession().get(Usuario.class, codUsuarioUsua);
		CommandException exception = null;
		if( usuarioTMP != null)
		{
			usuarioTMP.getGrupos().clear();
			usuarioTMP.getGrupos().addAll(listaGruposAlterar);
			getSession().update(usuarioTMP);
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


