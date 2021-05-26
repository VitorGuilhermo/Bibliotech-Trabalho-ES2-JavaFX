package bd.dal;

import bd.entidades.Autor;
import bd.entidades.Autor_Titulo;
import bd.util.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class Autor_TituloDAL {
    public boolean gravar(Conexao con, Autor_Titulo at){
        String sql = "insert into autor_titulo (titulo_tit_cod, autor_aut_cod) values (#1, #2)";
        sql = sql.replace("#1", ""+at.getTitulo().getCodigo());
        sql = sql.replace("#2", ""+at.getAutor().getCodigo());
        return con.manipular(sql);
    }
    public List<Autor> get(Conexao con, String filtro){
        List<Autor> autores = new ArrayList<>();
        
        String sql = "select * from autor_titulo inner join autor on aut_cod = autor_aut_cod";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = con.consultar(sql);
        try{
            while(rs.next())
                autores.add( new Autor(rs.getInt("autor_aut_cod"), rs.getString("aut_nome")));
        }
        catch(Exception e){
        }
        return autores;
    } 
}
