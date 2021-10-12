package bd.dal;

import bd.entidades.Cliente;
import bd.util.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAO {
    public boolean gravar(Conexao con, Cliente c){
        String sql = "insert into cliente values (default, '#1', '#2', '#3', '#4', '#5', '#6', true)";
        sql = sql.replace("#1", c.getNome());
        sql = sql.replace("#2", c.getDocumento());
        sql = sql.replace("#3", c.getEndereco());
        sql = sql.replace("#4", c.getTelefone());
        sql = sql.replace("#5", c.getSexo());
        sql = sql.replace("#6", c.getDataNasc().toString());
        return con.manipular(sql);
    }
    public boolean alterar(Conexao con, Cliente c){
        String sql = "update cliente set cli_nome='#1', cli_documento='#2', cli_endereco='#3', cli_telefone='#4', cli_sexo='#5', cli_datanasc='#6' where cli_cod="+c.getCodigo();
        sql = sql.replace("#1", c.getNome());
        sql = sql.replace("#2", c.getDocumento());
        sql = sql.replace("#3", c.getEndereco());
        sql = sql.replace("#4", c.getTelefone());
        sql = sql.replace("#5", c.getSexo());
        sql = sql.replace("#6", c.getDataNasc().toString());
        return con.manipular(sql);
    }
    public boolean desativar(Conexao con, int cod){
        String sql = "update cliente set cli_ativo=false where cli_cod="+cod;
        return con.manipular(sql);
    }
    public boolean apagar(Conexao con, int id){
        String sql = "delete from cliente where cli_cod="+id;
        return con.manipular(sql);
    }
    public Cliente get(Conexao con, int id){
        Cliente aux = null;
        String sql = "select * from cliente where cli_cod="+id;
        ResultSet rs = con.consultar(sql);
        try{
            if(rs.next())
                aux = new Cliente(rs.getInt("cli_cod"), rs.getString("cli_nome"), rs.getString("cli_documento"), rs.getString("cli_endereco"), rs.getString("cli_telefone"), rs.getString("cli_sexo"), rs.getDate("cli_datanasc").toLocalDate());
        }
        catch(Exception e){
        }
        return aux;
    }
    public Cliente validaAcesso(Conexao con, String doc){
        Cliente aux = null;
        String sql = "select * from cliente where cli_documento='"+doc+"' and cli_ativo=true";
        ResultSet rs = con.consultar(sql);
        try{
            if(rs.next())
                aux = new Cliente(rs.getInt("cli_cod"), rs.getString("cli_nome"), rs.getString("cli_documento"), rs.getString("cli_endereco"), rs.getString("cli_telefone"), rs.getString("cli_sexo"), rs.getDate("cli_datanasc").toLocalDate());
        }
        catch(Exception e){
        }
        return aux;
    }
    public List<Cliente> get(Conexao con, String filtro){
        List<Cliente> clientes = new ArrayList<>();
        
        String sql = "select * from cliente";
        if(!filtro.isEmpty()){
            sql += " where " + filtro;
            sql += " and cli_ativo=true";
        }
        else
            sql += " where cli_ativo=true";
        ResultSet rs = con.consultar(sql);
        try{
            while(rs.next())
                clientes.add( new Cliente(rs.getInt("cli_cod"), rs.getString("cli_nome"), rs.getString("cli_documento"), rs.getString("cli_endereco"), rs.getString("cli_telefone"), rs.getString("cli_sexo"), rs.getDate("cli_datanasc").toLocalDate()) );
        }
        catch(Exception e){
        }
        return clientes;
    }
    public Cliente getClienteEmp(Conexao con, String filtro){
        Cliente aux = null;
        String sql = "select * from cliente";
        if(!filtro.isEmpty()){
            sql += " where " + filtro;
            sql += " and cli_ativo=true";
        }
        else
            sql += " where cli_ativo=true";
            
        ResultSet rs = con.consultar(sql);
        try{
            if(rs.next())
                aux = new Cliente(rs.getInt("cli_cod"), rs.getString("cli_nome"), rs.getString("cli_documento"), rs.getString("cli_endereco"), rs.getString("cli_telefone"), rs.getString("cli_sexo"), rs.getDate("cli_datanasc").toLocalDate());
        }
        catch(Exception e){
        }
        return aux;
    }
    public int getNumeroExemplaresCliente(Conexao con, int id){
        int aux=0;
        String sql = "select * from cliente inner join emprestimo on cliente.cli_cod = emprestimo.cli_cod where cliente.cli_cod="+id;
        ResultSet rs = con.consultar(sql);
        try{
            if(rs.next())
                aux = rs.getInt("emp_qtde");
        }
        catch(Exception e){
        }
        return aux;
    }
}
