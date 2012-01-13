package dnsec.modulos.cadastros.associacoes.ui.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import dnsec.shared.database.hibernate.Funcao;

public class FuncaoTransferable implements Transferable{

	private static DataFlavor funcaoDataFlavor ;
	private Funcao[] funcoes;

	static{
		try {
			funcaoDataFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType +
			";class=\"" + dnsec.shared.database.hibernate.Funcao[].class.getName() + "\"");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DataFlavor getFuncaoDataFlavor()
	{
		return funcaoDataFlavor;
	}
	
    public FuncaoTransferable(Funcao funcoes[]){
		this.funcoes = funcoes;
    }

    public Object getTransferData(DataFlavor flavor)
                             throws UnsupportedFlavorException {
        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return funcoes;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { funcaoDataFlavor };
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return funcaoDataFlavor.equals(flavor);
    }



}
