package controller;

import bd.entidades.Cliente;

/**
 *
 * @author Vitor Guilhermo
 */
public class SingletonCliente {
    private static Cliente uniqueInstance;

    private SingletonCliente() {
    }
    public static Cliente getInstance(Cliente cli) {
        if (uniqueInstance == null)
            uniqueInstance = cli;
        return uniqueInstance;
    }
    public static Cliente getInstance() {
        return uniqueInstance;
    }
}
