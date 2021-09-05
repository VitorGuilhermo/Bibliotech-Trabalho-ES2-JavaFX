package controller;

import bd.entidades.Autor;
import bd.util.Banco;
import bd.util.Conexao;
import bibliotech.TelaCadastrarAutorController;
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
public class ControllerGerenciarAutor {
    public static ControllerGerenciarAutor instancia;
    
    public ControllerGerenciarAutor() {
    }
    public static ControllerGerenciarAutor retorna(){
        if (instancia == null){
            instancia = new ControllerGerenciarAutor();
            return (instancia);
        }
        return null;
    }
    
    
    public static void carregarTabela(TableView tabela, String filtro){
        Conexao con = Banco.getCon();
        Autor a = new Autor();
        
        List<Autor> autores = a.buscar(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(autores));
    }
    
    public void novo(TableView tabela) throws IOException {
       if(ControllerCadastrarAutor.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarAutor.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Autor");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            ControllerCadastrarAutor.instancia = null;
            carregarTabela(tabela, "");
        } 
    }
    
    public void alterar(TableView tabela, Autor autor) throws IOException {
        if(ControllerCadastrarAutor.retorna() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaCadastrarAutor.fxml"));
            Parent root = (Parent) loader.load();
            TelaCadastrarAutorController ctr = loader.getController();
            ctr.setDados(autor.getCodigo(), autor.getNome());

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Alterar Autor");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            ControllerCadastrarAutor.instancia = null;
            carregarTabela(tabela, "");
        }
    }
    
    public static void buscar(TableView tabela, TextField txFiltro) {
        String filtro = "upper(aut_nome) like '%#%'";
        
        filtro = filtro.replace("#", txFiltro.getText().toUpperCase());
        
        if(txFiltro.getText().isEmpty())
            carregarTabela(tabela, "");
        else
            carregarTabela(tabela, filtro);
    }
    
    public static void excluir(TableView tabela, Autor a) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão de um Autor");
        alert.setHeaderText("Confirma exclusão?");
        alert.setContentText("Tem certeza que deseja excluir o/a autor(a): "+a.getNome()+" ?");
        Optional<ButtonType> result =  alert.showAndWait();
        
        if(result.get() == ButtonType.OK){
            Conexao con = Banco.getCon();
            a.excluir(con);
            carregarTabela(tabela, "");
        }
    }
    
    public static void cancelar(Window janela) {
        janela.hide();
    }
}
