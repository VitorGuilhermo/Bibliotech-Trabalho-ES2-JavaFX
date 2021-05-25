package bibliotech;

import bd.entidades.Cliente;
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


public class TelaGerenciarClienteController implements Initializable {
    static TelaGerenciarClienteController instancia;
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

    
    public TelaGerenciarClienteController() {
    }
    public static TelaGerenciarClienteController retorna(){
        if (instancia == null){
            instancia = new TelaGerenciarClienteController();
            return (instancia);
        }
        return null;
    }
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

    private void carregarTabela(String filtro){
        Cliente c = new Cliente();
        
        List<Cliente> clientes = c.buscar(filtro);
        tabela.setItems(FXCollections.observableArrayList(clientes));
    }
    
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        String filtro = "upper(cli_nome) like '%#%'";
        
        filtro = filtro.replace("#", txFiltrar.getText().toUpperCase());
        
        if(txFiltrar.getText().isEmpty())
            carregarTabela("");
        else
            carregarTabela(filtro);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txFiltrar.getScene().getWindow().hide();
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão de um Cliente");
        alert.setHeaderText("Confirma exclusão?");
        alert.setContentText("Tem certeza que deseja excluir o cliente: "+tabela.getSelectionModel().getSelectedItem().getNome()+" com documento:"+tabela.getSelectionModel().getSelectedItem().getDocumento()+" ?");
        Optional<ButtonType> result =  alert.showAndWait();
        
        if(result.get() == ButtonType.OK){
            Cliente c = tabela.getSelectionModel().getSelectedItem();
            c.excluir();
            carregarTabela("");
        }
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(TelaCadastrarClienteController.retorna() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaCadastrarCliente.fxml"));
            Parent root = (Parent) loader.load();
            TelaCadastrarClienteController ctr = loader.getController();
            ctr.setDados(tabela.getSelectionModel().getSelectedItem().getCodigo(), tabela.getSelectionModel().getSelectedItem().getNome(),
                    tabela.getSelectionModel().getSelectedItem().getDocumento(), tabela.getSelectionModel().getSelectedItem().getEndereco(),
                    tabela.getSelectionModel().getSelectedItem().getTelefone(), tabela.getSelectionModel().getSelectedItem().getSexo(),
                    tabela.getSelectionModel().getSelectedItem().getDataNasc()
                    );

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Alterar Cliente");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            TelaCadastrarClienteController.instancia = null;
            carregarTabela("");
        }
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        if(TelaCadastrarClienteController.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("TelaCadastrarCliente.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Cliente");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            TelaCadastrarClienteController.instancia = null;
            carregarTabela("");
        }
    }
    
}
