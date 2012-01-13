package dnsec.modulos.cadastros.associacoes.ui.renderer;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Grupo;

public class GrupoFuncaoRenderer extends DefaultListCellRenderer{
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			Grupo grupo = (Grupo) value;
			lbl.setIcon(GerenciadorJanelas.ICONE_GRUPO_PEQUENO);
			lbl.setText(grupo.getCodGrupoGrup());
			lbl.setToolTipText((grupo.getDescrGrupoGrup() != null ? (grupo.getDescrGrupoGrup()) : ""));
			return lbl;
		}
}
