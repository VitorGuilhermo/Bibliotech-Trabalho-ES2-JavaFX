package bibliotech;

import bd.entidades.Assunto;
import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerCadastrarAssunto;
import controller.ControllerGerenciarAssunto;
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
        
        carregarTabela("");
    }    
    
    public void carregarTabela(String filtro){
        Assunto a = new Assunto();
        Conexao con = Banco.getCon();
        
        List<Assunto> assuntos = a.buscar(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(assuntos));
    }
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        String filtro = ControllerGerenciarAssunto.getInstance().buscar(txFiltro.getText(), "ast_nome");
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
            Assunto a = tabela.getSelectionModel().getSelectedItem();
            excluiu = ControllerGerenciarAssunto.getInstance().excluir(a.getCodigo(), a.getNome());
            if(excluiu)
                carregarTabela("");
        }
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            if(ControllerCadastrarAssunto.getInstance() == null && ControllerCadastrarAssunto.retorna() != null){
                Assunto a = tabela.getSelectionModel().getSelectedItem();
                ControllerGerenciarAssunto.retorna().alterar(a.getCodigo(), a.getNome());
                carregarTabela("");
            }
        }
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        if(ControllerCadastrarAssunto.getInstance() == null && ControllerCadastrarAssunto.retorna() != null){
            ControllerGerenciarAssunto.retorna().novo();
            carregarTabela("");
        }
    }
    
}
