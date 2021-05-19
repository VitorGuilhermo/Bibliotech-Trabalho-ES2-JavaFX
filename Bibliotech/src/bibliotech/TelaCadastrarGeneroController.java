package bibliotech;

import bd.dal.GeneroDAL;
import bd.entidades.Genero;
import bd.util.Banco;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
        txCodigo.getScene().getWindow().hide();
    }

    @FXML
    private void evtCadastrar(ActionEvent event) {
        Genero g = new Genero(txNome.getText());
        
        if(txNome.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Campo NOME vazio");
            alert.showAndWait();
        }
        else if(txCodigo.getText().isEmpty()){
            if(!g.gravar()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            g.setCodigo(Integer.parseInt(txCodigo.getText()));
            if(!g.alterar()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        txCodigo.getScene().getWindow().hide();
    }
    
}
