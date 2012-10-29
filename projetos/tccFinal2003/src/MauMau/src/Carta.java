/*
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
Classe: jogo.Carta
Responsabilidades: Representa uma carta do baralho

****************************************Alterações****************************************


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 28/08/2003
Responsável: Rogério de Paula Aguilar
Descrição: Criação da Classe
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 30/08/2003
Responsável: Rogério de Paula Aguilar
Descrição: Colocando imagens finais nas cartas
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 08/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Alterando classe para exibir as informações sobre o verso
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 14/09/2003
Responsável: Rogério de Paula Aguilar
Descrição:  Modificando rotinas de debug para utilizarem o método exibirDebugMsg da
		classe ControleJogo
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 18/09/2003
Responsável: Rogério de Paula Aguilar
Descrição:  Adicionando constantes

Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%





*/
package jogo;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;


/** 
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
<p>Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
<p>Classe: jogo.Carta
<p>Responsabilidades: Representa uma carta do baralho

@author Rogério de Paula Aguilar
@version  1.0

*/

public class Carta extends Sprite{
	/*
		OBS: Não modificar a ordem dos naipes e/ou cartas, pois
		a imagem das cartas será indexada segundo esta ordem.
		Se a ordem for modificada, a imagem deve ser modificada
		para refletir os novos índices.
	*/	

	
	/** 
		Naipes
	*/

	public final static byte PAUS = 0, COPAS = 1, ESPADAS = 2, OUROS = 3;	
	
	/** 
		Largura da imagem da carta
	*/
	public final static byte LARGURA_CARTA = 40;

	/** 
		Altura da imagem da carta
	*/

	public final static  byte ALTURA_CARTA = 54;	

	/*
		PAUS --> "árvore"
		COPAS --> Coração
		
	*/

	//valores das cartas --> Não mude aqui ! (utilizado como índice de vetores na classe baralho
	
	/** 
		Valores das cartas
	*/

	public final static byte CORINGA = 0, DOIS = 1, TRES = 2, QUATRO = 3, 
	CINCO = 4, SEIS = 5, SETE = 6, OITO = 7, NOVE = 8, DEZ = 9, DAMA = 10,
	VALETE = 11, REI = 12, AS = 13, VERSO = 14;


	/**
		Variáveis utilizadas em loops	
	*/
	public final static byte PRIMEIRO_VALOR = CORINGA,
				 ULTIMO_VALOR = AS;
	public final static byte PRIMEIRO_NAIPE = PAUS,
				 ULTIMO_NAIPE = OUROS;

	//naipe e valor da instância atual
	/** 
		Largura da imagem da carta
	*/

	/** 
		Naipe da carta atual
	*/
	private byte naipe;

	/** 
		Valor da imagem da carta
	*/
	private byte valor;

	
		
	/** 
		Construtor
	*/

	public Carta(Image imagem, byte naipe, byte valor){
		super(imagem);
		this.naipe = naipe;
		this.valor = valor;
		

	}

	/** 
		Modifica o naipe da carta
	*/

	public void setNaipe(byte naipe){
		this.naipe = naipe;
	}

	/** 
		Modifica o valor da carta
	*/

	public void setValor(byte valor){
		this.valor = valor;
	}

	/** 
		Retorna o naipe da carta
	*/
	public byte getNaipe(){
		return naipe;
	}

	/** 
		Retorna o valor da carta
	*/
	public byte getValor(){
		return valor;
	}

	/** 
		Retorna uma representação do naipe da carta num objeto String
	*/
	public String getNaipeStr(){
		StringBuffer str = new StringBuffer(" Naipe: ");

		switch(naipe){
			case PAUS:	
				str.append("PAUS");
				break;
			case COPAS:	
				str.append("COPAS");
				break;
			case ESPADAS:	
				str.append("ESPADAS");
				break;
			case OUROS:	
				str.append("OUROS");
				break;
			case VERSO:
				str.append("VERSO");
				break;
			default:
				str.append("INDEFINIDO");
		
		}

		return str.toString().trim();

	}

	/** 
		Retorna uma representação do valor da carta num objeto String
	*/
	public String getValorStr(){
		StringBuffer str = new StringBuffer(" Valor: ");

		switch(valor){
			case CORINGA:
				str.append("CORINGA");
				break;
			case DOIS:
				str.append("DOIS");
				break;
			case TRES:
				str.append("TRES");
				break;
			case QUATRO:
				str.append("QUATRO");
				break;
			case CINCO:
				str.append("CINCO");
				break;
			case SEIS:
				str.append("SEIS");
				break;
			case SETE:
				str.append("SETE");
				break;
			case OITO:
				str.append("OITO");
				break;
			case NOVE:
				str.append("NOVE");
				break;
			case DEZ:
				str.append("DEZ");
				break;
			case DAMA:
				str.append("DAMA");
				break;
			case VALETE:
				str.append("VALETE");
				break;
			case REI:
				str.append("REI");
				break;
			case AS:
				str.append("AS");
				break;
			default:
				str.append("INDEFINIDO");			

		}

		return str.toString().trim();

	}

	/** 
		Retorna uma representação da carta num objeto String
	*/
	public String toString(){
		StringBuffer str = new StringBuffer("Carta--> Naipe: ");

		switch(naipe){
			case PAUS:	
				str.append("PAUS");
				break;
			case COPAS:	
				str.append("COPAS");
				break;
			case ESPADAS:	
				str.append("ESPADAS");
				break;
			case OUROS:	
				str.append("OUROS");
				break;
			case VERSO:
				str.append("VERSO");
				break;
			default:
				str.append("INDEFINIDO");
		
		}

		str.append(" Valor: ");

		switch(valor){
			case CORINGA:
				str.append("CORINGA");
				break;
			case DOIS:
				str.append("DOIS");
				break;
			case TRES:
				str.append("TRES");
				break;
			case QUATRO:
				str.append("QUATRO");
				break;
			case CINCO:
				str.append("CINCO");
				break;
			case SEIS:
				str.append("SEIS");
				break;
			case SETE:
				str.append("SETE");
				break;
			case OITO:
				str.append("OITO");
				break;
			case NOVE:
				str.append("NOVE");
				break;
			case DEZ:
				str.append("DEZ");
				break;
			case DAMA:
				str.append("DAMA");
				break;
			case VALETE:
				str.append("VALETE");
				break;
			case REI:
				str.append("REI");
				break;
			case AS:
				str.append("AS");
				break;
			case VERSO:
				str.append("VERSO");
				break;
			default:
				str.append("INDEFINIDO");			

		}

		return str.toString().trim();
	}

	/** 
		Verifica a igualdade entre duas instâncias da classe
	*/
	public boolean equals(Object obj){
		if(! (obj instanceof Carta))
			return false;

		else
			return (this.naipe == ((Carta)obj).naipe)
			&& (this.valor == ((Carta)obj).valor);
	}

	
	


}