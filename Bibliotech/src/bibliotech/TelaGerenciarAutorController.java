package bibliotech;

import bd.entidades.Autor;
import controller.ControllerGerenciarAutor;
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

public class TelaGerenciarAutorController implements Initializable {
    @FXML
    private TextField txFiltro;
    @FXML
    private TableView<Autor> tabela;
    @FXML
    private TableColumn<Autor, Integer> colCodigo;
    @FXML
    private TableColumn<Autor, String> colNome;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        ControllerGerenciarAutor.getInstance().carregarTabela(tabela, "");
    }    

    @FXML
    private void evtBuscar(ActionEvent event) {
        ControllerGerenciarAutor.getInstance().buscar(tabela, txFiltro, "aut_nome");
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerGerenciarAutor.getInstance().cancelar( txFiltro.getScene().getWindow() );
    }   

    @FXML
    private void evtExcluir(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Autor a = tabela.getSelectionModel().getSelectedItem();
            ControllerGerenciarAutor.getInstance().excluir(tabela, a.getCodigo(), a.getNome());
        }
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Autor a = tabela.getSelectionModel().getSelectedItem();
            ControllerGerenciarAutor.retorna().alterar(tabela, a.getCodigo(), a.getNome());
        }
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        ControllerGerenciarAutor.retorna().novo(tabela);
    }
    
}
