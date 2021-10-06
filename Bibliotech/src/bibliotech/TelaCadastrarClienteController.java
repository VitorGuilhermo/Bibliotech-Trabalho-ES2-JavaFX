package bibliotech;

import controller.ControllerCadastrarCliente;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class TelaCadastrarClienteController implements Initializable {
    @FXML
    private TextField txCodigo;
    @FXML
    private TextField txNome;
    @FXML
    private TextField txDocumento;
    @FXML
    private TextField txEndereco;
    @FXML
    private TextField txTelefone;
    @FXML
    private TextField txSexo;
    @FXML
    private DatePicker dpDataNasc;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setDados(int cod, String nome, String documento, String endereco, String telefone, String sexo, LocalDate data){
        txCodigo.setText(""+cod);
        txNome.setText(nome);
        txDocumento.setText(documento);
        txEndereco.setText(endereco);
        txTelefone.setText(telefone);
        txSexo.setText(sexo);
        dpDataNasc.setValue(data);
    }
    
    @FXML
    private void evtCancelar(ActionEvent event) {
        txCodigo.getScene().getWindow().hide();
    }

    @FXML
    private void evtCadastrar(ActionEvent event) {
        ControllerCadastrarCliente c = ControllerCadastrarCliente.retorna();
        c.cadastrar(txCodigo.getText(), txNome.getText(), txDocumento.getText(), txEndereco.getText(), txTelefone.getText(), txSexo.getText(), dpDataNasc.getValue());
        
        evtCancelar(event);
    }
    
}
