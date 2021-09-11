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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerGerenciarGenero {
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
    
    public static void carregarTabela(TableView tabela, String filtro){
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
    
    public void alterar(TableView tabela, Genero g) throws IOException {
        if(ControllerCadastrarGenero.getInstance() == null && ControllerCadastrarGenero.retorna() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaCadastrarGenero.fxml"));
            Parent root = (Parent) loader.load();
            TelaCadastrarGeneroController ctr = loader.getController();
            ctr.setDados(g.getCodigo(), g.getNome());

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
    
    public static void buscar(TableView tabela, TextField txFiltro) {
        String filtro = "upper(gen_nome) like '%#%'";
        
        filtro = filtro.replace("#", txFiltro.getText().toUpperCase());
        
        if(txFiltro.getText().isEmpty())
            carregarTabela(tabela, "");
        else
            carregarTabela(tabela, filtro);
    }
    
    public static void excluir(TableView tabela, Genero g) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão de um Gênero");
        alert.setHeaderText("Confirma exclusão?");
        alert.setContentText("Tem certeza que deseja excluir o gênero: "+g.getNome()+" ?");
        Optional<ButtonType> result =  alert.showAndWait();
        
        if(result.get() == ButtonType.OK){
            Conexao con = Banco.getCon();
            g.excluir(con);
            carregarTabela(tabela, "");
        }
    }
    
    public static void cancelar(Window janela) {
        janela.hide();
    }
}
