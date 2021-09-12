package controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Vitor Guilhermo
 */
public class ControllerHomeCliente {
    private static ControllerHomeCliente instancia;
    
    
    private ControllerHomeCliente() {
    }
    public static ControllerHomeCliente retorna(){
        if (instancia == null)
            instancia = new ControllerHomeCliente();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerHomeCliente getInstance() {
        return instancia;
    }
    
    
    public void reservarLivro() throws IOException {
        if(ControllerReservarLivro.getInstance() == null && ControllerReservarLivro.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaReservarLivro.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Reservar Livro");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            ControllerReservarLivro.removeInstancia();
        }
    }
}
