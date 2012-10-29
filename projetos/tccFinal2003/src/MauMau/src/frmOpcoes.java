/*
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
Classe: jogo.frmOpcoes
Responsabilidades: Exibir op��es para o usu�rio

****************************************Altera��es****************************************

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 05/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Implementa��o do formul�rio de op��es
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 10/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando op��o para exibir informa��es sobre as cartas
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 05/11/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando op��o para inserir ip e porta do servidor
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


*/
package jogo;

import javax.microedition.lcdui.*;
import jogo.apibasica.*;

/**
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
<p>Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
<p>Classe: jogo.frmOpcoes
<p>Responsabilidades: Exibir op��es para o usu�rio, entre elas op��o de habilitar/desabilitar o modo open mau mau,<br>
			op��o de habilitar/desabilitar a visualiza��o de informa��es sobre a carta selecionada.<br>
			Tamb�m exibe campos onde o jogador deve digitar o endere�o e a porta do servidor.
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
		Exibe as op��es de habilitar/desabilitar modo open mau mau e <br>
		op��o de habilitar/desabilitar a visualiza��o de informa��es sobre a carta selecionada.<br>
	*/
	private ChoiceGroup grupo;
	
	/**
		Campo onde o jogador deve especificar o endere�o IP do servidor
	*/
	private TextField txtIP = new TextField("Endere�o do servidor: ", "", 32, TextField.URL);

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
		
		grupo = new ChoiceGroup("Op��es", Choice.MULTIPLE , new String[]{"Open Mau Mau", "Exibir informa��es sobre a carta selecionada"} , null); 
		append(grupo);
		append(txtIP);
		append(txtPorta);
		addCommand(cmdVoltar);
		setCommandListener(this);
	}	


	/**
		Seta a op��o open Mau Mau
	*/
	public void setOpenMauMau(boolean b){
		grupo.setSelectedIndex(0,  b);
	}

	/**
		Seta a op��o Exibir informa��es
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
		M�todo que � invocado quando o jogador seleciona algum comando
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
				alertaErro = new Alert("", "Porta do servidor inv�lida!", null, AlertType.INFO); 
				CartasMidlet.getDisplay().setCurrent(alertaErro);
			   }else{
				ControleJogo.setIPServidor(ip);
				ControleJogo.setPortaServidor(porta);

			   	CartasMidlet.getDisplay().setCurrent(telaVolta);
			   }
			}
	}

}