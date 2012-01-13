package dnsec.modulos.cadastros.usuario.business;

import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import dnsec.shared.database.hibernate.util.HibernateUtil;
import dnsec.shared.util.Constantes;


public class CriptografiaSenhaDinapImpl implements CriptografiaSenha{

	public String criptografar(String codUsuario, String senha) {
		Session session = null;
		java.sql.CallableStatement call = null;
       	String senhaCriptografada = null;
		try{
			session = HibernateUtil.openSession(Constantes.ARQUIVO_CONFIG_HIBERNATE);
			session.connection().setAutoCommit(false);
			call = session.connection().prepareCall("{call icd.proc_INF_get_hash(?, ?)  }");
	       	call.setString(1, senha);
			call.registerOutParameter(2, Types.VARCHAR);
	       	call.execute();
	       	senhaCriptografada = (String)call.getObject(2);
	       	senhaCriptografada = senhaCriptografada.substring(0,32);
			session.connection().commit();
		}catch(Exception e){
			e.printStackTrace();
			try {
				session.connection().rollback();
			} catch (HibernateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			if(call != null){
				try {
					call.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(session != null){
				HibernateUtil.closeSession(session);
			}
		}
		
		return senhaCriptografada;
	}

    public String descriptografar(String codUsuario, String senha) {
    	throw new UnsupportedOperationException("Não foi possível descriptografar pois trata-se de um algoritmo hash!");
    }


	public boolean suportaDescriptografia() {
		return false;
	}
	
	

}
