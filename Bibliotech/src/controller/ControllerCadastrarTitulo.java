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
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;



/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerCadastrarTitulo {
    private static ControllerCadastrarTitulo instancia;
    
    private ControllerCadastrarTitulo() {
    }
    public static ControllerCadastrarTitulo retorna(){
        if (instancia == null)
            instancia = new ControllerCadastrarTitulo();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerCadastrarTitulo getInstance() {
        return instancia;
    }
    
    
    public void cadastrar(String txCodigo, String txTitulo, Genero cbGenero, Editora cbEditora, Integer spQtdeExem, LocalDate dtDataPubl, LocalDate dtDataRegistro,
        Autor cbAutor1, Autor cbAutor2, Autor cbAutor3, Assunto cbAssunto1, Assunto cbAssunto2, Assunto cbAssunto3) {
        Conexao con = Banco.getCon();
        ArrayList<Autor> autores = new ArrayList<>();
        ArrayList<Assunto> assuntos = new ArrayList<>();
        Titulo t = new Titulo(txTitulo, cbGenero, cbEditora, spQtdeExem, dtDataPubl, dtDataRegistro, autores, assuntos);
        boolean gravouT=false;
        
        if(txTitulo.isEmpty() || cbGenero == null || cbEditora == null || spQtdeExem == null || spQtdeExem == null || dtDataPubl == null
                || dtDataRegistro == null || cbAutor1 == null || cbAssunto1 == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Algum campo está vazio");
            alert.showAndWait();
        }
        else if(validaIgualdadeCB(cbAutor1, cbAutor2, cbAutor3, cbAssunto1, cbAssunto2, cbAssunto3)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: O campo de Autor ou Assunto apresentam dados repetidos");
            alert.showAndWait();
        }
        else if(dtDataPubl.isAfter(LocalDate.now()) || dtDataRegistro.isAfter(LocalDate.now())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: A data não pode ser futura!");
            alert.showAndWait();
        }
        else if(dtDataPubl.isAfter(dtDataRegistro)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: A data de publicação do livro não pode ser posterior a data de registro!");
            alert.showAndWait();
        }
        else if(txCodigo.isEmpty()){
            //alimenta lista autores
            if (cbAutor1 != null)
                autores.add(cbAutor1);
            if (cbAutor2 != null)
                autores.add(cbAutor2);
            if (cbAutor3 != null) 
                autores.add(cbAutor3);
            //alimenta lista assuntos
            if (cbAssunto1 != null)
                assuntos.add(cbAssunto1);
            if (cbAssunto2 != null) 
                assuntos.add(cbAssunto2);
            if (cbAssunto3 != null)
                assuntos.add(cbAssunto3);
            
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
            t.setCodigo(Integer.parseInt(txCodigo));
            if(!t.alterar(con)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
    }
    
    
    public List<Autor> novoAutor() throws IOException {
        Conexao con = Banco.getCon();
        Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarAutor.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Autor");
        stage.showAndWait();

        ControllerCadastrarAutor.removeInstancia();
        return new Autor().buscar(con, "");
    }
    
    public List<Genero> novoGenero() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarGenero.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Gênero");
        stage.showAndWait();

        ControllerCadastrarGenero.removeInstancia();
        Conexao con = Banco.getCon();
        return new Genero().buscar(con, "");
    }
    
    public List<Assunto> novoAssunto() throws IOException {
        Conexao con = Banco.getCon();

        Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarAssunto.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Assunto");
        stage.showAndWait();

        ControllerCadastrarAssunto.removeInstancia();
        return new Assunto().buscar(con, "");
    }
    
    public boolean validaIgualdadeCB(Autor cbAutor1, Autor cbAutor2, Autor cbAutor3, Assunto cbAssunto1, Assunto cbAssunto2, Assunto cbAssunto3){
        if(cbAutor1 != null && cbAutor2 != null){
            if(cbAutor1.equalsAutor(cbAutor2))
                return true;
        }
        if(cbAutor2 != null && cbAutor3 != null){
            if(cbAutor2.equalsAutor(cbAutor3))
                return true;
        }
        if(cbAutor1 != null && cbAutor3 != null){
            if(cbAutor1.equalsAutor(cbAutor3))
                return true;
        }
        if(cbAssunto1 != null && cbAssunto2 != null){
            if(cbAssunto1.equalsAssunto(cbAssunto2))
                return true;
        }
        if(cbAssunto2 != null && cbAssunto3 != null){
            if(cbAssunto2.equalsAssunto(cbAssunto3))
                return true;
        }
        if(cbAssunto1 != null && cbAssunto3 != null){
            if(cbAssunto1.equalsAssunto(cbAssunto3))
                return true;
        }
        return false;
    }
}
