package dnsec.shared.database.hibernate;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Usuario implements Serializable{

	private Long numChapaFunc;
	private String codUsuarioUsua;
	private String nomeUsuarioUsua;
	private String codSenhaUsua;
	private Timestamp dataInclusaoUsua;
	private Timestamp dataUltAcessoUsua;
	private Timestamp dataUltAltSenhaUsua;
	private Long numDiasValidadesenhaUsua;
	private Long qtdeErrosLoginUsua;
	private String condAltSenhaUsua;
	private String numTelefoneUsua;
	private String codEmailUsua;
	private String condBloqueadoUsua;
	private String codUsuarioAdabasUsua;
	private Timestamp dataSolicNovaSenha;
	
	private Set grupos;
	
	public Set getGrupos() {
		return grupos;
	}



	public void setGrupos(Set grupos) {
		this.grupos = grupos;
	}



	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Usuario(Long numChapaFunc, String codUsuarioUsua, String nomeUsuarioUsua, String codSenhaUsua, Timestamp dataInclusaoUsua, Timestamp dataUltAcessoUsua, Timestamp dataUltAltSenhaUsua, Long numDiasValidadesenhaUsua, Long qtdeErrosLoginUsua, String condAltSenhaUsua, String numTelefoneUsua, String codEmailUsua, String condBloqueadoUsua, String codUsuarioAdabasUsua, Timestamp dataSolicNovaSenha) {
		super();
		// TODO Auto-generated constructor stub
		this.numChapaFunc = numChapaFunc;
		this.codUsuarioUsua = codUsuarioUsua;
		this.nomeUsuarioUsua = nomeUsuarioUsua;
		this.codSenhaUsua = codSenhaUsua;
		this.dataInclusaoUsua = dataInclusaoUsua;
		this.dataUltAcessoUsua = dataUltAcessoUsua;
		this.dataUltAltSenhaUsua = dataUltAltSenhaUsua;
		this.numDiasValidadesenhaUsua = numDiasValidadesenhaUsua;
		this.qtdeErrosLoginUsua = qtdeErrosLoginUsua;
		this.condAltSenhaUsua = condAltSenhaUsua;
		this.numTelefoneUsua = numTelefoneUsua;
		this.codEmailUsua = codEmailUsua;
		this.condBloqueadoUsua = condBloqueadoUsua;
		this.codUsuarioAdabasUsua = codUsuarioAdabasUsua;
		this.dataSolicNovaSenha = dataSolicNovaSenha;
	}



	public String getCodEmailUsua() {
		return codEmailUsua;
	}
	
	public void setCodEmailUsua(String codEmailUsua) {
		this.codEmailUsua = codEmailUsua;
	}
	
	public String getCodSenhaUsua() {
		return codSenhaUsua;
	}
	
	public void setCodSenhaUsua(String codSenhaUsua) {
		this.codSenhaUsua = codSenhaUsua;
	}
	
	public String getCodUsuarioAdabasUsua() {
		return codUsuarioAdabasUsua;
	}
	
	public void setCodUsuarioAdabasUsua(String codUsuarioAdabasUsua) {
		this.codUsuarioAdabasUsua = codUsuarioAdabasUsua;
	}
	
	public String getCodUsuarioUsua() {
		return codUsuarioUsua;
	}
	
	public void setCodUsuarioUsua(String codUsuarioUsua) {
		this.codUsuarioUsua = codUsuarioUsua;
	}
	
	public String getCondAltSenhaUsua() {
		return condAltSenhaUsua;
	}
	
	public void setCondAltSenhaUsua(String condAltSenhaUsua) {
		this.condAltSenhaUsua = condAltSenhaUsua;
	}
	
	public String getCondBloqueadoUsua() {
		return condBloqueadoUsua;
	}
	
	public void setCondBloqueadoUsua(String condBloqueadoUsua) {
		this.condBloqueadoUsua = condBloqueadoUsua;
	}
	
	public Timestamp getDataInclusaoUsua() {
		return dataInclusaoUsua;
	}
	
	public void setDataInclusaoUsua(Timestamp dataInclusaoUsua) {
		this.dataInclusaoUsua = dataInclusaoUsua;
	}
	
	public Timestamp getDataSolicNovaSenha() {
		return dataSolicNovaSenha;
	}
	
	public void setDataSolicNovaSenha(Timestamp dataSolicNovaSenha) {
		this.dataSolicNovaSenha = dataSolicNovaSenha;
	}
	
	public Timestamp getDataUltAcessoUsua() {
		return dataUltAcessoUsua;
	}
	
	public void setDataUltAcessoUsua(Timestamp dataUltAcessoUsua) {
		this.dataUltAcessoUsua = dataUltAcessoUsua;
	}
	
	public Timestamp getDataUltAltSenhaUsua() {
		return dataUltAltSenhaUsua;
	}
	
	public void setDataUltAltSenhaUsua(Timestamp dataUltAltSenhaUsua) {
		this.dataUltAltSenhaUsua = dataUltAltSenhaUsua;
	}
	
	public String getNomeUsuarioUsua() {
		return nomeUsuarioUsua;
	}
	
	public void setNomeUsuarioUsua(String nomeUsuarioUsua) {
		this.nomeUsuarioUsua = nomeUsuarioUsua;
	}
	
	public Long getNumChapaFunc() {
		return numChapaFunc;
	}
	
	public void setNumChapaFunc(Long numChapaFunc) {
		this.numChapaFunc = numChapaFunc;
	}
	
	public Long getNumDiasValidadesenhaUsua() {
		return numDiasValidadesenhaUsua;
	}
	
	public void setNumDiasValidadesenhaUsua(Long numDiasValidadesenhaUsua) {
		this.numDiasValidadesenhaUsua = numDiasValidadesenhaUsua;
	}
	
	public String getNumTelefoneUsua() {
		return numTelefoneUsua;
	}
	
	public void setNumTelefoneUsua(String numTelefoneUsua) {
		this.numTelefoneUsua = numTelefoneUsua;
	}
	
	public Long getQtdeErrosLoginUsua() {
		return qtdeErrosLoginUsua;
	}
	
	public void setQtdeErrosLoginUsua(Long qtdeErrosLoginUsua) {
		this.qtdeErrosLoginUsua = qtdeErrosLoginUsua;
	}
	
    public boolean equals(Object other) {
        if ( !(other instanceof Usuario) ) return false;
        Usuario castOther = (Usuario) other;
        return new EqualsBuilder()
            .append(this.getCodUsuarioUsua(), castOther.getCodUsuarioUsua())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getNumChapaFunc())
            .toHashCode();
    }	
	
}
