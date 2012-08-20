<script type="text/javascript">
//---------------------------------------------------------------------JS Cadastro de Marcas----------------------------------------------------------------------------------------------------------------------------------
var urlMarcas  = 'marcas';
var objCadMarcas = 
{
	carregarMarcas:
	function(acao) 
	{
		var url = '${contexto}/' + urlMarcas + '/listarJson'
		urlComp = '';
		if(acao) urlComp = 'acao=' + acao;
		else urlComp += 'acao=IR_PARA_PRIMEIRA_PAGINA';
		urlComp +='&paginaAtual=' + objCadMarcas.paginaAtual;		
		url += '?' + urlComp;
		$.ajax({
			  type: 'POST',
			  url: url,
			  data: {},
			  success: function(data)
			  {
				//log(data);
				var lista = data.pagina.objetos;
				var html = '';
				for(var i = 0; i < lista.length; i++) {
					//html += '<tr id=' + lista[i].id + '><td>' + lista[i].id + '<td/><td>nome</td><td>acao</td></tr>'; //+ lista[i].nome + '</td><td><a href="#" class="ui-state-default ui-corner-all" onClick="objCadMarcas.excluir(' + lista[i].id + ')">Excluir</a>&nbsp;&nbsp;<a href="#" class="ui-state-default ui-corner-all" onClick="objCadMarcas.montarDialogoEdicaoParaEditar(' + lista[i].id + ')">Editar</a></td></tr>';
					html +='<tr id=' + lista[i].id + '><td>' + lista[i].id + '</td><td>' + lista[i].nome + '</td>';
					html +='<td>'; 
					html +='<a href="#" class="ui-state-default ui-corner-all" onClick="objCadMarcas.excluir(' + lista[i].id + ')">Excluir</a>&nbsp;&nbsp;<a href="#" class="ui-state-default ui-corner-all" onClick="objCadMarcas.montarDialogoEdicaoParaEditar(' + lista[i].id + ')">Editar</a>';
					html +='</td></tr>';					
				}
				$('#tabelaMarcas tr:not(:first)').empty();
				$('#tabelaMarcas').append(html);
				objCadMarcas.paginaAtual=data.pagina.paginaAtual;				
			  },
			  error: function() 
			  {
					alert('Erro na chamada ajax!');
			  },
			  dataType: 'json'
			});		
		
	},

	montarJanelaEdicaoMarcas:
		function()
		{
			$('#divEdicaoMarca').dialog({
				width: 500,
				autoOpen: false,
				buttons: 
					{
					"Confirmar": 
						function() 
						{ 
							var url = '${contexto}/' + urlMarcas + '/editar';
							var dados =  $('#frmEdicaoMarca').serialize();
							$.ajax({
								  type: 'POST',
								  url: url,
								  assync: false,
								  data: dados,
								  success: function(data)
								  {
									//log(data);
									if(data.requisicaoAjax.status == 'SUCESSO') {
										$('#divEdicaoMarca').dialog("close");
										if($('#idMarca').val() == '') {
											objCadMarcas.carregarMarcas('IR_PARA_ULTIMA_PAGINA');
										} else {
											objCadMarcas.carregarMarcas('IR_PARA_PRIMEIRA_PAGINA');
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

			limparDadosFormulario:
				function()
				{
					$('#idMarca').val('');					
					$('#nomeMarca').val('');
				},


			montarBotoesPaginacao:
				function() 
				{
	
					$('#linkPrimeiro').click(
							function()
							{
								objCadMarcas.carregarMarcas('IR_PARA_PRIMEIRA_PAGINA')
							}
					);
					$('#linkAnterior').click(
							function()
							{
								objCadMarcas.carregarMarcas('IR_PARA_PAGINA_ANTERIOR')
							}
					);
					$('#linkProximo').click(
							function()
							{
								objCadMarcas.carregarMarcas('IR_PARA_PROXIMA_PAGINA')
							}
					);
					$('#linkUltimo').click(function()
					{
						objCadMarcas.carregarMarcas('IR_PARA_ULTIMA_PAGINA')
					});

				},
			montarDialogoEdicao:
				function()
				{
					//Link para abrir a caixa de diálogo de edição para inclusão
					$('#dialog_link').click(
							function()
							{
								objCadMarcas.limparDadosFormulario();
								$('#divEdicaoMarca').dialog('open');
							}
					);
				}
			,montarDialogoEdicaoParaEditar: 
				function(id)
				{
					var url = '${contexto}/' + urlMarcas + '/prepararEdicao'
					url += '?codMarca=' + id;
					$.ajax({
						  type: 'POST',
						  url: url,
						  data: {},
						  success: function(data)
						  {
							//log(data);
							if(data.requisicaoAjax.status == 'SUCESSO') {
								objCadMarcas.limparDadosFormulario();
								var obj = data.requisicaoAjax.listaObjetosRetorno[0];
								$('#idMarca').val(obj.id);					
								$('#nomeMarca').val(obj.nome);
								$('#divEdicaoMarca').dialog('open');
								
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
					//Carrega as marcas cadastradas
					objCadMarcas.carregarMarcas();
					//Monta janela para adicionar/editar marcas
					objCadMarcas.montarJanelaEdicaoMarcas();
					//Monta o diálogo para edição de dados
					objCadMarcas.montarDialogoEdicao();
					//Monta os botões para paginação de dados
					objCadMarcas.montarBotoesPaginacao();		

					
					$('#divCadastroMarcas').dialog({
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
						var url = '${contexto}/' + urlMarcas + '/excluir'
						url += '?codMarca=' + id;
						$.ajax({
							  type: 'POST',
							  url: url,
							  data: {},
							  success: function(data)
							  {
								//log(data);
								if(data.requisicaoAjax.status == 'SUCESSO') {
									//objCadMarcas.carregarMarcas('IR_PARA_ULTIMA_PAGINA');
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
//---------------------------------------------------------------------Final JS Cadastro de Marcas----------------------------------------------------------------------------------------------------------------------------------
</script>