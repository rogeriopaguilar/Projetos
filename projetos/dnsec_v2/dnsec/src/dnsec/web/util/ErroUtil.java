package dnsec.web.util;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.util.RecursosUtil;
import dnsec.shared.util.StringUtil;

public class ErroUtil {

	public static String METODO_ERRO = "ERRO_NAO_ESPERADO";
	
	public static boolean tratarErro(CommandException commandException)
	{
		String msgErroEspecifico = "";
		boolean erroEsperado = true;
		if (commandException.getCause() != null
				&& !(commandException.getCause() instanceof CommandException)) {
			// Erro não esperado na aplicação
			Throwable exception = commandException.getCause();
			exception.printStackTrace();
			CharArrayWriter arrayMsg = new CharArrayWriter();
			PrintWriter printWriter = new PrintWriter(arrayMsg);
			exception.printStackTrace(printWriter);
			msgErroEspecifico = arrayMsg.toString();
			erroEsperado = false;
		} else {
			// Erro esperado na aplicação
			String strKeyMsg = StringUtil.NullToStr(commandException
					.getStrKeyConfigFile());
			Object[] argsMsg = commandException.getObjArgs();
			if (argsMsg == null) {
				argsMsg = new Object[0];
			}
			msgErroEspecifico = "";
			if (!"".equals(strKeyMsg)) {
				msgErroEspecifico = RecursosUtil.getInstance().getResource(
						strKeyMsg, argsMsg);
			}
		}
		 FacesContext context = FacesContext.getCurrentInstance();
         context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msgErroEspecifico , msgErroEspecifico));
         return erroEsperado;
	}
	

	
}
