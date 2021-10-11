package controller;

import bibliotech.TelaAdicionarExemplarContController;
import java.io.IOException;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerAdicionarExemplar {
    private static ControllerAdicionarExemplar instancia;
    
    private ControllerAdicionarExemplar() {
    }
    public static ControllerAdicionarExemplar retorna(){
        if (instancia == null)
            instancia = new ControllerAdicionarExemplar();
        return instancia;
    }
    public static void removeInstancia() {
        instancia = null;
    }
    public static ControllerAdicionarExemplar getInstance() {
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
    
    public void adicionar(int cod, String nome, LocalDate dtp, int qtde) throws IOException {     
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotech/TelaAdicionarExemplarCont.fxml"));
        Parent root = (Parent) loader.load();
        TelaAdicionarExemplarContController ctr = loader.getController();

        ctr.setDados(cod, nome, dtp, qtde);

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Adicionar novos exemplares");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();

        ControllerAdicionarExemplarCont.removeInstancia();
    }
}
