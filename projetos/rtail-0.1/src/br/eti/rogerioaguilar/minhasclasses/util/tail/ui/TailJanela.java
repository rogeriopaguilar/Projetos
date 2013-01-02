package br.eti.rogerioaguilar.minhasclasses.util.tail.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.eti.rogerioaguilar.minhasclasses.util.tail.ThreadTail;
import br.eti.rogerioaguilar.minhasclasses.util.tail.listener.LogListener;
import br.eti.rogerioaguilar.minhasclasses.util.tail.listener.PopupListener;

/**
 * Janela principal da aplicação
 * @author Rogério de Paula Aguilar - 2008
 * */
public class TailJanela extends JFrame {
	private BorderLayout layoutMain;
	private JPanel panelCenter;
	private JMenuBar menuBar;
	private JMenu mnuArquivo;
	private JMenuItem menuFileExit;
	private JMenu menuHelp;
	private JMenuItem menuHelpAbout;
	private JLabel statusBar;
	private JMenuItem mnuAbrir;
	private BorderLayout borderLayout1;
	private JTextArea txtLog;
	private Thread threadTail;
	private ThreadTail tail;
	private JPopupMenu popup;
	private JMenu mnuEditar;
	private JMenuItem mnuCorTexto;
	private JMenuItem mnuCorFundo;
	private List listaArquivos;
	private ActionListener listenerAbrir;

	public TailJanela() {
		this.layoutMain = new BorderLayout();
		this.panelCenter = new JPanel();
		this.menuBar = new JMenuBar();
		this.mnuArquivo = new JMenu();
		this.menuFileExit = new JMenuItem();
		this.menuHelp = new JMenu();
		this.menuHelpAbout = new JMenuItem();
		this.statusBar = new JLabel();
		this.mnuAbrir = new JMenuItem();
		this.borderLayout1 = new BorderLayout();
		this.txtLog = new JTextArea();
		this.threadTail = null;
		this.tail = null;
		this.popup = null;
		this.mnuEditar = new JMenu();
		this.mnuCorTexto = new JMenuItem();
		this.mnuCorFundo = new JMenuItem();
		this.listaArquivos = new LinkedList();

		this.listenerAbrir = new ListenerAbrir();
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		setJMenuBar(this.menuBar);
		getContentPane().setLayout(this.layoutMain);
		this.panelCenter.setLayout(this.borderLayout1);
		setSize(new Dimension(
				Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit
						.getDefaultToolkit().getScreenSize().height / 2));
		setLocation(
				(Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2);

		setTitle("R-Tail 0.1 - BETA");
		this.mnuArquivo.setText("Arquivo");
		this.menuFileExit.setText("Sair");
		this.menuFileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				TailJanela.this.fileExit_ActionPerformed(ae);
			}
		});
		this.menuHelp.setText("Ajuda");
		this.menuHelpAbout.setText("Sobre");
		this.menuHelpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				TailJanela.this.helpAbout_ActionPerformed(ae);
			}
		});
		this.statusBar.setText("");
		this.mnuAbrir.setText("Abrir");
		this.mnuArquivo.add(this.mnuAbrir);
		this.mnuAbrir.addActionListener(this.listenerAbrir);
		this.mnuArquivo.addSeparator();
		this.mnuArquivo.addSeparator();
		this.mnuArquivo.add(this.menuFileExit);
		this.menuBar.add(this.mnuArquivo);
		this.mnuEditar.add(this.mnuCorTexto);
		this.mnuEditar.add(this.mnuCorFundo);
		this.menuBar.add(this.mnuEditar);
		this.menuHelp.add(this.menuHelpAbout);
		this.menuBar.add(this.menuHelp);
		getContentPane().add(this.statusBar, "South");
		getContentPane().add(this.panelCenter, "Center");
		this.panelCenter.add(new JScrollPane(this.txtLog), "Center");
		this.txtLog.setEditable(false);
		this.txtLog.setBackground(Color.GRAY);
		this.txtLog.setForeground(Color.WHITE);
		this.popup = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem("Limpar texto");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TailJanela.this.popUp_ActionPerformed(e);
			}
		});
		this.mnuCorFundo.setText("Cor de Fundo");
		this.mnuCorFundo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Color cor = JColorChooser.showDialog(TailJanela.this,
						"Selecione a cor",
						TailJanela.this.txtLog.getBackground());

				if (cor != null)
					TailJanela.this.txtLog.setBackground(cor);
			}
		});
		this.mnuEditar.setText("Editar");
		this.mnuCorTexto.setText("Cor do Texto");
		this.mnuCorTexto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Color cor = JColorChooser.showDialog(TailJanela.this,
						"Selecione a cor",
						TailJanela.this.txtLog.getForeground());

				if (cor != null)
					TailJanela.this.txtLog.setForeground(cor);
			}
		});
		this.popup.add(menuItem);
		MouseListener popupListener = new PopupListener(this.popup);
		this.txtLog.addMouseListener(popupListener);
	}

	void atualizaListaUltimosArquivos() {
		this.mnuArquivo.removeAll();
		this.mnuArquivo.add(this.mnuAbrir);
		this.mnuArquivo.addSeparator();

		int contMaximo = 4;
		if (this.listaArquivos.size() < 4)
			contMaximo = this.listaArquivos.size();

		for (int contArquivos = 0; contArquivos < contMaximo; contArquivos++) {
			JMenuItem itemArquivo = new JMenuItem();
			itemArquivo.setText("" + this.listaArquivos.get(contArquivos));
			final File arquivo = (File) this.listaArquivos.get(contArquivos);

			itemArquivo.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent evt) {
					if (arquivo != null) {

						synchronized (TailJanela.this) {
							if (TailJanela.this.tail != null) {
								TailJanela.this.tail.setContinuar(false);
							}
							TailJanela.this.tail = new ThreadTail(arquivo,
									new LogListener() {
										public void log(String msg) {
											TailJanela.this.txtLog.append(msg);
											TailJanela.this.txtLog
													.setCaretPosition(TailJanela.this.txtLog
															.getText().length() - 1 >= 0 ? TailJanela.this.txtLog
															.getText().length() - 1
															: 0);
										}

										public void inicioLog(Object obj) {
											TailJanela.this.txtLog.setText("");
										}
									});
							TailJanela.this.setTitle(arquivo.getAbsolutePath());
							new Thread(TailJanela.this.tail).start();
						}

					}
				}
			});
			this.mnuArquivo.add(itemArquivo);
		}
		this.mnuArquivo.addSeparator();
		this.mnuArquivo.add(this.menuFileExit);
	}

	void fileExit_ActionPerformed(ActionEvent e) {
		System.exit(0);
	}

	void helpAbout_ActionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(this, new TailJanelaSobre(), "Sobre", -1);
	}

	void popUp_ActionPerformed(ActionEvent e) {
		this.txtLog.setText("");
		this.txtLog.setCaretPosition(0);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TailJanela tailJanela = new TailJanela();
				tailJanela.setDefaultCloseOperation(3);
				TailJanela.setDefaultLookAndFeelDecorated(true);
				tailJanela.setVisible(true);
			}
		});
	}

	private class ListenerAbrir implements ActionListener {
		private JFileChooser chooser;

		public ListenerAbrir() {
			this.chooser = new JFileChooser();
		}

		public void actionPerformed(ActionEvent evt) {
			if (this.chooser.showOpenDialog(TailJanela.this) == 0) {
				if (this.chooser.getSelectedFile() != null) {
					synchronized (TailJanela.this) {
						if (TailJanela.this.tail != null) {
							TailJanela.this.tail.setContinuar(false);
						}
						TailJanela.this.tail = new ThreadTail(
								this.chooser.getSelectedFile(),
								new LogListener() {
									public void log(String msg) {
										TailJanela.this.txtLog.append(msg);
										TailJanela.this.txtLog
												.setCaretPosition(TailJanela.this.txtLog
														.getText().length() - 1 >= 0 ? TailJanela.this.txtLog
														.getText().length() - 1
														: 0);
									}

									public void inicioLog(Object obj) {
										TailJanela.this.txtLog.setText("");
									}
								});
						TailJanela.this.setTitle(this.chooser.getSelectedFile()
								.getAbsolutePath());
						if (TailJanela.this.listaArquivos.indexOf(this.chooser
								.getSelectedFile()) == -1) {
							TailJanela.this.listaArquivos.add(this.chooser
									.getSelectedFile());
						} else
							System.out.println("Arquivo existente");
						TailJanela.this.atualizaListaUltimosArquivos();
						new Thread(TailJanela.this.tail).start();
					}
				}
			}
		}
	}
}
