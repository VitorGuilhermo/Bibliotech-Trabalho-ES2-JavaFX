package bd.dal;

import bd.entidades.Assunto;
import bd.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class AssuntoDAL {
    public boolean gravar(Assunto a){
        String sql = "insert into assunto values (default, '#1')";
        sql = sql.replace("#1", a.getNome());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Assunto a){
        String sql = "update assunto set ast_nome='#1' where ast_cod="+a.getCodigo();
        sql = sql.replace("#1", a.getNome());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(int id){
        String sql = "delete from assunto where ast_cod="+id;
        return Banco.getCon().manipular(sql);
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
    public List<Assunto> get(String filtro){
        List<Assunto> assuntos = new ArrayList<>();
        
        String sql = "select * from assunto";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            while(rs.next())
                assuntos.add( new Assunto(rs.getInt("ast_cod"), rs.getString("ast_nome")) );
        }
        catch(Exception e){
        }
        return assuntos;
    }
}
