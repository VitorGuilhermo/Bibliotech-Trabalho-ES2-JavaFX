package bibliotech;

import controller.ControllerAdicionarExemplarCont;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Vitor Guilhermo
 */
public class TelaAdicionarExemplarContController implements Initializable {

    @FXML
    private TextField txTitulo;
    @FXML
    private TextField txDataImp;
    @FXML
    private TextField txQtdeExemplar;
    @FXML
    private Spinner<Integer> spQtde;
    private int cod;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spQtde.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000000, 1));

    }    

    public void setDados(int cod, String titulo, LocalDate dataImp, int qtde) {
        this.cod = cod;
        txTitulo.setText(titulo);
        txDataImp.setText(dataImp.toString());
        txQtdeExemplar.setText(""+qtde);
    }
    
    @FXML
    private void evtCancelar(ActionEvent event) {
        txTitulo.getScene().getWindow().hide();
    }

    @FXML
    private void evtAdicionar(ActionEvent event) {
        ControllerAdicionarExemplarCont.retorna().adicionar(cod, spQtde.getValue());
        evtCancelar(event);
    }
    
}
