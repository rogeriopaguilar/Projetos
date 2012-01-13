package br.com.dnsec.web.filtro;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Quando o filtro identifica que recurso deve ser verificado no cadastro de seguranca,
 * o id do recurso cadastrado no sistema de seguran�a deve ser de alguma forma mapeado
 * pela url que � passada ao filtro. O comportamento default do filtro � retirar a �ltima
 * parte da url (retirando a extens�o, se estiver presente) e utilizar como id do filtro.
 * Caso este comportamento default n�o seja o esperado para uma determinada alica��o,
 * a mesma deve configurar nos par�metros de inicializa��o do filtro uma classe que implemente
 * esta interface e que retorne � partir de uma determinada requisi��o qual id ser�
 * verificado no cadastro de seguran�a.
 * 
 * @version 0.1
 * @since 0.1
 * @author Rog�rio de Paula Aguilar
 * */
public interface IExtratorIdRecurso {

        /**
         * M�todo que deve retornar o id do recurso que ser� consultado no cadastro de fun��es do sistema
         * de seguran�a de acordo com a requisi��o
         * */
        public String getIdRecurso(HttpServletRequest request) throws ServletException;
        
}
