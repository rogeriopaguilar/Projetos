/*
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
Classe: jogo.frmEspera
Responsabilidades: Exibir uma tela para o usu�rio enquanto este aguarda o t�rmino de algum processo do jogo

****************************************Altera��es****************************************

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 15/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Implementa��o do formul�rio de espera
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


*/
package jogo;

import javax.microedition.lcdui.*;
import jogo.apibasica.*;


/**
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
<p>Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
<p>Classe: jogo.frmEspera
<p>Responsabilidades: Exibir uma tela para o usu�rio enquanto este aguarda o t�rmino de algum processo do jogo

@author Rog�rio de Paula Aguilar
@version 1.0

*/


public class frmEspera extends Form {

	/**
		Mensagem que fica aparecendo na tela do usu�rio
	*/
	private Ticker tk = new Ticker("Aguarde. Enviando informa��es para o servidor.");	
	private ImageItem img;


	/**
		Construtor
	*/
	public frmEspera(){
		super("");
		setTicker(tk);
		try{
			img = new ImageItem("",Image.createImage("/conexao.png"),ImageItem.LAYOUT_CENTER,"");
		}catch(Exception e){
			ControleJogo.exibirDebugMsg("frmEspera>>static>>Erroa ao carregar conexao.png");
		}
		append(img);

	}

	/**
		COnstrutor
	*/
	public frmEspera(String msg){
		super("");
		tk = new Ticker(msg);	
		setTicker(tk);

		try{
			img = new ImageItem("",Image.createImage("/conexao.png"),ImageItem.LAYOUT_CENTER,"");
		}catch(Exception e){
			ControleJogo.exibirDebugMsg("frmEspera>>static>>Erroa ao carregar conexao.png");
		}
		
		append(img);
	}

	/**
		Retorna a imagem associada a esta tela de espera
	*/
	public ImageItem getImage(){
		return img;
	}


	/**
		Retira todos os itens do formul�rio
	*/
	public void reset(String msg){
		deleteAll();
		tk = getTicker();		

		if(tk == null){
			tk = new Ticker(msg);	
		}else{
			tk.setString(msg);
		}	
		setTicker(tk);

		
		append(img);


	}	
	

}