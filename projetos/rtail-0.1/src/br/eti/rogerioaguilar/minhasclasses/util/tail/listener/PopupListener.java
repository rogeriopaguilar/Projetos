package br.eti.rogerioaguilar.minhasclasses.util.tail.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;

/**
 * Listener para a janela popup
 * @author Rogério de Paula Aguilar - 2008
 * */
public class PopupListener extends MouseAdapter {
	private JPopupMenu popup;

	public PopupListener(JPopupMenu popup) {
		this.popup = popup;
	}

	public void mousePressed(MouseEvent e) {
		maybeShowPopup(e);
	}

	public void mouseReleased(MouseEvent e) {
		maybeShowPopup(e);
	}

	private void maybeShowPopup(MouseEvent e) {
		if (e.isPopupTrigger())
			this.popup.show(e.getComponent(), e.getX(), e.getY());
	}
}
