package dnsec.modulos.cadastros.funcao.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sun.swing.examples.TableSorter;

import dnsec.modulos.cadastros.funcao.model.TabelaPesquisaFuncaoModel;
import dnsec.modulos.cadastros.funcao.vo.FuncaoSearchVo;
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
import dnsec.shared.swing.base.BaseJTable;
import dnsec.shared.swing.base.BaseJTextBox;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.Pagina;
import dnsec.shared.util.RecursosUtil;

public class FrmPesquisaFuncoes extends BaseJInternalFrame implements ActionListener, PesquisaSistemaListener{
	
		private BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_FUNCOES);

		private BaseJLabel lblCodigoFuncao;
		private BaseJFormattedTextField txtCodigoFuncao;
		private BaseJLabel lblNomeFuncao;
		private BaseJTextBox txtNomeFuncao;
		private BaseJButton cmdPesquisa;
		private BaseJLabel lblSistemaSelecionado;
		
		private BaseJButton cmdSair;
		private BaseJButton cmdPesquisaSistema;
		private BaseJButton cmdCancelarPesquisaSistema;
		private BaseJButton cmdPrimeiro;
		private BaseJButton cmdAnterior;
		private BaseJButton cmdProximo;
		private BaseJButton cmdUltimo;
		
		/**
		 * Botões para chamar a tela de manutenção das funções
		 * */
		private BaseJButton cmdIncluirFuncao;
		private BaseJButton cmdExcluirFuncao;
		private BaseJButton cmdAlterarFuncao;
		
		/**
		 * Indica se os botões de manutenção de funções devem ou não ser exibidos
		 * */
		private boolean exibirBotoesManutencao = true;
		
		

		private BaseJLabel lblPaginacao = new BaseJLabel();
		private BaseJFormattedTextField txtQtdePaginas = null;

		private BaseJTable tabelaDados; //= new BaseJTable(new TabelaPesquisaFuncaoModel());

		/**Lista de listeners que serão notificados quando alguma função for selecionada*/
		private List pesquisaFuncoesListenerList = new LinkedList();

		/**Função atualmente selecionada no grid*/
		private Funcao funcaoSelecionada;
		
		/**
		 * Vo para pesquisa
		 * Quando o usuário clica no botão de pesquisa, este vo é preenchido 
		 * com os dados da tela e enviado como filtro para a função de pesquisa.
		 */
		private FuncaoSearchVo funcaoSearchVo = new FuncaoSearchVo();
		
		
		/**
		 * Inicializa a tela e chama a função de pesquisa
		 * (Retornando sempre os primeiros registros) 
		 * */
		public FrmPesquisaFuncoes(){
			super();
			this.inicializarMaximizado = true;
			setFrameIcon(GerenciadorJanelas.ICONE_FUNCAO_PEQUENO);
			this.inicializarComponentes();

			this.addInternalFrameListener(
					new InternalFrameListener(){

						public void internalFrameActivated(InternalFrameEvent e) {
							//ActionEvent evt = new ActionEvent(cmdPesquisa, 0 , "");
							//FrmPesquisaFuncoes.this.actionPerformed(evt);
							/*Exibe ou esconde os botões de manutenção de acordo com 
							 * a opção feita quando o form foi criado
							 * */
							FrmPesquisaFuncoes.this.cmdIncluirFuncao.setVisible
							(
									FrmPesquisaFuncoes.this.isExibirBotoesManutencao()		
							);
							FrmPesquisaFuncoes.this.cmdExcluirFuncao.setVisible
							(
									FrmPesquisaFuncoes.this.isExibirBotoesManutencao()		
							);
							FrmPesquisaFuncoes.this.cmdAlterarFuncao.setVisible
							(
									FrmPesquisaFuncoes.this.isExibirBotoesManutencao()		
							);
						}

						public void internalFrameClosed(InternalFrameEvent e) {
						}

						public void internalFrameClosing(InternalFrameEvent e) {
						}

						public void internalFrameDeactivated(InternalFrameEvent e) {
						}

						public void internalFrameDeiconified(InternalFrameEvent e) {
						}

						public void internalFrameIconified(InternalFrameEvent e) {
						}

						public void internalFrameOpened(InternalFrameEvent e) {
						}
					}
			);
			this.setTitle(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.titulo.janela"));
		}
		
		/**
		 * Inicializa e posiciona os componentes na tela
		 * */
		public void inicializarComponentes(){
			TableSorter sorter = new TableSorter(new TabelaPesquisaFuncaoModel()); //ADDED THIS
	        //JTable table = new JTable(new MyTableModel());         //OLD
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
			lblCodigoFuncao = new BaseJLabel(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.codigo.funcao"));
			getContentPane().add(lblCodigoFuncao, constraints);

			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.weightx = 10;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			final NumberFormat nf = NumberFormat.getIntegerInstance();
			txtCodigoFuncao = new BaseJFormattedTextField();
			txtCodigoFuncao.setTamanhoMaximo(5);

			txtCodigoFuncao.setInputVerifier(
					new InputVerifier() {
					     public boolean verify(JComponent input) {
					         if (input instanceof JFormattedTextField) {
					             JFormattedTextField ftf = (JFormattedTextField)input;
					             Format formatter = nf;
					             if (formatter != null) {
					                 String text = ftf.getText();
					             
					                 try {
				                	    if("".equals(text.trim()))
						                 {
						                	ftf.setText(""); 
				                	    	return true;
						                 }
					                	 formatter.parseObject(text);
					                      return true;
					                  } catch (ParseException pe) {
					                      return false;
					                  }
					              }
					          }
					          return true;
					      }
					     /* public boolean shouldYieldFocus(JComponent input) {
					          return verify(input);
					      }*/
					  }					
			);
			nf.setGroupingUsed(false);
			getContentPane().add(txtCodigoFuncao, constraints);
			
			constraints.gridx = 2;
			constraints.gridy = linhaAtual;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblNomeFuncao = new BaseJLabel(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.nome.funcao"));
			getContentPane().add(lblNomeFuncao, constraints);

			constraints.gridx = 3;
			constraints.gridy = linhaAtual;
			constraints.weightx = 100;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			txtNomeFuncao = new BaseJTextBox();
			txtNomeFuncao.setTamanhoMaximo(30);
			getContentPane().add(txtNomeFuncao, constraints);


			constraints.gridx = 4;
			constraints.gridy = linhaAtual;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			cmdPesquisa = new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.botao.pesquisa"), GerenciadorJanelas.ICONE_BOTAO_PESQUISA);
			getContentPane().add(cmdPesquisa, constraints);
			cmdPesquisa.addActionListener(this);
			
			//Painel para selecionar o sistema
			linhaAtual++;
			JPanel painelSelecaoSistema = new JPanel();
			painelSelecaoSistema.setLayout(new FlowLayout( FlowLayout.LEFT ));
			lblSistemaSelecionado = new BaseJLabel(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.pesquisa.sistema"));
			painelSelecaoSistema.add(lblSistemaSelecionado);
			
			/*Associa o objeto como sendo listeners para a janela de pesquisa de sistemas*/
			cmdPesquisaSistema = new BaseJButton(RecursosUtil.getInstance().getResource("key.menu.pesquisa.sistemas"), GerenciadorJanelas.ICONE_SISTEMA_PEQUENO);
			cmdPesquisaSistema.addActionListener(
					new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							List listenersPesquisaSistema = new LinkedList();
							listenersPesquisaSistema.add(FrmPesquisaFuncoes.this);
							GerenciadorJanelas.getInstance().getAcaoPesquisaSistemas(getParent(), listenersPesquisaSistema, true, false).actionPerformed(e);
						}
					}
			);
			
			
			cmdCancelarPesquisaSistema = new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.botao.cancelar.pesquisa.sistema"), GerenciadorJanelas.ICONE_BOTAO_CANCELAR);
			painelSelecaoSistema.add(cmdPesquisaSistema);
			painelSelecaoSistema.add(cmdCancelarPesquisaSistema);
			cmdCancelarPesquisaSistema.addActionListener(this);
			
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 5;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			getContentPane().add(painelSelecaoSistema, constraints);

			
			//Botões para paginação e para
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
			cmdProximo =  new BaseJButton(GerenciadorJanelas.ICONE_BOTAO_PROXIMO);
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

			//Botões para manutenção e integração com a tela de cadastro de funções
			constraints.gridx = 4;
			constraints.gridy = 0;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;

			//constraints.anchor = GridBagConstraints.NORTHWEST;
			cmdIncluirFuncao = new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.botao.incluir"), GerenciadorJanelas.ICONE_BOTAO_INCLUIR);
			cmdIncluirFuncao.addActionListener(this);
			painelBotoes.add(cmdIncluirFuncao, constraints);
						
			constraints.gridx = 5;
			constraints.gridy = 0;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			cmdAlterarFuncao = new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.botao.alterar"), GerenciadorJanelas.ICONE_BOTAO_ALTERAR);
			cmdAlterarFuncao.addActionListener(this);
			painelBotoes.add(cmdAlterarFuncao, constraints);

			constraints.gridx = 6;
			constraints.gridy = 0;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			//constraints.anchor = GridBagConstraints.NORTHWEST;
			cmdExcluirFuncao = new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.botao.excluir"), GerenciadorJanelas.ICONE_BOTAO_EXCLUIR);
			cmdExcluirFuncao.addActionListener(this);
			painelBotoes.add(cmdExcluirFuncao, constraints);
		
			//Botões para manutenção
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

			

			
			//Tabela de dados
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
			    	FrmPesquisaFuncoes.this.funcaoSelecionada = null;
			    	if (e.getValueIsAdjusting()) return;
			        ListSelectionModel lsm =
			            (ListSelectionModel)e.getSource();
			        if (lsm.isSelectionEmpty()) {
			        } else {
			            int linhaSelecionada = lsm.getMinSelectionIndex();
			            //TabelaPesquisaFuncaoModel modelo = (TabelaPesquisaFuncaoModel)(FrmPesquisaFuncoes.this.tabelaDados.getModel());
			            TabelaPesquisaFuncaoModel modelo = (TabelaPesquisaFuncaoModel)((TableSorter)(FrmPesquisaFuncoes.this.tabelaDados.getModel())).getTableModel();

			            //Atualiza a função selecionada atualmente (para os procedimentos de alteração e exclusão de dados)
			            FrmPesquisaFuncoes.this.funcaoSelecionada = (Funcao) modelo.getPaginaDados().getListObjects().get(linhaSelecionada);

			            if(FrmPesquisaFuncoes.this.pesquisaFuncoesListenerList != null){
			            	//Notifica os listeners
			            	Iterator itListeners = FrmPesquisaFuncoes.this.pesquisaFuncoesListenerList.iterator();
			            	while(itListeners.hasNext()){
			            		((PesquisaFuncaoListener)itListeners.next()).funcaoSelecionada(
			            			(Funcao) modelo.getPaginaDados().getListObjects().get(linhaSelecionada)
			            		);
			            	}
			            	
			            	
			            	if(FrmPesquisaFuncoes.this.pesquisaFuncoesListenerList.size() > 0){
			            		if(	((PesquisaFuncaoListener)(pesquisaFuncoesListenerList.get(0))).fecharJanelaPesquisaFuncao()){
			            			if(! (pesquisaFuncoesListenerList.get(0) instanceof FrmFuncoes) ){
			            				FrmPesquisaFuncoes.this.fecharJanela();
			            			}else{
			            				FrmPesquisaFuncoes.this.moveToBack();
			            			}
				            		lsm.clearSelection();
			            			pesquisaFuncoesListenerList.remove(0);
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
			lblPaginacao.setForeground(Color.BLUE);
			painelPaginacao.add(lblPaginacao, constraints);

			//Campo de texto para número de registros pos página
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
			cmdSair = new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.botao.sair"), GerenciadorJanelas.ICONE_BOTAO_SAIR_PEQUENO);
			getContentPane().add(this.cmdSair, constraints);
			
			//Associa a ação para sair da tela
			cmdSair.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
						FrmPesquisaFuncoes.this.fecharJanela();
				}
			});

			pack();

			
		}

		public void actionPerformed(ActionEvent e) {
			Object args[] = null;
			
			if(e.getSource() == cmdPesquisa){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
				args = new Object[]{ this.getTelaVo() };
				this.funcaoSelecionada = null;
			}
			
			if(e.getSource() == cmdPrimeiro){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
				args = new Object[]{ this.getTelaVo() };
				this.funcaoSelecionada = null;
			}
			
			if(e.getSource() == cmdAnterior){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_ANTERIOR_REGISTRO);
				args = new Object[]{ this.getTelaVo() };
				this.funcaoSelecionada = null;
			}

			if(e.getSource() == cmdProximo){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PROXIMO_REGISTRO);
				args = new Object[]{ this.getTelaVo() };
				this.funcaoSelecionada = null;
			}

			if(e.getSource() == cmdUltimo){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_ULTIMO_REGISTRO);
				args = new Object[]{ this.getTelaVo() };
				this.funcaoSelecionada = null;
			}

			if(e.getSource() == cmdCancelarPesquisaSistema){
				//Cancela o sistema selecionado atualmente
				this.funcaoSearchVo.getFuncao().setSistema(null);
				this.lblSistemaSelecionado.setText(
						RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.pesquisa.sistema") + " "
				);
				return;
			}
			
			if(e.getSource() == cmdExcluirFuncao){
				//Exclui a função selecionada no grid
				if(funcaoSelecionada != null){
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_EXCLUIR);
					args = new Object[]{funcaoSelecionada};
				}else{
					//Nenhuma função foi selecionada no grid. Avisa o usuário
					JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado"), RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado.titulo.janela"), JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			
			if(e.getSource() == cmdIncluirFuncao){
				/*Chama a tela de cadastro de funções e fecha a tela atual*/
				GerenciadorJanelas.getInstance().getAcaoJanelaCadastroFuncoes(getParent(), BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO, new Funcao()).actionPerformed(e);
				//this.dispose();
				return;
			}

			if(e.getSource() == cmdAlterarFuncao){
				if(funcaoSelecionada != null){
					/*Chama a tela de cadastro de funções para alterar a funcao e fecha a tela atual*/
					GerenciadorJanelas.getInstance().getAcaoJanelaCadastroFuncoes(getParent(), BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO, funcaoSelecionada).actionPerformed(e);
					//this.dispose();
				}else{
					//Nenhuma função foi selecionada no grid. Avisa o usuário
					JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado"), RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado.titulo.janela"), JOptionPane.WARNING_MESSAGE);
					return;
				}

				return;
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

					
					if( this.exibirMsgConfirmacao(baseDispatchCRUDCommand.getStrMetodo()))
					{
						JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.realizada.com.sucesso"), RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.realizada.com.sucesso.titulo.janela"), JOptionPane.INFORMATION_MESSAGE);
						//Realiza a pesquisa para popular o grid
						baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
						baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
						args = new Object[]{ this.getTelaVo() };
						objRetorno = baseDispatchCRUDCommand.executar( args );
					}

					
					if(this.atualizarGrid(baseDispatchCRUDCommand.getStrMetodo())){
						//Atualiza as informações no grid
						Pagina pagina = (Pagina) objRetorno[0];
						//TabelaPesquisaFuncaoModel modelo = (TabelaPesquisaFuncaoModel)this.tabelaDados.getModel();
			            TabelaPesquisaFuncaoModel modelo = (TabelaPesquisaFuncaoModel)((TableSorter)(FrmPesquisaFuncoes.this.tabelaDados.getModel())).getTableModel();
						modelo.setPaginaDados(pagina);
						modelo.fireTableDataChanged();
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

		
		/******************************************Listener para a tela de seleção de sistemas****************************************************************/

		/**
		 * Seta o sistema selecionado atualmente
		 * */
		public void sistemaSelecionado(Sistema sistema) {
			funcaoSearchVo.getFuncao().setSistema(sistema);
			if(sistema != null){
				this.lblSistemaSelecionado.setText(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.pesquisa.sistema") + " " + sistema.getCodSistemaSist());
			}
		}

		/**Informa para a janela de seleção de sistemas que quando um sistema for selecionado a janela deve ser fechada*/
		public boolean fecharJanelaPesquisaSistema() {
			return true;
		}
		
		/*****************************************************************************************************************************************************/
		
		/**
		 * Adiciona um listener para ser notificado sobre a pesquisa
		 * @param pesquisaFuncaoListener
		 * */
		public void addPesquisaFuncaoListener(PesquisaFuncaoListener pesquisaFuncaoListener){
			if(pesquisaFuncaoListener != null){
				if(!pesquisaFuncoesListenerList.contains(pesquisaFuncaoListener)){
					pesquisaFuncoesListenerList.add(pesquisaFuncaoListener);
				}
			}
		}

		/**Remove um listener
		 * @param pesquisaFuncaoListener
		 * */
		public void removePesquisaSistemaListener(PesquisaFuncaoListener pesquisaFuncaoListener){
			if(pesquisaFuncoesListenerList.contains(pesquisaFuncaoListener)){
				pesquisaFuncoesListenerList.remove(pesquisaFuncaoListener);
			}
		}

		
		/**Limpa os listeners confirurados na janeça*/
		public void clearListeners(){
			try{
				this.pesquisaFuncoesListenerList.clear();
			}catch(UnsupportedOperationException e){
				this.pesquisaFuncoesListenerList = new LinkedList();
			}
		}
			
		/**Limpa os listeners configurados na tela de pesquisa e fecha a janela*/
		public void fecharJanela(){
			clearListeners();
			//GerenciadorJanelas.removePesquisaSistemaListener(this);
    		this.tabelaDados.getSelectionModel().clearSelection();
			super.fecharJanela();
		}

		
		/**Desabiltia todos os botões da tela.*/
		protected void desabilitarBotoes(){
			this.cmdPesquisa.setEnabled(false);
			this.cmdExcluirFuncao.setEnabled(false);
			this.cmdIncluirFuncao.setEnabled(false);
			this.cmdAlterarFuncao.setEnabled(false);
		}

		/**
		 * Atualiza o status dos botões da tela de acordo com o estado da operação atual
		 * @param strMetodo -> método para comparação  
		 * */
		protected void atualizarStatusBotoesManutencao(String strMetodo){
			this.cmdPesquisa.setEnabled(true);
			this.cmdExcluirFuncao.setEnabled(true);
			this.cmdAlterarFuncao.setEnabled(true);
			this.cmdIncluirFuncao.setEnabled(true);
			this.atualizarStatusBotoesControleSeguranca();
		}

		public void atualizarEstado(){
			atualizarStatusBotoesControleSeguranca();
		}
		
		protected void atualizarStatusBotoesControleSeguranca()
		{
			if(!Constantes.COD_USR_ADM.equals(GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua())){
				if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_INC_FUNCAO) == null)
				{
						cmdIncluirFuncao.setEnabled(false);	
				}
				if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_ALT_FUNCAO) == null)
				{
						cmdAlterarFuncao.setEnabled(false);	
				}
				if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_EXC_FUNCAO) == null)
				{
						cmdExcluirFuncao.setEnabled(false);	
				}
			}
		}

		
		/**
		 * Retorna quais métodos do command devem exibir a mensagem indicando que a operação ocorreu com sucesso
		 * @return boolean indicando se a mensagem de confirmação deve ou não ser exibida
		 * */
		protected boolean exibirMsgConfirmacao(String strMetodo){
			if( 
				BaseDispatchCRUDCommand.METODO_EXCLUIR.equals(baseDispatchCRUDCommand.getStrMetodo()) 	
			){
				return true;
			}
			return false;
		}

		/**
		 * Indica quais métodos no commando devem atualizar as informações na tela do usuário
		 * @return boolean indicando se as informações no grid da tela devem ou não se atualizadas
		 * */
		protected boolean atualizarGrid(String strMetodo){
			if( BaseDispatchCRUDCommand.METODO_LISTAR.equals(baseDispatchCRUDCommand.getStrMetodo()) 
													||
				BaseDispatchCRUDCommand.METODO_EXCLUIR.equals(baseDispatchCRUDCommand.getStrMetodo()) 	
			){
				return true;
			}
			return false;
		}
		
		/**
		 * Retorna os dados da tela encapsulados num vo.
		 * OBS: O sistema selecionado é configurado pela função
		 * sistemaSelecionado (funmção do listener de pesquisa
		 * de sistemas).
		 * */
		protected FuncaoSearchVo getTelaVo(){
			funcaoSearchVo.getFuncao().getCompId().setCodFuncaoFunc( null );
			if(this.txtCodigoFuncao.getText() != null && !"".equals(this.txtCodigoFuncao.getText())){
				try{
					funcaoSearchVo.getFuncao().getCompId().setCodFuncaoFunc( new Long( this.txtCodigoFuncao.getText()).longValue() );
				}catch(NumberFormatException e){
					e.printStackTrace();
				}
			}
			funcaoSearchVo.getFuncao().setNomeFunc(this.txtNomeFuncao.getText());
			return funcaoSearchVo;
		}

		public boolean isExibirBotoesManutencao() {
			return exibirBotoesManutencao;
		}

		public void setExibirBotoesManutencao(boolean exibirBotoesManutencao) {
			this.exibirBotoesManutencao = exibirBotoesManutencao;
		}

		public void removePesquisaFuncaoListener(PesquisaFuncaoListener pesquisaFuncaoListener){
			if(pesquisaFuncoesListenerList.contains(pesquisaFuncaoListener)){
				pesquisaFuncoesListenerList.remove(pesquisaFuncaoListener);
			}
		}

}
