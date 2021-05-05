package bd.dal;

import bd.entidades.Exemplar;
import bd.entidades.Titulo;
import bd.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class TituloDAL {
    public boolean gravar(Titulo t){
        boolean aux;
        String sql = "insert into titulo values (default, '#1', #2, #3, #4, #5, #6, '#7', '#8')";
        sql = sql.replace("#1", t.getNome());
        sql = sql.replace("#2", ""+t.getAutor().getCodigo());
        sql = sql.replace("#3", ""+t.getGenero().getCodigo());
        sql = sql.replace("#4", ""+t.getAssunto().getCodigo());
        sql = sql.replace("#5", ""+t.getEditora().getCodigo());
        sql = sql.replace("#6", ""+t.getQtdeExemplares());
        sql = sql.replace("#7", ""+t.getDataPubli());
        sql = sql.replace("#8", ""+t.getDataReg());
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
        String sql = "update titulo set tit_nome='#1', aut_cod=#2, gen_cod=#3, ast_cod=#4, edt_cod=#5, tit_qtdeexe=#6, tit_datapublic='#7', tit_datareg='#8' where tit_cod="+t.getCodigo();
        sql = sql.replace("#1", t.getNome());
        sql = sql.replace("#2", ""+t.getAutor().getCodigo());
        sql = sql.replace("#3", ""+t.getGenero().getCodigo());
        sql = sql.replace("#4", ""+t.getAssunto().getCodigo());
        sql = sql.replace("#5", ""+t.getEditora().getCodigo());
        sql = sql.replace("#6", ""+t.getQtdeExemplares());
        sql = sql.replace("#7", ""+t.getDataPubli());
        sql = sql.replace("#8", ""+t.getDataReg());
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
                aux = new Titulo(rs.getInt("tit_cod"), rs.getString("tit_nome"), new AutorDAL().get(rs.getInt("aut_cod")), new GeneroDAL().get(rs.getInt("gen_cod")), 
                        new AssuntoDAL().get(rs.getInt("ast_cod")), new EditoraDAL().get(rs.getInt("edt_cod")), rs.getInt("tit_qtdeexe"),
                        rs.getDate("tit_datapublic").toLocalDate(), rs.getDate("tit_datareg").toLocalDate());
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
                titulos.add( new Titulo(rs.getInt("tit_cod"), rs.getString("tit_nome"), new AutorDAL().get(rs.getInt("aut_cod")), new GeneroDAL().get(rs.getInt("gen_cod")), 
                        new AssuntoDAL().get(rs.getInt("ast_cod")), new EditoraDAL().get(rs.getInt("edt_cod")), rs.getInt("tit_qtdeexe"),
                        rs.getDate("tit_datapublic").toLocalDate(), rs.getDate("tit_datareg").toLocalDate()) );
        }
        catch(Exception e){
        }
        return titulos;
    } 
}
