package controller;

import bd.entidades.Cliente;
import bd.util.Banco;
import bd.util.Conexao;
import bibliotech.TelaCadastrarClienteController;
import java.io.IOException;
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
    public static ControllerGerenciarClientes instancia;
    
    public ControllerGerenciarClientes() {
    }
    public static ControllerGerenciarClientes retorna(){
        if (instancia == null){
            instancia = new ControllerGerenciarClientes();
            return (instancia);
        }
        return null;
    }
    
    public static void carregarTabela(TableView tabela, String filtro){
        Cliente c = new Cliente();
        Conexao con = Banco.getCon();
        List<Cliente> clientes = c.buscar(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(clientes));
    }
    
    public void novo(TableView tabela) throws IOException {
        if(ControllerCadastrarCliente.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarCliente.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Cliente");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            ControllerCadastrarCliente.instancia = null;
            carregarTabela(tabela, "");
        }
    }
    
    public void alterar(TableView tabela, Cliente c) throws IOException {
        if(ControllerCadastrarCliente.retorna() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaCadastrarCliente.fxml"));
            Parent root = (Parent) loader.load();
            TelaCadastrarClienteController ctr = loader.getController();
            ctr.setDados(c.getCodigo(), c.getNome(),
                    c.getDocumento(), c.getEndereco(),
                    c.getTelefone(), c.getSexo(),
                    c.getDataNasc()
                    );

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Alterar Cliente");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            ControllerCadastrarCliente.instancia = null;
            carregarTabela(tabela, "");
        }
    }
    
    public static void buscar(TableView tabela, TextField txFiltrar) {
        String filtro = "upper(cli_nome) like '%#%'";
        
        filtro = filtro.replace("#", txFiltrar.getText().toUpperCase());
        
        if(txFiltrar.getText().isEmpty())
            carregarTabela(tabela, "");
        else
            carregarTabela(tabela, filtro);
    }
    
    public static void desativar(TableView tabela, Cliente c) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Desativação de um Cliente");
            alert.setHeaderText("Confirma desativação?");
            alert.setContentText("Tem certeza que deseja desativar o cliente: "+c.getNome()+" com documento:"+c.getDocumento()+" ?");
            Optional<ButtonType> result =  alert.showAndWait();
            
            if(result.get() == ButtonType.OK){
                Conexao con = Banco.getCon();         
                c.desativar(con);
                carregarTabela(tabela, "");
            }
        }
    }
    
    public static void excluir(TableView tabela, Cliente c) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exclusão de um Cliente");
            alert.setHeaderText("Confirma exclusão?");
            alert.setContentText("Tem certeza que deseja excluir o cliente: "+c.getNome()+" com documento:"+c.getDocumento()+" ?");
            Optional<ButtonType> result =  alert.showAndWait();

            if(result.get() == ButtonType.OK){
                Conexao con = Banco.getCon();
                c.excluir(con);
                carregarTabela(tabela, "");
            }
        }
    }
    
    public static void cancelar(Window janela) {
        janela.hide();
    }
}
