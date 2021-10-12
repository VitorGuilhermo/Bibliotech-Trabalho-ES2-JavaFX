package bd.entidades;

import bd.dal.AssuntoDAO;
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
        return new AssuntoDAO().gravar(con, this);
    }
    public boolean alterar(Conexao con){
        return new AssuntoDAO().alterar(con, this);
    }
    public boolean excluir(Conexao con){
        return new AssuntoDAO().apagar(con, codigo);
    }
    public List<Assunto> buscar(Conexao con, String filtro){
        return new AssuntoDAO().get(con, filtro);
    }
    public Assunto buscarAst(Conexao con, String nome){
        return new AssuntoDAO().getAst(con, nome);
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
