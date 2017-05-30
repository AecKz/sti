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

// Carga inicial
$(document)
		.ready(
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
						}
					});
					
					//Cargar Usuarios
					$.ajax({
						url : '../sti/IncidenciaController',
						data : {
							"tipoConsulta" : "busquedaIncidenciasAFT"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							$("#loading").remove();
							if(data.numRegistros > 0){
								var listadoIncidencias = data.listadoIncidencias;
								$.each(listadoIncidencias, function(index){
									$("#dataTableContent").append("	<tr class='odd gradeX'>" +
											" <td relation='fechaInicio'>"+ listadoIncidencias[index].fechaInicio +"</td>" +
											" <td relation='categoria'>"+ listadoIncidencias[index].categoria+"</td>" +
											" <td relation='servicio'>"+ listadoIncidencias[index].servicio +"</td>" +
											" <td relation='titulo'>"+ listadoIncidencias[index].titulo +"</td>" +
											" <td relation='descripcion'>"+ listadoIncidencias[index].descripcion +"</td>" +
											" <td relation='solicitante'>"+ listadoIncidencias[index].solicitante +"</td>" +
											" <td relation='estado'>"+ listadoIncidencias[index].estado +"</td>" +
											" <td relation='fechaAsignacion'>"+ listadoIncidencias[index].fechaAsignacion +"</td>" +
											" <td relation='prioridad'>"+ listadoIncidencias[index].prioridad +"</td>" +
											" <td relation='responsable'>"+ listadoIncidencias[index].responsable +"</td>" +
										"</tr>");
							
								});
							}else{
								$("#dataTableContent").append("<tr><td colspan='2'>No existen Registros</td></tr>");
							}
						}
					});			

				});//Fin ready