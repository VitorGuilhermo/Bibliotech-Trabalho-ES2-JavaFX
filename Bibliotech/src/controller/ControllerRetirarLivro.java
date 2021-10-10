package controller;

import bd.entidades.Bibliotecario;
import bd.entidades.Titulo;
import bibliotech.TelaRetirarLivroContController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerRetirarLivro {
    private static ControllerRetirarLivro instancia;
    public static Bibliotecario bib;
    
    private ControllerRetirarLivro() {
    }
    public static ControllerRetirarLivro retorna(){
        if (instancia == null)
            instancia = new ControllerRetirarLivro();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerRetirarLivro getInstance() {
        return instancia;
    }

    
    public void confirmar(Titulo tit) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaRetirarLivroCont.fxml"));
        Parent root = (Parent) loader.load();
        TelaRetirarLivroContController ctr = loader.getController();

        ctr.setDados(tit);

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Excolha de exemplares para serem retirados");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();

        ControllerRetirarLivroCont.removeInstancia();
    }
}
