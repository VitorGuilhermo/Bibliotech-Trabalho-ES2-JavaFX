package bd.entidades;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author Vitor Guilhermo
 */
public class Multa {
    private int codigo;
    private double valor;

    
    public Multa() {
        this(0, 0.);
    }
    public Multa(double valor) {
        this(0, valor);
    }  
    public Multa(int codigo, double valor) {
        this.codigo = codigo;
        this.valor = valor;
    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return ""+valor;
    }
    
    public void calculaValorMulta(String tipoEditora, LocalDate dataInicial, LocalDate dataFinal) {
        Strategy tipoEdit;
        
        if(tipoEditora.equals("Saraiva")){
             tipoEdit = new EditoraSaraiva();
        }
        else if(tipoEditora.equals("Pearson")){
            tipoEdit = new EditoraPearson();
        }
        else{
            tipoEdit = new EditoraGenerica();
        }
        
        Editora editora = new Editora();
        editora.setTipoEditora(tipoEdit);
        
        //calcula quantidade de dias atrasados
        int dias = (int) dataInicial.until(dataFinal, ChronoUnit.DAYS);
        
        this.valor = editora.calculaMulta(dias);
    }
}
