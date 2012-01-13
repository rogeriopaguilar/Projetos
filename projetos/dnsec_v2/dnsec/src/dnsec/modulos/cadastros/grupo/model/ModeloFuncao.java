/**
 * 
 */
package dnsec.modulos.cadastros.grupo.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;

import dnsec.shared.database.hibernate.Funcao;

public class ModeloFuncao extends AbstractListModel
{

	List<Funcao> listaFuncoes = new LinkedList<Funcao>();
	
	public List<Funcao> getListaFuncoes() {
		return listaFuncoes;
	}

	public void adicionarFuncoes(Funcao[] funcoes)
	{
		for(Funcao atual : funcoes)
		{
			if(!listaFuncoes.contains(atual))
			{
				listaFuncoes.add(atual);
			}
		}
		this.fireContentsChanged(this,0,getSize());
	}

	public void removerFuncoes(Funcao[] funcoes)
	{
		this.listaFuncoes.removeAll(Arrays.asList(funcoes));
		this.fireContentsChanged(this,0,getSize());
	}

	
	public void setListaFuncoes(List<Funcao> listaFuncoes) {
		this.listaFuncoes = listaFuncoes;
		this.fireContentsChanged(this,0,getSize());
	}

	public int getSize() {
		return listaFuncoes.size();
	}

	public Object getElementAt(int index) {
		return listaFuncoes.get(index);
	}
	
}