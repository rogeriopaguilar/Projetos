package dnsec.modulos.cadastros.associacoes.ui.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import dnsec.modulos.cadastros.associacoes.model.ModeloListaObjetosGrupo;
import dnsec.shared.database.hibernate.Usuario;

public class UsuarioTransferHandler extends TransferHandler{

		private JComponent origem;
	
	  public boolean importData(JComponent c, Transferable t) {
		   JList listaDestino = (JList) c;
		   if(listaDestino == origem)
		   {
			   return false;
		   }
		   if (canImport(c, t.getTransferDataFlavors())) {
	            try {
	                Usuario[] usuarios= (Usuario[])t.getTransferData(UsuarioTransferable.getUsuarioDataFlavor());
	                ModeloListaObjetosGrupo modelo = (ModeloListaObjetosGrupo)listaDestino.getModel();
	                modelo.adicionarUsuarios(usuarios);
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
	        Usuario[] usuarios = null;
	        if(lista.getSelectedIndices() != null && lista.getSelectedIndices().length > 0)
	        {
	         usuarios = new Usuario[lista.getSelectedIndices().length];	
	         System.arraycopy(lista.getSelectedValues(), 0, usuarios,0,lista.getSelectedIndices().length);	
	        }
	        return new UsuarioTransferable(usuarios);
	    }

	    public int getSourceActions(JComponent c) {
	        return MOVE;
	    }

	    protected void exportDone(JComponent c, Transferable data, int action) {
	        if (action == MOVE) {
                JList listaOrigem = (JList)c;
	        	Usuario[] usuarios;
				try {
					usuarios = (Usuario[])data.getTransferData(UsuarioTransferable.getUsuarioDataFlavor());
	                ModeloListaObjetosGrupo modelo = (ModeloListaObjetosGrupo)listaOrigem.getModel();
	                modelo.removerUsuarios(usuarios);
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
	            if (UsuarioTransferable.getUsuarioDataFlavor().equals(flavors[i])) {
	                return true;
	            }
	        }
	        return false;
	    }

}