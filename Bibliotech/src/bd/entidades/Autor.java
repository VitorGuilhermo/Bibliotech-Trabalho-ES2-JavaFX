package bd.entidades;

import bd.dal.AutorDAO;
import bd.util.Conexao;
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
    
    public boolean gravar(Conexao con){
        return new AutorDAO().gravar(con, this);
    }
    public boolean alterar(Conexao con){
        return new AutorDAO().alterar(con, this);
    }
    public boolean excluir(Conexao con){
        return new AutorDAO().apagar(con, codigo);
    }
    public List<Autor> buscar(Conexao con, String filtro){
        return new AutorDAO().get(con, filtro);
    }
    @Override
    public String toString() {
        return nome;
    }
    public boolean equalsAutor(Autor aut) { 
        if(aut != null)
            if (nome.equals(aut.getNome()))
                return true;  
        return false; 
    }
}
