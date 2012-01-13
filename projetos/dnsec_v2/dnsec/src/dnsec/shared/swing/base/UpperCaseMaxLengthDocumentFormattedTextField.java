package dnsec.shared.swing.base;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;



public class UpperCaseMaxLengthDocumentFormattedTextField extends PlainDocument {
    	 
    	 BaseJFormattedTextField baseJTextBox = null;
    	 
    	 public UpperCaseMaxLengthDocumentFormattedTextField(BaseJFormattedTextField  baseJTextBox){
    		 this.baseJTextBox = baseJTextBox;
    	 }	

    	 
    	 public void insertString(int offs, String str, AttributeSet a) 
             throws BadLocationException {
 
             int tamanhoMaximo = baseJTextBox.getTamanhoMaximo();
             
    		 if (str == null) {
                 return;
             }
             //Exibe o texto com letras maiúsculas
             char[] upper = str.toCharArray();
             if(this.baseJTextBox.exibirMaiuscula){
	             for (int i = 0; i < upper.length; i++) {
	                 upper[i] = Character.toUpperCase(upper[i]);
	             }
             }

             //Corta a string para o tamanho máximo.
             if(tamanhoMaximo > 0){
            	if(getLength() + upper.length > tamanhoMaximo){
            		if(getLength() > tamanhoMaximo){
            			super.insertString(0,
            								super.getText(0, getLength()).substring(0, getLength() - tamanhoMaximo),
            								a);
            		}else{
                        //super.insertString(offs, new String(upper).substring(0, tamanhoMaximo - ( getLength() + upper.length )), a);
            		}
            	}else{
                    super.insertString(offs, new String(upper), a);
            	} 
             }else{
                 super.insertString(offs, new String(upper), a);
             }
             
         }
     }
