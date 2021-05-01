using System;
namespace CriandoCatalogJogosArqDotNET.Exceptions
{
    public class JogoJaCadastradoException : Exception
    {
        public JogoJaCadastradoException()
            : base("Este jogo já está cadastrado")
        {}
    }
}
