package dnsec.web.servlet;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

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

public class ServletSeguranca extends HttpServlet{
	
	//Utilizada para guardar na sessão um objeto Usuario representado os dados do usuário conectado ao sistema	
	public static String CHAVE_LOGIN_USUARIO = "dnsec.shared.database.hibernate.Usuario";
	//Após passar pela verificação, um atributo é colocado no objeto request indicando que a requisição passou pelo Servlet
	public static String CHAVE_REQUISICAO_PASSOU_PELO_Servlet = "dnsec.web.filter.login.ServletSeguranca";
	public static String  CHAVE_CONFIG_URL_LOGIN = "URL_LOGIN";
	public static String  CHAVE_CONFIG_SISTEMA_ID = "SISTEMA_ID";
	//Chave para o vo que guarda as configurações de segurança no contexto da aplicação
	public static String CHAVE_CONFIG_SEGURANCA_VO = "dnsec.web.vo.ConfigSegurancaVo";
	public static String  CHAVE_CONFIG_URL_LOGOFF= "URL_LOGOFF";
	public static String CHAVE_CONFIG_URL_INICIO_APLICACAO = "URL_INICIO_APLICACAO";	
	public static String  CHAVE_CONFIG_URL_ACESSO_NEGADO = "URL_ACESSO_NEGADO";
	public static String  CHAVE_CONFIG_URL_SESSAO_EXPIRADA = "URL_SESSAO_EXPIRADA";
	public static String  CHAVE_CONFIG_UTILIZAR_AJAX_COMO_DEFAULT = "UTILIZAR_AJAX_COMO_DEFAULT";
	
	static Logger logger = Logger.getLogger(ServletSeguranca.class.getName());
	
	public static final String DNSEC_REQUISICAO_AJAX = "DNSEC_REQUISICAO_AJAX";
	public static final String DNSEC_REQUISICAO_COMUM = "DNSEC_REQUISICAO_COMUM";
	public static final String DNSEC_OPERACAO_USUARIO_ESTA_CONECTADO = "VERIFICAR_SE_USUARIO_ESTA_CONECTADO";
	public static final String DNSEC_OPERACAO_EFETUAR_LOGIN = "EFETUAR_LOGIN";
	public static final String DNSEC_OPERACAO_EFETUAR_LOGOFF = "EFETUAR_LOGOFF";
	public static final String DNSEC_OPERACAO_STATUS_SUCESSO = "SUCESSO";
	public static final String DNSEC_OPERACAO_STATUS_ERRO_ESPERADO = "ERRO_ESPERADO";
	public static final String DNSEC_OPERACAO_STATUS_ERRO_NAO_ESPERADO = "ERRO_NAO_ESPERADO";

	public static final String DNSEC_OPERACAO_VERIFICAR_ACESSO_FUNCAO = "VERIFICAR_ACESSO_FUNCAO";
	public static final String DNSEC_OPERACAO_VERIFICAR_ACESSO_FUNCAO_TRAVAR_FUNCAO_PAI = "TRAVAR_FUNCAO_PAI";
	public static final String DNSEC_OPERACAO_VERIFICAR_ACESSO_FUNCAO_ID = "NOME_FUNCAO";

	public static final String DNSEC_ERRO_SESSAO_EXPIRADA = "ERRO_SESSAO_EXPIRADA";
	
	
	//Chave para mensagens de erro esperadas qeu são retornadas para o usuário
	public static final String CHAVE_MENSAGEM_ERRO_ESPERADO = "dnsec.erro.esperado";
	
	public static final String CHAVE_REQUISICAO_PASSOU_PELA_SEGURANCA = "CHAVE_REQUISICAO_PASSOU_PELA_SEGURANCA";

	
	/******************Servlet**********************/
	public static final String ACAO_LOGIN  = "DNSEC_LOGIN";
	public static final String ACAO_LOGOFF = "DNSEC_LOGOFF";
	public static final String CAMPO_LOGIN = "DNSEC_LOGIN";
	public static final String CAMPO_SENHA = "DNSEC_SENHA";
	public static final String ACAO_RECUPERAR_DADOS_USUARIO = "DNSEC_RECUPERAR_DADOS_USUARIO";
	//parâmetro que deve vir no request indicando a ação para o sevlet de segurança
	public static final String CHAVE_ACAO_LOGIN = "DNSEC_CHAVE_ACAO";
	

	//Sem problemas, pois ostream é thread safe!
	protected static  XStream xstream = new XStream();

	
	static{
		
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
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{


		boolean continuarProcessamento = true;

		
		String acao = request.getParameter(CHAVE_ACAO_LOGIN);
		if(request.getSession(false) == null && (acao == null  || (acao != null && !ACAO_LOGOFF.equals(acao) && !ACAO_LOGIN.equals(acao))))
		{
			if(request.getCookies() != null)
			{
				for(Cookie cookie : request.getCookies())
				{
					if(cookie != null && CHAVE_LOGIN_USUARIO.equals(cookie.getName())){
						logger.debug("Sessão expirada!");
						logger.debug("Apagando cookie de login...");
						cookie.setMaxAge(0);
						response.addCookie(cookie);
						break;
					}
				}
			}
			
			/*if(encontrouCookieLogin)
			{*/
				String msgErroEspecifico = RecursosUtil.getInstance().getResource("key.erro.sessao.expirada", new Object[0]);
				if(!isRequisicaoAjax(request))
				{
					//Requisição comum
					ConfigSegurancaVo configSegurancaVo = (ConfigSegurancaVo) ((HttpServletRequest)request).getSession().getServletContext().getAttribute(ServletSeguranca.CHAVE_CONFIG_SEGURANCA_VO);			
					request.setAttribute(ServletSeguranca.CHAVE_MENSAGEM_ERRO_ESPERADO, msgErroEspecifico);
					((HttpServletRequest)request).getSession().getServletContext().getRequestDispatcher(configSegurancaVo.getURL_SESSAO_EXPIRADA()).forward(request, response);
					
				}else{
					//Requisicao AJAX
					String xml = ServletSeguranca.montarXMLStatusOperacao(ServletSeguranca.DNSEC_ERRO_SESSAO_EXPIRADA, ServletSeguranca.DNSEC_ERRO_SESSAO_EXPIRADA, msgErroEspecifico);
					logger.debug("Requisição AJAX... Retronando xml: " + xml);
					enviarXMLComoTexto(xml, response, request);
					continuarProcessamento = false;
				}
			//}
		}

		
		synchronized (request.getSession()) {
			ConfigSegurancaVo configSegurancaoVo = (ConfigSegurancaVo) request.getSession().getServletContext().getAttribute(CHAVE_CONFIG_SEGURANCA_VO);			
			if(configSegurancaoVo == null)
			{
				logger.fatal("Vo contendo os dados de segurança não localizado no contexto da aplicação!");
				throw new ServletException("Vo contendo os dados de segurança não localizado no contexto da aplicação!");
			}

			/********************************************Servlet*****************************************************************/
			if(acao != null)
			{
					ConfigSegurancaVo configSegurancaVo = (ConfigSegurancaVo) request.getSession().getServletContext().getAttribute(ServletSeguranca.CHAVE_CONFIG_SEGURANCA_VO);			
					if(ACAO_LOGIN.equals(acao))
					{
						logger.debug("Requisição para efetuar login na aplicação");
						//if(true)throw new ServletException("TESTE ERRO"); --> Teste configuração erro
						synchronized(request.getSession())
						{
							try
							{
								request.getSession().removeAttribute(CHAVE_LOGIN_USUARIO);
								if(request.getParameter(CAMPO_LOGIN) == null)
								{
									logger.fatal("Usuário requisitou login, porém não enviou o parâmetro " + CAMPO_LOGIN);
									throw new ServletException("Usuário requisitou login, porém não enviou o parâmetro " + CAMPO_LOGIN);
								}
								if(request.getParameter(CAMPO_SENHA) == null)
								{
									logger.fatal("Usuário requisitou login, porém não enviou o parâmetro " + CAMPO_SENHA);
									throw new ServletException("Usuário requisitou login, porém não enviou o parâmetro " + CAMPO_SENHA);
								}
								logger.debug("Parâmetros " + CAMPO_LOGIN + " e " + CAMPO_SENHA + " encontados na requisição ");
								BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_LOGIN);
								baseDispatchCRUDCommand.setStrMetodo(CommandLogin.METODO_LOGIN);
								Usuario usuario = new Usuario();
								usuario.setCodUsuarioUsua(request.getParameter(CAMPO_LOGIN).toUpperCase());
								usuario.setCodSenhaUsua(request.getParameter(CAMPO_SENHA));
								Object args[] = new Object[]{usuario, configSegurancaVo.getSISTEMA_ID()};
								logger.debug("Executando login...");
								Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
								ControleSegurancaVo controleSegurancaVo = new ControleSegurancaVo(
									(Usuario)objRetorno[0],
									(Map)objRetorno[1],
									(Map)objRetorno[2],
									(Map)objRetorno[3]
								);
								
								
								controleSegurancaVo.getUsuarioConectado().setCodSenhaUsua("");
								request.getSession().setAttribute(ServletSeguranca.CHAVE_LOGIN_USUARIO, controleSegurancaVo);
								Cookie cookieLogin = new Cookie(CHAVE_LOGIN_USUARIO, CHAVE_LOGIN_USUARIO);
								logger.debug("Adicionando cookie de login --> " + cookieLogin);
								response.addCookie(cookieLogin);

								logger.debug("Login executado com sucesso...");
								if(!isRequisicaoAjax(request))
								{
									//Requisição comum
									if(request.getSession().getAttribute(CHAVE_CONFIG_URL_INICIO_APLICACAO) != null)
									{	
										logger.debug("Usuário conectado ao sistema...");
										/**
										 * Verifica se a requisição original possuía 
										 * instruções para verificar alguma regra de acesso à segurança
										 * de alguma função
										 * */
										HttpServletRequestWrapper wrapperRequest = new HttpServletRequestWrapper(request)
										{
										    protected Map parameters = null;
										    private boolean parsedParams = false;
										    private String queryParamString = null;

											public String getParameter(String id)
											{
												if(DNSEC_OPERACAO_VERIFICAR_ACESSO_FUNCAO.equals(id))
												{
													return ServletSeguranca.getParameter(
															"" + ((HttpServletRequest)getRequest()).getSession().getAttribute(CHAVE_CONFIG_URL_INICIO_APLICACAO),															
															DNSEC_OPERACAO_VERIFICAR_ACESSO_FUNCAO);	
												}else if(DNSEC_OPERACAO_VERIFICAR_ACESSO_FUNCAO_TRAVAR_FUNCAO_PAI.equals(id)){
													return ServletSeguranca.getParameter(
															"" + ((HttpServletRequest)getRequest()).getSession().getAttribute(CHAVE_CONFIG_URL_INICIO_APLICACAO),															
															DNSEC_OPERACAO_VERIFICAR_ACESSO_FUNCAO_TRAVAR_FUNCAO_PAI);	
												}
												return  super.getParameter(id);
											}
											
										    
										};
										continuarProcessamento = verificarAcessoAFuncao(wrapperRequest, response, configSegurancaVo);
										if(continuarProcessamento)
										{
											logger.debug("Enviando para requisição original --> " + request.getSession().getAttribute(CHAVE_CONFIG_URL_INICIO_APLICACAO) );
											request.getSession().getServletContext().getRequestDispatcher("" + request.getSession().getAttribute(CHAVE_CONFIG_URL_INICIO_APLICACAO) ).forward(request, response);
											request.getSession().removeAttribute(CHAVE_CONFIG_URL_INICIO_APLICACAO); 
										}
									}else
									{
										logger.debug("Enviando para requisição default configurada no arquivo --> " + configSegurancaVo.getURL_INICIO_APLICACAO());
										request.getSession().getServletContext().getRequestDispatcher(configSegurancaVo.getURL_INICIO_APLICACAO()).forward(request, response);
									}

									continuarProcessamento = false;
								}else
								{
									//Requisicao AJAX
									String xml = ServletSeguranca.montarXMLStatusOperacao(ServletSeguranca.DNSEC_OPERACAO_EFETUAR_LOGIN, ServletSeguranca.DNSEC_OPERACAO_STATUS_SUCESSO, "");
									logger.debug("Requisição AJAX... Retronando xml: " + xml);
									enviarXMLComoTexto(xml, response, request);
									continuarProcessamento = false;
								}
								
							}catch(CommandException commandException){
								logger.warn("Erro ao executar operação de login!",commandException);
								tratarMensagemErro(ServletSeguranca.DNSEC_OPERACAO_EFETUAR_LOGIN, request, response, commandException);
								continuarProcessamento = false;
							}
						}
					}else if(ACAO_LOGOFF.equals(acao)){
						logger.debug("Realizando logoff do usuário...");
						request.getSession().invalidate();
						if(request.getCookies() != null)
						{
							for(Cookie cookie : request.getCookies())
							{
								if(cookie != null && CHAVE_LOGIN_USUARIO.equals(cookie.getName())){
									logger.debug("Apagando cookie de login...");
									cookie.setMaxAge(0);
									response.addCookie(cookie);
									break;
								}
							}
						}
						
						if(!isRequisicaoAjax(request))
						{
							//Requisição comum
							request.getSession().getServletContext().getRequestDispatcher(configSegurancaVo.getURL_LOGOFF()).forward(request, response);
							continuarProcessamento = false;
						}else{
							//Requisicao AJAX
							String xml = ServletSeguranca.montarXMLStatusOperacao(ServletSeguranca.DNSEC_OPERACAO_EFETUAR_LOGOFF, ServletSeguranca.DNSEC_OPERACAO_STATUS_SUCESSO, "");
							logger.debug("Requisição AJAX... Retronando xml: " + xml);
							enviarXMLComoTexto(xml, response, request);
							continuarProcessamento = false;
						}
		
					}else if(ACAO_RECUPERAR_DADOS_USUARIO.equals(acao)){
						ControleSegurancaVo controleSegurancaVo = (ControleSegurancaVo) request.getSession().getAttribute(ServletSeguranca.CHAVE_LOGIN_USUARIO);
						StringBuilder builder = new StringBuilder();
						builder.append("<dadosusuario>");
						
						builder.append(xstream.toXML(controleSegurancaVo.getUsuarioConectado()));
						
						builder.append("<sistemas>");
						Iterator itSistemas = controleSegurancaVo.getMapaSistemasUsuario().values().iterator();

						while(itSistemas.hasNext())
						{	
							Sistema sistema = (Sistema) itSistemas.next();
							builder.append(xstream.toXML(sistema));
						}
						builder.append("</sistemas>");

						builder.append("<grupos>");
						Iterator itGrupos = controleSegurancaVo.getMapaGruposUsuario().values().iterator();
						
						while(itGrupos.hasNext())
						{	
							Grupo grupo = (Grupo) itGrupos.next();
							builder.append(xstream.toXML(grupo));
						}
						builder.append("</grupos>");

						
						builder.append("<funcoes>");
						Iterator itFuncoes = controleSegurancaVo.getMapaFuncoesUsuario().values().iterator();
						
						while(itFuncoes.hasNext())
						{	
							Funcao funcao = (Funcao) itFuncoes.next();
							builder.append(xstream.toXML(funcao));
						}
						builder.append("</funcoes>");
						builder.append("</dadosusuario>");
						
						logger.debug("Requisição AJAX... Retronando xml: " + builder);
						enviarXMLComoTexto(builder.toString(), response, request);
						continuarProcessamento = false;
						builder.delete(0, builder.length());
						builder = null;
					}else if(DNSEC_OPERACAO_VERIFICAR_ACESSO_FUNCAO.equals(acao))
					{
						logger.debug("Verificando direito de acesso...");
						continuarProcessamento = verificarAcessoAFuncao(request,  response, configSegurancaVo);
					}
			}else{
					/********************************************************************************************************************/
					if(request.getSession().getAttribute(CHAVE_LOGIN_USUARIO) == null)
					{
						if(request.getCookies() != null)
						{
							for(Cookie cookie : request.getCookies())
							{
								if(cookie != null && CHAVE_LOGIN_USUARIO.equals(cookie.getName())){
									logger.debug("Apagando cookie de login...");
									cookie.setMaxAge(0);
									response.addCookie(cookie);
									break;
								}
							}
						}
						continuarProcessamento = false;
						/* Usuário não está conectado ao sistema
						 * Redireciona para a página de login ou monta xml
						 */
						logger.debug("Usuário não conectado ao sistema...");
						//if(request.getParameter(DNSEC_AJAX_LOGIN) == null){ //&& !configSegurancaoVo.isUtilizarAjaxComoDefault()){
						if(!isRequisicaoAjax(request))
						{
							//Requisição HTTP comum
							logger.debug("Requisição comum... Redirecionando para " + configSegurancaoVo.getURL_LOGIN());
							logger.debug("Usuário requisitou --> " + request.getContextPath() + request.getServletPath() + "?" + request.getQueryString());
							request.getSession().removeAttribute(CHAVE_CONFIG_URL_INICIO_APLICACAO);
							if(!"/".equals(request.getServletPath()) && !"".equals(request.getServletPath()) && !(null == request.getServletPath()) )
							{
								request.getSession().setAttribute(CHAVE_CONFIG_URL_INICIO_APLICACAO, request.getServletPath() + "?" + request.getQueryString());
							}
							
							request.getSession().getServletContext().getRequestDispatcher(configSegurancaoVo.getURL_LOGIN()).forward(request, response);
						}else{
							//Requisiçaõ AJAX
							String xml = montarXMLStatusOperacao(DNSEC_OPERACAO_USUARIO_ESTA_CONECTADO, DNSEC_OPERACAO_STATUS_SUCESSO, "NAO_CONECTADO");
							logger.debug("Requisição AJAX... Retronando xml: " + xml);
							enviarXMLComoTexto(xml, response, request);
						}
					}
			}
		}
		if(continuarProcessamento)
		{
			//chain.doFilter(request, response);
			if(request.getParameter("PROXIMA_PAGINA") != null)
			{
				String proximaPagina = request.getParameter("PROXIMA_PAGINA");
				logger.debug("Redirecionando o usuário para a próxima página --> " + proximaPagina);
				request.getSession().getServletContext().getRequestDispatcher(proximaPagina).forward(request, response);
			}else{
				logger.warn("Parâmetro de request PROXIMA_PAGINA não especificado!");
			}
		}
		request.setAttribute(CHAVE_REQUISICAO_PASSOU_PELA_SEGURANCA, true);		
		logger.debug("SERVLET PATH --> " + request.getServletPath());

	}

	public static String montarXMLStatusOperacao(String operacao, String status, String mensagem){
		StringBuilder builder = new StringBuilder();
		String separador = System.getProperty("line.separator");
		//builder.append(separador + "<?xml version=\"1.0\"?>");
		builder.append(separador + "<operacaodnsec>");
		builder.append(separador + "<operacao>" + operacao + "</operacao>");
		builder.append(separador + "<statusoperacao>" + status + "</statusoperacao>");
		builder.append(separador + "<msgoperacao><![CDATA[" + mensagem + "]]></msgoperacao>");
		builder.append(separador + "</operacaodnsec>");
		return builder.toString();
	}
	
	protected boolean verificarAcessoAFuncao(HttpServletRequest request, HttpServletResponse response, ConfigSegurancaVo configSegurancaVo) throws ServletException, IOException{
		boolean continuarProcessamento = true;
		String nomeFuncao = request.getParameter(DNSEC_OPERACAO_VERIFICAR_ACESSO_FUNCAO_ID);
		if(nomeFuncao == null)
		{
			throw new ServletException("O nome da função não foi especificado para verificação de acesso!");
		}
		logger.debug("Verificando o acesso do usuário à função --> " + nomeFuncao);
		ControleSegurancaVo controleSegurancaVo = (ControleSegurancaVo) request.getSession().getAttribute(ServletSeguranca.CHAVE_LOGIN_USUARIO);
		Funcao funcao = (Funcao) controleSegurancaVo.getMapaFuncoesUsuario().get(nomeFuncao);
		if( funcao == null)
		{
			logger.debug("Usuário não possui acesso à função --> " + nomeFuncao);
			//Usuário não tem acesso à função
			if(!isRequisicaoAjax(request))
			{
				//Requisição comum
				if(configSegurancaVo.getURL_INICIO_APLICACAO().equals(configSegurancaVo.getURL_ACESSO_NEGADO()))
				{
					String msgErroEspecifico = RecursosUtil.getInstance().getResource("key.erro.acesso.negado", new Object[0]);
					request.setAttribute(ServletSeguranca.CHAVE_MENSAGEM_ERRO_ESPERADO, msgErroEspecifico);
					request.setAttribute(nomeFuncao, "BLOQUEADA");
					request.setAttribute("FUNCAO_BLOQUEADA", nomeFuncao);
				}
				logger.debug("Verificando o acesso do usuário à função --> " + nomeFuncao + " . Redirecionando para --> " + configSegurancaVo.getURL_ACESSO_NEGADO());
				request.getSession().getServletContext().getRequestDispatcher(configSegurancaVo.getURL_ACESSO_NEGADO()).forward(request, response);
				continuarProcessamento = false;
			}else
			{
				//Requisicao AJAX
				String xml = ServletSeguranca.montarXMLStatusOperacao(ServletSeguranca.DNSEC_OPERACAO_VERIFICAR_ACESSO_FUNCAO, ServletSeguranca.DNSEC_OPERACAO_STATUS_ERRO_ESPERADO, "BLOQUEADA");
				logger.debug("Requisição AJAX... Retronando xml: " + xml);
				enviarXMLComoTexto(xml, response, request);
				continuarProcessamento = false;
			}
		}else
		{
			if(request.getParameter(DNSEC_OPERACAO_VERIFICAR_ACESSO_FUNCAO_TRAVAR_FUNCAO_PAI) != null)
			{
				if(funcao.getFuncaoPai() != null &&  controleSegurancaVo.getMapaFuncoesUsuario().get(funcao.getFuncaoPai().getNomeFunc()) == null )
				{
					logger.debug("Usuário tem acesso à função mas não tem acesso à função pai... Função --> " + funcao.getNomeFunc() + " Função pai --> " + funcao.getFuncaoPai().getNomeFunc());
					//Usuário não tem acesso à função
					if(!isRequisicaoAjax(request))
					{
						//Requisição comum
						if(configSegurancaVo.getURL_INICIO_APLICACAO().equals(configSegurancaVo.getURL_ACESSO_NEGADO()))
						{
							String msgErroEspecifico = RecursosUtil.getInstance().getResource("key.erro.acesso.negado", new Object[0]);
							request.setAttribute(ServletSeguranca.CHAVE_MENSAGEM_ERRO_ESPERADO, msgErroEspecifico);
						}
						logger.debug("Verificando o acesso do usuário à função --> " + nomeFuncao + " . Redirecionando para --> " + configSegurancaVo.getURL_ACESSO_NEGADO());
						request.getSession().getServletContext().getRequestDispatcher(configSegurancaVo.getURL_ACESSO_NEGADO()).forward(request, response);
						continuarProcessamento = false;
					}else
					{
						//Requisicao AJAX
						String xml = ServletSeguranca.montarXMLStatusOperacao(ServletSeguranca.DNSEC_OPERACAO_VERIFICAR_ACESSO_FUNCAO, ServletSeguranca.DNSEC_OPERACAO_STATUS_ERRO_ESPERADO, "FUNCAO_PAI_BLOQUEADA");
						logger.debug("Requisição AJAX... Retronando xml: " + xml);
						enviarXMLComoTexto(xml, response, request);
						continuarProcessamento = false;
					}
				} 									
			}
			logger.debug("Usuário possui acesso à função --> " + nomeFuncao);
			request.setAttribute("FUNCAO_LIBERADA", nomeFuncao);
			//Usuário tem acesso à função
			if(isRequisicaoAjax(request))
			{
				//Requisicao AJAX
				String xml = ServletSeguranca.montarXMLStatusOperacao(ServletSeguranca.DNSEC_OPERACAO_VERIFICAR_ACESSO_FUNCAO, ServletSeguranca.DNSEC_OPERACAO_STATUS_SUCESSO, "LIBERADA");
				logger.debug("Requisição AJAX... Retronando xml: " + xml);
				enviarXMLComoTexto(xml, response, request);
				continuarProcessamento = false;
			}
		}
		return continuarProcessamento;
	}

	protected boolean isRequisicaoAjax(HttpServletRequest request)
	{
		return 
		(
				(request.getParameter(ServletSeguranca.DNSEC_REQUISICAO_AJAX) != null)
					||
				((ConfigSegurancaVo) request.getSession().getServletContext().getAttribute(CHAVE_CONFIG_SEGURANCA_VO)).isUtilizarAjaxComoDefault()
		) && 
		(request.getParameter(ServletSeguranca.DNSEC_REQUISICAO_COMUM) == null)
		
		;
	}

	
	protected void enviarXMLComoTexto(String xml, HttpServletResponse response, HttpServletRequest request) throws IOException
	{
		response.setCharacterEncoding(request.getCharacterEncoding());
		response.setContentType("text/plain");
		response.getOutputStream().print(xml);
		response.getOutputStream().flush();	
	}
	
	/*protected void enviarXML(String xml, HttpServletResponse response, HttpServletRequest request) throws IOException
	{
		response.setCharacterEncoding(request.getCharacterEncoding());
		response.setContentType("text/xml");
		response.getOutputStream().print(xml);
		response.getOutputStream().flush();	
	}*/

	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		logger.info("Inicializando Servlet de segurança DNSEC-v0.2");
		synchronized(config.getServletContext())
		{
			if(config.getServletContext().getAttribute(CHAVE_CONFIG_SEGURANCA_VO) == null)
			{
				ConfigSegurancaVo configSegurancaVo = new ConfigSegurancaVo();
				if(config.getInitParameter(CHAVE_CONFIG_URL_LOGIN) != null){
					configSegurancaVo.setURL_LOGIN(config.getInitParameter(CHAVE_CONFIG_URL_LOGIN));
					logger.info("Servlet de segurança DNSEC-v0.2 --> URL para login especificada como "  + configSegurancaVo.getURL_LOGIN());
				}else{
					logger.info("Servlet de segurança DNSEC-v0.2 --> URL para login especificada como "  + configSegurancaVo.getURL_LOGIN() + " (default)");
				}
				if(config.getInitParameter(ServletSeguranca.CHAVE_CONFIG_URL_LOGOFF) != null){
					configSegurancaVo.setURL_LOGOFF(config.getInitParameter(ServletSeguranca.CHAVE_CONFIG_URL_LOGOFF));
					logger.info("Servlet de segurança DNSEC-v0.2 --> URL para logoff especificada como "  + configSegurancaVo.getURL_LOGOFF());
				}else{
					logger.info("Servlet de segurança DNSEC-v0.2 --> URL para logoff especificada como "  + configSegurancaVo.getURL_LOGOFF() + " (default)");
				}
				if(config.getInitParameter(ServletSeguranca.CHAVE_CONFIG_URL_INICIO_APLICACAO) != null){
					configSegurancaVo.setURL_INICIO_APLICACAO(config.getInitParameter(ServletSeguranca.CHAVE_CONFIG_URL_INICIO_APLICACAO));
					logger.info("Servlet de segurança DNSEC-v0.2 --> URL para início da aplicação especificada como "  + configSegurancaVo.getURL_INICIO_APLICACAO());
				}else{
					logger.info("Servlet de segurança DNSEC-v0.2 --> URL para início da aplicação especificada como "  + configSegurancaVo.getURL_INICIO_APLICACAO() + " (default) ");
				}

				if(config.getInitParameter(ServletSeguranca.CHAVE_CONFIG_URL_ACESSO_NEGADO) != null){
					configSegurancaVo.setURL_ACESSO_NEGADO(config.getInitParameter(ServletSeguranca.CHAVE_CONFIG_URL_ACESSO_NEGADO));
					logger.info("Servlet de segurança DNSEC-v0.2 --> URL para redirecionamento em caso de acesso negado "  + configSegurancaVo.getURL_ACESSO_NEGADO());
				}else{
					logger.info("Servlet de segurança DNSEC-v0.2 --> URL para redirecionamento em caso de acesso negado "  + configSegurancaVo.getURL_ACESSO_NEGADO() + " (default) ");
				}

				if(config.getInitParameter(ServletSeguranca.CHAVE_CONFIG_URL_SESSAO_EXPIRADA) != null){
					configSegurancaVo.setURL_SESSAO_EXPIRADA(config.getInitParameter(ServletSeguranca.CHAVE_CONFIG_URL_SESSAO_EXPIRADA));
					logger.info("Servlet de segurança DNSEC-v0.2 --> URL para redirecionamento em caso de sessão expirada "  + configSegurancaVo.getURL_SESSAO_EXPIRADA());
				}else{
					logger.info("Servlet de segurança DNSEC-v0.2 --> URL para redirecionamento em caso de sessão expirada "  + configSegurancaVo.getURL_SESSAO_EXPIRADA() + " (default) ");
				}
				
				if(config.getInitParameter(ServletSeguranca.CHAVE_CONFIG_UTILIZAR_AJAX_COMO_DEFAULT) != null){
					configSegurancaVo.setUtilizarAjaxComoDefault(true);
					logger.info("Servlet de segurança DNSEC-v0.2 --> configurado para utilizar ajax como default (todas as requisições)");
				}else{
					configSegurancaVo.setUtilizarAjaxComoDefault(false);
					logger.info("Servlet de segurança DNSEC-v0.2 --> NÃO configurado para utilizar ajax como default");
				}
				
				
				if(config.getInitParameter(CHAVE_CONFIG_SISTEMA_ID) == null){
					logger.fatal("Parâmetro SISTEMA_ID não encontrado nos parâmetros de inicialização do Servlet.");
					throw new ServletException("Parâmetro SISTEMA_ID não encontrado nos parâmetros de inicialização do Servlet.");
				}else{
					configSegurancaVo.setSISTEMA_ID(config.getInitParameter(CHAVE_CONFIG_SISTEMA_ID));
					logger.info("Parâmetro SISTEMA_ID encontrado nos parâmetros de inicialização do Servlet. SISTEMA_ID --> " + configSegurancaVo.getSISTEMA_ID());
				}
				


				
				config.getServletContext().setAttribute(ServletSeguranca.CHAVE_CONFIG_SEGURANCA_VO, configSegurancaVo);				
			}
		}
		logger.info("Inicialização do Servlet de segurança DNSEC-v0.2 realizada com sucesso...");
	}

	
	
	public void tratarMensagemErro(String operacao, HttpServletRequest request, HttpServletResponse response, CommandException commandException) throws ServletException, IOException {
		if (commandException.getCause() != null && !(commandException.getCause() instanceof CommandException)) {
			// Erro não esperado na aplicação
			Throwable exception = commandException.getCause();
			exception.printStackTrace();
			CharArrayWriter arrayMsg = new CharArrayWriter();
			PrintWriter printWriter = new PrintWriter(arrayMsg);
			exception.printStackTrace(printWriter);
			String msgErroEspecifico = arrayMsg.toString();
			logger.fatal("Erro não esperado na aplicação...", exception);
			throw new ServletException(exception);
		} else {
			// Erro esperado na aplicação
			logger.debug("Erro esperado na aplicação...");
			String strKeyMsg = StringUtil.NullToStr(commandException.getStrKeyConfigFile());
			Object[] argsMsg = commandException.getObjArgs();
			if (argsMsg == null) {
				argsMsg = new Object[0];
			}
			String msgErroEspecifico = "";
			if (!"".equals(strKeyMsg)) {
				msgErroEspecifico = RecursosUtil.getInstance().getResource(strKeyMsg, argsMsg);
			}
			
			if(ServletSeguranca.DNSEC_OPERACAO_EFETUAR_LOGIN.equals(operacao)){
				//Erro durante o processo de login. Redireciona para a url de login ou monta o xml
				if(!isRequisicaoAjax(request))
				{
					//Requisição comum
					ConfigSegurancaVo configSegurancaVo = (ConfigSegurancaVo) ((HttpServletRequest)request).getSession().getServletContext().getAttribute(ServletSeguranca.CHAVE_CONFIG_SEGURANCA_VO);			
					request.setAttribute(ServletSeguranca.CHAVE_MENSAGEM_ERRO_ESPERADO, msgErroEspecifico);
					((HttpServletRequest)request).getSession().getServletContext().getRequestDispatcher(configSegurancaVo.getURL_LOGIN()).forward(request, response);
				}else{
					//Requisicao AJAX
					String xml = ServletSeguranca.montarXMLStatusOperacao(ServletSeguranca.DNSEC_OPERACAO_EFETUAR_LOGIN, ServletSeguranca.DNSEC_OPERACAO_STATUS_ERRO_ESPERADO, msgErroEspecifico);
					logger.debug("Requisição AJAX... Retronando xml: " + xml);
					response.setContentType("text/plain");
					response.getOutputStream().print(xml);
					response.getOutputStream().flush();	
				}
			}
			
		}
	}

	
	public static String getParameter(String URL, String nomeParametro){
		if(URL == null || nomeParametro == null)
		{
			return null;
		}
		if(URL.indexOf("?" + nomeParametro) == -1 && URL.indexOf("&" + nomeParametro) == -1)
		{
			return null;
		}
		int indiceInicial = -1;
		if(URL.indexOf("?" + nomeParametro + "=") == -1 && URL.indexOf("&" + nomeParametro + "=") == -1)
		{
			return null;
		}else
		{
			indiceInicial = URL.indexOf("?" + nomeParametro + "=");
			if(indiceInicial == -1)
			{
				indiceInicial = URL.indexOf("&" + nomeParametro + "=");
			}
			indiceInicial = indiceInicial + (nomeParametro + "").length() + 2;
		}

		int indiceFinal = URL.substring(indiceInicial, URL.length()).indexOf('&') ;
		if(indiceFinal == -1)
		{
			indiceFinal = URL.length();
		}else{
			indiceFinal += indiceInicial;
		}
		return URL.substring(indiceInicial, indiceFinal);
	}

	
	
}
