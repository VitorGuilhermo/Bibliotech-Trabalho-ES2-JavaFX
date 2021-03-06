package bd.entidades;

import bd.dal.BibliotecarioDAO;
import bd.util.Conexao;


public class Bibliotecario {
    private int cod;
    private String nome, documento, senha;

    
    public Bibliotecario() {
        this(0, "", "", "");
    }
    public Bibliotecario(String documento, String senha) {
        this(0, "", documento, senha);
    }
    public Bibliotecario(String nome, String documento, String senha) {
        this(0, nome, documento, senha);
    }
    public Bibliotecario(int cod, String nome, String documento, String senha) {
        this.cod = cod;
        this.nome = nome;
        this.documento = documento;
        this.senha = senha;
    }

    
    public int getCod() {
        return cod;
    }
    public void setCod(int cod) {
        this.cod = cod;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public Bibliotecario verificaLogin(Conexao con){
        Bibliotecario aux = new BibliotecarioDAO().get(con, documento, senha);
        return aux;
    }
    public String getNomeBibliotecario(Conexao con){
        return new BibliotecarioDAO().get(con, documento, senha).getNome();
    }
    @Override
    public String toString() {
        return nome;
    }
    
    
}
