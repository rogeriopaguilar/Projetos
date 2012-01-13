package dnsec.shared.database.hibernate;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;



public class Grupo  implements Serializable{

	private String codGrupoGrup;
	private String descrGrupoGrup;
	
	private Set usuarios;
	private Set sistemas;
	private Set funcoes;

	
	public Set getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(Set funcoes) {
		this.funcoes = funcoes;
	}

	public Set getSistemas() {
		return sistemas;
	}

	public void setSistemas(Set sistemas) {
		this.sistemas = sistemas;
	}

	public Set getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set usuarios) {
		this.usuarios = usuarios;
	}

	public String getCodGrupoGrup() {
		return codGrupoGrup;
	}
	
	public void setCodGrupoGrup(String codGrupoGrup) {
		this.codGrupoGrup = codGrupoGrup;
	}
	
	public String getDescrGrupoGrup() {
		return descrGrupoGrup;
	}
	
	public void setDescrGrupoGrup(String descrGrupoGrup) {
		this.descrGrupoGrup = descrGrupoGrup;
	}

	public Grupo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Grupo(String codGrupoGrup, String descrGrupoGrup) {
		super();
		// TODO Auto-generated constructor stub
		this.codGrupoGrup = codGrupoGrup;
		this.descrGrupoGrup = descrGrupoGrup;
	}


	public boolean equals(Object other) {
	    if ( !(other instanceof Grupo) ) return false;
	    Grupo castOther = (Grupo) other;
	    return new EqualsBuilder()
	        .append(this.getCodGrupoGrup(), castOther.getCodGrupoGrup())
	        .isEquals();
	}

	public int hashCode() {
	    return getCodGrupoGrup() != null ?
	    new HashCodeBuilder()
	        .append("" + getCodGrupoGrup())
	        .toHashCode()
	        : 1;
	}


	
}

