package bibliotech;

import bd.dal.AssuntoDAL;
import bd.dal.AutorDAL;
import bd.entidades.Assunto;
import bd.entidades.Autor;
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

     private void carregarTabela(String filtro){
        AutorDAL dal = new AutorDAL();
        
        List<Autor> autores = dal.get(filtro);
        tabela.setItems(FXCollections.observableArrayList(autores));
    }
     
    @FXML
    private void evtBuscar(ActionEvent event) {
        String filtro = "upper(aut_nome) like '%#%'";
        
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
        alert.setTitle("Exclusão de um Autor");
        alert.setHeaderText("Confirma exclusão?");
        alert.setContentText("Tem certeza que deseja excluir o/a autor(a): "+tabela.getSelectionModel().getSelectedItem().getNome()+" ?");
        Optional<ButtonType> result =  alert.showAndWait();
        
        if(result.get() == ButtonType.OK){
            Autor a = tabela.getSelectionModel().getSelectedItem();
            new AutorDAL().apagar(a.getCodigo());
            carregarTabela("");
        }
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaCadastrarAutor.fxml"));
        Parent root = (Parent) loader.load();
        TelaCadastrarAutorController ctr = loader.getController();
        ctr.setDados(tabela.getSelectionModel().getSelectedItem().getCodigo(), tabela.getSelectionModel().getSelectedItem().getNome());
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Alterar Autor");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();
        
        carregarTabela("");
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaCadastrarAutor.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Autor");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();
        
        carregarTabela("");
    }
    
}