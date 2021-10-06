package controller;

import bd.entidades.Cliente;
import bd.util.Banco;
import bd.util.Conexao;
import bibliotech.TelaCadastrarClienteController;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerGerenciarClientes extends ControllerGerenciar {
    private static ControllerGerenciarClientes instancia;
    
    private ControllerGerenciarClientes() {
    }
    public static ControllerGerenciarClientes retorna(){
        if (instancia == null)
            instancia = new ControllerGerenciarClientes();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerGerenciarClientes getInstance() {
        return instancia;
    }
    
    public void carregarTabela(TableView tabela, String filtro){
        Cliente c = new Cliente();
        Conexao con = Banco.getCon();
        List<Cliente> clientes = c.buscar(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(clientes));
    }
    
    public void novo(TableView tabela) throws IOException {
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
            carregarTabela(tabela, "");
        }
    }
    
    public void alterar(TableView tabela, int cod, String nome, String doc, String end, String tel, String sexo, LocalDate data) throws IOException {
        if(ControllerCadastrarCliente.getInstance() == null && ControllerCadastrarCliente.retorna() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaCadastrarCliente.fxml"));
            Parent root = (Parent) loader.load();
            TelaCadastrarClienteController ctr = loader.getController();
            ctr.setDados(cod, nome, doc, end, tel, sexo, data);

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Alterar Cliente");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            ControllerCadastrarCliente.removeInstancia();
            carregarTabela(tabela, "");
        }
    }
    
    public void desativar(TableView tabela, int cod, String nome, String doc) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Desativação de um Cliente");
            alert.setHeaderText("Confirma desativação?");
            alert.setContentText("Tem certeza que deseja desativar o cliente: "+nome+" com documento:"+doc+" ?");
            Optional<ButtonType> result =  alert.showAndWait();
            
            if(result.get() == ButtonType.OK){
                Conexao con = Banco.getCon(); 
                
                Cliente c = new Cliente();
                c.setCodigo(cod);
                c.desativar(con);
                
                carregarTabela(tabela, "");
            }
        }
    }
    
    public void excluir(TableView tabela, int cod, String nome, String doc) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exclusão de um Cliente");
            alert.setHeaderText("Confirma exclusão?");
            alert.setContentText("Tem certeza que deseja excluir o cliente: "+nome+" com documento:"+doc+" ?");
            Optional<ButtonType> result =  alert.showAndWait();

            if(result.get() == ButtonType.OK){
                Conexao con = Banco.getCon();
                
                Cliente c = new Cliente();
                c.setCodigo(cod);
                c.excluir(con);
                
                carregarTabela(tabela, "");
            }
        }
    }
}
