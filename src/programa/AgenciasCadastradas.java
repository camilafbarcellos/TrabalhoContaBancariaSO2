package programa;

import java.util.HashMap;

/**Objeto contendo uma lista hash
 * de agências para controlá-las
 * no servidor
 * @author Camila Florão Barcellos
 */
public class AgenciasCadastradas {

    private HashMap<Integer, Agencia> agencias;

    /**Construtor que inicializa a
     * lista hash de agências
     * @author Camila Florão Barcellos
     */
    public AgenciasCadastradas() {
        this.agencias = new HashMap<>();
    }

    /**Método de adição de um objeto agência no hash
     * @author Camila Florão Barcellos
     * @param numero int - Número da agência
     * @param agencia Agencia - Agência
     */
    public void addAgencia(int numero, Agencia agencia) {
        this.agencias.put(numero, agencia);
    }

    /**Método de retorno de um objeto agência no hash
     * pela chave de número que, caso não encontre, devolve
     * um objeto nulo
     * @author Camila Florão Barcellos
     * @param numero int - Número da agência
     * @return agencia - Agência com aquele número
     */
    public Agencia getAgencia(int numero) {
        return this.agencias.get(numero);
    }

    /**Método de remoção de um objeto agência no hash
     * pela chave de número
     * @author Camila Florão Barcellos
     * @param numero int - Número da agência
     */
    public void removeAgencia(int numero) {
        this.agencias.remove(numero);
    }

    /**Método de listagem dos objetos agência do hash
     * @author Camila Florão Barcellos
     */
    public void listarAgencias() {
        for (Agencia a : agencias.values()) {
            System.out.println("Número: " + a.getNumero()
                    + "\nDescrição: " + a.getDescricao() + "\n");
        }
    }

    /**Método de retorno do hash de agências
     * @return agencias - Lista de agências
     */
    public HashMap<Integer, Agencia> getAgencias() {
        return agencias;
    }

    /**Método de atribuição do hash de agências
     * @param agencias HashMap - Lista de agências
     */
    public void setAgencias(HashMap<Integer, Agencia> agencias) {
        this.agencias = agencias;
    }

}
