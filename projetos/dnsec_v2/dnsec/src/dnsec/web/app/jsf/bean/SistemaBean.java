package dnsec.web.app.jsf.bean;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import dnsec.modulos.cadastros.sistema.vo.SistemaSearchVo;
import dnsec.modulos.controle.seguranca.vo.ControleSegurancaVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Sistema;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.factory.CommandFactory;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.Pagina;
import dnsec.web.util.ErroUtil;

public class SistemaBean {

	private DataModel listaSistemas = new ListDataModel();
	private SistemaSearchVo sistemaSearchVo = new SistemaSearchVo();
	private Sistema sistemaVo = new Sistema();
	private BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_SISTEMAS);
	private UIData modelo = null;
	private String METODO = null;
	private String telaOrigemPesquisa = null;
	
	


	public String getTelaOrigemPesquisa() {
		return telaOrigemPesquisa;
	}




	public void setTelaOrigemPesquisa(String telaOrigemPesquisa) {
		this.telaOrigemPesquisa = telaOrigemPesquisa;
	}




	public String listarSistemasInicio()
	{
		Map parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		telaOrigemPesquisa = (String) parametros.get("telaOrigemPesquisa");
		this.listaSistemas.setWrappedData(Collections.EMPTY_LIST);
		this.METODO = BaseDispatchCRUDCommand.METODO_LISTAR;
		this.setSistemaVo(new Sistema());
		this.getSistemaSearchVo().setSistema(new Sistema());
		return "LISTAR_SISTEMAS";
	}

	
	
	
	public String listarSistemas()
	{
		List listaGrupos = null;
		baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
		baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
		Object[] args = new Object[]{ this.sistemaSearchVo };
		Usuario usuario = new Usuario();
		usuario.setCodUsuarioUsua(Constantes.COD_USR_ADM);
		GerenciadorJanelas.getInstance().setControleSegurancaVo(new ControleSegurancaVo(usuario, Collections.EMPTY_MAP, Collections.EMPTY_MAP, Collections.EMPTY_MAP));
		try{
			Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			baseDispatchCRUDCommand.getPagina().setRegistrosPorPagina(Long.MAX_VALUE);
			listaGrupos = ((Pagina)objRetorno[0]).getListObjects();
			this.listaSistemas.setWrappedData(listaGrupos);
			this.METODO = BaseDispatchCRUDCommand.METODO_LISTAR;
			this.setSistemaVo(new Sistema());
		}catch(CommandException commandException){
			commandException.printStackTrace();
			tratarErro(commandException);
		}
		return null;
	}


	public String excluir()
	{
		String codSistema = 
			((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("frmSistemas:codSistemaSistSelecionado");
		if(!StringUtils.isEmpty( codSistema ))
		{
			try{
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_EXCLUIR);
				Sistema sistema = new Sistema();
				sistema.setCodSistemaSist(codSistema);
				Object[] args = new Object[]{ sistema };
				Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			}catch(CommandException commandException){
				commandException.printStackTrace();
				tratarErro(commandException);
			}
		}
		this.setSistemaVo(new Sistema());
		return this.listarSistemas();
	}

	public String prepararEdicao()
	{
		String codSistema = 
			((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("frmSistemas:codSistemaSistSelecionado");
		if(StringUtils.isEmpty( codSistema ))
		{
			return null;
		}
		if(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(METODO))
		{
			return listarSistemas();
		}

		try{
			baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
			Sistema sistema = new Sistema();
			sistema.setCodSistemaSist(codSistema);
			Object[] args = new Object[]{ sistema };
			Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			setSistemaVo((Sistema) objRetorno[0]);
			this.METODO = BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO;
		}catch(CommandException commandException){
			commandException.printStackTrace();
			tratarErro(commandException);
		}
		return null;
	}


	public String prepararInclusao()
	{
		if(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO.equals(METODO))
		{
			return listarSistemas();
		}
		try{
			
			baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO);
			Object[] args = new Object[]{ getSistemaVo() };
			Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			setSistemaVo((Sistema) objRetorno[0]);
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
			Object[] args = new Object[]{ getSistemaVo() };
			Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			setSistemaVo(new Sistema());
			return listarSistemas();
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
 			this.setSistemaVo(new Sistema());
	 		this.getSistemaSearchVo().setSistema(new Sistema());
 		}
	}
	
	public UIData getModelo() {
		return modelo;
	}


	public void setModelo(UIData modelo) {
		this.modelo = modelo;
	}


	public DataModel getListaGrupos() {
		return listaSistemas;
	}


	public void setListaGrupos(DataModel listaGrupos) {
		this.listaSistemas = listaGrupos;
	}




	public SistemaSearchVo getSistemaSearchVo() {
		return sistemaSearchVo;
	}


	public void setSistemaSearchVo(SistemaSearchVo sistemaSearchVo) {
		this.sistemaSearchVo = sistemaSearchVo;
	}


	public Sistema getSistemaVo() {
		return sistemaVo;
	}


	public void setSistemaVo(Sistema sistemaVo) {
		this.sistemaVo = sistemaVo;
	}


	public String getMETODO() {
		return METODO;
	}


	public void setMETODO(String metodo) {
		METODO = metodo;
	}


	public DataModel getListaSistemas() {
		return listaSistemas;
	}


	public void setListaSistemas(DataModel listaSistemas) {
		this.listaSistemas = listaSistemas;
	}
	

}
