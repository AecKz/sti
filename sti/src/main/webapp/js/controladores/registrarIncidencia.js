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
	$('#btnEnviar').hide();
	//Enviamos datos del paciente
	var telefonoContacto = $('#txtTelefono').val();
	var tipoContacto = $('#selContacto').val();
	var titulo = $('#txtTitulo').val();
	var descripcion = $('#txtDescripcion').val();	
	$.ajax({
		url : '../sti/IncidenciaController',
		data : {
			"tipoConsulta" : "crearIncidencia",
			"tipoContacto":tipoContacto,
			"titulo":titulo,
			"descripcion":descripcion
		},
		type : 'POST',
		datatype : 'json',
		success : function(data) {
			var resultado = data.resultado;
			switch (resultado)
			{
			   case "ok":
				   alert('Incidente registrado exitosamente');
				   break;
			   case "error":
				   alert('Hubo un error');
				   break;
			   default: 
			       alert('Hubo un error');
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
					$("#menuLateral").append(
						"<li><i class='fa fa-cog'></i>"
						+ data.nombreServicio
						+ "</li>");
				}
			});		

		});