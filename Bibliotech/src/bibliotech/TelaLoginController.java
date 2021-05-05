package bibliotech;

import bd.util.Banco;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Vitor Guilhermo
 */
public class TelaLoginController implements Initializable {

    @FXML
    private TextField txDocumento;
    @FXML
    private PasswordField txSenha;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void evtSair(ActionEvent event) {
        txDocumento.getScene().getWindow().hide();
    }

    @FXML
    private void evtEntrar(ActionEvent event) throws IOException {
        if(txDocumento.getText().equals("444.444.444-44") && txSenha.getText().equals("1Sist2Biblio3Tech4")){
            Parent root = FXMLLoader.load(getClass().getResource("TelaPrincipal.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Bibliotech");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.setScene(scene);
            stage.setOnCloseRequest(e->{Banco.getCon().desconectar();});
            stage.show();

            txDocumento.getScene().getWindow().hide();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Falha");
            alert.setHeaderText("Erro no documento ou senha...");
            alert.setContentText("Tente novamente!");
            Optional<ButtonType> result =  alert.showAndWait();

            txDocumento.clear();
            txSenha.clear();
        }
    }
    
}
