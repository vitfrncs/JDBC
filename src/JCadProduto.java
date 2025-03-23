import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class JCadProduto extends JEspecificacoesComuns {
    private JTextField tNome;
    private JTextField tDescricao;
    private JTextField tPreco;
    private JButton enviar;
    private BDProdutos bdProdutos; // Conexão com o banco

    public JCadProduto() {
        super("Cadastro de Produtos");
        bdProdutos = new BDProdutos();
    }

    @Override
    public void montarFormulario() {
        tNome = new JTextField(30);
        tDescricao = new JTextField(30);
        tPreco = new JTextField(7);
        enviar = new JButton("Cadastrar Produto");

        JPanel painelHead = new JPanel();
        adicionarAoPainel(painelHead, new FlowLayout(FlowLayout.CENTER), new JLabel("Cadastro de Produtos"));

        JPanel painelNome = new JPanel();
        adicionarAoPainel(painelNome, new FlowLayout(FlowLayout.LEFT, 10, 5), new JLabel("Nome:"), tNome);

        JPanel painelDescricao = new JPanel();
        adicionarAoPainel(painelDescricao, new FlowLayout(FlowLayout.LEFT, 10, 5), new JLabel("Descrição:"), tDescricao);

        JPanel painelPreco = new JPanel();
        adicionarAoPainel(painelPreco, new FlowLayout(FlowLayout.LEFT, 10, 5), new JLabel("Preço (R$):"), tPreco);

        JPanel painelBotaoEsp = new JPanel();
        adicionarAoPainel(painelBotaoEsp, new FlowLayout(), enviar);

        JPanel painelBotoes = new JPanel();
        adicionarAoPainel(painelBotoes, new FlowLayout(), verEstoque, atualizarEstoque, jfdeletar);

        frame.setLayout(new GridLayout(6, 1));
        frame.add(painelHead);
        frame.add(painelNome);
        frame.add(painelDescricao);
        frame.add(painelPreco);
        frame.add(painelBotaoEsp);
        frame.add(painelBotoes);
    }

    @Override
    public void adicionarEventos() {
        enviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = tNome.getText().trim();
                String descricao = tDescricao.getText().trim();
                String precoTexto = tPreco.getText().trim();

                // verificar se os campos estão vazios
                if (nome.isEmpty() || descricao.isEmpty() || precoTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // verificar se o preço é um número válido
                double preco;
                try {
                    preco = Double.parseDouble(precoTexto);
                    if (preco < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Preço inválido! Insira um número positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // criando obj produto e cadastrando no BD
                Produto novoProduto = new Produto(nome, descricao, preco, 1);
                try {
                    bdProdutos.adicionarProduto(novoProduto);
                    tNome.setText("");
                    tDescricao.setText("");
                    tPreco.setText("");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao cadastrar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        adicionarEventosComuns();
    }

}
