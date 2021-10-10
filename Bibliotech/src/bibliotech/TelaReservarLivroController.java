package bibliotech;

import bd.entidades.Editora;
import bd.entidades.Genero;
import bd.entidades.Titulo;
import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerReservarLivro;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Vitor Guilhermo
 */
public class TelaReservarLivroController implements Initializable {

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
        
        carregarTabela("");
    }    

    public void carregarTabela(String filtro){
        Conexao con = Banco.getCon();
        Titulo t = new Titulo();
        
        List<Titulo> titulos = t.pesquisarFiltro(con, filtro);
        tabela.setItems(FXCollections.observableArrayList( titulos ));
    }
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        String filtro = ControllerReservarLivro.getInstance().buscar(txFiltro.getText());
        carregarTabela(filtro);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txFiltro.getScene().getWindow().hide();
    }

    @FXML
    private void evtReservar(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Titulo t = tabela.getSelectionModel().getSelectedItem();
            ControllerReservarLivro.getInstance().reservar(t.getCodigo(), t.getNome());
        }
    }
    
}
