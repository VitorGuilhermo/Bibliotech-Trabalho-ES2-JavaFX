package controller;

import bd.entidades.Cliente;
import bd.util.Banco;
import bd.util.Conexao;
import java.time.LocalDate;
import javafx.scene.control.Alert;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerCadastrarCliente {
    private static ControllerCadastrarCliente instancia;
    
    private ControllerCadastrarCliente() {
    }
    public static ControllerCadastrarCliente retorna(){
        if (instancia == null)
            instancia = new ControllerCadastrarCliente();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerCadastrarCliente getInstance() {
        return instancia;
    }
    
    public static void cadastrar(String txCodigo, String txNome, String txDocumento, String txEndereco, String txTelefone, String txSexo, LocalDate dpDataNasc) {
        Cliente c = new Cliente(txNome, txDocumento, txEndereco, txTelefone, txSexo, dpDataNasc);
        Conexao con = Banco.getCon();
        if(txNome.isEmpty() || txDocumento.isEmpty() || txEndereco.isEmpty() || txTelefone.isEmpty() || txSexo.isEmpty() || dpDataNasc == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Algum campo está vazio");
            alert.showAndWait();
        }
        else if(dpDataNasc.isAfter(LocalDate.now())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: não é possível gravar uma data futura");
            alert.showAndWait();
        }
        else if(txCodigo.isEmpty()){
            if(!c.gravar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            c.setCodigo(Integer.parseInt(txCodigo));
            if(!c.alterar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
    }
}
