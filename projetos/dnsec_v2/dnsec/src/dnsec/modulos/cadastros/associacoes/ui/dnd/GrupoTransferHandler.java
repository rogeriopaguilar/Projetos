package dnsec.modulos.cadastros.associacoes.ui.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import dnsec.modulos.cadastros.associacoes.model.ModeloListaGruposFuncao;
import dnsec.modulos.cadastros.associacoes.model.ModeloListaGruposSistema;
import dnsec.modulos.cadastros.associacoes.model.ModeloListaGruposUsuario;
import dnsec.shared.database.hibernate.Grupo;

public class GrupoTransferHandler extends TransferHandler{

	private JComponent origem;
	
	  public boolean importData(JComponent c, Transferable t) {
		   JList listaDestino = (JList) c;
		   if(listaDestino == origem)
		   {
			   return false;
		   }
		   if (canImport(c, t.getTransferDataFlavors())) {
	            try {
	                Grupo[] grupos= (Grupo[])t.getTransferData(GrupoTransferable.getGrupoDataFlavor());
					if(listaDestino.getModel() instanceof ModeloListaGruposSistema){ 
						ModeloListaGruposSistema modelo = (ModeloListaGruposSistema)listaDestino.getModel();
		                modelo.adicionarGrupos(grupos);
					}else if(listaDestino.getModel() instanceof ModeloListaGruposUsuario){
						ModeloListaGruposUsuario modelo = (ModeloListaGruposUsuario)listaDestino.getModel();
		                modelo.adicionarGrupos(grupos);
					}else{
						ModeloListaGruposFuncao modelo = (ModeloListaGruposFuncao)listaDestino.getModel();
		                modelo.adicionarGrupos(grupos);
					}
	                return true;
	            } catch (UnsupportedFlavorException ufe) {
	                System.out.println("importData: unsupported data flavor");
	            } catch (IOException ioe) {
	                System.out.println("importData: I/O exception");
	            }
	        }
	        return false;
	    }

	    protected Transferable createTransferable(JComponent c) {
	        JList lista = (JList)c;
	        origem = c;
	        Grupo[] grupos = null;
	        if(lista.getSelectedIndices() != null && lista.getSelectedIndices().length > 0)
	        {
	         grupos = new Grupo[lista.getSelectedIndices().length];	
	         System.arraycopy(lista.getSelectedValues(), 0, grupos,0,lista.getSelectedIndices().length);	
	        }
	        return new GrupoTransferable(grupos);
	    }

	    public int getSourceActions(JComponent c) {
	        return MOVE;
	    }

	    protected void exportDone(JComponent c, Transferable data, int action) {
	        if (action == MOVE) {
                JList listaOrigem = (JList)c;
	        	Grupo[] grupos;
				try {
					grupos = (Grupo[])data.getTransferData(GrupoTransferable.getGrupoDataFlavor());
					if(listaOrigem.getModel() instanceof ModeloListaGruposSistema){ 
						ModeloListaGruposSistema modelo = (ModeloListaGruposSistema)listaOrigem.getModel();
						modelo.removerGrupos(grupos);
					}else if(listaOrigem.getModel() instanceof ModeloListaGruposUsuario){
						ModeloListaGruposUsuario modelo = (ModeloListaGruposUsuario)listaOrigem.getModel();
						modelo.removerGrupos(grupos);
					}else{
						ModeloListaGruposFuncao modelo = (ModeloListaGruposFuncao)listaOrigem.getModel();
						modelo.removerGrupos(grupos);
					}
				} catch (UnsupportedFlavorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }

	    public boolean canImport(JComponent c, DataFlavor[] flavors) {
	        for (int i = 0; i < flavors.length; i++) {
	            if (GrupoTransferable.getGrupoDataFlavor().equals(flavors[i])) {
	                return true;
	            }
	        }
	        return false;
	    }

	
}
