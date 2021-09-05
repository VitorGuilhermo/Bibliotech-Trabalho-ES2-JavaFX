package controller;

import bd.entidades.Assunto;
import bd.util.Banco;
import bd.util.Conexao;
import bibliotech.TelaCadastrarAssuntoController;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerGerenciarAssunto {
    public static ControllerGerenciarAssunto instancia;
    
    public ControllerGerenciarAssunto() {
    }
    public static ControllerGerenciarAssunto retorna(){
        if (instancia == null){
            instancia = new ControllerGerenciarAssunto();
            return (instancia);
        }
        return null;
    }
    
    
    public static void carregarTabela(TableView tabela, String filtro){
        Assunto a = new Assunto();
        Conexao con = Banco.getCon();
        
        List<Assunto> assuntos = a.buscar(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(assuntos));
    }
    
    public void alterar(TableView tabela, int cod, String nome) throws IOException {
         if(ControllerCadastrarAssunto.retorna() != null){
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
            
            ControllerCadastrarAssunto.instancia = null;
            carregarTabela(tabela, "");
        }
    }
    
    public void novo(TableView tabela) throws IOException {
        if(ControllerCadastrarAssunto.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarAssunto.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Assunto");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            ControllerCadastrarAssunto.instancia = null;
            carregarTabela(tabela, "");
        }
    }
    
    public static void buscar(TableView tabela, TextField txFiltro) {
        String filtro = "upper(ast_nome) like '%#%'";
        
        filtro = filtro.replace("#", txFiltro.getText().toUpperCase());
        
        if(txFiltro.getText().isEmpty())
            carregarTabela(tabela, "");
        else
            carregarTabela(tabela, filtro);
    }
    
    public static void excluir(TableView tabela, Assunto assunto) {
        Conexao con = Banco.getCon();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão de um Assunto");
        alert.setHeaderText("Confirma exclusão?");
        alert.setContentText("Tem certeza que deseja excluir o assunto: "+assunto.getNome()+" ?");
        Optional<ButtonType> result =  alert.showAndWait();
        
        if(result.get() == ButtonType.OK){
            assunto.excluir(con);
            carregarTabela(tabela, "");
        }
    }
    
    public static void cancelar(Window janela) {
        janela.hide();
    }
    
}
