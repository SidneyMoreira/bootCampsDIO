using System;
using System.ComponentModel.DataAnnotations;

namespace curso.web.mvc.Models.Usuario
{
    public class RegistrarUsuarioViewModelInput
    {
        [Required(ErrorMessage = "O login é obrigatório")]
        public string Login { get; set; }

        [Required(ErrorMessage = "O e-mail é obrigatório")]
        [EmailAddress(ErrorMessage = "E-mail inválido!! Por favor digite um válido")]
        public string Email { get; set; }

        [Required(ErrorMessage = "A senha é obrigatória")]
        public string Senha { get; set; }
    }
}
