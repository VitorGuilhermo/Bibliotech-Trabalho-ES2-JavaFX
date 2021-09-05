package controller;

import bd.entidades.Editora;
import bd.util.Banco;
import bd.util.Conexao;
import bibliotech.TelaCadastrarEditoraController;
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
public class ControllerGerenciarEditora {
    public static ControllerGerenciarEditora instancia;
    
    public ControllerGerenciarEditora() {
    }
    public static ControllerGerenciarEditora retorna(){
        if (instancia == null){
            instancia = new ControllerGerenciarEditora();
            return (instancia);
        }
        return null;
    }
    
    
    public static void carregarTabela(TableView tabela, String filtro){
        Editora e = new Editora();
        Conexao con = Banco.getCon();
        List<Editora> editoras = e.buscar(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(editoras));
    }
    
    public void novo(TableView tabela) throws IOException {
        if(ControllerCadastrarEditora.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarEditora.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Editora");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            ControllerCadastrarEditora.instancia = null;
            carregarTabela(tabela, "");
        }
    }
    
    public void alterar(TableView tabela, Editora e) throws IOException {
        if(ControllerCadastrarEditora.retorna() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaCadastrarEditora.fxml"));
            Parent root = (Parent) loader.load();
            TelaCadastrarEditoraController ctr = loader.getController();
            ctr.setDados(e.getCodigo(), e.getNome(), e.getCnpj());

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Alterar Editora");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            ControllerCadastrarEditora.instancia = null;
            carregarTabela(tabela, "");
        }
    }
    
    public static void buscar(TableView tabela, TextField txFiltro) {
        String filtro = "upper(edt_nome) like '%#%'";
        
        filtro = filtro.replace("#", txFiltro.getText().toUpperCase());
        
        if(txFiltro.getText().isEmpty())
            carregarTabela(tabela, "");
        else
            carregarTabela(tabela, filtro);
    }
    
    public static void excluir(TableView tabela, Editora e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão de uma Editora");
        alert.setHeaderText("Confirma exclusão?");
        alert.setContentText("Tem certeza que deseja excluir a editora: "+e.getNome()+" ?");
        Optional<ButtonType> result =  alert.showAndWait();
        
        if(result.get() == ButtonType.OK){
            Conexao con = Banco.getCon();
            e.excluir(con);
            carregarTabela(tabela, "");
        }
    }
    
    public static void cancelar(Window janela) {
        janela.hide();
    }
}
