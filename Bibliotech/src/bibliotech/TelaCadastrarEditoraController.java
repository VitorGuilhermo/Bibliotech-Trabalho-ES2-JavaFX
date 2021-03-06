package bibliotech;

import controller.ControllerCadastrarEditora;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;


public class TelaCadastrarEditoraController implements Initializable {
    @FXML
    private TextField txCodigo;
    @FXML
    private TextField txNome;
    @FXML
    private TextField txCnpj;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setDados(int cod, String nome, String cnpj){
        txCodigo.setText(""+cod);
        txNome.setText(nome);
        txCnpj.setText(cnpj);
    }
    
    @FXML
    private void evtCancelar(ActionEvent event) {
        txCodigo.getScene().getWindow().hide();
    }

    @FXML
    private void evtCadastrar(ActionEvent event) {
        ControllerCadastrarEditora c = ControllerCadastrarEditora.retorna();
        c.cadastrar(txCodigo.getText(), txNome.getText(), txCnpj.getText());
        
        evtCancelar(event);
    }
    
}
