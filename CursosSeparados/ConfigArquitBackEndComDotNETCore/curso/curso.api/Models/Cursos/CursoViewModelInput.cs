using System.ComponentModel.DataAnnotations;

namespace curso.api.Models.Cursos
{
    public class CursoViewModelInput
    {
        [Required(ErrorMessage = "O nome do curso é obrigatorio")]
        public string Nome { get; set; }

        [Required(ErrorMessage = "O descricao do curso é obrigatoria")]
        public string Descricao { get; set; }
    }
}