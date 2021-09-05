package controller;

import bd.entidades.Autor;
import bd.util.Banco;
import bd.util.Conexao;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerCadastrarAutor {
    public static ControllerCadastrarAutor instancia;
    
    public ControllerCadastrarAutor() {
    }
    public static ControllerCadastrarAutor retorna(){
        if (instancia == null){
            instancia = new ControllerCadastrarAutor();
            return (instancia);
        }
        return null;
    }
    
    
    public static void cadastrar(TextField txCodigo, TextField txNome) {
        Autor a = new Autor(txNome.getText());
        Conexao con = Banco.getCon();
        if(txNome.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Campo NOME vazio");
            alert.showAndWait();
        }
        else if(txCodigo.getText().isEmpty()){
            if(!a.gravar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            a.setCodigo(Integer.parseInt(txCodigo.getText()));
            if(!a.alterar(con)){
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
