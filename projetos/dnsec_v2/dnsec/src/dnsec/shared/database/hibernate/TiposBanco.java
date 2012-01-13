package dnsec.shared.database.hibernate;

import org.apache.commons.lang.builder.EqualsBuilder;

public class TiposBanco implements java.io.Serializable
{

	private String codTipoBanco;

	public String getCodTipoBanco() {
		return codTipoBanco;
	}

	public void setCodTipoBanco(String codTipoBanco) {
		this.codTipoBanco = codTipoBanco;
	}
	
	public String toString()
	{
		return codTipoBanco;
	}
	
	public boolean equals(Object obj)
	{
		if(! (obj instanceof TiposBanco) )
		{
			return false;
		}
		return new EqualsBuilder().append(codTipoBanco, ((TiposBanco)obj).getCodTipoBanco()).isEquals();
	}
	
	
}
