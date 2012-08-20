package web;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import dao.ResponsavelDao;

@Resource
public class ResponsavelController {

	private Result result;
	private ResponsavelDao rDao;
	private Validator validator;

	public ResponsavelController(Result result, ResponsavelDao rDao,
			Validator validator) {
		this.result = result;
		this.rDao = rDao;
		this.validator = validator;
	}

	public void listarResponsaveisParaAutoComplete(String nome){
		result
		.use(Results.json())
		.withoutRoot()
		.from(RespostaAutocomplete.getResponstaAutoCompleteParaLista(rDao.listarPeloNome(nome), "Nome", "Id"))
		.recursive()
		.serialize();
	}
	
}
