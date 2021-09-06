package controller;

import bd.entidades.Cliente;
import bd.util.Banco;
import bd.util.Conexao;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerCadastrarCliente {
    public static ControllerCadastrarCliente instancia;
    
    public ControllerCadastrarCliente() {
    }
    public static ControllerCadastrarCliente retorna(){
        if (instancia == null){
            instancia = new ControllerCadastrarCliente();
            return (instancia);
        }
        return null;
    }
    
    public static void cadastrar(TextField txCodigo, TextField txNome, TextField txDocumento, TextField txEndereco, TextField txTelefone, TextField txSexo, DatePicker dpDataNasc) {
        Cliente c = new Cliente(txNome.getText(), txDocumento.getText(), txEndereco.getText(), txTelefone.getText(), txSexo.getText(), dpDataNasc.getValue());
        Conexao con = Banco.getCon();
        if(txNome.getText().isEmpty() || txDocumento.getText().isEmpty() || txEndereco.getText().isEmpty() || txTelefone.getText().isEmpty() || txSexo.getText().isEmpty() || dpDataNasc.getValue()== null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Algum campo está vazio");
            alert.showAndWait();
        }
        else if(dpDataNasc.getValue().isAfter(LocalDate.now())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: não é possível gravar uma data futura");
            alert.showAndWait();
        }
        else if(txCodigo.getText().isEmpty()){
            if(!c.gravar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            c.setCodigo(Integer.parseInt(txCodigo.getText()));
            if(!c.alterar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        txCodigo.getScene().getWindow().hide();
    }
    
    public static void cancelar(Window janela) {
        janela.hide();
    }
}
