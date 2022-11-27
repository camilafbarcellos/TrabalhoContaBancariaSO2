package programa;

import java.util.HashMap;

/**
 *
 * @author camila
 */
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
    
    public void addCorrentista(String cpf, Correntista correntista) {
        try {
            this.correntistas.put(cpf, correntista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Correntista getCorrentista(String cpf) {
        try {
            return this.correntistas.get(cpf);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void removeCorrentista(String cpf) {
        try {
            this.correntistas.remove(cpf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
    public void addCorrentista(Correntista correntista) {
        if (this.correntistas == null) {
            this.correntistas = new ArrayList<>();
        }

        this.correntistas.add(correntista);
        this.quantCorrentistas++;
    }
    
    public void removeCorrentista(Correntista correntista) {
        this.correntistas.remove(correntista);
        this.quantCorrentistas--;
    }
    */
}
