<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

{"pagina": 
	{"objetos": 
	[
	<c:forEach var="vo" items="${pagina.objetos}" varStatus="status">
		{
			"id": ${vo.id} ,
			"placa": "${vo.placa}",
			"anoFabricacao": ${vo.anoFabricacao},
			"responsavel":{
				"id":${vo.responsavel.id},
				"nome":"${vo.responsavel.nome}"
			   }
		}<c:if test="${not status.last}">,</c:if>
	</c:forEach>	
	
	],
    
    "paginaAtual": ${pagina.paginaAtual},
    "totalPaginas": ${pagina.totalPaginas},
    "registrosPorPagina": ${pagina.registrosPorPagina},
    "existeProximaPagina": ${pagina.existeProximaPagina},
    "existePaginaAnterior": ${pagina.existePaginaAnterior}
}}
