package bd.entidades;


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

    @Override
    public String toString() {
        return nome;
    }
    
}
