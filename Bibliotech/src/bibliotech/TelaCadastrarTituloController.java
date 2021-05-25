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
import java.util.ArrayList;
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

    static TelaCadastrarTituloController instancia;
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

    
    public TelaCadastrarTituloController() {
    }
    public static TelaCadastrarTituloController retorna(){
        if (instancia == null){
            instancia = new TelaCadastrarTituloController();
            return (instancia);
        }
        return null;
    }
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
        spQtdeExem.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000000, 1));
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
        else if(validaIgualdadeCB()){
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
            gravouT = t.gravar();
            if (gravouT){   //se gravou o titulo, vai gravar os assuntos e os autores
                for(Autor a : autores)
                    new Autor_Titulo(a, t).gravar();
                for(Assunto a : assuntos)
                    new Assunto_Titulo(a, t).gravar();
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
        if(TelaCadastrarAutorController.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("TelaCadastrarAutor.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Autor");
            stage.showAndWait();

            TelaCadastrarAutorController.instancia = null;
            cbAutor1.setItems(FXCollections.observableArrayList(new AutorDAL().get("")));
            cbAutor2.setItems(FXCollections.observableArrayList(new AutorDAL().get("")));
            cbAutor3.setItems(FXCollections.observableArrayList(new AutorDAL().get("")));
        }
    }

    @FXML
    private void evtNovoGenero(ActionEvent event) throws IOException {
        if(TelaCadastrarGeneroController.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("TelaCadastrarGenero.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Gênero");
            stage.showAndWait();

            TelaCadastrarGeneroController.instancia = null;
            cbGenero.setItems(FXCollections.observableArrayList(new GeneroDAL().get("")));
        }
    }

    @FXML
    private void evtNovoAssunto(ActionEvent event) throws IOException {
        if(TelaCadastrarAssuntoController.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("TelaCadastrarAssunto.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Assunto");
            stage.showAndWait();

            TelaCadastrarAssuntoController.instancia = null;
            cbAssunto1.setItems(FXCollections.observableArrayList(new AssuntoDAL().get("")));
            cbAssunto2.setItems(FXCollections.observableArrayList(new AssuntoDAL().get("")));
            cbAssunto3.setItems(FXCollections.observableArrayList(new AssuntoDAL().get("")));
        }
    }
    public boolean validaIgualdadeCB(){
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
