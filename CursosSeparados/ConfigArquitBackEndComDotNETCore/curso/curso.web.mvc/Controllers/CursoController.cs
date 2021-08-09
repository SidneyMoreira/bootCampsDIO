using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using curso.web.mvc.Models.Curso;
using curso.web.mvc.Services;
using Microsoft.AspNetCore.Mvc;
using Refit;

namespace curso.web.mvc.Controllers
{
    [Microsoft.AspNetCore.Authorization.Authorize]
    public class CursoController : Controller
    {
        private readonly ICursoService _cursoService;

        public CursoController(ICursoService cursoService)
        {
            _cursoService = cursoService;
        }

        
        // GET: /<controller>/
        public IActionResult Cadastrar()
        {
            return View();
        }


        [HttpPost]
        public async Task<IActionResult> Cadastrar(CadastrarCursoViewModelInput cadastrarCursoViewModelInput)
        {
            try
            {
                var curso = await _cursoService.Registrar(cadastrarCursoViewModelInput);
                ModelState.AddModelError("", $"Os curso {curso.Nome} foi cadastrado com sucesso");
            }
            catch (ApiException ex)
            {
                ModelState.AddModelError("", ex.Message);
            }
            catch (Exception ex)
            {
                ModelState.AddModelError("", ex.Message);
            }
            return View();
        }

        //GET: /<controller>/
        public async Task<IActionResult> Listar()
        {
            var cursos = await _cursoService.Obter();
            return View(cursos);
        }


        //public IActionResult Listar(ListarCursoViewModelOutput listarCursoViewModelOutput)
        //{
        //    return View();
        //}
    }
}
