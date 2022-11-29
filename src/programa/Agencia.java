package programa;

import java.util.HashMap;

public class Agencia {

    private int numero;
    private String descricao;
    private HashMap<Integer, Conta> contas;

    public Agencia(int numero, String descricao) {
        this.numero = numero;
        this.descricao = descricao;
        this.contas = new HashMap<>();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public HashMap<Integer, Conta> getContas() {
        return contas;
    }

    public void setContas(HashMap<Integer, Conta> contas) {
        this.contas = contas;
    }

    public void addConta(int numeroConta, Conta conta) {
        this.contas.put(numeroConta, conta);
    }

    public Conta getConta(int numeroConta) {
        return this.contas.get(numeroConta);
    }

    public void removeConta(int numeroConta) {
        this.contas.remove(numeroConta);
    }

    public void listarContas() {
        for (Conta c : contas.values()) {
            System.out.println("Número: " + c.getNumero()
                    + "\nAgência: " + c.getAgencia().getNumero()
                    + "\nSaldo: R$ " + c.getSaldo()
                    + "\nQuantidade de correntistas: " + c.getQuantCorrentistas()
                    + "\n");
        }
    }

}
