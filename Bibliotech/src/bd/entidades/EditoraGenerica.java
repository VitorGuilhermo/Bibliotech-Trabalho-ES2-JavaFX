package bd.entidades;

/**
 * @author Vitor Guilhermo
 */
public class EditoraGenerica implements Strategy {
    private Strategy tipoEdit;
    
    @Override
    public void calculaMulta(Multa m, String nome, int dias) {
        m.setValor(dias * 0.25);
    }
    
}
