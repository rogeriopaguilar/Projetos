/*
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
Classe: jogo.frmListaSalas
Responsabilidades: Exibe as salas para o usu�rio

****************************************Altera��es****************************************

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 13/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Implementa��o do formul�rio 
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 15/10/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Adicionando op��o de entrar na sala
	   Adicionando campo apelido
Status: ok
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


*/
package jogo;


import javax.microedition.lcdui.*;
import jogo.apibasica.*;
import java.util.*;
import java.io.*;
import javax.microedition.io.*;

/**
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
<p>Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
<p>Classe: jogo.frmListaSalas
<p>Responsabilidades: Exibe as salas para o usu�rio e um campo para digitar o apelido, quando o 
			<br>jogador estiver come�ando um jogo on-line

@author Rog�rio de Paula Aguilar
@version 1.0
*/

public class frmListaSalas extends Form implements CommandListener{
	
	/**
		Tela de volta
	*/
	private Displayable telaVolta;
	
	/**
		Comando utilizado para retornar para a tela anterior
	*/
	private Command cmdVoltar = new Command("Voltar", Command.SCREEN, 0);
	
	/**
		Comando utilizado para entrar na sala selecionada
	*/
	private Command cmdEntrarSala = new Command("Entrar na sala", Command.SCREEN, 0);

	/**
		Mensagem que fica aparecendo na tela do jogador
	*/
	private Ticker ticker = new Ticker("Selecione a sala, digite o apelido e clique na op��o entrar na sala");
	
	/**
		Vetor que guarda a lista de salas	
	*/
	private Vector vetorSalas;
	
	/**
		Exibe a lista de salas
	*/
	private ChoiceGroup choice;
	
	/**
		Texto para o jogador digitar o apelido
	*/
	private TextField txtApelido = new TextField("Apelido:", "", 15, TextField.ANY);
	
	/**
		Mensagem de erro
	*/
	private Alert alertaApelido;
	
	/**
		Mensagem de erro
	*/
	private Alert alertaErro;
	private ControleJogo controleJogo;

	/**
		Construtor
	*/
	public frmListaSalas(String titulo, Displayable d, String salas, ControleJogo c){
		super(titulo);
		setTicker(ticker);
		if(d == null || salas == null)
			throw new IllegalArgumentException("Displayable e Display devem ser diferentes de null!");
		telaVolta = d;
		addCommand(cmdEntrarSala);
		addCommand(cmdVoltar);
		setCommandListener(this);
		vetorSalas = new Vector();
		int i = 0, j = 0;
		while( (i = salas.indexOf("-", j)) != -1){
			String salaAtual = salas.substring(j, i);
			j = i + 1;
			vetorSalas.addElement(salaAtual);
		}
		vetorSalas.addElement(salas.substring(j, salas.length()));
		vetorSalas.trimToSize();		
		choice = new ChoiceGroup("", Choice.EXCLUSIVE);
		for(i = 0; i < vetorSalas.size(); i++){
			String strSala = (String)vetorSalas.elementAt(i);
			int indice = strSala.indexOf("[");
			choice.append( strSala.substring(0, indice) + " - " + strSala.substring(indice + 1, strSala.indexOf("]")) + " jogadores", null);
		} 
		append(txtApelido);
		append(choice);
		controleJogo = c;
		
	}	

	/**
		Apaga o formul�rio coloca a nova lista de salas
	*/
	public void reset(String salas){
		deleteAll();
		vetorSalas = new Vector();
		int i = 0, j = 0;
		while( (i = salas.indexOf("-", j)) != -1){
			String salaAtual = salas.substring(j, i);
			j = i + 1;
			vetorSalas.addElement(salaAtual);
		}
		vetorSalas.addElement(salas.substring(j, salas.length()));
		vetorSalas.trimToSize();		
		choice = new ChoiceGroup("", Choice.EXCLUSIVE);
		for(i = 0; i < vetorSalas.size(); i++){
			String strSala = (String)vetorSalas.elementAt(i);
			int indice = strSala.indexOf("[");
			choice.append( strSala.substring(0, indice) + " - " + strSala.substring(indice + 1, strSala.indexOf("]")) + " jogadores", null);
		} 
		append(txtApelido);
		append(choice);
		System.gc();
	}

	/**
		M�todo que � invocado quando o jogador seleciona algum comando
	*/
	public void commandAction(Command c, Displayable d){
		if(c == cmdVoltar){ 
			if(CartasMidlet.getDisplay() != null){
				if(telaVolta instanceof ControleJogo){			   
					((ControleJogo)telaVolta).setApresentacao(true);
			   		((ControleJogo)telaVolta).setTelaSelecao(true);
					ControleJogo.JOGO_EM_ANDAMENTO = false;
			   	}
				CartasMidlet.getDisplay().setCurrent(telaVolta);
			}
		}else if(c == cmdEntrarSala){
			if(txtApelido.getString().trim().length() == 0){
				if(alertaApelido == null)
					alertaApelido = new Alert("", "Digite o apelido!", null, AlertType.ERROR); 
				
				CartasMidlet.getDisplay().setCurrent(alertaApelido);
			}else{
				CartasMidlet.getDisplay().setCurrent(ControleJogo.frmespera);
				//Thread thread = new Thread(){
				//	public void run(){			
						String sala = (String)vetorSalas.elementAt(choice.getSelectedIndex());
						int indice = sala.indexOf("[");
						sala = sala.substring(0, indice).trim();
						try{
							String strResultado = ControleJogo.retornarMsgServidor(ControleJogo.getHostServidor(), "acao=ENTRAR_SALA&jogador=" + txtApelido.getString().replace('[','a').replace(']','b').replace('!','c').replace('@','d').replace('#','e').replace('$','f').replace('%','g').replace('�','h').replace('&','i').replace('*','j').trim() + "&sala=" + sala, true, frmListaSalas.this.controleJogo);			

							ControleJogo.nomeJogadores[0] =	txtApelido.getString().replace('[','a').replace(']','b').replace('!','c').replace('@','d').replace('#','e').replace('$','f').replace('%','g').replace('�','h').replace('&','i').replace('*','j').trim();

							ControleJogo.nomeJogador = txtApelido.getString().replace('[','a').replace(']','b').replace('!','c').replace('@','d').replace('#','e').replace('$','f').replace('%','g').replace('�','h').replace('&','i').replace('*','j').trim();

							ControleJogo.idSala = sala;
							ControleJogo.exibirDebugMsg("ControleJogo>>commandAction>>" + strResultado);
							
						}catch(Exception e){

							if(alertaErro == null)
								alertaErro = new Alert("", "Erro ao estabelecer conex�o (SK) " + e, null, AlertType.INFO); 
				 			else
								alertaErro.setString("Erro ao estabelecer conex�o (SK) " + e);

							CartasMidlet.getDisplay().setCurrent(alertaErro, frmListaSalas.this);
						

						}
				//	}
				/*};
				try{
					thread.start();
					thread.join();
				}catch(Exception e){}*/

			}

		}
	}

}