package bd.entidades;

import bd.dal.ReservaDAO;
import bd.util.Conexao;
import java.time.LocalDate;
import java.util.List;


public class Reserva {
    private int codigo;
    private LocalDate data;  //
    private Cliente cliente;
    private Titulo titulo;
    
    public Reserva() {
        this(0, LocalDate.now(), new Cliente(), new Titulo());
    }

    public Reserva(LocalDate data, Cliente cliente, Titulo titulo) {
        this(0, LocalDate.now(), new Cliente(), new Titulo());
    }

    public Reserva(int codigo, LocalDate data, Cliente cliente, Titulo titulo) {
        this.codigo = codigo;
        this.data = data;
        this.cliente = cliente;
        this.titulo = titulo;
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

    
    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }
    
     public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    
    public boolean gravar(Conexao con){
        return new ReservaDAO().gravarReserva(con, this);
    }
    
    public List<Reserva> buscar(Conexao con, int id){
        return new ReservaDAO().get(con, id);
    }
    
//    public boolean excluir(Conexao con){
//        return new ReservaDAO().apagar(con, codigo);
//    }
    
}
