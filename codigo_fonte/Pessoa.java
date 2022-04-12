public class Pessoa implements Comparable<Pessoa> {
    private String nome;
    private int nif;

    public Pessoa(){
        this.nome = "";
        this.nif = 0;
    }

    public Pessoa(String nome, int nif){
        this.nome = nome;
        this.nif = nif;
    }

    public Pessoa(Pessoa pessoa){
        this.nome = pessoa.nome;
        this.nif = pessoa.nif;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNif() {
        return this.nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    @Override
    public int compareTo(Pessoa p){
        return this.nif - p.nif;
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;
        if (o == null || this.getClass() != o.getClass()){
            return false;
        }
        Pessoa pessoa = (Pessoa) o;
        return this.nome.equals(pessoa.nome) && this.nif == pessoa.nif;
    }


    @Override
    public String toString(){
        return String.format("{Pessoa, Nome: %s, NIF: %d}", this.nome, this.nif);
    }

    @Override
    public Pessoa clone(){
        return new Pessoa(this);
    }
}
