using System;
namespace DIO.Bank
{
    public class Conta
    {
        private string Nome {get; set;}
        private double Credito {get; set;}
        private double Saldo {get; set;}
        private double SaldoMesAnterior {get; set;}
        private TipoConta TipoConta {get; set;}
        public Conta(string nome, double credito, double saldo, TipoConta tipoConta)
        {
            this.Nome = nome;
            this.Credito = credito;
            this.Saldo = saldo;
            this.TipoConta = tipoConta;
        }
        public bool Sacar(double valorSaque)
        {
            //Validação de saldo suficiente
            if(this.Saldo - valorSaque < (this.Credito * -1)){
                Console.WriteLine("Saldo insuficiente!!!");
                return false;
            }

            this.Saldo -= valorSaque;

            Console.WriteLine("Saldo atual da conta de {0} é {1}", this.Nome, this.Saldo);

            return true;
        }
        public void Depositar(double valorDepositado)
        {
            this.Saldo += valorDepositado;
            Console.WriteLine("Saldo atual da conta de {0} é {1}", this.Nome, this.Saldo);
        }
        public void Transferir(double valorTransferencia, Conta contaDestino)
        {
            if(this.Sacar(valorTransferencia))
            {
                contaDestino.Depositar(valorTransferencia);
            }
        }

        public override string ToString()
        {
            string retorno = "";
            retorno += "TipoConta: " + this.TipoConta + " | ";
            retorno += "Nome: " + this.Nome + " | ";
            retorno += "Saldo: " + this.Saldo + " | ";
            retorno += "Credito: " + this.Credito;
            return retorno;
        }
    }
}