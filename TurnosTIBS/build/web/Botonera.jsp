<%@page import="com.tibs.turnos.dao.Turno"%>
<%@page import="com.tibs.turnos.dao.Oficina"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tibs.turnos.dao.Servicio"%>
<%@page import="com.tibs.turnos.controlador.Consultas"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Botonera de Turnos TIBS</title>
        <link href="archivos/uploaded_files/icono_tibs_header.png" rel="shortcut icon" type="image/x-icon" />
        <link href="archivos/uploaded_files/icono_tibs_header.png" rel="apple-touch-icon" />
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.23/angular.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <link href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i&display=swap" rel="stylesheet">
    </head>
   <script>
    function inhabilitar(){
        alert("Esta función está inhabilitada para este equipo.");
        return false;
    }    
    document.oncontextmenu=inhabilitar
   
    </script>
    
   <!-- <script>
         var winFeature =
        'location=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes';
        window.open('Botonera.jsp','null',winFeature);  
        
    </script>-->
    <body onload="imprimirTurno('turnoDiv')" style="margin-top: 5px;height: 100%;width: 100%;text-align: left;background:url('/TurnosTIBS/archivos/uploaded_files/bi_business_intelligence1.png');background-size:cover;" >

        <form id="TurnoForm"  >
            <div style="margin: auto;display: table;width: 85%;height: 100%;alignment-adjust: central;">           
                <div style="display: table-cell;vertical-align: middle;alignment-adjust: central;" >
                    <div class="table-responsive">
                        <table class="table" style="alignment-adjust: central;" width="100%" height="100%">

                            <tbody  style="alignment-baseline: central;">
                                <tr>
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
                                    
                                    <td  width="300"><img hidden src="archivos/uploaded_files/tibs_logo.PNG" alt="Girl in a jacket" width="100" height="40" ></td>
                                    <td width="200" style="margin: 5px;alignment-adjust: auto;text-align: center;background-color: gray;"><label style="font-family: 'Roboto', sans-serif;color: white;font-size: 16px;"><%=n_fecha_format%>.</label></td>
                                    <td  style="alignment-adjust: auto;text-align: center;background-color: gray;width: 150px;">
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
                            <td width="200" style="margin: 5px;alignment-adjust: auto;text-align: center;background-color: gray;">
                                <div class="contenedorreloj">
                                    <div class="widgetreloj">
                                        <div class="reloj">
                                            <label id="horas" class="horas" style="font-size: 16px;"></label> <label >:</label>
                                            <label id="minutos" class="minutos" style="font-size: 16px;"></label><label >:</label>
                                            <label id="segundos" class="segundos" style="font-size: 16px;"></label>
                                            <label id="ampm" class="ampm" style="font-size: 16px;"></label>

                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td width="300"></td>
                            </tr>
                            <tr style="height: 180px;">

                            </tr>

                            </tbody></table>
                        <table class="table">
                            <thead> </thead>
                            <tbody>
                                <tr style="text-align: center;" >

                                    <td width="100"  ></td>
                                </tr>
                                <tr >
                                    <td colspan="6">
                                        <div >
                                            <div  >
                                                <label style="font-weight: bold;font-family: 'Roboto', sans-serif;font-size: 28px;color: black;"><strong>Bienvenido a TIBS de Monterrey</strong></label>
                                                <br><br>
                                                <%
                                                    Consultas ofi = new Consultas();
                                                    Oficina oficina = ofi.consulNombreOficina();
                                                %>
                                                <label style="font-weight: bold;font-family: 'Roboto', sans-serif;color: black;font-size: 26px;"><%=oficina.getNombre()%></label>
                                                <br><br><br>
                                                <label style="font-weight: bold;font-family: 'Roboto', sans-serif;color: black;font-size: 20px;">Seleccione un servicio para ser atendido</label>

                                                <input type="text" id="valServicio" name="valServicio" hidden>
                                                <input type="text" id="valNomServicio" name="valNomServicio" hidden>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                
                                <tr style="text-align: center;" >
                                    <td width="10%">
                                        
                                    </td>
                                    <td colspan="5" width="80%">

                                        <div><br>
                                            <% //TODO FOR DE SERVICIOS ACTIVOS
                                                Consultas co = new Consultas();
                                                List<Servicio> lstServ = co.serviciosActivos();
                                                for (Servicio s : lstServ) {
                                            %>
                                            <input type="button"   onclick="generarTurno('<%=s.getId()%>', '<%=s.getDescripcionServicio()%>')" style="margin: 5px;text-align: center;font-size: 24px;width: 234px;height: 103px;color:black;background: <%=s.getColor()%> 0% 0% no-repeat padding-box;border-radius: 10px;font-family: 'Roboto', sans-serif;;" value="<%=s.getDescripcionServicio()%>" data-wait="Espere ..."/>
                                            <%
                                                }
                                            %> 
                                        </div>
                                        <script>
                                            function generarTurno(p1, p2) {

                                                document.getElementById("valServicio").value = p1;
                                                document.getElementById("valNomServicio").value = p2;
                                                document.getElementById("TurnoForm").submit();

                                            }



                                        </script>
                                        <%
                                            System.out.println("valTurno ::: " + request.getParameter("valNomServicio"));
                                            System.out.println("valServicio ::: " + request.getParameter("valServicio"));
                                            if (null != request.getParameter("valServicio")) {
                                                Consultas coTurno = new Consultas();
                                                turno = coTurno.generarTurno(request.getParameter("valServicio"));
                                                System.out.println("turno ::: " + turno);
                                                Consultas saveTurno = new Consultas();
                                                
                                                

                                                saveTurno.guardarNuevoTurno(turno, request.getParameter("valNomServicio"), "1");
                                                
                                                
                                                response.sendRedirect("/TurnosTIBS/Botonera.jsp?turno=" + turno);

                                            } else {
                                                System.out.println("valServicio ::: " + request.getParameter("valServicio"));
                                            }


                                        %>
                                    </td>
                                    <td width="10%">
                                        
                                    </td>
                                </tr>



                            </tbody>

                        </table>

                    </div>
                </div>

            </div>

        </form>

    <dialog id="ticketPrint" >
        <div id="turnoDiv" hidden  >
            <label style="font-weight: bold;"><center>Technology & Information for Business Solutions </center></label>
            <hr  width="60%">
            <label><center>Atentido en Sucursal:</center></label>
            <label style="font-weight: bold;"><center>TIBS Monterrey Centro </center></label>
            <label><center><%=fechaActual%></center></label>
            <hr width="60%">
            <label><center>Número de Turno asignado:</center></label>
            <label style="font-weight: bold;font-family: 'Roboto', sans-serif;color: black;font-size: 60px;"><center><%=request.getParameter("turno")%></center></label>
            <label><center>Favor de esperar en el área de ventanillas</center></label>
            <hr  width="60%">
      


        </div>

    </dialog>
    <script>
        function imprimirTurno(turnoDiv) {
            var ficha = document.getElementById(turnoDiv);
            var ventimp = window.open(' ', 'popimpr');
            ventimp.document.write(ficha.innerHTML);
            ventimp.document.close();
            ventimp.print();
            ventimp.close();
            ticketPrint.close();
        }

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
        });
    </script>

</body>
</html>
