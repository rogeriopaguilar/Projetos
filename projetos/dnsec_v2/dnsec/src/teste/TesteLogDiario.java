package teste;

import org.apache.log4j.Logger;

public class TesteLogDiario {

	static Logger logger = Logger.getLogger(TesteLogDiario.class);
	
	public static void main(String[] args)
	{
		org.apache.log4j.PropertyConfigurator.configure(
				"C:\\Documents and Settings\\rogerio.aguilar\\Desktop\\projetos-tokio\\e-sinistro\\ViewController\\public_html\\WEB-INF\\classes\\log4j.esinistro.properties"		
		);
		logger.debug("TESTE");
	}


}
