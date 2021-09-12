package controller;

import bd.entidades.Bibliotecario;
import bd.util.Banco;
import bibliotech.TelaRetirarLivroController;
import java.io.IOException;
import java.sql.ResultSet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;



/**
 * @author Vitor Guilhermo
 */
public class ControllerHomeBib {
    private static ControllerHomeBib instancia;
    private Bibliotecario bib;
    
    
    private ControllerHomeBib() {
    }
    public static ControllerHomeBib retorna(){
        if (instancia == null)
            instancia = new ControllerHomeBib();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerHomeBib getInstance() {
        return instancia;
    }
   

    public void setBib(Bibliotecario bib) {
        this.bib = bib;
    }
    
    public void manipularTitulo() throws IOException {
        if(ControllerGerenciarTitulo.getInstance() == null && ControllerGerenciarTitulo.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaGerenciarTitulo.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Gerenciar Título");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            ControllerGerenciarTitulo.removeInstancia();
        }
    }
    public void manipularAssunto() throws IOException {
        if(ControllerGerenciarAssunto.getInstance() == null && ControllerGerenciarAssunto.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/GerenciarAssunto.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Gerenciar Assunto");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            ControllerGerenciarAssunto.removeInstancia();
        }
    }
    
    public void manipularEditora() throws IOException {
        if(ControllerGerenciarEditora.getInstance() == null && ControllerGerenciarEditora.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaGerenciarEditora.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Gerenciar Editora");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            ControllerGerenciarEditora.removeInstancia();
        }
    }
    
    public void manipularAutor() throws IOException {
        if(ControllerGerenciarAutor.getInstance() == null && ControllerGerenciarAutor.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaGerenciarAutor.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Gerenciar Autor");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            ControllerGerenciarAutor.removeInstancia();
        }
    }
    
    public void manipularGenero() throws IOException {
        if(ControllerGerenciarGenero.getInstance() == null && ControllerGerenciarGenero.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaGerenciarGenero.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Gerenciar Gênero");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            ControllerGerenciarGenero.removeInstancia();
        }
    }
   
    public void manipularCliente() throws IOException {
        if(ControllerGerenciarClientes.getInstance() == null && ControllerGerenciarClientes.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaGerenciarCliente.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Gerenciar Cliente");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            ControllerGerenciarClientes.removeInstancia();
        }
    }
   
    public void retirarLivro() throws IOException {
        if(ControllerRetirarLivro.getInstance() == null && ControllerRetirarLivro.retorna() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaRetirarLivro.fxml"));
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
            
            ControllerRetirarLivro.removeInstancia();
        }
    }
    
    public void efetuarEmprestimo() throws IOException {
        if(ControllerEfetuarEmprestimo.getInstance() == null && ControllerEfetuarEmprestimo.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaEfetuarEmprestimo.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Efetuar Empréstimo");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            ControllerEfetuarEmprestimo.removeInstancia();
        }
    }
    
    public void adicionarExemplar() throws IOException {
        if(ControllerAdicionarExemplar.getInstance() == null && ControllerAdicionarExemplar.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaAdicionarExemplar.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Adicionar novos exemplares");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();
            
            ControllerAdicionarExemplar.removeInstancia();
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
}
