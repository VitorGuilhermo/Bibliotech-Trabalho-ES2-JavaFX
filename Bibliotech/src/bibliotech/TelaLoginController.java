package bibliotech;

import bd.entidades.Bibliotecario;
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
    static TelaLoginController instancia;
    @FXML
    private TextField txDocumento;
    @FXML
    private PasswordField txSenha;

    
    public TelaLoginController() {
    }
    public static TelaLoginController retorna(){
        if (instancia == null){
            instancia = new TelaLoginController();
            return (instancia);
        }
        return null;
    }
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
        if(TelaPrincipalController.retorna() != null){
            Bibliotecario bib = new Bibliotecario(txDocumento.getText(), txSenha.getText());
            Bibliotecario aux = bib.verificaLogin();

            if(aux != null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaPrincipal.fxml"));
                Parent root = (Parent) loader.load();
                TelaPrincipalController ctr = loader.getController();
                ctr.setDados(aux);

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
            TelaPrincipalController.instancia = null;
        }
    }
    
}
