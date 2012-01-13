/**
 * 
 */
package dnsec.modulos.cadastros.grupo.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import dnsec.shared.database.hibernate.Funcao;

class RenderizadorFuncao extends DefaultListCellRenderer
{
	private static RenderizadorFuncao renderizadorFuncao = new RenderizadorFuncao();

	public static RenderizadorFuncao getInstance()
	{
		return renderizadorFuncao;
	}
	
	private RenderizadorFuncao(){
		
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
				cellHasFocus);
		Funcao funcao = (Funcao)value;
		lbl.setText(funcao.getCompId().getCodFuncaoFunc() + " - " + funcao.getNomeFunc());
		return lbl;
	}
	
	
	
}