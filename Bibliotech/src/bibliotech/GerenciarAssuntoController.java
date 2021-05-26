package bibliotech;

import bd.entidades.Assunto;
import bd.util.Banco;
import bd.util.Conexao;
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

public class GerenciarAssuntoController implements Initializable {

    static GerenciarAssuntoController instancia;
    @FXML
    private TextField txFiltro;
    @FXML
    private TableView<Assunto> tabela;
    @FXML
    private TableColumn<Assunto, Integer> colCod;
    @FXML
    private TableColumn<Assunto, String> colNome;

    
    public GerenciarAssuntoController() {
    }
    public static GerenciarAssuntoController retorna(){
        if (instancia == null){
            instancia = new GerenciarAssuntoController();
            return (instancia);
        }
        return null;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        carregarTabela("");
    }    

    private void carregarTabela(String filtro){
        Assunto a = new Assunto();
        Conexao con = Banco.getCon();
        
        List<Assunto> assuntos = a.buscar(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(assuntos));
    }
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        String filtro = "upper(ast_nome) like '%#%'";
        
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
        Conexao con = Banco.getCon();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão de um Assunto");
        alert.setHeaderText("Confirma exclusão?");
        alert.setContentText("Tem certeza que deseja excluir o assunto: "+tabela.getSelectionModel().getSelectedItem().getNome()+" ?");
        Optional<ButtonType> result =  alert.showAndWait();
        
        if(result.get() == ButtonType.OK){
            Assunto a = tabela.getSelectionModel().getSelectedItem();
            a.excluir(con);
            carregarTabela("");
        }
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(TelaCadastrarAssuntoController.retorna() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaCadastrarAssunto.fxml"));
            Parent root = (Parent) loader.load();
            TelaCadastrarAssuntoController ctr = loader.getController();
            ctr.setDados(tabela.getSelectionModel().getSelectedItem().getCodigo(), tabela.getSelectionModel().getSelectedItem().getNome());

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Alterar Assunto");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            TelaCadastrarAssuntoController.instancia = null;
            carregarTabela("");
        }
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        if(TelaCadastrarAssuntoController.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("TelaCadastrarAssunto.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Assunto");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            TelaCadastrarAssuntoController.instancia = null;
            carregarTabela("");
        }
    }
    
}
