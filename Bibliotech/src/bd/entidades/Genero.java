package bd.entidades;

import bd.dal.GeneroDAL;
import java.util.List;


public class Genero {
    private int codigo;
    private String nome;

    
    public Genero() {
        this(0, "");
    }
    public Genero(String nome) {
        this(0, nome);
    }
    public Genero(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public boolean gravar() {
        return new GeneroDAL().gravar(this);
    }
    public boolean alterar() {
        return new GeneroDAL().alterar(this);
    }
    public boolean excluir() {
        return new GeneroDAL().apagar(codigo);
    }
    public List<Genero> buscar(String filtro) {
        return new GeneroDAL().get(filtro);
    }
    
    @Override
    public String toString() {
        return nome;
    }
    
    
}
