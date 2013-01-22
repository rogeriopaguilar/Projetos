package simuladorsemaforos;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
*Programa que simula o tráfego de veículos por um semáforo entre duas ruas que se cruzam e tem direção única
*@author Rogério de Paula Aguilar - rogeriodpaguilarbr@gmail.com
 *
 * os arquivos fontes estão com a codificação utf-8
 * mais informações no arquivo leiame.txt
*/
public class JanelaSobre extends JPanel
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
     this.Tail.setText("Simulador de semáforos 0.1 - 2013 - Rogério de Paula Aguilar");
     this.jLabel1.setText("rogeriodpaguilarbr@gmail.com");
     add(this.Tail, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 15, 0, 15), 0, 0));
     add(this.jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
     
  }
   public JanelaSobre() {
	   init();
   }
	   
}
