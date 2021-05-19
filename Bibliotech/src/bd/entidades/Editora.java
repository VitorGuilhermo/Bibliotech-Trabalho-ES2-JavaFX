package bd.entidades;

import bd.dal.EditoraDAL;
import java.text.ParseException;
import java.util.List;
import javax.swing.text.MaskFormatter;


public class Editora {
    private int codigo;
    private String nome;
    private String cnpj;

    
    public Editora() {
        this(0, "", "");
    }
    public Editora(String nome, String cnpj) {
        this(0, nome, cnpj);
    }
    public Editora(int codigo, String nome, String cnpj) {
        this.codigo = codigo;
        this.nome = nome;
        if(cnpj.equals(""))
            this.cnpj = cnpj;
        else
            this.cnpj = formataCnpj(cnpj);
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
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public boolean gravar() {
        return new EditoraDAL().gravar(this);
    }
    public boolean alterar() {
        return new EditoraDAL().alterar(this);
    }
    public boolean excluir() {
        return new EditoraDAL().apagar(codigo);
    }
    public List<Editora> buscar(String filtro) {
        return new EditoraDAL().get(filtro);
    }
    public String formataCnpj(String cnpj){
        try {
            MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
            mask.setValueContainsLiteralCharacters(false);
            cnpj = mask.valueToString(cnpj);
        } catch (ParseException ex) {
            
        }
        return cnpj;
    }
    @Override
    public String toString() {
        return nome;
    }
    
    
}
