using System;
using System.ComponentModel.DataAnnotations;

namespace curso.web.mvc.Models.Curso
{
    public class CadastrarCursoViewModelInput
    {
        [Required(ErrorMessage = "O noem do curso é obrigatório")]
        public string Nome { get; set; }

        [Required(ErrorMessage = "A descrição do curso é obrigatória")]
        public string Descricao { get; set; }
    }
}
