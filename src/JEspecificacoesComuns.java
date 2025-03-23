import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class JEspecificacoesComuns {
    //atributos em comum das classes de interface gráfica (menos listagem):
    protected JFrame frame;
    protected JTextField tQtd;
    protected JButton cadastrar, verEstoque, atualizarEstoque, jfdeletar;

    public JEspecificacoesComuns(String titulo){
        frame = new JFrame(titulo);
        frame.setSize(470, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(250, 250, 250));

        tQtd = new JTextField(3);
        cadastrar = new JButton("Cadastrar produto");
        verEstoque = new JButton("Ver estoque");
        atualizarEstoque= new JButton("Atualizar estoque");
        jfdeletar = new JButton("Deletar produto");

        montarFormulario();
        adicionarEventos();

        frame.setVisible(true);
    }

    //esse metodo centraliza todas as inserções de componentes a um painel, o que melhora a manutenção do código
    protected void adicionarAoPainel(JPanel painel, LayoutManager layout, JComponent... componentes) {
        painel.setLayout(layout);
        for (JComponent c : componentes) {
            painel.add(c);
        }
    }


    public abstract void montarFormulario();
    public abstract void adicionarEventos();

    protected void adicionarEventosComuns() {
        cadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new JCadProduto();
            }
        });

        verEstoque.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false); // Esconde a tela atual
                new JVerEstoque(frame); // Passa a tela atual para permitir o retorno
            }
        });


        jfdeletar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new JDelete();
            }
        });

        atualizarEstoque.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new JAttEstoque();
            }
        });
    }
}