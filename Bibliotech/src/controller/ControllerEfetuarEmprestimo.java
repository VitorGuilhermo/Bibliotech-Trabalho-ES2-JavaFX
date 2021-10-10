package controller;

import bd.entidades.Cliente;
import bd.entidades.Emprestimo;
import bd.entidades.Exemplar;
import bd.entidades.Exemplar_Emprestimo;
import bd.entidades.Multa;
import bd.util.Banco;
import bd.util.Conexao;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Vitor Guilhermo
 */
public class ControllerEfetuarEmprestimo {
    private static ControllerEfetuarEmprestimo instancia;
    private static List<Exemplar> exemplares = new ArrayList<>();
    private static int qtdeLivrosJaEmprestados;

    private ControllerEfetuarEmprestimo() {
    }

    public static ControllerEfetuarEmprestimo retorna() {
        if (instancia == null) {
            instancia = new ControllerEfetuarEmprestimo();
        }
        return instancia;
    }

    public static void removeInstancia() {
        instancia = null;
    }

    public static ControllerEfetuarEmprestimo getInstance() {
        return instancia;
    }

    public List<Exemplar> getExemplares() {
        return exemplares;
    }

    public void novoCliente() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/bibliotech/TelaCadastrarCliente.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Cliente");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.showAndWait();

        ControllerCadastrarCliente.removeInstancia();
    }

    public boolean adicionarExe(Exemplar e) {
        if (exemplares.size() + qtdeLivrosJaEmprestados < 5) {
            if (!exemplares.contains(e)) {
                exemplares.add(e);
                return true;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: Não é possível adicionar Livros Repetidos ou com o Mesmo Código.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Erro: Um cliente só pode pegar 5 livros por vez!");
            alert.showAndWait();
        }
        return false;
    }

    public void removerExe(Exemplar e) {
        exemplares.remove(e);
    }

    public boolean finalizar(String txCodigo) {
        if (!txCodigo.isEmpty()) {
            boolean erro;
            if (!exemplares.isEmpty()) {
                Conexao con = Banco.getCon();
                Cliente cliente = new Cliente();
                cliente = cliente.buscarCliente(con, Integer.parseInt(txCodigo));

                Emprestimo emp = new Emprestimo(LocalDate.now(), LocalDate.now().plusDays(7), exemplares.size(), cliente, exemplares);
                //grava empréstimo
                erro = emp.gravar(con);
                if (erro) {
                    //grava exemplar_emprestimo
                    emp.setCodigo(Banco.getCon().getMaxPK("emprestimo", "emp_cod"));
                    for (Exemplar e : exemplares) {
                        boolean flag;
                        Exemplar_Emprestimo exempEmp = new Exemplar_Emprestimo(LocalDate.now().plusDays(7), new Multa(), e, emp);
                        flag = exempEmp.gravar(con);
                        if (!flag) {
                            erro = false;
                        }
                        //altera situacao exemplar
                        e.setSituacao(!e.isSituacao());
                        e.alteraSituacao(con);
                    }
                }
                if (!erro) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro ao gravar o empréstimo!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Empréstimo efetuado!");
                    alert.showAndWait();

                    return true;
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro: Não é possível gerar um empréstimo vazio!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Antes de efetuar o empréstimo insira um cliente!");
            alert.showAndWait();
        }
        return false;
    }

    public Cliente buscarCli(String sql, String filtro) {
        Cliente cli = new Cliente();
        Conexao con = Banco.getCon();

        cli = cli.getCliente(con, sql + filtro + "'");
        if (cli != null) {
            qtdeLivrosJaEmprestados = cli.getQtdeLivros(con);
            return cli;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Cliente inexistente. Verifique os dados!");
            alert.showAndWait();
        }
        return null;
    }

    public String buscarExe(String txFiltroExe) {
        String filtro = "upper(tit_nome) like '%#%'";

        filtro = filtro.replace("#", txFiltroExe.toUpperCase());

        if (txFiltroExe.isEmpty()) {
            return "";
        } else {
            return filtro;
        }
    }
}
