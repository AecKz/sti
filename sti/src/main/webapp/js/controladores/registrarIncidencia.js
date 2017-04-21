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
			alert("Sesion cerrada correctamente");
		}
	});
}

function crearIncidencia(idServicio){
	$("#menuCentral").empty();
	   $.ajax({
			url : '../sti/DashboardController',
			data : {
				"tipoConsulta" : "cargarServicios",
				"idCategoria" : idCategoria	
			},
			type : 'POST',
			datatype : 'json',
			success : function(data) {

			}
			});
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
					"tipoConsulta" : "cargarDatosServicio"
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					$('#lblTitulo').text(data.nombreServicio);
					$('#lblSubtitulo').text(data.descripcionServicio);										
				}
			});

		});