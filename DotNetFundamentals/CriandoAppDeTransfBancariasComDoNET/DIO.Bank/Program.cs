using System;
using System.Collections.Generic;

namespace DIO.Bank
{
    class Program
    {
        
        static void Main(string[] args)
        {
            Utils utils = new Utils();
            
            bool sair = false;

            while (!sair)
            {
                int opcaoUsuario = utils.ObterOpcaoUsuario();

                switch (opcaoUsuario)
                {
                    case 1:
                        utils.ListarContas();
                        break;
                    case 2:
                        utils.InserirConta();
                        break;
                    case 3:
                        utils.Transferir();
                        break;
                    case 4:
                        utils.Sacar();
                        break;
                    case 5:
                        utils.Depositar();
                        break;
                    case 6:
                        Console.Clear();
                        break;
                    case 0:
                        sair=true;
                        break;
                    default:
                        new ArgumentOutOfRangeException();
                        break;
                }
            }
            Console.WriteLine("Obrigado por utilizar nossos serviços!!");
            Console.ReadKey();
        }

        
    }
}
