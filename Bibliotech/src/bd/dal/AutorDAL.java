package bd.dal;

import bd.entidades.Autor;
import bd.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class AutorDAL {
    public boolean gravar(Autor a){
        String sql = "insert into autor values (default, '#1')";
        sql = sql.replace("#1", a.getNome());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Autor a){
        String sql = "update autor set aut_nome='#1' where aut_cod="+a.getCodigo();
        sql = sql.replace("#1", a.getNome());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(int id){
        String sql = "delete from autor where aut_cod="+id;
        return Banco.getCon().manipular(sql);
    }
    public Autor get(int id){
        Autor aux = null;
        String sql = "select * from autor where aut_cod="+id;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            if(rs.next())
                aux = new Autor(rs.getInt("aut_cod"), rs.getString("aut_nome"));
        }
        catch(Exception e){
        }
        return aux;
    }
    public List<Autor> get(String filtro){
        List<Autor> autores = new ArrayList<>();
        
        String sql = "select * from autor";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            while(rs.next())
                autores.add( new Autor(rs.getInt("aut_cod"), rs.getString("aut_nome")));
        }
        catch(Exception e){
        }
        return autores;
    }
}
