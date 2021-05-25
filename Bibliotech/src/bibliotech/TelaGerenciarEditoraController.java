package bibliotech;

import bd.entidades.Editora;
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

public class TelaGerenciarEditoraController implements Initializable {
    static TelaGerenciarEditoraController instancia;
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


    public TelaGerenciarEditoraController() {
    }
    public static TelaGerenciarEditoraController retorna(){
        if (instancia == null){
            instancia = new TelaGerenciarEditoraController();
            return (instancia);
        }
        return null;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        
        carregarTabela("");
    }    

    private void carregarTabela(String filtro){
        Editora e = new Editora();
        
        List<Editora> editoras = e.buscar(filtro);
        tabela.setItems(FXCollections.observableArrayList(editoras));
    }
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        String filtro = "upper(edt_nome) like '%#%'";
        
        filtro = filtro.replace("#", txFiltro.getText().toUpperCase());
        
        if(txFiltro.getText().isEmpty())
            carregarTabela("");
        else
            carregarTabela(filtro);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txFiltro.getScene().getWindow().hide();
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão de uma Editora");
        alert.setHeaderText("Confirma exclusão?");
        alert.setContentText("Tem certeza que deseja excluir a editora: "+tabela.getSelectionModel().getSelectedItem().getNome()+" ?");
        Optional<ButtonType> result =  alert.showAndWait();
        
        if(result.get() == ButtonType.OK){
            Editora e = tabela.getSelectionModel().getSelectedItem();
            e.excluir();
            carregarTabela("");
        }
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(TelaCadastrarEditoraController.retorna() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaCadastrarEditora.fxml"));
            Parent root = (Parent) loader.load();
            TelaCadastrarEditoraController ctr = loader.getController();
            ctr.setDados(tabela.getSelectionModel().getSelectedItem().getCodigo(), tabela.getSelectionModel().getSelectedItem().getNome(), tabela.getSelectionModel().getSelectedItem().getCnpj());

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Alterar Editora");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            TelaCadastrarEditoraController.instancia = null;
            carregarTabela("");
        }
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        if(TelaCadastrarEditoraController.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("TelaCadastrarEditora.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Editora");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            TelaCadastrarEditoraController.instancia = null;
            carregarTabela("");
        }
    }
    
}
