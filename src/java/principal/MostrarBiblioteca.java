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
import javax.ws.rs.ClientErrorException;
import modelo.Biblioteca;
import modelo.Libro;

/**
 *
 * @author Alex
 */
public class MostrarBiblioteca extends HttpServlet {

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
            System.out.println(token);
            if (token == null || !ver.comprobarToken(token)) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/noToken.html");
                rd.forward(request, response);
                return;
            }
            Biblioteca biblioteca = sB.getBiblioteca(Biblioteca.class, token);
            if (ver.comprobarBiblioteca(biblioteca) == false) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/noBiblioteca.html");
                rd.forward(request, response);
            }
            String respond = sB.getLibrosTexto(token);

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MostrarBiblioteca</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MostrarBiblioteca at " + request.getContextPath() + "</h1>");
            out.println("<h3>La biblioteca contiene la siguiente información: </h3>");
            out.println("Facultad: " + biblioteca.getFacultad() + "<br/>");
            out.println("Ciudad: " + biblioteca.getCiudad() + "<br/>");
            out.println("Lista de libros: </br>");
            out.println("<table border:'1px'>");
            for (Libro l : biblioteca.getLibros()){
                out.println("<tr><td>"+l.getIdLibro()+"</td>");
                out.println("<td>");
                out.println("<br/>&emsp;Título: " + l.getTitulo());
                out.println("<br/>&emsp;Autor: " + l.getAutor());
                out.println("<br/>&emsp;NºPáginas: " + l.getNumPag() + "<br/>");
                out.println("</td>");
            }
            out.println("</table>");
            out.println("<a href=\"index.html\">Volver atrás</a>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException | ServletException | ClientErrorException ex) {
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
