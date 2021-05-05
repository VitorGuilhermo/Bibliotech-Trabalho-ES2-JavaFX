package bibliotech;

import bd.dal.AssuntoDAL;
import bd.entidades.Assunto;
import bd.util.Banco;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

    public void setDados(int cod, String nome){
        txCod.setText(""+cod);
        txNome.setText(nome);
    }
    
    @FXML
    private void evtCancelar(ActionEvent event) {
        txCod.getScene().getWindow().hide();
    }

    @FXML
    private void evtCadastrar(ActionEvent event) {
        Assunto a = new Assunto(txNome.getText());
        
        if(txCod.getText().isEmpty()){
            if(!new AssuntoDAL().gravar(a)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            a.setCodigo(Integer.parseInt(txCod.getText()));
            if(!new AssuntoDAL().alterar(a)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        txCod.getScene().getWindow().hide();
    }
    
}
