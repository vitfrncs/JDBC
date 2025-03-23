import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class JAttEstoque extends JEspecificacoesComuns {
    private JTextField tId;
    private JTextField tQuantidade;
    private JButton btnVender;
    private JButton btnRepor;
    private BDProdutos bdProdutos;

    public JAttEstoque() {
        super("Atualização de Estoque");
        bdProdutos = new BDProdutos();
    }

    @Override
    public void montarFormulario() {
        tId = new JTextField(10);
        tQuantidade = new JTextField(10);
        btnVender = new JButton("Vender Produto");
        btnRepor = new JButton("Repor Estoque");

        JPanel painelHead = new JPanel();
        adicionarAoPainel(painelHead, new FlowLayout(FlowLayout.CENTER), new JLabel("Atualização de Estoque"));

        JPanel painelId = new JPanel();
        adicionarAoPainel(painelId, new FlowLayout(FlowLayout.LEFT, 10, 5), new JLabel("ID do Produto:"), tId);

        JPanel painelQuantidade = new JPanel();
        adicionarAoPainel(painelQuantidade, new FlowLayout(FlowLayout.LEFT, 10, 5), new JLabel("Quantidade:"), tQuantidade);

        JPanel painelBotoesEsp = new JPanel();
        adicionarAoPainel(painelBotoesEsp, new FlowLayout(), btnVender, btnRepor);

        JPanel painelBotoes = new JPanel();
        adicionarAoPainel(painelBotoes, new FlowLayout(), verEstoque, cadastrar, jfdeletar);

        frame.setLayout(new GridLayout(5, 1));
        frame.add(painelHead);
        frame.add(painelId);
        frame.add(painelQuantidade);
        frame.add(painelBotoesEsp);
        frame.add(painelBotoes);
    }

    @Override
    public void adicionarEventos() {
        btnVender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(tId.getText().trim());
                    int quantidade = Integer.parseInt(tQuantidade.getText().trim());

                    if (quantidade <= 0) {
                        JOptionPane.showMessageDialog(frame, "A quantidade deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    bdProdutos.venderProduto(id, quantidade);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "ID e Quantidade devem ser números inteiros!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnRepor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(tId.getText().trim());
                    int quantidade = Integer.parseInt(tQuantidade.getText().trim());

                    if (quantidade <= 0) {
                        JOptionPane.showMessageDialog(frame, "A quantidade deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    bdProdutos.reporEstoque(id, quantidade);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "ID e Quantidade devem ser números inteiros!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        adicionarEventosComuns();
    }
}