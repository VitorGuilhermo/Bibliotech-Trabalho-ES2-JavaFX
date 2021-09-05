package bibliotech;

import bd.entidades.Genero;
import controller.ControllerGerenciarGenero;
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


public class TelaGerenciarGeneroController implements Initializable {
    @FXML
    private TextField txFiltro;
    @FXML
    private TableView<Genero> tabela;
    @FXML
    private TableColumn<Genero, Integer> colCodigo;
    @FXML
    private TableColumn<Genero, String> colNome;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        ControllerGerenciarGenero.carregarTabela(tabela, "");
    }    
    
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        ControllerGerenciarGenero.buscar(tabela, txFiltro);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerGerenciarGenero.cancelar( txFiltro.getScene().getWindow() );
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null)
            ControllerGerenciarGenero.excluir(tabela, tabela.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(tabela.getSelectionModel().getSelectedItem() != null)
            new ControllerGerenciarGenero().alterar(tabela, tabela.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        new ControllerGerenciarGenero().novo(tabela);
    }
    
}
