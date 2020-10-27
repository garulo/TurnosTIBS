<%-- 
    Document   : Login
    Created on : Feb 23, 2020, 7:05:27 PM
    Author     : marco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Turnos TIBS</title>
         <link href="archivos/uploaded_files/icono_tibs_header.png" rel="shortcut icon" type="image/x-icon" />
        <link href="archivos/uploaded_files/icono_tibs_header.png" rel="apple-touch-icon" />
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.23/angular.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
       <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"  crossorigin="anonymous"></script>
        
       <link href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i&display=swap" rel="stylesheet">
       <link href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i&display=swap" rel="stylesheet">

    </head>
    <body style="height: 100%;width: 100%;text-align: left;background:url('/TurnosTIBS/archivos/uploaded_files/bi_business_intelligence.png');background-size:cover;" >
        <div style="margin: auto;display: table;height: 100%;alignment-adjust: central;background-color: white;margin-top: 10%;">           
            <div style="display: table-cell;vertical-align: middle;alignment-adjust: central;" >

                <div class="table-responsive" >
                    <form action="iniciosesion"   method="post" id="formlogin" >
                        <table class="table">
                            <thead>
                                <tr >
                            <div style="margin-left:80px;margin-top: 50px;">
                                <div  >
                                    <h2 style="font-size: 22px;color: #58585A;font-family: 'Roboto', sans-serif;">ADMINISTRADOR DE TURNOS</h2>
                                </div>
                            </div>
                            </tr>
                            </thead><br>
                            <tbody >
                                <tr>
                                    <td style="color: #58585A;margin: 10px;alignment-adjust: auto;width: 400px;text-align: center;">
                                        <img src="archivos/uploaded_files/Logo_Tibs_transparente.png"  width='70%' height='100%' style="display: block;margin: 0 auto;max-width: 100%;"  >
                                    </td>
                                    <td style="color: #58585A;margin: 10px;alignment-adjust: auto;width: 400px;text-align: left;">
                                        <div><label for="Usuario" style="color: #58585A;font-family: 'Roboto', sans-serif;font-size: 16px;" ><strong>Usuario</strong></label></div>
                                        <div><input type="text" style="width: 300px;height: 30px;font-size: 14px;" autofocus="true" maxlength="256" name="Usuario" data-name="Usuario" placeholder="Teclea tu usuario" id="txtUsuario"/></div>
                                        <br>
                                        <div><label for="password" style="color: #58585A;font-family: 'Roboto', sans-serif;font-size: 16px;"><strong>Password</strong></label></div>
                                        <div><input type="password" style="width: 300px;height: 30px;font-size: 14px;"  maxlength="20" name="password" data-name="Password" placeholder="Teclea tu contraseña" id="txtPassword"/></div>
                                        <br> </td>
                                </tr>
                                <tr>
                                    <td colspan="2"><input id="btniniciar"  class="btn btn-outline-primary btn-lg btn-block" type="button" onclick="submit()" value="ENTRAR" data-wait="Espere ..."  />
                                        </td>
                                </tr>
                            </tbody>
                        </table>
                        <br><br>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
