import javax.swing.*;
import java.sql.*;

public class BDProdutos {
    //Classe para tratar das operações relacionadas ao banco de dados
    private Connection conn; //Connection é uma classe que representa uma conexão com um banco de dados

    //Construtor de classe:
    public BDProdutos(){
        conectarBanco();
    }

    //Conectar com o banco criado no terminal:
    //estabelece a conexão com o banco de dados derby e a armazena na variável conn.
    private void conectarBanco() {
        try {
            String url = "jdbc:derby:MeuBancoDeDados";
            conn = DriverManager.getConnection(url); // tenta estabelecer a conexão com o banco de dados.
            // se der certo, conn recebe a conexão, caso contrario, lança erro
        } catch (SQLException e) {
            e.printStackTrace(); //exibe o erro ocorrido
        }
    }


    //Adicionar produto
    public void adicionarProduto(Produto produto) throws SQLException {
        //Verificando se produto já está cadastrado no banco de dados. Escolhi o nome e a descrição como parâmetros de comparação.
        String checkSql = "SELECT COUNT(*) FROM produtos WHERE nome = ? AND descricao = ?"; // ? são placeholders que serão substituídos pelos valores do prod
        PreparedStatement checkStmt = conn.prepareStatement(checkSql); //objeto usado para executar consultas SQL de forma segura.
        checkStmt.setString(1, produto.getNome()); //troca primeiro ? por nome do produto
        checkStmt.setString(2, produto.getDescricao());
        ResultSet rs = checkStmt.executeQuery(); //executa operação sql e retorna tabela como resultado

        rs.next(); // acessando primeira linha da tab rs
        int count = rs.getInt(1); // copiando o valor da primeira coluna da primeira linha pra variavél cont

        rs.close();
        checkStmt.close();

        // se a conta for maior que 0, já existe produto com esse nome e descrição
        if (count > 0) {
            JOptionPane.showMessageDialog(null, "Produto já existe no banco de dados.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String sql = "INSERT INTO produtos (nome, descricao, preco, estoque) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, produto.getNome());
            pstmt.setString(2, produto.getDescricao());
            pstmt.setDouble(3, produto.getPreco());
            pstmt.setInt(4, produto.getQuantidade());
            pstmt.executeUpdate();
            pstmt.close();
            JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso. :)", "Boa!", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Vender produto. Usuário escolhe o id relacionado ao produto e quantidade que deseja comprar
    public void venderProduto(int id, int quantidade) {
        try {
            // verificando se produto existe
            String checkSql = "SELECT estoque FROM produtos WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) { //prod existe
                // verificando se estoque é suficiente:
                int estoqueAtual = rs.getInt("estoque");
                rs.close();
                checkStmt.close();

                if (estoqueAtual >= quantidade) { // há estoque suficiente
                    String updateSql = "UPDATE produtos SET estoque = estoque - ? WHERE id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setInt(1, quantidade);
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();
                    updateStmt.close();

                    JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else { // não há estoque o suficiente
                    JOptionPane.showMessageDialog(null, "Estoque insuficiente!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else { // produto não existe
                JOptionPane.showMessageDialog(null, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) { //algum erro relacionado com a conexão do bd
            e.printStackTrace();
        }
    }

    //Reposição de estoque
    public void reporEstoque (int id, int qtd){
        try {
            // verificando se produto existe
            String checkSql = "SELECT estoque FROM produtos WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            boolean ver = rs.next();
            rs.close();
            checkStmt.close();

            if (ver) { // produto existe
                    String updateSql = "UPDATE produtos SET estoque = estoque + ? WHERE id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setInt(1, qtd);
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();
                    updateStmt.close();
                    JOptionPane.showMessageDialog(null, "Reposição cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            } else { // produto não existe
                JOptionPane.showMessageDialog(null, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) { //algum erro relacionado com a conexão do bd
            e.printStackTrace();
        }
    }

    //Deletar produto:
    public void deletarProduto(int id) throws SQLException {
        // verificando se o produto existe
        String checkSql = "SELECT COUNT(*) FROM produtos WHERE id = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setInt(1, id);
        ResultSet rs = checkStmt.executeQuery();

        rs.next();
        int count = rs.getInt(1);
        rs.close();
        checkStmt.close();

        if (count == 0) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // confirmação antes da exclusão
        int confirmacao = JOptionPane.showConfirmDialog(null,
                "Tem certeza que deseja excluir o produto de ID " + id + "?",
                "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.NO_OPTION) {
            return; // se o usuário escolher "Não", interrompe o processo
        }

        // se o produto existe e foi confirmado, faz a exclusão
        String deleteSql = "DELETE FROM produtos WHERE id = ?";
        PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
        deleteStmt.setInt(1, id);
        deleteStmt.executeUpdate();
        deleteStmt.close();

        JOptionPane.showMessageDialog(null, "Produto excluído com sucesso! :)", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }


    //Listagem de produtos. Usei metodo parecido com o da tarefa 2.
    public String listarProdutos() {
        StringBuilder lista = new StringBuilder();

        try {
            String sql = "SELECT id, nome, descricao, preco, estoque FROM produtos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) { // rs percorre todos os produtos até retornar false (que é quando não possui next)
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                double preco = rs.getDouble("preco");
                int estoque = rs.getInt("estoque");

                // adicionando os dados do produto à string
                lista.append("ID: ").append(id)
                        .append(" | Nome: ").append(nome)
                        .append(" | Descrição: ").append(descricao)
                        .append(" | Preço: R$ ").append(preco)
                        .append(" | Estoque: ").append(estoque)
                        .append("\n");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar produtos: :(" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return "Erro ao buscar produtos :(.";
        }

        return !lista.isEmpty() ? lista.toString() : "Nenhum produto cadastrado.";
    }

}