package bd.entidades;

import bd.dal.EmprestimoDAO;
import bd.util.Conexao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Emprestimo {
    private int codigo;
    private LocalDate data, dataDevolucaoP;
    private int quantidade;
    private Cliente cliente;
    private List<Exemplar> exemplares;

    
    public Emprestimo() {
        this(0, LocalDate.now(), LocalDate.now(), 0, new Cliente(), new ArrayList<Exemplar>());
    }
    public Emprestimo(LocalDate data, LocalDate dataDevolucaoP, int quantidade, Cliente cliente, List<Exemplar> exemplares) {
        this(0, data, dataDevolucaoP, quantidade, cliente, exemplares);
    }
    public Emprestimo(int codigo, LocalDate data, LocalDate dataDevolucaoP, int quantidade, Cliente cliente, List<Exemplar> exemplares) {
        this.codigo = codigo;
        this.data = data;
        this.dataDevolucaoP = dataDevolucaoP;
        this.quantidade = quantidade;
        this.cliente = cliente;
        this.exemplares = exemplares;
    }
    

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public LocalDate getDataDevolucaoP() {
        return dataDevolucaoP;
    }
    public void setDataDevolucaoP(LocalDate dataDevolucaoP) {
        this.dataDevolucaoP = dataDevolucaoP;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public List<Exemplar> getExemplares() {
        return exemplares;
    }
    public void setExemplares(Exemplar exemplar) {
        this.exemplares.add(exemplar);
    }
    
    public boolean gravar(Conexao con){
        return new EmprestimoDAO().gravar(con, this);
    }
    public List<Emprestimo> buscarPorCodigoCliente(Conexao con){
        return new EmprestimoDAO().getPorCodigoCliente(con, cliente.getCodigo());
    }
    
}
