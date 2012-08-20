package dao;

import java.util.List;

import model.Pessoa;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.util.jpa.EntityManagerCreator;

@Component
public class ResponsavelDao extends BaseDao<Pessoa>{
			
	public ResponsavelDao(EntityManagerCreator emCreator) {
		super(emCreator);
		// TODO Auto-generated constructor stub
	}

	public List<Pessoa> listarPeloNome(String nome) {
		return super.listAllByFieldUsingLike("nome", nome);
	}


	
	
}
