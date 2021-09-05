package controller;

import bd.entidades.Assunto;
import bd.entidades.Assunto_Titulo;
import bd.entidades.Autor;
import bd.entidades.Autor_Titulo;
import bd.entidades.Editora;
import bd.entidades.Genero;
import bd.entidades.Titulo;
import bd.util.Banco;
import bd.util.Conexao;
import bibliotech.TelaCadastrarGeneroController;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerCadastrarTitulo {
    public static ControllerCadastrarTitulo instancia;
    
    public ControllerCadastrarTitulo() {
    }
    public static ControllerCadastrarTitulo retorna(){
        if (instancia == null){
            instancia = new ControllerCadastrarTitulo();
            return (instancia);
        }
        return null;
    }
    
    
    public static void cadastrar(TextField txCodigo, TextField txTitulo, ComboBox<Genero> cbGenero, ComboBox<Editora> cbEditora, Spinner<Integer> spQtdeExem, DatePicker dtDataPubl, DatePicker dtDataRegistro,
        ComboBox<Autor> cbAutor1, ComboBox<Autor> cbAutor2, ComboBox<Autor> cbAutor3, ComboBox<Assunto> cbAssunto1, ComboBox<Assunto> cbAssunto2, ComboBox<Assunto> cbAssunto3) {
        Conexao con = Banco.getCon();
        ArrayList<Autor> autores = new ArrayList<>();
        ArrayList<Assunto> assuntos = new ArrayList<>();
        Titulo t = new Titulo(txTitulo.getText(), cbGenero.getValue(), cbEditora.getValue(), spQtdeExem.getValue(), dtDataPubl.getValue(), dtDataRegistro.getValue(), autores, assuntos);
        boolean gravouT=false;
        
        if(txTitulo.getText().isEmpty() || cbGenero.getValue() == null || cbEditora.getValue() == null || spQtdeExem.getValue() == null || spQtdeExem.getValue() == null || dtDataPubl.getValue() == null
                || dtDataRegistro.getValue() == null || cbAutor1.getValue() == null || cbAssunto1.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Algum campo está vazio");
            alert.showAndWait();
        }
        else if(ControllerCadastrarTitulo.validaIgualdadeCB(cbAutor1, cbAutor2, cbAutor3, cbAssunto1, cbAssunto2, cbAssunto3)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: O campo de Autor ou Assunto apresentam dados repetidos");
            alert.showAndWait();
        }
        else if(txCodigo.getText().isEmpty()){
            //alimenta lista autores
            if (!cbAutor1.getSelectionModel().isEmpty())
                autores.add(cbAutor1.getValue());
            if (!cbAutor2.getSelectionModel().isEmpty())
                autores.add(cbAutor2.getValue());
            if (!cbAutor3.getSelectionModel().isEmpty()) 
                autores.add(cbAutor3.getValue());
            //alimenta lista assuntos
            if (!cbAssunto1.getSelectionModel().isEmpty())
                assuntos.add(cbAssunto1.getValue());
            if (!cbAssunto2.getSelectionModel().isEmpty()) 
                assuntos.add(cbAssunto2.getValue());
            if (!cbAssunto3.getSelectionModel().isEmpty())
                assuntos.add(cbAssunto3.getValue());
            
            //grava titulo
            t.setAutores(autores);
            t.setAssuntos(assuntos);
            gravouT = t.gravar(con);
            if (gravouT){   //se gravou o titulo, vai gravar os assuntos e os autores
                for(Autor a : autores)
                    new Autor_Titulo(a, t).gravar(con);
                for(Assunto a : assuntos)
                    new Assunto_Titulo(a, t).gravar(con);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            t.setCodigo(Integer.parseInt(txCodigo.getText()));
            if(!t.alterar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        txCodigo.getScene().getWindow().hide();
    }
    
    
    public void novoAutor(ComboBox<Autor> cbAutor1, ComboBox<Autor> cbAutor2, ComboBox<Autor> cbAutor3) throws IOException {
        if(ControllerCadastrarAutor.retorna() != null){
            Conexao con = Banco.getCon();
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarAutor.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Autor");
            stage.showAndWait();

            ControllerCadastrarAutor.instancia = null;
            cbAutor1.setItems(FXCollections.observableArrayList(new Autor().buscar(con, "")));
            cbAutor2.setItems(FXCollections.observableArrayList(new Autor().buscar(con, "")));
            cbAutor3.setItems(FXCollections.observableArrayList(new Autor().buscar(con, "")));
        }
    }
    
    public void novoGenero(ComboBox<Genero> cbGenero) throws IOException {
        if(ControllerCadastrarGenero.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarGenero.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Gênero");
            stage.showAndWait();

            ControllerCadastrarGenero.instancia = null;
            Conexao con = Banco.getCon();
            cbGenero.setItems(FXCollections.observableArrayList(new Genero().buscar(con, "")));
        }
    }
    
    public void novoAssunto(ComboBox<Assunto> cbAssunto1, ComboBox<Assunto> cbAssunto2, ComboBox<Assunto> cbAssunto3) throws IOException {
        if(ControllerCadastrarAssunto.retorna() != null){
            Conexao con = Banco.getCon();
            
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarAssunto.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Assunto");
            stage.showAndWait();

            ControllerCadastrarAssunto.instancia = null;
            cbAssunto1.setItems(FXCollections.observableArrayList(new Assunto().buscar(con, "")));
            cbAssunto2.setItems(FXCollections.observableArrayList(new Assunto().buscar(con, "")));
            cbAssunto3.setItems(FXCollections.observableArrayList(new Assunto().buscar(con, "")));
        }
    }
    
    public static void cancelar(Window janela) {
        janela.hide();
    }
    
    public static boolean validaIgualdadeCB(ComboBox<Autor> cbAutor1, ComboBox<Autor> cbAutor2, ComboBox<Autor> cbAutor3, ComboBox<Assunto> cbAssunto1, ComboBox<Assunto> cbAssunto2, ComboBox<Assunto> cbAssunto3){
        if(cbAutor1.getValue() != null && cbAutor2.getValue() != null){
            if(cbAutor1.getValue().equalsAutor(cbAutor2.getValue()))
                return true;
        }
        if(cbAutor2.getValue() != null && cbAutor3.getValue() != null){
            if(cbAutor2.getValue().equalsAutor(cbAutor3.getValue()))
                return true;
        }
        if(cbAutor1.getValue() != null && cbAutor3.getValue() != null){
            if(cbAutor1.getValue().equalsAutor(cbAutor3.getValue()))
                return true;
        }
        if(cbAssunto1.getValue() != null && cbAssunto2.getValue() != null){
            if(cbAssunto1.getValue().equalsAssunto(cbAssunto2.getValue()))
                return true;
        }
        if(cbAssunto2.getValue() != null && cbAssunto3.getValue() != null){
            if(cbAssunto2.getValue().equalsAssunto(cbAssunto3.getValue()))
                return true;
        }
        if(cbAssunto1.getValue() != null && cbAssunto3.getValue() != null){
            if(cbAssunto1.getValue().equalsAssunto(cbAssunto3.getValue()))
                return true;
        }
        return false;
    }
}
