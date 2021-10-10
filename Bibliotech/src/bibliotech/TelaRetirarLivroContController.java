package bibliotech;

import bd.entidades.Exemplar;
import bd.entidades.Titulo;
import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerRetirarLivroCont;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
        txTitulo.setText(titulo.getNome());
        txDtPubl.setText(""+titulo.getDataReg());
        
        carregarTabela("titulo.tit_cod="+codTit);
    }
    
    public void carregarTabela(String filtro){
        Exemplar ex = new Exemplar();
        Conexao con = Banco.getCon();
        
        List<Exemplar> exemplares = ex.buscaExemplares(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(exemplares));
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txTitulo.getScene().getWindow().hide();
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null && !taMotivo.getText().isEmpty()){
            int titCod = ControllerRetirarLivroCont.retorna().excluir(tabela.getItems().isEmpty(), taMotivo.getText(), txTitulo.getText(), tabela.getSelectionModel().getSelectedItem(), codTit);
            if(titCod != -1){
                //atualiza tabela
                carregarTabela("titulo.tit_cod=" + titCod);
                taMotivo.clear();
            }

        }
    }

}
