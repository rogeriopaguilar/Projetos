package jogo.rede;


/**
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia

<p>Rog�rio de Paula Aguilar 8NA
<p>Luiz Fernando P.S. Forgas 8NA

<p>Trabalho de Conclus�o de Curso:
T�cnicas de desenvolvimento de Jogos para dispositivos m�veis

<p>Pacote: jogo.rede
<p>Interface: JogandoException 

<p>Descri��o: Exception que � lan�ada quando o usu�rio tenta entrar numa sala
		em que o jogo est� em andamento
*/
class JogandoException extends Exception{

	/**
		Construtor
	*/
	public JogandoException(String msg){
		super(msg);
	}
}