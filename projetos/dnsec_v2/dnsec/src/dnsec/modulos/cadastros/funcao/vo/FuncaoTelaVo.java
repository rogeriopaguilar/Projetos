package dnsec.modulos.cadastros.funcao.vo;

import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.vo.BaseVo;

public class FuncaoTelaVo extends BaseVo {
	private Funcao funcao = new Funcao();
	
	public Funcao getFuncao() {
		return funcao;
	}
	
	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
	
	
}
