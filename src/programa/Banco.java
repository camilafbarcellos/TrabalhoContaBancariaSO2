/*
 O exemplo abaixo está no livro: “Aprendendo Java 2”
 Mello, Chiara e Villela Novatec Editora Ltda. – www.novateceditora.com.br
 Tipo de comunicação entre todos os clientes. Um Cliente envia a mensagem e os demais (todos)
 recebem.
 */
package programa;

import java.io.*;
import java.net.*;
import java.util.*;

public class Banco extends Thread {

    // Parte que controla as conexões por meio de threads.
    // Note que a instanciação está no main.
    private static List<Cliente> clientes;

    private Cliente cliente;

    // socket deste cliente
    private Socket conexao;

    // tipo deste cliente
    private String tipoCliente;

    // construtor que recebe o socket deste cliente
    public Banco(Cliente c) {
        cliente = c;
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
            // Verificar se linha é null (conexão interrompida)
            // Se não for nula, pode-se compará-la com métodos string
            String linha = entrada.readLine();
            while (linha != null) {
                // Recebe 2 parâmetros - param1;param2
                // Divide a string a cada ;
                String[] protocoloEntrada = linha.split(";");
                int param1 = Integer.parseInt(protocoloEntrada[0]);
                String param2 = protocoloEntrada[1];
                switch (param1) {
                    case 1:
                        System.out.println("Rodando comando mkdir no diretório ...\\" + param2);
                        break;
                    case 2:
                        System.out.println("Rodando comando rmdir no diretório ...\\" + param2);
                        break;
                    case 3:
                        System.out.println("Rodando comando dir no diretório ...\\" + param2);
                        break;
                    default:
                        System.out.print("Comando de parâmetro " + param1 + " inexistente!");
                }
                // espera por uma nova linha.
                linha = entrada.readLine();
            }
            // Uma vez que o cliente enviou linha em branco, retira-se
            // fluxo de saída do vetor de clientes e fecha-se conexão.
            clientes.remove(cliente);
            System.out.println("\nCliente " + cliente.getTipo() + " em " + cliente.getIp() + " saiu...");
            System.out.println("\nLista de clientes conectados:");
                for (Cliente c : clientes) {
                    if(c.getTipo() == null) {
                        System.out.println("-> Não-autenticado em " + c.getIp());
                    } else {
                        System.out.println("-> " + c.getTipo() + " em " + c.getIp());
                    }
                }
            saida.close();
        } catch (IOException e) {
            // Caso ocorra alguma excessão de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
    }

    public static void main(String args[]) {
        // instancia o vetor de clientes conectados
        clientes = new ArrayList<>();
        try {
            // criando um socket que fica escutando a porta 2222.
            ServerSocket s = new ServerSocket(2222);
            System.out.println(". . . . . . . . . . . . . . . . . . . . .");
            System.out.println(".     BANCO MULTI SOCKETS - OPÇÃO 1     .");
            System.out.println(". . . . . . . . . . . . . . . . . . . . .");
            System.out.println(". Camila F Barcellos                    .");
            System.out.println(". Sistemas Operacionais II              .");
            System.out.println(". Prof. Roberto Wiest                   .");
            System.out.println(". . . . . . . . . . . . . . . . . . . . .");
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
                    if(c.getTipo() == null) {
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
