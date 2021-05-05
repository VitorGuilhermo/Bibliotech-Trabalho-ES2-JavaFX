package bibliotech;

import bd.dal.AssuntoDAL;
import bd.dal.AutorDAL;
import bd.dal.EditoraDAL;
import bd.dal.GeneroDAL;
import bd.dal.TituloDAL;
import bd.entidades.Assunto;
import bd.entidades.Autor;
import bd.entidades.Editora;
import bd.entidades.Genero;
import bd.entidades.Titulo;
import bd.util.Banco;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
    private ComboBox<Autor> cbAutor;
    @FXML
    private ComboBox<Genero> cbGenero;
    @FXML
    private ComboBox<Assunto> cbAssunto;
    @FXML
    private ComboBox<Editora> cbEditora;
    @FXML
    private Spinner<Integer> spQtdeExem;
    @FXML
    private DatePicker dtDataPubl;
    @FXML
    private DatePicker dtDataRegistro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbAutor.setItems(FXCollections.observableArrayList(new AutorDAL().get("")));
        cbGenero.setItems(FXCollections.observableArrayList(new GeneroDAL().get("")));
        cbAssunto.setItems(FXCollections.observableArrayList(new AssuntoDAL().get("")));
        cbEditora.setItems(FXCollections.observableArrayList(new EditoraDAL().get("")));
        spQtdeExem.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000000, 1));
    }    

    public void setDados(int cod, String nome, Autor autor, Genero gen, Assunto assunto, Editora editora, int qtde, LocalDate dataP, LocalDate dataR){
        txCodigo.setText(""+cod);
        txTitulo.setText(nome);
        cbAutor.getSelectionModel().select(autor);
        cbGenero.getSelectionModel().select(gen);
        cbAssunto.getSelectionModel().select(assunto);
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
        Titulo t = new Titulo(txTitulo.getText(), cbAutor.getValue(), cbGenero.getValue(), cbAssunto.getValue(), cbEditora.getValue(), spQtdeExem.getValue(), dtDataPubl.getValue(), dtDataRegistro.getValue());
        
        if(txCodigo.getText().isEmpty()){
            if(!new TituloDAL().gravar(t)){
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
        
        cbAutor.setItems(FXCollections.observableArrayList(new AutorDAL().get("")));
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
        
        cbAssunto.setItems(FXCollections.observableArrayList(new AssuntoDAL().get("")));
    }
    
}
