package tigre.sti.controlador;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import tigre.sti.dao.EtapaDAO;
import tigre.sti.dao.IncidenciaDAO;
import tigre.sti.dao.PrioridadDAO;
import tigre.sti.dao.ServicioDAO;
import tigre.sti.dao.SolucionDAO;
import tigre.sti.dao.UsuarioDAO;
import tigre.sti.dto.Estado;
import tigre.sti.dto.Etapa;
import tigre.sti.dto.Incidencia;
import tigre.sti.dto.Prioridad;
import tigre.sti.dto.Servicio;
import tigre.sti.dto.Solucion;
import tigre.sti.dto.Usuario;
import tigre.sti.util.Utilitarios;

/**
 * Servlet implementation class IndexController
 */
@WebServlet("/IncidenciaController")
public class IncidenciaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private String host;
//	private String port;
//	private String user;
//	private String pass;
//
//	public void init() {
//		// reads SMTP server setting from web.xml file
//		ServletContext context = getServletContext();
//		host = context.getInitParameter("host");
//		port = context.getInitParameter("port");
//		user = context.getInitParameter("user");
//		pass = context.getInitParameter("pass");
//	}

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
		JSONArray incidenciasJSONArray = new JSONArray();
		JSONObject incidenciasJSONObject = new JSONObject();
		JSONArray asignadasJSONArray = new JSONArray();
		JSONObject asignadasJSONObject = new JSONObject();
		ServicioDAO servicioDAO = new ServicioDAO();
		IncidenciaDAO incidenciaDAO = new IncidenciaDAO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		EstadoDAO estadoDAO = new EstadoDAO();
		EtapaDAO etapaDAO = new EtapaDAO();
		PrioridadDAO prioridadDAO = new PrioridadDAO();
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
			String codigo = request.getParameter("codigo") == null ? "" : request.getParameter("codigo");
			String idTecnico = request.getParameter("idTecnico") == null ? "" : request.getParameter("idTecnico");
			String idEstado = request.getParameter("idEstado") == null ? "" : request.getParameter("idEstado");
			String idPrioridad = request.getParameter("idPrioridad") == null ? "" : request.getParameter("idPrioridad");
			String descripcionSolucion = request.getParameter("solucion") == null ? "" : request.getParameter("solucion");
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

			if (tipoConsulta.equals("crearIncidencia")) {
				Incidencia incidencia = new Incidencia();
				Servicio servicio = new Servicio();
				Usuario usuarioReporta = new Usuario();
				servicio = servicioDAO.buscarPorId(Integer.parseInt(idServicio));
				Etapa etapa = new Etapa();
				etapa = etapaDAO.buscarPorId(1);
				incidencia.setEtapa(etapa);
				Estado estado = new Estado();
				estado= estadoDAO.buscarPorId(1);				
				incidencia.setEstado(estado);
				Date date = new Date();
				incidencia.setFechaInicio(new Timestamp(date.getTime()));
				incidencia.setServicio(servicio);
				usuarioReporta = usuarioDAO.buscarPorUsuario(idUsuarioSolicitante);				
				incidencia.setUsuario2(usuarioReporta);
				incidencia.setTelefonoContacto(telefonoContacto);
				incidencia.setTipoContacto(tipoContacto);
				incidencia.setTitulo(titulo);
				incidencia.setDescripcion(descripcion);
				Prioridad prioridad = new Prioridad();
				prioridad = prioridadDAO.buscarPorId(Integer.parseInt(idPrioridad));
				incidencia.setPrioridad(prioridad);
				incidenciaDAO.crear(incidencia);
				//Enviar Email
//				String toAddress = usuarioReporta.getPersona().getEmail();
//				String subject = "Incidencia Creada";
//				String message = "<i>Saludos!</i><br>";
//			        message += "<b>Se ha creado una incidencia!</b><br>";
//			        message += "<font color=red>STI</font>";
//				Utilitarios.sendEmail(host, port, user, pass, toAddress, subject, message);
			}
			if(tipoConsulta.equals("busquedaIncidenciasActivas")){
				Estado estado = new Estado();
				estado = estadoDAO.buscarPorId(1);//Estado Registrado
				List<Incidencia> incidencias = incidenciaDAO.buscarPorEstado(estado);
				for(Incidencia incidencia: incidencias){
					String fechaTurno = Utilitarios.dateToString(incidencia.getFechaInicio());
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
			if(tipoConsulta.equals("busquedaIncidenciasAsignadas")){
				Estado estado = new Estado();
				estado = estadoDAO.buscarPorId(6);
				List<Incidencia> incidencias = incidenciaDAO.buscarPorEstado(estado);
				for(Incidencia incidencia: incidencias){
					String fechaTurno = Utilitarios.dateToString(incidencia.getFechaInicio());
					asignadasJSONObject.put("fecha", fechaTurno);
					asignadasJSONObject.put("codigo", incidencia.getIdIncidencia());
					asignadasJSONObject.put("categoria", incidencia.getServicio().getCategoria().getNombre());
					asignadasJSONObject.put("servicio", incidencia.getServicio().getNombre());
					asignadasJSONObject.put("titulo", incidencia.getTitulo());
					asignadasJSONObject.put("descripcion", incidencia.getDescripcion());
					asignadasJSONObject.put("solicitante", incidencia.getUsuario2().getPersona().getNombres()+ 
							" " +incidencia.getUsuario2().getPersona().getApellidos());
					asignadasJSONObject.put("asignado", incidencia.getUsuario1().getPersona().getNombres()+ 
							" " +incidencia.getUsuario1().getPersona().getApellidos());
					asignadasJSONArray.add(asignadasJSONObject);
				}
				result.put("numRegistros", (asignadasJSONArray.size()));
				result.put("listadoAsignadas", asignadasJSONArray);
			}
			if(tipoConsulta.equals("cargarDatosIncidencia")){
				Incidencia incidencia = incidenciaDAO.buscarPorId(Integer.parseInt(idIncidencia));
				result.put("telefonoContacto", incidencia.getTelefonoContacto());
				result.put("tipoContacto", incidencia.getTipoContacto());
				result.put("titulo", incidencia.getTitulo());
				result.put("descripcion", incidencia.getDescripcion());
				result.put("prioridad", incidencia.getPrioridad().getNombre());
				result.put("estado", incidencia.getEstado().getNombre());
				Servicio servicio = servicioDAO.buscarPorId(incidencia.getServicio().getIdServicio());
				result.put("nombreServicio", servicio.getNombre());
				result.put("descripcionServicio", servicio.getDescripcion());
			}
			
			if(tipoConsulta.equals("busquedaIncidenciasActivasSolicitante")){
				Usuario usuario = new Usuario();
				usuario = usuarioDAO.buscarPorUsuario(idUsuarioSolicitante);
				List<Incidencia> incidenciasRegistradas = incidenciaDAO.buscarPorUsuarioSolicitante(usuario, 1);
				List<Incidencia> incidenciasResueltas = incidenciaDAO.buscarPorUsuarioSolicitante(usuario, 2);
				List<Incidencia> incidenciasAsignadas = incidenciaDAO.buscarPorUsuarioSolicitante(usuario, 5);
				List<Incidencia> incidencias = new ArrayList<Incidencia>();
				incidencias.addAll(incidenciasRegistradas);
				incidencias.addAll(incidenciasResueltas);
				incidencias.addAll(incidenciasAsignadas);
				for(Incidencia incidencia: incidencias){
					String fechaTurno = Utilitarios.dateToString(incidencia.getFechaInicio());
					incidenciasJSONObject.put("fecha", fechaTurno);
					incidenciasJSONObject.put("codigo", incidencia.getIdIncidencia());
					incidenciasJSONObject.put("servicio", incidencia.getServicio().getNombre());
					incidenciasJSONObject.put("categoria", incidencia.getServicio().getCategoria().getNombre());
					incidenciasJSONObject.put("titulo", incidencia.getTitulo());
					incidenciasJSONArray.add(incidenciasJSONObject);
				}
				result.put("numRegistros", (incidenciasJSONArray.size()));
				result.put("listadoIncidencias", incidenciasJSONArray);
			}
			
			if(tipoConsulta.equals("busquedaIncidenciasResueltasSolicitante")){
				Usuario usuario = new Usuario();
				usuario = usuarioDAO.buscarPorUsuario(idUsuarioSolicitante);
				List<Incidencia> incidencias = incidenciaDAO.buscarPorUsuarioSolicitante(usuario,2);
				for(Incidencia incidencia: incidencias){
					String fechaTurno = Utilitarios.dateToString(incidencia.getFechaInicio());
					incidenciasJSONObject.put("fecha", fechaTurno);
					incidenciasJSONObject.put("codigo", incidencia.getIdIncidencia());
					incidenciasJSONObject.put("servicio", incidencia.getServicio().getNombre());
					incidenciasJSONObject.put("categoria", incidencia.getServicio().getCategoria().getNombre());
					incidenciasJSONObject.put("titulo", incidencia.getTitulo());
					incidenciasJSONArray.add(incidenciasJSONObject);
				}
				result.put("numRegistros", (incidenciasJSONArray.size()));
				result.put("listadoIncidencias", incidenciasJSONArray);
			}
			
			if(tipoConsulta.equals("busquedaIncidenciasCerradasSolicitante")){
				Usuario usuario = new Usuario();
				usuario = usuarioDAO.buscarPorUsuario(idUsuarioSolicitante);
				List<Incidencia> incidencias = incidenciaDAO.buscarPorUsuarioSolicitante(usuario,4);
				for(Incidencia incidencia: incidencias){
					String fechaTurno = Utilitarios.dateToString(incidencia.getFechaInicio());
					incidenciasJSONObject.put("fecha", fechaTurno);
					incidenciasJSONObject.put("codigo", incidencia.getIdIncidencia());
					incidenciasJSONObject.put("servicio", incidencia.getServicio().getNombre());
					incidenciasJSONObject.put("categoria", incidencia.getServicio().getCategoria().getNombre());
					incidenciasJSONObject.put("titulo", incidencia.getTitulo());
					incidenciasJSONArray.add(incidenciasJSONObject);
				}
				result.put("numRegistros", (incidenciasJSONArray.size()));
				result.put("listadoIncidencias", incidenciasJSONArray);
			}
			
			if(tipoConsulta.equals("busquedaIncidenciasActivasTecnico")){
				Usuario usuario = new Usuario();
				usuario = usuarioDAO.buscarPorUsuario(idUsuarioSolicitante);
				List<Incidencia> incidencias = incidenciaDAO.buscarPorUsuarioResponsable(usuario,6);
				for(Incidencia incidencia: incidencias){
					String fechaTurno = Utilitarios.dateToString(incidencia.getFechaInicio());
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
			if(tipoConsulta.equals("busquedaIncidenciasProgresoTecnico")){
				Usuario usuario = new Usuario();
				usuario = usuarioDAO.buscarPorUsuario(idUsuarioSolicitante);
				List<Incidencia> incProgreso = incidenciaDAO.buscarPorUsuarioResponsable(usuario,5);
				for(Incidencia incidencia: incProgreso){
					String fechaTurno = Utilitarios.dateToString(incidencia.getFechaInicio());
					incidenciasJSONObject.put("fecha", fechaTurno);
					incidenciasJSONObject.put("codigo", incidencia.getIdIncidencia());
					incidenciasJSONObject.put("categoria", incidencia.getServicio().getCategoria().getNombre());
					incidenciasJSONObject.put("servicio", incidencia.getServicio().getNombre());
					incidenciasJSONObject.put("titulo", incidencia.getTitulo());
					incidenciasJSONObject.put("descripcion", incidencia.getDescripcion());
					incidenciasJSONObject.put("solicitante", incidencia.getUsuario2().getPersona().getNombres()+ 
							" " +incidencia.getUsuario2().getPersona().getApellidos());
					incidenciasJSONObject.put("solicitante", incidencia.getUsuario2().getPersona().getNombres()+ 
							" " +incidencia.getUsuario2().getPersona().getApellidos());
					incidenciasJSONArray.add(incidenciasJSONObject);
				}
				result.put("numRegistros", (incidenciasJSONArray.size()));
				result.put("listadoIncidencias", incidenciasJSONArray);
			}
			if(tipoConsulta.equals("busquedaIncidenciasResueltasTecnico")){
				Usuario usuario = new Usuario();
				usuario = usuarioDAO.buscarPorUsuario(idUsuarioSolicitante);
				List<Incidencia> incResueltas = incidenciaDAO.buscarPorUsuarioResponsable(usuario,2);
				List<Incidencia> incCanceladas = incidenciaDAO.buscarPorUsuarioResponsable(usuario,3);
				List<Incidencia> incidencias = new ArrayList<Incidencia>();
				incidencias.addAll(incResueltas);
				incidencias.addAll(incCanceladas);
				for(Incidencia incidencia: incidencias){
					String fechaTurno = Utilitarios.dateToString(incidencia.getFechaInicio());
					incidenciasJSONObject.put("fecha", fechaTurno);
					incidenciasJSONObject.put("codigo", incidencia.getIdIncidencia());
					incidenciasJSONObject.put("categoria", incidencia.getServicio().getCategoria().getNombre());
					incidenciasJSONObject.put("servicio", incidencia.getServicio().getNombre());
					incidenciasJSONObject.put("titulo", incidencia.getTitulo());
					incidenciasJSONObject.put("descripcion", incidencia.getDescripcion());
					incidenciasJSONObject.put("solicitante", incidencia.getUsuario2().getPersona().getNombres()+ 
							" " +incidencia.getUsuario2().getPersona().getApellidos());
					incidenciasJSONObject.put("estado", incidencia.getEstado().getNombre());
					incidenciasJSONArray.add(incidenciasJSONObject);
				}
				result.put("numRegistros", (incidenciasJSONArray.size()));
				result.put("listadoIncidencias", incidenciasJSONArray);
			}
						
			if(tipoConsulta.equals("asignarTecnicoIncidencia")){
				Incidencia incidencia = new Incidencia();
				incidencia = incidenciaDAO.buscarPorId(Integer.parseInt(codigo));
				Usuario usuario1 = new Usuario();
				usuario1 = usuarioDAO.buscarPorId(Integer.parseInt(idTecnico));
				incidencia.setUsuario1(usuario1);
				Estado estado = new Estado();
				estado = estadoDAO.buscarPorId(6); //Asignado
				Etapa etapa = new Etapa();
				etapa = etapaDAO.buscarPorId(2); //Espera Aprobacion
				incidencia.setEtapa(etapa);
				incidencia.setEstado(estado);
				Date date = new Date();
				incidencia.setFechaAsignacion(new Timestamp(date.getTime()));
				incidenciaDAO.editar(incidencia);				
			}	
			
			if(tipoConsulta.equals("crearSolucion") && idEstado.equals("2")){
				SolucionDAO solucionDAO = new SolucionDAO();
				Solucion solucion = new Solucion();
				Incidencia incidencia = new Incidencia();
				incidencia = incidenciaDAO.buscarPorId(Integer.parseInt(codigo));
				solucion.setIncidencia(incidencia);
				Date date = new Date();
				solucion.setFecha(new Timestamp(date.getTime()));
				solucion.setDescripcion(descripcionSolucion);
				solucionDAO.crear(solucion);
				Estado estado = new Estado();
				estado = estadoDAO.buscarPorId(Integer.parseInt(idEstado));
				Etapa etapa = new Etapa();
				etapa = etapaDAO.buscarPorId(4);//Espera validación
				incidencia.setEstado(estado);
				incidencia.setEtapa(etapa);
				incidenciaDAO.editar(incidencia);				
			}
			if(tipoConsulta.equals("cambiarEstadoIncidencia")){
				Incidencia incidencia = new Incidencia();
				if(!idIncidencia.equals("")){
					incidencia = incidenciaDAO.buscarPorId(Integer.parseInt(idIncidencia));
				}else{
					incidencia = incidenciaDAO.buscarPorId(Integer.parseInt(codigo));	
				}
				if(Integer.parseInt(idEstado) == 5){ // En progreso
					Etapa etapa = new Etapa();
					etapa = etapaDAO.buscarPorId(3); // En proceso
					incidencia.setEtapa(etapa);
					Estado estado = new Estado();
					estado = estadoDAO.buscarPorId(Integer.parseInt(idEstado));
					incidencia.setEstado(estado);
				}
				if(Integer.parseInt(idEstado) ==3){ // Cancelado
					Etapa etapa = new Etapa();
					etapa = etapaDAO.buscarPorId(5); // Terminado
					incidencia.setEtapa(etapa);
					Estado estado = new Estado();
					estado = estadoDAO.buscarPorId(Integer.parseInt(idEstado));
					incidencia.setEstado(estado);
					Date date = new Date();
					incidencia.setFechaCierre(new Timestamp(date.getTime()));
				}
				if(Integer.parseInt(idEstado) ==4){ // Cerrado
					Etapa etapa = new Etapa();
					etapa = etapaDAO.buscarPorId(5); // Terminado
					incidencia.setEtapa(etapa);
					Estado estado = new Estado();
					estado = estadoDAO.buscarPorId(Integer.parseInt(idEstado));
					incidencia.setEstado(estado);
					Date date = new Date();
					incidencia.setFechaCierre(new Timestamp(date.getTime()));
				}
				incidenciaDAO.editar(incidencia);
			}
			//Administrador Reportes
			if(tipoConsulta.equals("busquedaIncidenciasAbiertas")){
				Estado estado = new Estado();
				estado = estadoDAO.buscarPorId(1);
				List<Incidencia> incRegistradas = incidenciaDAO.buscarPorEstado(estado);
				Estado estado2 = new Estado();
				estado2 = estadoDAO.buscarPorId(6);
				List<Incidencia> incAsignadas = incidenciaDAO.buscarPorEstado(estado2);
				List<Incidencia> incidencias = new ArrayList<Incidencia>();
				incidencias.addAll(incRegistradas);
				incidencias.addAll(incAsignadas);
				for(Incidencia incidencia: incidencias){
					String fechaInicio = Utilitarios.dateToString(incidencia.getFechaInicio());
					incidenciasJSONObject.put("fechaInicio", fechaInicio);
					incidenciasJSONObject.put("codigo", incidencia.getIdIncidencia());
					incidenciasJSONObject.put("categoria", incidencia.getServicio().getCategoria().getNombre());
					incidenciasJSONObject.put("servicio", incidencia.getServicio().getNombre());
					incidenciasJSONObject.put("titulo", incidencia.getTitulo());
					incidenciasJSONObject.put("descripcion", incidencia.getDescripcion());
					incidenciasJSONObject.put("solicitante", incidencia.getUsuario2().getPersona().getNombres()+ 
							" " +incidencia.getUsuario2().getPersona().getApellidos());
					incidenciasJSONObject.put("estado", incidencia.getEstado().getNombre());
					String fechaAsignacion = ""; 
					if(incidencia.getFechaAsignacion()!= null){
						fechaAsignacion = Utilitarios.dateToString(incidencia.getFechaAsignacion());
					}
					incidenciasJSONObject.put("fechaAsignacion", fechaAsignacion);
					incidenciasJSONObject.put("prioridad", incidencia.getPrioridad().getNombre());
					if(incidencia.getUsuario1()!= null){
						incidenciasJSONObject.put("responsable", incidencia.getUsuario1().getPersona().getNombres()+ 
								" " +incidencia.getUsuario1().getPersona().getApellidos());
					}else{
						incidenciasJSONObject.put("responsable", "");
					}
					incidenciasJSONArray.add(incidenciasJSONObject);
				}
				result.put("numRegistros", (incidenciasJSONArray.size()));
				result.put("listadoIncidencias", incidenciasJSONArray);
			}
			if(tipoConsulta.equals("busquedaIncidenciasProgreso")){
				Estado estado = new Estado();
				estado = estadoDAO.buscarPorId(2);
				List<Incidencia> incResueltas = incidenciaDAO.buscarPorEstado(estado);
				Estado estado2 = new Estado();
				estado2 = estadoDAO.buscarPorId(5);
				List<Incidencia> incProgreso = incidenciaDAO.buscarPorEstado(estado2);
				List<Incidencia> incidencias = new ArrayList<Incidencia>();
				incidencias.addAll(incResueltas);
				incidencias.addAll(incProgreso);
				for(Incidencia incidencia: incidencias){
					String fechaInicio = Utilitarios.dateToString(incidencia.getFechaInicio());
					incidenciasJSONObject.put("fechaInicio", fechaInicio);
					incidenciasJSONObject.put("codigo", incidencia.getIdIncidencia());
					incidenciasJSONObject.put("categoria", incidencia.getServicio().getCategoria().getNombre());
					incidenciasJSONObject.put("servicio", incidencia.getServicio().getNombre());
					incidenciasJSONObject.put("titulo", incidencia.getTitulo());
					incidenciasJSONObject.put("descripcion", incidencia.getDescripcion());
					incidenciasJSONObject.put("solicitante", incidencia.getUsuario2().getPersona().getNombres()+ 
							" " +incidencia.getUsuario2().getPersona().getApellidos());
					incidenciasJSONObject.put("estado", incidencia.getEstado().getNombre());
					String fechaAsignacion = Utilitarios.dateToString(incidencia.getFechaAsignacion());
					incidenciasJSONObject.put("fechaAsignacion", fechaAsignacion);
					incidenciasJSONObject.put("prioridad", incidencia.getPrioridad().getNombre());
					if(incidencia.getUsuario1()!= null){
						incidenciasJSONObject.put("responsable", incidencia.getUsuario1().getPersona().getNombres()+ 
								" " +incidencia.getUsuario1().getPersona().getApellidos());
					}else{
						incidenciasJSONObject.put("responsable", "");
					}
					incidenciasJSONArray.add(incidenciasJSONObject);
				}
				result.put("numRegistros", (incidenciasJSONArray.size()));
				result.put("listadoIncidencias", incidenciasJSONArray);
			}

			if(tipoConsulta.equals("busquedaIncidenciasCerradas")){
				Estado estado = new Estado();
				estado = estadoDAO.buscarPorId(3);
				List<Incidencia> incCanceladas = incidenciaDAO.buscarPorEstado(estado);
				Estado estado2 = new Estado();
				estado2 = estadoDAO.buscarPorId(4);
				List<Incidencia> incCerradas = incidenciaDAO.buscarPorEstado(estado2);
				List<Incidencia> incidencias = new ArrayList<Incidencia>();
				incidencias.addAll(incCanceladas);
				incidencias.addAll(incCerradas);
				for(Incidencia incidencia: incidencias){
					String fechaInicio = Utilitarios.dateToString(incidencia.getFechaInicio());
					incidenciasJSONObject.put("fechaInicio", fechaInicio);
					incidenciasJSONObject.put("codigo", incidencia.getIdIncidencia());
					incidenciasJSONObject.put("categoria", incidencia.getServicio().getCategoria().getNombre());
					incidenciasJSONObject.put("servicio", incidencia.getServicio().getNombre());
					incidenciasJSONObject.put("titulo", incidencia.getTitulo());
					incidenciasJSONObject.put("descripcion", incidencia.getDescripcion());
					incidenciasJSONObject.put("solicitante", incidencia.getUsuario2().getPersona().getNombres()+ 
							" " +incidencia.getUsuario2().getPersona().getApellidos());
					incidenciasJSONObject.put("estado", incidencia.getEstado().getNombre());
					String fechaAsignacion = Utilitarios.dateToString(incidencia.getFechaAsignacion());
					incidenciasJSONObject.put("fechaAsignacion", fechaAsignacion);
					incidenciasJSONObject.put("prioridad", incidencia.getPrioridad().getNombre());
					if(incidencia.getUsuario1()!= null){
						incidenciasJSONObject.put("responsable", incidencia.getUsuario1().getPersona().getNombres()+ 
								" " +incidencia.getUsuario1().getPersona().getApellidos());
					}else{
						incidenciasJSONObject.put("responsable", "");
					}
					if(incidencia.getSolucions()!=null){
						String aux = "";
						for(int i = 0; i<incidencia.getSolucions().size(); i++){
							aux += incidencia.getSolucions().get(i).getDescripcion()+ "-";
						}
						incidenciasJSONObject.put("solucion", aux);
					}else{
						incidenciasJSONObject.put("solucion", "");
					}
					incidenciasJSONArray.add(incidenciasJSONObject);
				}
				result.put("numRegistros", (incidenciasJSONArray.size()));
				result.put("listadoIncidencias", incidenciasJSONArray);
			}
			
			if(tipoConsulta.equals("busquedaIncidenciasCriticas")){
				List<Incidencia> incidencias = incidenciaDAO.buscarIncidenciasCriticas();
				for(Incidencia incidencia: incidencias){
					String fechaInicio = Utilitarios.dateToString(incidencia.getFechaInicio());
					incidenciasJSONObject.put("fechaInicio", fechaInicio);
					incidenciasJSONObject.put("codigo", incidencia.getIdIncidencia());
					incidenciasJSONObject.put("categoria", incidencia.getServicio().getCategoria().getNombre());
					incidenciasJSONObject.put("servicio", incidencia.getServicio().getNombre());
					incidenciasJSONObject.put("titulo", incidencia.getTitulo());
					incidenciasJSONObject.put("descripcion", incidencia.getDescripcion());
					incidenciasJSONObject.put("solicitante", incidencia.getUsuario2().getPersona().getNombres()+ 
							" " +incidencia.getUsuario2().getPersona().getApellidos());
					incidenciasJSONObject.put("estado", incidencia.getEstado().getNombre());
					String fechaAsignacion = Utilitarios.dateToString(incidencia.getFechaAsignacion());
					incidenciasJSONObject.put("fechaAsignacion", fechaAsignacion);
					incidenciasJSONObject.put("prioridad", incidencia.getPrioridad().getNombre());
					if(incidencia.getUsuario1()!= null){
						incidenciasJSONObject.put("responsable", incidencia.getUsuario1().getPersona().getNombres()+ 
								" " +incidencia.getUsuario1().getPersona().getApellidos());
					}else{
						incidenciasJSONObject.put("responsable", "");
					}
					if(incidencia.getSolucions()!=null){
						String aux = "";
						for(int i = 0; i<incidencia.getSolucions().size(); i++){
							aux += incidencia.getSolucions().get(i).getDescripcion()+ "-";
						}
						incidenciasJSONObject.put("solucion", aux);
					}else{
						incidenciasJSONObject.put("solucion", "");
					}
					incidenciasJSONArray.add(incidenciasJSONObject);
				}
				result.put("numRegistros", (incidenciasJSONArray.size()));
				result.put("listadoIncidencias", incidenciasJSONArray);
			}
			//Incidencias que no se resolvieron en el tiempo correspondiente
			if (tipoConsulta.equals("busquedaIncidenciasRFT")) {
				List<Incidencia> incidencias = incidenciaDAO.buscarIncidenciasResueltas();
				for (Incidencia incidencia : incidencias) {
					Date fechaInicioIncidencia = incidencia.getFechaInicio();
					Date fechaSolucion = incidencia.getSolucions().get(0).getFecha();
					long diferenciaFechas = fechaSolucion.getTime() - fechaInicioIncidencia.getTime();
					long diferenciaHoras = diferenciaFechas / (1000 * 60 * 60); //Diferencia en horas
					String prioridad = incidencia.getPrioridad().getDuracion().substring(0, 1);
					if (diferenciaHoras > Long.parseLong(prioridad)) {
						String fechaInicio = Utilitarios.dateToString(incidencia.getFechaInicio());
						incidenciasJSONObject.put("fechaInicio", fechaInicio);
						incidenciasJSONObject.put("codigo", incidencia.getIdIncidencia());
						incidenciasJSONObject.put("categoria", incidencia.getServicio().getCategoria().getNombre());
						incidenciasJSONObject.put("servicio", incidencia.getServicio().getNombre());
						incidenciasJSONObject.put("titulo", incidencia.getTitulo());
						incidenciasJSONObject.put("descripcion", incidencia.getDescripcion());
						incidenciasJSONObject.put("solicitante", incidencia.getUsuario2().getPersona().getNombres()
								+ " " + incidencia.getUsuario2().getPersona().getApellidos());
						incidenciasJSONObject.put("estado", incidencia.getEstado().getNombre());
						String fechaAsignacion = Utilitarios.dateToString(incidencia.getFechaAsignacion());
						incidenciasJSONObject.put("fechaAsignacion", fechaAsignacion);
						incidenciasJSONObject.put("prioridad", incidencia.getPrioridad().getNombre());
						if (incidencia.getUsuario1() != null) {
							incidenciasJSONObject.put("responsable", incidencia.getUsuario1().getPersona().getNombres()
									+ " " + incidencia.getUsuario1().getPersona().getApellidos());
						} else {
							incidenciasJSONObject.put("responsable", "");
						}
						if (incidencia.getSolucions() != null) {
							String aux = "";
							for (int i = 0; i < incidencia.getSolucions().size(); i++) {
								aux += incidencia.getSolucions().get(i).getDescripcion() + "-";
							}
							incidenciasJSONObject.put("solucion", aux);
						} else {
							incidenciasJSONObject.put("solucion", "");
						}
						incidenciasJSONArray.add(incidenciasJSONObject);
					}
					result.put("numRegistros", (incidenciasJSONArray.size()));
					result.put("listadoIncidencias", incidenciasJSONArray);
				}
			}
			//Incidencias que no se asignaron en el tiempo correspondiente
			if (tipoConsulta.equals("busquedaIncidenciasAFT")) {
				Estado estado = new Estado();
				estado = estadoDAO.buscarPorId(6);
				List<Incidencia> incidencias = incidenciaDAO.buscarPorEstado(estado);
				for (Incidencia incidencia : incidencias) {
					Date fechaInicioIncidencia = incidencia.getFechaInicio();
					Date fechaAsignacion = incidencia.getFechaAsignacion();
					long diferenciaFechas = fechaAsignacion.getTime() - fechaInicioIncidencia.getTime();
					long diferenciaHoras = diferenciaFechas / (1000 * 60 * 60); //Diferencia en horas
					String prioridad = incidencia.getPrioridad().getDuracion().substring(0, 1);
					if (diferenciaHoras > Long.parseLong(prioridad)) {
						String fechaInicio = Utilitarios.dateToString(incidencia.getFechaInicio());
						incidenciasJSONObject.put("fechaInicio", fechaInicio);
						incidenciasJSONObject.put("codigo", incidencia.getIdIncidencia());
						incidenciasJSONObject.put("categoria", incidencia.getServicio().getCategoria().getNombre());
						incidenciasJSONObject.put("servicio", incidencia.getServicio().getNombre());
						incidenciasJSONObject.put("titulo", incidencia.getTitulo());
						incidenciasJSONObject.put("descripcion", incidencia.getDescripcion());
						incidenciasJSONObject.put("solicitante", incidencia.getUsuario2().getPersona().getNombres()
								+ " " + incidencia.getUsuario2().getPersona().getApellidos());
						incidenciasJSONObject.put("estado", incidencia.getEstado().getNombre());
						String fechaAsigna = Utilitarios.dateToString(incidencia.getFechaAsignacion());
						incidenciasJSONObject.put("fechaAsignacion", fechaAsigna);
						incidenciasJSONObject.put("prioridad", incidencia.getPrioridad().getNombre());
						if (incidencia.getUsuario1() != null) {
							incidenciasJSONObject.put("responsable", incidencia.getUsuario1().getPersona().getNombres()
									+ " " + incidencia.getUsuario1().getPersona().getApellidos());
						} else {
							incidenciasJSONObject.put("responsable", "");
						}
						if (incidencia.getSolucions() != null) {
							String aux = "";
							for (int i = 0; i < incidencia.getSolucions().size(); i++) {
								aux += incidencia.getSolucions().get(i).getDescripcion() + "-";
							}
							incidenciasJSONObject.put("solucion", aux);
						} else {
							incidenciasJSONObject.put("solucion", "");
						}
						incidenciasJSONArray.add(incidenciasJSONObject);
					}
					result.put("numRegistros", (incidenciasJSONArray.size()));
					result.put("listadoIncidencias", incidenciasJSONArray);
				}
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
