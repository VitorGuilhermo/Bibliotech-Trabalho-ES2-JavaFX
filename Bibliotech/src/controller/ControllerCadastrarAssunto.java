package controller;

import bd.entidades.Assunto;
import bd.util.Banco;
import bd.util.Conexao;
import javafx.scene.control.Alert;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerCadastrarAssunto extends ControllerCadastrar {
    private static ControllerCadastrarAssunto instancia;
     
    private ControllerCadastrarAssunto() {
    }
    public static ControllerCadastrarAssunto retorna(){
        if (instancia == null)
            instancia = new ControllerCadastrarAssunto();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerCadastrarAssunto getInstance() {
        return instancia;
    }


    @Override
    public void gravarOuAlterar(Conexao con, String txCod, String txNome) {
        Assunto a = new Assunto(txNome);
        if(txCod.isEmpty()){
            if(!a.gravar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +con.getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            a.setCodigo(Integer.parseInt(txCod));
            if(!a.alterar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +con.getMensagemErro());
                alert.showAndWait();
            }
        }
    }
}
