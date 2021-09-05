package controller;

import bd.entidades.Editora;
import bd.util.Banco;
import bd.util.Conexao;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerCadastrarEditora {
    public static ControllerCadastrarEditora instancia;
    
    public ControllerCadastrarEditora() {
    }
    public static ControllerCadastrarEditora retorna(){
        if (instancia == null){
            instancia = new ControllerCadastrarEditora();
            return (instancia);
        }
        return null;
    }
    
    public static void cadastrar(TextField txCodigo, TextField txNome, TextField txCnpj) {
        Editora e = new Editora(txNome.getText(), txCnpj.getText());
        Conexao con = Banco.getCon();
        
        if(txNome.getText().isEmpty() || txCnpj.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Campo NOME ou CNPJ vazio");
            alert.showAndWait();
        }
        else if(txCodigo.getText().isEmpty()){
            if(!e.gravar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            e.setCodigo(Integer.parseInt(txCodigo.getText()));
            if(!e.alterar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        cancelar( txCodigo.getScene().getWindow() );
    }
    
    public static void cancelar(Window janela) {
        janela.hide();
    }
}
