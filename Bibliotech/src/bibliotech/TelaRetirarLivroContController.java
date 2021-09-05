package bibliotech;

import bd.entidades.Baixa;
import bd.entidades.Bibliotecario;
import bd.entidades.Exemplar;
import bd.entidades.Titulo;
import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerRetirarLivroCont;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Vitor Guilhermo
 */
public class TelaRetirarLivroContController implements Initializable {
    @FXML
    private TableView<Exemplar> tabela;
    @FXML
    private TableColumn<Exemplar, Integer> colCod;
    @FXML
    private TableColumn<Exemplar, Boolean> colSituacao;
    @FXML
    private TextField txTitulo;
    @FXML
    private TextField txDtPubl;
    @FXML
    private TextArea taMotivo;
    private int codTit;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));
    }
    public void setDados(Titulo titulo){
        codTit = titulo.getCodigo();
        new ControllerRetirarLivroCont().setDados(tabela, txTitulo, txDtPubl, titulo);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerRetirarLivroCont.cancelar( txTitulo.getScene().getWindow() );
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        new ControllerRetirarLivroCont().excluir(tabela, taMotivo, txTitulo, tabela.getSelectionModel().getSelectedItem(), codTit);
    }
    
}
