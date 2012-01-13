package dnsec.modulos.cadastros.sistema.model;

import java.util.Vector;

import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.swing.base.BaseTableModel;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.RecursosUtil;

public class TabelaPesquisaSistemaModel extends BaseTableModel{

	public TabelaPesquisaSistemaModel() {
		super();
		setDataVector(new Object[0][8], 
				new Object[]{ 
					RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.codigo.sistema"),
					RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.descricao.sistema"),
					RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.condicao.cadastrar.sistema"),
					RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.nome.banco"),
					RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.nome.servidor"),
					RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.tipo.banco"),
					RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.nome.proprietario"),
					RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.nome.analista")
				}
		);
		
	}

	public int getColumnCount()
	{
		return 8;
	}
	
	public Object getValueAt(int row, int column)
	{
		
		Object retorno = null;
		if(getPaginaDados() != null)
		{
			Sistema sistema = (Sistema) super.getPaginaDados().getListObjects().get(row);
			switch(column){
		
				case 0: return sistema.getCodSistemaSist();
				case 1: return sistema.getDescrSistemaSist();
				case 2: return Constantes.CONSTANTE_SIM.equals(sistema.getCondCadastrarSist()) ? Constantes.CONSTANTE_BLN_SIM : Constantes.CONSTANTE_BLN_NAO;
				case 3: return sistema.getNomeBancoSist();
				case 4: return sistema.getNomeServidorSist();
				case 5: return sistema.getTipoBancoSist();
				case 6: return sistema.getNomeProprietarioSist();
				case 7: return sistema.getNomeAnalistaRespSist();
			}
		}
		return retorno;
	}

	
	public TabelaPesquisaSistemaModel(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public TabelaPesquisaSistemaModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public TabelaPesquisaSistemaModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public TabelaPesquisaSistemaModel(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public TabelaPesquisaSistemaModel(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

}
