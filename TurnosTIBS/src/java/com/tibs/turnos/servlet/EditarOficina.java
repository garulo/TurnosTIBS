/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibs.turnos.servlet;

import com.tibs.turnos.controlador.Consultas;
import com.tibs.turnos.dao.Servicio;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marco
 */
public class EditarOficina extends HttpServlet {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String user = request.getParameter("user");
        String editOfiId = request.getParameter("editOfiId");
        String editOfiNum = request.getParameter("editOfiNum");
        String editOfiNombre = request.getParameter("editOfiNombre");
        String editOfiDesc = request.getParameter("editOfiDesc");
        String editOfiDir = request.getParameter("editOfiDir");
        String editOfiCP = request.getParameter("editOfiCP");
        String editOfiMun = request.getParameter("editOfiMun");
        String editOfiTel = request.getParameter("editOfiTel");
        String editOfiCity = request.getParameter("editOfiCity");
        String editOfiEmail = request.getParameter("editOfiEmail");
        String editOfiServ = request.getParameter("editOfiServ");
        
        
        Consultas cl = new Consultas();
        Consultas cc = new Consultas();
        List<Servicio> lstServ = cc.slctServ(editOfiServ);
        boolean updateLocal = cl.serv_ofi(lstServ);
        System.out.println("updateLocal ::::: "+updateLocal);
        
        Consultas co = new Consultas();
        if(co.editarOficina(user,editOfiId,editOfiNum,editOfiNombre,editOfiDesc,editOfiDir,editOfiCP,editOfiMun,editOfiTel,editOfiCity,editOfiEmail,editOfiServ)){

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
