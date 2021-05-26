package bibliotech;

import bd.entidades.Bibliotecario;
import bd.util.Banco;
import bd.util.Conexao;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Vitor Guilhermo
 */
public class TelaPrincipalController implements Initializable {
    static TelaPrincipalController instancia;
    @FXML
    private Label lblNomeUser;
    private Bibliotecario bib;

    
    public TelaPrincipalController() {
    }
    public static TelaPrincipalController retorna(){
        if (instancia == null){
            instancia = new TelaPrincipalController();
            return (instancia);
        }
        return null;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setDados(Bibliotecario bib){
        this.bib = bib;
        Conexao con = Banco.getCon();
        lblNomeUser.setText(bib.getNomeBibliotecario(con));
    }
    
    @FXML
    private void evtManipularTitulo(ActionEvent event) throws IOException {
        if(TelaGerenciarTituloController.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("TelaGerenciarTitulo.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Gerenciar Título");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            TelaGerenciarTituloController.instancia = null;
        }
    }

    @FXML
    private void evtManipularAssunto(ActionEvent event) throws IOException {
        if(GerenciarAssuntoController.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("GerenciarAssunto.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Gerenciar Assunto");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            GerenciarAssuntoController.instancia = null;
        }
    }

    @FXML
    private void evtManipularEditora(ActionEvent event) throws IOException {
        if(TelaGerenciarEditoraController.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("TelaGerenciarEditora.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Gerenciar Editora");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            TelaGerenciarEditoraController.instancia = null;
        }
    }

    @FXML
    private void evtManipularAutor(ActionEvent event) throws IOException {
        if(TelaGerenciarAutorController.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("TelaGerenciarAutor.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Gerenciar Autor");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            TelaGerenciarAutorController.instancia = null;
        }
    }

    @FXML
    private void evtManipularGenero(ActionEvent event) throws IOException {
        if(TelaGerenciarGeneroController.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("TelaGerenciarGenero.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Gerenciar Gênero");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            TelaGerenciarGeneroController.instancia = null;
        }
    }

    @FXML
    private void evtManipularCliente(ActionEvent event) throws IOException {
        if(TelaGerenciarClienteController.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("TelaGerenciarCliente.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Gerenciar Cliente");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            TelaGerenciarClienteController.instancia = null;
        }
    }

    @FXML
    private void evtRetirarLivro(ActionEvent event) throws IOException {
        if(TelaRetirarLivroController.retorna() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaRetirarLivro.fxml"));
            Parent root = (Parent) loader.load();
            TelaRetirarLivroController ctr = loader.getController();

            ctr.setDados(bib);

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Retirar livros do acervo da biblioteca");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            TelaRetirarLivroController.instancia = null;
        }
    }

    @FXML
    private void evtEfetuarEmprestimo(ActionEvent event) throws IOException {
        if(TelaEfetuarEmprestimoController.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("TelaEfetuarEmprestimo.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Efetuar Empréstimo");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            TelaEfetuarEmprestimoController.instancia = null;
        }
    }

    public void gerarRelatorio(String sql, String relat) { //GERAÇÃO DE RELATÓRIOS
        try {  
            ResultSet rs = Banco.getCon().consultar(sql); 
            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
            //chama o relatório
            String jasperPrint = JasperFillManager.fillReportToFile(relat, null, jrRS);
            JasperViewer viewer = new JasperViewer(jasperPrint, false, false);
            
            viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);//maximizado
            viewer.setVisible(true);
        } 
        catch (JRException erro){
            erro.printStackTrace();
        }
    }
    @FXML
    private void evtRelExemp(ActionEvent event) {
        gerarRelatorio("select titulo.tit_nome, exemplar.exe_cod, exemplar.exe_situacao from exemplar inner join titulo on exemplar.tit_cod = titulo.tit_cod", "MyReports/rel_exemplares.jasper");
    }

    @FXML
    private void evtRelTitulo(ActionEvent event) {
        gerarRelatorio("select titulo.tit_cod, titulo.tit_nome, genero.gen_nome, editora.edt_nome, titulo.tit_datapublic, titulo.tit_qtdeexe from titulo, genero, editora where genero.gen_cod = titulo.gen_cod and editora.edt_cod = titulo.edt_cod order by titulo.tit_nome", "MyReports/rel_titulo.jasper");
    }

    @FXML
    private void evtRelAutor(ActionEvent event) {
        gerarRelatorio("select * from autor order by autor.aut_nome", "MyReports/rel_autor.jasper");
    }

    @FXML
    private void evtRelAssunto(ActionEvent event) {
        gerarRelatorio("select distinct assunto.ast_nome, titulo.tit_cod, titulo.tit_nome, titulo.tit_datapublic from titulo inner join assunto_titulo on titulo.tit_cod = assunto_titulo.titulo_tit_cod inner join assunto on assunto.ast_cod = assunto_titulo.assunto_asn_cod order by titulo.tit_nome", "MyReports/rel_tit_ast.jasper");
    }
    
}
