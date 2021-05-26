package bd.entidades;

import bd.dal.BaixaDAL;
import bd.util.Conexao;
import java.time.LocalDate;


public class Baixa {
    private int codigo;
    private String nome;
    private LocalDate data;
    private String motivo;
    private Bibliotecario bibliotecario;

    
    public Baixa() {
        this(0, "", LocalDate.now(), "", new Bibliotecario());
    }
    public Baixa(String nome, LocalDate data, String motivo, Bibliotecario bibliotecario) {
        this(0, nome, data, motivo, bibliotecario);
    }
    public Baixa(int codigo, String nome, LocalDate data, String motivo, Bibliotecario bibliotecario) {
        this.codigo = codigo;
        this.nome = nome;
        this.data = data;
        this.motivo = motivo;
        this.bibliotecario = bibliotecario;
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
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }
    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }
   
    public boolean gravar(Conexao con){
        return new BaixaDAL().gravar(con, this);
    }
    
    
}
