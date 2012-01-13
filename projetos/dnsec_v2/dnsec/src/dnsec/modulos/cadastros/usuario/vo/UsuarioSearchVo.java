package dnsec.modulos.cadastros.usuario.vo;

import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.vo.BaseSearchVo;

public class UsuarioSearchVo extends BaseSearchVo{

		private Usuario usuario = new Usuario();

		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}
		
		
	
}
