using System.Globalization;
using System.Numerics;
using System;

namespace Revisao
{
    class Program
    {
        static void Main(string[] args)
        {
            Aluno[] alunos = new Aluno[5];
            var indiceAluno = 0;

            bool sair = false;
            
            while (!sair)
            {
                int opcaoUsuario = ObterOpcaoUsuario();

                switch (opcaoUsuario)
                {
                    case 1:
                        //TODO: Adicionar Aluno
                        Aluno aluno = new Aluno();
                        Console.Write("\nInfome o Nome do Aluno: ");
                        aluno.Nome = Console.ReadLine();

                        Console.Write("\nInfome a Nota do Aluno: ");

                        if (decimal.TryParse(Console.ReadLine(), out decimal nota))
                        {
                            aluno.Nota = nota;
                        }
                        else
                        {
                            new ArgumentException("Valor da nota deve ser decimal!!");
                        }

                        alunos[indiceAluno] = aluno;
                        indiceAluno++;

                        break;
                    case 2:
                        //TODO: Listagem Aluno
                        Console.Clear();
                        foreach (var listaAlunos in alunos)
                        {
                            if(!string.IsNullOrEmpty(listaAlunos.Nome))
                            {
                                Console.WriteLine($"\tNome do Aluno: {listaAlunos.Nome} \t\tNota: {listaAlunos.Nota}");
                            }
                        }
                        Console.Write("\n\nPressione uma tecla para continuar...");
                        Console.ReadKey();
                        break;
                    case 3:
                        decimal notaTotal = 0;
                        var nrAlunos = 0;
                        for (int i = 0; i < alunos.Length; i++)
                        {
                            if(!string.IsNullOrEmpty(alunos[i].Nome))
                            {
                                notaTotal += alunos[i].Nota;
                                nrAlunos++;
                            }
                        }
                        decimal mediaGeral = notaTotal / nrAlunos;

                        Conceito conceitoGeral;

                        if (mediaGeral < 2)
                        {
                            conceitoGeral = Conceito.E;
                        }
                        else if (mediaGeral < 4)
                        {
                            conceitoGeral = Conceito.D;
                        }
                        else if (mediaGeral < 6)
                        {
                            conceitoGeral = Conceito.C;
                        }
                        else if (mediaGeral < 8)
                        {
                            conceitoGeral = Conceito.B;
                        }
                        else
                        { 
                            conceitoGeral = Conceito.A;
                        }
                        
                        Console.Clear();
                        Console.WriteLine($"Média Geral: {mediaGeral} - Conceito: {conceitoGeral}");
                        Console.Write("\n\nPressione uma tecla para continuar...");
                        Console.ReadKey();
                        break;
                    case 0:
                        //TODO: Sair
                        sair = true;
                        Console.Clear();
                        break;
                    default:
                        new ArgumentOutOfRangeException();
                        break;
                }
                Console.WriteLine("Obrigado por utilizar nossos serviços!!");
                Console.ReadKey();
            }


        }

        private static int ObterOpcaoUsuario()
        {
            Console.Clear();
            Console.WriteLine("\nInforme a Opção Desejada: ");
            Console.WriteLine("\t\t\t\t1 - Inserir Novo Aluno");
            Console.WriteLine("\t\t\t\t2 - Listar Alunos");
            Console.WriteLine("\t\t\t\t3 - Calcular Média Geral");
            Console.WriteLine("\t\t\t\t0 - Sair");
            Console.Write("\r\n\t\t\t\tOpção: ");
            int opcaoUsuario = int.Parse(Console.ReadLine());
            return opcaoUsuario;
        }
    }
}
