package jogo.rede;

import javax.swing.*;
import javax.swing.tree.*;

/**
2003 - Faculdade Senac de Ci�ncias Exatas e Tecnologia

<p>Rog�rio de Paula Aguilar 8NA
<p>Luiz Fernando P.S. Forgas 8NA

<p>Trabalho de Conclus�o de Curso:
T�cnicas de desenvolvimento de Jogos para dispositivos m�veis

<p>Pacote: jogo.rede
<p>Interface: MontarArvore

<p>Descri��o: Interface utilizada para montar os n�s da �rvore
		de salas do servidor
*/

public interface MontarArvore{

	/**
		M�todo que deve ser implementado pelas classes que queiram fazer parte da �rvore do servidor
	*/
	public DefaultMutableTreeNode retornarNo(JTree arvore);
}