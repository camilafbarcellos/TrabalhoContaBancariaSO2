package programa;

/**Objeto de correntista
 * @author Camila Florão Barcellos
 */
public class Correntista {
    private String nome;
    private String cpf;
    
    /**Construtor que seta o nome e o cpf
     * @author Camila Florão Barcellos
     * @param nome String - Nome do correntista
     * @param cpf String - CPF do correntista
     */
    public Correntista(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    /**Método de retorno do nome
     * @return nome - Nome do correntista
     */
    public String getNome() {
        return nome;
    }

    /**Método de atribuição do nome
     * @param nome String - Nome do correntista
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**Método de retorno do CPF
     * @return cpf - CPF do correntista
     */
    public String getCpf() {
        return cpf;
    }

    /**Método de atribuição do CPF
     * @param cpf String - CPF do correntista
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
