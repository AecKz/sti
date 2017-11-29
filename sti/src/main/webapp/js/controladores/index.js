var usuarioLogin = "";
var contrasenaLogin = "";
var tipoConsulta = "";

$(document).ready(function() {
	//Login
	$("#btnLogin").click(function() {
		tipoConsulta = "login";
		usuarioLogin = $("#txtUsuarioLogin").val().trim();
		contrasenaLogin = $("#txtContrasenaLogin").val().trim();
		$.ajax({
			url : '../sti/IndexController',
			data : {
				"usuarioLogin" : usuarioLogin,
				"contrasenaLogin" : contrasenaLogin,
				"tipoConsulta" : tipoConsulta
			},
			type : 'POST',
			datatype : 'json',
			success : function(data) {
				if (data.success) {
					window.location = "/sti/dashboard.jsp";
				} else {
					alert("Usuario o Contrase&ntilde;a Incorrecta")
				}
			}
		});
	});//Fin Login 
});