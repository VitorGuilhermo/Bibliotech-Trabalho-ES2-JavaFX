package bibliotech;

import bd.entidades.Assunto;
import bd.util.Banco;
import bd.util.Conexao;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class TelaCadastrarAssuntoController implements Initializable {
    
    static TelaCadastrarAssuntoController instancia;
    @FXML
    private TextField txCod;
    @FXML
    private TextField txNome;

    
    public TelaCadastrarAssuntoController() {
    }
    public static TelaCadastrarAssuntoController retorna(){
        if (instancia == null){
            instancia = new TelaCadastrarAssuntoController();
            return (instancia);
        }
        return null;
    }
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
        Conexao con = Banco.getCon();
        Assunto a = new Assunto(txNome.getText());
        
        if(txNome.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Campo NOME vazio");
            alert.showAndWait();
        }
        else if(txCod.getText().isEmpty()){
            if(!a.gravar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            a.setCodigo(Integer.parseInt(txCod.getText()));
            if(!a.alterar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        txCod.getScene().getWindow().hide();
    }
    
}
