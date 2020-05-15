/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import RestServices.ServicioBiblioteca;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ExportarLibro extends HttpServlet {

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
        ListaLibros listaLibros = null;

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
        listaLibros = (ListaLibros) sB.getLibros(ListaLibros.class, token);
        if (listaLibros == null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/noLibro.html");
            rd.forward(request, response);
        }
        String numLibro = request.getParameter("numLibro");
        String nombreFichero = request.getParameter("nombreFichero");
        String respuesta = sB.exportarLibro(numLibro, nombreFichero, token);

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ExportarLibro</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExportarLibro at " + request.getContextPath() + "</h1>");
            out.println("<h3>El libro se ha exportado a la ruta donde guarde su IDE los archivos /h3>");
            out.println("<a href=\"index.html\">Volver atrás</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private static void descifrarString(String respuesta, String nombreFichero) {
        String newRespuesta = respuesta.replaceAll("#", "\n");
        System.out.println(newRespuesta);
        BufferedWriter bw = null;
        try {
            File fichero = new File("./" + nombreFichero + ".xml");
            bw = new BufferedWriter(new FileWriter(fichero));
            bw.write(newRespuesta);
        } catch (IOException ex) {
            Logger.getLogger(ExportarLibro.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(ExportarLibro.class.getName()).log(Level.SEVERE, null, ex);
            }
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
