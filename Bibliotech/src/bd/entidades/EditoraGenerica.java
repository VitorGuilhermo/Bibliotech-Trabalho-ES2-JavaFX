package bd.entidades;

/**
 * @author Vitor Guilhermo
 */
public class EditoraGenerica implements Strategy {

    @Override
    public double calculaMulta(int dias) {
        return 0.25 * dias;
    }
    
}
