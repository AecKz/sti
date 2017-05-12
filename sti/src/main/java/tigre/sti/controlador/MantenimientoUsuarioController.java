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
import tigre.sti.dao.PersonaDAO;
import tigre.sti.dao.RolDAO;
import tigre.sti.dao.UsuarioDAO;
import tigre.sti.dto.Persona;
import tigre.sti.dto.Rol;
import tigre.sti.dto.Usuario;

/**
 * Servlet implementation class MantenimientoUsuarioController
 */
@WebServlet("/MantenimientoUsuarioController")
public class MantenimientoUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MantenimientoUsuarioController() {
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
			String idUsuario = request.getParameter("codigo") == null ? ""
					: request.getParameter("codigo");
			String nombres = request.getParameter("nombres") == null ? ""
					: request.getParameter("nombres");
			String apellidos = request.getParameter("apellidos") == null ? ""
					: request.getParameter("apellidos");			
			String direccion = request.getParameter("direccion") == null ? ""
					: request.getParameter("direccion");	
			String email = request.getParameter("email") == null ? ""
					: request.getParameter("email");
			String telefono = request.getParameter("telefono") == null ? ""
					: request.getParameter("telefono");
			String usuario = request.getParameter("usuario") == null ? ""
					: request.getParameter("usuario");
			String rol = request.getParameter("rol") == null ? ""
					: request.getParameter("rol");

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Usuario usuarioDTO = new Usuario();
			PersonaDAO personaDAO = new PersonaDAO();
			Persona persona = new Persona();
			RolDAO rolDAO = new RolDAO();
			Rol rolDTO = new Rol();			
			JSONObject usuarioJSONObject = new JSONObject();
			JSONArray usuarioJSONArray = new JSONArray();
			JSONObject rolJSONObject = new JSONObject();
			JSONArray rolJSONArray = new JSONArray();
			
			if (!idUsuario.equals("")){
				usuarioDTO.setIdUsuario(Integer.parseInt(idUsuario));
			}
			if (!nombres.equals("")){
				persona.setNombres(nombres);
			}
			if (!apellidos.equals("")){
				persona.setApellidos(apellidos);
			}
			if (!direccion.equals("")){
				persona.setDireccion(direccion);
			}
			if (!email.equals("")){
				persona.setEmail(email);
			}
			if (!telefono.equals("")){
				persona.setTelefono(telefono);
			}
			if(!usuario.equals("")){
				usuarioDTO.setUsuario(usuario);
			}
			if(!rol.equals("")){
				rolDTO = rolDAO.buscarPorId(Integer.parseInt(rol));				
			}
			
			//Usuarios
			if (tipoConsulta.equals("consultarUsuarios")) {
				List<Usuario> results = usuarioDAO.buscarTodosCompleto();
				for(Usuario resultado: results){
					usuarioJSONObject.put("codigo", resultado.getIdUsuario());
					usuarioJSONObject.put("nombres", resultado.getPersona().getNombres());
					usuarioJSONObject.put("apellidos", resultado.getPersona().getApellidos());
					usuarioJSONObject.put("direccion", resultado.getPersona().getDireccion());
					usuarioJSONObject.put("email", resultado.getPersona().getEmail());
					usuarioJSONObject.put("telefono", resultado.getPersona().getTelefono());
					usuarioJSONObject.put("usuario", resultado.getUsuario());
					usuarioJSONObject.put("rol", resultado.getRol().getRol());
					usuarioJSONArray.add(usuarioJSONObject);
				}
				result.put("numRegistros", results.size());
				result.put("listadoUsuarios", usuarioJSONArray);
			}

			if (tipoConsulta.equals("actualizar")) {
				persona = personaDAO.editar(persona);
				usuarioDTO.setPersona(persona);
				usuarioDTO.setRol(rolDTO);
				usuarioDAO.editar(usuarioDTO);
			}
			if (tipoConsulta.equals("eliminar")) {
				usuarioDAO.eliminar(usuarioDTO);
				personaDAO.eliminar(persona);
			}
			if (tipoConsulta.equals("crear")) {
				persona = personaDAO.crear(persona);
				usuarioDTO.setPersona(persona);
				usuarioDTO.setRol(rolDTO);
				usuarioDTO.setClave("123");
				usuarioDAO.crear(usuarioDTO);
			}
			//Cargar Rol
			if (tipoConsulta.equals("cargarRol")) {
				List<Rol> roles = rolDAO.buscarTodos();
				for(Rol resultado: roles){
					rolJSONObject.put("id", resultado.getIdRol());
					rolJSONObject.put("text", resultado.getRol());
					rolJSONArray.add(rolJSONObject);
				}
				result.put("listadoRoles", rolJSONArray);
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
