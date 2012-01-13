package dnsec.shared.database.hibernate.pk;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class FuncaoPk implements Serializable{

	private Long codFuncaoFunc;
	private String codSistemaSist;
	
	public FuncaoPk() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getCodFuncaoFunc() {
		return codFuncaoFunc;
	}
	
	public void setCodFuncaoFunc(Long codFuncaoFunc) {
		this.codFuncaoFunc = codFuncaoFunc;
	}
	
	public String getCodSistemaSist() {
		return codSistemaSist;
	}
	
	public void setCodSistemaSist(String codSistemaSist) {
		this.codSistemaSist = codSistemaSist;
	}
	
	public FuncaoPk(Long codFuncaoFunc, String codSistemaSist) {
		super();
		// TODO Auto-generated constructor stub
		this.codFuncaoFunc = codFuncaoFunc;
		this.codSistemaSist = codSistemaSist;
	}

	public boolean equals(Object other) {
	    if ( !(other instanceof FuncaoPk) ) return false;
	    FuncaoPk castOther = (FuncaoPk) other;
	    return new EqualsBuilder()
	        .append(this.getCodSistemaSist(), castOther.getCodSistemaSist())
	        .append(this.getCodFuncaoFunc(), castOther.getCodFuncaoFunc())
	        .isEquals();
	}

	public int hashCode() {
	    return new HashCodeBuilder()
	        .append("" + getCodSistemaSist() + "" + getCodFuncaoFunc())
	        .toHashCode();
	}

}
