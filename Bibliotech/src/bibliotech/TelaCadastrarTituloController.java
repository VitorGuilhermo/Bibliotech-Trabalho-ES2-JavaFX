package bibliotech;

import bd.entidades.Assunto;
import bd.entidades.Autor;
import bd.entidades.Editora;
import bd.entidades.Genero;
import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerCadastrarAssunto;
import controller.ControllerCadastrarAutor;
import controller.ControllerCadastrarGenero;
import controller.ControllerCadastrarTitulo;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;


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
        Conexao con = Banco.getCon();
        List<Autor> autores = new Autor().buscar(con, "");
        List<Assunto> assuntos = new Assunto().buscar(con, "");
        cbAutor1.setItems(FXCollections.observableArrayList( autores ));
        cbAutor2.setItems(FXCollections.observableArrayList( autores));
        cbAutor3.setItems(FXCollections.observableArrayList( autores));
        cbGenero.setItems(FXCollections.observableArrayList(new Genero().buscar(con, "")));
        cbAssunto1.setItems(FXCollections.observableArrayList( assuntos ));
        cbAssunto2.setItems(FXCollections.observableArrayList( assuntos ));
        cbAssunto3.setItems(FXCollections.observableArrayList( assuntos ));
        cbEditora.setItems(FXCollections.observableArrayList(new Editora().buscar(con, "")));
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
        ControllerCadastrarTitulo.getInstance().cadastrar(txCodigo.getText(), txTitulo.getText(), cbGenero.getValue(), cbEditora.getValue(), spQtdeExem.getValue(), dtDataPubl.getValue(), dtDataRegistro.getValue(), cbAutor1.getValue(), cbAutor2.getValue(), cbAutor3.getValue(), cbAssunto1.getValue(), cbAssunto2.getValue(), cbAssunto3.getValue());
        
        evtCancelar(event);
    }

    @FXML
    private void evtNovoAutor(ActionEvent event) throws IOException {
        if(ControllerCadastrarAutor.getInstance() == null && ControllerCadastrarAutor.retorna() != null){
            List<Autor> autores = ControllerCadastrarTitulo.getInstance().novoAutor();
            cbAutor1.setItems(FXCollections.observableArrayList(autores));
            cbAutor2.setItems(FXCollections.observableArrayList(autores));
            cbAutor3.setItems(FXCollections.observableArrayList(autores));
        }
    }

    @FXML
    private void evtNovoGenero(ActionEvent event) throws IOException {
        if(ControllerCadastrarGenero.getInstance() == null && ControllerCadastrarGenero.retorna() != null){
            List<Genero> generos = ControllerCadastrarTitulo.getInstance().novoGenero();
            cbGenero.setItems(FXCollections.observableArrayList(generos));
        }
    }

    @FXML
    private void evtNovoAssunto(ActionEvent event) throws IOException {
        if(ControllerCadastrarAssunto.getInstance() == null && ControllerCadastrarAssunto.retorna() != null){
            List<Assunto> assuntos = ControllerCadastrarTitulo.getInstance().novoAssunto();
            cbAssunto1.setItems(FXCollections.observableArrayList(assuntos));
            cbAssunto2.setItems(FXCollections.observableArrayList(assuntos));
            cbAssunto3.setItems(FXCollections.observableArrayList(assuntos));
        }
    }
}
