package dnsec.modulos.cadastros.associacoes.ui.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import dnsec.shared.database.hibernate.Usuario;

public class UsuarioTransferable implements Transferable{

	private static DataFlavor usuarioDataFlavor ;
	private Usuario[] usuarios;

	static{
		try {
			usuarioDataFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType +
			";class=\"" + dnsec.shared.database.hibernate.Usuario[].class.getName() + "\"");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DataFlavor getUsuarioDataFlavor()
	{
		return usuarioDataFlavor;
	}
	
    public UsuarioTransferable(Usuario usuarios[]){
		this.usuarios = usuarios;
    }

    public Object getTransferData(DataFlavor flavor)
                             throws UnsupportedFlavorException {
        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return usuarios;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { usuarioDataFlavor };
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return usuarioDataFlavor.equals(flavor);
    }
}
