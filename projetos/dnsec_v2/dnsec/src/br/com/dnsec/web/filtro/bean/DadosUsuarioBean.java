package br.com.dnsec.web.filtro.bean;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilizada para guardar dados relacionados com  o usu�rio
 * na sess�o da aplica��o web
 * 
 * @version 0.1
 * @author Rog�rio de Paula Aguilar
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
