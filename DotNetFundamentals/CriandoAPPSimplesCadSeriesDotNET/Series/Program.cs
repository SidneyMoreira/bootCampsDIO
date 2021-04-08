using System;

namespace Series
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
                        utils.ListarSeries();
                        break;
                    case 2:
                        utils.InserirSerie();
                        break;
                    case 3:
                        utils.AtualizarSerie();
                        break;
                    case 4:
                        utils.ExcluirSerie();
                        break;
                    case 5:
                        utils.VisualizarSerie();
                        break;
                    case 6:
                        Console.Clear();
                        break;
                    case 0:
                        sair = true;
                        break;
                    default:
                        new ArgumentOutOfRangeException();
                        break;
                }
            }
        }
    }
}

