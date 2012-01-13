/**
 * 
 */
package dnsec.modulos.cadastros.associacoes.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;

import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.database.hibernate.Grupo;
import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.database.hibernate.Usuario;

public class ModeloListaObjetosGrupo extends DefaultListModel
{
	private List listaObjetos = new LinkedList();

	public void adicionarGrupos(Grupo[] grupos)
	{
		for(Grupo atual : grupos)
		{
			if(!listaObjetos.contains(atual))
			{
				listaObjetos.add(atual);
			}
		}
		this.fireContentsChanged(this,0,getSize());
		ajustarDadosModeloGrupo();		
	}

	public void removerGrupos(Grupo[] grupos)
	{
		this.listaObjetos.removeAll(Arrays.asList(grupos));
		this.fireContentsChanged(this,0,getSize());
		ajustarDadosModeloGrupo();		
	}

	public void adicionarSistemas(Sistema[] sistemas)
	{
		for(Sistema atual : sistemas)
		{
			if(!listaObjetos.contains(atual))
			{
				listaObjetos.add(atual);
			}
		}
		this.fireContentsChanged(this,0,getSize());
		this.ajustarDadosModeloSistema();
	}

	public void removerSistemas(Sistema[] sistemas)
	{
		this.listaObjetos.removeAll(Arrays.asList(sistemas));
		this.fireContentsChanged(this,0,getSize());
		this.ajustarDadosModeloSistema();
	}
	

	public void adicionarUsuarios(Usuario[] usuarios)
	{
		for(Usuario atual : usuarios)
		{
			if(!listaObjetos.contains(atual))
			{
				listaObjetos.add(atual);
			}
		}
		this.fireContentsChanged(this,0,getSize());
		this.ajustarDadosModeloUsuario();
	}

	public void removerUsuarios(Usuario[] usuarios)
	{
		this.listaObjetos.removeAll(Arrays.asList(usuarios));
		this.fireContentsChanged(this,0,getSize());
		this.ajustarDadosModeloUsuario();
	}

	public void adicionarFuncoes(Funcao[] funcoes)
	{
		for(Funcao atual : funcoes)
		{
			if(!listaObjetos.contains(atual))
			{
				listaObjetos.add(atual);
			}
		}
		this.ajustarDadosModeloFuncao();
		this.fireContentsChanged(this,0,getSize());
	}

	public void removerFuncoes(Funcao[] funcoes)
	{
		this.listaObjetos.removeAll(Arrays.asList(funcoes));
		this.ajustarDadosModeloFuncao();
		this.fireContentsChanged(this,0,getSize());
	}
	
	public Object getElementAt(int indice) {
		return listaObjetos.get(indice);
	}

	public int getSize() {
		return listaObjetos.size();
	}

	public List getListaObjetos() {
		return listaObjetos;
	}

	private void ajustarDadosModeloUsuario(){
		Collections.sort(listaObjetos,
				new Comparator(){
					public int compare(Object o1, Object o2) {
						String codUsuarioUm = "";
						String codUsuarioDois = "";
						int comp = 0;
						Usuario usuarioUm = (Usuario) o1;
						Usuario usuarioDois = (Usuario) o2;
						if(usuarioUm != null && usuarioUm.getCodUsuarioUsua() != null){
							codUsuarioUm = usuarioUm.getCodUsuarioUsua().trim().toUpperCase();
						}
						if(usuarioDois != null && usuarioDois.getCodUsuarioUsua() != null){
							codUsuarioDois = usuarioDois.getCodUsuarioUsua().trim().toUpperCase();
						}
						comp = codUsuarioUm.compareTo(codUsuarioDois);
						return comp;
					}
				});
	}
	
	
	
	private void ajustarDadosModeloGrupo(){
		Collections.sort(listaObjetos,
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

	private void ajustarDadosModeloSistema(){
		Collections.sort(listaObjetos,
				new Comparator(){
					public int compare(Object o1, Object o2) {
						String codSistemaUm = "";
						String codSistemaDois = "";
						int comp = 0;
						Sistema sistemaUm = (Sistema) o1;
						Sistema sistemaDois = (Sistema) o2;
						if(sistemaUm != null && sistemaUm.getCodSistemaSist() != null){
							codSistemaUm = sistemaUm.getCodSistemaSist().trim().toUpperCase();
						}
						if(sistemaDois != null && sistemaDois.getCodSistemaSist() != null){
							codSistemaDois = sistemaDois.getCodSistemaSist().trim().toUpperCase();
						}
						comp = codSistemaUm.compareTo(codSistemaDois);
						return comp;
					}
				});
		
	}

	
	private void ajustarDadosModeloFuncao(){
		if(listaObjetos != null && listaObjetos.size() > 0)
		{
			//Retira todos os sistemas da lista
			Iterator it = listaObjetos.iterator();
			while(it.hasNext())
			{
				if(it.next() instanceof Sistema){
					it.remove();
				}
			}
			
			//Ordena as funções pelo código do sistema
			Collections.sort(listaObjetos,
			new Comparator(){
				public int compare(Object o1, Object o2) {
					String codSistemaUm = "";
					String codSistemaDois = "";
					int comp = 0;
					Funcao funcaoUm = (Funcao) o1;
					Funcao funcaoDois = (Funcao) o2;
					if(funcaoUm != null && funcaoUm.getSistema() != null && funcaoUm.getSistema().getCodSistemaSist() != null){
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

			if(listaObjetos.size() > 0){
				//Adiciona os sistemas para servirem como separador
				it = listaObjetos.iterator();
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
					listaObjetos.add( sistemasAAdicionar.get(sistema) + cont, sistema);
					//System.out.println(sistemasAAdicionar.get(sistema));
					cont++;
				}
			}
		}
	}
	
	public void setListaObjetos(List listaObjetos) {
		this.listaObjetos = listaObjetos;
		if(listaObjetos.size() > 0 && listaObjetos.get(0) instanceof Funcao){
			this.ajustarDadosModeloFuncao();
		}
		this.fireContentsChanged(this,0,getSize());
	}

	public void fireContentsChanged(){
		this.fireContentsChanged(this,0,getSize());
	}	
}