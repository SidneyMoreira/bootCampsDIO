using System.Collections.Generic;
using System.Threading.Tasks;
using curso.web.mvc.Models.Curso;
using Refit;

namespace curso.web.mvc.Services
{
    public interface ICursoService
    {
        [Post("/api/v1/cursos")]
        [Headers("Authorization: Bearer")]
        Task<CadastrarCursoViewModelOutput> Registrar(CadastrarCursoViewModelInput cadastrarCursoViewModelInput);

        [Get("/api/v1/cursos")]
        [Headers("Authorization: Bearer")]
        Task<IList<ListarCursoViewModelOutput>> Obter();
    }
}
