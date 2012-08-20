package util;

import java.util.List;

public class Pagina<E> {

	public static enum Acoes {
		IR_PARA_PROXIMA_PAGINA, IR_PARA_PAGINA_ANTERIOR, IR_PARA_PRIMEIRA_PAGINA, IR_PARA_ULTIMA_PAGINA
	};

	public static long DEFAULT_REGISTROS_POR_PAGINA = 5;
	private List<E> objetos;
	private long paginaAtual;
	private long totalPaginas;
	private long registrosPorPagina = DEFAULT_REGISTROS_POR_PAGINA;
	private boolean existeProximaPagina;
	private boolean existePaginaAnterior;

	public long getRegistrosPorPagina() {
		return registrosPorPagina;
	}

	public void setRegistrosPorPagina(long registrosPorPagina) {
		this.registrosPorPagina = registrosPorPagina;
	}

	public List<E> getObjetos() {
		return objetos;
	}

	public void setObjetos(List<E> objetos) {
		this.objetos = objetos;
	}

	public long getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(long paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

	public long getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(long totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public boolean isExisteProximaPagina() {
		return existeProximaPagina;
	}

	public void setExisteProximaPagina(boolean existeProximaPagina) {
		this.existeProximaPagina = existeProximaPagina;
	}

	public boolean isExistePaginaAnterior() {
		return existePaginaAnterior;
	}

	public void setExistePaginaAnterior(boolean existePaginaAnterior) {
		this.existePaginaAnterior = existePaginaAnterior;
	}

}
