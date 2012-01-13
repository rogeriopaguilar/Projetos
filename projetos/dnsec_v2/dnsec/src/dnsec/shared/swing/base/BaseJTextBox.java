package dnsec.shared.swing.base;

import javax.swing.JTextField;
import javax.swing.text.Document;



public class BaseJTextBox extends JTextField{

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

	
	public BaseJTextBox() {
		super();

		// TODO Auto-generated constructor stub
	}

	public BaseJTextBox(Document doc, String text, int columns) {
		super(doc, text, columns);
		// TODO Auto-generated constructor stub
	}

	public BaseJTextBox(int columns) {
		super(columns);
		// TODO Auto-generated constructor stub
	}

	public BaseJTextBox(String text, int columns) {
		super(text, columns);
		// TODO Auto-generated constructor stub
	}

	 /**
	  * Retorna um objeto document que limita o tamanho do texto
	  * e formata todas os caracteres para letra maiúscula.
	  * */
	 protected Document createDefaultModel() {
         return new UpperCaseMaxLengthDocument(this);
     }
 
	 
}
