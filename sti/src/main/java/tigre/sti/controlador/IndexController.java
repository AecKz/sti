package tigre.sti.controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import tigre.sti.dao.PersonaDAO;
import tigre.sti.dao.UsuarioDAO;
import tigre.sti.dto.Persona;
import tigre.sti.dto.Rol;
import tigre.sti.dto.Usuario;

/**
 * Servlet implementation class IndexController
 */
@WebServlet("/IndexController")
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexController() {
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
			String usuarioLogin = request.getParameter("usuarioLogin") == null ? ""
					: request.getParameter("usuarioLogin").trim();
			String contrasenaLogin = request.getParameter("contrasenaLogin") == null ? ""
					: request.getParameter("contrasenaLogin").trim();

			Persona persona = new Persona();
			PersonaDAO personaDAO = new PersonaDAO();

			Usuario usuario = new Usuario();
			UsuarioDAO usuarioDAO = new UsuarioDAO();

			if (tipoConsulta.equals("crear")) {
				personaDAO.crear(persona);
				usuario.setPersona(persona);
				usuarioDAO.crear(usuario);
				activarSesion(request, usuario);
			}

			if (tipoConsulta.equals("login")) {
				Boolean flagLogin = false;
				// flagLogin = usuarioDAO.login(usuarioLogin,
				// Utilitarios.encriptacionClave(contrasenaLogin));
				flagLogin = usuarioDAO.login(usuarioLogin, contrasenaLogin);
				if (!flagLogin) {
					result.put("errorLogin", "Usuario o Contraseña Incorrecta");
				} else {
					persona = personaDAO.buscarPorUsuario(usuarioLogin);
					int idPersonaLogin = persona.getIdPersona();
					usuario = usuarioDAO.buscarPorIdPersona(idPersonaLogin);
					activarSesion(request, usuario);
				}
			}
			if (tipoConsulta.equals("cerrarSesion")) {
				cerrarSesion(request, response);
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

	private void activarSesion(HttpServletRequest request, Usuario usuario) {
		// Activacion de la sesion y agregamos
		HttpSession session = request.getSession();
		if (usuario != null) {
			session.setAttribute("login", usuario.getUsuario());
			// Verificar Rol
			Rol rol = usuario.getRol();
			if (rol != null) {
				session.setAttribute("rol", usuario.getRol().getRol());
			} else {
				session.setAttribute("rol", "");
			}
		} else {
			session.setAttribute("login", "");
			session.setAttribute("rol", "");
		}
	}

	private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Activacion de la sesion y agregamos
		HttpSession session = request.getSession();
		session.removeAttribute("login");
		session.removeAttribute("rol");
		session.invalidate();
		response.sendRedirect("index.jsp");
	}

}
