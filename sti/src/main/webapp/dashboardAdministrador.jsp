<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>STI</title>

<!-- Bootstrap core CSS -->

<link href="css/bootstrap.min.css" rel="stylesheet">

<link href="fonts/css/font-awesome.min.css" rel="stylesheet">
<link href="css/animate.min.css" rel="stylesheet">

<!-- Custom styling plus plugins -->
<link href="css/custom.css" rel="stylesheet">
<script src="js/jquery.min.js"></script>
<script src="js/nprogress.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/custom.js"></script>
<script src="js/controladores/dashboardAdministrador.js"></script>
<!-- bootstrap progress js -->
<script src="js/progressbar/bootstrap-progressbar.min.js"></script>
<script src="js/nicescroll/jquery.nicescroll.min.js"></script>
<!-- pace -->
<script src="js/pace/pace.min.js"></script>
</head>


<body class="nav-md">
	<%
	// Permitimos el acceso si la session existe		
			if (session.getAttribute("login") == null) {
				response.sendRedirect("/sti/index.jsp");
			} else {
				if (session.getAttribute("rol").equals("usuario")) {
					response.sendRedirect("/sti/dashboard.jsp");
				} else if (session.getAttribute("rol").equals("coordinador")) {
					response.sendRedirect("/sti/dashboardCoordinador.jsp");
				} else if (session.getAttribute("rol").equals("tecnico")) {
					response.sendRedirect("/sti/dashboardTecnico.jsp");
				}
			}	
	%>
	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col">
				<div class="left_col scroll-view">

					<div class="navbar nav_title" style="border: 0;">
						<a href="dashboard.jsp" class="site_title"><i
							class="fa fa-paw"></i> <span>STI</span></a>
					</div>
					<!-- sidebar menu -->
					<div id="sidebar-menu"
						class="main_menu_side hidden-print main_menu">

						<div class="menu_section">
							<h3>Administrador</h3>
							<ul class="nav side-menu" id="menuLateral">
								<li><a><i class="fa fa-file-text-o"></i>Reportes<span class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu" style="display: none">
										<li><a href="incidenciasAbiertas.jsp">Incidencias Abiertas</a></li>
										<li><a href="incidenciasEnProgreso.jsp">Incidencias En Progreso</a>
										</li>
										<li><a href="incidenciasCerradas.jsp">Incidencias Cerradas</a></li>
										<li><a href="incidenciasCriticas.jsp">Incidencias Criticas</a></li>
										<li><a href="incidenciasAFT.jsp">Incidencias AFT</a></li>
										<li><a href="incidenciasRFT.jsp">Incidencias RFT</a></li>
									</ul></li>
								<li><a><i class="fa fa-edit"></i>Mantenimiento<span class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu" style="display: none">
										<li><a href="mantenimientoUsuarios.jsp">Usuarios</a>
										</li>
										<li><a href="mantenimientoCategorias.jsp">Categorias</a>
										</li>
										<li><a href="mantenimientoServicios.jsp">Servicios</a>
										</li>										
									</ul>
								</li>
							</ul>
						</div>
					</div>
					<!-- /sidebar menu -->

					<!-- /menu footer buttons -->
					<!-- 					<div class="sidebar-footer hidden-small"> -->
					<!-- 						<a data-toggle="tooltip" data-placement="top" title="Perfil" href="datosEstudiante.jsp"> -->
					<!-- 							<span class="glyphicon glyphicon-cog" aria-hidden="true"></span> -->
					<!-- 						</a> -->
					<!-- 						<a data-toggle="tooltip" data-placement="top" title="FullScreen"> -->
					<!-- 							<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span> -->
					<!-- 						</a> -->
					<!-- 						<a data-toggle="tooltip" data-placement="top" title="Lock"> -->
					<!-- 							<span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span> -->
					<!-- 						</a> -->
					<!-- 						<a data-toggle="tooltip" data-placement="top" title="Logout" href="index.jsp" onclick="signOut();"> -->
					<!-- 							<span class="glyphicon glyphicon-off" aria-hidden="true"></span> -->
					<!-- 						</a> -->
					<!-- 					</div> -->
					<!-- /menu footer buttons -->
				</div>
			</div>

			<!-- top navigation -->
			<div class="top_nav">

				<div class="nav_menu">
					<nav class="" role="navigation">
						<div class="nav toggle">
							<a id="menu_toggle"><i class="fa fa-bars"></i></a>
						</div>

						<ul class="nav navbar-nav navbar-right">
							<li class=""><a href="javascript:;"
								class="user-profile dropdown-toggle" data-toggle="dropdown"
								aria-expanded="false"> <img id="imgUsuarioMenu"
									src="images/img.jpg" alt=""><span id="txtUsuarioCabecera"></span>
									<span class=" fa fa-angle-down"></span>
							</a>
								<ul
									class="dropdown-menu dropdown-usermenu animated fadeInDown pull-right">
									<li><a href="dashboard.jsp"> Inicio</a></li>
									<li><a href="datosUsuario.jsp"> <!--                       <span class="badge bg-red pull-right">50%</span> -->
											<span>Perfil</span>
									</a></li>
									<li><a href="index.jsp" onclick="signOut();"><i
											class="fa fa-sign-out pull-right"></i> Log Out</a></li>
								</ul></li>
							<!-- 					correos -->
							<!--               <li role="presentation" class="dropdown"> -->
							<!--                 <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false"> -->
							<!--                   <i class="fa fa-envelope-o"></i> -->
							<!--                   <span class="badge bg-green">6</span> -->
							<!--                 </a> -->
							<!--                 <ul id="menu1" class="dropdown-menu list-unstyled msg_list animated fadeInDown" role="menu"> -->
							<!--                   <li> -->
							<!--                     <a> -->
							<!--                       <span class="image"> -->
							<!--                                         <img src="images/img.jpg" alt="Profile Image" /> -->
							<!--                                     </span> -->
							<!--                       <span> -->
							<!--                                         <span>John Smith</span> -->
							<!--                       <span class="time">3 mins ago</span> -->
							<!--                       </span> -->
							<!--                       <span class="message"> -->
							<!--                                         Film festivals used to be do-or-die moments for movie makers. They were where... -->
							<!--                                     </span> -->
							<!--                     </a> -->
							<!--                   </li> -->
							<!--                   <li> -->
							<!--                     <a> -->
							<!--                       <span class="image"> -->
							<!--                                         <img src="images/img.jpg" alt="Profile Image" /> -->
							<!--                                     </span> -->
							<!--                       <span> -->
							<!--                                         <span>John Smith</span> -->
							<!--                       <span class="time">3 mins ago</span> -->
							<!--                       </span> -->
							<!--                       <span class="message"> -->
							<!--                                         Film festivals used to be do-or-die moments for movie makers. They were where... -->
							<!--                                     </span> -->
							<!--                     </a> -->
							<!--                   </li> -->
							<!--                   <li> -->
							<!--                     <a> -->
							<!--                       <span class="image"> -->
							<!--                                         <img src="images/img.jpg" alt="Profile Image" /> -->
							<!--                                     </span> -->
							<!--                       <span> -->
							<!--                                         <span>John Smith</span> -->
							<!--                       <span class="time">3 mins ago</span> -->
							<!--                       </span> -->
							<!--                       <span class="message"> -->
							<!--                                         Film festivals used to be do-or-die moments for movie makers. They were where... -->
							<!--                                     </span> -->
							<!--                     </a> -->
							<!--                   </li> -->
							<!--                   <li> -->
							<!--                     <a> -->
							<!--                       <span class="image"> -->
							<!--                                         <img src="images/img.jpg" alt="Profile Image" /> -->
							<!--                                     </span> -->
							<!--                       <span> -->
							<!--                                         <span>John Smith</span> -->
							<!--                       <span class="time">3 mins ago</span> -->
							<!--                       </span> -->
							<!--                       <span class="message"> -->
							<!--                                         Film festivals used to be do-or-die moments for movie makers. They were where... -->
							<!--                                     </span> -->
							<!--                     </a> -->
							<!--                   </li> -->
							<!--                   <li> -->
							<!--                     <div class="text-center"> -->
							<!--                       <a> -->
							<!--                         <strong>See All Alerts</strong> -->
							<!--                         <i class="fa fa-angle-right"></i> -->
							<!--                       </a> -->
							<!--                     </div> -->
							<!--                   </li> -->
							<!--                 </ul> -->
							<!--               </li> -->
							<!-- fincorreos -->
						</ul>
					</nav>
				</div>

			</div>
			<!-- /top navigation -->


			<!-- page content -->
			<div class="right_col" role="main">

				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div class="dashboard_graph">

							<div class="row x_title">
								<div>
									<h3>Bienvenido (a) a Global Services de Tigre</h3>
								</div>
							</div>
							<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="row x_title">
								<div>
									<h3 id="lblTitulo">Resumen ITIL</h3>
								</div>
							</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h3 class="panel-title">Incidencias Críticas</h3>
									</div>
									<ul class="list-group" id="listaCriticas">
									</ul>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h3 class="panel-title">Incidencias Resueltas Fuera de Tiempo</h3>
									</div>
									<ul class="list-group" id="listaRFT">
									</ul>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h3 class="panel-title">Incidencias Asignadas Fuera de Tiempo</h3>
									</div>
									<ul class="list-group" id="listaAFT">
									</ul>
								</div>

							</div>
						</div>

					</div>
					<br />
				</div>
				<!-- /page content -->

			</div>

		</div>
	</div>

	<div id="custom_notifications" class="custom-notifications dsp_none">
		<ul class="list-unstyled notifications clearfix"
			data-tabbed_notifications="notif-group">
		</ul>
		<div class="clearfix"></div>
		<div id="notif-group" class="tabbed_notifications"></div>
	</div>
</body>
</html>