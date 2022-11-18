package programa;

import java.io.*;
import java.net.*;

public class Cliente extends Thread {

    // Flag que indica quando se deve terminar a execução.
    private static boolean done = false;
    // parte que controla a recepção de mensagens deste cliente
    private Socket socket;
    // atributos do cliente
    private String ip;
    private String tipo;
    private PrintStream saida;

    // construtor que recebe o socket deste cliente
    public Cliente(Socket socket) {
        setSocket(socket);
    }

    public static void main(String args[]) {
        try {
            System.out.println(". . . . . . . . . . . . . . . . . . . . .");
            System.out.println(".     BANCO MULTI SOCKETS - CLIENTE     .");
            System.out.println(". . . . . . . . . . . . . . . . . . . . .");
            System.out.println(". Camila F Barcellos                    .");
            System.out.println(". Sistemas Operacionais II              .");
            System.out.println(". Prof. Roberto Wiest                   .");
            System.out.println(". . . . . . . . . . . . . . . . . . . . .");
            // Para se conectar a algum servidor, basta se criar um
            // objeto da classe Socket. O primeiro parâmetro é o IP ou
            // o endereço da máquina a qual se quer conectar e o
            // segundo parâmetro é a porta da aplicação. Neste caso,
            // utiliza-se o IP da máquina local (127.0.0.1) e a porta
            // da aplicação ServidorDeChat. Nada impede a mudança
            // desses valores, tentando estabelecer uma conexão com
            // outras portas em outras máquinas.
            Socket conexao = new Socket("127.0.0.1", 2222);
            // uma vez estabelecida a comunicação, deve-se obter os
            // objetos que permitem controlar o fluxo de comunicação
            PrintStream saida = new PrintStream(conexao.getOutputStream());
            // enviar antes de tudo o nome do usuário
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            // autenticacao de tipo de cliente
            String tipoCliente = "";
            do {
                System.out.printf("\nTipo de cliente (admin / user): ");
                tipoCliente = teclado.readLine();
                if (!tipoCliente.equalsIgnoreCase("admin") && !tipoCliente.equalsIgnoreCase("user")) {
                    System.out.println("Tipo incorreto! Tente novamente...");
                }
            } while (!tipoCliente.equalsIgnoreCase("admin") && !tipoCliente.equalsIgnoreCase("user"));
            saida.println(tipoCliente);

            // autenticacao de cliente admin
            String senhaAdmin = "";
            if (tipoCliente.equalsIgnoreCase("admin")) {
                do {
                    System.out.printf("Senha de administrador (0-sair): ");
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
            
            // Uma vez que tudo está pronto, antes de iniciar o loop
            // principal, executar a thread de recepção de mensagens.
            Thread t = new Cliente(conexao);
            t.start();

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

                    System.out.printf("Sua opção: ");
                    opcaoMenu = Integer.parseInt(teclado.readLine());

                    switch (opcaoMenu) {
                        case 0:
                            saida.println("SAIR");
                            return;
                        case 1:
                            // deposito();
                            System.out.println("Escolheu DEPOSITO");
                            acionarRecepcaoDeMensagens(conexao, teclado, saida);
                            break;
                        case 2:
                            // saque();
                            System.out.println("Escolheu SAQUE");
                            acionarRecepcaoDeMensagens(conexao, teclado, saida);
                            break;
                        case 3:
                            // extrato();
                            System.out.println("Escolheu EXTRATO");
                            acionarRecepcaoDeMensagens(conexao, teclado, saida);
                            break;
                        default:
                            System.out.println("Opção inválida!");
                    }
                } while (opcaoMenu != 0);
            } else {
                int opcaoMenu, opcaoOperacao;
                do {
                    System.out.println("\n. . . . . . . . . . . . . . . . .");
                    System.out.println(".     MENU DE ADMINISTRADOR     .");
                    System.out.println(". . . . . . . . . . . . . . . . .");
                    System.out.println(". 1 - Criar                     .");
                    System.out.println(". 2 - Ler                       .");
                    System.out.println(". 3 - Atualizar                 .");
                    System.out.println(". 4 - Deletar                   .");
                    System.out.println(". 0 - Sair                      .");
                    System.out.println(". . . . . . . . . . . . . . . . .");
                    System.out.printf("Sua opção: ");
                    opcaoMenu = Integer.parseInt(teclado.readLine());

                    switch (opcaoMenu) {
                        case 0:
                            saida.println("SAIR");
                            return;
                        case 1:
                            System.out.println("Escolheu CRIAR");
                            do {
                                System.out.printf("Agência (1) ou Conta Bancária (2): ");
                                opcaoOperacao = Integer.parseInt(teclado.readLine());

                                switch (opcaoOperacao) {
                                    case 1:
                                        System.out.println("Escolheu AGÊNCIA");
                                        acionarRecepcaoDeMensagens(conexao, teclado, saida);
                                        break;
                                    case 2:
                                        System.out.println("Escolheu CONTA");
                                        acionarRecepcaoDeMensagens(conexao, teclado, saida);
                                        break;
                                    default:
                                        System.out.println("Opção inválida!");
                                }
                            } while (opcaoOperacao != 1 && opcaoOperacao != 2);
                            break;
                        case 2:
                            System.out.println("Escolheu LER");
                            do {
                                System.out.printf("Agência (1) ou Conta Bancária (2): ");
                                opcaoOperacao = Integer.parseInt(teclado.readLine());

                                switch (opcaoOperacao) {
                                    case 1:
                                        System.out.println("Escolheu AGÊNCIA");
                                        acionarRecepcaoDeMensagens(conexao, teclado, saida);
                                        break;
                                    case 2:
                                        System.out.println("Escolheu CONTA");
                                        acionarRecepcaoDeMensagens(conexao, teclado, saida);
                                        break;
                                    default:
                                        System.out.println("Opção inválida!");
                                }
                            } while (opcaoOperacao != 1 && opcaoOperacao != 2);
                            break;
                        case 3:
                            System.out.println("Escolheu ATUALIZAR");
                            do {
                                System.out.printf("Agência (1) ou Conta Bancária (2): ");
                                opcaoOperacao = Integer.parseInt(teclado.readLine());

                                switch (opcaoOperacao) {
                                    case 1:
                                        System.out.println("Escolheu AGÊNCIA");
                                        acionarRecepcaoDeMensagens(conexao, teclado, saida);
                                        break;
                                    case 2:
                                        System.out.println("Escolheu CONTA");
                                        acionarRecepcaoDeMensagens(conexao, teclado, saida);
                                        break;
                                    default:
                                        System.out.println("Opção inválida!");
                                }
                            } while (opcaoOperacao != 1 && opcaoOperacao != 2);
                            break;
                        case 4:
                            System.out.println("Escolheu DELETAR");
                            do {
                                System.out.printf("Agência (1) ou Conta Bancária (2): ");
                                opcaoOperacao = Integer.parseInt(teclado.readLine());

                                switch (opcaoOperacao) {
                                    case 1:
                                        System.out.println("Escolheu AGÊNCIA");
                                        acionarRecepcaoDeMensagens(conexao, teclado, saida);
                                        break;
                                    case 2:
                                        System.out.println("Escolheu CONTA");
                                        acionarRecepcaoDeMensagens(conexao, teclado, saida);
                                        break;
                                    default:
                                        System.out.println("Opção inválida!");
                                }
                            } while (opcaoOperacao != 1 && opcaoOperacao != 2);
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

    public static void acionarRecepcaoDeMensagens(Socket conexao, BufferedReader teclado, PrintStream saida) {
        try {
            // loop principal: obtendo uma linha digitada no teclado e
            // enviando-a para o servidor.
            String linha;
            while (true) {
                // ler a linha digitada no teclado
                System.out.print("> ");
                linha = teclado.readLine();
                // antes de enviar, verifica se a conexão não foi fechada
                if (done) {
                    break;
                }
                // envia para o servidor
                saida.println(linha);
                return;
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    // execução da thread
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String linha;
            while (true) {
                // pega o que o servidor enviou
                linha = entrada.readLine();
                // caso a linha seja nula, finaliza conexao
                if (linha == null) {
                    System.out.println("\nEncerrando conexão do cliente...");
                    System.exit(0);
                }
                // caso a linha não seja nula, deve-se imprimi-la
                System.out.println();
                System.out.println(linha);
            }
        } catch (IOException e) {
            // caso ocorra alguma exceção de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
        // sinaliza para o main que a conexão encerrou.
        done = true;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public PrintStream getSaida() {
        return saida;
    }

    public void setSaida(PrintStream saida) {
        this.saida = saida;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
