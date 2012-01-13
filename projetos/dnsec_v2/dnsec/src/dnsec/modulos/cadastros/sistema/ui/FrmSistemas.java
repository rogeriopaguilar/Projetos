package dnsec.modulos.cadastros.sistema.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dnsec.modulos.cadastros.sistema.vo.SistemaTelaVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.database.hibernate.TiposBanco;
import dnsec.shared.factory.CommandFactory;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.swing.base.BaseJButton;
import dnsec.shared.swing.base.BaseJCheckBox;
import dnsec.shared.swing.base.BaseJComboBox;
import dnsec.shared.swing.base.BaseJFormattedTextField;
import dnsec.shared.swing.base.BaseJInternalFrame;
import dnsec.shared.swing.base.BaseJLabel;
import dnsec.shared.swing.base.BaseJTextBox;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.RecursosUtil;


//public class FrmSistemas extends JFrame implements ActionListener{
public class FrmSistemas extends BaseJInternalFrame implements ActionListener{
	
		private BaseJLabel lblCodigoSistema;
		private BaseJLabel lblDescricaoSistema;
		private BaseJLabel lblNomeBancoSistema;
		private BaseJLabel lblNomeServidorSistema;
		private BaseJLabel lblTipoBancoSistema;
		private BaseJLabel lblNomeProprietarioSistema;
		private BaseJLabel lblNomeAnalistaResponsavelSistema;
		private BaseJButton cmdConfirmarOperacao;
		private BaseJButton cmdConfirmarCancelarOperacao;
		private BaseJFormattedTextField txtCodigoSistema;
		private BaseJTextBox txtDescricaoSistema;
		private BaseJCheckBox chkCondCadastrarSistema;
		private BaseJTextBox txtNomeBancoSistema;
		private BaseJTextBox txtNomeServidorSistema;
		private BaseJTextBox txtNomeProprietarioSistema;
		private BaseJTextBox txtNomeAnalistaResponsavelSistema;
		private BaseJComboBox cmbTipoBancoSistema;
		
		//private BaseJButton teste = new BaseJButton("Teste");
		
		/**Command para sistemas*/
		private BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_SISTEMAS);

		/**
		 * Vos que encapsulam os dados da tela
		 * */
		private String strMetodoCommand = "";
		
		private SistemaTelaVo sistemaTelaVo = new SistemaTelaVo();
		
		/**
		 * Substitui os dados da tela pelos dados do vo
		 * */
		public void setTelaVo(SistemaTelaVo sistemaTelaVo){
			txtCodigoSistema.setText( ( sistemaTelaVo.getSistema().getCodSistemaSist() == null ? "" : sistemaTelaVo.getSistema().getCodSistemaSist()) );
			txtDescricaoSistema.setText( ( sistemaTelaVo.getSistema().getDescrSistemaSist() == null ? "" : sistemaTelaVo.getSistema().getDescrSistemaSist() ) );
			chkCondCadastrarSistema.setSelected(Constantes.CONSTANTE_SIM.equals(sistemaTelaVo.getSistema().getCondCadastrarSist()));
			txtNomeBancoSistema.setText( ( sistemaTelaVo.getSistema().getNomeBancoSist() == null ? "" : sistemaTelaVo.getSistema().getNomeBancoSist()));
			txtNomeServidorSistema.setText( ( sistemaTelaVo.getSistema().getNomeServidorSist() == null ? "" : sistemaTelaVo.getSistema().getNomeServidorSist()));
			txtNomeProprietarioSistema.setText( ( sistemaTelaVo.getSistema().getNomeProprietarioSist() == null ? "" : sistemaTelaVo.getSistema().getNomeProprietarioSist()));
			txtNomeAnalistaResponsavelSistema.setText( ( sistemaTelaVo.getSistema().getNomeAnalistaRespSist() == null ? "" : sistemaTelaVo.getSistema().getNomeAnalistaRespSist()));
			carregarTiposBanco();
			for(int indiceLista = 0; indiceLista < cmbTipoBancoSistema.getModel().getSize(); indiceLista++)
			{
				if( ("" + cmbTipoBancoSistema.getModel().getElementAt(indiceLista)).toUpperCase().equalsIgnoreCase(sistemaTelaVo.getSistema().getTipoBancoSist()) )
				{
					cmbTipoBancoSistema.getModel().setSelectedItem(cmbTipoBancoSistema.getModel().getElementAt(indiceLista));
					break;
				}
			}
		}
		
		/**
		 * Retorna os dados da tela encapsulados num vo
		 * */
		public SistemaTelaVo getTelaVo(){
			sistemaTelaVo.getSistema().setCodSistemaSist(txtCodigoSistema.getText());	
			sistemaTelaVo.getSistema().setCondCadastrarSist(chkCondCadastrarSistema.isSelected() ? Constantes.CONSTANTE_SIM : Constantes.CONSTANTE_NAO);
			sistemaTelaVo.getSistema().setDescrSistemaSist(txtDescricaoSistema.getText());
			sistemaTelaVo.getSistema().setNomeAnalistaRespSist(txtNomeAnalistaResponsavelSistema.getText());
			sistemaTelaVo.getSistema().setNomeBancoSist(txtNomeBancoSistema.getText());
			sistemaTelaVo.getSistema().setNomeProprietarioSist(txtNomeProprietarioSistema.getText());
			sistemaTelaVo.getSistema().setNomeServidorSist(txtNomeServidorSistema.getText());
			sistemaTelaVo.getSistema().setTipoBancoSist("" + cmbTipoBancoSistema.getSelectedItem());
			return this.sistemaTelaVo;
		}
		
		
		public FrmSistemas(){
			super();
			this.inicializarMaximizado = false;
			setFrameIcon(GerenciadorJanelas.ICONE_SISTEMA_PEQUENO);
			this.inicializarComponentes();
			this.setTitle(RecursosUtil.getInstance().getResource("key.cadastro.sistemas.titulo.janela"));
		}

		public void atualizarEstado(){
			FrmSistemas.this.strMetodoCommand = baseDispatchCRUDCommand.getStrMetodo();
			ActionEvent evt = new ActionEvent(new BaseJButton(),0,"");
			FrmSistemas.this.actionPerformed(evt);
			FrmSistemas.this.txtCodigoSistema.setEnabled(true);
			//Se estiver editando o registro, não pode alterar a chave primária
			if(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())){
				FrmSistemas.this.txtCodigoSistema.setEnabled(false);
			}
			//carregarTiposBanco();
			centralizarJanela();
		}	

		private void carregarTiposBanco()
		{
			cmbTipoBancoSistema.removeAllItems();
			CommandFactory.getCommand(CommandFactory.COMMAND_TIPOS_BANCO).setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
			try {
				Object[] objRetorno = CommandFactory.getCommand(CommandFactory.COMMAND_TIPOS_BANCO).executar(null);
				List<TiposBanco> listaTiposBanco = (List) objRetorno[0];
				for(TiposBanco banco : listaTiposBanco)
				{
					cmbTipoBancoSistema.addItem(banco);
				}
				
			} catch (CommandException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void inicializarComponentes(){
			GridBagLayout gridBagLayout = new GridBagLayout();
			GridBagConstraints constraints = new GridBagConstraints();
			getContentPane().setLayout(gridBagLayout);
			
			int linhaAtual = 0;
			JPanel painel = new JPanel();
			painel.setLayout(new GridBagLayout());
			
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblCodigoSistema = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.sistemas.label.codigo.sistema"));
			painel.add(lblCodigoSistema, constraints);

			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			txtCodigoSistema = new BaseJFormattedTextField();
			txtCodigoSistema.setColumns(3);
			txtCodigoSistema.setTamanhoMaximo(3);
			painel.add(txtCodigoSistema, constraints);

			constraints.gridx = 2;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			chkCondCadastrarSistema = new BaseJCheckBox(RecursosUtil.getInstance().getResource("key.cadastro.sistemas.label.cond.cadastrar.sistema"));
			//painel.add(chkCondCadastrarSistema, constraints);

			linhaAtual++;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblDescricaoSistema = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.sistemas.label.descricao.sistema"));
			painel.add(lblDescricaoSistema, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 2;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			txtDescricaoSistema = new BaseJTextBox();
			txtDescricaoSistema.setTamanhoMaximo(30);
			txtDescricaoSistema.setColumns(30);
			painel.add(txtDescricaoSistema, constraints);


			linhaAtual++;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblNomeAnalistaResponsavelSistema = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.sistemas.label.analista"));
			painel.add(lblNomeAnalistaResponsavelSistema, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 2;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			txtNomeAnalistaResponsavelSistema = new BaseJTextBox();
			txtNomeAnalistaResponsavelSistema.setTamanhoMaximo(20);
			txtNomeAnalistaResponsavelSistema.setColumns(20);
			painel.add(txtNomeAnalistaResponsavelSistema, constraints);
			
			
			linhaAtual++;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblNomeBancoSistema= new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.sistemas.label.banco.sistema"));
			painel.add(lblNomeBancoSistema, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 2;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			txtNomeBancoSistema = new BaseJTextBox();
			txtNomeBancoSistema.setTamanhoMaximo(20);
			txtNomeBancoSistema.setColumns(20);
			painel.add(txtNomeBancoSistema, constraints);
			

			linhaAtual++;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblNomeProprietarioSistema = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.sistemas.label.proprietario"));
			painel.add(lblNomeProprietarioSistema, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 2;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			txtNomeProprietarioSistema = new BaseJTextBox();
			txtNomeProprietarioSistema.setTamanhoMaximo(20);
			txtNomeProprietarioSistema.setColumns(20);
			painel.add(txtNomeProprietarioSistema, constraints);


			linhaAtual++;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblNomeServidorSistema = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.sistemas.label.servidor.sistema"));
			painel.add(lblNomeServidorSistema, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			txtNomeServidorSistema = new BaseJTextBox();
			txtNomeServidorSistema.setTamanhoMaximo(25);
			txtNomeServidorSistema.setColumns(20);
			painel.add(txtNomeServidorSistema, constraints);


			JPanel painelTipoBanco = new JPanel();
			lblTipoBancoSistema = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.sistemas.label.tipo.banco"));
			painelTipoBanco.add(lblTipoBancoSistema, constraints);
			cmbTipoBancoSistema = new BaseJComboBox();
			cmbTipoBancoSistema.setEditable(true);
			carregarTiposBanco();
			painelTipoBanco.add(cmbTipoBancoSistema, constraints);

			constraints.gridx = 2;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			painel.add(painelTipoBanco, constraints);

			linhaAtual++;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = GridBagConstraints.REMAINDER;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			JPanel painelBotoes = new JPanel();
			cmdConfirmarOperacao = new BaseJButton(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.confirmar"), GerenciadorJanelas.ICONE_BOTAO_CONFIRMAR);
			cmdConfirmarCancelarOperacao = new BaseJButton(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.cancelar"), GerenciadorJanelas.ICONE_BOTAO_CANCELAR);
			cmdConfirmarCancelarOperacao.addActionListener(this);
			cmdConfirmarOperacao.addActionListener(this);
  			painelBotoes.add(cmdConfirmarOperacao);
			painelBotoes.add(cmdConfirmarCancelarOperacao);
			painel.add(painelBotoes, constraints);

			constraints.gridx=0;
			constraints.gridy=0;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.insets = new Insets(0, 0, 0, 0);
			constraints.fill = GridBagConstraints.BOTH;
			constraints.anchor = GridBagConstraints.NORTH;
			getContentPane().add(painel, constraints);

			
			/*teste.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					cmbTipoBancoSistema.getModel().setSelectedItem(cmbTipoBancoSistema.getModel().getElementAt(2));
				}
			});
			
			getContentPane().add(teste);*/
			
			pack();
		}

		public void desabilitarBotoes(){
			this.cmdConfirmarCancelarOperacao.setEnabled(false);
			this.cmdConfirmarOperacao.setEnabled(false);
		}

		
		public boolean exibirMsgConfirmacao(String strMetodo){
			if(
				BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo())
															||
				BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())
			
			){
				return true;
			}
			return false;
		}

		
		public boolean atualizarDadosTela(String strMetodo){
			if(
				BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo())
															||
				BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())
			
			){
				return true;
			}
			return false;
		}

		
		public void atualizarStatusBotoesManutencao(String strMetodo){
			this.cmdConfirmarCancelarOperacao.setEnabled(true);
			this.cmdConfirmarOperacao.setEnabled(true);
		}
		
		public void actionPerformed(ActionEvent e) {
			Object[] args = new Object[]{ this.getTelaVo().getSistema() };
			this.baseDispatchCRUDCommand.setStrMetodo(this.strMetodoCommand);
			
			if(e.getSource() == cmdConfirmarCancelarOperacao){
				/**Fecha a janela e retorna para a tela de pesquisa*/
				GerenciadorJanelas.getInstance().getAcaoPesquisaSistemas(getParent(), new LinkedList(), false, true).actionPerformed(e);
				//this.dispose();
				this.setVisible(false);
				return;
			}
			

			if(e.getSource() == this.cmdConfirmarOperacao){
				if((BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo()))){
					baseDispatchCRUDCommand.setMetodoAnterior(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO);
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO);
					//args = new Object[]{ this.getTelaVo().getSistema() };
				}else{
					if( BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())){
						baseDispatchCRUDCommand.setMetodoAnterior(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
						baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO);
						//args = new Object[]{ this.getTelaVo().getSistema() };
					}else{
						//Botão não deveria estar habilitado!
					}
				}
			}
			
			if(baseDispatchCRUDCommand != null)
			{
				try{
					this.desabilitarBotoes();
					Object objRetorno[] = baseDispatchCRUDCommand.executar( args );

					
					if( this.exibirMsgConfirmacao(baseDispatchCRUDCommand.getStrMetodo()))
					{
						JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.realizada.com.sucesso"), RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.realizada.com.sucesso.titulo.janela"), JOptionPane.INFORMATION_MESSAGE);
						/*Fecha a janela e retorna para a tela de pesquisa*/
						GerenciadorJanelas.getInstance().getAcaoPesquisaSistemas(getParent(), new LinkedList(), false, true).actionPerformed(e);
						this.setVisible(false);
						//this.dispose();
						return;
					}

					if(this.atualizarDadosTela(baseDispatchCRUDCommand.getStrMetodo())){
						SistemaTelaVo sistemaTelaVo = new SistemaTelaVo();
						sistemaTelaVo.setSistema((Sistema) objRetorno[0]);
						this.setTelaVo(sistemaTelaVo);
					}

				}catch(CommandException commandException){
					super.tratarMensagemErro(commandException);
					/*
					 * Se estava numa operação de gravação volta para o status anterior
					 * */
					if(BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo()))
					{
						baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO);
						baseDispatchCRUDCommand.setMetodoAnterior("");
					}else if(BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo()))
					{
						baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
						baseDispatchCRUDCommand.setMetodoAnterior("");
					}

				}
			}else{
				JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.nao.conigurada"), RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.nao.conigurada.titulo.janela"), JOptionPane.WARNING_MESSAGE);
			}
			this.atualizarStatusBotoesManutencao(baseDispatchCRUDCommand.getStrMetodo());
		}


		
		public BaseDispatchCRUDCommand getBaseDispatchCRUDCommand() {
			return baseDispatchCRUDCommand;
		}

		public void setBaseDispatchCRUDCommand(
				BaseDispatchCRUDCommand baseDispatchCRUDCommand) {
			this.baseDispatchCRUDCommand = baseDispatchCRUDCommand;
		}

}
