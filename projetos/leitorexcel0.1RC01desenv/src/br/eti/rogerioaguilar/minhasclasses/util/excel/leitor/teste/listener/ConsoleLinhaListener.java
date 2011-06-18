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
import br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.listener.LinhaListener;

/**
 * Listener utilizado nos testes da api que exibe o conteúdo das colunas de uma determinada 	<br>
 * linha da planilha no console.																<br>
 * 
 * @version 0.1
 * @author Rogério de Paula Aguilar
 * */

public class ConsoleLinhaListener implements LinhaListener {
    static Logger log = Logger.getLogger(ConsoleLinhaListener.class);

	/**
	 * Método implementado para exibir o conteúdo da linha no console. 
	 * @see br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.listener.LinhaListener#lendoLinha(int, Map)
	 * */
    public boolean lendoLinha(int linha, Map dadosLinha)
			throws ListenerException {
		log.debug("***************************************************Listener linha Teste***************************************************");
		log.debug("Linha --> " + linha);
		log.debug("Dados linha (MAPA) --> " + dadosLinha);
		log.debug("Lista de vos com os dados da linha --> " + dadosLinha.get(LinhaListener.LISTA_VOS_LINHA));
		log.debug("Objeto original da api poi --> " + dadosLinha.get(LinhaListener.CHAVE_LINHA_OBJETO_ORIGINAL_POI));
		log.debug("**************************************************************************************************************************");
		return true;
	}
	
}
