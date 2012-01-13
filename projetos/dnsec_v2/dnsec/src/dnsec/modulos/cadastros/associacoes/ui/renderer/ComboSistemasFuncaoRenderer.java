/**
 * 
 */
package dnsec.modulos.cadastros.associacoes.ui.renderer;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Sistema;

public class ComboSistemasFuncaoRenderer extends DefaultListCellRenderer {
	public Component getListCellRendererComponent(JList arg0, Object arg1, int arg2, boolean arg3, boolean arg4) {
		JLabel lbl = (JLabel) super.getListCellRendererComponent(arg0, arg1, arg2, arg3, arg4);
		if(arg1 != null && ((Sistema)arg1).getCodSistemaSist() != null) 
			lbl.setText(((Sistema) arg1).getCodSistemaSist());
		lbl.setIcon(GerenciadorJanelas.ICONE_SISTEMA_PEQUENO);
		return lbl;
	}
}