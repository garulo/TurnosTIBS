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
public class AgregarOficina extends HttpServlet {
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
        String agreOfiNum = request.getParameter("agreOfiNum");
        String agreOfiNombre = request.getParameter("agreOfiNombre");
        String agreOfiDesc = request.getParameter("agreOfiDesc");
        String agreOfiDir = request.getParameter("agreOfiDir");
        String agreOfiCP = request.getParameter("agreOfiCP");
        String agreOfiMun = request.getParameter("agreOfiMun");
        String agreOfiTel = request.getParameter("agreOfiTel");
        String agreOfiCity = request.getParameter("agreOfiCity");
        String agreOfiEmail = request.getParameter("agreOfiEmail");
         String agreOfiServ = request.getParameter("agreOfiServ");
        
        Consultas cl = new Consultas();
        Consultas cc = new Consultas();
        List<Servicio> lstServ = cc.slctServ(agreOfiServ);
        boolean updateLocal = cl.serv_ofi(lstServ);
        System.out.println("updateLocal ::::: "+updateLocal);
         
        Consultas co = new Consultas();
        if(co.agregarOficina(user,agreOfiNum,agreOfiNombre,agreOfiDesc,agreOfiDir,agreOfiCP,agreOfiMun,agreOfiTel,agreOfiCity,agreOfiEmail,agreOfiServ)){
            System.out.println("Ventanilla Creada Por ::: "+user+" ... "+agreOfiNombre);
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
