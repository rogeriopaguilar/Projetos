package dao;

import java.util.List;

import model.Marca;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.util.jpa.EntityManagerCreator;

@Component
public class MarcaDao extends BaseDao<Marca>{
			

	public MarcaDao(EntityManagerCreator emCreator) {
		super(emCreator);
		// TODO Auto-generated constructor stub
	}

	public List<Marca> listarPeloNome(String nome) {
		return super.listAllByFieldUsingLike("nome", nome);
	}

	
	
}
