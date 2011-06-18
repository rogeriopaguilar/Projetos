/*
 * Copyright 2006 Rogério de Paula Aguilar
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
 * Vo que encapsula os dados de uma célula da planilha. Nos mapas passados aos listeners, <br>
 * objetos deste tipo são passados sob chaves definidas nas interfaces para os listeners, <br>
 * contendo o tipo da célula e o valor para a mesma.									  <br>
 * 																						  <br>	
 * Exemplo de utilização:																  <br>
 * 																						  <br>	
 *  LinhaColunaListenerVo voAtual = ...	 //Obtem o vo atual								  <br>
 *																						  <br>	   											
 *   if(voAtual.isCelulaContemErro()){													  <br>
 *   	System.out.println("Erro -->" + voAtual.getErro());								  <br>	
 *   }else if(voAtual.isCelulaNula()){													  <br>
 *   	System.out.println("Célula é nula!");											  <br>	
 *   }else if(voAtual.isCelulaBranca()){												  <br>	
 *   	System.out.println("Célula é branca!");											  <br>	
 *   }else if(voAtual.isCelulaFormula()){												  <br>	
 *   	System.out.println("Fórmula --> " + voAtual.getValorStrFormula());				  <br>	
 *   }else if(voAtual.getValorNumerico() != null){										  <br>
 *   	System.out.println("Valor Numérico --> " + voAtual.getValorNumerico());		      <br>	
 *   }else if(voAtual.getValorBoolean() != null){										  <br>
 *   	System.out.println("Valor booleano --> " + voAtual.getValorBoolean());		      <br>	
 *   }else if(voAtual.getValorStr() != null){
 *   	System.out.println("Valor String --> " + voAtual.getValorStr());		      <br>	
 *   }
 *   
 * @version 0.1
 * @author Rogério de Paula Aguilar
 * */
public class LinhaColunaListenerVo implements Serializable{

	//Índice da célula
	private int linha = -1;
	private int coluna = -1;
	
	//Tipo da célula
	private boolean celulaContemErro = false;
	private boolean celulaNula = false;
	private boolean celulaBranca = false;
	private boolean celulaFormula = false;
	
	//Se celulaContemErro == true, guarda o código do erro
	private Byte erro;
	//Se valorNumero != null, significa que o valor da célula é um número
	private Double valorNumerico;
	//Se valorBoolean != null, significa que a célula é booleana
	private Boolean valorBoolean;
	//Se valorStr != null, significa que a célula possui um valor do tipo String
	private String valorStr;
	//Se celulaFormula == true, contém o valor da fórmula
	private String valorStrFormula;

	//Objeto original da API POI associado com a célula
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
		}else throw new IllegalArgumentException("Objeto original da api POI já definido para este vo!");
	}

	/**
	 * Retorna o valor da célula como um objeto String.
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
		}else throw new IllegalArgumentException("Valor String para esta célula já definido para este vo!");
	}

	/**
	 * Construtor padrão
	 * */
	public LinhaColunaListenerVo() {
		super();
	}

	/**
	 * Retorna o índice da coluna 
	 * */
	public int getColuna() {
		return coluna;
	}
	
	/**
	 * Modifica o valor do índice da coluna
	 * @throws IllegalArgumentException se o índice for definido mais de uma vez ou se o índice for menor ou igual a 0
	 * */
	public void setColuna(int coluna) {
		if(this.coluna == -1){
			if(coluna > 0){
				this.coluna = coluna;
			}else throw new IllegalArgumentException("O índice para a coluna deve possuir um valor maior que zero!");
		}else throw new IllegalArgumentException("Índice da coluna já definido para este vo!");
		
	}
	
	/**
	 * Retorna o índice da linha
	 * */
	public int getLinha() {
		return linha;
	}
	
	/**
	 * Modifica o índice da linha
	 * @throws IllegalArgumentException se o índice for definido mais de uma vez ou se o índice for menor ou igual a zero
	 * */
	public void setLinha(int linha) {
		if(this.linha == -1){
			if(linha > 0){
				this.linha = linha;
			}else throw new IllegalArgumentException("O índice para a linha deve possuir um valor maior que zero!");
		}else throw new IllegalArgumentException("O índice da linha já foi definido para este vo!");
	}
	
	/**
	 * Retorna o conteúdo do vo num objeto String
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
