using curso.web.mvc.Models.Usuario;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling MVC for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace curso.web.mvc.Controllers
{
    public class UsuarioController : Controller
    {
        // GET: /<controller>/
        public IActionResult Cadastrar()
        {
            return View();
        }

        [HttpPost]
        public IActionResult Cadastrar(RegistrarUsuarioViewModelInput registrarUsuarioViewModelInput)
        {
            return View();
        }

        public IActionResult Logar()
        {
            return View();
        }

        [HttpPost]
        public IActionResult Logar(LoginViewModelInput loginViewModelInput)
        {
            return View();
        }
    }
}
