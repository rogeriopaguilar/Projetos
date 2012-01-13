package dnsec.web.app.jsf.bean.associacos.nos;

import java.io.Serializable;

import dnsec.shared.database.hibernate.Grupo;


public class GrupoNo implements Serializable{
	private String tipo = TIPO_NO_FILHO;
	public static final String TIPO_NO_RAIZ = "NO_RAIZ";
	public static final String TIPO_NO_FILHO = "NO_FILHO";
	public static final String TIPO_NO_ATUALIZACAO = "NO_ATUALIZACAO";
	
	private Grupo grupo;

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
}
