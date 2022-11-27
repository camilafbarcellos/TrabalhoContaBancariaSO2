package programa;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author camila
 */
public class AgenciasCadastradas {

    private HashMap<Integer, Agencia> agencias;

    public AgenciasCadastradas() {
        this.agencias = new HashMap<>();
    }

    public void addAgencia(int numero, Agencia agencia) {
        try {
            this.agencias.put(numero, agencia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Agencia getAgencia(int numero) {
        try {
            return this.agencias.get(numero);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Agencia getAgenciaOld(int numeroAgencia) {
        Iterator it = agencias.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry el = (Map.Entry) it.next();
            int numero = (Integer) el.getKey();
            if (numero == numeroAgencia) {
                Agencia a = this.getAgencia(numero);
                return a;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        
        return null;
    }

    public void removeAgencia(int numero) {
        try {
            this.agencias.remove(numero);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarAgencias() {
        Iterator it = agencias.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry el = (Map.Entry) it.next();
            int numero = (Integer) el.getKey();
            Agencia a = this.getAgenciaOld(numero);
            System.out.println(a.getNumero() + " = " + a.getDescricao());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public HashMap<Integer, Agencia> getAgencias() {
        return agencias;
    }

    public void setAgencias(HashMap<Integer, Agencia> agencias) {
        this.agencias = agencias;
    }

}
