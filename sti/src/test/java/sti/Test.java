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
				System.out.println("Se pasó");
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
//	@org.junit.Test
//	public void generarExcel(){
//		String fileName = "C://excel//MyFirstExcel.xlsx";
//		XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
//        IncidenciaDAO incidenciaDAO = new IncidenciaDAO();
//        List <Incidencia> criticas = incidenciaDAO.buscarIncidenciasCriticas();
//        String[] headers = new String[]{
//                "id",
//                "Titulo",
//                "Descripcion"
//            };
//        
//        Object[][] datatypes = {
//                {"id", "titulo", "descripcion"}
//        };
//        Object[] row = {};
//        datatypes = ArrayUtils.add(datatypes, row);
//        int rowNum = 0;
//        System.out.println("Creating excel");
//
//        for (Object[] datatype : datatypes) {
//            Row row = sheet.createRow(rowNum++);
//            int colNum = 0;
//            for (Object field : datatype) {
//                Cell cell = row.createCell(colNum++);
//                if (field instanceof String) {
//                    cell.setCellValue((String) field);
//                } else if (field instanceof Integer) {
//                    cell.setCellValue((Integer) field);
//                }
//            }
//        }
//
//        try {
//            FileOutputStream outputStream = new FileOutputStream(fileName);
//            workbook.write(outputStream);
//            workbook.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Done");
//		
//	}
}
