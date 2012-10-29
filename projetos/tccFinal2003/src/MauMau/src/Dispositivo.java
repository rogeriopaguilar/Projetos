/*
Classe: jogo.apibasica.Dispositivo
Objetivo: Guarda informações sobre o dispositivo (largura da tela, altura da tela, 
	  coordenadas X e Y do centro da tela) 

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 04/09/2003
Responsável: Rogério de Paula Aguilar
Descrição: Tornando dados estáticos (OBS: A classe dave ser instanciada para preencher estes dados
		corretamente)
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


*/
package jogo.apibasica;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.game.*;

/**
2003 - Faculdade Senac de Ciências Exatas e Tecnologia 

<p>Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis 

<p>Classe: jogo.apibasica.Dispositivo
<p>Objetivo: Guarda informações sobre o dispositivo (largura da tela, altura da tela, 
	  coordenadas X e Y do centro da tela) 

@author Rogério de Paula Aguilar
@version 1.0
*/
public class Dispositivo{
	/**
		Display do dispositivo
	*/
	private Display displayDispositivo;
	
	/**
		Coordenadas da tela
	*/
	private static int X_CENTRO, Y_CENTRO, larguraTela, alturaTela;
	
	/**
		Canvas utilizado apenas para obter coordenadas da tela
	*/
	private Canvas canvas = new GameCanvas(false){

		public void paint(Graphics g){}
	};

	/**
		Construtor
	*/
	public Dispositivo(Display display){
		if(display == null){
		   throw new IllegalArgumentException("Dispositivo>>Dispositivo(Display display)>>Display deve ser diferente de null!");
		}else{
		   displayDispositivo = display;
		   Displayable d = display.getCurrent();
		   display.setCurrent(canvas);	
		   X_CENTRO = canvas.getWidth() / 2;
		   Y_CENTRO = canvas.getHeight() / 2;
		   larguraTela = canvas.getWidth();
		   alturaTela = canvas.getHeight(); 
		   display.setCurrent(d);
			   

		   d = null;
		   canvas = null;
		   System.gc();
		
		}
	}

	/**
		Retorna a largura da tela
	*/
	public static int getLarguraTela(){
		return larguraTela;
	}

	/**
		Retorna a altura da tela
	*/
	public static int getAlturaTela(){
		return alturaTela;
	}

	/**
		Retorna a coordenada X do centro da tela
	*/
	
	public static int getCentroX(){
		return X_CENTRO;
	}	
	
	/**
		Retorna a coordenada Y do centro da tela
	*/
	
	public static int getCentroY(){
		return Y_CENTRO;
	}

	

}