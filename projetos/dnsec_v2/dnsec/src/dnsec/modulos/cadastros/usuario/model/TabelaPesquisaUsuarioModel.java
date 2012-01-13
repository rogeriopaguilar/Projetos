package dnsec.modulos.cadastros.usuario.model;

import java.util.Vector;

import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.swing.base.BaseTableModel;

public class TabelaPesquisaUsuarioModel extends BaseTableModel{




	public TabelaPesquisaUsuarioModel() {
		super();
		
		setDataVector(new Object[0][5], 
				new Object[]{ 
				 "Código",
				 "Nome",
				 "Telefone",
				 "e-mail",
				 "Bloqueado"
				}
		);
	}

	public Object 	getValueAt(int linha, int coluna) 
	{
		Usuario usuario = (Usuario) paginaDados.getListObjects().get(linha);
		
		switch(coluna)
		{
		case 0: return usuario.getCodUsuarioUsua(); //break;
		case 1: return usuario.getNomeUsuarioUsua(); //break;
		case 2: return usuario.getNumTelefoneUsua(); //break;
		case 3: return usuario.getCodEmailUsua(); //break;
		case 4: return usuario.getCondBloqueadoUsua();// break;
		}
		return null;
	}
	
	
	public int getColumnCount()
	{
		return 5;
	}
	
	public TabelaPesquisaUsuarioModel(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public TabelaPesquisaUsuarioModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public TabelaPesquisaUsuarioModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public TabelaPesquisaUsuarioModel(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public TabelaPesquisaUsuarioModel(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}



}
