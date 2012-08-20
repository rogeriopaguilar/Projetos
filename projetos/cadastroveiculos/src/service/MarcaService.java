package service;

import java.util.List;

import javax.persistence.Query;

import model.Marca;

import org.apache.commons.lang3.StringUtils;

import service.excessao.ExcessaoNegocio;
import util.Pagina;
import util.Pagina.Acoes;
import br.com.caelum.vraptor.ioc.Component;
import dao.MarcaDao;

@Component
public class MarcaService {

	private MarcaDao marcaDao;
	
	public List<Marca> listarPeloNome(String nome) {
		return marcaDao.listarPeloNome(nome);
	}

	public List<Marca> listAll() {
		return marcaDao.listAll();
	}

	public Pagina<Marca> listAllPaginated(long paginaAtual, Acoes acao,
			long registrosPorPagina) {
		return marcaDao.listAllPaginated(paginaAtual, acao, registrosPorPagina);
	}

	public List<Marca> listAllByFieldUsingLike(String campo, Object valor) {
		return marcaDao.listAllByFieldUsingLike(campo, valor);
	}

	public Pagina listAllByFieldUsingLikePaginated(String campo, Object valor,
			long paginaAtual, Acoes acao, long registrosPorPagina) {
		return marcaDao.listAllByFieldUsingLikePaginated(campo, valor,
				paginaAtual, acao, registrosPorPagina);
	}

	public void delete(Marca vo) {
		marcaDao.delete(vo);
	}

	public Marca loadRecord(Long ID) {
		return marcaDao.loadRecord(ID);
	}

	public Pagina queryPaginated(Query query, Query queryCount,
			long paginaAtual, Acoes acao, long registrosPorPagina) {
		return marcaDao.queryPaginated(query, queryCount, paginaAtual, acao,
				registrosPorPagina);
	}

	public MarcaService(MarcaDao marcaDao) {
		super();
		this.marcaDao = marcaDao;
	}
	
	public void incluir(Marca marca) throws ExcessaoNegocio{
		validarMarca(marca);
		marca = marcaDao.save(marca);
	}

	public void excluir(Marca marca) throws ExcessaoNegocio{
		if(marca != null && marca.getId() != null) {
			marcaDao.delete(marca);
		}
	}

	public void editar(Marca marca) throws ExcessaoNegocio{
			validarMarca(marca);
			Marca vTMP = marcaDao.loadRecord(marca.getId());
			marcaDao.update(marca);
	}

	public Marca getMarcaParaEdicao(Long codMarca) {
		return marcaDao.loadRecord(codMarca);
	}
	
	private void validarMarca(Marca marca) throws ExcessaoNegocio {
		if(marca == null) throw new IllegalArgumentException("Parâmetro Marca deve ser informado!");
		if(StringUtils.isEmpty(marca.getNome())) throw new ExcessaoNegocio("O nome deve ser informado!");
	}
	
	
}
