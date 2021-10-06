package bibliotech;

import bd.entidades.Editora;
import bd.entidades.Genero;
import bd.entidades.Titulo;
import controller.ControllerGerenciarTitulo;
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
        
        ControllerGerenciarTitulo.getInstance().carregarTabela(tabela, "");
    }    
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        ControllerGerenciarTitulo.getInstance().buscar(tabela, txFiltro, "tit_nome");
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerGerenciarTitulo.getInstance().cancelar( txFiltro.getScene().getWindow() );
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Titulo t = tabela.getSelectionModel().getSelectedItem();
            ControllerGerenciarTitulo.retorna().alterar(tabela, t.getCodigo(), t.getNome(), t.getGenero(), t.getEditora(), t.getQtdeExemplares(), t.getDataPubli(), t.getDataReg());   
        }
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        ControllerGerenciarTitulo.retorna().novo(tabela);
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        Titulo t = tabela.getSelectionModel().getSelectedItem();
        ControllerGerenciarTitulo.getInstance().excluir(tabela, t.getCodigo(), t.getNome(), t.getQtdeExemplares());
    }
    
}
