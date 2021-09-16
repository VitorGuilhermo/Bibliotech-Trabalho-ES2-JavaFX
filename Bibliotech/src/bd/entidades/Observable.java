package bd.entidades;

import bd.util.Conexao;

/**
 *
 * @author Vitor Guilhermo
 */
public interface Observable {
    public void addObserver(Conexao con, int codCliente);
    public void removeObserver(Observer ob);
    public void notifyObservers();
}
