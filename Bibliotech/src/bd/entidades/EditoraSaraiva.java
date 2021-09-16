package bd.entidades;

/**
 * @author Vitor Guilhermo
 */
public class EditoraSaraiva implements Strategy {
    private Strategy tipoEdit;
    
    @Override
    public void calculaMulta(Multa m, String nome, int dias) {
        if(nome.equals("Saraiva")){
            m.setValor(dias * 0.75);
        }
        else{
            tipoEdit = new EditoraPearson();
            tipoEdit.calculaMulta(m, nome, dias);
        }
    }
    
}
