package programa;

import java.util.HashMap;

public class Conta {

    private Agencia agencia;
    private int numero;
    private float saldo;
    private HashMap<String, Correntista> correntistas;
    private int quantCorrentistas;

    public Conta(Agencia agencia, int numero) {
        this.agencia = agencia;
        this.numero = numero;
        this.saldo = 0;
        this.quantCorrentistas = 0;
        this.correntistas = new HashMap<>();
    }

    public void depositar(float valor) {
        this.saldo += valor;
    }

    public void sacar(float valor) {
        this.saldo -= valor;
    }

    public void verificarSaldo() {
        System.out.println("Saldo disponível: R$ " + saldo);
    }

    public int getQuantCorrentistas() {
        return quantCorrentistas;
    }

    public void setQuantCorrentistas(int quantCorrentistas) {
        this.quantCorrentistas = quantCorrentistas;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public HashMap<String, Correntista> getCorrentistas() {
        return correntistas;
    }

    public void setCorrentistas(HashMap<String, Correntista> correntistas) {
        this.correntistas = correntistas;
    }

    public Boolean addCorrentista(String cpf, Correntista correntista) {
        if (this.quantCorrentistas < 3) {
            this.correntistas.put(cpf, correntista);
            return true;
        } else {
            return false;
        }
    }

    public Correntista getCorrentista(String cpf) {
        return this.correntistas.get(cpf);
    }

    public void removeCorrentista(String cpf) {
        this.correntistas.remove(cpf);
    }

    public void listarCorrentistas() {
        System.out.println("{");
        for (Correntista c : correntistas.values()) {
            System.out.println("CPF: " + c.getCpf()
                    + "\nNome: " + c.getNome() + "\n");
        }
        System.out.println("}");
    }
}
