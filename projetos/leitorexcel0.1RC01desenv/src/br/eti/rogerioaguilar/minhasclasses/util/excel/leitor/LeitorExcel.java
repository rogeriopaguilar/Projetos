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

package br.eti.rogerioaguilar.minhasclasses.util.excel.leitor;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.exception.ListenerException;
import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.exception.PlanilhaNaoEncontradaException;
import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.gramatica.impl.LeitorExcelReader;
import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.gramatica.impl.ParseException;
import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.listener.ColunaListener;
import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.listener.LinhaListener;
import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.util.LinhaColunaListenerVo;

/**
 * Classe que faz a leitura dos dados de um planilha do excel de acordo com a string passada como parâmetro <br>
 * no construtor da classe. Esta string segue a seguinte gramática:											<br>
 * 																											<br>
 * GRAMATICA := REG(-REG)* 																					<br>
 * REG := [linha|*|linha-linha, coluna(;coluna)* |*]														<br>	
 * linha := número inteiro ou *																				<br>
 * coluna := número inteiro ou *																			<br>
 * 																											<br>
 * Devem ser expecificados os índices das linhas e/ou colunas desejadas para que o programa faça a leitura  <br>
 * A primeira linha da planilha possui o índice 1 e a primeira coluna também possui este índice				<br>
 * Exemplos de utilização da string:																		<br>
 * 																											<br>
 * [*,*] --> O programa irá ler todas as linhas e colunas da planilha que possuírem algum conteúdo			<br>	
 * [1,*] --> O programa irá ler todas as colunas da primeira linha da planilha								<br>
 * [2,5] --> o programa irá ler a coluna 5 da segunda linha da planilha										<br>
 * [2,1;2] --> O programa irá ler as colunas 1 e dois da segunda linha da planilha							<br>
 * [*,1] --> O programa irá ler todas as colunas com índice 1 de todas as linhas da planilha 				<br>
 * [*,4;5] --> O programa irá ler as colunas com índice 4 e 5 de todas as linhas da planilha				<br>
 * 			 algum conteúdo																					<br>
 * [1-10,1] --> O programa irá ler a coluna 1 de todas as linhas que estiverem entre os índices 1 e 10 		<br>
 * 				que possuam conteúdo 																		<br>
 * [1-10,1;2;3] --> O programa irá ler as colunas 1, 2 e 3 de todas as linhas que estiverem entre os índices<br>
 *  			1 e 10																						<br>
 * 				que possuam conteúdo 																		<br>
 * [1,1;2;3;4;5] --> O programa irá ler as colunas 1, 2, 3, 4 e 5 da primeira linha da planilha				<br>
 * [1,1;2]-[5,4;5] --> O programa irá ler as colunas 1 e dois da primeira linha da planilha e as colunas    <br>
 * 					   4 e 5 da linha 5																		<br>
 * OBS: A combinação acima pode ser repetida para diferentes configurações de linha quantas vezes			<br>
 * for necessário.																							<br>
 * 																											<br>
 * Exemplo de configurações diferentes para várias linhas de uma planilha:									<br>
 * [1,1;2]-[2,10;20]-[5,3;7;15]-[7,1;4;7;8;20;30]															<br>
 * 																											<br>
 * A classe possui dois construtores que devem ser utilizados para inicializar a leitura de uma planilha.	<br>
 * Além da String contendo as linhas e colunas que devem ser lidas, as outras informações que devem ser 	<br>
 * passadas para os construtores são:																			<br>		
 * 																											<br>
 * indicePlanilha --> Índice da planilha que será lida no arquivo (a primeira planilha tem o índice 1)		<br>
 * 
 * caminhoArquivoExcel (primeiro construtor) --> caminho completo do arquivo da planilha excel				<br>
 * 													OU														<br>
 * streamArquivo (segundo construtor) --> InputStream para uma planilha										<br>
 * 
 * 
 * linhaListener --> Alguma classe que implemente a interface LinhaListener e que será chamada pelo leitor	<br>
 * 					 quando este ler umas das linhas especificadas na string de leitura						<br>
 * colunaListener --> Alguma classe que implemente a interface ColunaListener e que será chamada pelo leitor<br>															
 *					 quando este ler uma das colunas especificadas na string de leitura 					<br>
 *																											<br>
 * Após criar o objeto Leitor com os parâmetros definidos acima, é necessário chamar o método 				<br>
 * processarLeituraPlanilha, que irá ler as linhas e colunas especificadas e chamar os listeners correspondentes <br>
 * com as informações encontradas na linha e/ou coluna.														<br>
 * 																											<br>
 * Fluxo de execução:																						<br>
 * 																											<br>
 * 1 - O leitor abre a planilha																				<br>
 * 2 - o leitor começa a ler as linhas e colunas da planilha												<br>
 * 3 - Para cada linha encontrada:																			<br>
 * 			3.1 - Caso a linha esteja na lista de linhas que devem ser processadas passada ao construtor:			<br>
 * 				     3.1.1 - O leitor monta uma lista de vos contendos os dados das colunas para a linha atual que		<br>
 * 					 		 estejam dentro do padrão de leitura passado ao construtor									<br>
 * 					 3.1.2 - O leitor chama o método LinhaListener.lendoLinha passando o mapa com os dados da linha		<br>
 * 							 e aguarda o final do processamento.														<br>
 * 							 3.1.2.1 - Caso o método do listener retorne true, o leitor continua o processamento da     <br>
 * 									   da planilha. Caso contrário, o processamento da planilha é interrompido			<br>
 * 							  		   Se o processamento da planilha continuar (de acordo com o parâmetro de retorno	<br>
 * 									   do listener para a linha descrito anteriormente), o leitor chama o listener para <br>
 * 									   a coluna para cada coluna da linha atual. O comportamento deste listener é o mesmo <br>
 * 									   do listener para a linha, ou seja, se o listener retornar false o processamento da <br>
 * 									   planilha é interrompido.															  <br>	
 * 																														 <br>			
 * Exemplo de utilização:																																	<br>			
 * 																																								<br>				
 *1 - Classe Listener que imprime os dados da linha no console:																									<br>
 *																																								<br>	
 * public class ConsoleLinhaListener implements LinhaListener {																									<br>
 *																																								<br>
 *    public boolean lendoLinha(int linha, Map dadosLinha)																										<br>
 *			throws ListenerException {																															<br>
 *		System.out.println("***************************************************Listener linha Teste***************************************************");		<br>
 *		System.out.println("Linha --> " + linha);																												<br>
 *		System.out.println("Dados linha (MAPA) --> " + dadosLinha);																								<br>	
 *	 	System.out.println("Lista de vos com os dados da linha --> " + dadosLinha.get(LinhaListener.LISTA_VOS_LINHA));											<br>
 *		System.out.println("Objeto original da api poi --> " + dadosLinha.get(LinhaListener.CHAVE_LINHA_OBJETO_ORIGINAL_POI));									<br>		
 *		System.out.println("**************************************************************************************************************************");		<br>
 *		return true;																																			<br>
 *	}																																							<br>
 * }																																							<br>					
 * 																																								<br>
 * 2 - Classe Listener que imprime os dados da coluna no console:																								<br>
 * 																																								<br>
 * public class ConsoleColunaListener implements ColunaListener {																								<br>
 *    public boolean lendoColuna(int linha, int coluna, Map dadosColuna)																						<br>
 *			throws ListenerException {																															<br>
 *		System.out.println("###################################################Coluna Teste###################################################");				<br>	
 *		System.out.println("Linha --> " + linha);																												<br>
 *		System.out.println("Coluna --> " + coluna);																												<br>
 *		System.out.println("Vo contendo os dados da coluna --> " + dadosColuna.get(ColunaListener.CHAVE_VO_COLUNA));											<br>	
 *		System.out.println("Objeto original da API POI--> " + dadosColuna.get(ColunaListener.CHAVE_COLUNA_OBJETO_ORIGINAL_POI));								<br>
 *		System.out.println("Dados coluna --> " + dadosColuna);																									<br>
 *		LinhaColunaListenerVo voAtual = (LinhaColunaListenerVo) dadosColuna.get(ColunaListener.CHAVE_VO_COLUNA);												<br>
 *		if(voAtual.isCelulaContemErro()){													  																	<br>
 *		  	System.out.println("Erro -->" + voAtual.getErro());								  																	<br>
 *		}else if(voAtual.isCelulaNula()){													  																	<br>		
 *			System.out.println("Célula é nula!");											  																	<br>
 *		}else if(voAtual.isCelulaBranca()){												  																		<br>
 *			System.out.println("Célula é branca!");											  																	<br>	
 *		}else if(voAtual.isCelulaFormula()){												 																	<br>
 *			System.out.println("Fórmula --> " + voAtual.getValorStrFormula());				  																	<br>
 *		}else if(voAtual.getValorNumerico() != null){										 																	<br>
 *			System.out.println("Valor Numérico --> " + voAtual.getValorNumerico());		      																	<br>
 *		}else if(voAtual.getValorBoolean() != null){										  																	<br>
 *			System.out.println("Valor booleano --> " + voAtual.getValorBoolean());		      																	<br>
 *		}else if(voAtual.getValorStr() != null){																												<br>
 *			System.out.println("Valor String --> " + voAtual.getValorStr());		     																		<br>
 *		}																																						<br>
 *		System.out.println("##################################################################################################################");				<br>			
 *		return true;																																			<br>
 *	}																																							<br>
 * }																																							<br>
 * 																																								<br>
 *	3 - Chamada para o leitor processar a leitura da planilha com os listeners definidos anteriormente:															<br> 
 *																																								<br>
 *			InputStream is = new FileInputStream("/usr/local/java/temp/teste.xls");																							<br>
 *			LeitorExcel leitor = new LeitorExcel("[1,1;2]-[2,10;20]-[5,3;7;15]-[7,1;4;7;8;20;30]", 1, is, 																								<br>
 *						new ConsoleLinhaListener(), new ConsoleColunaListener()																					<br>
 *					);																																			<br>
 *			leitor.processarLeituraPlanilha();																													<br>
 *																																								<br>
 * Existe uma classe que pode ser utilizada para testes:																										<br>	
 * 																																								<br>
 * Forma de utilização:																																			<br>
 * 1 - Direto na classe:																																		<br>
 * 		java br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.teste.TesteLeitorExcel indicePlanilha padrão de leitura  caminho do arquivo					<br>
 * Exemplo 																																						<br>																			
 * 		java br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.teste.TesteLeitorExcel 1 "[5,5]-[6-27,1;10]" "/usr/local/java/temp/teste.xls"				<br>
 * 																																								<br>
 * 2 - Através do jar da aplicação:																																<br>
 * 		java -jar leitor-excel-0.1-RC01.jar indicePlanilha padrão de leitura  caminho do arquivo 															<br>		
 * 																																								<br>
 * Exemplo 																																						<br>
 * 		java -jar leitor-excel-0.1-RC01.jar 1 "[5,5]" "/usr/local/java/temp/teste.xls" 																						<br>					
 * 
 * 
 * 
 * 
 * 
 *
 * @see br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.util.LinhaColunaListenerVo																																							<br>					
 * @version 0.1																					
 * @author Rogério de Paula Aguilar
 */

public class LeitorExcel{
    static Logger log = Logger.getLogger(LeitorExcel.class);

	
	private String strPadraoLeitura = "[*,*]";	
	private int indicePlanilha		= 1;
	private String caminhoArquivoExcel = null;
	private LinhaListener linhaListener = null;
	private ColunaListener colunaListener = null;
	private InputStream streamArquivo = null;
	
	
	/**
	 * Construtor 
	 * @param strPadraoLeitura padrão para identificar as linhas e colunas que devem ser processadas
	 * @param indicePlanilha	índice da planilha que será processada, começando em 1
	 * @param caminhoArquivoExcel caminho completo do arquivo que será processado 
	 * @param linhaListener  classe que receberá notificações sobre a leitura das linhas
	 * @param colunaListener classe que receberá notificações sobre a leitura das colunas
	 */
	public LeitorExcel(String strPadraoLeitura, int indicePlanilha, String caminhoArquivoExcel, LinhaListener linhaListener, ColunaListener colunaListener){ 
		super();
		this.strPadraoLeitura = strPadraoLeitura;
		this.indicePlanilha = indicePlanilha;
		this.caminhoArquivoExcel = caminhoArquivoExcel;
		this.linhaListener = linhaListener;
		this.colunaListener = colunaListener;
	}

	/**
	 * Construtor 
	 * @param strPadraoLeitura padrão para identificar as linhas e colunas que devem ser processadas
	 * @param indicePlanilha	índice da planilha que será processada, começando em 1
	 * @param streamArquivo Objeto InputStream contendo os dados do arquivo que será processado
	 * @param linhaListener  classe que receberá notificações sobre a leitura das linhas
	 * @param colunaListener classe que receberá notificações sobre a leitura das colunas
	 */
	public LeitorExcel(String strPadraoLeitura, int indicePlanilha, InputStream streamArquivo, LinhaListener linhaListener, ColunaListener colunaListener){ 
		super();
		this.strPadraoLeitura = strPadraoLeitura;
		this.indicePlanilha = indicePlanilha;
		this.streamArquivo = streamArquivo;
		this.linhaListener = linhaListener;
		this.colunaListener = colunaListener;
	}

	
	public String toString(){
		return ReflectionToStringBuilder.reflectionToString(this);
	}
	
	private List getListaColunasLinha(String idLinha, Map mapaLinhasAProcessar){
		Set setLinhas = (Set)mapaLinhasAProcessar.get(idLinha);
		List listaColunas = null;
		if(setLinhas != null) listaColunas = new LinkedList(setLinhas);
		return listaColunas;
	}
	
	/**
	 * Realiza o processamento de leitura seguindo o fluxo:
	 * 
	 * 1 - O leitor abre a planilha																				<br>
	 * 2 - o leitor começa a ler as linhas e colunas da planilha												<br>
	 * 3 - Para cada linha encontrada:																			<br>
	 * 			3.1 - Caso a linha esteja na lista de linhas que devem ser processadas passada ao construtor:			<br>
	 * 				     3.1.1 - O leitor monta uma lista de vos contendos os dados das colunas para a linha atual que		<br>
	 * 					 		 estejam dentro do padrão de leitura passado ao construtor									<br>
	 * 					 3.1.2 - O leitor chama o método LinhaListener.lendoLinha passando o mapa com os dados da linha		<br>
	 * 							 e aguarda o final do processamento.														<br>
	 * 							 3.1.2.1 - Caso o método do listener retorne true, o leitor continua o processamento da     <br>
	 * 									   da planilha. Caso contrário, o processamento da planilha é interrompido			<br>
	 * 							  		   Se o processamento da planilha continuar (de acordo com o parâmetro de retorno	<br>
	 * 									   do listener para a linha descrito anteriormente), o leitor chama o listener para <br>
	 * 									   a coluna para cada coluna da linha atual. O comportamento deste listener é o mesmo <br>
	 * 									   do listener para a linha, ou seja, se o listener retornar false o processamento da <br>
	 * 									   planilha é interrompido.	
	 * 
	 * @throws ParseException
	 * @throws PlanilhaNaoEncontradaException caso o índice da planilha não seja encontrado no arquivo
	 * @throws FileNotFoundException caso o arquivo passado como parâmetro não exista
	 * @throws ListenerException caso ocorra algum erro na chamada de algum dos listeners
	 * @throws IOException caso ocorra algum problema de io
	 * */
	public void processarLeituraPlanilha() throws ParseException, PlanilhaNaoEncontradaException, FileNotFoundException,IOException, ListenerException{
			
		try{
			log.debug("Inicializando o processamento da leitura do arquivo...");
			log.debug("Dados para o processamento --> " + this);
			POIFSFileSystem fs = null; 
			if(this.streamArquivo != null){
				fs = new POIFSFileSystem(streamArquivo);
			}else if(this.caminhoArquivoExcel != null){
				fs = new POIFSFileSystem(new FileInputStream(this.caminhoArquivoExcel));
			}else{
				throw new IllegalArgumentException("Não foi definido um stream para o arquivo nem um caminho para o arquivo!");
			}
			log.debug("Processando a string de entrada --> " + this.strPadraoLeitura);
			Map mapaLinhasAProcessar = LeitorExcelReader.getMapaEntradas(
																			new ByteArrayInputStream(
																					this.strPadraoLeitura.getBytes()
																									)
																		);
			log.debug("A string de entrada --> " + this.strPadraoLeitura + " foi processada com sucesso.");
			log.debug("Mapa retornado --> " + mapaLinhasAProcessar);

			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet planilha = wb.getSheetAt(this.indicePlanilha - 1);			
			if(planilha == null){
				log.error("Planilha não encontrada no índice -->" + this.indicePlanilha);
				throw new PlanilhaNaoEncontradaException("Planilha não encontrada no índice -->" + this.indicePlanilha);
			}else{	
				log.debug("A string de entrada --> " + this.strPadraoLeitura + " foi processada com sucesso.");
				boolean processarTodasAsLinhas = (mapaLinhasAProcessar.get("*") != null);
				boolean processarTodasAsColunas = false;
			    boolean continuarProcessamentoLinha = true;
				Map propriedadesListenerLinha = new HashMap();
				Map propriedadesListenerColuna = new HashMap();
				List listaColunas = null;
				List listaVosColunas = new LinkedList();
				if(processarTodasAsLinhas){
					log.debug("Processando todas as linhas...");
				}
				Iterator itLinhas = planilha.rowIterator();
				while( itLinhas.hasNext() && continuarProcessamentoLinha) {          
	                HSSFRow linha = (HSSFRow) itLinhas.next();
					propriedadesListenerLinha.clear();
					listaVosColunas.clear();
					propriedadesListenerLinha.put(LinhaListener.CHAVE_LINHA_OBJETO_ORIGINAL_POI, linha);
					int intLinhaAtual = linha.getRowNum() + 1;
					
					log.debug("Processando linha --> " + intLinhaAtual);
					if(!processarTodasAsLinhas){
						listaColunas = getListaColunasLinha("" + intLinhaAtual, mapaLinhasAProcessar);
					}else{
						listaColunas = getListaColunasLinha("*", mapaLinhasAProcessar);
					}
					boolean processarLinhaAtual = processarTodasAsLinhas || (listaColunas != null);
					if(processarLinhaAtual){
						Iterator itColunas = linha.cellIterator();
						processarTodasAsColunas = (listaColunas != null) && (listaColunas.size() > 0) && ("" + listaColunas.get(0)).equals("*"); 
						while( itColunas.hasNext() ) {
							  HSSFCell celula = (HSSFCell) itColunas.next();
							  int intCelulaAtual = celula.getCellNum() + 1;
							  boolean processarColunaAtual = processarTodasAsColunas || 
							  ( (listaColunas != null) && (listaColunas.size() > 0) && listaColunas.indexOf(new Long(intCelulaAtual)) != -1);
							  LinhaColunaListenerVo linhaColunaListenerVo = new LinhaColunaListenerVo();
							  linhaColunaListenerVo.setLinha(intLinhaAtual);
							  linhaColunaListenerVo.setColuna(intCelulaAtual);
							  linhaColunaListenerVo.setCelulaAtual(celula); 
							  if(processarColunaAtual){
								if(celula != null){
								  log.debug("Coluna --> " + intCelulaAtual + " para a linha --> " + intLinhaAtual + " deve ser processada...");
								  switch(celula.getCellType()){
										case HSSFCell.CELL_TYPE_STRING:
											linhaColunaListenerVo.setValorStr(celula.getStringCellValue());
											break;
										case HSSFCell.CELL_TYPE_NUMERIC:	
											linhaColunaListenerVo.setValorNumerico(new Double(celula.getNumericCellValue()));
											break;
										case HSSFCell.CELL_TYPE_FORMULA:
											linhaColunaListenerVo.setCelulaFormula(true);
											linhaColunaListenerVo.setValorStrFormula(celula.getCellFormula());
											break;
										case HSSFCell.CELL_TYPE_ERROR:
											linhaColunaListenerVo.setCelulaContemErro(true);
											linhaColunaListenerVo.setErro(new Byte(celula.getErrorCellValue()));
											break;
										case HSSFCell.CELL_TYPE_BOOLEAN:
											linhaColunaListenerVo.setValorBoolean(new Boolean(celula.getBooleanCellValue()));
											break;
										case HSSFCell.CELL_TYPE_BLANK:
											linhaColunaListenerVo.setCelulaBranca(true);
											linhaColunaListenerVo.setValorStr("");
											break;
									}
								}else{
									log.warn("Célula é nula!");
									linhaColunaListenerVo.setCelulaNula(true);
								}
								listaVosColunas.add(linhaColunaListenerVo);
							  }else{
								log.debug("Coluna --> " + intCelulaAtual + " para a linha --> " + intLinhaAtual + " não deve ser processada...");
							  }
						}
						if(this.linhaListener != null){
							log.debug("Chamando o listener para a linha --> " + intLinhaAtual);
							Collections.sort(listaVosColunas,
									new Comparator(){
										public int compare(Object arg0, Object arg1) {
											int colunaUm = ((LinhaColunaListenerVo) arg0).getColuna();
											int colunaDois = ((LinhaColunaListenerVo) arg1).getColuna();
											if(colunaUm < colunaDois){
												return -1;
											}else if(colunaUm > colunaDois){
												return 1;
											}
											return 0;
										}
									}
							);
							propriedadesListenerLinha.put(LinhaListener.LISTA_VOS_LINHA, listaVosColunas);
							continuarProcessamentoLinha = this.linhaListener.lendoLinha(intLinhaAtual, propriedadesListenerLinha);
						    if(!continuarProcessamentoLinha){
								log.debug("Listener retornou boolean false indicando que o processamento deve ser interrompido!");
						    }
						}else{
							log.debug("Listener não configurado para a linha --> " + intLinhaAtual);
						}
						if(this.colunaListener != null){
							Iterator itColunasVoListener = listaVosColunas.iterator();
						    boolean continuarProcessamentoColunasnaLinha = true;
							while(itColunasVoListener.hasNext() && continuarProcessamentoColunasnaLinha){
								propriedadesListenerColuna.clear();
								LinhaColunaListenerVo voAtual = (LinhaColunaListenerVo) itColunasVoListener.next();
								propriedadesListenerColuna.put(ColunaListener.CHAVE_VO_COLUNA, voAtual);
								propriedadesListenerColuna.put(ColunaListener.CHAVE_COLUNA_OBJETO_ORIGINAL_POI, voAtual.getCelulaAtual());
								log.debug("Chamando o listener para a coluna --> " + voAtual.getColuna() + " na linha " + voAtual.getLinha());
							    continuarProcessamentoColunasnaLinha = this.colunaListener.lendoColuna(voAtual.getLinha(), voAtual.getColuna(), propriedadesListenerColuna);
							    if(!continuarProcessamentoColunasnaLinha){
									log.debug("Listener de coluna retornou boolean false indicando que o processamento das colunas na linha " + voAtual.getLinha() + " deve ser interrompido!");
							    }
							}
						}else{
							log.debug("Listener não configurado para processamento das colunas");
						}
					}else{
						log.debug("Linha --> " + intLinhaAtual + " não será processada!");
					}
				}
			}
			log.debug("Processamento da planilha realizado com sucesso!");
		}catch(ParseException e){
			e.printStackTrace();
			log.error("Erro ao processar a string de entrada ", e);
			throw e;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("Arquivo " + this.caminhoArquivoExcel + " não encontrado", e);
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Erro ao abrir o arquivo " + this.caminhoArquivoExcel, e);
			throw e;
		} catch (ListenerException e) {
			e.printStackTrace();
			log.error("Erro ao processar o listener " , e);
			throw e;
		}
	}
	
}
