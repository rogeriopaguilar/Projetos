package dnsec.modulos.cadastros.associacoes.ui.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import dnsec.modulos.cadastros.associacoes.model.ModeloListaObjetosGrupo;
import dnsec.modulos.cadastros.grupo.model.ModeloFuncao;
import dnsec.shared.database.hibernate.Funcao;

public class FuncaoTransferHandler extends TransferHandler{

		private JComponent origem;
	
	  public boolean importData(JComponent c, Transferable t) {
		   JList listaDestino = (JList) c;
		   if(listaDestino == origem)
		   {
			   return false;
		   }
		   if (canImport(c, t.getTransferDataFlavors())) {
	            try {
	                Funcao[] funcoes= (Funcao[])t.getTransferData(FuncaoTransferable.getFuncaoDataFlavor());
	                if(listaDestino.getModel() instanceof ModeloFuncao)
	                {
	                	ModeloFuncao modelo = (ModeloFuncao)listaDestino.getModel();
		                modelo.adicionarFuncoes(funcoes);
	                }else
	                {
	                	ModeloListaObjetosGrupo modelo = (ModeloListaObjetosGrupo)listaDestino.getModel();
		                modelo.adicionarFuncoes(funcoes);
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
	        Funcao[] funcoes = null;
	        if(lista.getSelectedIndices() != null && lista.getSelectedIndices().length > 0)
	        {
	         funcoes = new Funcao[lista.getSelectedIndices().length];	
	         System.arraycopy(lista.getSelectedValues(), 0, funcoes,0,lista.getSelectedIndices().length);	
	        }
	        return new FuncaoTransferable(funcoes);
	    }

	    public int getSourceActions(JComponent c) {
	        return MOVE;
	    }

	    protected void exportDone(JComponent c, Transferable data, int action) {
	        if (action == MOVE) {
                JList listaOrigem = (JList)c;
	        	Funcao[] funcoes;
				try {
					funcoes = (Funcao[])data.getTransferData(FuncaoTransferable.getFuncaoDataFlavor());
	                if(listaOrigem.getModel() instanceof ModeloFuncao)
	                {
	                	ModeloFuncao modelo = (ModeloFuncao)listaOrigem.getModel();
		                modelo.removerFuncoes(funcoes);
	                }else
	                {
	                	ModeloListaObjetosGrupo modelo = (ModeloListaObjetosGrupo)listaOrigem.getModel();
		                modelo.removerFuncoes(funcoes);
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
	            if (FuncaoTransferable.getFuncaoDataFlavor().equals(flavors[i])) {
	                return true;
	            }
	        }
	        return false;
	    }

	
}
