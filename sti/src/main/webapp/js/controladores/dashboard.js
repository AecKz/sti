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
// Carga inicial
$(document).ready(
		function() {
			// Cargar Categorias
			$.ajax({
				url : '../sti/DashboardController',
				data : {
					"tipoConsulta" : "cargarCategorias"
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					if (data.numRegistros > 0) {
						var listadoCategorias = data.listadoCategorias;
						$.each(listadoCategorias, function(index) {
							$("#menuLateral").append(
									"<li><a href='dashboard.jsp'><i class='fa fa-folder-open-o'></i>"
											+ listadoCategorias[index].nombre
											+ "</a></li>");
						});
					} else {
						$("#menuLateral").append("No existen Registros");
					}
				}
			});
		});