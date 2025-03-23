public class Produto {
    private String nome;
    private String descricao;
    private double preco;
    private int quantidade;

    public Produto(String d, String n, double p, int q) {
        this.descricao = d;
        this.nome = n;
        this.preco = p;
        this.quantidade = q;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}