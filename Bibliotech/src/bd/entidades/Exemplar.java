package bd.entidades;


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

    @Override
    public String toString() {
        return titulo.getNome();
    }
    
    
}
