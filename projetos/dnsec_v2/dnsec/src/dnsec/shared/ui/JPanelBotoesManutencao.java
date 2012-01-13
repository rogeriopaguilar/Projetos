package dnsec.shared.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.swing.base.BaseJButton;
import dnsec.shared.swing.base.BaseJPanel;
import dnsec.shared.util.RecursosUtil;

public class JPanelBotoesManutencao extends BaseJPanel{

		protected BaseJButton cmdIncluir;
		protected BaseJButton cmdExcluir;
		protected BaseJButton cmdAlterar;
		protected BaseJButton cmdGravar;
		protected BaseJButton cmdPrimeiro;
		protected BaseJButton cmdAnterior;
		protected BaseJButton cmdProximo;
		protected BaseJButton cmdUltimo;
		protected BaseJButton cmdPesquisa;
		
		
		/*protected boolean exibirBotaoIncluir = true;
		protected boolean exibirBotaoExclur = true;
		protected boolean exibirBotaoAlterar = true;
		protected boolean exibirBotaoGravar = true;
		protected boolean exibirBotaoPrimeiro = true;
		protected boolean exibirBotaoAnterior = true;
		protected boolean exibirBotaoProximo = true;
		protected boolean exibirBotaoUltimo = true;
		protected boolean exibirBotaoPesquisa = true;*/
	
		
		protected ActionListener actionListener;
		
		public void inicializarComponentes(){
			//Inicializando botões
			cmdIncluir =  new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.incluir"), GerenciadorJanelas.ICONE_BOTAO_INCLUIR);
			cmdIncluir.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.incluir.tooltiptext"));

			cmdExcluir =  new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.excluir"), GerenciadorJanelas.ICONE_BOTAO_EXCLUIR);
			cmdExcluir.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.excluir.tooltiptext"));
			
			cmdAlterar =  new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.alterar"), GerenciadorJanelas.ICONE_BOTAO_ALTERAR);
			cmdAlterar.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.alterar.tooltiptext"));
			
			cmdGravar =  new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.gravar"), GerenciadorJanelas.ICONE_BOTAO_CONFIRMAR);
			cmdGravar.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.gravar.tooltiptext"));
			
			cmdPrimeiro =  new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.primeiro"), GerenciadorJanelas.ICONE_BOTAO_PRIMEIRO);
			cmdPrimeiro.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.primeiro.tooltiptext"));

			cmdAnterior =  new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.anterior"),GerenciadorJanelas.ICONE_BOTAO_ANTERIOR);
			cmdAnterior.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.anterior.tooltiptext"));

			cmdProximo =  new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.proximo"), GerenciadorJanelas.ICONE_BOTAO_PROXIMO);
			cmdProximo.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.proximo.tooltiptext"));

			cmdUltimo =  new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.ultimo"), GerenciadorJanelas.ICONE_BOTAO_ULTIMO);
			cmdUltimo.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.ultimo.tooltiptext"));

			cmdPesquisa =  new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.pesquisar"), GerenciadorJanelas.ICONE_BOTAO_PESQUISA);
			cmdPesquisa.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.pesquisar.tooltiptext"));

			//Adicionando botões
			GridBagLayout gridBagLayout = new GridBagLayout();
			GridBagConstraints constraints = new GridBagConstraints();
			this.setLayout(gridBagLayout);
			
			int linhaAtual = 0;

			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 2;
			this.add(cmdPesquisa, constraints);
			linhaAtual++;

			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			//constraints.insets = new Insets(10,10,10,10);
			//constraints.insets = new Insets(10,10,10,10);
			this.add(cmdIncluir, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.BOTH;
			this.add(cmdExcluir, constraints);

			constraints.gridx = 2;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			this.add(cmdAlterar, constraints);
			
			constraints.gridx = 3;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			this.add(cmdGravar, constraints);

			linhaAtual++;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			this.add(cmdPrimeiro, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			this.add(cmdAnterior, constraints);

			constraints.gridx = 2;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			this.add(cmdProximo, constraints);

			constraints.gridx = 3;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			this.add(cmdUltimo, constraints);

			
			//Configurando listeners
			cmdIncluir.addActionListener(actionListener);
			cmdExcluir.addActionListener(actionListener);
			cmdAlterar.addActionListener(actionListener);
			cmdGravar.addActionListener(actionListener);
			cmdPrimeiro.addActionListener(actionListener);
			cmdAnterior.addActionListener(actionListener);
			cmdProximo.addActionListener(actionListener);
			cmdUltimo.addActionListener(actionListener);
			cmdPesquisa.addActionListener(actionListener);
			
		}

		
		public JPanelBotoesManutencao(ActionListener actionListener) {
			super();
			this.actionListener = actionListener;
			this.inicializarComponentes();
		}

		public BaseJButton getCmdAlterar() {
			return cmdAlterar;
		}
		
		public void setCmdAlterar(BaseJButton cmdAlterar) {
			this.cmdAlterar = cmdAlterar;
		}
		
		public BaseJButton getCmdAnterior() {
			return cmdAnterior;
		}
		
		public void setCmdAnterior(BaseJButton cmdAnterior) {
			this.cmdAnterior = cmdAnterior;
		}
		
		public BaseJButton getCmdExcluir() {
			return cmdExcluir;
		}
		
		public void setCmdExcluir(BaseJButton cmdExcluir) {
			this.cmdExcluir = cmdExcluir;
		}
		
		public BaseJButton getCmdGravar() {
			return cmdGravar;
		}
		
		public void setCmdGravar(BaseJButton cmdGravar) {
			this.cmdGravar = cmdGravar;
		}
		
		public BaseJButton getCmdIncluir() {
			return cmdIncluir;
		}
		
		public void setCmdIncluir(BaseJButton cmdIncluir) {
			this.cmdIncluir = cmdIncluir;
		}
		
		public BaseJButton getCmdPrimeiro() {
			return cmdPrimeiro;
		}
		
		public void setCmdPrimeiro(BaseJButton cmdPrimeiro) {
			this.cmdPrimeiro = cmdPrimeiro;
		}
		
		public BaseJButton getCmdProximo() {
			return cmdProximo;
		}
		
		public void setCmdProximo(BaseJButton cmdProximo) {
			this.cmdProximo = cmdProximo;
		}
		
		public BaseJButton getCmdUltimo() {
			return cmdUltimo;
		}
		
		public void setCmdUltimo(BaseJButton cmdUltimo) {
			this.cmdUltimo = cmdUltimo;
		}


		public ActionListener getActionListener() {
			return actionListener;
		}


		public void setActionListener(ActionListener actionListener) {
			this.actionListener = actionListener;
		}


		public BaseJButton getCmdPesquisa() {
			return cmdPesquisa;
		}


		public void setCmdPesquisa(BaseJButton cmdPesquisa) {
			this.cmdPesquisa = cmdPesquisa;
		}

		/**
		 * Desabilita todos os botões de manutenção
		 * */
		public void desabilitarBotoes() {
			getCmdIncluir().setEnabled(false);
			getCmdExcluir().setEnabled(false);
			getCmdAlterar().setEnabled(false);
			getCmdGravar().setEnabled(false);
			getCmdPrimeiro().setEnabled(false);
			getCmdAnterior().setEnabled(false);
			getCmdProximo().setEnabled(false);
			getCmdUltimo().setEnabled(false);
			getCmdPesquisa().setEnabled(false);
		}
		

		public void deixarBotoesInvisiveis() {
			getCmdIncluir().setVisible(false);
			getCmdExcluir().setVisible(false);
			getCmdAlterar().setVisible(false);
			getCmdGravar().setVisible(false);
			getCmdPrimeiro().setVisible(false);
			getCmdAnterior().setVisible(false);
			getCmdProximo().setVisible(false);
			getCmdUltimo().setVisible(false);
			getCmdPesquisa().setVisible(false);
		}
		
		
		/**
		 * Atualiza o status dos botões de menutanção da aplciação de acordo
		 * com o status do comando passado como parâmetro.
		 * Deve ser sobrescrito caso seja necessário algum comportamento
		 * específico em alguma tela.
		 * */
		public void atualizarStatusBotoesManutencao(String strMetodo , BaseDispatchCRUDCommand baseDispatchCRUDCommand) {
			if(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo())){
				getCmdIncluir().setEnabled(true);
				getCmdExcluir().setEnabled(false);
				getCmdAlterar().setEnabled(false);
				getCmdGravar().setEnabled(true);
				getCmdPrimeiro().setEnabled(false);
				getCmdAnterior().setEnabled(false);
				getCmdProximo().setEnabled(false);
				getCmdUltimo().setEnabled(false);
				getCmdPesquisa().setEnabled(false);
			}
			if(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())){
				getCmdIncluir().setEnabled(false);
				getCmdExcluir().setEnabled(false);
				getCmdAlterar().setEnabled(true);
				getCmdGravar().setEnabled(true);
				getCmdPrimeiro().setEnabled(false);
				getCmdAnterior().setEnabled(false);
				getCmdProximo().setEnabled(false);
				getCmdUltimo().setEnabled(false);
				 getCmdPesquisa().setEnabled(false);
			}
			if(BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo())){
				 getCmdIncluir().setEnabled(true);
				 getCmdIncluir().setText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.incluir"));
				 getCmdExcluir().setEnabled(true);
				 getCmdAlterar().setEnabled(true);
				 getCmdGravar().setEnabled(false);
				 getCmdPrimeiro().setEnabled(true);
				 getCmdAnterior().setEnabled(true);
				 getCmdProximo().setEnabled(true);
				 getCmdUltimo().setEnabled(true);
				 getCmdPesquisa().setEnabled(true);
			}
			if(BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())){
				 getCmdIncluir().setEnabled(true);
				 getCmdExcluir().setEnabled(true);
				 getCmdAlterar().setEnabled(true);
				 getCmdAlterar().setText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.alterar"));
				 getCmdGravar().setEnabled(false);
				 getCmdPrimeiro().setEnabled(true);
				 getCmdAnterior().setEnabled(true);
				 getCmdProximo().setEnabled(true);
				 getCmdUltimo().setEnabled(true);
				 getCmdPesquisa().setEnabled(true);
			}
			if(BaseDispatchCRUDCommand.METODO_EXCLUIR.equals(baseDispatchCRUDCommand.getStrMetodo())){
				 getCmdIncluir().setEnabled(true);
				 getCmdExcluir().setEnabled(true);
				 getCmdAlterar().setEnabled(true);
				 getCmdGravar().setEnabled(false);
				 getCmdPrimeiro().setEnabled(true);
				 getCmdAnterior().setEnabled(true);
				 getCmdProximo().setEnabled(true);
				 getCmdUltimo().setEnabled(true);
				 getCmdPesquisa().setEnabled(true);
			}	
			if(BaseDispatchCRUDCommand.METODO_LISTAR.equals(baseDispatchCRUDCommand.getStrMetodo())){
				 getCmdIncluir().setEnabled(true);
				 getCmdExcluir().setEnabled(true);
				 getCmdAlterar().setEnabled(true);
				 getCmdGravar().setEnabled(false);
				 getCmdPrimeiro().setEnabled(true);
				 getCmdAnterior().setEnabled(true);
				 getCmdProximo().setEnabled(true);
				 getCmdUltimo().setEnabled(true);
				 getCmdPesquisa().setEnabled(true);
				 getCmdAlterar().setText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.alterar"));
				 getCmdIncluir().setText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.incluir"));
			}	

	}

		
		

}
