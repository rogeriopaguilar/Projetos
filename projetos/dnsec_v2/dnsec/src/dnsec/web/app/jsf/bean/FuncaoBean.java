package dnsec.web.app.jsf.bean;

import java.util.Collections;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import dnsec.modulos.cadastros.funcao.vo.FuncaoSearchVo;
import dnsec.modulos.controle.seguranca.vo.ControleSegurancaVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Funcao;
import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.database.hibernate.pk.FuncaoPk;
import dnsec.shared.factory.CommandFactory;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.Pagina;
import dnsec.web.util.ErroUtil;

public class FuncaoBean {

	private DataModel listaFuncoes = new ListDataModel();
	private FuncaoSearchVo funcaoSearchVo = new FuncaoSearchVo();
	private Funcao funcaoVo = new Funcao();
	private BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_FUNCOES);
	private UIData modelo = null;
	private String METODO = null;
	
	public String listarFuncoesInicio()
	{
		this.listaFuncoes.setWrappedData(Collections.EMPTY_LIST);
		this.METODO = BaseDispatchCRUDCommand.METODO_LISTAR;
		this.setFuncaoVo(new Funcao());
		this.setFuncaoSearchVo(new FuncaoSearchVo());
		getFuncaoSearchVo().getFuncao().setSistema(new Sistema());
		return "LISTAR_FUNCOES";
	}

	
	public String listarFuncoes()
	{
		List listaFuncoes = null;
		baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
		baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
		Object[] args = new Object[]{ this.funcaoSearchVo };
		//TODO --> Remover esta dependência
		Usuario usuario = new Usuario();
		usuario.setCodUsuarioUsua(Constantes.COD_USR_ADM);
		GerenciadorJanelas.getInstance().setControleSegurancaVo(new ControleSegurancaVo(usuario, Collections.EMPTY_MAP, Collections.EMPTY_MAP, Collections.EMPTY_MAP));
		
		try{
			Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			baseDispatchCRUDCommand.getPagina().setRegistrosPorPagina(Long.MAX_VALUE);
			listaFuncoes = ((Pagina)objRetorno[0]).getListObjects();
			this.listaFuncoes.setWrappedData(listaFuncoes);
			this.METODO = BaseDispatchCRUDCommand.METODO_LISTAR;
			this.setFuncaoVo(new Funcao());
		}catch(CommandException commandException){
			commandException.printStackTrace();
			tratarErro(commandException);
		}
		return null;
	}


	public String excluir()
	{
		String codFuncao = 
			((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("frmFuncoes:codFuncaoFuncSelecionada");
		String codSistema = 
			((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("frmFuncoes:codSistemaFuncaoFuncSelecionada");

		
		
		if(!StringUtils.isEmpty( codFuncao ) && !StringUtils.isEmpty( codSistema ))
		{
			try{
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_EXCLUIR);
				Funcao funcao = new Funcao();
				FuncaoPk funcaoPk = new FuncaoPk();
				funcaoPk.setCodFuncaoFunc(new Long(codFuncao));
				funcaoPk.setCodSistemaSist(codSistema);
				funcao.setCompId(funcaoPk);
				Object[] args = new Object[]{ funcao };
				Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			}catch(CommandException commandException){
				commandException.printStackTrace();
				tratarErro(commandException);
			}
		}
		this.setFuncaoVo(new Funcao());
		return this.listarFuncoes();
	}

	public String prepararEdicao()
	{
		if(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(METODO))
		{
			return listarFuncoes();
		}

		String codFuncao = 
			((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("frmFuncoes:codFuncaoFuncSelecionada");
		String codSistema = 
			((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("frmFuncoes:codSistemaFuncaoFuncSelecionada");

		
		if(!StringUtils.isEmpty( codFuncao ) && !StringUtils.isEmpty( codSistema ))
		{
			try{
				Funcao funcao = new Funcao();
				FuncaoPk funcaoPk = new FuncaoPk();
				funcaoPk.setCodFuncaoFunc(new Long(codFuncao));
				funcaoPk.setCodSistemaSist(codSistema);
				funcao.setCompId(funcaoPk);
				Object[] args = new Object[]{ funcao };
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
				Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
				setFuncaoVo((Funcao)objRetorno[0]);
				this.METODO = BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO;
			}catch(CommandException commandException){
				commandException.printStackTrace();
				tratarErro(commandException);
			}
		}
		return null;
	}


	public String prepararInclusao()
	{
		if(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO.equals(METODO))
		{
			return listarFuncoes();
		}
		try{
			
			baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO);
			Object[] args = new Object[]{ getFuncaoVo() };
			Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			setFuncaoVo((Funcao) objRetorno[0]);
			this.METODO = BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO;
		}catch(CommandException commandException){
			commandException.printStackTrace();
			tratarErro(commandException);
		}
		return null;
	}
	
	
	public String confirmarEdicao()
	{
		try{
			if(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(METODO))
			{
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO);
			}else{
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO);
			}
			if( !StringUtils.isEmpty(getFuncaoVo().getCodSistemaPaiFunc())){
				getFuncaoVo().getFuncaoPai().setCodFuncaoPaiFunc(getFuncaoVo().getCodFuncaoPaiFunc());
				getFuncaoVo().getFuncaoPai().setCodSistemaPaiFunc(getFuncaoVo().getCodSistemaPaiFunc());
			}
			Object[] args = new Object[]{ getFuncaoVo() };
			Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			setFuncaoVo(new Funcao());
			return listarFuncoes();
		}catch(CommandException commandException){
			commandException.printStackTrace();
			tratarErro(commandException);
		}
		return null;
	}
	
	protected void tratarErro(CommandException commandException)
	{
		boolean erroEsperado = ErroUtil.tratarErro(commandException);
 		if(!erroEsperado)
 		{
			//this.METODO = BaseDispatchCRUDCommand.METODO_LISTAR;
			this.METODO = ErroUtil.METODO_ERRO;
 			this.setFuncaoVo(new Funcao());
	 		this.getFuncaoSearchVo().setFuncao(new Funcao());
 		}
	}
	
	public UIData getModelo() {
		return modelo;
	}


	public void setModelo(UIData modelo) {
		this.modelo = modelo;
	}



	public FuncaoSearchVo getFuncaoSearchVo() {
		return funcaoSearchVo;
	}


	public void setFuncaoSearchVo(FuncaoSearchVo funcaoSearchVo) {
		this.funcaoSearchVo = funcaoSearchVo;
	}


	public Funcao getFuncaoVo() {
		return funcaoVo;
	}


	public void setFuncaoVo(Funcao funcaoVo) {
		this.funcaoVo = funcaoVo;
		if(funcaoVo.getSistema() == null)
		{
			funcaoVo.setSistema(new Sistema());
		}
		if(funcaoVo.getFuncaoPai() == null)
		{
			funcaoVo.setFuncaoPai(new Funcao());
		}
	}


	public DataModel getListaFuncoes() {
		return listaFuncoes;
	}


	public void setListaFuncoes(DataModel listaFuncoes) {
		this.listaFuncoes = listaFuncoes;
	}


	public String getMETODO() {
		return METODO;
	}


	public void setMETODO(String metodo) {
		METODO = metodo;
	}



}
