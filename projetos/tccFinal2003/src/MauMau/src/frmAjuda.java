/*
2003 - Faculdade Senac de Ciências Exatas e Tecnologia
Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
Classe: jogo.frmAjuda
Responsabilidades: Exibir a ajuda para o usuário

****************************************Alterações****************************************

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Data: 22/08/2003
Responsável: Rogério de Paula Aguilar
Descrição: Implementação do formulário de ajuda e término da apresentação
Status: pendente (falta terminar o formulário de ajuda)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


*/
package jogo;

import javax.microedition.lcdui.*;
import jogo.apibasica.*;


/**
<p>2003 - Faculdade Senac de Ciências Exatas e Tecnologia
<p>Projeto de conclusão de curso: Técnicas de desenvolvimento de jogos para dispositivos móveis
<p>Classe: jogo.frmAjuda
<p>Responsabilidades: Exibir a ajuda para o usuário
@author Rogério de Paula Aguilar
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
			setTicker(new Ticker("2003 - Rogério de Paula Aguilar (rogeriopaguilar@terra.com.br) - Luiz Fernando P.S. Forgas (luizforgas@terra.com.br) - Trabalho de Conclusão de Curso"));
		if(d == null || display ==null)
			throw new IllegalArgumentException("Displayable e Display devem ser diferentes de nul!");
		telaVolta = d;
		StringItem strRegras = new StringItem("Regras do jogo Mau-Mau", "");
		strRegras.setLayout(Item.LAYOUT_CENTER);
		append(strRegras);
		append(new StringItem("\nRegra geral: ", "O jogador deve jogar uma carta do mesmo naipe ou valor da carta que estiver no baralho no momento da sua jogada, ou jogar uma das cartas especiais. O jogador que ficar sem cartas primeiro vence o jogo. Se o baralho ficar vazio, o jogador que tiver o menor número de cartas é considerado vencedor."));
		append(new StringItem("Cartas especiais (* indica que a regra é válida para todos os naipes)", ""));
		append(new StringItem(" A*"," o jogador que jogar um AS pode jogar novamente."));
		append(new StringItem(" 2*"," carta utilizada para defender jogadas hostis (ver próximas regras)"));
		append(new StringItem(" 4 de ESPADAS"," todo mundo (excluindo o jogador que jogou esta carta) na mesa compra uma carta. Esta jogada é indefensável."));
		append(new StringItem(" 5*"," esta carta inverte o sentido do jogo."));
		append(new StringItem(" 7*"," o próximo jogador deve comprar três cartas, jogar outro sete (quando um sete é jogado sobre outro, a quantidade de cartas para compra será a quantidade atual mais 3 e a penalidade é passada para o próximo jogador). Pode ser defendido por um 2 do mesmo naipe do sete atual, por um coringa ou por uma valete."));
		append(new StringItem(" 9*"," o próximo jogador deve comprar uma carta. Pode ser defendido pelo dois do mesmo naipe do nove que está no baralho, por um coringa ou por uma valete."));
		append(new StringItem(" 10 de PAUS"," o próximo jogador deve comprar cinco cartas. Pode ser defendido pelo dois de paus, por um coringa ou por uma valete."));
		append(new StringItem(" C(CORINGA)*"," pode ser jogado sobre qualquer carta e anula o efeito de qualquer carta especial."));
		append(new StringItem(" V(Valete)*"," pode ser jogado sobre qualquer carta e anula o efeito de qualquer outra carta especial. Ao jogar, o jogador escolhe o naipe que deve ser utilizado na próxima jogada."));
		append(new StringItem(" D(DAMA)*"," pula o próximo jogador."));
		append(new StringItem(" R(REI)*"," faz o jogador anterior comprar uma carta. Esta jogada é indefensável."));
		append(new StringItem(" \n"," Quando o jogador tiver apenas duas cartas, antes de jogar sua penúltima carta ele deve dizer \"Mau-Mau\" ou receberá três cartas como penalidade. O jogador só pode dizer \"Mau-Mau\" nesta situação, senão também será penalizado com três cartas."));

		append(new StringItem(" OBSERVAÇÃO:"," Se o jogador atual paga uma penalidade, a mesma não é passada para o próximo jogador. Exemplo: Se existe um sete de paus e o jogador atual compra três cartas (que é a penalidade do sete), o próximo jogador não precisa comprar três cartas, pois o efeito do sete já foi aplicado no jogador anterior. Nestes casos, basta seguir a regra geral."));



		StringItem strDescricaoInterface = new StringItem("Descrição da interface de jogo", "");
		strDescricaoInterface.setLayout(Item.LAYOUT_CENTER|Item.LAYOUT_NEWLINE_AFTER|Item.LAYOUT_NEWLINE_BEFORE);
		append(strDescricaoInterface);
		append(new StringItem("", "A tela do jogador apresenta as cartas do mesmo. Selecionando a tecla que aponta para cima, o jogador pode visualizar o baralho. Estando na tela do baralho, se o usuário selecionar a tecla baixo, é mostrada a tela anterior. Estando na tela de um jogador, se o usuário selecionar a tecla baixo, o jogo irá mostrar a tela do próximo jogador, e selecionando as teclas esquerda ou direita é possível navegar pelas cartas do jogador."));

		
		StringItem strDescricaoMenus = new StringItem("Descrição dos menus", "");
		strDescricaoMenus.setLayout(Item.LAYOUT_CENTER|Item.LAYOUT_NEWLINE_AFTER|Item.LAYOUT_NEWLINE_BEFORE);
		append(strDescricaoMenus);
		append(new StringItem("Jogar: ", "Inicia um novo jogo contra o computador."));
		append(new StringItem("Jogar on-line: ", "Inicia um novo jogo on-line contra outras pessoas que estejam conectadas ao servidor."));
		append(new StringItem("Visualizador de cartas: ", "Exibe um formulário onde é possível visualizar todas as cartas do baralho."));
		append(new StringItem("Opções: ", "Exibe opções de configuração do jogo. O modeo Open Mau-Mau permite que os jogadores vejam as suas cartas e a dos outros jogadores. Se esta opção não estiver selecionada, os jogadores podem ver apenas as suas cartas. O endereço do servidor e a porta especificam onde o jogo tentará se conectar para o modo jogo on-line."));
		append(new StringItem("Ajuda: ", "Exibe esta tela."));


		StringItem strDescricaoJogoOnLine= new StringItem("Iniciando um jogo on-line", "");
		strDescricaoJogoOnLine.setLayout(Item.LAYOUT_CENTER|Item.LAYOUT_NEWLINE_AFTER|Item.LAYOUT_NEWLINE_BEFORE);
		append(strDescricaoJogoOnLine);
		append(new StringItem("", "Para iniciar um jogo on-line, à partir da tela de apresentação do jogo, selecione a opção jogo on-line. Se aparecer uma mensagem dizendo que você estará entrando num ambiente de rede, apenas confirme esta mensagem. O programa tentará se conectar ao servidor. Se ocorrer algum erro na conexão, verifique, através do menu opções, se o endereço e a porta do servidor estão corretos. Se estiverem, o servidor pode estar desligado ou com problemas. Neste caso, tente novamente mais tarde. Se não ocorrerem problemas, será apresentado um formulário contendo uma lista de salas disponíveis para o jogo. Selecione uma das salas, digite o seu apelido e clique na opção entrar na sala. Aparecerá uma tela informando quem está na sala. Se você foi o primeiro a entrar na sala de jogo, você será notificado quando alguém entrar na sala, e poderá começar o jogo através da opção começar jogo (esta opção só aparecerá a partir do momento que mais alguém entrar na sala). Se você não foi o primeiro a entrar na sala, deve aguardar para que o primeiro jogador que entrou na sala comece o jogo."));


		
		addCommand(cmdVoltar);
		setCommandListener(this);
	}	

	/**
		Método invocado quando o jogador seleciona algum comando
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