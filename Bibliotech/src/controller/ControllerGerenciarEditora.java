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
    private static ControllerGerenciarEditora instancia;
    
    private ControllerGerenciarEditora() {
    }
    public static ControllerGerenciarEditora retorna(){
        if (instancia == null)
            instancia = new ControllerGerenciarEditora();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerGerenciarEditora getInstance() {
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
        Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarEditora.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Editora");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();

        ControllerCadastrarEditora.removeInstancia();
    }
    
    public void alterar(int cod, String nome, String cnpj) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaCadastrarEditora.fxml"));
        Parent root = (Parent) loader.load();
        TelaCadastrarEditoraController ctr = loader.getController();
        ctr.setDados(cod, nome, cnpj);

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Alterar Editora");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();

        ControllerCadastrarEditora.removeInstancia();
    }
    
    public boolean excluir(int cod, String nome) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão de uma Editora");
        alert.setHeaderText("Confirma exclusão?");
        alert.setContentText("Tem certeza que deseja excluir a editora: "+nome+" ?");
        Optional<ButtonType> result =  alert.showAndWait();
        
        if(result.get() == ButtonType.OK){
            Conexao con = Banco.getCon();
            
            Editora e = new Editora();
            e.setCodigo(cod);
            e.excluir(con);
            
            return true;
        }
        return false;
    }
}
