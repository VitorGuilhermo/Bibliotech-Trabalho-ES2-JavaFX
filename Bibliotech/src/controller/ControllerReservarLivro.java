package controller;

import bd.entidades.Cliente;
import bd.entidades.Reserva;
import bd.entidades.Titulo;
import bd.util.Banco;
import bd.util.Conexao;
import java.time.LocalDate;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
    
    
    public String buscar(String txFiltro) {
        String filtro = "upper(tit_nome) like '%#%'";
        
        filtro = filtro.replace("#", txFiltro.toUpperCase());
        
        if(txFiltro.isEmpty())
            return "";
        else
            return filtro;
    }
    
    public void reservar(int cod, String nome) {
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
}
