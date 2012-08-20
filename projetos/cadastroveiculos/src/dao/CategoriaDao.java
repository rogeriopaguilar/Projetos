package dao;

import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Marca;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.util.jpa.EntityManagerCreator;

@Component
public class CategoriaDao extends BaseDao<Categoria>{
			
	public CategoriaDao(EntityManagerCreator emCreator) {
		super(emCreator);
	}

	public List<Categoria> listarPeloNome(String nome) {
		return super.listAllByFieldUsingLike("nome", nome);
	}

	
	
}
