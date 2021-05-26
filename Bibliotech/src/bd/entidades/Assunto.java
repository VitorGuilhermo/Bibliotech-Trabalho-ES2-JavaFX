package bd.entidades;

import bd.dal.AssuntoDAL;
import bd.util.Conexao;
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

    public boolean gravar(Conexao con){
        return new AssuntoDAL().gravar(con, this);
    }
    public boolean alterar(Conexao con){
        return new AssuntoDAL().alterar(con, this);
    }
    public boolean excluir(Conexao con){
        return new AssuntoDAL().apagar(con, codigo);
    }
    public List<Assunto> buscar(Conexao con, String filtro){
        return new AssuntoDAL().get(con, filtro);
    }
    
    @Override
    public String toString() {
        return nome;
    }
    public boolean equalsAssunto(Assunto ast) { 
        if(ast != null)
            if (nome.equals(ast.getNome()))
                return true;  
        return false; 
    }
}
