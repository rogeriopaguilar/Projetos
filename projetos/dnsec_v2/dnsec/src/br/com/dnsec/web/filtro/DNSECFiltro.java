

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

    //Parâmetro que pode vir em alguma requisição indicando que a mesma é uma requisição AJAX
    public static final String CHAVE_REQUEST_DNSEC_REQUISICAO_AJAX = "dnsec.web.filtro.requisicao.ajax";        
    //Parâmetro que pode vir na requisição indicando que a mesma deve ser processada como uma requisição normal (não ajax) 
    public static final String CHAVE_REQUEST_CONF_DNSEC_REQ_NORMAL = "dnsec.web.filtro.requisicao.normal";
    //Parâmetro de inicialização do filtro que indica que todas as requisições devem ser tratadas como requisiçõesAJAX
    public static final String CHAVE_ARQUIVO_CONF_DNSEC_REQ_DEFAULT_AJAX = "dnsec.web.filtro.requisicao.default.ajax";

    //Parâmetro de inicialização do filtro que indica as urls que devem passar sem verificação do filtro
    public static final String CHAVE_ARQUIVO_CONF_DNSEC_URLS_EXCLUIR_FILTRO = "dnsec.web.filtro.urls.excluir.filtro";

    //Chave para o objeto da sessão que contém os dados do usuário conectado
    public static final String CHAVE_SESSAO_DNSEC_DADOS_USUARIO = "dnsec.web.filtro.sessao.dados.usuario";

    //Chave para parâmetro de requisiçao que deve estar presente indicando que a requisiçao e uma requisiçao para efetuar login
    public static final String CHAVE_REQUEST_DNSEC_ACAO_LOGIN = "dnsec.web.filtro.requisicao.login";
    //Chave para parâmetro de request que deve conter o código do usuário que deseja realizar login no sistema
    public static final String CHAVE_REQUEST_DNSEC_ACAO_LOGIN_USUARIO = "dnsec.web.filtro.requisicao.login.usuario";
    //Chave para parâmetro de request que deve possuir a senha do usuário que deseja realizar login no sistema
    public static final String CHAVE_REQUEST_DNSEC_ACAO_LOGIN_SENHA = "dnsec.web.filtro.requisicao.login.senha";
    //Chave para resposta de requisição de login ajax indicando que deve ser exibida a página de login
    public static final String CHAVE_RESPOSTA_AJAX_DNSEC_ACAO_LOGIN = "dnsec.web.filtro.requisicao.login.resposta.xml.ajax.redirecionar.login";


    //Chaves para os status possíveis para as operações requisitadas via ajax
    public static final String CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_SUCESSO = "dnsec.web.filtro.acao.status.SUCESSO";
    public static final String CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO = "dnsec.web.filtro.acao.status.ERRO";
    public static final String CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_MENSAGEM = "dnsec.web.filtro.acao.status.erro.mensagem";
    public static final String CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_DESC = "dnsec.web.filtro.acao.status.erro.descricao";
    public static final String CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_STACK_TRACE = "dnsec.web.filtro.acao.status.erro.stack.trace";

    //Chave indicando que a requisição de verificação de acesso a um recurso deve verificar também a função pai
    public static final String CHAVE_REQUEST_DNSEC_ACESSO_FUNCAO_TRAVAR_FUNCAO_PAI = "dnsec.web.filtro.pagina.acesso.travar.funcao.pai";    
    
    /*Chave para o caminho completo de uma classe que pode estar no arquivo de configuração responsável por implementar que id de recurso
     * está relacionado a uma determinada url. Por default o filtro retira a última parte da URL, a extensão (se presente) e utiliza esta parte
     * da url como id do recurso que deve ser consultado
     * */
    public static final String CHAVE_ARQUIVO_CONF_DNSEC_CLASSE_EXTRACAO_ID_RECURSO = "dnsec.web.filtro.classe.extracao.id.recurso";
    
    //Chave para entrada no arquivo de configuração que pode estar presente indicando a página de login no sistema
    public static final String CHAVE_ARQUIVO_CONF_DNSEC_PAGINA_LOGIN = "dnsec.web.filtro.pagina.login";
    //Chave para entrada no arquivo de configuração que pode estar presente indicando a página de acesso negado a um determinado recurso
    public static final String CHAVE_ARQUIVO_CONF_DNSEC_PAGINA_ACESSO_NEGADO = "dnsec.web.filtro.pagina.acesso.negado";

    //Código do sistema que será utilizado no para pesquisa no cadastro de segurança
    public static String  CHAVE_ARQUIVO_CONF_DNSEC_SISTEMA_ID = "dnsec.web.filtro.sistema.id";
	


    //Caminhos "default" para páginas de login e de acesso negado
    protected String PAGINA_LOGIN_DEFAULT = "/paginas/login_default.html";
    protected String PAGINA_ACESSO_NEGADO_DEFAULT = "/paginas/acesso_negado_default.html";
    

    protected String caminhoPaginaAcessoNegado = null;
    protected String caminhoPaginaLogin = null;
    protected String classeIdRecurso = null;
    protected IExtratorIdRecurso iExtratorIdRecurso = null;
    protected String sistemaId = null;
    //Sem problemas, pois ostream é thread safe!
    protected XStream xstream = new XStream();

    private String[] strURLsExcluirFiltro = new String[0];

    private FilterConfig _filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        _filterConfig = filterConfig;

        if(logger.isLoggable(Level.FINER))
        {
            logger.finer("Inicializando filtro DNSEC");
        }
        //Verifica se existe o código do sistema que deve estar configurado para o cadastro de segurança
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
                logger.severe("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_SISTEMA_ID + " não existe no arquivo de configuração");
            }
            throw new ServletException("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_SISTEMA_ID + " não existe no arquivo de configuração. Sem ela não é possível consistir os dados no sistema de segurança!");
        }    

        //Verifica se existe entrada indicando lista de urls que devem ser desconsideradas pelo filtro    
        if(logger.isLoggable(Level.FINER))
        {
            logger.finer("Verificando existência da entrada " + CHAVE_ARQUIVO_CONF_DNSEC_URLS_EXCLUIR_FILTRO + " no arquivo de configuração");
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
                logger.finer("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_URLS_EXCLUIR_FILTRO + " não existe no arquivo de configuração");
            }
        }    

        //Verifica se existe uma página de login configurada no arquivo web.xml
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
                logger.finer("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_PAGINA_LOGIN + " não existe no arquivo de configuração");
            }
        }    

        //Verifica se existe uma página de acesso negado configurada no arquivo de inicialização
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
                logger.finer("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_PAGINA_ACESSO_NEGADO + " não existe no arquivo de configuração");
            }
        }    


        //Verifica se existe uma classe configurada para extrair o id do recurso no sistema de segurança
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
                logger.finer("Entrada " + CHAVE_ARQUIVO_CONF_DNSEC_CLASSE_EXTRACAO_ID_RECURSO + " não existe no arquivo de configuração");
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
    *  Se o recurso não está na lista de exclusão para o filtro então
    *      Se não existe objeto com os dados do usuário na sessão
    *         se requisição é uma requisição para realizar o login
    *            validar usuário e senha
    *            se usuário e senha válidos
    *               adicionar dados do usuário na sessão
    *               se requisição normal
    *                   processar recurso
    *               senão// requisição AJAX
    *                   retornar XML contendo os dados da operação
    *            senão
    *               adicionar mensagem de erro de login na requisição
    *               se requisição normal
    *                   retornar para a tela de login
    *               senão
    *                   retornar XML contendo o status da operação
    *         senão
    *           se requisição normal
    *               se existe uma página de login configurada
    *                   redirecionar para a página de login
    *               senão
    *                   exibir página de login default
    *           senão
    *               retornar xml contendo o status da requisição
    *      senão 
    *           Se o recurso está presente na lista de funções dos grupos
    *           relacionados ao usuário
    *               Se requisição normal
    *                   processar o próximo recurso
    *               senão //Requisição AJAX
    *                   retornar XML contendo o status da requisição
    *           senão
    *               Se requisição normal
    *                   Se existe uma página de acesso negado
    *                       retornar para esta página
    *                   senão
    *                       retornar mensagem default de acesso negado
    *               senão //Requisição AJAX
    *                   retornar XML com o status da requisição
    *  senão
    *      processar o próximo recurso
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
            logger.finest("*****************Parâmetros da requisição*****************");
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
        //Verifica se o recurso é um recurso protegido
        //Se o recurso não está na lista de exclusão para o filtro então
        if(!isRecursoEstaNaListaDeExclusao(strIdRecurso))
        {
            //Se não existe objeto com os dados do usuário na sessão
            if( session.getAttribute(CHAVE_SESSAO_DNSEC_DADOS_USUARIO) == null)
            {
                if( request.getParameter(CHAVE_REQUEST_DNSEC_ACAO_LOGIN_USUARIO) != null)
                {
                    if(logger.isLoggable(Level.FINER))
                    {
                        logger.finer("Usuário solicitou login no sistema");
                    }
                   /*
                    *         se requisição é uma requisição para realizar o login
                    *            validar usuário e senha
                    *            se usuário e senha válidos
                    *               adicionar dados do usuário na sessão
                    *               se requisição normal
                    *                   processar recurso
                    *               senão// requisição AJAX
                    *                   retornar XML contendo os dados da operação
                    *            senão
                    *               adicionar mensagem de erro de login na requisição
                    *               se requisição normal
                    *                   retornar para a tela de login
                    *               senão
                    *                   retornar XML contendo o status da operação
                    */
    
                    String strUsuario = request.getParameter(CHAVE_REQUEST_DNSEC_ACAO_LOGIN_USUARIO);
                    String strSenha = request.getParameter(CHAVE_REQUEST_DNSEC_ACAO_LOGIN_SENHA);


                    if(logger.isLoggable(Level.FINER))
                    {
                        logger.finer("Parâmetros para login:\n");
                        logger.finer(CHAVE_REQUEST_DNSEC_ACAO_LOGIN_USUARIO + " --> " + strUsuario);
                        logger.finer(CHAVE_REQUEST_DNSEC_ACAO_LOGIN_SENHA + " --> " + strSenha);
                    }
                    
                    if(strUsuario == null)
                    {
                        if(isRequisicaoAjax(httpRequest))
                        {
                        	Map<String,String> mapaParametros = new HashMap<String,String>();
                        	mapaParametros.put(CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_MENSAGEM, "Parâmetro " + CHAVE_REQUEST_DNSEC_ACAO_LOGIN_USUARIO + " não está presenta na requisição!");
                            String strResposta = getRespostaRequisicaoAjax(CHAVE_REQUEST_DNSEC_ACAO_LOGIN,CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO,mapaParametros);
                        	response.setContentType("text/plain");
                            response.getOutputStream().print(strResposta);                                                    
                            response.getOutputStream().flush();
                            return;
                        }else{
                            ServletException ex = new ServletException("Parâmetro " + CHAVE_REQUEST_DNSEC_ACAO_LOGIN_USUARIO + " não está presenta na requisição!");
                            logger.throwing(getClass().getName(), "doFilter", ex);
                            throw ex;
                        }
                    }
                    if(strSenha == null)
                    {
                        if(isRequisicaoAjax(httpRequest))
                        {
                        	Map<String,String> mapaParametros = new HashMap<String,String>();
                        	mapaParametros.put(CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_MENSAGEM, "Parâmetro " + CHAVE_REQUEST_DNSEC_ACAO_LOGIN_SENHA + " não está presenta na requisição!");
                            String strResposta = getRespostaRequisicaoAjax(CHAVE_REQUEST_DNSEC_ACAO_LOGIN,CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO,mapaParametros);
                        	response.setContentType("text/plain");
                            response.getOutputStream().print(strResposta);                                                    
                            response.getOutputStream().flush();
                        }else{
                            ServletException ex = new ServletException("Parâmetro " + CHAVE_REQUEST_DNSEC_ACAO_LOGIN_SENHA + " não está presenta na requisição!");
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
                       /*         senão
                        *           se requisição normal
                        *               se existe uma página de login configurada
                        *                   redirecionar para a página de login
                        *               senão
                        *                   exibir página de login default
                        *           senão
                        *               retornar xml contendo o status da requisição
                        **/
                        if(isRequisicaoAjax(httpRequest))
                        {
                            Map<String,String> mapaParametros = new HashMap<String,String>();
                            mapaParametros.put(CHAVE_RESPOSTA_AJAX_DNSEC_ACAO_LOGIN, CHAVE_RESPOSTA_AJAX_DNSEC_ACAO_LOGIN);
                            if(logger.isLoggable(Level.FINER))
                            {
                            	logger.finer("Dados do usuário não encontrados na sessão e ação de login não especificada (requisição ajax)...");
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
                                    logger.finer("Redirecionando para a página de login configurada no arquivo --> " + caminhoPaginaLogin);
                                }
                                httpRequest.getSession().getServletContext().getRequestDispatcher(caminhoPaginaLogin).forward(request, response);
                                return;
                            }else
                            {
                                if(logger.isLoggable(Level.FINER))
                                {
                                    logger.finer("Redirecionando para a página de login default...");
                                }
                                httpRequest.getSession().getServletContext().getRequestDispatcher(PAGINA_LOGIN_DEFAULT).forward(request, response);
                                return;
                            }
                        }
                }
            }else
            {
                    /*      senão 
                    *           Se o recurso está presente na lista de funções dos grupos
                    *           relacionados ao usuário
                    *               Se requisição normal
                    *                   processar o próximo recurso
                    *               senão //Requisição AJAX
                    *                   retornar XML contendo o status da requisição
                    *           senão
                    *               Se requisição normal
                    *                   Se existe uma página de acesso negado
                    *                       retornar para esta página
                    *                   senão
                    *                       retornar mensagem default de acesso negado
                    *               senão //Requisição AJAX
                    *                   retornar XML com o status da requisição
                    */                   
                    String idRecurso = null; 
                    if(iExtratorIdRecurso != null)
                    {
                        if(logger.isLoggable(Level.FINER))
                        {
                            logger.finer("Passando requisição para o objeto  " + iExtratorIdRecurso.getClass().getName() + " para retornar o id do recurso");
                        }
                        idRecurso = iExtratorIdRecurso.getIdRecurso(httpRequest);
                    }else
                    {
                        if(logger.isLoggable(Level.FINER))
                        {
                            logger.finer("Utilizando método default para obter o id do recurso");
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
                                        logger.finer("Redirecionando para a página de acesso negado configurada no arquivo --> " + caminhoPaginaAcessoNegado);
                                    }
                                    httpRequest.getSession().getServletContext().getRequestDispatcher(caminhoPaginaAcessoNegado).forward(request, response);
                                    return;
                                }else
                                {
                                    if(logger.isLoggable(Level.FINER))
                                    {
                                        logger.finer("Redirecionando para a página de acesso negado default...");
                                    }
                                    httpRequest.getSession().getServletContext().getRequestDispatcher(PAGINA_ACESSO_NEGADO_DEFAULT).forward(request, response);
                                    return;
                                }
                            }
                    }
            }
        }
        //processar o próximo recurso
        chain.doFilter(request, response);
    }
   
    /**
     * Verifica se o recurso está na lista de exclusão do filtro. Por exemplo se 
     * o filtro for configurado para ser utilizado em qualquer padrão de URL:
     * 
     * <filter-mapping>
     *      <filter-name>DNSECFiltro</filter-name>
     *      <url-pattern>/*</url-pattern>
     * </filter-mapping>
     * 
     * as requisições estáticas também passarão pelo filtro, como por exemplo requisições
     * para arquivos de folha de estilo (.css) ou arquivos javascript (.js)
     * Para que o filtro não tente validar estes tipos de arquivos é possível adicionar
     * os tipos de requisições que devem ser ignoradas pelo filtro através do parâmetro
     * de inicialização do filtro dnsec.web.urls.excluir.filtro que deve conter
     * a lista dos padrões de url que serão desconsideradas pelo filtro
     * 
     * @since 0.1
     * */
    protected boolean isRecursoEstaNaListaDeExclusao(String idRecurso)
    {
        //TODO --> Bater com a especificação de servlets
        boolean blnRecursoEstaNaListaDeExclusao = false;
        if(logger.isLoggable(Level.FINER))
        {
            logger.finer("Verificando se o recurso " + idRecurso + " está na lista de urls que devem ser desconsideradas");
        }
        if(strURLsExcluirFiltro == null)
        {
            strURLsExcluirFiltro = new String[0];        
        }
        
        for(String strPadraoAtual : strURLsExcluirFiltro)
        {
            if(logger.isLoggable(Level.FINER))
            {
                logger.finer("Verificando padrão --> " + strPadraoAtual);
            }
            if("/*".equals(strPadraoAtual))
            {
                if(logger.isLoggable(Level.FINER))
                {
                    logger.finer("Padrão global encontrado...");
                }
                logger.warning("Ativando o padrão /* todas as requisições serão desconsideradas pelo filtro...");
                blnRecursoEstaNaListaDeExclusao = true;
                break;
            }else if(strPadraoAtual.indexOf("*.") != -1)
            {
                if(logger.isLoggable(Level.FINER))
                {
                    logger.finer("Padrão extensão encontrado...");
                }
                String strExtensao = strPadraoAtual.substring(strPadraoAtual.indexOf("*."), strPadraoAtual.length());
                if(logger.isLoggable(Level.FINER))
                {
                    logger.finer("Extensão encontrado --> " + strExtensao);
                }
                if(idRecurso.endsWith(strExtensao))
                {
                    if(logger.isLoggable(Level.FINER))
                    {
                        logger.finer("recurso termina com a extensão " + strExtensao);
                    }
                    blnRecursoEstaNaListaDeExclusao = true;
                    break;
                }
            }else
            {
                if(logger.isLoggable(Level.FINER))
                {
                    logger.finer("Verificando padrão de diretório");
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
                logger.finer("Recurso requerido está na lista de exclusão definida para o filtro, requisição não será verificada pelo filtro");
            }else
            {
                logger.finer("Recurso requerido não está na lista de exclusão definida para o filtro, requisição será verificada pelo filtro");
            }
        }
        return blnRecursoEstaNaListaDeExclusao;
        
    }
    
    /**
     * Verifica se a requisição é uma requisição normal ou se deve
     * ser tratada como uma requisição AJAX. Caso a requisição seja
     * do tipo AJAX, será retornado um XML contendo o status da operação
     * ao invés do redirecionamento que ocorre em requisições normais.
     * A requisição ajax pode ser configurada no arquivo web.xml indicando
     * que todas as requisições da aplicação serão realizadas por meio
     * do ajax ou pode ser configurada por requisição, através do parâmetro
     * dnsec.web.filtro.requisicao.ajax que deve estar presente na 
     * requisição, indicando que a mesma é uma requisição ajax.
     * Caso o parâmetro dnsec.web.filtro.requisicao.default.ajax
     * esteja presente no arquivo de configuração indicando que todas as
     * requisições são ajax é possível configurar alguma requisição
     * dentro da aplicação como sendo uma requisição normal,
     * adicionando o parâmetro dnsec.web.filtro.requisicao.normal
     * como parâmetro de request
     * @since 0.1
     * */
    protected boolean isRequisicaoAjax(HttpServletRequest request)
    {
        
            return (_filterConfig != null && _filterConfig.getInitParameter("CHAVE_ARQUIVO_CONF_DNSEC_REQ_DEFAULT_AJAX") != null && !(request.getParameter("CHAVE_REQUEST_CONF_DNSEC_REQ_NORMAL") != null))
                                                ||
                   (request.getParameter("CHAVE_REQUEST_DNSEC_REQUISICAO_AJAX") != null);
    }
    
    
    /**
     * Método responsável por realizar o login do usuário no sistema
     * */    
     protected DadosUsuarioBean login(String strUsuario, String strSenha, HttpServletRequest request, HttpServletResponse response) throws CommandException
     {
            //TODO --> Verificar caso em que o usuário já está conectado ao sistema
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
			// Erro não esperado na aplicação
			Throwable exception = commandException.getCause();
			exception.printStackTrace();
			CharArrayWriter arrayMsg = new CharArrayWriter();
			PrintWriter printWriter = new PrintWriter(arrayMsg);
			exception.printStackTrace(printWriter);
			logger.severe("Erro não esperado na aplicação...");
			logger.throwing("Erro não esperado na aplicação...", "tratarMensagemErro", exception);
			throw new ServletException(exception);
		} else {
			// Erro esperado na aplicação
			if(logger.isLoggable(Level.FINER))
                        {
                            logger.finer("Erro esperado na aplicação...");
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
			// Erro não esperado na aplicação
			Throwable exception = commandException.getCause();
			exception.printStackTrace();
			CharArrayWriter arrayMsg = new CharArrayWriter();
			PrintWriter printWriter = new PrintWriter(arrayMsg);
			exception.printStackTrace(printWriter);
			logger.severe("Erro não esperado na aplicação...");
        	mapaParametros.put(CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_MENSAGEM, commandException.getMessage());
        	mapaParametros.put(CHAVE_REQUEST_DNSEC_STATUS_OPERACAO_ERRO_STACK_TRACE, arrayMsg.toString());
		} else {
			// Erro esperado na aplicação
			if(logger.isLoggable(Level.FINER))
                        {
                            logger.finer("Erro esperado na aplicação...");
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
      * xml contendo a resposta para a requisição ajax
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
     * Verifica se o recurso está na lista de recursos relacionados com algum dos grupos
     * do usuário indicando que o usuário possui acesso ao recurso
     * 
     * */
    public boolean isRecursoAcessivel(HttpServletRequest request, HttpServletResponse response, String idRecurso) throws ServletException
    {
		boolean continuarProcessamento = true;
		if(idRecurso == null)
		{
			throw new ServletException("O nome da função não foi especificado para verificação de acesso!");
		}
		if(logger.isLoggable(Level.FINER))
        {
            logger.finer("Verificando o acesso do usuário à função --> " + idRecurso);
        }
		DadosUsuarioBean dadosUsuarioBean = (DadosUsuarioBean) request.getSession().getAttribute(CHAVE_SESSAO_DNSEC_DADOS_USUARIO);
		ControleSegurancaVo controleSegurancaVo = (ControleSegurancaVo) dadosUsuarioBean.retornarValorAtributo("CONTROLE_SEGURANCA_VO");
		Funcao funcao = (Funcao) controleSegurancaVo.getMapaFuncoesUsuario().get(idRecurso);
		if( funcao == null)
		{
			if(logger.isLoggable(Level.FINER))
			{
			    logger.finer("Usuário não possui acesso à função --> " + idRecurso);
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
						logger.finer("Usuário tem acesso à função mas não tem acesso à função pai... Função --> " + funcao.getNomeFunc() + " Função pai --> " + funcao.getFuncaoPai().getNomeFunc());
					}
					continuarProcessamento = false;
				} 									
			}
			if(logger.isLoggable(Level.FINER))
			{
				logger.finer("Usuário possui acesso à função --> " + idRecurso);
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
