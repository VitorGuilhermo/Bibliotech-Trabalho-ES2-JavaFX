package bibliotech;

import bd.entidades.Cliente;
import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerCadastrarCliente;
import controller.ControllerGerenciarClientes;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
        
        carregarTabela("");
    }    
    
    public void carregarTabela(String filtro){
        Cliente c = new Cliente();
        Conexao con = Banco.getCon();
        
        List<Cliente> clientes = c.buscar(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(clientes));
    }
    
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        String filtro = ControllerGerenciarClientes.getInstance().buscar(txFiltrar.getText(), "cli_nome");
        carregarTabela(filtro);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txFiltrar.getScene().getWindow().hide();
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            boolean excluiu;
            Cliente c = tabela.getSelectionModel().getSelectedItem();
            excluiu = ControllerGerenciarClientes.getInstance().excluir(c.getCodigo(), c.getNome(), c.getDocumento());
            if(excluiu)
                carregarTabela("");
        }
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            if(ControllerCadastrarCliente.getInstance() == null && ControllerCadastrarCliente.retorna() != null){
                Cliente c = tabela.getSelectionModel().getSelectedItem();
                ControllerGerenciarClientes.getInstance().alterar(c.getCodigo(), c.getNome(), c.getDocumento(), c.getEndereco(), c.getTelefone(), c.getSexo(), c.getDataNasc());
                carregarTabela("");
            }
        }
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        if(ControllerCadastrarCliente.getInstance() == null && ControllerCadastrarCliente.retorna() != null){    
            ControllerGerenciarClientes.getInstance().novo();
            carregarTabela("");
        }
    }

    @FXML
    private void evtDesativar(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            boolean desativou;
            Cliente c = tabela.getSelectionModel().getSelectedItem();
            desativou = ControllerGerenciarClientes.getInstance().desativar(c.getCodigo(), c.getNome(), c.getDocumento());
            if(desativou)
                carregarTabela("");
        }
    }
    
}
