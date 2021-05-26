package bd.entidades;

import bd.dal.Exemplar_EmprestimoDAL;
import bd.util.Conexao;
import java.time.LocalDate;


public class Exemplar_Emprestimo {
    private int codigo;
    private LocalDate dataDevolucaoR;
    private double multa;
    private Exemplar exemplar;
    private Emprestimo emprestimo;

    
    
    public Exemplar_Emprestimo() {
        this(0, LocalDate.now(), 0., new Exemplar(), new Emprestimo());
    }
    public Exemplar_Emprestimo(LocalDate dataDevolucaoR, double multa, Exemplar exemplar, Emprestimo emprestimo) {
        this(0, dataDevolucaoR, multa, exemplar, emprestimo);
    }
    public Exemplar_Emprestimo(int codigo, LocalDate dataDevolucaoR, double multa, Exemplar exemplar, Emprestimo emprestimo) {
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
    public double getMulta() {
        return multa;
    }
    public void setMulta(double multa) {
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
        return new Exemplar_EmprestimoDAL().gravar(con, this);
    }
}
