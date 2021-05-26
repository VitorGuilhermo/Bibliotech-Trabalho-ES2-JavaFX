package bd.entidades;

import bd.dal.Assunto_TituloDAL;
import bd.util.Conexao;
import java.util.List;


public class Assunto_Titulo {
    private int cod;
    private Assunto assunto;
    private Titulo titulo;

    
    public Assunto_Titulo() {
        this(0, new Assunto(), new Titulo());
    }
    public Assunto_Titulo(Assunto assunto, Titulo titulo) {
        this(0, assunto, titulo);
    }
    public Assunto_Titulo(int cod, Assunto assunto, Titulo titulo) {
        this.cod = cod;
        this.assunto = assunto;
        this.titulo = titulo;
    }

    public int getCod() {
        return cod;
    }
    public void setCod(int cod) {
        this.cod = cod;
    }
    public Assunto getAssunto() {
        return assunto;
    }
    public void setAssunto(Assunto assunto) {
        this.assunto = assunto;
    }
    public Titulo getTitulo() {
        return titulo;
    }
    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }
    
    public boolean gravar(Conexao con){
        return new Assunto_TituloDAL().gravar(con, this);
    }
    public boolean excluir(Conexao con, int cod){
        return new Assunto_TituloDAL().apagar(con, cod);
    }
    public List<Assunto> buscar(Conexao con, String filtro){
        return new Assunto_TituloDAL().get(con, filtro);
    }
    
}
