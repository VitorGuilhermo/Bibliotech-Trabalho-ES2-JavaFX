package controller;

import bd.entidades.Autor;
import bd.util.Banco;
import bd.util.Conexao;
import javafx.scene.control.Alert;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerCadastrarAutor {
    private static ControllerCadastrarAutor instancia;
    
    private ControllerCadastrarAutor() {
    }
    public static ControllerCadastrarAutor retorna(){
        if (instancia == null)
            instancia = new ControllerCadastrarAutor();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerCadastrarAutor getInstance() {
        return instancia;
    }
    
    
    public static void cadastrar(String txCodigo, String txNome) {
        Autor a = new Autor(txNome);
        Conexao con = Banco.getCon();
        if(txNome.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Campo NOME vazio");
            alert.showAndWait();
        }
        else if(txCodigo.isEmpty()){
            if(!a.gravar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            a.setCodigo(Integer.parseInt(txCodigo));
            if(!a.alterar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
    }
}
