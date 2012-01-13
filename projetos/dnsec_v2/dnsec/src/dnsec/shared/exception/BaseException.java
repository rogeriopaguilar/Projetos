package dnsec.shared.exception;

/**
 * Guarda a chave e os parâmetros para a mensagem de erro que será registrada no arquivo de <br>
 * recursos da aplicação, caso estas informações existam. 									<br>
 * O campo strKeyConfigFile deve conter a chave da mensagem do arquivo e o campo			<br>
 * objArgs[] deve conter os dados que serão utilizados para substituir os					<br>
 * valores na msg.																			<br>	
 * Exemplo:																					<br>
 * 																							<br>
 * --no arquivo:																			<br>	
 * 																							<br>
 * 		key.erro.teste=Mensagem de teste. P1 {0} P2 {1}										<br>		
 * 																							<br>
 * 		Um objeto para exibir esta msg poderia ser configurado da seguinte forma:			<br>
 * class TesteException{																	<br>
 * 																							<br>	
 * 		public void teste(){																<br>
 * 				BaseException exception = new BaseException();								<br>	
 * 				exception.setStrKeyConfigFile("key.erro.teste");							<br>
 * 				exception.setObjArgs(new Object[]{"TesteP1", "TesteP2"});					<br>
 * 		}																					<br>
 * }																						<br>
 * 																							<br>
 * Quando a msg acima for formatada pelo tratamento de erros do command, ela será exibida	<br>
 * da seguinte forma:																		<br>	
 * 																							<br>
 * Mensagem de teste. P1 TesteP1 P2 TesteP2													<br>
 * 																							<br>
 * 
 * @see #dnsec.shared.utilInternacUtil
 * */

public class BaseException extends Exception{

	private String strKeyConfigFile = "";
	private Object[] objArgs = new Object[0];
	
	public String toString(){
			StringBuffer str = new StringBuffer();
			str.append(System.getProperty("line.separator") + "-CommandException---------------------------------------- " + System.getProperty("line.separator"));
			str.append("--strKeyConfigFile: " + strKeyConfigFile + System.getProperty("line.separator"));
			if(objArgs != null && objArgs.length > 0){
				str.append("--args: " + System.getProperty("line.separator"));
				for(int i = 0; i < objArgs.length; i++){
					str.append("---args[" + i + "]: " + objArgs[i] + System.getProperty("line.separator"));
				}	
			}
			str.append("--------------------------------------------------------- " + System.getProperty("line.separator"));
			return str.toString();
	}
	
	public Object[] getObjArgs() {
		return objArgs;
	}

	public void setObjArgs(Object[] objArgs) {
		this.objArgs = objArgs;
	}

	public String getStrKeyConfigFile() {
		return strKeyConfigFile;
	}

	public void setStrKeyConfigFile(String strKeyConfigFile) {
		this.strKeyConfigFile = strKeyConfigFile;
	}

	public BaseException(){
		super();
		
	}

	public BaseException(String str){
		super(str);
	}

	public BaseException(Throwable throwable){
		super(throwable);
	}
	
	public BaseException(String str, Throwable throwable){
		super(str, throwable);
	}

}
