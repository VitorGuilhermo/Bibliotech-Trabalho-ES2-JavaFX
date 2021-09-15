package bd.entidades;

/**
 * @author Vitor Guilhermo
 */
public class EditoraPearson implements Strategy {

    @Override
    public double calculaMulta(int dias) {
        return 0.50 * dias;
    }
    
}
