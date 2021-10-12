package bd.dal;

import bd.entidades.Exemplar;
import bd.entidades.Exemplar_Emprestimo;
import bd.entidades.Multa;
import bd.util.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Exemplar_EmprestimoDAO {
    public boolean gravar(Conexao con, Exemplar_Emprestimo ee){
        String sql = "insert into exemplar_emprestimo (epe_dt_devol, epe_multa, exe_cod, emp_cod) values ('#1', #2, #3, #4)";
        sql = sql.replace("#1", ""+ee.getDataDevolucaoR());
        sql = sql.replace("#2", ""+ee.getMulta().getValor());
        sql = sql.replace("#3", ""+ee.getExemplar().getCodigo());
        sql = sql.replace("#4", ""+ee.getEmprestimo().getCodigo());
        return con.manipular(sql);
    }

    public boolean alterarMulta(Conexao con, Exemplar_Emprestimo ee){
        String sql = "update exemplar_emprestimo set epe_multa=#1 where epe_cod="+ee.getCodigo();
        sql = sql.replace("#1", ""+ee.getMulta().getValor());
        return con.manipular(sql);
    }
    
    public List<Exemplar_Emprestimo> get(Conexao con, String filtro){
        List<Exemplar_Emprestimo> exemplares_emp = new ArrayList<>();
        
        String sql = "select * from exemplar_emprestimo";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = con.consultar(sql);
        try{
            while(rs.next())
                exemplares_emp.add( new Exemplar_Emprestimo(rs.getInt("epe_cod"), rs.getDate("epe_dt_devol").toLocalDate(), new Multa(rs.getDouble("epe_multa")), 
                        new ExemplarDAO().get(con, rs.getInt("exe_cod")), new EmprestimoDAO().get(con, rs.getInt("emp_cod"))));
        }
        catch(Exception e){
        }
        return exemplares_emp;
    }
    
    public List<Exemplar> getExemplaresDoEmprestimo(Conexao con, int id){
        List<Exemplar> exemplares = new ArrayList<>();
        
        String sql = "select * from exemplar_emprestimo where emp_cod="+id;
        ResultSet rs = con.consultar(sql);
        try{
            while(rs.next())
                exemplares.add( new ExemplarDAO().get(con, rs.getInt("emp_cod")) );
        }
        catch(Exception e){
        }
        return exemplares;
    }
}