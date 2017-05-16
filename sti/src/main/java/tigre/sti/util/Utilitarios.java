package tigre.sti.util;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Utilitarios {

	@SuppressWarnings("unused")
	public static String generarCodigoAleatorioPorLongitud(int longitud) {

		String NUMEROS = "0123456789";
		String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
		String ESPECIALES = "!#$%^&*_=`~|/;:,.?�-�+@";
		String clave = "";
		String key = NUMEROS + MAYUSCULAS + MINUSCULAS; // si se desea agregar
														// caracteres especiales
														// poner en key
														// ESPECIALES
		for (int i = 0; i < longitud; i++) {
			clave += (key.charAt((int) (Math.random() * key.length())));
		}
		return clave;

	}

	public static String encriptacionClave(String clave) {
		try {

			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(clave.getBytes());
			byte messageDigest[] = md.digest();
			StringBuffer cadenaHexadecimal = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				cadenaHexadecimal.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			clave = cadenaHexadecimal.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return clave;
	}

	public static String numeroRandomico(int length) {
		String retorno = "";

		while (retorno.length() < length) {

			Random rand = new Random();
			int randomNum = rand.nextInt(10);
			retorno += randomNum;
		}
		return retorno;
	}

	// Metodo para agregar dias a una fecha timestamp
	public static long agregarDiaFechaTimestamp(Timestamp date, int dias) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_WEEK, dias); // agregamos los dias a la fecha
												// timestamp ingresada
		Timestamp ts = new Timestamp(cal.getTime().getTime());
		return ts.getTime();
	}

	// Metodo para verificar si es ano bisiesto
	public static boolean esAnoBisiesto(int year) {
		assert year >= 1583; // not valid before this date.
		return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
	}

	public static boolean validarCedula(String cedula) {
		int[] modulo9 = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
		boolean valorRetorno = true;
		BigDecimal verif = new BigDecimal(0);
		if (cedula.length() != 10)
			valorRetorno = false;
		else {
			for (int i = 0; i < 9; i++) {
				BigDecimal temp = new BigDecimal(
						new BigDecimal(cedula.substring(i, (i + 1))).multiply(new BigDecimal(modulo9[i])).toString());
				if (temp.doubleValue() > 9)
					temp = temp.subtract(new BigDecimal("9"));
				verif = verif.add(temp);
			}
			if (verif.doubleValue() % 10 == 0)
				if (Integer.parseInt(cedula.substring(9, 10)) == 0)
					valorRetorno = true;
				else
					valorRetorno = false;
			else if ((10 - (verif.doubleValue() % 10)) == Integer.parseInt(cedula.substring(9, 10)))
				valorRetorno = true;
			else
				valorRetorno = false;
		}
		return valorRetorno;
	}

	public static boolean validaRUC(String RUC) {
		int[] modulo11 = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		boolean valorRetorno = true;
		BigDecimal verif = new BigDecimal(0);
		if (RUC.length() < 13)
			valorRetorno = false;
		else if (Integer.parseInt(RUC.substring(0, 2)) < 1 || Integer.parseInt(RUC.substring(0, 2)) > 22) {
			valorRetorno = false;
		} else {
			if (Integer.parseInt(RUC.substring(2, 3)) < 0
					|| (Integer.parseInt(RUC.substring(2, 3)) > 6 && Integer.parseInt(RUC.substring(2, 3)) < 9)) {
				valorRetorno = false;
			} else {
				if (Integer.parseInt(RUC.substring(2, 3)) == 9) { // sociedad
																	// privada o
																	// extranjeros
					if (!RUC.substring(10, 13).equals("001"))
						valorRetorno = false;
					else {
						modulo11[0] = 4;
						modulo11[1] = 3;
						modulo11[2] = 2;
						modulo11[3] = 7;
						modulo11[4] = 6;
						modulo11[5] = 5;
						modulo11[6] = 4;
						modulo11[7] = 3;
						modulo11[8] = 2;
						for (int i = 0; i < 9; i++) {
							verif = verif.add(
									new BigDecimal(RUC.substring(i, (i + 1))).multiply(new BigDecimal(modulo11[i])));
						}
						if (verif.doubleValue() % 11 == 0)
							if (Integer.parseInt(RUC.substring(9, 10)) == 0)
								valorRetorno = true;
							else
								valorRetorno = false;
						else if ((11 - (verif.doubleValue() % 11)) == Integer.parseInt(RUC.substring(9, 10)))
							valorRetorno = true;
						else
							valorRetorno = false;
					}
				} else if (Integer.parseInt(RUC.substring(2, 3)) == 6) { // sociedad
																			// p�blicas
					if (!RUC.substring(10, 13).equals("001"))
						valorRetorno = false;
					else {
						modulo11[0] = 3;
						modulo11[1] = 2;
						modulo11[2] = 7;
						modulo11[3] = 6;
						modulo11[4] = 5;
						modulo11[5] = 4;
						modulo11[6] = 3;
						modulo11[7] = 2;
						for (int i = 0; i < 8; i++) {
							verif = verif.add(
									new BigDecimal(RUC.substring(i, (i + 1))).multiply(new BigDecimal(modulo11[i])));
						}
						if (verif.doubleValue() % 11 == 0)
							if (Integer.parseInt(RUC.substring(8, 9)) == 0)
								valorRetorno = true;
							else
								valorRetorno = false;
						else if ((11 - (verif.doubleValue() % 11)) == Integer.parseInt(RUC.substring(8, 9)))
							valorRetorno = true;
						else
							valorRetorno = false;
					}
				} else if (Integer.parseInt(RUC.substring(2, 3)) < 6 && Integer.parseInt(RUC.substring(2, 3)) >= 0) { // personas
																														// naturales
					if (!RUC.substring(10, 13).equals("001"))
						valorRetorno = false;
					else {
						valorRetorno = validarCedula(RUC.substring(0, 10));
					}
				}
			}
		}
		return valorRetorno;
	}

	public static Date stringToDate(String fechaString) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		try {
			startDate = df.parse(fechaString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startDate;
	}

	/**
	 * Transforma de fecha a string
	 * 
	 * @param fecha
	 *            tipo Date
	 * @return string formato dd-MM-yyyy
	 */
	public static String dateToString(Date fecha) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String fechaFinal = "";
		fechaFinal = df.format(fecha);
		return fechaFinal;
	}

	public static String buscarDia(int codigoDia) {
		// (0 = Sunday, 1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 = Thursday, 5
		// = Friday, 6 = Saturday)
		String diaNombre;
		switch (codigoDia) {
		case 0:
			diaNombre = "DOMINGO";
			break;
		case 1:
			diaNombre = "LUNES";
			break;
		case 2:
			diaNombre = "MARTES";
			break;
		case 3:
			diaNombre = "MIERCOLES";
			break;
		case 4:
			diaNombre = "JUEVES";
			break;
		case 5:
			diaNombre = "VIERNES";
			break;
		case 6:
			diaNombre = "SABADO";
			break;
		default:
			diaNombre = "Invalid DAY";
			break;
		}
		return diaNombre;
	}

	 public static void sendEmail(String host, String port,
	            final String userName, final String password, String toAddress,
	            String subject, String message) throws AddressException,
	            MessagingException {
	 
	        // sets SMTP server properties
	        Properties properties = new Properties();
	        properties.put("mail.smtp.host", host);
	        properties.put("mail.smtp.port", port);
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");
	 
	        // creates a new session with an authenticator
	        Authenticator auth = new Authenticator() {
	            public PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(userName, password);
	            }
	        };
	 
	        Session session = Session.getInstance(properties, auth);
	 
	        // creates a new e-mail message
	        Message msg = new MimeMessage(session);
	 
	        msg.setFrom(new InternetAddress(userName));
	        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
	        msg.setRecipients(Message.RecipientType.TO, toAddresses);
	        msg.setSubject(subject);
	        msg.setSentDate(new Date());
	        //msg.setText(message);
	        msg.setContent(message, "text/html");
	        // sends the e-mail
	        Transport.send(msg);
	 
	    } 
	
//	public static String fechaDatePickertoDate(String fechaDatepicker) {
//		String fechaString = "";
//		String[] fechaDatePickerArray = fechaDatepicker.split(" ");
//		String anio = fechaDatePickerArray[3];
//		String dia = fechaDatePickerArray[2];
//		String auxMes = fechaDatePickerArray[1];
//		String mes = "";
//		switch (auxMes) {
//		case "Jan":
//			mes = "01";
//			break;
//		case "Feb":
//			mes = "02";
//			break;
//		case "Mar":
//			mes = "03";
//			break;
//		case "Apr":
//			mes = "04";
//			break;
//		case "May":
//			mes = "05";
//			break;
//		case "Jun":
//			mes = "06";
//			break;
//		case "Jul":
//			mes = "07";
//			break;
//		case "Aug":
//			mes = "08";
//			break;
//		case "Sep":
//			mes = "09";
//			break;
//		case "Oct":
//			mes = "10";
//			break;
//		case "Nov":
//			mes = "11";
//			break;
//		case "Dec":
//			mes = "12";
//			break;
//		}
//		fechaString = anio + "-" + mes + "-" + dia;
//		return fechaString;
//	}

}