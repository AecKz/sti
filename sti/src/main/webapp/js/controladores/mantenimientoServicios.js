var codigo = "";
var nombre = "";
var descripcion = "";
var categoria = "";
var servicioP = "";
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
				    $.validate({
				    	lang : 'es',
				        modules : 'date, security'
				    });
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
					//Cargar select2 categorias
					$.ajax({
						url : '../sti/MantenimientoServicioController',
						data : {
							"tipoConsulta" : "cargarCategorias"
						},
						type : 'post',
						datatype : 'json',
						success : function (data) {
							var categorias = data.listadoCategorias;	
							$('.select2Categoria').select2({
								data : categorias,
								minimumResultsForSearch: Infinity,
								placeholder : 'Seleccione una categoria'
							});												
						}
					});//Fin carga categoria
					//Cargar select2 servicio
					$.ajax({
						url : '../sti/MantenimientoServicioController',
						data : {
							"tipoConsulta" : "cargarServiciosPadre"
						},
						type : 'post',
						datatype : 'json',
						success : function (data) {
							var servicios = data.listadoServiciosPadre;	
							$('.select2Servicio').select2({
								data : servicios,
								minimumResultsForSearch: Infinity,
								placeholder : 'Seleccione un servicio'
							});												
						}
					});//Fin carga roles
					//Cargar Categorias
					$.ajax({
						url : '../sti/MantenimientoServicioController',
						data : {
							"tipoConsulta" : "consultarServicios"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							$("#loading").remove();
							if(data.numRegistros > 0){
								var listadoServicios = data.listadoServicios;
								$.each(listadoServicios, function(index){
									$("#dataTableContent").append("	<tr class='odd gradeX'>" +
											" <td relation='nombre'>"+ listadoServicios[index].nombre +"</td>" +
											" <td relation='descripcion'>"+ listadoServicios[index].descripcion +"</td>" +
											" <td relation='categoria'>"+ listadoServicios[index].categoria+"</td>" +
											" <td relation='servicioP'>"+ listadoServicios[index].servicioP +"</td>" +
											" <td width='175px'>" +
												" <input type='hidden' value='"+ listadoServicios[index].codigo +"'/>" +
												" <button type='button' class='btn btn-success btn-xs actualizar-btn'>" +
				  									" <span class='glyphicon glyphicon glyphicon-edit'></span> Actualizar" +
												" </button>" +
												" <button type='button' class='btn btn-danger btn-xs eliminar-btn'>" +
												  	"<span class='glyphicon glyphicon glyphicon-remove' id='delete-record'></span> Eliminar" +
												" </button>" +
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
								
								/* Inicio Controles Eliminar Registro */
								$(".eliminar-btn").bind({click: function() {
										var r = confirm("Seguro que desea eliminar el Servicio: " + $(this).parent().parent().children().first().text());
										if (r == true){
											codigo = $(this).parent().children().first().val();
											nombre = ""; descripcion = ""; categoria =""; servicioP="";
											tipoConsulta = "eliminar";
											enviarDatos(codigo, nombre, descripcion, categoria, servicioP, tipoConsulta);
									    	$(this).parent().parent().remove();
										}
									}
								});	
								/* Fin Controles Eliminar Registro */
							}else{
								$("#dataTableContent").append("<tr><td colspan='2'>No existen Registros</td></tr>");
							}
						}
					});			
					
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
							nombre = $("#nombre").val();
							descripcion = $("#descripcion").val();
							categoria = $("#categoria").select2("val");
							servicioP = $("#servicioP").select2("val");
							if (codigo == ""){
								tipoConsulta = "crear";
							}else{
								tipoConsulta = "actualizar";
							}
							if(retorno){
								var flag = 0;
								var re = new RegExp("^([a-z A-Z]+)$");
								if (re.test(nombre)) {
								    console.log("Valid");
								    flag++;
								} else {
								    flag--;
								}
								if (re.test(descripcion)) {
								    console.log("Valid");
								    flag++;
								} else {
								    flag--;
								}
								if(flag > 1){
									enviarDatos(codigo, nombre, descripcion, categoria, servicioP, tipoConsulta);
								}else{
									alert('Datos incorrectos!');
								}
							}
						});
					/* Fin Controles Grabar Resgistro*/
					
					function enviarDatos(codigo, nombre, descripcion, categoria, servicioP, tipoConsulta){
						$.ajax({
							url : '../sti/MantenimientoServicioController',
							data : {
								"codigo" : codigo,
								"nombre" : nombre,
								"descripcion" : descripcion,
								"categoria" : categoria,
								"servicioP" : servicioP,
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
					//Imprimir
					$('#btnImprimirLista').click(function(){	
							$.print("#reporteServicios");
					});

				});// Fin ready