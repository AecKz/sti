package tigre.sti.controlador;

import java.io.IOException;
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
import tigre.sti.dao.PrioridadDAO;
import tigre.sti.dao.ServicioDAO;
import tigre.sti.dao.UsuarioDAO;
import tigre.sti.dto.Estado;
import tigre.sti.dto.Prioridad;
import tigre.sti.dto.Servicio;
import tigre.sti.dto.Usuario;

/**
 * Servlet implementation class IndexController
 */
@WebServlet("/CatalogosController")
public class CatalogosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CatalogosController() {
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
		JSONArray tecnicosJSONArray = new JSONArray();
		JSONObject tecnicosJSONObject = new JSONObject();
		JSONArray prioridadJSONArray = new JSONArray();
		JSONObject prioridadJSONObject = new JSONObject();
		JSONArray estadosJSONArray = new JSONArray();
		JSONObject estadosJSONObject = new JSONObject();
		ServicioDAO servicioDAO = new ServicioDAO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		EstadoDAO estadoDAO = new EstadoDAO();
		PrioridadDAO prioridadDAO = new PrioridadDAO();
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? ""
					: request.getParameter("tipoConsulta");
			String idServicio = request.getParameter("idServicio") == null ? "" : request.getParameter("idServicio");
			String idUsuarioSolicitante = request.getParameter("idSolicitante") == null ? "" : request.getParameter("idSolicitante");
			String idIncidencia= request.getParameter("idIncidencia") == null ? "" : request.getParameter("idIncidencia");
			HttpSession session = request.getSession();
			
			if(idServicio.equals("") && session.getAttribute("idServicio")!= null){
				idServicio = session.getAttribute("idServicio").toString();
			}
			if(idIncidencia.equals("") && session.getAttribute("idIncidencia")!= null){
				idIncidencia = session.getAttribute("idIncidencia").toString();
			}
			if(idUsuarioSolicitante.equals("")){
				idUsuarioSolicitante = session.getAttribute("login").toString();
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
				result.put("idServicio", servicio.getIdServicio());
			}			

			if (tipoConsulta.equals("cargarTecnicos")) {
				List<Usuario> tecnicos = usuarioDAO.buscarTodosPorRol(4);
				for(Usuario tecnico: tecnicos){
					tecnicosJSONObject.put("id", tecnico.getIdUsuario());
					tecnicosJSONObject.put("text", tecnico.getPersona().getNombres() 
							+ " " + tecnico.getPersona().getApellidos());
					tecnicosJSONArray.add(tecnicosJSONObject);
				}
				result.put("listadoTecnicos", tecnicosJSONArray);
			}
			if (tipoConsulta.equals("cargarPrioridad")) {
				List<Prioridad> prioridades = prioridadDAO.buscarTodos();
				for(Prioridad prioridad: prioridades){
					prioridadJSONObject.put("id", prioridad.getIdPrioridad());
					prioridadJSONObject.put("text", prioridad.getNombre());
					prioridadJSONArray.add(prioridadJSONObject);
				}
				result.put("listadoPrioridad", prioridadJSONArray);
			}
			
			if (tipoConsulta.equals("cargarEstados")) {
				List<Estado> estados = estadoDAO.buscarTodos();
				for(Estado estado: estados){
					estadosJSONObject.put("id", estado.getIdEstado());
					estadosJSONObject.put("text", estado.getNombre());
					estadosJSONArray.add(estadosJSONObject);
				}
				result.put("listadoEstados", estadosJSONArray);
			}
			
			if (tipoConsulta.equals("cargarEstadosTecnico")) {
				List<Estado> estados = estadoDAO.buscarEstadosTecnico();
				for(Estado estado: estados){
					estadosJSONObject.put("id", estado.getIdEstado());
					estadosJSONObject.put("text", estado.getNombre());
					estadosJSONArray.add(estadosJSONObject);
				}
				result.put("listadoEstados", estadosJSONArray);
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
