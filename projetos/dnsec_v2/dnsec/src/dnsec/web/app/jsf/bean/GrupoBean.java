package dnsec.web.app.jsf.bean;

import java.util.Collections;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.commons.lang.StringUtils;

import dnsec.modulos.cadastros.grupo.vo.GrupoSearchVo;
import dnsec.modulos.controle.seguranca.vo.ControleSegurancaVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Grupo;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.factory.CommandFactory;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.Pagina;
import dnsec.web.util.ErroUtil;

public class GrupoBean {

	private DataModel listaGrupos = new ListDataModel();
	private GrupoSearchVo grupoSearchVo = new GrupoSearchVo();
	private Grupo grupoVo = new Grupo();
	private BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_GRUPOS);
	private UIData modelo = null;
	private String METODO = null;
	
	public GrupoSearchVo getGrupoSearchVo() {
		return grupoSearchVo;
		
	}


	public void setGrupoSearchVo(GrupoSearchVo grupoSearchVo) {
		this.grupoSearchVo = grupoSearchVo;
	}


	public String listarGruposInicio()
	{
		this.listaGrupos.setWrappedData(Collections.EMPTY_LIST);
		this.METODO = BaseDispatchCRUDCommand.METODO_LISTAR;
		this.setGrupo(new Grupo());
		this.getGrupoSearchVo().setGrupo(new Grupo());
		return "LISTAR_GRUPOS";
	}

	
	public String listarGrupos()
	{
		List listaGrupos = null;
		baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
		baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
		Object[] args = new Object[]{ this.grupoSearchVo };
		Usuario usuario = new Usuario();
		usuario.setCodUsuarioUsua(Constantes.COD_USR_ADM);
		GerenciadorJanelas.getInstance().setControleSegurancaVo(new ControleSegurancaVo(usuario, Collections.EMPTY_MAP, Collections.EMPTY_MAP, Collections.EMPTY_MAP));
		try{
			Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			baseDispatchCRUDCommand.getPagina().setRegistrosPorPagina(Long.MAX_VALUE);
			listaGrupos = ((Pagina)objRetorno[0]).getListObjects();
			this.listaGrupos.setWrappedData(listaGrupos);
			this.METODO = BaseDispatchCRUDCommand.METODO_LISTAR;
			this.setGrupo(new Grupo());
		}catch(CommandException commandException){
			commandException.printStackTrace();
			tratarErro(commandException);
		}
		return null;
	}


	public String excluir()
	{
		if(!StringUtils.isEmpty(getGrupo().getCodGrupoGrup()))
		{
			try{
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_EXCLUIR);
				Object[] args = new Object[]{ getGrupo() };
				Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			}catch(CommandException commandException){
				commandException.printStackTrace();
				tratarErro(commandException);
			}
		}
		this.setGrupo(new Grupo());
		return this.listarGrupos();
	}

	public String prepararEdicao()
	{
		if(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(METODO))
		{
			return listarGrupos();
		}
		if(!StringUtils.isEmpty(getGrupo().getCodGrupoGrup()))
		{
			try{
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
				Object[] args = new Object[]{ getGrupo() };
				Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
				setGrupo(new Grupo());
				getGrupoSearchVo().setGrupo((Grupo) objRetorno[0]);
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
			return listarGrupos();
		}
		try{
			
			baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO);
			Object[] args = new Object[]{ getGrupo() };
			Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			setGrupo(new Grupo());
			getGrupoSearchVo().setGrupo((Grupo) objRetorno[0]);
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
			Object[] args = new Object[]{ getGrupoSearchVo().getGrupo() };
			Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			setGrupo(new Grupo());
			getGrupoSearchVo().setGrupo(new Grupo());
			return listarGrupos();
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
 			this.setGrupo(new Grupo());
	 		this.getGrupoSearchVo().setGrupo(new Grupo());
 		}
	}
	
	public UIData getModelo() {
		return modelo;
	}


	public void setModelo(UIData modelo) {
		this.modelo = modelo;
	}


	public DataModel getListaGrupos() {
		return listaGrupos;
	}


	public void setListaGrupos(DataModel listaGrupos) {
		this.listaGrupos = listaGrupos;
	}


	public Grupo getGrupo() {
		return grupoVo;
	}


	public void setGrupo(Grupo grupoVo) {
		this.grupoVo = grupoVo;
	}


	public String getMETODO() {
		return METODO;
	}


	public void setMETODO(String metodo) {
		METODO = metodo;
	}
	

}
