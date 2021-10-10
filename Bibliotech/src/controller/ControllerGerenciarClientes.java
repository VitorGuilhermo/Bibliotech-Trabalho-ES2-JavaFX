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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerGerenciarClientes {
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
    
    
    public String buscar(String txFiltrar, String chave) {
        String filtro = "upper("+chave+") like '%#%'";
        
        filtro = filtro.replace("#", txFiltrar.toUpperCase());
        
        if(txFiltrar.isEmpty())
            return "";
        else
            return filtro;
    }
    
    public void novo() throws IOException {
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
    
    public void alterar(int cod, String nome, String doc, String end, String tel, String sexo, LocalDate data) throws IOException {      
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
    }
    
    public boolean desativar(int cod, String nome, String doc) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Desativação de um Cliente");
        alert.setHeaderText("Confirma desativação?");
        alert.setContentText("Tem certeza que deseja desativar o cliente: " + nome + " com documento:" + doc + " ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Conexao con = Banco.getCon();

            Cliente c = new Cliente();
            c.setCodigo(cod);
            c.desativar(con);

            return true;
        }
        return false;
    }
    
    public boolean excluir(int cod, String nome, String doc) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão de um Cliente");
        alert.setHeaderText("Confirma exclusão?");
        alert.setContentText("Tem certeza que deseja excluir o cliente: " + nome + " com documento:" + doc + " ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Conexao con = Banco.getCon();

            Cliente c = new Cliente();
            c.setCodigo(cod);
            c.excluir(con);

            return true;
        }
        return false;
    }
}
