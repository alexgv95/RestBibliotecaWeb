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
import modelo.ListaLibros;

/**
 *
 * @author Alex
 */
public class GestionarBiblioteca extends HttpServlet {

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
        response.setHeader("Refresh", "60");
        try {
            token = (String) request.getServletContext().getAttribute("token");
            response.setContentType("text/html;charset=UTF-8");

            if (token == null || !ver.comprobarToken(token)) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/noToken.html");
                rd.forward(request, response);
            }
            Biblioteca biblioteca = null;
            biblioteca = sB.getBiblioteca(Biblioteca.class, token);
            if (biblioteca == null || ver.comprobarBiblioteca(biblioteca) == false) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/noBiblioteca.html");
                rd.forward(request, response);
            }
            ListaLibros listaLibros = (ListaLibros) sB.getLibros(ListaLibros.class, token);

            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet GestionarBiblioteca</title>");
                out.println("<style>");
                out.println("table, th, td {\n"
                        + "  border: 1px solid black;\n"
                        + "}");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h3>Biblioteca: " + biblioteca.getFacultad() + "</h3>");
                out.println("<table>");
                out.println("<tr><th>Id Libro</th>"
                        + "<th>Título</th>" + "<th>Autor</th>" + "<th>Nº Páginas</th></tr>");
                for (int i = 0; i < listaLibros.getLibros().size(); i++) {
                    out.println("<tr>");
                    out.println("<td>" + listaLibros.getLibros().get(i).getIdLibro() + "</td>");
                    out.println("<td>" + listaLibros.getLibros().get(i).getTitulo() + "</td>");
                    out.println("<td>" + listaLibros.getLibros().get(i).getAutor() + "</td>");
                    out.println("<td>" + listaLibros.getLibros().get(i).getNumPag() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table> <br>");
                out.println("<table>");
                out.println("<tr>");
                out.println("<td>Añadir Libro</td>");
                out.println("<td colspan=\"5\" style=\"text-align:center;\"> <form name=\"GetLibroForm\" method=\"post\" action=\"FormularioLibro.html\">\n"
                        + "          <input type=\"submit\" value=\"Añadir un libro\" style=\"width:100%\"/></td> </form> ");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td> <form name=\"GetLibroForm\" method=\"post\" action=\"ObtenerLibro\">\n"
                        + "            Obtener Libro</td>"
                        + "<td>Número del Libro <input type=\"text\" name=\"numLibro\"/></td> "
                        + "<td colspan=\"4\" style=\"text-align:center;\"><input type=\"submit\" value=\"Obtener libro\" style=\"width:100%\"/> </td></form> ");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td> <form name=\"PutLibroForm\" method=\"post\" action=\"ModificarLibro\">\n"
                        + "            Modificar Libro</td>"
                        + "<td>Número del libro <input type=\"text\" name=\"numLibro\"/></td> "
                        + "<td>Título del libro <input type=\"text\" name=\"tituloLibro\"/></td> "
                        + "<td>Autor del libro <input type=\"text\" name=\"autorLibro\"/></td> "
                        + "<td>Número de páginas <input type=\"int\" name=\"numPag\"/></td> "
                        + "<td><input type=\"submit\" value=\"Modificar libro\"/> </td></form> ");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td> <form name=\"DeleteLibroForm\" method=\"post\" action=\"BorrarLibro\">\n"
                        + "            Borrar Libro</td>"
                        + "<td>Número del libro <input type=\"text\" name=\"numLibro\"/></td> "
                        + "<td colspan=\"4\" style=\"text-align:center;\"><input type=\"submit\" value=\"Borrar libro\" style=\"width:100%\"/> </td></form> ");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td> <form name=\"ExportForm\" method=\"post\" action=\"ExportarLibro\">\n"
                        + "            Exportar Libro</td>"
                        + "<td>Número del Libro <input type=\"text\" name=\"numLibro\"/></td> "
                        + "<td>Fichero destino (sin .xml) <input type=\"text\" name=\"nombreFichero\"/></td> "
                        + "<td colspan=\"4\" style=\"text-align:center;\"><input type=\"submit\" value=\"Exportar libro\" style=\"width:100%\"/> </td></form> ");
                out.println("</tr>");
                out.println("</table>");
                out.println("<a href=\"index.html\">Volver atrás</a>");
                out.println("</body>");
                out.println("</html>");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } catch (Exception ex) {
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
