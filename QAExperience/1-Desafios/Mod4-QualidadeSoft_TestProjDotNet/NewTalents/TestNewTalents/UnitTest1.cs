using System;
using NewTalents;
using Xunit;

namespace TestNewTalents
{
    public class UnitTest1
    {
        public Calculadora construirCalculadora()
        {
            string data = "11/12/1975";
            Calculadora calc = new Calculadora(data);
            return calc;
        }

        [Theory]
        [InlineData(1,2,3)]
        [InlineData(4, 5, 9)]
        public void TestSomar(int val1, int val2, int resultado)
        {
            Calculadora calc = construirCalculadora();

            int resultadoCalculadora = calc.somar(val1, val2);

            Assert.Equal(resultado, resultadoCalculadora);
        }

        [Theory]
        [InlineData(21, 2, 19)]
        [InlineData(10, 3, 7)]
        public void TestSubtrair(int val1, int val2, int resultado)
        {
            Calculadora calc = construirCalculadora();

            int resultadoCalculadora = calc.subtrair(val1, val2);

            Assert.Equal(resultado, resultadoCalculadora);
        }

        [Theory]
        [InlineData(21, 2, 42)]
        [InlineData(10, 3, 30)]
        public void TestMultiplicar(int val1, int val2, int resultado)
        {
            Calculadora calc = construirCalculadora();

            int resultadoCalculadora = calc.multiplicar(val1, val2);

            Assert.Equal(resultado, resultadoCalculadora);
        }

        [Theory]
        [InlineData(21, 7, 3)]
        [InlineData(10, 2, 5)]
        public void TestDividir(int val1, int val2, int resultado)
        {
            Calculadora calc = construirCalculadora();

            int resultadoCalculadora = calc.divir(val1, val2);

            Assert.Equal(resultado, resultadoCalculadora);
        }

        [Fact]
        public void TestarDivisaoPorZero()
        {
            Calculadora calc = construirCalculadora();

            Assert.Throws<DivideByZeroException>(
                () => calc.divir(3, 0)
            );
        }

        [Fact]
        public void TestarHistorico()
        {
            Calculadora calc = construirCalculadora();

            calc.somar(4, 3);
            calc.subtrair(15, 8);
            calc.multiplicar(4, 8);
            calc.divir(30, 7);

            var lista = calc.historico();

            Assert.NotEmpty(lista);
            Assert.Equal(3, lista.Count);
        }
    }
}
