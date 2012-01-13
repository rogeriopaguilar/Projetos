package dnsec.shared.listener;

import dnsec.shared.database.hibernate.Usuario;

public interface PesquisaUsuarioListener {
		public void usuarioSelecionado(Usuario usuario);
		public boolean fecharJanelaPesquisaSistema();
}
