using System;
using System.Collections.Generic;

namespace DIO.Bank
{
    public class Utils
    {
        private static List<Conta> listContas = new List<Conta>();

        public void Depositar()
		{
			Console.Write("Digite o número da conta: ");
			int indiceConta = int.Parse(Console.ReadLine());

			Console.Write("Digite o valor a ser depositado: ");
			double valorDeposito = double.Parse(Console.ReadLine());

            listContas[indiceConta].Depositar(valorDeposito);
		}
        public void Sacar()
		{
			Console.Write("Digite o número da conta: ");
			int indiceConta = int.Parse(Console.ReadLine());

			Console.Write("Digite o valor a ser sacado: ");
			double valorSaque = double.Parse(Console.ReadLine());

            listContas[indiceConta].Sacar(valorSaque);
		}

		public void Transferir()
		{
			Console.Write("Digite o número da conta de origem: ");
			int indiceContaOrigem = int.Parse(Console.ReadLine());

            Console.Write("Digite o número da conta de destino: ");
			int indiceContaDestino = int.Parse(Console.ReadLine());

			Console.Write("Digite o valor a ser transferido: ");
			double valorTransferencia = double.Parse(Console.ReadLine());

            listContas[indiceContaOrigem].Transferir(valorTransferencia, listContas[indiceContaDestino]);
		}

		public void InserirConta()
		{
			Console.WriteLine("Inserir nova conta");

			Console.Write("Digite 1 para Conta Fisica ou 2 para Juridica: ");
			int entradaTipoConta = int.Parse(Console.ReadLine());

			Console.Write("Digite o Nome do Cliente: ");
			string entradaNome = Console.ReadLine();

			Console.Write("Digite o saldo inicial: ");
			double entradaSaldo = double.Parse(Console.ReadLine());

			Console.Write("Digite o crédito: ");
			double entradaCredito = double.Parse(Console.ReadLine());

			Conta novaConta = new Conta(tipoConta: (TipoConta)entradaTipoConta,
										saldo: entradaSaldo,
										credito: entradaCredito,
										nome: entradaNome);

			listContas.Add(novaConta);
		}

		public void ListarContas()
		{
			Console.WriteLine("Listar contas");

			if (listContas.Count == 0)
			{
				Console.WriteLine("Nenhuma conta cadastrada.");
                Console.ReadKey();
				return;
			}

			for (int i = 0; i < listContas.Count; i++)
			{
				Conta conta = listContas[i];
				Console.Write("#{0} - ", i);
				Console.WriteLine(conta);
                
			}
            Console.ReadKey();
        }
        public int ObterOpcaoUsuario()
            {
                Console.Clear();
                Console.WriteLine("\nInforme a Opção Desejada: ");
                Console.WriteLine("\t\t\t\t1 - Lista Contas");
                Console.WriteLine("\t\t\t\t2 - Inserir Nova Conta");
                Console.WriteLine("\t\t\t\t3 - Transferir");
                Console.WriteLine("\t\t\t\t4 - Sacar");
                Console.WriteLine("\t\t\t\t5 - Depositar");
                Console.WriteLine("\t\t\t\t6 - Limpara Tela");
                Console.WriteLine("\t\t\t\t0 - Sair");
                Console.Write("\r\n\t\t\t\tOpção: ");
                int opcaoUsuario = int.Parse(Console.ReadLine());
                return opcaoUsuario;
            }
    }
}