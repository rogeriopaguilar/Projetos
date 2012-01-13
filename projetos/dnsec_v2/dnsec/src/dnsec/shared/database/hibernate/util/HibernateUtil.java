/**
 * Classe utilitária para a utilização
 * do Hibernate. Adaptada da classe presente
 * no guia de referência do Hibernate para acomodar
 * mais de um arquivo de configuração.
 * 
 * @version 0.1
 * @author Rogério de Paula Aguilar
 * */
package dnsec.shared.database.hibernate.util;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static Log log = LogFactory.getLog(HibernateUtil.class);
	private static Map mapaSessionFactory = Collections.synchronizedMap(new HashMap());

	private static Map mapaSession = Collections.synchronizedMap(new HashMap());
	
	
	/**
	 * Retorna o objeto SessionFactory associado ao arquivo de configuração
	 * passado como parâmetro e faz o cache do objeto SessionFactory.
	 * 
	 * @param strConfigFile caminho do arquivo de configuração
	 * @return SessionFactory sessionFactory associada ao arquivo
	 * @throws ExceptionInInitializerError caso o objeto ainda não exista e ocorra algum erro durante a inicialização do mesmo
	 * */
	private synchronized static SessionFactory getSessionFactory(String strConfigFile) throws ExceptionInInitializerError{
		SessionFactory sessionFactory = (SessionFactory) mapaSessionFactory.get(strConfigFile);
		if(sessionFactory == null){
			try {
				if(HibernateUtil.class.getClassLoader().getResource(strConfigFile) != null)
				{
					// Create the SessionFactory
					sessionFactory = new Configuration().configure(HibernateUtil.class.getClassLoader().getResource(strConfigFile))
							.buildSessionFactory();
				}else{
					// Create the SessionFactory
					sessionFactory = new Configuration().configure(new File(strConfigFile))
							.buildSessionFactory();
				}
				mapaSessionFactory.put(strConfigFile, sessionFactory);
			} catch (Throwable ex) {
				// Make sure you log the exception, as it might be swallowed
				log.error("Initial SessionFactory creation failed.", ex);
				throw new ExceptionInInitializerError(ex);
			}
		}
		
		return sessionFactory;
	}

	
	
	public static final ThreadLocal session = new ThreadLocal();

	/**
	 * Retorna o objeto session associado com a Thread Atual de execução.
	 * 
	 * @param strConfigFile caminho do arquivo de configuração
	 * @return Session session associada à thread atual
	 * @throws ExceptionInInitializerError caso o objeto ainda não exista e ocorra algum erro durante a inicialização do mesmo
	 * */
	public static Session currentSession(String strConfigFile) {
		Session s = (Session) session.get();
		// Open a new Session, if this Thread has none yet
		if (s == null) {
			s = getSessionFactory(strConfigFile).openSession();
			session.set(s);
		}
		return s;
	}

	/**
	 * Fecha o objeto session associado com a Thread Atual de execução.
	 */
	public static void closeCurrentSession() {
		Session s = (Session) session.get();
		if (s != null)
			s.close();
		session.set(null);
	}

	/**
	 * Retorna um novo objeto session 
	 * @param strConfigFile caminho do arquivo de configuração
	 * @return Session novo objeto session 
	 * @throws ExceptionInInitializerError caso o objeto ainda não exista e ocorra algum erro durante a inicialização do mesmo
	 * */

	public static Session openSession(String strConfigFile) {
		Session s = getSessionFactory(strConfigFile).openSession(); //--> Como normalmente fazemos em applicações web
		/*Session s = null;
		SessionFactory factory = getSessionFactory(strConfigFile);
		synchronized(mapaSession)
		{
			log.debug("Procurando session no cache...");
			s = (Session) mapaSession.get(strConfigFile + "Session");
			if(s == null){
				s = factory.openSession();
				log.debug("Registrando session no cache...");
				mapaSession.put(strConfigFile + "Session", s);
			}else{
				log.debug("Session encontrada no cache...");
			}
		}*/
		return s;
	}

	/**
	 * Fecha o objeto passado como parâmetro
	 */
	public static void closeSession(Session s) {
		if (s != null)
			s.close();
	}

}