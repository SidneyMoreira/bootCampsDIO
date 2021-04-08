using System;
using System.Collections.Generic;

namespace Series
{
    public class Utils
    {
        static SerieRepositorio repositorio = new SerieRepositorio();
        public void ListarSeries()

        {
            Console.WriteLine("Listar Séries");
            var lista = repositorio.Lista();

            if (lista.Count == 0)
            {
                Console.WriteLine("Nenhuma série cadastrada.");
                Console.ReadKey();
                return;
            }

            foreach (var serie in lista)
            {
                var excluido = serie.retornaExcluido();

                Console.WriteLine("#ID {0}: - {1} -  {2}", serie.retornaId(), serie.retornaTitulo(), (excluido ? "** Excluído **" : ""));


            }
            Console.ReadKey();
        }
        public void InserirSerie()
        {
            repositorio.retornaCampos(0);
        }
        public void AtualizarSerie()
        {
            repositorio.retornaCampos(1);

        }
        public void ExcluirSerie()
        {
            Console.WriteLine("Digite o ID da série: ");
            int indiceSerie = int.Parse(Console.ReadLine());

            Console.WriteLine("Deseja realmente excluir essa serie? (S = sim/ N = não) ");
            string confirma = Console.ReadLine().ToUpper();

            if (confirma.Equals("S"))
            {
                repositorio.Exclui(indiceSerie);
                Console.WriteLine("Série ID: {0} excluída com sucesso!!", indiceSerie);
                Console.ReadKey();
            }
        }

        public void VisualizarSerie()
        {
            Console.WriteLine("Digite o ID da série: ");
            int indiceSerie = int.Parse(Console.ReadLine());

            var serie = repositorio.RetornaPorId(indiceSerie);

            Console.WriteLine(serie);
            Console.ReadKey();
        }


        public int ObterOpcaoUsuario()
        {
            Console.Clear();
            Console.WriteLine("\nInforme a Opção Desejada: ");
            Console.WriteLine("\t\t\t\t1 - Lista Séries");
            Console.WriteLine("\t\t\t\t2 - Inserir Nova Série");
            Console.WriteLine("\t\t\t\t3 - Atualizar Série");
            Console.WriteLine("\t\t\t\t4 - Excluir Série");
            Console.WriteLine("\t\t\t\t5 - Visualizar Série");
            Console.WriteLine("\t\t\t\t6 - Limpar a Tela");
            Console.WriteLine("\t\t\t\t0 - Sair");
            Console.Write("\r\n\t\t\t\tOpção: ");
            int opcaoUsuario = int.Parse(Console.ReadLine());
            return opcaoUsuario;
        }
    }
}