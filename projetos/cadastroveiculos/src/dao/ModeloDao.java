package dao;

import java.util.List;

import model.Modelo;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.util.jpa.EntityManagerCreator;

@Component
public class ModeloDao extends BaseDao<Modelo>{
			
	public ModeloDao(EntityManagerCreator emCreator) {
		super(emCreator);
		// TODO Auto-generated constructor stub
	}

	public List<Modelo> listarPeloNome(String nome) {
		return super.listAllByFieldUsingLike("nome", nome);
	}

	
	
}
