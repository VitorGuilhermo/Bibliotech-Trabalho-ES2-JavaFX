package controller;

import bd.entidades.Cliente;
import bd.entidades.Reserva;
import bd.entidades.Titulo;
import bd.util.Banco;
import bd.util.Conexao;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerReservarLivro {
    private static ControllerReservarLivro instancia;
    
    private ControllerReservarLivro() {
    }
    public static ControllerReservarLivro retorna(){
        if (instancia == null)
            instancia = new ControllerReservarLivro();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerReservarLivro getInstance() {
        return instancia;
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
    
    public static void reservar(int cod, String nome) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reserva de um título");
        alert.setHeaderText("Tem certeza que deseja reservar o título: "+nome+" ?");
        Optional<ButtonType> result =  alert.showAndWait();
   
        if(result.get() == ButtonType.OK){
            Titulo tit = new Titulo();
            tit.setCodigo(cod);
            Cliente cli = SingletonCliente.getInstance();
            Reserva res = new Reserva(LocalDate.now(), cli, tit);
            tit.addObserver(Banco.getCon(), cli.getCodigo());
            
            Conexao con = Banco.getCon();
            if( res.gravar(con) ){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText("Reserva efetivada!");
            }
            else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro na reserva");
                alert.setHeaderText("Houve um erro na reserva do livro!");
            }
            alert.showAndWait();
        }
    }
    
    public static void cancelar(Window janela) {
        janela.hide();
    }
    
    
}
