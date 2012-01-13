package dnsec.modulos.cadastros.sistema.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.InputVerifier;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.sun.swing.examples.TableSorter;

import dnsec.modulos.cadastros.sistema.model.TabelaPesquisaSistemaModel;
import dnsec.modulos.cadastros.sistema.vo.SistemaSearchVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.factory.CommandFactory;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.listener.PesquisaSistemaListener;
import dnsec.shared.swing.base.BaseJButton;
import dnsec.shared.swing.base.BaseJFormattedTextField;
import dnsec.shared.swing.base.BaseJInternalFrame;
import dnsec.shared.swing.base.BaseJLabel;
import dnsec.shared.swing.base.BaseJTable;
import dnsec.shared.swing.base.BaseJTextBox;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.Pagina;
import dnsec.shared.util.RecursosUtil;

public class FrmPesquisaSistemas extends BaseJInternalFrame implements ActionListener{
		
		private BaseJLabel lblCodigoSistema; 
		private BaseJTextBox txtCodigoSistema;
		private BaseJLabel lblDescricaoSistema; 
		private BaseJTextBox txtDescricaoSistema;
		private BaseJButton cmdPesquisa;

		private BaseJButton cmdSair;
		private BaseJButton cmdPrimeiro;
		private BaseJButton cmdAnterior;
		private BaseJButton cmdProximo;
		private BaseJButton cmdUltimo;
		private BaseJButton cmdIncluirSistema;
		private BaseJButton cmdExcluirSistema;
		private BaseJButton cmdAlterarSistema;
		
		private BaseJTable tabelaDados; 

		private SistemaSearchVo sistemaSearchVo = new SistemaSearchVo();
		private BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_SISTEMAS);

		private BaseJLabel lblPaginacao;
		private BaseJFormattedTextField txtQtdePaginas;

		private List pesquisaSistemaListenerList = new LinkedList();
		private Sistema sistemaSelecionado = null;
		
		public BaseDispatchCRUDCommand getBaseDispatchCRUDCommand() {
			return baseDispatchCRUDCommand;
		}

		public void setBaseDispatchCRUDCommand(
				BaseDispatchCRUDCommand baseDispatchCRUDCommand) {
			this.baseDispatchCRUDCommand = baseDispatchCRUDCommand;
		}

		public BaseJButton getCmdPesquisa() {
			return cmdPesquisa;
		}

		public void setCmdPesquisa(BaseJButton cmdPesquisa) {
			this.cmdPesquisa = cmdPesquisa;
		}

		public BaseJButton getCmdSair() {
			return cmdSair;
		}

		public void setCmdSair(BaseJButton cmdSair) {
			this.cmdSair = cmdSair;
		}


		public BaseJLabel getLblCodigoSistema() {
			return lblCodigoSistema;
		}

		public void setLblCodigoSistema(BaseJLabel lblCodigoSistema) {
			this.lblCodigoSistema = lblCodigoSistema;
		}

		public BaseJLabel getLblDescricaoSistema() {
			return lblDescricaoSistema;
		}

		public void setLblDescricaoSistema(BaseJLabel lblDescricaoSistema) {
			this.lblDescricaoSistema = lblDescricaoSistema;
		}

		
		public void addPesquisaSistemaListener(PesquisaSistemaListener pesquisaSistemaListener){
			if(pesquisaSistemaListener != null){
				if(!pesquisaSistemaListenerList.contains(pesquisaSistemaListener)){
					pesquisaSistemaListenerList.add(pesquisaSistemaListener);
				}
			}
		}

		public void removePesquisaSistemaListener(PesquisaSistemaListener pesquisaSistemaListener){
			if(pesquisaSistemaListenerList.contains(pesquisaSistemaListener)){
				pesquisaSistemaListenerList.remove(pesquisaSistemaListener);
			}
		}

		
		public SistemaSearchVo getSistemaSearchVo() {
			return sistemaSearchVo;
		}

		public void setSistemaSearchVo(SistemaSearchVo sistemaSearchVo) {
			this.sistemaSearchVo = sistemaSearchVo;
		}

		public BaseJTable getTabelaDados() {
			return tabelaDados;
		}

		public void setTabelaDados(BaseJTable tabelaDados) {
			this.tabelaDados = tabelaDados;
		}

		public BaseJTextBox getTxtCodigoSistema() {
			return txtCodigoSistema;
		}

		public void setTxtCodigoSistema(BaseJTextBox txtCodigoSistema) {
			this.txtCodigoSistema = txtCodigoSistema;
		}

		public BaseJTextBox getTxtDescricaoSistema() {
			return txtDescricaoSistema;
		}

		public void setTxtDescricaoSistema(BaseJTextBox txtDescricaoSistema) {
			this.txtDescricaoSistema = txtDescricaoSistema;
		}

		public FrmPesquisaSistemas(){ 
			super();
			this.inicializarMaximizado = true;
			setFrameIcon(GerenciadorJanelas.ICONE_SISTEMA_PEQUENO);
			this.inicializarComponentes();

			this.addInternalFrameListener(
					new InternalFrameListener(){

						public void internalFrameActivated(InternalFrameEvent e) {
							//ActionEvent evt = new ActionEvent(cmdPesquisa, 0 , "");
							//FrmPesquisaSistemas.this.actionPerformed(evt);
						}

						public void internalFrameClosed(InternalFrameEvent e) {
							// TODO Auto-generated method stub
							
						}

						public void internalFrameClosing(InternalFrameEvent e) {
							// TODO Auto-generated method stub
							
						}

						public void internalFrameDeactivated(InternalFrameEvent e) {
							// TODO Auto-generated method stub
							
						}

						public void internalFrameDeiconified(InternalFrameEvent e) {
							// TODO Auto-generated method stub
							
						}

						public void internalFrameIconified(InternalFrameEvent e) {
							// TODO Auto-generated method stub
							
						}

						public void internalFrameOpened(InternalFrameEvent e) {
							// TODO Auto-generated method stub
							
						}
				
					}
			);
			this.setTitle(RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.titulo.janela"));
		}
		
		public void inicializarComponentes(){
			TableSorter sorter = new TableSorter(new TabelaPesquisaSistemaModel());
	        tabelaDados = new BaseJTable(sorter);             //NEW
	        sorter.setTableHeader(tabelaDados.getTableHeader()); //ADDED THIS
	        //table.setPreferredScrollableViewportSize(new Dimension(500, 70));

			
			GridBagLayout gridBagLayout = new GridBagLayout();
			GridBagConstraints constraints = new GridBagConstraints();
			getContentPane().setLayout(gridBagLayout);
			
			int linhaAtual = 0;


			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblCodigoSistema = new BaseJLabel(RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.codigo.sistema"));
			getContentPane().add(lblCodigoSistema, constraints);

			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.weightx = 10;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			txtCodigoSistema = new BaseJTextBox();
			txtCodigoSistema.setTamanhoMaximo(3);
			getContentPane().add(txtCodigoSistema, constraints);
			
			constraints.gridx = 2;
			constraints.gridy = linhaAtual;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblDescricaoSistema = new BaseJLabel(RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.descricao.sistema"));
			getContentPane().add(lblDescricaoSistema, constraints);

			constraints.gridx = 3;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			txtDescricaoSistema = new BaseJTextBox();
			txtDescricaoSistema.setTamanhoMaximo(30);
			getContentPane().add(txtDescricaoSistema, constraints);


			constraints.gridx = 4;
			constraints.gridy = linhaAtual;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			cmdPesquisa = new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.botao.pesquisa"), GerenciadorJanelas.ICONE_BOTAO_PESQUISA);
			getContentPane().add(cmdPesquisa, constraints);
			cmdPesquisa.addActionListener(this);
			
			
			JPanel painelBotoes = new JPanel();
			painelBotoes.setLayout(new GridBagLayout());
	
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			cmdPrimeiro =  new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.primeiro"), GerenciadorJanelas.ICONE_BOTAO_PRIMEIRO);
			cmdPrimeiro.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.primeiro.tooltiptext"));
			painelBotoes.add(cmdPrimeiro, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 0;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			cmdAnterior =  new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.anterior"), GerenciadorJanelas.ICONE_BOTAO_ANTERIOR);
			cmdAnterior.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.anterior.tooltiptext"));
			painelBotoes.add(cmdAnterior, constraints);

			constraints.gridx = 2;
			constraints.gridy = 0;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			cmdProximo =  new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.proximo"), GerenciadorJanelas.ICONE_BOTAO_PROXIMO);
			cmdProximo.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.proximo.tooltiptext"));
			painelBotoes.add(cmdProximo, constraints);

			constraints.gridx = 3;
			constraints.gridy = 0;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			cmdUltimo =  new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.ultimo"), GerenciadorJanelas.ICONE_BOTAO_ULTIMO);
			cmdUltimo.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.ultimo.tooltiptext"));
			painelBotoes.add(cmdUltimo, constraints);

			
			constraints.gridx = 4;
			constraints.gridy = 0;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			cmdIncluirSistema =  new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.botao.incluir"), GerenciadorJanelas.ICONE_BOTAO_INCLUIR);
			cmdIncluirSistema.setToolTipText(RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.botao.incluir.tooltiptext"));
			painelBotoes.add(cmdIncluirSistema, constraints);

			constraints.gridx = 5;
			constraints.gridy = 0;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			cmdAlterarSistema =  new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.botao.alterar"), GerenciadorJanelas.ICONE_BOTAO_ALTERAR);
			cmdAlterarSistema.setToolTipText(RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.botao.alterar.tooltiptext"));
			painelBotoes.add(cmdAlterarSistema, constraints);
			
			
			constraints.gridx = 6;
			constraints.gridy = 0;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			cmdExcluirSistema =  new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.botao.excluir"), GerenciadorJanelas.ICONE_BOTAO_EXCLUIR);
			cmdExcluirSistema.setToolTipText(RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.botao.excluir.tooltiptext"));
			painelBotoes.add(cmdExcluirSistema, constraints);

			linhaAtual++;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 5;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor= GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			getContentPane().add(painelBotoes, constraints);
			cmdPrimeiro.addActionListener(this);
			cmdAnterior.addActionListener(this);
			cmdProximo.addActionListener(this);
			cmdUltimo.addActionListener(this);
			cmdIncluirSistema.addActionListener(this);
			cmdExcluirSistema.addActionListener(this);
			cmdAlterarSistema.addActionListener(this);
			
			linhaAtual++;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.gridheight = 5;
			constraints.gridwidth = 5;
			constraints.fill = GridBagConstraints.BOTH;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			getContentPane().add(new JScrollPane(tabelaDados), constraints);
			
			
			/**
			 * Quando o usuário seleciona um registro no grid,
			 * notifica o listener sobre a informação selecionada
			 * e fecha a janela, caso o listener retorne 
			 * true no método de confirmação de
			 * fechamento da janela
			 * */
			ListSelectionModel rowSM = tabelaDados.getSelectionModel();
			rowSM.addListSelectionListener(new ListSelectionListener() {
			    public void valueChanged(ListSelectionEvent e) {
			        if (e.getValueIsAdjusting()) return;
			        ListSelectionModel lsm =
			            (ListSelectionModel)e.getSource();
			        if (lsm.isSelectionEmpty()) {
			        } else {
			            int linhaSelecionada = lsm.getMinSelectionIndex();
			           // TabelaPesquisaSistemaModel modelo = (TabelaPesquisaSistemaModel)(FrmPesquisaSistemas.this.tabelaDados.getModel());
				        TabelaPesquisaSistemaModel modelo = (TabelaPesquisaSistemaModel)((TableSorter)(FrmPesquisaSistemas.this.tabelaDados.getModel())).getTableModel();

			            FrmPesquisaSistemas.this.sistemaSelecionado = (Sistema) modelo.getPaginaDados().getListObjects().get(linhaSelecionada);
			            if(FrmPesquisaSistemas.this.pesquisaSistemaListenerList != null){
			            	//Notifica os listeners
			            	Iterator itListeners = FrmPesquisaSistemas.this.pesquisaSistemaListenerList.iterator();
			            	while(itListeners.hasNext()){
			            		((PesquisaSistemaListener)itListeners.next()).sistemaSelecionado(
			            			(Sistema) modelo.getPaginaDados().getListObjects().get(linhaSelecionada)
			            		);
			            	}
			            	
			            	//Caso o primeiro listener tenha requisitado, fecha a janela de pesquisa
			            	if(FrmPesquisaSistemas.this.pesquisaSistemaListenerList.size() > 0){
			            		if(	((PesquisaSistemaListener)(pesquisaSistemaListenerList.get(0))).fecharJanelaPesquisaSistema()){
			            			//GerenciadorJanelas.removePesquisaSistemaListener( ((PesquisaSistemaListener)(pesquisaSistemaListenerList.get(0))));
			            			pesquisaSistemaListenerList.remove(0);
				            		lsm.clearSelection();
			            			FrmPesquisaSistemas.this.fecharJanela();
			            		}
			            	}
			            }
			        }
			    }
			});
			
			
			linhaAtual+=5;

			JPanel painelPaginacao = new JPanel();
			painelPaginacao.setLayout(new GridBagLayout());
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 3;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.weightx = 0;
			constraints.weighty = 0;
			lblPaginacao = new BaseJLabel();
			lblPaginacao.setForeground(Color.BLUE);
			painelPaginacao.add(lblPaginacao, constraints);
			//Campo de texto para número de registros pos página
			NumberFormat nf = NumberFormat.getInstance();
			nf.setParseIntegerOnly(true);
			nf.setMaximumFractionDigits(0);
			this.txtQtdePaginas = new BaseJFormattedTextField(nf);
			this.txtQtdePaginas.setValue(
					new Long(
							this.baseDispatchCRUDCommand.getPagina().getRegistrosPorPagina()
							)
			);
			constraints.gridx = 3;
			constraints.gridy = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 3;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.weightx = 0;
			constraints.weighty = 0;
			painelPaginacao.add(txtQtdePaginas, constraints);
			
			/**
			 * Limita a quantidade de páginas para o valor máximo definido 
			 * na classe página
			 * */
			txtQtdePaginas.setInputVerifier(
					new InputVerifier(){
						public boolean verify(JComponent input) {
							   if (input instanceof JFormattedTextField) {
						           JFormattedTextField ftf = (JFormattedTextField)input;
						           AbstractFormatter formatter = ftf.getFormatter();
						           if (formatter != null) {
						               String text = ftf.getText();
						               try {
						                    Long valor = (Long) formatter.stringToValue(text);
						                    if(valor.longValue() > Pagina.MAXIMO_REGISTROS_POR_PAGINA_DEFAULT){
						                    	return false;
						                    }
						                    return true;
						               } catch (ParseException pe) {
						                    return false;
						                }
						              }
						          }
						          return true;						}
				
					}
			);

			

			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 5;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.weightx = 0;
			constraints.weighty = 0;
			getContentPane().add(painelPaginacao, constraints);

			
			
			
			
			
			linhaAtual++;
			
			constraints.gridx = 4;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.SOUTHEAST;
			constraints.weightx = 0;
			constraints.weighty = 0;
			cmdSair = new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.sistemas.label.botao.sair"), GerenciadorJanelas.ICONE_BOTAO_SAIR_PEQUENO);
			getContentPane().add(this.cmdSair, constraints);
			
			//Associa a ação para sair da tela
			cmdSair.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
						FrmPesquisaSistemas.this.fecharJanela();
				}
			});

			pack();
		}

		
		protected SistemaSearchVo getTelaVo(){
			sistemaSearchVo.getSistema().setCodSistemaSist(this.txtCodigoSistema.getText());
			sistemaSearchVo.getSistema().setDescrSistemaSist(this.txtDescricaoSistema.getText());
			return sistemaSearchVo;
		}
		
		protected void desabilitarBotoes(){
			this.cmdPesquisa.setEnabled(false);
			
		}

		public void atualizarEstado(){
			this.atualizarStatusBotoesControleSeguranca();
		}
		
		protected void atualizarStatusBotoesManutencao(String strMetodo){
			this.cmdPesquisa.setEnabled(true);
			this.atualizarStatusBotoesControleSeguranca();
		}

		protected void atualizarStatusBotoesControleSeguranca()
		{
			if(!Constantes.COD_USR_ADM.equals(GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua())){
				if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_INC_SISTEMA) == null)
				{
						cmdIncluirSistema.setEnabled(false);	
				}
				if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_ALT_SISTEMA) == null)
				{
						cmdAlterarSistema.setEnabled(false);	
				}
				if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_EXC_SISTEMA) == null)
				{
						cmdExcluirSistema.setEnabled(false);	
				}
			}
		}
		
		
		protected boolean atualizarGrid(String strMetodo){
			if( BaseDispatchCRUDCommand.METODO_LISTAR.equals(baseDispatchCRUDCommand.getStrMetodo()) ){
				return true;
			}
			return false;
		}
		
		public void actionPerformed(ActionEvent e) {
			Object args[] = null;
			
			if(e.getSource() == cmdIncluirSistema)
			{
				GerenciadorJanelas.getInstance().getAcaoCadastroSistemas(getParent(), BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO, new Sistema()).actionPerformed(e);
				//this.dispose();
				return;
			}

			if(e.getSource() == cmdExcluirSistema){
				//Exclui o sistema selecionado
				if(sistemaSelecionado != null){
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_EXCLUIR);
					args = new Object[]{sistemaSelecionado};
				}else{
					//Nenhum sistema foi selecionada no grid. Avisa o usuário
					JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado"), RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado.titulo.janela"), JOptionPane.WARNING_MESSAGE);
					return;
				}
			}

			if(e.getSource() == cmdAlterarSistema){
				//Prepara os dados para alteração do sistema selecionado
				if(sistemaSelecionado != null){
					GerenciadorJanelas.getInstance().getAcaoCadastroSistemas(getParent(), BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO, sistemaSelecionado).actionPerformed(e);
					//this.dispose();
					return;
				}else{
					//Nenhum sistema foi selecionada no grid. Avisa o usuário
					JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado"), RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado.titulo.janela"), JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			
			
			
			if(e.getSource() == cmdPesquisa){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
				args = new Object[]{ this.getTelaVo() };
				this.sistemaSelecionado = null;
			}
			
			if(e.getSource() == cmdPrimeiro){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
				args = new Object[]{ this.getTelaVo() };
			}
			
			if(e.getSource() == cmdAnterior){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_ANTERIOR_REGISTRO);
				args = new Object[]{ this.getTelaVo() };
			}

			if(e.getSource() == cmdProximo){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PROXIMO_REGISTRO);
				args = new Object[]{ this.getTelaVo() };
			}

			if(e.getSource() == cmdUltimo){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_ULTIMO_REGISTRO);
				args = new Object[]{ this.getTelaVo() };
			}

			
			
			if(baseDispatchCRUDCommand != null)
			{
				try{
					this.desabilitarBotoes();
					/*Se estiver pesquisando atualiza o número de páginas que o usuário digitou*/
					if(this.atualizarGrid(baseDispatchCRUDCommand.getStrMetodo())){
						this.baseDispatchCRUDCommand.getPagina().setRegistrosPorPagina(
								((Long)txtQtdePaginas.getValue()).longValue()
						);
					}
					Object objRetorno[] = baseDispatchCRUDCommand.executar( args );

					if(this.atualizarGrid(baseDispatchCRUDCommand.getStrMetodo())){
						//Atualiza as informações no grid
						Pagina pagina = (Pagina) objRetorno[0];
						//TabelaPesquisaSistemaModel modelo = (TabelaPesquisaSistemaModel)this.tabelaDados.getModel();
				        TabelaPesquisaSistemaModel modelo = (TabelaPesquisaSistemaModel)((TableSorter)(FrmPesquisaSistemas.this.tabelaDados.getModel())).getTableModel();
						modelo.setPaginaDados(pagina);
						modelo.fireTableDataChanged();
						
						tabelaDados.getColumnModel().setColumnSelectionAllowed(false);
						
						tabelaDados.getColumnModel().getColumn(2).setCellRenderer(
								new DefaultTableCellRenderer()
								{
									public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
									{
										JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value,isSelected, hasFocus,row,column);
										boolean valor = (Boolean)value;
										JCheckBox chk = new JCheckBox();
										chk.setSelected(valor);
										chk.setBackground(lbl.getBackground());
										chk.setForeground(lbl.getForeground());
										return chk;
									} 									
								}
						);		
						//System.out.println("" + baseDispatchCRUDCommand.getPagina());
						//Atualiza as informações sobre a paginação
						Long totalPaginas = new Long(this.baseDispatchCRUDCommand.getPagina().getTotalPaginas());
						Long totalRegistros = new Long(this.baseDispatchCRUDCommand.getPagina().getTotalRegistros());
						Long paginaAtual = new Long(this.baseDispatchCRUDCommand.getPagina().getPaginaAtual());
						this.lblPaginacao.setText(
								RecursosUtil.getInstance().getResource("key.msg.paginacao", new Object[]{paginaAtual, totalPaginas, totalRegistros} )
						);
						this.txtQtdePaginas.setValue(
								new Long(
										this.baseDispatchCRUDCommand.getPagina().getRegistrosPorPagina()
										)
						);

					}
					
					if( this.exibirMsgConfirmacao(baseDispatchCRUDCommand.getStrMetodo()))
					{
						JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.realizada.com.sucesso"), RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.realizada.com.sucesso.titulo.janela"), JOptionPane.INFORMATION_MESSAGE);
						//Realiza a pesquisa para popular o grid
						baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
						baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
						args = new Object[]{ this.getTelaVo() };
						objRetorno = baseDispatchCRUDCommand.executar( args );
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
		
		public void clearListeners(){
			try{
				this.pesquisaSistemaListenerList.clear();
			}catch(UnsupportedOperationException e){
				this.pesquisaSistemaListenerList = new LinkedList();
			}
		}
			
		public void fecharJanela(){
			clearListeners();
    		this.tabelaDados.getSelectionModel().clearSelection();
			super.fecharJanela();
		}

		protected boolean exibirMsgConfirmacao(String strMetodo){
			if( 
				BaseDispatchCRUDCommand.METODO_EXCLUIR.equals(baseDispatchCRUDCommand.getStrMetodo()) 	
			){
				return true;
			}
			return false;
		}
		
		
}
