/*
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
Classe: jogo.frmAjuda
Responsabilidades: Exibir as cartas do baralho

****************************************Altera��es****************************************

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 01/09/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Cria��o da classe
Status: OK
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


*/
package jogo;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

import jogo.*;
import jogo.apibasica.*;


/**
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
<p>Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
<p>Classe: jogo.frmAjuda
<p>Responsabilidades: Exibir as cartas do baralho

@author Rog�rio de Paula Aguilar
@version 1.0
*/

public class frmVisualizadorCartas extends Canvas implements CommandListener{
	
	/**
		Tela de volta	
	*/
	private Displayable telaVolta;
	
	/**
		Comando utilizado para retornar � tela anterior
	*/
	private Command cmdVoltar = new Command("Voltar", Command.SCREEN, 0);
	
	/**
		Tela do jogo
	*/
	private Display display;
	
	/**
		Informa��o sobre a carta que est� sendo exibida
	*/
	private byte naipe, valor;
	
	/**
		Comando utilizado para mover para a pr�xima carta 
	*/
	private Command cmdProximaCarta = new Command("Pr�xima Carta", Command.SCREEN, 0);

	/**
		Comando utilizado para mover para a carta anterior
	*/
	private Command cmdCartaAnterior = new Command("Carta anterior", Command.SCREEN, 0);
	
	/**
		Imagem da carta atual
	*/
	private ImageItem imagemAtual;	
	
	/**
		Baralho
	*/
	private Baralho baralho;
	
	/**
		Objeto utilizado para obter informa��es sobre o dispositivo
	*/
	private Dispositivo dispositivo;

	/**
		Construtor
	*/
	public frmVisualizadorCartas(String titulo, Displayable telaVolta, Display display, Baralho baralho, Dispositivo d){
		//super(false);
		
		this.display = display;
		this.telaVolta = telaVolta;
		this.baralho = baralho;			
		dispositivo = d;		
		//imagemAtual = new ImageItem(baralho.cartasBaralho[naipe][valor],  ImageItem.LAYOUT_CENTER, "");

		addCommand(cmdProximaCarta);
		addCommand(cmdCartaAnterior);
		addCommand(cmdVoltar);
		setCommandListener(this);
				
	}
	
	/**
		M�todo que � invocado quando o usu�rio seleciona algum comando
	*/
	public void commandAction(Command c, Displayable d){
		if(c == cmdVoltar){ 
			if(display != null){
			   ((ControleJogo)telaVolta).setApresentacao(true);
			   ((ControleJogo)telaVolta).setTelaSelecao(true);
			   display.setCurrent(telaVolta);
			}
		}else if(c == cmdProximaCarta){
			valor +=1;
			if(valor == 14){
			   naipe += 1;
			   if(naipe == 4) naipe = 0;
			   valor = 0;
			   	
			}
			repaint();
			
		}else if(c == cmdCartaAnterior){
			valor -=1;
			if(valor == -1){
			   naipe -= 1;
			   if(naipe == -1) naipe = 3;
			   valor = 13;
			   	
			}
			repaint();
			
		}
	

	}

	/**
		Desenha a carta atual e a s informa��es sobre a mesma
	*/
	public void paint(Graphics g){
		//super.paint(g);
		g.setColor(0, 190, 0);
		g.fillRect(0, 0, dispositivo.getLarguraTela(), dispositivo.getAlturaTela());
		
		g.setColor(255, 255, 255);
		
		g.setFont(Font.getFont(Font.STYLE_BOLD ));

		g.drawString(baralho.cartasBaralho[naipe][valor].getNaipeStr() , 0, 0, 0);
		g.drawString(baralho.cartasBaralho[naipe][valor].getValorStr() , 0, Font.getFont(Font.STYLE_BOLD ).getHeight() + 2, 0);


		baralho.cartasBaralho[naipe][valor].setPosition( (dispositivo.getLarguraTela() - baralho.cartasBaralho[naipe][valor].getWidth()) / 2,
								 (dispositivo.getAlturaTela() - baralho.cartasBaralho[naipe][valor].getHeight()) / 2 );
		baralho.cartasBaralho[naipe][valor].paint(g);
	}

}