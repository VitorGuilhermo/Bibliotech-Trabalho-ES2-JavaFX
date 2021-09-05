package controller;

import bd.entidades.Assunto;
import bd.util.Banco;
import bd.util.Conexao;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerCadastrarAssunto {
    public static ControllerCadastrarAssunto instancia;
    
    
    public ControllerCadastrarAssunto() {
    }
    public static ControllerCadastrarAssunto retorna(){
        if (instancia == null){
            instancia = new ControllerCadastrarAssunto();
            return (instancia);
        }
        return null;
    }
    
    public static void cadastrar(TextField txCod, TextField txNome) {
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
        cancelar( txCod.getScene().getWindow() );
    }
    
    public static void cancelar(Window janela) {
        janela.hide();
    }
}
