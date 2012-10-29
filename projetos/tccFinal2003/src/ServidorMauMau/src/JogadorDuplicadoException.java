package jogo.rede;

/**
2003 - Faculdade Senac de Ciências Exatas e Tecnologia

<p>Rogério de Paula Aguilar 8NA
<p>Luiz Fernando P.S. Forgas 8NA

<p>Trabalho de Conclusão de Curso:
Técnicas de desenvolvimento de Jogos para dispositivos móveis

<p>Pacote: jogo.rede
<p>Interface: JogadorDuplicadoException

<p>Descrição: Exception que é lançada quando o usuário tenta entrar com um apelido
		 que já existe na sala
*/

class JogadorDuplicadoException extends Exception{
	/**
		Construtor
	*/
	public JogadorDuplicadoException(String msg){
		super(msg);
	}

}