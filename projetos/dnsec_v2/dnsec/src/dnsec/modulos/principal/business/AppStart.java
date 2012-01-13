package dnsec.modulos.principal.business;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dnsec.modulos.cadastros.grupo.business.CommandGrupos;
import dnsec.modulos.cadastros.grupo.vo.GrupoSearchVo;
import dnsec.modulos.testeacessodados.ui.FrmTesteConexao;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.command.impl.BaseDispatchCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Grupo;
import dnsec.shared.database.hibernate.util.HibernateUtil;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.swing.base.BaseJButton;
import dnsec.shared.swing.base.BaseJFrame;
import dnsec.shared.swing.base.BaseJLabel;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.Pagina;

public class AppStart {
    static Logger logger = Logger.getLogger(AppStart.class.getName());

	static File arquivoSelecionado = null;
	static BaseJLabel lblBaseSelecionada = new BaseJLabel("nenhuma");

    /*<?xml version='1.0' encoding='utf-8'?>
    <!DOCTYPE hibernate-configuration PUBLIC
    "-//Hibernate/Hibernate Configuration DTD//EN"
    "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
    <hibernate-configuration>
    	<session-factory>
    		<property name="show_sql">true</property>
    		<property name="dialect">org.hibernate.dialect.MySQLDialect</property>
    		<property name="connection.driver_class">com.mysql.jdbc.Driver</property>
    		<property name="connection.url">jdbc:mysql://192.168.0.116:3306/dnsec</property> 
    		<property name="hibernate.c3p0.min_size">1</property>
    		<property name="hibernate.c3p0.max_size">5</property>
    		<property name="hibernate.c3p0.timeout">1800</property>
    		<property name="hibernate.c3p0.max_statements">50</property>
    		<property name="connection.username">dnsec</property>
    		<property name="connection.password">dnsec</property>
    		
    		<!-- Mapping files -->
    		<mapping resource="config/Sistema.hbm.xml"/>
    		<mapping resource="config/Funcao.hbm.xml"/>
    		<mapping resource="config/Grupo.hbm.xml"/>
    		<mapping resource="config/Usuario.hbm.xml"/>
    	</session-factory>
    </hibernate-configuration>*/

    static{
    	URL urlArquivoConfig = AppStart.class.getClassLoader().getResource(Constantes.ARQUIVO_CONFIG_LOG);
    	URL urlArquivoBancoDados = AppStart.class.getClassLoader().getResource("config/dnsec-hibernate-cfg-td.xml");
    	StringBuilder str = new StringBuilder();
    	BufferedReader reader = null;
    	BufferedWriter writer = null;
		try {
			System.out.println("Configurando log...");
			reader = new BufferedReader(
					new InputStreamReader ( 
							urlArquivoConfig.openStream())
			);
	    	String linha = "";
	    	while( ( linha = reader.readLine() ) != null)
	    	{
	    		str.append(linha + System.getProperty("line.separator"));
	    	}
	    	SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhhmmss");
	    	String caminhoArquivo = "dnsec-log-config.txt";
	    	String caminhoDirTemp = System.getProperty("user.home");
	    	File arquivoLogTemp = new File(caminhoDirTemp, caminhoArquivo);
	    	if(!arquivoLogTemp.exists())
	    	{
		    	arquivoLogTemp.createNewFile();
		    	writer = new BufferedWriter(
		    			new FileWriter(arquivoLogTemp)
		    	);
		    	writer.write(str.toString());
		    	writer.close();
		    	writer = null;
		    	System.out.println( "########## TEMP_DIR --> " + caminhoDirTemp);
		    	System.out.println( "############### TEMP_LOG --> " + arquivoLogTemp.getAbsolutePath());
		    	System.out.println("Log temporário configurado com sucesso!");
	    	}else{
		    	System.out.println("Log temporário já existem em " + arquivoLogTemp.getAbsolutePath());
	    	}	
	    	PropertyConfigurator.configureAndWatch(arquivoLogTemp.getPath(), 15000);
	    	
	    	System.out.println("Copiando arquivo de configuração do banco default...");
	    	reader = new BufferedReader(
					new InputStreamReader ( 
							urlArquivoBancoDados.openStream())
			);
	    	linha = "";
	    	str.delete(0, str.length());
	    	while( ( linha = reader.readLine() ) != null)
	    	{
	    		str.append(linha + System.getProperty("line.separator"));
	    	}
	    	caminhoArquivo = "dnsec-hibernate-cfg-td.xml";
	    	arquivoLogTemp = new File(caminhoDirTemp, caminhoArquivo);
	    	if(!arquivoLogTemp.exists())
	    	{
		    	arquivoLogTemp.createNewFile();
		    	writer = new BufferedWriter(
		    			new FileWriter(arquivoLogTemp)
		    	);
		    	writer.write(str.toString());
		    	writer.close();
		    	writer = null;
		    	System.out.println("Arquivo default para o banco de dados gravado em " + arquivoLogTemp.getAbsolutePath());
	    	}else{
		    	System.out.println("Arquivo default para o banco de dados já existe em " + arquivoLogTemp.getAbsolutePath());
	    	}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Impossível configurar log temporário, configurando log default!");
	    	PropertyConfigurator.configure(urlArquivoConfig);
		}finally{
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    	
    }
	
	/**
	 * Exibe a janela principal do sistema
	 * */
	public static void main(String[] args) throws Exception{
		
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		/**
		 * Monta o formulário para o usuário selecionar 
		 * a base de dados
		 * */
		final BaseJFrame frmOpcoesBanco = new BaseJFrame("DNSEC");
		frmOpcoesBanco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frmOpcoesBanco.setIconImage(Toolkit.getDefaultToolkit().getImage(AppStart.class.getClassLoader().getResource("config/resource/dnsec.gif")));
		BaseJLabel lblSelecaoBase = new BaseJLabel("Base de dados:");
		lblBaseSelecionada.setForeground(Color.BLUE);
		BaseJButton cmdSelecionar = new BaseJButton("Confirmar", GerenciadorJanelas.ICONE_BOTAO_CONFIRMAR);
		BaseJButton cmdSelecionarArquivo = new BaseJButton("...");
		
		JPanel painelControles = new JPanel();
		painelControles.add(lblSelecaoBase);
		painelControles.add(lblBaseSelecionada);
		painelControles.add(cmdSelecionarArquivo);		
		
		JPanel painelControlesCmd = new JPanel();
		painelControlesCmd.setLayout(new FlowLayout(FlowLayout.CENTER));
		painelControlesCmd.add(cmdSelecionar);
		frmOpcoesBanco.getContentPane().setLayout(new BorderLayout());
		frmOpcoesBanco.getContentPane().add(painelControles, BorderLayout.CENTER);
		frmOpcoesBanco.getContentPane().add(painelControlesCmd, BorderLayout.SOUTH);
		cmdSelecionar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(arquivoSelecionado != null)
				{
					Constantes.ARQUIVO_CONFIG_HIBERNATE = arquivoSelecionado.getAbsolutePath();
					frmOpcoesBanco.dispose();
					FrmTesteConexao frm = new FrmTesteConexao();
				    /* Inicializa o formulário que faz o teste da conexão com
					 * a base de dados. Caso o teste ocorra com sucesso, o formulário
					 * de teste inicializa o formulário principal da aplicação.
					 * */
					frm.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(frmOpcoesBanco,"É necessário selecionar o arquivo de configuração!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		cmdSelecionarArquivo.addActionListener
		(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						JFileChooser selecaoArquivo = new JFileChooser();
						selecaoArquivo.addChoosableFileFilter
						(
								new FileFilter()
								{
									public boolean accept(File f) 
									{
										 if (f.isDirectory()) 
										 {
												return true;
									     }
									     String nomeArquivo = f.getName();
									     if (nomeArquivo != null) 
									     {
											if (nomeArquivo.endsWith("xml"))
											{
											        return true;
											} else 
											{
											    return false;
											}
									    }
									    return false;							
								    }
								
								public String getDescription()
								{
									return "XML para a base de dados (*.xml)";
								}
					          }
						
						);
						
						int returnVal = selecaoArquivo.showOpenDialog(frmOpcoesBanco);
				        if (returnVal == JFileChooser.APPROVE_OPTION) {
				            arquivoSelecionado = selecaoArquivo.getSelectedFile();
				            lblBaseSelecionada.setText(arquivoSelecionado.getAbsolutePath());
				            String strUserDir = System.getProperty("user.home");
				            frmOpcoesBanco.pack();
				            frmOpcoesBanco.centralizarJanelaNaTela();
				            try{
				            	Properties prop = new Properties();
				            	prop.put("banco.de.dados", arquivoSelecionado.getAbsolutePath());
				            	prop.store(new FileOutputStream(new File(strUserDir,"prefs.properties")), "");
				            }catch(Exception e){
				            	e.printStackTrace();
				            }
				        }
						
				}
					
					
					
			}
	);
		

        try{
            String strUserDir = System.getProperty("user.home");
        	Properties prop = new Properties();
        	prop.load(new FileInputStream(new File(strUserDir,"prefs.properties")));
        	if(prop.getProperty("banco.de.dados") != null)
        	{
        		File arquivoConfig = new File(prop.getProperty("banco.de.dados"));
        		if(arquivoConfig.exists())
        		{
        			lblBaseSelecionada.setText(arquivoConfig.getAbsolutePath());
        			arquivoSelecionado = arquivoConfig;
        		}
        	}
        }catch(Exception e){
        	e.printStackTrace();
        }

		
		frmOpcoesBanco.pack();
		frmOpcoesBanco.centralizarJanelaNaTela();
		frmOpcoesBanco.setVisible(true);
		
	}
	
	
	/**
	 * Método utilitário para testar o hibernate
	 * */
	private static void testesHibernate(){
		
		Session session = null; 
		Transaction t = null;
		try{
			session = HibernateUtil.openSession("config/dnsec-hibernate-cfg.xml");
			t = session.beginTransaction();
			
			
			/*Usuario usuario = (Usuario) session.get(Usuario.class, "AANDRADE");
			System.out.println("QTDE Grupos --> " + usuario.getGrupos().size());
			Grupo grupo = (Grupo) session.get(Grupo.class, "PUB05");
			
			usuario.getGrupos().add(grupo);
			grupo.getUsuarios().add(usuario);*/
			
			/*session.persist(usuario);
			session.persist(grupo);/*
			
			/*Criteria crit = session.createCriteria(Grupo.class);
			Iterator it = crit.list().iterator();
			while(it.hasNext()){
				Grupo grupo = (Grupo) it.next();
				System.out.println("Grupo --> " + grupo.getDescrGrupoGrup());
			}*/
			
			/*Criteria crit = session.createCriteria(Usuario.class);
			List listUsuarios = crit.list();
			Iterator it = listUsuarios.iterator();
			System.out.println("Usuários --> " + listUsuarios.size());
			while(it.hasNext()){
				Usuario usuario = (Usuario) it.next();
				//if(usuario != null){
					System.out.println("Usuário --> " + usuario.getCodUsuarioUsua());
				//}
			}*/

			
			/*Sistema sistema = ((Sistema) session.get(Sistema.class, "SIT"));
			Set listaFuncoes = sistema.getFuncoes();
			System.out.println(sistema);
			Iterator itFuncoes = listaFuncoes.iterator();
			while(itFuncoes.hasNext()){
				Funcao funcao = (Funcao) itFuncoes.next();
				System.out.println("\nFuncao --> " + funcao.getNomeFunc());
				System.out.println("--Sistema --> " + funcao.getSistema().getCodSistemaSist());
				if(funcao.getFuncaoPai() != null){
					System.out.println("--Função pai --> " + funcao.getFuncaoPai().getNomeFunc());
				}	
			}*/
			
			/*Funcao funcao = ( (Funcao) session.get(Funcao.class, new FuncaoPk( new Long(2), "001" )));
			funcao.setDescrFuncaoFunc(null);
			session.update(funcao);
			Iterator itFuncoes = funcao.getFuncoesFilhas().iterator();
			while(itFuncoes.hasNext()){
				Funcao funcaoTMP = (Funcao) itFuncoes.next();
				System.out.println("Funcao Filha --> " + funcaoTMP.getCompId().getCodFuncaoFunc() + "  " + funcaoTMP.getNomeFunc());
			}*/
			
			/*session.flush();
			t.commit();*/
			
			/*ICommand commandLogin = new CommandLogin();
			Usuario usuario = new Usuario();
			usuario.setCodUsuarioUsua("MWDC");
			usuario.setCodSenhaUsua("MWDC");
			commandLogin.executar(new Object[]{usuario});*/
			
			/*Testes com grupos*/
			/**************Teste listagem com filtros***********************************************/
			BaseDispatchCommand cmd = new CommandGrupos();
			cmd.setStrMetodo(BaseDispatchCRUDCommand.METODO_LISTAR);
			GrupoSearchVo grupoSearchVo = new GrupoSearchVo();
			//Filtro pela descrição
			//grupoSearchVo.getGrupo().setDescrGrupoGrup("abcdef");
			//Filtro pelo código
			grupoSearchVo.getGrupo().setCodGrupoGrup("jornaleiro");
			Pagina pagina = (Pagina) cmd.executar(new Object[]{ grupoSearchVo } )[0];
			List listaGrupos = pagina.getListObjects();
			Iterator itGrupos = listaGrupos.iterator();
			while(itGrupos.hasNext()){
				Grupo grupo = (Grupo) itGrupos.next();
				//System.out.println(" Grupo --> " + grupo.getDescrGrupoGrup());
			}
			/****************************************************************************/
			
			/**************Teste de inclusão de grupo**********************************************
			BaseDispatchCommand cmd = new CommandGrupos();
			cmd.setStrMetodo(CommandGrupos.METODO_REMOVER_FUNCAO);
			Grupo grupo = new Grupo("DST", "GRUPO DE TESTE");
			Funcao funcao = new Funcao();
			funcao.setCompId(new FuncaoPk(new Long(995),"CEV"));
			cmd.executar(new Object[]{ grupo, funcao });
			
			/****************************************************************************/
		
			t.commit();
			
		}catch(Exception e){
			if(e instanceof CommandException){
				
			}
			if(t != null){
				try{t.rollback();}catch(Exception e2){}
			}
			e.printStackTrace();
		}finally{
			if(session != null){
				HibernateUtil.closeSession(session);
			}
		}
	}
}
