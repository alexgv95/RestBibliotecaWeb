/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import RestServices.ServicioBiblioteca;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

/**
 *
 * @author Alex
 */
public class ImportarLibro extends HttpServlet {

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
        token = (String) request.getServletContext().getAttribute("token");

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

        String nombreFichero = request.getParameter("nombreFichero");
        File file = new File(nombreFichero);
        String ruta = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("\\") + 1);
        String[] ruta1 = ruta.split("\\.");
        String rutaFichero = ruta1[0];
        String contenidoFichero = codificadorString(file);
        String respuesta = sB.importarLibro(rutaFichero, contenidoFichero, token);

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ImportarLibro</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ImportarLibro at " + request.getContextPath() + "</h1>");
            out.println("<h3>" + respuesta + "</h3>");
            out.println("<a href=\"index.html\">Volver atrás</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private static String codificadorString(File file) {
        String contenidoFile = "";
        String STOP = "#";
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while (line != null) {
                contenidoFile = contenidoFile + line + STOP;
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportarLibro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImportarLibro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contenidoFile;
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
