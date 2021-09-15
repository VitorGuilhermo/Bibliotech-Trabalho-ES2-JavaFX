package bd.dal;

import bd.entidades.Emprestimo;
import bd.util.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class EmprestimoDAL {
    public boolean gravar(Conexao con, Emprestimo e){
        String sql = "insert into emprestimo values (default, '#1', '#2', #3, #4)";
        sql = sql.replace("#1", ""+e.getData());
        sql = sql.replace("#2", ""+e.getDataDevolucaoP());
        sql = sql.replace("#3", ""+e.getQuantidade());
        sql = sql.replace("#4", ""+e.getCliente().getCodigo());
        return con.manipular(sql);
    }
    
    public Emprestimo get(Conexao con, int id){
        Emprestimo aux = null;
        String sql = "select * from emprestimo where emp_cod="+id;
        ResultSet rs = con.consultar(sql);
        try{
            if(rs.next())
                aux = new Emprestimo(rs.getInt("emp_cod"), rs.getDate("emp_data").toLocalDate(), rs.getDate("emp_dt_devol").toLocalDate(), rs.getInt("emp_qtde"), 
                        new ClienteDAL().get(con, rs.getInt("cli_cod")), new Exemplar_EmprestimoDAL().getExemplaresDoEmprestimo(con, rs.getInt("emp_cod")));
        }
        catch(Exception e){
        }
        return aux;
    }
    public List<Emprestimo> getPorCodigoCliente(Conexao con, int id){
        List<Emprestimo> aux = new ArrayList<>();
        String sql = "select * from emprestimo where cli_cod="+id;
        ResultSet rs = con.consultar(sql);
        try{
            while(rs.next())
                aux.add( new Emprestimo(rs.getInt("emp_cod"), rs.getDate("emp_data").toLocalDate(), rs.getDate("emp_dt_devol").toLocalDate(), rs.getInt("emp_qtde"), 
                        new ClienteDAL().get(con, rs.getInt("cli_cod")), new Exemplar_EmprestimoDAL().getExemplaresDoEmprestimo(con, rs.getInt("emp_cod"))) );
        }
        catch(Exception e){
        }
        return aux;
    }
}
