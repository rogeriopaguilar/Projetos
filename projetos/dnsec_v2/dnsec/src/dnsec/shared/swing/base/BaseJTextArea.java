package dnsec.shared.swing.base;

import javax.swing.JTextArea;
import javax.swing.text.Document;


public class BaseJTextArea extends JTextArea{

	protected boolean exibirMaiuscula = true;
	protected int tamanhoMaximo = -1; 
	
	public boolean isExibirMaiuscula() {
		return exibirMaiuscula;
	}

	public void setExibirMaiuscula(boolean exibirMaiuscula) {
		this.exibirMaiuscula = exibirMaiuscula;
	}

	public int getTamanhoMaximo() {
		return tamanhoMaximo;
	}

	public void setTamanhoMaximo(int tamanhoMaximo) {
		this.tamanhoMaximo = tamanhoMaximo;
	}

	public BaseJTextArea() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BaseJTextArea(Document doc, String text, int rows, int columns) {
		super(doc, text, rows, columns);
		// TODO Auto-generated constructor stub
	}

	public BaseJTextArea(Document doc) {
		super(doc);
		// TODO Auto-generated constructor stub
	}

	public BaseJTextArea(int rows, int columns) {
		super(rows, columns);
		// TODO Auto-generated constructor stub
	}

	public BaseJTextArea(String text, int rows, int columns) {
		super(text, rows, columns);
		// TODO Auto-generated constructor stub
	}

	public BaseJTextArea(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}


	 protected Document createDefaultModel() {
        return new UpperCaseMaxLengthDocumentTextArea(this);
    }

	
}
