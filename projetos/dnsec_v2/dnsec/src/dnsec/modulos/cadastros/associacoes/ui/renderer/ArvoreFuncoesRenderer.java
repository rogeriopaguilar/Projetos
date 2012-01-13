/**
 * 
 */
package dnsec.modulos.cadastros.associacoes.ui.renderer;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.database.hibernate.Sistema;

public final class ArvoreFuncoesRenderer extends DefaultTreeCellRenderer {

	public Component getTreeCellRendererComponent(JTree tree,Object value, boolean sel, boolean expanded, boolean leaf,	int row, boolean hasFocus) {
		JLabel lblFuncao = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode no = (DefaultMutableTreeNode) value;
		if (no.getUserObject() instanceof Funcao) {
			Funcao funcao = (Funcao) no.getUserObject();
			lblFuncao.setIcon(GerenciadorJanelas.ICONE_FUNCAO_PEQUENO);
			lblFuncao.setText(funcao.getCompId().getCodFuncaoFunc() + " - " + funcao.getNomeFunc());
			lblFuncao.setToolTipText(
					funcao.getNomeFunc() + " - " + funcao.getDescrFuncaoFunc()
			);
		}else if (no.getUserObject() instanceof Sistema) {
			Sistema sistema = (Sistema) no.getUserObject();
			lblFuncao.setIcon(GerenciadorJanelas.ICONE_SISTEMA_PEQUENO);
			lblFuncao.setText(sistema.getCodSistemaSist());
			lblFuncao.setToolTipText(
					sistema.getDescrSistemaSist()
			);
		}else if (no.getUserObject() instanceof JButton) {
			lblFuncao = GerenciadorJanelas.LABEL_RECARREGAR;
		}
		
		return lblFuncao;
	}
}