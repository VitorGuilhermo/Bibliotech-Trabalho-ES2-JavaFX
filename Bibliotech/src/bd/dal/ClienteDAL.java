package bd.dal;

import bd.entidades.Cliente;
import bd.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAL {
    public boolean gravar(Cliente c){
        String sql = "insert into cliente values (default, '#1', '#2', '#3', '#4', '#5', '#6')";
        sql = sql.replace("#1", c.getNome());
        sql = sql.replace("#2", c.getDocumento());
        sql = sql.replace("#3", c.getEndereco());
        sql = sql.replace("#4", c.getTelefone());
        sql = sql.replace("#5", c.getSexo());
        sql = sql.replace("#6", c.getDataNasc().toString());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Cliente c){
        String sql = "update cliente set cli_nome='#1', cli_documento='#2', cli_endereco='#3', cli_telefone='#4', cli_sexo='#5', cli_datanasc='#6' where cli_cod="+c.getCodigo();
        sql = sql.replace("#1", c.getNome());
        sql = sql.replace("#2", c.getDocumento());
        sql = sql.replace("#3", c.getEndereco());
        sql = sql.replace("#4", c.getTelefone());
        sql = sql.replace("#5", c.getSexo());
        sql = sql.replace("#6", c.getDataNasc().toString());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(int id){
        String sql = "delete from cliente where cli_cod="+id;
        return Banco.getCon().manipular(sql);
    }
    public Cliente get(int id){
        Cliente aux = null;
        String sql = "select * from cliente where cli_cod="+id;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            if(rs.next())
                aux = new Cliente(rs.getInt("cli_cod"), rs.getString("cli_nome"), rs.getString("cli_documento"), rs.getString("cli_endereco"), rs.getString("cli_telefone"), rs.getString("cli_sexo"), rs.getDate("cli_datanasc").toLocalDate());
        }
        catch(Exception e){
        }
        return aux;
    }
    public List<Cliente> get(String filtro){
        List<Cliente> clientes = new ArrayList<>();
        
        String sql = "select * from cliente";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            while(rs.next())
                clientes.add( new Cliente(rs.getInt("cli_cod"), rs.getString("cli_nome"), rs.getString("cli_documento"), rs.getString("cli_endereco"), rs.getString("cli_telefone"), rs.getString("cli_sexo"), rs.getDate("cli_datanasc").toLocalDate()) );
        }
        catch(Exception e){
        }
        return clientes;
    }
    public Cliente getClienteEmp(String filtro){
        Cliente aux = null;
        String sql = "select * from cliente";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            if(rs.next())
                aux = new Cliente(rs.getInt("cli_cod"), rs.getString("cli_nome"), rs.getString("cli_documento"), rs.getString("cli_endereco"), rs.getString("cli_telefone"), rs.getString("cli_sexo"), rs.getDate("cli_datanasc").toLocalDate());
        }
        catch(Exception e){
        }
        return aux;
    }
    public int getNumeroExemplaresCliente(int id){
        int aux=0;
        String sql = "select * from cliente inner join emprestimo on cliente.cli_cod = emprestimo.cli_cod where cliente.cli_cod="+id;
        ResultSet rs = Banco.getCon().consultar(sql);
        try{
            if(rs.next())
                aux = rs.getInt("emp_qtde");
        }
        catch(Exception e){
        }
        return aux;
    }
}
