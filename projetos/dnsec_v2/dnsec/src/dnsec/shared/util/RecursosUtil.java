package dnsec.shared.util;

import java.io.File;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 															<br>
 * Classe utilitária para o gerenciamento dos recursos		<br>
 * da aplicação (Busca msgs no arquivo de recursos da 		<br>
 * aplicação e pode formatar a mensagem contida no arquivo)	<br>
 * 
 * @author Rogério de Paula Aguilar
 * @version 0.1
 * */

//TODO --> Adaptar para carregara rquivos de recursos de diferentes línguas
public class RecursosUtil {
	
	/**Objeto utilizado para carregar as informações do arquivo de recursos*/
	private static ResourceBundle resourceBundle;
	/**Objeto representando o arquivo de informações do arquivo*/
	private File arquivoRecursos;
	/** Intância da classe que será utilizada durante a execução do programa*/
	private static RecursosUtil iUtil = new RecursosUtil();
	/**Mapa para recursos de diferentes locales*/
	private static Map mapaRecursos = Collections.synchronizedMap(new HashMap());

	
	/** Retorna uma referência ao objeto que representa os dados do recurso da aplciação */
	private File getArquivoRecursos(){
		return this.arquivoRecursos;
	}

	/** Modifica o objeto de referência ao arquivo de recursos */
	private void setArquivoRecursos(File arquivoRecursos){
		this.arquivoRecursos = arquivoRecursos;
	}

	/**Construtor*/
	private RecursosUtil(){
		this.arquivoRecursos = new File(Constantes.ARQUIVO_RECURSOS);
		System.out.println(Constantes.ARQUIVO_RECURSOS + ".properties");
		System.out.println(getClass().getClassLoader().getResource(Constantes.ARQUIVO_RECURSOS + ".properties") == null);
		resourceBundle = ResourceBundle.getBundle(Constantes.ARQUIVO_RECURSOS, Locale.getDefault(), getClass().getClassLoader());
	}


	
	/**Retorna a instância que será utilizada durante a execução do programa*/
	public static RecursosUtil getInstance(){
		/* Verifica se o arquivo de recursos foi modificado
		 * Se o arquivo de recursos foi modificado durante a execução do programa, 
		 * carrega novamente as entradas do arquivo
		 * */
		synchronized(iUtil){
			File arquivoRecursosTMP = new File(Constantes.ARQUIVO_RECURSOS);
			if(arquivoRecursosTMP.lastModified() != iUtil.getArquivoRecursos().lastModified()){
				resourceBundle = ResourceBundle.getBundle(Constantes.ARQUIVO_RECURSOS, Locale.getDefault(), RecursosUtil.class.getClassLoader());
				iUtil.setArquivoRecursos(arquivoRecursosTMP);
			}
		}
		return iUtil;
	}

	/**Retorna a instância que será utilizada durante a execução do programa*/
	//TODO --> Adaptar para recarregar o arquivo automaticamente como é feito para o recurso default
	public static RecursosUtil getInstance(Locale locale){
		synchronized(mapaRecursos){
			RecursosUtil iUtil = null;
			if(mapaRecursos.get(locale) == null)
			{
				iUtil = new RecursosUtil();
				ResourceBundle resourceBundle = ResourceBundle.getBundle(Constantes.ARQUIVO_RECURSOS, locale, RecursosUtil.class.getClassLoader());
				File arquivoRecursosTMP = new File(Constantes.ARQUIVO_RECURSOS);
				iUtil.setArquivoRecursos(arquivoRecursosTMP);
				mapaRecursos.put(locale, iUtil);
			}else
			{
				/*File arquivoRecursosTMP = new File(Constantes.ARQUIVO_RECURSOS);
				if(arquivoRecursosTMP.lastModified() != iUtil.getArquivoRecursos().lastModified()){
					resourceBundle = ResourceBundle.getBundle(Constantes.ARQUIVO_RECURSOS, locale, RecursosUtil.class.getClassLoader());
					iUtil.setArquivoRecursos(arquivoRecursosTMP);
				}*/
				iUtil = (RecursosUtil)mapaRecursos.get(locale);				
			}
			return iUtil;
		}
		
	}

	
	/**
	 * Retorna uma mensagem do arquivo de recursos																<br>
	 * @param resource - chave para a mensagem no arquivo														<br>
	 * @return mensagem do arquivo de recursos ou "???" + resource + "???" caso a mensagem não seja encontrada	<br>
	 * Para utilizar formatação utilize o método getResource(String, args)										<br>
	 * @see getResource(String,Object[])																		<br>
	 * */
	public String getResource(String resource){
		String strResource = null;
		try{
			strResource = this.resourceBundle.getString(resource);
		}catch(MissingResourceException e){
			//String não encontrada no arquivo de recursos
			strResource = "???" + resource + "???";
		}
		return strResource;
	}

	/**
	 * Retorna uma mensagem do arquivo de recursos substituindo os parâmetros passados							<br>
	 * @param resource - chave para a mensagem no arquivo														<br>	
	 * @param args - argumentos que serão utilizados para substituição na mensagem								<br>		
	 * @return mensagem do arquivo de recursos formatada, "???" + resource + "???" caso a mensagem 				<br>
	 * não seja encontrada ou strResource + "!!!Invalida pattern or arguments!!!" caso ocorra algum problema 	<br>
	 * durante a formatação (substituição dos coringas na mensagem pelos valores passados em args)				<br>
	 * */
	public String getResource(String resource, Object args[]){
		String strResource = getResource(resource);
		try{
			strResource = MessageFormat.format(strResource, args);
		}catch(IllegalArgumentException e){
			strResource = strResource + " !!!Invalid pattern or arguments!!!"; 
		}
		return strResource;
	}

	
	
}
