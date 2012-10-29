/*
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
Classe: jogo.frmAjuda
Responsabilidades: Exibir a ajuda para o usu�rio

****************************************Altera��es****************************************

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 22/08/2003
Respons�vel: Rog�rio de Paula Aguilar
Descri��o: Implementa��o do formul�rio de ajuda e t�rmino da apresenta��o
Status: pendente (falta terminar o formul�rio de ajuda)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


*/
package jogo;

import javax.microedition.lcdui.*;
import jogo.apibasica.*;


/**
<p>2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia
<p>Projeto de conclus�o de curso: T�cnicas de desenvolvimento de jogos para dispositivos m�veis
<p>Classe: jogo.frmAjuda
<p>Responsabilidades: Exibir a ajuda para o usu�rio
@author Rog�rio de Paula Aguilar
@version 1.0
*/

public class frmAjuda extends Form implements CommandListener{
	
	/**
		Tela de volta
	*/
	private Displayable telaVolta;
	
	/**
		Comando utilizado para voltar para a tela anterior
	*/
	private Command cmdVoltar = new Command("Voltar", Command.SCREEN, 0);

	/**
		Construtor
	*/
	
	public frmAjuda(String titulo, Displayable d, Display display){
		super(titulo);
		if(getTicker() == null)
			setTicker(new Ticker("2003 - Rog�rio de Paula Aguilar (rogeriopaguilar@terra.com.br) - Luiz Fernando P.S. Forgas (luizforgas@terra.com.br) - Trabalho de Conclus�o de Curso"));
		if(d == null || display ==null)
			throw new IllegalArgumentException("Displayable e Display devem ser diferentes de nul!");
		telaVolta = d;
		StringItem strRegras = new StringItem("Regras do jogo Mau-Mau", "");
		strRegras.setLayout(Item.LAYOUT_CENTER);
		append(strRegras);
		append(new StringItem("\nRegra geral: ", "O jogador deve jogar uma carta do mesmo naipe ou valor da carta que estiver no baralho no momento da sua jogada, ou jogar uma das cartas especiais. O jogador que ficar sem cartas primeiro vence o jogo. Se o baralho ficar vazio, o jogador que tiver o menor n�mero de cartas � considerado vencedor."));
		append(new StringItem("Cartas especiais (* indica que a regra � v�lida para todos os naipes)", ""));
		append(new StringItem(" A*"," o jogador que jogar um AS pode jogar novamente."));
		append(new StringItem(" 2*"," carta utilizada para defender jogadas hostis (ver pr�ximas regras)"));
		append(new StringItem(" 4 de ESPADAS"," todo mundo (excluindo o jogador que jogou esta carta) na mesa compra uma carta. Esta jogada � indefens�vel."));
		append(new StringItem(" 5*"," esta carta inverte o sentido do jogo."));
		append(new StringItem(" 7*"," o pr�ximo jogador deve comprar tr�s cartas, jogar outro sete (quando um sete � jogado sobre outro, a quantidade de cartas para compra ser� a quantidade atual mais 3 e a penalidade � passada para o pr�ximo jogador). Pode ser defendido por um 2 do mesmo naipe do sete atual, por um coringa ou por uma valete."));
		append(new StringItem(" 9*"," o pr�ximo jogador deve comprar uma carta. Pode ser defendido pelo dois do mesmo naipe do nove que est� no baralho, por um coringa ou por uma valete."));
		append(new StringItem(" 10 de PAUS"," o pr�ximo jogador deve comprar cinco cartas. Pode ser defendido pelo dois de paus, por um coringa ou por uma valete."));
		append(new StringItem(" C(CORINGA)*"," pode ser jogado sobre qualquer carta e anula o efeito de qualquer carta especial."));
		append(new StringItem(" V(Valete)*"," pode ser jogado sobre qualquer carta e anula o efeito de qualquer outra carta especial. Ao jogar, o jogador escolhe o naipe que deve ser utilizado na pr�xima jogada."));
		append(new StringItem(" D(DAMA)*"," pula o pr�ximo jogador."));
		append(new StringItem(" R(REI)*"," faz o jogador anterior comprar uma carta. Esta jogada � indefens�vel."));
		append(new StringItem(" \n"," Quando o jogador tiver apenas duas cartas, antes de jogar sua pen�ltima carta ele deve dizer \"Mau-Mau\" ou receber� tr�s cartas como penalidade. O jogador s� pode dizer \"Mau-Mau\" nesta situa��o, sen�o tamb�m ser� penalizado com tr�s cartas."));

		append(new StringItem(" OBSERVA��O:"," Se o jogador atual paga uma penalidade, a mesma n�o � passada para o pr�ximo jogador. Exemplo: Se existe um sete de paus e o jogador atual compra tr�s cartas (que � a penalidade do sete), o pr�ximo jogador n�o precisa comprar tr�s cartas, pois o efeito do sete j� foi aplicado no jogador anterior. Nestes casos, basta seguir a regra geral."));



		StringItem strDescricaoInterface = new StringItem("Descri��o da interface de jogo", "");
		strDescricaoInterface.setLayout(Item.LAYOUT_CENTER|Item.LAYOUT_NEWLINE_AFTER|Item.LAYOUT_NEWLINE_BEFORE);
		append(strDescricaoInterface);
		append(new StringItem("", "A tela do jogador apresenta as cartas do mesmo. Selecionando a tecla que aponta para cima, o jogador pode visualizar o baralho. Estando na tela do baralho, se o usu�rio selecionar a tecla baixo, � mostrada a tela anterior. Estando na tela de um jogador, se o usu�rio selecionar a tecla baixo, o jogo ir� mostrar a tela do pr�ximo jogador, e selecionando as teclas esquerda ou direita � poss�vel navegar pelas cartas do jogador."));

		
		StringItem strDescricaoMenus = new StringItem("Descri��o dos menus", "");
		strDescricaoMenus.setLayout(Item.LAYOUT_CENTER|Item.LAYOUT_NEWLINE_AFTER|Item.LAYOUT_NEWLINE_BEFORE);
		append(strDescricaoMenus);
		append(new StringItem("Jogar: ", "Inicia um novo jogo contra o computador."));
		append(new StringItem("Jogar on-line: ", "Inicia um novo jogo on-line contra outras pessoas que estejam conectadas ao servidor."));
		append(new StringItem("Visualizador de cartas: ", "Exibe um formul�rio onde � poss�vel visualizar todas as cartas do baralho."));
		append(new StringItem("Op��es: ", "Exibe op��es de configura��o do jogo. O modeo Open Mau-Mau permite que os jogadores vejam as suas cartas e a dos outros jogadores. Se esta op��o n�o estiver selecionada, os jogadores podem ver apenas as suas cartas. O endere�o do servidor e a porta especificam onde o jogo tentar� se conectar para o modo jogo on-line."));
		append(new StringItem("Ajuda: ", "Exibe esta tela."));


		StringItem strDescricaoJogoOnLine= new StringItem("Iniciando um jogo on-line", "");
		strDescricaoJogoOnLine.setLayout(Item.LAYOUT_CENTER|Item.LAYOUT_NEWLINE_AFTER|Item.LAYOUT_NEWLINE_BEFORE);
		append(strDescricaoJogoOnLine);
		append(new StringItem("", "Para iniciar um jogo on-line, � partir da tela de apresenta��o do jogo, selecione a op��o jogo on-line. Se aparecer uma mensagem dizendo que voc� estar� entrando num ambiente de rede, apenas confirme esta mensagem. O programa tentar� se conectar ao servidor. Se ocorrer algum erro na conex�o, verifique, atrav�s do menu op��es, se o endere�o e a porta do servidor est�o corretos. Se estiverem, o servidor pode estar desligado ou com problemas. Neste caso, tente novamente mais tarde. Se n�o ocorrerem problemas, ser� apresentado um formul�rio contendo uma lista de salas dispon�veis para o jogo. Selecione uma das salas, digite o seu apelido e clique na op��o entrar na sala. Aparecer� uma tela informando quem est� na sala. Se voc� foi o primeiro a entrar na sala de jogo, voc� ser� notificado quando algu�m entrar na sala, e poder� come�ar o jogo atrav�s da op��o come�ar jogo (esta op��o s� aparecer� a partir do momento que mais algu�m entrar na sala). Se voc� n�o foi o primeiro a entrar na sala, deve aguardar para que o primeiro jogador que entrou na sala comece o jogo."));


		
		addCommand(cmdVoltar);
		setCommandListener(this);
	}	

	/**
		M�todo invocado quando o jogador seleciona algum comando
	*/
	
	public void commandAction(Command c, Displayable d){
		if(c == cmdVoltar) 
			if(CartasMidlet.getDisplay() != null){
				if(telaVolta instanceof ControleJogo){			   
					((ControleJogo)telaVolta).setApresentacao(true);
			   		((ControleJogo)telaVolta).setTelaSelecao(true);
			   	}
				System.gc();
				CartasMidlet.getDisplay().setCurrent(telaVolta);
				
			}
	}

}