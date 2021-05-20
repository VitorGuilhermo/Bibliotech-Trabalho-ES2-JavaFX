package bibliotech;

import bd.entidades.Genero;
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


public class TelaGerenciarGeneroController implements Initializable {
    static TelaGerenciarGeneroController instancia;
    @FXML
    private TextField txFiltro;
    @FXML
    private TableView<Genero> tabela;
    @FXML
    private TableColumn<Genero, Integer> colCodigo;
    @FXML
    private TableColumn<Genero, String> colNome;

    
    public TelaGerenciarGeneroController() {
    }
    public static TelaGerenciarGeneroController retorna(){
        if (instancia == null){
            instancia = new TelaGerenciarGeneroController();
            return (instancia);
        }
        return null;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        carregarTabela("");
    }    

    private void carregarTabela(String filtro){
        Genero g = new Genero();
        
        List<Genero> generos = g.buscar(filtro);
        tabela.setItems(FXCollections.observableArrayList(generos));
    }
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        String filtro = "upper(gen_nome) like '%#%'";
        
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
        alert.setTitle("Exclusão de um Gênero");
        alert.setHeaderText("Confirma exclusão?");
        alert.setContentText("Tem certeza que deseja excluir o gênero: "+tabela.getSelectionModel().getSelectedItem().getNome()+" ?");
        Optional<ButtonType> result =  alert.showAndWait();
        
        if(result.get() == ButtonType.OK){
            Genero g = tabela.getSelectionModel().getSelectedItem();
            g.excluir();
            carregarTabela("");
        }
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaCadastrarGenero.fxml"));
        Parent root = (Parent) loader.load();
        TelaCadastrarGeneroController ctr = loader.getController();
        ctr.setDados(tabela.getSelectionModel().getSelectedItem().getCodigo(), tabela.getSelectionModel().getSelectedItem().getNome());
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Alterar Gênero");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();
        
        carregarTabela("");
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaCadastrarGenero.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Gênero");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();
        
        carregarTabela("");
    }
    
}
