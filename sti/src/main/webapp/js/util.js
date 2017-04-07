	/* Asigna los valores a los elementos del formulario dependiendo del tipo*/
	function elementType(text, elem, type) {
		switch (type) {
		case "text":
			$("#" + elem).val("");
			$("#" + elem).val(text);
			break;
		case "password":
			$("#" + elem).val("");
			$("#" + elem).val(text);
			break;
		case "number":
			$("#" + elem).val("");
			$("#" + elem).val(parseFloat(text));
			break;
		case "checkbox":
			if (text == "Si")
				$("#" + elem).prop("checked", "checked");
			else
				$("#" + elem).removeAttr("checked");
			break;
		case "select":
			$("#" + elem + " option[value='" + text + "']").attr("selected", true);
			break;
		case "select2":
			$("#" + elem).select2("val", text);
		case "textarea":
			$("#" + elem).val(text);
			break;
		}
	}

	$(function () {
		/* Esconde el popup*/
		$("#msgPopup").hide();

		/*	filtro de busqueda */
		$('#filter').keyup(function () {

			var rex = new RegExp($(this).val(), 'i');
			$('.searchable tr').hide();
			$('.searchable tr').filter(function () {
				return rex.test($(this).text());
			}).show();
		});

		/* Envento cuando se cierra el Popup de ingreso de datos */
		$("#close-popup").click(function () {
			location.reload();
		});

		/* Resetea el formulario cada vez que presiona el boton nuevo */
		$("#addButton").click(function () {
			$("#msgPopup").hide();
			$("#formCrud").get(0).reset();
		});
	});

	//funcion que valida que solo se ingresen letras minusculas o numero recibe el evento
	//en el input debe ir onkeypress="validarSoloLetrasNumeros(event)" ejemplo en registrarse.jsp
	function validarSoloLetrasNumeros(evt) {
		var theEvent = evt || window.event;
		var key = theEvent.keyCode || theEvent.which;
		key = String.fromCharCode(key);
		var regex1 = /[a-zA-Z]/;
		var regex2 = /[0-9]/;
		if (!regex1.test(key) && !regex2.test(key)) {
			theEvent.returnValue = false;
			if (theEvent.preventDefault)
				theEvent.preventDefault();
		}
	}
	//funcion que valida dependiendo el regex q reciba
	//en el input debe ir onkeypress="validarSoloLetrasNumeros(event)" ejemplo en registrarse.jsp
	function validarKeyPress(evt, regex) {
		var theEvent = evt || window.event;
		var key = theEvent.keyCode || theEvent.which;
		key = String.fromCharCode(key);
		if (!regex.test(key)) {
			theEvent.returnValue = false;
			if (theEvent.preventDefault)
				theEvent.preventDefault();
		}
	}

	function IsEmail(email) {
		var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		return regex.test(email);
	}

	//valida el numero de cedula recibe el input
	function validaCedula(cedula) {

		var retorno = true;
		var v1 = 0,
		d1 = 0,
		d2 = 0,
		d3 = 0,
		d4 = 0,
		d5 = 0,
		d6 = 0,
		d7 = 0,
		d8 = 0,
		d9 = 0;
		var cv1 = 0,
		cd1 = 0,
		cd2 = 0,
		cd3 = 0,
		cd4 = 0,
		cd5 = 0,
		cd6 = 0,
		cd7 = 0,
		cd8 = 0,
		cd9 = 0;
		var w_acum = 0,
		w_sum = 0,
		w_verif;
		if (cedula.length != 10) {
			retorno = false;
		} else {

			v1 = parseInt(cedula.charAt(9), 10);
			d1 = parseInt(cedula.charAt(8), 10);
			d2 = parseInt(cedula.charAt(7), 10);
			d3 = parseInt(cedula.charAt(6), 10);
			d4 = parseInt(cedula.charAt(5), 10);
			d5 = parseInt(cedula.charAt(4), 10);
			d6 = parseInt(cedula.charAt(3), 10);
			d7 = parseInt(cedula.charAt(2), 10);
			d8 = parseInt(cedula.charAt(1), 10);
			d9 = parseInt(cedula.charAt(0), 10);

			cv1 = v1;
			cd1 = d1;
			cd2 = d2;
			cd3 = d3;
			cd4 = d4;
			cd5 = d5;
			cd6 = d6;
			cd7 = d7;
			cd8 = d8;
			cd9 = d9;

			w_sum = cd1 * 2;
			if (w_sum > 9) {
				w_sum -= 9;
			}
			w_acum = w_acum + w_sum;
			w_sum = cd2 * 1;
			w_acum = w_acum + w_sum;

			w_sum = cd3 * 2;
			if (w_sum > 9) {
				w_sum -= 9;
			}
			w_acum = w_acum + w_sum;
			w_sum = cd4 * 1;
			w_acum = w_acum + w_sum;

			w_sum = cd5 * 2;
			if (w_sum > 9) {
				w_sum -= 9;
			}
			w_acum = w_acum + w_sum;
			w_sum = cd6 * 1;
			w_acum = w_acum + w_sum;

			w_sum = cd7 * 2;
			if (w_sum > 9) {
				w_sum = w_sum - 9;
			}
			w_acum = w_acum + w_sum;
			w_sum = cd8 * 1;
			w_acum = w_acum + w_sum;

			w_sum = cd9 * 2;
			if (w_sum > 9) {
				w_sum = w_sum - 9;
			}
			w_acum = w_acum + w_sum;

			w_verif = 0;
			if (w_acum <= 10) {
				w_verif = 10 - w_acum;
			} else {
				if (w_acum <= 20) {
					w_verif = 20 - w_acum;
				} else {
					if (w_acum <= 30) {
						w_verif = 30 - w_acum;
					} else {
						if (w_acum <= 40) {
							w_verif = 40 - w_acum;
						} else {
							if (w_acum <= 50) {
								w_verif = 50 - w_acum;
							} else {
								if (w_acum <= 60) {
									w_verif = 60 - w_acum;
								} else {
									if (w_acum <= 70) {
										w_verif = 70 - w_acum;
									} else {
										if (w_acum <= 80) {
											w_verif = 80 - w_acum;
										} else {
											if (w_acum <= 90) {
												w_verif = 90 - w_acum;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			if (w_verif == cv1) {
				retorno = true;
			} else {
				retorno = false;
			}
		}
		return retorno;
	}

	//funcion que valida que las claves tengan un caracter especial, una mayuscula, una minuscula y un numero
	function validaClave(clave) {
		var retorno = false;
		//var re1=/\W/;
		var re2 = /[0-9]/;
		var re3 = /[a-z]/;
		var re4 = /[A-Z]/;
		//var nre=/\s/;

		if (re2.exec(clave) != null && re3.exec(clave) != null && re4.exec(clave) != null) {
			retorno = true;
		}
		return retorno;
	}

	// metodo cara generar y descargar archivos
	abrirReporte = function (verb, url, data, target) {
		var form = document.createElement("form");
		form.action = url;
		form.method = verb;
		form.target = target || "_blank";
		if (data) {
			for (var key in data) {
				var input = document.createElement("input");
				input.name = key;
				input.value = typeof data[key] === "object" ? JSON.stringify(data[key]) : data[key];
				form.appendChild(input);
			}
		}
		form.style.display = 'none';
		document.body.appendChild(form);
		form.submit();
	};