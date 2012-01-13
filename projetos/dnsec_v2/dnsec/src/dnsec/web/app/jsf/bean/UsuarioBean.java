package dnsec.web.app.jsf.bean;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;

import dnsec.modulos.cadastros.sistema.vo.SistemaSearchVo;
import dnsec.modulos.cadastros.usuario.vo.UsuarioSearchVo;
import dnsec.modulos.cadastros.usuario.vo.UsuarioTelaVo;
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

public class UsuarioBean {

	private DataModel listaUsuarios = new ListDataModel();
	private UsuarioSearchVo usuarioSearchVo = new UsuarioSearchVo();
	private Usuario usuarioVo = new Usuario();
	private BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_USUARIOS);
	private UIData modelo = null;
	private String METODO = null;
	private String telaOrigemPesquisa = null;
	private String confirmacaoSenha = null;
	private UIData numDiasValidadeSenha = null;
	
	


	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}




	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}




	public String getTelaOrigemPesquisa() {
		return telaOrigemPesquisa;
	}




	public void setTelaOrigemPesquisa(String telaOrigemPesquisa) {
		this.telaOrigemPesquisa = telaOrigemPesquisa;
	}




	public String listarUsuariosInicio()
	{
		Map parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		telaOrigemPesquisa = (String) parametros.get("telaOrigemPesquisa");
		this.listaUsuarios.setWrappedData(Collections.EMPTY_LIST);
		this.METODO = BaseDispatchCRUDCommand.METODO_LISTAR;
		this.setUsuarioVo(new Usuario());
		this.getUsuarioSearchVo().setUsuario(new Usuario());
		return "LISTAR_USUARIOS";
	}

	
	
	
	public String listarUsuarios()
	{
		List listaUsuarios = null;
		baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
		baseDispatchCRUDCommand.setProximaAcaoPaginacao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
		Object[] args = new Object[]{ this.usuarioSearchVo };
		Usuario usuario = new Usuario();
		usuario.setCodUsuarioUsua(Constantes.COD_USR_ADM);
		GerenciadorJanelas.getInstance().setControleSegurancaVo(new ControleSegurancaVo(usuario, Collections.EMPTY_MAP, Collections.EMPTY_MAP, Collections.EMPTY_MAP));
		try{
			Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			baseDispatchCRUDCommand.getPagina().setRegistrosPorPagina(Long.MAX_VALUE);
			listaUsuarios = ((Pagina)objRetorno[0]).getListObjects();
			this.listaUsuarios.setWrappedData(listaUsuarios);
			this.METODO = BaseDispatchCRUDCommand.METODO_LISTAR;
			this.setUsuarioVo(new Usuario());
		}catch(CommandException commandException){
			commandException.printStackTrace();
			tratarErro(commandException);
		}
		return null;
	}


	public String excluir()
	{
		String codUsuario = 
			((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("frmUsuarios:usuariocodUsuarioUsua");
		if(!StringUtils.isEmpty( codUsuario ))
		{
			try{
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_EXCLUIR);
				Usuario usuario = new Usuario();
				usuario.setCodUsuarioUsua(codUsuario);
				Object[] args = new Object[]{ usuario };
				Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			}catch(CommandException commandException){
				commandException.printStackTrace();
				tratarErro(commandException);
			}
		}
		this.setUsuarioVo(new Usuario());
		return this.listarUsuarios();
	}

	public String prepararEdicao()
	{
		String codUsuario = 
			((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("frmUsuarios:usuariocodUsuarioUsua");
		if(StringUtils.isEmpty( codUsuario ))
		{
			return null;
		}
		if(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(METODO))
		{
			return listarUsuarios();
		}

		try{
			baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
			Usuario usuario = new Usuario();
			usuario.setCodUsuarioUsua(codUsuario);
			Object[] args = new Object[]{ usuario };
			Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			setUsuarioVo((Usuario) objRetorno[0]);
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
			return listarUsuarios();
		}
		try{
			
			baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO);
			Object[] args = new Object[]{ getUsuarioVo() };
			Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
			setUsuarioVo((Usuario) objRetorno[0]);
			this.METODO = BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO;
			this.confirmacaoSenha = null;
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
			
			if(!validarSenha())
			{
				CommandException exception = new CommandException();
				exception.setStrKeyConfigFile("key.erro.usuarios.senha.diferente.confirmacao");
				tratarErro(exception);
			}else
			{
				Object[] args = new Object[]{ getUsuarioVo() };
				Object objRetorno[] = baseDispatchCRUDCommand.executar( args );
				setUsuarioVo(new Usuario());
				return listarUsuarios();
			}
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
 			this.setUsuarioVo(new Usuario());
	 		this.getUsuarioSearchVo().setUsuario(new Usuario());
 		}
	}
	
	public UIData getModelo() {
		return modelo;
	}


	public void setModelo(UIData modelo) {
		this.modelo = modelo;
	}


	public DataModel getListaGrupos() {
		return listaUsuarios;
	}


	public void setListaGrupos(DataModel listaGrupos) {
		this.listaUsuarios = listaGrupos;
	}




	public UsuarioSearchVo getUsuarioSearchVo() {
		return usuarioSearchVo;
	}


	public void setUsuarioSearchVo(UsuarioSearchVo sistemaSearchVo) {
		this.usuarioSearchVo = sistemaSearchVo;
	}


	public Usuario getUsuarioVo() {
		return usuarioVo;
	}


	public void setUsuarioVo(Usuario sistemaVo) {
		this.usuarioVo = sistemaVo;
	}


	public String getMETODO() {
		return METODO;
	}


	public void setMETODO(String metodo) {
		METODO = metodo;
	}


	public DataModel getListaUsuarios() {
		return listaUsuarios;
	}


	public void setListaUsuarios(DataModel listaSistemas) {
		this.listaUsuarios = listaSistemas;
	}
	
	private boolean validarSenha(){
		return new EqualsBuilder().append(getUsuarioVo().getCodSenhaUsua(), this.confirmacaoSenha).isEquals();
	}

	public void diasValidadeSenhaAlterado(ValueChangeEvent evt)
	{
		getUsuarioVo().setNumDiasValidadesenhaUsua(new Long( ((Number)evt.getNewValue()).longValue()));
	}
	
	
}
