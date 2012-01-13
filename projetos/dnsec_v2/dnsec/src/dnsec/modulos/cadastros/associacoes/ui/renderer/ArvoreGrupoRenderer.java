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
import dnsec.shared.database.hibernate.Grupo;

public  class ArvoreGrupoRenderer extends DefaultTreeCellRenderer {

	public ArvoreGrupoRenderer() {
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		JLabel lblGrupo = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode no = (DefaultMutableTreeNode) value;
		if (no.getUserObject() instanceof Grupo) {
			Grupo grupo = (Grupo) no.getUserObject();
			lblGrupo.setIcon(GerenciadorJanelas.ICONE_GRUPO_PEQUENO);
			lblGrupo.setText(grupo.getCodGrupoGrup());
			lblGrupo.setToolTipText(grupo.getDescrGrupoGrup() != null ? grupo.getDescrGrupoGrup()	: "");
		}else if (no.getUserObject() instanceof JButton) {
			lblGrupo = GerenciadorJanelas.LABEL_RECARREGAR;
		}
		return lblGrupo;
	}
}