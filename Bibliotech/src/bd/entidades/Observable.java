package bd.entidades;

import java.util.List;

/**
 *
 * @author Vitor Guilhermo
 */
public interface Observable {
    public void addObserver(Observer ob);
    public void removeObserver(Observer ob);
    public List<Observer> notifyObservers();
}
