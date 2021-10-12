package controller;

import bd.entidades.Autor;
import bd.util.Conexao;
import javafx.scene.control.Alert;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerCadastrarAutor extends ControllerCadastrar {
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

    @Override
    public void gravarOuAlterar(Conexao con, String txCodigo, String txNome) {
        Autor a = new Autor(txNome);
        if(txCodigo.isEmpty()){
            Autor aux = a.buscarAut(con, txNome.toUpperCase());
            if(aux!= null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar (Autor já existente) ");//
                alert.showAndWait();
            }
            else if(!a.gravar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +con.getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            a.setCodigo(Integer.parseInt(txCodigo));
            Autor aux = a.buscarAut(con, txNome.toUpperCase());
            if(aux!= null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar (Autor já existente) ");//
                alert.showAndWait();
            }
            else if(!a.alterar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +con.getMensagemErro());
                alert.showAndWait();
            }
        }
    }
}
