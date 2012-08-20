<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div id="loadAjax" style="display: none;">
<div id="divCadastroVeiculos">
	<c:forEach var="error" items="${errors}">
			${error.category} ${error.message}<br />
	</c:forEach>

	<table id="tabelaVeiculos" border="1">
			<tr>
				<th>Placa</th>
				<th>Ano</th>
				<th>Responsável</th>
				<th style="width: 100px;" align="center">Ações</th>
			</tr>
			<tr>
				<td></td>
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
	
	<div id="divEdicaoVeiculo" style="display: none;">
		<form action='editar' method="post" id="frmEdicaoVeiculo">
			<input type="hidden" name="veiculo.id" id="idVeiculo"  />				
		
		
		<div id="content" class="container_16 clearfix" style="width:400px;">
				<div class="grid_16" style="width:390px !important;">
					<h2>Dados do veículo</h2>
				</div>

				<div class="grid_16" style="width:390px !important;">

					<p>
						<label for="title">Placa: <small>A placa deve ser única.</small></label>
						<input type="text" name="veiculo.placa" id="placaVeiculo" />					</p>
				</div>

				<div class="grid_16" style="width:390px !important;">
					<p>
						<label for="title">Ano de fabricação: <small>Digite um número válido</small></label>
						<input type="text" name="veiculo.anoFabricacao" id="anoFabricacaoVeiculo" />						
					</p>
						
				</div>
				
				<div class="grid_16" style="width:390px !important;">
					<p>
						<label for="title">Responsável: <small>Digite algo para pesquisar</small></label>
				 		<input type="text" name="veiculo.responsavel.nome" id="responsavelVeiculoTMP"  /> 
						<input type="hidden" name="veiculo.responsavel.id" id="responsavelVeiculo"  />				
					</p>
				</div>

				<div class="grid_16" style="width:390px !important;">
					<p>
						<label>Marca: <small>Digite algo para pesquisar</small></label>
				 		<input type="text" name="veiculo.marca.nome" id="marcaVeiculoTMP" /> 
						<input type="hidden" name="veiculo.marca.id" id="marcaVeiculo" />				
						
					</p>
				</div>

				<div class="grid_16" style="width:390px !important;">
					<p>
						<label>Categoria: <small>Digite algo para pesquisar</small></label>
				 		<input type="text" name="veiculo.categoria.nome" id="categoriaVeiculoTMP" /> 
						<input type="hidden" name="veiculo.categoria.id" id="categoriaVeiculo" />				
					</p>
				</div>

				<div class="grid_16" style="width:390px !important;">
					<p>
						<label>Modelo: <small>Digite algo para pesquisar</small></label>
				 		<input type="text" name="veiculo.modelo.nome" id="modeloVeiculoTMP" /> 
						<input type="hidden" name="veiculo.modelo.id" id="modeloVeiculo" />				
					</p>
					
				</div>
			</div>		
		
				 
		</form>
	</div>
</div>	
</div>	
