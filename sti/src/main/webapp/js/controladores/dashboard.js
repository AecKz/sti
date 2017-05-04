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
function cargarServicios(idCategoria) {
	$("#menuCentral").empty();
	$
			.ajax({
				url : '../sti/DashboardController',
				data : {
					"tipoConsulta" : "cargarServicios",
					"idCategoria" : idCategoria
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					if (data.numRegistros > 0) {
						var listadoServicios = data.listadoServicios;
						$
								.each(
										listadoServicios,
										function(index) {
											$("#menuCentral")
													.append(
															"<li><a id='"
																	+ listadoServicios[index].idServicio
																	+ "' href='#' onclick='registrarIncidencia(id);'><i class='fa fa-cog'></i>"
																	+ listadoServicios[index].nombre
																	+ "</a></li>");
										});
					} else {
						$("#menuCentral").append("No existen Registros");
					}
				}
			});
}

function registrarIncidencia(idServicio) {
	debugger;
	$("#menuCentral").empty();
	$
			.ajax({
				url : '../sti/IncidenciaController',
				data : {
					"tipoConsulta" : "cargarServicios",
					"idServicio" : idServicio
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					if (data.numRegistros > 0) {
						var listadoServicios = data.listadoServicios;
						$
								.each(
										listadoServicios,
										function(index) {
											$("#menuCentral")
													.append(
															"<li><a id='"
																	+ listadoServicios[index].idServicio
																	+ "' href='#' onclick='registrarIncidencia(id)'><i class='fa fa-cog'></i>"
																	+ listadoServicios[index].nombre
																	+ "</a></li>");
										});
					} else {
						window.location = "registrarIncidencia.jsp?var="+idServicio;
					}
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

					// Cargar Categorias
					$
							.ajax({
								url : '../sti/DashboardController',
								data : {
									"tipoConsulta" : "cargarCategorias"
								},
								type : 'POST',
								datatype : 'json',
								success : function(data) {
									if (data.numRegistros > 0) {
										var listadoCategorias = data.listadoCategorias;
										$
												.each(
														listadoCategorias,
														function(index) {
															$("#menuLateral")
																	.append(
																			"<li><a id='"
																					+ listadoCategorias[index].idCategoria
																					+ "' href='#' onclick='cargarServicios(id)'><i class='fa fa-folder-open-o'></i>"
																					+ listadoCategorias[index].nombre
																					+ "</a></li>");
														});
									} else {
										$("#menuLateral").append(
												"No existen Registros");
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