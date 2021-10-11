package controller;

import bd.entidades.Genero;
import bd.util.Banco;
import bd.util.Conexao;
import javafx.scene.control.Alert;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerCadastrarGenero extends ControllerCadastrar {
    private static ControllerCadastrarGenero instancia;
    
    private ControllerCadastrarGenero() {
    }
    public static ControllerCadastrarGenero retorna(){
        if (instancia == null)
            instancia = new ControllerCadastrarGenero();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerCadastrarGenero getInstance() {
        return instancia;
    }
    

    @Override
    public void gravarOuAlterar(Conexao con, String txCod, String txNome) {
        Genero g = new Genero(txNome);
        if(txCod.isEmpty()){
            if(!g.gravar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            g.setCodigo(Integer.parseInt(txCod));
            if(!g.alterar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
    }
}
