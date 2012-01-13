package dnsec.modulos.cadastros.usuario.ui;

import java.awt.Color;
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

import dnsec.modulos.cadastros.usuario.model.TabelaPesquisaUsuarioModel;
import dnsec.modulos.cadastros.usuario.vo.UsuarioSearchVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.factory.CommandFactory;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.listener.PesquisaUsuarioListener;
import dnsec.shared.swing.base.BaseJButton;
import dnsec.shared.swing.base.BaseJFormattedTextField;
import dnsec.shared.swing.base.BaseJInternalFrame;
import dnsec.shared.swing.base.BaseJLabel;
import dnsec.shared.swing.base.BaseJTable;
import dnsec.shared.swing.base.BaseJTextBox;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.Pagina;
import dnsec.shared.util.RecursosUtil;

public class FrmPesquisaUsuarios extends BaseJInternalFrame implements
		ActionListener {

	/**
	 * Lista de listeners que serão notificados quando alguma função for
	 * selecionada
	 */
	private List pesquisaUsuariosListenerList = new LinkedList();

	private boolean exibirBotoesManutencao;

	private BaseJLabel lblCodUsuarioUsua;

	private BaseJLabel lblNomeUsuarioUsua;

	private BaseJTextBox txtCodUsuarioUsua;

	private BaseJTextBox txtNomeUsuarioUsua;

	private BaseJButton cmdPrimeiro;

	private BaseJButton cmdAnterior;

	private BaseJButton cmdProximo;

	private BaseJButton cmdUltimo;

	/**
	 * Botões para chamar a tela de manutenção das funções
	 */
	private BaseJButton cmdIncluirUsuario;

	private BaseJButton cmdExcluirUsuario;

	private BaseJButton cmdAlterarUsuario;

	private BaseJButton cmdSair = new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.botao.sair"), GerenciadorJanelas.ICONE_BOTAO_SAIR_PEQUENO);

	private BaseJButton cmdPesquisa = new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.botao.pesquisa"), GerenciadorJanelas.ICONE_BOTAO_PESQUISA);

	private BaseJTable tabelaDados;

	private BaseJLabel lblPaginacao = new BaseJLabel("Páginas");

	private BaseJFormattedTextField txtQtdePaginas = null;

	private BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_USUARIOS);

	private UsuarioSearchVo usuarioSearchVo = new UsuarioSearchVo();

	private Usuario usuarioSelecionado;

	protected void inicializarComponentes() {
		TableSorter sorter = new TableSorter(new TabelaPesquisaUsuarioModel());
		tabelaDados = new BaseJTable(sorter); // NEW
		sorter.setTableHeader(tabelaDados.getTableHeader()); // ADDED THIS
		// table.setPreferredScrollableViewportSize(new Dimension(500, 70));

		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		getContentPane().setLayout(gridBagLayout);

		lblCodUsuarioUsua = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.usuario.codigo.usuario"));
		lblNomeUsuarioUsua = new BaseJLabel(RecursosUtil.getInstance().getResource("key.cadastro.usuario.nome.usuario"));
		txtCodUsuarioUsua = new BaseJTextBox(15);
		txtCodUsuarioUsua.setTamanhoMaximo(30);

		txtNomeUsuarioUsua = new BaseJTextBox(15);
		txtNomeUsuarioUsua.setTamanhoMaximo(30);

		int linhaAtual = 0;

		constraints.gridx = 0;
		constraints.gridy = linhaAtual;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.NONE;
		// constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(lblCodUsuarioUsua, constraints);

		constraints.gridx = 1;
		constraints.gridy = linhaAtual;
		constraints.weightx = 100;
		constraints.weighty = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		// constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(txtCodUsuarioUsua, constraints);

		constraints.gridx = 6;
		constraints.gridy = linhaAtual;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.NONE;
		// constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(lblNomeUsuarioUsua, constraints);

		constraints.gridx = 7;
		constraints.gridy = linhaAtual;
		constraints.weightx = 100;
		constraints.weighty = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		// constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(txtNomeUsuarioUsua, constraints);

		constraints.gridx = 12;
		constraints.gridy = linhaAtual;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.NONE;
		// constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(cmdPesquisa, constraints);
		cmdPesquisa.addActionListener(this);

		linhaAtual++;
		// Botões para paginação e para
		JPanel painelBotoes = new JPanel();
		painelBotoes.setLayout(new GridBagLayout());

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.NONE;
		// constraints.anchor = GridBagConstraints.NORTHWEST;
		cmdPrimeiro = new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.primeiro"), GerenciadorJanelas.ICONE_BOTAO_PRIMEIRO);
		cmdPrimeiro.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.primeiro.tooltiptext"));
		painelBotoes.add(cmdPrimeiro, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.NONE;
		// constraints.anchor = GridBagConstraints.NORTHWEST;
		cmdAnterior = new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.anterior"), GerenciadorJanelas.ICONE_BOTAO_ANTERIOR);
		cmdAnterior.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.anterior.tooltiptext"));
		painelBotoes.add(cmdAnterior, constraints);

		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.NONE;
		// constraints.anchor = GridBagConstraints.NORTHWEST;
		cmdProximo = new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.proximo"), GerenciadorJanelas.ICONE_BOTAO_PROXIMO);
		cmdProximo.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.proximo.tooltiptext"));
		painelBotoes.add(cmdProximo, constraints);

		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.NONE;
		// constraints.anchor = GridBagConstraints.NORTHWEST;
		cmdUltimo = new BaseJButton(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.ultimo"), GerenciadorJanelas.ICONE_BOTAO_ULTIMO);
		cmdUltimo.setToolTipText(RecursosUtil.getInstance().getResource("key.jpanelmanutencao.ultimo.tooltiptext"));
		painelBotoes.add(cmdUltimo, constraints);

		constraints.gridx = 4;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.NONE;

		// constraints.anchor = GridBagConstraints.NORTHWEST;
		cmdIncluirUsuario = new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.botao.incluir"), GerenciadorJanelas.ICONE_BOTAO_INCLUIR);
		cmdIncluirUsuario.addActionListener(this);
		painelBotoes.add(cmdIncluirUsuario, constraints);

		constraints.gridx = 5;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.NONE;
		// constraints.anchor = GridBagConstraints.NORTHWEST;
		cmdAlterarUsuario = new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.botao.alterar"), GerenciadorJanelas.ICONE_BOTAO_ALTERAR);
		cmdAlterarUsuario.addActionListener(this);
		painelBotoes.add(cmdAlterarUsuario, constraints);

		constraints.gridx = 6;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.NONE;
		// constraints.anchor = GridBagConstraints.NORTHWEST;
		cmdExcluirUsuario = new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.botao.excluir"), GerenciadorJanelas.ICONE_BOTAO_EXCLUIR);
		cmdExcluirUsuario.addActionListener(this);
		painelBotoes.add(cmdExcluirUsuario, constraints);

		// Botões para manutenção
		linhaAtual++;
		constraints.gridx = 0;
		constraints.gridy = linhaAtual;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 13;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(painelBotoes, constraints);
		cmdPrimeiro.addActionListener(this);
		cmdAnterior.addActionListener(this);
		cmdProximo.addActionListener(this);
		cmdUltimo.addActionListener(this);

		// Tabela de dados
		linhaAtual++;
		constraints.gridx = 0;
		constraints.gridy = linhaAtual;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 5;
		constraints.gridwidth = 13;
		constraints.fill = GridBagConstraints.BOTH;
		// constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(new JScrollPane(tabelaDados), constraints);

		/**
		 * Quando o usuário seleciona um registro no grid, notifica o listener
		 * sobre a informação selecionada e fecha a janela, caso o listener
		 * retorne true no método de confirmação de fechamento da janela
		 */
		ListSelectionModel rowSM = tabelaDados.getSelectionModel();
		rowSM.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				FrmPesquisaUsuarios.this.usuarioSelecionado = null;
				if (e.getValueIsAdjusting())
					return;
				ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				if (lsm.isSelectionEmpty()) {
				} else {
					int linhaSelecionada = lsm.getMinSelectionIndex();
					// TabelaPesquisaUsuarioModel modelo =
					// (TabelaPesquisaUsuarioModel)(FrmPesquisaUsuarios.this.tabelaDados.getModel());
					TabelaPesquisaUsuarioModel modelo = (TabelaPesquisaUsuarioModel) ((TableSorter) (FrmPesquisaUsuarios.this.tabelaDados.getModel())).getTableModel();

					// Atualiza a função selecionada atualmente (para os
					// procedimentos de alteração e exclusão de dados)
					FrmPesquisaUsuarios.this.usuarioSelecionado = (Usuario) modelo.getPaginaDados().getListObjects().get(linhaSelecionada);

					if (FrmPesquisaUsuarios.this.pesquisaUsuariosListenerList != null) {
						// Notifica os listeners
						Iterator itListeners = FrmPesquisaUsuarios.this.pesquisaUsuariosListenerList.iterator();
						while (itListeners.hasNext()) {
							((PesquisaUsuarioListener) itListeners.next()).usuarioSelecionado((Usuario) modelo.getPaginaDados().getListObjects().get(linhaSelecionada));
						}

						// Caso o primeiro listener tenha requisitado, fecha a
						// janela de pesquisa
						if (FrmPesquisaUsuarios.this.pesquisaUsuariosListenerList.size() > 0) {
							if (((PesquisaUsuarioListener) (pesquisaUsuariosListenerList.get(0))).fecharJanelaPesquisaSistema()) {
								// GerenciadorJanelas.removePesquisaUsuarioListener(((PesquisaUsuarioListener)(pesquisaUsuariosListenerList.get(0))));
								pesquisaUsuariosListenerList.remove(0);
			            		lsm.clearSelection();
								FrmPesquisaUsuarios.this.fecharJanela();
							}
						}
					}

				}
			}
		});

		linhaAtual += 5;

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

		// Campo de texto para número de registros pos página
		// TODO arrumar para permitir null no campo
		NumberFormat nf = NumberFormat.getIntegerInstance();
		nf.setParseIntegerOnly(true);
		nf.setMaximumFractionDigits(0);
		this.txtQtdePaginas = new BaseJFormattedTextField(nf);
		this.txtQtdePaginas.setValue(new Long(this.baseDispatchCRUDCommand.getPagina().getRegistrosPorPagina()));
		constraints.gridx = 6;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.weightx = 0;
		constraints.weighty = 0;
		painelPaginacao.add(txtQtdePaginas, constraints);

		/**
		 * Limita a quantidade de páginas para o valor máximo definido na classe
		 * página
		 */
		txtQtdePaginas.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				if (input instanceof JFormattedTextField) {
					JFormattedTextField ftf = (JFormattedTextField) input;
					AbstractFormatter formatter = ftf.getFormatter();
					if (formatter != null) {
						String text = ftf.getText();
						try {
							Long valor = (Long) formatter.stringToValue(text);
							if (valor.longValue() > Pagina.MAXIMO_REGISTROS_POR_PAGINA_DEFAULT) {
								return false;
							}
							return true;
						} catch (ParseException pe) {
							return false;
						}
					}
				}
				return true;
			}

		});

		constraints.gridx = 0;
		constraints.gridy = linhaAtual;
		constraints.gridheight = 1;
		constraints.gridwidth = 12;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.weightx = 0;
		constraints.weighty = 0;
		getContentPane().add(painelPaginacao, constraints);

		linhaAtual++;

		constraints.gridx = 12;
		constraints.gridy = linhaAtual;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.SOUTHEAST;
		constraints.weightx = 0;
		constraints.weighty = 0;
		getContentPane().add(this.cmdSair, constraints);

		// Associa a ação para sair da tela
		cmdSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmPesquisaUsuarios.this.fecharJanela();
			}
		});

		pack();
	}

	public void actionPerformed(ActionEvent e) {
		Object args[] = null;

		if (e.getSource() == cmdPesquisa) {
			baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
			baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
			args = new Object[] { this.getTelaVo() };
			this.usuarioSelecionado = null;
		}

		if (e.getSource() == cmdPrimeiro) {
			baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
			baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
			args = new Object[] { this.getTelaVo() };
			this.usuarioSelecionado = null;
		}

		if (e.getSource() == cmdAnterior) {
			baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
			baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_ANTERIOR_REGISTRO);
			args = new Object[] { this.getTelaVo() };
			this.usuarioSelecionado = null;
		}

		if (e.getSource() == cmdProximo) {
			baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
			baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PROXIMO_REGISTRO);
			args = new Object[] { this.getTelaVo() };
			this.usuarioSelecionado = null;
		}

		if (e.getSource() == cmdUltimo) {
			baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
			baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_ULTIMO_REGISTRO);
			args = new Object[] { this.getTelaVo() };
			this.usuarioSelecionado = null;
		}

		if (e.getSource() == cmdExcluirUsuario) {
			if (usuarioSelecionado != null) {
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_EXCLUIR);
				args = new Object[] { usuarioSelecionado };
			} else {
				JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado"),
								RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado.titulo.janela"), 
								JOptionPane.WARNING_MESSAGE);
				return;
			}
		}

		if (e.getSource() == cmdIncluirUsuario) {
			GerenciadorJanelas.getInstance().getAcaoJanelaCadastroUsuarios(getParent(), BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO, new Usuario()).actionPerformed(e);
			// this.dispose();
			return;
		}

		if (e.getSource() == cmdAlterarUsuario) {
			if (usuarioSelecionado != null) {
				GerenciadorJanelas.getInstance().getAcaoJanelaCadastroUsuarios(getParent(), BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO, usuarioSelecionado).actionPerformed(e);
				// this.dispose();
			} else {
				// Nenhum usuário foi selecionado no grid. Avisa o usuário
				JOptionPane.showMessageDialog( this, RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado"), RecursosUtil.getInstance().getResource("key.msg.nenhum.item.selecionado.titulo.janela"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			return;
		}

		if (baseDispatchCRUDCommand != null) {
			try {
				this.desabilitarBotoes();
				/*
				 * Se estiver pesquisando atualiza o número de páginas que o
				 * usuário digitou
				 */
				if (this.atualizarGrid(baseDispatchCRUDCommand.getStrMetodo())) {
					this.baseDispatchCRUDCommand.getPagina().setRegistrosPorPagina(((Long) txtQtdePaginas.getValue()).longValue());
				}
				Object objRetorno[] = baseDispatchCRUDCommand.executar(args);

				if (this.exibirMsgConfirmacao(baseDispatchCRUDCommand
						.getStrMetodo())) {
					JOptionPane.showMessageDialog( this, RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.realizada.com.sucesso"),
									RecursosUtil.getInstance().getResource(	"key.jpanelmanutencao.action.realizada.com.sucesso.titulo.janela"),
									JOptionPane.INFORMATION_MESSAGE);
					// Realiza a pesquisa para popular o grid
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
					baseDispatchCRUDCommand	.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
					args = new Object[] { this.getTelaVo() };
					objRetorno = baseDispatchCRUDCommand.executar(args);
				}

				if (this.atualizarGrid(baseDispatchCRUDCommand.getStrMetodo())) {
					// Atualiza as informações no grid
					Pagina pagina = (Pagina) objRetorno[0];
					// TabelaPesquisaUsuarioModel modelo =
					// (TabelaPesquisaUsuarioModel)this.tabelaDados.getModel();
					TabelaPesquisaUsuarioModel modelo = (TabelaPesquisaUsuarioModel) ((TableSorter) (FrmPesquisaUsuarios.this.tabelaDados.getModel())).getTableModel();
					modelo.setPaginaDados(pagina);
					modelo.fireTableDataChanged();
					// Atualiza as informações sobre a paginação
					Long totalPaginas = new Long(this.baseDispatchCRUDCommand.getPagina().getTotalPaginas());
					Long totalRegistros = new Long(this.baseDispatchCRUDCommand.getPagina().getTotalRegistros());
					Long paginaAtual = new Long(this.baseDispatchCRUDCommand.getPagina().getPaginaAtual());
					this.lblPaginacao.setText(RecursosUtil.getInstance().getResource("key.msg.paginacao",
									new Object[] { paginaAtual, totalPaginas, totalRegistros }));
					this.txtQtdePaginas.setValue(new Long(this.baseDispatchCRUDCommand.getPagina().getRegistrosPorPagina()));

				}

			} catch (CommandException commandException) {
				super.tratarMensagemErro(commandException);
				/*
				 * Se estava numa operação de gravação volta para o status
				 * anterior
				 */
				if (BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo())) {
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO);
					baseDispatchCRUDCommand.setMetodoAnterior("");
				} else if (BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO
						.equals(baseDispatchCRUDCommand.getStrMetodo())) {
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
					baseDispatchCRUDCommand.setMetodoAnterior("");
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.nao.conigurada"),
							RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.nao.conigurada.titulo.janela"),
							JOptionPane.WARNING_MESSAGE);
		}
		this.atualizarStatusBotoesManutencao(baseDispatchCRUDCommand.getStrMetodo());
	}

	public FrmPesquisaUsuarios() {
		inicializarMaximizado = true;
		setTitle(RecursosUtil.getInstance().getResource("key.pesquisa.usuario.titulo.janela"));
		setFrameIcon(GerenciadorJanelas.ICONE_USUARIO_PEQUENO);
		inicializarComponentes();
		this.addInternalFrameListener(new InternalFrameListener() {

			public void internalFrameActivated(InternalFrameEvent e) {
				// ActionEvent evt = new ActionEvent(cmdPesquisa, 0 , "");
				// FrmPesquisaUsuarios.this.actionPerformed(evt);
				/*
				 * Exibe ou esconde os botões de manutenção de acordo com a
				 * opção feita quando o form foi criado
				 */
				FrmPesquisaUsuarios.this.cmdIncluirUsuario.setVisible(FrmPesquisaUsuarios.this.isExibirBotoesManutencao());
				FrmPesquisaUsuarios.this.cmdExcluirUsuario.setVisible(FrmPesquisaUsuarios.this.isExibirBotoesManutencao());
				FrmPesquisaUsuarios.this.cmdAlterarUsuario.setVisible(FrmPesquisaUsuarios.this.isExibirBotoesManutencao());
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
		});

	}

	public boolean isExibirBotoesManutencao() {
		return exibirBotoesManutencao;
	}

	public void setExibirBotoesManutencao(boolean exibirBotoesManutencao) {
		this.exibirBotoesManutencao = exibirBotoesManutencao;
	}

	public void addPesquisaUsuarioListener(
			PesquisaUsuarioListener pesquisaUsuarioListener) {
		if (pesquisaUsuarioListener != null) {
			if (!pesquisaUsuariosListenerList.contains(pesquisaUsuarioListener)) {
				pesquisaUsuariosListenerList.add(pesquisaUsuarioListener);
			}
		}
	}

	public void removerPesquisaUsuarioListener(
			PesquisaUsuarioListener pesquisaUsuarioListener) {
		if (pesquisaUsuarioListener != null) {
			if (!pesquisaUsuariosListenerList.contains(pesquisaUsuarioListener)) {
				pesquisaUsuariosListenerList.add(pesquisaUsuarioListener);
			}
		}
	}

	protected UsuarioSearchVo getTelaVo() {
		usuarioSearchVo.getUsuario().setCodUsuarioUsua(
				this.txtCodUsuarioUsua.getText());
		usuarioSearchVo.getUsuario().setNomeUsuarioUsua(
				this.txtNomeUsuarioUsua.getText());
		return usuarioSearchVo;
	}

	/** Limpa os listeners confirurados na janeça */
	public void clearListeners() {
		try {
			this.pesquisaUsuariosListenerList.clear();
		} catch (UnsupportedOperationException e) {
			this.pesquisaUsuariosListenerList = new LinkedList();
		}
	}

	/** Limpa os listeners configurados na tela de pesquisa e fecha a janela */
	public void fecharJanela() {
		clearListeners();
		this.tabelaDados.getSelectionModel().clearSelection();
		super.fecharJanela();
	}

	/** Desabiltia todos os botões da tela. */
	protected void desabilitarBotoes() {
		this.cmdPesquisa.setEnabled(false);
		this.cmdExcluirUsuario.setEnabled(false);
		this.cmdIncluirUsuario.setEnabled(false);
		this.cmdAlterarUsuario.setEnabled(false);
	}

	/**
	 * Atualiza o status dos botões da tela de acordo com o estado da operação
	 * atual
	 * 
	 * @param strMetodo ->
	 *            método para comparação
	 */
	protected void atualizarStatusBotoesManutencao(String strMetodo) {
		this.cmdPesquisa.setEnabled(true);
		this.cmdExcluirUsuario.setEnabled(true);
		this.cmdAlterarUsuario.setEnabled(true);
		this.cmdIncluirUsuario.setEnabled(true);
		atualizarStatusBotoesControleSeguranca();
	}

	public void atualizarEstado(){
		atualizarStatusBotoesControleSeguranca();
	}
	
	protected void atualizarStatusBotoesControleSeguranca()
	{
		if(!Constantes.COD_USR_ADM.equals(GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua())){
			if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_INC_USUARIO) == null)
			{
					cmdIncluirUsuario.setEnabled(false);	
			}
			if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_ALT_USUARIO) == null)
			{
					cmdAlterarUsuario.setEnabled(false);	
			}
			if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_EXC_USUARIO) == null)
			{
					cmdExcluirUsuario.setEnabled(false);	
			}
		}
	}
	
	
	/**
	 * Retorna quais métodos do command devem exibir a mensagem indicando que a
	 * operação ocorreu com sucesso
	 * 
	 * @return boolean indicando se a mensagem de confirmação deve ou não ser
	 *         exibida
	 */
	protected boolean exibirMsgConfirmacao(String strMetodo) {
		if (BaseDispatchCRUDCommand.METODO_EXCLUIR
				.equals(baseDispatchCRUDCommand.getStrMetodo())) {
			return true;
		}
		return false;
	}

	/**
	 * Indica quais métodos no commando devem atualizar as informações na tela
	 * do usuário
	 * 
	 * @return boolean indicando se as informações no grid da tela devem ou não
	 *         se atualizadas
	 */
	protected boolean atualizarGrid(String strMetodo) {
		if (BaseDispatchCRUDCommand.METODO_LISTAR
				.equals(baseDispatchCRUDCommand.getStrMetodo())
				|| BaseDispatchCRUDCommand.METODO_EXCLUIR
						.equals(baseDispatchCRUDCommand.getStrMetodo())) {
			return true;
		}
		return false;
	}

}
