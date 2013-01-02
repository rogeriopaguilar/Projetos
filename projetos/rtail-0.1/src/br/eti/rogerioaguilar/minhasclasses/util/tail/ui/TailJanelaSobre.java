package br.eti.rogerioaguilar.minhasclasses.util.tail.ui;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Janela que exibe informações sobre o programa
 * @author Rogério de Paula Aguilar - 2008
 * */
public class TailJanelaSobre extends JPanel
{
	private JLabel Tail;
	private GridBagLayout layoutMain;
	private Border border;
    private JLabel jLabel1;
 
   private void init()
  {
     this.Tail = new JLabel();
     this.layoutMain = new GridBagLayout();
     this.border = BorderFactory.createEtchedBorder();
     this.jLabel1 = new JLabel();
     setLayout(this.layoutMain);
     setBorder(this.border);
     this.Tail.setText("R-Tail 0.1 - BETA - 2008 - Rogério de Paula Aguilar");
     this.jLabel1.setText("rogeriodpaguilarbr@gmail.com");
     add(this.Tail, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 15, 0, 15), 0, 0));
     add(this.jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
     
  }
   public TailJanelaSobre() { 
	   init();
   }
	   
}
