package bibliotech;

import bd.dal.ExemplarDAL;
import bd.entidades.Baixa;
import bd.entidades.Bibliotecario;
import bd.entidades.Exemplar;
import bd.entidades.Titulo;
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
 * FXML Controller class
 *
 * @author Windows 10
 */
public class TelaRetirarLivroContController implements Initializable {
    static TelaRetirarLivroContController instancia;
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
    private Bibliotecario bibliotecario;
    private int codTit;
    
    
    
    
    public TelaRetirarLivroContController() {
    }
    public static TelaRetirarLivroContController retorna(){
        if (instancia == null){
            instancia = new TelaRetirarLivroContController();
            return (instancia);
        }
        return null;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));
    }    

    public void setDados(Titulo titulo, Bibliotecario bib){
        codTit = titulo.getCodigo();
        txTitulo.setText(titulo.getNome());
        txDtPubl.setText(""+titulo.getDataReg());
        bibliotecario = bib;
        carregarTabela("tit_cod="+titulo.getCodigo());
    }
    private void carregarTabela(String filtro){
        ExemplarDAL dal = new ExemplarDAL();
        
        List<Exemplar> exemplares = dal.get(filtro);
        tabela.setItems(FXCollections.observableArrayList(exemplares));
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txTitulo.getScene().getWindow().hide();
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null && !taMotivo.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exclusão de um Exemplar");
            alert.setHeaderText("Confirma exclusão?");
            alert.setContentText("Tem certeza que deseja excluir o exemplar: "+tabela.getSelectionModel().getSelectedItem().getTitulo().getNome()+" ?");
            Optional<ButtonType> result =  alert.showAndWait();

            
            if(result.get() == ButtonType.OK){
                Titulo tit = new Titulo();
                tit.setCodigo(codTit);
                if(tit.pesquisar().getQtdeExemplares() > 0){
                    //primeiro da a baixa
                    Baixa baixa = new Baixa(txTitulo.getText(), LocalDate.now(), taMotivo.getText(), bibliotecario);
                    baixa.gravar();
                    //exclui o exemplar
                    Exemplar exe = new Exemplar(tabela.getSelectionModel().getSelectedItem().getCodigo(), tabela.getSelectionModel().getSelectedItem().isSituacao(), tabela.getSelectionModel().getSelectedItem().getTitulo());
                    exe.excluir();
                    //diminui quantidade de exemplares do titulo
                    tit.decrementaQtdeExemplar();
                    //atualiza tabela
                    carregarTabela("tit_cod=" + tit.getCodigo());
                    taMotivo.clear();  
                }
                else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro: não é possível excluir um título com 0 exemplares");
                    alert.showAndWait();
                }
            }
        }
    }
    
}
