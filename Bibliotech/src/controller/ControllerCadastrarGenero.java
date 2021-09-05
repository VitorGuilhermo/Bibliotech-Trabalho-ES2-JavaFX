package controller;

import bd.entidades.Genero;
import bd.util.Banco;
import bd.util.Conexao;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerCadastrarGenero {
    public static ControllerCadastrarGenero instancia;
    
    public ControllerCadastrarGenero() {
    }
    public static ControllerCadastrarGenero retorna(){
        if (instancia == null){
            instancia = new ControllerCadastrarGenero();
            return (instancia);
        }
        return null;
    }
    
    public static void cadastrar(TextField txNome, TextField txCodigo) {
        Genero g = new Genero(txNome.getText());
        Conexao con = Banco.getCon();
        if(txNome.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Campo NOME vazio");
            alert.showAndWait();
        }
        else if(txCodigo.getText().isEmpty()){
            if(!g.gravar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            g.setCodigo(Integer.parseInt(txCodigo.getText()));
            if(!g.alterar(con)){
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
