/**
 * 
 */
package dnsec.modulos.cadastros.associacoes.ui.renderer;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import dnsec.modulos.cadastros.associacoes.ui.FrmAssociacoes;
import dnsec.shared.controller.GerenciadorJanelas;

public class ComboOpcoesExibicaoGrupoRenderer extends DefaultListCellRenderer {
	public Component getListCellRendererComponent(JList arg0, Object arg1, int arg2, boolean arg3, boolean arg4) {
		JLabel lbl = (JLabel) super.getListCellRendererComponent(arg0, arg1, arg2, arg3, arg4);
		if (FrmAssociacoes.EXIBIR_GRUPO_FUNCAO.equals("" + arg1)) {
			lbl.setIcon(GerenciadorJanelas.ICONE_FUNCAO_PEQUENO);
		} else if (FrmAssociacoes.EXIBIR_GRUPO_SISTEMA.equals("" + arg1)) {
			lbl.setIcon(GerenciadorJanelas.ICONE_GRUPO_PEQUENO);
		} else {
			// EXIBIR_GRUPO_USUARIO
			lbl.setIcon(GerenciadorJanelas.ICONE_USUARIO_PEQUENO);
		}
		return lbl;
	}
}