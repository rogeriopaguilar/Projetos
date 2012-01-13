package dnsec.web.app.jsf.bean.associacos.nos;

import java.io.Serializable;

import dnsec.shared.database.hibernate.Usuario;


public class UsuarioNo implements Serializable{
	private String tipo = TIPO_NO_FILHO;
	public static final String TIPO_NO_RAIZ = "NO_RAIZ";
	public static final String TIPO_NO_FILHO = "NO_FILHO";
	public static final String TIPO_NO_ATUALIZACAO = "NO_ATUALIZACAO";
	
	private Usuario usuario;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
