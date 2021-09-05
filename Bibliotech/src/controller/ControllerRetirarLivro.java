package controller;

import bd.entidades.Bibliotecario;
import bd.entidades.Titulo;
import bd.util.Banco;
import bd.util.Conexao;
import bibliotech.TelaRetirarLivroContController;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerRetirarLivro {
    public static ControllerRetirarLivro instancia;
    public static Bibliotecario bib;
    
    public ControllerRetirarLivro() {
    }
    public static ControllerRetirarLivro retorna(){
        if (instancia == null){
            instancia = new ControllerRetirarLivro();
            return (instancia);
        }
        return null;
    }
    
    
    public static void carregarTabela(TableView tabela, String filtro, String contSql){
        Titulo t = new Titulo();
        Conexao con = Banco.getCon();
        List<Titulo> titulos = t.buscarTitulosCompostos(con, filtro, contSql);
        tabela.setItems(FXCollections.observableArrayList(titulos));
    }
    
    
    public void confirmar(TableView tabela, Titulo tit) throws IOException {
        if(ControllerRetirarLivroCont.retorna() != null){
            if(tabela.getSelectionModel().getSelectedItem() != null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaRetirarLivroCont.fxml"));
                Parent root = (Parent) loader.load();
                TelaRetirarLivroContController ctr = loader.getController();

                ctr.setDados(tit);
                
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("Excolha de exemplares para serem retirados");
                stage.getIcons().add(new Image("img/icone.png"));
                stage.showAndWait();

                carregarTabela(tabela, "", "");
            }
            ControllerRetirarLivroCont.instancia = null;
        }
    }
    
    public static void buscar(TableView tabela, TextField txFiltro, ComboBox<String> cbConsulta) {
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
            carregarTabela(tabela, "", contSql);
        else
            carregarTabela(tabela, filtro, contSql);
    }
    
    public static void cancelar(Window janela) {
        janela.hide();
    }
}
