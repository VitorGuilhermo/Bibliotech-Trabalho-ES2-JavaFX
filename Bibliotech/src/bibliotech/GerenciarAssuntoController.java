package bibliotech;

import bd.entidades.Assunto;
import controller.ControllerGerenciarAssunto;
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

public class GerenciarAssuntoController implements Initializable {
    @FXML
    private TextField txFiltro;
    @FXML
    private TableView<Assunto> tabela;
    @FXML
    private TableColumn<Assunto, Integer> colCod;
    @FXML
    private TableColumn<Assunto, String> colNome;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        ControllerGerenciarAssunto.getInstance().carregarTabela(tabela, "");
    }    
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        ControllerGerenciarAssunto.getInstance().buscar(tabela, txFiltro, "ast_nome");
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerGerenciarAssunto.getInstance().cancelar( txFiltro.getScene().getWindow() );
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null)
            ControllerGerenciarAssunto.getInstance().excluir(tabela, tabela.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(tabela.getSelectionModel().getSelectedItem() != null)
            ControllerGerenciarAssunto.retorna().alterar(tabela, tabela.getSelectionModel().getSelectedItem().getCodigo(), tabela.getSelectionModel().getSelectedItem().getNome());
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        ControllerGerenciarAssunto.retorna().novo(tabela);
    }
    
}
