package dnsec.modulos.cadastros.associacoes.ui.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import dnsec.modulos.cadastros.associacoes.ui.dnd.FuncaoTransferHandler;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.swing.base.BaseJList;

public class GruposRelacionadosRenderer extends DefaultListCellRenderer{
		private Color codFundoPainelRelacionado = new Color(191, 208, 213);
		private String codSistemaAnterior = "";
		private BaseJList listaRelacionadosGrupo;
		
		public GruposRelacionadosRenderer(BaseJList listaRelacionadosGrupo){
			this.listaRelacionadosGrupo = listaRelacionadosGrupo;			
		}
		
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

			JLabel lbl = (JLabel) super.getListCellRendererComponent(list,value, index, isSelected, cellHasFocus);
			lbl.setIcon(GerenciadorJanelas.ICONE_FUNCAO_PEQUENO);

			if (value instanceof Funcao) {
				Funcao funcao = (Funcao) value;
				lbl.setText(funcao.getCompId().getCodFuncaoFunc()+ " - " + funcao.getNomeFunc() + " - " + funcao.getDescrFuncaoFunc());
			} else if (value instanceof Sistema) {
				Sistema sistema = (Sistema) value;
				lbl.setIcon(GerenciadorJanelas.ICONE_SISTEMA_PEQUENO);
				lbl.setText(sistema.getCodSistemaSist() + " - " + sistema.getDescrSistemaSist());
				//Se for a lista de funções, os sistemas servem como separadores
				if(listaRelacionadosGrupo.getTransferHandler() instanceof FuncaoTransferHandler)
				{
					lbl.setBackground(codFundoPainelRelacionado);
					lbl.setText(sistema.getCodSistemaSist());
				}
			} else if (value instanceof Usuario) {
				Usuario usuario = (Usuario) value;
				lbl.setIcon(GerenciadorJanelas.ICONE_USUARIO_PEQUENO);
				lbl.setText(usuario.getCodUsuarioUsua() + " - " + usuario.getNomeUsuarioUsua());
			}
			return lbl;
		}

	
}
