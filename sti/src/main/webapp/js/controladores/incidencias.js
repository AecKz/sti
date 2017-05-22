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
			//Cargar incidencias pendientes
			$.ajax({
						url : '../sti/IncidenciaController',
						data : {
							"tipoConsulta" : "busquedaIncidenciasActivasSolicitante"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							if (data.numRegistros > 0) {
								var listadoIncidencias = data.listadoIncidencias;
								$.each(listadoIncidencias,function(index) {
									$("#listaPendientes").append(
											"<li class='list-group-item'><a id='"+ listadoIncidencias[index].codigo
											+ "' href='#' onclick='cargarIncidencia(id)'>"
											+ listadoIncidencias[index].servicio +"</a><br>"
											+ listadoIncidencias[index].fecha +"-"
											+ listadoIncidencias[index].categoria+"</li>");
												});
							} else {
								$("#listaPendientes").append("<li class='list-group-item'> No existen Registros</li>");
							}
						}
					});	//Fin pendientes
			//Cargar incidencias resueltas
			$.ajax({
						url : '../sti/IncidenciaController',
						data : {
							"tipoConsulta" : "busquedaIncidenciasResueltasSolicitante"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							if (data.numRegistros > 0) {
								var listadoIncidencias = data.listadoIncidencias;
								$.each(listadoIncidencias,function(index) {
									$("#listaResueltas").append(
											"<li class='list-group-item'><a id='"+ listadoIncidencias[index].codigo
											+ "' href='#' onclick='cargarIncidencia(id)'>"
											+ listadoIncidencias[index].servicio +"</a><br>"
											+ listadoIncidencias[index].fecha +"-"
											+ listadoIncidencias[index].categoria+"</li>");
												});
							} else {
								$("#listaResueltas").append("<li class='list-group-item'> No existen Registros</li>");
							}
						}
					});	//Fin resueltas
			//Cargar incidencias cerradas
			$.ajax({
						url : '../sti/IncidenciaController',
						data : {
							"tipoConsulta" : "busquedaIncidenciasCerradasSolicitante"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							if (data.numRegistros > 0) {
								var listadoIncidencias = data.listadoIncidencias;
								$.each(listadoIncidencias,function(index) {
									$("#listaCerradas").append(
											"<li class='list-group-item'><a id='"+ listadoIncidencias[index].codigo
											+ "' href='#' onclick='cargarIncidencia(id)'>"
											+ listadoIncidencias[index].servicio +"</a><br>"
											+ listadoIncidencias[index].fecha +"-"
											+ listadoIncidencias[index].categoria+"</li>");
												});
							} else {
								$("#listaCerradas").append("<li class='list-group-item'> No existen Registros</li>");
							}
						}
					});	//Fin cerradas
	
		});// Fin ready