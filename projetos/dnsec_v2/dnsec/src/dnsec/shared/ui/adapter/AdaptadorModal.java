/**
 * Adaptador para simular os eventos do mouse
 * para um frame interno modal
 * */

package dnsec.shared.ui.adapter;

import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.MouseInputAdapter;

public class AdaptadorModal extends InternalFrameAdapter {
	    JPanel glass = new JPanel();

	    public JPanel getGlass() {
			return glass;
		}

		public void setGlass(JPanel glass) {
			this.glass = glass;
		}

		public AdaptadorModal() {
	      glass.setOpaque(false);
	      MouseInputAdapter adapter = new MouseInputAdapter(){};
	      glass.addMouseListener(adapter);
	      glass.addMouseMotionListener(adapter);
	    }

		public void internalFrameClosing(InternalFrameEvent e) {
	      glass.setVisible(false);
	    }
		
		public void internalFrameClosed(InternalFrameEvent e) {
		      glass.setVisible(false);
	    }
		
  }
