package programa;

import java.io.*;
import java.net.*;
import java.util.*;

public class Banco extends Thread {

    private static AgenciasCadastradas listaAgencias;
    private Agencia agencia;
    private Conta conta;
    private Correntista correntista;
    private static List<Cliente> clientes;
    private Cliente cliente;
    private Socket conexao;
    private String tipoCliente;

    // construtor que recebe o socket deste cliente
    public Banco(Cliente c) {
        cliente = c;
        conexao = c.getSocket();
    }

    // execução da thread
    public void run() {
        try {
            // objetos que permitem controlar fluxo de comunicação
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getSocket().getInputStream()));
            PrintStream saida = new PrintStream(cliente.getSocket().getOutputStream());
            cliente.setSaida(saida);

            tipoCliente = entrada.readLine();
            // agora, verifica se string recebida é valida, pois
            // sem a conexão foi interrompida, a string é null.
            // Se isso ocorrer, deve-se terminar a execução.
            if (tipoCliente == null) {
                return;
            }
            cliente.setTipo(tipoCliente);

            // clientes é objeto compartilhado por várias threads!
            // De acordo com o manual da API, os métodos são
            // sincronizados. Portanto, não há problemas de acessos
            // simultâneos.
            // Loop principal: esperando por alguma string do cliente.
            // Quando recebe, envia a todos os conectados até que o
            // cliente envie linha em branco.
            // Verificar se linha em branco
            // Se não, pode-se compará-la com métodos string
            // OBS.: linha pode ser nula pois retorna para menu
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
                                    + ", da Agência " + numeroAgencia
                                    + ", no valor de R$ " + valor + " realizado por "
                                    + correntista.getNome() + " em "
                                    + cliente.getIp());
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
                                System.out.println("\n-> Correntista de CPF " + cpfCorrentista + " inexistente!");
                                break;
                            }
                            valor = Float.parseFloat(protocoloEntrada[4]);
                            conta.sacar(valor);
                            System.out.println("\n-> Saque na Conta " + numeroConta
                                    + ", da Agência " + numeroAgencia
                                    + ", no valor de R$ " + valor + " realizado por "
                                    + correntista.getNome() + " em "
                                    + cliente.getIp());
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
                    String descricaoAgencia;

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

                        case 3:

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

                        case 7:

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

            // Uma vez que o cliente enviou linha em branco, retira-se
            // fluxo de saída do vetor de clientes e fecha-se conexão.
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
            // Caso ocorra alguma excessão de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
    }

    public static void main(String args[]) {
        // instancia o vetor de clientes conectados
        clientes = new ArrayList<>();
        listaAgencias = new AgenciasCadastradas();
        try {
            // criando um socket que fica escutando a porta 2222.
            ServerSocket s = new ServerSocket(2222);
            System.out.println(". . . . . . . . . . . . . . . . . . . . . .");
            System.out.println(".     BANCO MULTI SOCKETS - SERVIDOR      .");
            System.out.println(". . . . . . . . . . . . . . . . . . . . . .");
            System.out.println(". Camila F Barcellos                      .");
            System.out.println(". Sistemas Operacionais II                .");
            System.out.println(". Prof. Roberto Wiest                     .");
            System.out.println(". . . . . . . . . . . . . . . . . . . . . .");
            // Loop principal
            while (true) {
                // aguarda algum cliente se conectar. A execução do
                // servidor fica bloqueada na chamada do método accept da
                // classe ServerSocket. Quando algum cliente se conectar
                // ao servidor, o método desbloqueia e retorna com um
                // objeto da classe Socket, que é porta da comunicação.
                System.out.println("\nAguardando conexão de clientes...");
                Socket conexao = s.accept();
                Cliente cliente = new Cliente(conexao);
                cliente.setIp(conexao.getRemoteSocketAddress().toString());

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
                // voltando ao loop, esperando mais alguém se conectar.
            }
        } catch (IOException e) {
            // caso ocorra alguma excessão de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
    }
}
