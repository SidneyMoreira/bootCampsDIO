﻿using System;
using System.ComponentModel.DataAnnotations;
using System.Reflection.Metadata;

namespace curso.api.Models.Usuarios
{
    
    public class RegistroViewModelInput
    {
        [Required(ErrorMessage = "O Login é obrigatório!")]
        public string Login { get; set; }

        [Required(ErrorMessage = "O Email é obrigatório!")]
        [EmailAddress(ErrorMessage = "O e-mail é inválido")]
        public string Email { get; set; }

        [Required(ErrorMessage = "A Senha é obrigatória!")]
        public string Senha { get; set; }
    }
}
