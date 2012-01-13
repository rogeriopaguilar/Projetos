package dnsec.shared.swing.base;

import java.text.Format;

import javax.swing.JFormattedTextField;
import javax.swing.text.Document;

public class BaseJFormattedTextField extends JFormattedTextField{

	protected boolean exibirMaiuscula = true;
	protected int tamanhoMaximo = -1; 

	/*public static Document upperCaseJFormattedTextField = new PlainDocument(){
		public void insertString(int offs, String str, AttributeSet a) 
         throws BadLocationException {
         
			 if (str == null) {
	             return;
	         }
			 
	         //Exibe o texto com letras maiúsculas
	         char[] upper = str.toCharArray();
	         for (int i = 0; i < upper.length; i++) {
	             upper[i] = Character.toUpperCase(upper[i]);
	         }
		}
	};*/
	
	
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

	public BaseJFormattedTextField() {
		super();
		//setDocument(upperCaseJFormattedTextField);
	}

	public BaseJFormattedTextField(AbstractFormatter formatter) {
		super(formatter);
	}

	public BaseJFormattedTextField(AbstractFormatterFactory factory, Object currentValue) {
		super(factory, currentValue);
	}

	public BaseJFormattedTextField(AbstractFormatterFactory factory) {
		super(factory);
	}

	public BaseJFormattedTextField(Format format) {
		super(format);
	}

	public BaseJFormattedTextField(Object value) {
		super(value);
	}

	
	 /**
	  * Retorna um objeto document que limita o tamanho do texto
	  * e formata todas os caracteres para letra maiúscula.
	  * */
	 protected Document createDefaultModel() {
        return new UpperCaseMaxLengthDocumentFormattedTextField(this);
    }
	
}
