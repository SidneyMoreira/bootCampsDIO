using System;
using Classes.Heranca;

namespace _03_Classes
{
    class Program
    {
        static void Main(string[] args)
        {
            Ponto p1 = new Ponto(2,4);
            
            Ponto3D p2 = new Ponto3D(4,5,6);

            p1.CalcularDistancia3();
            p2.CalcularDistancia3();
        }
    }
}
