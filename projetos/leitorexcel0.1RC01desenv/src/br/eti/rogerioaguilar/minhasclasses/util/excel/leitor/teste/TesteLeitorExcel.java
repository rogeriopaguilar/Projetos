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


package br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.teste;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;

import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.LeitorExcel;
import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.teste.listener.ConsoleColunaListener;
import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.teste.listener.ConsoleLinhaListener;

/**
 * Classe utilizada para testes com os listeners que imprimem a informação das colunas			<br>
 * no console.																					<br>
 * Forma de utilização:																		<br>
 * 1 - Direto na classe:	
 * 		java br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.teste.TesteLeitorExcel <indicePlanilha> <padrão de leitura>  <caminho do arquivo> <br>
 * Exemplo <br>
 * 		java br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.teste.TesteLeitorExcel 1 "[5,5]-[6-27,1;10]" "/usr/local/java/temp/teste.xls"	<br>
 * 																																					<br>
 * 2 - Através do jar da aplicação:																													<br>
 * 		java -jar leitor-excel-0.1-RC01.jar <indicePlanilha> <padrão de leitura>  <caminho do arquivo> 												<br>		
 * 																																					<br>
 * Exemplo 																																			<br>
 * 		java -jar leitor-excel-0.1-RC01.jar 1 "[5,5]" "/usr/local/java/temp/teste.xls"																<br>					
 * 
 * @version 0.1
 * @author Rogério de Paula Aguilar
 * @since 0.1
 * */
public class TesteLeitorExcel {
	
	public static void main(String[] args) throws Exception{
		//PropertyConfigurator.configure("config/log4j.properties");
		if(args.length != 3){
			System.out.println("Exemplo de chamada do programa através da classe:");
			System.out.println("     java br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.teste.TesteLeitorExcel 1 \"[*,*]-[6-27,1;10]\" \"/usr/local/java/temp/teste.xls\"");
			System.out.println("Exemplo de chamada do programa através do jar:");
			System.out.println("     java -jar leitor-excel-0.1-RC01.jar 1 \"[*,*]\" \"/usr/local/java/temp/teste.xls\"");
			System.exit(0);
		}else{
			//Exemplo com stream
			//InputStream is = TesteLeitorExcel.class.getResourceAsStream(args[0]);
			InputStream is = new FileInputStream(args[2]);
			LeitorExcel leitor = new LeitorExcel(args[1], Integer.parseInt(args[0]), is, 
						new ConsoleLinhaListener(), new ConsoleColunaListener()
					);
			leitor.processarLeituraPlanilha();
		}
	}

}
