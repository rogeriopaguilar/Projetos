<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><sitemesh:write property='title' /></title>

<meta http-equiv="Cache-control" CONTENT="no-cache"></meta>
<meta http-equiv="pragma" CONTENT="no-cache"></meta>
<meta http-equiv="Expires" CONTENT="-1"></meta>

<c:set var="contexto" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript" src="${contexto}/js/json-crockford.js"></script>
<script type="text/javascript" src="${contexto}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${contexto}/js/util.js"></script>
<script type="text/javascript" src="${contexto}/js/jquery-ui-1.8.19.custom.min.js"></script>
<script type="text/javascript" src="${contexto}/js/jquery.maskedinput-1.3.min.js"></script>

<%@include file="js/cadmarcas-js.jsp" %>


<%@include file="js/cadveiculos-js.jsp" %>


<script type="text/javascript">
	$(
		function() {

			$('#linkCadastroVeiculos').click(
					function()
					{
						$('#content').load('veiculos/listar #loadAjax', 
								function(){ 
									objCadVeiculos.init();
									$('#divCadastroVeiculos').dialog('open');
								}
						);
					}
			);


			
			$('#linkCadastroMarcas').click(
					function()
					{
						$('#content').load('marcas/listar #loadAjax', 
								function(){ 
									objCadMarcas.init();
									$('#divCadastroMarcas').dialog('open');
								}
						);
					}
			);

		}
	);
</script>


<link rel="stylesheet" href="${contexto}/css/reset.css" type="text/css" media="screen" charset="utf-8" />
<link type="text/css" href="${contexto}/css/smoothness/jquery-ui-1.8.19.custom.css" rel="stylesheet" />
<link rel="stylesheet" href="${contexto}/css/960.css" type="text/css" media="screen" charset="utf-8" />
<link rel="stylesheet" href="${contexto}/css/template.css" type="text/css" media="screen" charset="utf-8" />
<link rel="stylesheet" href="${contexto}/css/colour.css" type="text/css" media="screen" charset="utf-8" />
<link rel="stylesheet" href="${contexto}/css/dialog.css" type="text/css" media="screen" charset="utf-8" />
<link rel="stylesheet" href="${contexto}/css/ajustes.css" type="text/css" media="screen" charset="utf-8" />


<sitemesh:write property='head' />


</head>
<body>

		<h1 id="head">Cadastro de Veículos</h1>
		
		<div id="dialog-overlay"></div>
		<div id="dialog-box">
		    <div class="dialog-content">
		        <div id="dialog-message"></div>
		        <a href="#" class="button">Close</a>
		    </div>
		</div>		

		<ul id="navigation">
			<%--<li><span class="active">Overview</span></li>--%>
			<li><a href="#" id="linkCadastroVeiculos">Veí­culos</a></li>
			<li><a href="#" id="linkCadastroMarcas">Marcas</a></li>
			<li><a href="#">Modelos</a></li>
			<li><a href="#">Categorias</a></li>
			<li><a href="#">Responsáveis</a></li>
			<div id="dialogo-espera" style="display: none;float: right;">
				<h2>Aguarde...</h2>
			</div>
			
		</ul>

	<div class='mainBody'>
	
		<div id="content" class="container_16 clearfix" style="height: 500px">
			<div class="grid_11">
			</div>
			<sitemesh:write property='body' />
		</div>
	</div>

			<div id="foot">
				<i>Designed by </i><a href="http://mathew-davies.co.uk/">Mathew Davies</a>
			</div>

</body>
</html>