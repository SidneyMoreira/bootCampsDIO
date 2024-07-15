using DesafioFundamentos.Models;
using System;
using System.Globalization;

// Coloca o encoding para UTF8 para exibir acentuação
Console.OutputEncoding = System.Text.Encoding.UTF8;

decimal precoInicial = 0;
decimal precoPorHora = 0;

Console.WriteLine("Seja bem-vindo ao sistema de estacionamento!\n");

precoInicial = ObterDecimalDoUsuario("Digite o preço inicial:");
precoPorHora = ObterDecimalDoUsuario("Agora digite o preço por hora:");

// Instancia a classe Estacionamento, já com os valores obtidos anteriormente
Estacionamento estacionamento = new Estacionamento(precoInicial, precoPorHora);

bool exibirMenu = true;

// Realiza o loop do menu
while (exibirMenu)
{
    Console.Clear();
    Console.WriteLine("Digite a sua opção:");
    Console.WriteLine("1 - Cadastrar veículo");
    Console.WriteLine("2 - Remover veículo");
    Console.WriteLine("3 - Listar veículos");
    Console.WriteLine("4 - Encerrar");

    switch (Console.ReadLine())
    {
        case "1":
            estacionamento.AdicionarVeiculo();
            break;
        case "2":
            estacionamento.RemoverVeiculo();
            break;
        case "3":
            estacionamento.ListarVeiculos();
            break;
        case "4":
            exibirMenu = false;
            break;
        default:
            Console.WriteLine("Opção inválida. Tente novamente.");
            break;
    }

    if (exibirMenu)
    {
        Console.WriteLine("Pressione uma tecla para continuar");
        Console.ReadKey();
    }
}

Console.WriteLine("O programa se encerrou");

static decimal ObterDecimalDoUsuario(string mensagem)
{
    decimal valor;
    Console.WriteLine(mensagem);
    while (!decimal.TryParse(Console.ReadLine(), NumberStyles.AllowDecimalPoint, CultureInfo.InvariantCulture, out valor))
    {
        Console.WriteLine("Entrada inválida. Por favor, digite um número decimal válido.");
    }
    return valor;
}