package tigre.sti.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tigre.sti.dao.ServicioDAO;
import tigre.sti.dto.Servicio;

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
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? ""
					: request.getParameter("tipoConsulta");
			String idServicio = request.getParameter("idServicio") == null ? "" : request.getParameter("idServicio");

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
