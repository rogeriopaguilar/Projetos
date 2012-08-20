  	var objLogConfig = {"log" : "true"};
  	function log(objJson){
  	  	if(objLogConfig.log){ alert(JSON.stringify(objJson, null, '\t')); }
  	}

	if(typeof objUtil == "undefined") {
		objUtil = {
					TIPO_MENSAGEM:{"ERRO":"ERRO", "INFO":"INFO", "WARNING":"WARNING"},
					ACOES_PAGINACAO:{"IR_PARA_PROXIMA_PAGINA":"IR_PARA_PROXIMA_PAGINA", "IR_PARA_PAGINA_ANTERIOR":"IR_PARA_PAGINA_ANTERIOR", "IR_PARA_PRIMEIRA_PAGINA":"IR_PARA_PRIMEIRA_PAGINA", "IR_PARA_ULTIMA_PAGINA":"IR_PARA_ULTIMA_PAGINA"},
					
					exibirMensagem : function(message) {

						    // get the screen height and width 
						    var maskHeight = $(document).height(); 
						    var maskWidth = $(window).width();
						     
						    // calculate the values for center alignment
						    var dialogTop =  (maskHeight/3) - ($('#dialog-box').height()); 
						    var dialogLeft = (maskWidth/2) - ($('#dialog-box').width()/2);
						     
						    // assign values to the overlay and dialog box
						    $('#dialog-overlay').css({height:maskHeight, width:maskWidth}).show();
						    $('#dialog-box').css({top:dialogTop, left:dialogLeft}).show();
						     
						    // display the message
						    $('#dialog-message').html(message);
						
					}


		
				}
	}
  	

	$(
		function() {
			$('body').hide();
			$('body').fadeIn("slow");
			
			

			$('body').ajaxStart(function() {
				$( "#dialogo-espera" ).fadeIn('slow');
			});
			
			$('body').ajaxComplete(function() {
				$( "#dialogo-espera" ).fadeOut('slow');
			});
			
			$.ajaxSetup({cache:false});
			
			// if user clicked on button, the overlay layer or the dialogbox, close the dialog 
		    $('a.btn-ok, #dialog-overlay, #dialog-box').click(function () {    
		        $('#dialog-overlay, #dialog-box').hide();      
		        return false;
		    });
		     
		    // if user resize the window, call the same function again
		    // to make sure the overlay fills the screen and dialogbox aligned to center   
		    $(window).resize(function () {
		         
		        //only do it if the dialog box is not hidden
		        if (!$('#dialog-box').is(':hidden')) popup();      
		    });
		     			
		}

		
	);  	
