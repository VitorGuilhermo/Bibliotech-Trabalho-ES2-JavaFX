package bd.dal;

import bd.entidades.Emprestimo;
import bd.util.Conexao;


public class EmprestimoDAL {
    public boolean gravar(Conexao con, Emprestimo e){
        String sql = "insert into emprestimo values (default, '#1', '#2', #3, #4)";
        sql = sql.replace("#1", ""+e.getData());
        sql = sql.replace("#2", ""+e.getDataDevolucaoP());
        sql = sql.replace("#3", ""+e.getQuantidade());
        sql = sql.replace("#4", ""+e.getCliente().getCodigo());
        return con.manipular(sql);
    }
}
