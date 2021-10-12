package bd.dal;

import bd.entidades.Exemplar;
import bd.util.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ExemplarDAO {
    public boolean gravar(Conexao con, Exemplar e){
        String sql = "insert into exemplar values (default, #1, #2)";
        sql = sql.replace("#1", ""+e.isSituacao());
        sql = sql.replace("#2", ""+e.getTitulo().getCodigo());
        return con.manipular(sql);
    }
    public boolean alterar(Conexao con, Exemplar e){
        String sql = "update exemplar set exe_situacao=#1, tit_cod=#2 where exe_cod="+e.getCodigo();
        sql = sql.replace("#1", ""+e.isSituacao());
        sql = sql.replace("#2", ""+e.getTitulo().getCodigo());
        return con.manipular(sql);
    }
    public boolean alterarSituacao(Conexao con, Exemplar e){
        String sql = "update exemplar set exe_situacao=#1 where exe_cod="+e.getCodigo();
        sql = sql.replace("#1", ""+e.isSituacao());
        return con.manipular(sql);
    }
    public boolean apagar(Conexao con, int id){
        String sql = "delete from exemplar where exe_cod="+id;
        return con.manipular(sql);
    }
    public Exemplar get(Conexao con, int id){
        Exemplar aux = null;
        String sql = "select * from exemplar where exe_cod="+id;
        ResultSet rs = con.consultar(sql);
        try{
            if(rs.next())
                aux = new Exemplar(rs.getInt("exe_cod"), rs.getBoolean("exe_situacao"), new TituloDAO().get(con, rs.getInt("tit_cod")));
        }
        catch(Exception e){
        }
        return aux;
    }
    public List<Exemplar> get(Conexao con, String filtro){
        List<Exemplar> exemplares = new ArrayList<>();
        
        String sql = "select * from exemplar";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = con.consultar(sql);
        try{
            while(rs.next())
                exemplares.add( new Exemplar(rs.getInt("exe_cod"), rs.getBoolean("exe_situacao"), new TituloDAO().get(con, rs.getInt("tit_cod"))) );
        }
        catch(Exception e){
        }
        return exemplares;
    }
    public List<Exemplar> getExemplares(Conexao con, String filtro){
        List<Exemplar> exemplares = new ArrayList<>();
        
        String sql = "select * from exemplar";
        if(!filtro.isEmpty()){
            sql = "select * from exemplar inner join titulo on exemplar.tit_cod = titulo.tit_cod";
            sql += " where " + filtro;
            sql += " and exe_situacao=false";
        }
        else
            sql += " where exe_situacao=false";
 
        ResultSet rs = con.consultar(sql);
        try{
            while(rs.next())
                exemplares.add( new Exemplar(rs.getInt("exe_cod"), rs.getBoolean("exe_situacao"), new TituloDAO().get(con, rs.getInt("tit_cod"))) );
        }
        catch(Exception e){
        }
        return exemplares;
    }
}
