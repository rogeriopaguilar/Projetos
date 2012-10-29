package jogo.rede;
/**
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia

<p>Rog�rio de Paula Aguilar 8NA
<p>Luiz Fernando P.S. Forgas 8NA

<p>Trabalho de Conclus�o de Curso:
T�cnicas de desenvolvimento de Jogos para dispositivos m�veis

<p>Pacote: jogo.rede
<p>Interface: JogadorNaoEncontradoException

<p>Descri��o: Exception que � lan�ada quando um jogador n�o � encontrado
*/

class JogadorNaoEncontradoException extends Exception{
	/**
		Construtor
	*/
	public JogadorNaoEncontradoException(String msg){
		super(msg);
	}

}