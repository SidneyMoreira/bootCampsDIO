using System.Collections.Generic;
using curso.web.mvc.Models.Curso;
using Microsoft.AspNetCore.Mvc;

namespace curso.web.mvc.Controllers
{
    public class CursoController : Controller
    {
        // GET: /<controller>/
        public IActionResult Cadastrar()
        {
            return View();
        }

        [HttpPost]
        public IActionResult Cadastrar(CadastrarCursoViewModelInput cadastrarCursoViewModelInput)
        {
            return View();
        }

        //GET: /<controller>/
        public IActionResult Listar()
        {
            var cursos = new List<ListarCursoViewModelOutput>
            {
                new ListarCursoViewModelOutput()
                {
                    Nome = "Curso A",
                    Descricao = "Cursoa nada a ver",
                    Login = "SidMoreira@gamil.com"
                },
                new ListarCursoViewModelOutput()
                {
                    Nome = "Curso B",
                    Descricao = "Cursoa nada a ver2",
                    Login = "SidMoreira@gamil.com"
                },
                new ListarCursoViewModelOutput()
                {
                    Nome = "Curso C",
                    Descricao = "Cursoa nada a ver3",
                    Login = "SidMoreira@gamil.com"
                }
            };
            return View(cursos);
        }


        //public IActionResult Listar(ListarCursoViewModelOutput listarCursoViewModelOutput)
        //{
        //    return View();
        //}
    }
}
