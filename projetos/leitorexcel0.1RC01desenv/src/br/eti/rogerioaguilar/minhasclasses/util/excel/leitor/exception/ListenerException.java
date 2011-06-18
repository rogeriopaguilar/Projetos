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


package br.eti.rogerioaguilar.minhasclasses.util.excel.leitor.exception;

/**
 * Classe de erro utilizada nos listeners para linha e/ou coluna. Quando algum erro ocorre 		 <br>
 * durante o processamento do c�digo contido em algum dos listeners, este deve ser encapsulado	 <br>
 * nesta classe e lan�ado para o leitor, que ir� cancelar o processamento da leitura da planila. <br>
 * 
 * @version 0.1
 * @author Rog�rio de Paula Aguilar
 * @since 0.1
 * */
public class ListenerException extends Exception{

	private static final long serialVersionUID = 1503302241279285724L;

	public ListenerException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListenerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ListenerException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ListenerException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}


}
