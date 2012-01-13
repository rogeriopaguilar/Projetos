<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
		<script language="JavaScript">
			<!--
			function init(){
				<c:if test="${not empty requestScope['dnsec.erro.esperado']}">
					alert("${requestScope['dnsec.erro.esperado']}");			
				</c:if>


				<c:if test="${not empty requestScope['FUNCAO_LIBERADA']}">
					alert('Usuário possui acesso à função --> ${requestScope["FUNCAO_LIBERADA"]}' );			
				</c:if>
				
			}
			
			function submitVerificacaoAcesso()
			{
				document.forms[0]['DNSEC_CHAVE_ACAO'].value = 'VERIFICAR_ACESSO_FUNCAO';
				document.getElementById('cmdVerificarAcessoAFuncao').disabled = true;				
				document.forms[0].submit();				
			}

			function submitLogoff()
			{
				document.forms[0]['DNSEC_CHAVE_ACAO'].value = 'DNSEC_LOGOFF';
				document.getElementById('cmdLogoff').disabled = true;				
				document.forms[0].submit();				
			}
			
			
			// -->
		</script>
		<body onload="init();">
			Seja bem vindo!
			<br>
			Dados do usuário conectado:
			<p>
			<c:set var="segurancaVo" value="${sessionScope['dnsec.shared.database.hibernate.Usuario']}"/>
			<c:set var="usuario" value="${sessionScope['dnsec.shared.database.hibernate.Usuario']['usuarioConectado']}"/>

			<br/>Código:    ${usuario.codUsuarioUsua}
			<br/>Nome:   ${usuario.nomeUsuarioUsua}
			<br/>E-mail: ${usuario.codEmailUsua}
			<br/>
			</p>
			
			<p>
			Sistemas relacionados ao usuário:
			<c:forEach items="${segurancaVo.mapaSistemasUsuario}" var="sistemaVo">
				<br/>- Sistema: ${sistemaVo.value.codSistemaSist}
			</c:forEach>
			</p>

			<p>
			Grupos relacionados ao usuário:
			<c:forEach items="${segurancaVo.mapaGruposUsuario}" var="grupoVo">
				<br/>- Sistema: ${grupoVo.value.codGrupoGrup}
			</c:forEach>
			</p>

			
			<p>
			Funções relacionadas ao usuário:
			<c:forEach items="${segurancaVo.mapaFuncoesUsuario}" var="funcaoVo">
				<br/>- Sistema: ${funcaoVo.value.nomeFunc}
			</c:forEach>
			</p>
			
			<form action="login.sec" method="POST">
				<input type="hidden" name="DNSEC_CHAVE_ACAO" value="DNSEC_LOGOFF"/>
				<input type="text" name="NOME_FUNCAO" id="NOME_FUNCAO"/>
				<input type="hidden" name="PROXIMA_PAGINA"value="/inicio.jsp"/>
				<input type="button" id="cmdVerificarAcessoAFuncao" value="Verificar direito de acesso" onclick ="submitVerificacaoAcesso();"/>
				<input type="button" id="cmdLogoff" value="Logoff Comum" onclick="submitLogoff();"/>
			</form>		
		</body>
</html>
