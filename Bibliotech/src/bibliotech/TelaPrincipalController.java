package bibliotech;

import bd.util.Banco;
import bd.util.Conexao;
import controller.ControllerHomeBib;
import controller.SingletonBibliotecario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Vitor Guilhermo
 */
public class TelaPrincipalController implements Initializable {   
    @FXML
    private Label lblNomeUser;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setDados(){
        Conexao con = Banco.getCon();
        lblNomeUser.setText(SingletonBibliotecario.getInstance().getNomeBibliotecario(con));
    }
    
    @FXML
    private void evtManipularTitulo(ActionEvent event) throws IOException {
        ControllerHomeBib.retorna().manipularTitulo();
    }

    @FXML
    private void evtManipularAssunto(ActionEvent event) throws IOException {
        ControllerHomeBib.retorna().manipularAssunto();
    }

    @FXML
    private void evtManipularEditora(ActionEvent event) throws IOException {
        ControllerHomeBib.retorna().manipularEditora();
    }

    @FXML
    private void evtManipularAutor(ActionEvent event) throws IOException {
        ControllerHomeBib.retorna().manipularAutor();
    }

    @FXML
    private void evtManipularGenero(ActionEvent event) throws IOException {
        ControllerHomeBib.retorna().manipularGenero();
    }

    @FXML
    private void evtManipularCliente(ActionEvent event) throws IOException {
        ControllerHomeBib.retorna().manipularCliente();
    }

    @FXML
    private void evtRetirarLivro(ActionEvent event) throws IOException {
        ControllerHomeBib.retorna().retirarLivro();
    }

    @FXML
    private void evtEfetuarEmprestimo(ActionEvent event) throws IOException {
        ControllerHomeBib.retorna().efetuarEmprestimo();
    }

    @FXML
    private void evtAdicionarExemplar(ActionEvent event) throws IOException {
        ControllerHomeBib.retorna().adicionarExemplar();
    }
    
    @FXML
    private void evtGerarMulta(ActionEvent event) throws IOException {
        ControllerHomeBib.retorna().gerarMulta();
    }
    
    @FXML
    private void evtRelExemp(ActionEvent event) {
        ControllerHomeBib.retorna().gerarRelatorio("select titulo.tit_nome, exemplar.exe_cod, exemplar.exe_situacao from exemplar inner join titulo on exemplar.tit_cod = titulo.tit_cod", "MyReports/rel_exemplares.jasper");
    }

    @FXML
    private void evtRelTitulo(ActionEvent event) {
        ControllerHomeBib.retorna().gerarRelatorio("select titulo.tit_cod, titulo.tit_nome, genero.gen_nome, editora.edt_nome, titulo.tit_datapublic, titulo.tit_qtdeexe from titulo, genero, editora where genero.gen_cod = titulo.gen_cod and editora.edt_cod = titulo.edt_cod order by titulo.tit_nome", "MyReports/rel_titulo.jasper");
    }

    @FXML
    private void evtRelAutor(ActionEvent event) {
        ControllerHomeBib.retorna().gerarRelatorio("select * from autor order by autor.aut_nome", "MyReports/rel_autor.jasper");
    }

    @FXML
    private void evtRelAssunto(ActionEvent event) {
        ControllerHomeBib.retorna().gerarRelatorio("select distinct assunto.ast_nome, titulo.tit_cod, titulo.tit_nome, titulo.tit_datapublic from titulo inner join assunto_titulo on titulo.tit_cod = assunto_titulo.titulo_tit_cod inner join assunto on assunto.ast_cod = assunto_titulo.assunto_asn_cod order by titulo.tit_nome", "MyReports/rel_tit_ast.jasper");
    }
 
    
}
