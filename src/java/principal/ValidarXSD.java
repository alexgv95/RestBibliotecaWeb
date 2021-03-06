/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import RestServices.ServicioValidador;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alex
 */
public class ValidarXSD extends HttpServlet {

    ServicioValidador sV = new ServicioValidador();

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

        String nombreFichero = request.getParameter("nombreFichero");
        File file = new File(nombreFichero);
//        String ruta = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("\\") + 1);
//        String[] ruta1 = ruta.split("\\.");
//        String rutaFichero = ruta1[0];
        String contenidoFichero = codificadorStringXSD(file);
        String respuesta = sV.postValidarXSD(contenidoFichero);
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ValidarXSD</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ValidarXSD at " + request.getContextPath() + "</h1>");
            out.println("<h3>" + respuesta + "</h3>");
            out.println("<a href=\"index.html\">Volver atrás</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private static String codificadorStringXSD(File file) {
        String contenidoFile = "";
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while (line != null) {
                contenidoFile = contenidoFile + line;
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ValidarXSD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ValidarXSD.class.getName()).log(Level.SEVERE, null, ex);
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
