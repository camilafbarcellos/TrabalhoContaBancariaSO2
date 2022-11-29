package programa;

import java.util.HashMap;

/**Objeto de agência bancária
 * @author Camila Florão Barcellos
 */
public class Agencia {

    private int numero;
    private String descricao;
    private HashMap<Integer, Conta> contas;

    /**Construtor que seta o número e descrição
     * da conta e inicializa a lista hash de contas
     * @author Camila Florão Barcellos
     * @param numero int - Número da agência
     * @param descricao String - Descrição da agência
     */
    public Agencia(int numero, String descricao) {
        this.numero = numero;
        this.descricao = descricao;
        this.contas = new HashMap<>();
    }

    /**Método de retorno do número
     * @return numero - Número da agência
     */
    public int getNumero() {
        return numero;
    }

    /**Método de atribuição do número
     * @param numero int - Número da agência
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**Método de retorno da descrição
     * @return descricao - Descrição da agência
     */
    public String getDescricao() {
        return descricao;
    }

    /**Método de atribuição da descrição
     * @param descricao String - Descrição da agência
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**Método de retorno do hash de contas
     * @return contas - Lista de contas
     */
    public HashMap<Integer, Conta> getContas() {
        return contas;
    }

    /**Método de atribuição do hash de contas
     * @param contas HashMap - Lista de contas
     */
    public void setContas(HashMap<Integer, Conta> contas) {
        this.contas = contas;
    }

    /**Método de adição de um objeto conta no hash
     * @author Camila Florão Barcellos
     * @param numeroConta int - Número da conta
     * @param conta Conta - Conta
     */
    public void addConta(int numeroConta, Conta conta) {
        this.contas.put(numeroConta, conta);
    }

    /**Método de retorno de um objeto conta no hash
     * pela chave de número que, caso não encontre, devolve
     * um objeto nulo
     * @author Camila Florão Barcellos
     * @param numeroConta int - Número da conta
     * @return conta - Conta com aquele número
     */
    public Conta getConta(int numeroConta) {
        return this.contas.get(numeroConta);
    }

    /**Método de remoção de um objeto conta no hash
     * pela chave de número
     * @author Camila Florão Barcellos
     * @param numeroConta int - Número da conta
     */
    public void removeConta(int numeroConta) {
        this.contas.remove(numeroConta);
    }

    /**Método de listagem dos objetos conta do hash
     * junto da listagem de seus correntistas
     * @author Camila Florão Barcellos
     */
    public void listarContas() {
        for (Conta c : contas.values()) {
            System.out.println("Número: " + c.getNumero()
                    + "\nAgência: " + c.getAgencia().getNumero()
                    + "\nSaldo: R$ " + c.getSaldo()
                    + "\nQuantidade de correntistas: " + c.getQuantCorrentistas());
            c.listarCorrentistas();
        }
    }

}
