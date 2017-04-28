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
					// Cargar Incidencias activas
					$.ajax({
						url : '../sti/IncidenciaController',
						data : {
							"tipoConsulta" : "busquedaIncidenciasActivas"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							if(data.numRegistros > 0){
								var listadoIncidencias = data.listadoIncidencias;
								$.each(listadoIncidencias, function(index){
									$("#contentIncidencias").append("	<tr class='odd gradeX'>" +
											" <td relation='fecha'>"+ listadoIncidencias[index].fecha +"</td>" +
											" <td relation='categoria'>"+ listadoIncidencias[index].categoria +"</td>" +
											" <td relation='servicio'>"+ listadoIncidencias[index].servicio +"</td>" +
											" <td relation='titulo'>"+ listadoIncidencias[index].titulo +"</td>" +
											" <td relation='descripcion'>"+ listadoIncidencias[index].descripcion +"</td>" +
											" <td relation='solicitante'>"+ listadoIncidencias[index].solicitante +"</td>" +
											" <td width='175px'>" +
											" <input type='hidden' value='"+ listadoIncidencias[index].codigo +"'/>" +
											" <button type='button' class='btn btn-success btn-xs actualizar-btn'>" +
			  									" <span class='glyphicon glyphicon glyphicon-edit'></span> Gestionar" +
											" </button>"+ 
										"</td>" +
										"</tr>");						
								});
							
							/* Inicio Controles Actualizar Registro*/
							$(".actualizar-btn").bind({click: function() {
									$("#addButton").trigger("click");
									$("#codigo").val($(this).parent().children().first().val());
									var elem = $(this).parent();
									var bandera = 1;
									do {
										elem = elem.prev();
										if (elem.is("td")){
											var elemCode = elem.attr("relation");
											elementType(elem.text(), elemCode, $("#"+elemCode).attr("type"));
										}else {
											bandera = 0;
										}
									} while (bandera == 1);
								  }
							});
							/* Fin Controles Actualizar Registro*/
							}else{
								$("#contentIncidencias").append("<tr><td colspan='4'>No existen Registros</td></tr>");
							}
						}
					});// Fin Cargar Incidencias activas
					/* Inicio Controles Grabar Registro*/
					$("#save-record").click(function() {
						var retorno=true;
						$(".required").css("border", "1px solid #ccc");
						$(".required").each(function(index) {
							var cadena = $(this).val();
							if (cadena.trim().length <= 0&&retorno) {
								$(this).css("border", "1px solid red");
								alert("Por favor ingrese el campo requerido");
								$(this).focus();
								retorno= false;
							}
						});		
						codigo = $("#codigo").val();
						horaInicio = $("#horaInicio").val();
						horaFinal = $("#horaFinal").val();
						dia = $("#dia").val();
						if (codigo == ""){
							tipoConsulta = "crear";
						}else{
							tipoConsulta = "actualizar";
						}
						if(retorno){
							enviarDatos(codigo, horaInicio, horaFinal, dia, tipoConsulta);
						}
					});
				/* Fin Controles Grabar Resgistro*/
					

				});