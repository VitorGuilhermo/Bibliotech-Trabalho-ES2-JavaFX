package bibliotech;

import bd.entidades.Exemplar;
import controller.ControllerEfetuarEmprestimo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaEfetuarEmprestimoController implements Initializable {
    @FXML
    private TextField txFiltro;
    @FXML
    private ComboBox<String> cbFiltro;
    @FXML
    private TextField txCodigo;
    @FXML
    private TextField txNome;
    @FXML
    private TextField txDocumento;
    @FXML
    private TextField txFiltroExe;
    @FXML
    private TableView<Exemplar> tabela;
    @FXML
    private TableColumn<Exemplar, Integer> colCodigo;
    @FXML
    private TableColumn<Exemplar, String> colTitulo;
    @FXML
    private TableColumn<Exemplar, Boolean> colSituacao;
    @FXML
    private ListView<Exemplar> listaExe;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));
        
        cbFiltro.getItems().add("Nome");
        cbFiltro.getItems().add("Documento");
        cbFiltro.getItems().add("Telefone");
        cbFiltro.getSelectionModel().select(1);
        
        ControllerEfetuarEmprestimo.carregaTabela(tabela, "");
        colSituacao.setCellFactory(column -> {
            return new TableCell<Exemplar, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty); //This is mandatory

                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setStyle("");
                    } 
                    else {
                        if(item)
                            setText("Indisponível"); //Put the String data in the cell
                        else
                            setText("Disponível");
                    }
                }
            };
        });
    }    
    
    @FXML
    private void evtNovoCliente(ActionEvent event) throws IOException {
        new ControllerEfetuarEmprestimo().novoCliente();
    }

    @FXML
    private void evtBuscarExe(ActionEvent event) {
        ControllerEfetuarEmprestimo.buscarExe(tabela, txFiltroExe);
    }

    @FXML
    private void evtAdicionarExe(ActionEvent event) {
        ControllerEfetuarEmprestimo.adicionarExe(tabela, listaExe, tabela.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txCodigo.getScene().getWindow().hide();
    }

    @FXML
    private void evtFinalizar(ActionEvent event) {
        new ControllerEfetuarEmprestimo().finalizar(txCodigo);
    }

    @FXML
    private void evtRemoverExe(ActionEvent event) {
        new ControllerEfetuarEmprestimo().removerExe(listaExe);
    }

    @FXML
    private void evtBuscar(ActionEvent event) {
        new ControllerEfetuarEmprestimo().buscarCli(cbFiltro, txFiltro, txCodigo, txNome, txDocumento);
    }
    
}
