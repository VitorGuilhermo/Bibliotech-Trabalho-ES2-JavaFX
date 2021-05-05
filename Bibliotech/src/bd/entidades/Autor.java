package bd.entidades;


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
    
    @Override
    public String toString() {
        return nome;
    }
}
