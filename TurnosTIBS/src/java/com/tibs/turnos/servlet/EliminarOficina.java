
package com.tibs.turnos.servlet;

import com.tibs.turnos.controlador.Consultas;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marco
 */
public class EliminarOficina extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String txtNumOficina = request.getParameter("txtNumOficina");
        String txtIdOficina = request.getParameter("txtIdOficina");
        String txtUserElim = request.getParameter("txtUserElim");
        Consultas co = new Consultas();
        if(co.eliminarOficina(txtNumOficina,txtIdOficina,txtUserElim)){

        response.sendRedirect("/TurnosTIBS/DashBoard1.jsp");
        }else{
        response.sendRedirect("/TurnosTIBS/DashBoard1.jsp");
        
        }
     }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
