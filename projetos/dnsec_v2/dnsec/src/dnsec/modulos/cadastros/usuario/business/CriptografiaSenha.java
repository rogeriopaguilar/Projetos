package dnsec.modulos.cadastros.usuario.business;

public interface CriptografiaSenha {
		public String criptografar(String codUsuario, String senha);
		public String descriptografar(String codUsuario, String senha);
		public boolean suportaDescriptografia();
}
