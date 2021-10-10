package bibliotech;

import bd.entidades.Autor;
import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerCadastrarAutor;
import controller.ControllerGerenciarAutor;
import java.io.IOException;
import java.net.URL;
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
        
        carregarTabela("");
    }    

    public void carregarTabela(String filtro){    //FAZER AQ RETORNAR UMA LISTA
        Conexao con = Banco.getCon();
        Autor a = new Autor();
        
        List<Autor> autores = a.buscar(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(autores));
    }
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        String filtro = ControllerGerenciarAutor.getInstance().buscar(txFiltro.getText(), "aut_nome");
        carregarTabela(filtro);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txFiltro.getScene().getWindow().hide();
    }   

    @FXML
    private void evtExcluir(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Autor a = tabela.getSelectionModel().getSelectedItem();
            boolean excluiu = ControllerGerenciarAutor.getInstance().excluir(tabela, a.getCodigo(), a.getNome());
            if(excluiu)
                carregarTabela("");
        }
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Autor a = tabela.getSelectionModel().getSelectedItem();
            if (ControllerCadastrarAutor.getInstance() == null && ControllerCadastrarAutor.retorna() != null) {
                ControllerGerenciarAutor.retorna().alterar(a.getCodigo(), a.getNome());
                carregarTabela("");
            }
        }
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        if(ControllerCadastrarAutor.getInstance() == null && ControllerCadastrarAutor.retorna() != null){
            ControllerGerenciarAutor.retorna().novo();
            carregarTabela("");
        }
    }
    
}
