<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div id="loadAjax">
<div id="divCadastroMarcas">
	<c:forEach var="error" items="${errors}">
			${error.category} ${error.message}<br />
	</c:forEach>

	<table id="tabelaMarcas" border="1">
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th style="width: 100px;" align="center">Ações</th>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
			</tr>
	</table>
	<p>	
		<a style="" id="linkPrimeiro">&lt;&lt;</a>
		<a class="" id="linkAnterior">&lt;</a>
		<a class="" id="linkProximo">&gt;</a>
		<a class="" id="linkUltimo">&gt;&gt;</a>
		<a class="ui-state-default ui-corner-all" id="dialog_link" href="#">
			Novo
		</a>	
	</p>
	
	<div id="divEdicaoMarca" style="display: none;">
		<form action='editar' method="post" id="frmEdicaoMarca">
			<input type="hidden" name="marca.id" id="idMarca"  />				
		
		
		<div id="content" class="container_16 clearfix" style="width:400px;">
				<div class="grid_16" style="width:390px !important;">
					<h2>Dados da Marca</h2>
				</div>

				<div class="grid_16" style="width:390px !important;">

					<p>
						<label for="title">Nome: <small>O nome deve ser informado.</small></label>
						<input type="text" name="marca.nome" id="nomeMarca" />					</p>
				</div>

			</div>		
				 
		</form>
	</div>
</div>	
</div>	
