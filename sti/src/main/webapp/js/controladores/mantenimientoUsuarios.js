var codigo = "";
var	nombres = "";
var apellidos = "";
var direccion = "";
var telefono = "";
var email = "";
var usuario = "";							
var rol = "";
var tipoConsulta="";

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
					//Cargar select2 roles
					$.ajax({
						url : '../sti/MantenimientoUsuarioController',
						data : {
							"tipoConsulta" : "cargarRol"
						},
						type : 'post',
						datatype : 'json',
						success : function (data) {
							var roles = data.listadoRoles;	
							$('.select2Rol').select2({
								data : roles,
								minimumResultsForSearch: Infinity,
								placeholder : 'Seleccione un rol'
							});												
						}
					});//Fin carga roles
					
					//Cargar Usuarios
					$.ajax({
						url : '../sti/MantenimientoUsuarioController',
						data : {
							"tipoConsulta" : "consultarUsuarios"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							$("#loading").remove();
							if(data.numRegistros > 0){
								var listadoUsuarios = data.listadoUsuarios;
								$.each(listadoUsuarios, function(index){
									$("#dataTableContent").append("	<tr class='odd gradeX'>" +
											" <td relation='nombres'>"+ listadoUsuarios[index].nombres +"</td>" +
											" <td relation='apellidos'>"+ listadoUsuarios[index].apellidos+"</td>" +
											" <td relation='direccion'>"+ listadoUsuarios[index].direccion +"</td>" +
											" <td relation='telefono'>"+ listadoUsuarios[index].telefono +"</td>" +
											" <td relation='email'>"+ listadoUsuarios[index].email +"</td>" +
											" <td relation='usuario'>"+ listadoUsuarios[index].usuario +"</td>" +
											" <td relation='rol'>"+ listadoUsuarios[index].rol +"</td>" +
											" <td width='175px'>" +
												" <input type='hidden' value='"+ listadoUsuarios[index].codigo +"'/>" +
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
										var r = confirm("Seguro que desea eliminar el Usuario: " + $(this).parent().parent().children().first().text());
										if (r == true){
											codigo = $(this).parent().children().first().val();
											nombre = ""; descripcion = ""; 
											tipoConsulta = "eliminar";
											enviarDatos(codigo, nombres, apellidos, direccion, telefono, email, usuario, rol, tipoConsulta);
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
							nombres = $("#nombres").val();
							apellidos = $("#apellidos").val();
							direccion = $("#direccion").val();
							telefono = $("#telefono").val();
							email = $("#email").val();
							usuario = $("#usuario").val();							
							rol = $("#rol").select2("val");
							if (codigo == ""){
								tipoConsulta = "crear";
							}else{
								tipoConsulta = "actualizar";
							}
							if(retorno){
								var flag = 0;
								var re = new RegExp("^([a-z A-Z]+)$");
								var re2 = new RegExp("^([0-9]+)$");
								if (re.test(nombres)) {
								    console.log("Valid");
								    flag++;
								} else {
								    flag--;
								}
								if (re.test(apellidos)) {
								    console.log("Valid");
								    flag++;
								} else {
								    flag--;
								}
								if (re.test(usuario)) {
								    console.log("Valid");
								    flag++;
								} else {
								    flag--;
								}
								if (re2.test(telefono)) {
								    console.log("Valid");
								    flag++;
								} else {
								    flag--;
								}
								if(flag >= 4){
									enviarDatos(codigo, nombres, apellidos, direccion, telefono, email, usuario, rol, tipoConsulta);
								}else{
									alert('Datos incorrectos!');
								}								
							}
						});
					/* Fin Controles Grabar Resgistro*/
					
					function enviarDatos(codigo, nombres, apellidos, direccion, telefono, email, usuario, rol, tipoConsulta){
						$.ajax({
							url : '../sti/MantenimientoUsuarioController',
							data : {
								"codigo" : codigo,
								"nombres" : nombres,
								"apellidos" : apellidos,								
								"direccion" : direccion,
								"telefono" : telefono,
								"email" : email,
								"usuario" : usuario,
								"rol" : rol,
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
							$.print("#reporteUsuarios");
					});

				});//Fin ready