package programa;

import java.io.*;
import java.net.*;

/**Cliente do banco
 * @author Camila Florão Barcellos
 */
public class Cliente extends Thread {

    // flag que indica quando se deve terminar a execução.
    private static boolean done = false;
    private Socket socket;
    // atributos do cliente
    private String ip;
    private String tipo;
    private PrintStream saida;

    // construtor que recebe o socket deste cliente

    /**Construtor que recebe e seta o socket do cliente
     * @author Camila Florão Barcellos
     * @param socket Socket - Socket do cliente
     */
    public Cliente(Socket socket) {
        setSocket(socket);
    }

    /**Método principal de execução do cliente que
     * controla o tipo de cliente conectado e
     * apresenta o menu de opções para cada tipo,
     * montando o protocolo enviado ao servidor
     * @author Camila Florão Barcellos
     * @param args String - Bloco de comandos
     */
    public static void main(String args[]) {
        try {
            System.out.println(". . . . . . . . . . . . . . . . . . . . .");
            System.out.println(".     BANCO MULTI SOCKETS - CLIENTE     .");
            System.out.println(". . . . . . . . . . . . . . . . . . . . .");
            System.out.println(". Camila F Barcellos                    .");
            System.out.println(". Sistemas Operacionais II              .");
            System.out.println(". Prof. Roberto Wiest                   .");
            System.out.println(". . . . . . . . . . . . . . . . . . . . .");
            
            // instancia a nova conexão
            Socket conexao = new Socket("127.0.0.1", 2222);
            // instancia objetos que permitem controlar o fluxo de comunicação
            PrintStream saida = new PrintStream(conexao.getOutputStream());
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            // autenticação do tipo de cliente
            String tipoCliente = "";
            do {
                System.out.print("\nTipo de cliente (admin / user): ");
                tipoCliente = teclado.readLine();
                if (!tipoCliente.equalsIgnoreCase("admin") && !tipoCliente.equalsIgnoreCase("user")) {
                    System.out.println("Tipo incorreto! Tente novamente...");
                }
            } while (!tipoCliente.equalsIgnoreCase("admin") && !tipoCliente.equalsIgnoreCase("user"));

            // validação de cliente admin
            String senhaAdmin = "";
            if (tipoCliente.equalsIgnoreCase("admin")) {
                do {
                    System.out.print("Senha de administrador (0-sair): ");
                    senhaAdmin = teclado.readLine();
                    if (!senhaAdmin.equals("123456") && !senhaAdmin.equals("0")) {
                        System.out.println("Senha incorreta! Tente novamente...");
                    } else if (senhaAdmin.equals("0")) {
                        saida.println("SAIR");
                        return;
                    }
                } while (!senhaAdmin.equals("123456") && !senhaAdmin.equals("0"));

                System.out.println("Autenticado com sucesso!");
            }
            
            // envia primeiramente o tipo do cliente ao servidor
            saida.println(tipoCliente);

            // executa a thread de recepção de mensagens
            Thread t = new Cliente(conexao);
            t.start();

            // mensagem contendo o protocolo a ser enviado ao servidor
            String protocoloEntrada;

            // menu de cada tipo de cliente
            if (tipoCliente.equalsIgnoreCase("user")) {
                int opcaoMenu;
                do {
                    System.out.println("\n. . . . . . . . . . . . . .");
                    System.out.println(".     MENU DE USUÁRIO     .");
                    System.out.println(". . . . . . . . . . . . . .");
                    System.out.println(". 1 - Depositar           .");
                    System.out.println(". 2 - Sacar               .");
                    System.out.println(". 3 - Verificar saldo     .");
                    System.out.println(". 0 - Sair                .");
                    System.out.println(". . . . . . . . . . . . . .");
                    System.out.print("Sua opção: ");
                    opcaoMenu = Integer.parseInt(teclado.readLine());
                    // começa a montar o protocolo
                    protocoloEntrada = String.valueOf(opcaoMenu);

                    switch (opcaoMenu) {
                        case 0:
                            saida.println("SAIR");
                            return;

                        case 1:
                            System.out.println("Protocolo de depósito:"
                                    + "\nnumeroAgencia;numeroConta;cpfCorrentista;valor");
                            acionarRecepcaoDeMensagens(conexao, teclado, saida, protocoloEntrada);
                            break;

                        case 2:
                            System.out.println("Protocolo de saque:"
                                    + "\nnumeroAgencia;numeroConta;cpfCorrentista;valor");
                            acionarRecepcaoDeMensagens(conexao, teclado, saida, protocoloEntrada);
                            break;

                        case 3:
                            System.out.println("Protocolo de extrato:"
                                    + "\nnumeroAgencia;numeroConta");
                            acionarRecepcaoDeMensagens(conexao, teclado, saida, protocoloEntrada);
                            break;

                        default:
                            System.out.println("Opção inválida!");
                    }
                } while (opcaoMenu != 0);

            } else {
                int opcaoMenu;
                do {
                    System.out.println("\n. . . . . . . . . . . . . . . . .");
                    System.out.println(".     MENU DE ADMINISTRADOR     .");
                    System.out.println(". . . . . . . . . . . . . . . . .");
                    System.out.println(". 1 - Criar Agência             .");
                    System.out.println(". 2 - Ler Agências              .");
                    System.out.println(". 3 - Atualizar Agência         .");
                    System.out.println(". 4 - Deletar Agência           .");
                    System.out.println(".                               .");
                    System.out.println(". 5 - Criar Conta               .");
                    System.out.println(". 6 - Ler Contas                .");
                    System.out.println(". 7 - Atualizar Conta           .");
                    System.out.println(". 8 - Deletar Conta             .");
                    System.out.println(". 0 - Sair                      .");
                    System.out.println(". . . . . . . . . . . . . . . . .");
                    System.out.print("Sua opção: ");
                    opcaoMenu = Integer.parseInt(teclado.readLine());
                    // começa a montar o protocolo
                    protocoloEntrada = String.valueOf(opcaoMenu);

                    switch (opcaoMenu) {
                        case 0:
                            saida.println("SAIR");
                            return;

                        case 1:
                            System.out.println("Protocolo de criação de Agência:"
                                    + "\nnumeroAgencia;descricaoAgencia");
                            acionarRecepcaoDeMensagens(conexao, teclado, saida, protocoloEntrada);
                            break;

                        case 2:
                            System.out.println("Pressione 'Enter' para prosseguir");
                            acionarRecepcaoDeMensagens(conexao, teclado, saida, protocoloEntrada);
                            break;

                        case 3:
                            System.out.println("Protocolo de atualização de Agência:"
                                    + "\nnumeroAgencia;descricaoAgencia"
                                    + "\nObs.: permitida apenas alteração de descrição");
                            acionarRecepcaoDeMensagens(conexao, teclado, saida, protocoloEntrada);
                            break;

                        case 4:
                            System.out.println("Protocolo de remoção de Agência:"
                                    + "\nnumeroAgencia");
                            acionarRecepcaoDeMensagens(conexao, teclado, saida, protocoloEntrada);
                            break;

                        case 5:
                            System.out.println("Protocolo de criação de Conta:"
                                    + "\nnumeroAgencia;numeroConta");
                            acionarRecepcaoDeMensagens(conexao, teclado, saida, protocoloEntrada);
                            break;

                        case 6:
                            System.out.println("Protocolo de listagem de Contas:"
                                    + "\nnumeroAgencia");
                            acionarRecepcaoDeMensagens(conexao, teclado, saida, protocoloEntrada);
                            break;

                        case 7:
                            System.out.println("Protocolo de atualização de Conta:"
                                    + "\nnumeroAgencia;numeroConta;cpfCorrentista;nomeCorrentista"
                                    + "\nObs.: em caso de adição de Correntista, informar CPF e nome,"
                                    + "\nem caso de remoção, informar apenas CPF!"
                                    + "\nInformar um CPF já existente irá remover o Correntista!");
                            acionarRecepcaoDeMensagens(conexao, teclado, saida, protocoloEntrada);
                            break;

                        case 8:
                            System.out.println("Protocolo de remoção de Conta:"
                                    + "\nnumeroAgencia;numeroConta");
                            acionarRecepcaoDeMensagens(conexao, teclado, saida, protocoloEntrada);
                            break;

                        default:
                            System.out.println("Opção inválida!");
                    }
                } while (opcaoMenu != 0);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    /**Recepção e tratamento das mensagens contendo os
     * protocolos a serem enviados ao servidor por meio
     * de um loop que lê a linha e envia para o servidor
     * @author Camila Florão Barcellos
     * @param conexao Socket - Socket do cliente
     * @param teclado BufferedReader - Fluxo do teclado
     * @param saida PrintStream - Fluxo da comunicação
     * @param protocoloEntrada String - Protocolo de envio
     */
    public static void acionarRecepcaoDeMensagens(Socket conexao, BufferedReader teclado,
            PrintStream saida, String protocoloEntrada) {
        try {
            // loop principal: obtendo uma linha digitada no teclado e
            // enviando-a para o servidor.
            String linha;
            while (true) {
                System.out.print("> ");
                // lê a linha digitada no teclado
                linha = teclado.readLine();
                // antes de enviar, verifica se a conexão não foi fechada
                if (done) {
                    break;
                }
                // junta a linha com o protocolo inicial contendo a opção
                protocoloEntrada += ";" + linha;
                System.out.println("Protocolo enviado: " + protocoloEntrada);
                // envia para o servidor
                saida.println(protocoloEntrada);
                return;
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    /**Execução da thread que realiza o controle do cliente
     * e das mensagens recebidas do servidor para impressão
     * ou finalização do cliente
     * @author Camila Florão Barcellos
     */
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String linha;
            while (true) {
                // captura a mensagem enviada pelo servidor
                linha = entrada.readLine();
                // caso a linha seja nula, finaliza conexão
                if (linha == null) {
                    System.out.println("\nEncerrando conexão do cliente...");
                    System.exit(0);
                }
                // caso a linha não seja nula, deve-se imprimi-la
                System.out.println();
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        // sinaliza para o main que a conexão encerrou
        done = true;
    }

    /**Método de retorno do tipo
     * @return tipo - Tipo do cliente
     */
    public String getTipo() {
        return tipo;
    }

    /**Método de atribuição do tipo
     * @param tipo String - Tipo do cliente
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**Método de retorno do socket
     * @return socket - Socket do cliente
     */
    public Socket getSocket() {
        return socket;
    }

    /**Método de atribuição do socket
     * @param socket Socket - Socket do cliente
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**Método de retorno da saída
     * @return saida - Saída do fluxo de comunicação
     */
    public PrintStream getSaida() {
        return saida;
    }

    /**Método de atribuição da saída
     * @param saida PrintStream - Saída do fluxo de comunicação
     */
    public void setSaida(PrintStream saida) {
        this.saida = saida;
    }

    /**Método de retorno do IP
     * @return ip - Endereço do cliente
     */
    public String getIp() {
        return ip;
    }

    /**Método de atribuição do IP
     * @param ip String - Endereço do cliente
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
}
