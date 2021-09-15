package controller;

import bd.entidades.Cliente;
import bd.entidades.Editora;
import bd.entidades.EditoraGenerica;
import bd.entidades.EditoraPearson;
import bd.entidades.EditoraSaraiva;
import bd.entidades.Emprestimo;
import bd.entidades.Exemplar_Emprestimo;
import bd.entidades.Multa;
import bd.entidades.Strategy;
import bd.entidades.Titulo;
import bd.util.Banco;
import bd.util.Conexao;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Window;

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
    
    public static void carregaTabela(TableView tabela, String filtro){
        Exemplar_Emprestimo ee = new Exemplar_Emprestimo();
        Conexao con = Banco.getCon();
        List<Exemplar_Emprestimo> exememplares_emp = ee.buscar(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(exememplares_emp));
    }
    
    public static void buscar(TableView tabela, ComboBox<String> cbFiltro, TextField txFiltro){
        Exemplar_Emprestimo ee;
        Cliente cli = new Cliente();
        String sql="";
        String filtro = txFiltro.getText();
        Conexao con = Banco.getCon();
        
        if(!filtro.isEmpty()){
            if(cbFiltro.getSelectionModel().getSelectedItem().equals("Código Exemplar")){
                sql = "exe_cod="+filtro;
            }
            else if(cbFiltro.getSelectionModel().getSelectedItem().equals("Nome Cliente")){
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
        
        if(txFiltro.getText().isEmpty())
            carregaTabela(tabela, "");
        else
            carregaTabela(tabela, sql);  
    }
    
    public static void gerarMulta(TableView<Exemplar_Emprestimo> tabela){
        Titulo tit = tabela.getSelectionModel().getSelectedItem().getExemplar().getTitulo();
        String tipoEditora = tit.getEditora().getNome();
        
        Multa m = new Multa();
        m.calculaValorMulta(tipoEditora, tabela.getSelectionModel().getSelectedItem().getEmprestimo().getData(), tabela.getSelectionModel().getSelectedItem().getEmprestimo().getDataDevolucaoP());

        Exemplar_Emprestimo ee = new Exemplar_Emprestimo();
        ee.setCodigo(tabela.getSelectionModel().getSelectedItem().getCodigo());
        ee.setMulta( m );
        
        Conexao con = Banco.getCon();
        boolean sucesso = ee.alteraMulta(con);
        if(sucesso){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Multa gerada com sucesso!");
            alert.showAndWait();
            
            carregaTabela(tabela, "");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: não foi possível gerar a multa no momento...");
            alert.showAndWait();
        }
        
    }
    
    public static void cancelar(Window janela) {
        janela.hide();
    }
}
