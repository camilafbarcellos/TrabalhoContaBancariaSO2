package programa;

import java.util.HashMap;

/**Objeto de conta bancária
 * @author Camila Florão Barcellos
 */
public class Conta {

    private Agencia agencia;
    private int numero;
    private float saldo;
    private HashMap<String, Correntista> correntistas;
    private int quantCorrentistas;

    /**Construtor que recebe a agência e o
     * número da conta e inicializa o saldo,
     * a quantidade de correntistas e a lista
     * hash de correntistas
     * @author Camila Florão Barcellos
     * @param agencia Agencia - Agência
     * @param numero int - Número
     */
    public Conta(Agencia agencia, int numero) {
        this.agencia = agencia;
        this.numero = numero;
        this.saldo = 0;
        this.quantCorrentistas = 0;
        this.correntistas = new HashMap<>();
    }

    /**Realiza depósitos na conta ao acrescentar
     * ao saldo o valor do parâmetro
     * @author Camila Florão Barcellos
     * @param valor float - Valor de depósito
     */
    public void depositar(float valor) {
        this.saldo += valor;
    }

    /**Realiza saques na conta ao decrementar
     * do saldo o valor do parâmetro
     * @author Camila Florão Barcellos
     * @param valor float - Valor de saque
     */
    public void sacar(float valor) {
        this.saldo -= valor;
    }

    /**Realiza o extrato da conta ao exibir o
     * saldo disponível
     * @author Camila Florão Barcellos
     */
    public void verificarSaldo() {
        System.out.println("Saldo disponível: R$ " + saldo);
    }

     /**Método de retorno da quantidade de correntistas
     * @return quantCorrentistas - Quantidade de correntistas
     */
    public int getQuantCorrentistas() {
        return quantCorrentistas;
    }

    /**Método de atribuição da quantidade de correntistas
     * @param quantCorrentistas int - Quantidade de correntistas
     */
    public void setQuantCorrentistas(int quantCorrentistas) {
        this.quantCorrentistas = quantCorrentistas;
    }

    /**Método de retorno da agência
     * @return agencia - Agência da conta
     */
    public Agencia getAgencia() {
        return agencia;
    }

    /**Método de atribuição da agência
     * @param agencia Agencia - Agência da conta
     */
    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    /**Método de retorno do número
     * @return numero - Número da conta
     */
    public int getNumero() {
        return numero;
    }

    /**Método de atribuição do número
     * @param numero int - Número da conta
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**Método de retorno do saldo
     * @return saldo - Saldo da conta
     */
    public float getSaldo() {
        return saldo;
    }

    /**Método de atribuição do saldo
     * @param saldo float - Saldo da conta
     */
    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    /**Método de retorno do hash de correntistas
     * @return correntistas - Lista de correntistas
     */
    public HashMap<String, Correntista> getCorrentistas() {
        return correntistas;
    }

    /**Método de atribuição do hash de correntistas
     * @param correntistas HashMap - Lista de correntistas
     */
    public void setCorrentistas(HashMap<String, Correntista> correntistas) {
        this.correntistas = correntistas;
    }

    /**Método de adição de um objeto correntista no hash
     * que aumenta a quantidade de correntistas
     * @author Camila Florão Barcellos
     * @param cpf String - CPF do correntista
     * @param correntista Correntista - Correntista
     */
    public void addCorrentista(String cpf, Correntista correntista) {
        this.correntistas.put(cpf, correntista);
        this.quantCorrentistas++;
    }

    /**Método de retorno de um objeto correntista no hash
     * pela chave de CPF que, caso não encontre, devolve
     * um objeto nulo
     * @author Camila Florão Barcellos
     * @param cpf String - CPF do correntista
     * @return correntista - Correntista com aquele CPF
     */
    public Correntista getCorrentista(String cpf) {
        return this.correntistas.get(cpf);
    }

    /**Método de remoção de um objeto correntista no hash
     * pela chave de CPF e diminui a quantidade de correntistas
     * @author Camila Florão Barcellos
     * @param cpf String - CPF do correntista
     */
    public void removeCorrentista(String cpf) {
        this.correntistas.remove(cpf);
        this.quantCorrentistas--;
    }

    /**Método de listagem dos objetos correntista do hash
     * @author Camila Florão Barcellos
     */
    public void listarCorrentistas() {
        System.out.println("Correntistas cadastrados:");
        for (Correntista c : correntistas.values()) {
            System.out.println("CPF: " + c.getCpf()
                    + "\nNome: " + c.getNome() + "\n");
        }
    }
}
