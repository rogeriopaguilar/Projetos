package dao;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.EntidadeBase;
import util.Pagina;
import util.Pagina.Acoes;
import br.com.caelum.vraptor.util.jpa.EntityManagerCreator;

public class BaseDao<E extends EntidadeBase> {

	private EntityManager em;
	private Class persistentClass;
	
	public BaseDao(EntityManagerCreator emCreator){
		this.em = emCreator.getInstance();
		this.persistentClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		
	}
	
	protected EntityManager getEntityManager(){
		return em;
	}

	public List<E> listAll() {
		final String strQuery = "select o from " + getPersistentClass().getName() + " o ";
		List<E> listReturn = em.createQuery(strQuery).getResultList();
		return listReturn;
	}
	
	
	public Pagina<E> listAllPaginated(long paginaAtual, Acoes acao, long registrosPorPagina) {
		final String strQuery = "select o from " + getPersistentClass().getName() + " o ";
		final String strQueryCount = "select count(o) from " + getPersistentClass().getName() + " o ";

		Query query = em.createQuery(strQuery);
		Query queryCount = em.createQuery(strQueryCount);
		
		return this.queryPaginated(query, queryCount, paginaAtual, acao, registrosPorPagina);
	}
	
	/**
	 * Retorna uma lista contendo todas as entidades que possuem o campo like %valor%.
	 * 
	 * @param campo nome do campo
	 * @param valor que será utilizado na consulta
	 * @return lista contendo as entidades que obedecem os parâmetros da consulta
	 * */
	public List<E> listAllByFieldUsingLike(String campo, Object valor) {
		final String strQuery = "select o from " + getPersistentClass().getName() + " o ";
		StringBuilder builder = new StringBuilder(strQuery);
		builder.append("where ").append(campo).append(" like :valor");
		Query query = em.createQuery(builder.toString());
		query.setParameter("valor",  "%" + valor + "%");
		List<E> listReturn = query.getResultList();
		return listReturn;
	}
	

	/**
	 * Retorna uma lista contendo todas as entidades que possuem o campo=valor utilizando paginação.
	 * 
	 * @param campo nome do campo
	 * @param valor que será utilizado na consulta
	 * @param paginaAtual 
	 * @param registrosPorPagina 
	 * @param acao 
	 * @return lista contendo as entidades que obedecem os parâmetros da consulta
	 * 
	 * @see BaseDao#queryPaginated(Query, Query, long, Acoes, long)
	 * */
	public Pagina listAllByFieldUsingLikePaginated(String campo, Object valor, long paginaAtual, Acoes acao, long registrosPorPagina) {
		final String strQuery = "select o from " + getPersistentClass().getName() + " o ";
		final String strQueryCount = "select count(o) from " + getPersistentClass().getName() + " o ";
		
		StringBuilder builder = new StringBuilder(strQuery);
		builder.append("where ").append(campo).append(" like :valor");
		Query query = em.createQuery(builder.toString());
		query.setParameter("valor",  "%" + valor + "%");

		
		StringBuilder builderCount = new StringBuilder(strQueryCount);
		builderCount.append("where ").append(campo).append(" like :valor");
		Query queryCount = em.createQuery(builderCount.toString());
		queryCount.setParameter("valor",  "%" + valor + "%");
		
		return this.queryPaginated(query, queryCount, paginaAtual, acao, registrosPorPagina);
		
	}
	
	
	public E save(E vo) {
		((EntidadeBase)vo).setDataCadastro(new Date());
		em.persist(vo);
		em.flush();
		return vo;
	}
	
	public void update(E vo) {
		Object obj = loadRecord(((EntidadeBase)vo).getId());
		((EntidadeBase)vo).setDataAlteracao(new Date());
		if(obj != null) {
			em.merge(vo);
		}
	}

	public void delete(E vo) {
		Object obj = loadRecord(((EntidadeBase)vo).getId());
		if(obj != null) {
			em.remove(obj);
		}
	}

	public E loadRecord(Long ID) {
		return (E) em.find(getPersistentClass(), ID);
	}

	public Class getPersistentClass() {
		return persistentClass;
	}
	
	/**
	 * Executa uma consulta e retorna um objeto Pagina contendo os dados para paginação.
	 * 
	 * @param query deve conter a consulta (Ex: select o from Objeto o where o.campo=1)
	 * @param queryCount deve conter a consulta que retorna a quantidade de registros (Ex: select count(o) from Objeto o where o.campo=1)
	 * @param paginaAtual deve ser o número correspondente à pagina atual
	 * @param acao deve informar qual ação será realizada (ir para a próxima página, ir para a página anterior, ir para a última página ou ir para a primeira página)
	 * @param registrosPorPagina deve ser o número correspondente à quantidade de regostros por página
	 * 
	 * @return Pagina contendo a lista de objetos, a página atual após a ação realizada (Ex: página atual era 1, ação passada foi ir para a próxima página, no retorno a página atual será 2), a quantidade total de páginas 
	 * 		   e dois booleans indicando se existem a página anterior e a próxima
	 * */
	public Pagina queryPaginated(Query query, Query queryCount, long paginaAtual, Pagina.Acoes acao, long registrosPorPagina){
		if(registrosPorPagina <=0 ){
			registrosPorPagina = Pagina.DEFAULT_REGISTROS_POR_PAGINA;
		}
		if(acao == null) {
			acao = Pagina.Acoes.IR_PARA_PRIMEIRA_PAGINA;
		}
		Pagina pagina 	= new Pagina();
		List lista = new LinkedList();
		
		Long qtdeRegistros = ((Number) queryCount.getResultList().get(0)).longValue();
		Long qtdeTotalPaginas = qtdeRegistros / registrosPorPagina;
		if(qtdeRegistros % registrosPorPagina > 0) {
			qtdeTotalPaginas += 1;
		}
		
		long paginaParaIr = -1;
		if(Pagina.Acoes.IR_PARA_PRIMEIRA_PAGINA.equals(acao))
		{
			paginaParaIr = 1;
		}else if(Pagina.Acoes.IR_PARA_PAGINA_ANTERIOR.equals(acao))
		{
			paginaParaIr = paginaAtual - 1;
			if(paginaParaIr <= 0) {
				paginaParaIr = 1;
			}
		}else if(Pagina.Acoes.IR_PARA_PROXIMA_PAGINA.equals(acao))
		{
			paginaParaIr = paginaAtual + 1;
		}else if(Pagina.Acoes.IR_PARA_ULTIMA_PAGINA.equals(acao))
		{
			paginaParaIr = qtdeTotalPaginas;
		}
		
		boolean achouListaFinal = false;
		do{
			lista = query.setFirstResult((int)((paginaParaIr-1) * registrosPorPagina))
			.setMaxResults((int)registrosPorPagina < 0 ? Integer.MAX_VALUE : (int)registrosPorPagina).getResultList();
			
			if(lista.size() == 0) {
				paginaParaIr -= 1;
				if(paginaParaIr == 0) {
					paginaParaIr = 1;
					achouListaFinal = true;
				}
			} else {
				achouListaFinal = true;
			}
			
		}while( !achouListaFinal );
		
		pagina.setObjetos(lista);
		pagina.setTotalPaginas(qtdeTotalPaginas);
		pagina.setPaginaAtual(paginaParaIr);
		pagina.setExistePaginaAnterior(paginaParaIr > 1);
		pagina.setExisteProximaPagina(paginaParaIr < qtdeTotalPaginas);
		
		return pagina;

	}
	
	
}