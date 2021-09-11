package bibliotech;

import bd.util.Banco;
import controller.ControllerLogin;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Vitor Guilhermo
 */
public class Bibliotech extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        if(ControllerLogin.getInstance() == null && ControllerLogin.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("TelaLogin.fxml"));

            Scene scene = new Scene(root);
            stage.setTitle("Login");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            ControllerLogin.removeInstancia();
        }
    }

    public static void main(String[] args) {
        if(!Banco.conectarBanco()){
            JOptionPane.showMessageDialog(null, "Erro ao conectar o banco\n"+Banco.getCon().getMensagemErro());
            Platform.exit();    //finaliza tudo que tem na memória
        }
        else
            launch(args);
    }
    
}
