package teste;

import java.util.ArrayList;
import java.util.Arrays;

public class  HandlerFactory {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getHandler(new String[]{ "/test2", "TestServlet2", "/test", "TestServlet"   },  "/test2/"));
	}

	 public static String getHandler(String[] config, String requestUri)
	  {
	    String padroes[] = new String[config.length/2]; 
	    java.util.Map mapa = new java.util.HashMap();
	    int cont = 0;
	    boolean tempadraogeral = false;
	    int indicePadraoGeral = -1;
	    for(int i = 0; i < config.length; i++)
	    {
	           if("/".equals(config[i]))
	           {
	        	   tempadraogeral = true;
	        	   indicePadraoGeral = i;
	           }
			 if(i==0 || (i > 1 && i % 2 == 0)) {
				 padroes[cont] = config[i]; 
				 mapa.put(padroes[cont], config[i+1]);
				 cont++;
			 }
	    }
		Arrays.sort(padroes);
		for(int i = padroes.length-1; i > 0; i--)
		{
	           if(requestUri.indexOf(padroes[i]) != -1) return (String)mapa.get(padroes[i]); 
		}
		if(requestUri.startsWith("/") && tempadraogeral) return config[indicePadraoGeral+1];
	    return "5jptN3";
	  }
}
