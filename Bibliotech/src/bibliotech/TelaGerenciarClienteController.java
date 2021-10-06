package bibliotech;

import bd.entidades.Cliente;
import controller.ControllerGerenciarClientes;
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


public class TelaGerenciarClienteController implements Initializable {
    @FXML
    private TextField txFiltrar;
    @FXML
    private TableView<Cliente> tabela;
    @FXML
    private TableColumn<Cliente, Integer> colCodigo;
    @FXML
    private TableColumn<Cliente, String> colNome;
    @FXML
    private TableColumn<Cliente, String> colDocumento;
    @FXML
    private TableColumn<Cliente, String> colSexo;
    @FXML
    private TableColumn<Cliente, String> colTelefone;
    @FXML
    private TableColumn<Cliente, LocalDate> colDtNasc;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colDtNasc.setCellValueFactory(new PropertyValueFactory<>("dataNasc"));
        
        ControllerGerenciarClientes.getInstance().carregarTabela(tabela, "");
    }    
    
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        ControllerGerenciarClientes.getInstance().buscar(tabela, txFiltrar, "cli_nome");
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerGerenciarClientes.getInstance().cancelar( txFiltrar.getScene().getWindow() );
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        Cliente c = tabela.getSelectionModel().getSelectedItem();
        ControllerGerenciarClientes.getInstance().excluir(tabela, c.getCodigo(), c.getNome(), c.getDocumento());
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Cliente c = tabela.getSelectionModel().getSelectedItem();
            ControllerGerenciarClientes.retorna().alterar(tabela, c.getCodigo(), c.getNome(), c.getDocumento(), c.getEndereco(), c.getTelefone(), c.getSexo(), c.getDataNasc());
        }
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        ControllerGerenciarClientes.retorna().novo(tabela);
    }

    @FXML
    private void evtDesativar(ActionEvent event) {
        Cliente c = tabela.getSelectionModel().getSelectedItem();
        ControllerGerenciarClientes.getInstance().desativar(tabela, c.getCodigo(), c.getNome(), c.getDocumento());
    }
    
}
