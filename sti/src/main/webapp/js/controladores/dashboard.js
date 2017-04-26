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
						window.location = "registrarIncidencia.jsp?idServicio="+idServicio;
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

				});