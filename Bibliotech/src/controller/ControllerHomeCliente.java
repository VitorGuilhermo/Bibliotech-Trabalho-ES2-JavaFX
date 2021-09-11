package controller;

/**
 * @author Vitor Guilhermo
 */
public class ControllerHomeCliente {
    private static ControllerHomeCliente instancia;
    
    
    private ControllerHomeCliente() {
    }
    public static ControllerHomeCliente retorna(){
        if (instancia == null)
            instancia = new ControllerHomeCliente();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerHomeCliente getInstance() {
        return instancia;
    }
}
