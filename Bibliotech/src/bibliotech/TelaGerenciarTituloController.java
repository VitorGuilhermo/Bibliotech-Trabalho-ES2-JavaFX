package bibliotech;

import bd.dal.TituloDAL;
import bd.entidades.Assunto_Titulo;
import bd.entidades.Autor_Titulo;
import bd.entidades.Editora;
import bd.entidades.Genero;
import bd.entidades.Titulo;
import bd.util.Banco;
import bd.util.Conexao;
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

public class TelaGerenciarTituloController implements Initializable {
    static TelaGerenciarTituloController instancia;
    @FXML
    private TextField txFiltro;
    @FXML
    private TableView<Titulo> tabela;
    @FXML
    private TableColumn<Titulo, Integer> colCodigo;
    @FXML
    private TableColumn<Titulo, String> colTitulo;
    @FXML
    private TableColumn<Titulo, Editora> colEditora;
    @FXML
    private TableColumn<Titulo, LocalDate> colDataImp;
    @FXML
    private TableColumn<Titulo, Integer> colQtdeExe;
    @FXML
    private TableColumn<Titulo, Genero> colGenero;

    
    public TelaGerenciarTituloController() {
    }
    public static TelaGerenciarTituloController retorna(){
        if (instancia == null){
            instancia = new TelaGerenciarTituloController();
            return (instancia);
        }
        return null;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colEditora.setCellValueFactory(new PropertyValueFactory<>("editora"));
        colDataImp.setCellValueFactory(new PropertyValueFactory<>("dataPubli"));
        colQtdeExe.setCellValueFactory(new PropertyValueFactory<>("qtdeExemplares"));
        
        carregarTabela("");
    }    

    private void carregarTabela(String filtro){
        TituloDAL dal = new TituloDAL();
        Conexao con = Banco.getCon();
        List<Titulo> titulos = dal.get(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(titulos));
    }
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        String filtro = "upper(tit_nome) like '%#%'";
        
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
    private void evtAlterar(ActionEvent event) throws IOException {
        if(TelaCadastrarTituloController.retorna() != null){
            Conexao con = Banco.getCon();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaCadastrarTitulo.fxml"));
            Parent root = (Parent) loader.load();
            TelaCadastrarTituloController ctr = loader.getController();

            ctr.setDados(tabela.getSelectionModel().getSelectedItem().getCodigo(), tabela.getSelectionModel().getSelectedItem().getNome(), new Autor_Titulo().buscar(con, "titulo_tit_cod="+tabela.getSelectionModel().getSelectedItem().getCodigo()),
                tabela.getSelectionModel().getSelectedItem().getGenero(), new Assunto_Titulo().buscar(con, "titulo_tit_cod="+tabela.getSelectionModel().getSelectedItem().getCodigo()), tabela.getSelectionModel().getSelectedItem().getEditora(),
                tabela.getSelectionModel().getSelectedItem().getQtdeExemplares(), tabela.getSelectionModel().getSelectedItem().getDataPubli(), tabela.getSelectionModel().getSelectedItem().getDataReg());

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Alterar Título");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            TelaCadastrarTituloController.instancia = null;
            carregarTabela("");
        }
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        if(TelaCadastrarTituloController.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("TelaCadastrarTitulo.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Título");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            TelaCadastrarTituloController.instancia = null;
            carregarTabela("");
        }
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exclusão de um Título");
            alert.setHeaderText("Confirma exclusão?");
            alert.setContentText("Tem certeza que deseja excluir o título: "+tabela.getSelectionModel().getSelectedItem().getNome()+" ?");
            Optional<ButtonType> result =  alert.showAndWait();
            
            
            if(result.get() == ButtonType.OK){
                Conexao con = Banco.getCon();
                Titulo t = tabela.getSelectionModel().getSelectedItem();
                if(t.getQtdeExemplares() == 0){
                    new Autor_Titulo().excluir(con, t.getCodigo());
                    new Assunto_Titulo().excluir(con, t.getCodigo());
                    t.excluir(con, t.getCodigo());
                    carregarTabela("");
                }
                else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Falha");
                    alert.setHeaderText("Erro: Não é possível excluir títulos com quantidades de exemplares maiores que 0!");
                    alert.setContentText("Erro na exclusão!");
                }
            }
        }
    }
    
}
