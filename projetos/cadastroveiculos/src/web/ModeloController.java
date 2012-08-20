package web;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import dao.ModeloDao;

@Resource
public class ModeloController {

	private Result result;
	private ModeloDao dao;
	private Validator validator;

	public ModeloController(Result result, ModeloDao dao,
			Validator validator) {
		this.result = result;
		this.dao = dao;
		this.validator = validator;
	}

	public void listarModelosParaAutoComplete(String nome){
		result
		.use(Results.json())
		.withoutRoot()
		.from(RespostaAutocomplete.getResponstaAutoCompleteParaLista(dao.listarPeloNome(nome), "Nome", "Id"))
		.recursive()
		.serialize();
	}
	
}
