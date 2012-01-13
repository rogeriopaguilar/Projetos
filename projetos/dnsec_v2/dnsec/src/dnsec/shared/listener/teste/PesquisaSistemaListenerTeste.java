package dnsec.shared.listener.teste;

import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.listener.PesquisaSistemaListener;

public class PesquisaSistemaListenerTeste implements PesquisaSistemaListener{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

	public void sistemaSelecionado(Sistema sistema) {
		System.out.println("Sistema selecionado -->" + sistema);
	}

	public boolean fecharJanelaPesquisaSistema() {
		return true;
	}

}
