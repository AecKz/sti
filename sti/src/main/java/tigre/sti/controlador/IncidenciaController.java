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
import tigre.sti.util.Utilitarios;

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
		JSONArray incidenciasJSONArray = new JSONArray();
		JSONObject incidenciasJSONObject = new JSONObject();
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
			String telefonoContacto = request.getParameter("telefonoContacto") == null ? "" : request.getParameter("telefonoContacto");
			String tipoContacto = request.getParameter("tipoContacto") == null ? "" : request.getParameter("tipoContacto");
			String titulo = request.getParameter("titulo") == null ? "" : request.getParameter("titulo");
			String descripcion = request.getParameter("descripcion") == null ? "" : request.getParameter("descripcion");
			
			HttpSession session = request.getSession();
			
			if(idServicio.equals("") && session.getAttribute("idServicio")!= null){
				idServicio = session.getAttribute("idServicio").toString();
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
				usuarioReporta = usuarioDAO.buscarPorUsuario(idUsuarioSolicitante);				
				incidencia.setUsuario2(usuarioReporta);
				incidencia.setTelefonoContacto(telefonoContacto);
				incidencia.setTipoContacto(tipoContacto);
				incidencia.setTitulo(titulo);
				incidencia.setDescripcion(descripcion);				
				incidenciaDAO.crear(incidencia);
			}
			if(tipoConsulta.equals("busquedaIncidenciasActivas")){
				Estado estado = new Estado();
				estado = estadoDAO.buscarPorId(1);
				List<Incidencia> incidencias = incidenciaDAO.buscarPorEstado(estado);
				for(Incidencia incidencia: incidencias){
					String fechaTurno = Utilitarios.dateToString(incidencia.getFecha());
					incidenciasJSONObject.put("fecha", fechaTurno);
					incidenciasJSONObject.put("codigo", incidencia.getIdIncidencia());
					incidenciasJSONObject.put("categoria", incidencia.getServicio().getCategoria().getNombre());
					incidenciasJSONObject.put("servicio", incidencia.getServicio().getNombre());
					incidenciasJSONObject.put("titulo", incidencia.getTitulo());
					incidenciasJSONObject.put("descripcion", incidencia.getDescripcion());
					incidenciasJSONObject.put("solicitante", incidencia.getUsuario2().getPersona().getNombres()+ 
							" " +incidencia.getUsuario2().getPersona().getApellidos());
					incidenciasJSONArray.add(incidenciasJSONObject);
				}
				result.put("numRegistros", (incidenciasJSONArray.size()));
				result.put("listadoIncidencias", incidenciasJSONArray);
			}
			if(tipoConsulta.equals("busquedaIncidencia")){
				incidenciaDAO.buscarPorId(Integer.parseInt(idIncidencia));
			}
			
			if(tipoConsulta.equals("busquedaIncidenciasActivasSolicitante")){
				Usuario usuario = new Usuario();
				usuario = usuarioDAO.buscarPorUsuario(idUsuarioSolicitante);
				List<Incidencia> incidencias = incidenciaDAO.buscarPorUsuarioSolicitante(usuario);
				for(Incidencia incidencia: incidencias){
					String fechaTurno = Utilitarios.dateToString(incidencia.getFecha());
					incidenciasJSONObject.put("fecha", fechaTurno);
					incidenciasJSONObject.put("codigo", incidencia.getIdIncidencia());
					incidenciasJSONObject.put("servicio", incidencia.getServicio().getNombre());
					incidenciasJSONObject.put("titulo", incidencia.getTitulo());
					incidenciasJSONArray.add(incidenciasJSONObject);
				}
				result.put("numRegistros", (incidenciasJSONArray.size()));
				result.put("listadoIncidencias", incidenciasJSONArray);
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
