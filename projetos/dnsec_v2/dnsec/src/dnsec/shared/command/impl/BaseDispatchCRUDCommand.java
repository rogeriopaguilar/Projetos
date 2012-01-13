/**
 * Classe b�sica para padronizar os m�todos CRUD 
 * da aplica��o.
 * @author raguilar
 * */

package dnsec.shared.command.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dnsec.shared.database.hibernate.util.HibernateUtil;
import dnsec.shared.icommand.IValidador;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.Pagina;

public abstract class BaseDispatchCRUDCommand extends BaseDispatchCommand implements IValidador{

	/**
	 * Objeto que encapsula as informa��es sobre a pagina��o dos 
	 * dados na tela
	 * */
	protected Pagina pagina = new Pagina();
	
	/**
	 * Configura a pr�xima a��o que ser� realizada durante
	 * a pagina��o de dados (olhar constantes na classe p�gina)
	 * */
	public void setProximaAcaoPaginacao(int proximaAcaoPaginacao){
		   pagina.setProximaAcao(proximaAcaoPaginacao);
	}
	
	/**
	 * Quem chama o m�todo numa subclasse de BaseDispatchCRUDCommand
	 * atrav�s de uma refer�ncia a um BaseDispatchCommand deve
	 * configurar a propriedade strMetodo com as constantes
	 * definidas abaixo antes de chamar o m�todo executar.
	 * */
	public static final String METODO_LISTAR = "listar";
	public static final String METODO_PREPARAR_INCLUSAO = "prepararInclusao";
	public static final String METODO_PREPARAR_EDICAO = "prepararEdicao";
	public static final String METODO_CONFIRMAR_INCLUSAO = "confirmarInclusao";
	public static final String METODO_CONFIRMAR_EDICAO = "confirmarEdicao";
	public static final String METODO_EXCLUIR = "excluir";

	/**
	 * M�todos padronizados para opera��es CRUD
	 * */
	public abstract Object[] listar(Object[] args) throws CommandException;
	public abstract Object[] prepararInclusao(Object[] args) throws CommandException;
	public abstract Object[] prepararEdicao(Object[] args) throws CommandException;
	public abstract Object[] confirmarInclusao(Object[] args) throws CommandException;
	public abstract Object[] confirmarEdicao(Object[] args) throws CommandException;
	public abstract Object[] excluir(Object[] args) throws CommandException;

	/**
	 * Objetos do hibernate
	 * */
	private Session session;
	private Transaction transaction;
	
	/**
	 * Indica se o objeto session foi ou n�o passado como par�metro
	 * */
	protected boolean sessaoExterna = false;

	
	/**
	 * Inicializa a transa��o. Se o objeto session foi passado como par�metro no construtor,
	 * a transa��o n�o � inicializada, pois a mesma deve ser controlada pelo chamador do m�todo.
	 * */
	protected void beginTransaction(){
		if(!this.isSessaoExterna()){
			//Se a sess�o n�o foi passada no construtor, obt�m uma nova
			synchronized(this){
				if(getSession() == null || !getSession().isOpen()){
					session = HibernateUtil.openSession(Constantes.ARQUIVO_CONFIG_HIBERNATE);
					setTransaction(null);
				}
				if(getTransaction() == null)
				{
					setTransaction(getSession().beginTransaction());
				}
			}
		}
	}

	/**
	 * Faz o commit da transa��o apenas no caso dela ter sido inicializada
	 * na inst�ncia da classe. Se a sess�o foi passada no construtor,
	 * o commit deve ser tratado em outra inst�ncia
	 * */
	protected void commitTransaction(){
		if(!this.isSessaoExterna()){
			if(getTransaction() != null)
			{
				getTransaction().commit();
			}
		}
	}

	/**
	 * Faz o rollback da transa��o apenas no caso dela ter sido inicializada
	 * na inst�ncia da classe. Se a sess�o foi passada no construtor,
	 * o rollback deve ser tratado em outro ponto e n�o diretamente aqui
	 * */
	protected void rollbackTransaction(){
		if(!this.isSessaoExterna()){
			if(getTransaction() != null)
			{
				getTransaction().rollback();
			}
		}
	}

	/**
	 * Sobrescreve o m�todo definido na superclasse para inicializar a transa��o, comitar
	 * a mesma ou realizar o rollback, caso ocorra algum erro
	 * no atributo strMetodo
	 * */
	public Object[] executar(Object[] args) throws CommandException {
		Object objReturn = null;
		try{
			/*
			 * Inicializa a transa��o. Depois chama o m�todo implementado na subclasse.
			 * Caso o m�todo retorne sem erros, faz o commit da transa��o.
			 * Sen�o informa o erro e faz o rollback da transa��o.
			 * */
			beginTransaction();
			objReturn = this.getClass().getDeclaredMethod(this.getStrMetodo(), new Class[]{ Object[].class} ).invoke(this, new Object[] { args });
			commitTransaction();
		}catch(NoSuchMethodException e){
			rollbackTransaction();
			e.printStackTrace();
			throw new CommandException("M�todo configurado em strMetodo n�o encontrado na classe!", e);
		} catch (IllegalArgumentException e) {
			rollbackTransaction();
			e.printStackTrace();
			throw new CommandException("Erro ao executar m�todo configurado em strMetodo!", e);
		} catch (SecurityException e) {
			rollbackTransaction();
			e.printStackTrace();
			throw new CommandException("Erro ao executar m�todo configurado em strMetodo!", e);
		} catch (IllegalAccessException e) {
			rollbackTransaction();
			e.printStackTrace();
			throw new CommandException("Erro ao executar m�todo configurado em strMetodo!", e);
		} catch (InvocationTargetException e) {
			rollbackTransaction();
			e.printStackTrace();
			if(e.getTargetException() != null && (e.getTargetException() instanceof CommandException)){
				throw (CommandException )e.getTargetException();
			}else{
				throw new CommandException("Erro ao executar m�todo configurado em strMetodo!", e);
			}
		} catch (Exception e) {
			rollbackTransaction();
			e.printStackTrace();
			if(e instanceof CommandException){
				throw (CommandException)e;
			}else{
				throw new CommandException("Erro n�o esperado ao executar m�todo configurado em strMetodo!", e);
			}
		}finally{
			//Fecha a sess�o do hibernate, independente do que aconte�a.
			closeSession();
		}	
		return (Object[])objReturn;
	}

	public void executarEspecifico(String strNomeMetodo, Object[] args, Object retorno) throws CommandException {
		Object objReturn = null;
		try{
			/*
			 * Inicializa a transa��o. Depois chama o m�todo implementado na subclasse.
			 * Caso o m�todo retorne sem erros, faz o commit da transa��o.
			 * Sen�o informa o erro e faz o rollback da transa��o.
			 * */
			beginTransaction();
			List listaClasses = Arrays.asList(args);
			List listaTipoClasses = new LinkedList();
			Iterator it = listaClasses.iterator();
			while(it.hasNext()){
				Object obj = it.next();
				Class classe = obj.getClass();
				listaTipoClasses.add(classe);
			}
			Class[] arrayClasses = new Class[listaTipoClasses.size()];
			listaTipoClasses.toArray(arrayClasses); 
			objReturn = this.getClass().getDeclaredMethod(strNomeMetodo, arrayClasses).invoke(this, args);
			retorno = objReturn;
			commitTransaction();
		}catch(NoSuchMethodException e){
			rollbackTransaction();
			//TODO --> Colocar log para indicar que o m�todo n�o foi encontrado
			e.printStackTrace();
			throw new CommandException("M�todo configurado em strMetodo n�o encontrado na classe!", e);
		} catch (IllegalArgumentException e) {
			rollbackTransaction();
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CommandException("Erro ao executar m�todo configurado em strMetodo!", e);
		} catch (SecurityException e) {
			rollbackTransaction();
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CommandException("Erro ao executar m�todo configurado em strMetodo!", e);
		} catch (IllegalAccessException e) {
			rollbackTransaction();
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CommandException("Erro ao executar m�todo configurado em strMetodo!", e);
		} catch (InvocationTargetException e) {
			rollbackTransaction();
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e.getTargetException() != null && (e.getTargetException() instanceof CommandException)){
				throw (CommandException )e.getTargetException();
			}else{
				throw new CommandException("Erro ao executar m�todo configurado em strMetodo!", e);
			}
		}finally{
			//Fecha a sess�o do hibernate, independente do que aconte�a.
			closeSession();
		}
	}


	
	
	/**
	 * Fecha a sess�o do hibernate se ela n�o foi passada no construtor.
	 * */
	protected void closeSession(){
		if(!this.isSessaoExterna()){
			if(getSession() != null)
			{
				HibernateUtil.closeSession(getSession());
				setTransaction(null);
			}
		}
	}

	
	/**
	 * Retorna se a sess�o foi ou n�o passada no construtor
	 * */
	public boolean isSessaoExterna() {
		return sessaoExterna;
	}
	
	
	public void setSessaoExterna(boolean sessaoExterna) {
		this.sessaoExterna = sessaoExterna;
	}
	
	public Session getSession() {
		return session;
	}
	
	public Transaction getTransaction() {
		return transaction;
	}
	
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	public BaseDispatchCRUDCommand() {
		super();
	}

	public BaseDispatchCRUDCommand(Session session) {
		super();
		this.session = session;
		this.sessaoExterna = true;
	}
	public Pagina getPagina() {
		return pagina;
	}
	public void setPagina(Pagina pagina) {
		this.pagina = pagina;
	}
	
	/**
	 * Ajusta as informa��es do objeto de pagina��o de acordo com a lista
	 * de objetos contidfa no objeto e com a pr�xima a��o configurada
	 * no objeto p�gina.
	 * As subclasses devem chamar este m�todo ao final do m�todo listar,
	 * ap�s configurar os objetos de busca (critPesquisa) e de contagem
	 * de registros (critContador).
	 * Ao final da execu��o do m�todo, as informa��es no objeto p�gina
	 * estar�o atualizadas.
	 * @param critPesquisa Objeto Criteria configurado com as informa��es para o filtro
	 *        da pesquisa
	 * @param critContador Objeto Criteria contendo a mesma configura��o de critPesquisa,
	 * 		  por�m utilizado para pesquisar o n�mero de registros retornados       
	 * */
	protected void paginarDados(Criteria critPesquisa, Criteria critContador){
		
		//Criteria critContador --> OBS: Voltar a utilizar quando a pagina��o no banco for realizada 
		
		int paginaAtual = (int)pagina.getPaginaAtual();
		switch(pagina.getProximaAcao()){
			case Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO:
			{
				paginaAtual = 1;
				break;
			}case Pagina.ACAO_MOVER_ANTERIOR_REGISTRO:
			{
				paginaAtual -= 1;
				if(paginaAtual <=  0 ){
					paginaAtual = 1;
				}
				break;
			}case Pagina.ACAO_MOVER_PROXIMO_REGISTRO:
			{
				paginaAtual++;
				break;
			}case Pagina.ACAO_MOVER_ULTIMO_REGISTRO:
			{
				paginaAtual = -1;
				break;
			}case Pagina.ACAO_MOVER_PAGINA_ESPECIFICA:
			{
				paginaAtual = (int)pagina.getPaginaEspecificaAMover();
				break;
			}default:{
				pagina.setProximaAcao(Pagina.ACAO_MOVER_PRIMEIRO_REGISTRO);
				paginaAtual = 1;
			}
		}

		if(pagina.getRegistrosPorPagina() <= 0){
			pagina.setRegistrosPorPagina(Pagina.REGISTROS_POR_PAGINA_DEFAULT);
		}

		List listaObjetosRetorno = new LinkedList( critPesquisa.list() );
		
		/*Elimina os registros duplicados
		 * OBS: Anteriormente a pagina��o era realizada no banco de dados,
		 * por�m devido a uma limita��o do hibernate com rela��o � utiliza��o
		 * de distinct na API Criteria, tive que substituir pela pagina��o
		 * "na tela" eliminando os objetos duplicados "na m�o"
		 */
		List listaTMP = new LinkedList();
		Iterator it = listaObjetosRetorno.iterator();
		while(it.hasNext()){
			Object obj = it.next();
			if(!listaTMP.contains(obj)){
				listaTMP.add(obj);
			}
		}

		Integer totalRegistros = listaTMP.size();
		pagina.setTotalRegistros(totalRegistros);

		if(totalRegistros.longValue() > pagina.getRegistrosPorPagina()){
			pagina.setTotalPaginas( (int ) (totalRegistros.longValue() / pagina.getRegistrosPorPagina()));
			if(totalRegistros.longValue() % pagina.getRegistrosPorPagina() > 0){
				pagina.setTotalPaginas( pagina.getTotalPaginas() + 1);
			}
		}else{
			pagina.setTotalPaginas( 1 );
		}	
		
		while(paginaAtual > pagina.getTotalPaginas()){
			paginaAtual--;
		}

		if(paginaAtual == -1){
			//�ltima P�gina
			paginaAtual = (int)pagina.getTotalPaginas();
		}
		
		
		long indiceInicialLista = (paginaAtual - 1) * pagina.getRegistrosPorPagina();
		long indiceFinalLista = paginaAtual * pagina.getRegistrosPorPagina();
		if(indiceFinalLista > listaTMP.size()){
			indiceFinalLista = listaTMP.size();
		}
		
		
		pagina.setListObjects(listaTMP.subList((int)indiceInicialLista, (int)indiceFinalLista));
		pagina.setPaginaAtual(paginaAtual);
	}	
	
	protected BeanUtils getBeanUtils(){
	    Converter converterBigDecimal = new org.apache.commons.beanutils.converters.BigDecimalConverter(null);
	    ConvertUtils.register(converterBigDecimal, BigDecimal.class);

	    Converter converterBigInteger = new org.apache.commons.beanutils.converters.BigIntegerConverter(null);
	    ConvertUtils.register(converterBigInteger, BigInteger.class);

	    Converter converterBoolean = new org.apache.commons.beanutils.converters.BooleanConverter(null);
	    ConvertUtils.register(converterBoolean, Boolean.class);
	    ConvertUtils.register(converterBoolean, Boolean.TYPE);

	    Converter converterByte = new org.apache.commons.beanutils.converters.ByteConverter(null);
	    ConvertUtils.register(converterByte, Byte.class);
	    ConvertUtils.register(converterByte, Byte.TYPE);

	    Converter converterCharacter = new org.apache.commons.beanutils.converters.CharacterConverter(null);
	    ConvertUtils.register(converterCharacter, Character.class);
	    ConvertUtils.register(converterCharacter, Character.TYPE);

	    Converter converterDouble = new org.apache.commons.beanutils.converters.DoubleConverter(null);
	    ConvertUtils.register(converterDouble, Double.class);
	    ConvertUtils.register(converterDouble, Double.TYPE);

	    Converter converterFloat = new org.apache.commons.beanutils.converters.FloatConverter(null);
	    ConvertUtils.register(converterFloat, Float.class);
	    ConvertUtils.register(converterFloat, Float.TYPE);

	    Converter converterInteger = new org.apache.commons.beanutils.converters.IntegerConverter(null);
	    ConvertUtils.register(converterInteger, Integer.class);
	    ConvertUtils.register(converterInteger, Integer.TYPE);

	    Converter converterLong = new org.apache.commons.beanutils.converters.LongConverter(null);
	    ConvertUtils.register(converterLong, Long.class);
	    ConvertUtils.register(converterLong, Long.TYPE);

	    Converter converterShort = new org.apache.commons.beanutils.converters.ShortConverter(null);
	    ConvertUtils.register(converterShort, Short.class);
	    ConvertUtils.register(converterBoolean, Short.TYPE);

	    Converter converterDate = new org.apache.commons.beanutils.converters.SqlDateConverter(null);
	    ConvertUtils.register(converterDate, Date.class);

	    Converter converterTime = new org.apache.commons.beanutils.converters.SqlTimeConverter(null);
	    ConvertUtils.register(converterTime, Time.class);

	    Converter converterTimestamp = new org.apache.commons.beanutils.converters.SqlTimestampConverter(null);
	    ConvertUtils.register(converterTimestamp, Timestamp.class);

	    return new BeanUtils();
	}
}
