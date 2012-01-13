package dnsec.web.app.jsf.bean.associacos.nos;

import java.io.Serializable;

import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.database.hibernate.Grupo;
import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.database.hibernate.Usuario;


public class AssociacaoGrupoNo implements Serializable{
	private String tipo = TIPO_NO_FILHO;
	private String tipoObjetoNo = null;
	public static final String TIPO_NO_RAIZ = "NO_RAIZ";
	public static final String TIPO_NO_FILHO = "NO_FILHO";
	public static final String TIPO_NO_ATUALIZACAO = "NO_ATUALIZACAO";
	public static final String TIPO_OBJETO_NO_SISTEMA = "SISTEMA";
	public static final String TIPO_OBJETO_NO_GRUPO   = "GRUPO";
	public static final String TIPO_OBJETO_NO_USUARIO = "USUARIO";
	public static final String TIPO_OBJETO_NO_FUNCAO  = "FUNCAO";
	
	private Sistema sistema = null;
	private Grupo grupo = null;
	private Usuario usuario = null;
	private Funcao funcao = null;
	
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
		setTipo(TIPO_OBJETO_NO_GRUPO);
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		setTipo(TIPO_OBJETO_NO_USUARIO);
	}
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
		setTipo(TIPO_OBJETO_NO_SISTEMA);
	}
	
	public String toString(){
		if(sistema != null) return sistema.getCodSistemaSist();
		return "Sistemas";
		
	}
	public String getTipoObjetoNo() {
		return tipoObjetoNo;
	}
	public void setTipoObjetoNo(String tipoObjetoNo) {
		this.tipoObjetoNo = tipoObjetoNo;
	}
	public Funcao getFuncao() {
		return funcao;
	}
	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
		setTipoObjetoNo(TIPO_OBJETO_NO_FUNCAO);
	}
	
}
