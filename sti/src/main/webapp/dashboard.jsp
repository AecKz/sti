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
	<script src="js/controladores/dashboard.js"></script>
	<!-- bootstrap progress js -->
	<script src="js/progressbar/bootstrap-progressbar.min.js"></script>
	<script src="js/nicescroll/jquery.nicescroll.min.js"></script>
  	<!-- pace -->
	<script src="js/pace/pace.min.js"></script>
</head>


<body class="nav-md">
		<%
			// Permitimos el acceso si la session existe		
				if(session.getAttribute("login") == null){
				    response.sendRedirect("/index.jsp");
				}else{
					if(session.getAttribute("rol").equals("Administrador")){
						response.sendRedirect("/dashboardAdministrador.jsp");
					}else if(session.getAttribute("rol").equals("Coordinador")){
						response.sendRedirect("/dashboardCoordinador.jsp");
					}else if(session.getAttribute("rol").equals("")){
						response.sendRedirect("/nuevoUsuario.jsp");
					}
				}
		%>
	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col">
				<div class="left_col scroll-view">

					<div class="navbar nav_title" style="border: 0;">
						<a href="dashboard.jsp" class="site_title"><i class="fa fa-paw"></i> <span>STI</span></a>
					</div>
					<div class="clearfix"></div>

					<!-- menu prile quick info -->
					<div class="profile">
						<div class="profile_pic">
							<img src="images/img.jpg" alt="..." class="img-circle profile_img">
						</div>
						<div class="profile_info">
							<span>Bienvenida (o),</span>
							<h2 id="txtUsuarioMenu"></h2>
						</div>
					</div>
					<!-- /menu prile quick info -->

					<br />

					<!-- sidebar menu -->
					<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">

						<div class="menu_section">								
							<h3>Estudiante</h3>
							<ul class="nav side-menu">
								<li><a href="dashboard.jsp"><i class="fa fa-home"></i>Home<span class="fa fa-home fa-fw"></span></a>
								</li>
								<li><a><i class="fa fa-edit"></i>Registro de Turnos<span class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu" style="display: none">
										<li><a href="registroTurno.jsp">Turno Normal</a>
										</li>
<!-- 										<li><a href="#">Turno Extra</a> -->
<!-- 										</li> -->
									</ul>
								</li>
								<li><a><i class="fa fa-book"></i>Informes<span class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu" style="display: none">
										<li><a href="informeTurnosEstudiante.jsp">Turno Normal</a>
										</li>
<!-- 										<li><a href="#">Turno Extra</a> -->
<!-- 										</li> -->
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
              <li class="">
                <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                  <img id="imgUsuarioMenu" src="images/img.jpg" alt=""><span id="txtUsuarioCabecera"></span>
                  <span class=" fa fa-angle-down"></span>
                </a>
                <ul class="dropdown-menu dropdown-usermenu animated fadeInDown pull-right">
                  <li><a href="dashboard.jsp">  Inicio</a>
                  </li>
                  <li>
                    <a href="datosEstudiante.jsp">
<!--                       <span class="badge bg-red pull-right">50%</span> -->
                      <span>Perfil</span>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">Ayuda</a>
                  </li>
                  <li><a href="index.jsp" onclick="signOut();"><i class="fa fa-sign-out pull-right"></i> Log Out</a>
                  </li>
                </ul>
              </li>
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
									<h3>Bienvenida (o) al Sistema de la Cl&iacute;nica Odontol&oacute;gica de la UDLA</h3>
									<div id="btnGoogle">
									<div class="g-signin2" data-onsuccess="onSignIn"></div>
									</div>
								</div>
							</div>
							<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="x_content">

                  <div class="col-md-3 col-sm-3 col-xs-12 profile_left">

                    <div class="profile_img">

                      <!-- end of image cropping -->
                      <div id="crop-avatar">
                        <!-- Current avatar -->
                        <div class="avatar-view" title="">
                          <img src="images/picture.jpg" class="img-fluid" alt="Responsive image">
                        </div>

                        <!-- Loading state -->
                        <div class="loading" aria-label="Loading" role="img" tabindex="-1"></div>
                      </div>
                      <!-- end of image cropping -->

                    </div>
                    <h3 id="txtUsuarioContent"></h3>

                    <ul class="list-unstyled user_data">
                      <li><i class="fa fa-envelope user-profile-icon"></i><span id="txtUsuario"></span>
                      </li>
                      <li>
                        <i class="fa fa-university user-profile-icon"></i><span id="txtMatricula"></span>
                      </li>
                      <li>
                        <i class="fa fa-hospital-o user-profile-icon"></i><span id="txtClinica"></span>
                      </li>
                    </ul>

                    <a class="btn btn-success" id="btnEditarPerfil"><i class="fa fa-edit m-right-xs"></i>Editar Perfil</a>
                    <br />
                    
                  </div>
                  <div class="col-md-9 col-sm-9 col-xs-12">

<!--                     <div class="profile_title"> -->
<!--                       <div class="col-md-6"> -->
<!--                         <h2>Estad&iacute;sticas</h2> -->
<!--                       </div> -->
<!--                     </div> -->
<!--                     start of user-activity-graph -->
<!--                     <div id="graph_bar" style="width:100%; height:280px;"></div> -->
<!--                     end of user-activity-graph -->

                    <div class="" role="tabpanel" data-example-id="togglable-tabs">
                      <ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
                        <li role="presentation" class="active"><a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true">Turnos Reservados</a>
                        </li>
                        <li role="presentation" class=""><a href="#tab_content2" role="tab" id="profile-tab" data-toggle="tab" aria-expanded="false">Turnos Ocupados </a>
                        </li>
                        <li role="presentation" class=""><a href="#tab_content3" role="tab" id="profile-tab2" data-toggle="tab" aria-expanded="false">Turnos Cancelados</a>
                        </li>
                      </ul>
                      <div id="myTabContent" class="tab-content">
                        <div role="tabpanel" class="tab-pane fade active in" id="tab_content1" aria-labelledby="home-tab">
 						<!-- tablas de turnos reservados -->
							<table id="tblTurnosReservados" class="table">
							 <thead>
								<tr>
								<th>FECHA</th>
								<th>HORARIO</th>
								<th>TRATAMIENTO</th>
								<th>PACIENTE</th>
								<th>CUBICULO</th>
								</tr>
							</thead>
							<tbody id="contentReservados"></tbody>
							</table>
					   <!-- final tablas de turnos reservados-->

                        </div>
                        <div role="tabpanel" class="tab-pane fade" id="tab_content2" aria-labelledby="profile-tab">

                   <!-- tablas de turnos ocupados -->
							<table id="tblTurnosOcupados" class="table">
							 <thead>
								<tr>
								<th>FECHA</th>
								<th>HORARIO</th>
								<th>TRATAMIENTO</th>
								<th>PACIENTE</th>
								<th>CUBICULO</th>
								</tr>
							</thead>
							<tbody id="contentOcupados"></tbody>
							</table>
					   <!-- final tablas de turnos ocupados-->

                        </div>
                        <div role="tabpanel" class="tab-pane fade" id="tab_content3" aria-labelledby="profile-tab">
                        <!-- tablas de turnos cancelados -->
							<table id="tblTurnosCancelados" class="table">
							 <thead>
								<tr>
								<th>FECHA</th>
								<th>HORARIO</th>
								<th>TRATAMIENTO</th>
								<th>PACIENTE</th>
								<th>CUBICULO</th>
								</tr>
							</thead>
							<tbody id="contentCancelados"></tbody>
							</table>
					   <!-- final tablas de turnos cancelados-->
                        </div>
                      </div>
                    </div>
                  </div>
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
    <ul class="list-unstyled notifications clearfix" data-tabbed_notifications="notif-group">
    </ul>
    <div class="clearfix"></div>
    <div id="notif-group" class="tabbed_notifications"></div>
  </div>	
</body>
</html>