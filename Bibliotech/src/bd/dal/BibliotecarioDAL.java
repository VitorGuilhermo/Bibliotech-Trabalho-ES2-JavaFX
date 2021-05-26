package bd.dal;

import bd.entidades.Bibliotecario;
import bd.util.Conexao;
import java.sql.ResultSet;


public class BibliotecarioDAL {
    public Bibliotecario get(Conexao con, String documento, String senha){
        Bibliotecario aux = null;
        String sql = "select * from bibliotecario where bib_documento='"+documento+"' and bib_senha='"+senha+"' and bib_ativo=true";
        ResultSet rs = con.consultar(sql);
        try{
            if(rs.next())
                aux = new Bibliotecario(rs.getInt("bib_cod"), rs.getString("bib_nome"), rs.getString("bib_documento"), rs.getString("bib_senha"));
        }
        catch(Exception e){
        }
        return aux;
    }
}
