package bibliotech;

import bd.entidades.Editora;
import bd.entidades.Genero;
import bd.entidades.Titulo;
import controller.ControllerAdicionarExemplar;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Vitor Guilhermo
 */
public class TelaAdicionarExemplarController implements Initializable {

    @FXML
    private TextField txFiltro;
    @FXML
    private TableView<Titulo> tabela;
    @FXML
    private TableColumn<Titulo, Integer> colCodigo;
    @FXML
    private TableColumn<Titulo, String> colTitulo;
    @FXML
    private TableColumn<Titulo, Genero> colGenero;
    @FXML
    private TableColumn<Titulo, Editora> colEditora;
    @FXML
    private TableColumn<Titulo, LocalDate> colDataImp;
    @FXML
    private TableColumn<Titulo, Integer> colQtdeExe;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colEditora.setCellValueFactory(new PropertyValueFactory<>("editora"));
        colDataImp.setCellValueFactory(new PropertyValueFactory<>("dataPubli"));
        colQtdeExe.setCellValueFactory(new PropertyValueFactory<>("qtdeExemplares"));
        
        ControllerAdicionarExemplar.carregarTabela(tabela, "");
    }    

    @FXML
    private void evtBuscar(ActionEvent event) {
        ControllerAdicionarExemplar.buscar(tabela, txFiltro);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerAdicionarExemplar.cancelar( txFiltro.getScene().getWindow() );
    }

    @FXML
    private void evtAdicionar(ActionEvent event) throws IOException {
        if(tabela.getSelectionModel().getSelectedItem() != null)
            ControllerAdicionarExemplar.retorna().adicionar(tabela, tabela.getSelectionModel().getSelectedItem());
    }
    
}
