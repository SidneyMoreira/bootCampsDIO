using System.Runtime.CompilerServices;
namespace Classes.Heranca
{
    public class Ponto
    {
        public int x, y;
        private int distancia;
        public Ponto(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        protected void CalcularDistancia()
        {
            //Faz algo
            CalcularDistancia2();
        }
        private void CalcularDistancia2()
        {
            //Faz algo
        }
        public virtual void CalcularDistancia3()
        {
            //Faz algo
        }

    }
}