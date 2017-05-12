var codigo = "";
var nombre = "";
var descripcion = "";
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
					
					//Cargar Categorias
					$.ajax({
						url : '../sti/MantenimientoCategoriaController',
						data : {
							"tipoConsulta" : "consultarCategorias"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							$("#loading").remove();
							if(data.numRegistros > 0){
								var listadoCategorias = data.listadoCategorias;
								$.each(listadoCategorias, function(index){
									$("#dataTableContent").append("	<tr class='odd gradeX'>" +
											" <td relation='nombre'>"+ listadoCategorias[index].nombre +"</td>" +
											" <td relation='descripcion'>"+ listadoCategorias[index].descripcion +"</td>" +
											" <td width='175px'>" +
												" <input type='hidden' value='"+ listadoCategorias[index].codigo +"'/>" +
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
										var r = confirm("Seguro que desea eliminar la categoria: " + $(this).parent().parent().children().first().text());
										if (r == true){
											codigo = $(this).parent().children().first().val();
											nombre = ""; descripcion = ""; 
											tipoConsulta = "eliminar";
											enviarDatos(codigo, nombre, descripcion, tipoConsulta);
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
							if (codigo == ""){
								tipoConsulta = "crear";
							}else{
								tipoConsulta = "actualizar";
							}
							if(retorno){
								enviarDatos(codigo, nombre, descripcion, tipoConsulta);
							}
						});
					/* Fin Controles Grabar Resgistro*/
					
					function enviarDatos(codigo, nombre, descripcion, tipoConsulta){
						$.ajax({
							url : '../TratamientoController',
							data : {
								"codigo" : codigo,
								"nombre" : nombre,
								"descripcion" : descripcion,
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

				});//Fin ready