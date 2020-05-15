/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import RestServices.ServicioBiblioteca;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Biblioteca;

/**
 *
 * @author Alex
 */
public class CrearBiblioteca extends HttpServlet {

    ServicioBiblioteca sB = new ServicioBiblioteca();
    Verificador ver = new Verificador();
    String token = "";

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
        token = (String) getServletContext().getAttribute("token");
        if (token == null || !ver.comprobarToken(token)) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/noToken.html");
            rd.forward(request, response);
        }

        String nombreFacultad = request.getParameter("nombreFacultad");
        String nombreCiudad = request.getParameter("nombreCiudad");
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setFacultad(nombreFacultad);
        biblioteca.setCiudad(nombreCiudad);

        Biblioteca newBiblioteca = sB.postBiblioteca(biblioteca, Biblioteca.class, token);
        System.out.println(newBiblioteca);
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CrearBiblioteca</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CrearBiblioteca at " + request.getContextPath() + "</h1>");
            out.println("<h3> Se ha creado la biblioteca " + biblioteca.getFacultad() + "</h3>");
            out.println("<a href=\"index.html\">Volver atrás</a>");
            out.println("</body>");
            out.println("</html>");
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
