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
public class ObtenerLibro extends HttpServlet {

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
            token = (String) request.getServletContext().getAttribute("token");
            response.setContentType("text/html;charset=UTF-8");

            if (token == null || !ver.comprobarToken(token)) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/noToken.html");
                rd.forward(request, response);
                return;
            }
            Biblioteca biblioteca = null;
            biblioteca = sB.getBiblioteca(Biblioteca.class, token);
            if (biblioteca == null || ver.comprobarBiblioteca(biblioteca) == false) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/noBiblioteca.html");
                rd.forward(request, response);
            }
            String idLibro = request.getParameter("numLibro");
            Libro libro = sB.getLibro(Libro.class, idLibro, token);

            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MostrarLibro</title>");
            out.println("<style>");
            out.println("table, th, td {\n"
                    + "  border: 1px solid black;\n"
                    + "}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>El libro " + "obtenido es: </h3>");
            out.println("<table>");
            out.println("<tr><th>Id Libro</th>" + "<th>Título</th>"
                    + "<th>Autor</th>" + "<th>Nº Páginas</th></tr>");
            out.println("<tr>");
            out.println("<td>" + libro.getIdLibro() + "</td>");
            out.println("<td>" + libro.getTitulo() + "</td>");
            out.println("<td>" + libro.getAutor() + "</td>");
            out.println("<td>" + libro.getNumPag() + "</td>");
            out.println("</tr>");
            out.println("</table> <br>");
            out.println("<h4><a href=\"/RestBibliotecaWeb/GestionarBiblioteca\">Gestionar Biblioteca</a></h4>");
            out.println("</body>");
            out.println("</html>");

        } catch (IOException | ServletException | ClientErrorException ex) {
            System.out.println(ex);
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
