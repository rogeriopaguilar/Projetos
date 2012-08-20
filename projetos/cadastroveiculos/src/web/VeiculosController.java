package web;

import model.Veiculo;
import service.VeiculoService;
import service.excessao.ExcessaoNegocio;
import util.Pagina;
import web.RequisicaoAjax.Status;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.Validations;
import br.com.caelum.vraptor.view.Results;
import dao.VeiculosDao;

@Resource
public class VeiculosController {

	private Result result;
	private Validator validator;
	private VeiculoService veiculoService;
	
	public VeiculosController(Result result, VeiculosDao vDao, Validator validator, VeiculoService veiculoService) {
		this.result = result;
		this.validator = validator;
		this.veiculoService = veiculoService;
	}

	//Redireciona a requisição para o jsp. A lista de veículos será carregada via ajax
	public void listar() {
	}
	
	public void editar(final Veiculo veiculo) {
		RequisicaoAjax req = new RequisicaoAjax();
		
		//Validação movida para camada de negócio
		/*validator.checking(new Validations() {
			{
				that(veiculo.getPlaca() != null
						&& !veiculo.getPlaca().isEmpty(), "veiculo.placa",
						"placa.vazia");
				// that(produto.getPreco() > 0, "produto.preco",
				// "preco.invalido");
			}
		});
		if(validator.hasErrors()){
			req.setStatus(Status.ERRO);
			for(Message m : validator.getErrors()) {
					req.adicionarMensagen(m.getMessage());
			}
		} else {
			req.setStatus(Status.SUCESSO).adicionarMensagen("Operação realizada com sucesso!");
		}*/
		//validator.onErrorUse(Results.logic()).forwardTo(VeiculosController.class).listar();
		
		try {
			if(veiculo.getId() == null || veiculo.getId().intValue() == 0) {
				veiculoService.incluir(veiculo);
			} else {
				veiculoService.editar(veiculo);
			}
			req.setStatus(Status.SUCESSO);
		} catch (ExcessaoNegocio e) {
			req.setStatus(Status.ERRO);
			req.adicionarMensagen(e.getMessage());
		}
		//result.redirectTo(this.getClass()).listar();
		result.use(Results.json()).from(req).recursive().serialize();
	}
	
	public void excluir(Long codVeiculo) {
		RequisicaoAjax req = new RequisicaoAjax();
		try {
			Veiculo veiculo = new Veiculo();
			veiculo.setId(codVeiculo);
			veiculoService.excluir(veiculo);
			req.setStatus(Status.SUCESSO);
		} catch (ExcessaoNegocio e) {
			req.setStatus(Status.ERRO);
			req.adicionarMensagen(e.getMessage());
		}
		result.use(Results.json()).from(req).recursive().serialize();
	}
	
	public void prepararEdicao(Long codVeiculo) {
		RequisicaoAjax req = new RequisicaoAjax();
		Veiculo veiculo = veiculoService.getVeiculoParaEdicao(codVeiculo);
		if(veiculo == null) {
			req.setStatus(Status.ERRO);
			req.adicionarMensagen("Registro não encontrado!");
		} else {
			req.setStatus(Status.SUCESSO);
			req.adicionarObjetoRetorno(veiculo);
			req.adicionarObjetoRetorno(veiculo.getMarca());
			req.adicionarObjetoRetorno(veiculo.getModelo());
			req.adicionarObjetoRetorno(veiculo.getCategoria());
			req.adicionarObjetoRetorno(veiculo.getResponsavel());
			
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
		Pagina<Veiculo> pagina = veiculoService.listAllPaginated(lngPaginaAtual, Pagina.Acoes.valueOf(acao), lngRegistrosPorPagina);
		result.include("pagina", pagina);
	}
	
	
	
	
}
