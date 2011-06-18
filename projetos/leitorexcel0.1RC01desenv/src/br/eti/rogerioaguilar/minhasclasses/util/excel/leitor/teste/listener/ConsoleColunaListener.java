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


package br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.teste.listener;

import java.util.Map;

import org.apache.log4j.Logger;

import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.exception.ListenerException;
import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.listener.ColunaListener;
import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.util.LinhaColunaListenerVo;

/**
 * Listener utilizado nos testes da aplicação que exibe o conteúdo da coluna no console  
 * 
 * @version 0.1
 * @author Rogério de Paula Aguilar
 * */
public class ConsoleColunaListener implements ColunaListener {
    static Logger log = Logger.getLogger(ConsoleColunaListener.class);

	/**
	 * Exibe os dados da coluna no console
	 * @see br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.listener.ColunaListener#lendoColuna(int, int, Map)
	 * */
    public boolean lendoColuna(int linha, int coluna, Map dadosColuna)
			throws ListenerException {
    	 		log.debug("###################################################Coluna Teste###################################################");				 	
    	 		log.debug("Linha --> " + linha);																												 
    	 		log.debug("Coluna --> " + coluna);																												 
    	 		log.debug("Vo contendo os dados da coluna --> " + dadosColuna.get(ColunaListener.CHAVE_VO_COLUNA));											 	
    	 		log.debug("Objeto original da API POI--> " + dadosColuna.get(ColunaListener.CHAVE_COLUNA_OBJETO_ORIGINAL_POI));								 
    	 		log.debug("Dados coluna --> " + dadosColuna);																									 
    	 		LinhaColunaListenerVo voAtual = (LinhaColunaListenerVo) dadosColuna.get(ColunaListener.CHAVE_VO_COLUNA);												 
    	 		if(voAtual.isCelulaContemErro()){													  																	 
    	 			log.debug("Erro -->" + voAtual.getErro());								  																	 
    	 		}else if(voAtual.isCelulaNula()){													  																	 		
    	 			log.debug("Célula é nula!");											  																	 
    	 		}else if(voAtual.isCelulaBranca()){												  																		 
    	 			log.debug("Célula é branca!");											  																	 	
    	 		}else if(voAtual.isCelulaFormula()){												 																	 
    	 			log.debug("Fórmula --> " + voAtual.getValorStrFormula());				  																	 
    	 		}else if(voAtual.getValorNumerico() != null){										 																	 
    	 			log.debug("Valor Numérico --> " + voAtual.getValorNumerico());		      																	 
    	 		}else if(voAtual.getValorBoolean() != null){										  																	 
    	 			log.debug("Valor booleano --> " + voAtual.getValorBoolean());		      																	 
    	 		}else if(voAtual.getValorStr() != null){																												 
    	 			log.debug("Valor String --> " + voAtual.getValorStr());		     																		 
    	 		}																																						 
    	 		log.debug("##################################################################################################################");				 			
    	 		return true;																																			 
    }
}
