package bd.dal;

import bd.entidades.Assunto;
import bd.entidades.Assunto_Titulo;
import bd.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class Assunto_TituloDAL {
    public boolean gravar(Assunto_Titulo at){
        String sql = "insert into assunto_titulo (assunto_asn_cod, titulo_tit_cod) values (#1, #2)";
        sql = sql.replace("#1", ""+at.getAssunto().getCodigo());
        sql = sql.replace("#2", ""+at.getTitulo().getCodigo());
        return Banco.getCon().manipular(sql);
    }
    public List<Assunto> get(String filtro){
        List<Assunto> assuntos = new ArrayList<>();
        
        String sql = "select * from assunto_titulo inner join assunto on ast_cod = assunto_asn_cod";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            while(rs.next())
                assuntos.add( new Assunto(rs.getInt("assunto_asn_cod"), rs.getString("ast_nome")) );
        }
        catch(Exception e){
        }
        return assuntos;
    }
}
