import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class JDelete extends JEspecificacoesComuns {
    private JTextField tId;
    private JButton deletar;
    private BDProdutos bdProdutos;

    public JDelete() {
        super("Deletar Produto");
        bdProdutos = new BDProdutos();
    }

    @Override
    public void montarFormulario() {
        tId = new JTextField(10);
        deletar = new JButton("Deletar Produto");

        JPanel painelHead = new JPanel();
        adicionarAoPainel(painelHead, new FlowLayout(FlowLayout.CENTER), new JLabel("Deletar Produto"));

        JPanel painelId = new JPanel();
        adicionarAoPainel(painelId, new FlowLayout(FlowLayout.LEFT, 10, 5), new JLabel("ID do Produto:"), tId);

        JPanel painelBotaoEsp = new JPanel();
        adicionarAoPainel(painelBotaoEsp, new FlowLayout(), deletar);

        JPanel painelBotoes = new JPanel();
        adicionarAoPainel(painelBotoes, new FlowLayout(), verEstoque, atualizarEstoque, cadastrar);

        frame.setLayout(new GridLayout(4, 1));
        frame.add(painelHead);
        frame.add(painelId);
        frame.add(painelBotaoEsp);
        frame.add(painelBotoes);


    }

    @Override
    public void adicionarEventos() {
        deletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idTexto = tId.getText().trim();

                if (idTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "O campo ID deve ser preenchido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int id;
                try {
                    id = Integer.parseInt(idTexto);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "ID inválido! Insira um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    bdProdutos.deletarProduto(id);
                    tId.setText("");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao deletar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        adicionarEventosComuns();
    }
}

