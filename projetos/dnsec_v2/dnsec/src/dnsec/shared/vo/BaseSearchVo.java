package dnsec.shared.vo;


public class BaseSearchVo extends BaseVo {

	
	/**
	 * Indica se todos os registros devem ser retornados
	 * na pesquisa
	 * */
	private boolean retornarTodosOsRegistros = false;
	
	/**
	 * Se != null, deve conter o campo de ordenacao
	 * */
	private String campoOrdenacao;

	/**
	 * Indica se a pesquisa deve ser  realizada
	 * */

	private boolean realizarPesquisa = true;
	
	public String getCampoOrdenacao() {
		return campoOrdenacao;
	}

	public void setCampoOrdenacao(String campoOrdenacao) {
		this.campoOrdenacao = campoOrdenacao;
	}

	public boolean isRetornarTodosOsRegistros() {
		return retornarTodosOsRegistros;
	}

	public void setRetornarTodosOsRegistros(boolean retornarTodosOsRegistros) {
		this.retornarTodosOsRegistros = retornarTodosOsRegistros;
	}

	public boolean isRealizarPesquisa() {
		return realizarPesquisa;
	}

	public void setRealizarPesquisa(boolean realizarPesquisa) {
		this.realizarPesquisa = realizarPesquisa;
	}
	
	
	
	
	
	
}
