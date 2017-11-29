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
  <link href="css/icheck/flat/green.css" rel="stylesheet">
  <script src="js/jquery.min.js"></script>
  <script src="js/controladores/index.js"></script>

</head>

<body style="background:#F7F7F7;">
  <div class="">
    <a class="hiddenanchor" id="toregister"></a>
    <a class="hiddenanchor" id="tologin"></a>

    <div id="wrapper">
      <div id="login" class="animate form">
        <section class="login_content">
          <form>          
            <h1>Login</h1>
            <div>
              <input type="text" class="form-control" id="txtUsuarioLogin" placeholder="Usuario" required/>
            </div>
            <div>
              <input type="password" class="form-control" id="txtContrasenaLogin" placeholder="Contrase&ntilde;a" required/>
            </div>
            <div>
		       <button type="button" class="btn btn-primary" id="btnLogin">Enviar</button>
<!--                <a class="reset_pass" href="#">Recuperar Contrase&ntilde;a</a> -->
            </div>            
            <div class="clearfix"></div>
            <div class="separator">

              <div class="clearfix"></div>
              <br />
              <div>
                <h1><i class="fa fa-paw" style="font-size: 26px;"></i> STI</h1>

                <p> ©2017 Todos los derechos reservados</p>
              </div>
            </div>
          </form>
          <!-- form -->
        </section>
        <!-- content -->
      </div>
    </div>
  </div>

</body>
</html>