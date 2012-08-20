package web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class RequisicaoAjax implements Serializable {

	public static enum Status {
		SUCESSO, ERRO
	};

	private Status status;
	private List<String> listaMensagens = new ArrayList<String>();
	private List<Object> listaObjetosRetorno = new ArrayList<Object>();
	
	public Status getStatus() {
		return status;
	}

	public RequisicaoAjax setStatus(Status status) {
		this.status = status;
		return this;
	}

	public List<String> getListaMensagens() {
		return new ArrayList(listaMensagens);
	}

	public RequisicaoAjax adicionarMensagen(String mensagem) {
		this.listaMensagens.add(mensagem);
		return this;
	}

	public RequisicaoAjax removerMensagen(String mensagem) {
		this.listaMensagens.add(mensagem);
		return this;
	}
	
	public void adicionarObjetoRetorno(Object objeto) {
		listaObjetosRetorno.add(objeto);
	}
	
	public Object removerObjetoRetorno(Object objeto) {
		return listaObjetosRetorno.remove(objeto);
	}
	
	public List<Object> getListaObjetosRetorno() {
		return listaObjetosRetorno;
	}

}
