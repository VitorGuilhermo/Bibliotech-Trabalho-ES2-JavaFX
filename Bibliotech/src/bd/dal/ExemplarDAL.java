package bd.dal;

import bd.entidades.Exemplar;
import bd.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ExemplarDAL {
    public boolean gravar(Exemplar e){
        String sql = "insert into exemplar values (default, #1, #2)";
        sql = sql.replace("#1", ""+e.isSituacao());
        sql = sql.replace("#2", ""+e.getTitulo().getCodigo());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Exemplar e){
        String sql = "update exemplar set exe_situacao=#1, tit_cod=#2 where exe_cod="+e.getCodigo();
        sql = sql.replace("#1", ""+e.isSituacao());
        sql = sql.replace("#2", ""+e.getTitulo().getCodigo());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(int id){
        String sql = "delete from exemplar where exe_cod="+id;
        return Banco.getCon().manipular(sql);
    }
    public Exemplar get(int id){
        Exemplar aux = null;
        String sql = "select * from exemplar where exe_cod="+id;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            if(rs.next())
                aux = new Exemplar(rs.getInt("exe_cod"), rs.getBoolean("exe_situacao"), new TituloDAL().get(rs.getInt("tit_cod")));
        }
        catch(Exception e){
        }
        return aux;
    }
    public List<Exemplar> get(String filtro){
        List<Exemplar> exemplares = new ArrayList<>();
        
        String sql = "select * from exemplar";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            while(rs.next())
                exemplares.add( new Exemplar(rs.getInt("exe_cod"), rs.getBoolean("exe_situacao"), new TituloDAL().get(rs.getInt("tit_cod"))) );
        }
        catch(Exception e){
        }
        return exemplares;
    }
}
