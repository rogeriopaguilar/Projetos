package dnsec.modulos.cadastros.sistema.vo;

import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.vo.BaseSearchVo;

public class SistemaTelaVo extends BaseSearchVo{

	
	private Sistema sistema = new Sistema();

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public SistemaTelaVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SistemaTelaVo(Sistema sistema) {
		super();
		// TODO Auto-generated constructor stub
		this.sistema = sistema;
	}
	
	
}
