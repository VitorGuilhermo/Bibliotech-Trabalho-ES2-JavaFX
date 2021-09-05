package controller;

import bd.entidades.Assunto_Titulo;
import bd.entidades.Autor_Titulo;
import bd.entidades.Titulo;
import bd.util.Banco;
import bd.util.Conexao;
import bibliotech.TelaCadastrarTituloController;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerGerenciarTitulo {
    public static ControllerGerenciarTitulo instancia;
    
    public ControllerGerenciarTitulo() {
    }
    public static ControllerGerenciarTitulo retorna(){
        if (instancia == null){
            instancia = new ControllerGerenciarTitulo();
            return (instancia);
        }
        return null;
    }
    
    public static void carregarTabela(TableView tabela, String filtro){
        Conexao con = Banco.getCon();
        List<Titulo> titulos = new Titulo().pesquisarFiltro(con, filtro);
        tabela.setItems(FXCollections.observableArrayList( titulos ));
    }
    
    public static void buscar(TableView tabela, TextField txFiltro) {
        String filtro = "upper(tit_nome) like '%#%'";
        
        filtro = filtro.replace("#", txFiltro.getText().toUpperCase());
        
        if(txFiltro.getText().isEmpty())
            carregarTabela(tabela, "");
        else
            carregarTabela(tabela, filtro);
    }
    
    public void novo(TableView tabela) throws IOException {
        if(ControllerCadastrarTitulo.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarTitulo.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Título");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            ControllerCadastrarTitulo.instancia = null;
            carregarTabela(tabela, "");
        }
    }
    
    public void alterar(TableView tabela, Titulo tit) throws IOException {
        if(ControllerCadastrarTitulo.retorna() != null){
            Conexao con = Banco.getCon();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaCadastrarTitulo.fxml"));
            Parent root = (Parent) loader.load();
            TelaCadastrarTituloController ctr = loader.getController();

            ctr.setDados(tit.getCodigo(), tit.getNome(), new Autor_Titulo().buscar(con, "titulo_tit_cod="+tit.getCodigo()),
                tit.getGenero(), new Assunto_Titulo().buscar(con, "titulo_tit_cod="+tit.getCodigo()), tit.getEditora(),
                tit.getQtdeExemplares(), tit.getDataPubli(), tit.getDataReg());

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Alterar Título");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            ControllerCadastrarTitulo.instancia = null;
            carregarTabela(tabela, "");
        }
    }
    
    public static void excluir(TableView tabela, Titulo tit) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exclusão de um Título");
            alert.setHeaderText("Confirma exclusão?");
            alert.setContentText("Tem certeza que deseja excluir o título: "+tit.getNome()+" ?");
            Optional<ButtonType> result =  alert.showAndWait();
            
            
            if(result.get() == ButtonType.OK){
                Conexao con = Banco.getCon();
                if(tit.getQtdeExemplares() == 0){
                    new Autor_Titulo().excluir(con, tit.getCodigo());
                    new Assunto_Titulo().excluir(con, tit.getCodigo());
                    tit.excluir(con, tit.getCodigo());
                    carregarTabela(tabela, "");
                }
                else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Falha");
                    alert.setHeaderText("Erro: Não é possível excluir títulos com quantidades de exemplares maiores que 0!");
                    alert.setContentText("Erro na exclusão!");
                }
            }
        }
    }
    
    public static void cancelar(Window janela) {
        janela.hide();
    }
}