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
import dnsec.shared.database.hibernate.Sistema;

public class ArvoreSistemasRenderer extends DefaultTreeCellRenderer {
	public Component getTreeCellRendererComponent(JTree tree,Object value, boolean sel, boolean expanded, boolean leaf,	int row, boolean hasFocus) {
		JLabel lblSistema = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode no = (DefaultMutableTreeNode) value;
		if (no.getUserObject() instanceof Sistema) {
			Sistema sistema = (Sistema) no.getUserObject();
			lblSistema.setIcon(GerenciadorJanelas.ICONE_SISTEMA_PEQUENO);
			lblSistema.setText(sistema.getCodSistemaSist());
			lblSistema.setToolTipText(sistema.getCodSistemaSist() != null ? sistema.getCodSistemaSist() : "");
		}else if (no.getUserObject() instanceof JButton) {
			lblSistema.setText("");
			lblSistema.setIcon(GerenciadorJanelas.ICONE_RECARREGAR);
		}
		return lblSistema;
	}
}