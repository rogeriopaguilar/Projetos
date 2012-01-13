package dnsec.shared.swing.base;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.text.MaskFormatter;

import com.greef.ui.calendar.JCalendar;

public class PainelSelecaoData extends JPanel {

	private JCalendar calendario;
	private JToggleButton botaoSelecao;
	private JDialog frmSelecao;
	private JFormattedTextField txtData;
	private MaskFormatter formatter;
	private SimpleDateFormat fmtData;
	private Date data = null;
	private BaseJButton botaoOk = new BaseJButton("OK");
	private BaseJButton botaoCancelar = new BaseJButton("Cancelar");
	
	public void setEnabled(boolean b){
		super.setEnabled(b);
		botaoSelecao.setEnabled(b);
		txtData.setEditable(b);
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
		if(data != null)
		{
			txtData.setText(fmtData.format(data));
		}else{
			txtData.setText("__/__/____");
		}
	}

	private void inicializarComponentes(){
		calendario = new JCalendar();
		frmSelecao = new JDialog ();
		frmSelecao.addWindowListener(new WindowListener(){

			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void windowClosing(WindowEvent arg0) {
				botaoSelecao.setSelected(false);
			}

			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		botaoSelecao = new JToggleButton(new ImageIcon(getClass().getClassLoader().getResource("config/resource/data.gif")));
		frmSelecao.getContentPane().setLayout(new BorderLayout());
		frmSelecao.getContentPane().add(calendario, BorderLayout.CENTER);

		JPanel painelBotoes = new JPanel();
		painelBotoes.setLayout(new GridLayout(1,2));
		painelBotoes.add(botaoOk);
		painelBotoes.add(botaoCancelar);

		botaoOk.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				data = calendario.getSelectedDate();
				if(data != null)
				{
					txtData.setText(fmtData.format(data));
				}else{

					txtData.setText("__/__/____");
				}
				botaoSelecao.setSelected(false);
				frmSelecao.dispose();
			}
			
		});
		botaoCancelar.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent arg0) {
						botaoSelecao.setSelected(false);
						frmSelecao.dispose();
					}
				}
		);
		
		frmSelecao.getContentPane().add(painelBotoes, BorderLayout.SOUTH);
		
		fmtData = new SimpleDateFormat("dd/MM/yyyy");
		try {
			formatter = new MaskFormatter("##/##/####");
			formatter.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txtData = new JFormattedTextField(formatter);
		txtData.setInputVerifier(
				new InputVerifier(){
					
					@Override
					public boolean verify(JComponent arg0) {
						JFormattedTextField txtData = ((JFormattedTextField)arg0);
						String texto = txtData.getText();
						boolean valido = true;
						try {
							data = fmtData.parse(texto);
						} catch (ParseException e) {
							valido = false;
							data = null;
						}
						return valido;
					}}
		);
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.weightx = 100;
		constraints.weighty = 100;
		add(txtData, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.LAST_LINE_END;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.weightx = 100;
		constraints.weighty = 100;
		add(botaoSelecao, constraints);
		
		botaoSelecao.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				if( ((JToggleButton)arg0.getSource()).isSelected() )
				{
					frmSelecao.pack();
					String texto = txtData.getText();
					try {
						data = fmtData.parse(texto);
						calendario.setDate(data);
					} catch (ParseException e) {
						data = null;
					}
					//calendario.setTodayDate();
					frmSelecao.setModal(true);
					frmSelecao.setResizable(false);
					frmSelecao.setLocation(MouseInfo.getPointerInfo().getLocation());
					frmSelecao.setVisible(true);
				}
			}});
		
		/*botaoSelecao.addMouseMotionListener(new MouseMotionListener(){

			public void mouseDragged(MouseEvent arg0) {
				
			}

			public void mouseMoved(MouseEvent arg0) {
				frmSelecao.setLocation(arg0.getX(), arg0.getY());
			}
			
			
		});*/
	}
	
	public PainelSelecaoData() {
		inicializarComponentes();
	}

	public PainelSelecaoData(LayoutManager arg0) {
		super(arg0);
		inicializarComponentes();
	}

	public PainelSelecaoData(boolean arg0) {
		super(arg0);
		inicializarComponentes();
	}

	public PainelSelecaoData(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		inicializarComponentes();
	}

	
	public static void main(String[] args)
	{
		JFrame frm = new JFrame();
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PainelSelecaoData painelSelecaoData = new PainelSelecaoData();
		painelSelecaoData.setData(new Date());
		frm.getContentPane().add(painelSelecaoData);
		frm.pack();
		frm.setVisible(true);
		
	}
}
