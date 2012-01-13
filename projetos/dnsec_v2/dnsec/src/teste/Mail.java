package teste;

import java.util.Random;

/*
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
*/
public class Mail {

	
	public static void main(String[] args)
	{
		/*
		String str = System.getProperty("line.separator") + "Bom dia. Conversei pelo atendimento através do msn e ficou";
		str+=System.getProperty("line.separator") + "combinado o cancelamento dos meus pedidos :";
		str+=System.getProperty("line.separator") + "25102856 no valor de R$ 1.729,79";
		str+=System.getProperty("line.separator") + "25102871 no valor de R$ 159,32";
		str+=System.getProperty("line.separator") + "Os dados da conta para reembolso são:";
		str+=System.getProperty("line.separator") + "Banco: Bradesco";
		str+=System.getProperty("line.separator") + "Ag: 0593-2";
		str+=System.getProperty("line.separator") + "CC: 0172387-1";
		str+=System.getProperty("line.separator") + "Favorecido: Rogério de Paula Aguilar";
		str+=System.getProperty("line.separator") + "Por favor me confirmem a data do reembolso pois preciso urgente";
		str+=System.getProperty("line.separator") + "adquirir o produto em outro lugar já que era um presente que";
		str+=System.getProperty("line.separator") + "deveria ter sido entregue no sábado pessado.";
		str+=System.getProperty("line.separator") + "Já faz um mês que fiz o pedido e vcs sempre contam uma história diferente, não respondem meus e-mails";
		str+=System.getProperty("line.separator") + "e não me dão nenhuma satisfação. Estou mandando em lote pra ver se vcs respondem agora.";
		str+=System.getProperty("line.separator") + "Att.";
		str+=System.getProperty("line.separator") + "Rogério";

		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.sao.terra.com.br");
		try {
			email.addTo("contato@portalmicro.com.br", "Portal Micro");
			email.setSubject("Aos cuidados do financeiro");
			email.setAuthentication("duquepop","102030");
			email.setMsg(str);
			while(true)
			{
				try{	
					String fr = "" + new Random().nextInt(Integer.MAX_VALUE);
					email.setFrom(fr + "@terra.com.br", "Rogério");

					email.send();
					System.out.println("email enviado ");
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			//System.out.println("OK");

		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
		
	
}
