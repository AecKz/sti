package sti;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.Ignore;

import tigre.sti.dao.IncidenciaDAO;
import tigre.sti.dto.Incidencia;
import tigre.sti.util.Utilitarios;

public class Test {
	@org.junit.Test
	@Ignore
	public void incidenciasVencidas(){
		IncidenciaDAO incidenciaDAO = new IncidenciaDAO();
		List<Incidencia> incidencias = incidenciaDAO.buscarIncidenciasResueltas();
		for(Incidencia incidencia: incidencias){
			Date fechaInicioIncidencia = incidencia.getFechaInicio();
			Date fechaSolucion = incidencia.getSolucions().get(0).getFecha();
			long diferenciaFechas = fechaSolucion.getTime() - fechaInicioIncidencia.getTime();
			long diferenciaHoras = diferenciaFechas/ (1000 * 60 * 60);
//			System.out.println(diferenciaHoras);
			String prioridad = incidencia.getPrioridad().getDuracion().substring(0, 1);
//			System.out.println(prioridad);
			if(diferenciaHoras>Long.parseLong(prioridad))
			{
				System.out.println("Se pas�");
			}
			
		}
	}
	@org.junit.Test
	@Ignore
	public void enviarMail(){
		String host = "smtp.gmail.com";
		String port = "587";
		String user = "jconde@udlanet.ec";
		String pass = "J0rg3C0nd3U";
		//Enviar Email
		String toAddress = "aex90@hotmail.com";
		String subject = "Incidencia Creada";
		String message = "<i>Saludos!</i><br>";
	        message += "<b>Se ha creado una incidencia!</b><br>";
	        message += "<font color=red>STI</font>";
		try {
			Utilitarios.sendEmail(host, port, user, pass, toAddress, subject, message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
