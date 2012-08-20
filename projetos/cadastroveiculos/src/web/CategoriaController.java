package web;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import dao.CategoriaDao;

@Resource
public class CategoriaController {

	private Result result;
	private CategoriaDao dao;
	private Validator validator;

	public CategoriaController(Result result, CategoriaDao dao,
			Validator validator) {
		this.result = result;
		this.dao = dao;
		this.validator = validator;
	}

	public void listarCategoriasParaAutoComplete(String nome){
		result
		.use(Results.json())
		.withoutRoot()
		.from(RespostaAutocomplete.getResponstaAutoCompleteParaLista(dao.listarPeloNome(nome), "Nome", "Id"))
		.recursive()
		.serialize();
	}
	
}
