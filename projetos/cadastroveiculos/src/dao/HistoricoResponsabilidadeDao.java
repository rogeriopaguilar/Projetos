package dao;

import java.util.Date;

import javax.persistence.Query;

import model.HistoricoResponsabilidade;
import model.Veiculo;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.util.jpa.EntityManagerCreator;

@Component
public class HistoricoResponsabilidadeDao extends BaseDao<HistoricoResponsabilidade> {

	public HistoricoResponsabilidadeDao(EntityManagerCreator emCreator) {
		super(emCreator);
	}

	public void atualizarUltimoHistorico(Veiculo veiculo, Date dataFinal) {
		Query query = getEntityManager().createQuery("update HistoricoResponsabilidade u set u.dataFinal=:data where u.veiculo.id=:id and u.dataFinal is null");
		query.setParameter("data", dataFinal);
		query.setParameter("id", veiculo.getId());
		query.executeUpdate();
		getEntityManager().flush();
	}

}
