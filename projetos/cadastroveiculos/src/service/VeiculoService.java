package service;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import model.Categoria;
import model.HistoricoResponsabilidade;
import model.Marca;
import model.Modelo;
import model.Pessoa;
import model.Veiculo;

import org.apache.commons.lang3.StringUtils;

import service.excessao.ExcessaoNegocio;
import util.Pagina;
import util.Pagina.Acoes;
import br.com.caelum.vraptor.ioc.Component;
import dao.CategoriaDao;
import dao.HistoricoResponsabilidadeDao;
import dao.MarcaDao;
import dao.ModeloDao;
import dao.ResponsavelDao;
import dao.VeiculosDao;

@Component
public class VeiculoService {

	private VeiculosDao veiculoDao;
	private CategoriaDao categoriaDao;
	private MarcaDao marcaDao;
	private ModeloDao modeloDao;
	private ResponsavelDao responsavelDao;
	private HistoricoResponsabilidadeDao historicoResponsabilidadeDao;
	
	public VeiculoService(VeiculosDao veiculoDao, CategoriaDao categoriaDao,
			MarcaDao marcaDao, ModeloDao modeloDao,
			ResponsavelDao responsavelDao, HistoricoResponsabilidadeDao historicoResponsabilidadeDao) {
		super();
		this.veiculoDao = veiculoDao;
		this.categoriaDao = categoriaDao;
		this.marcaDao = marcaDao;
		this.modeloDao = modeloDao;
		this.responsavelDao = responsavelDao;
		this.historicoResponsabilidadeDao = historicoResponsabilidadeDao;
	}
	

	public void incluir(Veiculo veiculo) throws ExcessaoNegocio{
		validarVeiculo(veiculo);
		veiculo = veiculoDao.save(veiculo);
	}

	public void excluir(Veiculo veiculo) throws ExcessaoNegocio{
		if(veiculo != null && veiculo.getId() != null) {
			veiculoDao.delete(veiculo);
		}
	}

	public void editar(Veiculo veiculo) throws ExcessaoNegocio{
			validarVeiculo(veiculo);
			Veiculo vTMP = veiculoDao.loadRecord(veiculo.getId());
			HistoricoResponsabilidade historico = null;
			Date dataInicio = new Date();
			if(veiculo != null && vTMP != null && !( vTMP.getResponsavel().getId().compareTo(veiculo.getResponsavel().getId()) == 0) ) {
					historico = new HistoricoResponsabilidade();
					historico.setDataInicio(dataInicio);
					historico.setVeiculo(vTMP);
					historico.setResponsavel(vTMP.getResponsavel());
			}
			veiculoDao.update(veiculo);
			
			if(historico != null) { 
				historicoResponsabilidadeDao.atualizarUltimoHistorico(vTMP, dataInicio);
				historicoResponsabilidadeDao.save(historico); 
			};
			
	}
	
	public Veiculo getVeiculoParaEdicao(Long codVeiculo) {
		return veiculoDao.getVeiculoParaEdicao(codVeiculo);
	}
	
	private void validarVeiculo(Veiculo veiculo) throws ExcessaoNegocio {
		if(veiculo == null) throw new IllegalArgumentException("Parâmetro veiculo deve ser informado!");
		Long qtdeRegistros = 0L;
		if(veiculo.getId() == null || veiculo.getId().intValue() == 0) {
			//Inclusão
			qtdeRegistros = veiculoDao.getQtdeRegistrosPorPlaca(veiculo.getPlaca(), null);			
		} else {
			//Edição
			qtdeRegistros = veiculoDao.getQtdeRegistrosPorPlaca(veiculo.getPlaca(), veiculo.getId());			
		}
		if(qtdeRegistros > 0) {
			throw new ExcessaoNegocio("A placa informada já existe!");
		}
		Marca marca = veiculo.getMarca();
		Categoria categoria = veiculo.getCategoria();
		Modelo modelo = veiculo.getModelo();
		Pessoa responsavel = veiculo.getResponsavel();
		if(StringUtils.isEmpty(veiculo.getPlaca()))	throw new ExcessaoNegocio("A placa deve ser informada!");
		if(veiculo.getAnoFabricacao() == 0 || veiculo.getAnoFabricacao() < 1900)	throw new ExcessaoNegocio("O ano de fabricação deve ser informado e deve ser maior que 1900!");
		if(marca == null || marca.getId() == null) throw new ExcessaoNegocio("Marca deve ser informada!");
		if(categoria == null || categoria.getId() == null) throw new ExcessaoNegocio("Categoria deve ser informada!");
		if(modelo == null || modelo.getId() == null) throw new ExcessaoNegocio("Modelo deve ser informado!");
		if(responsavel == null || responsavel.getId() == null) throw new ExcessaoNegocio("Responsavel deve ser informado!");
		marca = marcaDao.loadRecord(marca.getId());
		categoria = categoriaDao.loadRecord(categoria.getId());
		modelo = modeloDao.loadRecord(modelo.getId());
		responsavel = responsavelDao.loadRecord(responsavel.getId());
		if(marca == null) throw new ExcessaoNegocio("Marca selecionada não foi encontrada!");
		if(categoria == null) throw new ExcessaoNegocio("Categoria selecionada não foi encontrada!!");
		if(modelo == null) throw new ExcessaoNegocio("Modelo selecionado não foi encontrado!!");
		if(responsavel == null) throw new ExcessaoNegocio("Responsavel selecionado não foi encontrado!");
		veiculo.setMarca(marca);
		veiculo.setCategoria(categoria);
		veiculo.setModelo(modelo);
		veiculo.setResponsavel(responsavel);
	}
	
	public Long getQtdeRegistrosPorPlaca(String placa, Long idExcluir) {
		return veiculoDao.getQtdeRegistrosPorPlaca(placa, idExcluir);
	}

	public List<Veiculo> listAll() {
		return veiculoDao.listAll();
	}

	public List<Veiculo> listAllByFieldUsingLike(String campo, Object valor) {
		return veiculoDao.listAllByFieldUsingLike(campo, valor);
	}

	public Pagina<Veiculo> listAllPaginated(long paginaAtual, Acoes acao,
			long registrosPorPagina) {
		return veiculoDao.listAllPaginated(paginaAtual, acao,
				registrosPorPagina);
	}

	public Pagina listAllByFieldUsingLikePaginated(String campo, Object valor,
			long paginaAtual, Acoes acao, long registrosPorPagina) {
		return veiculoDao.listAllByFieldUsingLikePaginated(campo, valor,
				paginaAtual, acao, registrosPorPagina);
	}

	public Veiculo loadRecord(Long ID) {
		return veiculoDao.loadRecord(ID);
	}

	public Pagina queryPaginated(Query query, Query queryCount,
			long paginaAtual, Acoes acao, long registrosPorPagina) {
		return veiculoDao.queryPaginated(query, queryCount, paginaAtual, acao,
				registrosPorPagina);
	}

	
}
