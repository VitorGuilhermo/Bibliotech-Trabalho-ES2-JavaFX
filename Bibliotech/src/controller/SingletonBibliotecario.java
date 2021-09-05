package controller;

import bd.entidades.Bibliotecario;

/**
 *
 * @author Vitor Guilhermo
 */
public class SingletonBibliotecario {

    private static Bibliotecario uniqueInstance;

    private SingletonBibliotecario() {
    }
    public static Bibliotecario getInstance(Bibliotecario bib) {
        if (uniqueInstance == null) {
            uniqueInstance = bib;
        }

        return uniqueInstance;
    }
    public static Bibliotecario getInstance() {
        return uniqueInstance;
    }
}
