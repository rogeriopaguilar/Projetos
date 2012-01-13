package dnsec.shared.util;

public class Constantes {

	/**
	 * Constante contendo o separador de arquivos específico do sistema operacional
	 * */	
	public static final String separador = System.getProperty("file.separator");

	/**
	 * Constante para o caminho do arquivo de recursos da aplicação
	 */
	public static final String ARQUIVO_RECURSOS = "config/dnsecresources";
	/**
	 * Constante contendo o caminho do arquivo de recursos da aplicação
	 * */
	public static  String ARQUIVO_CONFIG_HIBERNATE = "config/dnsec-hibernate-cfg.xml";
	/**
	 * Constante contendo o caminho do arquivo de configuração do log da aplicação
	 * */
	public static final String ARQUIVO_CONFIG_LOG = "config/log4j.dnsec.properties";
	
	
	
	/**
	 * Constantes utilizadas para pesquisar
	 * no arquivo de recursos da aplicação
	 */
	//Janela principal do sistema
	public static final String KEY_TITULO_JANELA_PRINCIPAL = "key.titulo.janela.principal";
	public static final String KEY_MENU_CADASTROS = "key.menu.cadastros";
	public static final String KEY_MENU_CADASTRO_SISTEMAS = "key.menu.cadastro.sistemas";
	public static final String KEY_MENU_CADASTRO_USUARIOS = "key.menu.cadastro.usuarios";
	public static final String KEY_MENU_CADASTRO_GRUPOS= "key.menu.cadastro.grupos";
	public static final String KEY_MENU_ASSOCIACOES = "key.menu.cadastro.associacoes";
	
	//erros do módulo de cadastro de grupos
	public static final String KEY_ERRO_GRUPOS_CHAVE_PRIMARIA_DUPLICADA = "key.erro.grupos.chave.primaria.duplicada";
	public static final String KEY_ERRO_GRUPOS_POSSUI_FUNCOES_ASSOCIADAS = "key.erro.grupos.possui.funcoes.associadas";
	public static final String KEY_ERRO_GRUPOS_POSSUI_USUARIO_ASSOCIADOS = "key.erro.grupos.possui.usuarios.associados";
	public static final String KEY_ERRO_GRUPOS_POSSUI_SISTEMAS_ASSOCIADOS = "key.erro.grupos.possui.sistemas.associados";
	
	
	/**
	 * Constantes gerais do arquivo de recursos
	 * */
	public static final String KEY_ERRO_OPERACAO_NAO_IMPLEMENTADA = "key.erro.operacao.nao.implementada";

	
	/**
	 * Constantes para SIM e NÃO
	 * */
	public static final String CONSTANTE_NAO = "N";
	public static final String CONSTANTE_SIM = "S";
	public static final Boolean CONSTANTE_BLN_NAO = new Boolean(false);
	public static final Boolean CONSTANTE_BLN_SIM = new Boolean(true);
	
	
	/**
	 * Constantes para as funções de segurança

		    incSistema --> inclusão de sistemas
		    altSistema --> alteração de sistemas
		    excSistemas --> exclusão de sistemas
		    incUsuario --> inclusão de usuários
		    altUsuario --> alteração de usuários
		    excUsuário --> exclusão de usuários
		    incGrupo --> inclusão de grupos
		    altGrupo --> alteração de grupos
		    excGrupo --> exclusão de grupos
		    incFuncao --> inclusão de funções
		    altFuncao --> alteração de funções
		    excFuncao --> exclusão de funções
		    altRelSistema --> gravação dos relacionamentos da aba sistema
		                      do formulário de associações
		    altRelUsuario --> gravação dos relacionamentos da aba usuários
		                      do formulário de associações
		    altRelGrupo --> gravação dos relacionamentos da aba grupo
		                      do formulário de associações
		    altRelFuncao --> gravação dos relacionamentos da aba função
		                      do formulário de associações

	 * 
	 * */

	public static final String COD_GRUPO_SIST    = "DNSECADMIN";
	public static final String COD_USR_ADM        = "DNSEC_ADMIN";
	public static final String COD_SIST_ADM        = "SEC";
	
    public static String FUNC_SEC_INC_SISTEMA     = "incSistema"; 
    public static String FUNC_SEC_ALT_SISTEMA     = "altSistema"; 
    public static String FUNC_SEC_EXC_SISTEMA     = "excSistemas";
    public static String FUNC_SEC_INC_USUARIO     = "incUsuario"; 
    public static String FUNC_SEC_ALT_USUARIO     = "altUsuario"; 
    public static String FUNC_SEC_EXC_USUARIO     = "excUsuario"; 
    public static String FUNC_SEC_INC_GRUPO       = "incGrupo";
    public static String FUNC_SEC_ALT_GRUPO       = "altGrupo"; 
    public static String FUNC_SEC_EXC_GRUPO       = "excGrupo"; 
    public static String FUNC_SEC_INC_FUNCAO      = "incFuncao";
    public static String FUNC_SEC_ALT_FUNCAO      = "altFuncao"; 
    public static String FUNC_SEC_EXC_FUNCAO      = "excFuncao"; 
    public static String FUNC_SEC_ALT_REL_SISTEMA = "altRelSistema"; 
    public static String FUNC_SEC_ALT_REL_USUARIO = "altRelUsuario"; 
    public static String FUNC_SEC_ALT_REL_GRUPO   = "altRelGrupo"; 
    public static String FUNC_SEC_ALT_REL_FUNCAO  = "altRelFuncao"; 
    public static String FUNC_ACESSO_TODOS_GRUPOS = "accTodosGrupos";
    public static String FUNC_ACESSO_TODOS_USUARIOS = "accTodosUsuarios";
    public static String FUNC_ACESSO_TODAS_FUNCOES = "accTodasFuncoes";
    public static String FUNC_ACESSO_TODOS_SISTEMAS = "accTodosSistemas";
	

    //Bases de dados - Abril
    public static final String BANCO_TD = "TD52";
    public static final String BANCO_TH = "TH52";
    public static final String BANCO_TP = "TP52";
    
    public static final String SIM = "S";
    public static final String NAO = "N";

    public static final String MYSQL = "MYSQL";
    public static final String ORACLE = "ORACLE";
    public static final String SQLSERVER= "SQL-SERVER";
    
}



