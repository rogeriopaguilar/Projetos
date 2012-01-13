package teste;

import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TesteURL {
	
	
	public static String getParameter(String URL, String nomeParametro){
		if(URL == null || nomeParametro == null)
		{
			return null;
		}
		if(URL.indexOf("?" + nomeParametro) == -1 && URL.indexOf("&" + nomeParametro) == -1)
		{
			return null;
		}
		int indiceInicial = -1;
		if(URL.indexOf("?" + nomeParametro + "=") == -1 && URL.indexOf("&" + nomeParametro + "=") == -1)
		{
			/*indiceInicial = URL.indexOf("?" + nomeParametro) + nomeParametro.length() + 2;
			if(indiceInicial == -1)
			{
				indiceInicial = URL.indexOf("&" + nomeParametro) + nomeParametro.length() + 2;
			}*/
			return null;
		}else
		{
			indiceInicial = URL.indexOf("?" + nomeParametro + "=");
			if(indiceInicial == -1)
			{
				indiceInicial = URL.indexOf("&" + nomeParametro + "=");
			}
			indiceInicial = indiceInicial + (nomeParametro + "").length() + 2;
		}

		int indiceFinal = URL.substring(indiceInicial, URL.length()).indexOf('&') ;
		if(indiceFinal == -1)
		{
			indiceFinal = URL.length();
		}else{
			indiceFinal += indiceInicial;
		}
		return URL.substring(indiceInicial, indiceFinal);
	}

	static URL url;
	static int contThreads = 0;
	static Object obj = new Object();
	private long contThreadsTotal;
	
	

	public static void main(String[] args) throws MalformedURLException
	{
		
		
		url = new URL("http://www.portalmicro.com.br");		
		/*System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "212.76.224.165");
		System.getProperties().put("proxyPort", "8080");*/

		
		while(true)
		{

			
			synchronized(obj){
				contThreads++;
				System.out.println("contThreads " + contThreads);
				while(contThreads > 1000)
				{
					try 
					{
						System.out.println("Thread " + contThreads + " esperando ");
						obj.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("Thread " + contThreads + " LIBERADA! ");
			}
			
			new Thread()
			{
			public void run(){
				try{
				URLConnection conexao = url.openConnection();
				conexao.setDoInput(true);
				conexao.setDoOutput(true);
				/*try{
					Thread.sleep(60 * 1000 * 10);
				}catch(InterruptedException e){}*/
				byte[] buffer = new byte[5000];
				
				InputStream is = conexao.getInputStream();
				int i = -1;
				/*do{
					i= is.read(buffer);
				}while(i != -1);*/
				}catch(Exception e){
					e.printStackTrace();
				}
				synchronized(obj){
					contThreads--;
					obj.notifyAll();
				}
			}
			}.start();
			
			
		}
		
		
	}
	

}
