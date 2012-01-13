package dnsec.web.app.jsf.bean.associacos.nos;

import java.io.Serializable;

import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.database.hibernate.Sistema;


public class FuncaoNo implements Serializable{
	private String tipo = TIPO_NO_FILHO;
	public static final String TIPO_NO_RAIZ = "NO_RAIZ";
	public static final String TIPO_NO_FILHO = "NO_FILHO";
	public static final String TIPO_NO_ATUALIZACAO = "NO_ATUALIZACAO";
	public static final String TIPO_NO_SISTEMA = "NO_SISTEMA";
	private Funcao funcao;
	private Sistema sistema;
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Funcao getFuncao() {
		return funcao;
	}
	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
	public Sistema getSistema() {
		return sistema;
	}
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
	

	
}
