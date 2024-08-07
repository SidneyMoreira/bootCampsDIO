﻿using System;
namespace curso.web.mvc.Models.Usuario
{
    public class LoginViewModelOutput
    {
        public string Token { get; set; }
        public LoginViewModelDetalhesOutput Usuario { get; set; }
    }

    public class LoginViewModelDetalhesOutput
    {
        public int Codigo { get; set; }
        public string Login { get; set; }
        public string Email { get; set; }
    }
}
