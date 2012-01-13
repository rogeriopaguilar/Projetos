package dnsec.modulos.cadastros.funcao.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dnsec.modulos.cadastros.funcao.vo.FuncaoTelaVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.factory.CommandFactory;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.listener.PesquisaFuncaoListener;
import dnsec.shared.listener.PesquisaSistemaListener;
import dnsec.shared.swing.base.BaseJButton;
import dnsec.shared.swing.base.BaseJFormattedTextField;
import dnsec.shared.swing.base.BaseJInternalFrame;
import dnsec.shared.swing.base.BaseJLabel;
import dnsec.shared.swing.base.BaseJTextArea;
import dnsec.shared.swing.base.BaseJTextBox;
import dnsec.shared.ui.JPanelBotoesManutencao;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.RecursosUtil;
import dnsec.shared.util.StringUtil;

public class FrmFuncoes extends BaseJInternalFrame implements ActionListener, PesquisaFuncaoListener, PesquisaSistemaListener{
	
		private JPanelBotoesManutencao jPanelBotoesManutencao;
		private BaseJButton cmdSair;
		
		private BaseJLabel lblSistema;
		private BaseJLabel lblSistemaSelecionado;
		private BaseJLabel lblSistemaPai;
		private BaseJLabel lblSistemaPaiSelecionado;
		private BaseJLabel lblFuncaoPai;
		private BaseJLabel lblFuncaoPaiSelecionada;
		private BaseJLabel lblCodigo;
		private BaseJLabel lblNome;
		private BaseJLabel lblDescricao;
		
		private BaseJButton cmdPesquisaSistema;
		private BaseJButton cmdPesquisaSistemaPai;
		private BaseJButton cmdPesquisaFuncaoPai;
		private BaseJButton cmdConfirmarOperacao;
		private BaseJButton cmdConfirmarCancelarOperacao;
		
		private BaseJFormattedTextField txtCodigoFuncao;
		private BaseJTextBox txtNomeFuncao;
		private BaseJTextArea txtDescricaoFuncao;

		/**
		 * Como o popup de seleção de sistema é chamado duas vezes
		 * (uma para a seleção do sistema e outra para a seleção do
		 *  sistema pai) é necessária uma forma de saber por qual
		 *  botão a tela foi chamada. As variáveis abaixo são utilizadas
		 *  nesta situação.
		 * */
		private int TIPO_SELECAO_SISTEMA_PAI = 0;
		private int TIPO_SELECAO_SISTEMA = 1;
		private int TIPO_SELECAO_SISTEMA_ATUAL = -1;  
		
		
		/**Command para funções*/
		private BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_FUNCOES);

		
		/**
		 * Vos que encapsulam os dados da tela
		 * */
		private FuncaoTelaVo funcaoTelaVo = new FuncaoTelaVo();
 
		private String strMetodoCommand = "";
		
		/**
		 * Retorna os dados da tela encapsulados num vo
		 * */
		public FuncaoTelaVo getTelaVo(){
				try{
					funcaoTelaVo.getFuncao().getCompId().setCodFuncaoFunc(new Long(this.txtCodigoFuncao.getText().trim()));
				}catch(NumberFormatException e){
					funcaoTelaVo.getFuncao().getCompId().setCodFuncaoFunc(null);
				}
				funcaoTelaVo.getFuncao().setNomeFunc(this.txtNomeFuncao.getText());
				funcaoTelaVo.getFuncao().setDescrFuncaoFunc(this.txtDescricaoFuncao.getText());
				return this.funcaoTelaVo;
		}
		

		
		/**
		 * Substitui os dados da tela pelos dados do vo
		 * */
		public void setTelaVo(FuncaoTelaVo funcaoTelaVo){
				if(funcaoTelaVo.getFuncao() != null){

					cmdPesquisaSistema.setText(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar"));
					cmdPesquisaSistemaPai.setText(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar"));
					cmdPesquisaFuncaoPai.setText(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar"));

					lblSistemaSelecionado.setText(
							RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.nao.selecionado")
					);
					
					lblSistemaPaiSelecionado.setText(
							RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.nao.selecionado")
					);
					
					lblFuncaoPaiSelecionada.setText(
							RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.nao.selecionada")
					);

					
					this.funcaoTelaVo = funcaoTelaVo;
					
					
					if(!"".equals(StringUtil.NullToStrTrim(funcaoTelaVo.getFuncao().getCompId().getCodSistemaSist()))){
						cmdPesquisaSistema.setText(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar.cancela.pesquisa"));
						lblSistemaSelecionado.setText(
								funcaoTelaVo.getFuncao().getCompId().getCodSistemaSist()
						);
					}
					
					if(!"".equals(StringUtil.NullToStrTrim(funcaoTelaVo.getFuncao().getCodSistemaPaiFunc()))){
						cmdPesquisaSistemaPai.setText(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar.cancela.pesquisa"));
						this.lblSistemaPaiSelecionado.setText(
							funcaoTelaVo.getFuncao().getCodSistemaPaiFunc()
						);
					}
					
					//Dados da função pai
					if(funcaoTelaVo.getFuncao().getFuncaoPai() != null){
						cmdPesquisaFuncaoPai.setText(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar.cancela.pesquisa"));
						this.lblFuncaoPaiSelecionada.setText(
								funcaoTelaVo.getFuncao().getFuncaoPai().getCompId().getCodFuncaoFunc() + " - " + funcaoTelaVo.getFuncao().getFuncaoPai().getNomeFunc()
						);
					}
					
					if(funcaoTelaVo.getFuncao().getCompId().getCodFuncaoFunc() != null){
						this.txtCodigoFuncao.setText("" + funcaoTelaVo.getFuncao().getCompId().getCodFuncaoFunc());
					}else{
						this.txtCodigoFuncao.setText("");
					}
					this.txtNomeFuncao.setText(funcaoTelaVo.getFuncao().getNomeFunc());
					this.txtDescricaoFuncao.setText(funcaoTelaVo.getFuncao().getDescrFuncaoFunc());
					
				}
		}
		
		
		public FrmFuncoes(){
			super();
			this.inicializarMaximizado = false;
			setFrameIcon(GerenciadorJanelas.ICONE_FUNCAO_PEQUENO);
			this.inicializarComponentes();
			this.setTitle(RecursosUtil.getInstance().getResource("key.cadastro.funcoes.titulo.janela"));
		}

		public void atualizarEstado(){
			FrmFuncoes.this.strMetodoCommand = baseDispatchCRUDCommand.getStrMetodo();
			ActionEvent evt = new ActionEvent(new BaseJButton(),0,"");
			FrmFuncoes.this.actionPerformed(evt);
			FrmFuncoes.this.txtCodigoFuncao.setEnabled(true);
			FrmFuncoes.this.txtNomeFuncao.setEnabled(true);
			FrmFuncoes.this.cmdPesquisaSistema.setEnabled(true);
			FrmFuncoes.this.cmdPesquisaSistemaPai.setEnabled(true);
			FrmFuncoes.this.cmdPesquisaFuncaoPai.setEnabled(true);

			//Se estiver editando o registro, não pode alterar a chave primária
			if(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())){
				FrmFuncoes.this.txtCodigoFuncao.setEnabled(false);
				FrmFuncoes.this.cmdPesquisaSistema.setEnabled(false);
				FrmFuncoes.this.cmdPesquisaSistemaPai.setEnabled(false);
				FrmFuncoes.this.cmdPesquisaFuncaoPai.setEnabled(false);
				
				//Se for uma função pré-definida pelko sistema de segurança o nome não pode ser alterado
				Funcao funcao = getTelaVo().getFuncao();
				if(
						(
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_INC_SISTEMA) ||      
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_SISTEMA) ||      
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_EXC_SISTEMA) ||     
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_INC_USUARIO) ||      
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_USUARIO) ||      
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_EXC_USUARIO) ||      
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_INC_GRUPO) ||       
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_GRUPO) ||        
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_EXC_GRUPO) ||        
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_INC_FUNCAO) ||      
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_FUNCAO) ||       
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_EXC_FUNCAO) ||       
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_REL_SISTEMA) ||  
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_REL_USUARIO) ||  
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_REL_GRUPO) ||    
							funcao.getNomeFunc().equals(Constantes.FUNC_SEC_ALT_REL_FUNCAO) ||   
							funcao.getNomeFunc().equals(Constantes.FUNC_ACESSO_TODOS_GRUPOS) || 
							funcao.getNomeFunc().equals(Constantes.FUNC_ACESSO_TODOS_USUARIOS) || 
							funcao.getNomeFunc().equals(Constantes.FUNC_ACESSO_TODAS_FUNCOES) || 
							funcao.getNomeFunc().equals(Constantes.FUNC_ACESSO_TODOS_SISTEMAS)
						)
													&& 
						Constantes.COD_SIST_ADM.equals(funcao.getCompId().getCodSistemaSist())
				){
							FrmFuncoes.this.txtNomeFuncao.setEnabled(false);
			   }
			}
			centralizarJanela();
		}
		
		public void inicializarComponentes(){
			GridBagLayout gridBagLayout = new GridBagLayout();
			GridBagConstraints constraints = new GridBagConstraints();
			getContentPane().setLayout(gridBagLayout);
			jPanelBotoesManutencao = new JPanelBotoesManutencao(this);
			cmdSair = new BaseJButton(RecursosUtil.getInstance().getResource("key.cadastro.grupos.label.botao.sair"));

			
			
			
			int linhaAtual = 0;
			
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblSistema = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.sistema"));
			getContentPane().add(lblSistema, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.CENTER;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblSistemaSelecionado= new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.nao.selecionado"));
			lblSistemaSelecionado.setForeground(Color.BLUE);
			getContentPane().add(lblSistemaSelecionado, constraints);
			
			constraints.gridx = 2;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.CENTER;
			constraints.anchor = GridBagConstraints.EAST;
			constraints.insets = new Insets(2, 2, 2, 2);
			cmdPesquisaSistema = new BaseJButton(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar"), GerenciadorJanelas.ICONE_SISTEMA_PEQUENO);
			getContentPane().add(cmdPesquisaSistema, constraints);
			

			linhaAtual++;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblSistemaPai = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.sistema.pai"));
			getContentPane().add(lblSistemaPai, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.CENTER;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblSistemaPaiSelecionado = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.nao.selecionado"));
			lblSistemaPaiSelecionado.setForeground(Color.BLUE);
			getContentPane().add(lblSistemaPaiSelecionado, constraints);
			
			constraints.gridx = 2;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.CENTER;
			constraints.anchor = GridBagConstraints.EAST;
			constraints.insets = new Insets(2, 2, 2, 2);
			cmdPesquisaSistemaPai = new BaseJButton(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar"), GerenciadorJanelas.ICONE_SISTEMA_PEQUENO);
			getContentPane().add(cmdPesquisaSistemaPai, constraints);

			
			linhaAtual++;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblFuncaoPai = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.funcao.pai"));
			getContentPane().add(lblFuncaoPai, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.CENTER;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblFuncaoPaiSelecionada = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.nao.selecionada"));
			lblFuncaoPaiSelecionada.setForeground(Color.BLUE);
			getContentPane().add(lblFuncaoPaiSelecionada, constraints);
			
			constraints.gridx = 2;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.CENTER;
			constraints.anchor = GridBagConstraints.EAST;
			constraints.insets = new Insets(2, 2, 2, 2);
			cmdPesquisaFuncaoPai = new BaseJButton(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar"), GerenciadorJanelas.ICONE_FUNCAO_PEQUENO);
			getContentPane().add(cmdPesquisaFuncaoPai, constraints);

			linhaAtual++;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblCodigo = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.codigo"));
			getContentPane().add(lblCodigo, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.weightx = 10;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 2;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			//constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			/*try {
				txtCodigoFuncao = new BaseJFormattedTextField(new MaskFormatter("#####"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			NumberFormat nf = NumberFormat.getIntegerInstance();
			nf.setGroupingUsed(false);
			txtCodigoFuncao = new BaseJFormattedTextField(nf);
			//txtCodigoFuncao.setValue(new Long(1));
			getContentPane().add(txtCodigoFuncao, constraints);


			linhaAtual++;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblNome = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.nome"));
			getContentPane().add(lblNome, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.weightx = 10;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 2;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			//constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			txtNomeFuncao = new BaseJTextBox();
			txtNomeFuncao.setTamanhoMaximo(30);
			txtNomeFuncao.setExibirMaiuscula(false);
			getContentPane().add(txtNomeFuncao, constraints);

			linhaAtual++;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblDescricao = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.descricao"));
			getContentPane().add(lblDescricao, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.weightx = 0;
			constraints.weighty = 100;
			constraints.gridheight = 5;
			constraints.gridwidth = 2;
			constraints.fill = GridBagConstraints.BOTH;
			//constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			txtDescricaoFuncao = new BaseJTextArea(4,20);
			txtDescricaoFuncao.setTamanhoMaximo(60);
			JScrollPane paneDescricao = new JScrollPane(txtDescricaoFuncao);
			getContentPane().add(paneDescricao, constraints);
			

			JPanel painelBotoes = new JPanel();
			cmdConfirmarOperacao = new BaseJButton(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.confirmar"), GerenciadorJanelas.ICONE_BOTAO_CONFIRMAR);
			cmdConfirmarCancelarOperacao = new BaseJButton(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.cancelar"), GerenciadorJanelas.ICONE_BOTAO_CANCELAR);
			painelBotoes.add(cmdConfirmarOperacao);
			painelBotoes.add(cmdConfirmarCancelarOperacao);

			linhaAtual += 5;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 10;
			constraints.weighty = 0;
			constraints.gridheight = 2;
			constraints.gridwidth = 3;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			//constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			getContentPane().add(painelBotoes, constraints);

			cmdPesquisaSistema.addActionListener(this);
			cmdPesquisaSistemaPai.addActionListener(this);
			cmdPesquisaFuncaoPai.addActionListener(this);
			cmdConfirmarOperacao.addActionListener(this);
			cmdConfirmarCancelarOperacao.addActionListener(this);
			
			
			pack();
		}

		public void desabilitarBotoes(){
			this.cmdConfirmarCancelarOperacao.setEnabled(false);
			this.cmdConfirmarOperacao.setEnabled(false);
			this.cmdPesquisaFuncaoPai.setEnabled(false);
			this.cmdPesquisaSistema.setEnabled(false);
			this.cmdPesquisaSistemaPai.setEnabled(false);
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
			this.cmdPesquisaFuncaoPai.setEnabled(true);
			this.cmdPesquisaSistema.setEnabled(true);
			this.cmdPesquisaSistemaPai.setEnabled(true);
		}
		
		public void actionPerformed(ActionEvent e) {
			Object[] args = new Object[]{this.getTelaVo().getFuncao()};
			
			this.baseDispatchCRUDCommand.setStrMetodo(this.strMetodoCommand);
			
			if(e.getSource() == cmdConfirmarCancelarOperacao){
				/**Fecha a janela e retorna para a tela de pesquisa*/
				GerenciadorJanelas.getInstance().getAcaoCadastroFuncoes(getParent()).actionPerformed(e);
				//GerenciadorJanelas.removePesquisaFuncaoListener(this);
				//GerenciadorJanelas.removePesquisaSistemaListener(this);
				this.setVisible(false);
				return;
			}
			
			
			if(e.getSource() == this.cmdConfirmarOperacao){
				if((BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo()))){
					baseDispatchCRUDCommand.setMetodoAnterior(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO);
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO);
					args = new Object[]{ this.getTelaVo().getFuncao() };
				}else{
					if( BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())){
						baseDispatchCRUDCommand.setMetodoAnterior(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
						baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO);
						args = new Object[]{ this.getTelaVo().getFuncao() };
					}else{
						//Botão não deveria estar habilitado!
					}
				}
			}

			/*Botões de pesquisa de sistemas*/
			if(	e.getSource() == cmdPesquisaSistema	
							||
				e.getSource() == cmdPesquisaSistemaPai)
			{
				if(	e.getSource() == cmdPesquisaSistema){
					if(cmdPesquisaSistema.getText().equals(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar.cancela.pesquisa")))
					{
						//Cancelando o sistema selecionado
						this.TIPO_SELECAO_SISTEMA_ATUAL = -1;
						getTelaVo().getFuncao().setSistema(null);
						lblSistemaSelecionado.setText(
								RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.nao.selecionado")
						);
						cmdPesquisaSistema.setText(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar"));
						return;
					}else{
						//Pesquisando
						this.TIPO_SELECAO_SISTEMA_ATUAL = this.TIPO_SELECAO_SISTEMA;
					}	
				}else{
					if(cmdPesquisaSistemaPai.getText().equals(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar.cancela.pesquisa")))
					{
						//Cancelando o sistema selecionado
						this.TIPO_SELECAO_SISTEMA_ATUAL = -1;
						getTelaVo().getFuncao().setCodSistemaPaiFunc(null);
						lblSistemaPaiSelecionado.setText(
								RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.nao.selecionado")
						);
						cmdPesquisaSistemaPai.setText(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar"));
						return;
					}else{
						//Pesquisando
						this.TIPO_SELECAO_SISTEMA_ATUAL = this.TIPO_SELECAO_SISTEMA_PAI;
					}	
				}
				
				/* Abre o popup de pesquisa de sistemas, 
				 * configurando o listener de retorno como sendo 
				 * a intância atual  
				 * */
				List listenersSistemasList = new LinkedList();
				listenersSistemasList.add(this);
				GerenciadorJanelas.getInstance().getAcaoPesquisaSistemas(getParent(), listenersSistemasList, true, false).actionPerformed(e);
				return;
			}

			
			/*Botões de pesquisa de funções*/
			if(	e.getSource() == cmdPesquisaFuncaoPai)
			{
					if(cmdPesquisaFuncaoPai.getText().equals(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar.cancela.pesquisa")))
					{
						//Cancelando a função pai selecionada
						getTelaVo().getFuncao().setFuncaoPai(null);
						getTelaVo().getFuncao().setCodFuncaoPaiFunc(null);
						lblFuncaoPaiSelecionada.setText(
								RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.nao.selecionada")
						);
						cmdPesquisaFuncaoPai.setText(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar"));
						return;
					}
				
				/* Abre o popup de pesquisa de funções, 
				 * configurando o listener de retorno como sendo 
				 * a intância atual  
				 * */
				List listenersFuncoesList = new LinkedList();
				listenersFuncoesList.add(this);
				GerenciadorJanelas.getInstance().getAcaoPesquisaFuncoes(getParent(), listenersFuncoesList, Collections.EMPTY_LIST, false).actionPerformed(e);
				return;
			}

			
			if(baseDispatchCRUDCommand != null)
			{
				try{
					this.desabilitarBotoes();
					Object objRetorno[] = baseDispatchCRUDCommand.executar( args );

					
					if( this.exibirMsgConfirmacao(baseDispatchCRUDCommand.getStrMetodo()))
					{
						JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.realizada.com.sucesso"), RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.realizada.com.sucesso.titulo.janela"), JOptionPane.INFORMATION_MESSAGE);
						if(
								BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo())
								&&
								JOptionPane.showConfirmDialog(this, "Deseja cadastrar mais funções?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION){
							baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO);
							FuncaoTelaVo funcaoTelaVO = getTelaVo();
							funcaoTelaVo.getFuncao().getCompId().setCodFuncaoFunc(null);
							GerenciadorJanelas.getInstance().getAcaoJanelaCadastroFuncoes(getParent(), BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO, getTelaVo().getFuncao()).actionPerformed(e);
							return;
						}else{
							this.setVisible(false);
							this.setTelaVo(new FuncaoTelaVo());
							/*Fecha a janela e retorna para a tela de pesquisa*/
							GerenciadorJanelas.getInstance().getAcaoCadastroFuncoes(getParent()).actionPerformed(e);
						}
					}

					if(this.atualizarDadosTela(baseDispatchCRUDCommand.getStrMetodo())){
						FuncaoTelaVo funcaoTelaVo = new FuncaoTelaVo();
						funcaoTelaVo.setFuncao((Funcao) objRetorno[0]);
						this.setTelaVo(funcaoTelaVo);	
						this.cmdPesquisaSistema.requestFocus();
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


		
		/**
		 * Callback para a tela de pesquisa de funções
		 * Seta no vo a função selecionada atualmente
		 * */
		public void funcaoSelecionada(Funcao funcao) {
			this.getTelaVo().getFuncao().setFuncaoPai(funcao);
			this.getTelaVo().getFuncao().setCodFuncaoPaiFunc(funcao.getCompId().getCodFuncaoFunc());
			this.getTelaVo().getFuncao().setCodSistemaPaiFunc(funcao.getCompId().getCodSistemaSist());
			this.getTelaVo().getFuncao().setSistema(funcao.getSistema());
			this.getTelaVo().getFuncao().getCompId().setCodSistemaSist(funcao.getSistema().getCodSistemaSist());
			this.setTelaVo(this.getTelaVo());
		}

		
		/**
		 * Indica para a tela de pesquisa de funções que quando uma função
		 * for selecionada a tela de pesquisa deve ser fechada
		 * */
		public boolean fecharJanelaPesquisaFuncao() {
			return true;
		}

		/**
		 * Seta no vo os dados do sistema selecionado e do sistema pai selecionado
		 * Callback da janela de pesquisa de sistemas
		 * */
		public void sistemaSelecionado(Sistema sistema) {
			if(this.TIPO_SELECAO_SISTEMA_ATUAL == TIPO_SELECAO_SISTEMA){
				getTelaVo().getFuncao().setSistema(sistema);
				getTelaVo().getFuncao().getCompId().setCodSistemaSist(sistema.getCodSistemaSist());
				cmdPesquisaSistema.setText(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar.cancela.pesquisa"));
				this.lblSistemaSelecionado.setText(sistema.getCodSistemaSist());
			}else if(this.TIPO_SELECAO_SISTEMA_ATUAL == TIPO_SELECAO_SISTEMA_PAI){
				getTelaVo().getFuncao().setCodSistemaPaiFunc(sistema.getCodSistemaSist());
				cmdPesquisaSistemaPai.setText(RecursosUtil.getInstance().getResource("key.cadastro.funcao.label.botao.pesquisar.cancela.pesquisa"));
				this.lblSistemaPaiSelecionado.setText(sistema.getCodSistemaSist());
			}
			
			
		}

		public boolean fecharJanelaPesquisaSistema() {
			return true;
		}

		public BaseDispatchCRUDCommand getBaseDispatchCRUDCommand() {
			return baseDispatchCRUDCommand;
		}

		public void setBaseDispatchCRUDCommand(
				BaseDispatchCRUDCommand baseDispatchCRUDCommand) {
			this.baseDispatchCRUDCommand = baseDispatchCRUDCommand;
		}

		public static void main(String[] args){
			new FrmFuncoes().show();
		}
}
