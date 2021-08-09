using System;
using System.ComponentModel.DataAnnotations;

namespace curso.web.mvc.Models.Curso
{
    public class CadastrarCursoViewModelOutput
    {
        public string Nome { get; set; }

        public string Descricao { get; set; }

        public string Login { get; set; }
    }
}
