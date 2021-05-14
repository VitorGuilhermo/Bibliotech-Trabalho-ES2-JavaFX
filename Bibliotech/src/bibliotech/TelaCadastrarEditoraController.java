package bibliotech;

import bd.dal.EditoraDAL;
import bd.entidades.Editora;
import bd.util.Banco;
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
        txCodigo.getScene().getWindow().hide();
    }

    @FXML
    private void evtCadastrar(ActionEvent event) {
        Editora e = new Editora(txNome.getText(), txCnpj.getText());
        
        if(txCodigo.getText().isEmpty()){
            if(!e.gravar()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            e.setCodigo(Integer.parseInt(txCodigo.getText()));
            if(!e.alterar()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        txCodigo.getScene().getWindow().hide();
    }
    
}
