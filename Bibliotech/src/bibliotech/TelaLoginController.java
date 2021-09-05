package bibliotech;

import controller.ControllerLogin;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


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
        ControllerLogin c = new ControllerLogin();
        c.sair( txDocumento.getScene().getWindow() );
    }

    @FXML
    private void evtEntrar(ActionEvent event) throws IOException {
        ControllerLogin c = new ControllerLogin();
        c.entrar( txDocumento, txSenha, txDocumento.getText(), txSenha.getText()) ;
    }
    
}
