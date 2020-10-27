
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
public class AgregarUsuarioCentral extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String txtAgregarUUsuario = request.getParameter("txtAgregarUUsuario");
        String txtAgregarUNombre = request.getParameter("txtAgregarUNombre");
        String txtAgregarUPass = request.getParameter("txtAgregarUPass");
        String txtAgregarUEmail = request.getParameter("txtAgregarUEmail");
        String txtAgregarUEPerfil = request.getParameter("txtAgregarUEPerfil");
        String txtAgregarUOfi = request.getParameter("txtAgregarUOfi");
        String txtAgregarUEstatus = request.getParameter("txtAgregarUEstatus");
        String useragr = request.getParameter("useragr");
        Consultas co = new Consultas();
        if(co.agregarUsuarioCentral(txtAgregarUUsuario,txtAgregarUNombre,txtAgregarUPass,txtAgregarUEmail,txtAgregarUEPerfil,txtAgregarUOfi,txtAgregarUEstatus,useragr)){

            response.sendRedirect("/TurnosTIBS/DashBoard1.jsp");
        }else{
        response.sendRedirect("/TurnosTIBS/DashBoard1.jsp");
        
        }
        
          }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
