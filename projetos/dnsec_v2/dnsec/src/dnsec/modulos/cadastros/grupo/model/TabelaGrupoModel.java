package dnsec.modulos.cadastros.grupo.model;

import java.util.Vector;

import dnsec.shared.database.hibernate.Grupo;
import dnsec.shared.swing.base.BaseTableModel;
import dnsec.shared.util.RecursosUtil;

public class TabelaGrupoModel extends BaseTableModel{




	public TabelaGrupoModel() {
		super();
		setDataVector(new Object[0][2], new Object[]{ RecursosUtil.getInstance().getResource("key.cadastro.grupos.label.codigo.grupo"),RecursosUtil.getInstance().getResource("key.cadastro.grupos.label.codigo.descricao") });
	}

	public TabelaGrupoModel(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public TabelaGrupoModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public TabelaGrupoModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public TabelaGrupoModel(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public TabelaGrupoModel(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public Object getValueAt(int row, int column)
	{
		Object retorno = null;
		if(getPaginaDados() != null)
		{
			Grupo grupo = (Grupo) super.getPaginaDados().getListObjects().get(row);
			switch(column){
				case 0:
					retorno = grupo.getCodGrupoGrup();
					break;
				case 1:
					retorno = grupo.getDescrGrupoGrup();
					break;
			}
		}
		return retorno;
	}
	
	public int getColumnCount()
	{
		return 2;
	}
}
