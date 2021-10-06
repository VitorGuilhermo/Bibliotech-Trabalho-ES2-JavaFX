package controller;

import bd.entidades.Genero;
import bd.util.Banco;
import bd.util.Conexao;
import bibliotech.TelaCadastrarGeneroController;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerGerenciarGenero extends ControllerGerenciar {
    private static ControllerGerenciarGenero instancia;
    
    private ControllerGerenciarGenero() {
    }
    public static ControllerGerenciarGenero retorna(){
        if (instancia == null)
            instancia = new ControllerGerenciarGenero();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerGerenciarGenero getInstance() {
        return instancia;
    }
    
    public void carregarTabela(TableView tabela, String filtro){
        Genero g = new Genero();
        Conexao con = Banco.getCon();
        List<Genero> generos = g.buscar(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(generos));
    }
    
    public void novo(TableView tabela) throws IOException {
        if(ControllerCadastrarGenero.getInstance() == null && ControllerCadastrarGenero.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarGenero.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Gênero");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            ControllerCadastrarGenero.removeInstancia();
            carregarTabela(tabela, "");
        }
    }
    
    public void alterar(TableView tabela, int cod, String nome) throws IOException {
        if(ControllerCadastrarGenero.getInstance() == null && ControllerCadastrarGenero.retorna() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaCadastrarGenero.fxml"));
            Parent root = (Parent) loader.load();
            TelaCadastrarGeneroController ctr = loader.getController();
            ctr.setDados(cod, nome);

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Alterar Gênero");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            ControllerCadastrarGenero.removeInstancia();
            carregarTabela(tabela, "");
        }
    }
    
    public void excluir(TableView tabela, int cod, String nome) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão de um Gênero");
        alert.setHeaderText("Confirma exclusão?");
        alert.setContentText("Tem certeza que deseja excluir o gênero: "+nome+" ?");
        Optional<ButtonType> result =  alert.showAndWait();
        
        if(result.get() == ButtonType.OK){
            Conexao con = Banco.getCon();
            
            Genero g = new Genero();
            g.setCodigo(cod);
            g.excluir(con);
            
            carregarTabela(tabela, "");
        }
    }
}
