package controller;

import bd.entidades.Autor;
import bd.util.Banco;
import bd.util.Conexao;
import bibliotech.TelaCadastrarAutorController;
import java.io.IOException;
import java.util.Optional;
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
public class ControllerGerenciarAutor {

    private static ControllerGerenciarAutor instancia;

    private ControllerGerenciarAutor() {
    }

    public static ControllerGerenciarAutor retorna() {
        if (instancia == null) {
            instancia = new ControllerGerenciarAutor();
        }
        return instancia;
    }

    public static void removeInstancia() {
        instancia = null;
    }

    public static ControllerGerenciarAutor getInstance() {
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
        Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarAutor.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Autor");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();

        ControllerCadastrarAutor.removeInstancia();
    }

    public void alterar(int cod, String nome) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaCadastrarAutor.fxml"));
        Parent root = (Parent) loader.load();
        TelaCadastrarAutorController ctr = loader.getController();
        ctr.setDados(cod, nome);

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Alterar Autor");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();

        ControllerCadastrarAutor.removeInstancia();
    }

    public boolean excluir(TableView tabela, int cod, String nome) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão de um Autor");
        alert.setHeaderText("Confirma exclusão?");
        alert.setContentText("Tem certeza que deseja excluir o/a autor(a): " + nome + " ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Conexao con = Banco.getCon();

            Autor a = new Autor();
            a.setCodigo(cod);
            a.excluir(con);

            return true;
        }
        return false;
    }
}
