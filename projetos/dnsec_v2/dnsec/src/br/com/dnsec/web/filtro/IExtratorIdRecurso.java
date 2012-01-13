package br.com.dnsec.web.filtro;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Quando o filtro identifica que recurso deve ser verificado no cadastro de seguranca,
 * o id do recurso cadastrado no sistema de segurança deve ser de alguma forma mapeado
 * pela url que é passada ao filtro. O comportamento default do filtro é retirar a última
 * parte da url (retirando a extensão, se estiver presente) e utilizar como id do filtro.
 * Caso este comportamento default não seja o esperado para uma determinada alicação,
 * a mesma deve configurar nos parâmetros de inicialização do filtro uma classe que implemente
 * esta interface e que retorne à partir de uma determinada requisição qual id será
 * verificado no cadastro de segurança.
 * 
 * @version 0.1
 * @since 0.1
 * @author Rogério de Paula Aguilar
 * */
public interface IExtratorIdRecurso {

        /**
         * Método que deve retornar o id do recurso que será consultado no cadastro de funções do sistema
         * de segurança de acordo com a requisição
         * */
        public String getIdRecurso(HttpServletRequest request) throws ServletException;
        
}
