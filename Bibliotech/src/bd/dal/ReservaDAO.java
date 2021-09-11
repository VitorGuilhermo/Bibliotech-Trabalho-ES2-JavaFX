package bd.dal;

import bd.entidades.Reserva;
import bd.util.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    public boolean gravarReserva(Conexao con, Reserva r){
        String sql = "insert into reserva values (default, #1, #2, #3)";
        sql = sql.replace("#1", ""+r.getData());
        sql = sql.replace("#2", ""+r.getCliente().getCodigo());
        sql = sql.replace("#3", ""+r.getTitulo().getCodigo());
        return con.manipular(sql);
    }
    
    public boolean apagarPorTitulo(Conexao con, int id){
        String sql = "delete from reserva where tit_cod="+id;
        return con.manipular(sql);
    }
    
    public List<Reserva> get(Conexao con, int id){
        List<Reserva> reservas = new ArrayList<>();
        
        String sql = "select * from reserva where tit_cod=" +id;
        ResultSet rs = con.consultar(sql);
        try{
            while(rs.next())
                reservas.add( new Reserva(rs.getInt("res_cod"), rs.getDate("res_data").toLocalDate(), new ClienteDAL().get(con, rs.getInt("cli_cod")), new TituloDAL().get(con, rs.getInt("tit_cod")) ) );
        }
        catch(Exception e){
        }
        return reservas;
    }
}
