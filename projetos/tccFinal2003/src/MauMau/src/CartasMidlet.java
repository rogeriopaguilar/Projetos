/*
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
Classe: jogo.CartasMidlet
Responsabilidades: Classe principal, que inicializa os objetos de controle do jogo

****************************************Alterações****************************************

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 22/08/2003
Responsável: Rogério de Paula Aguilar
Descrição: Implementação do formulário de ajuda e término da apresentação
Status: pendente (falta terminar o formulário de ajuda)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 04/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: mudando display para ser estático e adicionando o método getDisplay
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%



*/

package jogo;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import jogo.apibasica.*;


/**
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
<p>Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
<p>Classe: jogo.CartasMidlet
<p>Responsabilidades: Classe principal, que inicializa os objetos de controle do jogo

@author Rogério de Paula Aguilar
@version 1.0
*/


public class CartasMidlet extends MIDlet {
	
	/** 
		Objeto contendo as características do dispositivo 
		em que o jogo está sendo executado
	*/
	private Dispositivo dispositivo;

	/** 
		Display do jogo
	*/
	private static Display display;
	/** 
		Objeto que controla a execução do jogo
	*/
	private ControleJogo controleJogo;


	/** 
		Inicializa o MIDlet
	*/

	public void startApp(){
		if(ControleJogo.DEBUG_MODE){
			System.out.println("CartasMidlet>>startAPP>>Endereço do Servidor: " + getAppProperty("IP_SERVIDOR"));
			System.out.println("CartasMidlet>>startAPP>>Porta do Servidor: " + getAppProperty("PORTA_SERVIDOR"));
				
		}
		display = Display.getDisplay(this);
		dispositivo = new Dispositivo(display);
		controleJogo = new ControleJogo(true, dispositivo, display, this, getAppProperty("IP_SERVIDOR"), Integer.parseInt(getAppProperty("PORTA_SERVIDOR")));
		controleJogo.setIPServidor(getAppProperty("IP_SERVIDOR"));
		controleJogo.setPortaServidor(getAppProperty("PORTA_SERVIDOR"));

	}

	public void pauseApp(){}

	/** 
		Sai da aplicação
	*/

	public void destroyApp(boolean b){
		System.gc();
		controleJogo.parar();


		if(ControleJogo.DEBUG_MODE)
			System.out.println("CartasMidlet>>destroyApp>>Saindo da aplicação...");

		notifyDestroyed();
	}

	/** 
		Retorna o display do jogo
	*/
	public static Display getDisplay(){
		return display;
	}


}