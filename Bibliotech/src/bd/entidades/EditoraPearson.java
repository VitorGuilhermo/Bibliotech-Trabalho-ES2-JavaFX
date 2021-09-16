package bd.entidades;

/**
 * @author Vitor Guilhermo
 */
public class EditoraPearson implements Strategy {
    private Strategy tipoEdit;
    
    @Override
    public void calculaMulta(Multa m, String nome, int dias) {
        if(nome.equals("Pearson")){
            m.setValor(dias * 0.50);
        }
        else{
            tipoEdit = new EditoraGenerica();
            tipoEdit.calculaMulta(m, nome, dias);
        }
    }
    
}
