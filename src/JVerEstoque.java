import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JVerEstoque {
    private BDProdutos bdProdutos;
    private JFrame estoqueFrame;

    public JVerEstoque(JFrame telaAnterior) {
        bdProdutos = new BDProdutos();

        estoqueFrame = new JFrame("Estoque de Produtos");
        estoqueFrame.setSize(470, 250);
        estoqueFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas esta janela
        estoqueFrame.getContentPane().setBackground(new Color(250, 250, 250));

        // painel principal com layout BorderLayout
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));

        // título da página
        JLabel titulo = new JLabel("Estoque Atual");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setHorizontalAlignment(JLabel.CENTER);

        // criando um JTextArea para mostrar os produtos
        JTextArea areaLista = new JTextArea(15, 30);
        areaLista.setEditable(false);
        areaLista.setOpaque(true);
        areaLista.setBorder(BorderFactory.createEmptyBorder());

        // criando uma barra de rolagem
        JScrollPane scrollPane = new JScrollPane(areaLista);

        // margens, para melhor espaçamento
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        // usando metodo listar produtos, que retorna uma string tratada com todos os produtos separados por linha
        String listaProdutos = bdProdutos.listarProdutos();
        areaLista.setText(listaProdutos);
        areaLista.setFont(new Font("Arial", Font.PLAIN, 14));

        // criando o botão "Voltar"
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setBackground(new Color(220, 220, 220));
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                estoqueFrame.dispose();
                if (telaAnterior != null) {
                    telaAnterior.setVisible(true); // torna a tela anterior visível novamente
                }
            }
        });

        // Painel para o botão
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnVoltar);

        // Adicionando componentes ao painel principal
        painelPrincipal.add(titulo, BorderLayout.NORTH);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        estoqueFrame.add(painelPrincipal);

        estoqueFrame.setVisible(true);
    }
}
