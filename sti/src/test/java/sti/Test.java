package sti;

import java.util.Date;
import java.util.List;

import tigre.sti.dao.IncidenciaDAO;
import tigre.sti.dto.Incidencia;

public class Test {
	@org.junit.Test
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
}
