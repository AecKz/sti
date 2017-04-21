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
import tigre.sti.dao.CategoriaDAO;
import tigre.sti.dao.PersonaDAO;
import tigre.sti.dao.ServicioDAO;
import tigre.sti.dto.Categoria;
import tigre.sti.dto.Persona;
import tigre.sti.dto.Servicio;

/**
 * Servlet implementation class IndexController
 */
@WebServlet("/DashboardController")
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardController() {
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
		JSONArray categoriasJSONArray = new JSONArray();
		JSONObject categoriasJSONObject = new JSONObject();
		JSONArray serviciosJSONArray = new JSONArray();
		JSONObject serviciosJSONObject = new JSONObject();
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		ServicioDAO servicioDAO = new ServicioDAO();
		HttpSession session = request.getSession();
		String usuario = session.getAttribute("login").toString();
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? ""
					: request.getParameter("tipoConsulta");
			String idCategoria = request.getParameter("idCategoria") == null ? "" : request.getParameter("idCategoria");

			if (tipoConsulta.equals("cargarDatosMenus")) {
				Persona persona = new Persona();
				PersonaDAO personaDAO = new PersonaDAO();
				persona = personaDAO.buscarPorUsuario(usuario);
				String nombreCompleto = persona.getNombres() + " " + persona.getApellidos();
				result.put("nombreCompleto", nombreCompleto);
				result.put("email", persona.getEmail());
				result.put("extension", persona.getTelefono());				
			}

			if (tipoConsulta.equals("cargarCategorias")) {
				List<Categoria> categorias = categoriaDAO.buscarTodos();
				for (Categoria categoria : categorias) {
					categoriasJSONObject.put("idCategoria", categoria.getIdCategoria());
					categoriasJSONObject.put("nombre", categoria.getNombre());
					categoriasJSONArray.add(categoriasJSONObject);
				}
				result.put("numRegistros", (categoriasJSONArray.size()));
				result.put("listadoCategorias", categoriasJSONArray);
			}

			if (tipoConsulta.equals("cargarServicios")) {
				List<Servicio> servicios = servicioDAO.buscarPorIdCategoria(Integer.parseInt(idCategoria));
				for (Servicio servicio : servicios) {
					serviciosJSONObject.put("idServicio", servicio.getIdServicio());
					serviciosJSONObject.put("nombre", servicio.getNombre());
					serviciosJSONArray.add(serviciosJSONObject);
				}
				result.put("numRegistros", (serviciosJSONArray.size()));
				result.put("listadoServicios", serviciosJSONArray);
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
