package bd.dal;

import bd.entidades.Editora;
import bd.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class EditoraDAL {
    public boolean gravar(Editora e){
        String sql = "insert into editora values (default, '#1', '#2')";
        sql = sql.replace("#1", e.getNome());
        sql = sql.replace("#2", e.getCnpj());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Editora e){
        String sql = "update editora set edt_nome='#1', edt_cnpj='#2' where edt_cod="+e.getCodigo();
        sql = sql.replace("#1", e.getNome());
        sql = sql.replace("#2", e.getCnpj());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(int id){
        String sql = "delete from editora where edt_cod="+id;
        return Banco.getCon().manipular(sql);
    }
    public Editora get(int id){
        Editora aux = null;
        String sql = "select * from editora where edt_cod="+id;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            if(rs.next())
                aux = new Editora(rs.getInt("edt_cod"), rs.getString("edt_nome"), rs.getString("edt_cnpj"));
        }
        catch(Exception e){
        }
        return aux;
    }
    public List<Editora> get(String filtro){
        List<Editora> editoras = new ArrayList<>();
        
        String sql = "select * from editora";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            while(rs.next())
                editoras.add( new Editora(rs.getInt("edt_cod"), rs.getString("edt_nome"), rs.getString("edt_cnpj")) );
        }
        catch(Exception e){
        }
        return editoras;
    }
}