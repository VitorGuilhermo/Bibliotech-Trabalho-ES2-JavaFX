package bibliotech;

import bd.dal.AssuntoDAL;
import bd.dal.AutorDAL;
import bd.dal.EditoraDAL;
import bd.dal.GeneroDAL;
import bd.dal.TituloDAL;
import bd.entidades.Assunto;
import bd.entidades.Assunto_Titulo;
import bd.entidades.Autor;
import bd.entidades.Autor_Titulo;
import bd.entidades.Editora;
import bd.entidades.Genero;
import bd.entidades.Titulo;
import bd.util.Banco;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class TelaCadastrarTituloController implements Initializable {

    @FXML
    private TextField txCodigo;
    @FXML
    private TextField txTitulo;
    @FXML
    private ComboBox<Genero> cbGenero;
    @FXML
    private ComboBox<Editora> cbEditora;
    @FXML
    private Spinner<Integer> spQtdeExem;
    @FXML
    private DatePicker dtDataPubl;
    @FXML
    private DatePicker dtDataRegistro;
    @FXML
    private ComboBox<Autor> cbAutor1;
    @FXML
    private ComboBox<Autor> cbAutor2;
    @FXML
    private ComboBox<Autor> cbAutor3;
    @FXML
    private ComboBox<Assunto> cbAssunto1;
    @FXML
    private ComboBox<Assunto> cbAssunto2;
    @FXML
    private ComboBox<Assunto> cbAssunto3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbAutor1.setItems(FXCollections.observableArrayList(new AutorDAL().get("")));
        cbAutor2.setItems(FXCollections.observableArrayList(new AutorDAL().get("")));
        cbAutor3.setItems(FXCollections.observableArrayList(new AutorDAL().get("")));
        cbGenero.setItems(FXCollections.observableArrayList(new GeneroDAL().get("")));
        cbAssunto1.setItems(FXCollections.observableArrayList(new AssuntoDAL().get("")));
        cbAssunto2.setItems(FXCollections.observableArrayList(new AssuntoDAL().get("")));
        cbAssunto3.setItems(FXCollections.observableArrayList(new AssuntoDAL().get("")));
        cbEditora.setItems(FXCollections.observableArrayList(new EditoraDAL().get("")));
        spQtdeExem.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000000, 1));
    }    

    public void setDados(int cod, String nome, List<Autor>autores, Genero gen, List<Assunto>assuntos, Editora editora, int qtde, LocalDate dataP, LocalDate dataR){
        txCodigo.setText(""+cod);
        txTitulo.setText(nome);
        int i=0;
        for(Autor a: autores){
            if(i==0)
                cbAutor1.getSelectionModel().select(a);
            else if(i==1)
                cbAutor2.getSelectionModel().select(a);
            else if(i==2)
                cbAutor3.getSelectionModel().select(a);
            i++;    
        }
        cbGenero.getSelectionModel().select(gen);
        i=0;
        for(Assunto a: assuntos){
            if(i==0)
                cbAssunto1.getSelectionModel().select(a);
            else if(i==1)
                cbAssunto2.getSelectionModel().select(a);
            else if(i==2)
                cbAssunto3.getSelectionModel().select(a);
            i++;    
        }
        cbEditora.getSelectionModel().select(editora);
        spQtdeExem.getEditor().textProperty().set(""+qtde);
        spQtdeExem.setDisable(true);
        dtDataPubl.setValue(dataP);
        dtDataRegistro.setValue(dataR);
    }
    
    @FXML
    private void evtCancelar(ActionEvent event) {
        txCodigo.getScene().getWindow().hide();
    }

    @FXML
    private void evtCadastrar(ActionEvent event) {
        boolean gravouT=false;
        Titulo t = new Titulo(txTitulo.getText(), cbGenero.getValue(), cbEditora.getValue(), spQtdeExem.getValue(), dtDataPubl.getValue(), dtDataRegistro.getValue());
        Autor_Titulo at1=null, at2=null, at3=null;
        Assunto_Titulo ast1=null, ast2=null, ast3=null;
        
        if(txCodigo.getText().isEmpty()){
            gravouT = t.gravar();
            if (gravouT) {
                //grava autores
                if (!cbAutor1.getSelectionModel().isEmpty()) {
                    at1 = new Autor_Titulo(cbAutor1.getValue(), t);
                    at1.gravar();
                }
                if (!cbAutor2.getSelectionModel().isEmpty()) {
                    at2 = new Autor_Titulo(cbAutor2.getValue(), t);
                    at2.gravar();
                }
                if (!cbAutor3.getSelectionModel().isEmpty()) {
                    at3 = new Autor_Titulo(cbAutor3.getValue(), t);
                    at3.gravar();
                }
                //grava assuntos
                if (!cbAssunto1.getSelectionModel().isEmpty()) {
                    ast1 = new Assunto_Titulo(cbAssunto1.getValue(), t);
                    ast1.gravar();
                }
                if (!cbAssunto2.getSelectionModel().isEmpty()) {
                    ast2 = new Assunto_Titulo(cbAssunto2.getValue(), t);
                    ast2.gravar();
                }
                if (!cbAssunto3.getSelectionModel().isEmpty()) {
                    ast3 = new Assunto_Titulo(cbAssunto3.getValue(), t);
                    ast3.gravar();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao gravar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{  //alterar
            t.setCodigo(Integer.parseInt(txCodigo.getText()));
            if(!new TituloDAL().alterar(t)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: ao alterar " +Banco.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        txCodigo.getScene().getWindow().hide();
    }

    @FXML
    private void evtNovoAutor(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaCadastrarAutor.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Autor");
        stage.showAndWait();
        
        cbAutor1.setItems(FXCollections.observableArrayList(new AutorDAL().get("")));
        cbAutor2.setItems(FXCollections.observableArrayList(new AutorDAL().get("")));
        cbAutor3.setItems(FXCollections.observableArrayList(new AutorDAL().get("")));
    }

    @FXML
    private void evtNovoGenero(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaCadastrarGenero.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Gênero");
        stage.showAndWait();
        
        cbGenero.setItems(FXCollections.observableArrayList(new GeneroDAL().get("")));
    }

    @FXML
    private void evtNovoAssunto(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaCadastrarAssunto.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Assunto");
        stage.showAndWait();
        
        cbAssunto1.setItems(FXCollections.observableArrayList(new AssuntoDAL().get("")));
        cbAssunto2.setItems(FXCollections.observableArrayList(new AssuntoDAL().get("")));
        cbAssunto3.setItems(FXCollections.observableArrayList(new AssuntoDAL().get("")));
    }
    
}
