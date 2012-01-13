/**
 * Realiza o login do usuário no sistema 
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
	 *            --> args[0] - Usuário
	 *            --> arg[1]  - Código do sistema que o usuário está tentando acessar
	 * @throws CommandException - caso ocorra algum erro
	 * @return
	 */
	public Object[] login(Object[] args) throws CommandException {
		/*
		 * Regras de segurança para o sistema DNSEC:
		 * 
		 * 1 - Sigla do sistema: SEC 2 - Sigla do grupo relativo ao sistema:
		 * DNSECADMIN 3 - Quando o usuário fizer login no sistema, verificar
		 * usuário e senha 4 - Caso o usuário e a senha estejam corretos,
		 * verificar se o usuário está relacionado ao grupo DNSECADMIN ou se é o
		 * usuário DNSEC_ADMIN. Caso o usuário não esteja relacionado com o
		 * grupo e não seja o usuário DNSEC_ADMIN, não permitir acesso ao
		 * sistema; 5 - Caso o usuário esteja relacionado ao grupo, verificar as
		 * regras de segurança; 6 - Funções de segurança:
		 * 
		 * incSistema --> inclusão de sistemas altSistema --> alteração de
		 * sistemas excSistemas --> exclusão de sistemas incUsuario --> inclusão
		 * de usuários altUsuario --> alteração de usuários excUsuário -->
		 * exclusão de usuários incGrupo --> inclusão de grupos altGrupo -->
		 * alteração de grupos excGrupo --> exclusão de grupos incFuncao -->
		 * inclusão de funções altFuncao --> alteração de funções excFuncao -->
		 * exclusão de funções altRelSistema --> gravação dos relacionamentos da
		 * aba sistema do formulário de associações altRelUsuario --> gravação
		 * dos relacionamentos da aba usuários do formulário de associações
		 * altRelGrupo --> gravação dos relacionamentos da aba grupo do
		 * formulário de associações altRelFuncao --> gravação dos
		 * relacionamentos da aba função do formulário de associações
		 * 
		 * accTodosGrupos --> Usuário possui acesso a todos os grupos
		 * accTodosUsuarios --> usuário possui acesso a todos os usuários
		 * accTodasFuncoes --> usuário possui acesso a todas as funções
		 * accTodosSistemas --> usuário possui acesso a todos os sistemas
		 */

		Object[] objRetorno = null;
		Usuario usuario = (Usuario) args[0];
		String codSistemaLogin = (String) args[1];
		String senha = usuario.getCodSenhaUsua();
		usuario = (Usuario) getSession().get(Usuario.class, usuario.getCodUsuarioUsua());
		CommandException exception = null;
		if (usuario == null) {
			// Usuário não existe.
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

				// Senha inválida.
				if (!senha.equals(senhaCriptografada)) {
					exception = new CommandException();
					exception
							.setStrKeyConfigFile("key.erro.login.senha.invalida");
					// exception.setObjArgs(new Object[] { grupo } );
					exception.setObjArgs(new Object[0]);
					throw exception;
				} else {
					// Carrega os grupos, funções e sistemas do usuário
					Iterator itGruposUsuario = usuario.getGrupos().iterator();
					while (itGruposUsuario.hasNext()) {
						Grupo grupo = (Grupo) itGruposUsuario.next();
						grupo.getFuncoes().iterator();
						// Guarda os nomes das funções num mapa
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

					// Verifica se o usuário possui acesso ao sistemae se não é o admin, caso seja o sistema de segurança
					if(Constantes.COD_SIST_ADM.equals(codSistemaLogin)){
						if (mapaGruposUsuario.get(Constantes.COD_GRUPO_SIST) == null && !Constantes.COD_USR_ADM.equalsIgnoreCase(usuario.getCodUsuarioUsua())) {
							exception = new CommandException();
							exception.setStrKeyConfigFile("key.erro.login.usuario.nao.possui.acesso.ao.sistema");
							// exception.setObjArgs(new Object[] { grupo } );
							exception.setObjArgs(new Object[0]);
							throw exception;
						}
					}else{
						//Usuário está realizando o login em outro sistema que utiliza a api de login
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
	 * usuários administradores existe na base de dados Verifica se o usuário
	 * DNSEC_ADMIN existe na base de dados Verifica se o sistema SEC existe na
	 * base de dados Verifica se as funções de controle de acesso relacionadas
	 * com o sistema de segurança existem na base de dados Verifica se os tipos
	 * de banco de dados existem Cadastra as informações não existentes para
	 * deixar o banco pronto para utilização
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
		sistema.setDescrSistemaSist("Controle de segurança");
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
		grupo.setDescrGrupoGrup("Grupo de usuários administradores");
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
