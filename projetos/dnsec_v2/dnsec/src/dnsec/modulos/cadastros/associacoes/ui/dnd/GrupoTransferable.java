package dnsec.modulos.cadastros.associacoes.ui.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import dnsec.shared.database.hibernate.Grupo;

public class GrupoTransferable implements Transferable{

	private static DataFlavor grupoDataFlavor ;
	private Grupo[] grupos;

	static{
		try {
			grupoDataFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType +
			";class=\"" + dnsec.shared.database.hibernate.Grupo[].class.getName() + "\"");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DataFlavor getGrupoDataFlavor()
	{
		return grupoDataFlavor;
	}
	
    public GrupoTransferable(Grupo grupos[]){
		this.grupos = grupos;
    }

    public Object getTransferData(DataFlavor flavor)
                             throws UnsupportedFlavorException {
        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return grupos;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { grupoDataFlavor };
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return grupoDataFlavor.equals(flavor);
    }

}
