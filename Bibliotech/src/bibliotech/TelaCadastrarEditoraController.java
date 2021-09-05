package bibliotech;

import bd.entidades.Editora;
import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerCadastrarEditora;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
        ControllerCadastrarEditora.cancelar( txCodigo.getScene().getWindow() );
    }

    @FXML
    private void evtCadastrar(ActionEvent event) {
        ControllerCadastrarEditora.cadastrar(txCodigo, txNome, txCnpj);
    }
    
}
