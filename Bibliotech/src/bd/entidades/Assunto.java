package bd.entidades;

import bd.dal.AssuntoDAL;
import java.util.List;


public class Assunto {
    private int codigo;
    private String nome;

    
    public Assunto() {
        this(0, "");
    }
    public Assunto(String nome) {
        this(0, nome);
    }
    public Assunto(int codigo, String nome) {
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
        return new AssuntoDAL().gravar(this);
    }
    public boolean alterar(){
        return new AssuntoDAL().alterar(this);
    }
    public boolean excluir(){
        return new AssuntoDAL().apagar(codigo);
    }
    public List<Assunto> buscar(String filtro){
        return new AssuntoDAL().get(filtro);
    }
    
    @Override
    public String toString() {
        return nome;
    }
    
}
