package bd.dal;

import bd.entidades.Assunto;
import bd.util.Banco;
import bd.util.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class AssuntoDAO {
    public boolean gravar(Conexao con, Assunto a){
        String sql = "insert into assunto values (default, '#1')";
        sql = sql.replace("#1", a.getNome());
        return con.manipular(sql);
    }
    public boolean alterar(Conexao con, Assunto a){
        String sql = "update assunto set ast_nome='#1' where ast_cod="+a.getCodigo();
        sql = sql.replace("#1", a.getNome());
        return con.manipular(sql);
    }
    public boolean apagar(Conexao con, int id){
        String sql = "delete from assunto where ast_cod="+id;
        return con.manipular(sql);
    }
    public Assunto get(int id){
        Assunto aux = null;
        String sql = "select * from assunto where ast_cod="+id;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            if(rs.next())
                aux = new Assunto(rs.getInt("ast_cod"), rs.getString("ast_nome"));
        }
        catch(Exception e){
        }
        return aux;
    }
    public List<Assunto> get(Conexao con, String filtro){
        List<Assunto> assuntos = new ArrayList<>();
        
        String sql = "select * from assunto";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = con.consultar(sql);
        try{
            while(rs.next())
                assuntos.add( new Assunto(rs.getInt("ast_cod"), rs.getString("ast_nome")) );
        }
        catch(Exception e){
        }
        return assuntos;
    }
    public Assunto getAst(Conexao con, String nome){
        Assunto aux = null;
        String sql = "select * from assunto where upper(ast_nome) like '"+nome+"'";
        ResultSet rs = con.consultar(sql);
        try{
            if(rs.next())
                aux = new Assunto(rs.getInt("ast_cod"), rs.getString("ast_nome"));
        }
        catch(Exception e){
        }
        return aux;
    }
}
