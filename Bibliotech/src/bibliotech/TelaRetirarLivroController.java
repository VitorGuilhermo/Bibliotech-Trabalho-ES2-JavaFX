package bibliotech;

import bd.entidades.Bibliotecario;
import bd.entidades.Editora;
import bd.entidades.Titulo;
import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerRetirarLivro;
import controller.ControllerRetirarLivroCont;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaRetirarLivroController implements Initializable {
    
    @FXML
    private ComboBox<String> cbConsulta;
    @FXML
    private TextField txFiltro;
    @FXML
    private TableView<Titulo> tabela;
    @FXML
    private TableColumn<Titulo, Integer> colCod;
    @FXML
    private TableColumn<Titulo, String> colTitulo;
    @FXML
    private TableColumn<Titulo, Integer> colQuantidade;
    @FXML
    private TableColumn<Titulo, String> colAutores;
    @FXML
    private TableColumn<Titulo, String> colAssuntos;
    @FXML
    private TableColumn<Titulo, Editora> colEditora;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEditora.setCellValueFactory(new PropertyValueFactory<>("editora"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("qtdeExemplares"));
        colAutores.setCellValueFactory(new PropertyValueFactory<>("autores"));
        colAssuntos.setCellValueFactory(new PropertyValueFactory<>("assuntos"));
        
        cbConsulta.getItems().add("Titulo");
        cbConsulta.getItems().add("Autor");
        cbConsulta.getItems().add("Editora");
        cbConsulta.getItems().add("Assunto");
        cbConsulta.getSelectionModel().select(0);
        
        carregarTabela("", "");
    }    
    public void setDados(Bibliotecario bib){
        ControllerRetirarLivro.bib = bib;
    }
    
    public void carregarTabela(String filtro, String contSql){
        Titulo t = new Titulo();
        Conexao con = Banco.getCon();
        
        List<Titulo> titulos = t.buscarTitulosCompostos(con, filtro, contSql);
        tabela.setItems(FXCollections.observableArrayList(titulos));
    }
    
    @FXML
    private void evtBuscar(ActionEvent event) {
        String filtro = "upper(tit_nome) like '%#%'";
        String col = cbConsulta.getSelectionModel().getSelectedItem();
        String contSql = "";
        
        if(col.equals("Autor")){
            filtro = "upper(aut_nome) like '%#%'";
            contSql = "inner join autor_titulo on titulo.tit_cod = autor_titulo.titulo_tit_cod inner join autor on autor_titulo.autor_aut_cod = autor.aut_cod";
        }
        else if(col.equals("Editora")){
            filtro = "upper(edt_nome) like '%#%'";
            contSql = "inner join editora on titulo.edt_cod = editora.edt_cod";
        }
        else if(col.equals("Assunto")){
            filtro = "upper(ast_nome) like '%#%'";
            contSql = "inner join assunto_titulo on titulo.tit_cod = assunto_titulo.titulo_tit_cod inner join assunto on assunto_titulo.assunto_asn_cod = assunto.ast_cod";
        }
        filtro = filtro.replace("#", txFiltro.getText().toUpperCase());
        
        if(txFiltro.getText().isEmpty())
            carregarTabela("", contSql);
        else
            carregarTabela(filtro, contSql);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txFiltro.getScene().getWindow().hide();
    }

    @FXML
    private void evtConfirmar(ActionEvent event) throws IOException {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            if(ControllerRetirarLivroCont.getInstance() == null && ControllerRetirarLivroCont.retorna() != null){
                ControllerRetirarLivro.retorna().confirmar(tabela.getSelectionModel().getSelectedItem());
                carregarTabela("", "");
            }
        }
    }
    
}
