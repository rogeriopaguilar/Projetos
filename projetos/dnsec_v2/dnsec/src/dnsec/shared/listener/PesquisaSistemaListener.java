package dnsec.shared.listener;

import dnsec.shared.database.hibernate.Sistema;

public interface PesquisaSistemaListener {
		public void sistemaSelecionado(Sistema sistema);
		public boolean fecharJanelaPesquisaSistema();
}
