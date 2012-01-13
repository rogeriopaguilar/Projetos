/*
 * CriptografiaSenhaMySQLImpl.java
 *
 * Created on April 29, 2007, 11:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dnsec.modulos.cadastros.usuario.business;

import dnsec.shared.util.CriptografiaUtil;

/**
 *
 * @author usuario
 */
public class CriptografiaSenhaMySQLImpl implements CriptografiaSenha{
    
    /** Creates a new instance of CriptografiaSenhaMySQLImpl */
    public CriptografiaSenhaMySQLImpl() {
    }

    public String criptografar(String codUsuario, String senha) {
        String senhaCriptografada = "";
    	try{
    		senhaCriptografada = CriptografiaUtil.getInstance().encrypt(senha);
        }catch(Exception e){
        	throw new UnsupportedOperationException("Não foi possível criptografar!", e);
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
