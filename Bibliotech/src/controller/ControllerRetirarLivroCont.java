package controller;

import bd.entidades.Baixa;
import bd.entidades.Cliente;
import bd.entidades.Exemplar;
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
public class ControllerRetirarLivroCont {
    private static ControllerRetirarLivroCont instancia;
    
    private ControllerRetirarLivroCont() {
    }
    public static ControllerRetirarLivroCont retorna(){
        if (instancia == null)
            instancia = new ControllerRetirarLivroCont();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerRetirarLivroCont getInstance() {
        return instancia;
    }
    
    
    public int excluir(boolean tabelaVazia, String taMotivo, String txTitulo, Exemplar e, int cod) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão de um Exemplar");
        alert.setHeaderText("Confirma exclusão?");
        alert.setContentText("Tem certeza que deseja excluir o exemplar: " + e.getTitulo().getNome() + " ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Conexao con = Banco.getCon();
            Titulo tit = new Titulo();
            tit.setCodigo(cod);

            if (tit.pesquisar(con).getQtdeExemplares() > 0) {
                //primeiro da a baixa
                Baixa baixa = new Baixa(txTitulo, LocalDate.now(), taMotivo, SingletonBibliotecario.getInstance());
                baixa.gravar(con);
                //exclui o exemplar
                Exemplar exe = new Exemplar(e.getCodigo(), e.isSituacao(), e.getTitulo());
                exe.excluir(con);
                //diminui quantidade de exemplares do titulo
                tit.decrementaQtdeExemplar(con);
                
                if (tabelaVazia) {
                    //exclui o exemplar da reserva do cliente
                    Reserva res = new Reserva(LocalDate.now(), new Cliente(), tit);
                    res.excluir(con);
                }
                return tit.getCodigo();
            } 
            else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: não é possível excluir um título com 0 exemplares");
                alert.showAndWait();
            }
        }
        return -1;
    }
}
