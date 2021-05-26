package bibliotech;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class TelaPrincipalClienteController implements Initializable {

    @FXML
    private Label txUsuário;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setDados(String nome){
        txUsuário.setText(nome);
    }
}
