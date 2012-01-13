package dnsec.modulos.cadastros.funcao.vo;

import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.vo.BaseSearchVo;

public class FuncaoSearchVo extends BaseSearchVo{

		private Funcao funcao = new Funcao();

		public Funcao getFuncao() {
			return funcao;
		}

		public void setFuncao(Funcao funcao) {
			this.funcao = funcao;
		}

		public FuncaoSearchVo() {
			super();
		}

		public FuncaoSearchVo(Funcao funcao) {
			super();
			this.funcao = funcao;
		}
		
}
