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


package br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.teste;

import java.io.ByteArrayInputStream;

import org.apache.log4j.Logger;

import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.gramatica.impl.LeitorExcelReader;

/**
 * Classe utilizada para testar o parser gerado � partir da gram�tica definida										<br>
 * em br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.gramatica.definicao.LeitorExcel.jj						<br>
 * 																													<br>
 * 																													<br>	
 * Padr�es utilizados nos testes:																					<br>
 * 																													<br>	
 * 																													<br>
 * [*,*] --> O programa ir� ler todas as linhas e colunas da planilha que possu�rem algum conte�do					<br>
 * [1,*] --> O programa ir� ler todas as colunas da primeira linha da planilha										<br>	
 * [2,5] --> o programa ir� ler a coluna 5 da segunda linha da planilha												<br>
 * [2,1;2] --> O programa ir� ler as colunas 1 e dois da segunda linha da planilha									<br>
 * [*,1] --> O programa ir� ler todas as colunas com �ndice 1 de todas as linhas da planilha 						<br>
 * [*,4;5] --> O programa ir� ler as colunas com �ndice 4 e 5 de todas as linhas da planilha						<br>
 * 			 algum conte�do																							<br>
 * [1-10,1] --> O programa ir� ler a coluna 1 de todas as linhas que estiverem entre os �ndices 1 e 10 				<br>
 * 				que possuam conte�do 																				<br>
 * [1-10,1;2;3] --> O programa ir� ler as colunas 1, 2 e 3 de todas as linhas que estiverem entre os �ndices		<br>
 *  			1 e 10																								<br>
 * 				que possuam conte�do 																				<br>
 * [1,1;2;3;4;5] --> O programa ir� ler as colunas 1, 2, 3, 4 e 5 da primeira linha da planilha						<br>
 * [1,1;2]-[5,4;5] --> O programa ir� ler as colunas 1 e dois da primeira linha da planilha e as colunas    		<br>
 * 					   4 e 5 da linha 5																				<br>
 * Exemplo de configura��es diferentes para v�rias linhas de uma planilha:											<br>
 * [1,1;2]-[2,10;20]-[5,3;7;15]-[7,1;4;7;8;20;30]																	<br>
 * 																													<br>
 * @version 0.1																										
 * @author Rog�rio de Paula Aguilar
 * @since 0.1 
 * */
public class TesteParser {
	static Logger logger = Logger.getLogger(TesteParser.class);
	
	public static void main(String[] args) throws Exception{

		//PropertyConfigurator.configure("config/log4j.properties");

		String strPadroesTeste[] = {
				  "[*,*]",
				  "[1,*]",
				  "[2,5]",
				  "[2,1;2]",
				  "[*,1]",
				  "[*,4;5]",
				  "[1-10,1]",
				  "[1-10,1;2;3]",
				  "[1,1;2;3;4;5]",
				  "[1,1;2]-[5,4;5]",
				  "[1,1;2]-[2,10;20]-[5,3;7;15]-[7,1;4;7;8;20;30]"															
		};  
		
		for(int cont = 0; cont < strPadroesTeste.length; cont++){
			logger.debug("********************Testando parser para o padr�o --> " + strPadroesTeste[cont] + " ********************");	
			logger.debug(LeitorExcelReader.getMapaEntradas(new ByteArrayInputStream(strPadroesTeste[cont].getBytes())));	
			logger.debug("*********************************************************************************************************");
		}	
	}

}
