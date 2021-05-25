package bibliotech;

import bd.entidades.Cliente;
import bd.util.Banco;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class TelaCadastrarClienteController implements Initializable {

    static TelaCadastrarClienteController instancia;
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


    public TelaCadastrarClienteController() {
    }
    public static TelaCadastrarClienteController retorna(){
        if (instancia == null){
            instancia = new TelaCadastrarClienteController();
            return (instancia);
        }
        return null;
    }
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
        Cliente c = new Cliente(txNome.getText(), txDocumento.getText(), txEndereco.getText(), txTelefone.getText(), txSexo.getText(), dpDataNasc.getValue());
        
        if(txNome.getText().isEmpty() || txDocumento.getText().isEmpty() || txEndereco.getText().isEmpty() || txTelefone.getText().isEmpty() || txSexo.getText().isEmpty() || dpDataNasc.getValue()== null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Algum campo está vazio");
            alert.showAndWait();
        }
        else if(txCodigo.getText().isEmpty()){
            if(!c.gravar()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            c.setCodigo(Integer.parseInt(txCodigo.getText()));
            if(!c.alterar()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        txCodigo.getScene().getWindow().hide();
    }
    
}
