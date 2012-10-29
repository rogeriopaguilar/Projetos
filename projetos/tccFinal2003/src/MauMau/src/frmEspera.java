/*
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
Classe: jogo.frmEspera
Responsabilidades: Exibir uma tela para o usuário enquanto este aguarda o término de algum processo do jogo

****************************************Alterações****************************************

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 15/10/2003
Responsável: Rogério de Paula Aguilar
Descrição: Implementação do formulário de espera
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


*/
package jogo;

import javax.microedition.lcdui.*;
import jogo.apibasica.*;


/**
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
<p>Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
<p>Classe: jogo.frmEspera
<p>Responsabilidades: Exibir uma tela para o usuário enquanto este aguarda o término de algum processo do jogo

@author Rogério de Paula Aguilar
@version 1.0

*/


public class frmEspera extends Form {

	/**
		Mensagem que fica aparecendo na tela do usuário
	*/
	private Ticker tk = new Ticker("Aguarde. Enviando informações para o servidor.");	
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
		Retira todos os itens do formulário
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