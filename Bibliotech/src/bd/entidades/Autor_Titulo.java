package bd.entidades;

import bd.dal.Autor_TituloDAL;
import bd.util.Conexao;
import java.util.List;


public class Autor_Titulo {
    private int cod;
    private Autor autor;
    private Titulo titulo;

    
    public Autor_Titulo() {
        this(0, new Autor(), new Titulo());
    }
    public Autor_Titulo(Autor autor, Titulo titulo) {
        this(0, autor, titulo);
    }
    public Autor_Titulo(int cod, Autor autor, Titulo titulo) {
        this.cod = cod;
        this.autor = autor;
        this.titulo = titulo;
    }

    public int getCod() {
        return cod;
    }
    public void setCod(int cod) {
        this.cod = cod;
    }
    public Autor getAutor() {
        return autor;
    }
    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    public Titulo getTitulo() {
        return titulo;
    }
    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }
    
    public boolean gravar(Conexao con){
        return new Autor_TituloDAL().gravar(con, this);
    }
    public List<Autor> buscar(Conexao con, String filtro){
        return new Autor_TituloDAL().get(con, filtro);
    }

    @Override
    public String toString() {
        return ""+autor;
    }
    
}
