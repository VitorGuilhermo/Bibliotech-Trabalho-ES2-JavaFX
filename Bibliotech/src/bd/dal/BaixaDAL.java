package bd.dal;

import bd.entidades.Baixa;
import bd.util.Conexao;


public class BaixaDAL {
    public boolean gravar(Conexao con, Baixa b){
        String sql = "insert into baixa values (default, '#1', '#2', '#3', #4)";
        sql = sql.replace("#1", b.getNome());
        sql = sql.replace("#2", ""+b.getData());
        sql = sql.replace("#3", b.getMotivo());
        sql = sql.replace("#4", ""+b.getBibliotecario().getCod());
        return con.manipular(sql);
    }
}
