<script type="text/javascript">
//---------------------------------------------------------------------JS Cadastro de Veículos----------------------------------------------------------------------------------------------------------------------------------

var urlVeiculos = 'veiculos';
var objCadVeiculos = 
{
	carregarVeiculos:
	function(acao) 
	{
		var url = '${contexto}/' + urlVeiculos + '/listarJson'
		urlComp = '';
		if(acao) urlComp = 'acao=' + acao;
		else urlComp += 'acao=IR_PARA_PRIMEIRA_PAGINA';
		urlComp +='&paginaAtual=' + objCadVeiculos.paginaAtual;		
		url += '?' + urlComp;
		$.ajax({
			  type: 'POST',
			  url: url,
			  data: {},
			  success: function(data)
			  {
				<%--log(data);--%>
				var lista = data.pagina.objetos;
				var html = '';
				for(var i = 0; i < lista.length; i++) {
					html += '<tr id=' + lista[i].id + '><td>' + lista[i].placa + '</td><td>' + lista[i].anoFabricacao + '</td><td>' + lista[i].responsavel.nome + '</td><td><a href="#" class="ui-state-default ui-corner-all" onClick="objCadVeiculos.excluir(' + lista[i].id + ')">Excluir</a>&nbsp;&nbsp;<a href="#" class="ui-state-default ui-corner-all" onClick="objCadVeiculos.montarDialogoEdicaoParaEditar(' + lista[i].id + ')">Editar</a></td></tr>';
				}
				$('#tabelaVeiculos tr:not(:first)').empty();
				$('#tabelaVeiculos').append(html);
				objCadVeiculos.paginaAtual=data.pagina.paginaAtual;				
			  },
			  error: function() 
			  {
					alert('Erro na chamada ajax!');
			  },
			  dataType: 'json'
			});		
		
	},

	montarJanelaEdicaoVeiculos:
		function()
		{
			$('#divEdicaoVeiculo').dialog({
				width: 500,
				autoOpen: false,
				buttons: 
					{
					"Confirmar": 
						function() 
						{ 
							var url = '${contexto}/' + urlVeiculos + '/editar';
							var dados =  $('#frmEdicaoVeiculo').serialize();
							$.ajax({
								  type: 'POST',
								  url: url,
								  assync: false,
								  data: dados,
								  success: function(data)
								  {
									//log(data);
									if(data.requisicaoAjax.status == 'SUCESSO') {
										$('#divEdicaoVeiculo').dialog("close");
										if($('#idVeiculo').val() == '') {
											objCadVeiculos.carregarVeiculos('IR_PARA_ULTIMA_PAGINA');
										} else {
											objCadVeiculos.carregarVeiculos('IR_PARA_PRIMEIRA_PAGINA');
										}
									} else {
										objUtil.exibirMensagem(data.requisicaoAjax.listaMensagens[0]);										
									}
								  },
								  error: function() 
								  {
										alert('Erro na chamada ajax!');
								  },
								  dataType: 'json'
								});		

							
						},
					"Cancelar": 
						function() 
						{ 
							$(this).dialog("close"); 
						} 
				},
				position:
				{
					   my: "center",
					   at: "center",
					   of: window
				}					
			});
		},

		montarCamposParaAutocomplete:
			function()
			{
				$( "#responsavelVeiculoTMP" ).autocomplete({
					source: 
						function(req,resp)
						{
							var url = '${contexto}/responsavel/listarResponsaveisParaAutoComplete';
							$.ajax({
								  type: 'POST',
								  url: url,
								  assync: false,
								  data: {"nome":req.term},
								  success: function(data)
								  {
									<%--log(data);--%>
									resp(data);
								  },
								  error: function() 
								  {
										alert('Erro na chamada ajax!');
								  },
								  dataType: 'json'
								});		
						},
					  select:function(event, ui) 
					  {
						event.preventDefault();  
						$('#responsavelVeiculo').val(ui.item.value);   										
						$('#responsavelVeiculoTMP').val(ui.item.label);   										
					  }
				});

				
				$('#responsavelVeiculoTMP').change(function(){
					$('#responsavelVeiculo').val('');   										
					$('#responsavelVeiculoTMP').val('');   										
				});   										

				
				$( "#modeloVeiculoTMP" ).autocomplete({
					source: 
						function(req,resp)
						{
							var url = '${contexto}/modelo/listarModelosParaAutoComplete';
							$.ajax({
								  type: 'POST',
								  url: url,
								  data: {"nome":req.term},
								  success: function(data)
								  {
									<%--log(data);--%>
									//alert<a class="ui-state-default ui-corner-all" id="dialog_link" href="#"><span class="ui-icon ui-icon-newwin"></span>Open Dialog</a>($.type(data));
									resp(data);
								  },
								  error: function() 
								  {
										alert('Erro na chamada ajax!');
								  },
								  dataType: 'json'
								});		
						},
						  select:function(event, ui) 
						  {
							event.preventDefault();  
							$('#modeloVeiculo').val(ui.item.value);   										
							$('#modeloVeiculoTMP').val(ui.item.label);   										
						  }
				});

				$('#modeloVeiculoTMP').change(function(){
					$('#modeloVeiculo').val('');   										
					$('#modeloVeiculoTMP').val('');   										
				});   										

				
	
				$( "#categoriaVeiculoTMP" ).autocomplete({
					source: 
						function(req,resp)
						{
							var url = '${contexto}/categoria/listarCategoriasParaAutoComplete';
							$.ajax({
								  type: 'POST',
								  url: url,
								  data: {"nome":req.term},
								  success: function(data)
								  {
									<%--log(data);--%>
									resp(data);
								  },
								  error: function() 
								  {
										alert('Erro na chamada ajax!');
								  },
								  dataType: 'json'
								});		
						},
						  select:function(event, ui) 
						  {
							event.preventDefault();  
							$('#categoriaVeiculo').val(ui.item.value);   										
							$('#categoriaVeiculoTMP').val(ui.item.label);   										
						  }
						
				});

				$('#categoriaVeiculoTMP').change(function(){
					$('#categoriaVeiculo').val('');   										
					$('#categoriaVeiculoTMP').val('');   										
				});   										

				
				
				$( "#marcaVeiculoTMP" ).autocomplete({
					source: 
						function(req,resp)
						{
							var url = '${contexto}/marcas/listarMarcasParaAutoComplete';
							$.ajax({
								  type: 'POST',
								  url: url,
								  data: {"nome":req.term},
								  success: function(data)
								  {
									<%--log(data);--%>
									resp(data);
								  },
								  error: function() 
								  {
										alert('Erro na chamada ajax!');
								  },
								  dataType: 'json'
								});		
						},
					  select:function(event, ui) 
					  {
						event.preventDefault();  
						$('#marcaVeiculo').val(ui.item.value);   										
						$('#marcaVeiculoTMP').val(ui.item.label);   										
					  }
				});

				$('#marcaVeiculoTMP').change(function(){
					$('#marcaVeiculo').val('');   										
					$('#marcaVeiculoTMP').val('');   										
				});   										


				
			},
			limparDadosFormulario:
				function()
				{
					$('#idVeiculo').val('');					
					$('#placaVeiculo').val('');
					$('#responsavelVeiculoTMP').val(''); 
					$('#responsavelVeiculo').val('');				
					$('#anoFabricacaoVeiculo').val('');
					$('#marcaVeiculoTMP').val('');
					$('#marcaVeiculo').val('');				
					$('#categoriaVeiculoTMP').val('');
					$('#categoriaVeiculo').val('');				
					$('#modeloVeiculoTMP').val('');
					$('#modeloVeiculo').val('');			
				},


			montarBotoesPaginacao:
				function() 
				{
	
					$('#linkPrimeiro').click(
							function()
							{
								objCadVeiculos.carregarVeiculos('IR_PARA_PRIMEIRA_PAGINA')
							}
					);
					$('#linkAnterior').click(
							function()
							{
								objCadVeiculos.carregarVeiculos('IR_PARA_PAGINA_ANTERIOR')
							}
					);
					$('#linkProximo').click(
							function()
							{
								objCadVeiculos.carregarVeiculos('IR_PARA_PROXIMA_PAGINA')
							}
					);
					$('#linkUltimo').click(function()
					{
						objCadVeiculos.carregarVeiculos('IR_PARA_ULTIMA_PAGINA')
					});

				},
			montarDialogoEdicao:
				function()
				{
					//Link para abrir a caixa de diálogo de edição para inclusão
					$('#dialog_link').click(
							function()
							{
								objCadVeiculos.limparDadosFormulario();
								$('#divEdicaoVeiculo').dialog('open');
							}
					);
				}
			,montarDialogoEdicaoParaEditar: 
				function(id)
				{
					var url = '${contexto}/' + urlVeiculos + '/prepararEdicao'
					url += '?codVeiculo=' + id;
					$.ajax({
						  type: 'POST',
						  url: url,
						  data: {},
						  success: function(data)
						  {
							<%-- log(data); --%>
							if(data.requisicaoAjax.status == 'SUCESSO') {
								objCadVeiculos.limparDadosFormulario();
								var obj = data.requisicaoAjax.listaObjetosRetorno[0];
								var marca = data.requisicaoAjax.listaObjetosRetorno[1];
								var modelo =  data.requisicaoAjax.listaObjetosRetorno[2];
								var categoria  = data.requisicaoAjax.listaObjetosRetorno[3];
								var responsavel = data.requisicaoAjax.listaObjetosRetorno[4];
								$('#idVeiculo').val(obj.id);					
								$('#placaVeiculo').val(obj.placa);
								$('#responsavelVeiculoTMP').val(responsavel.nome); 
								$('#responsavelVeiculo').val(responsavel.id);				
								$('#anoFabricacaoVeiculo').val(obj.anoFabricacao);
								$('#marcaVeiculoTMP').val(marca.nome);
								$('#marcaVeiculo').val(marca.id);				
								$('#categoriaVeiculoTMP').val(categoria.nome);
								$('#categoriaVeiculo').val(categoria.id);				
								$('#modeloVeiculoTMP').val(modelo.nome);
								$('#modeloVeiculo').val(modelo.id);			
								$('#divEdicaoVeiculo').dialog('open');
								
							} else {
								objUtil.exibirMensagem(data.requisicaoAjax.listaMensagens[0]);										
							}
										
						  },
						  error: function() 
						  {
								alert('Erro na chamada ajax!');
						  },
						  dataType: 'json'
						});		
					
				}
			, paginaAtual:1,
			init:
				function()
				{
					//Carrega os veículo cadastrados
					objCadVeiculos.carregarVeiculos();
					//Monta janela para adicionar/editar veículos
					objCadVeiculos.montarJanelaEdicaoVeiculos();
					//Monta os campos para autocomplete
					objCadVeiculos.montarCamposParaAutocomplete();		
					//Monta o diálogo para edição de dados
					objCadVeiculos.montarDialogoEdicao();
					//Monta os botões para paginação de dados
					objCadVeiculos.montarBotoesPaginacao();		
					//Máscara para o campo de ano de fabricação
					$("#anoFabricacaoVeiculo").mask("9999");

					
					$('#divCadastroVeiculos').dialog({
						width: 700,
						autoOpen: false,
						buttons: 
							{
							"Fechar": 
								function() 
								{ 
									$(this).dialog("close"); 
								} 
						},
						position:
						{
							   my: "center",
							   at: "center",
							   of: window
						}});
										

					
				},


			excluir: 
				function(id){
						var url = '${contexto}/' + urlVeiculos + '/excluir'
						url += '?codVeiculo=' + id;
						$.ajax({
							  type: 'POST',
							  url: url,
							  data: {},
							  success: function(data)
							  {
								<%--log(data);--%>
								if(data.requisicaoAjax.status == 'SUCESSO') {
									//objCadVeiculos.carregarVeiculos('IR_PARA_ULTIMA_PAGINA');
									$('tr[id=' + id + ']').remove();
								} else {
									objUtil.exibirMensagem(data.requisicaoAjax.listaMensagens[0]);										
								}
											
							  },
							  error: function() 
							  {
									alert('Erro na chamada ajax!');
							  },
							  dataType: 'json'
							});		

				}
			
};

//---------------------------------------------------------------------Final JS Cadastro de Veículos----------------------------------------------------------------------------------------------------------------------------------

</script>
