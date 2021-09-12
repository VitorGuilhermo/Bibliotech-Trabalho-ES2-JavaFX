package bd.entidades;

/**
 *
 * @author Vitor Guilhermo
 */
public interface Observable {
    public void addObserver(Observer ob);
    public void removeObserver(Observer ob);
    public String notifyObservers();
}
