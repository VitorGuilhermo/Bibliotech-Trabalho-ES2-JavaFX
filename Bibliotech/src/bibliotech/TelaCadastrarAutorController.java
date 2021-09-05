package bibliotech;

import bd.entidades.Autor;
import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerCadastrarAutor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class TelaCadastrarAutorController implements Initializable {
    @FXML
    private TextField txCodigo;
    @FXML
    private TextField txNome;

    public void setDados(int cod, String nome){
        txCodigo.setText(""+cod);
        txNome.setText(nome);
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerCadastrarAutor.cancelar( txCodigo.getScene().getWindow() );
    }

    @FXML
    private void evtCadastrar(ActionEvent event) {
        ControllerCadastrarAutor.cadastrar(txCodigo, txNome);
    }
    
}
