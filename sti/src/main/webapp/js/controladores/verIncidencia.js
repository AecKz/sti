//signOut
function signOut() {
	cerrarSesion();
}
function cerrarSesion() {
	$.ajax({
		url : '../sti/IndexController',
		data : {
			"tipoConsulta" : "cerrarSesion"
		},
		type : 'POST',
		datatype : 'json',
		success : function(data) {
		}
	});
}
function reAbrirIncidencia(){
	$.ajax({
		url : '../sti/IncidenciaController',
		data : {
			"tipoConsulta" : "cambiarEstadoIncidencia",
			"idEstado": 5
		},
		type : 'POST',
		datatype : 'json',
		success : function(data) {
			alert("Incidencia Reabierta");
			window.location = "dashboard.jsp";
		}
	});
}
function cerrarIncidencia(){
	$.ajax({
		url : '../sti/IncidenciaController',
		data : {
			"tipoConsulta" : "cambiarEstadoIncidencia",
			"idEstado": 4
		},
		type : 'POST',
		datatype : 'json',
		success : function(data) {
			alert("Incidencia Cerrada");
			window.location = "dashboard.jsp";
		}
	});
}
function cargarIncidencia(idIncidencia) {
	window.location = "verIncidencia.jsp?var="+idIncidencia;
}
// Carga inicial
$(document).ready(
		function() {
			// Cargar Datos del Menu
			$.ajax({
				url : '../sti/DashboardController',
				data : {
					"tipoConsulta" : "cargarDatosMenus"
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					var nombreCompleto = data.nombreCompleto;
					$('#txtUsuarioCabecera').text(nombreCompleto);
					$('#txtSolicitante').val(nombreCompleto);
					$('#txtEmail').val(data.email);
					$('#txtExtension').val(data.extension);					
				}
			});
			// Cargar Datos del Menu
			$.ajax({
				url : '../sti/IncidenciaController',
				data : {
					"tipoConsulta" : "cargarDatosIncidencia"
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					debugger;
					$('#lblTitulo').text(data.nombreServicio);
					$('#lblSubtitulo').text(data.descripcionServicio);
					$('#txtTelefono').val(data.telefonoContacto);
					$('#selContacto').val(data.tipoContacto);
					$('#txtTitulo').val(data.titulo);
					$('#txtEstado').val(data.estado);
					$('#txtPrioridad').text(data.prioridad);
					$('#txtDescripcion').text(data.descripcion);					
					if(data.estado === 'RESUELTO'){
						$('#btnReabrir').show();
						$('#btnCerrar').show();
					}else{
						$('#btnReabrir').hide();
						$('#btnCerrar').hide();						
					}
				}
			});		
			//Cargar menu barra
			$
					.ajax({
						url : '../sti/IncidenciaController',
						data : {
							"tipoConsulta" : "busquedaIncidenciasActivasSolicitante"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {							
							if (data.numRegistros > 0) {
								var listadoIncidencias = data.listadoIncidencias;
								$('#contadorIncidencias').text(data.numRegistros);
								$.each(listadoIncidencias,function(index) {
									$("#menu1").append(
											"<li><a id='"+ listadoIncidencias[index].codigo
											+ "' href='#' onclick='cargarIncidencia(id)'><span><span>"
											+ listadoIncidencias[index].servicio +"</span><span class='time'>"
											+ listadoIncidencias[index].fecha +"</span></span><span class='message'>"
											+ listadoIncidencias[index].titulo+"</span></a></li>");
												});
							} else {
								$("#menu1").append("No existen Registros");
							}
						}
					});	
		});// Fin ready