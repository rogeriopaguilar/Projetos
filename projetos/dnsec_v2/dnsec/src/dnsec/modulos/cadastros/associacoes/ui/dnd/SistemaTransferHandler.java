package dnsec.modulos.cadastros.associacoes.ui.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import dnsec.modulos.cadastros.associacoes.model.ModeloListaObjetosGrupo;
import dnsec.shared.database.hibernate.Sistema;

public class SistemaTransferHandler extends TransferHandler{

		private JComponent origem;
	
	  public boolean importData(JComponent c, Transferable t) {
		   JList listaDestino = (JList) c;
		   if(listaDestino == origem)
		   {
			   return false;
		   }
		   if (canImport(c, t.getTransferDataFlavors())) {
	            try {
	                Sistema[] sistemas= (Sistema[])t.getTransferData(SistemaTransferable.getSistemaDataFlavor());
	                ModeloListaObjetosGrupo modelo = (ModeloListaObjetosGrupo)listaDestino.getModel();
	                modelo.adicionarSistemas(sistemas);
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
	        Sistema[] sistemas = null;
	        if(lista.getSelectedIndices() != null && lista.getSelectedIndices().length > 0)
	        {
	         sistemas = new Sistema[lista.getSelectedIndices().length];	
	         System.arraycopy(lista.getSelectedValues(), 0, sistemas,0,lista.getSelectedIndices().length);	
	        }
	        return new SistemaTransferable(sistemas);
	    }

	    public int getSourceActions(JComponent c) {
	        return MOVE;
	    }

	    protected void exportDone(JComponent c, Transferable data, int action) {
	        if (action == MOVE) {
                JList listaOrigem = (JList)c;
	        	Sistema[] sistemas;
				try {
					sistemas = (Sistema[])data.getTransferData(SistemaTransferable.getSistemaDataFlavor());
	                ModeloListaObjetosGrupo modelo = (ModeloListaObjetosGrupo)listaOrigem.getModel();
	                modelo.removerSistemas(sistemas);
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
	            if (SistemaTransferable.getSistemaDataFlavor().equals(flavors[i])) {
	                return true;
	            }
	        }
	        return false;
	    }

}
