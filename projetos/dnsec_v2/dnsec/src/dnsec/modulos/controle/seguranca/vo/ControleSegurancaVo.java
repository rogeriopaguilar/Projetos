package dnsec.modulos.controle.seguranca.vo;

import java.util.Collections;
import java.util.Map;

import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.vo.BaseVo;

public class ControleSegurancaVo extends BaseVo{

		private Usuario usuarioConectado;
		private Map mapaGruposUsuario;
		private Map mapaSistemasUsuario;
		private Map mapaFuncoesUsuario;
		
		
		
		public ControleSegurancaVo(Usuario usuarioConectado, Map mapaGruposUsuario, Map mapaSistemasUsuario, Map mapaFuncoesUsuario) {
			super();
			// TODO Auto-generated constructor stub
			this.usuarioConectado = usuarioConectado;
			/*
			this.mapaGruposUsuario = Collections.unmodifiableMap(mapaGruposUsuario);
			this.mapaSistemasUsuario = Collections.unmodifiableMap(mapaSistemasUsuario);
			this.mapaFuncoesUsuario = Collections.unmodifiableMap(mapaFuncoesUsuario);*/
			this.mapaGruposUsuario = mapaGruposUsuario;
			this.mapaSistemasUsuario = mapaSistemasUsuario;
			this.mapaFuncoesUsuario = mapaFuncoesUsuario;
			
		}
		
		public Map getMapaGruposUsuario() {
			return mapaGruposUsuario;
		}
		public Map getMapaSistemasUsuario() {
			return mapaSistemasUsuario;
		}
		public Usuario getUsuarioConectado() {
			return usuarioConectado;
		}
		public Map getMapaFuncoesUsuario() {
			return mapaFuncoesUsuario;
		}
		
	
}
