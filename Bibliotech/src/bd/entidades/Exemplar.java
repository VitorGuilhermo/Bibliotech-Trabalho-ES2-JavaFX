package bd.entidades;

import bd.dal.ExemplarDAL;
import bd.util.Conexao;
import java.util.List;


public class Exemplar {
    private int codigo;
    private boolean situacao;
    private Titulo titulo;

    
    public Exemplar() {
        this(0, false, new Titulo());
    }
    public Exemplar(boolean situacao, Titulo titulo) {
        this(0, false, titulo);
    }
    public Exemplar(int codigo, boolean situacao, Titulo titulo) {
        this.codigo = codigo;
        this.situacao = situacao;
        this.titulo = titulo;
    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public boolean isSituacao() {
        return situacao;
    }
    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }
    public Titulo getTitulo() {
        return titulo;
    }
    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }
    
    public List<Exemplar> buscaExemplares(Conexao con, String filtro){
        return new ExemplarDAL().getExemplares(con, filtro);
    }
    public List<Exemplar> buscar(Conexao con, String filtro){
        return new ExemplarDAL().get(con, filtro);
    }
    public boolean excluir(Conexao con){
        return new ExemplarDAL().apagar(con, codigo);
    }
    public boolean alteraSituacao(Conexao con){
        return new ExemplarDAL().alterarSituacao(con, this);
    }
    @Override
    public String toString() {
        return titulo.getNome();
    }
    
    
}
