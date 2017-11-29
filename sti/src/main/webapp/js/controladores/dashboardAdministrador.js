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
				
					//Cargar incidencias criticas
					$.ajax({
								url : '../sti/IncidenciaController',
								data : {
									"tipoConsulta" : "busquedaIncidenciasCriticas"
								},
								type : 'POST',
								datatype : 'json',
								success : function(data) {
									if (data.numRegistros > 0) {
										var listadoIncidencias = data.listadoIncidencias;
										$.each(listadoIncidencias,function(index) {
											$("#listaCriticas").append(
													"<li class='list-group-item'><a id='"+ listadoIncidencias[index].codigo
													+ "' href='incidenciasCriticas.jsp'>"
													+ listadoIncidencias[index].servicio +"</a><br>"
													+ listadoIncidencias[index].fechaInicio +"-"
													+ listadoIncidencias[index].categoria+"</li>");
														});
									} else {
										$("#listaCriticas").append("<li class='list-group-item'> No existen Registros</li>");
									}
								}
							});	//Fin criticas
					//Cargar incidencias RFT
					$.ajax({
								url : '../sti/IncidenciaController',
								data : {
									"tipoConsulta" : "busquedaIncidenciasRFT"
								},
								type : 'POST',
								datatype : 'json',
								success : function(data) {
									if (data.numRegistros > 0) {
										var listadoIncidencias = data.listadoIncidencias;
										$.each(listadoIncidencias,function(index) {
											$("#listaRFT").append(
													"<li class='list-group-item'><a id='"+ listadoIncidencias[index].codigo
													+ "' href='incidenciasRFT.jsp'>"
													+ listadoIncidencias[index].servicio +"</a><br>"
													+ listadoIncidencias[index].fechaInicio +"-"
													+ listadoIncidencias[index].categoria+"</li>");
														});
									} else {
										$("#listaRFT").append("<li class='list-group-item'> No existen Registros</li>");
									}
								}
							});	//Fin RFT
					
					//Cargar incidencias AFT
					$.ajax({
								url : '../sti/IncidenciaController',
								data : {
									"tipoConsulta" : "busquedaIncidenciasAFT"
								},
								type : 'POST',
								datatype : 'json',
								success : function(data) {
									if (data.numRegistros > 0) {
										var listadoIncidencias = data.listadoIncidencias;
										$.each(listadoIncidencias,function(index) {
											$("#listaAFT").append(
													"<li class='list-group-item'><a id='"+ listadoIncidencias[index].codigo
													+ "' href='incidenciasAFT.jsp'>"
													+ listadoIncidencias[index].servicio +"</a><br>"
													+ listadoIncidencias[index].fechaInicio +"-"
													+ listadoIncidencias[index].categoria+"</li>");
														});
									} else {
										$("#listaAFT").append("<li class='list-group-item'> No existen Registros</li>");
									}
								}
							});	//Fin AFT
					
					
				});//Fin ready