package controller;

import bd.entidades.Assunto;
import bd.util.Banco;
import bd.util.Conexao;
import bibliotech.TelaCadastrarAssuntoController;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerGerenciarAssunto {
    private static ControllerGerenciarAssunto instancia;
    
    private ControllerGerenciarAssunto() {
    }
    public static ControllerGerenciarAssunto retorna(){
        if (instancia == null)
            instancia = new ControllerGerenciarAssunto();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerGerenciarAssunto getInstance() {
        return instancia;
    }
    

    public String buscar(String txFiltrar, String chave) {
        String filtro = "upper("+chave+") like '%#%'";
        
        filtro = filtro.replace("#", txFiltrar.toUpperCase());
        
        if(txFiltrar.isEmpty())
            return "";
        else
            return filtro;
    }
    
    public void alterar(int cod, String nome) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaCadastrarAssunto.fxml"));
        Parent root = (Parent) loader.load();
        TelaCadastrarAssuntoController ctr = loader.getController();
        ctr.setDados(cod, nome);

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Alterar Assunto");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();

        ControllerCadastrarAssunto.removeInstancia();
    }
    
    public void novo() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarAssunto.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Assunto");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();

        ControllerCadastrarAssunto.removeInstancia();  
    }
    
    public boolean  excluir(int cod, String nome) {
        Conexao con = Banco.getCon();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão de um Assunto");
        alert.setHeaderText("Confirma exclusão?");
        alert.setContentText("Tem certeza que deseja excluir o assunto: "+nome+" ?");
        Optional<ButtonType> result =  alert.showAndWait();
        
        if(result.get() == ButtonType.OK){
            Assunto a = new Assunto();
            a.setCodigo(cod);
            a.excluir(con);
            
            return true;
        }
        return false;
    }
    
}
