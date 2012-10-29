package jogo.rede;

import javax.swing.*;
import javax.swing.tree.*;

/**
2003 - Faculdade Senac de Ciências Exatas e Tecnologia

<p>Rogério de Paula Aguilar 8NA
<p>Luiz Fernando P.S. Forgas 8NA

<p>Trabalho de Conclusão de Curso:
Técnicas de desenvolvimento de Jogos para dispositivos móveis

<p>Pacote: jogo.rede
<p>Interface: MontarArvore

<p>Descrição: Interface utilizada para montar os nós da árvore
		de salas do servidor
*/

public interface MontarArvore{

	/**
		Método que deve ser implementado pelas classes que queiram fazer parte da árvore do servidor
	*/
	public DefaultMutableTreeNode retornarNo(JTree arvore);
}