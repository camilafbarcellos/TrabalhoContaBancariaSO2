package programa;

import java.util.HashMap;

/**
 *
 * @author camila
 */
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
        try {
            this.contas.put(numeroConta, conta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Conta getConta(int numeroConta) {
        try {
            return this.contas.get(numeroConta);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void removeConta(int numeroConta) {
        try {
            this.contas.remove(numeroConta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void listarContas() {
        System.out.println(contas);
    }
    
    /*
    public void listarContas() {        
        Iterator it = contas.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry el = (Map.Entry) it.next();
            int numero = (Integer) el.getKey();
            Conta c = this.getConta(numero);
            System.out.println(c.getNumero() + " = " + c.getSaldo());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
    */

}
