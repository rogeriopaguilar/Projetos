package dnsec.web.vo;

import java.io.Serializable;

public class ConfigSegurancaVo implements Serializable {

	public static final String URL_LOGIN_DEFAULT = "/login.jsp";
	private static final String URL_INICIO_APLICACAO_DEFAULT = "/index.jsp";
	private boolean utilizarAjaxComoDefault = false;
	
	
	private String URL_LOGIN = "/login.jsp";
	private String URL_INICIO_APLICACAO = "/index.jsp";
	private String SISTEMA_ID = "";
	private String URL_LOGOFF = URL_LOGIN;
	private  String URL_ACESSO_NEGADO = URL_INICIO_APLICACAO;
	private String URL_SESSAO_EXPIRADA = URL_LOGIN;

	public String getURL_ACESSO_NEGADO() {
		return URL_ACESSO_NEGADO;
	}

	public void setURL_ACESSO_NEGADO(String url_acesso_negado) {
		URL_ACESSO_NEGADO = url_acesso_negado;
	}

	public String getURL_SESSAO_EXPIRADA() {
		return URL_SESSAO_EXPIRADA;
	}

	public void setURL_SESSAO_EXPIRADA(String url_sessao_expirada) {
		URL_SESSAO_EXPIRADA = url_sessao_expirada;
	}

	public String getURL_LOGOFF() {
		return URL_LOGOFF;
	}

	public void setURL_LOGOFF(String url_logoff) {
		URL_LOGOFF = url_logoff;
	}

	public String getSISTEMA_ID() {
		return SISTEMA_ID;
	}
	
	public void setSISTEMA_ID(String sistema_id) {
		SISTEMA_ID = sistema_id;
	}
	
	public String getURL_LOGIN() {
		return URL_LOGIN;
	}
	
	public void setURL_LOGIN(String url_login) {
		URL_LOGIN = url_login;
	}

	public String getURL_INICIO_APLICACAO() {
		return URL_INICIO_APLICACAO;
	}

	public void setURL_INICIO_APLICACAO(String url_inicio_aplicacao) {
		URL_INICIO_APLICACAO = url_inicio_aplicacao;
	}

	public boolean isUtilizarAjaxComoDefault() {
		return utilizarAjaxComoDefault;
	}

	public void setUtilizarAjaxComoDefault(boolean utilizarAjaxComoDefault) {
		this.utilizarAjaxComoDefault = utilizarAjaxComoDefault;
	}
	
}
