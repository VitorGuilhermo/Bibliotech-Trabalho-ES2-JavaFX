package bibliotech;

import controller.ControllerCadastrarAssunto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class TelaCadastrarAssuntoController implements Initializable {

    @FXML
    private TextField txCod;
    @FXML
    private TextField txNome;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setDados(int cod, String nome) {
        txCod.setText("" + cod);
        txNome.setText(nome);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerCadastrarAssunto.cancelar(txCod.getScene().getWindow());
    }

    @FXML
    private void evtCadastrar(ActionEvent event) {
        ControllerCadastrarAssunto.cadastrar(txCod, txNome);
    }

}
