package programa;

import java.util.HashMap;

public class AgenciasCadastradas {

    private HashMap<Integer, Agencia> agencias;

    public AgenciasCadastradas() {
        this.agencias = new HashMap<>();
    }

    public void addAgencia(int numero, Agencia agencia) {
        this.agencias.put(numero, agencia);
    }

    public Agencia getAgencia(int numero) {
        return this.agencias.get(numero);
    }

    public void removeAgencia(int numero) {
        this.agencias.remove(numero);
    }

    public void listarAgencias() {
        for (Agencia a : agencias.values()) {
            System.out.println("Número: " + a.getNumero()
                    + "\nDescrição: " + a.getDescricao() + "\n");
        }
    }

    public HashMap<Integer, Agencia> getAgencias() {
        return agencias;
    }

    public void setAgencias(HashMap<Integer, Agencia> agencias) {
        this.agencias = agencias;
    }

}
