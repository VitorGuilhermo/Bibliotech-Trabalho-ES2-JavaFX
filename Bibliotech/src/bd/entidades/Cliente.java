package bd.entidades;

import bd.dal.ClienteDAL;
import java.time.LocalDate;
import java.util.List;

public class Cliente {

    private int codigo;
    private String nome, documento, endereco, telefone;
    private String sexo;
    private LocalDate dataNasc;

    public Cliente() {
        this(0, "", "", "", "", "", LocalDate.now());
    }

    public Cliente(String nome, String documento, String endereco, String telefone, String sexo, LocalDate dataNasc) {
        this(0, nome, documento, endereco, telefone, sexo, dataNasc);
    }

    public Cliente(int codigo, String nome, String documento, String endereco, String telefone, String sexo, LocalDate dataNasc) {
        this.codigo = codigo;
        this.nome = nome;
        this.documento = documento;
        this.endereco = endereco;
        this.telefone = telefone;
        this.sexo = sexo;
        this.dataNasc = dataNasc;
    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
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
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public LocalDate getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public boolean gravar() {
        return new ClienteDAL().gravar(this);
    }
    public boolean alterar() {
        return new ClienteDAL().alterar(this);
    }
    public boolean excluir() {
        return new ClienteDAL().apagar(codigo);
    }
    public List<Cliente> buscar(String filtro) {
        return new ClienteDAL().get(filtro);
    }

    @Override
    public String toString() {
        return nome;
    }

}
