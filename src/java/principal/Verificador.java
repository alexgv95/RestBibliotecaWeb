/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import modelo.Biblioteca;

/**
 *
 * @author Alex
 */
public class Verificador {

    boolean tokenValidado = false;
    boolean bibliotecaExiste = false;

    public boolean comprobarToken(String token) {
        tokenValidado = !token.equals("");
        return tokenValidado;
    }

    public boolean comprobarBiblioteca(Biblioteca biblioteca) {
        bibliotecaExiste = !biblioteca.getFacultad().equals("");
        return bibliotecaExiste;
    }

}
