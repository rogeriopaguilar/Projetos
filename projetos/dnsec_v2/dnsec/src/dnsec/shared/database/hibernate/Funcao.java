package dnsec.shared.database.hibernate;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;

import dnsec.shared.database.hibernate.pk.FuncaoPk;

public class Funcao implements Serializable{

	private FuncaoPk compId = new FuncaoPk();
	private String nomeFunc;
	private String descrFuncaoFunc;
	private String codSistemaPaiFunc;
	private Long codFuncaoPaiFunc;
	private Sistema sistema;
	
	private Funcao funcaoPai;
	private Set funcoesFilhas;
	private Set grupos;

	

	public Set getGrupos() {
		return grupos;
	}

	public void setGrupos(Set grupos) {
		this.grupos = grupos;
	}

	public Set getFuncoesFilhas() {
		return funcoesFilhas;
	}

	public void setFuncoesFilhas(Set funcoesFilhas) {
		this.funcoesFilhas = funcoesFilhas;
	}

	public Funcao getFuncaoPai() {
		return funcaoPai;
	}
	
	public void setFuncaoPai(Funcao funcaoPai) {
		this.funcaoPai = funcaoPai;
	}
	
	public Funcao(FuncaoPk funcaoPk, String nomeFunc, String descrFuncaoFunc, String codSistemaPaiFunc, Long codFuncaoPaiFunc) {
		super();
		// TODO Auto-generated constructor stub
		this.compId = funcaoPk;
		this.nomeFunc = nomeFunc;
		this.descrFuncaoFunc = descrFuncaoFunc;
		this.codSistemaPaiFunc = codSistemaPaiFunc;
		this.codFuncaoPaiFunc = codFuncaoPaiFunc;
	}
	public Funcao() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getCodFuncaoPaiFunc() {
		return codFuncaoPaiFunc;
	}
	public void setCodFuncaoPaiFunc(Long codFuncaoPaiFunc) {
		this.codFuncaoPaiFunc = codFuncaoPaiFunc;
	}
	public String getCodSistemaPaiFunc() {
		return codSistemaPaiFunc;
	}
	public void setCodSistemaPaiFunc(String codSistemaPaiFunc) {
		this.codSistemaPaiFunc = codSistemaPaiFunc;
	}
	public String getDescrFuncaoFunc() {
		return descrFuncaoFunc;
	}
	public void setDescrFuncaoFunc(String descrFuncaoFunc) {
		this.descrFuncaoFunc = descrFuncaoFunc;
	}
	public String getNomeFunc() {
		return nomeFunc;
	}
	public void setNomeFunc(String nomeFunc) {
		this.nomeFunc = nomeFunc;
	}

	public FuncaoPk getCompId() {
		return compId;
	}
	
	public void setCompId(FuncaoPk compId) {
		this.compId = compId;
	}
	
    public boolean equals(Object other) {
        if ( !(other instanceof Funcao) ) return false;
        Funcao castOther = (Funcao) other;
        return new EqualsBuilder()
            .append(this.getCompId(), castOther.getCompId())
            .isEquals();
    }

    public int hashCode() {
        return this.getCompId().hashCode();
    }
	public Sistema getSistema() {
		return sistema;
	}
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
	
	
}
