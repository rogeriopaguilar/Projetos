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


package br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.listener;

import java.util.Map;

import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.exception.ListenerException;

/**
 * Listener que deve ser implementado para ser notificado sobre a leitura dos dados das colunas especificadas <br>
 * na string contendo o padrão de leitura passada no construtor do objeto leitor. É possível acessar um vo    <br>
 * contendo os dados da célula ou o objeto original da API POI que representa a coluna que será processada    <br>
 * através das constantes definidas nesta classe															  <br>
 * 
 * @version 0.1
 * @author Rogério de Paula Aguilar
 * */
public interface ColunaListener {
		
		/**
		 * O mapa dadosColuna passado no método lendoColuna possui em uma de suas entradas um objeto <br>
		 * do tipo br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.util.LinhaColunaListenerVo  <br>
		 * que encapsula os dados da coluna que será processada. Este objeto encontra-se no mapa     <br>
		 * sob esta chave																 			 <br>
		 * 
		 * @since 0.1
		 * */   
		public static final String CHAVE_VO_COLUNA = "CHAVE_VO_COLUNA";
	
		/**
		 * O mapa dadosColuna passado no método lendoColuna possui em uma de suas entradas o objeto <br>
		 * original da API POI para a coluna que será precessada. A chave para este objeto no mapa	<br>
		 * é a constante definida abaixo. Desta forma, se for necessário acessar o objeto original  <br>
		 * da api POI, isto pode ser feito da seguinte forma dentro do método lendoColuna:			<br>
		 * 																							<br>
		 *  org.apache.poi.hssf.usermodel.HSSFCell celula = 										<br>
		 *			(org.apache.poi.hssf.usermodel.HSSFCell)										<br>
		 * 		dadosColuna.get(ColunaListener.CHAVE_COLUNA_OBJETO_ORIGINAL_POI)					<br>
		 * 
		 * @since 0.1
		 * */
		public static final String CHAVE_COLUNA_OBJETO_ORIGINAL_POI = "OBJETO_COLUNA_ORIGINAL_POI";

		/**
		 * O método abaixo deve ser implementado por alguma classe que esteja interessada em ser notificada <br>
		 * sobre a leitura de uma das colunas informadas na String de leitura no objeto LeitorExcel criado  <br>
		 * 
		 * @param linha  índice da linha que será processada	
		 * @param coluna  índice da coluna que será processada
		 * @param dadosColuna  Mapa contendo os dados da coluna que será processada. O mapa contém um vo do tipo
		 * br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.util.LinhaColunaListenerVo que possui os dados da
		 * coluna que será processada sob a chave ColunaListener.CHAVE_VO_COLUNA e o objeto original da API POI sob 
		 * a chave ColunaListener.CHAVE_COLUNA_OBJETO_ORIGINAL_POI	
		 * @throws ListenerException  exceção que deve ser lançada caso algum erro ocorra durante o processamento
		 * da coluna. Caso isto ocorra, todo o processamento da planilha é cancelado e a exceção é lançada para o chamador
		 * do método de processamento
		 * @return boolean indicando se o processamento da planilha deve continuar ou não
		 * 
		 * @since 0.1
		 * */
		public boolean lendoColuna(int linha, int coluna, Map dadosColuna) throws ListenerException;
		
}
