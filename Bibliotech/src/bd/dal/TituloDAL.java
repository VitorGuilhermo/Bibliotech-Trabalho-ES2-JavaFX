package bd.dal;

import bd.entidades.Assunto;
import bd.entidades.Autor;
import bd.entidades.Exemplar;
import bd.entidades.Titulo;
import bd.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class TituloDAL {
    public boolean gravar(Titulo t){
        boolean aux;
        String sql = "insert into titulo values (default, '#1', #2, #3, #4, '#5', '#6')";
        sql = sql.replace("#1", t.getNome());
        sql = sql.replace("#2", ""+t.getGenero().getCodigo());
        sql = sql.replace("#3", ""+t.getEditora().getCodigo());
        sql = sql.replace("#4", ""+t.getQtdeExemplares());
        sql = sql.replace("#5", ""+t.getDataPubli());
        sql = sql.replace("#6", ""+t.getDataReg());
        aux = Banco.getCon().manipular(sql);
        //insere os exemplares do titulo
        if(aux){
            for(int i=0; i<t.getQtdeExemplares(); i++){
                int cod = Banco.getCon().getMaxPK("titulo", "tit_cod");
                t.setCodigo(cod);
                Exemplar e = new Exemplar(false, t);
                aux = new ExemplarDAL().gravar(e);
            }
        }
        //retorna true se conseguiu inserir o titulo e seus exemplares
        return aux;
    }
    public boolean alterar(Titulo t){
        String sql = "update titulo set tit_nome='#1', gen_cod=#2, edt_cod=#3, tit_qtdeexe=#4, tit_datapublic='#5', tit_datareg='#6' where tit_cod="+t.getCodigo();
        sql = sql.replace("#1", t.getNome());
        sql = sql.replace("#2", ""+t.getGenero().getCodigo());
        sql = sql.replace("#3", ""+t.getEditora().getCodigo());
        sql = sql.replace("#4", ""+t.getQtdeExemplares());
        sql = sql.replace("#5", ""+t.getDataPubli());
        sql = sql.replace("#6", ""+t.getDataReg());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterarQtdeExemplares(int id, int qtde){
        String sql = "update titulo set tit_qtdeexe=#1 where tit_cod="+id;
        sql = sql.replace("#1", ""+qtde);
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(int id){
        String sql = "delete from titulo where tit_cod="+id;
        return Banco.getCon().manipular(sql);
    }
    public Titulo get(int id){
        Titulo aux = null;
        String sql = "select * from titulo where tit_cod="+id;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            if(rs.next())
                aux = new Titulo(rs.getInt("tit_cod"), rs.getString("tit_nome"), new GeneroDAL().get(rs.getInt("gen_cod")), 
                        new EditoraDAL().get(rs.getInt("edt_cod")), rs.getInt("tit_qtdeexe"),
                        rs.getDate("tit_datapublic").toLocalDate(), rs.getDate("tit_datareg").toLocalDate(),
                        new Autor_TituloDAL().get(" titulo_tit_cod="+rs.getInt("tit_cod")),  new Assunto_TituloDAL().get(" titulo_tit_cod="+rs.getInt("tit_cod")) );
        }
        catch(Exception e){
        }
        return aux;
    }
    public List<Titulo> get(String filtro){
        List<Titulo> titulos = new ArrayList<>();
        
        String sql = "select * from titulo";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            while(rs.next())
                titulos.add( new Titulo(rs.getInt("tit_cod"), rs.getString("tit_nome"), new GeneroDAL().get(rs.getInt("gen_cod")), 
                        new EditoraDAL().get(rs.getInt("edt_cod")), rs.getInt("tit_qtdeexe"),
                        rs.getDate("tit_datapublic").toLocalDate(), rs.getDate("tit_datareg").toLocalDate(),
                        new Autor_TituloDAL().get(" titulo_tit_cod="+rs.getInt("tit_cod")),  new Assunto_TituloDAL().get(" titulo_tit_cod="+rs.getInt("tit_cod"))));
        }
        catch(Exception e){
        }
        return titulos;
    } 
    public List<Titulo> getTitulosCompostos(String filtro, String contSql){
        List<Titulo> titulos = new ArrayList<>();
        
        String sql = "select * from titulo "+contSql;
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            while(rs.next())
                titulos.add( new Titulo(rs.getInt("tit_cod"), rs.getString("tit_nome"), new GeneroDAL().get(rs.getInt("gen_cod")), 
                        new EditoraDAL().get(rs.getInt("edt_cod")), rs.getInt("tit_qtdeexe"),
                        rs.getDate("tit_datapublic").toLocalDate(), rs.getDate("tit_datareg").toLocalDate(),
                        new Autor_TituloDAL().get(" titulo_tit_cod="+rs.getInt("tit_cod")),  new Assunto_TituloDAL().get(" titulo_tit_cod="+rs.getInt("tit_cod"))));
        }
        catch(Exception e){
        }
        return titulos;
    } 
}
