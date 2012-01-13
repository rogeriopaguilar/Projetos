package dnsec.modulos.cadastros.funcao.model;

import java.util.Vector;

import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.swing.base.BaseTableModel;
import dnsec.shared.util.RecursosUtil;

public class TabelaPesquisaFuncaoModel extends BaseTableModel{




	public TabelaPesquisaFuncaoModel() {
		super();
		
		setDataVector(new Object[0][5], 
				new Object[]{ 
					RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.codigo.funcao"),
					RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.nome.funcao"),
					RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.descricao.funcao"),
					RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.sistema.funcao"),
					RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.sistema.pai.funcao"),
					RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.funcao.pai.funcao")
				}
		);
	}

	public Object 	getValueAt(int linha, int coluna) 
	{
		Funcao funcao = (Funcao) paginaDados.getListObjects().get(linha);
		
		switch(coluna)
		{
		case 0: return funcao.getCompId().getCodFuncaoFunc(); //break;
		case 1: return funcao.getNomeFunc(); //break;
		case 2: return funcao.getDescrFuncaoFunc(); //break;
		case 3: return funcao.getSistema() != null ? funcao.getSistema().getCodSistemaSist() : ""; //break;
		case 4: return funcao.getCodSistemaPaiFunc();// break;
		case 5: return (funcao.getFuncaoPai() != null ? funcao.getFuncaoPai().getCompId().getCodFuncaoFunc() : null); //break;
		}
		return null;
	}
	
	
	public int getColumnCount()
	{
		return 5;
	}
	
	public TabelaPesquisaFuncaoModel(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public TabelaPesquisaFuncaoModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public TabelaPesquisaFuncaoModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public TabelaPesquisaFuncaoModel(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public TabelaPesquisaFuncaoModel(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}



}
