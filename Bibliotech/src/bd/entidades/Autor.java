package bd.entidades;

import bd.dal.AutorDAL;
import java.util.List;


public class Autor {
    private int codigo;
    private String nome;

    
    public Autor() {
        this(0, "");
    }
    public Autor(String nome) {
        this(0, nome);
    }
    public Autor(int codigo, String nome) {
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
    
    public boolean gravar(){
        return new AutorDAL().gravar(this);
    }
    public boolean alterar(){
        return new AutorDAL().alterar(this);
    }
    public boolean excluir(){
        return new AutorDAL().apagar(codigo);
    }
    public List<Autor> buscar(String filtro){
        return new AutorDAL().get(filtro);
    }
    @Override
    public String toString() {
        return nome;
    }
}
