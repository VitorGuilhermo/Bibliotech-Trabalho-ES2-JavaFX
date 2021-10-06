package controller;

import bd.entidades.Cliente;
import bd.entidades.Emprestimo;
import bd.entidades.Exemplar;
import bd.entidades.Exemplar_Emprestimo;
import bd.entidades.Multa;
import bd.util.Banco;
import bd.util.Conexao;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerEfetuarEmprestimo {
    private static ControllerEfetuarEmprestimo instancia;
    private static List<Exemplar> exemplares = new ArrayList<>();
    private static int qtdeLivrosJaEmprestados;
    
    
    private ControllerEfetuarEmprestimo() {
    }
    public static ControllerEfetuarEmprestimo retorna(){
        if (instancia == null)
            instancia = new ControllerEfetuarEmprestimo();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerEfetuarEmprestimo getInstance() {
        return instancia;
    }
    
    public static void carregaTabela(TableView tabela, String filtro){
        Exemplar e = new Exemplar();
        Conexao con = Banco.getCon();
        List<Exemplar> exememplares = e.buscaExemplares(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(exememplares));
    }
    public static void carregaListaExemplares(ListView<Exemplar> listaExe){
        listaExe.setItems(FXCollections.observableArrayList( exemplares ));
    }
    
    public void novoCliente() throws IOException {
        if(ControllerCadastrarCliente.getInstance() == null && ControllerCadastrarCliente.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarCliente.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Cliente");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            ControllerCadastrarCliente.removeInstancia();
        }
    }
    
    public static void adicionarExe(TableView tabela, ListView<Exemplar> listaExe, Exemplar e){
        if(tabela.getSelectionModel().getSelectedItem() != null){
            if(exemplares.size()+qtdeLivrosJaEmprestados < 5){
               if(!exemplares.contains(e)){
                    exemplares.add(e);
                    carregaListaExemplares(listaExe);
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro: Não é possível adicionar Livros Repetidos ou com o Mesmo Código.");
                    alert.showAndWait();
                } 
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Erro: Um cliente só pode pegar 5 livros por vez!");
                alert.showAndWait();
            }
        }
    }
    
    public void removerExe(ListView<Exemplar> listaExe) {
        if(listaExe.getSelectionModel().getSelectedItem() != null){
            exemplares.remove(listaExe.getSelectionModel().getSelectedItem());
            carregaListaExemplares(listaExe);
        }
    }
    
    public void finalizar(TextField txCodigo) {
        if(!txCodigo.getText().isEmpty()){
            boolean erro;
            if(!exemplares.isEmpty()){
                Conexao con = Banco.getCon();
                Cliente cliente = new Cliente();
                cliente = cliente.buscarCliente(con, Integer.parseInt(txCodigo.getText()));
                
                Emprestimo emp = new Emprestimo(LocalDate.now(), LocalDate.now().plusDays(7), exemplares.size(), cliente, exemplares);
                //grava empréstimo
                erro = emp.gravar(con);
                if(erro){
                    //grava exemplar_emprestimo
                    emp.setCodigo(Banco.getCon().getMaxPK("emprestimo", "emp_cod"));
                    for(Exemplar e : exemplares){
                        boolean flag;                    
                        Exemplar_Emprestimo exempEmp = new Exemplar_Emprestimo(LocalDate.now().plusDays(7), new Multa(), e, emp);
                        flag = exempEmp.gravar(con);
                        if(!flag)
                            erro = false;
                        //altera situacao exemplar
                        e.setSituacao(!e.isSituacao());
                        e.alteraSituacao(con);
                    }
                }
                if(!erro){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro ao gravar o empréstimo!");
                    alert.showAndWait();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Empréstimo efetuado!");
                    alert.showAndWait();
                    
                    txCodigo.getScene().getWindow().hide();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: Não é possível gerar um empréstimo vazio!");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Antes de efetuar o empréstimo insira um cliente!");
            alert.showAndWait();
        }
    }
    
    public void buscarCli(ComboBox<String> cbFiltro, TextField txFiltro, TextField txCodigo, TextField txNome, TextField txDocumento) {
        Cliente cli = new Cliente();
        String sql = "cli_documento='";
        String filtro = txFiltro.getText();
        Conexao con = Banco.getCon();
        
        if(cbFiltro.getSelectionModel().getSelectedItem().equals("Nome"))
            sql = "cli_nome='";
        else if(cbFiltro.getSelectionModel().getSelectedItem().equals("Telefone")){
            sql = "cli_telefone='";
            filtro = new Cliente().formataTelefone(txFiltro.getText());
        }
        else
            filtro = new Cliente().formataCpf(txFiltro.getText());
            
        cli = cli.getCliente(con, sql+filtro+"'");
        if(cli != null){
            txCodigo.setText(""+cli.getCodigo());
            txNome.setText(cli.getNome());
            txDocumento.setText(cli.formataCpf(cli.getDocumento()));
            
            qtdeLivrosJaEmprestados = cli.getQtdeLivros(con);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Cliente inexistente. Verifique os dados!");
            alert.showAndWait();
        }
    }
    
    public static void buscarExe(TableView tabela, TextField txFiltroExe) {
        String filtro = "upper(tit_nome) like '%#%'";
        
        filtro = filtro.replace("#", txFiltroExe.getText().toUpperCase());
        
        if(txFiltroExe.getText().isEmpty())
            carregaTabela(tabela, "");
        else
            carregaTabela(tabela, filtro);
    }
    
    public static void cancelar(Window janela) {
        janela.hide();
    }
}
