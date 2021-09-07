package controller;

import bd.entidades.Baixa;
import bd.entidades.Exemplar;
import bd.entidades.Observer;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerRetirarLivroCont {
    public static ControllerRetirarLivroCont instancia;
    
    public ControllerRetirarLivroCont() {
    }
    public static ControllerRetirarLivroCont retorna(){
        if (instancia == null){
            instancia = new ControllerRetirarLivroCont();
            return (instancia);
        }
        return null;
    }
    
    public static void carregarTabela(TableView tabela, String filtro){
        Exemplar ex = new Exemplar();
        Conexao con = Banco.getCon();
        List<Exemplar> exemplares = ex.buscaExemplares(con, filtro);
        tabela.setItems(FXCollections.observableArrayList(exemplares));
    }
    public void setDados(TableView tabela, TextField txTitulo, TextField txDtPubl, Titulo titulo){
        txTitulo.setText(titulo.getNome());
        txDtPubl.setText(""+titulo.getDataReg());
        carregarTabela(tabela, "titulo.tit_cod="+titulo.getCodigo());
    }
    
    public void excluir(TableView tabela, TextArea taMotivo, TextField txTitulo, Exemplar e, int cod) {
        if(tabela.getSelectionModel().getSelectedItem() != null && !taMotivo.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exclusão de um Exemplar");
            alert.setHeaderText("Confirma exclusão?");
            alert.setContentText("Tem certeza que deseja excluir o exemplar: "+e.getTitulo().getNome()+" ?");
            Optional<ButtonType> result =  alert.showAndWait();
   
            if(result.get() == ButtonType.OK){
                Conexao con = Banco.getCon();
                Titulo tit = new Titulo();
                tit.setCodigo(cod);
                
                if(tit.pesquisar(con).getQtdeExemplares() > 0){
                    //primeiro da a baixa
                    Baixa baixa = new Baixa(txTitulo.getText(), LocalDate.now(), taMotivo.getText(), SingletonBibliotecario.getInstance());
                    baixa.gravar(con);
                    //exclui o exemplar
                    Exemplar exe = new Exemplar(e.getCodigo(), e.isSituacao(), e.getTitulo());
                    exe.excluir(con);
                    //diminui quantidade de exemplares do titulo
                    tit.decrementaQtdeExemplar(con);
                    //atualiza tabela
                    carregarTabela(tabela, "titulo.tit_cod=" + tit.getCodigo());
                    taMotivo.clear();  
                    
                    if (tabela.getItems().isEmpty()){
                        List<Observer> observers = tit.notifyObservers();
                        String msg = "";
                        for(Observer o : observers)
                            msg += "Notificando "+o+" ...\n"; 
                        
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Notificando usuários da exclusão do título reservado");
                        alert.setContentText(msg);
                        alert.showAndWait();
                    }
                }
                else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro: não é possível excluir um título com 0 exemplares");
                    alert.showAndWait();
                }
            }
        }
    }
    
    public static void cancelar(Window janela) {
        janela.hide();
    }
}
