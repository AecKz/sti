//signOut
function signOut() {
   cerrarSesion();
 }
function cerrarSesion(){
	   $.ajax({
			url : '../IndexController',
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
