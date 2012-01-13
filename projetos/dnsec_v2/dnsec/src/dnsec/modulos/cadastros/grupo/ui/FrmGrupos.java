package dnsec.modulos.cadastros.grupo.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import com.sun.swing.examples.TableSorter;

import dnsec.modulos.cadastros.grupo.model.TabelaGrupoModel;
import dnsec.modulos.cadastros.grupo.vo.GrupoSearchVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Grupo;
import dnsec.shared.factory.CommandFactory;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.swing.base.BaseJButton;
import dnsec.shared.swing.base.BaseJFormattedTextField;
import dnsec.shared.swing.base.BaseJInternalFrame;
import dnsec.shared.swing.base.BaseJLabel;
import dnsec.shared.swing.base.BaseJTable;
import dnsec.shared.swing.base.BaseJTextArea;
import dnsec.shared.swing.base.BaseJTextBox;
import dnsec.shared.ui.JPanelBotoesManutencao;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.Pagina;
import dnsec.shared.util.RecursosUtil;
import dnsec.shared.util.StringUtil;

public class FrmGrupos extends BaseJInternalFrame implements ActionListener{
    	static Logger logger = Logger.getLogger(FrmGrupos.class.getName());

    	private BaseJLabel lblCodigoGrupo;
		private BaseJLabel lblDescricaoGrupo;
		private BaseJTextBox txtCodigoGrupo;
		private BaseJTextArea txtDescricaoGrupo;
		private JPanelBotoesManutencao jPanelBotoesManutencao = new JPanelBotoesManutencao(this);
		private BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_GRUPOS);
		
		private BaseJButton cmdSair = new BaseJButton(RecursosUtil.getInstance().getResource("key.cadastro.grupos.label.botao.sair"), GerenciadorJanelas.ICONE_BOTAO_SAIR_PEQUENO);
		private BaseJLabel lblPaginacao = new BaseJLabel();
		private BaseJFormattedTextField txtQtdePaginas = null;
		
		private BaseJTable tabelaDados; //new BaseJTable(new TabelaGrupoModel());
		private Grupo grupoAtual = new Grupo();
		
		public FrmGrupos(){
			super();
			inicializarMaximizado = true;
			setFrameIcon(new ImageIcon(getClass().getClassLoader().getResource("config/resource/grupo_pequeno.jpg")));
			this.inicializarComponentes();
			this.setTitle(RecursosUtil.getInstance().getResource("key.cadastro.grupos.titulo.janela"));
		}

		public void atualizarEstado(){
			ActionEvent evt = new ActionEvent(FrmGrupos.this.jPanelBotoesManutencao.getCmdPesquisa(), 0 , "");
			FrmGrupos.this.actionPerformed(evt);
			atualizarStatusBotoesControleSeguranca();			
		}

		protected void atualizarStatusBotoesControleSeguranca()
		{
			if(!Constantes.COD_USR_ADM.equals(GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua())){
				if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_INC_GRUPO) == null)
				{
						jPanelBotoesManutencao.getCmdIncluir().setEnabled(false);	
				}
				if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_ALT_GRUPO) == null)
				{
					jPanelBotoesManutencao.getCmdAlterar().setEnabled(false);	
				}
				if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_ALT_GRUPO) == null)
				{
					jPanelBotoesManutencao.getCmdExcluir().setEnabled(false);	
				}
			}
		}
		
		
		public void inicializarComponentes(){
			TableSorter sorter = new TableSorter(new TabelaGrupoModel());
			tabelaDados = new BaseJTable(sorter); // NEW
			sorter.setTableHeader(tabelaDados.getTableHeader()); // ADDED THIS
			// table.setPreferredScrollableViewportSize(new Dimension(500, 70));
			
			GridBagLayout gridBagLayout = new GridBagLayout();
			GridBagConstraints constraints = new GridBagConstraints();
			//getContentPane().setLayout(gridBagLayout);
			
			JPanel painelComponentes = new JPanel();
			painelComponentes.setLayout(gridBagLayout);
			
			int linhaAtual = 0;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.weightx = 0;
			constraints.weighty = 100;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.NORTHWEST;
			constraints.insets = new Insets(2, 2, 2, 2);
			lblCodigoGrupo = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.grupos.label.codigo.grupo"));
			//getContentPane().add(lblCodigoGrupo, constraints);
			painelComponentes.add(lblCodigoGrupo, constraints);
			

			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.NORTH;
			txtCodigoGrupo = new BaseJTextBox();
			txtCodigoGrupo.setTamanhoMaximo(10);
			//getContentPane().add(txtCodigoGrupo, constraints);
			painelComponentes.add(txtCodigoGrupo, constraints);
			
			
			linhaAtual++;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.weightx = 0;
			constraints.weighty = 100;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.NORTHWEST;
			lblDescricaoGrupo = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.grupos.label.codigo.descricao"));
			//getContentPane().add(lblDescricaoGrupo, constraints);
			painelComponentes.add(lblDescricaoGrupo, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 3;
			constraints.gridwidth = 1;
			constraints.weightx = 100;
			constraints.weighty = 100;
			constraints.fill = GridBagConstraints.BOTH;
			constraints.anchor = GridBagConstraints.NORTH;
			txtDescricaoGrupo = new BaseJTextArea(5,20);
			txtDescricaoGrupo.setTamanhoMaximo(255);
			JScrollPane paneDescricao = new JScrollPane(txtDescricaoGrupo);
			//getContentPane().add(paneDescricao, constraints);
			painelComponentes.add(paneDescricao, constraints);
			
			linhaAtual += 3;
			
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 10;
			constraints.gridwidth = 2;
			constraints.weightx =100;
			constraints.weighty = 0;
			constraints.fill = GridBagConstraints.BOTH;
			constraints.anchor = GridBagConstraints.NORTH;
			JScrollPane jScrollPane = new JScrollPane(this.tabelaDados);
			jScrollPane.setMinimumSize(new Dimension(getMinimumSize().width ,150));
			//getContentPane().add(jScrollPane, constraints);
			painelComponentes.add(jScrollPane, constraints);
			
			linhaAtual+=10;
			
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
			
			//txtQtdePaginas.setMinimumSize(new Dimension(200, txtQtdePaginas.getSize().height));
			
			
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
			
			/*constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.NORTHWEST;
			constraints.weightx = 0;
			constraints.weighty = 100;
			//getContentPane().add(lblPaginacao, constraints);*/

			
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 2;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.CENTER;
			constraints.weightx = 0;
			constraints.weighty = 100;
			txtQtdePaginas.setColumns(5);
			JPanel painelPaginacao = new JPanel();
			painelPaginacao.add(lblPaginacao);
			painelPaginacao.add(txtQtdePaginas);

			painelComponentes.add(painelPaginacao, constraints);			

			
			linhaAtual += 1;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 2;
			constraints.gridwidth = 2;
			constraints.fill = GridBagConstraints.BOTH;
			constraints.anchor = GridBagConstraints.CENTER;
			constraints.weightx =100;
			constraints.weighty = 100;
			//getContentPane().add(jPanelBotoesManutencao, constraints);
			painelComponentes.add(jPanelBotoesManutencao, constraints);
			
			linhaAtual+=2;
			
			
			//Associando cor ao fundo do label de paginação
			lblPaginacao.setForeground(Color.BLUE);
			
			/**
			 * Quando o usuário seleciona um registro no grid,
			 * atualiza as informações na tela com os dados 
			 * selecionados no grid e desabilita o botão
			 * que contém o código do grupo.
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
			            //TabelaGrupoModel modelo = (TabelaGrupoModel)FrmGrupos.this.tabelaDados.getModel();
			            TabelaGrupoModel modelo = (TabelaGrupoModel)((TableSorter)FrmGrupos.this.tabelaDados.getModel()).getTableModel();
			            
			            Grupo grupoSelecionado = (Grupo) modelo.getPaginaDados().getListObjects().get(linhaSelecionada);
			            grupoSelecionado.setFuncoes(Collections.EMPTY_SET);
			            /*FrmGrupos.this.setGrupoTelaVo(
			            		grupoSelecionado, Collections.EMPTY_LIST
			            );
			            //FrmGrupos.this.txtCodigoGrupo.setEnabled(false);*/
			            grupoAtual = grupoSelecionado;
			        }
			    }
			});
			
			
			
			gridBagLayout = new GridBagLayout();
			linhaAtual = 0;
			constraints.gridx = 0;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 5;
			constraints.gridwidth = 5;
			constraints.fill = GridBagConstraints.BOTH;
			constraints.anchor = GridBagConstraints.NORTHEAST;
			constraints.weightx = 100;
			constraints.weighty = 100;
			
			getContentPane().setLayout(gridBagLayout);
			getContentPane().add(painelComponentes, constraints);


			linhaAtual = 5;
			constraints.gridx = 4;
			constraints.gridy = linhaAtual;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.SOUTHEAST;
			constraints.weightx = 100;
			constraints.weighty = 100;
			getContentPane().add(this.cmdSair, constraints);

			//Associa a ação para sair da tela
			cmdSair.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
						FrmGrupos.this.fecharJanela();
				}
			});
			
			
			pack();
		}

		protected Grupo getGrupoTelaVo(){
			//Grupo grupo = new Grupo();
			grupoAtual.setCodGrupoGrup(StringUtil.NullToStrUpperTrim(this.txtCodigoGrupo.getText()));
			grupoAtual.setDescrGrupoGrup(StringUtil.NullToStrUpperTrim(this.txtDescricaoGrupo.getText()));
			return grupoAtual;
		}
		
		protected void setGrupoTelaVo(Grupo grupo, List listaFuncoesDisponiveis){
			grupoAtual = grupo;
			this.txtCodigoGrupo.setText(StringUtil.NullToStrUpperTrim(grupoAtual.getCodGrupoGrup()));
			this.txtDescricaoGrupo.setText(StringUtil.NullToStrUpperTrim(grupoAtual.getDescrGrupoGrup()));
			/*if(grupoAtual.getFuncoes() != null){
				this.modeloFuncoesRelacionadas.setListaFuncoes(new LinkedList(grupoAtual.getFuncoes()));
			}else{
				this.modeloFuncoesRelacionadas.setListaFuncoes(new LinkedList());
			}
			this.modeloFuncoesDisponiveis.setListaFuncoes(listaFuncoesDisponiveis);*/
		}
		
		public void actionPerformed(ActionEvent e) {
			Object args[] = null;
		
			if(e.getSource() == jPanelBotoesManutencao.getCmdIncluir()){
				if(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.incluir").equals(jPanelBotoesManutencao.getCmdIncluir().getText())){
					jPanelBotoesManutencao.getCmdIncluir().setText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.cancelar"));
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO);
		            FrmGrupos.this.txtCodigoGrupo.setEnabled(true);
				}else{
					jPanelBotoesManutencao.getCmdIncluir().setText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.incluir"));
					//Simula a ação de pesquisa
					this.setGrupoTelaVo(new Grupo(), Collections.EMPTY_LIST);
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
					baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
					args = new Object[]{ new GrupoSearchVo(this.getGrupoTelaVo()) };
					FrmGrupos.this.txtCodigoGrupo.setEnabled(true);
				}	
			}
			
			if(e.getSource() == jPanelBotoesManutencao.getCmdExcluir()){
				/*Se nenhum grupo foi selecionado avisa o usuário*/
				if(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.alterar").equals(jPanelBotoesManutencao.getCmdAlterar().getText())){
					/*Se nenhum grupo foi selecionado avisa o usuário*/
					if(grupoAtual == null || grupoAtual.getCodGrupoGrup() == null || "".equals(grupoAtual.getCodGrupoGrup())){
						JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado"), RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado.titulo.janela"), JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_EXCLUIR);
				args = new Object[]{ this.grupoAtual };
			}
			
			if(e.getSource() == jPanelBotoesManutencao.getCmdAlterar()){
				if(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.alterar").equals(jPanelBotoesManutencao.getCmdAlterar().getText())){
					/*Se nenhum grupo foi selecionado avisa o usuário*/
					if(grupoAtual == null || grupoAtual.getCodGrupoGrup() == null || "".equals(grupoAtual.getCodGrupoGrup())){
						JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado"), RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado.titulo.janela"), JOptionPane.WARNING_MESSAGE);
						return;
					}
					jPanelBotoesManutencao.getCmdAlterar().setText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.cancelar"));
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
					args = new Object[]{ this.grupoAtual, false, false };
					this.setGrupoTelaVo(grupoAtual, Collections.EMPTY_LIST);
					txtCodigoGrupo.setEnabled(false);
				}else{
					jPanelBotoesManutencao.getCmdAlterar().setText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.alterar"));
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
					baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
					this.setGrupoTelaVo(new Grupo(), Collections.EMPTY_LIST);
					args = new Object[]{ new GrupoSearchVo(this.getGrupoTelaVo()) };
					FrmGrupos.this.txtCodigoGrupo.setEnabled(true);
				}	
			}
			
			if(e.getSource() == jPanelBotoesManutencao.getCmdGravar()){
				if((BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo()))){
					baseDispatchCRUDCommand.setMetodoAnterior(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO);
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO);
					args = new Object[]{ this.getGrupoTelaVo() };
				}else{
					if( BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())){
						baseDispatchCRUDCommand.setMetodoAnterior(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
						baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO);
						args = new Object[]{ this.getGrupoTelaVo(), Collections.EMPTY_LIST, false };
					}else{
						//Botão não deveria estar habilitado!
					}
				}
			}

			if(e.getSource() == jPanelBotoesManutencao.getCmdPrimeiro()){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
				args = new Object[]{ new GrupoSearchVo(this.getGrupoTelaVo()) };
			}
			
			if(e.getSource() == jPanelBotoesManutencao.getCmdPrimeiro()){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
				args = new Object[]{ new GrupoSearchVo(this.getGrupoTelaVo()) };
			}

			
			if(e.getSource() == jPanelBotoesManutencao.getCmdAnterior()){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_ANTERIOR_REGISTRO);
				args = new Object[]{ new GrupoSearchVo(this.getGrupoTelaVo()) };
			}
			
			if(e.getSource() == jPanelBotoesManutencao.getCmdProximo()){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PROXIMO_REGISTRO);
				args = new Object[]{ new GrupoSearchVo(this.getGrupoTelaVo()) };
			}
			
			if(e.getSource() == jPanelBotoesManutencao.getCmdUltimo()){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_ULTIMO_REGISTRO);
				args = new Object[]{ new GrupoSearchVo(this.getGrupoTelaVo()) };
			}
			
			if(e.getSource() == jPanelBotoesManutencao.getCmdPesquisa()){
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
				baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
				args = new Object[]{ new GrupoSearchVo(this.getGrupoTelaVo()) };
	            //Reseta os dados do formulário
				FrmGrupos.this.txtCodigoGrupo.setEnabled(true);
				this.setGrupoTelaVo(new Grupo(), new LinkedList());
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
						this.txtCodigoGrupo.setEnabled(true);
						this.setGrupoTelaVo(new Grupo(), Collections.EMPTY_LIST);
						args = new Object[]{ new GrupoSearchVo(this.getGrupoTelaVo()) };
						objRetorno = baseDispatchCRUDCommand.executar( args );
					}
					if(this.atualizarGrid(baseDispatchCRUDCommand.getStrMetodo())){
						//Atualiza as informações no grid
						Pagina pagina = (Pagina) objRetorno[0];
						//TabelaGrupoModel modelo = (TabelaGrupoModel)this.tabelaDados.getModel();
			            TabelaGrupoModel modelo = (TabelaGrupoModel)((TableSorter)FrmGrupos.this.tabelaDados.getModel()).getTableModel();
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
					if(this.atualizarDadosTela(baseDispatchCRUDCommand.getStrMetodo())){
						if(objRetorno.length > 1){
							this.setGrupoTelaVo((Grupo) objRetorno[0], (List)objRetorno[1]);	
						}else{
							this.setGrupoTelaVo((Grupo) objRetorno[0], Collections.EMPTY_LIST);	
						}
						this.txtCodigoGrupo.requestFocus();
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

		private void desabilitarBotoes() {
			jPanelBotoesManutencao.desabilitarBotoes();
		}

		protected boolean exibirMsgConfirmacao(String strMetodo) {
			if(
					BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo())
												||
					BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())
												||
					BaseDispatchCRUDCommand.METODO_EXCLUIR.equals(baseDispatchCRUDCommand.getStrMetodo())
			){
					return true;
			}else{
					return false;
			}
		}

		protected boolean atualizarDadosTela(String strMetodo) {
			if(
					BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo())
																||
					BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())
			
			){
					return true;
			}else{
					return false;
			}
		}

		/**
		 * Retorna true se o método configurado atualmente
		 * necessitar de atualização dos dados no grid.
		 * */
		protected boolean atualizarGrid(String strMetodo) {
			if(
					BaseDispatchCRUDCommand.METODO_LISTAR.equals(baseDispatchCRUDCommand.getStrMetodo())
			){
					return true;
			}else{
					return false;
			}
		}

		private void atualizarStatusBotoesManutencao(String strMetodo) {
				jPanelBotoesManutencao.atualizarStatusBotoesManutencao(strMetodo, baseDispatchCRUDCommand);
				atualizarStatusBotoesControleSeguranca();			
		}

}
