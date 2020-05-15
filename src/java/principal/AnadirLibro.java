/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import RestServices.ServicioBiblioteca;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Biblioteca;
import modelo.Libro;

/**
 *
 * @author Alex
 */
public class AnadirLibro extends HttpServlet {

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

        try {
            token = (String) getServletContext().getAttribute("token");
            if (token == null || !ver.comprobarToken(token)) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/noToken.html");
                rd.forward(request, response);
            }
            Biblioteca biblioteca = sB.getBiblioteca(Biblioteca.class, token);
            if (ver.comprobarBiblioteca(biblioteca) == false) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/noBiblioteca.html");
                rd.forward(request, response);
            }
            String tituloLibro = request.getParameter("tituloLibro");
            String autorLibro = request.getParameter("autorLibro");
            Integer numPag = Integer.parseInt(request.getParameter("numPag"));

            Libro libro = new Libro();
            libro.setTitulo(tituloLibro);
            libro.setAutor(autorLibro);
            libro.setNumPag(numPag);

            sB.postLibro(libro, Libro.class, token);
            RequestDispatcher rd = getServletContext().getNamedDispatcher("GestionarBiblioteca");
            rd.forward(request, response);

        } catch (IOException ex) {
            System.out.println(ex);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/noBiblioteca.html");
            rd.forward(request, response);
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
