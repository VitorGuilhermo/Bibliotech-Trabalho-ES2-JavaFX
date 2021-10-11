package controller;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/**
 * @author Vitor Guilhermo
 */
public abstract class ControllerGerenciar {
    
    public final void buscar(TableView tabela, TextField txFiltrar, String chave) {
        String filtro = "upper("+chave+") like '%#%'";
        
        filtro = filtro.replace("#", txFiltrar.getText().toUpperCase());
        
        if(txFiltrar.getText().isEmpty())
            carregarTabela(tabela, "");
        else
            carregarTabela(tabela, filtro);
    }
    
    public final void cancelar(Window janela) {
        janela.hide();
    }    
    public abstract void carregarTabela(TableView tabela, String filtro);
     
}
