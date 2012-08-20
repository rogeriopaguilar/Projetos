package dao;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import util.Pagina;
import util.Pagina.Acoes;

import model.Veiculo;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.util.jpa.EntityManagerCreator;

@Component
public class VeiculosDao extends BaseDao<Veiculo> {
			
	public VeiculosDao(EntityManagerCreator emCreator) {
		super(emCreator);
	}

	public Long getQtdeRegistrosPorPlaca(String placa, Long idExcluir) {
		final String strQuery = "select count(v) from Veiculo v where v.placa=:placa ";
		String str = strQuery;
		Long qtde = 0l;
		if(idExcluir != null) {
			StringBuilder builderQuery = new StringBuilder(strQuery);
			builderQuery.append(" and v.id != :id ");
			str = builderQuery.toString();
		}
		TypedQuery query = getEntityManager().createQuery(str, Long.class);
		query.setParameter("placa", placa);
		if(idExcluir != null) {
			query.setParameter("id", idExcluir);
		}
		qtde = ((Number)query.getSingleResult()).longValue();
		return qtde;
		
	}
	
	public Veiculo getVeiculoParaEdicao(Long codVeiculo) {
		Veiculo veiculo = null;
		final String strQuery = "select v from Veiculo v " +
		" left outer join fetch v.categoria " +
		" left outer join fetch v.marca " +
		" left outer join fetch v.modelo " +
		" left outer join fetch v.responsavel " +
		" where v.id=:id ";
		TypedQuery query = getEntityManager().createQuery(strQuery, Veiculo.class);
		query.setParameter("id", codVeiculo);
		try{
			veiculo = (Veiculo)query.getSingleResult();
		}catch(RuntimeException e) {
			veiculo = null;
		}
		return veiculo;
	}

	@Override
	public Pagina<Veiculo> listAllPaginated(long paginaAtual, Acoes acao,
			long registrosPorPagina) {
		final String strQuery = "select o from Veiculo o left outer join fetch o.responsavel ";
		final String strQueryCount = "select count(o) from Veiculo o ";

		Query query = getEntityManager().createQuery(strQuery);
		Query queryCount = getEntityManager().createQuery(strQueryCount);
		
		return super.queryPaginated(query, queryCount, paginaAtual, acao, registrosPorPagina);
		
	}
	
}
