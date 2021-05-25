package bd.dal;

import bd.entidades.Exemplar_Emprestimo;
import bd.util.Banco;

public class Exemplar_EmprestimoDAL {
    public boolean gravar(Exemplar_Emprestimo ee){
        String sql = "insert into exemplar_emprestimo (epe_dt_devol, epe_multa, exe_cod, emp_cod) values ('#1', #2, #3, #4)";
        sql = sql.replace("#1", ""+ee.getDataDevolucaoR());
        sql = sql.replace("#2", ""+ee.getMulta());
        sql = sql.replace("#3", ""+ee.getExemplar().getCodigo());
        sql = sql.replace("#4", ""+ee.getEmprestimo().getCodigo());
        return Banco.getCon().manipular(sql);
    }
}
