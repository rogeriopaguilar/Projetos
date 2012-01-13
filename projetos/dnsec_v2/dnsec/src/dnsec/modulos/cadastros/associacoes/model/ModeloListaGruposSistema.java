/**
 * 
 */
package dnsec.modulos.cadastros.associacoes.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;

import dnsec.shared.database.hibernate.Grupo;

public class ModeloListaGruposSistema extends AbstractListModel
{
	private List<Grupo> listaGrupos = new LinkedList<Grupo>();

	
	public void adicionarGrupos(Grupo[] grupos)
	{
		for(Grupo atual : grupos)
		{
			if(!listaGrupos.contains(atual))
			{
				listaGrupos.add(atual);
			}
		}
		this.fireContentsChanged(this,0,getSize());
		this.ajustarDadosModeloGrupo();
	}

	public void removerGrupos(Grupo[] grupos)
	{
		this.listaGrupos.removeAll(Arrays.asList(grupos));
		this.fireContentsChanged(this,0,getSize());
		this.ajustarDadosModeloGrupo();
	}

	
	public Object getElementAt(int indice) {
		return listaGrupos.get(indice);
	}

	public int getSize() {
		return listaGrupos.size();
	}
	
	public List<Grupo> getListaGrupos()
	{
		return listaGrupos;
	}

	public void setListaGrupos(List<Grupo> listaGrupos)
	{
		this.listaGrupos = listaGrupos;
		this.fireContentsChanged(this, 0,getSize());
		this.ajustarDadosModeloGrupo();
	}
	
	
	private void ajustarDadosModeloGrupo(){
		Collections.sort(listaGrupos,
				new Comparator(){
					public int compare(Object o1, Object o2) {
						String codGrupoUm = "";
						String codGrupoDois = "";
						int comp = 0;
						Grupo grupoUm = (Grupo) o1;
						Grupo grupoDois = (Grupo) o2;
						if(grupoUm != null && grupoUm.getCodGrupoGrup() != null){
							codGrupoUm = grupoUm.getCodGrupoGrup().trim().toUpperCase();
						}
						if(grupoDois != null && grupoDois.getCodGrupoGrup() != null){
							codGrupoDois = grupoDois.getCodGrupoGrup().trim().toUpperCase();
						}
						comp = codGrupoUm.compareTo(codGrupoDois);
						return comp;
					}
				});
		
	}
	
}