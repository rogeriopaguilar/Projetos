

package br.com.dnsec.web.filtro;

import br.com.dnsec.web.filtro.bean.DadosUsuarioBean;

import br.com.dnsec.web.filtro.exception.LoginException;

import com.thoughtworks.xstream.XStream;

import dnsec.modulos.controle.seguranca.business.CommandLogin;

import dnsec.modulos.controle.seguranca.vo.ControleSegurancaVo;

import dnsec.shared.command.impl.BaseDispatchCRUDCommand;

import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.database.hibernate.Grupo;
import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.database.hibernate.pk.FuncaoPk;
import dnsec.shared.factory.CommandFactory;

import dnsec.shared.icommand.exception.CommandException;

import dnsec.shared.util.RecursosUtil;
import dnsec.shared.util.StringUtil;

import dnsec.web.vo.ConfigSegurancaVo;

import java.io.CharArrayWriter;
import java.io.IOException;

import java.io.PrintWriter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class DNSECFiltro implements Filter {

    private static Logger logger = Logger.getLogger("dnsec.filtro.web.DNSECFiltro");

    //Par�metro que pode vir em alguma requisi��o indicando que a mesma � uma requisi��o AJAX
    public static final String CHAVE_REQUEST_DNSEC_REQUISICAO_AJAX = "dnsec.web.filtro.requisicao.ajax";        
    //Par�metro que pode vir na requisi��o indicando que a mesma deve ser processada como uma requisi��o normal (n�o ajax) 
    public static final String CHAVE_REQUEST_CONF_DNSEC_REQ_NORMAL = "dnsec.web.filtro.requisicao.normal";
    //Par�metro de inicializa��o do filtro que indica que todas as requisi��es devem ser tratadas como requisi��esAJAX
    public static final String CHAVE_ARQUIVO_CONF_DNSEC_REQ_DEFAULT_AJAX = "dnsec.web.filtro.requisicao.default.ajax";

    //Par�metro de inicializa��o do filtro que indica as urls que devem passar sem verifica��o do filtro
    public static final String CHAVE_ARQUIVO_CONF_DNSEC_URLS_EXCLUIR_FILTRO = "dnsec.web.filtro.urls.excluir.filtro";

    //Chave para o objeto da sess�o que cont�m os dados do usu�rio conectado
    public static final String CHAVE_SESSAO_DNSEC_DADOS_USUARIO = "dnsec.web.filtro.sessao.dados.usuario";

    //Chave para par�metro de requisi�ao que deve estar presente indicando que a requisi�ao e uma requisi�ao para efetuar login
    public static final String CHAVE_REQUEST_DNSEC_ACAO_LOGIN = "dnsec.web.filtro.requisicao.login";
    //Chave para par�metro de request que deve conter o c�digo do usu�rio que deseja realizar login no sistema
    public static final String CHAVE_REQUEST_DNSEC_ACAO_LOGIN_USUARIO = "dnsec.web.filtro.requisicao.login.usuario";
    //Chave para par�metro de request que deve possuir a senha do usu�rio que deseja realizar login no sistema
    public static final String CHAVE_REQUEST_DNSEC_ACAO_LOGIN_SENHA = "dnsec.web.filtro.requisicao.login.senha";
    //Chave para resposta de requisi��o de login ajax indicando que deve ser exibida a p�gina de login
    public static final String CHAVE_RESPOSTA_AJAX_DNSEC_ACAO_LOGIN = "dnsec.web.filtro.requisicao.login.resposta.xml.ajax.redirecionar.login";


    //Chaves para os status poss�veis para as opera��es requisitadas via ajax
    public static final String CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_SUCESSO = "dnsec.web.filtro.acao.status.SUCESSO";
    public static final String CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO = "dnsec.web.filtro.acao.status.ERRO";
    public static final String CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_MENSAGEM = "dnsec.web.filtro.acao.status.erro.mensagem";
    public static final String CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_DESC = "dnsec.web.filtro.acao.status.erro.descricao";
    public static final String CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_STACK_TRACE = "dnsec.web.filtro.acao.status.erro.stack.trace";

    //Chave indicando que a requisi��o de verifica��o de acesso a um recurso deve verificar tamb�m a fun��o pai
    public static final String CHAVE_REQUEST_DNSEC_ACESSO_FUNCAO_TRAVAR_FUNCAO_PAI = "dnsec.web.filtro.pagina.acesso.travar.funcao.pai";    
    
    /*Chave para o caminho completo de uma classe que pode estar no arquivo de configura��o respons�vel por implementar que id de recurso
     * est� relacionado a uma determinada url. Por default o filtro retira a �ltima parte da URL, a extens�o (se presente) e utiliza esta parte
     * da url como id do recurso que deve ser consultado
     * */
    public static final String CHAVE_ARQUIVO_CONF_DNSEC_CLASSE_EXTRACAO_ID_RECURSO = "dnsec.web.filtro.classe.extracao.id.recurso";
    
    //Chave para entrada no arquivo de configura��o que pode estar presente indicando a p�gina de login no sistema
    public static final String CHAVE_ARQUIVO_CONF_DNSEC_PAGINA_LOGIN = "dnsec.web.filtro.pagina.login";
    //Chave para entrada no arquivo de configura��o que pode estar presente indicando a p�gina de acesso negado a um determinado recurso
    public static final String CHAVE_ARQUIVO_CONF_DNSEC_PAGINA_ACESSO_NEGADO = "dnsec.web.filtro.pagina.acesso.negado";

    //C�digo do sistema que ser� utilizado no para pesquisa no cadastro de seguran�a
    public static String  CHAVE_ARQUIVO_CONF_DNSEC_SISTEMA_ID = "dnsec.web.filtro.sistema.id";
	


    //Caminhos "default" para p�ginas de login e de acesso negado
    protected String PAGINA_LOGIN_DEFAULT = "/paginas/login_default.html";
    protected String PAGINA_ACESSO_NEGADO_DEFAULT = "/paginas/acesso_negado_default.html";
    

    protected String caminhoPaginaAcessoNegado = null;
    protected String caminhoPaginaLogin = null;
    protected String classeIdRecurso = null;
    protected IExtratorIdRecurso iExtratorIdRecurso = null;
    protected String sistemaId = null;
    //Sem problemas, pois ostream � thread safe!
    protected XStream xstream = new XStream();

    private String[] strURLsExcluirFiltro = new String[0];

    private FilterConfig _filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        _filterConfig = filterConfig;

        if(logger.isLoggable(Level.FINER))
        {
            logger.finer("Inicializando filtro DNSEC");
        }
        //Verifica se existe o c�digo do sistema que deve estar configurado para o cadastro de seguran�a
        if(_filterConfig.getInitParameter(CHAVE_ARQUIVO_CONF_DNSEC_SISTEMA_ID) != null)
        {
            sistemaId = _filterConfig.getInitParameter(CHAVE_ARQUIVO_CONF_DNSEC_SISTEMA_ID);
            if(logger.isLoggable(Level.FINER))
            {
                logger.finer("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_SISTEMA_ID + " encontrada --> " + sistemaId);
            }
        }else
        {
            if(logger.isLoggable(Level.SEVERE))
            {
                logger.severe("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_SISTEMA_ID + " n�o existe no arquivo de configura��o");
            }
            throw new ServletException("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_SISTEMA_ID + " n�o existe no arquivo de configura��o. Sem ela n�o � poss�vel consistir os dados no sistema de seguran�a!");
        }    

        //Verifica se existe entrada indicando lista de urls que devem ser desconsideradas pelo filtro    
        if(logger.isLoggable(Level.FINER))
        {
            logger.finer("Verificando exist�ncia da entrada " + CHAVE_ARQUIVO_CONF_DNSEC_URLS_EXCLUIR_FILTRO + " no arquivo de configura��o");
        }
        if(_filterConfig.getInitParameter(CHAVE_ARQUIVO_CONF_DNSEC_URLS_EXCLUIR_FILTRO) != null)
        {
            strURLsExcluirFiltro = _filterConfig.getInitParameter(CHAVE_ARQUIVO_CONF_DNSEC_URLS_EXCLUIR_FILTRO).split("[,]");
            if(logger.isLoggable(Level.FINER))
            {
                logger.finer("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_URLS_EXCLUIR_FILTRO + " encontrada --> " + _filterConfig.getInitParameter(CHAVE_ARQUIVO_CONF_DNSEC_URLS_EXCLUIR_FILTRO));
            }
        }else
        {
            if(logger.isLoggable(Level.FINER))
            {
                logger.finer("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_URLS_EXCLUIR_FILTRO + " n�o existe no arquivo de configura��o");
            }
        }    

        //Verifica se existe uma p�gina de login configurada no arquivo web.xml
        if(_filterConfig.getInitParameter(CHAVE_ARQUIVO_CONF_DNSEC_PAGINA_LOGIN) != null)
        {
            caminhoPaginaLogin = _filterConfig.getInitParameter(CHAVE_ARQUIVO_CONF_DNSEC_PAGINA_LOGIN);
            if(logger.isLoggable(Level.FINER))
            {
                logger.finer("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_PAGINA_LOGIN + " encontrada --> " + caminhoPaginaLogin);
            }
        }else
        {
            if(logger.isLoggable(Level.FINER))
            {
                logger.finer("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_PAGINA_LOGIN + " n�o existe no arquivo de configura��o");
            }
        }    

        //Verifica se existe uma p�gina de acesso negado configurada no arquivo de inicializa��o
        if(_filterConfig.getInitParameter(CHAVE_ARQUIVO_CONF_DNSEC_PAGINA_ACESSO_NEGADO) != null)
        {
            caminhoPaginaAcessoNegado = _filterConfig.getInitParameter(CHAVE_ARQUIVO_CONF_DNSEC_PAGINA_ACESSO_NEGADO);
            if(logger.isLoggable(Level.FINER))
            {
                logger.finer("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_PAGINA_ACESSO_NEGADO + " encontrada --> " + caminhoPaginaAcessoNegado);
            }
        }else
        {
            if(logger.isLoggable(Level.FINER))
            {
                logger.finer("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_PAGINA_ACESSO_NEGADO + " n�o existe no arquivo de configura��o");
            }
        }    


        //Verifica se existe uma classe configurada para extrair o id do recurso no sistema de seguran�a
        if(_filterConfig.getInitParameter(CHAVE_ARQUIVO_CONF_DNSEC_CLASSE_EXTRACAO_ID_RECURSO) != null)
        {
            classeIdRecurso = _filterConfig.getInitParameter(CHAVE_ARQUIVO_CONF_DNSEC_CLASSE_EXTRACAO_ID_RECURSO);
            if(logger.isLoggable(Level.FINER))
            {
                logger.finer("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_CLASSE_EXTRACAO_ID_RECURSO + " encontrada --> " + classeIdRecurso );
                logger.finer("Instanciando classe...");
                try {
                    iExtratorIdRecurso = (IExtratorIdRecurso)Class.forName(classeIdRecurso).newInstance();
                }catch (Exception e) {
                    e.printStackTrace();
                    ServletException ex = new ServletException("Erro ao instanciar classe...", e);
                    logger.throwing(getClass().getName(), "doFilter", ex);
                    throw ex;
                }
            }
        }else
        {
            if(logger.isLoggable(Level.FINER))
            {
                logger.finer("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_CLASSE_EXTRACAO_ID_RECURSO + " n�o existe no arquivo de configura��o");
            }
        }    

        xstream.alias("usuario", Usuario.class);
        xstream.alias("grupo", Grupo.class);
        xstream.alias("funcao", Funcao.class);
        xstream.alias("sistema", Sistema.class);
        xstream.omitField(Usuario.class,"grupos");
        xstream.aliasField("numchapafunc", Usuario.class, "numChapaFunc");
        xstream.aliasField("codusuariousua", Usuario.class, "codUsuarioUsua"); 
        xstream.aliasField("nomeusuariousua", Usuario.class, "nomeUsuarioUsua");
        xstream.aliasField("codsenhausua", Usuario.class, "codSenhaUsua");
        xstream.aliasField("datainclusaousua", Usuario.class, "dataInclusaoUsua" );
        xstream.aliasField("dataultacessousua", Usuario.class, "dataUltAcessoUsua");
        xstream.aliasField("dataultaltsenhausua", Usuario.class, "dataUltAltSenhaUsua");
        xstream.aliasField("numdiasvalidadesenhausua", Usuario.class, "numDiasValidadesenhaUsua");
        xstream.aliasField("qtdeerrosloginusua", Usuario.class, "qtdeErrosLoginUsua");
        xstream.aliasField("condaltsenhausua", Usuario.class, "condAltSenhaUsua");
        xstream.aliasField("numtelefoneusua", Usuario.class, "numTelefoneUsua");
        xstream.aliasField("codemailusua", Usuario.class, "codEmailUsua");
        xstream.aliasField("condbloqueadousua", Usuario.class, "condBloqueadoUsua");
        xstream.aliasField("codusuarioadabasusua", Usuario.class, "codUsuarioAdabasUsua");
        xstream.aliasField("datasolicnovasenha", Usuario.class, "dataSolicNovaSenha");
        xstream.omitField(Sistema.class,"grupos");
        xstream.omitField(Sistema.class,"funcoes");
        xstream.aliasField("codsistemasist", Sistema.class, "codSistemaSist");
        xstream.aliasField("descrsistemasist", Sistema.class, "descrSistemaSist");
        xstream.aliasField("condcadastrarsist", Sistema.class, "condCadastrarSist");
        xstream.aliasField("nomebancosist", Sistema.class, "nomeBancoSist");
        xstream.aliasField("nomeservidorsist", Sistema.class, "nomeServidorSist");
        xstream.aliasField("tipobancosist", Sistema.class, "tipoBancoSist");
        xstream.aliasField("nomeproprietariosist", Sistema.class, "nomeProprietarioSist");
        xstream.aliasField("nomeanalistarespsist", Sistema.class, "nomeAnalistaRespSist");
        xstream.omitField(Grupo.class,"usuarios");
        xstream.omitField(Grupo.class,"sistemas");
        xstream.omitField(Grupo.class,"funcoes");
        xstream.aliasField("codgrupogrup", Grupo.class, "codGrupoGrup" );
        xstream.aliasField("descrgrupogrup", Grupo.class, "descrGrupoGrup");
        xstream.omitField(Funcao.class,"funcoesFilhas");
        xstream.omitField(Funcao.class,"grupos");
        //xstream.omitField(Funcao.class,"compId");
        xstream.omitField(Funcao.class,"sistema");
        xstream.aliasField("compid", Funcao.class, "compId");
        //FuncaoPk compId = new FuncaoPk();
        xstream.aliasField("codfuncaofunc", FuncaoPk.class, "codFuncaoFunc");
        xstream.aliasField("codsistemasist", FuncaoPk.class, "codSistemaSist");
        xstream.aliasField("nomefunc", Funcao.class, "nomeFunc");
        xstream.aliasField("descrfuncaofunc", Funcao.class, "descrFuncaoFunc");
        xstream.aliasField("codsistemapaifunc", Funcao.class, "codSistemaPaiFunc");
        xstream.aliasField("codfuncaopaifunc", Funcao.class, "codFuncaoPaiFunc" );
            
    }

    public void destroy() {
        _filterConfig = null;
    }

    /**
    * Algoritmo:
    * 
    *  Se o recurso n�o est� na lista de exclus�o para o filtro ent�o
    *      Se n�o existe objeto com os dados do usu�rio na sess�o
    *         se requisi��o � uma requisi��o para realizar o login
    *            validar usu�rio e senha
    *            se usu�rio e senha v�lidos
    *               adicionar dados do usu�rio na sess�o
    *               se requisi��o normal
    *                   processar recurso
    *               sen�o// requisi��o AJAX
    *                   retornar XML contendo os dados da opera��o
    *            sen�o
    *               adicionar mensagem de erro de login na requisi��o
    *               se requisi��o normal
    *                   retornar para a tela de login
    *               sen�o
    *                   retornar XML contendo o status da opera��o
    *         sen�o
    *           se requisi��o normal
    *               se existe uma p�gina de login configurada
    *                   redirecionar para a p�gina de login
    *               sen�o
    *                   exibir p�gina de login default
    *           sen�o
    *               retornar xml contendo o status da requisi��o
    *      sen�o 
    *           Se o recurso est� presente na lista de fun��es dos grupos
    *           relacionados ao usu�rio
    *               Se requisi��o normal
    *                   processar o pr�ximo recurso
    *               sen�o //Requisi��o AJAX
    *                   retornar XML contendo o status da requisi��o
    *           sen�o
    *               Se requisi��o normal
    *                   Se existe uma p�gina de acesso negado
    *                       retornar para esta p�gina
    *                   sen�o
    *                       retornar mensagem default de acesso negado
    *               sen�o //Requisi��o AJAX
    *                   retornar XML com o status da requisi��o
    *  sen�o
    *      processar o pr�ximo recurso
    * */ 
    
    public void doFilter(ServletRequest request, ServletResponse response, 
                         FilterChain chain) throws IOException, ServletException 
    {
        HttpServletRequest httpRequest = ((HttpServletRequest)request);
        HttpServletResponse httpResponse = ((HttpServletResponse)response);
        HttpSession session = httpRequest.getSession();
        
        if(logger.isLoggable(Level.FINER))
        {
            logger.finer("Inicializando processamento do filtro DNSEC");
        }
        if(logger.isLoggable(Level.FINEST))
        {
            logger.finest("*****************Par�metros da requisi��o*****************");
            Enumeration<String> enumParametros = request.getParameterNames();
            while(enumParametros.hasMoreElements())
            {
                String chaveParametro = enumParametros.nextElement();
                logger.finest("\n*--" + chaveParametro);
                for(String valoresParametro : request.getParameterValues(chaveParametro))
                {
                    logger.finest("\n*----" + valoresParametro);
                }
                logger.finest("*");
            }
            logger.finest("\n**********************************************************");
        }
        
        String strIdRecurso = "";
        //Verifica se o recurso � um recurso protegido
        //Se o recurso n�o est� na lista de exclus�o para o filtro ent�o
        if(!isRecursoEstaNaListaDeExclusao(strIdRecurso))
        {
            //Se n�o existe objeto com os dados do usu�rio na sess�o
            if( session.getAttribute(CHAVE_SESSAO_DNSEC_DADOS_USUARIO) == null)
            {
                if( request.getParameter(CHAVE_REQUEST_DNSEC_ACAO_LOGIN_USUARIO) != null)
                {
                    if(logger.isLoggable(Level.FINER))
                    {
                        logger.finer("Usu�rio solicitou login no sistema");
                    }
                   /*
                    *         se requisi��o � uma requisi��o para realizar o login
                    *            validar usu�rio e senha
                    *            se usu�rio e senha v�lidos
                    *               adicionar dados do usu�rio na sess�o
                    *               se requisi��o normal
                    *                   processar recurso
                    *               sen�o// requisi��o AJAX
                    *                   retornar XML contendo os dados da opera��o
                    *            sen�o
                    *               adicionar mensagem de erro de login na requisi��o
                    *               se requisi��o normal
                    *                   retornar para a tela de login
                    *               sen�o
                    *                   retornar XML contendo o status da opera��o
                    */
    
                    String strUsuario = request.getParameter(CHAVE_REQUEST_DNSEC_ACAO_LOGIN_USUARIO);
                    String strSenha = request.getParameter(CHAVE_REQUEST_DNSEC_ACAO_LOGIN_SENHA);


                    if(logger.isLoggable(Level.FINER))
                    {
                        logger.finer("Par�metros para login:\n");
                        logger.finer(CHAVE_REQUEST_DNSEC_ACAO_LOGIN_USUARIO + " --> " + strUsuario);
                        logger.finer(CHAVE_REQUEST_DNSEC_ACAO_LOGIN_SENHA + " --> " + strSenha);
                    }
                    
                    if(strUsuario == null)
                    {
                        if(isRequisicaoAjax(httpRequest))
                        {
                        	Map<String,String> mapaParametros = new HashMap<String,String>();
                        	mapaParametros.put(CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_MENSAGEM, "Par�metro " + CHAVE_REQUEST_DNSEC_ACAO_LOGIN_USUARIO + " n�o est� presenta na requisi��o!");
                            String strResposta = getRespostaRequisicaoAjax(CHAVE_REQUEST_DNSEC_ACAO_LOGIN,CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO,mapaParametros);
                        	response.setContentType("text/plain");
                            response.getOutputStream().print(strResposta);                                                    
                            response.getOutputStream().flush();
                            return;
                        }else{
                            ServletException ex = new ServletException("Par�metro " + CHAVE_REQUEST_DNSEC_ACAO_LOGIN_USUARIO + " n�o est� presenta na requisi��o!");
                            logger.throwing(getClass().getName(), "doFilter", ex);
                            throw ex;
                        }
                    }
                    if(strSenha == null)
                    {
                        if(isRequisicaoAjax(httpRequest))
                        {
                        	Map<String,String> mapaParametros = new HashMap<String,String>();
                        	mapaParametros.put(CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_MENSAGEM, "Par�metro " + CHAVE_REQUEST_DNSEC_ACAO_LOGIN_SENHA + " n�o est� presenta na requisi��o!");
                            String strResposta = getRespostaRequisicaoAjax(CHAVE_REQUEST_DNSEC_ACAO_LOGIN,CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO,mapaParametros);
                        	response.setContentType("text/plain");
                            response.getOutputStream().print(strResposta);                                                    
                            response.getOutputStream().flush();
                        }else{
                            ServletException ex = new ServletException("Par�metro " + CHAVE_REQUEST_DNSEC_ACAO_LOGIN_SENHA + " n�o est� presenta na requisi��o!");
                            logger.throwing(getClass().getName(), "doFilter", ex);
                            throw ex;
                        }
                    }
                    
                    if(logger.isLoggable(Level.FINER))
                    {
                        logger.finer("Inicializando procedimento de login");
                    }
                    try{
                        DadosUsuarioBean dadosUsuarioBean = login(strUsuario, strSenha, httpRequest, httpResponse);                    
                        session.setAttribute(CHAVE_SESSAO_DNSEC_DADOS_USUARIO, dadosUsuarioBean);
                        if(logger.isLoggable(Level.FINER))
                        {
                            logger.finer("Procedimento de login realizado com sucesso");
                        }
                        if(isRequisicaoAjax(httpRequest))
                        {
                            response.setContentType("text/plain");
                            Map mapaParametros = new HashMap();
                            mapaParametros.put("dadosusuario",
                            			xstream.toXML(((ControleSegurancaVo)dadosUsuarioBean.retornarValorAtributo("CONTROLE_SEGURANCA_VO")).getUsuarioConectado())
                            			);
                            response.getOutputStream().print(getRespostaRequisicaoAjax(CHAVE_REQUEST_DNSEC_ACAO_LOGIN, CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_SUCESSO, mapaParametros));                                                    
                            response.getOutputStream().flush();
                            return;
                        }
                    }catch(CommandException commandException)
                    {
                        if(logger.isLoggable(Level.FINER))
                        {
                            logger.finer("Erro no processo de login no sistema --> " + commandException);
                        }
                        if(isRequisicaoAjax(httpRequest))
                        {
                        	Map<String,String> mapaParametros = new HashMap<String,String>();
                        	tratarMensagemErroAjax(commandException, mapaParametros);	
                            String strResposta = getRespostaRequisicaoAjax(CHAVE_REQUEST_DNSEC_ACAO_LOGIN,CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO,mapaParametros);
                            if(logger.isLoggable(Level.FINER))
                            {
                            	logger.finer("Enviando resposta xml(ajax) --> " + strResposta);
                            }
                        	response.setContentType("text/plain");
                            response.getOutputStream().print(strResposta);                                                    
                            response.getOutputStream().flush();
                            return;
                        }else
                        {
                            tratarMensagemErro(DNSECFiltro.CHAVE_REQUEST_DNSEC_ACAO_LOGIN, httpRequest, httpResponse, commandException);
                            return;
                        }
                    }
                }else
                {
                       /*         sen�o
                        *           se requisi��o normal
                        *               se existe uma p�gina de login configurada
                        *                   redirecionar para a p�gina de login
                        *               sen�o
                        *                   exibir p�gina de login default
                        *           sen�o
                        *               retornar xml contendo o status da requisi��o
                        **/
                        if(isRequisicaoAjax(httpRequest))
                        {
                            Map<String,String> mapaParametros = new HashMap<String,String>();
                            mapaParametros.put(CHAVE_RESPOSTA_AJAX_DNSEC_ACAO_LOGIN, CHAVE_RESPOSTA_AJAX_DNSEC_ACAO_LOGIN);
                            if(logger.isLoggable(Level.FINER))
                            {
                            	logger.finer("Dados do usu�rio n�o encontrados na sess�o e a��o de login n�o especificada (requisi��o ajax)...");
                            }
                        	response.setContentType("text/plain");
                            response.getOutputStream().print(getRespostaRequisicaoAjax(CHAVE_REQUEST_DNSEC_ACAO_LOGIN, CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO, mapaParametros));                                                    
                            response.getOutputStream().flush();
                            return;
                        }
                        else
                        {
                            if(caminhoPaginaLogin != null)
                            {
                                if(logger.isLoggable(Level.FINER))
                                {
                                    logger.finer("Redirecionando para a p�gina de login configurada no arquivo --> " + caminhoPaginaLogin);
                                }
                                httpRequest.getSession().getServletContext().getRequestDispatcher(caminhoPaginaLogin).forward(request, response);
                                return;
                            }else
                            {
                                if(logger.isLoggable(Level.FINER))
                                {
                                    logger.finer("Redirecionando para a p�gina de login default...");
                                }
                                httpRequest.getSession().getServletContext().getRequestDispatcher(PAGINA_LOGIN_DEFAULT).forward(request, response);
                                return;
                            }
                        }
                }
            }else
            {
                    /*      sen�o 
                    *           Se o recurso est� presente na lista de fun��es dos grupos
                    *           relacionados ao usu�rio
                    *               Se requisi��o normal
                    *                   processar o pr�ximo recurso
                    *               sen�o //Requisi��o AJAX
                    *                   retornar XML contendo o status da requisi��o
                    *           sen�o
                    *               Se requisi��o normal
                    *                   Se existe uma p�gina de acesso negado
                    *                       retornar para esta p�gina
                    *                   sen�o
                    *                       retornar mensagem default de acesso negado
                    *               sen�o //Requisi��o AJAX
                    *                   retornar XML com o status da requisi��o
                    */                   
                    String idRecurso = null; 
                    if(iExtratorIdRecurso != null)
                    {
                        if(logger.isLoggable(Level.FINER))
                        {
                            logger.finer("Passando requisi��o para o objeto  " + iExtratorIdRecurso.getClass().getName() + " para retornar o id do recurso");
                        }
                        idRecurso = iExtratorIdRecurso.getIdRecurso(httpRequest);
                    }else
                    {
                        if(logger.isLoggable(Level.FINER))
                        {
                            logger.finer("Utilizando m�todo default para obter o id do recurso");
                        }
                        idRecurso = httpRequest.getRequestURI();
                        if(idRecurso.lastIndexOf("/") != -1)
                        {
                            idRecurso = idRecurso.substring(idRecurso.lastIndexOf("/") + 1, idRecurso.length());
                        }
                        idRecurso = idRecurso.replaceAll("[.].*", "");
                    }
                    if(logger.isLoggable(Level.FINER))
                    {
                        logger.finer("id do recurso --> " + idRecurso);
                    }
                    if(!isRecursoAcessivel(httpRequest, httpResponse, idRecurso))
                    {
                            if(isRequisicaoAjax(httpRequest))
                            {
                            	Map<String,String> mapaParametros = new HashMap<String,String>();
                            	mapaParametros.put(CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_MENSAGEM, CHAVE_ARQUIVO_CONF_DNSEC_PAGINA_ACESSO_NEGADO);
                                String strResposta = getRespostaRequisicaoAjax(CHAVE_REQUEST_DNSEC_ACAO_LOGIN,CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO,mapaParametros);
                            	response.setContentType("text/plain");
                                response.getOutputStream().print(strResposta);                                                    
                                response.getOutputStream().flush();
                                return;
                            }else
                            {
                                if(caminhoPaginaAcessoNegado != null)
                                {
                                    if(logger.isLoggable(Level.FINER))
                                    {
                                        logger.finer("Redirecionando para a p�gina de acesso negado configurada no arquivo --> " + caminhoPaginaAcessoNegado);
                                    }
                                    httpRequest.getSession().getServletContext().getRequestDispatcher(caminhoPaginaAcessoNegado).forward(request, response);
                                    return;
                                }else
                                {
                                    if(logger.isLoggable(Level.FINER))
                                    {
                                        logger.finer("Redirecionando para a p�gina de acesso negado default...");
                                    }
                                    httpRequest.getSession().getServletContext().getRequestDispatcher(PAGINA_ACESSO_NEGADO_DEFAULT).forward(request, response);
                                    return;
                                }
                            }
                    }
            }
        }
        //processar o pr�ximo recurso
        chain.doFilter(request, response);
    }
   
    /**
     * Verifica se o recurso est� na lista de exclus�o do filtro. Por exemplo se 
     * o filtro for configurado para ser utilizado em qualquer padr�o de URL:
     * 
     * <filter-mapping>
     *      <filter-name>DNSECFiltro</filter-name>
     *      <url-pattern>/*</url-pattern>
     * </filter-mapping>
     * 
     * as requisi��es est�ticas tamb�m passar�o pelo filtro, como por exemplo requisi��es
     * para arquivos de folha de estilo (.css) ou arquivos javascript (.js)
     * Para que o filtro n�o tente validar estes tipos de arquivos � poss�vel adicionar
     * os tipos de requisi��es que devem ser ignoradas pelo filtro atrav�s do par�metro
     * de inicializa��o do filtro dnsec.web.urls.excluir.filtro que deve conter
     * a lista dos padr�es de url que ser�o desconsideradas pelo filtro
     * 
     * @since 0.1
     * */
    protected boolean isRecursoEstaNaListaDeExclusao(String idRecurso)
    {
        //TODO --> Bater com a especifica��o de servlets
        boolean blnRecursoEstaNaListaDeExclusao = false;
        if(logger.isLoggable(Level.FINER))
        {
            logger.finer("Verificando se o recurso " + idRecurso + " est� na lista de urls que devem ser desconsideradas");
        }
        if(strURLsExcluirFiltro == null)
        {
            strURLsExcluirFiltro = new String[0];        
        }
        
        for(String strPadraoAtual : strURLsExcluirFiltro)
        {
            if(logger.isLoggable(Level.FINER))
            {
                logger.finer("Verificando padr�o --> " + strPadraoAtual);
            }
            if("/*".equals(strPadraoAtual))
            {
                if(logger.isLoggable(Level.FINER))
                {
                    logger.finer("Padr�o global encontrado...");
                }
                logger.warning("Ativando o padr�o /* todas as requisi��es ser�o desconsideradas pelo filtro...");
                blnRecursoEstaNaListaDeExclusao = true;
                break;
            }else if(strPadraoAtual.indexOf("*.") != -1)
            {
                if(logger.isLoggable(Level.FINER))
                {
                    logger.finer("Padr�o extens�o encontrado...");
                }
                String strExtensao = strPadraoAtual.substring(strPadraoAtual.indexOf("*."), strPadraoAtual.length());
                if(logger.isLoggable(Level.FINER))
                {
                    logger.finer("Extens�o encontrado --> " + strExtensao);
                }
                if(idRecurso.endsWith(strExtensao))
                {
                    if(logger.isLoggable(Level.FINER))
                    {
                        logger.finer("recurso termina com a extens�o " + strExtensao);
                    }
                    blnRecursoEstaNaListaDeExclusao = true;
                    break;
                }
            }else
            {
                if(logger.isLoggable(Level.FINER))
                {
                    logger.finer("Verificando padr�o de diret�rio");
                }
                if(strPadraoAtual.endsWith("/*"))
                {
                    strPadraoAtual = strPadraoAtual.substring(0, strPadraoAtual.length() - 2);
                    if(idRecurso.indexOf(strPadraoAtual) != -1 || (idRecurso + "/").indexOf(strPadraoAtual) != -1)
                    {
                        blnRecursoEstaNaListaDeExclusao = true;
                        break;
                    }
                }else
                {
                    if(!strPadraoAtual.endsWith("/"))
                    {
                        strPadraoAtual += "/"; 
                    }
                    if(idRecurso.endsWith(strPadraoAtual) || (idRecurso + "/").endsWith(strPadraoAtual))
                    {
                        blnRecursoEstaNaListaDeExclusao = true;
                        break;
                    }
                }
           }
            
        }
        
        if(blnRecursoEstaNaListaDeExclusao)
        {
            if(logger.isLoggable(Level.FINER))
            {
                logger.finer("Recurso requerido est� na lista de exclus�o definida para o filtro, requisi��o n�o ser� verificada pelo filtro");
            }else
            {
                logger.finer("Recurso requerido n�o est� na lista de exclus�o definida para o filtro, requisi��o ser� verificada pelo filtro");
            }
        }
        return blnRecursoEstaNaListaDeExclusao;
        
    }
    
    /**
     * Verifica se a requisi��o � uma requisi��o normal ou se deve
     * ser tratada como uma requisi��o AJAX. Caso a requisi��o seja
     * do tipo AJAX, ser� retornado um XML contendo o status da opera��o
     * ao inv�s do redirecionamento que ocorre em requisi��es normais.
     * A requisi��o ajax pode ser configurada no arquivo web.xml indicando
     * que todas as requisi��es da aplica��o ser�o realizadas por meio
     * do ajax ou pode ser configurada por requisi��o, atrav�s do par�metro
     * dnsec.web.filtro.requisicao.ajax que deve estar presente na 
     * requisi��o, indicando que a mesma � uma requisi��o ajax.
     * Caso o par�metro dnsec.web.filtro.requisicao.default.ajax
     * esteja presente no arquivo de configura��o indicando que todas as
     * requisi��es s�o ajax � poss�vel configurar alguma requisi��o
     * dentro da aplica��o como sendo uma requisi��o normal,
     * adicionando o par�metro dnsec.web.filtro.requisicao.normal
     * como par�metro de request
     * @since 0.1
     * */
    protected boolean isRequisicaoAjax(HttpServletRequest request)
    {
        
            return (_filterConfig != null && _filterConfig.getInitParameter("CHAVE_ARQUIVO_CONF_DNSEC_REQ_DEFAULT_AJAX") != null && !(request.getParameter("CHAVE_REQUEST_CONF_DNSEC_REQ_NORMAL") != null))
                                                ||
                   (request.getParameter("CHAVE_REQUEST_DNSEC_REQUISICAO_AJAX") != null);
    }
    
    
    /**
     * M�todo respons�vel por realizar o login do usu�rio no sistema
     * */    
     protected DadosUsuarioBean login(String strUsuario, String strSenha, HttpServletRequest request, HttpServletResponse response) throws CommandException
     {
            //TODO --> Verificar caso em que o usu�rio j� est� conectado ao sistema
            DadosUsuarioBean dadosUsuarioBean = null;
            synchronized(request.getSession())
            {
                    BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_LOGIN);
                    baseDispatchCRUDCommand.setStrMetodo(CommandLogin.METODO_LOGIN);
                    Usuario usuario = new Usuario();
                    usuario.setCodUsuarioUsua(strUsuario.toUpperCase());
                    usuario.setCodSenhaUsua(strSenha);
                    Object args[] = new Object[]{usuario, sistemaId};
                    if(logger.isLoggable(Level.FINER))
                    {
                        logger.finer("Executando login...");
                    }
                    Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
                    ControleSegurancaVo controleSegurancaVo = new ControleSegurancaVo(
                            (Usuario)objRetorno[0],
                            (Map)objRetorno[1],
                            (Map)objRetorno[2],
                            (Map)objRetorno[3]
                    );
                    dadosUsuarioBean = new DadosUsuarioBean();
                    dadosUsuarioBean.adicionarAtributo("CONTROLE_SEGURANCA_VO", controleSegurancaVo);
            }
            return dadosUsuarioBean;
     }


	public boolean tratarMensagemErro(String operacao, HttpServletRequest request, HttpServletResponse response, CommandException commandException) throws ServletException, IOException {
		if (commandException.getCause() != null && !(commandException.getCause() instanceof CommandException)) {
			// Erro n�o esperado na aplica��o
			Throwable exception = commandException.getCause();
			exception.printStackTrace();
			CharArrayWriter arrayMsg = new CharArrayWriter();
			PrintWriter printWriter = new PrintWriter(arrayMsg);
			exception.printStackTrace(printWriter);
			logger.severe("Erro n�o esperado na aplica��o...");
			logger.throwing("Erro n�o esperado na aplica��o...", "tratarMensagemErro", exception);
			throw new ServletException(exception);
		} else {
			// Erro esperado na aplica��o
			if(logger.isLoggable(Level.FINER))
                        {
                            logger.finer("Erro esperado na aplica��o...");
                        }
			String strKeyMsg = StringUtil.NullToStr(commandException.getStrKeyConfigFile());
			Object[] argsMsg = commandException.getObjArgs();
			if (argsMsg == null) {
				argsMsg = new Object[0];
			}
			String msgErroEspecifico = "";
			if (!"".equals(strKeyMsg)) {
				msgErroEspecifico = RecursosUtil.getInstance().getResource(strKeyMsg, argsMsg);
			}
		}
		return true;
	}

	
	public void tratarMensagemErroAjax(CommandException commandException, Map<String,String> mapaParametros)
	{
		if (commandException.getCause() != null && !(commandException.getCause() instanceof CommandException)) {
			// Erro n�o esperado na aplica��o
			Throwable exception = commandException.getCause();
			exception.printStackTrace();
			CharArrayWriter arrayMsg = new CharArrayWriter();
			PrintWriter printWriter = new PrintWriter(arrayMsg);
			exception.printStackTrace(printWriter);
			logger.severe("Erro n�o esperado na aplica��o...");
        	mapaParametros.put(CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_MENSAGEM, commandException.getMessage());
        	mapaParametros.put(CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_STACK_TRACE, arrayMsg.toString());
		} else {
			// Erro esperado na aplica��o
			if(logger.isLoggable(Level.FINER))
                        {
                            logger.finer("Erro esperado na aplica��o...");
                        }
			String strKeyMsg = StringUtil.NullToStr(commandException.getStrKeyConfigFile());
			Object[] argsMsg = commandException.getObjArgs();
			if (argsMsg == null) {
				argsMsg = new Object[0];
			}
			String msgErroEspecifico = "";
			if (!"".equals(strKeyMsg)) {
				msgErroEspecifico = RecursosUtil.getInstance().getResource(strKeyMsg, argsMsg);
			}
        	mapaParametros.put(CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_MENSAGEM, msgErroEspecifico);
		}
	}
	
     
     /**
      * xml contendo a resposta para a requisi��o ajax
      * 
      * <requisicao-dnsec>
      *     <tipo-requisicao>
      *     </tipo-requisicao>
      *     <status-operacao>
      *     </status-operacao>
      *     <parametros-retorno>
      *         <nome-parametro></nome-parametro>
      *         <valor-parametro></valor-parametro>
      *     </parametros-retorno>
      *</requisicao-dnsec>
      * */
     protected String getRespostaRequisicaoAjax(String operacaoDnsec, String statusOperacao, Map<String, String> parametrosRetorno)
     {
        StringBuilder builder = new StringBuilder();
        builder.append("<requisicao-dnsec>");     
        builder.append("<tipo-requisicao>");
        builder.append("<![CDATA[");
        builder.append(operacaoDnsec);
        builder.append("]]>");
        builder.append("</tipo-requisicao>");
        builder.append("<status-operacao>");
        builder.append("<![CDATA[");
        builder.append(statusOperacao);
        builder.append("]]>");
        builder.append("</status-operacao>");
        builder.append("<parametros-retorno>");    
        Set chaves = parametrosRetorno.keySet();
        Iterator<String> itChaves = chaves.iterator();
        while(itChaves.hasNext())
        {
            String nomeParametro = itChaves.next();
            builder.append("<nome-parametro><![CDATA[" + nomeParametro + "]]></nome-parametro>");
            builder.append("<valor-parametro><![CDATA[" + parametrosRetorno.get(nomeParametro) + "]]></valor-parametro>");
        }
        builder.append("</parametros-retorno>");
        builder.append("</requisicao-dnsec>");     
        String strRetorno = builder.toString();
        if(logger.isLoggable(Level.FINER))
        {
            logger.finer("XML para retorno:\n");
            logger.finer(strRetorno);
        }
        return strRetorno;
     }
     

    /**
     * Verifica se o recurso est� na lista de recursos relacionados com algum dos grupos
     * do usu�rio indicando que o usu�rio possui acesso ao recurso
     * 
     * */
    public boolean isRecursoAcessivel(HttpServletRequest request, HttpServletResponse response, String idRecurso) throws ServletException
    {
		boolean continuarProcessamento = true;
		if(idRecurso == null)
		{
			throw new ServletException("O nome da fun��o n�o foi especificado para verifica��o de acesso!");
		}
		if(logger.isLoggable(Level.FINER))
        {
            logger.finer("Verificando o acesso do usu�rio � fun��o --> " + idRecurso);
        }
		DadosUsuarioBean dadosUsuarioBean = (DadosUsuarioBean) request.getSession().getAttribute(CHAVE_SESSAO_DNSEC_DADOS_USUARIO);
		ControleSegurancaVo controleSegurancaVo = (ControleSegurancaVo) dadosUsuarioBean.retornarValorAtributo("CONTROLE_SEGURANCA_VO");
		Funcao funcao = (Funcao) controleSegurancaVo.getMapaFuncoesUsuario().get(idRecurso);
		if( funcao == null)
		{
			if(logger.isLoggable(Level.FINER))
			{
			    logger.finer("Usu�rio n�o possui acesso � fun��o --> " + idRecurso);
			}
			continuarProcessamento = false;
		}else
		{
			if(request.getParameter(CHAVE_REQUEST_DNSEC_ACESSO_FUNCAO_TRAVAR_FUNCAO_PAI) != null)
			{
				if(funcao.getFuncaoPai() != null &&  controleSegurancaVo.getMapaFuncoesUsuario().get(funcao.getFuncaoPai().getNomeFunc()) == null )
				{
					if(logger.isLoggable(Level.FINER))
					{
						logger.finer("Usu�rio tem acesso � fun��o mas n�o tem acesso � fun��o pai... Fun��o --> " + funcao.getNomeFunc() + " Fun��o pai --> " + funcao.getFuncaoPai().getNomeFunc());
					}
					continuarProcessamento = false;
				} 									
			}
			if(logger.isLoggable(Level.FINER))
			{
				logger.finer("Usu�rio possui acesso � fun��o --> " + idRecurso);
			}
		}
		return continuarProcessamento;
    }


        protected void enviarXMLComoTexto(String xml, HttpServletResponse response, HttpServletRequest request) throws IOException
	{
		response.setCharacterEncoding(request.getCharacterEncoding());
		response.setContentType("text/plain");
		response.getOutputStream().print(xml);
		response.getOutputStream().flush();	
	}




    
}
