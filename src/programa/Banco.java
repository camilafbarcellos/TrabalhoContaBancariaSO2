package programa;

import java.io.*;
import java.net.*;
import java.util.*;

/**Servidor do banco
 * @author Camila Florão Barcellos
 */
public class Banco extends Thread {

    private static AgenciasCadastradas listaAgencias;
    private Agencia agencia;
    private Conta conta;
    private Correntista correntista;
    private static List<Cliente> clientes;
    private Cliente cliente;
    private Socket conexao;
    private String tipoCliente;

    /**Construtor que recebe o cliente e seta
     * o seu socket
     * @author Camila Florão Barcellos
     * @param c Cliente - Cliente do banco
     */
    public Banco(Cliente c) {
        cliente = c;
        conexao = c.getSocket();
    }

    /**Execução da thread que realiza o controle dos clientes
     * e dos protocolos de entrada enviados por eles para
     * realizar as ações sobre o banco
     * @author Camila Florão Barcellos
     */
    public void run() {
        try {
            // objetos que permitem controlar fluxo de comunicação
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getSocket().getInputStream()));
            PrintStream saida = new PrintStream(cliente.getSocket().getOutputStream());
            cliente.setSaida(saida);

            tipoCliente = entrada.readLine();
            if (tipoCliente == null) {
                return;
            }
            cliente.setTipo(tipoCliente);
            
            String linha = entrada.readLine();

            if (tipoCliente.equalsIgnoreCase("user")) {

                while (!linha.equals("SAIR") && !linha.trim().equals("")) {
                    // Protocolo de entrada de cliente tipo user:
                    // operacao;numeroAgencia;numeroConta;cpfCorrentista;valor
                    // OBS.: valor é parâmetro de saque e depósito
                    String[] protocoloEntrada = linha.split(";");
                    int operacao = Integer.parseInt(protocoloEntrada[0]);
                    int numeroAgencia, numeroConta;
                    float valor;
                    String cpfCorrentista;

                    switch (operacao) {
                        case 1: // protocolo operacao;numeroAgencia;numeroConta;cpfCorrentista;valor
                            numeroAgencia = Integer.parseInt(protocoloEntrada[1]);
                            if (listaAgencias.getAgencia(numeroAgencia) == null) {
                                System.out.println("\n-> Agência " + numeroAgencia + " inexistente!");
                                break;
                            }
                            agencia = listaAgencias.getAgencia(numeroAgencia);
                            numeroConta = Integer.parseInt(protocoloEntrada[2]);
                            if (agencia.getConta(numeroConta) == null) {
                                System.out.println("\n-> Conta " + numeroConta + " inexistente!");
                                break;
                            }
                            conta = agencia.getConta(numeroConta);
                            cpfCorrentista = protocoloEntrada[3];
                            if (conta.getCorrentista(cpfCorrentista) == null) {
                                System.out.println("\n-> Correntista de CPF " + cpfCorrentista + " inexistente!");
                                break;
                            }
                            correntista = conta.getCorrentista(cpfCorrentista);
                            valor = Float.parseFloat(protocoloEntrada[4]);
                            conta.depositar(valor);
                            System.out.println("\n-> Depósito na Conta " + conta.getNumero()
                                    + " da Agência " + numeroAgencia
                                    + " no valor de R$ " + valor + " realizado por "
                                    + cpfCorrentista + " em " + cliente.getIp());
                            break;

                        case 2: // protocolo operacao;numeroAgencia;numeroConta;cpfCorrentista;valor
                            numeroAgencia = Integer.parseInt(protocoloEntrada[1]);
                            if (listaAgencias.getAgencia(numeroAgencia) == null) {
                                System.out.println("\n-> Agência " + numeroAgencia + " inexistente!");
                                break;
                            }
                            agencia = listaAgencias.getAgencia(numeroAgencia);
                            numeroConta = Integer.parseInt(protocoloEntrada[2]);
                            if (agencia.getConta(numeroConta) == null) {
                                System.out.println("\n-> Conta " + numeroConta + " inexistente!");
                                break;
                            }
                            conta = agencia.getConta(numeroConta);
                            cpfCorrentista = protocoloEntrada[3];
                            if (conta.getCorrentista(cpfCorrentista) == null) {
                                System.out.println("\n-> Correntista de CPF " + cpfCorrentista+ " inexistente!");
                                break;
                            }
                            valor = Float.parseFloat(protocoloEntrada[4]);
                            conta.sacar(valor);
                            System.out.println("\n-> Saque na Conta " + numeroConta
                                    + " da Agência " + numeroAgencia
                                    + " no valor de R$ " + valor + " realizado por "
                                    + cpfCorrentista + cliente.getIp());
                            break;

                        case 3: // protocolo operacao;numeroAgencia;numeroConta
                            numeroAgencia = Integer.parseInt(protocoloEntrada[1]);
                            if (listaAgencias.getAgencia(numeroAgencia) == null) {
                                System.out.println("\n-> Agência " + numeroAgencia + " inexistente!");
                                break;
                            }
                            agencia = listaAgencias.getAgencia(numeroAgencia);
                            numeroConta = Integer.parseInt(protocoloEntrada[2]);
                            if (agencia.getConta(numeroConta) == null) {
                                System.out.println("\n-> Conta " + numeroConta + " inexistente!");
                                break;
                            }
                            conta = agencia.getConta(numeroConta);
                            System.out.println("\n-> Extrato da Conta " + numeroConta
                                    + " da Agência " + numeroAgencia + " em "
                                    + cliente.getIp());
                            conta.verificarSaldo();
                            break;

                        default:
                            break;
                    }

                    // espera por uma nova linha.
                    linha = entrada.readLine();
                }

            } else {
                while (!linha.equals("SAIR") && !linha.trim().equals("")) {
                    // Protocolo de entrada de cliente tipo admin depende
                    // do alvo da operação realizada - Agencia ou Conta
                    String[] protocoloEntrada = linha.split(";");
                    // primeiro parâmetro é a operação
                    int operacao = Integer.parseInt(protocoloEntrada[0]);
                    int numeroAgencia, numeroConta;
                    String descricaoAgencia, cpfCorrentista, nomeCorrentista;

                    switch (operacao) {
                        case 1: // protocolo operacao;numeroAgencia;descricaoAgencia
                            numeroAgencia = Integer.parseInt(protocoloEntrada[1]);
                            descricaoAgencia = protocoloEntrada[2];
                            if (listaAgencias.getAgencia(numeroAgencia) != null) {
                                System.out.println("\n-> Agência " + numeroAgencia
                                        + " já possui cadastro!");
                                break;
                            }
                            agencia = new Agencia(numeroAgencia, descricaoAgencia);
                            listaAgencias.addAgencia(numeroAgencia, agencia);
                            System.out.println("\n-> Agência " + numeroAgencia + " criada em "
                                    + cliente.getIp());
                            break;

                        case 2: // protocolo operacao
                            System.out.println("\n-> Leitura de Agências em "
                                    + cliente.getIp() + ":");
                            listaAgencias.listarAgencias();
                            break;

                        case 3: // protocolo operacao;numeroAgencia;descricaoAgencia
                            numeroAgencia = Integer.parseInt(protocoloEntrada[1]);
                            descricaoAgencia = protocoloEntrada[2];
                            if (listaAgencias.getAgencia(numeroAgencia) != null) {
                                agencia = listaAgencias.getAgencia(numeroAgencia);
                                agencia.setDescricao(descricaoAgencia);
                                System.out.println("\n-> Agência " + numeroAgencia
                                        + " alterada em " + cliente.getIp());
                                break;
                            }
                            System.out.println("\n-> Agência " + numeroAgencia + " inexistente!");
                            break;

                        case 4: // protocolo operacao;numeroAgencia
                            numeroAgencia = Integer.parseInt(protocoloEntrada[1]);
                            if (listaAgencias.getAgencia(numeroAgencia) != null) {
                                listaAgencias.removeAgencia(numeroAgencia);
                                System.out.println("\n-> Agência " + numeroAgencia
                                        + " removida em " + cliente.getIp());
                                break;
                            }
                            System.out.println("\n-> Agência " + numeroAgencia + " inexistente!");
                            break;

                        case 5: // protocolo operacao;numeroAgencia;numeroConta
                            numeroAgencia = Integer.parseInt(protocoloEntrada[1]);
                            numeroConta = Integer.parseInt(protocoloEntrada[2]);
                            if (listaAgencias.getAgencia(numeroAgencia) != null) {
                                agencia = listaAgencias.getAgencia(numeroAgencia);
                                if (agencia.getConta(numeroConta) != null) {
                                    System.out.println("\n-> Conta " + numeroConta
                                            + " já possui cadastro na Agência "
                                            + numeroAgencia + "!");
                                    break;
                                }
                                conta = new Conta(agencia, numeroConta);
                                agencia.addConta(numeroConta, conta);
                                System.out.println("\n-> Conta " + numeroConta
                                        + " criada na Agência " + numeroAgencia
                                        + " em " + cliente.getIp());
                                break;
                            }
                            System.out.println("\n-> Agência " + numeroAgencia + " inexistente!");
                            break;

                        case 6: // protocolo operacao;numeroAgencia
                            numeroAgencia = Integer.parseInt(protocoloEntrada[1]);
                            if (listaAgencias.getAgencia(numeroAgencia) != null) {
                                agencia = listaAgencias.getAgencia(numeroAgencia);
                                System.out.println("\n-> Leitura de Contas da Agência "
                                        + numeroAgencia + " em " + cliente.getIp() + ":");
                                agencia.listarContas();
                                break;
                            }
                            System.out.println("\n-> Agência " + numeroAgencia + " inexistente!");
                            break;

                        case 7: // protocolo numeroAgencia;numeroConta;cpfCorrentista;nomeCorrentista
                            numeroAgencia = Integer.parseInt(protocoloEntrada[1]);
                            numeroConta = Integer.parseInt(protocoloEntrada[2]);
                            cpfCorrentista = protocoloEntrada[3];
                            if (listaAgencias.getAgencia(numeroAgencia) != null) {
                                agencia = listaAgencias.getAgencia(numeroAgencia);
                                if (agencia.getConta(numeroConta) != null) {
                                    conta = agencia.getConta(numeroConta);
                                    if (conta.getCorrentista(cpfCorrentista) == null) {
                                        if (conta.getQuantCorrentistas() < 3) {
                                            // caso adição, requer parâmetro nomeCorrentista
                                            nomeCorrentista = protocoloEntrada[4];
                                            correntista = new Correntista(cpfCorrentista, nomeCorrentista);
                                            conta.addCorrentista(cpfCorrentista, correntista);
                                            System.out.println("\n-> Correntista de CPF "
                                                    + cpfCorrentista + " adicionado à Conta "
                                                    + numeroConta + " da Agência " + numeroAgencia
                                                    + " em " + cliente.getIp());
                                            break;
                                        }
                                        System.out.println("\n-> Conta " + numeroConta 
                                                + " possui quantidade máxima de Correntistas!");
                                        break;
                                    }

                                    conta.removeCorrentista(cpfCorrentista);
                                    System.out.println("\n-> Correntista de CPF "
                                            + cpfCorrentista + " removido da Conta "
                                            + numeroConta + " da Agência " + numeroAgencia
                                            + " em " + cliente.getIp());
                                    break;
                                }
                                System.out.println("\n-> Conta " + numeroConta + " inexistente!");
                                break;
                            }
                            System.out.println("\n-> Agência " + numeroAgencia + " inexistente!");
                            break;

                        case 8: // protocolo operacao;numeroAgencia;numeroConta
                            numeroAgencia = Integer.parseInt(protocoloEntrada[1]);
                            numeroConta = Integer.parseInt(protocoloEntrada[2]);
                            if (listaAgencias.getAgencia(numeroAgencia) != null) {
                                agencia = listaAgencias.getAgencia(numeroAgencia);
                                if (agencia.getConta(numeroConta) != null) {
                                    conta = agencia.getConta(numeroConta);
                                    agencia.removeConta(conta.getNumero());
                                    System.out.println("\n-> Conta " + numeroConta
                                            + " removida da Agência " + numeroAgencia
                                            + " em " + cliente.getIp());
                                    break;
                                }
                                System.out.println("\n-> Conta " + numeroConta + " inexistente!");
                                break;
                            }
                            System.out.println("\n-> Agência " + numeroAgencia + " inexistente!");
                            break;

                        default:
                            break;
                    }

                    // espera por uma nova linha.
                    linha = entrada.readLine();
                }
            }
            
            clientes.remove(cliente);
            saida.close();
            System.out.println("\nCliente " + cliente.getTipo() + " em " + cliente.getIp() + " saiu...");
            System.out.println("\nLista de clientes conectados:");
            for (Cliente c : clientes) {
                if (c.getTipo() == null) {
                    System.out.println("-> Não-autenticado em " + c.getIp());
                } else {
                    System.out.println("-> " + c.getTipo() + " em " + c.getIp());
                }
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    /**Método principal de execução do servidor que
     * realiza o tratamento das conexões dos clientes
     * ao servidor por meio de threads
     * @author Camila Florão Barcellos
     * @param args String - Bloco de comandos
     */
    public static void main(String args[]) {
        // instancia a lista de clientes conectados
        clientes = new ArrayList<>();
        // instancia o objeto de agências cadastradas
        listaAgencias = new AgenciasCadastradas();
        try {
            // criando um socket que fica escutando a porta 2222
            ServerSocket s = new ServerSocket(2222);
            System.out.println(". . . . . . . . . . . . . . . . . . . . . .");
            System.out.println(".     BANCO MULTI SOCKETS - SERVIDOR      .");
            System.out.println(". . . . . . . . . . . . . . . . . . . . . .");
            System.out.println(". Camila F Barcellos                      .");
            System.out.println(". Sistemas Operacionais II                .");
            System.out.println(". Prof. Roberto Wiest                     .");
            System.out.println(". . . . . . . . . . . . . . . . . . . . . .");
            
            while (true) {
                System.out.println("\nAguardando conexão de clientes...");
                // aceita a conexão
                Socket conexao = s.accept();
                // instancia cliente com o socket
                Cliente cliente = new Cliente(conexao);
                // atribui o endereço do cliente ao seu IP
                cliente.setIp(conexao.getRemoteSocketAddress().toString());
                // adiciona o cliente na lista de clientes do servidor
                clientes.add(cliente);

                System.out.println("\nNovo cliente em " + conexao.getRemoteSocketAddress());
                System.out.println("\nLista de clientes conectados:");
                for (Cliente c : clientes) {
                    if (c.getTipo() == null) {
                        System.out.println("-> Não-autenticado em " + c.getIp());
                    } else {
                        System.out.println("-> " + c.getTipo() + " em " + c.getIp());
                    }
                }

                // cria uma nova thread para tratar essa conexão
                Thread t = new Banco(cliente);
                t.start();
                // volta ao loop e espera mais conexões
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
}
