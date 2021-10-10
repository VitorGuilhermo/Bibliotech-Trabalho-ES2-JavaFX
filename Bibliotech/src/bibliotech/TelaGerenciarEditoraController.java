package bibliotech;

import bd.entidades.Editora;
import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerCadastrarEditora;
import controller.ControllerGerenciarEditora;
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
        
        carregarTabela("");
    }    
    
    public void carregarTabela(String filtro){
        Editora e = new Editora();
        Conexao con = Banco.getCon();
        
        List<Editora> editoras = e.buscar(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(editoras));
    }
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        String filtro = ControllerGerenciarEditora.getInstance().buscar(txFiltro.getText(), "edt_nome");
        carregarTabela(filtro);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txFiltro.getScene().getWindow().hide();
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            boolean excluiu;
            Editora e = tabela.getSelectionModel().getSelectedItem();
            excluiu = ControllerGerenciarEditora.getInstance().excluir(e.getCodigo(), e.getNome());
            if(excluiu)
                carregarTabela("");
        }
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            if(ControllerCadastrarEditora.getInstance() == null && ControllerCadastrarEditora.retorna() != null){
                Editora e = tabela.getSelectionModel().getSelectedItem();
                ControllerGerenciarEditora.retorna().alterar(e.getCodigo(), e.getNome(), e.getCnpj());
                carregarTabela("");
            }
        }
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        if(ControllerCadastrarEditora.getInstance() == null && ControllerCadastrarEditora.retorna() != null){
            ControllerGerenciarEditora.retorna().novo();
            carregarTabela("");
        }
    }
    
}
