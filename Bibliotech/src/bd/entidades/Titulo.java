package bd.entidades;

import bd.dal.ReservaDAO;
import bd.dal.TituloDAL;
import bd.util.Banco;
import bd.util.Conexao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Titulo implements Observable {
    private int codigo;
    private String nome;
    private Genero genero;
    private Editora editora;
    private int qtdeExemplares;
    private LocalDate dataPubli;
    private LocalDate dataReg;
    private List<Autor> autores;
    private List<Assunto> assuntos;
    
    public List<Observer> observers;
    
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
        this.observers = new ArrayList<>();
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
    

    public boolean gravar(Conexao con){
        return new TituloDAL().gravar(con, this);
    }
    public boolean alterar(Conexao con){
        return new TituloDAL().alterar(con, this);
    }
    public boolean excluir(Conexao con, int cod){
        return new TituloDAL().apagar(con, cod);
    }
    public List<Titulo> buscarTitulosCompostos(Conexao con, String filtro, String contSql){
        return new TituloDAL().getTitulosCompostos(con, filtro, contSql);
    }
    public Titulo pesquisar(Conexao con){
        return new TituloDAL().get(con, codigo);
    }
    public List<Titulo> pesquisarFiltro(Conexao con, String filtro){
        return new TituloDAL().get(con, filtro);
    }
    public void incrementaQtdeExemplar(Conexao con, int qtde){
        Titulo t = this.pesquisar(con);
        new TituloDAL().alterarQtdeExemplares(con, t.getCodigo(), t.getQtdeExemplares()+qtde);
    }
    public void decrementaQtdeExemplar(Conexao con){
        Titulo t = this.pesquisar(con);
        new TituloDAL().alterarQtdeExemplares(con, t.getCodigo(), t.getQtdeExemplares()-1);
    }
    public String getTodosAssuntos(){
        String ast = "";
        for(Assunto a : assuntos)
            ast = ast.concat(a.getNome()+" ");
        return ast;
    }
    public String getTodosAutores(){
        String aut = "";
        for(Autor a : autores)
            aut = aut.concat(a.getNome()+" ");
        return aut;
    }
    @Override
    public String toString() {
        return nome;
    }

    // Design Patern: Observer
    @Override
    public void addObserver(Observer ob) {
        observers.add(ob);
    }

    @Override
    public void removeObserver(Observer ob) {
        observers.remove(ob);
    }

    @Override
    public String notifyObservers() {
        Conexao con = Banco.getCon();
        List <Reserva> reservas = new ReservaDAO().get(con, getCodigo());
        String msg="";    
        
        for(Reserva r : reservas) {
            Cliente c = r.getCliente();
            addObserver(c);
        }
        for(Observer ob : observers){
            //chama o update do observer
            msg += ob.update();
        }
        return msg;
    }
    
}
