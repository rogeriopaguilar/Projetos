package dnsec.shared.swing.base;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class BaseJButton extends JButton{

		private String strCodFuncaoSeguranca;
		
		public String getStrCodFuncaoSeguranca() {
			return strCodFuncaoSeguranca;
		}


		public void setStrCodFuncaoSeguranca(String strCodFuncaoSeguranca) {
			this.strCodFuncaoSeguranca = strCodFuncaoSeguranca;
		}


		/*public boolean isEnabled()
		{
			if(strCodFuncaoSeguranca != null)
			{
				if(
					GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(strCodFuncaoSeguranca) != null
					//Constantes.COD_USR_ADM.equals(GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua())
					//||
				){
					if(!super.isEnabled()){
						super.setEnabled(true);
					}
				}else{
					if(super.isEnabled()){
						super.setEnabled(false);
					}
				}
			}
			return super.isEnabled();
		}*/
		
		
		/*public void setEnabled(boolean b){
			if(strCodFuncaoSeguranca != null)
			{
				if(
					GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(strCodFuncaoSeguranca) == null
					//Constantes.COD_USR_ADM.equals(GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua())
					//||
				)
				{
					super.setEnabled(false);
					return;
				}
			}
			super.setEnabled(b);
		}*/
		
	
		public BaseJButton(){
			super();
		}

		public BaseJButton(Action action){
			super(action);
		}

		public BaseJButton(Icon icon){
			super(icon);
		}

		public BaseJButton(String text){
			super(text);
		}

		public BaseJButton(String text, Icon icon){
			super(text, icon);
		}

}
