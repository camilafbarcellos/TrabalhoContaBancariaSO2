# TrabalhoContaBancariaSO2
Trabalho avaliativo da segunda etapa da disciplina de Sistemas Operacionais II no semestre 2022/2.

Desenvolvido por Camila F. Barcellos.

Implementação de uma aplicação para gerenciar Agências e Contas Bancárias utilizando os conceitos de threads, comunicação entre processos, sincronização e multi sockets.

Funcionamento da aplicação:
- Dois tipos de clientes conectando ao servidor: admin, responsável pela manutenção de agências e contas bancárias, e user, habilitado para realizar procedimentos sobre a sua conta bancária.
- As agências bancárias poderão possuir diversas contas. Já as contas poderão possuir no máximo três correntistas.

Opções de implementação:
- Procolo simples utilizando string como tipo de dados, como Agência: “numero;descricao” e Conta Bancária: “agencia;numero_conta;nome_cliente;cpf”.
- Protocolo complexo utilizando tipos de dados distintos, como Agência: numero|int|descrição|string e Conta Bancária: agencia|int|numero_conta|int|nome_cliente|string|cpf|string.
