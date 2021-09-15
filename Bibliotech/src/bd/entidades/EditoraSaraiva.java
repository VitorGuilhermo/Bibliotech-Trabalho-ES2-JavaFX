package bd.entidades;

/**
 * @author Vitor Guilhermo
 */
public class EditoraSaraiva implements Strategy {

    @Override
    public double calculaMulta(int dias) {
        return 0.75 * dias;
    }
    
}
