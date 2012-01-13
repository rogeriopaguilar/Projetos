package dnsec.web.app.jsf.bean;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIData;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.DropEvent;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;

import dnsec.modulos.cadastros.funcao.business.CommandFuncoes;
import dnsec.modulos.cadastros.funcao.vo.FuncaoSearchVo;
import dnsec.modulos.cadastros.grupo.business.CommandGrupos;
import dnsec.modulos.cadastros.grupo.vo.GrupoSearchVo;
import dnsec.modulos.cadastros.sistema.business.CommandSistemas;
import dnsec.modulos.cadastros.sistema.vo.SistemaSearchVo;
import dnsec.modulos.cadastros.usuario.business.CommandUsuarios;
import dnsec.modulos.cadastros.usuario.vo.UsuarioSearchVo;
import dnsec.modulos.controle.seguranca.vo.ControleSegurancaVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.database.hibernate.Grupo;
import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.factory.CommandFactory;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.Pagina;
import dnsec.web.app.jsf.bean.associacos.nos.AssociacaoGrupoNo;
import dnsec.web.app.jsf.bean.associacos.nos.FuncaoNo;
import dnsec.web.app.jsf.bean.associacos.nos.GrupoNo;
import dnsec.web.app.jsf.bean.associacos.nos.SistemaNo;
import dnsec.web.app.jsf.bean.associacos.nos.UsuarioNo;
import dnsec.web.util.ErroUtil;



public class AssociacoesBean {

	private static final int TAB_SISTEMAS = 0, TAB_GRUPOS=1, TAB_USUARIOS = 2, TAB_FUNCOES = 3;
	private int tabSelecionada = TAB_SISTEMAS;
	private String METODO = null;
	
	//Aba sistemas
	private BaseDispatchCRUDCommand baseDispatchCRUDCommandSistemas = CommandFactory.getCommand(CommandFactory.COMMAND_SISTEMAS);
	private DataModel listaSistemas = new ListDataModel();
	private boolean sistemasCarregados = false;
    private TreeNode noRaizSistemas = null;	
    private DataModel listaGruposDisponiveis = new ListDataModel();
    private DataModel listaGruposRelacionados = new ListDataModel();
    private Sistema sistemaSelecionado = null;
    private UIData modelo = null;
    
    //Aba grupos
    private DataModel listaGrupos = new ListDataModel();
    private TreeNode noRaizGrupos = null;	
	private BaseDispatchCRUDCommand baseDispatchCRUDCommandGrupos = CommandFactory.getCommand(CommandFactory.COMMAND_GRUPOS);
    private DataModel listaFuncoesDisponiveisGrupo = new ListDataModel();
    private DataModel listaFuncoesRelacionadasGrupo = new ListDataModel();
    private DataModel listaSistemasDisponiveisGrupo = new ListDataModel();
    private DataModel listaSistemasRelacionadosGrupo = new ListDataModel();
    private DataModel listaUsuariosDisponiveisGrupo = new ListDataModel();
    private DataModel listaUsuariosRelacionadosGrupo = new ListDataModel();
    private DataModel listaDisponiveisGrupo = new ListDataModel();
    private DataModel listaRelacionadosGrupo = new ListDataModel();
    private boolean gruposCarregados = false;
    private static int TIPO_CARREGAMENTO_FUNCOES = 1, TIPO_CARREGAMENTO_SISTEMAS = 2, TIPO_CARREGAMENTO_USUARIOS = 3; 
    private int tipoCarregamentoGrupo = TIPO_CARREGAMENTO_FUNCOES;
	private List<SelectItem> listaSistemasSelect = new LinkedList<SelectItem>();
	private String codSistemaSelecionadoGrupo = null;
	private Grupo grupoSelecionado = null;
    //Final aba grupos

	//Aba usuários
	private BaseDispatchCRUDCommand baseDispatchCRUDCommandUsuarios = CommandFactory.getCommand(CommandFactory.COMMAND_USUARIOS);
	private DataModel listaUsuarios = new ListDataModel();
	private boolean usuariosCarregados = false;
    private TreeNode noRaizUsuarios = null;	
    private DataModel listaGruposDisponiveisUsuario = new ListDataModel();
    private DataModel listaGruposRelacionadosUsuario = new ListDataModel();
    private Usuario usuarioSelecionado = null;
	//Final aba usuários
    
    //Aba funções
	private BaseDispatchCRUDCommand baseDispatchCRUDCommandFuncoes = CommandFactory.getCommand(CommandFactory.COMMAND_FUNCOES);
	private DataModel listaFuncoes = new ListDataModel();
	private boolean funcoesCarregadas = false;
    private TreeNode noRaizFuncoes = null;	
    private DataModel listaGruposDisponiveisFuncao  = new ListDataModel();
    private DataModel listaGruposRelacionadosFuncao = new ListDataModel();
    private Funcao funcaoSelecionada = null;
    //Final aba funções
	
	public BaseDispatchCRUDCommand getBaseDispatchCRUDCommandUsuarios() {
		return baseDispatchCRUDCommandUsuarios;
	}

	public void setBaseDispatchCRUDCommandUsuarios(
			BaseDispatchCRUDCommand baseDispatchCRUDCommandUsuarios) {
		this.baseDispatchCRUDCommandUsuarios = baseDispatchCRUDCommandUsuarios;
	}

	public DataModel getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(DataModel listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public boolean isUsuariosCarregados() {
		return usuariosCarregados;
	}

	public void setUsuariosCarregados(boolean usuariosCarregados) {
		this.usuariosCarregados = usuariosCarregados;
	}

	public TreeNode getNoRaizUsuarios() {
		return noRaizUsuarios;
	}

	public void setNoRaizUsuarios(TreeNode noRaizUsuarios) {
		this.noRaizUsuarios = noRaizUsuarios;
	}

	public DataModel getListaGruposDisponiveisUsuario() {
		return listaGruposDisponiveisUsuario;
	}

	public void setListaGruposDisponiveisUsuario(
			DataModel listaGruposDisponiveisUsuario) {
		this.listaGruposDisponiveisUsuario = listaGruposDisponiveisUsuario;
	}

	public DataModel getListaGruposRelacionadosUsuario() {
		return listaGruposRelacionadosUsuario;
	}

	public void setListaGruposRelacionadosUsuario(
			DataModel listaGruposRelacionadosUsuario) {
		this.listaGruposRelacionadosUsuario = listaGruposRelacionadosUsuario;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	public List<SelectItem> getListaSistemasSelect() {
		return listaSistemasSelect;
	}

	public void setListaSistemasSelect(List<SelectItem> listaSistemasSelect) {
		this.listaSistemasSelect = listaSistemasSelect;
	}

	public DataModel getListaFuncoesDisponiveisGrupo() {
		return listaFuncoesDisponiveisGrupo;
	}

	public void setListaFuncoesDisponiveisGrupo(
			DataModel listaFuncoesDisponiveisGrupo) {
		this.listaFuncoesDisponiveisGrupo = listaFuncoesDisponiveisGrupo;
	}

	public DataModel getListaFuncoesRelacionadasGrupo() {
		return listaFuncoesRelacionadasGrupo;
	}

	public void setListaFuncoesRelacionadasGrupo(
			DataModel listaFuncoesRelacionadasGrupo) {
		this.listaFuncoesRelacionadasGrupo = listaFuncoesRelacionadasGrupo;
	}

	public DataModel getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(DataModel listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public DataModel getListaSistemas() {
		return listaSistemas;
	}

	public void setListaSistemas(DataModel listaSistemas) {
		this.listaSistemas = listaSistemas;
	}

	public DataModel getListaSistemasDisponiveisGrupo() {
		return listaSistemasDisponiveisGrupo;
	}

	public void setListaSistemasDisponiveisGrupo(
			DataModel listaSistemasDisponiveisGrupo) {
		this.listaSistemasDisponiveisGrupo = listaSistemasDisponiveisGrupo;
	}

	public DataModel getListaSistemasRelacionadosGrupo() {
		return listaSistemasRelacionadosGrupo;
	}

	public void setListaSistemasRelacionadosGrupo(
			DataModel listaSistemasRelacionadosGrupo) {
		this.listaSistemasRelacionadosGrupo = listaSistemasRelacionadosGrupo;
	}

	public DataModel getListaUsuariosDisponiveisGrupo() {
		return listaUsuariosDisponiveisGrupo;
	}

	public void setListaUsuariosDisponiveisGrupo(
			DataModel listaUsuariosDisponiveisGrupo) {
		this.listaUsuariosDisponiveisGrupo = listaUsuariosDisponiveisGrupo;
	}

	public DataModel getListaUsuariosRelacionadosGrupo() {
		return listaUsuariosRelacionadosGrupo;
	}

	public void setListaUsuariosRelacionadosGrupo(
			DataModel listaUsuariosRelacionadosGrupo) {
		this.listaUsuariosRelacionadosGrupo = listaUsuariosRelacionadosGrupo;
	}

	public TreeNode getNoRaizGrupos() {
		return noRaizGrupos;
	}

	public void setNoRaizGrupos(TreeNode noRaizGrupos) {
		this.noRaizGrupos = noRaizGrupos;
	}

	public UIData getModelo() {
		return modelo;
	}

	public void setModelo(UIData modelo) {
		this.modelo = modelo;
	}

	public DataModel getListaGruposDisponiveis() {
		return listaGruposDisponiveis;
	}

	public void setListaGruposDisponiveis(DataModel listaGruposDisponiveis) {
		this.listaGruposDisponiveis = listaGruposDisponiveis;
	}


	public DataModel getListaGruposRelacionados() {
		return listaGruposRelacionados;
	}

	public void setListaGruposRelacionados(DataModel listaGruposRelacionados) {
		this.listaGruposRelacionados = listaGruposRelacionados;
	}

	public AssociacoesBean()
    {
		noRaizSistemas = new TreeNodeImpl();
		noRaizGrupos   = new TreeNodeImpl();
		noRaizUsuarios = new TreeNodeImpl();
		noRaizFuncoes  = new TreeNodeImpl();
    }
	
	/*********************************************Aba Sistemas******************************************************/
	protected void carregarSistemas()
	{
		baseDispatchCRUDCommandSistemas.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
		baseDispatchCRUDCommandSistemas.getPagina().setRegistrosPorPagina(Long.MAX_VALUE);
		baseDispatchCRUDCommandSistemas.getPagina().setProximaAcao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
		Object args[] = new Object[] { new SistemaSearchVo() };
		try{
			//TODO --> Corrigir quando adicionar o controle de segurança
			Usuario usuario = new Usuario();
			usuario.setCodUsuarioUsua(Constantes.COD_USR_ADM);
			GerenciadorJanelas.getInstance().setControleSegurancaVo(new ControleSegurancaVo(usuario, Collections.EMPTY_MAP, Collections.EMPTY_MAP, Collections.EMPTY_MAP));
			Object[] objRetorno = baseDispatchCRUDCommandSistemas.executar(args);
			if(!sistemasCarregados)
			{
				// Atualiza a árvore de sistemas
				Pagina pagina = (Pagina) objRetorno[0];
				TreeNode no = null;
				Iterator itSistemas = pagina.getListObjects().iterator();
	
				int cont = 1;
				TreeNode noSistemas = new TreeNodeImpl();
				SistemaNo sistemaNo = new SistemaNo();
				sistemaNo.setSistema(new Sistema());
				sistemaNo.getSistema().setCodSistemaSist("Sistemas");
				sistemaNo.setTipo(SistemaNo.TIPO_NO_RAIZ);
				noSistemas.setData(sistemaNo);
				no = new TreeNodeImpl();
				sistemaNo = new SistemaNo();
				sistemaNo.setTipo(SistemaNo.TIPO_NO_ATUALIZACAO);
				no.setData(sistemaNo);
				noSistemas.addChild(cont++, no);
				while (itSistemas.hasNext()) 
				{
					no = new TreeNodeImpl();
					sistemaNo = new SistemaNo();
					sistemaNo.setTipo(SistemaNo.TIPO_NO_FILHO);
					Sistema sistema = (Sistema) itSistemas.next();
					SelectItem selectItemSistema = new SelectItem(sistema.getCodSistemaSist(), sistema.getCodSistemaSist());
					listaSistemasSelect.add(selectItemSistema);
					sistemaNo.setSistema(sistema);
					no.setData(sistemaNo);
					noSistemas.addChild(cont++, no);
				}
				noRaizSistemas.addChild(1, noSistemas);
				sistemasCarregados = true;
			}
		}catch(CommandException commandException){
			commandException.printStackTrace();
			tratarErro(commandException);
		}
		
		
	}

	public void carregarGruposSistemaSelecionado(NodeSelectedEvent evt)
	{
		try
		{
			baseDispatchCRUDCommandSistemas.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
			this.listaGruposDisponiveis.setWrappedData(Collections.EMPTY_LIST);
			this.listaGruposRelacionados.setWrappedData(Collections.EMPTY_LIST);
			this.sistemaSelecionado = null;
			if(((HtmlTree)evt.getSource()).getRowData() instanceof SistemaNo
					&& 
					((SistemaNo)((HtmlTree)evt.getSource()).getRowData()).getTipo() == SistemaNo.TIPO_NO_FILHO
			)
			{
				Sistema sistema = ((SistemaNo)((HtmlTree)evt.getSource()).getRowData()).getSistema();
				this.sistemaSelecionado = sistema;
				Object[] args = new Object[] { sistema, true, true };
				Object objRetorno[] = baseDispatchCRUDCommandSistemas.executar(args);
				sistema = (Sistema) objRetorno[0];
				List<Grupo> listaGrupos = new LinkedList<Grupo>(sistema.getGrupos());
				List<Grupo> listaGruposNaoRelacionados = (List<Grupo>) objRetorno[1];
				this.listaGruposDisponiveis.setWrappedData(listaGruposNaoRelacionados);
				this.listaGruposRelacionados.setWrappedData(listaGrupos);
			}else
			{
				listarAssociacoesInicio();
			}
		}catch(CommandException commandException){
			commandException.printStackTrace();
			tratarErro(commandException);
		}
		
	}	

	public void transferirSistemaDisponivelParaSelecionado(DropEvent evt)
	{
		Object obj = evt.getDragValue();
		((List)listaGruposDisponiveis.getWrappedData()).remove(obj);
		((List)listaGruposRelacionados.getWrappedData()).add(obj);
	}

	public void transferirSistemaSelecionadoParaDisponivel(DropEvent evt)
	{
		Object obj = evt.getDragValue();
		((List)listaGruposDisponiveis.getWrappedData()).add(obj);
		((List)listaGruposRelacionados.getWrappedData()).remove(obj);
	}

	public void salvarRelacionamentos()
	{
		if(this.sistemaSelecionado != null)
		{
			try
			{
				baseDispatchCRUDCommandSistemas.setStrMetodo(CommandSistemas.METODO_SALVAR_GRUPOS_SISTEMA);
				Sistema sistemaAlterar = new Sistema();
				sistemaAlterar.setCodSistemaSist(this.sistemaSelecionado.getCodSistemaSist());
				Object[] args = new Object[] { sistemaAlterar, this.listaGruposRelacionados.getWrappedData() };
				baseDispatchCRUDCommandSistemas.executar(args);
			}catch(CommandException commandException){
				commandException.printStackTrace();
				tratarErro(commandException);
			}
		}
	}
	
	
	public String listarAssociacoesInicio()
	{
		sistemasCarregados = false;
		carregarSistemas();
		listaGruposDisponiveis.setWrappedData(Collections.EMPTY_LIST);
		listaGruposRelacionados.setWrappedData(Collections.EMPTY_LIST);
		sistemaSelecionado = null;
		tabSelecionada = TAB_SISTEMAS;
		grupoSelecionado = null;
		listaDisponiveisGrupo.setWrappedData(Collections.EMPTY_LIST);
		listaRelacionadosGrupo.setWrappedData(Collections.EMPTY_LIST);
		return "ASSOCIACOES_INICIO";
	}

	
	protected void tratarErro(CommandException commandException)
	{
		boolean erroEsperado = ErroUtil.tratarErro(commandException);
 		if(!erroEsperado)
 		{
			//this.METODO = BaseDispatchCRUDCommand.METODO_LISTAR;
			this.METODO = ErroUtil.METODO_ERRO;
 		}
	}
	
	public TreeNode getNoRaizSistemas() {
		return noRaizSistemas;
	}

	public void setNoRaizSistemas(TreeNode noRaizSistemas) {
		this.noRaizSistemas = noRaizSistemas;
	}

	public void tabSistemasSelecionada(ActionEvent evt)
	{
		tabSelecionada = TAB_SISTEMAS;
	}

	public void tabGruposSelecionada(ActionEvent evt)
	{
		tabSelecionada = TAB_GRUPOS;
		carregarGrupos();
	}
	
	public void tabUsuariosSelecionada(ActionEvent evt)
	{
		tabSelecionada = TAB_USUARIOS;
		carregarUsuarios();
		
	}
	
	public void tabFuncoesSelecionada(ActionEvent evt)
	{
		tabSelecionada = TAB_FUNCOES;
		carregarFuncoes();
	}

	public Sistema getSistemaSelecionado() {
		return sistemaSelecionado;
	}

	public void setSistemaSelecionado(Sistema sistemaSelecionado) {
		this.sistemaSelecionado = sistemaSelecionado;
	}
	/*********************************************Final Aba Sistemas******************************************************/
	
	
	/***********************Aba grupos******************************************************/
	protected void carregarGrupos()
	{
		carregarSistemas();
		baseDispatchCRUDCommandGrupos.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
		baseDispatchCRUDCommandGrupos.getPagina().setRegistrosPorPagina(Long.MAX_VALUE);
		baseDispatchCRUDCommandGrupos.getPagina().setProximaAcao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
		Object args[] = new Object[] { new GrupoSearchVo() };
		try{
			//TODO --> Corrigir quando adicionar o controle de segurança
			Usuario usuario = new Usuario();
			usuario.setCodUsuarioUsua(Constantes.COD_USR_ADM);
			GerenciadorJanelas.getInstance().setControleSegurancaVo(new ControleSegurancaVo(usuario, Collections.EMPTY_MAP, Collections.EMPTY_MAP, Collections.EMPTY_MAP));
			Object[] objRetorno = baseDispatchCRUDCommandGrupos.executar(args);
			if(!gruposCarregados)
			{
				// Atualiza a árvore de grupos
				Pagina pagina = (Pagina) objRetorno[0];
				TreeNode no = null;
				Iterator itGrupos = pagina.getListObjects().iterator();
	
				int cont = 1;
				TreeNode noGrupos= new TreeNodeImpl();
				GrupoNo grupoNo = new GrupoNo();
				grupoNo.setGrupo(new Grupo());
				grupoNo.getGrupo().setCodGrupoGrup("Grupos");
				grupoNo.setTipo(GrupoNo.TIPO_NO_RAIZ);
				noGrupos.setData(grupoNo);
				no = new TreeNodeImpl();
				grupoNo = new GrupoNo();
				grupoNo.setTipo(GrupoNo.TIPO_NO_ATUALIZACAO);
				no.setData(grupoNo);
				noGrupos.addChild(cont++, no);
				while (itGrupos.hasNext()) 
				{
					no = new TreeNodeImpl();
					grupoNo = new GrupoNo();
					grupoNo.setTipo(SistemaNo.TIPO_NO_FILHO);
					Grupo grupo = (Grupo) itGrupos.next();
					grupoNo.setGrupo(grupo);
					no.setData(grupoNo);
					noGrupos.addChild(cont++, no);
				}
				noRaizGrupos.addChild(1, noGrupos);
				gruposCarregados = true;
			}
		}catch(CommandException commandException){
			commandException.printStackTrace();
			tratarErro(commandException);
		}
		
		
	}

	
	public String carregarFuncoesGrupo()
	{
		if(grupoSelecionado != null)
		{
			try
			{
				carregarDadosGrupo(grupoSelecionado);	
			}catch(CommandException commandException){
				commandException.printStackTrace();
				tratarErro(commandException);
			}
		}
		return null;
	}	

	public String tipoCarregamentoGrupoAlterado()
	{
		if(grupoSelecionado != null)
		{
			try
			{
				carregarDadosGrupo(grupoSelecionado);	
			}catch(CommandException commandException){
				commandException.printStackTrace();
				tratarErro(commandException);
			}
		}
		return null;
	}	
	
	public void carregarDadosGrupoSelecionado(NodeSelectedEvent evt)
	{
		try
		{
			this.listaDisponiveisGrupo.setWrappedData(Collections.EMPTY_LIST);
			this.listaRelacionadosGrupo.setWrappedData(Collections.EMPTY_LIST);
			this.grupoSelecionado = null;
			if(((HtmlTree)evt.getSource()).getRowData() instanceof GrupoNo
					&& 
					((GrupoNo)((HtmlTree)evt.getSource()).getRowData()).getTipo() == GrupoNo.TIPO_NO_FILHO
			)
			{
				Grupo grupo = new Grupo();
				grupo.setCodGrupoGrup(
						((GrupoNo)((HtmlTree)evt.getSource()).getRowData()).getGrupo().getCodGrupoGrup()
				);
				carregarDadosGrupo(grupo);
			}else{
				gruposCarregados = false;
				carregarGrupos();
			}
		}catch(CommandException commandException){
			commandException.printStackTrace();
			tratarErro(commandException);
		}
		
	}	

	private void carregarDadosGrupo(Grupo grupo) throws CommandException
	{
		this.grupoSelecionado = grupo;
		boolean carregarListaFuncoesDisponiveis = false;
		boolean carregarListaSistemasDisponiveis = false;
		boolean carregarListaUsuariosDisponiveis = false;
		Sistema sistemaFiltroPesquisaFuncoes = null;
		if (TIPO_CARREGAMENTO_FUNCOES == tipoCarregamentoGrupo) {
			carregarListaFuncoesDisponiveis = true;
			sistemaFiltroPesquisaFuncoes = new Sistema();
			sistemaFiltroPesquisaFuncoes.setCodSistemaSist(codSistemaSelecionadoGrupo);
		}else if (TIPO_CARREGAMENTO_SISTEMAS == tipoCarregamentoGrupo) {
			carregarListaSistemasDisponiveis = true;
		} else if (TIPO_CARREGAMENTO_USUARIOS == tipoCarregamentoGrupo) {
			carregarListaUsuariosDisponiveis = true;
		}
		Object[] args = new Object[] 
		{ 
				grupo, 
				carregarListaFuncoesDisponiveis,
				carregarListaFuncoesDisponiveis,
				carregarListaSistemasDisponiveis,
				carregarListaSistemasDisponiveis,
				carregarListaUsuariosDisponiveis,
				carregarListaUsuariosDisponiveis,
				sistemaFiltroPesquisaFuncoes 
		};
		baseDispatchCRUDCommandGrupos.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);		
		Object objRetorno[] = baseDispatchCRUDCommandGrupos.executar(args);
		grupo = (Grupo) objRetorno[0];
		
		if (TIPO_CARREGAMENTO_FUNCOES == tipoCarregamentoGrupo) {
			
			List listaFuncoesTMP = new LinkedList(grupo.getFuncoes());
			List listaFuncoesNaoRelacionadasTMP = (List) objRetorno[1];
			List listaFuncoes = new LinkedList();
			List listaFuncoesNaoRelacionadas = new LinkedList();
			ajustarDadosModeloFuncoesGrupo(listaFuncoesTMP);
			ajustarDadosModeloFuncoesGrupo(listaFuncoesNaoRelacionadasTMP);
			Iterator it = listaFuncoesTMP.iterator();
			while(it.hasNext())
			{
				Object obj = it.next();
				AssociacaoGrupoNo no = new AssociacaoGrupoNo();
				if(obj instanceof Funcao)
				{
					Funcao funcao = (Funcao) obj;
					no.setFuncao(funcao);
				}else
				{
					Sistema sistema = (Sistema) obj;
					no.setSistema(sistema);
				}
				listaFuncoes.add(no);
			}

			it = listaFuncoesNaoRelacionadasTMP.iterator();
			while(it.hasNext())
			{
				Object obj = it.next();
				AssociacaoGrupoNo no = new AssociacaoGrupoNo();
				if(obj instanceof Funcao)
				{
					Funcao funcao = (Funcao) obj;
					no.setFuncao(funcao);
				}else
				{
					Sistema sistema = (Sistema) obj;
					no.setSistema(sistema);
				}
				listaFuncoesNaoRelacionadas.add(no);
			}
			
			
			listaFuncoesDisponiveisGrupo.setWrappedData(listaFuncoesNaoRelacionadas);
			listaFuncoesRelacionadasGrupo.setWrappedData(listaFuncoes);
			listaDisponiveisGrupo = listaFuncoesDisponiveisGrupo;
			listaRelacionadosGrupo = listaFuncoesRelacionadasGrupo;
		}else if (TIPO_CARREGAMENTO_SISTEMAS == tipoCarregamentoGrupo) {
			List listaSistemasTMP = new LinkedList(grupo.getSistemas());
			List listaSistemasNaoRelacionadosTMP = (List) objRetorno[2];
			List listaSistemas = new LinkedList();
			List listaSistemasNaoRelacionados = new LinkedList();
			Iterator it = listaSistemasTMP.iterator();
			while(it.hasNext())
			{
				Sistema sistema = (Sistema) it.next();
				AssociacaoGrupoNo no = new AssociacaoGrupoNo();
				no.setSistema(sistema);
				listaSistemas.add(no);
			}

			it = listaSistemasNaoRelacionadosTMP.iterator();
			while(it.hasNext())
			{
				Sistema sistema = (Sistema) it.next();
				AssociacaoGrupoNo no = new AssociacaoGrupoNo();
				no.setSistema(sistema);
				listaSistemasNaoRelacionados.add(no);
			}
			listaSistemasDisponiveisGrupo.setWrappedData(listaSistemasNaoRelacionados);
			listaSistemasRelacionadosGrupo.setWrappedData(listaSistemas);
			listaDisponiveisGrupo = listaSistemasDisponiveisGrupo;
			listaRelacionadosGrupo = listaSistemasRelacionadosGrupo;
		} else if (TIPO_CARREGAMENTO_USUARIOS == tipoCarregamentoGrupo) {
			List listaUsuariosTMP = new LinkedList(grupo.getUsuarios());
			List listaUsuariosNaoRelacionadosTMP = (List) objRetorno[3];
			List listaUsuarios = new LinkedList();
			List listaUsuariosNaoRelacionados = new LinkedList();
			Iterator it = listaUsuariosTMP.iterator();
			while(it.hasNext())
			{
				Usuario usuario = (Usuario) it.next();
				AssociacaoGrupoNo no = new AssociacaoGrupoNo();
				no.setUsuario(usuario);
				listaUsuarios.add(no);
			}

			it = listaUsuariosNaoRelacionadosTMP.iterator();
			while(it.hasNext())
			{
				Usuario usuario = (Usuario) it.next();
				AssociacaoGrupoNo no = new AssociacaoGrupoNo();
				no.setUsuario(usuario);
				listaUsuariosNaoRelacionados.add(no);
			}

			listaUsuariosDisponiveisGrupo.setWrappedData(listaUsuariosNaoRelacionados);
			listaUsuariosRelacionadosGrupo.setWrappedData(listaUsuarios);
			listaDisponiveisGrupo = listaUsuariosDisponiveisGrupo;
			listaRelacionadosGrupo = listaUsuariosRelacionadosGrupo;
		}
	}
	
	private void ajustarDadosModeloFuncoesGrupo(List listaObjetos){
		if(listaObjetos != null && listaObjetos.size() > 0)
		{
			List listaTMP = new LinkedList();
			boolean eraListaAssociacaoNo = false;
			
			//Retira todos os sistemas da lista
			Iterator it = listaObjetos.iterator();
			while(it.hasNext())
			{
				Object obj = it.next();
				if(obj instanceof Sistema){
					it.remove();
				}else if(obj instanceof AssociacaoGrupoNo && ((AssociacaoGrupoNo)obj).getSistema() != null){
					it.remove();
				}else if(obj instanceof AssociacaoGrupoNo && ((AssociacaoGrupoNo)obj).getFuncao() != null){
					listaTMP.add(((AssociacaoGrupoNo)obj).getFuncao());
				}	
			}
			eraListaAssociacaoNo = listaTMP.size() > 0;

			if(eraListaAssociacaoNo)
			{
				listaObjetos.clear();
				listaObjetos.addAll(listaTMP);
				listaTMP.clear();
				listaTMP = null;
			}
		
			//Ordena as funções pelo código do sistema
			Collections.sort(listaObjetos,
			new Comparator(){
				public int compare(Object o1, Object o2) {
					String codSistemaUm = "";
					String codSistemaDois = "";
					int comp = 0;
					Funcao funcaoUm = (Funcao) o1;
					Funcao funcaoDois = (Funcao) o2;
					if(funcaoUm != null && funcaoUm.getSistema() != null && funcaoUm.getSistema().getCodSistemaSist() != null){
						codSistemaUm = funcaoUm.getSistema().getCodSistemaSist().trim().toUpperCase();
					}
					if(funcaoDois != null && funcaoDois.getSistema() != null && funcaoDois.getSistema().getCodSistemaSist() != null){
						codSistemaDois = funcaoDois.getSistema().getCodSistemaSist().trim().toUpperCase();
					}
					comp = codSistemaUm.compareTo(codSistemaDois);
					if(comp == 0){
						//Ordena pelo código da função
						comp = funcaoUm.getCompId().getCodFuncaoFunc().compareTo(funcaoDois.getCompId().getCodFuncaoFunc());
					}
					return comp;
				}
			});

			if(listaObjetos.size() > 0){
				//Adiciona os sistemas para servirem como separador
				it = listaObjetos.iterator();
				int indice = 0;
				
				final Map<Sistema, Integer> sistemasAAdicionar = new HashMap<Sistema, Integer>();
				sistemasAAdicionar.clear();
				String codSistemaSist = null;
				Sistema sistema = new Sistema();
				while(it.hasNext()){
					Object obj = it.next();
					codSistemaSist = ((Funcao)obj).getCompId().getCodSistemaSist();
					sistema = new Sistema();
					sistema.setCodSistemaSist(codSistemaSist);
					if(sistemasAAdicionar.get(sistema) == null){
						sistemasAAdicionar.put(sistema, indice);
					}
					indice++;
				}

				List listadSistemasOrdenadosPeloIndice = new LinkedList(sistemasAAdicionar.keySet()); 
					Collections.sort(
						listadSistemasOrdenadosPeloIndice,
						new Comparator(){
							public int compare(Object o1, Object o2) {
								Sistema sistemaUm = (Sistema)o1;
								Sistema sistemaDois = (Sistema)o2;
								return sistemasAAdicionar.get(sistemaUm).compareTo(sistemasAAdicionar.get(sistemaDois));
							}
						}
					);
				it = listadSistemasOrdenadosPeloIndice.iterator();
				int cont = 0;
				while(it.hasNext()){
					sistema = (Sistema) it.next();
					listaObjetos.add( sistemasAAdicionar.get(sistema) + cont, sistema);
					//System.out.println(sistemasAAdicionar.get(sistema));
					cont++;
				}
			}
			
			
			if(eraListaAssociacaoNo)
			{
				it = listaObjetos.iterator();
				List listaTMPObj = new LinkedList();
				while(it.hasNext())
				{
					Object obj = it.next();
					AssociacaoGrupoNo no = new AssociacaoGrupoNo();
					if(obj instanceof Funcao)
					{
						Funcao funcao = (Funcao) obj;
						no.setFuncao(funcao);
					}else
					{
						Sistema sistema = (Sistema) obj;
						no.setSistema(sistema);
					}
					listaTMPObj.add(no);
				}
				listaObjetos.clear();
				listaObjetos.addAll(listaTMPObj);
				listaTMPObj.clear();
				listaTMPObj = null;
			}
			
			
		}
	}

	
	public void transferirObjetoGrupoDisponivelParaSelecionado(DropEvent evt)
	{
		Object obj = evt.getDragValue();
		if (TIPO_CARREGAMENTO_FUNCOES == tipoCarregamentoGrupo &&  obj instanceof AssociacaoGrupoNo && ((AssociacaoGrupoNo)obj).getFuncao() != null) 
		{
			((List)listaFuncoesDisponiveisGrupo.getWrappedData()).remove(obj);
			((List)listaFuncoesRelacionadasGrupo.getWrappedData()).add(obj);
			ajustarDadosModeloFuncoesGrupo(((List)listaFuncoesDisponiveisGrupo.getWrappedData()));
			ajustarDadosModeloFuncoesGrupo(((List)listaFuncoesRelacionadasGrupo.getWrappedData()));
		}else if (TIPO_CARREGAMENTO_SISTEMAS == tipoCarregamentoGrupo &&  obj instanceof AssociacaoGrupoNo && ((AssociacaoGrupoNo)obj).getSistema() != null)	
		{ 
			((List)listaSistemasDisponiveisGrupo.getWrappedData()).remove(obj);
			((List)listaSistemasRelacionadosGrupo.getWrappedData()).add(obj);
		}else if (TIPO_CARREGAMENTO_USUARIOS == tipoCarregamentoGrupo &&  obj instanceof AssociacaoGrupoNo && ((AssociacaoGrupoNo)obj).getUsuario() != null)
	    { 
			((List)listaUsuariosDisponiveisGrupo.getWrappedData()).remove(obj);
			((List)listaUsuariosRelacionadosGrupo.getWrappedData()).add(obj);
		}
	}	

	public void transferirObjetoGrupoSelecionadoParaDisponivel(DropEvent evt)
	{
		Object obj = evt.getDragValue();
		if (TIPO_CARREGAMENTO_FUNCOES == tipoCarregamentoGrupo &&  obj instanceof AssociacaoGrupoNo && ((AssociacaoGrupoNo)obj).getFuncao() != null) 
		{
			((List)listaFuncoesDisponiveisGrupo.getWrappedData()).add(obj);
			((List)listaFuncoesRelacionadasGrupo.getWrappedData()).remove(obj);
			ajustarDadosModeloFuncoesGrupo(((List)listaFuncoesDisponiveisGrupo.getWrappedData()));
			ajustarDadosModeloFuncoesGrupo(((List)listaFuncoesRelacionadasGrupo.getWrappedData()));
		}else if (TIPO_CARREGAMENTO_SISTEMAS == tipoCarregamentoGrupo &&  obj instanceof AssociacaoGrupoNo && ((AssociacaoGrupoNo)obj).getSistema() != null)	
		{ 
			((List)listaSistemasDisponiveisGrupo.getWrappedData()).add(obj);
			((List)listaSistemasRelacionadosGrupo.getWrappedData()).remove(obj);
		}else if (TIPO_CARREGAMENTO_USUARIOS == tipoCarregamentoGrupo &&  obj instanceof AssociacaoGrupoNo && ((AssociacaoGrupoNo)obj).getUsuario() != null)
	    { 
			((List)listaUsuariosDisponiveisGrupo.getWrappedData()).add(obj);
			((List)listaUsuariosRelacionadosGrupo.getWrappedData()).remove(obj);
		}
	}	
	

	public void salvarRelacionamentosGrupo()
	{
		if(this.grupoSelecionado!= null)
		{
			try
			{
				Grupo grupoAlterar = new Grupo();
				grupoAlterar.setCodGrupoGrup(grupoSelecionado.getCodGrupoGrup());
				List listaTMP = new LinkedList();
				Iterator it = ((List)listaRelacionadosGrupo.getWrappedData()).iterator();
				while(it.hasNext())
				{
					AssociacaoGrupoNo no = (AssociacaoGrupoNo)it.next();
					if(no.getFuncao() != null)
					{
						listaTMP.add(no.getFuncao());
					}else if(no.getUsuario() != null)
					{
						listaTMP.add(no.getUsuario());
					}else if(no.getSistema() != null && TIPO_CARREGAMENTO_FUNCOES != tipoCarregamentoGrupo)
					{
						//No  caso das funções o nó para o sistema está deisponível apenas para visualização, então ele é desconsiderado aqui
						listaTMP.add(no.getSistema());
					}
				}
				
				Object[] args = new Object[] { grupoAlterar, listaTMP};

				if (TIPO_CARREGAMENTO_FUNCOES == tipoCarregamentoGrupo)
				{
					baseDispatchCRUDCommandGrupos.setStrMetodo(CommandGrupos.METODO_SALVAR_FUNCOES_GRUPO);
				}else if (TIPO_CARREGAMENTO_SISTEMAS == tipoCarregamentoGrupo)
				{
					baseDispatchCRUDCommandGrupos.setStrMetodo(CommandGrupos.METODO_SALVAR_SISTEMAS_GRUPO);
				}else if (TIPO_CARREGAMENTO_USUARIOS == tipoCarregamentoGrupo)
				{
					baseDispatchCRUDCommandGrupos.setStrMetodo(CommandGrupos.METODO_SALVAR_USUARIOS_GRUPO);
				}
				Object retorno = baseDispatchCRUDCommandGrupos.executar(args);
			}catch(CommandException commandException){
				commandException.printStackTrace();
				tratarErro(commandException);
			}
		}
	}
	
	/********************Final aba Grupos*************************************************************/

	
	/************************************Aba usuários****************************************************/

	protected void carregarUsuarios()
	{
		baseDispatchCRUDCommandUsuarios.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
		baseDispatchCRUDCommandUsuarios.getPagina().setRegistrosPorPagina(Long.MAX_VALUE);
		baseDispatchCRUDCommandUsuarios.getPagina().setProximaAcao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
		Object args[] = new Object[] { new UsuarioSearchVo() };
		try{
			//TODO --> Corrigir quando adicionar o controle de segurança
			Usuario usuario = new Usuario();
			usuario.setCodUsuarioUsua(Constantes.COD_USR_ADM);
			GerenciadorJanelas.getInstance().setControleSegurancaVo(new ControleSegurancaVo(usuario, Collections.EMPTY_MAP, Collections.EMPTY_MAP, Collections.EMPTY_MAP));
			Object[] objRetorno = baseDispatchCRUDCommandUsuarios.executar(args);
			if(!usuariosCarregados)
			{
				// Atualiza a árvore de grupos
				Pagina pagina = (Pagina) objRetorno[0];
				TreeNode no = null;
				Iterator itUsuarios = pagina.getListObjects().iterator();
	
				int cont = 1;
				TreeNode noUsuarios= new TreeNodeImpl();
				UsuarioNo usuarioNo = new UsuarioNo();
				usuarioNo.setUsuario(new Usuario());
				usuarioNo.getUsuario().setCodUsuarioUsua("Usuários");
				usuarioNo.setTipo(UsuarioNo.TIPO_NO_RAIZ);
				noUsuarios.setData(usuarioNo);
				no = new TreeNodeImpl();
				usuarioNo = new UsuarioNo();
				usuarioNo.setTipo(UsuarioNo.TIPO_NO_ATUALIZACAO);
				no.setData(usuarioNo);
				noUsuarios.addChild(cont++, no);
				while (itUsuarios.hasNext()) 
				{
					no = new TreeNodeImpl();
					usuarioNo = new UsuarioNo();
					usuarioNo.setTipo(UsuarioNo.TIPO_NO_FILHO);
					Usuario usuarioTMP = (Usuario) itUsuarios.next();
					usuarioNo.setUsuario(usuarioTMP);
					no.setData(usuarioNo);
					noUsuarios.addChild(cont++, no);
				}
				noRaizUsuarios.addChild(1, noUsuarios);
				usuariosCarregados = true;
			}
		}catch(CommandException commandException){
			commandException.printStackTrace();
			tratarErro(commandException);
		}
	}

	
	
	public void carregarGruposUsuarioSelecionado(NodeSelectedEvent evt)
	{
		try
		{
			baseDispatchCRUDCommandUsuarios.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
			this.listaGruposDisponiveisUsuario.setWrappedData(Collections.EMPTY_LIST);
			this.listaGruposRelacionadosUsuario.setWrappedData(Collections.EMPTY_LIST);
			this.sistemaSelecionado = null;
			if(((HtmlTree)evt.getSource()).getRowData() instanceof UsuarioNo
					&& 
					((UsuarioNo)((HtmlTree)evt.getSource()).getRowData()).getTipo() == SistemaNo.TIPO_NO_FILHO
			)
			{
				Usuario usuario = ((UsuarioNo)((HtmlTree)evt.getSource()).getRowData()).getUsuario();
				this.usuarioSelecionado = usuario;
				Object[] args = new Object[] { usuario, true, true };
				Object objRetorno[] = baseDispatchCRUDCommandUsuarios.executar(args);
				Usuario usuarioTMP = (Usuario) objRetorno[0];
				List<Grupo> listaGrupos = new LinkedList<Grupo>(usuarioTMP.getGrupos());
				List<Grupo> listaGruposNaoRelacionados = (List<Grupo>) objRetorno[1];
				this.listaGruposDisponiveisUsuario.setWrappedData(listaGruposNaoRelacionados);
				this.listaGruposRelacionadosUsuario.setWrappedData(listaGrupos);
			}else
			{
				usuariosCarregados = false;
				carregarUsuarios();
			}
		}catch(CommandException commandException){
			commandException.printStackTrace();
			tratarErro(commandException);
		}
		
	}	
	
	public void transferirGrupoSelecionadoParaDisponivelUsuario(DropEvent evt)
	{
		Object obj = evt.getDragValue();
		((List)listaGruposDisponiveisUsuario.getWrappedData()).add(obj);
		((List)listaGruposRelacionadosUsuario.getWrappedData()).remove(obj);
	}

	public void transferirGrupoDisponivelParaSelecionadoUsuario(DropEvent evt)
	{
		Object obj = evt.getDragValue();
		((List)listaGruposDisponiveisUsuario.getWrappedData()).remove(obj);
		((List)listaGruposRelacionadosUsuario.getWrappedData()).add(obj);
	}
	
	
	public void salvarRelacionamentosUsuario()
	{
		if(this.usuarioSelecionado != null)
		{
			try
			{
				baseDispatchCRUDCommandUsuarios.setStrMetodo(CommandUsuarios.METODO_SALVAR_GRUPOS_USUARIO);
				Usuario usuarioAlterar = new Usuario();
				usuarioAlterar.setCodUsuarioUsua(this.usuarioSelecionado.getCodUsuarioUsua());
				Object[] args = new Object[] { usuarioAlterar, this.listaGruposRelacionadosUsuario.getWrappedData() };
				baseDispatchCRUDCommandUsuarios.executar(args);
			}catch(CommandException commandException){
				commandException.printStackTrace();
				tratarErro(commandException);
			}
		}
	}

	
	
	/*********************************Final aba usuários***************************************************************/

	
	/********************************Aba funções***********************************************************************/
	protected void carregarFuncoes()
	{
		baseDispatchCRUDCommandFuncoes.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
		baseDispatchCRUDCommandFuncoes.getPagina().setRegistrosPorPagina(Long.MAX_VALUE);
		baseDispatchCRUDCommandFuncoes.getPagina().setProximaAcao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
		Object args[] = new Object[] { new FuncaoSearchVo() };
		try{
			//TODO --> Corrigir quando adicionar o controle de segurança
			Usuario usuario = new Usuario();
			usuario.setCodUsuarioUsua(Constantes.COD_USR_ADM);
			GerenciadorJanelas.getInstance().setControleSegurancaVo(new ControleSegurancaVo(usuario, Collections.EMPTY_MAP, Collections.EMPTY_MAP, Collections.EMPTY_MAP));
			Object[] objRetorno = baseDispatchCRUDCommandFuncoes.executar(args);
			if(!funcoesCarregadas)
			{
				// Atualiza a árvore de funções
				Pagina pagina = (Pagina) objRetorno[0];
				TreeNode no = null;
				Iterator itFuncoes= pagina.getListObjects().iterator();
	
				int cont = 1;
				TreeNode noFuncoes = new TreeNodeImpl();
				FuncaoNo funcaoNo = new FuncaoNo();
				funcaoNo.setFuncao(new Funcao());
				funcaoNo.getFuncao().setNomeFunc("Funções");
				funcaoNo.setTipo(UsuarioNo.TIPO_NO_RAIZ);
				noFuncoes.setData(funcaoNo);
				no = new TreeNodeImpl();
				funcaoNo = new FuncaoNo();
				funcaoNo.setTipo(UsuarioNo.TIPO_NO_ATUALIZACAO);
				no.setData(funcaoNo);
				noFuncoes.addChild(cont++, no);

				TreeNode noSistemaAtual = null;
				Map<Sistema, TreeNode> mapaSistemas = new HashMap<Sistema, TreeNode>();
				Map<Sistema, Integer> mapaQtdeFilhosSistemas = new HashMap<Sistema, Integer>();
				while (itFuncoes.hasNext()) {
					no = new TreeNodeImpl();
					Funcao funcao = (Funcao) itFuncoes.next();
					FuncaoNo funcaoNoTMP = new FuncaoNo();
					funcaoNoTMP.setFuncao(funcao);
					funcaoNoTMP.setTipo(FuncaoNo.TIPO_NO_FILHO);
					no.setData(funcaoNoTMP);
					noSistemaAtual = mapaSistemas.get(funcao.getSistema());
					if(noSistemaAtual == null)
					{
						noSistemaAtual = new TreeNodeImpl();
						funcaoNoTMP = new FuncaoNo();
						Sistema sistemaAtual = funcao.getSistema();
						funcaoNoTMP.setSistema(sistemaAtual);
						funcaoNoTMP.setTipo(FuncaoNo.TIPO_NO_SISTEMA);
						noSistemaAtual.setData(funcaoNoTMP);
						mapaSistemas.put(funcao.getSistema(), noSistemaAtual);
						//noFuncoes.addChild(cont++, noFuncaoTMP);
					
					}
					Integer qtdeFilhos = mapaQtdeFilhosSistemas.get(funcao.getSistema());
					if( qtdeFilhos == null)
					{
						qtdeFilhos = 0;
					}
					noSistemaAtual.addChild(qtdeFilhos++, no);
					mapaQtdeFilhosSistemas.put(funcao.getSistema(), qtdeFilhos);
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
					Sistema sistemaAtual = (Sistema)itSistemas.next();
					noFuncoes.addChild(cont++, mapaSistemas.get(sistemaAtual));
				}
				
				noRaizFuncoes.addChild(1, noFuncoes);
				funcoesCarregadas = true;
			}
		}catch(CommandException commandException){
			commandException.printStackTrace();
			tratarErro(commandException);
		}
	}
	

	public void carregarGruposFuncaoSelecionada(NodeSelectedEvent evt)
	{
		try
		{
			baseDispatchCRUDCommandFuncoes.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
			this.listaGruposDisponiveisFuncao.setWrappedData(Collections.EMPTY_LIST);
			this.listaGruposRelacionadosFuncao.setWrappedData(Collections.EMPTY_LIST);
			this.funcaoSelecionada = null;
			if(((HtmlTree)evt.getSource()).getRowData() instanceof FuncaoNo
					&& 
					((FuncaoNo)((HtmlTree)evt.getSource()).getRowData()).getTipo() == FuncaoNo.TIPO_NO_FILHO
			)
			{
				Funcao funcao = ((FuncaoNo)((HtmlTree)evt.getSource()).getRowData()).getFuncao();
				this.funcaoSelecionada = funcao;
				Object[] args = new Object[] { funcao, true, true };
				Object objRetorno[] = baseDispatchCRUDCommandFuncoes.executar(args);
				Funcao funcaoTMP = (Funcao) objRetorno[0];
				List<Grupo> listaGrupos = new LinkedList<Grupo>(funcaoTMP.getGrupos());
				List<Grupo> listaGruposNaoRelacionados = (List<Grupo>) objRetorno[1];
				this.listaGruposDisponiveisFuncao.setWrappedData(listaGruposNaoRelacionados);
				this.listaGruposRelacionadosFuncao.setWrappedData(listaGrupos);
			}else if(
				((FuncaoNo)((HtmlTree)evt.getSource()).getRowData()).getTipo() == FuncaoNo.TIPO_NO_ATUALIZACAO
				)
			{
				funcoesCarregadas = false;
				carregarFuncoes();
			}
		}catch(CommandException commandException){
			commandException.printStackTrace();
			tratarErro(commandException);
		}
	}	
	
	public void transferirGrupoSelecionadoParaDisponivelFuncao(DropEvent evt)
	{
		Object obj = evt.getDragValue();
		((List)listaGruposDisponiveisFuncao.getWrappedData()).add(obj);
		((List)listaGruposRelacionadosFuncao.getWrappedData()).remove(obj);
	}

	public void transferirGrupoDisponivelParaSelecionadoFuncao(DropEvent evt)
	{
		Object obj = evt.getDragValue();
		((List)listaGruposDisponiveisFuncao.getWrappedData()).remove(obj);
		((List)listaGruposRelacionadosFuncao.getWrappedData()).add(obj);
	}
	
	
	public void salvarRelacionamentosFuncao()
	{
		if(this.funcaoSelecionada != null)
		{
			try
			{
				baseDispatchCRUDCommandFuncoes.setStrMetodo(CommandFuncoes.METODO_SALVAR_GRUPOS_FUNCAO);
				Funcao funcaoAlterar = new Funcao();
				funcaoAlterar.setCompId(this.funcaoSelecionada.getCompId());
				Object[] args = new Object[] { funcaoAlterar, this.listaGruposRelacionadosFuncao.getWrappedData() };
				baseDispatchCRUDCommandFuncoes.executar(args);
			}catch(CommandException commandException){
				commandException.printStackTrace();
				tratarErro(commandException);
			}
		}
	}
	
	
	/******************************************************************************************************************/
	
	public DataModel getListaDisponiveisGrupo() {
		return listaDisponiveisGrupo;
	}

	public void setListaDisponiveisGrupo(DataModel listaDisponiveisGrupo) {
		this.listaDisponiveisGrupo = listaDisponiveisGrupo;
	}

	public DataModel getListaRelacionadosGrupo() {
		return listaRelacionadosGrupo;
	}

	public void setListaRelacionadosGrupo(DataModel listaRelacionadosGrupo) {
		this.listaRelacionadosGrupo = listaRelacionadosGrupo;
	}

	public int getTipoCarregamentoGrupo() {
		return tipoCarregamentoGrupo;
	}

	public void setTipoCarregamentoGrupo(int tipoCarregamentoGrupo) {
		this.tipoCarregamentoGrupo = tipoCarregamentoGrupo;
	}

	public String getCodSistemaSelecionadoGrupo() {
		return codSistemaSelecionadoGrupo;
	}

	public void setCodSistemaSelecionadoGrupo(String codSistemaSelecionadoGrupo) {
		this.codSistemaSelecionadoGrupo = codSistemaSelecionadoGrupo;
	}

	public int getTabSelecionada() {
		return tabSelecionada;
	}

	public void setTabSelecionada(int tabSelecionada) {
		this.tabSelecionada = tabSelecionada;
	}

	public String getMETODO() {
		return METODO;
	}

	public void setMETODO(String metodo) {
		METODO = metodo;
	}

	public BaseDispatchCRUDCommand getBaseDispatchCRUDCommandSistemas() {
		return baseDispatchCRUDCommandSistemas;
	}

	public void setBaseDispatchCRUDCommandSistemas(
			BaseDispatchCRUDCommand baseDispatchCRUDCommandSistemas) {
		this.baseDispatchCRUDCommandSistemas = baseDispatchCRUDCommandSistemas;
	}

	public boolean isSistemasCarregados() {
		return sistemasCarregados;
	}

	public void setSistemasCarregados(boolean sistemasCarregados) {
		this.sistemasCarregados = sistemasCarregados;
	}

	public BaseDispatchCRUDCommand getBaseDispatchCRUDCommandGrupos() {
		return baseDispatchCRUDCommandGrupos;
	}

	public void setBaseDispatchCRUDCommandGrupos(
			BaseDispatchCRUDCommand baseDispatchCRUDCommandGrupos) {
		this.baseDispatchCRUDCommandGrupos = baseDispatchCRUDCommandGrupos;
	}

	public boolean isGruposCarregados() {
		return gruposCarregados;
	}

	public void setGruposCarregados(boolean gruposCarregados) {
		this.gruposCarregados = gruposCarregados;
	}

	public static int getTIPO_CARREGAMENTO_FUNCOES() {
		return TIPO_CARREGAMENTO_FUNCOES;
	}

	public static void setTIPO_CARREGAMENTO_FUNCOES(int tipo_carregamento_funcoes) {
		TIPO_CARREGAMENTO_FUNCOES = tipo_carregamento_funcoes;
	}

	public static int getTIPO_CARREGAMENTO_SISTEMAS() {
		return TIPO_CARREGAMENTO_SISTEMAS;
	}

	public static void setTIPO_CARREGAMENTO_SISTEMAS(int tipo_carregamento_sistemas) {
		TIPO_CARREGAMENTO_SISTEMAS = tipo_carregamento_sistemas;
	}

	public static int getTIPO_CARREGAMENTO_USUARIOS() {
		return TIPO_CARREGAMENTO_USUARIOS;
	}

	public static void setTIPO_CARREGAMENTO_USUARIOS(int tipo_carregamento_usuarios) {
		TIPO_CARREGAMENTO_USUARIOS = tipo_carregamento_usuarios;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public BaseDispatchCRUDCommand getBaseDispatchCRUDCommandFuncao() {
		return baseDispatchCRUDCommandFuncoes;
	}

	public void setBaseDispatchCRUDCommandFuncao(
			BaseDispatchCRUDCommand baseDispatchCRUDCommandFuncao) {
		this.baseDispatchCRUDCommandFuncoes = baseDispatchCRUDCommandFuncao;
	}

	public DataModel getListaFuncoes() {
		return listaFuncoes;
	}

	public void setListaFuncoes(DataModel listaFuncoes) {
		this.listaFuncoes = listaFuncoes;
	}

	public boolean isFuncoesCarregadas() {
		return funcoesCarregadas;
	}

	public void setFuncoesCarregadas(boolean funcoesCarregadas) {
		this.funcoesCarregadas = funcoesCarregadas;
	}

	public TreeNode getNoRaizFuncoes() {
		return noRaizFuncoes;
	}

	public void setNoRaizFuncoes(TreeNode noRaizFuncoes) {
		this.noRaizFuncoes = noRaizFuncoes;
	}

	public DataModel getListaGruposDisponiveisFuncao() {
		return listaGruposDisponiveisFuncao;
	}

	public void setListaGruposDisponiveisFuncao(
			DataModel listaGruposDisponiveisFuncao) {
		this.listaGruposDisponiveisFuncao = listaGruposDisponiveisFuncao;
	}

	public DataModel getListaGruposRelacionadosFuncao() {
		return listaGruposRelacionadosFuncao;
	}

	public void setListaGruposRelacionadosFuncao(
			DataModel listaGruposRelacionadosFuncao) {
		this.listaGruposRelacionadosFuncao = listaGruposRelacionadosFuncao;
	}

	public Funcao getFuncaoSelecionada() {
		return funcaoSelecionada;
	}

	public void setFuncaoSelecionada(Funcao funcaoSelecionada) {
		this.funcaoSelecionada = funcaoSelecionada;
	}

	public static int getTAB_SISTEMAS() {
		return TAB_SISTEMAS;
	}

	public static int getTAB_GRUPOS() {
		return TAB_GRUPOS;
	}

	public static int getTAB_USUARIOS() {
		return TAB_USUARIOS;
	}

	public static int getTAB_FUNCOES() {
		return TAB_FUNCOES;
	}
	
	
}
