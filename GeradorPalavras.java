/**
 * Classe que gera, à partir de um conjunto de letras, as combinações possíveis das letras para palavras de um determinado tamanho. 		 <br/>
 * Por exemplo, suponha que as letras sejam "a" e "b" e queremos gerar todas as palavras de tamanho 3 formadas através da dombinação destas  <br/>
 * letras . Para este caso o programa deve ser chamado da seguinte forma: <p>
 * 
 * <code>
 * 	GeradorPalavras.gerarPalavras("ab", 3, null);
 * </code>
 * 
 * <p/>
 * O terceiro parâmetro do método indica o que deve ser feito com cada palavra gerada e é definido por uma implementação 					 <br/>
 * da interface GeradorPalavras.ProcessadorPalavra   . 																						 <br/> 
 * Esta interface possui um método chamado processarPalavra que recebe um objeto String e dois números. Para cada palavra gerada  			 <br/>
 * o método processarPalavra é chamado recebendo a palavra gerada como primeiro parâmetro, o contador indicando qual foi a palavra			 <br/>
 * gerada em relação ao total como segundo parâmetro e o total de palavras que serão geradas como terceiro parâmetro. Exemplo de             <br/>
 * implementação que imprime no console a possibilidade gerada: 																			  <p/>
 * 
 * <code>
 * 		import static GeradorPalavras.*;
 * 		...
 * 
 * 	public static void main(String[] args) {
 *
 *		final StringBuilder strBuilder = new StringBuilder();
 *
 *		String ascii = "ab";
 *		ProcessadorPalavra ex = new ProcessadorPalavra() {
 *			public void processarPalavra( String palavra, long contPalavraAtual, long totalPalavras ) {
 *				strBuilder.delete(0, strBuilder.length())
 *				.append(contPalavraAtual+1).append("/").append(totalPalavras)
 *				.append(" --> ").append(palavra);
 *				System.out.println(strBuilder.toString());
 *			}
 *		};
 *		gerarPalavras(ascii, 4, ex);
 *   }
 * </code>
 * 
 * @author Rogério de Paula Aguilar
 * 
 * */
public class GeradorPalavras {
	
	public static interface ProcessadorPalavra {
		public void processarPalavra(String palavra, long contPalavraAtual, long totalPalavras);
	}

	public static void gerarPalavras(String alfabeto, int tamanhoPalavra, ProcessadorPalavra ex) {
		if(alfabeto == null) throw new IllegalArgumentException("alfabeto deve ser <> null!");
		if(tamanhoPalavra <=0 ) throw new IllegalArgumentException("o tamanho da palavra deve ser > 0!");
		
		//Separa o alfabeto em caracteres
		char[] caracteres = alfabeto.toCharArray();
		
		//cria um array que representa o tamanho das entradas que devem ser geradas e 
		char[] caracteresIniciais = new char[tamanhoPalavra];
		for(int i = 0; i < tamanhoPalavra; i++) {
			caracteresIniciais[i] = caracteres[0];
		}
		
		//cria a primeira palavra com o primeiro símbolo do alfabeto e chama o "ProcessadorPalavra" caso exista para processar esta entrada
		String palavraInicial = new String( caracteresIniciais );

		long totalPalavras = (long)Math.pow(alfabeto.length() , tamanhoPalavra);
		long contPalavraAtual = 0;
		
		//chama a implementação passando como parâmetro a primeira palavra gerada
		if(ex != null)
			ex.processarPalavra( palavraInicial, contPalavraAtual, totalPalavras );
		
		//recupera os caracteres da palavra inicial
		char[] caracteresPalavraAtual = palavraInicial.toCharArray();
		
		int j = palavraInicial.length() - 1;
		do {
			boolean erro = false;
			do {
				try{
					//recupera o próximo caracter para a posição atual.
					caracteresPalavraAtual[j] = proximoCaracter( caracteresPalavraAtual[j], caracteres );
					//chama a implementação contendo a nova palavra gerada
					if(ex != null) {
						contPalavraAtual++;
						ex.processarPalavra( new String(caracteresPalavraAtual), contPalavraAtual, totalPalavras );
					}
				}catch(ArrayIndexOutOfBoundsException e){
					//O caracter atual já contém o último símbolo do alfabeto. Tento gerar o próximo símbolo para o caracter à esquerda
					try {
						char cTMP = proximoCaracter( caracteresPalavraAtual[j-1], caracteres );
						caracteresPalavraAtual[j-1] = cTMP;
						/* consegui gerar o símbolo para o caracter à esquerda, então para todos os caracteres à direita deste eu seto novamente o primeiro símbolo do alfabeto
						   e reseto o contador para que o processo retorne ao caracter mais à direita */
						for(int a = j; a < palavraInicial.length(); a++) {
							caracteresPalavraAtual[a] = caracteres[0];
							j = a;
						}
						//chamo a implementação passando a nova palavra gerada
						if(ex != null) {
							contPalavraAtual++;
							ex.processarPalavra( new String(caracteresPalavraAtual), contPalavraAtual, totalPalavras );
						}
					} catch (ArrayIndexOutOfBoundsException e2) {
						//não consegui gerar o caracter à esquerda, então os dois caracteres possuem o último símbolo do alfabeto
						erro = true;
					}
					
				}
			} while(!erro);	
			//volto para o caracter anterior e recomeço o processo até chagar ao primeiro caracter à esqueda
			j--;
		} while( j >=0 );
	}

	private static char proximoCaracter(char c, char[] caracteres) {
		char chrRetorno = 'a';
		
		for(int i = 0; i < caracteres.length; i++) {
			if( caracteres[i] == c) {
				chrRetorno = caracteres[i+1];
			}
		}
		
		return  chrRetorno;
	}
	

	public static void main(String[] args) {
		
		String ascii = 
			"0123456789:;<=>?" + 
			"@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_"+
			"`abcdefghijklmnopqrstuvwxyz{|}" +
			"ÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞ" +
			"ßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
		

		final StringBuilder strBuilder = new StringBuilder();

		//String ascii = "abcd";
		ProcessadorPalavra ex = new ProcessadorPalavra() {
			public void processarPalavra( String palavra, long contPalavraAtual, long totalPalavras ) {
				strBuilder.delete(0, strBuilder.length())
				.append(contPalavraAtual+1).append("/").append(totalPalavras)
				.append(" --> ").append(palavra);
				System.out.println(strBuilder.toString());
			}
		};
		gerarPalavras(ascii, 2, ex);
			
	}	
	
	
}


