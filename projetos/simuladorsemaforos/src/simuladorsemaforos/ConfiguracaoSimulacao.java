package simuladorsemaforos;

import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedDeque;


/**
*Programa que simula o tráfego de veículos por um semáforo entre duas ruas que se cruzam e tem direção única
*@author Rogério de Paula Aguilar - rogeriodpaguilarbr@gmail.com
 *
 * os arquivos fontes estão com a codificação utf-8
 * mais informações no arquivo leiame.txt
*/


public class ConfiguracaoSimulacao {

	private Deque<Carro> listaCarrosPistaEsquedaDireita = new ConcurrentLinkedDeque<Carro>();
	private Deque<Carro> listaCarrosPistaCimaBaixo = new ConcurrentLinkedDeque<Carro>();
	
	private int segundosParaFecharOSemaforo = 15;
	private boolean exibirImagens = true;
	private boolean exibirInformacoes = true;

	
	public void limpar() {
		listaCarrosPistaEsquedaDireita.clear();
		listaCarrosPistaCimaBaixo.clear();
		
	}
	
	public void adicionarCarroPistaEsquerdaDireita(Carro carro) {
		listaCarrosPistaEsquedaDireita.addFirst(carro);
	}

	public void adicionarCarroPistaCimaBaixo(Carro carro) {
		listaCarrosPistaCimaBaixo.addFirst(carro);
	}
	
	public Carro getProximoCarroSimulacaoPistaEsquerdaDireita() {
		try {
			return listaCarrosPistaEsquedaDireita.removeLast();
		}catch(NoSuchElementException e) {}
		return null;
	}

	public Carro getProximoCarroSimulacaoPistaCimaBaixo() {
		try {
			return listaCarrosPistaCimaBaixo.removeLast();
		}catch(NoSuchElementException e) {}
		return null;
	}

	public int getSegundosParaFecharOSemaforo() {
		return segundosParaFecharOSemaforo;
	}

	public void setSegundosParaFecharOSemaforo(int segundosParaFecharOSemaforo) {
		this.segundosParaFecharOSemaforo = segundosParaFecharOSemaforo;
	}

	public boolean isExibirImagens() {
		return exibirImagens;
	}

	public void setExibirImagens(boolean exibirImagens) {
		this.exibirImagens = exibirImagens;
	}

	public boolean isExibirInformacoes() {
		return exibirInformacoes;
	}

	public void setExibirInformacoes(boolean exibirInformacoes) {
		this.exibirInformacoes = exibirInformacoes;
	}
	
	
	
}
