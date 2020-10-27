<%-- 
    Document   : TurnosDashBoard
    Created on : Mar 5, 2020, 1:39:00 PM
    Author     : marco
--%>

<%@page import="com.tibs.turnos.dao.Oficina"%>
<%@page import="com.tibs.turnos.controlador.Consultas"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
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
        <style>
            body{
                margin: 0;display: flex;
            }
            video {
                position: fixed;
                min-width: 100%;
                min-height: 100%;
                left: 50%;
                top: 50%;
                transform: translateX(-50%) translateY(-50%);
                z-index: -1;
                /*-webkit-filter:sepia(100%);*/
            }
        </style>
        <link href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i&display=swap" rel="stylesheet">
    </head>

    <script type="text/javascript">
        var wsUri = "ws://localhost:8080/TurnosTIBS/endp";
        var websocket = new WebSocket(wsUri); //creamos el socket
        websocket.onopen = function (evt) { //manejamos los eventos...
            //log("Conectado..."); //... y aparecerá en la pantalla
            log(evt.data);
        };
        websocket.onmessage = function (evt) { // cuando se recibe un mensaje
            //log("Mensaje recibido:" + evt.data);
            log(evt.data);
        };
        websocket.onerror = function (evt) {
            //log("oho!.. error:" + evt.data);
            log(evt.data);
        };
        function log(mensaje) { //aqui mostrará el LOG de lo que está haciendo el WebSocket
            //var logDiv = document.getElementById("log");
            //logDiv.innerHTML = (mensaje + '<br/>');

            var cadena = mensaje;
            nuevaCadena = cadena.replace('"', '');
            nuevaCadena = nuevaCadena.replace('"', '');
            var arrayDeCadenas = nuevaCadena.split(",");

            for (var i = 0; i < arrayDeCadenas.length; i++) {
                if (arrayDeCadenas[1]) {
                    document.getElementById('vf').innerHTML = arrayDeCadenas[0];
                }
                if (arrayDeCadenas[1]) {
                    document.getElementById('tf').innerHTML = arrayDeCadenas[1];
                }
                if (arrayDeCadenas[1]) {
                    document.getElementById('tf1').innerHTML = arrayDeCadenas[2];
                }
                if (arrayDeCadenas[1]) {
                    document.getElementById('tf2').innerHTML = arrayDeCadenas[3];
                }
                if (arrayDeCadenas[1]) {
                    document.getElementById('tf3').innerHTML = arrayDeCadenas[4];
                }
                if (arrayDeCadenas[1]) {
                    document.getElementById('tf4').innerHTML = arrayDeCadenas[5];
                }

            }

        }
        ;
        $(function () {
            var actualizarHora = function () {
                var fecha = new Date(),
                        hora = fecha.getHours(),
                        minutos = fecha.getMinutes(),
                        segundos = fecha.getSeconds(),
                        diaSemana = fecha.getDay(),
                        dia = fecha.getDate(),
                        mes = fecha.getMonth(),
                        anio = fecha.getFullYear(),
                        ampm;

                var $pHoras = $("#horas"),
                        $pSegundos = $("#segundos"),
                        $pMinutos = $("#minutos"),
                        $pAMPM = $("#ampm"),
                        $pDiaSemana = $("#diaSemana"),
                        $pDia = $("#dia"),
                        $pMes = $("#mes"),
                        $pAnio = $("#anio");
                var semana = ['Domingo', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'];
                var meses = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];

                $pDiaSemana.text(semana[diaSemana]);
                $pDia.text(dia);
                $pMes.text(meses[mes]);
                $pAnio.text(anio);
                if (hora >= 12) {
                    hora = hora - 12;
                    ampm = "PM";
                } else {
                    ampm = "AM";
                }
                if (hora == 0) {
                    hora = 12;
                }
                if (hora < 10) {
                    $pHoras.text("0" + hora)
                } else {
                    $pHoras.text(hora)
                }
                ;
                if (minutos < 10) {
                    $pMinutos.text("0" + minutos)
                } else {
                    $pMinutos.text(minutos)
                }
                ;
                if (segundos < 10) {
                    $pSegundos.text("0" + segundos)
                } else {
                    $pSegundos.text(segundos)
                }
                ;
                $pAMPM.text(ampm);

            };


            actualizarHora();
            var intervalo = setInterval(actualizarHora, 1000);
        });</script>
    <body >
        <%
                                    Consultas ofi = new Consultas();
                                    Oficina oficina = ofi.consulNombreOficina();
                                   // System.out.println("*** "+oficina.getVideo()+"***");
                                %>
        <video controls="true" autoplay loop  poster="<%=oficina.getPoster()%>" >
         
            <source src="<%=oficina.getVideo()%>" > 
        </video>

        <div style="float: left;width: 100%;" >  

            <div >
                <table id="Table" class="table" style="alignment-adjust: central;" width="100%">

                    <tbody  style="alignment-baseline: central;width: 100%"  >

                        <tr >
                            <%
                                System.out.println(":::Carga :::");
                                Date fechaActual = new Date();
                                String n_fecha_format = new String();
                                n_fecha_format = new SimpleDateFormat("EEEEEEEEE dd 'de' MMMMM yyyy").format(fechaActual);
                                n_fecha_format = n_fecha_format.substring(0, 1).toUpperCase() + n_fecha_format.substring(1);
                                DateFormat time = new SimpleDateFormat("HH:mm");
                                String n_time_format = new String();
                                n_time_format = time.format(fechaActual);
                                String turno = new String();
                            %>
                            <td  rowspan="2"><img src="archivos/uploaded_files/Logo_Tibs_transparente.png" ></td>

                            <td style="margin: 5px;alignment-adjust: auto;text-align: center;background-color: gray;width:225px; "><label style="font-family: 'Roboto', sans-serif;color: white;font-size: 16px;color: white;"><%=n_fecha_format%>.</label></td>
                            <td width="200"  style="alignment-adjust: auto;text-align: center;background-color: gray;width: 150px;">
                                <div id="TT_JyewbhtBtpnB4FDKjfqDzjzzjWaK1pQFbdEd1cCIKkj" ></div>
                                <script type="text/javascript" src="https://www.tutiempo.net/s-widget/l_JyewbhtBtpnB4FDKjfqDzjzzjWaK1pQFbdEd1cCIKkj"></script>
                            </td>
                    <style>
                        .contenedorreloj {
                            max-width: 1000px;
                            margin: auto;
                        }
                        .widgetreloj {
                            top: 0;
                            bottom: 0;
                            left: 0;
                            right: 0;
                            margin: auto;
                        }

                        .reloj { 
                            font-weight: bold;
                            font-family: 'Roboto', sans-serif;
                            color: white;
                            font-size: 20px;
                        }

                    </style>
                    <td  width="200" style="margin: 5px;alignment-adjust: auto;text-align: center;background-color: gray;">
                        <div class="contenedorreloj">
                            <div class="widgetreloj">
                                <div class="reloj">
                                    <strong><label id="horas" class="horas"></label> <label >:</label>
                                        <label id="minutos" class="minutos"></label><label >:</label>
                                        <label id="segundos" class="segundos"></label>
                                        <label id="ampm" class="ampm"></label></strong>


                                </div>
                            </div>
                        </div>
                    </td>
                    </tr>
                    <tr style="height: 100px"></tr>
                    </tbody>


                </table>
            </div>

            <div style="position: fixed;bottom: 0;float: left;width: 100%;margin-bottom: 10px;"  >
                <table class="table" style="alignment-adjust: central;" width="100%" >
                    <tbody style="alignment-baseline: central;width: 100%" >
                        <tr>
                            <td colspan="6" style="alignment-adjust: auto;text-align: center;background-color: white;height: 70px;">
                                
                                <label style="font-size: 30px;color: black;font-family: sans-serif"><center><strong><%=oficina.getNombre().toUpperCase()%></strong></center></label>

                            </td>
                        </tr>
                        <tr >
                            <td  rowspan="3" style="alignment-adjust: auto;text-align: center;background-color:  gray;"><label style="font-family: 'Roboto', sans-serif;color: white;font-size: 30px;"><strong>ATENDIENDO TURNO</strong></label><br><label id="tf" style="font-weight: bold;font-size: 80px;font-family: 'Roboto', sans-serif;color: white;"></label></td>
                            <td rowspan="3" style="alignment-adjust: auto;text-align: center;background-color:  gray;"><label style="font-family: 'Roboto', sans-serif;color: #FFC20A;font-size: 30px;"><strong>VENTANILLA</strong></label><br><label id="vf" style="font-weight: bold;font-family: 'Roboto', sans-serif;color: #FFC20A;font-size: 80px;"></label></td>
                        </tr>
                        <tr>    
                            <td  colspan="4" style="alignment-adjust: auto;text-align: center;background-color: darkred;" ><label style="font-family: 'Roboto', sans-serif;color: white;font-size: 16px;"><strong>PRÓXIMOS TURNOS</strong></label></td>
                        </tr>
                        <tr>    
                            <td style="alignment-adjust: auto;text-align: center;background-color: gray" ><label style="font-family: 'Roboto', sans-serif;color: white;"><strong>Turno</strong></label><br><label id="tf1" style="font-weight: bold;font-family: 'Roboto', sans-serif;color: white;font-size: 40px;"></label></td>
                            <td  style="alignment-adjust: auto;text-align: center;background-color: gray;" ><label style="font-family: 'Roboto', sans-serif;color: white;"><strong>Turno</strong></label><br><label id="tf2" style="font-weight: bold;font-family: 'Roboto', sans-serif;color: white;font-size: 40px;"></label></td>
                            <td  style="alignment-adjust: auto;text-align: center;background-color: gray;" ><label style="font-family: 'Roboto', sans-serif;color: white;"><strong>Turno</strong></label><br><label id="tf3" style="font-weight: bold;font-family: 'Roboto', sans-serif;color: white;font-size: 40px;"></label></td>
                            <td  style="alignment-adjust: auto;text-align: center;background-color: gray;" ><label style="font-family: 'Roboto', sans-serif;color: white;"><strong>Turno</strong></label><br><label id="tf4" style="font-weight: bold;font-family: 'Roboto', sans-serif;color: white;font-size: 40px;"></label></td>

                        </tr>
                    </tbody>
                </table> 
            </div>
        </div>
    </body>
</html>
