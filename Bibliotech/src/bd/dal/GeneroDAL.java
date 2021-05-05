package bd.dal;

import bd.entidades.Genero;
import bd.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class GeneroDAL {
    public boolean gravar(Genero g){
        String sql = "insert into genero values (default, '#1')";
        sql = sql.replace("#1", g.getNome());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Genero g){
        String sql = "update genero set gen_nome='#1' where gen_cod="+g.getCodigo();
        sql = sql.replace("#1", g.getNome());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(int id){
        String sql = "delete from genero where gen_cod="+id;
        return Banco.getCon().manipular(sql);
    }
    public Genero get(int id){
        Genero aux = null;
        String sql = "select * from genero where gen_cod="+id;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            if(rs.next())
                aux = new Genero(rs.getInt("gen_cod"), rs.getString("gen_nome"));
        }
        catch(Exception e){
        }
        return aux;
    }
    public List<Genero> get(String filtro){
        List<Genero> generos = new ArrayList<>();
        
        String sql = "select * from genero";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            while(rs.next())
                generos.add( new Genero(rs.getInt("gen_cod"), rs.getString("gen_nome")) );
        }
        catch(Exception e){
        }
        return generos;
    }
}
