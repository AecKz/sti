var codigo = "";
var idTecnico = "";
var tipoConsulta = "";
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
									$("#addButton2").trigger("click");
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
						idTecnico = $("#selectTecnico").select2("val");
						tipoConsulta = "asignarTecnicoIncidencia";
						if(retorno){
							enviarDatos(codigo, idTecnico, tipoConsulta);
						}						
					});
				/* Fin Controles Grabar Resgistro*/
					function enviarDatos(codigo, idTecnico, tipoConsulta){
						$.ajax({
							url : '../sti/IncidenciaController',
							data : {
								"codigo" : codigo,
								"idTecnico" : idTecnico,
								"tipoConsulta": tipoConsulta
							},
							type : 'POST',
							datatype : 'json',
							success : function(data) {
								if(data.success){
									$("#msgPopup").show();
								}else{
									alert(data.error);
								}
							}
						});
					}
				
				//Cargar select2 tecnicos
					$.ajax({
						url : '../sti/CatalogosController',
						data : {
							"tipoConsulta" : "cargarTecnicos"
						},
						type : 'post',
						datatype : 'json',
						success : function (data) {
							var tecnicos = data.listadoTecnicos;	
							$('.select2Tecnico').select2({
								data : tecnicos,
								minimumResultsForSearch: Infinity,
								placeholder : 'Seleccione un técnico'
							});												
						}
					});//Fin carga tecnicos
					
					// Cargar Incidencias activas
					$.ajax({
						url : '../sti/IncidenciaController',
						data : {
							"tipoConsulta" : "busquedaIncidenciasAsignadas"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							if(data.numRegistros > 0){
								var listadoAsignadas = data.listadoAsignadas;
								$.each(listadoAsignadas, function(index){
									$("#contentAsignadas").append("	<tr class='odd gradeX'>" +
											" <td relation='fecha'>"+ listadoAsignadas[index].fecha +"</td>" +
											" <td relation='categoria'>"+ listadoAsignadas[index].categoria +"</td>" +
											" <td relation='servicio'>"+ listadoAsignadas[index].servicio +"</td>" +
											" <td relation='titulo'>"+ listadoAsignadas[index].titulo +"</td>" +
											" <td relation='descripcion'>"+ listadoAsignadas[index].descripcion +"</td>" +
											" <td relation='solicitante'>"+ listadoAsignadas[index].solicitante +"</td>" +
											" <td relation='asignado'>"+ listadoAsignadas[index].asignado +"</td>" +
											" <td width='175px'>" +
											" <input type='hidden' value='"+ listadoAsignadas[index].codigo +"'/>" +
										"</td>" +
										"</tr>");						
								});							
							}else{
								$("#contentAsignadas").append("<tr><td colspan='4'>No existen Registros</td></tr>");
							}
						}
					});// Fin Cargar Incidencias asignadas
					

				});//Fin ready