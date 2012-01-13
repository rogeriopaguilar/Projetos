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
 * Factory para os objetos command da aplicação. <br>
 * As classes que utilizam os objetos command devem preferencialmente <br>
 * utilizar o método getCommand desta classe ao invés de <br>
 * criar os objetos diretamente. <br>
 * Exemplo de utilização: <br>
 * 
 * <p>Supondo que o método executa() queira utilizar o command para a manutenção
 * de funções:</p>
 * 
 * <br>public void executa(){
 * <br>		//Obtendo referência ao command para a manutenção de funções
 * <br>		BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_FUNCOES);
 * <br>}
 * 
 * @author Rogério de Paula Aguilar
 * @version 0.1
 * */

public class CommandFactory {

	/**Commands para os módulos da aplicação*/
	private static BaseDispatchCRUDCommand baseDispatchCRUDCommandFuncoes = new CommandFuncoes();
	private static BaseDispatchCRUDCommand baseDispatchCRUDCommandSistemas = new CommandSistemas();
	private static BaseDispatchCRUDCommand baseDispatchCRUDCommandGrupos = new CommandGrupos();
	private static CriptografiaSenha criptografiaSenha = new CriptografiaSenhaMySQLImpl();
	private static BaseDispatchCRUDCommand baseDispatchCRUDCommandUsuarios = new CommandUsuarios(criptografiaSenha);
	private static BaseDispatchCRUDCommand baseDispatchCRUDCommandLogin = new CommandLogin(criptografiaSenha);
	private static BaseDispatchCRUDCommand baseDispatchCRUDCommandTiposBanco = new CommandTiposBanco();

	/**Índices para a implementação do command do módulo de funções*/
	public static final int COMMAND_FUNCOES  = 0;
	/**Índices para a implementação do command do módulo de sistemas*/
	public static final int COMMAND_SISTEMAS = 1;
	/**Índices para a implementação do command do módulo de grupos*/
	public static final int COMMAND_GRUPOS   = 2;
	public static final int COMMAND_USUARIOS = 3;
	public static final int COMMAND_LOGIN = 4;
	public static final int COMMAND_TIPOS_BANCO = 5;
	
	/**
	 * Retorna uma referência para o objeto command, de acordo com o índice.
	 * @param indice índice para os comandos da aplicação (Verificar constantes desta classe)
	 * @return objeto que implementa o command especificado pelo índice
	 * @throws IllegalArgumentException caso o índice informado seja inválido
	 * */
	public static BaseDispatchCRUDCommand getCommand(int indice){
			switch(indice){
				case 0: return baseDispatchCRUDCommandFuncoes;
				case 1: return baseDispatchCRUDCommandSistemas;
				case 2: return baseDispatchCRUDCommandGrupos;
				case 3: return baseDispatchCRUDCommandUsuarios;
				case 4: return baseDispatchCRUDCommandLogin;
				case 5: return baseDispatchCRUDCommandTiposBanco;
				default: throw new IllegalArgumentException("Índice inválido!");
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
			default: throw new IllegalArgumentException("Índice inválido!");
		}
	}

	
}
