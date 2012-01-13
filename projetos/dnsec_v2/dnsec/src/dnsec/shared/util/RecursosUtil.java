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
 * Classe utilit�ria para o gerenciamento dos recursos		<br>
 * da aplica��o (Busca msgs no arquivo de recursos da 		<br>
 * aplica��o e pode formatar a mensagem contida no arquivo)	<br>
 * 
 * @author Rog�rio de Paula Aguilar
 * @version 0.1
 * */

//TODO --> Adaptar para carregara rquivos de recursos de diferentes l�nguas
public class RecursosUtil {
	
	/**Objeto utilizado para carregar as informa��es do arquivo de recursos*/
	private static ResourceBundle resourceBundle;
	/**Objeto representando o arquivo de informa��es do arquivo*/
	private File arquivoRecursos;
	/** Int�ncia da classe que ser� utilizada durante a execu��o do programa*/
	private static RecursosUtil iUtil = new RecursosUtil();
	/**Mapa para recursos de diferentes locales*/
	private static Map mapaRecursos = Collections.synchronizedMap(new HashMap());

	
	/** Retorna uma refer�ncia ao objeto que representa os dados do recurso da aplcia��o */
	private File getArquivoRecursos(){
		return this.arquivoRecursos;
	}

	/** Modifica o objeto de refer�ncia ao arquivo de recursos */
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


	
	/**Retorna a inst�ncia que ser� utilizada durante a execu��o do programa*/
	public static RecursosUtil getInstance(){
		/* Verifica se o arquivo de recursos foi modificado
		 * Se o arquivo de recursos foi modificado durante a execu��o do programa, 
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

	/**Retorna a inst�ncia que ser� utilizada durante a execu��o do programa*/
	//TODO --> Adaptar para recarregar o arquivo automaticamente como � feito para o recurso default
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
	 * @return mensagem do arquivo de recursos ou "???" + resource + "???" caso a mensagem n�o seja encontrada	<br>
	 * Para utilizar formata��o utilize o m�todo getResource(String, args)										<br>
	 * @see getResource(String,Object[])																		<br>
	 * */
	public String getResource(String resource){
		String strResource = null;
		try{
			strResource = this.resourceBundle.getString(resource);
		}catch(MissingResourceException e){
			//String n�o encontrada no arquivo de recursos
			strResource = "???" + resource + "???";
		}
		return strResource;
	}

	/**
	 * Retorna uma mensagem do arquivo de recursos substituindo os par�metros passados							<br>
	 * @param resource - chave para a mensagem no arquivo														<br>	
	 * @param args - argumentos que ser�o utilizados para substitui��o na mensagem								<br>		
	 * @return mensagem do arquivo de recursos formatada, "???" + resource + "???" caso a mensagem 				<br>
	 * n�o seja encontrada ou strResource + "!!!Invalida pattern or arguments!!!" caso ocorra algum problema 	<br>
	 * durante a formata��o (substitui��o dos coringas na mensagem pelos valores passados em args)				<br>
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
