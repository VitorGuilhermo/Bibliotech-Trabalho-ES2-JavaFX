package controller;

import bd.entidades.Exemplar;
import bd.entidades.Titulo;
import bd.util.Banco;
import bd.util.Conexao;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.stage.Window;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerAdicionarExemplarCont {
    private static ControllerAdicionarExemplarCont instancia;
    private static List<String> mensagens = new ArrayList<>();
    
    private ControllerAdicionarExemplarCont() {
    }
    public static ControllerAdicionarExemplarCont retorna(){
        if (instancia == null)
            instancia = new ControllerAdicionarExemplarCont();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerAdicionarExemplarCont getInstance() {
        return instancia;
    }
    
    public void addMensagem(String msg){
        mensagens.add(msg);
    }
    
    
    public static void adicionar(int cod, int qtde) {
        Conexao con = Banco.getCon();
        
        Titulo aux = new Titulo();
        aux.setCodigo(cod);
        
        Titulo tit = aux.pesquisar(con);
        
        if(tit != null){
            //adiciona exemplares
            for(int i=0; i<qtde;i++){
                Exemplar e = new Exemplar(false, tit);
                e.gravar(con);
            }
            //muda a qtde de exemplar na entidade titulo
            tit.incrementaQtdeExemplar(con, qtde);
            
            //notifica os clientes que tinham esse titulo reservado de sua chegada
            tit.notifyObservers();
            if(!mensagens.isEmpty()){
                String msg="";
                for(String m : mensagens)
                    msg += m;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notificando usuários da chegada do título reservado");
                alert.setContentText(msg);
                alert.showAndWait();
                
                mensagens.clear();
            }
        }
    }
}
