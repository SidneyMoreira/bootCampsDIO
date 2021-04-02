using System;
namespace Instrucoes
{
    public class Program
    {
        static void Declaracoes(){
            int a;
            int b = 2, c = 3;
            const int d = 4;
            a = 1;

            Console.WriteLine(a+b+c+d);
        }
        
        static void InstrucaoIf(string[] args)
        {
            if (args.Length == 0)
            {
                Console.WriteLine("Nenhum arqumento");
            } else if (args.Length == 1)
            {
                Console.WriteLine("Um arqumento");
            } else
            {
                Console.WriteLine($"{args.Length} argumentos");
            }
        }

        static void IntrucaoSwitch(string[] args)
        {
            int numeroDeArgumentos = args.Length;
            switch(numeroDeArgumentos)
            {
                case 0:
                    Console.WriteLine("Nenhum arqumento");
                    break;
                case 1:
                    Console.WriteLine("Um arqumento");
                    break;
                default:
                    Console.WriteLine($"{args.Length} argumentos");
                    break;
            }
        }
        
        static void InstrucaoWhile(string[] args)
        {
            int i = 0;
            while (i < args.Length)
            {
                Console.WriteLine(args[i]);
                i++;
            }
        }

        static void InstrucaoDo(string[] args)
        {   
            string texto;
            do
            {
                texto = Console.ReadLine();
                Console.WriteLine(texto);

            } while (!string.IsNullOrEmpty(texto));
        }

        static void InstrucaoFor(string[] args)
        {
            for (int i = 0; i < args.Length; i++)
            {
                Console.WriteLine(args[i]);
            }
        }

        static void InstrucaoForeach(string[] args)
        {
            foreach (string s in args)
            {
                Console.WriteLine(s);
            }
        }

        static void InstrucaoBreak(string[] args)
        {
            while (true)
            {
                string s = Console.ReadLine();

                if(string.IsNullOrEmpty(s))
                {
                    break;
                }
                Console.WriteLine(s);
            }
        }

        static void InstrucaoContinue(string[] args)
        {
            for (int i = 0; i < args.Length; i++)
            {
                if(args[i].StartsWith("/"))
                {
                    continue;
                }
                Console.WriteLine(args[i]);
            }
        }

        static void InstrucaoReturn()
        {
            int somar(int a, int b){
                return a + b;
            }

            Console.WriteLine(somar(1, 2));
            Console.WriteLine(somar(3, 4));
            Console.WriteLine(somar(5, 6));

            return;
        }

        static void InstrucaoTryCatchFinallyThrow(string[] args)
        {
            double Dividir(double x, double y)
            {
                if(y == 0)
                {
                    throw new DivideByZeroException();
                }
                return x /y;
            }

            try
            {
                if(args.Length !=2)
                {
                    throw new InvalidOperationException("Informe 2 numeros");
                }

                double x = double.Parse(args[0]);
                double y = double.Parse(args[1]);
                Console.WriteLine(Dividir(x, y));
            }
            catch (InvalidOperationException e)
            {
                Console.WriteLine(e.Message);
            }
            catch (Exception e)
            {
                Console.WriteLine($"Erro genérico: {e.Message}");
            }
            finally
            {
                Console.WriteLine("Até Breve!");
            }
        }

        static void InstrucaoUsing(string[] args)
        {
            using (System.IO.TextWriter w = System.IO.File.CreateText("texto.txt"))
            {
                w.WriteLine("Linha 1");
                w.WriteLine("Linha 2");
                w.WriteLine("Linha 3");
            }
        }
    }
}