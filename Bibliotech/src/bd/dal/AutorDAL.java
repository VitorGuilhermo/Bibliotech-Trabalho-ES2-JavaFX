package bd.dal;

import bd.entidades.Autor;
import bd.util.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class AutorDAL {
    public boolean gravar(Conexao con, Autor a){
        String sql = "insert into autor values (default, '#1')";
        sql = sql.replace("#1", a.getNome());
        return con.manipular(sql);
    }
    public boolean alterar(Conexao con, Autor a){
        String sql = "update autor set aut_nome='#1' where aut_cod="+a.getCodigo();
        sql = sql.replace("#1", a.getNome());
        return con.manipular(sql);
    }
    public boolean apagar(Conexao con, int id){
        String sql = "delete from autor where aut_cod="+id;
        return con.manipular(sql);
    }
    public Autor get(Conexao con, int id){
        Autor aux = null;
        String sql = "select * from autor where aut_cod="+id;
        ResultSet rs = con.consultar(sql);
        try{
            if(rs.next())
                aux = new Autor(rs.getInt("aut_cod"), rs.getString("aut_nome"));
        }
        catch(Exception e){
        }
        return aux;
    }
    public List<Autor> get(Conexao con, String filtro){
        List<Autor> autores = new ArrayList<>();
        
        String sql = "select * from autor";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = con.consultar(sql);
        try{
            while(rs.next())
                autores.add( new Autor(rs.getInt("aut_cod"), rs.getString("aut_nome")));
        }
        catch(Exception e){
        }
        return autores;
    }
}
