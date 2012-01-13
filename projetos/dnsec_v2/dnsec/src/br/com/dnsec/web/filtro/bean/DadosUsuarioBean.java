package br.com.dnsec.web.filtro.bean;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilizada para guardar dados relacionados com  o usuário
 * na sessão da aplicação web
 * 
 * @version 0.1
 * @author Rogério de Paula Aguilar
 * */
public class DadosUsuarioBean implements Serializable{
    
    private Map mapaAtributosUsuario = new HashMap();
    
    public DadosUsuarioBean() 
    {
    }
    
    public void adicionarAtributo(Object chave, Object valor)
    {
        mapaAtributosUsuario.put(chave, valor);
    }
    
    public Object retornarValorAtributo(Object chave)
    {
        return mapaAtributosUsuario.get(chave);
    }
    
}
