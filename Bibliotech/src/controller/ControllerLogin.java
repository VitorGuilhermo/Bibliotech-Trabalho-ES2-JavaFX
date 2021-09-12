package controller;

import bd.entidades.Bibliotecario;
import bd.entidades.Cliente;
import bd.util.Banco;
import bd.util.Conexao;
import bibliotech.TelaPrincipalClienteController;
import bibliotech.TelaPrincipalController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 * @author Vitor Guilhermo
 */
public class ControllerLogin {
    private static ControllerLogin instancia;
    
    private ControllerLogin() {
    }
    public static ControllerLogin retorna(){
        if (instancia == null)
            instancia = new ControllerLogin();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerLogin getInstance() {
        return instancia;
    }
    
    
    public void sair(Window janela){
        janela.hide();
    }
    
    public void entrar(TextField btnDoc, PasswordField btnSenha, String documento, String senha) throws IOException{
        if(ControllerHomeBib.retorna() != null){
            Conexao con = Banco.getCon();
            
            Bibliotecario bib = new Bibliotecario(documento, senha);
            Bibliotecario aux = bib.verificaLogin(con);
            Cliente cli = new Cliente(documento);
            cli = cli.verificaLogin(con, senha);
            
            if(aux != null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaPrincipal.fxml"));
                Parent root = (Parent) loader.load();
                TelaPrincipalController ctr = loader.getController();
                SingletonBibliotecario.getInstance(aux);
                ctr.setDados();

                Scene scene = new Scene(root);
                Stage stage = new Stage();

                stage.setTitle("Bibliotech");
                stage.getIcons().add(new Image("img/icone.png"));
                stage.setScene(scene);
                stage.setOnCloseRequest(e->{Banco.getCon().desconectar();});
                stage.show();

                ControllerHomeBib.removeInstancia();
                sair(btnDoc.getScene().getWindow());
            }
            else if(cli != null){
                if(ControllerHomeCliente.getInstance() == null && ControllerHomeCliente.retorna() != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaPrincipalCliente.fxml"));
                    Parent root = (Parent) loader.load();
                    TelaPrincipalClienteController ctr = loader.getController();
                    ctr.setDados(cli.getNome());
                    SingletonCliente.getInstance(cli);
                    
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();

                    stage.setTitle("Bibliotech");
                    stage.getIcons().add(new Image("img/icone.png"));
                    stage.setScene(scene);
                    stage.setOnCloseRequest(e->{Banco.getCon().desconectar();});
                    stage.show();

                    ControllerHomeCliente.removeInstancia();
                    sair(btnDoc.getScene().getWindow());
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Falha");
                alert.setHeaderText("Erro no documento ou senha...");
                alert.setContentText("Tente novamente!");
                alert.showAndWait();

                btnDoc.clear();
                btnSenha.clear();
            }
            
            ControllerLogin.instancia = null;
        }
    }
    
}
