package controller;

import bd.entidades.Editora;
import bd.util.Banco;
import bd.util.Conexao;
import javafx.scene.control.Alert;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerCadastrarEditora {
    private static ControllerCadastrarEditora instancia;
    
    private ControllerCadastrarEditora() {
    }
    public static ControllerCadastrarEditora retorna(){
        if (instancia == null)
            instancia = new ControllerCadastrarEditora();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerCadastrarEditora getInstance() {
        return instancia;
    }
    
    public static void cadastrar(String txCodigo, String txNome, String txCnpj) {
        Editora e = new Editora(txNome, txCnpj);
        Conexao con = Banco.getCon();

        if(txNome.isEmpty() || txCnpj.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Campo NOME ou CNPJ vazio");
            alert.showAndWait();
        }
        else if(txCodigo.isEmpty()){
            Editora aux = e.buscarEdt(con, e.getCnpj());
            if(aux!= null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar (CNPJ da editora já existente) ");//
                alert.showAndWait();
            }
            else if(!e.gravar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            e.setCodigo(Integer.parseInt(txCodigo));
            Editora aux = e.buscarEdt(con, e.getCnpj());
            if(aux!= null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar (CNPJ da editora já existente) ");//
                alert.showAndWait();
            }
            else if(!e.alterar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
    }
}
