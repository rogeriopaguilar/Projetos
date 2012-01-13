package dnsec.shared.listener.teste;

import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.listener.PesquisaFuncaoListener;

public class PesquisaFuncaoListenerTeste implements PesquisaFuncaoListener{

	public void funcaoSelecionada(Funcao funcao) {
		System.out.println("Função selecionada --> " + funcao);
	}

	public boolean fecharJanelaPesquisaFuncao() {
		return true;
	}

}
