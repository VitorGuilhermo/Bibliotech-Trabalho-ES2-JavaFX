package bibliotech;

import controller.ControllerHomeCliente;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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

    @FXML
    private void evtReservarLivro(ActionEvent event) throws IOException {
        ControllerHomeCliente.retorna().reservarLivro();
    }
}
