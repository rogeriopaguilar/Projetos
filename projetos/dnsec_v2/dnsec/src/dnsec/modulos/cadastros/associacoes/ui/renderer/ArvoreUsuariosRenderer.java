package dnsec.modulos.cadastros.associacoes.ui.renderer;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Usuario;

public class ArvoreUsuariosRenderer extends DefaultTreeCellRenderer{
		
	public Component getTreeCellRendererComponent(JTree tree,Object value, boolean sel, boolean expanded, boolean leaf,	int row, boolean hasFocus) {
			JLabel lblUsuario = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			DefaultMutableTreeNode no = (DefaultMutableTreeNode) value;
			if (no.getUserObject() instanceof Usuario) {
				Usuario usuario = (Usuario) no.getUserObject();
				lblUsuario.setIcon(GerenciadorJanelas.ICONE_USUARIO_PEQUENO);
				lblUsuario.setText(usuario.getCodUsuarioUsua());
				lblUsuario.setToolTipText(usuario.getNomeUsuarioUsua() != null ? usuario.getNomeUsuarioUsua() : "");
			}else if (no.getUserObject() instanceof JButton) {
				lblUsuario = GerenciadorJanelas.LABEL_RECARREGAR;
			}
			
			return lblUsuario;
		}

	
}
