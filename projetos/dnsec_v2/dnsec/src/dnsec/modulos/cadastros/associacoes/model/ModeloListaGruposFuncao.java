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

public class ModeloListaGruposFuncao extends AbstractListModel
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

	
	/*private void ajustarDadosModeloFuncao(){
		if(listaGrupos != null && listaGrupos.size() > 0)
		{
			//Retira todos os sistemas da lista
			Iterator it = listaGrupos.iterator();
			while(it.hasNext())
			{
				if(it.next() instanceof Sistema){
					it.remove();
				}
			}
			
			//Ordena os grupos pelo código do sistema
			Collections.sort(listaGrupos,
			new Comparator(){
				public int compare(Object o1, Object o2) {
					String codSistemaUm = "";
					String codSistemaDois = "";
					int comp = 0;
					Grupo grupoUm = (Grupo) o1;
					Grupo grupoDois = (Grupo) o2;
					if(grupoUm != null && grupoUm.getSistema() != null && funcaoUm.getSistema().getCodSistemaSist() != null){
						codSistemaUm = funcaoUm.getSistema().getCodSistemaSist().trim().toUpperCase();
					}
					if(funcaoDois != null && funcaoDois.getSistema() != null && funcaoDois.getSistema().getCodSistemaSist() != null){
						codSistemaDois = funcaoDois.getSistema().getCodSistemaSist().trim().toUpperCase();
					}
					comp = codSistemaUm.compareTo(codSistemaDois);
					if(comp == 0){
						//Ordena pelo código da função
						comp = funcaoUm.getCompId().getCodFuncaoFunc().compareTo(funcaoDois.getCompId().getCodFuncaoFunc());
					}
					return comp;
				}
			});

			if(listaGrupos.size() > 0){
				//Adiciona os sistemas para servirem como separador
				it = listaGrupos.iterator();
				int indice = 0;
				
				final Map<Sistema, Integer> sistemasAAdicionar = new HashMap<Sistema, Integer>();
				sistemasAAdicionar.clear();
				String codSistemaSist = null;
				Sistema sistema = new Sistema();
				while(it.hasNext()){
					Object obj = it.next();
					codSistemaSist = ((Funcao)obj).getCompId().getCodSistemaSist();
					sistema = new Sistema();
					sistema.setCodSistemaSist(codSistemaSist);
					if(sistemasAAdicionar.get(sistema) == null){
						sistemasAAdicionar.put(sistema, indice);
					}
					indice++;
				}

				List listadSistemasOrdenadosPeloIndice = new LinkedList(sistemasAAdicionar.keySet()); 
					Collections.sort(
						listadSistemasOrdenadosPeloIndice,
						new Comparator(){
							public int compare(Object o1, Object o2) {
								Sistema sistemaUm = (Sistema)o1;
								Sistema sistemaDois = (Sistema)o2;
								return sistemasAAdicionar.get(sistemaUm).compareTo(sistemasAAdicionar.get(sistemaDois));
							}
						}
					);
				it = listadSistemasOrdenadosPeloIndice.iterator();
				int cont = 0;
				while(it.hasNext()){
					sistema = (Sistema) it.next();
					listaGrupos.add( sistemasAAdicionar.get(sistema) + cont, sistema);
					//System.out.println(sistemasAAdicionar.get(sistema));
					cont++;
				}
			}
		}
	}*/
	
	
	
	
}