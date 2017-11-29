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
import tigre.sti.dto.Categoria;

/**
 * Servlet implementation class IndexController
 */
@WebServlet("/MantenimientoCategoriaController")
public class MantenimientoCategoriaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MantenimientoCategoriaController() {
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
			String idCategoria = request.getParameter("codigo") == null ? ""
					: request.getParameter("codigo");
			String nombre = request.getParameter("nombre") == null ? ""
					: request.getParameter("nombre").toUpperCase();
			String descripcion = request.getParameter("descripcion") == null ? ""
					: request.getParameter("descripcion").toUpperCase();	
			CategoriaDAO categoriaDAO = new CategoriaDAO();
			Categoria categoria = new Categoria();
			JSONObject categoriaJSONObject = new JSONObject();
			JSONArray categoriaJSONArray = new JSONArray();
			
			if (!idCategoria.equals("")){
				categoria.setIdCategoria(Integer.parseInt(idCategoria));
			}
			if (!nombre.equals("")){
				categoria.setNombre(nombre);
			}
			if (!descripcion.equals("")){
				categoria.setDescripcion(descripcion);
			}
			
			//Categorias
			if (tipoConsulta.equals("consultarCategorias")) {
				List<Categoria> results = categoriaDAO.buscarTodos();
				for(Categoria resultado: results){
					categoriaJSONObject.put("codigo", resultado.getIdCategoria());
					categoriaJSONObject.put("nombre", resultado.getNombre());
					categoriaJSONObject.put("descripcion", resultado.getDescripcion());										
					categoriaJSONArray.add(categoriaJSONObject);
				}
				result.put("numRegistros", results.size());
				result.put("listadoCategorias", categoriaJSONArray);
			}

			if (tipoConsulta.equals("actualizar")) {
				categoriaDAO.editar(categoria);
			}
			if (tipoConsulta.equals("eliminar")) {
				categoriaDAO.eliminar(categoria);
			}
			if (tipoConsulta.equals("crear")) {
				categoriaDAO.crear(categoria);
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
