/*
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
Classe: jogo.frmfrmEscolhaNaipe
Responsabilidades: permite que o usuário escolha o naipe da próxima carta (Valete(*))

*/
package jogo;


import javax.microedition.lcdui.*;
import jogo.apibasica.*;
import java.util.*;
import java.io.*;
import javax.microedition.io.*;

/**
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
<p>Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
<p>Classe: jogo.frmfrmEscolhaNaipe
<p>Responsabilidades: permite que o usuário escolha o naipe da próxima carta (Valete(*))

@author Rogério de Paula Aguilar
@version 1.0
*/


public class frmEscolhaNaipe extends Form implements CommandListener{
	
	/**
		Tela de volta
	*/
	private JogadorHumano telaVolta;
	
	/**
		Comando utilizado para confirmar o naipe escolhido
	*/
	private Command cmdEscolherNaipe = new Command("Escolher Naipe", Command.SCREEN, 0);
	
	/**
		Mensagem
	*/
	private ControleJogo.Mensagem m;
	
	/**
		Mensagem que fica aparecendo na tela do usuário
	*/
	private Ticker ticker = new Ticker("Quando você joga uma valete, deve escolher o naipe da próxima jogada. Selecione o naipe e clique em Escolher Naipe");
	
	/**
		Exibe os naipes para serem escolhidos
	*/
	private ChoiceGroup choice;

	private ControleJogo controleJogo;

	/**
		Construtor
	*/
	public frmEscolhaNaipe(JogadorHumano d, ControleJogo c, ControleJogo.Mensagem m){
		super("");
		setTicker(ticker);
		if(d == null)
			throw new IllegalArgumentException("Displayable deve ser diferente de null!");
		telaVolta = d;
		addCommand(cmdEscolherNaipe);
		setCommandListener(this);
		choice = new ChoiceGroup("Naipes:", Choice.EXCLUSIVE);
		choice.append("COPAS", null);
		choice.append("PAUS", null);
		choice.append("ESPADAS", null);
		choice.append("OUROS", null);
		this.m = m;
		append(choice);
		controleJogo = c;
		
	}	

	/**
		Método invocado quando o jogador seleciona algum comando
	*/
	public void commandAction(Command c, Displayable d){
		String strNaipe = "";
		if(choice.getSelectedIndex() == 0){
			controleJogo.NAIPE_ESCOLHIDO = Carta.COPAS;
			strNaipe = "COPAS";
		}else if(choice.getSelectedIndex() == 1){
			controleJogo.NAIPE_ESCOLHIDO = Carta.PAUS;
			strNaipe = "PAUS";
		}else if(choice.getSelectedIndex() == 2){
			controleJogo.NAIPE_ESCOLHIDO = Carta.ESPADAS;
			strNaipe = "ESPADAS";
		}else if(choice.getSelectedIndex() == 3){
			controleJogo.NAIPE_ESCOLHIDO = Carta.OUROS;
			strNaipe = "OUROS";
		
		}
		System.out.println("COMMAND_ACTION" + m.getString() +  " O próximo jogador será: " + controleJogo.getJogador(controleJogo.getIndiceProximoJogador()).getNome() + " e deverá jogar uma carta do naipe: " + strNaipe + ". Movendo para o próximo jogador.");
		m.setString(m.getString() +  " O próximo jogador será: " + controleJogo.getJogador(controleJogo.getJogadorAtual()).getNome() + " e deverá jogar uma carta do naipe: " + strNaipe + ". Movendo para o próximo jogador.");
		telaVolta.jogar(new Object[]{m});		

	}


}