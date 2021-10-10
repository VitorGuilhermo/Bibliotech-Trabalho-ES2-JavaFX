package controller;

import bd.entidades.Cliente;
import bd.entidades.Emprestimo;
import bd.entidades.Exemplar_Emprestimo;
import bd.entidades.Multa;
import bd.util.Banco;
import bd.util.Conexao;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerGerarMulta {
    private static ControllerGerarMulta instancia;
    
    
    private ControllerGerarMulta() {
    }
    public static ControllerGerarMulta retorna(){
        if (instancia == null)
            instancia = new ControllerGerarMulta();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerGerarMulta getInstance() {
        return instancia;
    }
    
    
    public String buscar(String cbFiltro, String filtro){
        Cliente cli = new Cliente();
        String sql="";
        Conexao con = Banco.getCon();
        
        if(!filtro.isEmpty()){
            if(cbFiltro.equals("Código Exemplar")){
                sql = "exe_cod="+filtro;
            }
            else if(cbFiltro.equals("Nome Cliente")){
                sql = "cli_nome='";
                cli = cli.getCliente(con, sql+filtro+"'");
                sql = "";

                Emprestimo e = new Emprestimo();
                e.setCliente(cli);
                List<Emprestimo> emprestimos = e.buscarPorCodigoCliente(con);
                if(emprestimos != null){
                    int i;
                    for( i=0; i<emprestimos.size(); i++ ){
                        if(i==emprestimos.size()-1)
                            sql += "emp_cod="+emprestimos.get(i).getCodigo();
                        else
                            sql += "emp_cod="+emprestimos.get(i).getCodigo()+" or ";
                    }
                }
            }
        }
        if(filtro.isEmpty())
            return "";
        else
            return sql; 
    }
    
    public boolean gerarMulta(int cod, String tipoEditora, LocalDate dataEmp, LocalDate dataDevolucao){
        Multa m = new Multa();
        m.calculaMulta(tipoEditora, dataEmp, dataDevolucao);

        Exemplar_Emprestimo ee = new Exemplar_Emprestimo();
        ee.setCodigo(cod);
        ee.setMulta( m );
        
        Conexao con = Banco.getCon();
        boolean sucesso = ee.alteraMulta(con);
        if(sucesso){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Multa gerada com sucesso!");
            alert.showAndWait();
            
            return true;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: não foi possível gerar a multa no momento...");
            alert.showAndWait();
        }
        return false;
    }
}
