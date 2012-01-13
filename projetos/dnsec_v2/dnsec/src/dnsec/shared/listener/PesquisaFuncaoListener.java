package dnsec.shared.listener;

import dnsec.shared.database.hibernate.Funcao;

public interface PesquisaFuncaoListener {

	public void funcaoSelecionada(Funcao funcao);
	public boolean fecharJanelaPesquisaFuncao();

}
