package web;

import model.Marca;
import model.Veiculo;
import service.MarcaService;
import service.excessao.ExcessaoNegocio;
import util.Pagina;
import web.RequisicaoAjax.Status;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import dao.MarcaDao;

@Resource
public class MarcasController {

	private Result result;
	private MarcaService marcaService;

	//Redireciona a requisição para o jsp. A lista de veículos será carregada via ajax
	public void listar() {
	}
	
	
	public MarcasController(Result result, MarcaService marcaService) {
		this.result = result;
		this.marcaService = marcaService;
	}

	public void listarMarcasParaAutoComplete(String nome){
		result
		.use(Results.json())
		.withoutRoot()
		.from(RespostaAutocomplete.getResponstaAutoCompleteParaLista(marcaService.listarPeloNome(nome), "Nome", "Id"))
		.recursive()
		.serialize();
	}
	
	
	public void excluir(Long codMarca) {
		RequisicaoAjax req = new RequisicaoAjax();
		try {
			Marca marca = new Marca();
			marca.setId(codMarca);
			marcaService.excluir(marca);
			req.setStatus(Status.SUCESSO);
		} catch (ExcessaoNegocio e) {
			req.setStatus(Status.ERRO);
			req.adicionarMensagen(e.getMessage());
		}
		result.use(Results.json()).from(req).recursive().serialize();
	}
	
	public void prepararEdicao(Long codMarca) {
		RequisicaoAjax req = new RequisicaoAjax();
		Marca marca = marcaService.getMarcaParaEdicao(codMarca);
		if(marca == null) {
			req.setStatus(Status.ERRO);
			req.adicionarMensagen("Registro não encontrado!");
		} else {
			req.setStatus(Status.SUCESSO);
			req.adicionarObjetoRetorno(marca);
		}
		result.use(Results.json()).from(req).include("listaObjetosRetorno").serialize();
	}

	public void listarJson(String acao, String registrosPorPagina, String paginaAtual){
		if(acao == null) {
			acao = Pagina.Acoes.IR_PARA_PRIMEIRA_PAGINA.name();
		}
		if(registrosPorPagina == null) {
			registrosPorPagina = "" + Pagina.DEFAULT_REGISTROS_POR_PAGINA;
		}
		if(paginaAtual == null) {
			paginaAtual = "1";
		}
		Long lngRegistrosPorPagina = Long.parseLong(registrosPorPagina);
		Long lngPaginaAtual = Long.parseLong(paginaAtual);
		Pagina<Marca> pagina = marcaService.listAllPaginated(lngPaginaAtual, Pagina.Acoes.valueOf(acao), lngRegistrosPorPagina);
		//result.include("pagina", pagina);
		result.use(Results.json()).from(pagina).include("objetos").serialize();
	}
	
	public void editar(final Marca marca) {
		RequisicaoAjax req = new RequisicaoAjax();
		
		try {
			if(marca.getId() == null || marca.getId().intValue() == 0) {
				marcaService.incluir(marca);
			} else {
				marcaService.editar(marca);
			}
			req.setStatus(Status.SUCESSO);
		} catch (ExcessaoNegocio e) {
			req.setStatus(Status.ERRO);
			req.adicionarMensagen(e.getMessage());
		}
		result.use(Results.json()).from(req).recursive().serialize();
	}
	
	
}
