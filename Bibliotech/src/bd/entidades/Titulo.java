package bd.entidades;

import java.time.LocalDate;


public class Titulo {
    private int codigo;
    private String nome;
    private Autor autor;
    private Genero genero;
    private Assunto assunto;
    private Editora editora;
    private int qtdeExemplares;
    private LocalDate dataPubli;
    private LocalDate dataReg;

    
    public Titulo() {
        this(0, "", new Autor(), new Genero(), new Assunto(), new Editora(), 0, LocalDate.now(), LocalDate.now());
    }
    public Titulo(String nome, Autor autor, Genero genero, Assunto assunto, Editora editora, int qtde, LocalDate dataPubli, LocalDate dataReg) {
        this(0, nome, autor, genero, assunto, editora, qtde, dataPubli, dataReg);
    }
    public Titulo(int codigo, String nome, Autor autor, Genero genero, Assunto assunto, Editora editora, int qtde, LocalDate dataPubli, LocalDate dataReg) {
        this.codigo = codigo;
        this.nome = nome;
        this.autor = autor;
        this.genero = genero;
        this.assunto = assunto;
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
    public Autor getAutor() {
        return autor;
    }
    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    public Genero getGenero() {
        return genero;
    }
    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    public Assunto getAssunto() {
        return assunto;
    }
    public void setAssunto(Assunto assunto) {
        this.assunto = assunto;
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

    @Override
    public String toString() {
        return nome;
    }
    
}
