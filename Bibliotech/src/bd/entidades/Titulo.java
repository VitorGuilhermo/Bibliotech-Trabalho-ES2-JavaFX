package bd.entidades;

import bd.dal.TituloDAL;
import java.time.LocalDate;


public class Titulo {
    private int codigo;
    private String nome;
    private Genero genero;
    private Editora editora;
    private int qtdeExemplares;
    private LocalDate dataPubli;
    private LocalDate dataReg;

    
    public Titulo() {
        this(0, "", new Genero(), new Editora(), 0, LocalDate.now(), LocalDate.now());
    }
    public Titulo(String nome, Genero genero, Editora editora, int qtde, LocalDate dataPubli, LocalDate dataReg) {
        this(0, nome, genero, editora, qtde, dataPubli, dataReg);
    }
    public Titulo(int codigo, String nome, Genero genero, Editora editora, int qtde, LocalDate dataPubli, LocalDate dataReg) {
        this.codigo = codigo;
        this.nome = nome;
        this.genero = genero;
        this.editora = editora;
        this.qtdeExemplares = qtde;
        this.dataPubli = dataPubli;
        this.dataReg = dataReg;
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
    public Genero getGenero() {
        return genero;
    }
    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    public Editora getEditora() {
        return editora;
    }
    public void setEditora(Editora editora) {
        this.editora = editora;
    }
    public int getQtdeExemplares() {
        return qtdeExemplares;
    }
    public void setQtdeExemplares(int qtdeExemplares) {
        this.qtdeExemplares = qtdeExemplares;
    }
    public LocalDate getDataPubli() {
        return dataPubli;
    }
    public void setDataPubli(LocalDate dataPubli) {
        this.dataPubli = dataPubli;
    }
    public LocalDate getDataReg() {
        return dataReg;
    }
    public void setDataReg(LocalDate dataReg) {
        this.dataReg = dataReg;
    }

    public boolean gravar(){
        return new TituloDAL().gravar(this);
    }
    
    @Override
    public String toString() {
        return nome;
    }
    
}
