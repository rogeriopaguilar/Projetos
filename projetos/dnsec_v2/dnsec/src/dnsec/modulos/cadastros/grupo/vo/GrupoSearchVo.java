package dnsec.modulos.cadastros.grupo.vo;

import dnsec.shared.database.hibernate.Grupo;
import dnsec.shared.vo.BaseSearchVo;

public class GrupoSearchVo extends BaseSearchVo{

	private Grupo grupo = new Grupo();

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public GrupoSearchVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GrupoSearchVo(Grupo grupo) {
		super();
		// TODO Auto-generated constructor stub
		this.grupo = grupo;
	}
	
	
	
}
