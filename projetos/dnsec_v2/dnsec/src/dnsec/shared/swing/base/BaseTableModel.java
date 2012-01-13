package dnsec.shared.swing.base;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import dnsec.shared.util.Pagina;

public class BaseTableModel extends DefaultTableModel{

	protected Pagina paginaDados = new Pagina();
	
	public int getRowCount()
	{
		int rowCount = 0;
		if(paginaDados != null){
			rowCount = paginaDados.getListObjects().size();
		}
		return rowCount;
	}
	
	
	public Pagina getPaginaDados() {
		return paginaDados;
	}

	public void setPaginaDados(Pagina paginaDados) {
		this.paginaDados = paginaDados;
	}
	
	/**
	 * Por padrão as células não são editáveis
	 * */
	public boolean isCellEditable(int row, int column){
		return false;
	}
	
	

	public BaseTableModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BaseTableModel(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public BaseTableModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public BaseTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public BaseTableModel(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public BaseTableModel(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}
	
}
