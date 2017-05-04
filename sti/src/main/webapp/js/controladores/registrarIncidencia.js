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

function crearIncidencia(){
	debugger;
	$('#btnEnviar').hide();
	//Enviamos datos del paciente
	var telefonoContacto = $('#txtTelefono').val();
	var tipoContacto = $('#selContacto').val();
	var titulo = $('#txtTitulo').val();
	var descripcion = $('#txtDescripcion').val();
	var idServicio = $('#lblServicio').text();
	$.ajax({
		url : '../sti/IncidenciaController',
		data : {
			"tipoConsulta" : "crearIncidencia",
			"tipoContacto":tipoContacto,
			"titulo":titulo,
			"idServicio":idServicio,
			"descripcion":descripcion
		},
		type : 'POST',
		datatype : 'json',
		success : function(data) {
			if (data.success) {
				window.location = "/sti/dashboard.jsp";
			} else {
				alert("Hubo un error")
			}
							
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
					$('#lblServicio').text(data.idServicio);
					$("#menuLateral").append(
						"<li><i class='fa fa-cog'></i>"
						+ data.nombreServicio
						+ "</li>");
				}
			});		
		});// Fin ready