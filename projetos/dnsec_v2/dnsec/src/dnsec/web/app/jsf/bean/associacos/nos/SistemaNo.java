package dnsec.web.app.jsf.bean.associacos.nos;

import java.io.Serializable;

import dnsec.shared.database.hibernate.Sistema;


public class SistemaNo implements Serializable{
	private String tipo = TIPO_NO_FILHO;
	public static final String TIPO_NO_RAIZ = "NO_RAIZ";
	public static final String TIPO_NO_FILHO = "NO_FILHO";
	public static final String TIPO_NO_ATUALIZACAO = "NO_ATUALIZACAO";
	private Sistema sistema = null;
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Sistema getSistema() {
		return sistema;
	}
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
	
	public String toString(){
		if(sistema != null) return sistema.getCodSistemaSist();
		return "Sistemas";
		
	}
	
}
