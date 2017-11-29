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
import tigre.sti.dao.CategoriaDAO;
import tigre.sti.dao.ServicioDAO;
import tigre.sti.dto.Categoria;
import tigre.sti.dto.Servicio;

/**
 * Servlet implementation class IndexController
 */
@WebServlet("/MantenimientoServicioController")
public class MantenimientoServicioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MantenimientoServicioController() {
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
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? ""
					: request.getParameter("tipoConsulta");
			String idServicio = request.getParameter("codigo") == null ? ""
					: request.getParameter("codigo");
			String nombre = request.getParameter("nombre") == null ? ""
					: request.getParameter("nombre").toUpperCase();
			String descripcion = request.getParameter("descripcion") == null ? ""
					: request.getParameter("descripcion").toUpperCase();
			String categoria = request.getParameter("categoria") == null ? ""
					: request.getParameter("categoria");
			String servicioP = request.getParameter("servicioP") == null ? ""
					: request.getParameter("servicioP");
			
			CategoriaDAO categoriaDAO = new CategoriaDAO();
			Categoria categoriaDTO = new Categoria();
			ServicioDAO servicioDAO = new ServicioDAO();
			Servicio servicioDTO = new Servicio();
			JSONObject categoriaJSONObject = new JSONObject();
			JSONArray categoriaJSONArray = new JSONArray();
			JSONObject servicioJSONObject = new JSONObject();
			JSONArray servicioJSONArray = new JSONArray();
			JSONObject servicioPadreJSONObject = new JSONObject();
			JSONArray servicioPadreJSONArray = new JSONArray();
			
			if (!idServicio.equals("")){
				servicioDTO.setIdServicio(Integer.parseInt(idServicio));
			}
			if (!nombre.equals("")){
				servicioDTO.setNombre(nombre);
			}
			if (!descripcion.equals("")){
				servicioDTO.setDescripcion(descripcion);
			}
			if (!categoria.equals("")){
				categoriaDTO = categoriaDAO.buscarPorId(Integer.parseInt(categoria));
				servicioDTO.setCategoria(categoriaDTO);
			}
			if (!servicioP.equals("")){
				Servicio servicioPadre = new Servicio();
				servicioPadre = servicioDAO.buscarPorId(Integer.parseInt(servicioP));
				servicioDTO.setServicio(servicioPadre);
			}
			// Cargar combos
			//Cargar Categoria
			if (tipoConsulta.equals("cargarCategorias")) {
				List<Categoria> categorias = categoriaDAO.buscarTodos();
				for(Categoria resultado: categorias){
					categoriaJSONObject.put("id", resultado.getIdCategoria());
					categoriaJSONObject.put("text", resultado.getNombre());
					categoriaJSONArray.add(categoriaJSONObject);
				}
				result.put("listadoCategorias", categoriaJSONArray);
			}
			//Cargar Rol
			if (tipoConsulta.equals("cargarServiciosPadre")) {
				List<Servicio> serviciosP = servicioDAO.buscarTodos();
				for(Servicio resultado: serviciosP){
					servicioPadreJSONObject.put("id", resultado.getIdServicio());
					servicioPadreJSONObject.put("text", resultado.getNombre());
					servicioPadreJSONArray.add(servicioPadreJSONObject);
				}
				result.put("listadoServiciosPadre", servicioPadreJSONArray);
			}
			
			
			//Servicios
			if (tipoConsulta.equals("consultarServicios")) {
				List<Servicio> results = servicioDAO.buscarTodos();
				for(Servicio resultado: results){
					servicioJSONObject.put("codigo", resultado.getIdServicio());
					servicioJSONObject.put("nombre", resultado.getNombre());
					servicioJSONObject.put("descripcion", resultado.getDescripcion());
					servicioJSONObject.put("categoria", resultado.getCategoria().getNombre());
					servicioJSONObject.put("servicioP", 
							(resultado.getServicio()!= null)?resultado.getServicio().getNombre(): "Ninguno");
					servicioJSONArray.add(servicioJSONObject);
				}
				result.put("numRegistros", results.size());
				result.put("listadoServicios", servicioJSONArray);
			}

			if (tipoConsulta.equals("actualizar")) {
				servicioDAO.editar(servicioDTO);
			}
			if (tipoConsulta.equals("eliminar")) {
				servicioDAO.eliminar(servicioDTO);
			}
			if (tipoConsulta.equals("crear")) {
				servicioDAO.crear(servicioDTO);
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
