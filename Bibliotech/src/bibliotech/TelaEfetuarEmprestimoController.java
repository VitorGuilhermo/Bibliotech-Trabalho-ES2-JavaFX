package bibliotech;

import bd.entidades.Cliente;
import bd.entidades.Emprestimo;
import bd.entidades.Exemplar;
import bd.entidades.Exemplar_Emprestimo;
import bd.util.Banco;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TelaEfetuarEmprestimoController implements Initializable {
    static TelaEfetuarEmprestimoController instancia;
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
    private List<Exemplar> exemplares = new ArrayList<>();
    private int qtdeLivrosJaEmprestados;
    
    
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
    }    
    public TelaEfetuarEmprestimoController() {
    }
    public static TelaEfetuarEmprestimoController retorna(){
        if (instancia == null){
            instancia = new TelaEfetuarEmprestimoController();
            return (instancia);
        }
        return null;
    }
    public void carregaTabela(String filtro){
        Exemplar e = new Exemplar();
        
        List<Exemplar> exememplares = e.buscaExemplares(filtro);
        tabela.setItems(FXCollections.observableArrayList(exememplares));
    }
    public void carregaListaExemplares(){
        listaExe.setItems(FXCollections.observableArrayList( exemplares ));
    }
    @FXML
    private void evtNovoCliente(ActionEvent event) throws IOException {
        if(TelaCadastrarClienteController.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("TelaCadastrarCliente.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Cliente");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            TelaCadastrarClienteController.instancia = null;
        }
    }

    @FXML
    private void evtBuscarExe(ActionEvent event) {
        String filtro = "upper(tit_nome) like '%#%'";
        
        filtro = filtro.replace("#", txFiltroExe.getText().toUpperCase());
        
        if(txFiltroExe.getText().isEmpty())
            carregaTabela("");
        else
            carregaTabela(filtro);
    }

    @FXML
    private void evtAdicionarExe(ActionEvent event) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            if(exemplares.size()+qtdeLivrosJaEmprestados < 5){
               if(!exemplares.contains(tabela.getSelectionModel().getSelectedItem())){
                exemplares.add(tabela.getSelectionModel().getSelectedItem());
                carregaListaExemplares();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro: Não é possível adicionar Livros Repetidos ou com o Mesmo Código.");
                    alert.showAndWait();
                } 
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Erro: Um cliente só pode pegar 5 livros por vez!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txCodigo.getScene().getWindow().hide();
    }

    @FXML
    private void evtFinalizar(ActionEvent event) {
        if(!txCodigo.getText().isEmpty()){
            boolean erro;
            if(!exemplares.isEmpty()){
                Cliente cliente = new Cliente();
                cliente = cliente.buscarCliente(Integer.parseInt(txCodigo.getText()));
                
                Emprestimo emp = new Emprestimo(LocalDate.now(), LocalDate.now().plusDays(7), exemplares.size(), cliente, exemplares);
                //grava empréstimo
                erro = emp.gravar();
                if(erro){
                    //grava exemplar_emprestimo
                    emp.setCodigo(Banco.getCon().getMaxPK("emprestimo", "emp_cod"));
                    for(Exemplar e : exemplares){
                        boolean flag;                    
                        Exemplar_Emprestimo exempEmp = new Exemplar_Emprestimo(LocalDate.now().plusDays(7), 0., e, emp);
                        flag = exempEmp.gravar();
                        if(!flag)
                            erro = false;
                        //altera situacao exemplar
                        e.setSituacao(!e.isSituacao());
                        e.alteraSituacao();
                    }
                }
                if(!erro){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro ao gravar o empréstimo!");
                    alert.showAndWait();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Empréstimo efetuado!");
                    alert.showAndWait();
                    
                    txCodigo.getScene().getWindow().hide();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: Não é possível gerar um empréstimo vazio!");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Antes de efetuar o empréstimo insira um cliente!");
            alert.showAndWait();
        }
    }

    @FXML
    private void evtRemoverExe(ActionEvent event) {
        if(listaExe.getSelectionModel().getSelectedItem() != null){
            exemplares.remove(listaExe.getSelectionModel().getSelectedItem());
            carregaListaExemplares();
        }
    }

    @FXML
    private void evtBuscar(ActionEvent event) {
        Cliente cli = new Cliente();
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
            
        cli = cli.getCliente(sql+filtro+"'");
        if(cli != null){
            txCodigo.setText(""+cli.getCodigo());
            txNome.setText(cli.getNome());
            txDocumento.setText(cli.formataCpf(cli.getDocumento()));
            
            qtdeLivrosJaEmprestados = cli.getQtdeLivros();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Cliente inexistente. Verifique os dados!");
            alert.showAndWait();
        }
    }
    
}
