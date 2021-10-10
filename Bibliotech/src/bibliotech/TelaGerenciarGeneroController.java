package bibliotech;

import bd.entidades.Genero;
import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerCadastrarGenero;
import controller.ControllerGerenciarGenero;
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


public class TelaGerenciarGeneroController implements Initializable {
    @FXML
    private TextField txFiltro;
    @FXML
    private TableView<Genero> tabela;
    @FXML
    private TableColumn<Genero, Integer> colCodigo;
    @FXML
    private TableColumn<Genero, String> colNome;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        carregarTabela("");
    }    
    
    public void carregarTabela(String filtro){
        Genero g = new Genero();
        Conexao con = Banco.getCon();
        
        List<Genero> generos = g.buscar(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(generos));
    }
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        String filtro = ControllerGerenciarGenero.getInstance().buscar(txFiltro.getText(), "gen_nome");
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
            Genero g = tabela.getSelectionModel().getSelectedItem();
            excluiu = ControllerGerenciarGenero.getInstance().excluir(g.getCodigo(), g.getNome());
            if(excluiu)
                carregarTabela("");
        }
    }

    @FXML
    private void evtAlterar(ActionEvent event) throws IOException {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            if(ControllerCadastrarGenero.getInstance() == null && ControllerCadastrarGenero.retorna() != null){
                Genero g = tabela.getSelectionModel().getSelectedItem();
                ControllerGerenciarGenero.retorna().alterar(g.getCodigo(), g.getNome());
                carregarTabela("");
            }
        }
    }

    @FXML
    private void evtNovo(ActionEvent event) throws IOException {
        if(ControllerCadastrarGenero.getInstance() == null && ControllerCadastrarGenero.retorna() != null){
            ControllerGerenciarGenero.retorna().novo();
            carregarTabela("");
        }
    }
    
}
