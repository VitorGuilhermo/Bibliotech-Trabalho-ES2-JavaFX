 package bd.entidades;

import bd.dal.ClienteDAO;
import bd.util.Conexao;
import controller.ControllerAdicionarExemplarCont;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.text.MaskFormatter;

public class Cliente implements Observer {

    private int codigo;
    private String nome, documento, endereco, telefone;
    private String sexo;
    private LocalDate dataNasc;

    public Cliente() {
        this(0, "", "", "", "", "", LocalDate.now());
    }
    public Cliente(String doc){
        if(doc.equals(""))
            this.documento = doc;
        else
            this.documento = formataCpf(doc.trim());
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

    public boolean gravar(Conexao con) {
        return new ClienteDAO().gravar(con, this);
    }
    public boolean alterar(Conexao con) {
        return new ClienteDAO().alterar(con, this);
    }
    public boolean excluir(Conexao con) {
        return new ClienteDAO().apagar(con, codigo);
    }
    public boolean desativar(Conexao con){
        return new ClienteDAO().desativar(con, codigo);
    }
    public List<Cliente> buscar(Conexao con, String filtro) {
        return new ClienteDAO().get(con, filtro);
    }
    public Cliente buscarCliente(Conexao con, int codigo) {
        return new ClienteDAO().get(con, codigo);
    }
    public Cliente buscarCli(Conexao con, String nome){
        return new ClienteDAO().getCli(con, nome);
    }
    
    //M??scaras
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
    public Cliente getCliente(Conexao con, String filtro){
        return new ClienteDAO().getClienteEmp(con, filtro);
    }
    public Cliente verificaLogin(Conexao con, String senha){
        if(!senha.equals(""))
            senha = formataCpf(senha.trim());
        if(senha.equals(documento))
            return new ClienteDAO().validaAcesso(con, documento);
        return null;
    }
    public int getQtdeLivros(Conexao con){
        return new ClienteDAO().getNumeroExemplaresCliente(con, codigo);
    }
    @Override
    public String toString() {
        return nome;
    }

    @Override
    public void update() {
        ControllerAdicionarExemplarCont.getInstance().addMensagem("Notificando "+this.nome+"...\n");
    }

}
