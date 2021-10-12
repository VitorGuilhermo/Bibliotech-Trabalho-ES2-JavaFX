package bd.dal;

import bd.entidades.Exemplar;
import bd.entidades.Titulo;
import bd.util.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class TituloDAO {
    public boolean gravar(Conexao con, Titulo t){
        boolean aux;
        String sql = "insert into titulo values (default, '#1', #2, #3, #4, '#5', '#6')";
        sql = sql.replace("#1", t.getNome());
        sql = sql.replace("#2", ""+t.getGenero().getCodigo());
        sql = sql.replace("#3", ""+t.getEditora().getCodigo());
        sql = sql.replace("#4", ""+t.getQtdeExemplares());
        sql = sql.replace("#5", ""+t.getDataPubli());
        sql = sql.replace("#6", ""+t.getDataReg());
        aux = con.manipular(sql);
        //insere os exemplares do titulo
        if(aux){
            for(int i=0; i<t.getQtdeExemplares(); i++){
                int cod = con.getMaxPK("titulo", "tit_cod");
                t.setCodigo(cod);
                Exemplar e = new Exemplar(false, t);
                aux = new ExemplarDAO().gravar(con, e);
            }
        }
        //retorna true se conseguiu inserir o titulo e seus exemplares
        return aux;
    }
    public boolean alterar(Conexao con, Titulo t){
        String sql = "update titulo set tit_nome='#1', gen_cod=#2, edt_cod=#3, tit_qtdeexe=#4, tit_datapublic='#5', tit_datareg='#6' where tit_cod="+t.getCodigo();
        sql = sql.replace("#1", t.getNome());
        sql = sql.replace("#2", ""+t.getGenero().getCodigo());
        sql = sql.replace("#3", ""+t.getEditora().getCodigo());
        sql = sql.replace("#4", ""+t.getQtdeExemplares());
        sql = sql.replace("#5", ""+t.getDataPubli());
        sql = sql.replace("#6", ""+t.getDataReg());
        return con.manipular(sql);
    }
    public boolean alterarQtdeExemplares(Conexao con, int id, int qtde){
        String sql = "update titulo set tit_qtdeexe=#1 where tit_cod="+id;
        sql = sql.replace("#1", ""+qtde);
        return con.manipular(sql);
    }
    public boolean apagar(Conexao con, int id){
        String sql = "delete from titulo where tit_cod="+id;
        return con.manipular(sql);
    }
    public Titulo get(Conexao con, int id){
        Titulo aux = null;
        String sql = "select * from titulo where tit_cod="+id;
        ResultSet rs = con.consultar(sql);
        try{
            if(rs.next())
                aux = new Titulo(rs.getInt("tit_cod"), rs.getString("tit_nome"), new GeneroDAO().get(con, rs.getInt("gen_cod")), 
                        new EditoraDAO().get(con, rs.getInt("edt_cod")), rs.getInt("tit_qtdeexe"),
                        rs.getDate("tit_datapublic").toLocalDate(), rs.getDate("tit_datareg").toLocalDate(),
                        new Autor_TituloDAO().get(con, " titulo_tit_cod="+rs.getInt("tit_cod")),  new Assunto_TituloDAO().get(con, " titulo_tit_cod="+rs.getInt("tit_cod")) );
        }
        catch(Exception e){
        }
        return aux;
    }
    public List<Titulo> get(Conexao con, String filtro){
        List<Titulo> titulos = new ArrayList<>();
        
        String sql = "select * from titulo";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = con.consultar(sql);
        try{
            while(rs.next())
                titulos.add( new Titulo(rs.getInt("tit_cod"), rs.getString("tit_nome"), new GeneroDAO().get(con, rs.getInt("gen_cod")), 
                        new EditoraDAO().get(con, rs.getInt("edt_cod")), rs.getInt("tit_qtdeexe"),
                        rs.getDate("tit_datapublic").toLocalDate(), rs.getDate("tit_datareg").toLocalDate(),
                        new Autor_TituloDAO().get(con, " titulo_tit_cod="+rs.getInt("tit_cod")),  new Assunto_TituloDAO().get(con," titulo_tit_cod="+rs.getInt("tit_cod"))));
        }
        catch(Exception e){
        }
        return titulos;
    } 
    public List<Titulo> getTitulosCompostos(Conexao con, String filtro, String contSql){
        List<Titulo> titulos = new ArrayList<>();
        
        String sql = "select * from titulo "+contSql;
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = con.consultar(sql);
        try{
            while(rs.next())
                titulos.add( new Titulo(rs.getInt("tit_cod"), rs.getString("tit_nome"), new GeneroDAO().get(con, rs.getInt("gen_cod")), 
                        new EditoraDAO().get(con, rs.getInt("edt_cod")), rs.getInt("tit_qtdeexe"),
                        rs.getDate("tit_datapublic").toLocalDate(), rs.getDate("tit_datareg").toLocalDate(),
                        new Autor_TituloDAO().get(con, " titulo_tit_cod="+rs.getInt("tit_cod")),  new Assunto_TituloDAO().get(con," titulo_tit_cod="+rs.getInt("tit_cod"))));
        }
        catch(Exception e){
        }
        return titulos;
    } 
}
