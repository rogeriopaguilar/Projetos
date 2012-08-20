import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Categoria;
import model.Marca;
import model.Modelo;
import model.Pessoa;
import model.Veiculo;



public class TesteJPA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("default-criacao");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		for(int i = 0; i < 10; i++){
			Marca marca = new Marca();
			marca.setDataCadastro(new Date());
			marca.setNome("Marca " + i);
			em.persist(marca);
			
			Modelo modelo = new Modelo();
			modelo.setDataCadastro(new Date());
			modelo.setNome("Modelo  " + i);
			em.persist(modelo);

			Categoria categoria = new Categoria();
			categoria.setDataCadastro(new Date());
			categoria.setNome("Categoria " + i);
			em.persist(categoria);
			
			Pessoa pessoa = new Pessoa();
			pessoa.setDataCadastro(new Date());
			pessoa.setNome("Pessoa " + i);
			em.persist(pessoa);
			
			
			Veiculo veiculo = new Veiculo();
			veiculo.setAnoFabricacao(1999);
			veiculo.setCategoria(categoria);
			veiculo.setDataCadastro(new Date());
			veiculo.setMarca(marca);
			veiculo.setModelo(modelo);
			veiculo.setCategoria(categoria);
			veiculo.setPlaca("Placa " + i);
			
			em.persist(veiculo);
			
		}
		
		
		em.getTransaction().commit();
		
	}

}
