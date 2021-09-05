package bibliotech;

import bd.entidades.Autor;
import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerGerenciarAutor;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
        
        ControllerGerenciarAutor.carregarTabela(tabela, "");
    }    

    @FXML
    private void evtBuscar(ActionEvent event) {
        ControllerGerenciarAutor.buscar(tabela, txFiltro);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerGerenciarAutor.cancelar( txFiltro.getScene().getWindow() );
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null)
            ControllerGerenciarAutor.excluir(tabela, tabela.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(tabela.getSelectionModel().getSelectedItem() != null)
            new ControllerGerenciarAutor().alterar(tabela, tabela.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        new ControllerGerenciarAutor().novo(tabela);
    }
    
}
