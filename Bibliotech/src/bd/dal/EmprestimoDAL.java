package bd.dal;

import bd.entidades.Emprestimo;
import bd.util.Banco;


public class EmprestimoDAL {
    public boolean gravar(Emprestimo e){
        String sql = "insert into emprestimo values (default, '#1', '#2', #3, #4)";
        sql = sql.replace("#1", ""+e.getData());
        sql = sql.replace("#2", ""+e.getDataDevolucaoP());
        sql = sql.replace("#3", ""+e.getQuantidade());
        sql = sql.replace("#4", ""+e.getCliente().getCodigo());
        return Banco.getCon().manipular(sql);
    }
}
