/*
 * Copyright 2006 Rog�rio de Paula Aguilar
 * 
 * This file is part of leitor-excel.
 *
 *   leitor-excel is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   leitor-excel is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Foobar; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * */


package br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.util;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Vo que encapsula os dados de uma c�lula da planilha. Nos mapas passados aos listeners, <br>
 * objetos deste tipo s�o passados sob chaves definidas nas interfaces para os listeners, <br>
 * contendo o tipo da c�lula e o valor para a mesma.									  <br>
 * 																						  <br>	
 * Exemplo de utiliza��o:																  <br>
 * 																						  <br>	
 *  LinhaColunaListenerVo voAtual = ...	 //Obtem o vo atual								  <br>
 *																						  <br>	   											
 *   if(voAtual.isCelulaContemErro()){													  <br>
 *   	System.out.println("Erro -->" + voAtual.getErro());								  <br>	
 *   }else if(voAtual.isCelulaNula()){													  <br>
 *   	System.out.println("C�lula � nula!");											  <br>	
 *   }else if(voAtual.isCelulaBranca()){												  <br>	
 *   	System.out.println("C�lula � branca!");											  <br>	
 *   }else if(voAtual.isCelulaFormula()){												  <br>	
 *   	System.out.println("F�rmula --> " + voAtual.getValorStrFormula());				  <br>	
 *   }else if(voAtual.getValorNumerico() != null){										  <br>
 *   	System.out.println("Valor Num�rico --> " + voAtual.getValorNumerico());		      <br>	
 *   }else if(voAtual.getValorBoolean() != null){										  <br>
 *   	System.out.println("Valor booleano --> " + voAtual.getValorBoolean());		      <br>	
 *   }else if(voAtual.getValorStr() != null){
 *   	System.out.println("Valor String --> " + voAtual.getValorStr());		      <br>	
 *   }
 *   
 * @version 0.1
 * @author Rog�rio de Paula Aguilar
 * */
public class LinhaColunaListenerVo implements Serializable{

	//�ndice da c�lula
	private int linha = -1;
	private int coluna = -1;
	
	//Tipo da c�lula
	private boolean celulaContemErro = false;
	private boolean celulaNula = false;
	private boolean celulaBranca = false;
	private boolean celulaFormula = false;
	
	//Se celulaContemErro == true, guarda o c�digo do erro
	private Byte erro;
	//Se valorNumero != null, significa que o valor da c�lula � um n�mero
	private Double valorNumerico;
	//Se valorBoolean != null, significa que a c�lula � booleana
	private Boolean valorBoolean;
	//Se valorStr != null, significa que a c�lula possui um valor do tipo String
	private String valorStr;
	//Se celulaFormula == true, cont�m o valor da f�rmula
	private String valorStrFormula;

	//Objeto original da API POI associado com a c�lula
	private HSSFCell celulaAtual = null;
	
	/**
	 * Retorna o objeto original da api POI
	 * */
	public HSSFCell getCelulaAtual() {
		return celulaAtual;
	}

	/**
	 * Modifica o objeto original da api poi relacionado com este VO
	 * @throws IllegalArgumentException se o objeto for modificado mais de uma vez
	 * */
	public void setCelulaAtual(HSSFCell celulaAtual) {
		if(this.celulaAtual == null){
			this.celulaAtual = celulaAtual;
		}else throw new IllegalArgumentException("Objeto original da api POI j� definido para este vo!");
	}

	/**
	 * Retorna o valor da c�lula como um objeto String.
	 * */
	public String getValorStr() {
		return valorStr;
	}

	/**
	 * Modifica o valor String deste vo
	 * @throws IllegalArgumentException caso o valor seja definido mais de uma vez
	 * */
	public void setValorStr(String valorStr) {
		if(this.valorStr == null){
			this.valorStr = valorStr;
		}else throw new IllegalArgumentException("Valor String para esta c�lula j� definido para este vo!");
	}

	/**
	 * Construtor padr�o
	 * */
	public LinhaColunaListenerVo() {
		super();
	}

	/**
	 * Retorna o �ndice da coluna 
	 * */
	public int getColuna() {
		return coluna;
	}
	
	/**
	 * Modifica o valor do �ndice da coluna
	 * @throws IllegalArgumentException se o �ndice for definido mais de uma vez ou se o �ndice for menor ou igual a 0
	 * */
	public void setColuna(int coluna) {
		if(this.coluna == -1){
			if(coluna > 0){
				this.coluna = coluna;
			}else throw new IllegalArgumentException("O �ndice para a coluna deve possuir um valor maior que zero!");
		}else throw new IllegalArgumentException("�ndice da coluna j� definido para este vo!");
		
	}
	
	/**
	 * Retorna o �ndice da linha
	 * */
	public int getLinha() {
		return linha;
	}
	
	/**
	 * Modifica o �ndice da linha
	 * @throws IllegalArgumentException se o �ndice for definido mais de uma vez ou se o �ndice for menor ou igual a zero
	 * */
	public void setLinha(int linha) {
		if(this.linha == -1){
			if(linha > 0){
				this.linha = linha;
			}else throw new IllegalArgumentException("O �ndice para a linha deve possuir um valor maior que zero!");
		}else throw new IllegalArgumentException("O �ndice da linha j� foi definido para este vo!");
	}
	
	/**
	 * Retorna o conte�do do vo num objeto String
	 * @see org.apache.commons.lang.builder.ReflectionToStringBuilder#ReflectionToStringBuilder(java.lang.Object)
	 * */
	public String toString(){
		return ReflectionToStringBuilder.reflectionToString(this);
	}

	public boolean isCelulaBranca() {
		return celulaBranca;
	}

	public void setCelulaBranca(boolean celulaBranca) {
		this.celulaBranca = celulaBranca;
	}

	public boolean isCelulaContemErro() {
		return celulaContemErro;
	}

	public void setCelulaContemErro(boolean celulaContemErro) {
		this.celulaContemErro = celulaContemErro;
	}

	public boolean isCelulaNula() {
		return celulaNula;
	}

	public void setCelulaNula(boolean celulaNula) {
		this.celulaNula = celulaNula;
	}

	public Byte getErro() {
		return erro;
	}

	public void setErro(Byte erro) {
		this.erro = erro;
	}

	public Boolean getValorBoolean() {
		return valorBoolean;
	}

	public void setValorBoolean(Boolean valorBoolean) {
		this.valorBoolean = valorBoolean;
	}

	public Double getValorNumerico() {
		return valorNumerico;
	}

	public void setValorNumerico(Double valorNumerico) {
		this.valorNumerico = valorNumerico;
	}

	public boolean isCelulaFormula() {
		return celulaFormula;
	}

	public void setCelulaFormula(boolean celulaFormula) {
		this.celulaFormula = celulaFormula;
	}

	public String getValorStrFormula() {
		return valorStrFormula;
	}

	public void setValorStrFormula(String valorStrFormula) {
		this.valorStrFormula = valorStrFormula;
	}


	
}
