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


package br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.listener;

import java.util.Map;

import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.exception.ListenerException;

/**
 * Listener que deve ser implementado por alguma classe interessada em receber uma notifica��o 	<br>
 * quando o leitor ler uma das linhas definidas na String contendo o padr�o de leitura passada  <br>
 * ao construtor do objeto leitor.																<br>
 * 
 * 
 * @version 0.1
 * @author Rog�rio de Paula Aguilar
 * */

public interface LinhaListener {
	
	/**
	 * O mapa dadosLinha passado no m�todo lendoLinha possui em uma de suas entradas um objeto  				   <br>
	 * List contendo vos do tipo br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.util.LinhaColunaListenerVo  <br>
	 * que encapsula os dados das colunas que pertencem � linha processada. Este objeto encontra-se no mapa		   <br>
	 * sob esta chave																 			 				   <br>
	 * @since 0.1
	 * */   
	public static final String LISTA_VOS_LINHA = "LISTA_VOS_LINHA";
	
	/**
	 * O mapa dadosLinha passado como par�metro no m�todo lendoLinha possum em uma de suas entradas o objeto	<br>
	 * original da API POI que representa a linha processada.													<br>
	 * Exemplo de utiliza��o:																					<br>
	 * 																											<br>
	 *  org.apache.poi.hssf.usermodel.HSSFRow linha = 															<br>
	 *			(org.apache.poi.hssf.usermodel.HSSFRow)															<br>
	 * 		dadosColuna.get(LinhaListener.CHAVE_LINHA_OBJETO_ORIGINAL_POI)										<br>
	 * @since 0.1
	 * */
	public static final String CHAVE_LINHA_OBJETO_ORIGINAL_POI = "OBJETO_LINHA_ORIGINAL_POI";
	
	/**
	 * M�todo que deve ser implementado para receber notifica��es sobre a leitura das linha especificadas       <br>
	 * na String contendo o padr�o de leitura passada ao construtor do objeto leitor.							<br>
	 * 
	 * @param linha �ndice da linha lida
	 * @param dadosLinha mapa contendo os dados da linha. O mapa possui uma lista de vos registrada sob a chave 
	 * LinhaListener.LISTA_VOS_LINHA e o objeto original da api POI que representa a linha registrado sob a chave
	 * LinhaListener.CHAVE_LINHA_OBJETO_ORIGINAL_POI
	 * @throws ListenerException  exce��o que deve ser lan�ada caso algum erro ocorra durante o processamento
	 * da coluna. Caso isto ocorra, todo o processamento da planilha � cancelado e a exce��o � lan�ada para o chamador
	 * do m�todo de processamento
	 * @return boolean indicando se o processamento da planilha deve continuar ou n�o
	 * @since 0.1
	 * */
	public boolean lendoLinha(int linha, Map dadosLinha) throws ListenerException;
}
