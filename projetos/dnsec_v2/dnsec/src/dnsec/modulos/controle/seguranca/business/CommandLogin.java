/**
 * Realiza o login do usu�rio no sistema 
 * */

package dnsec.modulos.controle.seguranca.business;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Expression;

import dnsec.modulos.cadastros.usuario.business.CriptografiaSenha;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.database.hibernate.Grupo;
import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.database.hibernate.TiposBanco;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.database.hibernate.pk.FuncaoPk;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.CriptografiaUtil;

public class CommandLogin extends BaseDispatchCRUDCommand {

	private CriptografiaSenha criptografiaSenha;

	public static String METODO_LOGIN = "login";

	public CommandLogin(CriptografiaSenha criptografiaSenha) {
		this.criptografiaSenha = criptografiaSenha;
	}

	/**
	 * Realiza o processo de login no site.
	 * 
	 * @param args
	 *            --> args[0] - Usu�rio
	 *            --> arg[1]  - C�digo do sistema que o usu�rio est� tentando acessar
	 * @throws CommandException - caso ocorra algum erro
	 * @return
	 */
	public Object[] login(Object[] args) throws CommandException {
		/*
		 * Regras de seguran�a para o sistema DNSEC:
		 * 
		 * 1 - Sigla do sistema: SEC 2 - Sigla do grupo relativo ao sistema:
		 * DNSECADMIN 3 - Quando o usu�rio fizer login no sistema, verificar
		 * usu�rio e senha 4 - Caso o usu�rio e a senha estejam corretos,
		 * verificar se o usu�rio est� relacionado ao grupo DNSECADMIN ou se � o
		 * usu�rio DNSEC_ADMIN. Caso o usu�rio n�o esteja relacionado com o
		 * grupo e n�o seja o usu�rio DNSEC_ADMIN, n�o permitir acesso ao
		 * sistema; 5 - Caso o usu�rio esteja relacionado ao grupo, verificar as
		 * regras de seguran�a; 6 - Fun��es de seguran�a:
		 * 
		 * incSistema --> inclus�o de sistemas altSistema --> altera��o de
		 * sistemas excSistemas --> exclus�o de sistemas incUsuario --> inclus�o
		 * de usu�rios altUsuario --> altera��o de usu�rios excUsu�rio -->
		 * exclus�o de usu�rios incGrupo --> inclus�o de grupos altGrupo -->
		 * altera��o de grupos excGrupo --> exclus�o de grupos incFuncao -->
		 * inclus�o de fun��es altFuncao --> altera��o de fun��es excFuncao -->
		 * exclus�o de fun��es altRelSistema --> grava��o dos relacionamentos da
		 * aba sistema do formul�rio de associa��es altRelUsuario --> grava��o
		 * dos relacionamentos da aba usu�rios do formul�rio de associa��es
		 * altRelGrupo --> grava��o dos relacionamentos da aba grupo do
		 * formul�rio de associa��es altRelFuncao --> grava��o dos
		 * relacionamentos da aba fun��o do formul�rio de associa��es
		 * 
		 * accTodosGrupos --> Usu�rio possui acesso a todos os grupos
		 * accTodosUsuarios --> usu�rio possui acesso a todos os usu�rios
		 * accTodasFuncoes --> usu�rio possui acesso a todas as fun��es
		 * accTodosSistemas --> usu�rio possui acesso a todos os sistemas
		 */

		Object[] objRetorno = null;
		Usuario usuario = (Usuario) args[0];
		String codSistemaLogin = (String) args[1];
		String senha = usuario.getCodSenhaUsua();
		usuario = (Usuario) getSession().get(Usuario.class, usuario.getCodUsuarioUsua());
		CommandException exception = null;
		if (usuario == null) {
			// Usu�rio n�o existe.
			exception = new CommandException();
			exception.setStrKeyConfigFile("key.erro.login.usuario.invalido");
			// exception.setObjArgs(new Object[] { grupo } );
			exception.setObjArgs(new Object[0]);
			throw exception;
		} else {
			Map mapaFuncoesUsuario = new HashMap();
			Map mapaSistemasUsuario = new HashMap();
			Map mapaGruposUsuario = new HashMap();
			objRetorno = new Object[] { usuario, mapaGruposUsuario,
					mapaSistemasUsuario, mapaFuncoesUsuario };
			if (criptografiaSenha != null) {
				String senhaCriptografada = "";
				if (senha == null) {
					senha = "";
				}
				senhaCriptografada = criptografiaSenha.criptografar(usuario
						.getCodUsuarioUsua(), senha);
				if (senhaCriptografada == null) {
					senhaCriptografada = "";
				}
				senha = usuario.getCodSenhaUsua();

				// Senha inv�lida.
				if (!senha.equals(senhaCriptografada)) {
					exception = new CommandException();
					exception
							.setStrKeyConfigFile("key.erro.login.senha.invalida");
					// exception.setObjArgs(new Object[] { grupo } );
					exception.setObjArgs(new Object[0]);
					throw exception;
				} else {
					// Carrega os grupos, fun��es e sistemas do usu�rio
					Iterator itGruposUsuario = usuario.getGrupos().iterator();
					while (itGruposUsuario.hasNext()) {
						Grupo grupo = (Grupo) itGruposUsuario.next();
						grupo.getFuncoes().iterator();
						// Guarda os nomes das fun��es num mapa
						for (Funcao funcao : new LinkedList<Funcao>(grupo.getFuncoes())) {
							mapaFuncoesUsuario.put(funcao.getNomeFunc(), funcao);
							/*Hibernate.initialize(funcao.getFuncoesFilhas());
							Hibernate.initialize(funcao.getGrupos());*/
						}
						Iterator itSistemas = grupo.getSistemas().iterator();
						while (itSistemas.hasNext()) {
							Sistema sistema = (Sistema) itSistemas.next();
							mapaSistemasUsuario.put(sistema.getCodSistemaSist(), sistema);
						}
						mapaGruposUsuario.put(grupo.getCodGrupoGrup(), grupo);
					}

					// Verifica se o usu�rio possui acesso ao sistemae se n�o � o admin, caso seja o sistema de seguran�a
					if(Constantes.COD_SIST_ADM.equals(codSistemaLogin)){
						if (mapaGruposUsuario.get(Constantes.COD_GRUPO_SIST) == null && !Constantes.COD_USR_ADM.equalsIgnoreCase(usuario.getCodUsuarioUsua())) {
							exception = new CommandException();
							exception.setStrKeyConfigFile("key.erro.login.usuario.nao.possui.acesso.ao.sistema");
							// exception.setObjArgs(new Object[] { grupo } );
							exception.setObjArgs(new Object[0]);
							throw exception;
						}
					}else{
						//Usu�rio est� realizando o login em outro sistema que utiliza a api de login
						if (mapaSistemasUsuario.get(codSistemaLogin) == null) {
							exception = new CommandException();
							exception.setStrKeyConfigFile("key.erro.login.usuario.nao.possui.acesso.ao.sistema");
							// exception.setObjArgs(new Object[] { grupo } );
							exception.setObjArgs(new Object[0]);
							throw exception;
						}
					}
				}
			}

		}

		return objRetorno;
	}

	@Override
	public Object[] listar(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] prepararInclusao(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] prepararEdicao(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] confirmarInclusao(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] confirmarEdicao(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] excluir(Object[] args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] validarDados(Object[] obj) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Verifica se o sistema existe na base de dados Verifica se o grupo de
	 * usu�rios administradores existe na base de dados Verifica se o usu�rio
	 * DNSEC_ADMIN existe na base de dados Verifica se o sistema SEC existe na
	 * base de dados Verifica se as fun��es de controle de acesso relacionadas
	 * com o sistema de seguran�a existem na base de dados Verifica se os tipos
	 * de banco de dados existem Cadastra as informa��es n�o existentes para
	 * deixar o banco pronto para utiliza��o
	 */
	public Object[] setupBancoDados(Object[] obj) throws CommandException{
		TiposBanco tipoBanco = new TiposBanco();

		tipoBanco.setCodTipoBanco(Constantes.MYSQL);
		if(getSession().get(TiposBanco.class, tipoBanco.getCodTipoBanco()) == null){
			getSession().save(tipoBanco);
		}
		
		tipoBanco = new TiposBanco();
		tipoBanco.setCodTipoBanco(Constantes.ORACLE);
		if(getSession().get(TiposBanco.class, tipoBanco.getCodTipoBanco()) == null){
			getSession().save(tipoBanco);
		}

		tipoBanco = new TiposBanco();
		tipoBanco.setCodTipoBanco(Constantes.SQLSERVER);
		if(getSession().get(TiposBanco.class, tipoBanco.getCodTipoBanco()) == null){
			getSession().save(tipoBanco);
		}

		Sistema sistema = new Sistema();
		sistema.setCodSistemaSist(Constantes.COD_SIST_ADM);
		sistema.setDescrSistemaSist("Controle de seguran�a");
		sistema.setCondCadastrarSist(Constantes.SIM);
		sistema.setNomeAnalistaRespSist("DNSEC_ADMIN");
		sistema.setNomeBancoSist(Constantes.MYSQL);
		sistema.setNomeProprietarioSist("DNSEC_ADMIN");
		sistema.setNomeServidorSist("SERVIDOR");
		sistema.setTipoBancoSist(Constantes.MYSQL);
		
		if(getSession().get(Sistema.class, sistema.getCodSistemaSist()) == null){
			getSession().save(sistema);
		}

		Usuario usuario = new Usuario();
		usuario.setCodUsuarioUsua(Constantes.COD_USR_ADM);
		usuario.setNomeUsuarioUsua("DNSEC_ADMIN");
		try {
			usuario.setCodSenhaUsua(CriptografiaUtil.getInstance().encrypt("adminadmin"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException("Erro ao criptografar senha...", e);
		}
		usuario.setDataInclusaoUsua(new Timestamp(System.currentTimeMillis()));
		if(getSession().get(Usuario.class, usuario.getCodUsuarioUsua()) == null){
			getSession().save(usuario);
		}

		Grupo grupo = new Grupo();
		grupo.setCodGrupoGrup(Constantes.COD_GRUPO_SIST);
		grupo.setDescrGrupoGrup("Grupo de usu�rios administradores");
		if(getSession().get(Grupo.class, grupo.getCodGrupoGrup()) == null){
			getSession().save(grupo);
			grupo = (Grupo) getSession().get(Grupo.class, Constantes.COD_GRUPO_SIST);
		}
		
		long idFuncao = 9999999;
		Funcao funcao = new Funcao();
	    FuncaoPk funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);
	    
	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_SEC_INC_SISTEMA);     
	    funcao.setCompId(funcaoPk);
		Criteria critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}

		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);
	    
	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_SEC_ALT_SISTEMA);   
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}
	    
		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_SEC_EXC_SISTEMA);     
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}

		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_SEC_INC_USUARIO);    
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}

		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_SEC_ALT_USUARIO);    
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}

		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_SEC_EXC_USUARIO);     
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}
	    
		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
	    funcao.setNomeFunc(Constantes.FUNC_SEC_INC_GRUPO);      
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}

		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_SEC_ALT_GRUPO);     
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}

		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_SEC_EXC_GRUPO);       
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}

		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_SEC_INC_FUNCAO);      
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}
	    

		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_SEC_ALT_FUNCAO);      
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}

	    
		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_SEC_EXC_FUNCAO);       
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}
	    

		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_SEC_ALT_REL_SISTEMA); 
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}

		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_SEC_ALT_REL_USUARIO);
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}

		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_SEC_ALT_REL_GRUPO);    
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}
	    
		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_SEC_ALT_REL_FUNCAO);  
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}
	    

		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_ACESSO_TODOS_GRUPOS);
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}

		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_ACESSO_TODOS_USUARIOS); 
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}
	    
		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
	    funcao.setNomeFunc(Constantes.FUNC_ACESSO_TODAS_FUNCOES);
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}

		funcao = new Funcao();
	    funcaoPk = new FuncaoPk();
	    funcaoPk.setCodSistemaSist(Constantes.COD_SIST_ADM);

	    funcaoPk.setCodFuncaoFunc(idFuncao++);
		funcao.setNomeFunc(Constantes.FUNC_ACESSO_TODOS_SISTEMAS); 
	    funcao.setCompId(funcaoPk);
		critFuncao = getSession().createCriteria(Funcao.class);
		critFuncao.add(Expression.eq("compId.codSistemaSist",funcaoPk.getCodSistemaSist()));
		critFuncao.add(Expression.eq("nomeFunc", funcao.getNomeFunc()));
	    if(critFuncao.list().size() == 0){
			getSession().save(funcao);
		}
	    

	    
	    for(int i = 0; i < 1000; i++)
	    {
	    	sistema = new Sistema();
	    	sistema.setCodSistemaSist("" + i);
	    	sistema.setDescrSistemaSist("Teste " + i);
	    	sistema.setTipoBancoSist(Constantes.MYSQL);
	    	getSession().save(sistema);
	    }
	    
	    return null;
	}
}
