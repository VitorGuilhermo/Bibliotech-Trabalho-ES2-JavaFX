package bd.entidades;

import bd.dal.ClienteDAL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.text.MaskFormatter;

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
        if(documento.equals(""))
            this.documento = documento;
        else
            this.documento = formataCpf(documento.trim());
        this.endereco = endereco;
        if(telefone.equals(""))
            this.telefone = telefone;
        else
            this.telefone = formataTelefone(telefone.trim());
        if(sexo.equals(""))
            this.sexo = sexo;
        else
            this.sexo = formataSexo(sexo.trim());
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
    public Cliente buscarCliente(int codigo) {
        return new ClienteDAL().get(codigo);
    }
    //Máscaras
    public String formataCpf(String cpf){
        try {
            MaskFormatter mask = new MaskFormatter("###.###.###-##");
            mask.setValueContainsLiteralCharacters(false);
            cpf = mask.valueToString(cpf);
        } 
        catch (ParseException ex) { }
        return cpf;
    }
    public String formataTelefone(String numero){
        try {
            MaskFormatter mask = new MaskFormatter("(##) #####-####");
            mask.setValueContainsLiteralCharacters(false);
            numero = mask.valueToString(numero);
        } 
        catch (ParseException ex) { }
        return numero;
    }
    public String formataSexo(String sexo){
        if(sexo.charAt(0) == 'M' || sexo.charAt(0) == 'm')
            return "M";
        else if(sexo.charAt(0) == 'F' || sexo.charAt(0) == 'f')
            return "F";
        return "I";
    }
    public Cliente getCliente(String filtro){
        return new ClienteDAL().getClienteEmp(filtro);
    }
    public int getQtdeLivros(){
        return new ClienteDAL().getNumeroExemplaresCliente(codigo);
    }
    @Override
    public String toString() {
        return nome;
    }

}
