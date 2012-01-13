/**
 * Classe que guarda a lista de objetos
 * e as informações sobre a página atual e
 * o total de páginas.
 * */
package dnsec.shared.util;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Pagina implements Serializable{

		public static final int ACAO_MOVER_PRIMEIRO_REGISTRO = 0;	
		public static final int ACAO_MOVER_ULTIMO_REGISTRO = 1;	
		public static final int ACAO_MOVER_ANTERIOR_REGISTRO = 2;	
		public static final int ACAO_MOVER_PAGINA_ESPECIFICA = 3;	
		public static final int ACAO_MOVER_PROXIMO_REGISTRO = 4;	
	
		public static final long REGISTROS_POR_PAGINA_DEFAULT = 10;
		public static final long MAXIMO_REGISTROS_POR_PAGINA_DEFAULT = 50;
		
		
		private long paginaAtual;
		private long totalPaginas;
		private long registrosPorPagina = REGISTROS_POR_PAGINA_DEFAULT;
		private long totalRegistros;
		private long paginaEspecificaAMover;
		private List listObjects = new LinkedList();

		

		private int proximaAcao = ACAO_MOVER_PRIMEIRO_REGISTRO;
		
		public String toString(){
			StringBuffer buffer = new StringBuffer();
			String separador = System.getProperty("line.separator");
			buffer.append(separador + "--------------Página--------------" + separador);
			buffer.append("paginaAtual --> " + paginaAtual + separador);
			buffer.append("Total de páginas --> " + totalPaginas + separador);
			buffer.append("Total de registros --> " + totalRegistros + separador);
			buffer.append("Registros por página --> " + registrosPorPagina + separador);
			buffer.append(separador + "----------------------------------" + separador);

			

			return buffer.toString().trim();
			
		}
		
		public int getProximaAcao() {
			return proximaAcao;
		}

		public void setProximaAcao(int proximaAcao) {
			this.proximaAcao = proximaAcao;
		}

		public List getListObjects() {
			synchronized(this){
				if(listObjects == null){
					listObjects = new LinkedList();
				}
			}
			return listObjects;
		}
		
		public void setListObjects(List listObjects) {
			this.listObjects = listObjects;
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

		public long getRegistrosPorPagina() {
			return registrosPorPagina;
		}

		public void setRegistrosPorPagina(long registrosPorPagina) {
			if(registrosPorPagina > 0){
				this.registrosPorPagina = registrosPorPagina;
			}
		}

		public long getTotalRegistros() {
			return totalRegistros;
		}

		public void setTotalRegistros(long totalRegistros) {
			this.totalRegistros = totalRegistros;
		}

		public long getPaginaEspecificaAMover() {
			return paginaEspecificaAMover;
		}

		public void setPaginaEspecificaAMover(long paginaEspecificaAMover) {
			this.paginaEspecificaAMover = paginaEspecificaAMover;
		}


		
		
			
}
