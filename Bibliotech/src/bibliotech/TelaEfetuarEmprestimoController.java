package bibliotech;

import bd.entidades.Cliente;
import bd.entidades.Exemplar;
import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerCadastrarCliente;
import controller.ControllerEfetuarEmprestimo;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaEfetuarEmprestimoController implements Initializable {
    @FXML
    private TextField txFiltro;
    @FXML
    private ComboBox<String> cbFiltro;
    @FXML
    private TextField txCodigo;
    @FXML
    private TextField txNome;
    @FXML
    private TextField txDocumento;
    @FXML
    private TextField txFiltroExe;
    @FXML
    private TableView<Exemplar> tabela;
    @FXML
    private TableColumn<Exemplar, Integer> colCodigo;
    @FXML
    private TableColumn<Exemplar, String> colTitulo;
    @FXML
    private TableColumn<Exemplar, Boolean> colSituacao;
    @FXML
    private ListView<Exemplar> listaExe;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));
        
        cbFiltro.getItems().add("Nome");
        cbFiltro.getItems().add("Documento");
        cbFiltro.getItems().add("Telefone");
        cbFiltro.getSelectionModel().select(1);
        
        carregaTabela("");
        
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
    
    public void carregaTabela(String filtro){
        Exemplar e = new Exemplar();
        Conexao con = Banco.getCon();
        
        List<Exemplar> exemplares = e.buscaExemplares(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(exemplares));
    }
    public void carregaListaExemplares(){
        listaExe.setItems(FXCollections.observableArrayList( ControllerEfetuarEmprestimo.getInstance().getExemplares() ));
    }
    
    @FXML
    private void evtNovoCliente(ActionEvent event) throws IOException {
        if(ControllerCadastrarCliente.getInstance() == null && ControllerCadastrarCliente.retorna() != null){
            ControllerEfetuarEmprestimo.retorna().novoCliente();
        }
    }

    @FXML
    private void evtBuscarExe(ActionEvent event) {
        String filtro = ControllerEfetuarEmprestimo.getInstance().buscarExe(txFiltroExe.getText());
        carregaTabela(filtro);
    }

    @FXML
    private void evtAdicionarExe(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            boolean adicionou;
            adicionou = ControllerEfetuarEmprestimo.getInstance().adicionarExe(tabela.getSelectionModel().getSelectedItem());
            if(adicionou)
                carregaListaExemplares();
        }
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txCodigo.getScene().getWindow().hide();
    }

    @FXML
    private void evtFinalizar(ActionEvent event) {
        boolean gravou = ControllerEfetuarEmprestimo.retorna().finalizar(txCodigo.getText());
        if(gravou)
            txCodigo.getScene().getWindow().hide();
    }

    @FXML
    private void evtRemoverExe(ActionEvent event) {
        if(listaExe.getSelectionModel().getSelectedItem() != null){
            ControllerEfetuarEmprestimo.getInstance().removerExe(listaExe.getSelectionModel().getSelectedItem());
            carregaListaExemplares();
        }
    }

    @FXML
    private void evtBuscar(ActionEvent event) {
        String sql = "cli_documento='";
        String filtro = txFiltro.getText();
        
        if(cbFiltro.getSelectionModel().getSelectedItem().equals("Nome"))
            sql = "cli_nome='";
        else if(cbFiltro.getSelectionModel().getSelectedItem().equals("Telefone")){
            sql = "cli_telefone='";
            filtro = new Cliente().formataTelefone(txFiltro.getText());
        }
        else
            filtro = new Cliente().formataCpf(txFiltro.getText());
        
        Cliente cli = ControllerEfetuarEmprestimo.retorna().buscarCli(sql, filtro);
        txCodigo.setText(""+cli.getCodigo());
        txNome.setText(cli.getNome());
        txDocumento.setText(cli.formataCpf(cli.getDocumento()));
    }
    
}
