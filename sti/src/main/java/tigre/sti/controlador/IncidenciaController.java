package tigre.sti.controlador;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tigre.sti.dao.EstadoDAO;
import tigre.sti.dao.IncidenciaDAO;
import tigre.sti.dao.ServicioDAO;
import tigre.sti.dao.UsuarioDAO;
import tigre.sti.dto.Estado;
import tigre.sti.dto.Incidencia;
import tigre.sti.dto.Servicio;
import tigre.sti.dto.Usuario;

/**
 * Servlet implementation class IndexController
 */
@WebServlet("/IncidenciaController")
public class IncidenciaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IncidenciaController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject result = new JSONObject();
		JSONArray serviciosJSONArray = new JSONArray();
		JSONObject serviciosJSONObject = new JSONObject();
		ServicioDAO servicioDAO = new ServicioDAO();
		IncidenciaDAO incidenciaDAO = new IncidenciaDAO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		EstadoDAO estadoDAO = new EstadoDAO();
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? ""
					: request.getParameter("tipoConsulta");
			String idServicio = request.getParameter("idServicio") == null ? "" : request.getParameter("idServicio");
			String idUsuarioSolicitante = request.getParameter("idSolicitante") == null ? "" : request.getParameter("idSolicitante");
			String idIncidencia= request.getParameter("idIncidencia") == null ? "" : request.getParameter("idIncidencia");
			
			HttpSession session = request.getSession();
			String auxIdServicio = session.getAttribute("idServicio").toString();
			
			if(idServicio.equals("")){
				idServicio = auxIdServicio;
			}
			
			
			if (tipoConsulta.equals("cargarServicios")) {
				Servicio servicio = servicioDAO.buscarPorId(Integer.parseInt(idServicio));
				List<Servicio> servicios = servicio.getServicios();
				if (servicios.size() > 0) {
					for (Servicio auxServicio : servicios) {
						serviciosJSONObject.put("idServicio", auxServicio.getIdServicio());
						serviciosJSONObject.put("nombre", auxServicio.getNombre());
						serviciosJSONArray.add(serviciosJSONObject);
					}					
				}
				result.put("numRegistros", (serviciosJSONArray.size()));
				result.put("listadoServicios", serviciosJSONArray);

			}
			if(tipoConsulta.equals("cargarDatosServicio")){
				Servicio servicio = servicioDAO.buscarPorId(Integer.parseInt(idServicio));
				result.put("nombreServicio", servicio.getNombre());
				result.put("descripcionServicio", servicio.getDescripcion());
			}			
			if (tipoConsulta.equals("crearIncidencia")) {
				Incidencia incidencia = new Incidencia();
				Servicio servicio = new Servicio();
				Usuario usuarioReporta = new Usuario();
				servicio = servicioDAO.buscarPorId(Integer.parseInt(idServicio));
				Estado estado = new Estado();
				estado= estadoDAO.buscarPorId(1);				
				incidencia.setEstado(estado);
				Date date = new Date();
				incidencia.setFecha(new Timestamp(date.getTime()));
				incidencia.setServicio(servicio);
				usuarioDAO.buscarPorId(idUsuarioSolicitante);				
				incidencia.setUsuario1(usuarioReporta);
				incidenciaDAO.crear(incidencia);
			}
			if(tipoConsulta.equals("busquedaIncidenciasActivas")){
				Estado estado = new Estado();
				estado = estadoDAO.buscarPorId(1);
				incidenciaDAO.buscarPorEstado(estado);
			}
			if(tipoConsulta.equals("busquedaIncidencia")){
				incidenciaDAO.buscarPorId(Integer.parseInt(idIncidencia));
			}

			result.put("success", Boolean.TRUE);
			response.setContentType("application/json; charset=UTF-8");
			result.write(response.getWriter());
		} catch (Exception e) {
			result.put("success", Boolean.FALSE);
			result.put("error", e.getLocalizedMessage());
			response.setContentType("application/json; charset=UTF-8");
			result.write(response.getWriter());
			e.printStackTrace();

		}
	}

}
