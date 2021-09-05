package bibliotech;

import bd.dal.TituloDAL;
import bd.entidades.Assunto_Titulo;
import bd.entidades.Autor_Titulo;
import bd.entidades.Editora;
import bd.entidades.Genero;
import bd.entidades.Titulo;
import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerGerenciarTitulo;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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

public class TelaGerenciarTituloController implements Initializable {
    @FXML
    private TextField txFiltro;
    @FXML
    private TableView<Titulo> tabela;
    @FXML
    private TableColumn<Titulo, Integer> colCodigo;
    @FXML
    private TableColumn<Titulo, String> colTitulo;
    @FXML
    private TableColumn<Titulo, Editora> colEditora;
    @FXML
    private TableColumn<Titulo, LocalDate> colDataImp;
    @FXML
    private TableColumn<Titulo, Integer> colQtdeExe;
    @FXML
    private TableColumn<Titulo, Genero> colGenero;

     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colEditora.setCellValueFactory(new PropertyValueFactory<>("editora"));
        colDataImp.setCellValueFactory(new PropertyValueFactory<>("dataPubli"));
        colQtdeExe.setCellValueFactory(new PropertyValueFactory<>("qtdeExemplares"));
        
        ControllerGerenciarTitulo.carregarTabela(tabela, "");
    }    
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        ControllerGerenciarTitulo.buscar(tabela, txFiltro);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerGerenciarTitulo.cancelar( txFiltro.getScene().getWindow() );
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(tabela.getSelectionModel().getSelectedItem() != null)
            new ControllerGerenciarTitulo().alterar(tabela, tabela.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        new ControllerGerenciarTitulo().novo(tabela);
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        ControllerGerenciarTitulo.excluir(tabela, tabela.getSelectionModel().getSelectedItem());
    }
    
}
