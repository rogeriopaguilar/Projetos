/*
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
Classe: jogo.frmOpcoes
Responsabilidades: Exibir opções para o usuário

****************************************Alterações****************************************

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 05/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Implementação do formulário de opções
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 10/10/2003
Responsável: Rogério de Paula Aguilar
Descrição: Adicionando opção para exibir informações sobre as cartas
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 05/11/2003
Responsável: Rogério de Paula Aguilar
Descrição: Adicionando opção para inserir ip e porta do servidor
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


*/
package jogo;

import javax.microedition.lcdui.*;
import jogo.apibasica.*;

/**
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
<p>Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
<p>Classe: jogo.frmOpcoes
<p>Responsabilidades: Exibir opções para o usuário, entre elas opção de habilitar/desabilitar o modo open mau mau,<br>
			opção de habilitar/desabilitar a visualização de informações sobre a carta selecionada.<br>
			Também exibe campos onde o jogador deve digitar o endereço e a porta do servidor.
*/
public class frmOpcoes extends Form implements CommandListener{
	
	/**
		Tela de volta
	*/
	private Displayable telaVolta;
	
	/**
		Comando utilizado para retornar para a tela anterior
	*/
	private Command cmdVoltar = new Command("Voltar", Command.SCREEN, 0);
	
	/**
		Exibe as opções de habilitar/desabilitar modo open mau mau e <br>
		opção de habilitar/desabilitar a visualização de informações sobre a carta selecionada.<br>
	*/
	private ChoiceGroup grupo;
	
	/**
		Campo onde o jogador deve especificar o endereço IP do servidor
	*/
	private TextField txtIP = new TextField("Endereço do servidor: ", "", 32, TextField.URL);

	/**
		Campo onde o jogador deve especificar a porta do servidor
	*/
	private TextField txtPorta = new TextField("Porta do servidor: ", "", 8, TextField.NUMERIC);
	

	/**
		Construtor
	*/
	public frmOpcoes(String titulo, Displayable d){
		super(titulo);
		//setTicker(new Ticker("Ajuda e Regras do Jogo"));
		if(d == null)
			throw new IllegalArgumentException("Displayable deve ser diferente de null!");
		telaVolta = d;
		
		grupo = new ChoiceGroup("Opções", Choice.MULTIPLE , new String[]{"Open Mau Mau", "Exibir informações sobre a carta selecionada"} , null); 
		append(grupo);
		append(txtIP);
		append(txtPorta);
		addCommand(cmdVoltar);
		setCommandListener(this);
	}	


	/**
		Seta a opção open Mau Mau
	*/
	public void setOpenMauMau(boolean b){
		grupo.setSelectedIndex(0,  b);
	}

	/**
		Seta a opção Exibir informações
	*/
	public void setExibirInformacoesCarta(boolean b){
		grupo.setSelectedIndex(1, b);
	}

	/**
		Modifica o ip do servidor
	*/
	public void setIPServidor(String ip){
		txtIP.setString(ip);
	}

	/**
		Modifica a porta do servidor
	*/
	public void setPortaServidor(String porta){
		txtPorta.setString(porta);
	}

	
	/**
		Método que é invocado quando o jogador seleciona algum comando
	*/
	public void commandAction(Command c, Displayable d){
		if(c == cmdVoltar) 
			if(CartasMidlet.getDisplay() != null){
			   if(telaVolta instanceof ControleJogo){
			   	((ControleJogo)telaVolta).setApresentacao(true);
			   	((ControleJogo)telaVolta).setTelaSelecao(true);
			   }
			   if(ControleJogo.DEBUG_MODE){
				if(grupo.isSelected(0)){
					System.out.println("frmOpcoes>>commandAction>>Modo open mau mau ativado");
				}else{
					System.out.println("frmOpcoes>>commandAction>>Modo open mau mau desativado");
				}
			   }
			   
			   ControleJogo.setOpenMauMau(grupo.isSelected(0));
			   ControleJogo.EXIBIR_INFORMACAO_CARTA = grupo.isSelected(1);	
			   String ip = txtIP.getString().trim();
			   String porta = txtPorta.getString().trim();
			   Alert alertaErro = null;
			   if(ip.length() == 0){
				alertaErro = new Alert("", "Digite o ip do servidor!", null, AlertType.INFO); 
			   	CartasMidlet.getDisplay().setCurrent(alertaErro);
			   }else if(porta.length() == 0){
				alertaErro = new Alert("", "Digite a porta do servidor!", null, AlertType.INFO); 
				CartasMidlet.getDisplay().setCurrent(alertaErro);
			   }else if(Integer.parseInt(porta) < 0){
				alertaErro = new Alert("", "Porta do servidor inválida!", null, AlertType.INFO); 
				CartasMidlet.getDisplay().setCurrent(alertaErro);
			   }else{
				ControleJogo.setIPServidor(ip);
				ControleJogo.setPortaServidor(porta);

			   	CartasMidlet.getDisplay().setCurrent(telaVolta);
			   }
			}
	}

}