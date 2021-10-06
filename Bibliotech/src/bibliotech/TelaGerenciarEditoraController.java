package bibliotech;

import bd.entidades.Editora;
import controller.ControllerGerenciarEditora;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaGerenciarEditoraController implements Initializable {
    @FXML
    private TextField txFiltro;
    @FXML
    private TableView<Editora> tabela;
    @FXML
    private TableColumn<Editora, Integer> colCodigo;
    @FXML
    private TableColumn<Editora, String> colNome;
    @FXML
    private TableColumn<Editora, String> colCnpj;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        
        ControllerGerenciarEditora.getInstance().carregarTabela(tabela, "");
    }    
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        ControllerGerenciarEditora.getInstance().buscar(tabela, txFiltro, "edt_nome");
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerGerenciarEditora.getInstance().cancelar( txFiltro.getScene().getWindow() );
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Editora e = tabela.getSelectionModel().getSelectedItem();
            ControllerGerenciarEditora.getInstance().excluir(tabela, e.getCodigo(), e.getNome());
        }
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Editora e = tabela.getSelectionModel().getSelectedItem();
            ControllerGerenciarEditora.retorna().alterar(tabela, e.getCodigo(), e.getNome(), e.getCnpj());
        }
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        ControllerGerenciarEditora.retorna().novo(tabela);
    }
    
}
