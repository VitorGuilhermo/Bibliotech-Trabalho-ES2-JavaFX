package bd.dal;

import bd.entidades.Observer;
import bd.util.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitor Guilhermo
 */
public class ObservadoresDAO {
    public boolean gravar(Conexao con, int codCliente, int codTitulo){
        String sql = "insert into observadores (cli_cod, tit_cod) values (#1, #2)";
        sql = sql.replace("#1", ""+codCliente);
        sql = sql.replace("#2", ""+codTitulo);
        return con.manipular(sql);
    }
    
    public List<Observer> get(Conexao con, int id){
        List<Observer> observadores = new ArrayList<>();
        
        String sql = "select * from observadores where tit_cod="+id;
        ResultSet rs = con.consultar(sql);
        try{
            while(rs.next())
                observadores.add( new ClienteDAL().get(con, rs.getInt("cli_cod")) );
        }
        catch(Exception e){
        }
        return observadores;
    }
}
