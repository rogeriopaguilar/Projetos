package dnsec.modulos.cadastros.associacoes.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import org.apache.commons.collections.map.LRUMap;

import dnsec.modulos.cadastros.associacoes.model.ModeloListaGruposFuncao;
import dnsec.modulos.cadastros.associacoes.model.ModeloListaGruposSistema;
import dnsec.modulos.cadastros.associacoes.model.ModeloListaGruposUsuario;
import dnsec.modulos.cadastros.associacoes.model.ModeloListaObjetosGrupo;
import dnsec.modulos.cadastros.associacoes.ui.dnd.FuncaoTransferHandler;
import dnsec.modulos.cadastros.associacoes.ui.dnd.GrupoTransferHandler;
import dnsec.modulos.cadastros.associacoes.ui.dnd.SistemaTransferHandler;
import dnsec.modulos.cadastros.associacoes.ui.dnd.UsuarioTransferHandler;
import dnsec.modulos.cadastros.associacoes.ui.renderer.ArvoreFuncoesRenderer;
import dnsec.modulos.cadastros.associacoes.ui.renderer.ArvoreGrupoRenderer;
import dnsec.modulos.cadastros.associacoes.ui.renderer.ArvoreSistemasRenderer;
import dnsec.modulos.cadastros.associacoes.ui.renderer.ArvoreUsuariosRenderer;
import dnsec.modulos.cadastros.associacoes.ui.renderer.ComboOpcoesExibicaoGrupoRenderer;
import dnsec.modulos.cadastros.associacoes.ui.renderer.ComboSistemasFuncaoRenderer;
import dnsec.modulos.cadastros.associacoes.ui.renderer.GrupoFuncaoRenderer;
import dnsec.modulos.cadastros.associacoes.ui.renderer.GrupoUsuarioRenderer;
import dnsec.modulos.cadastros.associacoes.ui.renderer.GruposRelacionadosRenderer;
import dnsec.modulos.cadastros.associacoes.ui.renderer.SistemaRenderer;
import dnsec.modulos.cadastros.funcao.business.CommandFuncoes;
import dnsec.modulos.cadastros.funcao.vo.FuncaoSearchVo;
import dnsec.modulos.cadastros.grupo.business.CommandGrupos;
import dnsec.modulos.cadastros.grupo.vo.GrupoSearchVo;
import dnsec.modulos.cadastros.sistema.business.CommandSistemas;
import dnsec.modulos.cadastros.sistema.vo.SistemaSearchVo;
import dnsec.modulos.cadastros.usuario.business.CommandUsuarios;
import dnsec.modulos.cadastros.usuario.vo.UsuarioSearchVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.database.hibernate.Grupo;
import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.factory.CommandFactory;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.swing.base.BaseJButton;
import dnsec.shared.swing.base.BaseJComboBox;
import dnsec.shared.swing.base.BaseJInternalFrame;
import dnsec.shared.swing.base.BaseJLabel;
import dnsec.shared.swing.base.BaseJList;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.Pagina;
import dnsec.shared.util.RecursosUtil;

public class FrmAssociacoes extends BaseJInternalFrame implements ActionListener {

	private JTabbedPane painelOpcoes;

	private MutableTreeNode noBotaoAtualizacao = new DefaultMutableTreeNode();

	// Controles para a aba sistemas
	private JTree arvoreSistemas;
	private DefaultMutableTreeNode noRaizSistemas;
	private DefaultTreeModel modeloArvoreSistemas;
	private JPanel painelControlesSistema;
	private JPanel painelControlesSistemaDireita;
	private JPanel painelControlesSistemaEsquerda;
	private JSplitPane painelSeparacaoControlesSistema;
	private JScrollPane scrollSistema;
	private JList listaDisponiveisSistema;
	private ModeloListaGruposSistema modeloListaDisponiveisSistema;
	private JScrollPane scrollDisponiveisSistema;
	private JList listaRelacionadosSistema;
	private GrupoTransferHandler grupoTransferHandler;
	private ModeloListaGruposSistema modeloListaRelacionadosSistema;
	private JButton cmdSalvarAssociacoes;
	private BaseJButton cmdSair;
	private JScrollPane scrollRelacionadosSistema;
	private boolean sistemasCarregados = false;

	//Commands
	private BaseDispatchCRUDCommand baseDispatchCRUDCommand = null;
	private BaseDispatchCRUDCommand baseDispatchCRUDCommandSistema = CommandFactory.getNewCommand(CommandFactory.COMMAND_SISTEMAS);
	private BaseDispatchCRUDCommand baseDispatchCRUDCommandGrupo = CommandFactory.getNewCommand(CommandFactory.COMMAND_GRUPOS);
	private BaseDispatchCRUDCommand baseDispatchCRUDCommandUsuario = CommandFactory.getNewCommand(CommandFactory.COMMAND_USUARIOS);
	private BaseDispatchCRUDCommand baseDispatchCRUDCommandFuncao = CommandFactory.getNewCommand(CommandFactory.COMMAND_FUNCOES);
	
	private String strMetodoCommand = "";

	// Controles para a aba grupo
	private JTree arvoreGrupo;
	private DefaultMutableTreeNode noRaizGrupos;
	private DefaultTreeModel modeloArvoreGrupos;
	private JPanel painelControlesGrupo;
	private JPanel painelControlesGruposDireita;
	private JPanel painelControlesGruposEsquerda;
	private JSplitPane painelSeparacaoControlesGrupos;
	private JScrollPane scrollGrupo;
	private JList listaDisponiveisGrupo;
	private ModeloListaObjetosGrupo modeloListaDisponiveisGrupo;
	private JScrollPane scrollDisponiveisGrupo;
	private BaseJList listaRelacionadosGrupo;
	private SistemaTransferHandler sistemaTransferHandler;
	private UsuarioTransferHandler usuarioTransferHandler;
	private FuncaoTransferHandler funcaoTransferHandler;
	private ModeloListaObjetosGrupo modeloListaRelacionadosGrupo;
	private JScrollPane scrollRelacionadosGrupos;
	public static String EXIBIR_GRUPO_FUNCAO = "Funções";
	public static String EXIBIR_GRUPO_USUARIO = "Usuários";
	public static String EXIBIR_GRUPO_SISTEMA = "Sistemas";
	private JComboBox comboOpcoesExibicaoGrupo;
	private boolean gruposCarregados = false;
	private BaseJLabel lblComboSistemaFuncao;
	private BaseJComboBox comboSistemasFuncao;
	private BaseJButton cmdPesquisarFuncoes;

	//Controles para a aba Usuários
	private JTree arvoreUsuarios;
	private DefaultMutableTreeNode noRaizUsuarios;
	private DefaultTreeModel modeloArvoreUsuarios;
	private JPanel painelControlesUsuarios;
	private JPanel painelControlesUsuariosDireita;
	private JPanel painelControlesUsuariosEsquerda;
	private JSplitPane painelSeparacaoControlesUsuarios;
	private JScrollPane scrollUsuarios;
	private JList listaDisponiveisUsuario;
	private ModeloListaGruposUsuario modeloListaDisponiveisUsuario;
	private JScrollPane scrollDisponiveisUsuario;
	private JList listaRelacionadosUsuario;
	private GrupoTransferHandler grupoUsuarioTransferHandler;
	private ModeloListaGruposUsuario modeloListaRelacionadosUsuario;
	private JScrollPane scrollRelacionadosUsuarios;
	private boolean usuariosCarregados = false;
	/*private BaseJLabel lblComboSistemasGrupoUsuario;
	private BaseJComboBox comboSistemasGrupoUsuario;
	private BaseJButton cmdPesquisarGruposUsuario;*/

	
	//Controles para a aba Funções
	private JTree arvoreFuncoes;
	private DefaultMutableTreeNode noRaizFuncoes;
	private DefaultTreeModel modeloArvoreFuncoes;
	private JPanel painelControlesFuncoes;
	private JPanel painelControlesFuncoesDireita;
	private JPanel painelControlesFuncoesEsquerda;
	private JSplitPane painelSeparacaoControlesFuncoes;
	private JScrollPane scrollFuncoes;
	private JList listaDisponiveisFuncao;
	private ModeloListaGruposFuncao modeloListaDisponiveisFuncao;
	private JScrollPane scrollDisponiveisFuncao;
	private JList listaRelacionadosFuncao;
	private GrupoTransferHandler grupoFuncaoTransferHandler;
	private ModeloListaGruposFuncao modeloListaRelacionadosFuncao;
	private JScrollPane scrollRelacionadosFuncao;
	private boolean funcoesCarregadas = false;
	
	
	// Cache 
	private static final int TAMANHO_CACHE = 5;
	private Map<Sistema, List<Grupo>[]> mapaGruposSistemas = new LRUMap(TAMANHO_CACHE);
	private Map<Object, List<Funcao>[]> mapaFuncoesGrupo = new LRUMap(TAMANHO_CACHE);
	private Map<Grupo, List<Sistema>[]> mapaSistemasGrupo = new LRUMap(TAMANHO_CACHE);
	private Map<Grupo, List<Usuario>[]> mapaUsuariosGrupo = new LRUMap(TAMANHO_CACHE);
	private Map<Usuario, List<Grupo>[]> mapaGruposUsuarios = new LRUMap(TAMANHO_CACHE);
	private Map<Funcao, List<Grupo>[]> mapaGruposFuncao = new LRUMap(TAMANHO_CACHE);


	public void inicializarComponentes() {
		
		noBotaoAtualizacao.setUserObject(new JButton("Recarregar"));

		
		/**
		 * ********************************************Painel de
		 * Sistemas****************************************************************************************
		 */
		// Monta a árvore com os dados para a aba sistemas
		noRaizSistemas = new DefaultMutableTreeNode();
		noRaizSistemas.setUserObject("Sistemas");
		modeloArvoreSistemas = new DefaultTreeModel(noRaizSistemas);

		arvoreSistemas = new JTree(modeloArvoreSistemas);

		// Seta o renderer para exibir a sigla do sistema
		arvoreSistemas.setCellRenderer(new ArvoreSistemasRenderer());

		// Quando o usuário seleciona um sistema, carrega as listas de grupos
		// disponíveis e relacionados
		arvoreSistemas.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreSistemas.getLastSelectedPathComponent();

				if (node == null)
					return;
				Object nodeInfo = node.getUserObject();
				if (node.isLeaf() && ! (nodeInfo instanceof JButton)) {
					baseDispatchCRUDCommandSistema.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
					baseDispatchCRUDCommand = baseDispatchCRUDCommandSistema;
					FrmAssociacoes.this.actionPerformed(new ActionEvent(FrmAssociacoes.this, 0, ""));
				}else{
					baseDispatchCRUDCommand = baseDispatchCRUDCommandSistema;
					baseDispatchCRUDCommandSistema.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
					long registrosPorPagina = baseDispatchCRUDCommandSistema.getPagina().getRegistrosPorPagina();
					baseDispatchCRUDCommandSistema.getPagina().setRegistrosPorPagina(Integer.MAX_VALUE);
					FrmAssociacoes.this.actionPerformed(new ActionEvent(this, 0, ""));
					baseDispatchCRUDCommandSistema.getPagina().setRegistrosPorPagina(registrosPorPagina);
				}
			}
		});

		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		// gridBagConstraints.anchor = GridBagConstraints.;
		gridBagConstraints.weightx = 100;
		gridBagConstraints.weighty = 100;

		painelControlesSistema = new JPanel();
		painelControlesSistemaDireita = new JPanel();
		painelControlesSistemaDireita.setLayout(new GridLayout(1, 2));
		modeloListaDisponiveisSistema = new ModeloListaGruposSistema();
		listaDisponiveisSistema = new JList(modeloListaDisponiveisSistema);
		listaDisponiveisSistema.setDragEnabled(true);
		grupoTransferHandler = new GrupoTransferHandler();
		listaDisponiveisSistema.setTransferHandler(grupoTransferHandler);
		listaDisponiveisSistema.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// Seta o renderer para exibir o nome do grupo
		DefaultListCellRenderer rendererSistema = new SistemaRenderer();
		listaDisponiveisSistema.setCellRenderer(rendererSistema);

		scrollDisponiveisSistema = new JScrollPane(listaDisponiveisSistema);
		modeloListaRelacionadosSistema = new ModeloListaGruposSistema();
		listaRelacionadosSistema = new JList(modeloListaRelacionadosSistema);
		listaRelacionadosSistema.setDragEnabled(true);
		listaRelacionadosSistema.setTransferHandler(grupoTransferHandler);
		listaRelacionadosSistema.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// Seta o renderer para exibir o nome do grupo
		listaRelacionadosSistema.setCellRenderer(rendererSistema);

		scrollRelacionadosSistema = new JScrollPane(listaRelacionadosSistema);
		scrollDisponiveisSistema.setBorder(BorderFactory.createTitledBorder("Grupos disponíveis"));
		painelControlesSistemaDireita.add(scrollDisponiveisSistema);
		scrollRelacionadosSistema.setBorder(BorderFactory.createTitledBorder("Grupos relacionados"));
		painelControlesSistemaDireita.add(scrollRelacionadosSistema);

		painelControlesSistemaEsquerda = new JPanel();
		painelControlesSistemaEsquerda.setLayout(new GridBagLayout());
		painelControlesSistema.setLayout(new BorderLayout());
		scrollSistema = new JScrollPane(arvoreSistemas);
		painelControlesSistemaEsquerda.add(scrollSistema, gridBagConstraints);
		painelControlesSistemaEsquerda.setMinimumSize(new Dimension(100, painelControlesSistemaEsquerda.getMinimumSize().height));
		painelSeparacaoControlesSistema = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelControlesSistemaEsquerda, painelControlesSistemaDireita);
		painelControlesSistema.add(painelSeparacaoControlesSistema, BorderLayout.CENTER);

		/**
		 * **********************************************************Final da
		 * montagem do painel de
		 * sistemas**************************************************
		 */

		/**
		 * ********************************************Painel de
		 * Grupos****************************************************************************************
		 */
		// Monta a árvore com os dados para a aba sistemas
		noRaizGrupos = new DefaultMutableTreeNode();
		noRaizGrupos.setUserObject("Grupos");
		modeloArvoreGrupos = new DefaultTreeModel(noRaizGrupos);

		arvoreGrupo = new JTree(modeloArvoreGrupos);

		// Seta o renderer para exibir o código do grupos
		arvoreGrupo.setCellRenderer(new ArvoreGrupoRenderer());

		// Quando o usuário seleciona um grupo, carrega as listas de usuários,
		// funções ou sistemas disponíveis e relacionados
		arvoreGrupo.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreGrupo.getLastSelectedPathComponent();
				if (node == null)
					return;


				if (node.isLeaf() && !(node.getUserObject() instanceof JButton)) {

					String strTipoCarregamento = (String) comboOpcoesExibicaoGrupo.getSelectedItem();
					if (EXIBIR_GRUPO_FUNCAO.equals(strTipoCarregamento)) {
						modeloListaDisponiveisGrupo.setListaObjetos(Collections.EMPTY_LIST);
						modeloListaRelacionadosGrupo.setListaObjetos(Collections.EMPTY_LIST);
						JOptionPane.showMessageDialog(FrmAssociacoes.this, "Selecione um sistema e clique no botão pesquisar funções disponíveis e relacionadas!");
						return;
					}
					
					baseDispatchCRUDCommandGrupo.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
					baseDispatchCRUDCommand = baseDispatchCRUDCommandGrupo;
					FrmAssociacoes.this.actionPerformed(new ActionEvent(FrmAssociacoes.this, 0, ""));
				}else{
					//Recarrega os grupos
					baseDispatchCRUDCommand = baseDispatchCRUDCommandGrupo;
					baseDispatchCRUDCommandGrupo.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
					long registrosPorPagina = baseDispatchCRUDCommandGrupo.getPagina().getRegistrosPorPagina();
					baseDispatchCRUDCommandGrupo.getPagina().setRegistrosPorPagina(Integer.MAX_VALUE);
					FrmAssociacoes.this.actionPerformed(new ActionEvent(this, 0, ""));
					baseDispatchCRUDCommandGrupo.getPagina().setRegistrosPorPagina(registrosPorPagina);
				}

			}
		});

		gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		// gridBagConstraints.anchor = GridBagConstraints.;
		gridBagConstraints.weightx = 100;
		gridBagConstraints.weighty = 100;

		painelControlesGrupo = new JPanel();

		JPanel painelControlesGruposDireitaTodos = new JPanel();
		painelControlesGruposDireitaTodos.setLayout(new BorderLayout());
		// Monta o combo com as opções de relacionamentos para sistemas
		comboOpcoesExibicaoGrupo = new JComboBox(new String[] { EXIBIR_GRUPO_FUNCAO, EXIBIR_GRUPO_SISTEMA, EXIBIR_GRUPO_USUARIO });
		comboOpcoesExibicaoGrupo.setRenderer(new ComboOpcoesExibicaoGrupoRenderer());
		comboOpcoesExibicaoGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				lblComboSistemaFuncao.setVisible(false);
				comboSistemasFuncao.setVisible(false);
				cmdPesquisarFuncoes.setVisible(false);

				String strTipoCarregamento = (String) comboOpcoesExibicaoGrupo.getSelectedItem();
				if (EXIBIR_GRUPO_FUNCAO.equals(strTipoCarregamento)) {
					lblComboSistemaFuncao.setVisible(true);
					comboSistemasFuncao.setVisible(true);
					cmdPesquisarFuncoes.setVisible(true);
				}
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreGrupo.getLastSelectedPathComponent();
				if (node == null)
					return;
				if (node.isLeaf()) {
					baseDispatchCRUDCommand = baseDispatchCRUDCommandGrupo;
					baseDispatchCRUDCommandGrupo.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
					FrmAssociacoes.this.actionPerformed(new ActionEvent(FrmAssociacoes.this, 0, ""));
				}

			}
		});

		comboSistemasFuncao = new BaseJComboBox(new DefaultComboBoxModel());
		comboSistemasFuncao.setRenderer(new ComboSistemasFuncaoRenderer());

		comboSistemasFuncao.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						modeloListaDisponiveisGrupo.setListaObjetos(Collections.EMPTY_LIST);
						//modeloListaRelacionadosGrupo.setListaObjetos(Collections.EMPTY_LIST);
					}
				}
		);

		sistemaTransferHandler = new SistemaTransferHandler();
		usuarioTransferHandler = new UsuarioTransferHandler();
		funcaoTransferHandler = new FuncaoTransferHandler();

		JPanel painelComboTipoPesquisa = new JPanel();
		painelComboTipoPesquisa.setLayout(new FlowLayout(FlowLayout.LEFT));
		// TODO --> Buscar o label do arquivo
		JLabel lblCombo = new JLabel("Exibir:");
		painelComboTipoPesquisa.add(lblCombo);
		painelComboTipoPesquisa.add(comboOpcoesExibicaoGrupo);
		painelControlesGruposDireitaTodos.add(painelComboTipoPesquisa,BorderLayout.NORTH);
		lblComboSistemaFuncao = new BaseJLabel("Sistema:");
		painelComboTipoPesquisa.add(lblComboSistemaFuncao);
		painelComboTipoPesquisa.add(comboSistemasFuncao);
		cmdPesquisarFuncoes = new BaseJButton("Pesquisar Funções Disponíveis e Relacionadas", GerenciadorJanelas.ICONE_BOTAO_PESQUISA);
		painelComboTipoPesquisa.add(cmdPesquisarFuncoes);
		cmdPesquisarFuncoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// Carrega a lista de funções disponíveis
				baseDispatchCRUDCommand = baseDispatchCRUDCommandGrupo;
				baseDispatchCRUDCommandGrupo.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
				FrmAssociacoes.this.actionPerformed(new ActionEvent(FrmAssociacoes.this, 0, ""));
			}
		});

		painelControlesGruposDireita = new JPanel();
		painelControlesGruposDireita.setLayout(new GridLayout(1, 2));

		modeloListaDisponiveisGrupo = new ModeloListaObjetosGrupo();
		listaDisponiveisGrupo = new JList(modeloListaDisponiveisGrupo);
		listaDisponiveisGrupo.setDragEnabled(true);
		listaDisponiveisGrupo.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);



		scrollDisponiveisGrupo = new JScrollPane(listaDisponiveisGrupo);
		modeloListaRelacionadosGrupo = new ModeloListaObjetosGrupo();
		listaRelacionadosGrupo = new BaseJList(modeloListaRelacionadosGrupo);
		listaRelacionadosGrupo.setDragEnabled(true);
		listaRelacionadosGrupo.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// Seta o renderer para exibir o nome da função, do sistema ou do
		// usuário relacionado ao grupo
		DefaultListCellRenderer rendererGrupoRelacionados = new GruposRelacionadosRenderer(listaRelacionadosGrupo);
		
		listaRelacionadosGrupo.setCellRenderer(rendererGrupoRelacionados);
		listaDisponiveisGrupo.setCellRenderer(rendererGrupoRelacionados);

		
		scrollRelacionadosGrupos = new JScrollPane(listaRelacionadosGrupo);
		scrollDisponiveisGrupo.setBorder(BorderFactory.createTitledBorder("Disponíveis"));
		painelControlesGruposDireita.add(scrollDisponiveisGrupo);
		scrollRelacionadosGrupos.setBorder(BorderFactory.createTitledBorder("Relacionados"));
		painelControlesGruposDireita.add(scrollRelacionadosGrupos);

		painelControlesGruposEsquerda = new JPanel();
		painelControlesGruposEsquerda.setLayout(new GridBagLayout());
		painelControlesGrupo.setLayout(new BorderLayout());
		scrollGrupo = new JScrollPane(arvoreGrupo);
		painelControlesGruposEsquerda.add(scrollGrupo, gridBagConstraints);
		painelControlesGruposEsquerda.setMinimumSize(new Dimension(150,	painelControlesGruposEsquerda.getMinimumSize().height));
		painelControlesGruposDireitaTodos.add(painelControlesGruposDireita, BorderLayout.CENTER);
		painelSeparacaoControlesGrupos = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelControlesGruposEsquerda,	painelControlesGruposDireitaTodos);
		painelControlesGrupo.add(painelSeparacaoControlesGrupos,BorderLayout.CENTER);

		/**
		 * **********************************************************Final da
		 * montagem do painel de
		 * grupos**************************************************
		 */

		
		
		/**
		 * ********************************************Painel de
		 * Usuários****************************************************************************************
		 */
		// Monta a árvore com os dados para a aba usuários
		noRaizUsuarios= new DefaultMutableTreeNode();
		noRaizUsuarios.setUserObject("Usuários");
		modeloArvoreUsuarios = new DefaultTreeModel(noRaizUsuarios);

		arvoreUsuarios = new JTree(modeloArvoreUsuarios);

		// Seta o renderer para exibir o código do usuário
		arvoreUsuarios.setCellRenderer(new ArvoreUsuariosRenderer());

		// Quando o usuário seleciona um usuário, carrega as listas de grupos
		// disponíveis e relacionados
		arvoreUsuarios.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreUsuarios.getLastSelectedPathComponent();
				if (node == null)
					return;
				Object nodeInfo = node.getUserObject();
				if (node.isLeaf() && !(node.getUserObject() instanceof JButton)) {
					baseDispatchCRUDCommandUsuario.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
					baseDispatchCRUDCommand = baseDispatchCRUDCommandUsuario;
					FrmAssociacoes.this.actionPerformed(new ActionEvent(FrmAssociacoes.this, 0, ""));
				}else{
					baseDispatchCRUDCommand = baseDispatchCRUDCommandUsuario;
					baseDispatchCRUDCommandUsuario.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
					long registrosPorPagina = baseDispatchCRUDCommandUsuario.getPagina().getRegistrosPorPagina();
					baseDispatchCRUDCommandUsuario.getPagina().setRegistrosPorPagina(Integer.MAX_VALUE);
					FrmAssociacoes.this.actionPerformed(new ActionEvent(this, 0, ""));
					baseDispatchCRUDCommandUsuario.getPagina().setRegistrosPorPagina(registrosPorPagina);
				}
			}
		});

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		// gridBagConstraints.anchor = GridBagConstraints.;
		gridBagConstraints.weightx = 100;
		gridBagConstraints.weighty = 100;

		painelControlesUsuarios= new JPanel();
		painelControlesUsuariosDireita = new JPanel();
		painelControlesUsuariosDireita.setLayout(new GridLayout(1, 2));
		modeloListaDisponiveisUsuario = new ModeloListaGruposUsuario();
		listaDisponiveisUsuario = new JList(modeloListaDisponiveisUsuario);
		listaDisponiveisUsuario.setDragEnabled(true);
		grupoUsuarioTransferHandler = new GrupoTransferHandler();
		listaDisponiveisUsuario.setTransferHandler(grupoUsuarioTransferHandler);
		listaDisponiveisUsuario.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// Seta o renderer para exibir o nome do grupo
		DefaultListCellRenderer rendererGrupoUsuario = new GrupoUsuarioRenderer();
		listaDisponiveisUsuario.setCellRenderer(rendererGrupoUsuario);

		scrollDisponiveisUsuario= new JScrollPane(listaDisponiveisUsuario);
		modeloListaRelacionadosUsuario = new ModeloListaGruposUsuario();
		listaRelacionadosUsuario = new JList(modeloListaRelacionadosUsuario);
		listaRelacionadosUsuario.setDragEnabled(true);
		listaRelacionadosUsuario.setTransferHandler(grupoUsuarioTransferHandler);
		listaRelacionadosUsuario.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// Seta o renderer para exibir o nome do grupo
		listaRelacionadosUsuario.setCellRenderer(rendererGrupoUsuario);

		scrollRelacionadosUsuarios = new JScrollPane(listaRelacionadosUsuario);
		scrollDisponiveisUsuario.setBorder(BorderFactory.createTitledBorder("Grupos disponíveis"));
		painelControlesUsuariosDireita.add(scrollDisponiveisUsuario);
		scrollRelacionadosUsuarios.setBorder(BorderFactory.createTitledBorder("Grupos relacionados"));
		painelControlesUsuariosDireita.add(scrollRelacionadosUsuarios);

		painelControlesUsuariosEsquerda = new JPanel();
		painelControlesUsuariosEsquerda.setLayout(new GridBagLayout());
		painelControlesUsuarios.setLayout(new BorderLayout());
		scrollUsuarios = new JScrollPane(arvoreUsuarios);
		painelControlesUsuariosEsquerda.add(scrollUsuarios, gridBagConstraints);
		painelControlesUsuariosEsquerda.setMinimumSize(new Dimension(200, painelControlesUsuariosEsquerda.getMinimumSize().height));
		painelSeparacaoControlesUsuarios = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelControlesUsuariosEsquerda, painelControlesUsuariosDireita);
		painelControlesUsuarios.add(painelSeparacaoControlesUsuarios, BorderLayout.CENTER);
		
		/************************Final do painel de usuários***************************************************************************************************/
		
		
		/**
		 * ********************************************Painel de
		 * Funções****************************************************************************************
		 */
		// Monta a árvore com os dados para a aba usuários
		noRaizFuncoes = new DefaultMutableTreeNode();
		noRaizFuncoes.setUserObject("Funções");
		modeloArvoreFuncoes = new DefaultTreeModel(noRaizFuncoes);

		arvoreFuncoes = new JTree(modeloArvoreUsuarios);

		// Seta o renderer para exibir o código da função
		arvoreFuncoes.setCellRenderer(new ArvoreFuncoesRenderer());

		// Quando o usuário seleciona uma função, carrega as listas de grupos
		// disponíveis e relacionados
		arvoreFuncoes.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreFuncoes.getLastSelectedPathComponent();
				if (node == null)
					return;
				Object nodeInfo = node.getUserObject();
				if (node.isLeaf() && !(node.getUserObject() instanceof JButton)) {
					baseDispatchCRUDCommandFuncao.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
					baseDispatchCRUDCommand = baseDispatchCRUDCommandFuncao;
					FrmAssociacoes.this.actionPerformed(new ActionEvent(FrmAssociacoes.this, 0, ""));
				}else if(!(node.getUserObject() instanceof JButton)){
					modeloListaDisponiveisFuncao.setListaGrupos(Collections.EMPTY_LIST);
					modeloListaRelacionadosFuncao.setListaGrupos(Collections.EMPTY_LIST);
				}else{
					baseDispatchCRUDCommand = baseDispatchCRUDCommandFuncao;
					baseDispatchCRUDCommandFuncao.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
					long registrosPorPagina = baseDispatchCRUDCommandFuncao.getPagina().getRegistrosPorPagina();
					baseDispatchCRUDCommandFuncao.getPagina().setRegistrosPorPagina(Integer.MAX_VALUE);
					FrmAssociacoes.this.actionPerformed(new ActionEvent(this, 0, ""));
					baseDispatchCRUDCommandFuncao.getPagina().setRegistrosPorPagina(registrosPorPagina);
				}
			}
		});

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		// gridBagConstraints.anchor = GridBagConstraints.;
		gridBagConstraints.weightx = 100;
		gridBagConstraints.weighty = 100;

		painelControlesFuncoes= new JPanel();
		painelControlesFuncoesDireita = new JPanel();
		painelControlesFuncoesDireita.setLayout(new GridLayout(1, 2));
		modeloListaDisponiveisFuncao = new ModeloListaGruposFuncao();
		listaDisponiveisFuncao = new JList(modeloListaDisponiveisFuncao);
		listaDisponiveisFuncao.setDragEnabled(true);
		grupoFuncaoTransferHandler = new GrupoTransferHandler();
		listaDisponiveisFuncao.setTransferHandler(grupoFuncaoTransferHandler);
		listaDisponiveisFuncao.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// Seta o renderer para exibir o nome do grupo
		DefaultListCellRenderer rendererGrupoFuncao = new GrupoFuncaoRenderer();
		listaDisponiveisFuncao.setCellRenderer(rendererGrupoUsuario);

		scrollDisponiveisFuncao = new JScrollPane(listaDisponiveisFuncao);
		modeloListaRelacionadosFuncao = new ModeloListaGruposFuncao();
		listaRelacionadosFuncao = new JList(modeloListaRelacionadosFuncao);
		listaRelacionadosFuncao.setDragEnabled(true);
		listaRelacionadosFuncao.setTransferHandler(grupoFuncaoTransferHandler);
		listaRelacionadosFuncao.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// Seta o renderer para exibir o nome do grupo
		listaRelacionadosFuncao.setCellRenderer(rendererGrupoUsuario);

		scrollRelacionadosFuncao = new JScrollPane(listaRelacionadosFuncao);
		scrollDisponiveisFuncao.setBorder(BorderFactory.createTitledBorder("Grupos disponíveis"));
		painelControlesFuncoesDireita.add(scrollDisponiveisFuncao);
		scrollRelacionadosFuncao.setBorder(BorderFactory.createTitledBorder("Grupos relacionados"));
		painelControlesFuncoesDireita.add(scrollRelacionadosFuncao);

		painelControlesFuncoesEsquerda = new JPanel();
		painelControlesFuncoesEsquerda.setLayout(new GridBagLayout());
		painelControlesFuncoes.setLayout(new BorderLayout());
		scrollFuncoes = new JScrollPane(arvoreFuncoes);
		painelControlesFuncoesEsquerda.add(scrollFuncoes, gridBagConstraints);
		painelControlesFuncoesEsquerda.setMinimumSize(new Dimension(200, painelControlesFuncoesEsquerda.getMinimumSize().height));
		painelSeparacaoControlesFuncoes = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelControlesFuncoesEsquerda, painelControlesFuncoesDireita);
		painelControlesFuncoes.add(painelSeparacaoControlesFuncoes, BorderLayout.CENTER);
		
		
		// Monta o painel
		painelOpcoes = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		painelOpcoes.addTab("Sistemas", GerenciadorJanelas.ICONE_SISTEMA_PEQUENO, painelControlesSistema);
		painelOpcoes.addTab("Grupos", GerenciadorJanelas.ICONE_GRUPO_PEQUENO, painelControlesGrupo);
		painelOpcoes.addTab("Usuários", GerenciadorJanelas.ICONE_USUARIO_PEQUENO, painelControlesUsuarios);
		painelOpcoes.addTab("Funções", GerenciadorJanelas.ICONE_FUNCAO_PEQUENO, painelControlesFuncoes);

		
		/*
		 * painelOpcoes.addTab("Funções", painelControlesFuncao);
		 * painelOpcoes.addTab("Usuários", painelControlesUsuario);
		 */

		// Verifica qual tab o usuário selecionou
		painelOpcoes.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				JTabbedPane pane = (JTabbedPane) evt.getSource();
				int sel = pane.getSelectedIndex();

				
				
				if (sel == 0) // Sistemas
				{ // Monta a árvore de sistemas
					//Limpa as listas
					/*modeloListaDisponiveisSistema.setListaGrupos(Collections.EMPTY_LIST);
					modeloListaRelacionadosSistema.setListaGrupos(Collections.EMPTY_LIST);*/
					
					/*baseDispatchCRUDCommand = baseDispatchCRUDCommandSistema;
					baseDispatchCRUDCommandSistema.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
					long registrosPorPagina = baseDispatchCRUDCommandSistema.getPagina().getRegistrosPorPagina();
					baseDispatchCRUDCommandSistema.getPagina().setRegistrosPorPagina(Integer.MAX_VALUE);
					FrmAssociacoes.this.actionPerformed(new ActionEvent(this, 0, ""));
					baseDispatchCRUDCommandSistema.getPagina().setRegistrosPorPagina(registrosPorPagina);
					*/
				} else if (sel == 1){ //Grupos
					//Limpa as listas
					/*modeloListaDisponiveisGrupo.setListaObjetos(Collections.EMPTY_LIST);
					modeloListaRelacionadosGrupo.setListaObjetos(Collections.EMPTY_LIST);*/

					
					// Monta a árvore de grupos
					if(!gruposCarregados){
						baseDispatchCRUDCommand = baseDispatchCRUDCommandGrupo;
						baseDispatchCRUDCommandGrupo.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
						long registrosPorPagina = baseDispatchCRUDCommandGrupo.getPagina().getRegistrosPorPagina();
						baseDispatchCRUDCommandGrupo.getPagina().setRegistrosPorPagina(Integer.MAX_VALUE);
						FrmAssociacoes.this.actionPerformed(new ActionEvent(this, 0, ""));
						baseDispatchCRUDCommandGrupo.getPagina().setRegistrosPorPagina(registrosPorPagina);
					}
				} else if(sel == 2){ //Usuários

					if(!usuariosCarregados){
						baseDispatchCRUDCommand = baseDispatchCRUDCommandUsuario;
						baseDispatchCRUDCommandUsuario.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
						long registrosPorPagina = baseDispatchCRUDCommandUsuario.getPagina().getRegistrosPorPagina();
						baseDispatchCRUDCommandUsuario.getPagina().setRegistrosPorPagina(Integer.MAX_VALUE);
						FrmAssociacoes.this.actionPerformed(new ActionEvent(this, 0, ""));
						baseDispatchCRUDCommandUsuario.getPagina().setRegistrosPorPagina(registrosPorPagina);
					}
				} else if(sel == 3){ //Funções

					if(!funcoesCarregadas)
					{
						baseDispatchCRUDCommand = baseDispatchCRUDCommandFuncao;
						baseDispatchCRUDCommandFuncao.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
						long registrosPorPagina = baseDispatchCRUDCommandFuncao.getPagina().getRegistrosPorPagina();
						baseDispatchCRUDCommandFuncao.getPagina().setRegistrosPorPagina(Integer.MAX_VALUE);
						FrmAssociacoes.this.actionPerformed(new ActionEvent(this, 0, ""));
						baseDispatchCRUDCommandFuncao.getPagina().setRegistrosPorPagina(registrosPorPagina);
					}
				}
			}

		});

		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());
		cmdSalvarAssociacoes = new JButton("Salvar", GerenciadorJanelas.ICONE_BOTAO_CONFIRMAR);
		cmdSalvarAssociacoes.addActionListener(this);

		
		painelPrincipal.add(painelOpcoes, BorderLayout.CENTER);

		JPanel painelBotaoSalvar = new JPanel();
		painelBotaoSalvar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		painelBotaoSalvar.add(cmdSalvarAssociacoes);
		cmdSair = new BaseJButton(RecursosUtil.getInstance().getResource("key.pesquisa.funcoes.label.botao.sair"), GerenciadorJanelas.ICONE_BOTAO_SAIR_PEQUENO);
		painelBotaoSalvar.add(cmdSair);
		// Associa a ação para sair da tela
		cmdSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmAssociacoes.this.fecharJanela();
			}
		});

		painelPrincipal.add(painelBotaoSalvar, BorderLayout.SOUTH);
		getContentPane().add(painelPrincipal);

		pack();
	}

	public FrmAssociacoes() {
		inicializarMaximizado = true;
		setFrameIcon(GerenciadorJanelas.ICONE_ASSOCIACOES_PEQUENO);
		setTitle("Associações");
		inicializarComponentes();
		// Carrega a lista de sistemas e monta a árvore
		baseDispatchCRUDCommand = baseDispatchCRUDCommandSistema;
		long registrosPorPagina = baseDispatchCRUDCommandSistema.getPagina().getRegistrosPorPagina();
		baseDispatchCRUDCommandSistema.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
		baseDispatchCRUDCommandSistema.getPagina().setRegistrosPorPagina(Integer.MAX_VALUE);
		FrmAssociacoes.this.actionPerformed(new ActionEvent(this, 0, ""));
		baseDispatchCRUDCommandSistema.getPagina().setRegistrosPorPagina(registrosPorPagina);
		
	}

	public void atualizarEstado() {
		atualizarStatusBotoesManutencao(null);
	}

	public void actionPerformed(ActionEvent e) {
		Object args[] = null;

		atualizarStatusBotoesManutencaoControleSeguranca();		
		
		if (e.getSource() == this.cmdSalvarAssociacoes) {
			int indicePainel = painelOpcoes.getSelectedIndex();
			if (indicePainel == 0) // Sistema - salva as novas configurações
									// dos sistemas
			{
				baseDispatchCRUDCommand = baseDispatchCRUDCommandSistema;
				baseDispatchCRUDCommand.setStrMetodo(CommandSistemas.METODO_SALVAR_GRUPOS_SISTEMA);
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreSistemas.getLastSelectedPathComponent();
				if (node == null || !(node.getUserObject() instanceof Sistema)) {
					// TODO --> retornar mensagem do arquivo
					JOptionPane.showMessageDialog(this, "É necessário selecionar um sistema!", "Teste", JOptionPane.WARNING_MESSAGE);
					return;
				}
				Sistema sistemaAlterar = new Sistema();
				sistemaAlterar.setCodSistemaSist(((Sistema) node.getUserObject()).getCodSistemaSist());
				args = new Object[] { sistemaAlterar, modeloListaRelacionadosSistema.getListaGrupos() };
			} else if (indicePainel == 1) // Grupo - salva as novas
											// configurações para o grupo
			{
				baseDispatchCRUDCommand = baseDispatchCRUDCommandGrupo;
				String strTipoCarregamento = (String) comboOpcoesExibicaoGrupo.getSelectedItem();
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreGrupo.getLastSelectedPathComponent();
				if (node == null || !(node.getUserObject() instanceof Grupo)) {
					// TODO --> retornar mensagem do arquivo
					JOptionPane.showMessageDialog(this, "É necessário selecionar um grupo!", "Teste", JOptionPane.WARNING_MESSAGE);
					return;
				}

				Grupo grupoAlterar = new Grupo();
				grupoAlterar.setCodGrupoGrup(((Grupo) node.getUserObject()).getCodGrupoGrup());
				args = new Object[] { grupoAlterar, modeloListaRelacionadosGrupo.getListaObjetos() };

				if (EXIBIR_GRUPO_FUNCAO.equals(strTipoCarregamento)) {
					baseDispatchCRUDCommand.setStrMetodo(CommandGrupos.METODO_SALVAR_FUNCOES_GRUPO);
					
					/*Neste caso o modelo possui além das funções os sistemas que servem como separadores
					  Na hora de gravar é necessário utilizar uma lista contendo apenas as funções
					  */
					List listaFuncoesASalvar = new LinkedList();
					Iterator it = modeloListaRelacionadosGrupo.getListaObjetos().iterator();
					while(it.hasNext())
					{
						Object objeto = it.next() ;
						if(objeto instanceof Funcao){
							listaFuncoesASalvar.add(objeto);
						}
					}
					args = new Object[] { grupoAlterar, listaFuncoesASalvar};
				} else if (EXIBIR_GRUPO_SISTEMA.equals(strTipoCarregamento)) {
					baseDispatchCRUDCommand.setStrMetodo(CommandGrupos.METODO_SALVAR_SISTEMAS_GRUPO);
				} else if (EXIBIR_GRUPO_USUARIO.equals(strTipoCarregamento)) {
					baseDispatchCRUDCommand.setStrMetodo(CommandGrupos.METODO_SALVAR_USUARIOS_GRUPO);
				}

			}else if(indicePainel == 2){ //Usuário
				baseDispatchCRUDCommand = baseDispatchCRUDCommandUsuario;
				baseDispatchCRUDCommand.setStrMetodo(CommandUsuarios.METODO_SALVAR_GRUPOS_USUARIO);
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreUsuarios.getLastSelectedPathComponent();
				if (node == null || !(node.getUserObject() instanceof Usuario)) {
					// TODO --> retornar mensagem do arquivo
					JOptionPane.showMessageDialog(this, "É necessário selecionar um usuário!", "", JOptionPane.WARNING_MESSAGE);
					return;
				}
				Usuario usuarioAlterar = new Usuario();
				usuarioAlterar.setCodUsuarioUsua(((Usuario) node.getUserObject()).getCodUsuarioUsua());
				args = new Object[] { usuarioAlterar, modeloListaRelacionadosUsuario.getListaGrupos() };
			}else if(indicePainel == 3){ //Função
				baseDispatchCRUDCommand = baseDispatchCRUDCommandFuncao;
				baseDispatchCRUDCommand.setStrMetodo(CommandFuncoes.METODO_SALVAR_GRUPOS_FUNCAO);
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreFuncoes.getLastSelectedPathComponent();
				if (node == null || !(node.getUserObject() instanceof Funcao)) {
					// TODO --> retornar mensagem do arquivo
					JOptionPane.showMessageDialog(this, "É necessário selecionar uma função!", "", JOptionPane.WARNING_MESSAGE);
					return;
				}
				Funcao funcaoAlterar = new Funcao();
				funcaoAlterar.setCompId(((Funcao) node.getUserObject()).getCompId());
				args = new Object[] { funcaoAlterar, modeloListaRelacionadosFuncao.getListaGrupos() };
			}
			

		}

		if (baseDispatchCRUDCommand instanceof CommandSistemas) {
			if (BaseDispatchCRUDCommand.METODO_LISTAR.equals(baseDispatchCRUDCommand.getStrMetodo())) {
				// Listar os sistemas para atualizar a árvore
				if (sistemasCarregados) {
					if (!(JOptionPane.showConfirmDialog(this, "Deseja recarregar os sistemas?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
						return;
					}else{
						modeloListaDisponiveisSistema.setListaGrupos(Collections.EMPTY_LIST);
						modeloListaRelacionadosSistema.setListaGrupos(Collections.EMPTY_LIST);
					}
				}
				args = new Object[] { new SistemaSearchVo() };
			} else if (BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())) {

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreSistemas.getLastSelectedPathComponent();
				if (node == null || !(node.getUserObject() instanceof Sistema)) {
					// TODO --> retornar mensagem do arquivo
					JOptionPane.showMessageDialog(this, "É necessário selecionar um sistema!", "Teste", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					// Listar os grupos disponíveis e relacionados ao sistema
					List[] listasGruposSistema = (List[]) mapaGruposSistemas.get(((Sistema) node.getUserObject()));
					if (listasGruposSistema != null) {
						if (!(JOptionPane.showConfirmDialog(this, "Deseja recarregar os grupos deste sistema?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
							modeloListaRelacionadosSistema.setListaGrupos(listasGruposSistema[0]);
							modeloListaDisponiveisSistema.setListaGrupos(listasGruposSistema[1]);
							return;
						}else{
							modeloListaDisponiveisSistema.setListaGrupos(Collections.EMPTY_LIST);
							modeloListaRelacionadosSistema.setListaGrupos(Collections.EMPTY_LIST);
						}
					}
					Sistema sistema = new Sistema();
					sistema.setCodSistemaSist(((Sistema) node.getUserObject()).getCodSistemaSist());
					args = new Object[] { sistema, true, true };
				}
			}
		} else if (baseDispatchCRUDCommand instanceof CommandGrupos) {
			if (BaseDispatchCRUDCommand.METODO_LISTAR
					.equals(baseDispatchCRUDCommand.getStrMetodo())) {
				// Listar os grupos para atualizar a árvore
				args = new Object[] { new GrupoSearchVo() };
				if (gruposCarregados) {
					if (!(JOptionPane.showConfirmDialog(this, "Deseja recarregar os grupos?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
						return;
					}else{
						modeloListaDisponiveisGrupo.setListaObjetos(Collections.EMPTY_LIST);
						modeloListaRelacionadosGrupo.setListaObjetos(Collections.EMPTY_LIST);
					}
				}
			} else if (BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())) {
				// Listar os grupos disponíveis e relacionados ao sistema
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreGrupo.getLastSelectedPathComponent();
				if (node == null || !(node.getUserObject() instanceof Grupo)) {
					// TODO --> retornar mensagem do arquivo
					JOptionPane.showMessageDialog(this, "É necessário selecionar um grupo!", "Teste", JOptionPane.WARNING_MESSAGE);
					return;
				}else{
					modeloListaDisponiveisGrupo.setListaObjetos(Collections.EMPTY_LIST);
					modeloListaRelacionadosGrupo.setListaObjetos(Collections.EMPTY_LIST);
				}

				Grupo grupo = new Grupo();
				grupo.setCodGrupoGrup(((Grupo) node.getUserObject()).getCodGrupoGrup());
				String strTipoCarregamento = (String) comboOpcoesExibicaoGrupo.getSelectedItem();
				boolean carregarListaFuncoesDisponiveis = false;
				boolean carregarListaSistemasDisponiveis = false;
				boolean carregarListaUsuariosDisponiveis = false;
				Sistema sistemaFiltroPesquisaFuncoes = null;
				if (EXIBIR_GRUPO_FUNCAO.equals(strTipoCarregamento)) {
					// Verifica se está no cache antes de pesquisar
					List[] listasFuncoesGrupo = (List[]) mapaFuncoesGrupo.get(((Grupo) node.getUserObject())
									+ ((Sistema) comboSistemasFuncao.getSelectedItem()).getCodSistemaSist());
					if (listasFuncoesGrupo != null) {
						if (!(JOptionPane.showConfirmDialog(this,"Deseja recarregar as funções deste grupo para o sistema selecionado?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
							modeloListaRelacionadosGrupo.setListaObjetos(listasFuncoesGrupo[0]);
							modeloListaDisponiveisGrupo.setListaObjetos(listasFuncoesGrupo[1]);
							listaDisponiveisGrupo.setTransferHandler(funcaoTransferHandler);
							listaRelacionadosGrupo.setTransferHandler(funcaoTransferHandler);
							return;
						}
					}
					carregarListaFuncoesDisponiveis = true;
					sistemaFiltroPesquisaFuncoes = new Sistema();
					sistemaFiltroPesquisaFuncoes.setCodSistemaSist(((Sistema) comboSistemasFuncao.getSelectedItem()).getCodSistemaSist());
				} else if (EXIBIR_GRUPO_SISTEMA.equals(strTipoCarregamento)) {
					// Verifica se está no cache antes de pesquisar
					List[] listasSistemasGrupo = (List[]) mapaSistemasGrupo.get(((Grupo) node.getUserObject()));
					if (listasSistemasGrupo != null) {
						if (!(JOptionPane.showConfirmDialog(this,"Deseja recarregar os sistemas deste grupo?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
							modeloListaRelacionadosGrupo.setListaObjetos(listasSistemasGrupo[0]);
							modeloListaDisponiveisGrupo.setListaObjetos(listasSistemasGrupo[1]);
							listaDisponiveisGrupo.setTransferHandler(sistemaTransferHandler);
							listaRelacionadosGrupo.setTransferHandler(sistemaTransferHandler);
							return;
						}
					}

					carregarListaSistemasDisponiveis = true;
				} else if (EXIBIR_GRUPO_USUARIO.equals(strTipoCarregamento)) {
					// Verifica se está no cache antes de pesquisar
					List[] listasUsuariosGrupo = (List[]) mapaUsuariosGrupo.get(((Grupo) node.getUserObject()));
					if (listasUsuariosGrupo != null) {
						if (!(JOptionPane.showConfirmDialog(this, "Deseja recarregar os usuários deste grupo?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
							modeloListaRelacionadosGrupo.setListaObjetos(listasUsuariosGrupo[0]);
							modeloListaDisponiveisGrupo.setListaObjetos(listasUsuariosGrupo[1]);
							listaDisponiveisGrupo.setTransferHandler(usuarioTransferHandler);
							listaRelacionadosGrupo.setTransferHandler(usuarioTransferHandler);
							return;
						}
					}

					carregarListaUsuariosDisponiveis = true;
				}

				// objRetorno = new Object[]{grupo, listaFuncoesDisponiveis,
				// listaSistemasDisponiveis, listaUsuariosDisponiveis};
				args = new Object[] { grupo, carregarListaFuncoesDisponiveis,
						carregarListaFuncoesDisponiveis,
						carregarListaSistemasDisponiveis,
						carregarListaSistemasDisponiveis,
						carregarListaUsuariosDisponiveis,
						carregarListaUsuariosDisponiveis,
						sistemaFiltroPesquisaFuncoes };
			}
		}else if(baseDispatchCRUDCommand instanceof CommandUsuarios){
			if (BaseDispatchCRUDCommand.METODO_LISTAR
					.equals(baseDispatchCRUDCommand.getStrMetodo())) 
			{
				// Listar os usuários para atualizar a árvore
				if (usuariosCarregados) {
					if (!(JOptionPane.showConfirmDialog(this, "Deseja recarregar os usuários?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
						return;
					}else{
						modeloListaDisponiveisUsuario.setListaGrupos(Collections.EMPTY_LIST);
						modeloListaRelacionadosUsuario.setListaGrupos(Collections.EMPTY_LIST);
					}
				}
				args = new Object[] { new UsuarioSearchVo() };
			} else if (BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreUsuarios.getLastSelectedPathComponent();
					if (node == null || !(node.getUserObject() instanceof Usuario)) {
						// TODO --> retornar mensagem do arquivo
						JOptionPane.showMessageDialog(this, "É necessário selecionar um usuário!", "", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						// Listar os grupos disponíveis e relacionados ao usuário
						List[] listasGruposUsuario = (List[]) mapaGruposUsuarios.get(((Usuario) node.getUserObject()));
						if (listasGruposUsuario != null) {
							if (!(JOptionPane.showConfirmDialog(this, "Deseja recarregar os grupos deste usuário?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
								modeloListaRelacionadosUsuario.setListaGrupos(listasGruposUsuario[0]);
								modeloListaDisponiveisUsuario.setListaGrupos(listasGruposUsuario[1]);
								return;
							}else{
								modeloListaDisponiveisUsuario.setListaGrupos(Collections.EMPTY_LIST);
								modeloListaRelacionadosUsuario.setListaGrupos(Collections.EMPTY_LIST);
							}
						}
						Usuario usuario = new Usuario();
						usuario.setCodUsuarioUsua(((Usuario) node.getUserObject()).getCodUsuarioUsua());
						args = new Object[] { usuario, true, true };
					}
			}
		}else if(baseDispatchCRUDCommand instanceof CommandFuncoes){
			if (BaseDispatchCRUDCommand.METODO_LISTAR
					.equals(baseDispatchCRUDCommand.getStrMetodo())) 
			{
				// Listar as funçõs para atualizar a árvore
				if (funcoesCarregadas) {
					if (!(JOptionPane.showConfirmDialog(this, "Deseja recarregar as funções?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
						return;
					}else{
						modeloListaDisponiveisFuncao.setListaGrupos(Collections.EMPTY_LIST);
						modeloListaRelacionadosFuncao.setListaGrupos(Collections.EMPTY_LIST);
					}
				}
				args = new Object[] { new FuncaoSearchVo() };
			} else if (BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreFuncoes.getLastSelectedPathComponent();
					if (node == null || !(node.getUserObject() instanceof Funcao)) {
						// TODO --> retornar mensagem do arquivo
						JOptionPane.showMessageDialog(this, "É necessário selecionar uma função!", "", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						// Listar os grupos disponíveis e relacionados ao usuário
						List[] listasGruposFuncao = (List[]) mapaGruposFuncao.get(((Funcao) node.getUserObject()));
						if (listasGruposFuncao != null) {
							if (!(JOptionPane.showConfirmDialog(this, "Deseja recarregar os grupos desta função?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
								modeloListaRelacionadosFuncao.setListaGrupos(listasGruposFuncao[0]);
								modeloListaDisponiveisFuncao.setListaGrupos(listasGruposFuncao[1]);
								return;
							}else{
								modeloListaDisponiveisFuncao.setListaGrupos(Collections.EMPTY_LIST);
								modeloListaRelacionadosFuncao.setListaGrupos(Collections.EMPTY_LIST);
							}
						}
						Funcao funcao = new Funcao();
						funcao.setCompId(((Funcao) node.getUserObject()).getCompId());
						args = new Object[] { funcao, true, true };
					}
			}
		}

		if (baseDispatchCRUDCommand != null) {
			try {
				this.desabilitarBotoes();
				/*
				 * Se estiver pesquisando atualiza o número de páginas que o
				 * usuário digitou
				 */
				if (this.atualizarGrid(baseDispatchCRUDCommand.getStrMetodo())) {
					/*
					 * this.baseDispatchCRUDCommand.getPagina().setRegistrosPorPagina(
					 * ((Long)txtQtdePaginas.getValue()).longValue() );
					 */
				}

				Object objRetorno[] = baseDispatchCRUDCommand.executar(args);

				if (baseDispatchCRUDCommand instanceof CommandSistemas) {
					if (BaseDispatchCRUDCommand.METODO_LISTAR.equals(baseDispatchCRUDCommand.getStrMetodo())) {
						// Atualiza a árvore de sistemas
						Pagina pagina = (Pagina) objRetorno[0];
						DefaultMutableTreeNode no = null;
						Iterator itSistemas = pagina.getListObjects().iterator();
						noRaizSistemas.removeAllChildren();
						modeloArvoreSistemas = new DefaultTreeModel(noRaizSistemas);
						noRaizSistemas.add(noBotaoAtualizacao);

						
						// Atualiza o combo de sistemas
						((DefaultComboBoxModel) comboSistemasFuncao.getModel()).removeAllElements();
						while (itSistemas.hasNext()) {
							no = new DefaultMutableTreeNode();
							Sistema sistema = (Sistema) itSistemas.next();
							no.setUserObject(sistema);
							noRaizSistemas.add(no);
							((DefaultComboBoxModel) comboSistemasFuncao.getModel()).addElement(sistema);
						}
						arvoreSistemas.setModel(modeloArvoreSistemas);
						sistemasCarregados = true;

					} else if (BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())) {
						Sistema sistema = (Sistema) objRetorno[0];
						List<Grupo> listaGrupos = new LinkedList<Grupo>(sistema.getGrupos());
						List<Grupo> listaGruposNaoRelacionados = (List<Grupo>) objRetorno[1];
						modeloListaRelacionadosSistema.setListaGrupos(listaGrupos);
						modeloListaDisponiveisSistema.setListaGrupos(listaGruposNaoRelacionados);
						// Guarda no cache
						mapaGruposSistemas.put(sistema, new List[] { listaGrupos, listaGruposNaoRelacionados });
					}
				} else if (baseDispatchCRUDCommand instanceof CommandGrupos) {
					if (BaseDispatchCRUDCommand.METODO_LISTAR.equals(baseDispatchCRUDCommand.getStrMetodo())) {
						// Atualiza a árvore de grupos
						Pagina pagina = (Pagina) objRetorno[0];
						DefaultMutableTreeNode no = null;
						Iterator itGrupos = pagina.getListObjects().iterator();
						noRaizGrupos.removeAllChildren();
						modeloArvoreGrupos = new DefaultTreeModel(noRaizGrupos);
						noRaizGrupos.add(noBotaoAtualizacao);
						while (itGrupos.hasNext()) {
							no = new DefaultMutableTreeNode();
							no.setUserObject(itGrupos.next());
							noRaizGrupos.add(no);
						}
						arvoreGrupo.setModel(modeloArvoreGrupos);
						gruposCarregados = true;
						
						
					} else if (BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO
							.equals(baseDispatchCRUDCommand.getStrMetodo())) {
						Grupo grupo = (Grupo) objRetorno[0];
						// objRetorno = new Object[]{grupo,
						// listaFuncoesDisponiveis, listaSistemasDisponiveis,
						// listaUsuariosDisponiveis};
						String strTipoCarregamento = (String) comboOpcoesExibicaoGrupo.getSelectedItem();
						if (EXIBIR_GRUPO_FUNCAO.equals(strTipoCarregamento)) {
							List listaFuncoes = new LinkedList(grupo.getFuncoes());
							List listaFuncoesNaoRelacionadas = (List) objRetorno[1];
							modeloListaDisponiveisGrupo.setListaObjetos(listaFuncoesNaoRelacionadas);
							modeloListaRelacionadosGrupo.setListaObjetos(listaFuncoes);
							listaDisponiveisGrupo.setTransferHandler(funcaoTransferHandler);
							listaRelacionadosGrupo.setTransferHandler(funcaoTransferHandler);
							// Guarda no cache
							mapaFuncoesGrupo.put(grupo + ((Sistema) comboSistemasFuncao.getSelectedItem()).getCodSistemaSist(), new List[] { listaFuncoes, listaFuncoesNaoRelacionadas });
						} else if (EXIBIR_GRUPO_SISTEMA.equals(strTipoCarregamento)) {
							List listaSistemas = new LinkedList(grupo.getSistemas());
							List listaSistemasNaoRelacionados = (List) objRetorno[2];
							modeloListaDisponiveisGrupo.setListaObjetos(listaSistemasNaoRelacionados);
							modeloListaRelacionadosGrupo.setListaObjetos(listaSistemas);
							listaDisponiveisGrupo.setTransferHandler(sistemaTransferHandler);
							listaRelacionadosGrupo.setTransferHandler(sistemaTransferHandler);
							mapaSistemasGrupo.put(grupo, new List[] { listaSistemas, listaSistemasNaoRelacionados });
						} else if (EXIBIR_GRUPO_USUARIO
								.equals(strTipoCarregamento)) {
							List listaUsuarios = new LinkedList(grupo.getUsuarios());
							List listaUsuariosNaoRelacionados = (List) objRetorno[3];
							modeloListaDisponiveisGrupo.setListaObjetos(listaUsuariosNaoRelacionados);
							modeloListaRelacionadosGrupo.setListaObjetos(listaUsuarios);
							listaDisponiveisGrupo.setTransferHandler(usuarioTransferHandler);
							listaRelacionadosGrupo.setTransferHandler(usuarioTransferHandler);
							// Guarda no cache
							mapaUsuariosGrupo.put(grupo, new List[] { listaUsuarios, listaUsuariosNaoRelacionados });
						}
					}
				}else if(baseDispatchCRUDCommand instanceof CommandUsuarios){
					if (BaseDispatchCRUDCommand.METODO_LISTAR.equals(baseDispatchCRUDCommand.getStrMetodo())) {
						// Atualiza a árvore de usuários
						Pagina pagina = (Pagina) objRetorno[0];
						DefaultMutableTreeNode no = null;
						Iterator itUsuarios = pagina.getListObjects().iterator();
						modeloArvoreUsuarios = new DefaultTreeModel(noRaizUsuarios);
						noRaizUsuarios.removeAllChildren();
						noRaizUsuarios.add(noBotaoAtualizacao);						
						while (itUsuarios.hasNext()) {
							no = new DefaultMutableTreeNode();
							Usuario usuario = (Usuario) itUsuarios.next();
							no.setUserObject(usuario);
							noRaizUsuarios.add(no);
						}
						usuariosCarregados = true;
						arvoreUsuarios.setModel(modeloArvoreUsuarios);
					} else if (BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())) {
						Usuario usuario = (Usuario) objRetorno[0];
						List<Grupo> listaGrupos = new LinkedList<Grupo>(usuario.getGrupos());
						List<Grupo> listaGruposNaoRelacionados = (List<Grupo>) objRetorno[1];
						modeloListaRelacionadosUsuario.setListaGrupos(listaGrupos);
						modeloListaDisponiveisUsuario.setListaGrupos(listaGruposNaoRelacionados);
						// Guarda no cache
						mapaGruposUsuarios.put(usuario, new List[] { listaGrupos, listaGruposNaoRelacionados });
					}
					
				}else if(baseDispatchCRUDCommand instanceof CommandFuncoes){
					if (BaseDispatchCRUDCommand.METODO_LISTAR.equals(baseDispatchCRUDCommand.getStrMetodo())) {
						// Atualiza a árvore de funções
						Pagina pagina = (Pagina) objRetorno[0];
						DefaultMutableTreeNode no = null;
						DefaultMutableTreeNode noSistemaAtual = null;
						Iterator itFuncoes = pagina.getListObjects().iterator();
						noRaizFuncoes.removeAllChildren();
						noRaizFuncoes.add(noBotaoAtualizacao);						
						Map<Sistema, DefaultMutableTreeNode> mapaSistemas = new HashMap<Sistema, DefaultMutableTreeNode>();
						while (itFuncoes.hasNext()) {
							no = new DefaultMutableTreeNode();
							Funcao funcao = (Funcao) itFuncoes.next();
							no.setUserObject(funcao);
							noSistemaAtual = mapaSistemas.get(funcao.getSistema());
							if(noSistemaAtual == null)
							{
								noSistemaAtual = new DefaultMutableTreeNode(funcao.getSistema());
								mapaSistemas.put(funcao.getSistema(), noSistemaAtual);
							}
							noSistemaAtual.add(no);
						}
						List listaSistemas = new LinkedList(mapaSistemas.keySet());
						Collections.sort(listaSistemas
										,
										new Comparator(){
							public int compare(Object o1, Object o2) {
								String codSistemaUm = "";
								String codSistemaDois = "";
								int comp = 0;
								Sistema sistemaUm = (Sistema) o1;
								Sistema sistemaDois = (Sistema) o2;
								if(sistemaUm != null && sistemaUm.getCodSistemaSist() != null){
									codSistemaUm = sistemaUm.getCodSistemaSist().trim().toUpperCase();
								}
								if(sistemaDois != null && sistemaDois.getCodSistemaSist() != null){
									codSistemaDois = sistemaDois.getCodSistemaSist().trim().toUpperCase();
								}
								comp = codSistemaUm.compareTo(codSistemaDois);
								return comp;
							}
						});
										
						Iterator itSistemas = listaSistemas.iterator();
						while(itSistemas.hasNext()){
							noRaizFuncoes.add((DefaultMutableTreeNode)mapaSistemas.get(itSistemas.next()));
						}

						funcoesCarregadas = true;
						modeloArvoreFuncoes = new DefaultTreeModel(noRaizFuncoes);
						arvoreFuncoes.setModel(modeloArvoreFuncoes);
						
					} else if (BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())) {
						Funcao funcao = (Funcao) objRetorno[0];
						List<Grupo> listaGrupos = new LinkedList<Grupo>(funcao.getGrupos());
						List<Grupo> listaGruposNaoRelacionados = (List<Grupo>) objRetorno[1];
						modeloListaRelacionadosFuncao.setListaGrupos(listaGrupos);
						modeloListaDisponiveisFuncao.setListaGrupos(listaGruposNaoRelacionados);
						// Guarda no cache
						mapaGruposFuncao.put(funcao, new List[] { listaGrupos, listaGruposNaoRelacionados });
					}
					
				}

				if (this.exibirMsgConfirmacao(baseDispatchCRUDCommand.getStrMetodo())) {
					JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.realizada.com.sucesso"),
									RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.realizada.com.sucesso.titulo.janela"), JOptionPane.INFORMATION_MESSAGE);
				}
				
				this.atualizarStatusBotoesManutencao(baseDispatchCRUDCommand.getStrMetodo());
			} catch (CommandException commandException) {
				super.tratarMensagemErro(commandException);
				/*
				 * Se estava numa operação de gravação volta para o status
				 * anterior
				 */
				if (BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo())) {
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO);
					baseDispatchCRUDCommand.setMetodoAnterior("");
				} else if (BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())) {
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
					baseDispatchCRUDCommand.setMetodoAnterior("");
				}
			}
		} else {
			JOptionPane.showMessageDialog(this,
							RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.nao.conigurada"),
							RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.nao.conigurada.titulo.janela"),
							JOptionPane.WARNING_MESSAGE);
		    this.atualizarStatusBotoesManutencao(baseDispatchCRUDCommand.getStrMetodo());
		}
	}

	public void desabilitarBotoes() {
		cmdSalvarAssociacoes.setEnabled(false);
		painelOpcoes.setEnabled(false);
		cmdPesquisarFuncoes.setEnabled(false);
		comboOpcoesExibicaoGrupo.setEnabled(false);
	}

	public boolean atualizarGrid(String strMetodo) {
		return false;
	}

	public boolean exibirMsgConfirmacao(String strmetodo) {
		if (baseDispatchCRUDCommand != null
				&& (
						CommandSistemas.METODO_SALVAR_GRUPOS_SISTEMA.equals(baseDispatchCRUDCommand.getStrMetodo())
						|| CommandUsuarios.METODO_SALVAR_GRUPOS_USUARIO.equals(baseDispatchCRUDCommand.getStrMetodo()) 
						|| CommandFuncoes.METODO_SALVAR_GRUPOS_FUNCAO.equals(baseDispatchCRUDCommand.getStrMetodo()) 
				)
			) {
			// usuário clicou salvar para os grupos do sistema ou do usuário selecionado
			return true;
		}
		if (baseDispatchCRUDCommand != null
				&& (CommandGrupos.METODO_SALVAR_FUNCOES_GRUPO.equals(baseDispatchCRUDCommand.getStrMetodo())
						|| CommandGrupos.METODO_SALVAR_SISTEMAS_GRUPO.equals(baseDispatchCRUDCommand.getStrMetodo()) || CommandGrupos.METODO_SALVAR_USUARIOS_GRUPO.equals(baseDispatchCRUDCommand.getStrMetodo()))) {
			// Usuário clicou no botão salvar na aba grupo
			return true;
		}

		return false;
	}

	public void atualizarStatusBotoesManutencao(String strMetodo) {
		painelOpcoes.setEnabled(true);
		cmdPesquisarFuncoes.setEnabled(true);
		comboOpcoesExibicaoGrupo.setEnabled(true);
		atualizarStatusBotoesManutencaoControleSeguranca();		
	}
	
	public void atualizarStatusBotoesManutencaoControleSeguranca(){
		int sel = this.painelOpcoes.getSelectedIndex();
		if(sel == 0){//Aba sistemas
			//Verifica se o usuário pode alterar esta aba
			if(!Constantes.COD_USR_ADM.equals(GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua())){
				if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_ALT_REL_SISTEMA) == null)
				{
					cmdSalvarAssociacoes.setEnabled(false);
				}else{
					cmdSalvarAssociacoes.setEnabled(true);
				}
			}else{
				cmdSalvarAssociacoes.setEnabled(true);
			}
		}else if(sel == 1){	//Aba grupo
			//Verifica se o usuário pode alterar esta aba
			if(!Constantes.COD_USR_ADM.equals(GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua())){
				if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_ALT_REL_GRUPO) == null)
				{
					cmdSalvarAssociacoes.setEnabled(false);
				}else{
					cmdSalvarAssociacoes.setEnabled(true);
				}
			}else{
				cmdSalvarAssociacoes.setEnabled(true);
			}
		}else if(sel == 2){	//Aba usuário
			//Verifica se o usuário pode alterar esta aba
			if(!Constantes.COD_USR_ADM.equals(GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua())){
				if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_ALT_REL_USUARIO) == null)
				{
					cmdSalvarAssociacoes.setEnabled(false);
				}else{
					cmdSalvarAssociacoes.setEnabled(true);
				}
			}else{
				cmdSalvarAssociacoes.setEnabled(true);
				
			}
		}else if(sel == 3){	 //Aba função
			//Verifica se o usuário pode alterar esta aba
			if(!Constantes.COD_USR_ADM.equals(GerenciadorJanelas.getControleSegurancaVo().getUsuarioConectado().getCodUsuarioUsua())){
				if(GerenciadorJanelas.getControleSegurancaVo().getMapaFuncoesUsuario().get(Constantes.FUNC_SEC_ALT_REL_FUNCAO) == null)
				{
					cmdSalvarAssociacoes.setEnabled(false);
				}else{
					cmdSalvarAssociacoes.setEnabled(true);
				}
			}else{
				cmdSalvarAssociacoes.setEnabled(true);
			}
		}	
		
	}
}
