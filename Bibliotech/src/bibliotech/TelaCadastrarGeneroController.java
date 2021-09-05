package bibliotech;

import controller.ControllerCadastrarGenero;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class TelaCadastrarGeneroController implements Initializable {
    @FXML
    private TextField txCodigo;
    @FXML
    private TextField txNome;

     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setDados(int cod, String nome){
        txCodigo.setText(""+cod);
        txNome.setText(nome);
    }
    
    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerCadastrarGenero.cancelar( txCodigo.getScene().getWindow() );
    }

    @FXML
    private void evtCadastrar(ActionEvent event) {
        ControllerCadastrarGenero.cadastrar(txNome, txCodigo);
    }
    
}
