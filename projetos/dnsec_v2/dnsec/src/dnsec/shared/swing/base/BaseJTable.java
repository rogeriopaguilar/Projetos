package dnsec.shared.swing.base;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class BaseJTable extends JTable {


	//Pinta a linha com fundo branco ou cinza ou azul para itens especiais
	static class RenderizadorLinha extends DefaultTableCellRenderer	implements TableCellRenderer {

			static Color corLinha = new Color(225,219,219);
			
			public Component getTableCellRendererComponent(
			     JTable table, Object color,
			     boolean isSelected, boolean hasFocus,
			     int row, int column) {
				 /*JLabel lblConteudo = new JLabel(
						 StringUtils.isEmpty("" + table.getValueAt(row, column)) ? "" : "" + table.getValueAt(row, column)
				 );*/
				 JLabel lblConteudo = (JLabel) super.getTableCellRendererComponent(table, color, isSelected, hasFocus, row, column);
				if(row % 2 == 0)
				{
					lblConteudo.setBackground(Color.WHITE);
				}else
				{
					lblConteudo.setBackground(corLinha);
				}
				
				return lblConteudo;
			}
	}
	
	public static TableCellRenderer renderer = new RenderizadorLinha();
	
	protected void inicializar(){
		this.setDefaultRenderer(Object.class, renderer);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    setAutoCreateColumnsFromModel(false);
	}
	
	public BaseJTable() {
		super();
		inicializar();
	}

	public BaseJTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		inicializar();
	}

	public BaseJTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		inicializar();
	}

	public BaseJTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
		inicializar();
	}

	public BaseJTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		inicializar();
	}

	public BaseJTable(TableModel dm) {
		super(dm);
		inicializar();
	}

	public BaseJTable(Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
		inicializar();
	}

	
}
