package bibliotech;

import bd.entidades.Cliente;
import bd.entidades.Exemplar_Emprestimo;
import bd.entidades.Multa;
import controller.ControllerGerarMulta;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * @author Vitor Guilhermo
 */
public class TelaGerarMultaControllerController implements Initializable {

    @FXML
    private TextField txFiltro;
    @FXML
    private ComboBox<String> cbFiltro;
    @FXML
    private TableView<Exemplar_Emprestimo> tabela;
    @FXML
    private TableColumn<Exemplar_Emprestimo, Integer> colCodigo;
    @FXML
    private TableColumn<Exemplar_Emprestimo, LocalDate> colDtDevolucao;
    @FXML
    private TableColumn<Exemplar_Emprestimo, Multa> colMulta;
    @FXML
    private TableColumn<Exemplar_Emprestimo, Integer> colCodigoExe;
    @FXML
    private TableColumn<Exemplar_Emprestimo, String> colNomeExe;
    @FXML
    private TableColumn<Exemplar_Emprestimo, LocalDate> colDtEmpEmp;
    @FXML
    private TableColumn<Exemplar_Emprestimo, LocalDate> colDtDevEmp;
    @FXML
    private TableColumn<Exemplar_Emprestimo, Cliente> colClienteEmp;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colDtDevolucao.setCellValueFactory(new PropertyValueFactory<>("dataDevolucaoR"));
        colMulta.setCellValueFactory(new PropertyValueFactory<>("multa"));
        colCodigoExe.setCellValueFactory(new PropertyValueFactory<>("exemplar"));
        colNomeExe.setCellValueFactory(new PropertyValueFactory<>("exemplar"));
        colDtEmpEmp.setCellValueFactory(new PropertyValueFactory<>("emprestimo"));
        colDtDevEmp.setCellValueFactory(new PropertyValueFactory<>("emprestimo"));
        colClienteEmp.setCellValueFactory(new PropertyValueFactory<>("emprestimo"));
        
        colCodigoExe.setCellValueFactory(new Callback<CellDataFeatures<Exemplar_Emprestimo, Integer>,ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(CellDataFeatures<Exemplar_Emprestimo, Integer> param) {
                return new SimpleObjectProperty<Integer>(param.getValue().getExemplar().getCodigo());
            }
        });
        
        colDtEmpEmp.setCellValueFactory(new Callback<CellDataFeatures<Exemplar_Emprestimo, LocalDate>,ObservableValue<LocalDate>>() {
            @Override
            public ObservableValue<LocalDate> call(CellDataFeatures<Exemplar_Emprestimo, LocalDate> param) {
                return new SimpleObjectProperty<LocalDate>(param.getValue().getEmprestimo().getData());
            }
        });
        
        colDtDevEmp.setCellValueFactory(new Callback<CellDataFeatures<Exemplar_Emprestimo, LocalDate>,ObservableValue<LocalDate>>() {
            @Override
            public ObservableValue<LocalDate> call(CellDataFeatures<Exemplar_Emprestimo, LocalDate> param) {
                return new SimpleObjectProperty<LocalDate>(param.getValue().getEmprestimo().getDataDevolucaoP());
            }
        });

        colClienteEmp.setCellValueFactory(new Callback<CellDataFeatures<Exemplar_Emprestimo, Cliente>,ObservableValue<Cliente>>() {
            @Override
            public ObservableValue<Cliente> call(CellDataFeatures<Exemplar_Emprestimo, Cliente> param) {
                return new SimpleObjectProperty<Cliente>(param.getValue().getEmprestimo().getCliente());
            }
        });

        
        cbFiltro.getItems().add("Código Exemplar");
        cbFiltro.getItems().add("Nome Cliente");
        
        
        cbFiltro.getSelectionModel().select(0);
        
        ControllerGerarMulta.carregaTabela(tabela, "");
    }    

    @FXML
    private void evtBuscar(ActionEvent event) {
        ControllerGerarMulta.buscar(tabela, cbFiltro, txFiltro);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        ControllerGerarMulta.cancelar( txFiltro.getScene().getWindow() );
    }

    @FXML
    private void evtGerar(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null)
            ControllerGerarMulta.gerarMulta(tabela);
    }
    
}
