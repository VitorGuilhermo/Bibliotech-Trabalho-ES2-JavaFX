package bibliotech;

import bd.entidades.Exemplar;
import bd.entidades.Titulo;
import controller.ControllerRetirarLivroCont;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
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

        colSituacao.setCellFactory(column -> {
            return new TableCell<Exemplar, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty); //This is mandatory

                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setStyle("");
                    } 
                    else {
                        if(item)
                            setText("Indisponível"); //Put the String data in the cell
                        else
                            setText("Disponível");
                    }
                }
            };
        });
    }

    public void setDados(Titulo titulo) {
        codTit = titulo.getCodigo();
        ControllerRetirarLivroCont.retorna().setDados(tabela, txTitulo, txDtPubl, titulo);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerRetirarLivroCont.retorna().cancelar(txTitulo.getScene().getWindow());
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        ControllerRetirarLivroCont.retorna().excluir(tabela, taMotivo, txTitulo, tabela.getSelectionModel().getSelectedItem(), codTit);
    }

}
