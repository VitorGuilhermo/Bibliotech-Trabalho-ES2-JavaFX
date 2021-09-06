package bibliotech;

import bd.entidades.Bibliotecario;
import bd.entidades.Editora;
import bd.entidades.Titulo;
import controller.ControllerRetirarLivro;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaRetirarLivroController implements Initializable {
    
    @FXML
    private ComboBox<String> cbConsulta;
    @FXML
    private TextField txFiltro;
    @FXML
    private TableView<Titulo> tabela;
    @FXML
    private TableColumn<Titulo, Integer> colCod;
    @FXML
    private TableColumn<Titulo, String> colTitulo;
    @FXML
    private TableColumn<Titulo, Integer> colQuantidade;
    @FXML
    private TableColumn<Titulo, String> colAutores;
    @FXML
    private TableColumn<Titulo, String> colAssuntos;
    @FXML
    private TableColumn<Titulo, Editora> colEditora;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEditora.setCellValueFactory(new PropertyValueFactory<>("editora"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("qtdeExemplares"));
        colAutores.setCellValueFactory(new PropertyValueFactory<>("autores"));
        colAssuntos.setCellValueFactory(new PropertyValueFactory<>("assuntos"));
        
        cbConsulta.getItems().add("Titulo");
        cbConsulta.getItems().add("Autor");
        cbConsulta.getItems().add("Editora");
        cbConsulta.getItems().add("Assunto");
        cbConsulta.getSelectionModel().select(0);
        
        ControllerRetirarLivro.carregarTabela(tabela, "", "");
    }    
    public void setDados(Bibliotecario bib){
        ControllerRetirarLivro.bib = bib;
    }
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        ControllerRetirarLivro.buscar(tabela, txFiltro, cbConsulta);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerRetirarLivro.cancelar( txFiltro.getScene().getWindow() );
    }

    @FXML
    private void evtConfirmar(ActionEvent event) throws IOException {
        new ControllerRetirarLivro().confirmar(tabela, tabela.getSelectionModel().getSelectedItem());
    }
    
}
