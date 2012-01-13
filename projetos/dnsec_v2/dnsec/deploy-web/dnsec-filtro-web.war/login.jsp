<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>Login</title>
		
		<script type="text/javascript" src="js/ajaxbase.js"></script>
		<script type="text/javascript" src="js/xml.js"></script>
		<script type="text/javascript" src="js/sarissa.js"></script>  
		<script type="text/javascript" src="js/javeline_xpath.js"></script>
		<script type="text/javascript" src="js/javeline_xslt.js"></script>
		<script type="text/javascript" src="js/cookies.js"></script>
		<script type="text/javascript">
		
		<!--

			 var usuarioConectado = false;
			 
		     String.prototype.replaceAll = function(strTarget, strSubString )
		     {
			     var strText = this;
			     var intIndexOfMatch = strText.indexOf( strTarget );
			     while (intIndexOfMatch != -1){
				     strText = strText.replace( strTarget, strSubString )
				     intIndexOfMatch = strText.indexOf( strTarget );
			     }
			     return( strText );
		     };

			function init()
			{
				<c:if test="${not empty requestScope['dnsec.erro.esperado']}">
					alert("${requestScope['dnsec.erro.esperado']}");			
				</c:if>
				
				var cookie = new Cookie('dnsec.shared.database.hibernate.Usuario');
				if(cookie['dnsec.shared.database.hibernate.Usuario'] != null)
				{
					alert('Usuário ainda está conectado ao sistema!');
				}
			}
			
			function errorHandler (status, statusText)
			{
				//alert('Erro ao executar a requisição AJAX! Status --> ' + status + ' Status Text ' + statusText);
				document.getElementById('cmdLoginAjax').disabled = true;				
				document.getElementById('cmdLogoffAjax').disabled = true;				
				var janelaErro = window.open('','','width=400,height=400,toolbars=no');
				janelaErro.document.write(ERROR_HTTP_REQUEST);
			}

			function callbackLogin(xml){
					document.getElementById('cmdLoginAjax').disabled = true;
				   document.getElementById('cmdLogoffAjax').disabled = false;				
									
					var operacao = XML.getNode(xml, '/operacaodnsec/operacao/text()', null);					
					var status = XML.getNode(xml, '/operacaodnsec/statusoperacao/text()', null);					
					var mensagem = XML.getNode(xml, '/operacaodnsec/msgoperacao/text()', null);

					var ser = new XMLSerializer();
					operacao = ser.serializeToString(operacao);					
					status = ser.serializeToString(status);					
					mensagem = ser.serializeToString(mensagem);
					
					if(status != 'SUCESSO')
					{
						alert(mensagem.replace('<![CDATA[','').replace(']]>',''));
						document.getElementById('divControlesLogin').style.display = 'block';
						document.getElementById('divControlesLogoff').style.display = 'none';
						usuarioConectado = false;
						document.getElementById('cmdLoginAjax').disabled = false;				
					}else
					{
						document.getElementById('divControlesLogin').style.display = 'none';
						document.getElementById('divControlesLogoff').style.display = 'block';
						//Recupera os dados do usuário
						var values = new Array();
						//Parâmetros
						values['DNSEC_CHAVE_ACAO']='DNSEC_RECUPERAR_DADOS_USUARIO';
						values['DNSEC_REQUISICAO_AJAX'] = true; //Informa o filtro que a requisição foi feita utilizando ajax							
						HTTP.post("inicio.sec", values, callbackDadosUsuario, errorHandler); 
						usuarioConectado = true;
					}


			}
	
			function submitLoginAjax()
			{
				var values = new Array();
				//Parâmetros
				values['DNSEC_CHAVE_ACAO']=document.forms[0]['DNSEC_CHAVE_ACAO'].value;
				values['DNSEC_LOGIN']=document.forms[0]['DNSEC_LOGIN'].value;
				values['DNSEC_SENHA']=document.forms[0]['DNSEC_SENHA'].value;
				values['DNSEC_REQUISICAO_AJAX'] = true; //Informa o filtro que a requisição foi feita utilizando ajax							
				HTTP.post("inicio.sec", values, callbackLogin, errorHandler); 
				document.getElementById('cmdLoginAjax').disabled = true;				
			}

			function submitLogoffAjax()
			{
				var values = new Array();
				//Parâmetros
				values['DNSEC_CHAVE_ACAO']='DNSEC_LOGOFF';
				values['DNSEC_REQUISICAO_AJAX'] = true; //Informa o filtro que a requisição foi feita utilizando ajax							
				HTTP.post("inicio.sec", values, callbackLogoff, errorHandler); 
				document.getElementById('cmdLogoffAjax').disabled = true;				
			}

			function callbackLogoff(xml){
					var operacao = XML.getNode(xml, '/operacaodnsec/operacao/text()', null);					
					var status = XML.getNode(xml, '/operacaodnsec/statusoperacao/text()', null);					
					var mensagem = XML.getNode(xml, '/operacaodnsec/msgoperacao/text()', null);

					var ser = new XMLSerializer();
					operacao = ser.serializeToString(operacao);					
					status = ser.serializeToString(status);					
					mensagem = ser.serializeToString(mensagem);
					
					if(status == 'SUCESSO')
					{
						document.getElementById('divControlesLogin').style.display = 'block';
						document.getElementById('divControlesLogoff').style.display = 'none';
						alert('Você foi desconectado do sistema!');
						usuarioConectado = false;
						document.getElementById('cmdLogoffAjax').disabled = true;				
						document.getElementById('cmdLoginAjax').disabled = false;				
					}else
					{
						alert(mensagem.replace('<![CDATA[','').replace(']]>',''));
						document.getElementById('cmdLogoffAjax').disabled = false;				
					}
					
					
			}



			function callbackDadosUsuario(xml)
			{
					var codUsuario = XML.getNode(xml, '/dadosusuario/usuario/codusuariousua/text()', null);					
					var nomeUsuario = XML.getNode(xml, '/dadosusuario/usuario/nomeusuariousua/text()', null);					
					var emailUsuario = XML.getNode(xml, '/dadosusuario/usuario/codemailusua/text()', null);					
					var sistemas = XML.getNodes(xml, '/dadosusuario/sistemas/sistema/codsistemasist/text()', null);
					var grupos = XML.getNodes(xml, '/dadosusuario/grupos/grupo/codgrupogrup/text()', null);
					var funcoes = XML.getNodes(xml, '/dadosusuario/funcoes/funcao/nomefunc/text()', null);
					
					var ser = new XMLSerializer();
					if(codUsuario)
					{
						codUsuario = ser.serializeToString(codUsuario);
					}else
					{
						codUsuario = '';
					}
					if(nomeUsuario)
					{
						nomeUsuario = ser.serializeToString(nomeUsuario);
					}else
					{
						nomeUsuario = '';
					}
					if(emailUsuario)
					{
						emailUsuario = ser.serializeToString(emailUsuario);
					}else
					{
						emailUsuario = '';
					}
			
				    document.getElementById("codigoUsuario").innerHTML = codUsuario;
					document.getElementById("nomeUsuario").innerHTML = nomeUsuario;
					document.getElementById("codEmailUsua").innerHTML = emailUsuario;
			
					var htmlSistemas = '';
					for(var cont = 0; cont < sistemas.length; cont++)
					{
						htmlSistemas += ser.serializeToString(sistemas[cont]) + "<br>";
					}
					document.getElementById("divSistemasUsuario").innerHTML = htmlSistemas;


					var htmlGrupos = '';
					for(var cont = 0; cont < grupos.length; cont++)
					{
						htmlGrupos += ser.serializeToString(grupos[cont]) + "<br>";
						
					}
					document.getElementById("divGruposUsuario").innerHTML = htmlGrupos;

					var htmlFuncoes = '';
					for(var cont = 0; cont < funcoes.length; cont++)
					{
						htmlFuncoes += ser.serializeToString(funcoes[cont]) + "<br>";
					}
					document.getElementById("divFuncoesUsuario").innerHTML = htmlFuncoes;

			}


			function submitVerificacaoAcesso()
			{
				var values = new Array();
				//Parâmetros
				values['DNSEC_CHAVE_ACAO']='VERIFICAR_ACESSO_FUNCAO';
				values['DNSEC_REQUISICAO_AJAX'] = true; //Informa o filtro que a requisição foi feita utilizando ajax							
				values['NOME_FUNCAO'] = document.getElementById('txtNomeFuncao').value; 							
				HTTP.post("inicio.sec", values, callbackDireitoAcesso, errorHandler); 
				document.getElementById('cmdVerificarAcessoAFuncao').disabled = true;				
			}
			
			function sessaoExpirada(xml){
					var status = XML.getNode(xml, '/operacaodnsec/statusoperacao/text()', null);					
					var ser = new XMLSerializer();
					if(status)
					{
						status = ser.serializeToString(status);
						return 'ERRO_NAO_SESSAO_EXPIRADA' == status;
					}
					return false;					
			}
			
			function callbackDireitoAcesso(xml)
			{
				if(!sessaoExpirada(xml))
				{
					document.getElementById('cmdVerificarAcessoAFuncao').disabled = false;
					var ser = new XMLSerializer();
					var mensagem = XML.getNode(xml, '/operacaodnsec/msgoperacao/text()', null);
					mensagem = ser.serializeToString(mensagem);
					mensagem = mensagem.replace('<![CDATA[','').replace(']]>','');
					if('BLOQUEADA' == mensagem)
					{
						alert('Usuário não tem acesso a esta função ou a função não existe!!');
					}else{
						alert('Usuário tem acesso a esta função!');
					}					
					
				}else{
					alert('Sessão expirada!');
				}				
			}
			
			// -->
		
		
		</script>
		
					

	</head>
	<body onload="init();">
		<div align="center" id="divControlesLogin">
			<form action="inicio.sec" method="POST">
				<input type="hidden" name="DNSEC_CHAVE_ACAO" value="DNSEC_LOGIN"/>
				Usuário: <input type="text" name="DNSEC_LOGIN"/>
				<br/>Senha: <input type="password" name="DNSEC_SENHA"/>
				<br/><input type="submit" value="Login Comum"/>
				<input type="button" id="cmdLoginAjax" value="Login Ajax" onclick ="submitLoginAjax();"/>

				<textarea rows="10" cols="10" id="txtTextXPath" style="display:none;"></textarea>
			</form>
		</div>
		<div align="left" id="divControlesLogoff" style="display:none;">
			Seja bem vindo ao sistema!
			<br>
			Dados do usuário conectado:
			<p>

			<br/>Código:    <span id="codigoUsuario"></span>
			<br/>Nome:   	<span id="nomeUsuario"></span>
			<br/>E-mail: 	<span id="codEmailUsua"></span>
			<br/>
			</p>


			<p>
			Sistemas relacionados ao usuário:
			<div id="divSistemasUsuario">
			</div>
			</p>

			<p>
			Grupos relacionados ao usuário:
			<div id="divGruposUsuario">
			</div>
			</p>

			
			<p>
			Funções relacionadas ao usuário:
			<div id="divFuncoesUsuario">
			</div>
			</p>


			<br>
			
			
			
			<form>
				<input type="hidden" name="DNSEC_CHAVE_ACAO" value="DNSEC_LOGOFF"/>
				<input type="text" id="txtNomeFuncao"/>
					<input type="button" id="cmdVerificarAcessoAFuncao" value="Verificar direito de acesso (AJAX)" onclick ="submitVerificacaoAcesso();"/>

				<input type="button" id="cmdLogoffAjax" value="Logoff Ajax" onclick ="submitLogoffAjax();"/>
				
			</form>		
		</div>	
			
			
	</body>	
</html>