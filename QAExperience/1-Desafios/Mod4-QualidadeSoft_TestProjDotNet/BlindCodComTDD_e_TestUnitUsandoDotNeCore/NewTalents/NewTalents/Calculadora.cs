using System;
using System.Collections.Generic;

namespace NewTalents
{
    public class Calculadora

    {
        
        private List<string> listaHistorico;
        private string data;

        public Calculadora(string data)
        {
           listaHistorico = new List<string>();
            this.data = data;
        }
        public int somar(int val1, int val2)
        {
            int res = val1 + val2;
            listaHistorico.Insert(0, "Resultado Soma: " + res + " - " + data);
            return res;
        }

        public int subtrair(int val1, int val2)
        {
            int res = val1 - val2;
            listaHistorico.Insert(0, "Resultado Subtracao: " + res + " - " + data);
            return res;
        }

        public int multiplicar(int val1, int val2)
        {
            int res = val1 * val2;
            listaHistorico.Insert(0, "Resultado Multiplicacao: " + res + " - " + data);
            return res;
        }

        public int divir(int val1, int val2)
        {
            int res = val1 / val2;
            listaHistorico.Insert(0, "Resultado Divisao: " + res + " - " + data);
            return res;
        }

        public List<string> historico()
        {
            
            listaHistorico.RemoveRange(3, listaHistorico.Count-3 );
            return listaHistorico;
        }
    }
}
