package bibliotech;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Vitor Guilhermo
 */
public class TelaPrincipalController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void evtManipularTitulo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaGerenciarTitulo.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Gerenciar Título");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();
    }

    @FXML
    private void evtManipularAssunto(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GerenciarAssunto.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Gerenciar Assunto");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();
    }

    @FXML
    private void evtManipularEditora(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaGerenciarEditora.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Gerenciar Editora");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();
    }

    @FXML
    private void evtManipularAutor(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaGerenciarAutor.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Gerenciar Autor");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();
    }

    @FXML
    private void evtManipularGenero(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaGerenciarGenero.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Gerenciar Gênero");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();
    }

    @FXML
    private void evtManipularCliente(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaGerenciarCliente.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Gerenciar Cliente");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();
    }
    
}
