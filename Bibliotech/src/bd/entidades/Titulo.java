package bd.entidades;

import bd.dal.TituloDAL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Titulo {
    private int codigo;
    private String nome;
    private Genero genero;
    private Editora editora;
    private int qtdeExemplares;
    private LocalDate dataPubli;
    private LocalDate dataReg;
    private List<Autor> autores;
    private List<Assunto> assuntos;
    
    public Titulo() {
        this(0, "", new Genero(), new Editora(), 0, LocalDate.now(), LocalDate.now(), new ArrayList<Autor>(), new ArrayList<Assunto>());
    }
    public Titulo(String nome, Genero genero, Editora editora, int qtde, LocalDate dataPubli, LocalDate dataReg, List<Autor> autores, List<Assunto> assuntos) {
        this(0, nome, genero, editora, qtde, dataPubli, dataReg, autores, assuntos);
    }
    public Titulo(int codigo, String nome, Genero genero, Editora editora, int qtde, LocalDate dataPubli, LocalDate dataReg, List<Autor> autores, List<Assunto> assuntos) {
        this.codigo = codigo;
        this.nome = nome;
        this.genero = genero;
        this.editora = editora;
        this.qtdeExemplares = qtde;
        this.dataPubli = dataPubli;
        this.dataReg = dataReg;
        this.autores = autores;
        this.assuntos = assuntos;
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
    public List<Autor> getAutores() {
        return autores;
    }
    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }
    public List<Assunto> getAssuntos() {
        return assuntos;
    }
    public void setAssuntos(List<Assunto> assuntos) {
        this.assuntos = assuntos;
    }
    

    public boolean gravar(){
        return new TituloDAL().gravar(this);
    }
    public Titulo pesquisar(){
        return new TituloDAL().get(codigo);
    }
    public void decrementaQtdeExemplar(){
        Titulo t = this.pesquisar();
        new TituloDAL().alterarQtdeExemplares(t.getCodigo(), t.getQtdeExemplares()-1);
    }
    
    @Override
    public String toString() {
        return nome;
    }
    
}
