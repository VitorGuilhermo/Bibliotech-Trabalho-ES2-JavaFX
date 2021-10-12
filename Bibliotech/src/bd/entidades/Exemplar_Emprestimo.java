package bd.entidades;

import bd.dal.Exemplar_EmprestimoDAO;
import bd.util.Conexao;
import java.time.LocalDate;
import java.util.List;


public class Exemplar_Emprestimo {
    private int codigo;
    private LocalDate dataDevolucaoR;
    private Multa multa;
    private Exemplar exemplar;
    private Emprestimo emprestimo;

    
    
    public Exemplar_Emprestimo() {
        this(0, LocalDate.now(), new Multa(), new Exemplar(), new Emprestimo());
    }
    public Exemplar_Emprestimo(LocalDate dataDevolucaoR, Multa multa, Exemplar exemplar, Emprestimo emprestimo) {
        this(0, dataDevolucaoR, multa, exemplar, emprestimo);
    }
    public Exemplar_Emprestimo(int codigo, LocalDate dataDevolucaoR, Multa multa, Exemplar exemplar, Emprestimo emprestimo) {
        this.codigo = codigo;
        this.dataDevolucaoR = dataDevolucaoR;
        this.multa = multa;
        this.exemplar = exemplar;
        this.emprestimo = emprestimo;
    }

    
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public LocalDate getDataDevolucaoR() {
        return dataDevolucaoR;
    }
    public void setDataDevolucaoR(LocalDate dataDevolucaoR) {
        this.dataDevolucaoR = dataDevolucaoR;
    }
    public Multa getMulta() {
        return multa;
    }
    public void setMulta(Multa multa) {
        this.multa = multa;
    }
    public Exemplar getExemplar() {
        return exemplar;
    }
    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }
    public Emprestimo getEmprestimo() {
        return emprestimo;
    }
    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }
    
    public boolean gravar(Conexao con){
        return new Exemplar_EmprestimoDAO().gravar(con, this);
    }
    public boolean alteraMulta(Conexao con) {
        return new Exemplar_EmprestimoDAO().alterarMulta(con, this);
    } 
    public List<Exemplar_Emprestimo> buscar(Conexao con, String filtro) {
        return new Exemplar_EmprestimoDAO().get(con, filtro);
    }
}
