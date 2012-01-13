package dnsec.shared.factory;

import dnsec.modulos.cadastros.funcao.business.CommandFuncoes;
import dnsec.modulos.cadastros.grupo.business.CommandGrupos;
import dnsec.modulos.cadastros.sistema.business.CommandSistemas;
import dnsec.modulos.cadastros.tiposbanco.business.CommandTiposBanco;
import dnsec.modulos.cadastros.usuario.business.CommandUsuarios;
import dnsec.modulos.cadastros.usuario.business.CriptografiaSenha;
import dnsec.modulos.cadastros.usuario.business.CriptografiaSenhaMySQLImpl;
import dnsec.modulos.controle.seguranca.business.CommandLogin;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;

/**
 * Factory para os objetos command da aplica��o. <br>
 * As classes que utilizam os objetos command devem preferencialmente <br>
 * utilizar o m�todo getCommand desta classe ao inv�s de <br>
 * criar os objetos diretamente. <br>
 * Exemplo de utiliza��o: <br>
 * 
 * <p>Supondo que o m�todo executa() queira utilizar o command para a manuten��o
 * de fun��es:</p>
 * 
 * <br>public void executa(){
 * <br>		//Obtendo refer�ncia ao command para a manuten��o de fun��es
 * <br>		BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_FUNCOES);
 * <br>}
 * 
 * @author Rog�rio de Paula Aguilar
 * @version 0.1
 * */

public class CommandFactory {

	/**Commands para os m�dulos da aplica��o*/
	private static BaseDispatchCRUDCommand baseDispatchCRUDCommandFuncoes = new CommandFuncoes();
	private static BaseDispatchCRUDCommand baseDispatchCRUDCommandSistemas = new CommandSistemas();
	private static BaseDispatchCRUDCommand baseDispatchCRUDCommandGrupos = new CommandGrupos();
	private static CriptografiaSenha criptografiaSenha = new CriptografiaSenhaMySQLImpl();
	private static BaseDispatchCRUDCommand baseDispatchCRUDCommandUsuarios = new CommandUsuarios(criptografiaSenha);
	private static BaseDispatchCRUDCommand baseDispatchCRUDCommandLogin = new CommandLogin(criptografiaSenha);
	private static BaseDispatchCRUDCommand baseDispatchCRUDCommandTiposBanco = new CommandTiposBanco();

	/**�ndices para a implementa��o do command do m�dulo de fun��es*/
	public static final int COMMAND_FUNCOES  = 0;
	/**�ndices para a implementa��o do command do m�dulo de sistemas*/
	public static final int COMMAND_SISTEMAS = 1;
	/**�ndices para a implementa��o do command do m�dulo de grupos*/
	public static final int COMMAND_GRUPOS   = 2;
	public static final int COMMAND_USUARIOS = 3;
	public static final int COMMAND_LOGIN = 4;
	public static final int COMMAND_TIPOS_BANCO = 5;
	
	/**
	 * Retorna uma refer�ncia para o objeto command, de acordo com o �ndice.
	 * @param indice �ndice para os comandos da aplica��o (Verificar constantes desta classe)
	 * @return objeto que implementa o command especificado pelo �ndice
	 * @throws IllegalArgumentException caso o �ndice informado seja inv�lido
	 * */
	public static BaseDispatchCRUDCommand getCommand(int indice){
			switch(indice){
				case 0: return baseDispatchCRUDCommandFuncoes;
				case 1: return baseDispatchCRUDCommandSistemas;
				case 2: return baseDispatchCRUDCommandGrupos;
				case 3: return baseDispatchCRUDCommandUsuarios;
				case 4: return baseDispatchCRUDCommandLogin;
				case 5: return baseDispatchCRUDCommandTiposBanco;
				default: throw new IllegalArgumentException("�ndice inv�lido!");
			}
	}

	public static BaseDispatchCRUDCommand getNewCommand(int indice){
		switch(indice){
			case 0: return new CommandFuncoes();
			case 1: return new CommandSistemas();
			case 2: return new CommandGrupos();
			case 3: return new CommandUsuarios(criptografiaSenha);
			case 4: return new CommandLogin(criptografiaSenha);
			case 5: return new CommandTiposBanco();
			default: throw new IllegalArgumentException("�ndice inv�lido!");
		}
	}

	
}
