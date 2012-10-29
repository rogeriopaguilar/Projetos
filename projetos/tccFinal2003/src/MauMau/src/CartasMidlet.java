/*
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
Classe: jogo.CartasMidlet
Responsabilidades: Classe principal, que inicializa os objetos de controle do jogo

****************************************Altera��es****************************************

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 22/08/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Implementa��o do formul�rio de ajuda e t�rmino da apresenta��o
Status: pendente (falta terminar o formul�rio de ajuda)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 04/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: mudando display para ser est�tico e adicionando o m�todo getDisplay
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%



*/

package jogo;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import jogo.apibasica.*;


/**
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
<p>Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
<p>Classe: jogo.CartasMidlet
<p>Responsabilidades: Classe principal, que inicializa os objetos de controle do jogo

@author Rog�rio de Paula Aguilar
@version 1.0
*/


public class CartasMidlet extends MIDlet {
	
	/** 
		Objeto contendo as caracter�sticas do dispositivo 
		em que o jogo est� sendo executado
	*/
	private Dispositivo dispositivo;

	/** 
		Display do jogo
	*/
	private static Display display;
	/** 
		Objeto que controla a execu��o do jogo
	*/
	private ControleJogo controleJogo;


	/** 
		Inicializa o MIDlet
	*/

	public void startApp(){
		if(ControleJogo.DEBUG_MODE){
			System.out.println("CartasMidlet>>startAPP>>Endere�o do Servidor: " + getAppProperty("IP_SERVIDOR"));
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
		Sai da aplica��o
	*/

	public void destroyApp(boolean b){
		System.gc();
		controleJogo.parar();


		if(ControleJogo.DEBUG_MODE)
			System.out.println("CartasMidlet>>destroyApp>>Saindo da aplica��o...");

		notifyDestroyed();
	}

	/** 
		Retorna o display do jogo
	*/
	public static Display getDisplay(){
		return display;
	}


}