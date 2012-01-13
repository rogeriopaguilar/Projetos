package dnsec.shared.database.hibernate;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Sistema implements Serializable {

		private String codSistemaSist;
		private String descrSistemaSist;
		private String condCadastrarSist;
		private String nomeBancoSist;
		private String nomeServidorSist;
		private String tipoBancoSist;
		private String nomeProprietarioSist;
		private String nomeAnalistaRespSist;
		
		private Set funcoes;
		private Set grupos;
		
		public Set getGrupos() {
			return grupos;
		}



		public void setGrupos(Set grupos) {
			this.grupos = grupos;
		}



		public Set getFuncoes() {
			return funcoes;
		}



		public void setFuncoes(Set funcoes) {
			this.funcoes = funcoes;
		}



		public Sistema() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		
		public Sistema(String codSistemaSist, String descrSistemaSist, String codCadastrarSist, String nomeBancoSist, String nomeServidorSist, String tipoBancoSist, String nomeProprietarioSist, String nomeAnalistaRespSist) {
			super();
			// TODO Auto-generated constructor stub
			this.codSistemaSist = codSistemaSist;
			this.descrSistemaSist = descrSistemaSist;
			this.condCadastrarSist = codCadastrarSist;
			this.nomeBancoSist = nomeBancoSist;
			this.nomeServidorSist = nomeServidorSist;
			this.tipoBancoSist = tipoBancoSist;
			this.nomeProprietarioSist = nomeProprietarioSist;
			this.nomeAnalistaRespSist = nomeAnalistaRespSist;
		}



		public String getCondCadastrarSist() {
			return condCadastrarSist;
		}
		public void setCondCadastrarSist(String codCadastrarSist) {
			this.condCadastrarSist = codCadastrarSist;
		}
		public String getCodSistemaSist() {
			return codSistemaSist;
		}
		public void setCodSistemaSist(String codSistemaSist) {
			this.codSistemaSist = codSistemaSist;
		}
		public String getDescrSistemaSist() {
			return descrSistemaSist;
		}
		public void setDescrSistemaSist(String descrSistemaSist) {
			this.descrSistemaSist = descrSistemaSist;
		}
		public String getNomeAnalistaRespSist() {
			return nomeAnalistaRespSist;
		}
		public void setNomeAnalistaRespSist(String nomeAnalistaRespSist) {
			this.nomeAnalistaRespSist = nomeAnalistaRespSist;
		}
		public String getNomeBancoSist() {
			return nomeBancoSist;
		}
		public void setNomeBancoSist(String nomeBancoSist) {
			this.nomeBancoSist = nomeBancoSist;
		}
		public String getNomeProprietarioSist() {
			return nomeProprietarioSist;
		}
		public void setNomeProprietarioSist(String nomeProprietarioSist) {
			this.nomeProprietarioSist = nomeProprietarioSist;
		}
		public String getNomeServidorSist() {
			return nomeServidorSist;
		}
		public void setNomeServidorSist(String nomeServidorSist) {
			this.nomeServidorSist = nomeServidorSist;
		}
		public String getTipoBancoSist() {
			return tipoBancoSist;
		}
		public void setTipoBancoSist(String tipoBancoSist) {
			this.tipoBancoSist = tipoBancoSist;
		}

	    public boolean equals(Object other) {
	        if ( !(other instanceof Sistema) ) return false;
	        Sistema castOther = (Sistema) other;
	        return new EqualsBuilder()
	            .append(this.getCodSistemaSist(), castOther.getCodSistemaSist())
	            .isEquals();
	    }

	    public int hashCode() {
	        return new HashCodeBuilder()
	            .append(getCodSistemaSist())
	            .toHashCode();
	    }
		
}
