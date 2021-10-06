package controller;

import bd.entidades.Assunto_Titulo;
import bd.entidades.Autor_Titulo;
import bd.entidades.Cliente;
import bd.entidades.Editora;
import bd.entidades.Genero;
import bd.entidades.Reserva;
import bd.entidades.Titulo;
import bd.util.Banco;
import bd.util.Conexao;
import bibliotech.TelaCadastrarTituloController;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerGerenciarTitulo extends ControllerGerenciar {
    private static ControllerGerenciarTitulo instancia;
    
    private ControllerGerenciarTitulo() {
    }
    public static ControllerGerenciarTitulo retorna(){
        if (instancia == null)
            instancia = new ControllerGerenciarTitulo();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerGerenciarTitulo getInstance() {
        return instancia;
    }
    
    public void carregarTabela(TableView tabela, String filtro){
        Conexao con = Banco.getCon();
        List<Titulo> titulos = new Titulo().pesquisarFiltro(con, filtro);
        tabela.setItems(FXCollections.observableArrayList( titulos ));
    }
    
    
    public void novo(TableView tabela) throws IOException {
        if(ControllerCadastrarTitulo.getInstance() == null && ControllerCadastrarTitulo.retorna() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarTitulo.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Cadastrar Título");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            ControllerCadastrarTitulo.removeInstancia();
            carregarTabela(tabela, "");
        }
    }
    
    public void alterar(TableView tabela, int cod, String nome, Genero gen, Editora edt, int qtde, LocalDate dtp, LocalDate dtr) throws IOException {
        if(ControllerCadastrarTitulo.getInstance() == null && ControllerCadastrarTitulo.retorna() != null){
            Conexao con = Banco.getCon();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaCadastrarTitulo.fxml"));
            Parent root = (Parent) loader.load();
            TelaCadastrarTituloController ctr = loader.getController();

            ctr.setDados(cod, nome, new Autor_Titulo().buscar(con, "titulo_tit_cod="+cod),
                gen, new Assunto_Titulo().buscar(con, "titulo_tit_cod="+cod), edt,
                qtde, dtp, dtr);

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Alterar Título");
            stage.getIcons().add(new Image("img/icone.png"));
            stage.showAndWait();

            ControllerCadastrarTitulo.removeInstancia();
            carregarTabela(tabela, "");
        }
    }
    
    public void excluir(TableView tabela, int cod, String nome, int qtde) {
        if(tabela.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exclusão de um Título");
            alert.setHeaderText("Confirma exclusão?");
            alert.setContentText("Tem certeza que deseja excluir o título: "+nome+" ?");
            Optional<ButtonType> result =  alert.showAndWait();
            
            
            if(result.get() == ButtonType.OK){
                Conexao con = Banco.getCon();
                if(qtde == 0){
                    new Autor_Titulo().excluir(con, cod);
                    new Assunto_Titulo().excluir(con, cod);
                    //exclui o exemplar da reserva do cliente   
                    Titulo tit = new Titulo();
                    tit.setCodigo(cod);
                    Reserva res = new Reserva(LocalDate.now(), new Cliente(), tit);
                    res.excluir(con);
                    tit.excluir(con, cod);
                    carregarTabela(tabela, "");
                }
                else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Falha");
                    alert.setHeaderText("Erro: Não é possível excluir títulos com quantidades de exemplares maiores que 0!");
                    alert.setContentText("Erro na exclusão!");
                    alert.showAndWait();
                }
            }
        }
    }
}
