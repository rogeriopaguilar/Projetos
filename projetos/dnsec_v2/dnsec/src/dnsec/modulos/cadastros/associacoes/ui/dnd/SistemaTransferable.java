package dnsec.modulos.cadastros.associacoes.ui.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import dnsec.shared.database.hibernate.Sistema;

public class SistemaTransferable implements Transferable{

	private static DataFlavor sistemaDataFlavor ;
	private Sistema[] sistemas;

	static{
		try {
			sistemaDataFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType +
			";class=\"" + dnsec.shared.database.hibernate.Sistema[].class.getName() + "\"");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DataFlavor getSistemaDataFlavor()
	{
		return sistemaDataFlavor;
	}
	
    public SistemaTransferable(Sistema sistemas[]){
		this.sistemas = sistemas;
    }

    public Object getTransferData(DataFlavor flavor)
                             throws UnsupportedFlavorException {
        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return sistemas;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { sistemaDataFlavor };
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return sistemaDataFlavor.equals(flavor);
    }



}
